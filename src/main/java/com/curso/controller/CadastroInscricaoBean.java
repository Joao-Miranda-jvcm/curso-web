package com.curso.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.curso.modelo.Atleta;
import com.curso.modelo.Categoria;
import com.curso.modelo.Evento;
import com.curso.modelo.Inscricao;
import com.curso.modelo.enums.Status;
import com.curso.service.InscricaoService;
import com.curso.util.MessageUtil;
import com.curso.util.NegocioException;


@Named
@ViewScoped
public class CadastroInscricaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Inscricao inscricao;
	private List<Atleta> atletas;
	private List<Status> statuss;
	private List<Categoria> categorias;
	private List<Evento> eventos;
	
	@Inject
	private InscricaoService inscricaoService;
	
	public void salvar() {
		try {
			this.inscricaoService.salvar(inscricao);
			MessageUtil.sucesso("Inscrição salva com sucesso!");
		} catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
		} catch (Exception ex) {
			MessageUtil.erro(ex.getMessage());
		}
		this.limpar();
	}
	
	@PostConstruct
	public void inicializar(){
		eventos = inscricaoService.buscarEventos();
		atletas = inscricaoService.buscarAtletas();
		statuss = Arrays.asList(Status.values());
		categorias = inscricaoService.buscarCategoria();
		limpar();
	}
	
	public void limpar() {
		this.inscricao = new Inscricao();
	}

	public Inscricao getInscricao() {
		return inscricao;
	}

	public void setInscricao(Inscricao inscricao) {
		this.inscricao = inscricao;
	}
	
	public List<Atleta> getAtletas(){
		return atletas;
	}
	
	public List<Status> getStatuss(){
		return statuss;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public List<Evento> getEventos() {
		return eventos;
	}
}
