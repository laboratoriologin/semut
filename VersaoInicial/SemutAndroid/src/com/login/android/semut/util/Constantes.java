package com.login.android.semut.util;

import android.R.string;

public final class Constantes {

	private Constantes() {
	}

	public static final String KEY_SERVLET = "5Mu1tL0g1N";
	public static final String SERVICE = "SemutSalvadorIntentService";
	public static final String SHARED_PREFS = "prefs";
	public static final Long CAT_TRANSALVADOR = 1L;
	public static final Long CAT_SUCOM = 2L;
	public static final Long CAT_CONFIG = 3L;
	public static final Long CAT_TELEFONES_UTEIS = 4L;
	public static final String GRUPO_TRANSALVADOR = "1";
	public static final String GRUPO_SUCOM = "2";

	public static final String PARAM_GRUPO = "grupo";
	public static final String PARAM_OCORRENCIA = "ocorrencia";
	public static final String PARAM_IMAGEM = "imagem";

	public static final String TRANSALVADOR = "Registre Aqui";
	public static final String SEMUT = "SEMUT";
	public static final String SUCOM = "SUCOM";
	public static final String TELEFONES_UTEIS = "TELEFONES ÚTEIS";
	public static final String CONFIGURACOES = "CONFIGURAÇÕES";

	public static final String FALETRANSALVADOR = "Fale com a Transalvador";
	public static final String TELTRANSALVADOR = "tel:712109-3600";
	public static final String FALESUCOM = "Alô SUCOM";
	public static final String TELSUOCM = "tel:712201-6900";
	public static final String OCORRENCIA = "Registro de ocorrência";
	public static final String FALEPOLUICAO = "Poluição Sonora";
	public static final String TELPOLUICAOSONORA = "tel:712201-6660";
	public static final String TELSAMU = "tel:192";
	public static final String TELOUVIDORIA = "tel:156";
	public static final String TELBOMB = "tel:193";
	public static final String TELDEFESA = "tel:199";
	public static final String TELPOLICIA = "tel:190";
	public static final String CONFIRMACAO = "confirmacao";
	
	public static final String URL = "http://177.1.212.50:9004/SemutADMSSA/servlet";
	public static final String URL_IMG = "http://177.1.212.50:9004/arquivos_semut/";
	/*public static final String URL = "http://10.0.0.95:8080/SemutADMSSA/servlet";
	public static final String URL_IMG = "http://10.0.0.95:8080/arquivos_semut/";*/
	public static final String URL_NOTICIA = URL + "/NoticiaServlet";
	public static final String URL_TIPO_OCORRENCIA = URL + "/TipoOcorrenciaServlet";
	public static final String URL_OCORRENCIA = URL + "/OcorrenciaServlet";
	public static final String URL_INSERT_USUARIO = URL + "/set_usuario";
	public static final String URL_INSERT_OCORRENCIA = URL + "/insert_ocorrencia";
	public static final String URL_INSERT_TOKEN_ANDROID = URL + "/novo_token";
	public static final String URL_USUARIO = URL + "/UsuarioServlet";
	public static final String URL_LEMBRAR_SENHA = URL + "/LembrarSenhaServlet";
	
	public static final String MSG_ERRO_NET = "Verifique sua conexão com a internet!";
	public static final String MSG_ERRO_GRAVAR = "Erro no banco de dados, reinstale o app!";	
	
	public static final String INPUTSTREAM = "INPUTSTREAM";
	public static final String FILETYPE = "FILETYPE";
	public static final String FILENAME = "FILENAME";
	
	// Chave do projeto definido no Google APIs para uso do GCM
    public static final String SENDER_ID = "1088850896455";
     
    // Tag usada para o LogCat
    public static final String TAG = "gcm";
	public static final String PARAM_N_PROTOCOLO_OCORRENCIA = "nprotocoloocorrencia";
	
	public interface Flags {
		
		int TRANSALVADOR = 1;
		int SUCOM = 2;
		int OCORRENCIA = 3;
		int NOTICIA = 4;
		int AVISO = 5;
		int ACIDENTE = 6;
		int EDUCACAO = 7;
		int ISREG = 8;
		
	}
	
	public interface GridViewTransalvador {
		
		int REGISTRO_OCORRENCIA = 0;
		int ALERTA = 1;
		int NOTICIA= 2;
		int EDUCACAO = 3;
		int FALE_COM = 4;
		int MEUS_REGISTROS = 3;
		
	}
	
	public interface GridViewSucom {
		
		int REGISTRO_OCORRENCIA = 0;
		int QUADRO_AVISO = 1;
		int FALE_SUCOM= 2;
		int FALE_POLUICAO_SONORA = 3;
		int MEUS_REGISTROS = 4;
		
	}
	
	public interface TipoNoticia {
		
		String TRANSALVADOR = "2";
		String SUCOM = "4";
		String EDUCACAO = "5";		
	}

	public interface Status {
		
		String OCORRENCIA = "0";
		String ALERTA = "1";
	}
}