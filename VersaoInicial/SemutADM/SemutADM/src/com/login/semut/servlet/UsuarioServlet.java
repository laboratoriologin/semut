package com.login.semut.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.login.semut.dao.UsuarioAPPDAO;
import com.login.semut.model.UsuarioAPP;

/**
 * Servlet implementation class NoticiaServlet
 */
@WebServlet("/servlet/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UsuarioServlet() {
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
		String email = new String();

		if (request.getParameter("email") != null) {
			email = (request.getParameter("email"));
		}

		usuario.setEmail(email);

		UsuarioAPP requestUsuarioAPP = new UsuarioAPPDAO().obterPorEmail(usuario);
		// requestNoticia.setKeyMobile(obj.toString());

		if (requestUsuarioAPP == null) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			/* Usuario */
			JSONObject objJsonResponseNoticia = new JSONObject();
			JSONObject objJsonNoticia = null;

			objJsonNoticia = new JSONObject();
			try {
				objJsonNoticia.put("id", requestUsuarioAPP.getId());
				objJsonNoticia.put("nome", requestUsuarioAPP.getNome());
				objJsonNoticia.put("email", requestUsuarioAPP.getEmail());
				objJsonNoticia.put("senha", requestUsuarioAPP.getSenha());
				objJsonNoticia.put("telefone", requestUsuarioAPP.getTelefone());
				objJsonResponseNoticia.put("usuario", objJsonNoticia);
			} catch (JSONException ex) {
				ex.printStackTrace();
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(objJsonResponseNoticia.toString());
			response.flushBuffer();
		}

	}

}
