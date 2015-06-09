package com.login.audit.laurofreitas.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.topsys.util.TSUtil;

import com.login.audit.laurofreitas.model.Grupo;
import com.login.audit.laurofreitas.model.Menu;
import com.login.audit.laurofreitas.model.Permissao;
import com.login.audit.laurofreitas.util.SemutUtil;

@ViewScoped
@ManagedBean(name = "grupoFaces")
public class GrupoFaces extends CrudFaces<Grupo> {
	
	private List<SelectItem> comboMenus;
	private Permissao permissaoSelecionada;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.comboMenus = super.initCombo(new Menu().pesquisarExecutaveis(), "id", "descricao");
		setFieldOrdem("descricao");
	}

	public String addPermissao() {

		Permissao permissao = new Permissao();
        
		permissao.setGrupo(getCrudModel());
		permissao.setMenu(new Menu());
		permissao.setFlagInserir(Boolean.TRUE);
		permissao.setFlagAlterar(Boolean.TRUE);
		permissao.setFlagExcluir(Boolean.TRUE);
		
		if (TSUtil.isEmpty(getCrudModel().getPermissoes())) {
			getCrudModel().setPermissoes(new ArrayList<Permissao>());
		}

		if (!this.getCrudModel().getPermissoes().contains(permissao)) {

			this.getCrudModel().getPermissoes().add(permissao);			

		} else {

			SemutUtil.addErrorMessage("Essa permissão já foi adicionada");
		}

		return null;
	}
	
	public String delPermissao(){
		getCrudModel().getPermissoes().remove(this.permissaoSelecionada);
		return null;
	}

	public List<SelectItem> getComboMenus() {
		return comboMenus;
	}

	public void setComboMenus(List<SelectItem> comboMenus) {
		this.comboMenus = comboMenus;
	}

	public Permissao getPermissaoSelecionada() {
		return permissaoSelecionada;
	}

	public void setPermissaoSelecionada(Permissao permissaoSelecionada) {
		this.permissaoSelecionada = permissaoSelecionada;
	}

}
