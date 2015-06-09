package com.login.audit.laurofreitas.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TokenIphone implements Serializable {

	private String token;
	
	public TokenIphone() {
	
	}
	
	public TokenIphone(String token) {
		this.token=token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
