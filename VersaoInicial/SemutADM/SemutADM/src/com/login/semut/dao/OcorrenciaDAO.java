package com.login.semut.dao;

import java.util.List;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

import com.login.semut.model.Ocorrencia;
import com.login.semut.model.QtdPorStatus;
import com.login.semut.model.Regiao;
import com.login.semut.model.RegiaoCoordenada;
import com.login.semut.model.TipoSituacao;

/**
 * @author Ricardo
 * 
 */
public class OcorrenciaDAO {

	public List<Ocorrencia> pesquisar() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("ocorrenciadao.pesquisar");

		return broker.getCollectionBean(Ocorrencia.class, "id", "descricao", "imagem", "latitude", "longitude", "usuario.id", "categoriaOcorrencia.id", "data", "status", "usuario.nome", "categoriaOcorrencia.nome");
	}

	public List<Ocorrencia> pesquisarAlertas(Ocorrencia argOcorrencia) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("ocorrenciadao.pesquisaralertas", argOcorrencia.getStatus());

		return broker.getCollectionBean(Ocorrencia.class, "id", "descricao", "imagem", "latitude", "longitude", "usuario.id", "categoriaOcorrencia.id", "data", "status", "dataAlerta", "usuario.nome", "usuario.email", "categoriaOcorrencia.nome");
	}

	public List<Ocorrencia> pesquisarTodasOcorrencias(Ocorrencia argOcorrencia) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("ocorrenciadao.pesquisartodasocorrencias");

		return broker.getCollectionBean(Ocorrencia.class, "id", "descricao", "imagem", "latitude", "longitude", "usuario.id", "categoriaOcorrencia.id", "data", "status", "dataAlerta", "usuario.nome", "usuario.email", "categoriaOcorrencia.nome");
	}

	public List<QtdPorStatus> pesquisarQtdOcorrenciasPorStatus(Ocorrencia ocorrencia, Regiao regiao) {

		  TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		  
		  if (regiao == null){

		  broker.setPropertySQL("ocorrenciadao.pesquisarqtdocorrenciasporstatus",TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getId()),TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId()), ocorrencia.getDataInicial(), ocorrencia.getDataFinal());
		  
		  }
		  else{
		   
		   broker.setPropertySQL("ocorrenciadao.pesquisarqtdocorrenciasporstatusregiao",TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getId()),TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId()), ocorrencia.getDataInicial(), ocorrencia.getDataFinal(), regiao.getId());
		  }
		   

		  return broker.getCollectionBean(QtdPorStatus.class, "status", "qtd");
		 }
	
	public List<QtdPorStatus> pesquisarQtdOcorrenciasPorUsuario(Ocorrencia ocorrencia, Regiao regiao) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		if (regiao == null){

		broker.setPropertySQL("ocorrenciadao.pesquisarqtdocorrenciasporusuario", TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId()), ocorrencia.getDataInicial(), ocorrencia.getDataFinal());
		
		}
		else{
			broker.setPropertySQL("ocorrenciadao.pesquisarqtdocorrenciasporusuarioregiao", TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId()), ocorrencia.getDataInicial(), ocorrencia.getDataFinal(), regiao.getId());
		}
			

		return broker.getCollectionBean(QtdPorStatus.class, "status", "qtd");
	}

	public List<Ocorrencia> pesquisarOcorrenciasPendentes() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("ocorrenciadao.pesquisarocorrenciaspendentes");

		return broker.getCollectionBean(Ocorrencia.class, "id", "descricao", "imagem", "latitude", "longitude", "usuario.id", "categoriaOcorrencia.id", "data", "status", "dataAlerta", "usuario.nome", "usuario.email", "categoriaOcorrencia.nome","categoriaOcorrencia.GrupoOcorrencia.nome");
	}

	public List<Ocorrencia> pesquisarOcorrenciasCruas(Ocorrencia ocorrencia, Regiao regiao) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		if (regiao.getId() != null) {
			broker.setPropertySQL("ocorrenciadao.pesquisarocorrenciascruasregiao", TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getId()), TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId()), ocorrencia.getDataInicial(), ocorrencia.getDataFinal(), ocorrencia.getTipoSituacao().getId(), regiao.getId());
		} else {
			broker.setPropertySQL("ocorrenciadao.pesquisarocorrenciascruas", TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getId()), TSUtil.tratarLong(ocorrencia.getCategoriaOcorrencia().getGrupoOcorrencia().getId()), ocorrencia.getDataInicial(), ocorrencia.getDataFinal(), ocorrencia.getTipoSituacao().getId());
		}

		return broker.getCollectionBean(Ocorrencia.class, "id", "latitude", "longitude", "tipoSituacao.id", "categoriaOcorrencia.nome", "usuario.id");
	}

	public List<RegiaoCoordenada> obterCoordenadaRegiao(Regiao regiao) {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("ocorrenciadao.obtercoordenadaregiao", regiao.getId());

		return broker.getCollectionBean(RegiaoCoordenada.class, "latitude", "longitude");
	}

	public List<Regiao> pesquisarRegioes(Regiao argRegiao) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("ocorrenciadao.pesquisarregioes", argRegiao.getId());

		List<Regiao> listRegiao = broker.getCollectionBean(Regiao.class, "id", "nome");

		for (Regiao regiao : listRegiao) {
			regiao.setListRegiaoCoordenada(obterCoordenadaRegiao(regiao));
		}
		return listRegiao;
	}

	public Ocorrencia pesquisarOcorrenciasIndividual(Integer id) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("ocorrenciadao.pesquisarocorrenciaindividual", id);

		return (Ocorrencia) broker.getObjectBean(Ocorrencia.class, "id", "descricao", "imagem", "latitude", "longitude", "usuario.id", "categoriaOcorrencia.id", "data", "status", "dataAlerta", "usuario.nome", "usuario.email", "categoriaOcorrencia.nome", "tipoSituacao.descricao", "tipoSituacao.id");
	}

	public Ocorrencia inserir(Ocorrencia ocorrencia, boolean hasImage) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		ocorrencia.setId(broker.getSequenceNextValue("dbo.ocorrencias"));

		if (hasImage) {
			ocorrencia.setImagem(ocorrencia.getId().toString() + ".jpg");
		}

		broker.setPropertySQL("ocorrenciadao.inserir", ocorrencia.getDescricao(), ocorrencia.getUsuario().getId(), ocorrencia.getImagem(), ocorrencia.getLatitude(), ocorrencia.getLongitude(), ocorrencia.getStatus(), ocorrencia.getCategoriaOcorrencia().getId(), ocorrencia.getData(), ocorrencia.getTipoSituacao().getId(), ocorrencia.getDataPendencia());

		broker.execute();

		return ocorrencia;
	}

	public QtdPorStatus obterPendentesGeral() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("ocorrenciadao.obterpendentesgeral");

		return (QtdPorStatus) broker.getObjectBean(QtdPorStatus.class,"qtd");
	}

	public List<QtdPorStatus> pesquisarPorGrupo(TipoSituacao situacao) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("ocorrenciadao.pesquisarhojeporstatus",situacao.getId());

		return broker.getCollectionBean(QtdPorStatus.class,  "qtd","status");
	}

}
