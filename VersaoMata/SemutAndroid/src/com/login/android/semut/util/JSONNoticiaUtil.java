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
import com.login.android.semut.model.Noticia;

public class JSONNoticiaUtil extends AsyncTask<String, Void, List<Noticia>> {

	private Observable observable;

	public JSONNoticiaUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected List<Noticia> doInBackground(String... params) {

		String tipoNoticia = params == null || params.length == 0 ? null : params[0];

		JSONObject jsonObject = new HttpUtil().getJSONFromURL(this.getURL(tipoNoticia));

		List<Noticia> retorno = new ArrayList<Noticia>();

		if (jsonObject != null) {

			try {

				retorno = this.recuperarNoticias(jsonObject, tipoNoticia);

			} catch (JSONException ex) {

			}

		}

		return retorno;
	}

	private List<Noticia> recuperarNoticiasTop(JSONObject jsonObject, String tipoNoticia) throws JSONException {
		JSONArray array = jsonObject.getJSONArray("noticias");

		List<Noticia> noticias = new ArrayList<Noticia>();

		JSONObject json = null;
		Noticia noticia = null;

		for (int i = 0; i < array.length(); i++) {
			json = array.getJSONObject(i);
			noticia = new Noticia();

			noticia.setTitulo(json.getString("titulo"));
			noticia.setDescricao((json.getString("descricao")));
			noticia.setImagem(json.getString("imagem"));
			noticia.setTipoNoticia(Integer.parseInt(tipoNoticia));
			noticia.setId(json.getInt("id"));

			noticias.add(noticia);
		}

		return noticias;
	}

	private String getURL(String tipoNoticia) {

		StringBuilder url = new StringBuilder(Constantes.URL_NOTICIA).append("?tipoNoticia=").append(tipoNoticia);

		return url.toString();

	}

	private List<Noticia> recuperarNoticias(JSONObject jsonObject, String tipoNoticia) throws JSONException {

		JSONArray array = jsonObject.getJSONArray("noticias");

		List<Noticia> noticias = new ArrayList<Noticia>();

		JSONObject json = null;

		Noticia noticia = null;

		for (int i = 0; i < array.length(); i++) {

			json = array.getJSONObject(i);

			noticia = new Noticia();

			noticia.setTitulo(json.getString("titulo"));

			noticia.setDescricao(json.getString("descricao"));
			
			noticia.setTipoNoticia(Integer.parseInt(tipoNoticia));
			
			noticia.setId(json.getInt("id"));

			try {
				noticia.setImagem(json.getString("imagem"));
			} catch (JSONException ex) {

			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm");
			sdf.setLenient(true);
			Date data;
			try {
				data = sdf.parse(json.getString("data"));

				noticia.setData_publicacao(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			noticias.add(noticia);
		}

		return noticias;
	}

	@Override
	protected void onPostExecute(List<Noticia> result) {
		try {
			observable.observe(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
