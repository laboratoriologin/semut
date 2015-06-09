package com.login.android.semut.lauro;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.login.android.semut.lauro.model.CategoriaOcorrencia;
import com.login.android.semut.lauro.model.Grupo;
import com.login.android.semut.lauro.model.Noticia;
import com.login.android.semut.lauro.model.Ocorrencia;
import com.login.android.semut.lauro.util.Constantes;
import com.login.android.semut.lauro.util.DrawableManager;
import com.login.android.semut.lauro.view.ActionBar;

public class DetalheNoticiaActivity extends DefaultActivity implements OnTouchListener {

	private Grupo grupo;
	private int idFlag;
	private int fromReg;
	private Noticia noticia;
	private Noticia educacao;
	private Ocorrencia ocorrencia;
	private List<CategoriaOcorrencia> lstCategoriaOcorrencia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_noticia);

		ocorrencia = (Ocorrencia) getIntent().getSerializableExtra("ocorrencia");

		noticia = (Noticia) getIntent().getSerializableExtra("noticia");

		educacao = (Noticia) getIntent().getSerializableExtra("educacao");

		final ImageView expadendView = (ImageView) findViewById(R.id.expanded_image);
		final ImageView thumbView = (ImageView) findViewById(R.noticiaDetalhe.imgNoticiaDetalhe);

		thumbView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				Drawable img = null;

				expadendView.setVisibility(View.VISIBLE);

				if (noticia != null && !noticia.getImagem().equals("")) {

					img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + noticia.getImagem());

				} else if (educacao != null && !educacao.getImagem().equals("")) {

					img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + educacao.getImagem());

				} else if (ocorrencia != null && !ocorrencia.getCaminhoImagem().equals("")) {

					img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + ocorrencia.getCaminhoImagem());

				}
				
				expadendView.setImageDrawable(img);
			}
		});

		expadendView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				expadendView.setVisibility(View.GONE);
			}
		});

		idFlag = getIntent().getIntExtra("idFlag", 0);
		fromReg = getIntent().getIntExtra("fromReg", 0);

		if (idFlag == Constantes.Flags.NOTICIA) {

			grupo = new Grupo(Constantes.CAT_TRANSALVADOR);

			this.initColorAndTitleByGrupo(grupo);

			((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

			if ((super.getDataManager().getNoticiaDAO().get(noticia.getId()) != null)) {
				Button button = (Button) findViewById(R.noticiaDetalhe.salvar_button);
				button.setVisibility(Button.GONE);
				Button button3 = (Button) findViewById(R.noticiaDetalhe.excluir_button);
				button3.setVisibility(Button.VISIBLE);
			}

			TextView txvChamada = (TextView) findViewById(R.noticiaDetalhe.titulo);
			txvChamada.setText(noticia.getTitulo());

			TextView txvDescricao = (TextView) findViewById(R.noticiaDetalhe.descricao);
			txvDescricao.setText(noticia.getDescricao());

			TextView txvDataPublicacao = (TextView) findViewById(R.noticiaDetalhe.data_publicacao);
			txvDataPublicacao.setText(DateFormat.format("dd/MM/yyyy kk:mm", noticia.getData_publicacao()));

			ImageView imgDataPublicacao = (ImageView) findViewById(R.noticiaDetalhe.imgNoticiaDetalhe);

			if (!noticia.getImagem().equals("")) {

				Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + noticia.getImagem());

				imgDataPublicacao.setImageDrawable(img);
			} else {
				imgDataPublicacao.setVisibility(View.GONE);
			}
		}

		else if (idFlag == Constantes.Flags.AVISO && ocorrencia == null) {

			grupo = new Grupo(Constantes.CAT_SUCOM);

			this.initColorAndTitleByGrupo(grupo);

			((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

			TextView txvChamada = (TextView) findViewById(R.noticiaDetalhe.titulo);
			txvChamada.setText(noticia.getTitulo());

			txvChamada.setTextColor(getResources().getColor(R.color.sucom));

			TextView txvDescricao = (TextView) findViewById(R.noticiaDetalhe.descricao);
			txvDescricao.setText(noticia.getDescricao());

			TextView txvDataPublicacao = (TextView) findViewById(R.noticiaDetalhe.data_publicacao);
			txvDataPublicacao.setText(DateFormat.format("dd/MM/yyyy kk:mm", noticia.getData_publicacao()));

			Button button = (Button) findViewById(R.noticiaDetalhe.compartilhar_button);
			button.setBackgroundResource(R.drawable.bt_compartilhar_sucom);

			Button button2 = (Button) findViewById(R.noticiaDetalhe.salvar_button);
			button2.setBackgroundResource(R.drawable.bt_salvar_sucom);

			if ((super.getDataManager().getNoticiaDAO().get(noticia.getId()) != null)) {
				button.setVisibility(Button.GONE);
				button2.setVisibility(Button.GONE);
				Button button3 = (Button) findViewById(R.noticiaDetalhe.excluir_button);
				button3.setBackgroundResource(R.drawable.bt_excluir_sucom);
				button3.setVisibility(Button.VISIBLE);
			}

			ImageView imgDataPublicacao2 = (ImageView) findViewById(R.noticiaDetalhe.imgNoticiaDetalhe);

			if (!noticia.getImagem().equals("")) {

				Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + noticia.getImagem());

				imgDataPublicacao2.setImageDrawable(img);
			} else {
				imgDataPublicacao2.setVisibility(View.GONE);
			}

		} else if (ocorrencia != null) {

			this.lstCategoriaOcorrencia = getDataManager().getCategoriaOcorrenciaDAO().getAll();
			grupo = new Grupo(Constantes.CAT_TRANSALVADOR);

			this.initColorAndTitleByGrupo(grupo);

			((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

			TextView txvChamada = (TextView) findViewById(R.noticiaDetalhe.titulo);
			for (CategoriaOcorrencia categoriaOcorrencia : lstCategoriaOcorrencia) {
				if (ocorrencia.getCategoriaId().equals(categoriaOcorrencia.getId())) {
					txvChamada.setText(categoriaOcorrencia.getDescricao());
				}
			}

			TextView txvDescricao = (TextView) findViewById(R.noticiaDetalhe.descricao);
			txvDescricao.setText(ocorrencia.getDescricao());

			TextView txvDataPublicacao = (TextView) findViewById(R.noticiaDetalhe.data_publicacao);
			txvDataPublicacao.setText(DateFormat.format("dd/MM/yyyy kk:mm", ocorrencia.getData()));

			ImageView imgDataPublicacao = (ImageView) findViewById(R.noticiaDetalhe.imgNoticiaDetalhe);

			if (!ocorrencia.getCaminhoImagem().equals("")) {

				Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + ocorrencia.getCaminhoImagem());

				imgDataPublicacao.setImageDrawable(img);
			} else {
				imgDataPublicacao.setVisibility(View.GONE);
			}

			Button button = (Button) findViewById(R.noticiaDetalhe.salvar_button);
			button.setVisibility(Button.GONE);
			Button button2 = (Button) findViewById(R.noticiaDetalhe.compartilhar_button);
			button2.setVisibility(Button.GONE);

		} else if (idFlag == Constantes.Flags.EDUCACAO) {

			grupo = new Grupo(Constantes.CAT_TRANSALVADOR);

			this.initColorAndTitleByGrupo(grupo);

			((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

			TextView txvChamada = (TextView) findViewById(R.noticiaDetalhe.titulo);
			txvChamada.setText(educacao.getTitulo());

			TextView txvDescricao = (TextView) findViewById(R.noticiaDetalhe.descricao);
			txvDescricao.setText(educacao.getDescricao());

			TextView txvDataPublicacao = (TextView) findViewById(R.noticiaDetalhe.data_publicacao);
			txvDataPublicacao.setText(DateFormat.format("dd/MM/yyyy kk:mm", educacao.getData_publicacao()));

			ImageView imgDataPublicacao = (ImageView) findViewById(R.noticiaDetalhe.imgNoticiaDetalhe);

			if (!educacao.getImagem().equals("")) {

				Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + educacao.getImagem());

				imgDataPublicacao.setImageDrawable(img);
			} else {
				imgDataPublicacao.setVisibility(View.GONE);
			}

			Button button = (Button) findViewById(R.noticiaDetalhe.salvar_button);
			Button button2 = (Button) findViewById(R.noticiaDetalhe.compartilhar_button);

			if ((super.getDataManager().getNoticiaDAO().get(educacao.getId()) != null)) {
				button.setVisibility(Button.GONE);
				button2.setVisibility(Button.GONE);
				Button button3 = (Button) findViewById(R.noticiaDetalhe.excluir_button);
				button3.setVisibility(Button.VISIBLE);
			}
		}
	}

	public void setExpandedView() {

	}

	public void salvar(View view) {

		if (noticia != null) {
			if (super.getDataManager().getNoticiaDAO().get(noticia.getId()) == null) {
				try {
					super.getDataManager().getNoticiaDAO().save(noticia);
					Toast toast = Toast.makeText(this, "Noticia arquivada com sucesso!", Toast.LENGTH_SHORT);
					toast.show();

				} catch (Exception e) {
					Toast toast = Toast.makeText(this, "Ocorreu um erro. Tente novamente mais tarde!", Toast.LENGTH_SHORT);
					toast.show();
					e.printStackTrace();
				}
			} else {
				Toast toast = Toast.makeText(this, "Noticia já foi arquivada!", Toast.LENGTH_SHORT);
				toast.show();
			}
		} else if (educacao != null) {
			if (super.getDataManager().getNoticiaDAO().get(educacao.getId()) == null) {
				try {
					super.getDataManager().getNoticiaDAO().save(educacao);
					Toast toast = Toast.makeText(this, "Dica de educação arquivada com sucesso!", Toast.LENGTH_SHORT);
					toast.show();

				} catch (Exception e) {
					Toast toast = Toast.makeText(this, "Ocorreu um erro. Tente novamente mais tarde!", Toast.LENGTH_SHORT);
					toast.show();
					e.printStackTrace();
				}
			} else {
				Toast toast = Toast.makeText(this, "Dica de educação já foi arquivada!", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}

	public void excluir(View view) {
		if (noticia != null) {
			if (super.getDataManager().getNoticiaDAO().get(noticia.getId()) != null) {
				try {
					super.getDataManager().getNoticiaDAO().delete(noticia.getId());
					Toast toast = Toast.makeText(this, "Notícia excluída com sucesso!", Toast.LENGTH_SHORT);
					toast.show();
				} catch (Exception e) {
					Toast toast = Toast.makeText(this, "Essa notícia já foi excluída!", Toast.LENGTH_SHORT);
					toast.show();
					e.printStackTrace();
				}
			}
		} else if (educacao != null) {
			if (super.getDataManager().getNoticiaDAO().get(educacao.getId()) != null) {
				try {
					super.getDataManager().getNoticiaDAO().delete(educacao.getId());
					Toast toast = Toast.makeText(this, "Dica de educação excluída com sucesso!", Toast.LENGTH_SHORT);
					toast.show();
				} catch (Exception e) {
					Toast toast = Toast.makeText(this, "Essa dica de educação já foi excluída!", Toast.LENGTH_SHORT);
					toast.show();
					e.printStackTrace();
				}
			}
		}

		if (fromReg == 8) {
			super.onBackPressed();
		}
	}

	public void compartilhar(View view) {
		Noticia noticia = (Noticia) getIntent().getSerializableExtra("noticia");

		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");

		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, noticia.getTitulo() + "\n\n" + noticia.getDescricao());

		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SEMUT");
		startActivity(Intent.createChooser(sharingIntent, "Compartilhar:"));
	}

}