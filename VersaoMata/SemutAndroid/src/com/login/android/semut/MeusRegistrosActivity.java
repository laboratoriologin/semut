package com.login.android.semut;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.login.android.semut.adapter.ListItemAdapterMeusRegistros;
import com.login.android.semut.model.Grupo;
import com.login.android.semut.model.Noticia;
import com.login.android.semut.model.Ocorrencia;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.view.ActionBar;

public class MeusRegistrosActivity extends DefaultActivity implements OnTouchListener, OnItemClickListener {

	private ListView listaDeOcorrencias;
	private ListItemAdapterMeusRegistros lstItemAdapterOcorrencia;
	private ArrayList<Noticia> lstNoticia;
	private ArrayList<Ocorrencia> lstOcorrencia;
	private Grupo grupo;
	public static final Integer TRANSALVADOR = 2;
	public static final Integer SUCOM = 4;
	public static final Integer EDUCACAO = 5;
	private int grupoFlag;
	private boolean onResume = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_meus_registros);

		grupoFlag = getIntent().getIntExtra("id", 0);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		if (grupoFlag == Constantes.Flags.TRANSALVADOR) {

			grupo = new Grupo(Constantes.CAT_TRANSALVADOR);

			this.lstNoticia = populate(TRANSALVADOR);

		} else if (grupoFlag == Constantes.Flags.SUCOM) {

			grupo = new Grupo(Constantes.CAT_SUCOM);

			((TextView) findViewById(R.activity_noticia.categoria)).setTextColor(getResources().getColor(R.color.sucom));

			this.lstNoticia = new ArrayList<Noticia>();

			this.lstNoticia = populate(SUCOM);

		}

		this.initColorAndTitleByGrupo(grupo);

		this.lstOcorrencia = recuperarOcorrências(grupo);

		listaDeOcorrencias = (ListView) findViewById(R.meusregistros.lstregistros);

		lstItemAdapterOcorrencia = new ListItemAdapterMeusRegistros(this, lstNoticia, lstOcorrencia);

		listaDeOcorrencias.setOnItemClickListener(this);

		listaDeOcorrencias.setAdapter(lstItemAdapterOcorrencia);
	}

	private ArrayList<Noticia> populate(Integer tipoNoticia) {

		lstNoticia = (ArrayList<Noticia>) super.getDataManager().getNoticiaDAO().getAllbyClause("TIPONOTICIA=?", new String[] { tipoNoticia.toString() }, null, null, null);

		if (tipoNoticia == TRANSALVADOR) {

			lstNoticia.addAll((ArrayList<Noticia>) super.getDataManager().getNoticiaDAO().getAllbyClause("TIPONOTICIA=?", new String[] { EDUCACAO.toString() }, null, null, null));
		}

		return lstNoticia;

	}

	private ArrayList<Ocorrencia> recuperarOcorrências(Grupo grupo) {

		ArrayList<Ocorrencia> ocorrencias = (ArrayList<Ocorrencia>) getDataManager().getOcorrenciaDAO().getAllbyClause("ID_GRUPO=?", new String[] { grupo.getId().toString() }, null, null, "DATA");

		for (Ocorrencia ocorrencia : ocorrencias) {
			ocorrencia.setCategoria(getDataManager().getCategoriaOcorrenciaDAO().get(Long.parseLong(ocorrencia.getCategoriaId())));
		}

		Collections.reverse(ocorrencias);

		return ocorrencias;

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

	/**
	 * @return the lstItemAdapterNoticia
	 */
	public ListItemAdapterMeusRegistros getLstItemAdapterNoticia() {
		return lstItemAdapterOcorrencia;
	}

	/**
	 * @param lstItemAdapterNoticia
	 *            the lstItemAdapterNoticia to set
	 */
	public void setLstItemAdapterNoticia(ListItemAdapterMeusRegistros lstItemAdapterNoticia) {
		this.lstItemAdapterOcorrencia = lstItemAdapterNoticia;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Object objeto = lstItemAdapterOcorrencia.getItem(arg2);

		if (objeto instanceof Noticia) {

			Intent intent = new Intent(this, DetalheNoticiaActivity.class);
			intent.putExtra("noticia", (Noticia) objeto);
			if (grupoFlag == Constantes.Flags.TRANSALVADOR)
				intent.putExtra("idFlag", Constantes.Flags.NOTICIA);
			else
				intent.putExtra("idFlag", Constantes.Flags.AVISO);
			
			intent.putExtra("fromReg", Constantes.Flags.ISREG);
			startActivity(intent);

		} else {
			String path = Environment.getExternalStorageDirectory().toString();

			File file = new File(path, "oc" + DateFormat.format("ddMMyyyhhmmss", ((Ocorrencia) objeto).getData()) + ".jpg");

			if (file.exists()) {
				Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
				ByteArrayOutputStream bos = new ByteArrayOutputStream();

				bitmap.compress(CompressFormat.JPEG, 60, bos);

				((Ocorrencia) objeto).setImagem(bos.toByteArray());
			}
			Intent intent = new Intent(this, ConfirmacaoOcorrenciaActivity.class);

			intent.putExtra(Constantes.PARAM_OCORRENCIA, (Ocorrencia) objeto);
			intent.putExtra("visualizar", "visualizar");

			startActivity(intent);
		}

	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
}
