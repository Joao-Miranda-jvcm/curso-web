package com.curso.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import com.curso.dao.CategoriaDAO;
import com.curso.modelo.Categoria;
import com.curso.util.NegocioException;

public class CategoriaService implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CategoriaDAO categoriaDAO;
	
	public void salvar(Categoria categoria) throws NegocioException {			
		
		this.categoriaDAO.salvar(categoria);
	}
	
	public Categoria buscarPelaSigla(String sigla) {
		return categoriaDAO.buscarPelaSigla(sigla);
		
	}

	public List<Categoria> buscarTodos() {
		return categoriaDAO.buscarTodos();
	}

	
	public void excluir(Categoria categoria) throws NegocioException {
		categoriaDAO.excluir(categoria);
		
	}
}
