package com.login.semut.dao;

import java.util.List;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.util.TSUtil;

import com.login.semut.model.Ocorrencia;
import com.login.semut.model.Regiao;
import com.login.semut.model.RegiaoCoordenada;

public class RegiaoDAO {
	
	public List<Regiao> pesquisarQtdSituacao(Ocorrencia ocorrencia, Regiao argRegiao) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("regiaodao.pesquisarqtdocorrencias", TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getId()), TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId()) , ocorrencia.getDataInicial(), ocorrencia.getDataFinal(),TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getId()),TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId()) , ocorrencia.getDataInicial(), ocorrencia.getDataFinal(),TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getId()),TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId()) , ocorrencia.getDataInicial(), ocorrencia.getDataFinal(),TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getId()),TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId()) , ocorrencia.getDataInicial(), ocorrencia.getDataFinal(), argRegiao.getId());
		
		//broker.setPropertySQL("regiaodao.pesquisarqtdocorrencias");

		return broker.getCollectionBean(Regiao.class,"id","nome", "quantidadeAceita","quantidadeRecusada","quantidadePendente","quantidadeResolvida");

	}

	public List<RegiaoCoordenada> pesquisar(Regiao regiao) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("regiaodao.pesquisarcoordenadas", regiao.getId());

		return broker.getCollectionBean(RegiaoCoordenada.class, "latitude", "longitude");

	}

}
