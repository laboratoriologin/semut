package com.login.android.semut.lauro;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.login.android.semut.lauro.adapter.GridItensTransalvadorAdapter;
import com.login.android.semut.lauro.business.CategoriaOcorrenciaBS;
import com.login.android.semut.lauro.model.CategoriaOcorrencia;
import com.login.android.semut.lauro.model.GridItem;
import com.login.android.semut.lauro.model.Grupo;
import com.login.android.semut.lauro.util.Constantes;
import com.login.android.semut.lauro.util.Utilitarios;
import com.login.android.semut.lauro.view.ActionBar;

public class TransalvadorActivity extends DefaultActivity implements OnTouchListener {

	private GridView gridViewItens;
	private GridItensTransalvadorAdapter gridItens;
	private ArrayList<GridItem> itens;
	private boolean onResume = false;
	private ProgressDialog progressDialog;

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

				if (posicaoClicada == Constantes.GridViewTransalvador.REGISTRO_OCORRENCIA) {

					preecherCategoriasOcorrencias(Constantes.GRUPO_TRANSALVADOR);

				} else if (posicaoClicada == Constantes.GridViewTransalvador.NOTICIA) {

					Intent mainIntent = new Intent(TransalvadorActivity.this, NoticiaActivity.class);

					mainIntent.putExtra(Constantes.ID_FLAG, Constantes.Flags.TRANSALVADOR);

					startActivity(mainIntent);

				} else if (posicaoClicada == Constantes.GridViewTransalvador.MEUS_REGISTROS) {

					Intent mainIntent = new Intent(TransalvadorActivity.this, MeusRegistrosActivity.class);

					mainIntent.putExtra(Constantes.ID, Constantes.Flags.TRANSALVADOR);

					startActivity(mainIntent);

				} else if (posicaoClicada == Constantes.GridViewTransalvador.ALERTA) {

					Intent mainIntent = new Intent(TransalvadorActivity.this, AlertasActivity.class);

					startActivity(mainIntent);

				} else if (posicaoClicada == Constantes.GridViewTransalvador.TIRE_DUVIDAS) {

					Intent mainIntent = new Intent(TransalvadorActivity.this, TirarDuvidaActivity.class);

					startActivity(mainIntent);

				}
			}
		});

		gridItens = new GridItensTransalvadorAdapter(this, itens);

		gridViewItens.setAdapter(gridItens);

	}

	public void popularItens() {

		itens = new ArrayList<GridItem>();

		GridItem item = new GridItem();

		item.setImagem(R.drawable.icon_bttrans_ocor);

		item.setTextoItem("Registro de ocorrência");

		GridItem item2 = new GridItem();

		item2.setImagem(R.drawable.icon_bttrans_alertas);

		item2.setTextoItem("Alertas");

		GridItem item3 = new GridItem();
		item3.setImagem(R.drawable.ic_bttrans_prefeitura_informa);
		item3.setTextoItem("Prefeitura informa");

		GridItem item4 = new GridItem();
		item4.setImagem(R.drawable.ic_bttrans_duvidas);
		item4.setTextoItem("Tire suas dúvidas");

		GridItem item5 = new GridItem();
		item5.setImagem(R.drawable.icon_bttrans_meus_regis);
		item5.setTextoItem("Meus Registros");

		itens.add(item);
		itens.add(item2);
		itens.add(item3);
		itens.add(item4);
		itens.add(item5);

	}

	public void preecherCategoriasOcorrencias(String grupo) {

		new CategoriaOcorrenciaBS(this).pesquisar(grupo);

		progressDialog = new ProgressDialog(this);

		progressDialog.setMessage(Constantes.BUSCANDO_INFORMACOES);

		progressDialog.show();

		progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

		if (!Utilitarios.hasConnection(this)) {
			Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void getBusinessResult(Object result) {

		this.progressDialog.dismiss();

		if (result != null && !(result instanceof String) && ((ArrayList<Object>) result).size() != 0) {
	
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
		} else if (getDataManager().getCategoriaOcorrenciaDAO().getAllbyClause(" ID_GRUPO = ?", new String[] { Constantes.GRUPO_TRANSALVADOR }, null, null, null).size() == 0) {
			Toast toast = Toast.makeText(this, "Não foi possível carregar as informações.", Toast.LENGTH_SHORT);
			toast.show();
		}

		if (getDataManager().getCategoriaOcorrenciaDAO().getAllbyClause(" ID_GRUPO = ?", new String[] { Constantes.GRUPO_TRANSALVADOR }, null, null, null).size() > 0) {
			Intent mainIntent = new Intent(TransalvadorActivity.this, OcorrenciaActivity.class);
			TransalvadorActivity.this.startActivity(mainIntent);
		}
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
}
