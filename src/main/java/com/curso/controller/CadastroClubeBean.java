package com.curso.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.curso.modelo.Clube;
import com.curso.service.ClubeService;
import com.curso.util.MessageUtil;
import com.curso.util.NegocioException;

@Named
@ViewScoped
public class CadastroClubeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Clube clube;
	
	
	@Inject
	private ClubeService clubeService;
	
	
	
	public void salvar() {
		try {
			this.clubeService.salvar(clube);
			MessageUtil.sucesso("Clube salvo com sucesso!");
		} catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
		}
		
		this.limpar();
	}
	
	@PostConstruct
	public void inicializar(){
		this.limpar();		
	}
	
	public void limpar() {
		this.clube = new Clube();
	}

	public Clube getClube() {
		return clube;
	}

	public void setClube(Clube clube) {
		this.clube = clube;
	}

	
	
}
