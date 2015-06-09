package com.login.android.semut.lauro.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.android.semut.lauro.model.Noticia;

public class NoticiaDAO extends DroidDao<Noticia, Integer> {

	public NoticiaDAO(TableDefinition<Noticia> tableDefinition,
			SQLiteDatabase database) {
		super(Noticia.class, tableDefinition, database);
	}

}
