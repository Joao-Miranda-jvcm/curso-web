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
import com.curso.modelo.enums.Role;
import com.curso.modelo.enums.Sexo;


@Entity
@NamedQueries({
	@NamedQuery(name="Atleta.buscarTodos", query="select a from Atleta a"),
	@NamedQuery(name="Atleta.buscarPorEmail", query="select a from Atleta a "
			+ "where a.email = :email")
})
public class Atleta {

	private Long codigo;
	private String nome;
	private String email;	
	private String telefone;
	private Date dataNascimento;
	private Sexo Sexo;	
	private Clube clube;
	private Role Role;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}	
	
	@Column(unique=true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}	
	
	/*
	 * Enums
	 */
	
	@Enumerated(EnumType.STRING)
	public Sexo getSexo() {
		return Sexo;
	}
	public void setSexo(Sexo sexo) {
		Sexo = sexo;
	}
	
	@Enumerated(EnumType.STRING)
	public Role getRole() {
		return Role;
	}
	public void setRole(Role role) {
		Role = role;
	}
	
	
	/*
	 * Relacionamentos
	 */
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_clube")
	public Clube getClube() {
		return clube;
	}
	public void setClube(Clube clube) {
		this.clube = clube;
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
		Atleta other = (Atleta) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
