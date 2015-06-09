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

import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

import com.login.semut.dao.GrupoOcorrenciaDAO;
import com.login.semut.dao.RegiaoDAO;
import com.login.semut.model.CategoriaOcorrencia;
import com.login.semut.model.GrupoOcorrencia;
import com.login.semut.model.Ocorrencia;
import com.login.semut.model.Regiao;
import com.login.semut.model.RegiaoCoordenada;

/**
 * Servlet implementation class OcorrenciaServlet
 */
@WebServlet("/site/ocorrencias_tempo_resolucao")
public class OcorrenciasPorTempoResolucaoServlet extends HttpServlet {
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

		ocorrencia.setCategoriaOcorrencia(new CategoriaOcorrencia());

		ocorrencia.getCategoriaOcorrencia().setGrupoOcorrencia(new GrupoOcorrencia());


		String grupo = request.getParameter("grupo");

		if (grupo != null && TSUtil.isNumeric(grupo)) {

			ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().setId(Long.parseLong(grupo));

		}

		String categoria = request.getParameter("categoria");

		if (categoria != null && TSUtil.isNumeric(categoria)) {

			ocorrencia.getCategoriaOcorrencia().setId(Long.parseLong(categoria));

		}

		List<GrupoOcorrencia> grupos = new GrupoOcorrenciaDAO().pesquisarOcorrenciasPorTempoResolucao(ocorrencia);

		if (grupos == null) {

			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();

		} else {

			JSONObject objJsonQtdPorStatus = null;
			JSONArray jsonGrupos = new JSONArray();
			for (GrupoOcorrencia grupoOcorrencia : grupos) {

				// Se o mes for menor que 10, zero a esquerda para formataacao
				if (grupoOcorrencia.getMesAnoOcorrencias().length() < 7) {
					grupoOcorrencia.setMesAnoOcorrencias("0" + grupoOcorrencia.getMesAnoOcorrencias());
				}

				try {
					objJsonQtdPorStatus = new JSONObject();
					objJsonQtdPorStatus.put("qtd", grupoOcorrencia.getQuantidadeOcorrencias());
					objJsonQtdPorStatus.put("agrupador", grupoOcorrencia.getMesAnoOcorrencias());

					jsonGrupos.put(objJsonQtdPorStatus);

				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonGrupos.toString());
			response.flushBuffer();
		}
	}

}
