package com.login.android.semut;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.login.android.semut.adapter.GridItensTransalvadorAdapter;
import com.login.android.semut.model.GridItem;
import com.login.android.semut.model.Grupo;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.view.ActionBar;

public class TransalvadorActivity extends DefaultActivity implements OnTouchListener {

	private GridView gridViewItens;
	private GridItensTransalvadorAdapter gridItens;
	private ArrayList<GridItem> itens;
	private boolean onResume = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transalvador);

		Grupo grupo = (Grupo) getIntent().getSerializableExtra(Constantes.PARAM_GRUPO);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		this.initColorAndTitleByGrupo(grupo);

		popularItens();

		gridViewItens = (GridView) findViewById(R.transalvador.gridview);
		gridViewItens.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicaoClicada, long arg3) {

				arg1.findViewById(R.transalvador.square).setBackgroundColor(getResources().getColor(R.color.transalvador_highlighted));

				if (posicaoClicada == Constantes.GridViewTransalvador.REGISTRO_OCORRENCIA) {

					Intent mainIntent = new Intent(TransalvadorActivity.this, OcorrenciaActivity.class);
					TransalvadorActivity.this.startActivity(mainIntent);

				} else if (posicaoClicada == Constantes.GridViewTransalvador.NOTICIA) {

					Intent mainIntent = new Intent(TransalvadorActivity.this, NoticiaActivity.class);
					mainIntent.putExtra("idFlag", Constantes.Flags.TRANSALVADOR);
					TransalvadorActivity.this.startActivity(mainIntent);

				} else if (posicaoClicada == Constantes.GridViewTransalvador.MEUS_REGISTROS) {

					Intent mainIntent = new Intent(TransalvadorActivity.this, MeusRegistrosActivity.class);
					mainIntent.putExtra("id", Constantes.Flags.TRANSALVADOR);
					TransalvadorActivity.this.startActivity(mainIntent);

				} else if (posicaoClicada == Constantes.GridViewTransalvador.ALERTA) {

					Intent mainIntent = new Intent(TransalvadorActivity.this, AlertasActivity.class);
					TransalvadorActivity.this.startActivity(mainIntent);

				}
			}
		});

		gridItens = new GridItensTransalvadorAdapter(this, itens);
		gridViewItens.setAdapter(gridItens);
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

	public void popularItens() {
		itens = new ArrayList<GridItem>();

		GridItem item = new GridItem();
		int imagem;
		imagem = R.drawable.icon_bttrans_ocor;
		item.setImagem(imagem);

		item.setTextoItem("Registro de ocorrência");

		GridItem item2 = new GridItem();
		int imagem2;
		imagem2 = R.drawable.icon_bttrans_alertas;
		item2.setImagem(imagem2);
		item2.setTextoItem("Alertas");

		GridItem item3 = new GridItem();
		int imagem3;
		imagem3 = R.drawable.icon_bttrans_noticias;
		item3.setImagem(imagem3);
		item3.setTextoItem("Notícias");

		GridItem item4 = new GridItem();
		int imagem4 = R.drawable.icon_bttrans_meus_regis;
		item4.setImagem(imagem4);
		item4.setTextoItem("Meus Registros");

		itens.add(item);
		itens.add(item2);
		itens.add(item3);
		itens.add(item4);
	}

	public GridView getGridViewItens() {
		return gridViewItens;
	}

	public void setGridViewItens(GridView gridViewItens) {
		this.gridViewItens = gridViewItens;
	}

	public GridItensTransalvadorAdapter getGridItens() {
		return gridItens;
	}

	public void setGridItens(GridItensTransalvadorAdapter gridItens) {
		this.gridItens = gridItens;
	}

	public ArrayList<GridItem> getItens() {
		return itens;
	}

	public void setItens(ArrayList<GridItem> itens) {
		this.itens = itens;
	}
}
