package com.login.audit.laurofreitas.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

import com.login.audit.laurofreitas.model.Regiao;
import com.login.audit.laurofreitas.model.RegiaoCoordenada;

@ViewScoped
@ManagedBean(name = "regiaoFaces")
public class RegiaoFaces extends CrudFaces<Regiao> {

	private List<Regiao> listRegiao;
	private String strJson;
	private String strJsonSelecionado;
	private String polygon;

	@PostConstruct
	protected void init() {
		this.clearFields();

		this.listRegiao = new Regiao().findAll();
		strJson = regioesToJson();
		strJsonSelecionado = regiaoSelecionadaToJson();

	}

	public String regiaoSelecionadaToJson() {
		JSONObject json = new JSONObject();

		JSONArray arryJsonCoordenada;
		JSONObject jsonCoordenada;

		JSONObject jsonRegiao;

		try {
			jsonRegiao = new JSONObject();
			jsonRegiao.put("id", getCrudModel().getId());

			arryJsonCoordenada = new JSONArray();
			for (RegiaoCoordenada regiaoCoordenada : getCrudModel().getListRegiaoCoordenada()) {
				jsonCoordenada = new JSONObject();
				jsonCoordenada.put("lat", regiaoCoordenada.getLatitude());
				jsonCoordenada.put("long", regiaoCoordenada.getLongitude());

				arryJsonCoordenada.put(jsonCoordenada);
			}

			jsonRegiao.put("coor", arryJsonCoordenada);

			json.put("regiao", jsonRegiao);

		} catch (Exception e) {

		}

		return json.toString();
	}

	public String regioesToJson() {
		JSONObject json = new JSONObject();

		JSONArray arryJsonCoordenada;
		JSONObject jsonCoordenada;

		JSONArray arryJsonRegiao = new JSONArray();
		JSONObject jsonRegiao;

		try {
			for (Regiao regiao : this.listRegiao) {

				jsonRegiao = new JSONObject();
				jsonRegiao.put("id", regiao.getId());
				jsonRegiao.put("nome", regiao.getNome());

				arryJsonCoordenada = new JSONArray();
				for (RegiaoCoordenada regiaoCoordenada : regiao.getListRegiaoCoordenada()) {
					jsonCoordenada = new JSONObject();
					jsonCoordenada.put("lat", regiaoCoordenada.getLatitude());
					jsonCoordenada.put("long", regiaoCoordenada.getLongitude());

					arryJsonCoordenada.put(jsonCoordenada);
				}

				jsonRegiao.put("coor", arryJsonCoordenada);
				arryJsonRegiao.put(jsonRegiao);
			}

			json.put("regioes", arryJsonRegiao);

		} catch (Exception e) {

		}

		return json.toString();
	}

	@Override
	protected String detail() {
		super.detail();

		this.listRegiao = new Regiao().findByIdDiferente(getCrudModel().getId());
		this.strJson = regioesToJson();
		this.strJsonSelecionado = regiaoSelecionadaToJson();

		return null;
	}
	
	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new Regiao());
		getCrudPesquisaModel().setListRegiaoCoordenada(new ArrayList<RegiaoCoordenada>());

		return null;
	}

	@Override
	protected void prePersist() {

		if (!TSUtil.isEmpty(this.polygon)) {
			String lat = "";
			String lng = "";

			getCrudModel().setListRegiaoCoordenada(new ArrayList<RegiaoCoordenada>());
			RegiaoCoordenada regiaoCoordenada;

			for (String item : this.polygon.split(";")) {
				lat = item.split(",")[0];
				lng = item.split(",")[1];

				regiaoCoordenada = new RegiaoCoordenada(lat, lng);
				regiaoCoordenada.setRegiao(getCrudModel());

				getCrudModel().getListRegiaoCoordenada().add(regiaoCoordenada);
			}

		}
	}

	public List<Regiao> getListRegiao() {
		return listRegiao;
	}

	public void setListRegiao(List<Regiao> listRegiao) {
		this.listRegiao = listRegiao;
	}

	public String getStrJson() {
		return strJson;
	}

	public void setStrJson(String strJson) {
		this.strJson = strJson;
	}

	public String getPolygon() {
		return polygon;
	}

	public void setPolygon(String polygon) {
		this.polygon = polygon;
	}

	public String getStrJsonSelecionado() {
		return strJsonSelecionado;
	}

	public void setStrJsonSelecionado(String strJsonSelecionado) {
		this.strJsonSelecionado = strJsonSelecionado;
	}
}