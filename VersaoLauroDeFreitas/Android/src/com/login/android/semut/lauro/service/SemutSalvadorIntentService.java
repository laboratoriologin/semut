package com.login.android.semut.lauro.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.login.android.semut.lauro.model.Ocorrencia;
import com.login.android.semut.lauro.sqlite.dao.DataManager;
import com.login.android.semut.lauro.util.Constantes;
import com.login.android.semut.lauro.util.JSONInsertOcorrenciaUtil;

public class SemutSalvadorIntentService extends IntentService {

	private Ocorrencia ocorrencia;
	private String fileName;
	private String fileType;
	private InputStream inputStream;
	private DataManager dataManager;

	public SemutSalvadorIntentService() {
		super(Constantes.SERVICE);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		this.ocorrencia = (Ocorrencia) intent.getSerializableExtra(Constantes.PARAM_OCORRENCIA);
		this.fileName = (String) intent.getSerializableExtra(Constantes.FILENAME);
		this.fileType = (String) intent.getSerializableExtra(Constantes.FILETYPE);

		Boolean condition = false;
		long endTime = System.currentTimeMillis();
		long count = 1;

		do {

			while (System.currentTimeMillis() < endTime) {
				synchronized (this) {
					try {
						wait(endTime - System.currentTimeMillis());
					} catch (Exception e) {
					}
				}
			}

			condition = gravar();

			if (count < 6)
				count++;

			endTime = (System.currentTimeMillis() + ((long) Math.pow(2, count) * 1000));

		} while (!condition);

		if (condition) {

			Intent broadcastIntent = new Intent();
			broadcastIntent.setAction("com.login.android.semut.lauro.intent.action.PROCESS_RESPONSE");
			broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
			broadcastIntent.putExtra(Constantes.PARAM_OCORRENCIA, this.ocorrencia);
			sendBroadcast(broadcastIntent);
			stopSelf();
		}
	}

	private Boolean gravar() {

		if (ocorrencia.getImagem() != null) {
			BitmapFactory.decodeByteArray(ocorrencia.getImagem(), 0, ocorrencia.getImagem().length);
			inputStream = new ByteArrayInputStream(ocorrencia.getImagem());
		}

		if ("-1".equals(this.ocorrencia.getNumeroProtocolo())) {

			Integer retorno;

			try {
				retorno = new JSONInsertOcorrenciaUtil().execute(ocorrencia, inputStream, fileType, fileName).get();
			} catch (InterruptedException e1) {
				return false;
			} catch (ExecutionException e1) {
				return false;
			}
			// Integer retorno = 0;

			if (retorno != 0) {
				this.ocorrencia.setNumeroProtocolo(retorno.toString());

				this.dataManager = new DataManager(this);

				try {
					this.dataManager.getOcorrenciaDAO().update(this.ocorrencia, "ID = ?", new String[] { ocorrencia.getId() });
				} catch (Exception e) {
					stopSelf();
				}
				return true;
			}
			return false;
		}
		return true;
	}
}
