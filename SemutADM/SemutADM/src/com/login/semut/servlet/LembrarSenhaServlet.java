package com.login.audit.laurofreitas.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSCryptoUtil;

import com.login.audit.laurofreitas.dao.UsuarioAPPDAO;
import com.login.audit.laurofreitas.model.UsuarioAPP;
import com.login.audit.laurofreitas.util.Constantes;
import com.login.audit.laurofreitas.util.EmailUtil;

/**
 * Servlet implementation class NoticiaServlet
 */
@WebServlet("/servlet/LembrarSenhaServlet")
public class LembrarSenhaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LembrarSenhaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UsuarioAPP usuario = new UsuarioAPP();
		String email = "";

		if (request.getParameter("email") != null) {
			email = (request.getParameter("email"));
		}

		usuario.setEmail(email);

		UsuarioAPP requestUsuarioAPP = new UsuarioAPPDAO().obterPorEmail(usuario);

		if (requestUsuarioAPP == null) {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			try {
			
				response.getWriter().write(new JSONObject().put("status", "0").toString());
			
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			response.flushBuffer();

		} else {

			Random random = new Random();
			String novaSenha = "";

			for (int i = 0; i < 6; i++) {
				novaSenha += String.valueOf(random.nextInt(10));
			}

			requestUsuarioAPP.setSenha(TSCryptoUtil.gerarHash(novaSenha, TSConstant.CRIPTOGRAFIA_MD5));

			try {

				new UsuarioAPPDAO().alterarSenha(requestUsuarioAPP);

				EmailUtil.enviar( requestUsuarioAPP.getEmail(), "Sua nova senha é: " + novaSenha);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				try {
					response.getWriter().write(new JSONObject().put("status", "1").toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				response.flushBuffer();

			} catch (TSApplicationException e) {
				e.printStackTrace();
			}

		}

	}

}
