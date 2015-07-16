package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import service.impl.MyUserDetailsService;

import java.util.Map;

/**
 * Created by weicheng on 2015/7/13.
 */
@Controller
@RequestMapping("/user")
public class SigninController extends BaseController {
	//@Resource(name = "userMapper")
	@Autowired
	//@Resource(name="userImpl")
    private MyUserDetailsService userMapper;
    @RequestMapping(value = "/signin.ajax", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody Map signin(@RequestBody Map param){
        try {
            String username = param.get("username").toString();
            //System.out.println(username);
            return initResult(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            return initResult(false, e.getMessage(), "");
        }
    }
}
