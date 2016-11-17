package jobs;

import java.util.Date;
import java.util.List;

import controllers.Dias;
import models.User;
import notifiers.Mails;
import play.jobs.Job;
import play.jobs.On;


@On("0 20 20 * * ?")
public class sendMailJob extends Job{

	public void doJob(){
		sendMails();
	}
	
	private void sendMails(){
		
		List<User> users = User.findAll();
		Date hoy = new Date();
		
		Double precioMasCaro = Dias.getPrecioMasCaro(hoy);
		Double precioMasBarato = Dias.getPrecioMasBarato(hoy);
		Integer horaMasCara = Dias.getHoraMasCara(hoy);
		Integer horaMasBarata = Dias.getHoraMasBarata(hoy);
		
		Double precioMasCaroDiscriminacion = Dias.getPrecioMasCaroDiscriminacion(hoy);
		Double precioMasBaratoDiscriminacion = Dias.getPrecioMasBaratoDiscriminacion(hoy);
		Integer horaMasCaraDiscriminacion = Dias.getHoraMasCaraDiscriminacion(hoy);
		Integer horaMasBarataDiscriminacion = Dias.getHoraMasBarataDiscriminacion(hoy);
		
		for(User user : users){
			Mails.resumen(user, precioMasCaro, precioMasBarato, horaMasBarata, horaMasCara, 
					precioMasCaroDiscriminacion, precioMasBaratoDiscriminacion, horaMasBarataDiscriminacion, 
					horaMasCaraDiscriminacion);
		}
		
	}
}
