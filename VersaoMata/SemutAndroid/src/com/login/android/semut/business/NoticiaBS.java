package com.login.android.semut.business;

import java.util.List;

import com.login.android.semut.DefaultActivity;
import com.login.android.semut.model.Noticia;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.util.JSONNoticiaUtil;

public class NoticiaBS implements Observable {

	private DefaultActivity activity;

	public NoticiaBS(DefaultActivity activity) {
		this.activity = activity;
	}

	/**
	 * Método que pesquisa imoveis utilizando o parâmetro como filtro. O retorno
	 * será observado pela interface passada como parâmetro na construção dessa
	 * classe através do método getBusinessResult
	 */

	public void pesquisar(String categoria) {

		new JSONNoticiaUtil(this).execute(categoria);

	}

	@Override
	public void observe(Object result) {

		activity.getBusinessResult(result);
	}
}
