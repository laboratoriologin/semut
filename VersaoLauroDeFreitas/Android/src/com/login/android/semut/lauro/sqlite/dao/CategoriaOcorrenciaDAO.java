package com.login.android.semut.lauro.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.android.semut.lauro.model.CategoriaOcorrencia;

public class CategoriaOcorrenciaDAO extends DroidDao<CategoriaOcorrencia, Long> {

	public CategoriaOcorrenciaDAO(TableDefinition<CategoriaOcorrencia> tableDefinition,
			SQLiteDatabase database) {
		super(CategoriaOcorrencia.class, tableDefinition, database);
	}

}
