package com.curso.dao;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import com.curso.modelo.Inscricao;
import com.curso.util.NegocioException;
import com.curso.util.jpa.Transactional;

public class InscricaoDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
			
	@Inject
	private EntityManager manager;
	
	@Transactional
	public void salvar(Inscricao inscricao) throws NegocioException {
		try {
	        manager.merge(inscricao);
	    } catch (PersistenceException e) {
	        Throwable cause = e.getCause();
	        if (cause instanceof ConstraintViolationException) {
	            throw new NegocioException("Violação de restrição, provavelmente contato já existe.");
	        }
	        throw new NegocioException("Erro ao salvar a inscrição.");
	    }
	}
	
	@Transactional
	public void excluir(Inscricao inscricao) throws NegocioException {
		inscricao = buscarPeloCodigo(inscricao.getCodigo());
		try {
			manager.remove(inscricao);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Esta inscrição não pode ser excluída.");
		}
	}
	
	public Inscricao buscarPeloCodigo(Long codigo) {
		return manager.find(Inscricao.class, codigo);
	}
	
	@SuppressWarnings("unchecked")
	public List<Inscricao> buscarTodos() {
		return manager.createNamedQuery("Inscricao.buscarTodos").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Inscricao> buscarComPaginacao(int first, int pageSize) {
		return manager.createNamedQuery("Inscricao.buscarTodos")
							.setFirstResult(first)
							.setMaxResults(pageSize)
							.getResultList();
	}
	
	public Long encontrarQuantidadeDeInscricao() {
		return manager.createQuery("select count(i) from Inscricao i", Long.class).getSingleResult();
	}

}
