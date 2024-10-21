package com.curso.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import com.curso.modelo.Atleta;
import com.curso.modelo.Categoria;
import com.curso.modelo.Clube;
import com.curso.modelo.Evento;
import com.curso.modelo.Inscricao;
import com.curso.modelo.enums.Status;
import com.curso.service.AtletaService;
import com.curso.service.CategoriaService;
import com.curso.service.InscricaoService;
import com.curso.util.MessageUtil;
import com.curso.util.NegocioException;


@Named
@ViewScoped
public class CadastroInscricaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Inscricao inscricao;
	private List<Atleta> atletas;
	private List<Status> statuss;
	private List<Categoria> categorias;
	private List<Evento> eventos;
	private List<Clube> clubes;
	private Atleta atleta;
	private Categoria categoria;
	private boolean cadastroAtletaAtivo;
	private boolean cadastroCategoriaAtivo;
	
	@Inject
	private InscricaoService inscricaoService;
	@Inject
	private AtletaService atletaService;
	@Inject
	private CategoriaService categoriaService;
	
	public void salvar() {
		try {
			
			if(cadastroAtletaAtivo) {
				String email = this.atleta.getEmail();
				this.atletaService.salvar(atleta);
				Atleta atletaSelecionado = atletaService.buscarPeloEmail(email);
				inscricao.setAtleta(atletaSelecionado);
			}else {
				this.atleta=null;
			}
			if(cadastroCategoriaAtivo) {
				String sigla = this.categoria.getSigla();
				this.categoriaService.salvar(categoria);
				Categoria categoriaSelecionada = categoriaService.buscarPelaSigla(sigla);
				inscricao.setCategoria(categoriaSelecionada);
			}else {
				this.categoria=null;
			}
			this.inscricaoService.salvar(inscricao);
			MessageUtil.sucesso("Inscrição salva com sucesso!");
		} catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
		} catch (Exception ex) {
			MessageUtil.erro(ex.getMessage());
		}
		
		this.limpar();
		cadastroAtletaAtivo=false;
		cadastroCategoriaAtivo=false;
	}
	
	@PostConstruct
	public void inicializar(){
		eventos = inscricaoService.buscarEventos();
		atletas = inscricaoService.buscarAtletas();
		statuss = Arrays.asList(Status.values());
		categorias = inscricaoService.buscarCategoria();
		clubes = atletaService.buscarClubes();
		limpar();
	}
	
	public void limpar() {
		this.inscricao = new Inscricao();
	}

	public Inscricao getInscricao() {
		return inscricao;
	}

	public void setInscricao(Inscricao inscricao) {
		this.inscricao = inscricao;
	}
	
	public List<Atleta> getAtletas(){
		return atletas;
	}
	
	public List<Status> getStatuss(){
		return statuss;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public Atleta getAtleta() {
		return atleta;
	}
	public String getEmailAtleta() {
		return this.atleta.getEmail();
	}

	public void setAtleta(Atleta atleta) {
		this.atleta = atleta;
	}
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public AtletaService getAtletaService() {
		return atletaService;
	}

	public void setAtletaService(AtletaService atletaService) {
		this.atletaService = atletaService;
	}
	public CategoriaService getCategoriaService() {
		return categoriaService;
	}

	public void setCategoriaService(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}
	public List<Clube> getClubes() {
		return atletaService.buscarClubes();
	}
	public boolean isCadastroAtletaAtivo() {
        return cadastroAtletaAtivo;
    }
	public boolean isCadastroCategoriaAtivo() {
		return cadastroCategoriaAtivo;
	}
    public void setCadastroAtletaAtivo(boolean cadastroAtletaAtivo) {
        this.cadastroAtletaAtivo = cadastroAtletaAtivo;
    }
    public void onPainelAtletaToggled(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            cadastroAtletaAtivo = true; 
            this.atleta = new Atleta();
        } else {
            cadastroAtletaAtivo = false;

        }
    }
    public void onPainelCategoriaToggled(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            cadastroCategoriaAtivo = true;  
            this.categoria = new Categoria();
        } else {
            cadastroCategoriaAtivo = false;
        }
    }
	
	
}
