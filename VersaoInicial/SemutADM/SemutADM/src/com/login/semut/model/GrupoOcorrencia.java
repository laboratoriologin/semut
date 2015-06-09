package com.login.semut.model;

import java.sql.Timestamp;
import java.util.Date;

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
 * @author anderson.carvalho
 * 
 */
@Entity
@Table(name = "grupo_ocorrencias")
public class GrupoOcorrencia extends TSActiveRecordAb<GrupoOcorrencia> {

	public GrupoOcorrencia() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Transient
	private Long quantidadeOcorrencias;

	@Transient
	private String dataOcorrencias;

	@Transient
	private String mesAnoOcorrencias;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public final void setId(final Long pId) {
		this.id = TSUtil.tratarLong(pId);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getQuantidadeOcorrencias() {
		return quantidadeOcorrencias;
	}

	public void setQuantidadeOcorrencias(Long quantidadeOcorrencias) {
		this.quantidadeOcorrencias = quantidadeOcorrencias;
	}

	public String getDataOcorrencias() {
		return dataOcorrencias;
	}

	public void setDataOcorrencias(String dataOcorrencias) {
		this.dataOcorrencias = dataOcorrencias;
	}

	public String getMesAnoOcorrencias() {
		return mesAnoOcorrencias;
	}

	public void setMesAnoOcorrencias(String mesAnoOcorrencias) {
		this.mesAnoOcorrencias = mesAnoOcorrencias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		GrupoOcorrencia other = (GrupoOcorrencia) obj;
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
