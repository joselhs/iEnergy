package controllers;

import models.User;
import play.i18n.Messages;

public class Users extends AbstractBaseController{
	
	public static void create(String firstname, String lastname, String email, String password) {
    	
		User object = User.find("byEmail", email).first();
		
		if(object != null){
			validation.addError(password, "Ese email ya está registrado");
			redirect("Application.register");
		}else{
			object = new User();
	    	
	    	object.email=email;
	    	object.firstname=firstname;
	    	object.lastname=lastname;
	    	object.password=password;
	        
	        validation.valid(object);
	        if (validation.hasErrors()) {
	        	redirect("User.register");
	        }
	        object.save();
	        redirect("Secure.login");
		}
        
    }
}
