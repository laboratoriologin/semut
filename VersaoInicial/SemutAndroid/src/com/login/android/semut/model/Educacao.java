package com.login.android.semut.model;

import java.io.Serializable;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;

@SuppressWarnings("serial")
@Table(name = "EDUCACAO")
public class Educacao implements Serializable {
	
	@PrimaryKey
	@Column(name = "TITULO")
	private String titulo;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	
	
	@Column(name = "IMAGEM")
	private String imagem;
	
	@Column(name = "DATAPUBLICACAO")
	private String data_publicacao;
	
	

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
	public String getData_publicacao() {
		return data_publicacao;
	}

	/**
	 * @param data_publicacao the data_publicacao to set
	 */
	public void setData_publicacao(String data_publicacao) {
		this.data_publicacao = data_publicacao;
	}

}
