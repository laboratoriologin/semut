package com.login.android.semut;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.login.android.semut.adapter.ListItemAdapterAlertas;
import com.login.android.semut.business.OcorrenciaBS;
import com.login.android.semut.model.Grupo;
import com.login.android.semut.model.Ocorrencia;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.util.Utilitarios;
import com.login.android.semut.view.ActionBar;

public class AlertasActivity extends DefaultActivity implements OnTouchListener, OnItemClickListener {

	private ListView listaDeAlertas;
	private List<Ocorrencia> lstAlertas = new ArrayList<Ocorrencia>();
	private ListItemAdapterAlertas lstItemAdapterOcorrencia;
	private Grupo grupo;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alertas);
		
		if(Utilitarios.hasConnection(this)){
			new OcorrenciaBS(this).pesquisar(Constantes.Status.ALERTA);
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Buscando alertas...");
			progressDialog.show();
		}else{
			Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
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
		progressDialog.dismiss();
		if (result != null) {
			this.lstAlertas = (List<Ocorrencia>) result;
			
			lstItemAdapterOcorrencia.setLstOcorrencia(lstAlertas);
			lstItemAdapterOcorrencia.notifyDataSetChanged();
		}
	}

}
