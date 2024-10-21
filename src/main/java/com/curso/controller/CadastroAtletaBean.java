package com.curso.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.curso.modelo.Atleta;
import com.curso.modelo.Clube;
import com.curso.modelo.enums.Role;
import com.curso.modelo.enums.Sexo;
import com.curso.service.AtletaService;
import com.curso.util.MessageUtil;
import com.curso.util.NegocioException;

@Named
@ViewScoped
public class CadastroAtletaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Atleta atleta;
	private List<Sexo> sexos;
	private List<Clube> clubes;
	private List<Role> roles;
	
	@Inject
	private AtletaService atletaService;
	
	
	public void salvar() {
		try {
			
			this.atletaService.salvar(atleta);
			MessageUtil.sucesso("Atleta salvo com sucesso!");
		} catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
		} catch (Exception ex) {
			MessageUtil.erro(ex.getMessage());
		}
		
		this.limpar();
	}
	
	@PostConstruct
	public void inicializar(){
		clubes = atletaService.buscarClubes();
		sexos = Arrays.asList(Sexo.values());
		roles = Arrays.asList(Role.values());
		limpar();		
	}	

	public void limpar() {
		this.atleta = new Atleta();		
	}	

	public Atleta getAtleta() {
		return atleta;
	}

	public void setAtleta(Atleta atleta) {
		this.atleta = atleta;
	}

	public List<Sexo> getSexos() {
		return sexos;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public List<Clube> getClubes() {
		return clubes;
		
	}
	
	
}
