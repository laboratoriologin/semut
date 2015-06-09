/**
 * 
 */
package com.login.semut.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

import com.login.semut.dao.TokenAndroidDAO;
import com.login.semut.dao.TokenIphoneDAO;
import com.login.semut.model.Token;

@WebServlet("/servlet/novo_token")
public class NovoTokenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NovoTokenServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String token = request.getParameter("token");
		String modelo = request.getParameter("modelo");

		if (!TSUtil.isEmpty(token)) {

			if ("iphone".equals(modelo)) {

				TokenIphoneDAO dao = new TokenIphoneDAO();

				Token tokenIphone = new Token(token);

				if (!TSUtil.isEmpty(dao.obter(tokenIphone))) {

					responseStatusJSON(response, "existe");

				} else {

					try {

						dao.inserir(tokenIphone);

						responseStatusJSON(response, "ok");

					} catch (TSApplicationException e) {

						responseStatusJSON(response, "erro");

					}

				}
			}
			else if ("android".equals(modelo)) {
				TokenAndroidDAO daoAndroid = new TokenAndroidDAO();
				Token tokenAndroid = new Token(token);
				
				if (!TSUtil.isEmpty(daoAndroid.obter(tokenAndroid))) {

					responseStatusJSON(response, "existe");

				} else {

					try {

						daoAndroid.inserir(tokenAndroid);

						responseStatusJSON(response, "ok");

					} catch (TSApplicationException e) {

						responseStatusJSON(response, "erro");

					}

				}
				
			}
			else{
				responseStatusJSON(response, "erro");
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
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
