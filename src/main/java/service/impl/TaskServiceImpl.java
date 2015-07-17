package service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mapper.TaskMapper;
import mapper.UserMapper;
import service.TaskService;

@Service("taskService")
public class TaskServiceImpl implements TaskService{
	@Autowired
	TaskMapper taskMapper;
	@Autowired
	UserMapper userMapper;
	
	private void splitTaskDetail(List<Map> taskList, String name, String listName){
		if(taskList == null){
			return;
		}
		int len = 500;
		for(Map item : taskList){
			Object detailObj = item.get(name);
			if(detailObj == null){
				continue;
			}
			String detail = (String)detailObj;
			List task_detail_list = new ArrayList<String>();
			for(int i=0; i<detail.length(); i+=len){
				task_detail_list.add(detail.substring(i, Math.min(i+len, detail.length())));
			}
			item.put(listName, task_detail_list);
			item.remove(name);
		}
	}
	
	@Override
	public List getTaskList(Map param) {
		List<Map> taskList = taskMapper.getTaskList(param);
		//System.out.println(taskList);
		if(taskList!=null && "正在进行中".equals(param.get("task_status"))){
			for(Map item: taskList){
				Map publisher = taskMapper.getPublisherByTaskId(item);
				item.put("publisher", publisher);
				Map executor = taskMapper.getExecutorByTaskId(item);
				item.put("executor",executor);
			}
		}
//		splitTaskDetail(taskList, "task_detail", "task_detail_list");
		System.out.println(taskList);
		return taskList;
	}

	@Override
	public List getAllTaskList(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getTaskHistoryDetail(Map param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getUserList(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean pickTask(Map param) throws Exception {
		Map user = myUser();
		if(user == null){
			throw new Exception("请先登录");
		}
		int userid = (Integer)user.get("userid");
		if(userid!=(Integer)param.get("userid")){
			throw new Exception("您不是正在登录的用户！");
		}
		Map task = taskMapper.getTaskById(param);
		if(task==null){
			throw new Exception("任务不存在");
		}
		if("!未处理".equals((String)task.get("task_status"))){
			throw new Exception("该任务已经被领取");
		}
		
		Map taskStatusMap = new HashMap();
		taskStatusMap.put("task_id", task.get("task_id"));
		taskStatusMap.put("task_status_old", task.get("task_status"));
		taskStatusMap.put("task_status_new", "正在进行中");
		if(1!=taskMapper.updateTaskStatus(taskStatusMap)){
			throw new Exception("领取任务失败");
		}
		Map processMap = new HashMap();
		processMap.put("task_id", task.get("task_id"));
		processMap.put("userid", user.get("userid"));
		processMap.put("process_duration", param.get("duration"));
		if(1!=taskMapper.insertProcess(processMap)){
			throw new Exception("领取任务失败");
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean publishTask(Map param) throws Exception {
		Map user = myUser();
		if(user == null){
			throw new Exception("请先登录");
		}
		int userid = (Integer)user.get("userid");
		if(userid!=(Integer)param.get("userid")){
			throw new Exception("您不是正在登录的用户！");
		}
		int max_name = 128;
		if(((String)param.get("task_name")).length() > max_name){
			throw new Exception("标题最长为" + max_name + "个字符");
		}
		
		int max_detail = 20000;
		if(((String)param.get("task_detail")).length() > max_detail){
			throw new Exception("任务详细描述太长，请删除部分内容再提交");
		}
		param.put("task_publisher", user.get("userid"));
		if (1 != taskMapper.insertTask(param)) {
			throw new Exception("发布任务失败");
		}
		return true;
	}

	@Override
	public Boolean cancelTask(Map param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delayTask(Map param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean endTask(Map param) throws Exception {
		Map user = myUser();
		if(user == null){
			throw new Exception("请先登录");
		}
		int userid = (Integer)user.get("userid");
		if(userid!=(Integer)param.get("userid")){
			throw new Exception("您不是正在登录的用户！");
		}
		Map process = taskMapper.getProcessById(param);
		return null;
	}

	@Override
	public Boolean editTask(Map param) throws Exception {
		int res = taskMapper.updateTask(param);
		if(res != 0)
			return true;
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean deleteTask(Map param) throws Exception {
		Map user = myUser();
		if(user == null){
			throw new Exception("请先登录");
		}
		int userid = (Integer)user.get("userid");
		if(userid!=(Integer)param.get("userid")){
			throw new Exception("您不是正在登录的用户！");
		}
		Map task = taskMapper.getTaskById(param);
		if (task == null) {
			throw new Exception("找不到该任务");
		}
		if ("已删除".equals(task.get("task_status"))) {
			throw new Exception("该任务已经被删除");
		}

		if (!"未处理".equals(task.get("task_status"))) {
			throw new Exception("该任务不处于【未处理】状态，不能删除");
		}
		Map taskStatus = new HashMap();
		taskStatus.put("task_id", param.get("task_id"));
		taskStatus.put("task_status_old", task.get("task_status"));
		taskStatus.put("task_status_new", "已删除");
		if (1 != taskMapper.updateTaskStatus(taskStatus)) {
			throw new Exception("删除任务失败");
		}
		return true;
	}

	@Override
	public Map myUser() throws Exception {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails == null || userDetails.getUsername() == null) {
			return null;
		}
		
		Map user = userMapper.getUser(userDetails.getUsername());
		System.out.println(user);
		user.remove("password");
		return user;
	}

}
