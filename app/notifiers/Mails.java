package notifiers;

import java.util.Date;

import models.User;
import play.mvc.Mailer;
import util.CalendarUtil;

public class Mails extends Mailer {

	public static void resumen(User user, Double precioMasCaro, Double precioMasBarato,Integer horaMasBarata, 
			Integer horaMasCara, Double precioMasCaroDiscriminacion, Double precioMasBaratoDiscriminacion,
			Integer horaMasBarataDiscriminacion, Integer horaMasCaraDiscriminacion){
		
		Date hoy = new Date();
		
		setSubject("Resumen de precios del "+CalendarUtil.formatDate(hoy));
		addRecipient(user.email);
		setFrom("info@energy-saving.com");
		send(user,precioMasCaro,precioMasBarato,horaMasCara,horaMasBarata,precioMasCaroDiscriminacion,
				precioMasBaratoDiscriminacion,horaMasCaraDiscriminacion,horaMasBarataDiscriminacion);
	}
}
