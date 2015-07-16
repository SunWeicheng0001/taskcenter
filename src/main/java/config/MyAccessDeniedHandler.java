package config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ��һ�����Ѿ���¼�����û�����ĳ����ҪȨ�޵ĵ�ַ������Ȩ�޲�����ʱ���׳��쳣�������ﴦ��
 * ����������ʱajax������ô����һ��json����
 * �������ͨ�������ҳ��������ô����Ȩ�޲���ҳ�档
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	String errorPage;

	static final ObjectMapper mapper = new ObjectMapper();
	static final Map map = new HashMap();
	static {
		map.put("success", false);
		map.put("message", "Ȩ�޲������ܾ����ʣ�");
	}
	
	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) { // ��ajax����
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().print(mapper.writeValueAsString(map));
		} else if (errorPage != null) { // ҳ�����󣬶��򵽴���ҳ��
			request.setAttribute(WebAttributes.ACCESS_DENIED_403,accessDeniedException);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
			dispatcher.forward(request, response);
		}
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}
}