package com.curso.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.curso.modelo.Competicao;
import com.curso.modelo.Evento;
import com.curso.modelo.enums.Etapa;
import com.curso.service.EventoService;
import com.curso.util.MessageUtil;
import com.curso.util.NegocioException;

@Named
@ViewScoped
public class CadastroEventoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Evento evento;
	private List<Etapa> etapas;
	private List<Competicao> competicoes;
	
	@Inject
	private EventoService eventoService;
	
	public void salvar() {
	try {
		eventoService.salvar(evento);
		MessageUtil.sucesso("Evento salvo com sucesso!");
		} catch (NegocioException e) {
		MessageUtil.erro(e.getMessage());
	}
		this.limpar();
	}
	
	@PostConstruct
	public void inicializar() {
		etapas = Arrays.asList(Etapa.values());
		competicoes = eventoService.buscarCompeticoes();
		this.limpar();
	}
	public void limpar() {
		this.evento = new Evento();
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<Etapa> getEtapas() {
		return etapas;
	}

	public List<Competicao> getCompeticoes() {
		return competicoes;
	}
	
	
	
}
