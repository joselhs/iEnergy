package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import javax.persistence.Query;

import com.jamonapi.utils.Logger;

import models.PConsumo;
import models.PVPCHora;
import models.CoeficientePerfiladoHora;
import models.DesgloseHorario;
import models.Dia;
import play.db.jpa.JPA;
import play.i18n.Messages;
import play.mvc.With;
import service.PreciosService;
import service.ReeClient;
import util.CalendarUtil;


public class Dias extends AbstractBaseController{

    public static void delete(Integer id) {
        deleteModel(Dia.class, id);
    }

    public static void save(Integer id, Dia entry, boolean goBack, Integer objectId) {
    	Dia object = bindModel(Dia.class, id);
        
        validation.valid(object);
        if (validation.hasErrors()) {

        }
        object.save();
        flash.success(Messages.get("crud.saved", object.getLabel()));
        
    }
    
    public static boolean existsInDB(Dia d){
		
		if(!Dia.find("byFecha", d.fecha).fetch().isEmpty()){
			return true;
		}else{
			return false;
		}
	}
    
    
    public static String getGraficaDiaSeleccionado(String fecha){
    	//Formateando Fecha
    	String[] subString = fecha.split("-");
    	String fechaFormateada = subString[2]+"/"+subString[1]+"/"+subString[0];
    	Date date = CalendarUtil.parseFecha(subString[2]+"/"+subString[1]+"/"+subString[0]);
    	
    	String preciosHoy = PreciosService.getChartDiaString(date);
        String preciosHoyDisc = PreciosService.getChartDiaDiscriminacionString(date);
        
    	return preciosHoy+"/"+preciosHoyDisc;
    }
    
    
    public static Double getPrecioMasBarato(Date date){
		 String fecha = CalendarUtil.formatFecha(date, "dd/MM/yyyy");
		 date = CalendarUtil.parseFecha(fecha);
		 
		 Double baratoA = new Double(1);

		 Dia dia = Dia.find("byFecha", date).first();
		 if(dia == null){
			 baratoA = 0.0;
		 }else{
			List<PVPCHora> precios = PVPCHora.find("byFecha", dia.fecha).fetch();
			 
			for(PVPCHora precio : precios){
				if(precio.TCUhA+precio.TEUhA < baratoA){
					baratoA = precio.TCUhA+precio.TEUhA;
				}
			}
		 }
		 
		 return baratoA;
	 }
	 
    public static Double getPrecioMasCaro(Date date){
		 String fecha = CalendarUtil.formatFecha(date, "dd/MM/yyyy");
		 date = CalendarUtil.parseFecha(fecha);
		 
		 Double caroA = new Double(0);

		 Dia dia = Dia.find("byFecha", date).first();
		 if(dia == null){
			 caroA = 0.0;
		 }else{
			List<PVPCHora> precios = PVPCHora.find("byFecha", dia.fecha).fetch();
			 
			for(PVPCHora precio : precios){
				if(precio.TCUhA+precio.TEUhA > caroA){
					caroA = precio.TCUhA+precio.TEUhA;
				}
			}
		 }
		 
		 return caroA;
	 }
	 
    public static Double getPrecioMasBaratoDiscriminacion(Date date){
		 String fecha = CalendarUtil.formatFecha(date, "dd/MM/yyyy");
		 date = CalendarUtil.parseFecha(fecha);
		 
		 Double baratoA = new Double(1);

		 Dia dia = Dia.find("byFecha", date).first();
		 if(dia == null){
			 baratoA = 0.0;
		 }else{
			List<PVPCHora> precios = PVPCHora.find("byFecha", dia.fecha).fetch();
			 
			for(PVPCHora precio : precios){
				if(precio.TCUhDHA+precio.TEUhDHA < baratoA){
					baratoA = precio.TCUhDHA+precio.TEUhDHA;
				}
			}
		 }
		 
		 return baratoA;
	 }
	 
   public static Double getPrecioMasCaroDiscriminacion(Date date){
		 String fecha = CalendarUtil.formatFecha(date, "dd/MM/yyyy");
		 date = CalendarUtil.parseFecha(fecha);
		 
		 Double caroA = new Double(0);

		 Dia dia = Dia.find("byFecha", date).first();
		 if(dia == null){
			 caroA = 0.0;
		 }else{
			List<PVPCHora> precios = PVPCHora.find("byFecha", dia.fecha).fetch();
			 
			for(PVPCHora precio : precios){
				if(precio.TCUhDHA+precio.TEUhDHA > caroA){
					caroA = precio.TCUhDHA+precio.TEUhDHA;
				}
			}
		 }
		 
		 return caroA;
	 }
   
   
   public static Integer getHoraMasBarata(Date date){
		 String fecha = CalendarUtil.formatFecha(date, "dd/MM/yyyy");
		 date = CalendarUtil.parseFecha(fecha);
		 
		 Double baratoA = new Double(1);
		 Integer hora = 0;

		 Dia dia = Dia.find("byFecha", date).first();
		 if(dia == null){
			 baratoA = 0.0;
		 }else{
			List<PVPCHora> precios = PVPCHora.find("byFecha", dia.fecha).fetch();
			 
			for(PVPCHora precio : precios){
				if(precio.TCUhA+precio.TEUhA < baratoA){
					baratoA = precio.TCUhA+precio.TEUhA;
					hora = precio.hora;
				}
			}
		 }
		 
		 return hora;
	 }
	 
  public static Integer getHoraMasCara(Date date){
		 String fecha = CalendarUtil.formatFecha(date, "dd/MM/yyyy");
		 date = CalendarUtil.parseFecha(fecha);
		 
		 Double caroA = new Double(0);
		 Integer hora = 0;

		 Dia dia = Dia.find("byFecha", date).first();
		 if(dia == null){
			 caroA = 0.0;
		 }else{
			List<PVPCHora> precios = PVPCHora.find("byFecha", dia.fecha).fetch();
			 
			for(PVPCHora precio : precios){
				if(precio.TCUhA+precio.TEUhA > caroA){
					caroA = precio.TCUhA+precio.TEUhA;
					hora = precio.hora;
				}
			}
		 }
		 
		 return hora;
	 }
	 
  	public static Integer getHoraMasBarataDiscriminacion(Date date){
		 String fecha = CalendarUtil.formatFecha(date, "dd/MM/yyyy");
		 date = CalendarUtil.parseFecha(fecha);
		 
		 Double baratoA = new Double(1);
		 Integer hora = 0;
		 
		 Dia dia = Dia.find("byFecha", date).first();
		 if(dia == null){
			 baratoA = 0.0;
		 }else{
			List<PVPCHora> precios = PVPCHora.find("byFecha", dia.fecha).fetch();
			 
			for(PVPCHora precio : precios){
				if(precio.TCUhDHA+precio.TEUhDHA < baratoA){
					baratoA = precio.TCUhDHA+precio.TEUhDHA;
					hora = precio.hora;
				}
			}
		 }
		 
		 return hora;
	 }
	 
	 public static Integer getHoraMasCaraDiscriminacion(Date date){
			 String fecha = CalendarUtil.formatFecha(date, "dd/MM/yyyy");
			 date = CalendarUtil.parseFecha(fecha);
			 
			 Double caroA = new Double(0);
			 Integer hora = 0;
			 Dia dia = Dia.find("byFecha", date).first();
			 if(dia == null){
				 caroA = 0.0;
			 }else{
				List<PVPCHora> precios = PVPCHora.find("byFecha", dia.fecha).fetch();
				 
				for(PVPCHora precio : precios){
					if(precio.TCUhDHA+precio.TEUhDHA > caroA){
						caroA = precio.TCUhDHA+precio.TEUhDHA;
						hora=precio.hora;
					}
				}
			 }
			 
		return hora;
	}
 
	public static String getDiaMasBarato(){
	 	Double media = new Double(0);
	 	Double mediaMasBarata = new Double(1);
	 	String diaMasBarato = "";
	 	Integer diaMasBaratoInt = 0;
	 	
	 	for(int i=0;i<7;i++){
	 		//Para calcular medias desde origen usar ReeClient.calculaMediaDiasAño
	 		media = ReeClient.calculaMediaDiasAño(i);
	 		
	 		if(media < mediaMasBarata){
	 			mediaMasBarata=media;
	 			diaMasBaratoInt=i;
	 		}
	 	}
	 	
	 	switch(diaMasBaratoInt){
	 	case 0:
	 		diaMasBarato="Lunes";
	 		break;
	 	case 1:
	 		diaMasBarato="Martes";
	 		break;
	 	case 2:
	 		diaMasBarato="Miércoles";
	 		break;
	 	case 3:
	 		diaMasBarato="Jueves";
	 		break;
	 	case 4:
	 		diaMasBarato="Viernes";
	 		break;
	 	case 5:
	 		diaMasBarato="Sábado";
	 		break;
	 	case 6:
	 		diaMasBarato="Domingo";
	 		break;
	 	}
	 	
	 	return diaMasBarato;
	}


	public static String getDiaMasCaro(){
	 	Double media = new Double(0);
	 	Double mediaMasCara = new Double(0);
	 	String diaMasCaro = "";
	 	Integer diaMasCaroInt = 0;
	 	
	 	for(int i=0;i<7;i++){
	 		media = ReeClient.calculaMediaDiasAño(i);
	 		
	 		if(media > mediaMasCara){
	 			mediaMasCara=media;
	 			diaMasCaroInt=i;
	 		}
	 	}
	 	
	 	switch(diaMasCaroInt){
	 	case 0:
	 		diaMasCaro="Lunes";
	 		break;
	 	case 1:
	 		diaMasCaro="Martes";
	 		break;
	 	case 2:
	 		diaMasCaro="Miércoles";
	 		break;
	 	case 3:
	 		diaMasCaro="Jueves";
	 		break;
	 	case 4:
	 		diaMasCaro="Viernes";
	 		break;
	 	case 5:
	 		diaMasCaro="Sábado";
	 		break;
	 	case 6:
	 		diaMasCaro="Domingo";
	 		break;
	 	}
	 	
	 	return diaMasCaro;
	}
 
    
}
