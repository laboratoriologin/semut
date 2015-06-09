/**
 * 
 */
package com.login.semut.dao;

import java.util.List;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;

import com.login.semut.model.Noticia;
import com.login.semut.model.TipoNoticia;

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
