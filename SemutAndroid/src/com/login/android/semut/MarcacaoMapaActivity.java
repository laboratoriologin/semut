package com.login.android.semut.lauro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.login.android.semut.lauro.model.Ocorrencia;
import com.login.android.semut.lauro.util.Constantes;
import com.login.android.semut.lauro.view.ActionBar;

public class MarcacaoMapaActivity extends DefaultActivity {

	private static final String SELECIONE_LOCAL = "Selecione o local";
	private static final float ZOOM_MAP = 13f;
	private GoogleMap googleMap;
	private LatLng posicaoSelecionada;
	private Ocorrencia ocorrencia;
	private String fileName;
	private String fileType;
	private Marker markerPosicaoAtual = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_marcacao_mapa);

		ocorrencia = (Ocorrencia) getIntent().getSerializableExtra(Constantes.PARAM_OCORRENCIA);
		fileName = (String) getIntent().getSerializableExtra(Constantes.FILENAME);
		fileType = (String) getIntent().getSerializableExtra(Constantes.FILETYPE);

		initColorAndTitleByGrupo(ocorrencia.getCategoria().getGrupo());

		((TextView) findViewById(R.id.titulo_action_bar)).setText(SELECIONE_LOCAL);

		Fragment fragment = getSupportFragmentManager().findFragmentById(R.marcacao_mapa.map);

		SupportMapFragment supportMapFragment = (SupportMapFragment) fragment;

		this.googleMap = supportMapFragment.getMap();

		this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(super.getLatitudeAtual(), super.getLongitudeAtual())));
		markerPosicaoAtual = this.googleMap.addMarker(new MarkerOptions()
		.position(new LatLng(super.getLatitudeAtual(), super
				.getLongitudeAtual())));

		this.googleMap.moveCamera(CameraUpdateFactory.zoomTo(ZOOM_MAP));
		
		posicaoSelecionada = markerPosicaoAtual.getPosition();

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(true);

		googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {

				googleMap.clear();

				googleMap.addMarker(new MarkerOptions().position(point));

				posicaoSelecionada = point;

			}
		});

	}

	public void continuar(View view) {

		if (posicaoSelecionada == null) {

			Toast.makeText(this, "Selecione uma posição no mapa", Toast.LENGTH_SHORT).show();

		} else {

			ocorrencia.setLatitude(posicaoSelecionada.latitude);

			ocorrencia.setLongitude(posicaoSelecionada.longitude);

			Intent intent = new Intent(this, ConfirmacaoOcorrenciaActivity.class);

			intent.putExtra(Constantes.PARAM_OCORRENCIA, ocorrencia);
			intent.putExtra(Constantes.FILETYPE, this.fileType);
			intent.putExtra(Constantes.FILENAME, this.fileName);

			startActivity(intent);

		}
	}

}
