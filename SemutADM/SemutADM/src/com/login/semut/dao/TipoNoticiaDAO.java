/**
 * 
 */
package com.login.audit.laurofreitas.dao;

import java.util.List;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;

import com.login.audit.laurofreitas.model.Noticia;
import com.login.audit.laurofreitas.model.TipoNoticia;

/**
 * @author Ricardo
 *
 */
public class TipoNoticiaDAO {
	
	public List<TipoNoticia> pesquisar() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf ();

		broker.setPropertySQL("tiponoticiadao.pesquisar" );
			return  broker.getCollectionBean(TipoNoticia.class ,"id", "nome");
	}

}
