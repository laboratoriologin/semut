package com.login.audit.laurofreitas.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSCryptoUtil;
import br.com.topsys.util.TSUtil;

import com.login.audit.laurofreitas.dao.UsuarioAPPDAO;
import com.login.audit.laurofreitas.model.UsuarioAPP;

/**
 * Servlet implementation class PreCadastroImovel
 */
@WebServlet("/servlet/set_usuario")
public class CadastroUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CadastroUsuario() {
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

		UsuarioAPP usuario = new UsuarioAPP();

		usuario.setNome(request.getParameter("nome"));

		usuario.setSenha(TSCryptoUtil.gerarHash(request.getParameter("senha"),TSConstant.CRIPTOGRAFIA_MD5));

		usuario.setEmail(request.getParameter("email"));

		usuario.setTelefone(request.getParameter("telefone"));

		UsuarioAPPDAO appDAO = new UsuarioAPPDAO();
		
		Boolean logado = !TSUtil.isEmpty(request.getParameter("logado"));

		if (appDAO.obterPorEmail(usuario) != null && !logado) {
			responseStatusJSON(response, "existe");
		} else {

			try {
				
				if(logado) {
					new UsuarioAPPDAO().alterar(usuario);
					responseStatusJSON(response, "alterado");

				} else {
					new UsuarioAPPDAO().inserir(usuario);
					responseStatusJSON(response, usuario.getId().toString());

				}

				
			} catch (TSApplicationException e) {
				responseStatusJSON(response, "erro");
			}

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
