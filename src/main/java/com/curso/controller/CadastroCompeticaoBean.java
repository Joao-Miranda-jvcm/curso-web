package com.curso.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.curso.modelo.Competicao;
import com.curso.service.CompeticaoService;
import com.curso.util.MessageUtil;
import com.curso.util.NegocioException;

@Named
@ViewScoped
public class CadastroCompeticaoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CompeticaoService competicaoService;
	
	private Competicao competicao;
	
	public void salvar() {
		try{
			this.competicaoService.salvar(competicao);
			MessageUtil.sucesso("Competição salva com sucesso!");
		}catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
		}
		this.limpar();
	}
	
	@PostConstruct
	public void inicializar() {
		this.limpar();
	}

	private void limpar() {
		this.competicao = new Competicao();
	}

	public Competicao getCompeticao() {
		return competicao;
	}

	public void setCompeticao(Competicao competicao) {
		this.competicao = competicao;
	}
	

	
	
	
}
