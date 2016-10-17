package models;

import java.util.Date;

import javax.persistence.Entity;

import play.data.validation.MaxSize;
import play.data.validation.Required;

@Entity
public class Dia extends AbstractDomainModel {
	
	@Required
	public Date fecha;
	
	@Required
	public Boolean verano;
	
	@Required
	public Double precioMedioDiaA;
	
	@Required
	public Double precioMedioDiaDHAP2;
	
	@Required
	public Double precioMedioDiaDHAP1;
	
	@Required
	public Double precioMedioHorasA;
	
	@Required
	public Double precioMedioHorasDHA;
	
	@Required
	public Double sumatorioCoeficientesA;
	
	@Required
	public Double sumatorioCoeficientesDHA;
	
}
