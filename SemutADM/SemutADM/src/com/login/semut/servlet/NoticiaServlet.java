package com.login.audit.laurofreitas.servlet;

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

import com.login.audit.laurofreitas.dao.NoticiaDAO;
import com.login.audit.laurofreitas.model.Noticia;
import com.login.audit.laurofreitas.model.TipoNoticia;

/**
 * Servlet implementation class NoticiaServlet
 */
@WebServlet("/servlet/NoticiaServlet")
public class NoticiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoticiaServlet() {
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

		List<Noticia> requestNoticia = new ArrayList<Noticia>();
		Noticia objNoticia = new Noticia();
		TipoNoticia tipoNoticia = new TipoNoticia();
		if (request.getParameter("tipoNoticia") != null) {
			tipoNoticia.setId(Long.parseLong((request.getParameter("tipoNoticia"))));
		}

		objNoticia.setTipoNoticia(tipoNoticia);
		// requestNoticia.setKeyMobile(obj.toString());
		requestNoticia = new NoticiaDAO().obterporfiltro(objNoticia);

		if (requestNoticia == null) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			/* Noticia */
			JSONObject objJsonResponseNoticia = new JSONObject();
			JSONArray arrayJsonNoticia = new JSONArray();
			JSONObject objJsonNoticia = null;

			for (Noticia noticia : requestNoticia) {

				objJsonNoticia = new JSONObject();
				try {
					objJsonNoticia.put("id", noticia.getId());
					objJsonNoticia.put("titulo", noticia.getTitulo());
					objJsonNoticia.put("descricao", noticia.getDescricao());

					if (noticia.getImagem() == null) {
						objJsonNoticia.put("imagem", "");
					} else {
						objJsonNoticia.put("imagem", noticia.getImagem());
					}
					objJsonNoticia.put("data", noticia.getData().toString());
					objJsonNoticia.put("tipoNoticia", noticia.getTipoNoticia().getId());
					objJsonNoticia.put("nomeTipoNoticia", noticia.getTipoNoticia().getNome());
					arrayJsonNoticia.put(objJsonNoticia);
					objJsonResponseNoticia.put("noticias", arrayJsonNoticia);
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
