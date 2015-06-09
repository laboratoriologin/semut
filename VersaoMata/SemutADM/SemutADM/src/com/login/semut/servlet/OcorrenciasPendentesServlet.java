package com.login.semut.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import br.com.topsys.util.TSUtil;

import com.login.semut.dao.OcorrenciaDAO;
import com.login.semut.model.GrupoOcorrencia;
import com.login.semut.model.Ocorrencia;
import com.login.semut.util.Constantes;

/**
 * Servlet implementation class OcorrenciaServlet
 */
@WebServlet("/site/ocorrencias_pendentes")
public class OcorrenciasPendentesServlet extends HttpServlet {
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
		
		GrupoOcorrencia grupoOcorrencia = new GrupoOcorrencia(Constantes.GRUPO_ADMINISTRADOR);
		
		List<Ocorrencia> ocorrencias = new OcorrenciaDAO().pesquisarOcorrenciasPendentes(grupoOcorrencia);

		if (TSUtil.isEmpty(ocorrencias)) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			JSONArray arrayJsonOcorrencia = new JSONArray();
			JSONObject objJsonOcorrencia = null;

			for (Ocorrencia ocorrencia : ocorrencias) {
				objJsonOcorrencia = new JSONObject();
				try {
					objJsonOcorrencia.put("id", ocorrencia.getId());
					objJsonOcorrencia.put("descricao", ocorrencia.getDescricao());
					objJsonOcorrencia.put("imagem", ocorrencia.getImagem());
					objJsonOcorrencia.put("categoriaOcorrencia", ocorrencia.getCategoriaOcorrencia().getId());
					objJsonOcorrencia.put("nomeTipoOcorrencia", ocorrencia.getCategoriaOcorrencia().getNome());
					objJsonOcorrencia.put("GrupoOcorrencia", ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getNome());
					objJsonOcorrencia.put("longitude", ocorrencia.getLongitude());
					objJsonOcorrencia.put("latitude", ocorrencia.getLatitude());
					objJsonOcorrencia.put("data", formatter.format(ocorrencia.getData()));
					objJsonOcorrencia.put("dataAlerta", ocorrencia.getDataAlerta()==null?"":formatter.format(ocorrencia.getDataAlerta()));
					arrayJsonOcorrencia.put(objJsonOcorrencia);
					
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(arrayJsonOcorrencia.toString());
			response.flushBuffer();
		}
	}
}
