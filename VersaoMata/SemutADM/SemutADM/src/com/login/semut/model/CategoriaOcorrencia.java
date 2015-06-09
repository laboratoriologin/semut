package com.login.semut.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

import com.login.semut.util.SemutUtil;

/**
 * 
 * @author anderson.carvalho
 * 
 */
@Entity
@Table(name = "tipo_ocorrencias")
public class CategoriaOcorrencia extends TSActiveRecordAb<CategoriaOcorrencia> {

	public CategoriaOcorrencia(Long id) {
		super();
		this.id = id;
	}

	public CategoriaOcorrencia() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Propriedade identificadora do objeto Categoria.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;

	/**
	 * Propriedade nome da categoria.
	 */
	@ManyToOne
	@JoinColumn(name = "id_grupo_ocorrencia")
	private GrupoOcorrencia grupoOcorrencia;
	
	
	@Override
	public final List<CategoriaOcorrencia> findByModel(final String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from CategoriaOcorrencia s where lower(s.nome) like coalesce(?, s.nome) ");
		
		
		List<Object> params = new ArrayList<Object>();
		params.add(SemutUtil.tratarString(nome));
		
		if(grupoOcorrencia != null && !TSUtil.isEmpty(TSUtil.tratarLong(grupoOcorrencia.getId()))){
			query.append(" and s.grupoOcorrencia.id = coalesce(?,s.grupoOcorrencia.id)");
			params.add(grupoOcorrencia.getId());
		}		

		return super.find(query.toString(),"id", params.toArray());		
	}
	
	public final List<CategoriaOcorrencia> findByIdGrupo(Long id) {

		StringBuilder query = new StringBuilder();

		query.append(" from CategoriaOcorrencia s where s.grupoOcorrencia.id=? ");
			
		List<Object> params = new ArrayList<Object>();
		params.add(id);	

		return super.find(query.toString(),"id", params.toArray());		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public String getNomeCompleto(){
		return nome /*+ " - " +this.getGrupoOcorrencia().getNome()*/;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GrupoOcorrencia getGrupoOcorrencia() {
		return grupoOcorrencia;
	}

	public void setGrupoOcorrencia(GrupoOcorrencia grupoOcorrencia) {
		this.grupoOcorrencia = grupoOcorrencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grupoOcorrencia == null) ? 0 : grupoOcorrencia.hashCode());
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
		CategoriaOcorrencia other = (CategoriaOcorrencia) obj;
		if (grupoOcorrencia == null) {
			if (other.grupoOcorrencia != null)
				return false;
		} else if (!grupoOcorrencia.equals(other.grupoOcorrencia))
			return false;
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
