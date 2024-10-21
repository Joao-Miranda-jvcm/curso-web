package com.curso.dao;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import com.curso.modelo.Competicao;
import com.curso.util.NegocioException;
import com.curso.util.jpa.Transactional;

public class CompeticaoDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional
	public void salvar(Competicao competicao) {
		manager.merge(competicao);
		
	}
	
	@Transactional
	public void excluir(Competicao competicao) throws NegocioException {
		competicao = buscarPeloCodigo(competicao.getCodigo() );
		try {
			manager.remove(competicao);
			manager.flush();
		}catch (PersistenceException e) {
			throw new NegocioException("Esta competição não pode ser excluída.");
		}
	}

	
	public Competicao buscarPeloCodigo(Long codigo) {
		return manager.find(Competicao.class,codigo);
	}
	
	@SuppressWarnings("unchecked")
	public List<Competicao> buscarTodos() {
		return manager.createNamedQuery("Competicao.buscarTodos").getResultList();
	}	
		
	@SuppressWarnings("unchecked")
	public List<Competicao> buscarComPaginacao(int first, int pageSize) {
		return manager.createNamedQuery("Competicao.buscarTodos")
							.setFirstResult(first)
							.setMaxResults(pageSize)
							.getResultList();
	}

	public Long encontrarQuantidadeDeCompeticoes() {
		return manager.createQuery("select count(o) from Competicao o", Long.class).getSingleResult();
	}

}
