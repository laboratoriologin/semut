package com.login.android.semut;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.login.android.semut.business.OcorrenciaBS;
import com.login.android.semut.business.UsuarioBS;
import com.login.android.semut.model.Grupo;
import com.login.android.semut.model.Ocorrencia;
import com.login.android.semut.model.Usuario;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.util.CryptoUtil;
import com.login.android.semut.util.Utilitarios;
import com.login.android.semut.view.ActionBar;

public class ConfirmacaoOcorrenciaActivity extends DefaultActivity {

	private Ocorrencia ocorrencia;
	private Bitmap fotoUpload;
	private Dialog dialog;
	private Dialog dialogLogin;
	private Dialog dialogLembrarSenha;
	private View dialogView;
	private View dialogViewLogin;
	private View dialogViewLembrarSenha;
	private boolean confirmacao;
	private String visualizar;
	private Usuario usuarioPesquisado;
	private Usuario usuarioRetornado = new Usuario();
	private ProgressDialog progressDialog;
	private byte[] imagem;
	private String fileName;
	private String fileType;
	private InputStream inputStream;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_confirmacao_ocorrencia);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		visualizar = (String) getIntent().getSerializableExtra("visualizar");

		ocorrencia = (Ocorrencia) getIntent().getSerializableExtra(Constantes.PARAM_OCORRENCIA);
		fileName = (String) getIntent().getSerializableExtra(Constantes.FILENAME);
		fileType = (String) getIntent().getSerializableExtra(Constantes.FILETYPE);

		Grupo grupo = new Grupo(ocorrencia.getGrupoId());

		if (ocorrencia.getImagem() != null) {
			fotoUpload = BitmapFactory.decodeByteArray(ocorrencia.getImagem(), 0, ocorrencia.getImagem().length);

			inputStream = new ByteArrayInputStream(ocorrencia.getImagem());

		}

		this.initColorAndTitleByGrupo(grupo);

		if (visualizar != null) {
			((Button) findViewById(R.confirmacao_ocorrencia.bt_concluir)).setVisibility(Button.GONE);
			((TextView) findViewById(R.confirmacao_ocorrencia.titulo)).setText("Ocorrência");
			((TextView) findViewById(R.confirmacao_ocorrencia.lb_protocolo)).setVisibility(View.VISIBLE);
			((TextView) findViewById(R.confirmacao_ocorrencia.protocolo)).setText(ocorrencia.getNumeroProtocolo());
			((TextView) findViewById(R.confirmacao_ocorrencia.protocolo)).setVisibility(View.VISIBLE);
		}

		this.initColorAndComponents();

		this.createDialog();
		this.createDialogLembrarSenha();

		if (getUsuarioLogado() == null) {

			dialogLogin.show();

		} else {

			((TextView) findViewById(R.confirmacao_ocorrencia.nome)).setText(getUsuarioLogado().getNome());

		}

	}

	private void initColorAndComponents() {

		((TextView) findViewById(R.confirmacao_ocorrencia.lb_data)).setTextColor(colorParsed);

		((TextView) findViewById(R.confirmacao_ocorrencia.lb_hora)).setTextColor(colorParsed);

		((TextView) findViewById(R.confirmacao_ocorrencia.lb_nome)).setTextColor(colorParsed);

		((TextView) findViewById(R.confirmacao_ocorrencia.lb_ocorrencia)).setTextColor(colorParsed);

		((TextView) findViewById(R.confirmacao_ocorrencia.lb_texto)).setTextColor(colorParsed);

		((TextView) findViewById(R.confirmacao_ocorrencia.lb_protocolo)).setTextColor(colorParsed);

		((TextView) findViewById(R.confirmacao_ocorrencia.titulo)).setTextColor(colorParsed);

		((TextView) findViewById(R.confirmacao_ocorrencia.data)).setText(SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT).format(ocorrencia.getData()));

		((TextView) findViewById(R.confirmacao_ocorrencia.hora)).setText(SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT).format(ocorrencia.getData()));

		((TextView) findViewById(R.confirmacao_ocorrencia.texto)).setText(ocorrencia.getDescricao());

		((TextView) findViewById(R.confirmacao_ocorrencia.ocorrencia)).setText(ocorrencia.getCategoria().getDescricao());

		if (fotoUpload != null) {

			((ImageView) findViewById(R.confirmacao_ocorrencia.imagem)).setImageBitmap(fotoUpload);

		}

	}

	public void concluir(View view) {
		if (getUsuarioLogado() == null) {

			dialogLogin.show();

		} else {

			dialogView.findViewById(R.dialog_confirmacao.bt_ok).setOnTouchListener(this);

			dialogView.findViewById(R.dialog_confirmacao.bt_ok).setBackgroundColor(colorParsed);

			ocorrencia.setUsuario(getUsuarioLogado());

			ocorrencia.setEmailUsuario(getUsuarioLogado().getEmail());

			ocorrencia.setCaminhoImagem("img");

			if(Utilitarios.hasConnection(this)){
				new OcorrenciaBS(this).inserir(ocorrencia, inputStream, fileType, fileName);
				progressDialog = new ProgressDialog(this);
				progressDialog.setMessage("Enviando...");
				progressDialog.show();
			}else{
				Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
			}
			


		}
	}

	public void dismissDialog(View view) {

		this.dialog.dismiss();

		Intent intent = new Intent(this, HomeActivity.class);

		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(intent);

		finish();

	}

	private void createDialogLembrarSenha() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		LayoutInflater inflater = this.getLayoutInflater();

		dialogView = inflater.inflate(R.layout.dialog_confirmacao, null);

		builder.setView(dialogView);

		this.dialog = builder.create();

		builder = new AlertDialog.Builder(this);

		dialogViewLembrarSenha = inflater.inflate(R.layout.dialog_lembrar_senha, null);

		builder.setView(dialogViewLembrarSenha);
		
		dialogViewLembrarSenha.findViewById(R.dialog_lembrar_senha.ll_bt_enviar).setBackgroundColor(colorParsed);

		dialogViewLembrarSenha.findViewById(R.dialog_lembrar_senha.bt_enviar).setBackgroundColor(colorParsed);
		dialogViewLembrarSenha.findViewById(R.dialog_lembrar_senha.bt_cancelar).setBackgroundColor(colorParsed);

		dialogViewLembrarSenha.findViewById(R.dialog_lembrar_senha.bt_cancelar).setOnTouchListener(this);

		this.dialogLembrarSenha = builder.create();

	}

	private void createDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		LayoutInflater inflater = this.getLayoutInflater();

		dialogView = inflater.inflate(R.layout.dialog_confirmacao, null);

		builder.setView(dialogView);

		this.dialog = builder.create();

		builder = new AlertDialog.Builder(this);

		dialogViewLogin = inflater.inflate(R.layout.dialog_login, null);

		builder.setView(dialogViewLogin);

		dialogViewLogin.findViewById(R.dialog_login.ll_btn_esqueci_senha).setBackgroundColor(colorParsed);

		dialogViewLogin.findViewById(R.dialog_login.bt_autenticar).setBackgroundColor(colorParsed);

		dialogViewLogin.findViewById(R.dialog_login.bt_cadastro).setBackgroundColor(colorParsed);

		dialogViewLogin.findViewById(R.dialog_login.bt_cancelar).setBackgroundColor(colorParsed);

		dialogViewLogin.findViewById(R.dialog_login.bt_esqueci_senha).setBackgroundColor(colorParsed);

		dialogViewLogin.findViewById(R.dialog_login.bt_autenticar).setOnTouchListener(this);

		dialogViewLogin.findViewById(R.dialog_login.bt_cadastro).setOnTouchListener(this);

		dialogViewLogin.findViewById(R.dialog_login.bt_cancelar).setOnTouchListener(this);

		dialogViewLogin.findViewById(R.dialog_login.bt_esqueci_senha).setOnTouchListener(this);

		this.dialogLogin = builder.create();

		this.dialogLogin.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {

				if (getUsuarioLogado() == null && !confirmacao) {

					// ConfirmacaoOcorrenciaActivity.this.cancelarLogin(null);
				}
			}
		});
	}

	public void enviarEmail(View view) {
		if (TextUtils.isEmpty(((TextView) dialogLembrarSenha.findViewById(R.dialog_lembrar_senha.username)).getText())) {
			Toast.makeText(ConfirmacaoOcorrenciaActivity.this, "Preencha o campo!", Toast.LENGTH_SHORT).show();
		} else {

			usuarioPesquisado = new Usuario();

			usuarioPesquisado.setEmail(((TextView) dialogLembrarSenha.findViewById(R.dialog_lembrar_senha.username)).getText().toString());

			if(Utilitarios.hasConnection(this)){
				new UsuarioBS(this).lembrarSenha(usuarioPesquisado);
			}else{
				Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
			}

			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Solicitando nova senha para o e-mail...");
			progressDialog.show();
		}
	}

	public void trocarSenha(View view) {

		this.dialogLogin.dismiss();

		this.dialogLembrarSenha.show();
	}

	public void autenticar(View view) {

		if (TextUtils.isEmpty(((TextView) dialogLogin.findViewById(R.id.username)).getText()) || TextUtils.isEmpty(((TextView) dialogLogin.findViewById(R.id.password)).getText())) {

			Toast.makeText(ConfirmacaoOcorrenciaActivity.this, "Preencha os dois campos!", Toast.LENGTH_SHORT).show();

		} else {

			usuarioPesquisado = new Usuario();

			usuarioPesquisado.setEmail(((TextView) dialogLogin.findViewById(R.id.username)).getText().toString());

			usuarioPesquisado.setSenha(((TextView) dialogLogin.findViewById(R.id.password)).getText().toString());

			if(Utilitarios.hasConnection(this)){
				new UsuarioBS(this).pesquisarPorEmail(usuarioPesquisado);
			}else{
				Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
			}

			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Autenticando...");
			progressDialog.show();

		}

	}

	public void criarCadastro(View view) {

		this.confirmacao = Boolean.TRUE;

		this.dialogLogin.dismiss();

		Intent intent = new Intent(this, CadastroActivity.class);

		intent.putExtra(Constantes.CONFIRMACAO, true);

		startActivityForResult(intent, 0);

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {

		super.onActivityResult(arg0, arg1, arg2);

		this.confirmacao = false;

		if (getUsuarioLogado() == null) {

			dialogLogin.show();

		} else {

			((TextView) findViewById(R.confirmacao_ocorrencia.nome)).setText(getUsuarioLogado().getNome());

		}

	}

	public void cancelarLogin(View view) {
		this.dialogLogin.dismiss();
		super.onBackPressed();
	}

	public void cancelarLembrarSenha(View view) {
		this.dialogLembrarSenha.dismiss();
		this.dialogLogin.show();
	}

	@Override
	public void getBusinessResult(Object result) {
		progressDialog.dismiss();

		if (result != null) {
			if (result instanceof Usuario) {
				usuarioRetornado = (Usuario) result;

				if (usuarioRetornado.getNome() == null) {

					usuarioPesquisado = new Usuario();

					Toast.makeText(this, "Usuário inválido!", Toast.LENGTH_SHORT).show();

				} else
					try {
						if (!usuarioRetornado.getSenha().equals(CryptoUtil.gerarHash(usuarioPesquisado.getSenha(), "MD5"))) {
							Toast.makeText(this, "Senha incorreta!", Toast.LENGTH_SHORT).show();
						} else {

							setUsuarioLogado(usuarioRetornado);

							((TextView) findViewById(R.confirmacao_ocorrencia.nome)).setText(getUsuarioLogado().getNome());

							ConfirmacaoOcorrenciaActivity.this.dialogLogin.cancel();

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} else if (result instanceof Boolean) {
				if ((Boolean) result) {
					Toast toast = Toast.makeText(this, "Sua senha foi alterada, você receberá uma nova senha pelo e-mail!", Toast.LENGTH_SHORT);
					toast.show();
				} else {
					Toast toast = Toast.makeText(this, "Erro. Tente novamente mais tarde!", Toast.LENGTH_SHORT);
					toast.show();
				}
			} else {
				if (((Integer) result) != 0) {
					try {

						ocorrencia.setNumeroProtocolo(((Integer) result).toString());

						super.getDataManager().getOcorrenciaDAO().save(ocorrencia);

						((TextView) dialogView.findViewById(R.dialog_confirmacao.nome)).setText(getUsuarioLogado().getNome() + ",");

						((TextView) dialogView.findViewById(R.dialog_confirmacao.chamado_sucesso)).setText(("seu chamado foi atendido com sucesso. Obrigado pela contribuição! O número do seu protocolo é " + result + "."));

						((TextView) dialogView.findViewById(R.dialog_confirmacao.nome)).setTextColor(colorParsed);

						((TextView) dialogView.findViewById(R.dialog_confirmacao.nome)).setText(getUsuarioLogado().getNome() + ",");

						((TextView) dialogView.findViewById(R.dialog_confirmacao.chamado_sucesso)).setText(("seu chamado foi atendido com sucesso. Obrigado pela contribuição! O número do seu protocolo é " + result + "."));

						((TextView) dialogView.findViewById(R.dialog_confirmacao.nome)).setTextColor(colorParsed);

						dialog.show();

					} catch (Exception e) {

						Toast toast = Toast.makeText(this, "Ocorreu um erro. Tente novamente mais tarde!", Toast.LENGTH_SHORT);

						toast.show();

						e.printStackTrace();

					}
				} else {
					Toast toast = Toast.makeText(this, "Ocorreu um erro. Tente novamente mais tarde!", Toast.LENGTH_SHORT);
					toast.show();

					Intent intent = new Intent(this, HomeActivity.class);

					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					startActivity(intent);

					finish();
				}
			}
		} else {
			Toast toast = Toast.makeText(this, "Ocorreu um erro. Tente novamente mais tarde!", Toast.LENGTH_SHORT);

			toast.show();
		}
	}
}
