package com.login.android.semut;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnTouchListener;

import com.login.android.semut.model.Grupo;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.view.ActionBar;

public class TelefoneActivity extends DefaultActivity implements OnTouchListener {

	private Grupo grupo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telefones);

		grupo = new Grupo(Constantes.CAT_TELEFONES_UTEIS);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		this.initColorAndTitleByGrupo(grupo);
	}

	public void onClickSamu(View v) {
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(Constantes.TELSAMU));
		startActivity(callIntent);
	}

	public void onClickBomb(View v) {
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(Constantes.TELBOMB));
		startActivity(callIntent);
	}

	public void onClickDefesa(View v) {
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(Constantes.TELDEFESA));
		startActivity(callIntent);
	}

	public void onClickPolicia(View v) {
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(Constantes.TELPOLICIA));
		startActivity(callIntent);
	}

}
