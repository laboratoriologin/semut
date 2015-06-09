package com.login.android.semut;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.login.android.semut.adapter.GridItensSucomAdapter;
import com.login.android.semut.model.GridItem;
import com.login.android.semut.model.Grupo;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.view.ActionBar;

public class SucomActivity extends DefaultActivity implements OnTouchListener {

	public SucomActivity() {
		super();

	}

	public SucomActivity(boolean flagTipo) {
		super();
		this.flagTipo = false;
	}

	private GridView gridViewItens;
	private GridItensSucomAdapter gridItens;
	private ArrayList<GridItem> itens;
	private boolean onResume = false;
	private Grupo grupo;

	public boolean flagTipo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sucom);

		grupo = new Grupo(Constantes.CAT_SUCOM);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		this.initColorAndTitleByGrupo(grupo);

		popularItens();

		gridViewItens = (GridView) findViewById(R.sucom.gridview);
		gridViewItens.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				arg1.findViewById(R.sucom.square).setBackgroundColor(getResources().getColor(R.color.sucom_highlighted));

				if (arg2 == Constantes.GridViewSucom.FALE_SUCOM) {
					Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(Constantes.TELSUOCM));
					startActivity(callIntent);
				}

				else if (arg2 == Constantes.GridViewSucom.FALE_POLUICAO_SONORA) {
					Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(Constantes.TELPOLUICAOSONORA));
					startActivity(callIntent);
				} else if (arg2 == Constantes.GridViewSucom.REGISTRO_OCORRENCIA) {
					Intent mainIntent = new Intent(SucomActivity.this, OcorrenciaActivity.class);
					mainIntent.putExtra(Constantes.PARAM_GRUPO, new Grupo(Constantes.CAT_SUCOM));
					SucomActivity.this.startActivity(mainIntent);
				} else if (arg2 == Constantes.GridViewSucom.MEUS_REGISTROS) {
					Intent mainIntent = new Intent(SucomActivity.this, MeusRegistrosActivity.class);
					mainIntent.putExtra("id", Constantes.Flags.SUCOM);
					SucomActivity.this.startActivity(mainIntent);
				} else if (arg2 == Constantes.GridViewSucom.QUADRO_AVISO) {
					Intent mainIntent = new Intent(SucomActivity.this, NoticiaActivity.class);
					mainIntent.putExtra("idFlag", Constantes.Flags.AVISO);

					SucomActivity.this.startActivity(mainIntent);
				}

			}
		});

		gridItens = new GridItensSucomAdapter(this, itens);
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
		imagem2 = R.drawable.icon_btsucom_qua_aviso;
		item2.setImagem(imagem2);
		item2.setTextoItem("Quadro de Avisos");

		GridItem item3 = new GridItem();
		int imagem3;
		imagem3 = R.drawable.icon_bttrans_fale_trans;
		item3.setImagem(imagem3);
		item3.setTextoItem("Alô SUCOM");

		GridItem item4 = new GridItem();
		int imagem4;
		imagem4 = R.drawable.icon_bttrans_fale_trans;
		item4.setImagem(imagem4);
		item4.setTextoItem("Poluição Sonora");

		GridItem item5 = new GridItem();
		int imagem5;
		imagem5 = R.drawable.icon_bttrans_meus_regis;
		item5.setImagem(imagem5);
		item5.setTextoItem("Meus Registros");

		itens.add(item);
		itens.add(item2);
		itens.add(item3);
		itens.add(item4);
		itens.add(item5);
	}

	public void ligarSucom() {
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("2201-6900"));
		startActivity(callIntent);
	}

	public GridView getGridViewItens() {
		return gridViewItens;
	}

	public void setGridViewItens(GridView gridViewItens) {
		this.gridViewItens = gridViewItens;
	}

	public GridItensSucomAdapter getGridItens() {
		return gridItens;
	}

	public void setGridItens(GridItensSucomAdapter gridItens) {
		this.gridItens = gridItens;
	}

	public ArrayList<GridItem> getItens() {
		return itens;
	}

	public void setItens(ArrayList<GridItem> itens) {
		this.itens = itens;
	}
}
