package com.login.semut.faces;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Cookie;

import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

import com.login.semut.model.Menu;
import com.login.semut.model.Permissao;
import com.login.semut.model.Usuario;
import com.login.semut.util.Constantes;
import com.login.semut.util.SemutUtil;
import com.login.semut.util.UsuarioUtil;

@SessionScoped
@ManagedBean(name = "autenticacaoFaces")
public class AutenticacaoFaces extends TSMainFaces {

	private String nomeTela;
	private String tela;
	private Usuario usuario;
	private List<Menu> menus;
	private List<Permissao> permissoes;
	private Permissao permissaoSelecionada;
	private Integer tabAtiva;

	public AutenticacaoFaces() {

		setTabAtiva(new Integer(0));

		setNomeTela("Área de Trabalho");

		clearFields();
	}

	protected void clearFields() {

		this.usuario = new Usuario();

		this.menus = Collections.emptyList();

		this.permissaoSelecionada = new Permissao();

	}

	public String redirecionar() {

		if (!TSUtil.isEmpty(this.permissaoSelecionada.getMenu().getManagedBeanReset())) {
			TSFacesUtil.removeManagedBeanInSession(this.permissaoSelecionada.getMenu().getManagedBeanReset());
		}

		setTela(this.permissaoSelecionada.getMenu().getUrl());
		setNomeTela("Área de Trabalho > " + permissaoSelecionada.getMenu().getMenuPai().getDescricao() + " > " + permissaoSelecionada.getMenu().getDescricao());
		setTabAtiva(Integer.valueOf(this.menus.indexOf(this.permissaoSelecionada.getMenu().getMenuPai())));

		return SUCESSO;
	}

	private void carregarMenu() {

		menus = new Menu().pesquisarCabecalhos(UsuarioUtil.obterUsuarioConectado().getGrupo().getId());

		Permissao permissao = new Permissao();
		permissao.setGrupo(UsuarioUtil.obterUsuarioConectado().getGrupo());
		permissoes = permissao.pesquisarPermissoes();
		permissao.setMenu(new Menu());
		permissao.getMenu().setId(Constantes.MENU_OCORRENCIA);

		this.permissaoSelecionada = permissoes.get(permissoes.indexOf(permissao));

		this.redirecionar();

	}

	public String login() {

		usuario = UsuarioUtil.usuarioAutenticado(usuario);

		if (TSUtil.isEmpty(usuario)) {
			clearFields();
			SemutUtil.addWarnMessage("Login/Senha sem credencial para acesso.");
			return null;
		}

		TSFacesUtil.addObjectInSession(Constantes.USUARIO_CONECTADO, usuario);

		try {
			Cookie myCookie = new Cookie("usuario", usuario.getNome());
			TSFacesUtil.getResponse().addCookie(myCookie);
			TSFacesUtil.getResponse().sendRedirect("index.html");
			this.carregarMenu();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String logout() {
		try {
			TSFacesUtil.getResponse().sendRedirect(TSFacesUtil.getRequest().getContextPath() + "/site/sair");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String dashboard() {
		try {
			TSFacesUtil.getResponse().sendRedirect(TSFacesUtil.getRequest().getContextPath() + "/site/index.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getNomeTela() {
		return nomeTela;
	}

	public void setNomeTela(String nomeTela) {
		this.nomeTela = nomeTela;
	}

	public String getTela() {
		return tela;
	}

	public void setTela(String tela) {
		this.tela = tela;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Integer getTabAtiva() {
		return tabAtiva;
	}

	public void setTabAtiva(Integer tabAtiva) {
		this.tabAtiva = tabAtiva;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public Permissao getPermissaoSelecionada() {
		return permissaoSelecionada;
	}

	public void setPermissaoSelecionada(Permissao permissaoSelecionada) {
		this.permissaoSelecionada = permissaoSelecionada;
	}

}
