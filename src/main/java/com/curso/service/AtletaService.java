package com.curso.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.curso.dao.AtletaDAO;
import com.curso.dao.ClubeDAO;
import com.curso.modelo.Atleta;
import com.curso.modelo.Clube;
import com.curso.util.NegocioException;

public class AtletaService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClubeDAO clubeDAO;
	@Inject
	private AtletaDAO atletaDAO;


	public void salvar(Atleta atleta) throws NegocioException {		
		this.atletaDAO.salvar(atleta);		
	}	
	
	public List<Atleta> buscarTodos() {
		return atletaDAO.buscarTodos();
	}
	
	public void excluir(Atleta atleta) throws NegocioException {
		atletaDAO.excluir(atleta);		
	}

	
	public Atleta buscarPeloEmail(String email) {
		return atletaDAO.buscarPeloEmail(email);
		
	}
	
	public List<Atleta> buscarPeloNome(String nome){
		return atletaDAO.buscarPeloNome(nome);
	}
	public Atleta buscarPeloCodigo(Long codigo) {
		return atletaDAO.buscarPeloCodigo(codigo);
	}
	
	
	/*
	 * Clubes
	 * 
	 */

	public List<Clube> buscarClubes() {
		return clubeDAO.buscarTodos();
	}
}