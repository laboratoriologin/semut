package com.login.android.semut;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.login.android.semut.business.UsuarioBS;
import com.login.android.semut.model.Grupo;
import com.login.android.semut.model.Usuario;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.util.Utilitarios;
import com.login.android.semut.view.ActionBar;

public class CadastroActivity extends DefaultActivity {

	private Usuario usuario;

	private final String ERRO_TENTE_NOVAMENTE_MAIS_TARDE = "Ocorreu um erro. Tente novamente mais tarde!";
	private final String DADOS_SALVOS_COM_SUCESSO = "Dados salvos com sucesso!";
	private Grupo grupo;
	private boolean confirmacaoOcorrencia;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_cadastro);

		grupo = new Grupo(Constantes.CAT_CONFIG);

		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		confirmacaoOcorrencia = getIntent().getBooleanExtra(Constantes.CONFIRMACAO, false);

		this.initColorAndTitleByGrupo(grupo);

		if (getUsuarioLogado() != null) {

			((EditText) findViewById(R.cadastro.nome)).setText(getUsuarioLogado().getNome());

			((EditText) findViewById(R.cadastro.email)).setText(getUsuarioLogado().getEmail());

			((EditText) findViewById(R.cadastro.telefone)).setText(getUsuarioLogado().getTelefone());

			((EditText) findViewById(R.cadastro.senha)).setText("");

			((EditText) findViewById(R.cadastro.confirmar_senha)).setText("");

		} else {

			findViewById(R.id.bt_deslogar).setVisibility(Button.GONE);

		}

	}

	public void onClickSalvar(View v) {

		if (validarCampos()) {

			usuario = new Usuario();

			usuario.setNome(((EditText) findViewById(R.cadastro.nome)).getText().toString());

			usuario.setTelefone(((EditText) findViewById(R.cadastro.telefone)).getText().toString());

			usuario.setEmail(((EditText) findViewById(R.cadastro.email)).getText().toString());

			usuario.setSenha(((EditText) findViewById(R.cadastro.senha)).getText().toString());

			usuario.setLogado(getUsuarioLogado() != null);

			if(Utilitarios.hasConnection(this)){
				new UsuarioBS(this).inserir(usuario);
			}else{
				Toast.makeText(this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
			}
			

			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Salvando dados...");
			progressDialog.show();

		}

	}

	public boolean validarCampos() {

		if (TextUtils.isEmpty(((EditText) findViewById(R.cadastro.nome)).getText()) || TextUtils.isEmpty(((EditText) findViewById(R.cadastro.email)).getText()) || TextUtils.isEmpty(((EditText) findViewById(R.cadastro.senha)).getText()) || TextUtils.isEmpty(((EditText) findViewById(R.cadastro.confirmar_senha)).getText())) {

			Toast.makeText(this, "Necessário preencher todos os campos!", Toast.LENGTH_SHORT).show();

			return false;
		}
		
		if (!isEmailValid(((EditText) findViewById(R.cadastro.email)).getText().toString())){
			
			Toast.makeText(this, "O e-mail fornecido está fora do padrão.", Toast.LENGTH_SHORT).show();

			return false;
		}

		if (!((EditText) findViewById(R.cadastro.senha)).getText().toString().equals(((EditText) findViewById(R.cadastro.confirmar_senha)).getText().toString())) {
			Toast.makeText(this, "Senhas não conferem!", Toast.LENGTH_SHORT).show();

			((EditText) findViewById(R.cadastro.senha)).setText("");
			((EditText) findViewById(R.cadastro.confirmar_senha)).setText("");

			return false;
		}

		return true;

	}
	
	public static boolean isEmailValid(String email) {
		Pattern patterns = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher matcher = patterns.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	public void onClickDeslogar(View v) {

		this.removerUsuarioLogado();

		findViewById(R.id.bt_deslogar).setVisibility(Button.GONE);

		((EditText) findViewById(R.cadastro.nome)).setText("");

		((EditText) findViewById(R.cadastro.email)).setText("");

		((EditText) findViewById(R.cadastro.telefone)).setText("");

		((EditText) findViewById(R.cadastro.senha)).setText("");

		((EditText) findViewById(R.cadastro.confirmar_senha)).setText("");

	}

	@Override
	public void getBusinessResult(Object result) {

		progressDialog.dismiss();

		if ("existe".equals(result)) {
			Toast.makeText(this, "Já existe usuário cadastrado com esse e-mail!", Toast.LENGTH_SHORT).show();
		} else if ("erro".equals(result)) {
			Toast.makeText(this, "Ocorreu um erro. Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
		} else if ("alterado".equals(result)) {
			Toast.makeText(this, "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();

			usuario.setId((String) result);

			this.replaceUsuarioLogado(usuario);

			Intent mainIntent = new Intent(CadastroActivity.this, HomeActivity.class);

			mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			CadastroActivity.this.startActivity(mainIntent);

			finish();
		} else {

			usuario.setId((String) result);

			this.replaceUsuarioLogado(usuario);

			findViewById(R.id.bt_deslogar).setVisibility(Button.VISIBLE);

			if (confirmacaoOcorrencia) {

				setResult(0, null);

				finish();

			} else {

				Intent mainIntent = new Intent(CadastroActivity.this, HomeActivity.class);

				mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				CadastroActivity.this.startActivity(mainIntent);

				finish();

			}

			Toast.makeText(getApplicationContext(), DADOS_SALVOS_COM_SUCESSO, Toast.LENGTH_SHORT).show();

		}

	}

}
