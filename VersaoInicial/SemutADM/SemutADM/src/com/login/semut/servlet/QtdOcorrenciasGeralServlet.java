package com.login.semut.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.login.semut.dao.OcorrenciaDAO;
import com.login.semut.model.QtdPorStatus;
import com.login.semut.model.TipoSituacao;
import com.login.semut.util.Constantes;

/**
 * Servlet implementation class OcorrenciaServlet
 */
@WebServlet("/site/qtd_ocorrencias_geral")
public class QtdOcorrenciasGeralServlet extends HttpServlet {
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

		OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
		QtdPorStatus geral = ocorrenciaDAO.obterPendentesGeral();
		geral.setStatus("geral");
		requestQtd.add(geral);
		TipoSituacao situacao = new TipoSituacao(Constantes.SITUACAO_RESOLVIDA);
		
		requestQtd.addAll(ocorrenciaDAO.pesquisarPorGrupo(situacao));

		if (requestQtd.isEmpty()) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			JSONObject objJsonQtdPorStatus = new JSONObject();

			for (QtdPorStatus qtd : requestQtd) {
				try {
					objJsonQtdPorStatus.put(qtd.getStatus().toLowerCase(), qtd.getQtd()==null?0:qtd.getQtd());
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(objJsonQtdPorStatus.toString());
			response.flushBuffer();
		}
	}
}
