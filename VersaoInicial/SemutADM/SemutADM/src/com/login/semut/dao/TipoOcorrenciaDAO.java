/**
 * 
 */
package com.login.semut.dao;

import java.util.List;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;

import com.login.semut.model.CategoriaOcorrencia;
import com.login.semut.model.Noticia;
import com.login.semut.model.TipoNoticia;

/**
 * @author Ricardo
 *
 */
public class TipoOcorrenciaDAO {
	
	public List<CategoriaOcorrencia> pesquisar() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf ();

		broker.setPropertySQL("tipoocorrenciadao.pesquisar" );
			return  broker.getCollectionBean(CategoriaOcorrencia.class ,"id",  "grupoOcorrencia.id", "nome");
	}
	
	public List<CategoriaOcorrencia> obterporfiltro(CategoriaOcorrencia argCategoriaOcorrencia) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf ();
			broker.setPropertySQL("tipoocorrenciadao.obterporfiltro" , argCategoriaOcorrencia.getGrupoOcorrencia().getId());
	
			return  broker.getCollectionBean(CategoriaOcorrencia.class ,"id", "grupoOcorrencia.id", "nome");
	}

}
