package com.login.android.semut.business;

import com.login.android.semut.DefaultActivity;
import com.login.android.semut.model.CategoriaOcorrencia;
import com.login.android.semut.util.JSONCategoriaOcorrenciaUtil;

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
