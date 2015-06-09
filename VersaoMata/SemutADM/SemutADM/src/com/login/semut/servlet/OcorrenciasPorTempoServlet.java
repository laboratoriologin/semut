package com.login.semut.servlet;

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

import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

import com.login.semut.dao.GrupoOcorrenciaDAO;
import com.login.semut.model.CategoriaOcorrencia;
import com.login.semut.model.GrupoOcorrencia;
import com.login.semut.model.Ocorrencia;
import com.login.semut.model.OcorrenciaTempo;
import com.login.semut.model.QtdPorStatus;

/**
 * Servlet implementation class OcorrenciaServlet
 */
@WebServlet("/site/ocorrencias_tempo")
public class OcorrenciasPorTempoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String MES = "mes";
	private final String DIA = "dia";
	private final String SEMANA = "semana";

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

		Ocorrencia ocorrencia = new Ocorrencia();

		ocorrencia.setCategoriaOcorrencia(new CategoriaOcorrencia());

		ocorrencia.getCategoriaOcorrencia().setGrupoOcorrencia(new GrupoOcorrencia());

		ocorrencia.setDataInicial(primeiroDiaMes);

		ocorrencia.setDataFinal(new Date());

		String grupo = request.getParameter("grupo");

		if (grupo != null && TSUtil.isNumeric(grupo)) {

			ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().setId(Long.parseLong(grupo));

		}

		String categoria = request.getParameter("categoria");

		if (categoria != null && TSUtil.isNumeric(categoria)) {

			ocorrencia.getCategoriaOcorrencia().setId(Long.parseLong(categoria));

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

		String periodo = request.getParameter("periodo");

		if (periodo == null) {

			response.setContentType("application/json");

			response.getWriter().write("");

			response.flushBuffer();

			return;

		}

		if (MES.equals(periodo)) {
			this.pesquisarPorMes(request, response, ocorrencia);
		} else if (SEMANA.equals(periodo)) {
			this.pesquisarPorSemana(request, response, ocorrencia);
		} else if (DIA.equals(periodo)) {
			this.pesquisarPorDia(request, response, ocorrencia);
		}

	}

	private void pesquisarPorDia(HttpServletRequest request, HttpServletResponse response, Ocorrencia ocorrencia) throws IOException {

		List<QtdPorStatus> grupos = new GrupoOcorrenciaDAO().pesquisarOcorrenciasPorDia(ocorrencia);

		if (grupos == null) {

			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();

		} else {

			JSONObject objJsonQtdPorStatus = null;
			JSONArray jsonGrupos = new JSONArray();
			String agrupador = null;

			List<OcorrenciaTempo> resultado = new ArrayList<>();

			OcorrenciaTempo ocorrenciaTempo = null;

			for (QtdPorStatus grupo : grupos) {

				agrupador = TSParseUtil.dateToString(TSParseUtil.stringToDate(grupo.getDataOcorrencia(), TSDateUtil.YYYY_MM_DD), TSDateUtil.DD_MM_YYYY);

				ocorrenciaTempo = new OcorrenciaTempo();

				ocorrenciaTempo.setAgrupador(agrupador);

				if (!resultado.contains(ocorrenciaTempo)) {
					resultado.add(ocorrenciaTempo);
				}

				resultado.get(resultado.indexOf(ocorrenciaTempo)).setValue(grupo);

			}

			try {

				for (OcorrenciaTempo ocTempo : resultado) {

					objJsonQtdPorStatus = new JSONObject();
					objJsonQtdPorStatus.put("agrupador", ocTempo.getAgrupador());
					objJsonQtdPorStatus.put("QTD_RESOLVIDA", ocTempo.getQuantidadeResolvida());
					objJsonQtdPorStatus.put("QTD_PENDENTE", ocTempo.getQuantidadePendente());
					objJsonQtdPorStatus.put("QTD_RECUSADA", ocTempo.getQuantidadeRecusada());
					objJsonQtdPorStatus.put("QTD_ACEITA", ocTempo.getQuantidadeAceita());
					jsonGrupos.put(objJsonQtdPorStatus);
				}

			} catch (JSONException ex) {
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonGrupos.toString());
			response.flushBuffer();
		}
	}

	private void pesquisarPorMes(HttpServletRequest request, HttpServletResponse response, Ocorrencia ocorrencia) throws IOException {
		List<QtdPorStatus> grupos = new GrupoOcorrenciaDAO().pesquisarOcorrenciasPorMes(ocorrencia);

		if (grupos == null) {

			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();

		} else {

			JSONObject objJsonQtdPorStatus = null;
			JSONArray jsonGrupos = new JSONArray();

			String agrupador = null;

			List<OcorrenciaTempo> resultado = new ArrayList<>();

			OcorrenciaTempo ocorrenciaTempo = null;

			for (QtdPorStatus grupo : grupos) {

				ocorrenciaTempo = new OcorrenciaTempo();

				ocorrenciaTempo.setAgrupador(grupo.getAgrupador());
				
				// Se o mes for menor que 10, zero a esquerda para formatacao
				if (ocorrenciaTempo.getAgrupador().length() < 7) {
					ocorrenciaTempo.setAgrupador("0" + ocorrenciaTempo.getAgrupador());
				}
			
				if (!resultado.contains(ocorrenciaTempo)) {
					resultado.add(ocorrenciaTempo);
				}

				resultado.get(resultado.indexOf(ocorrenciaTempo)).setValue(grupo);

			}

			try {

				for (OcorrenciaTempo ocTempo : resultado) {

					objJsonQtdPorStatus = new JSONObject();
					objJsonQtdPorStatus.put("agrupador", ocTempo.getAgrupador());
					objJsonQtdPorStatus.put("QTD_RESOLVIDA", ocTempo.getQuantidadeResolvida());
					objJsonQtdPorStatus.put("QTD_PENDENTE", ocTempo.getQuantidadePendente());
					objJsonQtdPorStatus.put("QTD_RECUSADA", ocTempo.getQuantidadeRecusada());
					objJsonQtdPorStatus.put("QTD_ACEITA", ocTempo.getQuantidadeAceita());
					jsonGrupos.put(objJsonQtdPorStatus);
				}

			} catch (JSONException ex) {
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonGrupos.toString());
			response.flushBuffer();
		}
	}

	private void pesquisarPorSemana(HttpServletRequest request, HttpServletResponse response, Ocorrencia ocorrencia) throws IOException {

		List<QtdPorStatus> grupos = new GrupoOcorrenciaDAO().pesquisarOcorrenciasPorDia(ocorrencia);

		if (grupos == null) {

			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();

		} else {

			JSONObject objJsonQtdPorStatus = null;
			JSONArray jsonGrupos = new JSONArray();
			
			Calendar calendar = Calendar.getInstance();

			String periodoSemana = null;

			String data = null;

			List<OcorrenciaTempo> resultado = new ArrayList<>();

			OcorrenciaTempo ocorrenciaTempo = null;

			for (QtdPorStatus grupo : grupos) {

				data = TSParseUtil.dateToString(TSParseUtil.stringToDate(grupo.getDataOcorrencia(), TSDateUtil.YYYY_MM_DD), TSDateUtil.DD_MM_YYYY);

				calendar.setTime(TSParseUtil.stringToDate(data));

				calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

				periodoSemana = TSParseUtil.dateToString(calendar.getTime(), TSDateUtil.DD_MM_YYYY);

				calendar.add(Calendar.DATE, 6);

				periodoSemana = periodoSemana + "-" + TSParseUtil.dateToString(calendar.getTime(), TSDateUtil.DD_MM_YYYY);
				
				ocorrenciaTempo = new OcorrenciaTempo();

				ocorrenciaTempo.setAgrupador(periodoSemana);
				
				if (!resultado.contains(ocorrenciaTempo)) {
					resultado.add(ocorrenciaTempo);
				}

				resultado.get(resultado.indexOf(ocorrenciaTempo)).setValue(grupo);

			}
			
			try {

				for (OcorrenciaTempo ocTempo : resultado) {

					objJsonQtdPorStatus = new JSONObject();
					objJsonQtdPorStatus.put("agrupador", ocTempo.getAgrupador());
					objJsonQtdPorStatus.put("QTD_RESOLVIDA", ocTempo.getQuantidadeResolvida());
					objJsonQtdPorStatus.put("QTD_PENDENTE", ocTempo.getQuantidadePendente());
					objJsonQtdPorStatus.put("QTD_RECUSADA", ocTempo.getQuantidadeRecusada());
					objJsonQtdPorStatus.put("QTD_ACEITA", ocTempo.getQuantidadeAceita());
					jsonGrupos.put(objJsonQtdPorStatus);
				}

			} catch (JSONException ex) {
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonGrupos.toString());
			response.flushBuffer();
		}
	}
}
