package com.login.semut.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.login.semut.model.TipoSituacao;

/**
 * @author anderson.carvalho
 * 
 */
@ViewScoped
@ManagedBean(name = "tipoSituacaoFaces")
public class TipoSituacaoFaces extends CrudFaces<TipoSituacao> {

	@PostConstruct
	protected void init() {

		this.clearFields();
		setFieldOrdem("descricao");
	}

	@Override
	public String limpar() {

		super.limpar();

		return null;
	}

	@Override
	public String limparPesquisa() {

		super.limparPesquisa();

		return null;
	}
}
