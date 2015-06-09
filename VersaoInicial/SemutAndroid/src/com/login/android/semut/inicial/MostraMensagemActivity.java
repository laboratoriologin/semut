package com.login.android.semut.inicial;


import com.login.android.semut.util.Constantes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity responsável por mostrar a mensagem 
 * recebida do GCM na tela.
 * 
 * @author lucasfreitas
 *
 */
public class MostraMensagemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		SharedPreferences.Editor editor = getSharedPreferences(Constantes.SHARED_PREFS, 0).edit();
		editor.putInt("count", 1);
		editor.commit();
		
		super.onCreate(savedInstanceState);
		// Definimos uma TextView para mostrar a mensagem na tela
		TextView texto = new TextView(getApplicationContext());
		// Define como texto da TextView a mensagem recebida do GCM
		texto.setText(getIntent().getStringExtra("mensagem_recebida"));
		// Ajusta tamanho e cor da fonte
		texto.setTextSize(20.0F);
		texto.setTextColor(Color.BLACK);
		/*
		 * Para tornar as coisas mais simples, mostraremos apenas uma TextView
		 * na tela com o conteúdo da mensagem recebida da Nuvem através do GCM.
		 */
		setContentView(texto);
	}

}
