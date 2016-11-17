package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;

@Entity
public class User extends AbstractDomainModel {

	@Required
	public String firstname;
	
	@Required
	public String lastname;
	
	@Required
	@Column(nullable=false)
	public String email;
	
	@Required
	@MinSize(6)
    @Column(nullable=false)
	public String password;
		
}