package com.login.audit.laurofreitas.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.login.audit.laurofreitas.model.CategoriaOcorrencia;
import com.login.audit.laurofreitas.model.GrupoOcorrencia;

/**
 * @author anderson.carvalho
 * 
 */
@ViewScoped
@ManagedBean(name = "categoriaOcorrenciaFaces")
public class CategoriaOcorrenciaFaces extends CrudFaces<CategoriaOcorrencia> {
	
	private List<SelectItem> gruposOcorrencias;

	@PostConstruct
	protected void init() {

		this.clearFields();
		setFieldOrdem("nome");
		getCrudModel().setGrupoOcorrencia(new GrupoOcorrencia(1L));
		
		/*this.gruposOcorrencias = super.initCombo(new GrupoOcorrencia().findAll("nome"), "id", "nome");*/
	}
	
	
	@Override
	public String limpar() {

		super.limpar();
		
		getCrudModel().setGrupoOcorrencia(new GrupoOcorrencia(1L));
		
		
		return null;
	}
	
	@Override
	public String limparPesquisa() {

		super.limparPesquisa();
		
		getCrudPesquisaModel().setGrupoOcorrencia(new GrupoOcorrencia(1L));
		
		
		return null;
	}


	public List<SelectItem> getGruposOcorrencias() {
		return gruposOcorrencias;
	}


	public void setGruposOcorrencias(List<SelectItem> gruposOcorrencias) {
		this.gruposOcorrencias = gruposOcorrencias;
	}

}
