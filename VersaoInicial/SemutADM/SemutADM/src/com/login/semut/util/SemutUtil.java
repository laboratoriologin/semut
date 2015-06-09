package com.login.semut.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;

public final class SemutUtil {

	private SemutUtil() {

	}

	public static String[] getVetor(String... parameters) {

		String[] vetor = null;

		if (!TSUtil.isEmpty(parameters)) {

			vetor = new String[parameters.length];
			int i = 0;

			for (String str : parameters) {

				vetor[i] = str;
				i++;

			}

		}

		return vetor;

	}

	public static void addErrorMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", msg));
	}

	public static void addInfoMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", msg));
	}

	public static void addWarnMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", msg));
	}

	public static String tratarString(String str) {
		return TSUtil.isEmpty(str) ? null : "%" + str.toLowerCase() + "%";
	}

	public static void criaArquivo(UploadedFile file, String arquivo) {
		try {
			criaArquivo(file.getInputstream(), arquivo);
		} catch (IOException e) {
			throw new TSSystemException(e);
		}
	}

	public static void criaArquivo(InputStream file, String arquivo) {

		try {
			FileUtils.copyInputStreamToFile(file, new File(arquivo));
		} catch (Exception ex) {
			throw new TSSystemException(ex);
		}
	}

	public static void deletarArquivo(String arquivo) {
		try {
			File file = new File(arquivo);
			file.delete();
		} catch (Exception ex) {
			throw new TSSystemException(ex);
		}
	}

	public static String obterExtensaoArquivo(String arquivo) {

		if (TSUtil.isEmpty(arquivo) || arquivo.indexOf(".") < 0) {
			return null;
		}

		return arquivo.substring(arquivo.indexOf("."), arquivo.length()).toLowerCase();

	}

}
