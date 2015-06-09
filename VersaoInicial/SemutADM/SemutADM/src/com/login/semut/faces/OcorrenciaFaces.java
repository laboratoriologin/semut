package com.login.semut.faces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.json.JSONException;
import org.primefaces.event.FileUploadEvent;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

import com.login.semut.dao.TokenAndroidDAO;
import com.login.semut.dao.TokenIphoneDAO;
import com.login.semut.model.CategoriaOcorrencia;
import com.login.semut.model.Ocorrencia;
import com.login.semut.model.TipoSituacao;
import com.login.semut.model.Token;
import com.login.semut.model.UsuarioAPP;
import com.login.semut.util.Constantes;
import com.login.semut.util.EnviaMensagem;
import com.login.semut.util.SemutUtil;

/**
 * @author anderson.carvalho
 * 
 */
@ViewScoped
@ManagedBean(name = "ocorrenciaFaces")
public class OcorrenciaFaces extends CrudFaces<Ocorrencia> {

	private List<SelectItem> categoriasOcorrencias;
	private List<SelectItem> tiposSituacao;
	private String caminhoImagem = "temp";
	private String nomeOriginalImagem;
	private String caminhoImagemSubstituida;
	private final int MAX_LARGURA = 1024;
	private final int MAX_ALTURA = 768;

	@PostConstruct
	protected void init() {

		this.clearFields();
		this.categoriasOcorrencias = super.initCombo(new CategoriaOcorrencia().findAll(), "id", "nomeCompleto");
		this.tiposSituacao = super.initCombo(new TipoSituacao().findAll(), "id", "descricao");
		this.getCrudPesquisaModel().setUsuario(new UsuarioAPP());
		if (!super.isFlagAlterar()) {
			this.getCrudModel().setStatus(true);
			this.getCrudModel().setTipoSituacao(new TipoSituacao(1L));
		}

		setFieldOrdem("data");

	}

	public void uploadFile(FileUploadEvent event) {

		nomeOriginalImagem = event.getFile().getFileName();

		if (!nomeOriginalImagem.equals(getCrudModel().getImagem())) {
			if (getCrudModel().getImagem() != null) {
				caminhoImagemSubstituida = getCrudModel().getImagem();
			}

			getCrudModel().setImagem(caminhoImagem);
			SemutUtil.criaArquivo(event.getFile(), Constantes.CAMINHO_ARQUIVO + caminhoImagem);

			if (!validarDimensaoMidia(caminhoImagem)) {

				SemutUtil.deletarArquivo(Constantes.CAMINHO_ARQUIVO + caminhoImagem);

				getCrudModel().setImagem(null);
			}
		}
	}

	private boolean validarDimensaoMidia(String caminho) {

		boolean valido = true;

		BufferedImage imagem = null;

		try {

			imagem = ImageIO.read(new File(Constantes.CAMINHO_ARQUIVO + caminho));

			if (!TSUtil.isEmpty(imagem)) {

				if (imagem.getWidth() > MAX_LARGURA || imagem.getHeight() > MAX_ALTURA) {

					this.addErrorMessage("Dimensão da imagem está muito grande, máximo de 1024px x 768 px!");

					valido = false;

				}
			}

		} catch (Exception ex) {

			throw new TSSystemException(ex);

		}

		return valido;

	}

	public void delMidia() {
		if (getCrudModel().getImagem().equals(caminhoImagem)) {
			SemutUtil.deletarArquivo(Constantes.CAMINHO_ARQUIVO + getCrudModel().getImagem());

		} else {
			caminhoImagemSubstituida = getCrudModel().getImagem();
		}

		getCrudModel().setImagem(null);
	}

	@Override
	public String limpar() {

		super.limpar();

		String paramOcorrencia = (String) TSFacesUtil.getObjectInSession(Constantes.PARAM_VER_OCORRENCIA);

		if (paramOcorrencia != null && TSUtil.isNumeric(paramOcorrencia)) {

			this.getCrudModel().setId(Long.valueOf(paramOcorrencia));

			this.detail();

			TSFacesUtil.removeObjectInSession(Constantes.PARAM_VER_OCORRENCIA);

		} else {

			getCrudModel().setCategoriaOcorrencia(new CategoriaOcorrencia());

			getCrudModel().setTipoSituacao(new TipoSituacao(1L));

			this.getCrudModel().setStatus(true);

		}

		return null;
	}

	@Override
	public String limparPesquisa() {

		super.limparPesquisa();

		getCrudPesquisaModel().setCategoriaOcorrencia(new CategoriaOcorrencia());

		getCrudPesquisaModel().setTipoSituacao(new TipoSituacao());

		getCrudPesquisaModel().setId(null);

		this.getCrudPesquisaModel().setUsuario(new UsuarioAPP());

		return null;
	}

	@Override
	protected boolean validaCampos() {

		boolean validado = true;

		if (TSUtil.isEmpty(getCrudModel().getLatitude()) || getCrudModel().getLatitude().equals(0D)) {
			addErrorMessage("Selecione uma posição no mapa.");
			validado = false;
		}

		if (!Constantes.SITUACAO_ACEITA.equals(this.getCrudModel().getTipoSituacao().getId()) && this.getCrudModel().getStatus()) {
			this.addErrorMessage("Antes de salvar uma ocorrênncia com o status de \"Alerta\" selecione a situação \"Aceita\".");
			validado = false;
		}

		return validado;
	}

	@Override
	protected void prePersist() {

		this.getCrudModel().setUsuario(new UsuarioAPP(1L));

		verificaProps();

	}

	public void verificaProps() {

		if (Constantes.SITUACAO_ACEITA.equals(this.getCrudModel().getTipoSituacao().getId())) {

			this.getCrudModel().setDataAceitacao(new Timestamp(System.currentTimeMillis()));

			if (this.getCrudModel().getStatus()) {

				this.getCrudModel().setDataAlerta(new Timestamp(System.currentTimeMillis()));

			}

		}

		if (Constantes.SITUACAO_RECUSADA.equals(this.getCrudModel().getTipoSituacao().getId())) {

			this.getCrudModel().setDataRecusa(new Timestamp(System.currentTimeMillis()));

		}

		if (Constantes.SITUACAO_PENDENTE.equals(this.getCrudModel().getTipoSituacao().getId())) {

			this.getCrudModel().setDataPendencia(new Timestamp(System.currentTimeMillis()));

		}

		if (Constantes.SITUACAO_RESOLVIDA.equals(this.getCrudModel().getTipoSituacao().getId())) {

			this.getCrudModel().setDataResolucao(new Timestamp(System.currentTimeMillis()));

		}

		if (this.getCrudModel().getImagem() != null && this.getCrudModel().getImagem().equals(caminhoImagem)) {

			File file = new File(Constantes.CAMINHO_ARQUIVO + caminhoImagem);

			file.renameTo(new File(Constantes.CAMINHO_ARQUIVO + nomeOriginalImagem));

			this.getCrudModel().setImagem(nomeOriginalImagem);

		}

		if (caminhoImagemSubstituida != null) {

			SemutUtil.deletarArquivo(Constantes.CAMINHO_ARQUIVO + caminhoImagemSubstituida);

		}

	}

	@Override
	protected String insert() throws TSApplicationException {

		this.getCrudModel().setData(new Timestamp(System.currentTimeMillis()));

		List<CategoriaOcorrencia> ListaCat = new CategoriaOcorrencia().findIdGrupo(this.getCrudModel().getCategoriaOcorrencia().getId());

		CategoriaOcorrencia cat = ListaCat.get(0);

		this.setClearFields(Boolean.FALSE);

		this.setDefaultMessage(Boolean.FALSE);

		this.categoriasOcorrencias = super.initCombo(new CategoriaOcorrencia().findAll(), "id", "nomeCompleto");
		this.tiposSituacao = super.initCombo(new TipoSituacao().findAll(), "id", "descricao");

		if (!validaCampos()) {
			return null;
		}

		if (Constantes.GRUPO_OCORRENCIA_TRANSALVADOR.equals(cat.getGrupoOcorrencia().getId()) && this.getCrudModel().getStatus()) {

			enviarPush();

		}

		this.prePersist();

		this.preInsert();

		this.getCrudModel().save();

		this.posPersist();

		this.detail();

		this.setDefaultMessage(Boolean.TRUE);

		return null;

	}

	@Override
	protected String update() throws TSApplicationException {

		super.setClearFields(false);

		this.setDefaultMessage(Boolean.FALSE);

		this.categoriasOcorrencias = super.initCombo(new CategoriaOcorrencia().findAll(), "id", "nomeCompleto");

		this.tiposSituacao = super.initCombo(new TipoSituacao().findAll(), "id", "descricao");

		if (!validaCampos()) {
			return null;
		}

		this.prePersist();

		this.preUpdate();

		this.getCrudModel().update();

		this.posPersist();

		this.setDefaultMessage(true);

		return null;
	}

	@Override
	protected String delete() throws TSApplicationException {

		super.detail();
		if (getCrudModel().getImagem() != null) {
			SemutUtil.deletarArquivo(Constantes.CAMINHO_ARQUIVO + getCrudModel().getImagem());
		}

		super.delete();

		return null;
	}

	public String enviarPush() {

		if (!Constantes.SITUACAO_ACEITA.equals(this.getCrudModel().getTipoSituacao().getId())) {

			this.addErrorMessage("Selecione a situação \"Aceita\" antes de enviar a mensagem para os usuários");

			return null;
		}

		if (this.getCrudModel().getCategoriaOcorrencia().getGrupoOcorrencia() != null && Constantes.GRUPO_OCORRENCIA_SUCOM.equals(this.getCrudModel().getCategoriaOcorrencia().getGrupoOcorrencia().getId())) {

			this.addErrorMessage("Não é permitido enviar alertas para ocorrências da Sucom");

			return null;
		}

		if (TSUtil.isEmpty(this.getCrudModel().getDescricao())) {

			this.addErrorMessage("Não é permitido enviar alertas com o campo descrição vazio");

			return null;

		}

		int pushsIphone = enviarPushIphone();

		int pushAndroid = enviarPushAndroid();

		this.addInfoMessage("Alerta enviado com sucesso! \nQuantidade de iPhones atingidos: " + pushsIphone + "\n" + "Quantidade de Androids atingidos: " + pushAndroid);

		try {

			verificaProps();

			this.getCrudModel().update();

		} catch (TSApplicationException e) {

			e.printStackTrace();

		}

		return null;

	}

	private int enviarPushIphone() {

		int retorno = 0;

		TokenIphoneDAO tokenIphoneDAO = new TokenIphoneDAO();

		List<Token> iphones = tokenIphoneDAO.pesquisar();

		PushNotificationPayload payload = PushNotificationPayload.complex();

		try {

			payload.addAlert(getCrudModel().getDescricao());

			payload.addSound("default");

			payload.addBadge(0);

			String[] tokens = new String[iphones.size()];

			for (int i = 0; i < iphones.size(); i++) {
				tokens[i] = iphones.get(i).getToken();
			}

			Token iphoneInvalido = new Token();

			String caminhoP12 = TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + "assets" + File.separator + Constantes.P12);

			List<PushedNotification> notifications = Push.payload(payload, caminhoP12, Constantes.CHAVE_P12, false, tokens);

			for (PushedNotification notification : notifications) {

				if (!notification.isSuccessful()) {

					iphoneInvalido.setToken(notification.getDevice().getToken());

					try {

						tokenIphoneDAO.excluir(iphoneInvalido);

					} catch (TSApplicationException ex) {
						ex.printStackTrace();
					}

				} else {
					retorno++;
				}

			}

		} catch (JSONException e) {

			e.printStackTrace();
		} catch (CommunicationException e) {

			e.printStackTrace();
		} catch (KeystoreException e) {

			e.printStackTrace();
		}

		return retorno;
	}

	private int enviarPushAndroid() {

		int retorno = 0;

		TokenAndroidDAO dao = new TokenAndroidDAO();
		List<Token> token = dao.pesquisar();
		EnviaMensagem em = new EnviaMensagem();

		int a = 1000;
		for (int i = 0; i < token.size(); i = i + a) {

			List<String> tokenString = new ArrayList<String>();

			if (token.size() >= (i + a)) {
				for (Token token2 : token.subList(i, i + a - 1)) {
					tokenString.add(token2.getToken());
				}
			} else {
				for (Token token2 : token) {
					tokenString.add(token2.getToken());
					retorno++;
				}
			}

			em.enviar(tokenString, getCrudModel().getDescricao());

		}

		return retorno;
	}

	public List<SelectItem> getCategoriasOcorrencias() {
		return categoriasOcorrencias;
	}

	public void setCategoriasOcorrencias(List<SelectItem> categoriasOcorrencias) {
		this.categoriasOcorrencias = categoriasOcorrencias;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public String getNomeOriginalImagem() {
		return nomeOriginalImagem;
	}

	public void setNomeOriginalImagem(String nomeOriginalImagem) {
		this.nomeOriginalImagem = nomeOriginalImagem;
	}

	public String getCaminhoImagemSubstituida() {
		return caminhoImagemSubstituida;
	}

	public void setCaminhoImagemSubstituida(String caminhoImagemSubstituida) {
		this.caminhoImagemSubstituida = caminhoImagemSubstituida;
	}

	public List<SelectItem> getTiposSituacao() {
		return tiposSituacao;
	}

	public void setTiposSituacao(List<SelectItem> tiposSituacao) {
		this.tiposSituacao = tiposSituacao;
	}

}
