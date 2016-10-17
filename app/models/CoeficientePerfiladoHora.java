package models;

import java.util.Date;

import javax.persistence.Entity;

import play.data.validation.MaxSize;
import play.data.validation.Required;

@Entity
public class CoeficientePerfiladoHora extends AbstractDomainModel {

	@Required
	public int hora;
	
	@Required
	public Date fecha;
	
	@Required
	public Double coeficienteA;
	
	@Required
	public Double coeficienteDHA;

}
