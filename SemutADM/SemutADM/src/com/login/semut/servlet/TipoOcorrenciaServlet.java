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

import com.login.audit.laurofreitas.dao.TipoOcorrenciaDAO;
import com.login.audit.laurofreitas.model.CategoriaOcorrencia;
import com.login.audit.laurofreitas.model.GrupoOcorrencia;

/**
 * Servlet implementation class NoticiaServlet
 */
@WebServlet("/servlet/TipoOcorrenciaServlet")
public class TipoOcorrenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TipoOcorrenciaServlet() {
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

		List<CategoriaOcorrencia> requestCategoriaOcorrencia = new ArrayList<CategoriaOcorrencia>();
		CategoriaOcorrencia objCategoriaOcorrencia = new CategoriaOcorrencia();
		GrupoOcorrencia grupoOcorrencia = new GrupoOcorrencia();
		if (request.getParameter("grupoOcorrencia") != null) {
			grupoOcorrencia.setId(Long.parseLong((request.getParameter("grupoOcorrencia"))));

			objCategoriaOcorrencia.setGrupoOcorrencia(grupoOcorrencia);
			requestCategoriaOcorrencia = new TipoOcorrenciaDAO().obterporfiltro(objCategoriaOcorrencia);
		} else {
			requestCategoriaOcorrencia = new TipoOcorrenciaDAO().pesquisar();
		}

		if (requestCategoriaOcorrencia == null) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			/* Noticia */
			JSONObject objJsonResponseNoticia = new JSONObject();
			JSONArray arrayJsonNoticia = new JSONArray();
			JSONObject objJsonNoticia = null;

			for (CategoriaOcorrencia tipoCategoriaOcorrencia : requestCategoriaOcorrencia) {

				objJsonNoticia = new JSONObject();
				try {
					objJsonNoticia.put("id", tipoCategoriaOcorrencia.getId());
					objJsonNoticia.put("nome", tipoCategoriaOcorrencia.getNome());
					objJsonNoticia.put("grupoOcorrencia", tipoCategoriaOcorrencia.getGrupoOcorrencia().getId());
					arrayJsonNoticia.put(objJsonNoticia);
					objJsonResponseNoticia.put("tipoOcorrencias", arrayJsonNoticia);
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
