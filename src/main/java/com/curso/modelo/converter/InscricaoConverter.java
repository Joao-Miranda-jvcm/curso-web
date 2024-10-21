package com.curso.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.curso.dao.InscricaoDAO;
import com.curso.modelo.Inscricao;
import com.curso.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Inscricao.class)
public class InscricaoConverter implements Converter {
	
	private InscricaoDAO inscricaoDAO;
	
	public InscricaoConverter() {
		this.inscricaoDAO = CDIServiceLocator.getBean(InscricaoDAO.class);
	}
	
	@Override   
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Inscricao retorno = null;

		if (value != null) {
			retorno = this.inscricaoDAO.buscarPeloCodigo(Long.parseLong(value));
		}

		return retorno;
	}

	@Override  
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Inscricao) value).getCodigo();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}
