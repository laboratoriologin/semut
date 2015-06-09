package com.login.semut.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class
 */
@WebFilter("/servlet/*")
public class SemutServletFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		if (isRequestFromMobile(req) || true) {
			chain.doFilter(request, response);
		}

	}

	private boolean isRequestFromMobile(HttpServletRequest request) {
		if (getKeyMd5().equals(request.getParameter("key_servlet"))) {
			return true;
		}

		return false;
	}

	private String getKeyMd5() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		return Utilitarios.gerarHash(Constantes.KEY_SERVLET + dateFormat.format(cal.getTime()).toString());
	}
}
