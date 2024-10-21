package com.curso.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.curso.dao.CompeticaoDAO;
import com.curso.modelo.Competicao;
import com.curso.util.NegocioException;

public class CompeticaoService implements Serializable {

	 static final long serialVersionUID = 1L;
	 
	 @Inject
	 private CompeticaoDAO competicaoDAO;
	 
	 public void salvar(Competicao competicao) throws NegocioException {
		 this.competicaoDAO.salvar(competicao);
	 }
	 
	 public void excluir(Competicao competicao)throws NegocioException {
		 this.competicaoDAO.excluir(competicao); 
	 }
	 public List<Competicao> buscarTodos(){
		 return competicaoDAO.buscarTodos();
	 }

}
