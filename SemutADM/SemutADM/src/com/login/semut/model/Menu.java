package com.login.audit.laurofreitas.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "menus")
public final class Menu extends TSActiveRecordAb<Menu> {

	@Id
	@GeneratedValue
	private Long id;

	private String descricao;

	private String url;

	@Column(name = "managed_bean_reset")
	private String managedBeanReset;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	private Integer ordem;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menuPai;

	@OneToMany(mappedBy = "menuPai")
	private List<Menu> menus;

	public Menu() {
	}

	public Menu(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getManagedBeanReset() {
		return managedBeanReset;
	}

	public void setManagedBeanReset(String managedBeanReset) {
		this.managedBeanReset = managedBeanReset;
	}

	public Menu getMenuPai() {
		return menuPai;
	}

	public void setMenuPai(Menu menuPai) {
		this.menuPai = menuPai;
	}

	public List<Menu> pesquisarCabecalhos(Long grupoID) {

		return findBySQL("select * from menus m where menu_id is null and flag_ativo = 1 and exists (select 1 from menus m2, permissoes p where m2.menu_id = m.id and m2.id = p.menu_id and p.grupo_id = ?) order by ordem, descricao", grupoID);

	}

	public List<Menu> pesquisarCabecalhos() {

		StringBuilder query = new StringBuilder();

		query.append(" from Menu m where menuPai is null and flagAtivo = true order by ordem, descricao");

		return super.find(query.toString(), "id");
	}

	public List<Menu> pesquisarExecutaveis() {

		StringBuilder query = new StringBuilder();

		query.append(" from Menu m where menuPai is not null and flagAtivo = true");

		return super.find(query.toString(), "id");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
