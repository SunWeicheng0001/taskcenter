package service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
		System.out.println(taskList);
//		splitTaskDetail(taskList, "task_detail", "task_detail_list");
//		System.out.println(taskList);
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
	public Boolean pickTask(Map param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean publishTask(Map param) throws Exception {
		int res = taskMapper.insertTask(param);
		if(res!=0)
			return true;
		return false;
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
		// TODO Auto-generated method stub
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
	public Boolean deleteTask(Map param) throws Exception {
		try {
			taskMapper.deleteTask(param);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
		user.remove("user_password");
		return user;
	}

}
