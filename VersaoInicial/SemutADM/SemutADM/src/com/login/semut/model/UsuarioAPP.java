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
		UsuarioAPP other = (UsuarioAPP) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
