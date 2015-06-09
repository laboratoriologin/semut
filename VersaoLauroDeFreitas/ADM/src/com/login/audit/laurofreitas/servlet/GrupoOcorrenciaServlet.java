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

import com.login.audit.laurofreitas.dao.GrupoOcorrenciaDAO;
import com.login.audit.laurofreitas.model.GrupoOcorrencia;

/**
 * Servlet implementation class NoticiaServlet
 */
@WebServlet("/servlet/GrupoOcorrenciaServlet")
public class GrupoOcorrenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GrupoOcorrenciaServlet() {
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

		List<GrupoOcorrencia> requestGrupoOcorrencia = new ArrayList<GrupoOcorrencia>();
		requestGrupoOcorrencia = new GrupoOcorrenciaDAO().pesquisar();

		if (requestGrupoOcorrencia == null) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			/* Noticia */
			JSONObject objJsonResponseNoticia = new JSONObject();
			JSONArray arrayJsonNoticia = new JSONArray();
			JSONObject objJsonNoticia = null;

			for (GrupoOcorrencia grupoCategoriaOcorrencia : requestGrupoOcorrencia) {

				objJsonNoticia = new JSONObject();
				
				try {
					objJsonNoticia.put("id", grupoCategoriaOcorrencia.getId());
					objJsonNoticia.put("nome", grupoCategoriaOcorrencia.getNome());
					arrayJsonNoticia.put(objJsonNoticia);
					objJsonResponseNoticia.put("grupoOcorrencias", arrayJsonNoticia);					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(objJsonResponseNoticia.toString());
			response.flushBuffer();
		}

	}

}
