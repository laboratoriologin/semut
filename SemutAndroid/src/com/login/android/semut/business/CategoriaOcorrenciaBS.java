package com.login.android.semut.lauro.business;

import com.login.android.semut.lauro.DefaultActivity;
import com.login.android.semut.lauro.model.CategoriaOcorrencia;
import com.login.android.semut.lauro.util.JSONCategoriaOcorrenciaUtil;

public class CategoriaOcorrenciaBS implements Observable {

	private DefaultActivity activity;

	public CategoriaOcorrenciaBS(DefaultActivity activity) {
		this.activity = activity;
	}
	
	public void pesquisar(String categoriaOcorrencia) {
		new JSONCategoriaOcorrenciaUtil(this).execute(categoriaOcorrencia);
	}

	@Override
	public void observe(Object result) {

		activity.getBusinessResult(result);
	}
}
