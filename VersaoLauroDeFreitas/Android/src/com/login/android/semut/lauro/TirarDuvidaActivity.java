package com.login.android.semut.lauro;

import android.graphics.Color;
import android.os.Bundle;

import com.login.android.semut.lauro.model.Grupo;
import com.login.android.semut.lauro.util.Constantes;
import com.login.android.semut.lauro.view.ActionBar;

public class TirarDuvidaActivity extends DefaultActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tirar_duvidas);

		this.initColorAndTitleByGrupo(new Grupo(Constantes.CAT_TRANSALVADOR));

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		findViewById(R.id.imagem_action_bar).setBackgroundColor(Color.TRANSPARENT);

		findViewById(R.id.imagem_action_bar).setOnTouchListener(this);

	}

}
