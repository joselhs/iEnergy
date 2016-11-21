package controllers;

import com.jamonapi.utils.Logger;

import models.User;
import play.data.validation.Required;
import play.libs.Crypto;
import play.mvc.Before;
import play.mvc.Controller;

public class Login extends Controller{
	
	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("id", user.id);
            renderArgs.put("user", user.firstname);  
        }
    }
	
	public static void authenticate(@Required String email, String password) throws Throwable {
        
		User user = User.find("byEmail", email).first();
		
		if(user != null){
			// Mark user as connected
	        session.put("email", email);

	        renderArgs.put("id", user.id);
	        renderArgs.put("user", user);
	        
	        redirect("Application.consumos");
		}else{
			redirect("Application.login");
		}
        
        
    }
	
	public static void register(String firstname, String lastname, String email, String password){
		Logger.log("Username: "+firstname);
		Logger.log("Password: "+lastname);
		Logger.log("Username: "+email);
		Logger.log("Password: "+password);
		
		redirect("Application.login");
	}
	
	static boolean isConnected() {
        return session.contains("email");
    }
}
