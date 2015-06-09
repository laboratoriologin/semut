package com.login.semut.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.login.semut.model.CategoriaOcorrencia;
import com.login.semut.model.GrupoOcorrencia;
import com.login.semut.model.Ocorrencia;
import com.login.semut.model.QtdPorStatus;

/**
 * Servlet implementation class OcorrenciaServlet
 */
@WebServlet("/site/OcorrenciaIndividualServlet")
public class OcorrenciaIndividualServlet extends HttpServlet {
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
		Ocorrencia ocorrencia = new Ocorrencia();

		String id = request.getParameter("id");

		if (id != null && TSUtil.isNumeric(id)) {

			ocorrencia = new OcorrenciaDAO().pesquisarOcorrenciasIndividual(Integer.parseInt(id));
		}

		if (ocorrencia == null) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			JSONObject objJsonOcorrencia = new JSONObject();
			JSONObject objJsonResponseOcorrencia = new JSONObject();

			try {
				objJsonOcorrencia.put("id", ocorrencia.getId());
				objJsonOcorrencia.put("descricao", ocorrencia.getDescricao());
				objJsonOcorrencia.put("imagem", ocorrencia.getImagem());
				objJsonOcorrencia.put("usuario", ocorrencia.getUsuario().getId());
				objJsonOcorrencia.put("nomeUsuario", ocorrencia.getUsuario().getNome());
				objJsonOcorrencia.put("email", ocorrencia.getUsuario().getEmail());
				objJsonOcorrencia.put("categoriaOcorrencia", ocorrencia.getCategoriaOcorrencia().getId());
				objJsonOcorrencia.put("nomeTipoOcorrencia", ocorrencia.getCategoriaOcorrencia().getNome());
				objJsonOcorrencia.put("longitude", ocorrencia.getLongitude());
				objJsonOcorrencia.put("latitude", ocorrencia.getLatitude());
				objJsonOcorrencia.put("data", ocorrencia.getData().toString());
				
				SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  

				try {
					
					Date date = formatter.parse(ocorrencia.getData().toString());
					
					objJsonOcorrencia.put("dataFormatada", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date));
				} catch (ParseException ex) {
					
					objJsonOcorrencia.put("dataFormatada", ocorrencia.getData().toString());
				}
				
				objJsonOcorrencia.put("dataAlerta", ocorrencia.getDataAlerta()==null?"":ocorrencia.getDataAlerta().toString());
				objJsonOcorrencia.put("status", ocorrencia.getStatus());
				objJsonOcorrencia.put("situacaoId", ocorrencia.getTipoSituacao().getId());
				objJsonOcorrencia.put("situacao", ocorrencia.getTipoSituacao().getDescricao());

				objJsonResponseOcorrencia.put("Ocorrencia", objJsonOcorrencia);

			} catch (JSONException ex) {
				ex.printStackTrace();
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(objJsonResponseOcorrencia.toString());
			response.flushBuffer();
		}
	}
}
