package com.login.audit.laurofreitas.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.login.audit.laurofreitas.model.CategoriaOcorrencia;
import com.login.audit.laurofreitas.model.Ocorrencia;
import com.login.audit.laurofreitas.model.UsuarioAPP;

/**
 * @author anderson.carvalho
 * 
 */
@ViewScoped
@ManagedBean(name = "usuarioAPPFaces")
public class UsuarioAPPFaces extends CrudFaces<UsuarioAPP> {

	@PostConstruct
	protected void init() {

		this.clearFields();
		setFieldOrdem("nome");
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
