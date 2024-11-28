package com.curso.controller;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.curso.modelo.DatasIniFim;
import com.curso.modelo.Inscricao;
import com.curso.modelo.enums.Ano;
import com.curso.modelo.enums.Mes;
import com.curso.service.InscricaoPDFService;
import com.curso.service.InscricaoService;
import com.curso.util.DateUtils;
import com.curso.util.MessageUtil;
import com.curso.util.NegocioException;
import com.itextpdf.io.source.ByteArrayOutputStream;




@Named
@ViewScoped
public class PesquisaInscricaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(PesquisaInscricaoBean.class);
	
	private Long qdeTotal = 0L;
	private List<Inscricao> inscricoes = new ArrayList<Inscricao>();
	private Inscricao inscricaoSelecionada;
	
	private List<String> anos = new ArrayList<>(Arrays.asList(Ano.getAnos()));
	private Integer ano;
	private List<Mes> meses;
	private Mes mes;
	private DatasIniFim datasTO;
	private Date dataInicio;
	private Date dataFim;
	private boolean consultado = false;
	
	
	
	@Inject
	private InscricaoService inscricaoService;
	
	@Inject
	private InscricaoPDFService inscricaoPDFService;
	
	@PostConstruct
	public void inicializar() {
		LocalDate data = LocalDate.now();
		setAno(data.getYear());
		setMes(Mes.porCodigo(data.getMonthValue()));
		
		anos = new ArrayList<>(Arrays.asList(Ano.getAnos()));
		meses = Arrays.asList(Mes.values());
	}
	
	public void consultarInscricoes() {
		datasTO = DateUtils.getDatasIniFim(getAno(),getMes());
		inscricoes = inscricaoService.buscarInscricoesPeriodo(datasTO.getIni(),datasTO.getFim());
		qdeTotal= (long)inscricoes.size();
		consultado=true;
	}
	
	public void excluir() {
		try {
			inscricaoService.excluir(inscricaoSelecionada);			
			this.inscricoes.remove(inscricaoSelecionada);
			MessageUtil.sucesso("Inscrição " + inscricaoSelecionada.getCodigo() + " excluída com sucesso.");
		} catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
		}
	}
	
	
	public void showPDFInscricao() {
		
		try {
		
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline=filename=file.pdf");
				
				ByteArrayOutputStream baos = inscricaoPDFService.generateStream(inscricoes);
				
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Pragma", "public");
				// setting the content type
				response.setContentType("application/pdf");
				// the contentlength
				response.setContentLength(baos.size());
				// write ByteArrayOutputStream to the ServletOutputStream
				ServletOutputStream os = response.getOutputStream();
				baos.writeTo(os);
				os.flush();
				os.close();
				context.responseComplete();
			} catch (NegocioException ne) {
				ne.printStackTrace();
				MessageUtil.erro(ne.getMessage());
			}catch (IOException e) {
				e.printStackTrace();
				MessageUtil.erro("Problema na escrita do PDF.");
			} catch (Exception ex) {
				ex.printStackTrace();
				MessageUtil.erro("Problema na geração do PDF.");
			}
	
			log.info("PDF gerado!");
	}
	
	public List<Inscricao> getInscricoes(){
		return inscricoes;
	}
	
	public Inscricao getInscricaoSelecionada() {
		return inscricaoSelecionada;
	}
	
	public void setInscricaoSelecionada(Inscricao inscricaoSelecionada) {
		this.inscricaoSelecionada = inscricaoSelecionada;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Mes getMes() {
		return mes;
	}

	public void setMes(Mes mes) {
		this.mes = mes;
	}

	public List<String> getAnos() {
		return anos;
	}

	public void setAnos(List<String> anos) {
		this.anos = anos;
	}

	public List<Mes> getMeses() {
		return meses;
	}

	public void setMeses(List<Mes> meses) {
		this.meses = meses;
	}

	public boolean isConsultado() {
		return consultado;
	}

	public void setConsultado(boolean consultado) {
		this.consultado = consultado;
	}
}
