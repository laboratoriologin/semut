package com.login.semut.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

/**
 * 
 * @author argus.guedes
 * 
 */
@Entity
@Table(name = "regioes_coordenadas")
public class RegiaoCoordenada extends TSActiveRecordAb<RegiaoCoordenada> {
	/**
	 * Propriedade identificadora do objeto Grupo(Grupo de usuários).
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Propriedade nome do objeto Região(Região de coordenadas).
	 */
	@ManyToOne
	@JoinColumn(name = "regiao_id")
	private Regiao regiao;

	private String latitude;

	private String longitude;
	
	public RegiaoCoordenada() {
	}
	
	public RegiaoCoordenada(String lat, String lng) {
		this.latitude = lat;
		this.longitude = lng;
	}

	/**
	 * Obtém a propriedade identificadora do objeto Região(Região de
	 * coordenadas).
	 * 
	 * @return id Inteiro longo. Identificador do objeto Região(Região de
	 *         coordenadas).
	 */
	public final Long getId() {
		return TSUtil.tratarLong(id);
	}

	/**
	 * Seta a prorpiedade Id do objeto Região(Região de coordenadas).
	 * 
	 * @param pId
	 *            Identificador do objeto Região(Região de coordenadas).
	 */
	public final void setId(final Long pId) {
		this.id = pId;
	}

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public void delete(Regiao regiao) throws TSApplicationException {
		
		this.delete("DELETE FROM RegiaoCoordenada where regiao.id = ?", regiao.getId());

	}
	
}
