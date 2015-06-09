package com.login.android.semut.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.android.semut.model.CategoriaOcorrencia;

public class CategoriaOcorrenciaDAO extends DroidDao<CategoriaOcorrencia, Long> {

	public CategoriaOcorrenciaDAO(TableDefinition<CategoriaOcorrencia> tableDefinition,
			SQLiteDatabase database) {
		super(CategoriaOcorrencia.class, tableDefinition, database);
	}

}
