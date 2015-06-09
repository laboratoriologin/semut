package com.login.android.semut.lauro.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

public class HttpUtil {

	private int TIMEOUT = 60000;

	public String get(String url) {

		try {

			HttpParams httpParameters = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);

			HttpClient httpclient = new DefaultHttpClient(httpParameters);

			HttpGet get = new HttpGet(url);

			HttpResponse response = httpclient.execute(get);

			if (response.getStatusLine().getStatusCode() != 200) {
				return null;
			} else {
				StringBuilder builder = new StringBuilder();
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;

				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				return builder.toString();
			}

		} catch (Exception e) {
			return null;
		}

	}

	public JSONObject getJSONFromURLPost(String url) {
		try {

			HttpParams httpParameters = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);

			HttpClient httpclient = new DefaultHttpClient(httpParameters);

			url = url + "&key_servlet=" + Utilitarios.getKeyMd5();

			HttpPost httppost = new HttpPost(url);

			HttpResponse response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() != 200) {
				return null;
			} else {
				return readJson(response);
			}

		} catch (Exception e) {
			return null;
		}

	}

	public JSONObject getJSONFromURLPostOcorrencia(String url, InputStream fileImage, String fileContentType, String fileName) {
		try {

			HttpParams httpParameters = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);

			HttpClient httpclient = new DefaultHttpClient(httpParameters);

			MultipartEntity entity = new MultipartEntity();

			if (fileImage != null) {

				entity.addPart("file", new InputStreamBody(fileImage, fileContentType, fileName));
			}

			url = url + "&key_servlet=" + Utilitarios.getKeyMd5();

			HttpPost httppost = new HttpPost(url);

			httppost.setEntity(entity);

			HttpResponse response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() != 200) {
				return null;
			} else {
				return readJson(response);
			}

		} catch (Exception e) {
			return null;
		}

	}

	public JSONObject getJSONFromURL(String url) {
		try {
			HttpParams httpParameters = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);

			HttpClient httpclient = new DefaultHttpClient(httpParameters);

			url = url + "&key_servlet=" + Utilitarios.getKeyMd5();

			HttpGet requisicao = new HttpGet(url);

			HttpResponse response;

			response = httpclient.execute(requisicao);

			if (response.getStatusLine().getStatusCode() != 200) {
				return null;
			} else {
				return readJson(response);
			}

		} catch (Exception e) {
			return null;
		}

	}

	private JSONObject readJson(HttpResponse response) {

		JSONObject jsonObject = null;

		try {

			StringBuilder builder = new StringBuilder();
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;

			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			jsonObject = new JSONObject(builder.toString());

		} catch (Exception ex) {
			return null;
		}
		return jsonObject;
	}

}
