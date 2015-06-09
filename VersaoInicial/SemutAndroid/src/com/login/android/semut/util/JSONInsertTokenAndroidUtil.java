package com.login.android.semut.util;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.login.android.semut.business.Observable;
import com.login.android.semut.inicial.DefaultActivity;
import com.login.android.semut.model.Ocorrencia;

public class JSONInsertTokenAndroidUtil extends AsyncTask<Object, Void, String> {

	private Observable observable;

	public JSONInsertTokenAndroidUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected String doInBackground(Object... params) {

		String token = params == null || params.length == 0 ? null : (String) params[0];

		JSONObject jsonObject;

		if (token == null) {
			return null;
		} else {
			jsonObject = new HttpUtil().getJSONFromURL(this.getURL(token));
		}

		if (jsonObject != null) {
			try {
				return jsonObject.getString("status");
			} catch (JSONException ex) {
				return null;
			}
		}
		return null;
	}

	private String getURL(String token) {

		StringBuilder url = new StringBuilder(Constantes.URL_INSERT_TOKEN_ANDROID);

		if (token != null) {
			url.append("?token=").append(token);
			url.append("&modelo=android");
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
