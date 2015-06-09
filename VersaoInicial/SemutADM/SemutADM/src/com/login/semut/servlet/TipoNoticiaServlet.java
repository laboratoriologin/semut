package com.login.semut.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.login.semut.dao.TipoNoticiaDAO;
import com.login.semut.model.TipoNoticia;

/**
 * Servlet implementation class NoticiaServlet
 */
@WebServlet("/servlet/TipoNoticiaServlet")
public class TipoNoticiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TipoNoticiaServlet() {
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

		List<TipoNoticia> requestTipoNoticia = new ArrayList<TipoNoticia>();
		// requestNoticia.setKeyMobile(obj.toString());
		requestTipoNoticia = new TipoNoticiaDAO().pesquisar();

		if (requestTipoNoticia == null) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			/* Noticia */
			JSONObject objJsonResponseNoticia = new JSONObject();
			JSONArray arrayJsonNoticia = new JSONArray();
			JSONObject objJsonNoticia = null;

			for (TipoNoticia tipoNoticia : requestTipoNoticia) {

				objJsonNoticia = new JSONObject();
				try {
					objJsonNoticia.put("id", tipoNoticia.getId());
					objJsonNoticia.put("nome", tipoNoticia.getNome());

					arrayJsonNoticia.put(objJsonNoticia);
					objJsonResponseNoticia.put("tiponoticias", arrayJsonNoticia);
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(objJsonResponseNoticia.toString());
			response.flushBuffer();
		}

	}

}
