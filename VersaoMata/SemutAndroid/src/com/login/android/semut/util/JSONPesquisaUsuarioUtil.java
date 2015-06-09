package com.login.android.semut.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.login.android.semut.DefaultActivity;
import com.login.android.semut.business.Observable;
import com.login.android.semut.model.Usuario;

public class JSONPesquisaUsuarioUtil extends AsyncTask<Object, Void, Usuario> {

	private Observable observable;

	public JSONPesquisaUsuarioUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected Usuario doInBackground(Object... params) {
		Usuario usuario = params == null || params.length == 0 ? null : (Usuario) params[0];
		Boolean pesquisarPorEmail = params == null || params.length == 0 ? null : (Boolean) params[1];

		JSONObject jsonObject = new JSONObject();
		if (pesquisarPorEmail == true && usuario != null) {

			jsonObject = new HttpUtil().getJSONFromURL(this.getURLUsusarioLogin(usuario));

			if (jsonObject != null) {
				try {
					return this.recuperarUsuario(jsonObject);
				} catch (JSONException ex) {
					return null;
				}
			}
		} else {
			return null;
		}

		return null;
	}

	private String getURLUsusarioLogin(Usuario usuario) {

		StringBuilder url = new StringBuilder(Constantes.URL_USUARIO);

		if (usuario.getEmail() != null && !TextUtils.isEmpty(usuario.getEmail())) {
			url.append("?email=").append(usuario.getEmail());
		}

		return url.toString();
	}

	private Usuario recuperarUsuario(JSONObject jsonObject) throws JSONException {

		if (jsonObject != null) {
			Usuario usuario = new Usuario();
			JSONObject jsonObjectUsuario = jsonObject.getJSONObject("usuario");
			if (jsonObjectUsuario != null) {
				usuario.setNome(jsonObjectUsuario.getString("nome"));
				usuario.setEmail(jsonObjectUsuario.getString("email"));

				try {
					usuario.setTelefone(jsonObjectUsuario.getString("telefone"));
				} catch(JSONException e) {
					usuario.setTelefone("");
				}

				usuario.setSenha(jsonObjectUsuario.getString("senha"));
				usuario.setId(jsonObjectUsuario.getString("id"));

				return usuario;
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(Usuario result) {
		try {
			observable.observe(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
