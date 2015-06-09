package com.login.android.semut.lauro.business;

import com.login.android.semut.lauro.DefaultActivity;
import com.login.android.semut.lauro.model.CategoriaOcorrencia;
import com.login.android.semut.lauro.util.JSONCategoriaOcorrenciaUtil;
import com.login.android.semut.lauro.util.JSONInsertTokenAndroidUtil;

public class TokenAndroidBS implements Observable {

	private DefaultActivity activity;

	public TokenAndroidBS(DefaultActivity activity) {
		this.activity = activity;
	}
	
	public void inserir(String token) {
		new JSONInsertTokenAndroidUtil(this).execute(token);
	}

	@Override
	public void observe(Object result) {

	}
}
