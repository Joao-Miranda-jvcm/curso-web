package com.curso.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.curso.modelo.Categoria;
import com.curso.service.CategoriaService;
import com.curso.util.MessageUtil;
import com.curso.util.NegocioException;

@Named
@ViewScoped
public class CadastroCategoriaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Categoria categoria;
	
	@Inject
	private CategoriaService categoriaService;
	
	
	public void salvar() {
		try {
			this.categoriaService.salvar(categoria);
			MessageUtil.sucesso("Categoria salva com sucesso!");
		} catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
		}
		
		this.limpar();
	}
	
	@PostConstruct
	public void inicializar() {
		this.limpar();
	}
	
	public void limpar() {
		this.categoria = new Categoria();
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;

	}
	
	
	
	
	
	

}
