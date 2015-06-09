package com.login.android.semut.lauro.business;

import com.login.android.semut.lauro.DefaultActivity;
import com.login.android.semut.lauro.model.Usuario;
import com.login.android.semut.lauro.util.JSONLembrarSenhaUsuarioUtil;
import com.login.android.semut.lauro.util.JSONPesquisaUsuarioUtil;
import com.login.android.semut.lauro.util.JSONInsertUsuarioUtil;

public class UsuarioBS implements Observable {

	private DefaultActivity activity;

	public UsuarioBS(DefaultActivity activity) {
		this.activity = activity;
	}

	/**
	 * Método que pesquisa imoveis utilizando o parâmetro como filtro. O retorno
	 * será observado pela interface passada como parâmetro na construção dessa
	 * classe através do método getBusinessResult
	 */
	
	public void inserir(Usuario usuario) {
		new JSONInsertUsuarioUtil(this).execute(usuario);
	}
	
	public void pesquisarPorEmail(Usuario usuario) {
		new JSONPesquisaUsuarioUtil(this).execute(usuario, true);
	}
	
	public void lembrarSenha(Usuario usuario){
		new JSONLembrarSenhaUsuarioUtil(this).execute(usuario);
	}

	@Override
	public void observe(Object result) {

		activity.getBusinessResult(result);
	}
}
