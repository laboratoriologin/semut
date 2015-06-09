package com.login.android.semut;

import android.os.Bundle;
import android.view.View.OnTouchListener;

import com.login.android.semut.model.Grupo;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.view.ActionBar;

public class NormasActivity extends DefaultActivity implements
OnTouchListener {

	private Grupo grupo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_normas);
		
		grupo = new Grupo(Constantes.CAT_CONFIG);
		
		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		this.initColorAndTitleByGrupo(grupo);
	}
}
