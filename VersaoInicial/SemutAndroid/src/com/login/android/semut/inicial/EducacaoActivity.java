package com.login.android.semut.inicial;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.login.android.semut.inicial.R;
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
	private Button btRecarregar;
	private ListItemAdapterEducacao lstItemAdapterEducacao;
	private List<Noticia> lstEducacao = new ArrayList<Noticia>();
	private Grupo grupo;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_educacao);

		btRecarregar = (Button) findViewById(R.id.bt_recarregar);

		if (Utilitarios.hasConnection(this)) {
			new NoticiaBS(this).pesquisar(Constantes.TipoNoticia.EDUCACAO);
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Buscando informações...");
			progressDialog.show();
			progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		} else {
			Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
			this.esconderLista();
		}

		grupo = new Grupo(Constantes.CAT_TRANSALVADOR);
		this.initColorAndTitleByGrupo(grupo);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
		listaDeEducacao = (ListView) findViewById(R.educacao.lstEducacao);
		lstItemAdapterEducacao = new ListItemAdapterEducacao(this, lstEducacao);
		listaDeEducacao.setOnItemClickListener(this);
		listaDeEducacao.setAdapter(lstItemAdapterEducacao);

	}

	public void esconderLista() {
		if (listaDeEducacao != null)
			listaDeEducacao.setVisibility(View.GONE);
		btRecarregar.setVisibility(View.VISIBLE);
	}

	public void mostrarLista() {
		if (listaDeEducacao != null)
			listaDeEducacao.setVisibility(View.VISIBLE);
		btRecarregar.setVisibility(View.GONE);
	}

	public void onClickRecarregar(View v) {
		mostrarLista();
		if (Utilitarios.hasConnection(this)) {
			new NoticiaBS(this).pesquisar(Constantes.TipoNoticia.EDUCACAO);
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Buscando informações...");
			progressDialog.show();
			progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		} else {
			Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
			this.esconderLista();
		}
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

		this.progressDialog.dismiss();

		if (result != null && ((ArrayList<Object>) result).size() != 0) {

			this.lstEducacao = (List<Noticia>) result;

			lstItemAdapterEducacao.setLstEducacao(this.lstEducacao);
			lstItemAdapterEducacao.notifyDataSetChanged();
		} else {
			Toast toast = Toast.makeText(this, "Não foi possível carregar as informações.", Toast.LENGTH_SHORT);
			toast.show();
			this.esconderLista();
		}
	}
}
