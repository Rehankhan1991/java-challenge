package jp.co.axa.apidemo.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.filter.GenericFilterBean;

import jp.co.axa.apidemo.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CustomFilter extends GenericFilterBean {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// Skip Public URI, don't require auth
		if (!CommonUtils.isNotPublicUrl(req.getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}

		String username = req.getHeader("USERNAME");
		String pass = req.getHeader("PASSWORD");
		
		// Override params for swagger 
		if(StringUtils.isEmpty(username)) {
			username = "AGHFF12";
		}
		

		// Mandatory headers not passed
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(pass)) {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User Cannot be Authorized, Pass Proper header");
			return;
		}

		// This Part could be replaced with some API calls to fetch User Details
		// Start
		String optEmpMaster = "AGHFF12";
		String password = "123456";
        // End
		
		// Authorized User
		if (!username.equals(optEmpMaster) || !pass.equals(password)) {
			log.warn(String.format("Request Rejected: request_url=%s", req.getRequestURI()));
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Wrong UserID or Password");
			return;
		}

		try {
			chain.doFilter(request, response);
		} catch (RequestRejectedException e) {
			log.warn(String.format("Request Rejected: request_url=%s", req.getRequestURI()));
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}