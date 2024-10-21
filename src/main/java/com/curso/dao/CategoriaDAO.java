package com.curso.dao;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import com.curso.modelo.Categoria;
import com.curso.util.NegocioException;
import com.curso.util.jpa.Transactional;

public class CategoriaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	
	@Transactional
	public void salvar(Categoria categoria) {
		manager.merge(categoria);
	}
	
	@Transactional
	public void excluir(Categoria categoria) throws NegocioException {
		categoria = buscarPeloCodigo(categoria.getCodigo());
		try {
			manager.remove(categoria);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Esta categoria não pode ser excluída.");
		}
	}
	
	public Categoria buscarPeloCodigo(Long codigo) {
		return manager.find(Categoria.class, codigo);
	}
	
	public Categoria buscarPelaSigla(String sigla) {
		return manager.createNamedQuery("Categoria.buscarPorSigla", Categoria.class)
				.setParameter("sigla", sigla)
				.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> buscarTodos() {
		return manager.createNamedQuery("Categoria.buscarTodos").getResultList();
	}
	
		
	@SuppressWarnings("unchecked")
	public List<Categoria> buscarComPaginacao(int first, int pageSize) {
		return manager.createNamedQuery("Categoria.buscarTodos")
							.setFirstResult(first)
							.setMaxResults(pageSize)
							.getResultList();
	}

	public Long encontrarQuantidadeDeCategorias() {
		return manager.createQuery("select count(t) from Categoria t", Long.class).getSingleResult();
	}
}
