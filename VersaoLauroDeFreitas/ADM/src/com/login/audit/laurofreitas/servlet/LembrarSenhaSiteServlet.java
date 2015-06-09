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

import com.login.audit.laurofreitas.dao.UsuarioDAO;
import com.login.audit.laurofreitas.model.Usuario;
import com.login.audit.laurofreitas.util.Constantes;
import com.login.audit.laurofreitas.util.EmailUtil;

/**
 * Servlet implementation class NoticiaServlet
 */
@WebServlet("/site/LembrarSenhaSiteServlet")
public class LembrarSenhaSiteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LembrarSenhaSiteServlet() {
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

		Usuario usuario = new Usuario();
		String email = new String();

		if (request.getParameter("email") != null) {
			email = (request.getParameter("email"));
		}

		usuario.setEmail(email);

		Usuario requestUsuario = new UsuarioDAO().obterPorEmail(usuario);
		// requestNoticia.setKeyMobile(obj.toString());

		if (requestUsuario == null) {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(new JSONObject().put("status", "0").toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.flushBuffer();

		} else {

			Random random = new Random();
			String novaSenha = new String();

			for (int i = 0; i < 6; i++) {
				novaSenha += String.valueOf(random.nextInt(10));
			}

			requestUsuario.setSenha(TSCryptoUtil.gerarHash(novaSenha, TSConstant.CRIPTOGRAFIA_MD5));

			try {

				new UsuarioDAO().alterarSenha(requestUsuario);

				EmailUtil.enviar(requestUsuario.getEmail(), "Sua nova senha é: " + novaSenha);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				try {
					response.getWriter().write(new JSONObject().put("status", "1").toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response.flushBuffer();

			} catch (TSApplicationException e) {
				e.printStackTrace();
			}

		}

	}

}
