package com.login.android.semut.model;

import java.io.Serializable;

import android.widget.ImageView;

@SuppressWarnings("serial")
public class GridItem implements Serializable {
	
	String textoItem;
	int imagem;
	
	public String getTextoItem() {
		return textoItem;
	}
	public void setTextoItem(String textoItem) {
		this.textoItem = textoItem;
	}
	public int getImagem() {
		return imagem;
	}
	public void setImagem(int imagem) {
		this.imagem = imagem;
	}

}
