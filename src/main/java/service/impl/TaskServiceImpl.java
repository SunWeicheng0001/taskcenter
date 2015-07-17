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
		if(taskList!=null && "���ڽ�����".equals(param.get("task_status"))){
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
			throw new Exception("���ȵ�¼");
		}
		int userid = (Integer)user.get("userid");
		if(userid!=(Integer)param.get("userid")){
			throw new Exception("���������ڵ�¼���û���");
		}
		Map task = taskMapper.getTaskById(param);
		if(task==null){
			throw new Exception("���񲻴���");
		}
		if("!δ����".equals((String)task.get("task_status"))){
			throw new Exception("�������Ѿ�����ȡ");
		}
		
		Map taskStatusMap = new HashMap();
		taskStatusMap.put("task_id", task.get("task_id"));
		taskStatusMap.put("task_status_old", task.get("task_status"));
		taskStatusMap.put("task_status_new", "���ڽ�����");
		if(1!=taskMapper.updateTaskStatus(taskStatusMap)){
			throw new Exception("��ȡ����ʧ��");
		}
		Map processMap = new HashMap();
		processMap.put("task_id", task.get("task_id"));
		processMap.put("userid", user.get("userid"));
		processMap.put("process_duration", param.get("duration"));
		if(1!=taskMapper.insertProcess(processMap)){
			throw new Exception("��ȡ����ʧ��");
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean publishTask(Map param) throws Exception {
		Map user = myUser();
		if(user == null){
			throw new Exception("���ȵ�¼");
		}
		int userid = (Integer)user.get("userid");
		if(userid!=(Integer)param.get("userid")){
			throw new Exception("���������ڵ�¼���û���");
		}
		int max_name = 128;
		if(((String)param.get("task_name")).length() > max_name){
			throw new Exception("�����Ϊ" + max_name + "���ַ�");
		}
		
		int max_detail = 20000;
		if(((String)param.get("task_detail")).length() > max_detail){
			throw new Exception("������ϸ����̫������ɾ�������������ύ");
		}
		param.put("task_publisher", user.get("userid"));
		if (1 != taskMapper.insertTask(param)) {
			throw new Exception("��������ʧ��");
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
			throw new Exception("���ȵ�¼");
		}
		int userid = (Integer)user.get("userid");
		if(userid!=(Integer)param.get("userid")){
			throw new Exception("���������ڵ�¼���û���");
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
			throw new Exception("���ȵ�¼");
		}
		int userid = (Integer)user.get("userid");
		if(userid!=(Integer)param.get("userid")){
			throw new Exception("���������ڵ�¼���û���");
		}
		Map task = taskMapper.getTaskById(param);
		if (task == null) {
			throw new Exception("�Ҳ���������");
		}
		if ("��ɾ��".equals(task.get("task_status"))) {
			throw new Exception("�������Ѿ���ɾ��");
		}

		if (!"δ����".equals(task.get("task_status"))) {
			throw new Exception("�����񲻴��ڡ�δ����״̬������ɾ��");
		}
		Map taskStatus = new HashMap();
		taskStatus.put("task_id", param.get("task_id"));
		taskStatus.put("task_status_old", task.get("task_status"));
		taskStatus.put("task_status_new", "��ɾ��");
		if (1 != taskMapper.updateTaskStatus(taskStatus)) {
			throw new Exception("ɾ������ʧ��");
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
