package com.curso.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.curso.dao.ClubeDAO;
import com.curso.modelo.Clube;
import com.curso.util.NegocioException;

public class ClubeService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClubeDAO clubeDAO;


	public void salvar(Clube clube) throws NegocioException {			
		
		this.clubeDAO.salvar(clube);
	}


	public List<Clube> buscarTodos() {
		return clubeDAO.buscarTodos();
	}

	
	public void excluir(Clube clube) throws NegocioException {
		clubeDAO.excluir(clube);
		
	}

	
}