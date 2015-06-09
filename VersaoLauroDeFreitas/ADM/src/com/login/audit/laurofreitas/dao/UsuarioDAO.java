/**
 * 
 */
package com.login.audit.laurofreitas.dao;

import java.util.List;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

import com.login.audit.laurofreitas.model.Noticia;
import com.login.audit.laurofreitas.model.Usuario;
import com.login.audit.laurofreitas.model.UsuarioAPP;

/**
 * @author Ricardo
 *
 */
public class UsuarioDAO {
	
	public List<Usuario> pesquisar() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf ();

		broker.setPropertySQL("usuariodao.pesquisar" );
			return  broker.getCollectionBean(Usuario.class ,"id", "nome"  ,  "email" , "senha" );
	}
	
	public Usuario obterPorEmail(Usuario usuario) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.obterporemail", usuario.getEmail());

		return (Usuario) broker.getObjectBean(Usuario.class, "id", "login", "senha", "flagAtivo", "nome", "grupo.id", "email");
	}
	
	public int alterarSenha(Usuario usuario) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.alterarsenha", usuario.getSenha(), usuario.getId());

		return broker.execute();
	}

}
