package com.login.android.semut.lauro.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.login.android.semut.lauro.DefaultActivity;
import com.login.android.semut.lauro.business.Observable;
import com.login.android.semut.lauro.model.Ocorrencia;

public class JSONInsertOcorrenciaUtil extends AsyncTask<Object, Void, Integer> {

	public JSONInsertOcorrenciaUtil() {
	}

	@Override
	protected Integer doInBackground(Object... params) {

		Ocorrencia ocorrencia = params == null || params.length == 0 ? null : (Ocorrencia) params[0];
		InputStream file = params == null || params.length == 1 ? null : (InputStream) params[1];
		String fileContentType = params == null || params.length == 2 ? null : (String) params[2];
		String fileName = params == null || params.length == 3 ? null : (String) params[3];

		JSONObject jsonObject;

		if (ocorrencia == null) {
			return 0;
		} else {
			jsonObject = new HttpUtil().getJSONFromURLPostOcorrencia(this.getURL(ocorrencia), file, fileContentType, fileName);
		}

		if (jsonObject != null) {
			try {
				return Integer.parseInt(jsonObject.getString("status"));
			} catch (JSONException ex) {
				return 0;
			}
		}
		return 0;
	}

	private String getURL(Ocorrencia ocorrencia) {

		StringBuilder url = new StringBuilder(Constantes.URL_INSERT_OCORRENCIA);

		if (ocorrencia.getLongitude() != null) {
			url.append("?longitude=").append(ocorrencia.getLongitude());
		}
		if (ocorrencia.getLatitude() != null) {
			url.append("&latitude=").append(ocorrencia.getLatitude());
		}
		if (ocorrencia.getDescricao() != null && !TextUtils.isEmpty(ocorrencia.getDescricao())) {
			try {
				url.append("&descricao=").append(URLEncoder.encode(ocorrencia.getDescricao(), "iso-8859-1"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (ocorrencia.getUsuario().getId() != null && !TextUtils.isEmpty(ocorrencia.getUsuario().getId())) {
			url.append("&id=").append(ocorrencia.getUsuario().getId());
		}
		if (ocorrencia.getCategoriaId() != null && !TextUtils.isEmpty(ocorrencia.getCategoriaId())) {
			url.append("&categoria=").append(ocorrencia.getCategoriaId());
		}

		
		return url.toString();
	}	
}
