package com.login.android.semut.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.android.semut.business.Observable;
import com.login.android.semut.inicial.DefaultActivity;
import com.login.android.semut.model.CategoriaOcorrencia;
import com.login.android.semut.model.Grupo;

public class JSONCategoriaOcorrenciaUtil extends AsyncTask<String, Void, List<CategoriaOcorrencia>> {

	private Observable observable;

	public JSONCategoriaOcorrenciaUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected List<CategoriaOcorrencia> doInBackground(String... params) {

		String grupoOcorrencia = params == null || params.length == 0 ? null : params[0];

		JSONObject jsonObject = new HttpUtil().getJSONFromURL(this.getURL(grupoOcorrencia));

		List<CategoriaOcorrencia> retorno = new ArrayList<CategoriaOcorrencia>();

		if (jsonObject != null) {

			try {

				retorno = this.recuperarTiposOcorrencias(jsonObject, grupoOcorrencia);

			} catch (JSONException ex) {

			}

		}

		return retorno;
	}

	private String getURL(String grupoOcorrencia) {

		StringBuilder url = new StringBuilder(Constantes.URL_TIPO_OCORRENCIA).append("?grupoOcorrencia=").append(grupoOcorrencia);

		return url.toString();

	}

	private List<CategoriaOcorrencia> recuperarTiposOcorrencias(JSONObject jsonObject, String grupoOcorrencia) throws JSONException {

		JSONArray array = jsonObject.getJSONArray("tipoOcorrencias");

		List<CategoriaOcorrencia> categoriasOcorrencias = new ArrayList<CategoriaOcorrencia>();

		JSONObject json = null;

		CategoriaOcorrencia categoriaOcorrencia = null;

		for (int i = 0; i < array.length(); i++) {

			json = array.getJSONObject(i);

			categoriaOcorrencia = new CategoriaOcorrencia();
			
			categoriaOcorrencia.setId(json.getString("id"));
			
			categoriaOcorrencia.setDescricao(json.getString("nome"));
			
			categoriaOcorrencia.setId_grupo(Long.parseLong(json.getString("grupoOcorrencia")));
			
			Grupo grupoOcorrenciaLocal = new Grupo();
			
			grupoOcorrenciaLocal.setId(categoriaOcorrencia.getId_grupo());

			categoriasOcorrencias.add(categoriaOcorrencia);
		}

		return categoriasOcorrencias;
	}

	@Override
	protected void onPostExecute(List<CategoriaOcorrencia> result) {
		try {
			observable.observe(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
