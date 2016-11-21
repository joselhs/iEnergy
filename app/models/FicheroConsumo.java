package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.data.validation.Required;

@Entity
public class FicheroConsumo extends AbstractDomainModel{

	@Required
	public String mes;
	
	@Required
	public String año;
	
	@Required
	public Date fecha;
	
	@Required
	@ManyToOne
	public User usuario;
	
	@Required
	@MaxSize(1000000)
	public String json;
	
	@Required
	public Double consumoTotal;
	
	@Required
	public String consumoPorDiaSemana;
	
	@Required
	public Double porcentajeLaborables;
	
	@Required
	public Double porcentajeTramoBarato;
}
