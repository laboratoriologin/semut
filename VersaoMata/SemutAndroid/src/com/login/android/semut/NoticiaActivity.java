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
import android.widget.TextView;
import android.widget.Toast;

import com.login.android.semut.adapter.ListItemAdapterNoticia;
import com.login.android.semut.business.NoticiaBS;
import com.login.android.semut.model.Dados;
import com.login.android.semut.model.Grupo;
import com.login.android.semut.model.Noticia;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.util.Utilitarios;
import com.login.android.semut.view.ActionBar;

public class NoticiaActivity extends DefaultActivity implements OnTouchListener, OnItemClickListener {

	private ListView listaDeNoticias;
	private ListItemAdapterNoticia lstItemAdapterNoticia;
	private List<Noticia> lstNoticia = new ArrayList<Noticia>();
	private Grupo grupo;
	private Integer idFlag;
	private boolean onResume = false;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noticia);
		idFlag = (Integer) getIntent().getSerializableExtra("idFlag");
		if (idFlag == Constantes.Flags.TRANSALVADOR) {
			grupo = new Grupo(Constantes.CAT_TRANSALVADOR);
			this.initColorAndTitleByGrupo(grupo);

			((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
			if(Utilitarios.hasConnection(this)){
				new NoticiaBS(this).pesquisar(Constantes.TipoNoticia.TRANSALVADOR);
				progressDialog = new ProgressDialog(this);
				progressDialog.setMessage("Buscando notícias...");
				progressDialog.show();
			}else{
				Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
			}	
			listaDeNoticias = (ListView) findViewById(R.noticia.lstNoticia);
			lstItemAdapterNoticia = new ListItemAdapterNoticia(this, lstNoticia, grupo);
			listaDeNoticias.setOnItemClickListener(this);
			listaDeNoticias.setAdapter(lstItemAdapterNoticia);

		} else {
			
			grupo = new Grupo(Constantes.CAT_SUCOM);
			
			this.initColorAndTitleByGrupo(grupo);

			((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
			
			if(Utilitarios.hasConnection(this)){
				new NoticiaBS(this).pesquisar(Constantes.TipoNoticia.SUCOM);
				progressDialog = new ProgressDialog(this);
				progressDialog.setMessage("Buscando notícias...");
				progressDialog.show();
			}else{
				Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
			}	
			
			findViewById(R.id.actionbar).setBackgroundResource(R.color.sucom);
			((TextView) findViewById(R.activity_noticia.categoria)).setTextColor(getResources().getColor(R.color.sucom));
			((TextView) findViewById(R.activity_noticia.categoria)).setText(R.string.Quadro_de_avisos);
			listaDeNoticias = (ListView) findViewById(R.noticia.lstNoticia);
			lstItemAdapterNoticia = new ListItemAdapterNoticia(this, lstNoticia, grupo);
			listaDeNoticias.setOnItemClickListener(this);
			listaDeNoticias.setAdapter(lstItemAdapterNoticia);
		}

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

	protected ArrayList<Noticia> PopulateNoticia() {
		Dados dados = new Dados();

		return dados.getNoticias();
	}

	protected ArrayList<Noticia> PopulateSucom() {
		Dados dados = new Dados();

		return dados.getQuadroDeAvisos();
	}

	/**
	 * @return the lstItemAdapterNoticia
	 */
	public ListItemAdapterNoticia getLstItemAdapterNoticia() {
		return lstItemAdapterNoticia;
	}

	/**
	 * @param lstItemAdapterNoticia
	 *            the lstItemAdapterNoticia to set
	 */
	public void setLstItemAdapterNoticia(ListItemAdapterNoticia lstItemAdapterNoticia) {
		this.lstItemAdapterNoticia = lstItemAdapterNoticia;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Noticia noticia = lstNoticia.get(Long.valueOf(arg3).intValue());

		Intent intent = new Intent(this, DetalheNoticiaActivity.class);
		intent.putExtra("noticia", noticia);
		if (Constantes.CAT_TRANSALVADOR.equals(grupo.getId())) {
			intent.putExtra("idFlag", Constantes.Flags.NOTICIA);
		} else {
			intent.putExtra("idFlag", Constantes.Flags.AVISO);
		}
		startActivity(intent);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void getBusinessResult(Object result) {
		
		progressDialog.dismiss();

		if (result != null) {

			this.lstNoticia = (List<Noticia>) result;

			lstItemAdapterNoticia.setLstNoticia(this.lstNoticia);
			lstItemAdapterNoticia.notifyDataSetChanged();
		}
	}
}
