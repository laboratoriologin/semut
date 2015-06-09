package com.login.android.semut.model;

import java.io.Serializable;
import java.util.Date;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

@SuppressWarnings("serial")
@Table(name = "NOTICIA")
public class Noticia implements Serializable {
	
	@PrimaryKey
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "TITULO")
	private String titulo;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	@Column(name = "IMAGEM")
	private String imagem;
	
	@Column(name = "DATAPUBLICACAO")
	private Date data_publicacao;
	
	@Column(name = "TIPONOTICIA")
	private Integer tipoNoticia;
	
	@Transient
	private Boolean tops;



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	/**
	 * @return the data_publicacao
	 */
	public Date getData_publicacao() {
		return data_publicacao;
	}

	/**
	 * @param data_publicacao the data_publicacao to set
	 */
	public void setData_publicacao(Date data_publicacao) {
		this.data_publicacao = data_publicacao;
	}

	@Override
	public String toString() {
		return titulo;
	}

	public Boolean getTops() {
		return tops;
	}

	public void setTops(Boolean tops) {
		this.tops = tops;
	}

	public Integer getTipoNoticia() {
		return tipoNoticia;
	}

	public void setTipoNoticia(Integer tipoNoticia) {
		this.tipoNoticia = tipoNoticia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
