package controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController extends BaseController{
	@Autowired
	TaskService taskService;
	
	@RequestMapping(value="/listTask.ajax", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody Map listTask(@RequestBody Map param){
		System.out.println("listTask.ajax");
		try {
			return initResult(true, taskService.getTaskList(param));
		} catch (Exception e) {
			e.printStackTrace();
			return initResult(false, e.getMessage(),"");
		}
	}
	
	@RequestMapping(value="/myUser.ajax", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody Map myUser(@RequestBody Map param){
		System.out.println("myUser.ajax");
		try {
			return initResult(true, taskService.myUser());
		} catch (Exception e) {
			e.printStackTrace();
			return initResult(false, e.getMessage(),"");
		}
	}
	
	@RequestMapping(value="/publishTask.ajax", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody Map publishTask(@RequestBody Map param){
		System.out.println("publishTask.ajax");
		try{
			Map user = taskService.myUser();
			int userid = (Integer)user.get("userid");
			param.put("userid",userid);
			return initResult(true, taskService.publishTask(param));
		}catch(Exception e){
			e.printStackTrace();
			return initResult(false, e.getMessage(),"");
		}
	}
	
	@RequestMapping(value="/deleteTask.ajax", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody Map deleteTask(@RequestBody Map param){
		System.out.println("deleteTask.ajax");
		try{
			return initResult(true, taskService.deleteTask(param));
		}catch(Exception e){
			e.printStackTrace();
			return initResult(false, e.getMessage(),"");
		}
	}
	
	@RequestMapping(value="/editTask.ajax", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody Map editTask(@RequestBody Map param){
		System.out.println("editTask.ajax");
		try{
			return initResult(true, taskService.editTask(param));
		}catch(Exception e){
			e.printStackTrace();
			return initResult(false, e.getMessage(),"");
		}
	}
}
