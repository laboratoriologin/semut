/**
 * 
 */
package com.login.audit.laurofreitas.dao;

import java.util.List;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.util.TSUtil;

import com.login.audit.laurofreitas.model.GrupoOcorrencia;
import com.login.audit.laurofreitas.model.Ocorrencia;
import com.login.audit.laurofreitas.model.QtdPorStatus;

/**
 * @author Ricardo
 * 
 */
public class GrupoOcorrenciaDAO {

	public List<GrupoOcorrencia> pesquisar() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("grupoocorrenciadao.pesquisar");
		return broker.getCollectionBean(GrupoOcorrencia.class, "id", "nome");
	}

	public List<QtdPorStatus> pesquisarOcorrenciasPorMes(Ocorrencia ocorrencia) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("grupoocorrenciadao.pesquisarocorrenciaspormes",ocorrencia.getCategoriaOcorrencia().getId(),ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId(), ocorrencia.getDataInicial(), ocorrencia.getDataFinal());

		return broker.getCollectionBean(QtdPorStatus.class,"qtd","agrupador","status");
	}
	
	public List<GrupoOcorrencia> pesquisarOcorrenciasPorTempoResolucao(Ocorrencia ocorrencia) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("grupoocorrenciadao.pesquisarocorrenciasportemporesolucao",TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getId()),ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId());

		return broker.getCollectionBean(GrupoOcorrencia.class,"quantidadeOcorrencias","mesAnoOcorrencias");
	}


	public List<QtdPorStatus> pesquisarOcorrenciasPorDia(Ocorrencia ocorrencia) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("grupoocorrenciadao.pesquisarocorrenciaspordia",ocorrencia.getCategoriaOcorrencia().getId(),ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId(),ocorrencia.getDataInicial(), ocorrencia.getDataFinal());
		
		return broker.getCollectionBean(QtdPorStatus.class,"qtd","dataOcorrencia","status");
	}

}
