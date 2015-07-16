package config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ��һ����δ��¼�����û�����ĳ����ҪȨ�޵ĵ�ַʱ���׳��쳣�������ﴦ��
 * ����������ʱajax������ô����һ��json����
 * �������ͨ�������ҳ��������ô���򵽵�¼ҳ�档
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint{
	String loginPage;
	static final ObjectMapper mapper = new ObjectMapper();
	static final Map map = new HashMap();
	static {
		map.put("success", false);
		map.put("message","���ȵ�¼");
	}
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){ //��ajax����
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().print(mapper.writeValueAsString(map));
		}else if(loginPage != null){
			RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
			dispatcher.forward(request, response);
		}
	}
	
	public void setLoginPage(String loginPage){
		this.loginPage = loginPage;
	}

}
