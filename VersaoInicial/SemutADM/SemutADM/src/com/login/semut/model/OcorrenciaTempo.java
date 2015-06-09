package com.login.semut.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OcorrenciaTempo implements Serializable {

	private String agrupador;
	private long quantidadeResolvida;
	private long quantidadeAceita;
	private long quantidadePendente;
	private long quantidadeRecusada;
	
	private final String RESOLVIDA = "resolvida";
	private final String PENDENTE = "pendente";
	private final String RECUSADA = "recusada";
	private final String ACEITA = "aceita";

	public String getAgrupador() {
		return agrupador;
	}

	public void setAgrupador(String agrupador) {
		this.agrupador = agrupador;
	}

	public long getQuantidadeResolvida() {
		return quantidadeResolvida;
	}

	public void setQuantidadeResolvida(long quantidadeResolvida) {
		this.quantidadeResolvida = quantidadeResolvida;
	}

	public long getQuantidadeAceita() {
		return quantidadeAceita;
	}

	public void setQuantidadeAceita(long quantidadeAceita) {
		this.quantidadeAceita = quantidadeAceita;
	}

	public long getQuantidadePendente() {
		return quantidadePendente;
	}

	public void setQuantidadePendente(long quantidadePendente) {
		this.quantidadePendente = quantidadePendente;
	}

	public long getQuantidadeRecusada() {
		return quantidadeRecusada;
	}

	public void setQuantidadeRecusada(long quantidadeRecusada) {
		this.quantidadeRecusada = quantidadeRecusada;
	}
	
	public void setValue(QtdPorStatus qtdPorStatus) {
		
		if(qtdPorStatus.getStatus().equalsIgnoreCase(RECUSADA)) {
			this.quantidadeRecusada =  this.quantidadeRecusada + qtdPorStatus.getQtd();
		} else if(qtdPorStatus.getStatus().equalsIgnoreCase(PENDENTE)) {
			this.quantidadePendente =  this.quantidadePendente + qtdPorStatus.getQtd();
		} else if(qtdPorStatus.getStatus().equalsIgnoreCase(ACEITA)) {
			this.quantidadeAceita =  this.quantidadeAceita + qtdPorStatus.getQtd();
		} else if(qtdPorStatus.getStatus().equalsIgnoreCase(RESOLVIDA)) {
			this.quantidadeResolvida =  this.quantidadeResolvida + qtdPorStatus.getQtd();
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agrupador == null) ? 0 : agrupador.hashCode());
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
		OcorrenciaTempo other = (OcorrenciaTempo) obj;
		if (agrupador == null) {
			if (other.agrupador != null)
				return false;
		} else if (!agrupador.equals(other.agrupador))
			return false;
		return true;
	}

}
