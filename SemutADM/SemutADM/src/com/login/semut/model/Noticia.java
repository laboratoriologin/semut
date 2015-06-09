/**
 * 
 */
package com.login.audit.laurofreitas.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.login.audit.laurofreitas.util.SemutUtil;

/**
 * @author Ricardo
 * 
 */
@Entity
@Table(name = "noticias")
public class Noticia extends TSActiveRecordAb<Noticia> {

	public Noticia() {

	}

	/**
	 * Propriedade identificadora do objeto Noticia.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Obt�m a propriedade identificadora do objeto Noticia.
	 * 
	 * @return id Inteiro longo. Identificador do objeto Noticia.
	 */
	public final Long getId() {
		return TSUtil.tratarLong(id);
	}

	/**
	 * Seta a prorpiedade Id do objeto Noticia.
	 * 
	 * @param pId
	 *            Identificador do objeto Noticia.
	 */
	public final void setId(final Long pId) {
		this.id = TSUtil.tratarLong(pId);
	}

	/**
	 * Propriedade String do objeto Noticia.
	 */
	private String titulo;

	/**
	 * Propriedade String do objeto Noticia.
	 */
	private String descricao;

	/**
	 * Propriedade String do objeto Noticia.
	 */
	private String imagem;

	/**
	 * Propriedade Date do objeto Noticia.
	 */
	private Timestamp data;

	/**
	 * Propriedade TipoNoticia do objeto Noticia.
	 */
	@ManyToOne
	@JoinColumn(name = "id_tipo_noticia")
	private TipoNoticia tipoNoticia;
	
	@Override
	public final List<Noticia> findByModel(final String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from Noticia n where lower(n.descricao) like coalesce(?, n.descricao) ");
		
		
		List<Object> params = new ArrayList<Object>();
		params.add(SemutUtil.tratarString(descricao));
		
		if(tipoNoticia != null && !TSUtil.isEmpty(TSUtil.tratarLong(tipoNoticia.getId()))){
			query.append(" and n.tipoNoticia.id = coalesce(?,n.tipoNoticia.id)");
			params.add(tipoNoticia.getId());
		}
	
		return super.find(query.toString(), "id", params.toArray());		
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}



	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the imagem
	 */
	public String getImagem() {
		return imagem;
	}

	/**
	 * @param imagem
	 *            the imagem to set
	 */
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	

	/**
	 * @return the data
	 */
	public Timestamp getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Timestamp data) {
		this.data = data;
	}

	/**
	 * @return the tipoNoticia
	 */
	public final TipoNoticia getTipoNoticia() {
		return tipoNoticia;
	}

	/**
	 * @param tipoNoticia
	 *            the tipoNoticia to set
	 */
	public final void setTipoNoticia(final TipoNoticia tipoNoticia) {
		this.tipoNoticia = tipoNoticia;
	}
	
	public String getDataExtenso() {
		
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(data);
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
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imagem == null) ? 0 : imagem.hashCode());
		result = prime * result
				+ ((tipoNoticia == null) ? 0 : tipoNoticia.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		Noticia other = (Noticia) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imagem == null) {
			if (other.imagem != null)
				return false;
		} else if (!imagem.equals(other.imagem))
			return false;
		if (tipoNoticia == null) {
			if (other.tipoNoticia != null)
				return false;
		} else if (!tipoNoticia.equals(other.tipoNoticia))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	
}
