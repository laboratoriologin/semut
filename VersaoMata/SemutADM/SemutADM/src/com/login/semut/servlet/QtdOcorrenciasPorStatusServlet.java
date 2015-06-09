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

import org.json.JSONException;
import org.json.JSONObject;

import br.com.topsys.util.TSUtil;

import com.login.semut.dao.OcorrenciaDAO;
import com.login.semut.model.CategoriaOcorrencia;
import com.login.semut.model.GrupoOcorrencia;
import com.login.semut.model.Ocorrencia;
import com.login.semut.model.QtdPorStatus;
import com.login.semut.model.Regiao;

/**
 * Servlet implementation class OcorrenciaServlet
 */
@WebServlet("/site/QtdOcorrenciasPorStatusServlet")
public class QtdOcorrenciasPorStatusServlet extends HttpServlet {
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

		List<QtdPorStatus> requestQtd = new ArrayList<QtdPorStatus>();
		List<QtdPorStatus> requestQtdRegiao = new ArrayList<QtdPorStatus>();

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

		requestQtd = new OcorrenciaDAO().pesquisarQtdOcorrenciasPorStatus(ocorrencia, null);
		
		String categoria = request.getParameter("categoria");

		if (categoria != null && TSUtil.isNumeric(categoria)) {

			ocorrencia.getCategoriaOcorrencia().setId(Long.parseLong(categoria));
		}

		Regiao regiao = new Regiao();
		if (request.getParameter("regiao") != null && !"0".equals(request.getParameter("regiao"))) {

			regiao.setId(Long.parseLong(request.getParameter("regiao")));
		}
		
		requestQtdRegiao = new OcorrenciaDAO().pesquisarQtdOcorrenciasPorStatus(ocorrencia, (regiao.getId() != null? regiao:null));
		
		Integer qtdTotal = 0;
		Integer qtdTotalRegiao = 0;

		JSONObject objJsonQtdPorStatus = new JSONObject();

		if (requestQtd == null) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			for (QtdPorStatus qtd : requestQtd) {
				try {
					objJsonQtdPorStatus.put("QTD_" + qtd.getStatus().toUpperCase(), qtd.getQtd());
					qtdTotal+=qtd.getQtd();
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		}

		if (requestQtdRegiao == null) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			for (QtdPorStatus qtd : requestQtdRegiao) {
				try {
					objJsonQtdPorStatus.put("QTD_REGIAO_"+qtd.getStatus().toUpperCase(), qtd.getQtd());
					qtdTotalRegiao+=qtd.getQtd();
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		try {
			objJsonQtdPorStatus.put("QTD_TOTAL", qtdTotal);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			objJsonQtdPorStatus.put("QTD_TOTAL_REGIAO", qtdTotalRegiao);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(objJsonQtdPorStatus.toString());
		response.flushBuffer();
	}
}
