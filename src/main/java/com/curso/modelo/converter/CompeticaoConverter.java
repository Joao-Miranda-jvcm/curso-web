package com.curso.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.curso.dao.CompeticaoDAO;
import com.curso.modelo.Competicao;
import com.curso.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Competicao.class)
public class CompeticaoConverter implements Converter {
	
	private CompeticaoDAO competicaoDAO;

	public CompeticaoConverter() {
		this.competicaoDAO = CDIServiceLocator.getBean(CompeticaoDAO.class);
	}
	
	@Override   
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Competicao retorno = null;

		if (value != null) {
			retorno = this.competicaoDAO.buscarPeloCodigo(Long.parseLong(value));
		}

		return retorno;
	}

	@Override  
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Competicao) value).getCodigo();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}
