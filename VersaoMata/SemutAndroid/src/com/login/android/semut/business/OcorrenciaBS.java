package com.login.android.semut.business;

import java.io.InputStream;

import com.login.android.semut.DefaultActivity;
import com.login.android.semut.model.Ocorrencia;
import com.login.android.semut.model.Usuario;
import com.login.android.semut.util.JSONInsertOcorrenciaUtil;
import com.login.android.semut.util.JSONNoticiaUtil;
import com.login.android.semut.util.JSONOcorrenciaUtil;
import com.login.android.semut.util.JSONPesquisaUsuarioUtil;
import com.login.android.semut.util.JSONInsertUsuarioUtil;

public class OcorrenciaBS implements Observable {

	private DefaultActivity activity;

	public OcorrenciaBS(DefaultActivity activity) {
		this.activity = activity;
	}
	
	public void inserir(Ocorrencia ocorrencia, InputStream fileImage, String fileContentType, String fileName) {
		new JSONInsertOcorrenciaUtil(this).execute(ocorrencia, fileImage, fileContentType, fileName);
	}
	
	public void pesquisar(String status) {
		new JSONOcorrenciaUtil(this).execute(status);
	}

	@Override
	public void observe(Object result) {

		activity.getBusinessResult(result);
	}
}
