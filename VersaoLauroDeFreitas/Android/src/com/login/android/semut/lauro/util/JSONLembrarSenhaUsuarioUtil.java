package com.login.android.semut.lauro.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.login.android.semut.lauro.DefaultActivity;
import com.login.android.semut.lauro.business.Observable;
import com.login.android.semut.lauro.model.Usuario;

public class JSONLembrarSenhaUsuarioUtil extends AsyncTask<Object, Void, Boolean> {

	private Observable observable;

	public JSONLembrarSenhaUsuarioUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		Usuario usuario = params == null || params.length == 0 ? null : (Usuario) params[0];

		JSONObject jsonObject = new JSONObject();

		jsonObject = new HttpUtil().getJSONFromURL(this.getURL(usuario));

		if (jsonObject != null) {
			try {
				return this.recuperarUsuario(jsonObject);
			} catch (JSONException ex) {
				return null;
			}
		}

		return null;
	}

	private String getURL(Usuario usuario) {

		StringBuilder url = new StringBuilder(Constantes.URL_LEMBRAR_SENHA);

		if (usuario.getEmail() != null && !TextUtils.isEmpty(usuario.getEmail())) {
			url.append("?email=").append(usuario.getEmail());
		}

		return url.toString();
	}

	private Boolean recuperarUsuario(JSONObject jsonObject) throws JSONException {

		if (jsonObject != null) {
			String status = jsonObject.getString("status");
			if ("1".equals(status)) {
				return true;
			} else
				return false;
		}
		
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		try {
			observable.observe(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
