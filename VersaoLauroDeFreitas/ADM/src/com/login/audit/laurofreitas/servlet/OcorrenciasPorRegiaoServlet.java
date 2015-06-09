package com.login.audit.laurofreitas.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.login.audit.laurofreitas.dao.RegiaoDAO;
import com.login.audit.laurofreitas.model.CategoriaOcorrencia;
import com.login.audit.laurofreitas.model.GrupoOcorrencia;
import com.login.audit.laurofreitas.model.Ocorrencia;
import com.login.audit.laurofreitas.model.Regiao;
import com.login.audit.laurofreitas.model.RegiaoCoordenada;

/**
 * Servlet implementation class OcorrenciaServlet
 */
@WebServlet("/site/OcorrenciasPorRegiaoServlet")
public class OcorrenciasPorRegiaoServlet extends HttpServlet {
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

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.DAY_OF_MONTH, 1);

		Date primeiroDiaMes = calendar.getTime();

		List<Regiao> regioes = new ArrayList<Regiao>();

		Ocorrencia ocorrencia = new Ocorrencia();

		ocorrencia.setCategoriaOcorrencia(new CategoriaOcorrencia());

		ocorrencia.getCategoriaOcorrencia().setGrupoOcorrencia(new GrupoOcorrencia());

		ocorrencia.setDataInicial(primeiroDiaMes);

		ocorrencia.setDataFinal(new Date());

		String categoria = request.getParameter("categoria");

		if (categoria != null && TSUtil.isNumeric(categoria)) {

			ocorrencia.getCategoriaOcorrencia().setId(Long.parseLong(categoria));

		}

		String grupo = request.getParameter("grupo");

		if (grupo != null && TSUtil.isNumeric(grupo)) {

			ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().setId(Long.parseLong(grupo));

		}

		if (request.getParameter("data_inicial") != null) {

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			try {

				ocorrencia.setDataInicial(formatter.parse(request.getParameter("data_inicial")));

			} catch (ParseException ex) {

				ocorrencia.setDataInicial(primeiroDiaMes);

			}

		}

		if (request.getParameter("data_final") != null) {

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			try {

				Date date = formatter.parse(request.getParameter("data_final"));

				date.setTime(date.getTime() + 86400000);

				ocorrencia.setDataFinal(date);

			} catch (ParseException ex) {

				Date date = new Date();
				date.setTime(date.getTime() + 86400000);
				ocorrencia.setDataFinal(date);
			}

		}

		Regiao argRegiao = new Regiao();

		if (request.getParameter("regiao") != null && !"0".equals(request.getParameter("regiao"))) {
			argRegiao.setId(Long.parseLong(request.getParameter("regiao")));
		}

		RegiaoDAO regiaoDAO = new RegiaoDAO();

		regioes = regiaoDAO.pesquisarQtdSituacao(ocorrencia, argRegiao);
		if (TSUtil.isEmpty(regioes)) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();

		} else {

			boolean existe = false;
			JSONObject dados = new JSONObject();
			JSONObject objJsonQtdPorStatus = null;
			JSONArray jsonRegioes = new JSONArray();
			try {
				for (Regiao regiao : regioes) {

					objJsonQtdPorStatus = new JSONObject();
					objJsonQtdPorStatus.put("id", regiao.getId());
					objJsonQtdPorStatus.put("nome", regiao.getNome());
					objJsonQtdPorStatus.put("QTD_PENDENTE", regiao.getQuantidadePendente());
					objJsonQtdPorStatus.put("QTD_ACEITA", regiao.getQuantidadeAceita());
					objJsonQtdPorStatus.put("QTD_RECUSADA", regiao.getQuantidadeRecusada());
					objJsonQtdPorStatus.put("QTD_RESOLVIDA", regiao.getQuantidadeResolvida());
					objJsonQtdPorStatus.put("icone", "map_statusBairro.png");

					if (regiao.getQuantidadeAceita() > 0 || regiao.getQuantidadePendente() > 0 || regiao.getQuantidadeRecusada() > 0 || regiao.getQuantidadeResolvida() > 0) {
						existe = true;
					}

					JSONArray array = new JSONArray();

					JSONObject ponto = null;

					regiao.setListRegiaoCoordenada(regiaoDAO.pesquisar(regiao));

					for (RegiaoCoordenada coordenada : regiao.getListRegiaoCoordenada()) {

						ponto = new JSONObject();
						ponto.put("lat", coordenada.getLatitude());
						ponto.put("lng", coordenada.getLongitude());
						array.put(ponto);
					}

					objJsonQtdPorStatus.put("pontos", array);

					jsonRegioes.put(objJsonQtdPorStatus);

				}

				dados.put("regioes", jsonRegioes);
				dados.put("existe", existe);
			} catch (JSONException ex) {
				ex.printStackTrace();
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(dados.toString());
			response.flushBuffer();
		}
	}
}
