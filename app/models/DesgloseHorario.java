package models;

import java.util.Date;

import javax.persistence.Entity;

import play.data.validation.MaxSize;
import play.data.validation.Required;

@Entity
public class DesgloseHorario extends AbstractDomainModel {

	@Required
	public String idArchivo;
	
	@Required
	public Date fecha;
	
	@Required
	@MaxSize(1000000)
	public String xml;
		
}
