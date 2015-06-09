package com.login.semut.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.topsys.util.TSUtil;

/**
 * Servlet Filter implementation class Piloto
 */
public class SemutSiteFilter implements Filter {

	private final String AJAX = "XMLHttpRequest";

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest r = (HttpServletRequest) request;

		HttpServletResponse response = (HttpServletResponse) resp;

		response.setDateHeader("Expires", -1);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, " + "no-store,must-revalidate, max-age=0, " + "post-check=0, pre-check=0");

		String uri = r.getRequestURI();

		if (uri != null) {
			uri = uri.substring(uri.lastIndexOf("/"), uri.length());
		}

		if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg")) {
			chain.doFilter(request, response);
		} else if ((!TSUtil.isEmpty(r.getSession().getAttribute(Constantes.USUARIO_CONECTADO)) || uri.contains("/login.xhtml") || uri.contains("/lembrarSenha.html") || uri.contains("/LembrarSenhaSiteServlet"))) {
			chain.doFilter(request, response);
		} else {

			if (isAjaxRequest(r)) {

				response.setStatus(600);

			} else {

				((HttpServletResponse) response).sendRedirect(r.getContextPath() + "/site/login.xhtml");

			}
		}

	}

	private boolean isAjaxRequest(HttpServletRequest request) {

		return AJAX.equals(request.getHeader("X-Requested-With"));

	}

}
