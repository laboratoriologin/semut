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
import com.login.semut.model.Regiao;
import com.login.semut.model.RegiaoCoordenada;

/**
 * Servlet implementation class OcorrenciaServlet
 */
@WebServlet("/site/RegioesServlet")
public class RegioesServlet extends HttpServlet {
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
		List<Regiao> requestRegiao = new ArrayList<Regiao>();
		
		Regiao argRregiao = new Regiao();

		String id = request.getParameter("id");

		if (id != null && TSUtil.isNumeric(id)) {
			argRregiao.setId(Long.parseLong(id));
		}

		requestRegiao = new OcorrenciaDAO().pesquisarRegioes(argRregiao);

		if (requestRegiao == null) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			JSONObject objJsonResponseOcorrencia = new JSONObject();
			JSONArray arrayJsonOcorrencia = new JSONArray();
			JSONObject objJsonOcorrencia = null;

			for (Regiao regiao : requestRegiao) {
				objJsonOcorrencia = new JSONObject();
				try {
					objJsonOcorrencia.put("id", regiao.getId());				
					objJsonOcorrencia.put("nome", regiao.getNome());
					
					JSONObject objJsonCoordenada = null;
					JSONArray arrayJsonCoordenada = new JSONArray();
					for (RegiaoCoordenada coordenada : regiao.getListRegiaoCoordenada()) {
						objJsonCoordenada = new JSONObject();
						objJsonCoordenada.put("latitude", coordenada.getLatitude());
						objJsonCoordenada.put("longitude", coordenada.getLongitude());
						
						arrayJsonCoordenada.put(objJsonCoordenada);
					}
					
					objJsonOcorrencia.put("coordenadas", arrayJsonCoordenada);
					arrayJsonOcorrencia.put(objJsonOcorrencia);
					objJsonResponseOcorrencia.put("regioes", arrayJsonOcorrencia);
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
