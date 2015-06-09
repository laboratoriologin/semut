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

import com.login.semut.dao.OcorrenciaDAO;
import com.login.semut.model.Ocorrencia;

/**
 * Servlet implementation class OcorrenciaServlet
 */
@WebServlet("/servlet/OcorrenciaServlet")
public class OcorrenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		List<Ocorrencia> requestOcorrencia = new ArrayList<Ocorrencia>();
		Ocorrencia objOcorrencia = new Ocorrencia();

		if (request.getParameter("status") != null) {
			if ("1".equals(request.getParameter("status"))) {
				objOcorrencia.setStatus(true);
			} else {
				objOcorrencia.setStatus(false);
			}
		}

		requestOcorrencia = new OcorrenciaDAO().pesquisarAlertas(objOcorrencia);

		if (requestOcorrencia == null) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			JSONObject objJsonResponseOcorrencia = new JSONObject();
			JSONArray arrayJsonOcorrencia = new JSONArray();
			JSONObject objJsonOcorrencia = null;

			for (Ocorrencia ocorrencia : requestOcorrencia) {
				objJsonOcorrencia = new JSONObject();
				try {
					objJsonOcorrencia.put("id", ocorrencia.getId());
					objJsonOcorrencia.put("descricao", ocorrencia.getDescricao());
					objJsonOcorrencia.put("imagem", ocorrencia.getImagem());
					objJsonOcorrencia.put("usuario", ocorrencia.getUsuario().getId());
					objJsonOcorrencia.put("nomeUsuario", ocorrencia.getUsuario().getNome());
					objJsonOcorrencia.put("email", ocorrencia.getUsuario().getEmail());
					objJsonOcorrencia.put("categoriaOcorrencia", ocorrencia.getCategoriaOcorrencia().getId());
					objJsonOcorrencia.put("nomeTipoOcorrencia", ocorrencia.getCategoriaOcorrencia().getNome());

					objJsonOcorrencia.put("GrupoOcorrencia", ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia());

					objJsonOcorrencia.put("longitude", ocorrencia.getLongitude());
					objJsonOcorrencia.put("latitude", ocorrencia.getLatitude());
					objJsonOcorrencia.put("data", ocorrencia.getData().toString());
					objJsonOcorrencia.put("dataAlerta", ocorrencia.getDataAlerta()==null?"":ocorrencia.getDataAlerta().toString());

					objJsonOcorrencia.put("status", ocorrencia.getStatus());

					arrayJsonOcorrencia.put(objJsonOcorrencia);
					objJsonResponseOcorrencia.put("Ocorrencias", arrayJsonOcorrencia);
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(objJsonResponseOcorrencia.toString());
			response.flushBuffer();
		}
	}
}
