package com.login.android.semut.lauro.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.android.semut.lauro.model.Ocorrencia;

public class OcorrenciaDAO extends DroidDao<Ocorrencia, Long> {

	public OcorrenciaDAO(TableDefinition<Ocorrencia> tableDefinition,
			SQLiteDatabase database) {
		super(Ocorrencia.class, tableDefinition, database);
	}

}
