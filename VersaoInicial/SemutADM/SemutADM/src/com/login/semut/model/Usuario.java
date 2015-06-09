package com.login.semut.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

import com.login.semut.util.Utilitarios;

/**
 * 
 * @author argus.guedes
 * 
 */
@Entity
@Table(name = "usuarios")
public class Usuario extends TSActiveRecordAb<Usuario> {

	/**
	 * Propriedade identificadora do objeto Usu�rio.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Propriedade nome do objeto Usu�rio.
	 */
	private String nome;
	/**
	 * Propriedade login do objeto Usu�rio.
	 */
	private String login;
	/**
	 * Propriedade senha do objeto Usu�rio.
	 */
	private String senha;
	/**
	 * Propriedade email do objeto Usu�rio.
	 */
	private String email;
	/**
	 * Propriedade confirma senha do objeto Usu�rio.
	 */
	@Transient
	private String confirmaSenha;
	/**
	 * Propriedade ativo do objeto Usu�rio.
	 */
	@Column(name = "flag_ativo")
	private Boolean flagAtivo;
	/**
	 * Propriedade grupo de usu�rio do objeto Usu�rio.
	 */
	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private Grupo grupo;

	/**
	 * M�todo construtor da classe, sem a��o.
	 */
	public Usuario() {

	}

	/**
	 * Obt�m a propriedade identificadora do objeto Usu�rio.
	 * 
	 * @return id Inteiro longo. Identificador do objeto usu�rio.
	 */
	public final Long getId() {
		return TSUtil.tratarLong(id);
	}

	/**
	 * Seta a prorpiedade Id do objeto Usu�rio.
	 * 
	 * @param pId
	 *            Identificador do objeto Usu�rio.
	 */
	public final void setId(final Long pId) {
		this.id = TSUtil.tratarLong(pId);
	}

	/**
	 * Obt�m a propriedade nome do objeto Usu�rio.
	 * 
	 * @return String. Nome do objeto Usu�rio.
	 */
	public final String getNome() {
		return nome;
	}

	/**
	 * Seta a propriedade nome do objeto Usu�rio.
	 * 
	 * @param pNome
	 *            String. Nome do objeto Usu�rio.
	 */
	public final void setNome(final String pNome) {
		this.nome = pNome;
	}

	/**
	 * Obt�m a propriedade login do objeto Usu�rio.
	 * 
	 * @return String. Propriedade Login do objeto Usu�rio.
	 */
	public final String getLogin() {

		return login;
	}

	/**
	 * Seta a propriedade login do objeto Usu�rio.
	 * 
	 * @param pLogin
	 *            String. Login do objeto Usu�rio.
	 */
	public final void setLogin(final String pLogin) {
		this.login = pLogin;
	}

	/**
	 * Obt�m a senha do objeto Usu�rio.
	 * 
	 * @return String. Retorna a propriedade senha do objeto usu�rio.
	 */
	public final String getSenha() {

		return senha;

	}

	/**
	 * Seta a propriedade senha do objeto Usu�rio.
	 * 
	 * @param pSenha
	 *            String. A senha do objeto Usu�rio.
	 */
	public final void setSenha(final String pSenha) {
		this.senha = pSenha;

	}

	/**
	 * Obt�m a propriedade ativo do objeto Usu�rio.
	 * 
	 * @return Verdadeiro ou False. Se o objeto usu�rio est� ativo.
	 */
	public final Boolean getFlagAtivo() {
		return flagAtivo;
	}

	/**
	 * Seta a propriedade ativo do objeto Usu�rio.
	 * 
	 * @param pFlagAtivo
	 *            Verdadeiro ou False. Se o objeto usu�rio est� ativo.
	 */
	public final void setFlagAtivo(final Boolean pFlagAtivo) {
		this.flagAtivo = pFlagAtivo;
	}

	/**
	 * Obt�m a propriedade ativo do objeto Usu�rio.
	 * 
	 * @return String. Situa��o do objeto Usu�rio. Ativo ou n�o
	 */
	public final String getSituacao() {
		return Utilitarios.getSituacao(flagAtivo);
	}

	/**
	 * Obt�m a propriedade confirmar senha do objeto Usu�rio.
	 * 
	 * @return String. A confirma��o da senha do objeto Usu�rio.
	 */
	public final String getConfirmaSenha() {
		return confirmaSenha;
	}

	/**
	 * Seta a propriedade confirmar senha do objeto usu�rio.
	 * 
	 * @param pConfirmaSenha
	 *            String. A confirma��o da senha do objeto Usu�rio.
	 */
	public final void setConfirmaSenha(final String pConfirmaSenha) {
		this.confirmaSenha = pConfirmaSenha;
	}

	/**
	 * Obt�m a propriedade email do objeto Usu�rio.
	 * 
	 * @return String. Retorna o email do objeto Usu�rio.
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * Seta o email do objeto usu�rio.
	 * 
	 * @param pEmail
	 *            String. Envia o e-mail do objeto usu�rio.
	 */
	public final void setEmail(final String pEmail) {
		this.email = pEmail;
	}

	/**
	 * Obt�m o grupo de usu�rios do objeto Usu�rio.
	 * 
	 * @return Grupo. Retorna o grupo de usu�rios do objeto Usu�rio.
	 */
	public final Grupo getGrupo() {
		return grupo;
	}

	/**
	 * Seta o grupo de usu�rios do objeto usu�rio.
	 * 
	 * @param pGrupo
	 *            Grupo. Envia o grupo de usu�rios do objeto Usu�rio.
	 */
	public final void setGrupo(final Grupo pGrupo) {
		this.grupo = pGrupo;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
