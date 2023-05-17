package com.curso.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class PesquisaClubeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Clube> clubes = new ArrayList<Clube>();
	private Clube clubeSelecionado;
	
	@Inject
	ClubeService clubeService;

		
	@PostConstruct
	public void inicializar() {
		clubes = clubeService.buscarTodos();
	}
	
	public void excluir() {
		try {
			clubeService.excluir(clubeSelecionado);			
			this.clubes.remove(clubeSelecionado);
			MessageUtil.sucesso("Clube " + clubeSelecionado.getSigla() + " excluído com sucesso.");
		} catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
		}
	}
	
	
	public List<Clube> getClubes() {
		return clubes;
	}
	
	public Clube getClubeSelecionado() {
		return clubeSelecionado;
	}

	public void setClubeSelecionado(Clube clubeSelecionado) {
		this.clubeSelecionado = clubeSelecionado;
	}

	
}