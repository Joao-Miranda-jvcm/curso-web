package com.curso.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.curso.modelo.Clube;
import com.curso.util.NegocioException;
import com.curso.util.jpa.Transactional;

public class ClubeDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	
	@Transactional
	public void salvar(Clube clube) {
		manager.merge(clube);
	}
	
	@Transactional
	public void excluir(Clube clube) throws NegocioException {
		clube = buscarPeloCodigo(clube.getCodigo());
		try {
			manager.remove(clube);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Este clube não pode ser excluído.");
		}
	}
	
	
	
	/*
	 * Buscas
	 */	
	
	public Clube buscarPeloCodigo(Long codigo) {
		return manager.find(Clube.class, codigo);
	}
	
	@SuppressWarnings("unchecked")
	public List<Clube> buscarTodos() {
		return manager.createNamedQuery("Clube.buscarTodos").getResultList();
	}
	
		
	@SuppressWarnings("unchecked")
	public List<Clube> buscarComPaginacao(int first, int pageSize) {
		return manager.createNamedQuery("Clube.buscarTodos")
							.setFirstResult(first)
							.setMaxResults(pageSize)
							.getResultList();
	}

	public Long encontrarQuantidadeDeClubes() {
		return manager.createQuery("select count(c) from Clube c", Long.class).getSingleResult();
	}
}