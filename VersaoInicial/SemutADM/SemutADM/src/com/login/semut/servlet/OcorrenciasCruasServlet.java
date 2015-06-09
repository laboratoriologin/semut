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

import br.com.topsys.util.TSUtil;

import com.login.semut.dao.OcorrenciaDAO;
import com.login.semut.model.CategoriaOcorrencia;
import com.login.semut.model.GrupoOcorrencia;
import com.login.semut.model.Ocorrencia;
import com.login.semut.model.QtdPorStatus;
import com.login.semut.model.Regiao;
import com.login.semut.model.TipoSituacao;

/**
 * Servlet implementation class OcorrenciaServlet
 */
@WebServlet("/site/OcorrenciasCruasServlet")
public class OcorrenciasCruasServlet extends HttpServlet {
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
		
		List<Ocorrencia> requestOcorrencia = new ArrayList<Ocorrencia>();
		
		Integer qtdUsuario = 0;
		Integer qtdSuperUsuario = 0;

		Ocorrencia ocorrencia = new Ocorrencia();

		ocorrencia.setCategoriaOcorrencia(new CategoriaOcorrencia());

		ocorrencia.getCategoriaOcorrencia().setGrupoOcorrencia(new GrupoOcorrencia());

		ocorrencia.setTipoSituacao(new TipoSituacao());

		ocorrencia.setDataInicial(primeiroDiaMes);

		ocorrencia.setDataFinal(new Date());

		String grupo = request.getParameter("grupo");

		String porUsuario = request.getParameter("porUsuario");
		
		String categoria = request.getParameter("categoria");

		if (categoria != null && TSUtil.isNumeric(categoria)) {

			ocorrencia.getCategoriaOcorrencia().setId(Long.parseLong(categoria));

		}

		if (grupo != null && TSUtil.isNumeric(grupo)) {
			ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().setId(Long.parseLong(grupo));
		}
		
		if (request.getParameter("situacao") != null) {
			ocorrencia.getTipoSituacao().setId(Long.parseLong(request.getParameter("situacao")));
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
		
		Regiao regiao = new Regiao();
		
		if (request.getParameter("regiao") != null && !"0".equals(request.getParameter("regiao"))) {
			regiao.setId(Long.parseLong(request.getParameter("regiao")));
		}

		requestOcorrencia = new OcorrenciaDAO().pesquisarOcorrenciasCruas(ocorrencia, regiao);

		if (requestOcorrencia == null) {
			response.setContentType("application/json");
			response.getWriter().write("");
			response.flushBuffer();
		} else {

			JSONObject objJsonResponseOcorrencia = new JSONObject();
			JSONArray arrayJsonOcorrencia = new JSONArray();
			JSONArray arrayJsonQTDS = new JSONArray();
			JSONObject objJsonOcorrencia = null;

			for (Ocorrencia ocorr : requestOcorrencia) {
				objJsonOcorrencia = new JSONObject();
				try {
					objJsonOcorrencia.put("id", ocorr.getId());

					objJsonOcorrencia.put("longitude", ocorr.getLongitude());
					objJsonOcorrencia.put("latitude", ocorr.getLatitude());
					objJsonOcorrencia.put("tipoOcorrencia", ocorr.getCategoriaOcorrencia().getNome());

					if (porUsuario != null && porUsuario.equals("true")) {
						if (ocorr.getUsuario().getId() == 1) {
							objJsonOcorrencia.put("icone", "map_usuario_semut.png");
							qtdSuperUsuario += 1;
						} else {
							objJsonOcorrencia.put("icone", "map_usuario.png");
							qtdUsuario += 1;
						}

					}

					else {
						if (ocorr.getTipoSituacao().getId() == 1) {
							objJsonOcorrencia.put("icone", "map_aceitas.png");
						} else if (ocorr.getTipoSituacao().getId() == 2) {
							objJsonOcorrencia.put("icone", "map_recusadas.png");
						} else if (ocorr.getTipoSituacao().getId() == 3) {
							objJsonOcorrencia.put("icone", "map_pendentes.png");
						} else if (ocorr.getTipoSituacao().getId() == 4) {
							objJsonOcorrencia.put("icone", "map_resolvidas.png");
						}
					}

					arrayJsonOcorrencia.put(objJsonOcorrencia);
					objJsonResponseOcorrencia.put("OcorrenciasCruas", arrayJsonOcorrencia);
				} catch (JSONException ex) {
					ex.printStackTrace();
					response.sendError(600, "Erro na pesquisa dos dados, entre em contato com o Administrador do sistema.");
				}
			}
			
			JSONObject objJsonOcorrencia1 = new JSONObject();
			
			try {
				objJsonOcorrencia1.put("Superintendencia", qtdSuperUsuario);
				objJsonOcorrencia1.put("Usuario", qtdUsuario);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			
			arrayJsonQTDS.put(objJsonOcorrencia1);
			
			try {
				objJsonResponseOcorrencia.put("Quantidades", arrayJsonQTDS);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(objJsonResponseOcorrencia.toString());
			response.flushBuffer();
		}
	}
}
