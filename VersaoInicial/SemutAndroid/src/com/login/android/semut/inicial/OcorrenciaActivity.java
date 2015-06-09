package com.login.android.semut.inicial;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.login.android.semut.inicial.R;
import com.login.android.semut.model.CategoriaOcorrencia;
import com.login.android.semut.model.Grupo;
import com.login.android.semut.model.Ocorrencia;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.util.Utilitarios;
import com.login.android.semut.view.ActionBar;

public class OcorrenciaActivity extends DefaultActivity {

	private static final String CAPTURA_IMAGEM = "Captura imagem";
	private static final String CANCELAR = "Cancelar";
	private static final String ESCOLHER_DA_GALERIA = "Escolher da Galeria";
	private static final String NOVA_FOTO = "Nova Foto";
	private static final String TEMP_FOTO = "semuttempfoto.jpg";
	private static final String CONTENT = "content";
	private static final String FILE = "file";
	private Uri selectedImage;
	private InputStream inputStream;
	private String fileType;
	private String fileName;
	private final int selectCodeFoto = 0;
	private final int selectCodeGaleria = 1;
	private Bitmap fotoUpload;
	private ImageView imagemUpload;
	private Grupo grupo;
	private List<String> opcoesSpinner;
	private Spinner spinner;
	private List<CategoriaOcorrencia> listaCategoriasOcorrencias;
	private CategoriaOcorrencia categoriaSelecionada;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_ocorrencia);

		this.grupo = (Grupo) getIntent().getSerializableExtra(Constantes.PARAM_GRUPO);

		if (this.grupo == null) {

			this.grupo = new Grupo(Constantes.CAT_TRANSALVADOR);
			this.grupo.setDescricao(Constantes.TRANSALVADOR);

		} else {
			this.grupo.setDescricao(Constantes.SUCOM);
		}

		this.initColorAndTitleByGrupo(grupo);

		((TextView) findViewById(R.ocorrencia.titulo)).setTextColor(colorParsed);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		findViewById(R.id.imagem_action_bar).setBackgroundColor(Color.TRANSPARENT);

		findViewById(R.id.imagem_action_bar).setOnTouchListener(this);

		imagemUpload = (ImageView) findViewById(R.ocorrencia.imagem_upload);

		this.initSpinner();

	}

	private void initSpinner() {

		opcoesSpinner = new ArrayList<String>();

		listaCategoriasOcorrencias = getDataManager().getCategoriaOcorrenciaDAO().getAll();

		if (Constantes.CAT_TRANSALVADOR.equals(this.grupo.getId())) {

			for (CategoriaOcorrencia categoriaOcorrencia : listaCategoriasOcorrencias) {
				if (categoriaOcorrencia.getId_grupo().equals(Long.parseLong(Constantes.GRUPO_TRANSALVADOR))) {
					opcoesSpinner.add(categoriaOcorrencia.getDescricao());
				}
			}

		} else if (Constantes.CAT_SUCOM.equals(this.grupo.getId())) {
			for (CategoriaOcorrencia categoriaOcorrencia : listaCategoriasOcorrencias) {
				if (categoriaOcorrencia.getId_grupo().equals(Long.parseLong(Constantes.GRUPO_SUCOM))) {
					opcoesSpinner.add(categoriaOcorrencia.getDescricao());
				}
			}
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesSpinner);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinner = (Spinner) findViewById(R.ocorrencia.spinner);
		this.spinner.setAdapter(dataAdapter);

	}

	public void openFoto(View view) {

		final CharSequence[] options = { NOVA_FOTO, ESCOLHER_DA_GALERIA, CANCELAR };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(CAPTURA_IMAGEM);

		builder.setItems(options, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (NOVA_FOTO.equals(options[item])) {

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					File f = new File(android.os.Environment.getExternalStorageDirectory(), TEMP_FOTO);

					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

					startActivityForResult(intent, selectCodeFoto);

				}

				else if (ESCOLHER_DA_GALERIA.equals(options[item])) {

					Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, selectCodeGaleria);

				} else if (options[item].equals(CANCELAR)) {
					dialog.dismiss();
				}

			}

		});

		builder.show();

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		if (resultCode == RESULT_OK) {

			if (requestCode == selectCodeGaleria) {

				this.captureFotoGaleria(requestCode, resultCode, imageReturnedIntent);

			} else {
				this.captureFoto(requestCode, resultCode, imageReturnedIntent);
			}

			findViewById(R.ocorrencia.bt_girar_imagem).setVisibility(Button.VISIBLE);

			findViewById(R.ocorrencia.bt_remover_imagem).setVisibility(Button.VISIBLE);

		}

	}

	private void captureFoto(int requestCode, int resultCode, Intent imageReturnedIntent) {

		File f = new File(Environment.getExternalStorageDirectory().toString());

		for (File temp : f.listFiles()) {

			if (TEMP_FOTO.equals(temp.getName())) {

				f = temp;

				break;

			}

		}

		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

		fotoUpload = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

		fotoUpload = Bitmap.createScaledBitmap(fotoUpload, 625, 470, false);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		fotoUpload.compress(CompressFormat.JPEG, 60, bos);

		this.imagemUpload.setImageBitmap(fotoUpload);
		this.fileType = ".JPG";
		this.fileName = TEMP_FOTO;

	}

	private void captureFotoGaleria(int requestCode, int resultCode, Intent imageReturnedIntent) {

		selectedImage = imageReturnedIntent.getData();

		if (selectedImage != null) {

			try {

				inputStream = getContentResolver().openInputStream(selectedImage);

				ByteArrayOutputStream bos = new ByteArrayOutputStream();

				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

				Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);

				bitmap = Bitmap.createScaledBitmap(bitmap, 625, 470, false);

				bitmap.compress(CompressFormat.JPEG, 60, bos);

				byte[] bitmapdata = bos.toByteArray();

				inputStream = new ByteArrayInputStream(bitmapdata);

				ContentResolver cR = this.getContentResolver();

				fileType = cR.getType(selectedImage);

				String scheme = selectedImage.getScheme();

				if (FILE.equals(scheme)) {

					fileName = selectedImage.getLastPathSegment();

				} else if (CONTENT.equals(scheme)) {

					String[] proj = { MediaStore.Images.Media.TITLE };

					Cursor cursor = this.getContentResolver().query(selectedImage, proj, null, null, null);

					if (cursor != null && cursor.getCount() != 0) {

						int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);

						cursor.moveToFirst();

						fileName = cursor.getString(columnIndex);

					}
				}

				this.fotoUpload = bitmap;

				this.imagemUpload.setImageBitmap(bitmap);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

	}

	public void rotateImage(View view) {

		Matrix matrix = new Matrix();

		matrix.postRotate(90);

		fotoUpload = Bitmap.createBitmap(fotoUpload, 0, 0, fotoUpload.getWidth(), fotoUpload.getHeight(), matrix, true);

		imagemUpload.setImageBitmap(fotoUpload);
	}

	public void removeImage(View view) {

		this.imagemUpload.setImageBitmap(null);

		this.fotoUpload = null;

		findViewById(R.ocorrencia.bt_girar_imagem).setVisibility(Button.GONE);

		findViewById(R.ocorrencia.bt_remover_imagem).setVisibility(Button.GONE);

	}

	public CategoriaOcorrencia getCategoriaSelecionada() {

		for (CategoriaOcorrencia cat : listaCategoriasOcorrencias) {
			if (cat.getDescricao().equals(opcoesSpinner.get(spinner.getSelectedItemPosition())) && ((this.grupo.getDescricao() == Constantes.TRANSALVADOR && cat.getId_grupo() == Long.parseLong(Constantes.GRUPO_TRANSALVADOR)) || (this.grupo.getDescricao() == Constantes.SUCOM && cat.getId_grupo() == Long.parseLong(Constantes.GRUPO_SUCOM)))) {
				categoriaSelecionada = cat;
				return categoriaSelecionada;
			}
		}

		return categoriaSelecionada;
	}

	public void continuar(View view) {

		

			CheckBox chk = (CheckBox) findViewById(R.ocorrencia.checkbox);

			Ocorrencia ocorrencia = new Ocorrencia();

			CategoriaOcorrencia cat = getCategoriaSelecionada();

			ocorrencia.setCategoria(cat);

			ocorrencia.setCategoriaId(cat.getId());

			ocorrencia.setGrupoId(cat.getId_grupo());
			
			ocorrencia.getCategoria().setGrupo(new Grupo(cat.getId_grupo()));

			ocorrencia.getCategoria().setDescricao(cat.getDescricao());

			ocorrencia.setDescricao(((TextView) findViewById(R.ocorrencia.texto)).getText().toString());

			ocorrencia.setData(new Date());			

			if (this.fotoUpload != null) {

				try {

					OutputStream fOut = null;

					String path = Environment.getExternalStorageDirectory().toString();

					File file = new File(path, "oc" + DateFormat.format("ddMMyyyhhmmss", ocorrencia.getData()) + ".jpg");

					fOut = new FileOutputStream(file);

					fotoUpload.compress(Bitmap.CompressFormat.JPEG, 55, fOut);

					fOut.flush();

					fOut.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				ByteArrayOutputStream bos = new ByteArrayOutputStream();

				this.fotoUpload.compress(CompressFormat.JPEG, 55, bos);

				ocorrencia.setImagem(bos.toByteArray());

			}

			if (chk.isChecked()) {

				if (super.getLatitudeAtual() == null) {

					Toast.makeText(this, "Não foi possível obter sua localização atual, selecione no mapa!", Toast.LENGTH_LONG).show();

					startMarcacaoMapa(ocorrencia);

				} else {

					startConfirmacao(ocorrencia);

				}

			} else {

				startMarcacaoMapa(ocorrencia);

			}
		
			if (!Utilitarios.hasConnection(this)) 
				Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();

	}

	private void startConfirmacao(Ocorrencia ocorrencia) {

		ocorrencia.setLatitude(getLatitudeAtual());

		ocorrencia.setLongitude(getLongitudeAtual());

		ocorrencia.setCaminhoImagem("img");

		Intent intent = new Intent(this, ConfirmacaoOcorrenciaActivity.class);

		intent.putExtra(Constantes.PARAM_OCORRENCIA, ocorrencia);
		intent.putExtra(Constantes.FILETYPE, this.fileType);
		intent.putExtra(Constantes.FILENAME, this.fileName);

		startActivity(intent);

	}

	private void startMarcacaoMapa(Ocorrencia ocorrencia) {

		Intent intent = new Intent(this, MarcacaoMapaActivity.class);

		intent.putExtra(Constantes.PARAM_OCORRENCIA, ocorrencia);
		intent.putExtra(Constantes.FILETYPE, this.fileType);
		intent.putExtra(Constantes.FILENAME, this.fileName);

		startActivity(intent);

	}

}
