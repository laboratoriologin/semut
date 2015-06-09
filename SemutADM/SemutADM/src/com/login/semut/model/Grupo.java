package com.login.audit.laurofreitas.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

/**
 * 
 * @author argus.guedes
 * 
 */
@Entity
@Table(name = "grupos")
public class Grupo extends TSActiveRecordAb<Grupo> {
	/**
	 * Propriedade identificadora do objeto Grupo(Grupo de usuários).
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Propriedade descrição do objeto Grupo(Grupo de usuários).
	 */
	private String descricao;
	/**
	 * Propriedade lista de permissões do objeto Grupo(Grupo de usuários).
	 */
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
	private List<Permissao> permissoes;

	/**
	 * Obtém a propriedade identificadora do objeto Grupo(Grupo de usuários).
	 * 
	 * @return id Inteiro longo. Identificador do objeto Grupo(Grupo de
	 *         usuários).
	 */
	public final Long getId() {
		return TSUtil.tratarLong(id);
	}

	/**
	 * Seta a prorpiedade Id do objeto Grupo(Grupo de usuários).
	 * 
	 * @param pId
	 *            Identificador do objeto Grupo(Grupo de usuários).
	 */
	public final void setId(final Long pId) {
		this.id = pId;
	}

	/**
	 * Obtém a propriedade descrição do objeto Grupo(Grupo de usuários).
	 * 
	 * @return String. Retorna a descrição do Grupo(Grupo de usuários).
	 */
	public final String getDescricao() {
		return descricao;
	}

	/**
	 * Seta a propriedade descrição do objeto Grupo(Grupo de usuários).
	 * 
	 * @param pDescricao
	 *            String. Nome do objeto Grupo(Grupo de usuários).
	 */
	public final void setDescricao(final String pDescricao) {
		this.descricao = pDescricao;
	}

	/**
	 * Obtém a propriedade lista de permissões do objeto Grupo(Grupo de
	 * usuários).
	 * 
	 * @return Lista de permissões.
	 */
	public final List<Permissao> getPermissoes() {
		return permissoes;
	}

	/**
	 * Seta a propriedade lista de permissões do objeto Grupo(Grupo de
	 * usuários).
	 * 
	 * @param pPermissoes
	 *            Lista de permissões.
	 */
	public final void setPermissoes(final List<Permissao> pPermissoes) {
		this.permissoes = pPermissoes;
	}

}
