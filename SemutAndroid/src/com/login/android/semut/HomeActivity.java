package com.login.android.semut.lauro;

import java.util.List;

import com.login.android.semut.lauro.business.CategoriaOcorrenciaBS;
import com.login.android.semut.lauro.business.UsuarioBS;
import com.login.android.semut.lauro.model.CategoriaOcorrencia;
import com.login.android.semut.lauro.model.Grupo;
import com.login.android.semut.lauro.util.Constantes;
import com.login.android.semut.lauro.util.GCM;
import com.login.android.semut.lauro.util.Utilitarios;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends DefaultActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		GCM.ativa(getApplicationContext(), this);
		preecherCategoriasOcorrencias(Constantes.GRUPO_SUCOM);
		preecherCategoriasOcorrencias(Constantes.GRUPO_TRANSALVADOR);
		// GCM.desativa(getApplicationContext());
	}
	
	public void preecherCategoriasOcorrencias(String grupo) {

		if (Utilitarios.hasConnection(this)) {
			new CategoriaOcorrenciaBS(this).pesquisar(grupo);
		} else {
			Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
		}
	}

	public void onClickTransalvador(View v) {

		Intent mainIntent = new Intent(HomeActivity.this, TransalvadorActivity.class);
		HomeActivity.this.startActivity(mainIntent);
		findViewById(R.home.elemento1square).setBackgroundColor(getResources().getColor(R.color.transalvador_highlighted));
	}

	public void onClickConfig(View v) {

		Intent mainIntent = new Intent(HomeActivity.this, ConfiguracaoActivity.class);
		HomeActivity.this.startActivity(mainIntent);
		findViewById(R.home.elemento3square).setBackgroundColor(getResources().getColor(R.color.configuracoes_highlighted));
	}

	public void onClickTel(View v) {

		Intent mainIntent = new Intent(HomeActivity.this, TelefoneActivity.class);
		HomeActivity.this.startActivity(mainIntent);
		findViewById(R.home.elemento4square).setBackgroundColor(getResources().getColor(R.color.telefones_uteis_highlighted));
	}
	
	@Override
	protected void onResume() {
		String mensagem = null;

		super.onResume();
		findViewById(R.home.elemento1square).setBackgroundColor(getResources().getColor(R.color.transalvador));
		//findViewById(R.home.elemento2square).setBackgroundColor(getResources().getColor(R.color.sucom));
		findViewById(R.home.elemento3square).setBackgroundColor(getResources().getColor(R.color.configuracoes));
		findViewById(R.home.elemento4square).setBackgroundColor(getResources().getColor(R.color.telefones_uteis));

		SharedPreferences.Editor editor = getSharedPreferences(Constantes.SHARED_PREFS, 0).edit();
		editor.putInt("count", 1);
		editor.commit();

		if (getIntent() != null) {
			mensagem = getIntent().getStringExtra("mensagem_recebida");
		}

		if (mensagem != null) {
			Intent intent = new Intent(HomeActivity.this, AlertasActivity.class);
			intent.putExtra("mensagem_recebida", mensagem);
			startActivity(intent);
			getIntent().setData(null);
			setIntent(null);
		}

		mensagem = null;

	}
	
	@Override
	public void getBusinessResult(Object result) {
		if (result != null && !(result instanceof String)) {
			@SuppressWarnings("unchecked")
			List<CategoriaOcorrencia> lista = (List<CategoriaOcorrencia>) result;

			for (CategoriaOcorrencia categoriaOcorrencia : lista) {
				CategoriaOcorrencia cat = getDataManager().getCategoriaOcorrenciaDAO().getByClause(" ID = ?", new String[] { categoriaOcorrencia.getId() });
				if (cat == null) {

					try {
						getDataManager().getCategoriaOcorrenciaDAO().save(categoriaOcorrencia);
					} catch (Exception e) {
						Log.i(Constantes.TAG, "Categoria já adicionada.");
					}
				} else if (!cat.getDescricao().equals(categoriaOcorrencia.getDescricao())) {
					try {
						getDataManager().getCategoriaOcorrenciaDAO().update(categoriaOcorrencia, Long.parseLong(cat.getId()));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			List<CategoriaOcorrencia> listaDoCelular = getDataManager().getCategoriaOcorrenciaDAO().getAllbyClause(" ID_GRUPO = ?", new String[] { lista.get(0).getId_grupo().toString() }, null, null, null);

			for (CategoriaOcorrencia categoriaOcorrenciaCel : listaDoCelular) {
				if (!lista.contains(categoriaOcorrenciaCel)) {
					getDataManager().getCategoriaOcorrenciaDAO().delete(Long.parseLong(categoriaOcorrenciaCel.getId()));
				}
			}
		}
	}
}
