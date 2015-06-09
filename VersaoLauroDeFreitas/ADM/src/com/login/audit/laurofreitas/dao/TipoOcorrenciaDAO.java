/**
 * 
 */
package com.login.audit.laurofreitas.dao;

import java.util.List;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;

import com.login.audit.laurofreitas.model.CategoriaOcorrencia;
import com.login.audit.laurofreitas.model.Noticia;
import com.login.audit.laurofreitas.model.TipoNoticia;

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
