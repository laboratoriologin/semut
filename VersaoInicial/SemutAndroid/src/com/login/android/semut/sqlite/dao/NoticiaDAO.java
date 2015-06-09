package com.login.android.semut.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.android.semut.model.Noticia;

public class NoticiaDAO extends DroidDao<Noticia, Integer> {

	public NoticiaDAO(TableDefinition<Noticia> tableDefinition,
			SQLiteDatabase database) {
		super(Noticia.class, tableDefinition, database);
	}

}
