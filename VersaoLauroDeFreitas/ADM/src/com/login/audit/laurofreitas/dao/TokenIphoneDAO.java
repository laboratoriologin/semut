package com.login.audit.laurofreitas.dao;

import java.util.List;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;

import com.login.audit.laurofreitas.model.Token;

public class TokenIphoneDAO {

	public void inserir(Token model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tokeniphonedao.inserir", model.getToken());

		broker.execute();

	}

	public Token obter(Token model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tokeniphonedao.obter", model.getToken());

		return (Token) broker.getObjectBean(Token.class, "token");

	}

	public void excluir(Token model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tokeniphonedao.excluir", model.getToken());

		broker.execute();

	}

	@SuppressWarnings("unchecked")
	public List<Token> pesquisar() {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("tokeniphonedao.pesquisar");

		return broker.getCollectionBean(Token.class, "token");

	}

}
