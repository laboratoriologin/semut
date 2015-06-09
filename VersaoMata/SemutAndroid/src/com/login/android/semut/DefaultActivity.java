package com.login.android.semut;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.android.semut.model.Grupo;
import com.login.android.semut.model.Usuario;
import com.login.android.semut.sqlite.dao.DataManager;
import com.login.android.semut.util.Constantes;

public class DefaultActivity extends FragmentActivity implements OnTouchListener {

	private final String NOME = "nome";
	private final String EMAIL = "email";
	private final String SENHA = "senha";
	private final String TELEFONE = "telefone";
	private final String ID = "id";
	protected int colorParsed;
	protected int pressedColorParsed;

	public DataManager getDataManager() {

		return ((SemutApp) getApplication()).getDataManager();

	}
	
	public void getBusinessResult(Object result) {

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			v.setBackgroundColor(pressedColorParsed);

		} else {

			v.setBackgroundColor(colorParsed);

		}

		return false;
	}

	protected void initColorAndTitleByGrupo(Grupo grupo) {

		int colorId = R.color.transalvador;

		int pressedColorId = R.color.transalvador_highlighted;

		TextView titulo = (TextView) findViewById(R.id.titulo_action_bar);

		titulo.setText(Constantes.OCORRENCIAS);
		
		((ImageView) findViewById(R.id.imagem_action_bar)).setImageDrawable(getResources().getDrawable(R.drawable.iconemenu_ocorrencias));

		if (grupo != null) {

			if (Constantes.CAT_SUCOM.equals(grupo.getId())) {

				colorId = R.color.sucom;

				pressedColorId = R.color.sucom_highlighted;

				titulo.setText(Constantes.SUCOM);

				((ImageView) findViewById(R.id.imagem_action_bar)).setImageDrawable(getResources().getDrawable(R.drawable.iconemenu_sucom));

			}

			if (Constantes.CAT_CONFIG.equals(grupo.getId())) {

				colorId = R.color.configuracoes;

				pressedColorId = R.color.configuracoes_highlighted;

				titulo.setText(Constantes.CONFIGURACOES);

				((ImageView) findViewById(R.id.imagem_action_bar)).setImageDrawable(getResources().getDrawable(R.drawable.iconemenu_configuracoes));

			}

			if (Constantes.CAT_TELEFONES_UTEIS.equals(grupo.getId())) {

				colorId = R.color.telefones_uteis;

				pressedColorId = R.color.telefones_uteis_highlighted;

				titulo.setText(Constantes.TELEFONES_UTEIS);

				((ImageView) findViewById(R.id.imagem_action_bar)).setImageDrawable(getResources().getDrawable(R.drawable.iconemenu_telefones_uteis));

			}

		}

		colorParsed = Color.parseColor(getResources().getText(colorId).toString());

		pressedColorParsed = Color.parseColor(getResources().getText(pressedColorId).toString());

		findViewById(R.id.actionbar).setBackgroundResource(colorId);

	}

	protected void initColorByGrupo(Grupo grupo) {

		int colorId = R.color.transalvador;

		int pressedColorId = R.color.transalvador_highlighted;

		if (grupo != null) {

			if (Constantes.CAT_SUCOM.equals(grupo.getId())) {

				colorId = R.color.sucom;

				pressedColorId = R.color.sucom_highlighted;
			}

			if (Constantes.CAT_CONFIG.equals(grupo.getId())) {

				colorId = R.color.configuracoes;

				pressedColorId = R.color.configuracoes_highlighted;
			}

			if (Constantes.CAT_TELEFONES_UTEIS.equals(grupo.getId())) {

				colorId = R.color.telefones_uteis;

				pressedColorId = R.color.telefones_uteis_highlighted;
			}

		}

		colorParsed = Color.parseColor(getResources().getText(colorId).toString());

		pressedColorParsed = Color.parseColor(getResources().getText(pressedColorId).toString());
	}

	public void backPressed(View view) {
		super.onBackPressed();
	}

	public Double getLatitudeAtual() {

		SemutApp app = (SemutApp) getApplication();

		return app.getLatitude();

	}

	public Double getLongitudeAtual() {

		SemutApp app = (SemutApp) getApplication();

		return app.getLongitude();

	}

	protected Usuario getUsuarioLogado() {

		Usuario usuario = new Usuario();

		usuario.setEmail(getSharedPreferences(Constantes.SHARED_PREFS, 0).getString(EMAIL, null));

		if (TextUtils.isEmpty(usuario.getEmail())) {

			return null;

		}
		
		usuario.setTelefone(getSharedPreferences(Constantes.SHARED_PREFS, 0).getString(TELEFONE, null));
		
		usuario.setSenha(getSharedPreferences(Constantes.SHARED_PREFS, 0).getString(SENHA, null));
		
		usuario.setNome(getSharedPreferences(Constantes.SHARED_PREFS, 0).getString(NOME, null));
		
		usuario.setId(getSharedPreferences(Constantes.SHARED_PREFS, 0).getString(ID, null));

		return usuario;

	}

	protected void setUsuarioLogado(Usuario usuario) {

		SharedPreferences.Editor editor = getSharedPreferences(Constantes.SHARED_PREFS, 0).edit();

		editor.putString(EMAIL, usuario.getEmail());

		editor.putString(NOME, usuario.getNome());
		
		editor.putString(SENHA, usuario.getSenha());
		
		editor.putString(TELEFONE, usuario.getTelefone());
		
		editor.putString(ID, usuario.getId());

		editor.commit();

	}

	protected void removerUsuarioLogado() {

		SharedPreferences.Editor editor = getSharedPreferences(Constantes.SHARED_PREFS, 0).edit();

		editor.remove(EMAIL);

		editor.remove(NOME);
		
		editor.remove(SENHA);
		
		editor.remove(TELEFONE);
		
		editor.remove(ID);

		editor.commit();
		
		getDataManager().getOcorrenciaDAO().deleteAll();

	}

	protected void replaceUsuarioLogado(Usuario usuario) {
		this.removerUsuarioLogado();
		this.setUsuarioLogado(usuario);
	}

}
