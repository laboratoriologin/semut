package com.login.audit.laurofreitas.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

import com.login.audit.laurofreitas.util.SemutUtil;

/**
 * 
 * @author anderson
 * 
 */
@Entity
@Table(name = "ocorrencias")
public class Ocorrencia extends TSActiveRecordAb<Ocorrencia> {

	public Ocorrencia() {
	}

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Timestamp data;

	@Column(name = "data_alerta")
	private Timestamp dataAlerta;

	@Column(name = "data_resolucao")
	private Timestamp dataResolucao;
	
	@Column(name = "data_recusa")
	private Timestamp dataRecusa;
	
	@Column(name = "data_pendencia")
	private Timestamp dataPendencia;
	
	@Column(name = "data_aceitacao")
	private Timestamp dataAceitacao;

	private String descricao;

	private Double latitude;

	private Double longitude;

	private String imagem;

	private Boolean status;

	@Transient
	private Date dataInicial;

	@Transient
	private Date dataFinal;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private UsuarioAPP usuario;

	@ManyToOne
	@JoinColumn(name = "situacao")
	private TipoSituacao tipoSituacao;

	@ManyToOne
	@JoinColumn(name = "id_tipo_ocorrencia")
	private CategoriaOcorrencia categoriaOcorrencia;

	@Override
	public final List<Ocorrencia> findByModel(final String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from Ocorrencia s where (? is null or lower(s.descricao) like ?) ");

		List<Object> params = new ArrayList<Object>();
		
		params.add(SemutUtil.tratarString(descricao));
		params.add(SemutUtil.tratarString(descricao));
		
		if (id != null && id != 0) {
			query.append(" and s.id = ?");
			params.add(id);
		}
		
		if (id == 0)
			this.setId(null);

		if (categoriaOcorrencia != null && categoriaOcorrencia.getId() != 0) {
			query.append(" and s.categoriaOcorrencia.id = coalesce(?,s.categoriaOcorrencia.id)");
			params.add(categoriaOcorrencia.getId());
		}
		if (tipoSituacao != null && tipoSituacao.getId() != 0) {
			query.append(" and s.tipoSituacao.id = coalesce(?,s.tipoSituacao.id)");
			params.add(tipoSituacao.getId());
		}
		if (status) {
			query.append(" and s.status = ?");
			params.add(status);
		}
		

		if(usuario != null && usuario.getNome() != ""){
			query.append(" and  lower(s.usuario.nome) like  ?");
			params.add(SemutUtil.tratarString(usuario.getNome()));
		}

		return super.find(query.toString(), "id", params.toArray());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;

	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public UsuarioAPP getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioAPP usuario) {
		this.usuario = usuario;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public CategoriaOcorrencia getCategoriaOcorrencia() {
		return categoriaOcorrencia;
	}

	public void setCategoriaOcorrencia(CategoriaOcorrencia categoriaOcorrencia) {
		this.categoriaOcorrencia = categoriaOcorrencia;
	}

	public Timestamp getDataAlerta() {
		return dataAlerta;
	}

	public void setDataAlerta(Timestamp dataAlerta) {
		this.dataAlerta = dataAlerta;
	}

	public TipoSituacao getTipoSituacao() {
		return tipoSituacao;
	}

	public void setTipoSituacao(TipoSituacao tipoSituacao) {
		this.tipoSituacao = tipoSituacao;
	}

	public Timestamp getDataResolucao() {
		return dataResolucao;
	}

	public void setDataResolucao(Timestamp dataResolucao) {
		this.dataResolucao = dataResolucao;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Timestamp getDataRecusa() {
		return dataRecusa;
	}

	public void setDataRecusa(Timestamp dataRecusa) {
		this.dataRecusa = dataRecusa;
	}

	public Timestamp getDataPendencia() {
		return dataPendencia;
	}

	public void setDataPendencia(Timestamp dataPendencia) {
		this.dataPendencia = dataPendencia;
	}

	public Timestamp getDataAceitacao() {
		return dataAceitacao;
	}

	public void setDataAceitacao(Timestamp dataAceitacao) {
		this.dataAceitacao = dataAceitacao;
	}

}
