package com.curso.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import com.curso.dao.AtletaDAO;
import com.curso.dao.CategoriaDAO;
import com.curso.dao.EventoDAO;
import com.curso.dao.InscricaoDAO;
import com.curso.modelo.Atleta;
import com.curso.modelo.Categoria;
import com.curso.modelo.Evento;
import com.curso.modelo.Inscricao;
import com.curso.util.NegocioException;

public class InscricaoService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private InscricaoDAO inscricaoDAO;
	@Inject
	private AtletaDAO atletaDAO;
	@Inject
	private CategoriaDAO categoriaDAO;
	@Inject
	private EventoDAO eventoDAO;
	
	public void salvar(Inscricao inscricao) throws NegocioException {		
		this.inscricaoDAO.salvar(inscricao);		
	}
	
	public List<Inscricao> buscarTodos() {
		return inscricaoDAO.buscarTodos();
	}
	
	public void excluir(Inscricao inscricao) throws NegocioException {
		inscricaoDAO.excluir(inscricao);		
	}
	
	/*
	 * relacionamento
	 * 
	 */
	
	public List<Atleta> buscarAtletas() {
		return atletaDAO.buscarTodos();
	}
	
	public List<Categoria> buscarCategoria(){
		return categoriaDAO.buscarTodos();
	}
	public List<Evento> buscarEventos(){
		return eventoDAO.buscarTodos();
	}
}
