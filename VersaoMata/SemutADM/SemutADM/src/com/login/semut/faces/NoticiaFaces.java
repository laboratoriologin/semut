package com.login.semut.faces;

import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.primefaces.event.FileUploadEvent;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;

import com.login.semut.model.Noticia;
import com.login.semut.model.TipoNoticia;
import com.login.semut.util.Constantes;
import com.login.semut.model.UsuarioAPP;
import com.login.semut.util.SemutUtil;

@ViewScoped
@ManagedBean(name = "noticiaFaces")
public class NoticiaFaces extends CrudFaces<Noticia> {

	private List<SelectItem> tiposNoticia;
	private String caminhoImagem = "temp";
	private String nomeOriginalImagem;
	private String caminhoImagemSubstituida;
	private final int MAX_LARGURA = 1024;
	private final int MAX_ALTURA = 768;

	@PostConstruct
	protected void init() {
		this.clearFields();
		
		this.tiposNoticia = super.initCombo(new TipoNoticia().findAll("nome"),
				"id", "nome");
		getCrudModel().setTipoNoticia(new TipoNoticia());
		setFieldOrdem("descricao");
	}

	public String limpar() {
		super.limpar();
		getCrudModel().setTipoNoticia(new TipoNoticia());
		return null;
	}

	public String limparPesquisa() {
		super.limparPesquisa();
		getCrudPesquisaModel().setTipoNoticia(new TipoNoticia());
		return null;
	}

	public void uploadFile(FileUploadEvent event) {

		nomeOriginalImagem = event.getFile().getFileName();

		if (!nomeOriginalImagem.equals(getCrudModel().getImagem())) {
			if (getCrudModel().getImagem() != null) {
				caminhoImagemSubstituida = getCrudModel().getImagem();
			}

			getCrudModel().setImagem(caminhoImagem);
			SemutUtil.criaArquivo(event.getFile(), Constantes.CAMINHO_ARQUIVO
					+ caminhoImagem);

			if (!validarDimensaoMidia(caminhoImagem)) {

				SemutUtil.deletarArquivo(Constantes.CAMINHO_ARQUIVO
						+ caminhoImagem);
				
				getCrudModel().setImagem(null);
			}
		}

	}

	private boolean validarDimensaoMidia(String caminho) {

		boolean valido = true;

		BufferedImage imagem = null;

		try {

			imagem = ImageIO
					.read(new File(Constantes.CAMINHO_ARQUIVO + caminho));

			if (!TSUtil.isEmpty(imagem)) {

				if (imagem.getWidth() > MAX_LARGURA
						|| imagem.getHeight() > MAX_ALTURA) {

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
		if (getCrudModel().getImagem() == caminhoImagem) {
			SemutUtil.deletarArquivo(Constantes.CAMINHO_ARQUIVO
					+ getCrudModel().getImagem());

		} else {
			caminhoImagemSubstituida = getCrudModel().getImagem();
		}

		getCrudModel().setImagem(null);
	}

	@Override
	protected void prePersist() {

		this.getCrudModel().setData(new Timestamp(System.currentTimeMillis()));

		if (this.getCrudModel().getImagem() != null
				&& this.getCrudModel().getImagem() == caminhoImagem) {

			File file = new File(Constantes.CAMINHO_ARQUIVO + caminhoImagem);
			file.renameTo(new File(Constantes.CAMINHO_ARQUIVO
					+ nomeOriginalImagem));
			this.getCrudModel().setImagem(nomeOriginalImagem);
		}
		if (caminhoImagemSubstituida != null) {
			SemutUtil.deletarArquivo(Constantes.CAMINHO_ARQUIVO
					+ caminhoImagemSubstituida);
		}
	}

	public List<SelectItem> getSelectItems() {
		return tiposNoticia;
	}

	public void setGrupos(List<SelectItem> tipoNoticia) {
		this.tiposNoticia = tipoNoticia;
	}

	public List<SelectItem> getTiposNoticia() {
		return tiposNoticia;
	}

	public void setTiposNoticia(List<SelectItem> tipoNoticia) {
		this.tiposNoticia = tipoNoticia;
	}
}
