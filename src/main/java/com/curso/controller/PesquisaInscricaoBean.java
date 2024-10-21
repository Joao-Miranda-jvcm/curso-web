package com.curso.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.curso.modelo.Inscricao;
import com.curso.service.InscricaoService;
import com.curso.util.MessageUtil;
import com.curso.util.NegocioException;

@Named
@ViewScoped
public class PesquisaInscricaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Inscricao> inscricoes = new ArrayList<Inscricao>();
	private Inscricao inscricaoSelecionada;
	
	@Inject
	private InscricaoService inscricaoService;
	
	@PostConstruct
	public void inicializar() {
		inscricoes = inscricaoService.buscarTodos();
	}
	
	public void excluir() {
		try {
			inscricaoService.excluir(inscricaoSelecionada);			
			this.inscricoes.remove(inscricaoSelecionada);
			MessageUtil.sucesso("Inscrição " + inscricaoSelecionada.getCodigo() + " excluída com sucesso.");
		} catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
		}
	}
	
	public List<Inscricao> getInscricoes(){
		return inscricoes;
	}
	
	public Inscricao getInscricaoSelecionada() {
		return inscricaoSelecionada;
	}
	
	public void setInscricaoSelecionada(Inscricao inscricaoSelecionada) {
		this.inscricaoSelecionada = inscricaoSelecionada;
	}
	
}
