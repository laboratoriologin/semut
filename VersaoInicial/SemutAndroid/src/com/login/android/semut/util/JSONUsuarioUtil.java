package com.login.android.semut.util;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.login.android.semut.business.Observable;
import com.login.android.semut.inicial.DefaultActivity;
import com.login.android.semut.model.Usuario;

public class JSONUsuarioUtil extends AsyncTask<Object, Void, String> {

	private Observable observable;

	public JSONUsuarioUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected String doInBackground(Object... params) {

		Usuario usuario = params == null || params.length == 0 ? null : (Usuario) params[0];

		JSONObject jsonObject;

		if (usuario == null) {
			return "erro";
		} else {
			jsonObject = new HttpUtil().getJSONFromURLPost(this.getURL(usuario));
		}

		if (jsonObject != null) {
			try {
				return jsonObject.getString("status");
			} catch (JSONException ex) {
				return "erro";
			}
		}
		return "erro";
	}

	private String getURL(Usuario usuario) {

		StringBuilder url = new StringBuilder(Constantes.URL_INSERT_USUARIO);

		if (usuario.getNome() != null && !TextUtils.isEmpty(usuario.getNome())) {
			url.append("?nome=").append(usuario.getNome());
		}

		if (usuario.getEmail() != null && !TextUtils.isEmpty(usuario.getEmail())) {
			url.append("&email=").append(usuario.getEmail());
		}

		if (usuario.getSenha() != null && !TextUtils.isEmpty(usuario.getSenha())) {
			url.append("&senha=").append(usuario.getSenha());
		}

		if (usuario.getTelefone() != null && !TextUtils.isEmpty(usuario.getTelefone())) {
			url.append("&telefone=").append(usuario.getTelefone());
		}

		if (usuario.isLogado()) {
			url.append("&logado=").append(usuario.isLogado());
		}

		return url.toString();
	}

	@Override
	protected void onPostExecute(String result) {
		try {
			observable.observe(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
