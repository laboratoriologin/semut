package com.login.semut.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

/**
 * 
 * @author argus.guedes
 * 
 */
@Entity
@Table(name = "usuario_app")
public class UsuarioAPP extends TSActiveRecordAb<UsuarioAPP> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String senha;

	private String email;

	private String telefone;

	@Transient
	private String confirmaSenha;

	public UsuarioAPP() {

	}

	public UsuarioAPP(long l) {
		this.id = l;
	}

	public final Long getId() {
		return TSUtil.tratarLong(id);
	}

	public final void setId(final Long pId) {
		this.id = TSUtil.tratarLong(pId);
	}

	public final String getNome() {
		return nome;
	}

	public final void setNome(final String pNome) {
		this.nome = pNome;
	}

	public final String getSenha() {

		return senha;
	}

	public final void setSenha(final String pSenha) {
		this.senha = pSenha;

	}

	public final String getConfirmaSenha() {
		return confirmaSenha;
	}

	public final void setConfirmaSenha(final String pConfirmaSenha) {
		this.confirmaSenha = pConfirmaSenha;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(final String pEmail) {
		this.email = pEmail;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
