package com.login.audit.laurofreitas.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import com.login.audit.laurofreitas.util.SemutUtil;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

/**
 * 
 * @author argus.guedes
 * 
 */
@Entity
@Table(name = "regioes")
public class Regiao extends TSActiveRecordAb<Regiao> {

	/**
	 * Propriedade identificadora do objeto Grupo(Grupo de usuários).
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Propriedade nome do objeto Região(Região de coordenadas).
	 */
	private String nome;

	@Transient
	private Integer quantidadeResolvida;

	@Transient
	private Integer quantidadePendente;

	@Transient
	private Integer quantidadeAceita;

	@Transient
	private Integer quantidadeRecusada;

	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL)
	private List<RegiaoCoordenada> listRegiaoCoordenada;

	public final Long getId() {
		return TSUtil.tratarLong(id);
	}

	public final void setId(final Long pId) {
		this.id = pId;
	}

	public final String getNome() {
		return nome;
	}

	public final void setNome(final String pNome) {
		this.nome = pNome;
	}

	public List<RegiaoCoordenada> getListRegiaoCoordenada() {
		return listRegiaoCoordenada;
	}

	public void setListRegiaoCoordenada(List<RegiaoCoordenada> listRegiaoCoordenada) {
		this.listRegiaoCoordenada = listRegiaoCoordenada;
	}

	@Override
	public final List<Regiao> findByModel(final String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		query.append(" from Regiao r where lower(r.nome) like coalesce(?, r.nome) ");

		params.add(SemutUtil.tratarString(this.nome));

		return super.find(query.toString(), "id", params.toArray());
	}

	public final List<Regiao> findByIdDiferente(Long id) {
		StringBuilder query = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		query.append(" from Regiao r where lower(r.id) <> coalesce(?, r.id) ");

		params.add(id);

		return super.find(query.toString(), "id", params.toArray());
	}

	public Integer getQuantidadeResolvida() {
		return quantidadeResolvida;
	}

	public void setQuantidadeResolvida(Integer quantidadeResolvida) {
		this.quantidadeResolvida = quantidadeResolvida;
	}

	public Integer getQuantidadePendente() {
		return quantidadePendente;
	}

	public void setQuantidadePendente(Integer quantidadePendente) {
		this.quantidadePendente = quantidadePendente;
	}

	public Integer getQuantidadeAceita() {
		return quantidadeAceita;
	}

	public void setQuantidadeAceita(Integer quantidadeAceita) {
		this.quantidadeAceita = quantidadeAceita;
	}

	public Integer getQuantidadeRecusada() {
		return quantidadeRecusada;
	}

	public void setQuantidadeRecusada(Integer quantidadeRecusada) {
		this.quantidadeRecusada = quantidadeRecusada;
	}

}
