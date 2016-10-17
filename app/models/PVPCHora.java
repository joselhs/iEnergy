package models;

import java.util.Date;

import javax.persistence.Entity;

import play.data.validation.MaxSize;
import play.data.validation.Required;

@Entity
public class PVPCHora extends AbstractDomainModel {

	@Required
	public int hora;
	
	@Required
	public Date fecha;
	
	@Required
	public Double TCUhA;
	
	@Required
	public Double TCUhDHA;
	
	@Required
	public Double TEUhA;
	
	@Required
	public Double TEUhDHA;

}
