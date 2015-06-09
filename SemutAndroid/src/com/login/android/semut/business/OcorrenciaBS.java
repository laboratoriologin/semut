package com.login.android.semut.lauro.business;

import java.io.InputStream;

import com.login.android.semut.lauro.DefaultActivity;
import com.login.android.semut.lauro.model.Ocorrencia;
import com.login.android.semut.lauro.model.Usuario;
import com.login.android.semut.lauro.util.JSONInsertOcorrenciaUtil;
import com.login.android.semut.lauro.util.JSONNoticiaUtil;
import com.login.android.semut.lauro.util.JSONOcorrenciaUtil;
import com.login.android.semut.lauro.util.JSONPesquisaUsuarioUtil;
import com.login.android.semut.lauro.util.JSONInsertUsuarioUtil;

public class OcorrenciaBS implements Observable {

	private DefaultActivity activity;

	public OcorrenciaBS(DefaultActivity activity) {
		this.activity = activity;
	}

	public void pesquisar(String status) {
		new JSONOcorrenciaUtil(this).execute(status);
	}

	@Override
	public void observe(Object result) {

		activity.getBusinessResult(result);
	}
}
