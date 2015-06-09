/**
 * 
 */
package com.login.audit.laurofreitas.dao;

import java.util.List;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;

import com.login.audit.laurofreitas.model.Noticia;

/**
 * @author Ricardo
 *
 */
public class NoticiaDAO {
	
	public List<Noticia> pesquisar() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf ();

		broker.setPropertySQL("noticiadao.pesquisar" );
			return  broker.getCollectionBean(Noticia.class ,"id", "titulo"  ,  "descricao" , "imagem" , "data" , "tipoNoticia.id" , "tipoNoticia.nome");
	}
	
	public List<Noticia> obterporfiltro(Noticia argNoticia) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf ();
			broker.setPropertySQL("noticiadao.obterporfiltro" , argNoticia.getTipoNoticia().getId() );
	
			return  broker.getCollectionBean(Noticia.class ,"id", "titulo"  ,  "descricao" , "imagem" , "data" , "tipoNoticia.id" , "tipoNoticia.nome");
	}

}
