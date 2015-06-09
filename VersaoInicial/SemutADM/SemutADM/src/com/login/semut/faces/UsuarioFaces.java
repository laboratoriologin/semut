package com.login.semut.faces;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.login.semut.model.Grupo;
import com.login.semut.model.Usuario;
import com.login.semut.util.UsuarioUtil;

@ViewScoped
@ManagedBean(name = "usuarioFaces")
public class UsuarioFaces extends CrudFaces<Usuario> {	
	
	private List<SelectItem> grupos;

	@PostConstruct
	protected void init() {
		this.clearFields();		
		this.grupos = super.initCombo(new Grupo().findAll("descricao"), "id", "descricao");
		setFieldOrdem("nome");
	}

	public String limpar() {
		super.limpar();		
		getCrudModel().setGrupo(new Grupo());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		return null;
	}

	public String limparPesquisa() {
		super.limparPesquisa();	
		getCrudPesquisaModel().setGrupo(new Grupo());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		return null;
	}
	
	protected void prePersist() {
		getCrudModel().setSenha(UsuarioUtil.getSenhaCriptografada(getCrudModel().getSenha()));
	}

	public List<SelectItem> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<SelectItem> grupos) {
		this.grupos = grupos;
	}	

}
