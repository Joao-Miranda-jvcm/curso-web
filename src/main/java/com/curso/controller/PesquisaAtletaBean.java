package com.curso.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.curso.modelo.Atleta;
import com.curso.service.AtletaService;
import com.curso.util.MessageUtil;
import com.curso.util.NegocioException;


@Named
@ViewScoped
public class PesquisaAtletaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Atleta> atletas = new ArrayList<Atleta>();
	private Atleta atletaSelecionado;
	
	@Inject
	private AtletaService atletaService;

		
	@PostConstruct
	public void inicializar() {
		atletas = atletaService.buscarTodos();
	}
	
	public void excluir() {
		try {
			atletaService.excluir(atletaSelecionado);			
			this.atletas.remove(atletaSelecionado);
			MessageUtil.sucesso("Atleta " + atletaSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
		}
	}
	
	
	public List<Atleta> getAtletas() {
		return atletas;
	}

	public Atleta getAtletaSelecionado() {
		return atletaSelecionado;
	}

	public void setAtletaSelecionado(Atleta atletaSelecionado) {
		this.atletaSelecionado = atletaSelecionado;
	}

	
}