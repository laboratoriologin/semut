package com.login.android.semut.inicial;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.login.android.semut.adapter.ListItemAdapterAlertas;
import com.login.android.semut.business.NoticiaBS;
import com.login.android.semut.business.OcorrenciaBS;
import com.login.android.semut.model.Alerta;
import com.login.android.semut.model.Dados;
import com.login.android.semut.model.Grupo;
import com.login.android.semut.model.Noticia;
import com.login.android.semut.model.Ocorrencia;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.util.Utilitarios;
import com.login.android.semut.view.ActionBar;

public class AlertasActivity extends DefaultActivity implements OnTouchListener, OnItemClickListener {

	private ListView listaDeAlertas;
	private Button btRecarregar;
	private List<Ocorrencia> lstAlertas = new ArrayList<Ocorrencia>();
	private ListItemAdapterAlertas lstItemAdapterOcorrencia;
	private Grupo grupo;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alertas);

		btRecarregar = (Button) findViewById(R.id.bt_recarregar);

		if (Utilitarios.hasConnection(this)) {
			new OcorrenciaBS(this).pesquisar(Constantes.Status.ALERTA);
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Buscando alertas...");
			progressDialog.show();
			progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		} else {
			Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
			this.esconderLista();
		}

		grupo = new Grupo(Constantes.CAT_TRANSALVADOR);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		this.initColorAndTitleByGrupo(grupo);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		String mensagem = getIntent().getStringExtra("mensagem_recebida");

		listaDeAlertas = (ListView) findViewById(R.alertas.lstAlertas);
		lstItemAdapterOcorrencia = new ListItemAdapterAlertas(this, lstAlertas);
		listaDeAlertas.setOnItemClickListener(this);
		listaDeAlertas.setAdapter(lstItemAdapterOcorrencia);
	}

	public void esconderLista() {
		if (listaDeAlertas != null)
			listaDeAlertas.setVisibility(View.GONE);
		btRecarregar.setVisibility(View.VISIBLE);
	}

	public void mostrarLista() {
		if (listaDeAlertas != null)
			listaDeAlertas.setVisibility(View.VISIBLE);
		btRecarregar.setVisibility(View.GONE);
	}

	public void onClickRecarregar(View v) {
		mostrarLista();
		if (Utilitarios.hasConnection(this)) {
			new OcorrenciaBS(this).pesquisar(Constantes.Status.ALERTA);
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Buscando alertas...");
			progressDialog.show();
			progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		} else {
			Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
			this.esconderLista();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Ocorrencia ocorrencia = lstAlertas.get(Long.valueOf(arg3).intValue());

		Intent intent = new Intent(this, DetalheNoticiaActivity.class);
		intent.putExtra("ocorrencia", ocorrencia);
		startActivity(intent);

	}

	public ListView getListaDeAlertas() {
		return listaDeAlertas;
	}

	public void setListaDeAlertas(ListView listaDeAlertas) {
		this.listaDeAlertas = listaDeAlertas;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getBusinessResult(Object result) {

		this.progressDialog.dismiss();

		if (result != null && ((ArrayList<Object>) result).size() != 0) {
			this.lstAlertas = (List<Ocorrencia>) result;

			lstItemAdapterOcorrencia.setLstOcorrencia(lstAlertas);
			lstItemAdapterOcorrencia.notifyDataSetChanged();
		} else {
			Toast toast = Toast.makeText(this, "Não foi possível carregar as informações.", Toast.LENGTH_SHORT);
			toast.show();
			this.esconderLista();
		}
	}

}
