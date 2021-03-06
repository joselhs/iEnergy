package controllers;

import models.User;

public class Security extends Secure.Security{
	
	public static boolean authenticate(String email, String password) {
		boolean allowed;
		
		User user = User.find("byEmail", email).first();
		
		if(user != null && user.password.equals(password)){
			allowed = true;
			renderArgs.put("userId", user.id);
		}else{
			allowed = false;
		}	
		
		return allowed;
    }
	
	static void onDisconnected() {
	    Application.precios();
	}
	
	static void onAuthenticated() {
	    Application.consumos();
	}
}
