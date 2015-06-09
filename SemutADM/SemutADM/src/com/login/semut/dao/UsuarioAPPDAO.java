/**
 * 
 */
package com.login.audit.laurofreitas.dao;

import java.util.List;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

import com.login.audit.laurofreitas.model.Noticia;
import com.login.audit.laurofreitas.model.UsuarioAPP;

/**
 * @author Ricardo
 * 
 */
public class UsuarioAPPDAO {

	public List<UsuarioAPP> pesquisar() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuarioappdao.pesquisar");
		return broker.getCollectionBean(UsuarioAPP.class, "id", "nome", "email", "senha", "telefone");
	}
	
	public UsuarioAPP obterPorEmail(UsuarioAPP usuario) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuarioappdao.obterporemail", usuario.getEmail());

		return (UsuarioAPP) broker.getObjectBean(UsuarioAPP.class, "id", "nome", "email", "senha", "telefone");
	}

	public int inserir(UsuarioAPP usuario) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		
		usuario.setId(broker.getSequenceNextValue("dbo.usuario_app"));

		broker.setPropertySQL("usuarioappdao.inserir", usuario.getNome(), usuario.getEmail(), usuario.getTelefone(), usuario.getSenha());

		return broker.execute();
	}

	public int alterar(UsuarioAPP usuario) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuarioappdao.alterar", usuario.getNome(), usuario.getTelefone(), usuario.getSenha(), usuario.getEmail());

		return broker.execute();
	}
	
	public int alterarSenha(UsuarioAPP usuario) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuarioappdao.alterarsenha", usuario.getSenha(), usuario.getId());

		return broker.execute();
	}

}
