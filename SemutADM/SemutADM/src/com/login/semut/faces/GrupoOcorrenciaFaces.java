package com.login.audit.laurofreitas.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.login.audit.laurofreitas.model.GrupoOcorrencia;
import com.login.audit.laurofreitas.model.Ocorrencia;

/**
 * @author anderson.carvalho
 * 
 */
@ViewScoped
@ManagedBean(name = "grupoOcorrenciaFaces")
public class GrupoOcorrenciaFaces extends CrudFaces<GrupoOcorrencia> {

	@PostConstruct
	protected void init() {

		this.clearFields();
		setFieldOrdem("nome");
	}

}
