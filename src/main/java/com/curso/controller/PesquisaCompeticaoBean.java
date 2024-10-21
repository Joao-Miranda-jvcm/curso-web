package com.curso.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.curso.modelo.Competicao;
import com.curso.service.CompeticaoService;
import com.curso.util.MessageUtil;
import com.curso.util.NegocioException;

@Named
@ViewScoped
public class PesquisaCompeticaoBean implements Serializable{

	
	private static final long serialVersionUID = 1L;		
	
	@Inject
	private CompeticaoService competicaoService;
	
	private List<Competicao> competicoes = new ArrayList<Competicao>();
	private Competicao competicaoSelecionada;
	
	public void excluir() {
		try {
			competicaoService.excluir(competicaoSelecionada);
			this.competicoes.remove(competicaoSelecionada);
			MessageUtil.sucesso("Competição " + competicaoSelecionada.getNome() + " excluída com sucesso.");
		}catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
		}
	}
	
	@PostConstruct
	public void inicializar() {
		competicoes = this.competicaoService.buscarTodos();
	}

	public List<Competicao> getCompeticoes() {
		return competicoes;
	}

	public Competicao getCompeticaoSelecionada() {
		return competicaoSelecionada;
	}

	public void setCompeticaoSelecionada(Competicao competicaoSelecionada) {
		this.competicaoSelecionada = competicaoSelecionada;
	}
	

}
