package com.login.audit.laurofreitas.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;

import com.login.audit.laurofreitas.dao.OcorrenciaDAO;
import com.login.audit.laurofreitas.model.CategoriaOcorrencia;
import com.login.audit.laurofreitas.model.Ocorrencia;
import com.login.audit.laurofreitas.model.TipoSituacao;
import com.login.audit.laurofreitas.model.UsuarioAPP;
import com.login.audit.laurofreitas.util.Constantes;

/**
 * Servlet implementation class PreCadastroImovel
 */
@WebServlet("/servlet/insert_ocorrencia")
public class CadastroOcorrencia extends HttpServlet {
	private static final String FILE = "file";
	private static final String ID = "id";
	private static final String DESCRICAO = "descricao";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String CATEGORIA = "categoria";
	private static final long serialVersionUID = 1L;
	private Ocorrencia ocorrencia;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CadastroOcorrencia() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		execute(req, resp);
	};

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		ocorrencia = new Ocorrencia();

		ocorrencia.setStatus(false);
		
		ocorrencia.setTipoSituacao(new TipoSituacao(3L));

		ocorrencia.setData(new Timestamp(System.currentTimeMillis()));
		
		ocorrencia.setDataPendencia(new Timestamp(System.currentTimeMillis()));

		InputStream fileContent = null;

		try {

			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

			for (FileItem item : items) {

				if (CATEGORIA.equals(item.getFieldName())) {
					ocorrencia.setCategoriaOcorrencia(new CategoriaOcorrencia(Long.parseLong(item.getString())));
				}

				if (LONGITUDE.equals(item.getFieldName())) {
					ocorrencia.setLongitude(Double.parseDouble(item.getString()));
				}

				if (LATITUDE.equals(item.getFieldName())) {
					ocorrencia.setLatitude(Double.parseDouble(item.getString()));
				}

				if (DESCRICAO.equals(item.getFieldName())) {
					ocorrencia.setDescricao(item.getString());
				}

				if (ID.equals(item.getFieldName())) {
					ocorrencia.setUsuario(new UsuarioAPP(Long.parseLong(item.getString())));
				}

				if (FILE.equals(item.getFieldName())) {
					fileContent = item.getInputStream();
				}

			}

		} catch (FileUploadException e1) {

			// e1.printStackTrace();
		}

		if (!TSUtil.isEmpty(request.getParameter(LONGITUDE))) {
			ocorrencia.setLongitude(Double.parseDouble(request.getParameter(LONGITUDE)));
		}

		if (!TSUtil.isEmpty(request.getParameter(LATITUDE))) {
			ocorrencia.setLatitude(Double.parseDouble(request.getParameter(LATITUDE)));
		}
		if (!TSUtil.isEmpty(request.getParameter(DESCRICAO))) {
			ocorrencia.setDescricao(request.getParameter(DESCRICAO));
		}
		if (!TSUtil.isEmpty(request.getParameter(ID))) {
			UsuarioAPP usuario = new UsuarioAPP(Long.parseLong(request.getParameter(ID)));
			ocorrencia.setUsuario(usuario);
		}
		if (!TSUtil.isEmpty(request.getParameter(CATEGORIA))) {
			CategoriaOcorrencia categoria = new CategoriaOcorrencia(Long.parseLong(request.getParameter(CATEGORIA)));
			ocorrencia.setCategoriaOcorrencia(categoria);
		}

		try {

			boolean hasImage = fileContent != null;

			ocorrencia = new OcorrenciaDAO().inserir(ocorrencia, hasImage);

			if (hasImage) {

				this.inserirArquivos(fileContent);

			}

			responseStatusJSON(response, ocorrencia.getId().toString());

		} catch (TSApplicationException e) {
			e.printStackTrace();
			responseStatusJSON(response, "0");
		}

	}

	private void inserirArquivos(InputStream fileContent) {

		try {

			TSFile.inputStreamToFile(fileContent, Constantes.CAMINHO_ARQUIVO + ocorrencia.getId().toString() + ".jpg");

		} catch (TSApplicationException e) {
			e.printStackTrace();
		}

	}

	private void responseStatusJSON(HttpServletResponse response, String resultado) throws IOException {
		response.setContentType("application/json");
		JSONObject object = new JSONObject();
		try {
			object.put("status", resultado);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.getWriter().write(object.toString());
		response.flushBuffer();
	}

}
