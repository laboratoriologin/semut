package com.login.android.semut.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.android.semut.DefaultActivity;
import com.login.android.semut.business.Observable;
import com.login.android.semut.model.CategoriaOcorrencia;
import com.login.android.semut.model.Noticia;
import com.login.android.semut.model.Ocorrencia;

public class JSONOcorrenciaUtil extends AsyncTask<String, Void, List<Ocorrencia>> {

	private Observable observable;

	public JSONOcorrenciaUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected List<Ocorrencia> doInBackground(String... params) {

		String status = params == null || params.length == 0 ? null : params[0];

		JSONObject jsonObject = new HttpUtil().getJSONFromURL(this.getURL(status));

		List<Ocorrencia> retorno = new ArrayList<Ocorrencia>();

		if (jsonObject != null) {

			try {

				retorno = this.recuperarNoticias(jsonObject, status);

			} catch (JSONException ex) {

			}

		}

		return retorno;
	}

	private String getURL(String status) {

		StringBuilder url = new StringBuilder(Constantes.URL_OCORRENCIA).append("?status=").append(status);

		return url.toString();

	}

	private List<Ocorrencia> recuperarNoticias(JSONObject jsonObject, String status) throws JSONException {

		JSONArray array = jsonObject.getJSONArray("Ocorrencias");

		List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();

		JSONObject json = null;

		Ocorrencia ocorrencia = null;

		for (int i = 0; i < array.length(); i++) {

			json = array.getJSONObject(i);

			ocorrencia = new Ocorrencia();
			
			ocorrencia.setCategoria(new CategoriaOcorrencia());

			ocorrencia.setDescricao(json.getString("descricao"));
			
			ocorrencia.setCategoriaId(json.getString("categoriaOcorrencia"));
			
			ocorrencia.getCategoria().setDescricao(json.getString("nomeTipoOcorrencia"));
			
			ocorrencia.setGrupoId(0L);
			
			ocorrencia.setLatitude(json.getDouble("latitude"));
			
			ocorrencia.setLongitude(json.getDouble("longitude"));
			
			ocorrencia.setEmailUsuario(json.getString("email"));
		
			try {
				ocorrencia.setCaminhoImagem(json.getString("imagem"));
			} catch (JSONException ex) {
				ocorrencia.setCaminhoImagem("");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm");
			sdf.setLenient(true);
			Date data;
			try {
				data = sdf.parse(json.getString("dataAlerta"));

				ocorrencia.setData(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ocorrencias.add(ocorrencia);
		}

		return ocorrencias;
	}

	@Override
	protected void onPostExecute(List<Ocorrencia> result) {
		try {
			observable.observe(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
