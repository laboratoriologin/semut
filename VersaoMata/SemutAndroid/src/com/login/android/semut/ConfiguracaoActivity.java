package com.login.android.semut;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.login.android.semut.model.Grupo;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.view.ActionBar;

public class ConfiguracaoActivity extends DefaultActivity {

	private Grupo grupo;
	private boolean onResume = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracao);

		grupo = new Grupo(Constantes.CAT_CONFIG);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		this.initColorAndTitleByGrupo(grupo);
	}

	public void onClickNormas(View v) {

		Intent mainIntent = new Intent(ConfiguracaoActivity.this, NormasActivity.class);

		ConfiguracaoActivity.this.startActivity(mainIntent);

		findViewById(R.id.config_normas).setBackgroundColor(getResources().getColor(R.color.configuracoes_highlighted));

	}

	public void onClickCadastro(View v) {

		Intent mainIntent = new Intent(ConfiguracaoActivity.this, CadastroActivity.class);

		ConfiguracaoActivity.this.startActivity(mainIntent);

		findViewById(R.id.config_cadastro).setBackgroundColor(getResources().getColor(R.color.configuracoes_highlighted));
		
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (onResume) {
			Intent intent = getIntent();
			overridePendingTransition(0, 0);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			finish();

			overridePendingTransition(0, 0);
			startActivity(intent);
		}

		onResume = true;
	}

}
