package com.login.semut.faces;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import br.com.topsys.database.hibernate.TSActiveRecordIf;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

public abstract class CrudFaces <T extends TSActiveRecordIf<T>> extends TSMainFaces{

	private T crudModel;
	
	private T crudPesquisaModel;
	
	private List<T> grid;
	
	private String fieldOrdem;
	
	public String getFieldOrdem() {
		return fieldOrdem;
	}

	public void setFieldOrdem(String fieldOrdem) {
		this.fieldOrdem = fieldOrdem;
	}

	protected Integer tabIndex;
	
	private boolean flagAlterar;
	
	private Class<T> modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	@Override
	protected void clearFields() {
		this.limpar();
		this.limparPesquisa();
		this.tabIndex = 0;
	}
	
	public String limpar(){
		this.flagAlterar = false;
		try {
			this.crudModel = modelClass.newInstance();
		} catch (Exception e) {
			throw new TSSystemException(e);
		}
		return null;
	}
	
	public String limparPesquisa(){
		this.grid = Collections.emptyList();
		try {
			this.crudPesquisaModel = modelClass.newInstance();
		} catch (Exception e) {
			throw new TSSystemException(e);
		}
		
		return null;
	}
	
	protected boolean validaCampos(){
		return true;
	}
	
	protected void prePersist(){
	}
	
	protected void preInsert(){
	}
	
	protected void preUpdate(){
	}
	
	protected void posDetail(){
	}
	
	protected void posPersist() throws TSApplicationException{
	}
	
	@Override
	protected String insert() throws TSApplicationException {
		
		this.setClearFields(Boolean.FALSE);
		
		this.setDefaultMessage(Boolean.FALSE);
		
		if(!validaCampos()){
			return null;
		}
		
		this.prePersist();
		
		this.preInsert();
		
		this.crudModel.save();
		
		this.posPersist();
		
		this.limpar();
		
		this.setDefaultMessage(Boolean.TRUE);
		
		return null;
		
	}
	
	
	@Override
	protected String update() throws TSApplicationException {
		
		this.setClearFields(Boolean.FALSE);
		
		this.setDefaultMessage(Boolean.FALSE);

		if(!validaCampos()){
			return null;
		}
		
		this.prePersist();
		
		this.preUpdate();
		
		this.crudModel.update();
		
		this.posPersist();
		
		this.limpar();
		
		this.setDefaultMessage(Boolean.TRUE);
		
		return null;
		
	}
	
	@Override
	protected String delete() throws TSApplicationException {
		
		this.crudModel.delete();
		
		this.limpar();
		
		this.grid = this.crudPesquisaModel.findByModel();
		
		this.tabIndex = 1;
		
		return null;
	}
	
	@Override
	protected String detail() {

		this.crudModel = this.crudModel.getById();
		
		this.tabIndex = 0;
		
		this.flagAlterar = true;
		
		this.posDetail();
		
		return null;
		
	}
	
	@Override
	protected String find() {
		
		this.grid = this.crudPesquisaModel.findByModel(getFieldOrdem());
		
		TSFacesUtil.gerarResultadoLista(this.grid);
		
		return null;
		
	}

	public T getCrudModel() {
		return crudModel;
	}

	public void setCrudModel(T crudModel) {
		this.crudModel = crudModel;
	}

	public T getCrudPesquisaModel() {
		return crudPesquisaModel;
	}

	public void setCrudPesquisaModel(T crudPesquisaModel) {
		this.crudPesquisaModel = crudPesquisaModel;
	}

	public List<T> getGrid() {
		return grid;
	}

	public void setGrid(List<T> grid) {
		this.grid = grid;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public boolean isFlagAlterar() {
		return flagAlterar;
	}

	public void setFlagAlterar(boolean flagAlterar) {
		this.flagAlterar = flagAlterar;
	}
	
}
