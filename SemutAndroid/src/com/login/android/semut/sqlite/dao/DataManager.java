package com.login.android.semut.lauro.sqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.login.android.semut.lauro.exception.PersistException;
import com.login.android.semut.lauro.model.CategoriaOcorrencia;
import com.login.android.semut.lauro.model.Noticia;
import com.login.android.semut.lauro.model.Ocorrencia;
import com.login.android.semut.lauro.sqlite.CategoriaOcorrenciaTableDefinition;
import com.login.android.semut.lauro.sqlite.NoticiaTableDefinition;
import com.login.android.semut.lauro.sqlite.OcorrenciaTableDefinition;
import com.login.android.semut.lauro.sqlite.OpenHelper;
import com.login.android.semut.lauro.sqlite.UsuarioTableDefinition;

public class DataManager {

	private SQLiteDatabase database;
	private NoticiaDAO noticiaDAO;
	private OcorrenciaDAO ocorrenciaDAO;
	private UsuarioDAO usuarioDAO;
	private CategoriaOcorrenciaDAO categoriaOcorrenciaDAO;

	public DataManager(Context context) {

		SQLiteOpenHelper openHelper = new OpenHelper(context, "semut", null,1);

		this.database = openHelper.getWritableDatabase();

		this.ocorrenciaDAO = new OcorrenciaDAO(new OcorrenciaTableDefinition(), database);
		this.noticiaDAO = new NoticiaDAO(new NoticiaTableDefinition(), database);
		this.usuarioDAO = new UsuarioDAO(new UsuarioTableDefinition(), database);
		this.categoriaOcorrenciaDAO = new CategoriaOcorrenciaDAO(new CategoriaOcorrenciaTableDefinition(), database);
	}

	public long save(Ocorrencia ocorrencia) throws PersistException {
		long result = 0;

		try {

			this.database.beginTransaction();

			result = this.ocorrenciaDAO.save(ocorrencia);

			this.database.setTransactionSuccessful();

		} catch (Exception ex) {

			throw new PersistException(ex);

		} finally {

			this.database.endTransaction();
		}

		return result;
	}

	public long save(Noticia noticia) throws PersistException {
		long result = 0;

		try {

			this.database.beginTransaction();

			result = this.noticiaDAO.save(noticia);

			this.database.setTransactionSuccessful();

		} catch (Exception ex) {

			throw new PersistException(ex);

		} finally {

			this.database.endTransaction();
		}

		return result;
	}
	
	public long save(CategoriaOcorrencia cat) throws PersistException {
		long result = 0;

		try {

			this.database.beginTransaction();

			result = this.categoriaOcorrenciaDAO.save(cat);

			this.database.setTransactionSuccessful();

		} catch (Exception ex) {

			throw new PersistException(ex);

		} finally {

			this.database.endTransaction();
		}

		return result;
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	public NoticiaDAO getNoticiaDAO() {
		return noticiaDAO;
	}

	public void setNoticiaDAO(NoticiaDAO noticiaDAO) {
		this.noticiaDAO = noticiaDAO;
	}

	public OcorrenciaDAO getOcorrenciaDAO() {
		return ocorrenciaDAO;
	}

	public void setOcorrenciaDAO(OcorrenciaDAO ocorrenciaDAO) {
		this.ocorrenciaDAO = ocorrenciaDAO;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public CategoriaOcorrenciaDAO getCategoriaOcorrenciaDAO() {
		return categoriaOcorrenciaDAO;
	}

	public void setCategoriaOcorrenciaDAO(CategoriaOcorrenciaDAO categoriaOcorrenciaDAO) {
		this.categoriaOcorrenciaDAO = categoriaOcorrenciaDAO;
	}

}
