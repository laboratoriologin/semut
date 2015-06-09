/**
 * 
 */
package com.login.semut.dao;

import java.util.List;

import com.login.semut.model.Token;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

public class TokenAndroidDAO {

	public void inserir(Token token) throws TSApplicationException {
		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();
		broker.setPropertySQL("tokenandroiddao.inserir", token.getToken());
		broker.execute();
	}

	public Token obter(Token model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tokenandroiddao.obter", model.getToken());

		return (Token) broker.getObjectBean(Token.class, "token");

	}

	@SuppressWarnings("unchecked")
	public List<Token> pesquisar() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tokenandroiddao.pesquisar");

		return broker.getCollectionBean(Token.class, "token");

	}
}
