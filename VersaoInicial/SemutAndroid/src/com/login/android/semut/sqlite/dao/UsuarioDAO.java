package com.login.android.semut.sqlite.dao;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;

import com.login.android.semut.model.Usuario;

public class UsuarioDAO extends DroidDao<Usuario, Long> {

	public UsuarioDAO(TableDefinition<Usuario> tableDefinition,
			SQLiteDatabase database) {
		super(Usuario.class, tableDefinition, database);
	}

}
