package com.login.android.semut.sqlite;

import org.droidpersistence.dao.TableDefinition;

import com.login.android.semut.model.Usuario;

public class UsuarioTableDefinition extends TableDefinition<Usuario> {

	public UsuarioTableDefinition() {
		super(Usuario.class);
	}

}
