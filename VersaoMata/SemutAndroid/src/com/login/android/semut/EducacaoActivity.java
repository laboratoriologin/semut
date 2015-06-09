package com.login.android.semut;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.login.android.semut.adapter.ListItemAdapterEducacao;
import com.login.android.semut.business.NoticiaBS;
import com.login.android.semut.business.UsuarioBS;
import com.login.android.semut.model.Dados;
import com.login.android.semut.model.Educacao;
import com.login.android.semut.model.Grupo;
import com.login.android.semut.model.Noticia;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.util.Utilitarios;
import com.login.android.semut.view.ActionBar;

public class EducacaoActivity extends DefaultActivity implements OnTouchListener, OnItemClickListener {

	private ListView listaDeEducacao;
	private ListItemAdapterEducacao lstItemAdapterEducacao;
	private List<Noticia> lstEducacao = new ArrayList<Noticia>();
	private Grupo grupo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_educacao);

		if (Utilitarios.hasConnection(this)) {
			new NoticiaBS(this).pesquisar(Constantes.TipoNoticia.EDUCACAO);
		} else {
			Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
		}

		grupo = new Grupo(Constantes.CAT_TRANSALVADOR);
		this.initColorAndTitleByGrupo(grupo);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
		listaDeEducacao = (ListView) findViewById(R.educacao.lstEducacao);
		lstItemAdapterEducacao = new ListItemAdapterEducacao(this, lstEducacao);
		listaDeEducacao.setOnItemClickListener(this);
		listaDeEducacao.setAdapter(lstItemAdapterEducacao);

	}

	/**
	 * @return the lstItemAdapterNoticia
	 */
	public ListItemAdapterEducacao getLstItemAdapterNoticia() {
		return lstItemAdapterEducacao;
	}

	/**
	 * @param lstItemAdapterNoticia
	 *            the lstItemAdapterNoticia to set
	 */
	public void setLstItemAdapterEducacao(ListItemAdapterEducacao lstItemAdapterEducacao) {
		this.lstItemAdapterEducacao = lstItemAdapterEducacao;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Noticia educacao = lstEducacao.get(Long.valueOf(arg3).intValue());
		Intent intent = new Intent(this, DetalheNoticiaActivity.class);
		intent.putExtra("educacao", educacao);
		intent.putExtra("idFlag", Constantes.Flags.EDUCACAO);
		startActivity(intent);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void getBusinessResult(Object result) {

		if (result != null) {

			this.lstEducacao = (List<Noticia>) result;

			lstItemAdapterEducacao.setLstEducacao(this.lstEducacao);
			lstItemAdapterEducacao.notifyDataSetChanged();
		}
	}
}
