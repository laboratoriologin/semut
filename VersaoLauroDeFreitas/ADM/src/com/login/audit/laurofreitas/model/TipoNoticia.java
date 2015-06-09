/**
 * 
 */
package com.login.audit.laurofreitas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

/**
 * @author Ricardo
 * 
 */
@Entity
@Table(name = "tipo_noticias")
public class TipoNoticia extends TSActiveRecordAb<TipoNoticia> {

	public TipoNoticia(Long id) {
		super();
		this.id = id;
	}

	public TipoNoticia() {

	}
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Propriedade identificadora do objeto TipoNoticia.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Propriedade nome do objeto TipoNoticia.
	 */
	private String nome;

	/**
	 * Obt�m a propriedade identificadora do objeto TipoNoticia.
	 * 
	 * @return id Inteiro longo. Identificador do objeto TipoNoticia.
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * Seta a prorpiedade Id do objeto TipoNoticia.
	 * 
	 * @param pId
	 *            Identificador do objeto TipoNoticia.
	 */

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoNoticia other = (TipoNoticia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
