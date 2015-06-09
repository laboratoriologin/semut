package com.login.android.semut.model;

import java.io.Serializable;
import java.util.Date;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

@SuppressWarnings("serial")
@Table(name = "OCORRENCIA")
public class Ocorrencia implements Serializable {

	@PrimaryKey
	@Column(name = "DATA")
	private Date data;
	
	@Column(name = "ID")
	private String id;

	@Column(name = "ID_CATEGORIA")
	private String categoriaId;

	@Column(name = "ID_GRUPO")
	private Long grupoId;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "LATITUDE")
	private Double latitude;

	@Column(name = "LONGITUDE")
	private Double longitude;

	@Column(name = "CAMINHO_IMAGEM")
	private String caminhoImagem;

	@Column(name = "EMAIL_USUARIO")
	private String emailUsuario;
	
	@Column(name = "NUMERO_PROTOCOLO")
	private String numeroProtocolo;

	@Transient
	private CategoriaOcorrencia categoria;

	@Transient
	private byte[] imagem;

	@Transient
	private Usuario usuario;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String texto) {
		this.descricao = texto;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public CategoriaOcorrencia getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaOcorrencia categoria) {
		this.categoria = categoria;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Long getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(Long grupoId) {
		this.grupoId = grupoId;
	}

	@Override
	public String toString() {
		return categoriaId;
	}

	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
