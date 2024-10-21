package com.curso.dao;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import com.curso.modelo.Evento;
import com.curso.util.NegocioException;
import com.curso.util.jpa.Transactional;

public class EventoDAO implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional
	public void salvar(Evento evento) {
		manager.merge(evento);
	}
	
	@Transactional
	public void excluir(Evento evento) throws NegocioException {
		evento = buscarPeloCodigo(evento.getCodigo());
		try {
			manager.remove(evento);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Este evento não pode ser excluído.");
		}
	}
	
	
	public Evento buscarPeloCodigo(Long codigo) {
		return manager.find(Evento.class, codigo);
	}
	
	@SuppressWarnings("unchecked")
	public List<Evento> buscarTodos() {
		return manager.createNamedQuery("Evento.buscarTodos").getResultList();
	}	
		
	@SuppressWarnings("unchecked")
	public List<Evento> buscarComPaginacao(int first, int pageSize) {
		return manager.createNamedQuery("Evento.buscarTodos")
							.setFirstResult(first)
							.setMaxResults(pageSize)
							.getResultList();
	}

	public Long encontrarQuantidadeDeEventos() {
		return manager.createQuery("select count(e) from Evento e", Long.class).getSingleResult();
	}
}
	
	
	


