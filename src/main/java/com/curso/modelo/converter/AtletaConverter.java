package com.curso.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.curso.dao.AtletaDAO;
import com.curso.modelo.Atleta;
import com.curso.util.cdi.CDIServiceLocator;



@FacesConverter(forClass=Atleta.class)
public class AtletaConverter implements Converter {

	private AtletaDAO atletaDAO;
	
	public AtletaConverter() {
		this.atletaDAO = CDIServiceLocator.getBean(AtletaDAO.class);
	}
	
	@Override    //converte tipo String para objeto - necessário mapear do modelo relacional para obj
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Atleta retorno = null;

		if (value != null) {
			retorno = this.atletaDAO.buscarPeloCodigo(Long.parseLong(value));
		}

		return retorno;
	}

	@Override  //converte de objeto para codigo - necessário mapear do modelo obj para relacional
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Atleta) value).getCodigo();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}