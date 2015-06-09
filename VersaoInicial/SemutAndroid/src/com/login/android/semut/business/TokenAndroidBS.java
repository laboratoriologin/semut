package com.login.android.semut.business;

import com.login.android.semut.inicial.DefaultActivity;
import com.login.android.semut.model.CategoriaOcorrencia;
import com.login.android.semut.util.JSONCategoriaOcorrenciaUtil;
import com.login.android.semut.util.JSONInsertTokenAndroidUtil;

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
