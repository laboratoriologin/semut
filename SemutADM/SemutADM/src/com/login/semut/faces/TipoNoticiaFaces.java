package com.login.audit.laurofreitas.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import com.login.audit.laurofreitas.model.Noticia;
import com.login.audit.laurofreitas.model.TipoNoticia;

@ViewScoped
@ManagedBean(name = "tipoNoticiaFaces")
public class TipoNoticiaFaces extends CrudFaces<TipoNoticia> {

	

	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("nome");
	}

	public String limpar() {
		super.limpar();
			return null;
	}

	public String limparPesquisa() {
		super.limparPesquisa();
			return null;
	}
}
