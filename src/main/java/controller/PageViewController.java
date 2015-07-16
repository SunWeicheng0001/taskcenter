package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by weicheng on 2015/7/13.
 */
@Controller
public class PageViewController {
	public void ControlLog(String function){
		System.out.println("Recevied request to show "+function+" page");
	}
	
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String taskcenter(ModelMap model){
    	ControlLog("index");
        return "index";
    }
    @RequestMapping(value = "/taskcenter.htm", method=RequestMethod.GET)
    public String getCommonPage(){
    	ControlLog("taskcenter");
    	return "taskcenter";
    }
    @RequestMapping(value = "/loginfailed.htm", method=RequestMethod.GET)
    public String getAdminPage(){
    	ControlLog("loginfailed");
    	return "loginfailed";
    }
    @RequestMapping(value = "/logout.htm", method=RequestMethod.GET)
    public String getTaskcenterPage(){
    	ControlLog("logout");
    	return "logout";
    }
    @RequestMapping(value = "/404.htm", method=RequestMethod.GET)
    public String get404Page(){
    	ControlLog("404");
    	return "404";
    }
}
