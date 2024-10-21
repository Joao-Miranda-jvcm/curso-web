package com.curso.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.curso.modelo.enums.Status;

@Entity
@NamedQueries({
	@NamedQuery(name="Inscricao.buscarTodos", query="select i from Inscricao i")
})
public class Inscricao {
	
	private Long codigo;
	private boolean isAlergico;
    private String alergia;
    private boolean isCondicaoFisica;
    private Integer comprovante;
    private Integer arquivo;
    private Date dataInscricao;
    private String contato;
    private String telefoneContato;
    private Atleta atleta;
    private Status status;
    private Categoria categoria;
    private Evento evento;
    
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public boolean isAlergico() {
		return isAlergico;
	}
	public void setAlergico(boolean isAlergico) {
		this.isAlergico = isAlergico;
	}
	
	public String getAlergia() {
		return alergia;
	}
	public void setAlergia(String alergia) {
		this.alergia = alergia;
	}
	
	public boolean isCondicaoFisica() {
		return isCondicaoFisica;
	}
	public void setCondicaoFisica(boolean isCondicaoFisica) {
		this.isCondicaoFisica = isCondicaoFisica;
	}
	
	@Column(unique=true)
	public Integer getComprovante() {
		return comprovante;
	}
	public void setComprovante(Integer comprovante) {
		this.comprovante = comprovante;
	}
	
	@Column(unique=true)
	public Integer getArquivo() {
		return arquivo;
	}
	public void setArquivo(Integer arquivo) {
		this.arquivo = arquivo;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDataInscricao() {
		return dataInscricao;
	}
	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
	}
	
	@Column(unique=true)
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	
	@Column(unique=true)
	public String getTelefoneContato() {
		return telefoneContato;
	}
	public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato = telefoneContato;
	}
	
	/*
	 * Enums
	 */
	
	@Enumerated(EnumType.STRING)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	/*
	 * Relacionamentos
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_atleta")
	public Atleta getAtleta() {
		return atleta;
	}
	public void setAtleta(Atleta atleta) {
		this.atleta = atleta;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_categoria")
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_evento")
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inscricao other = (Inscricao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
