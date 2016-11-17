package controllers;

import play.mvc.*;
import service.PreciosService;

import java.util.*;

import com.jamonapi.utils.Logger;

import models.*;

public class Application extends Controller {

	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.firstname);
        }
    }
	
    public static void precios(){
        String preciosHoy = PreciosService.getChartDiaString(new Date());
        String preciosHoyDisc = PreciosService.getChartDiaDiscriminacionString(new Date());
    	String mediasDiasSemana = PreciosService.getChartMediaDiasString();
    	String mediasMeses = PreciosService.getPreciosMesesAño();
    	
    	render(preciosHoy, preciosHoyDisc, mediasDiasSemana, mediasMeses);
    }
    
    public static void consumos(){
    	render();
    }
    
    public static void resumen(){
    	Double precioBaratoA = Dias.getPrecioMasBarato(new Date());
    	Double precioCaroA = Dias.getPrecioMasCaro(new Date());
    	Double precioBaratoDHA = Dias.getPrecioMasBaratoDiscriminacion(new Date());
    	Double precioCaroDHA = Dias.getPrecioMasCaroDiscriminacion(new Date());
    	Integer horaBarataA = Dias.getHoraMasBarata(new Date());
    	Integer horaCaraA = Dias.getHoraMasCara(new Date());
    	Integer horaBarataDHA = Dias.getHoraMasBarataDiscriminacion(new Date());
    	Integer horaCaraDHA = Dias.getHoraMasCaraDiscriminacion(new Date());
    	String diaMasBarato = Dias.getDiaMasBarato();
    	String diaMasCaro = Dias.getDiaMasCaro();

    	
    	render(precioBaratoA,precioCaroA,precioBaratoDHA,precioCaroDHA, horaBarataA, horaCaraA, 
    			horaBarataDHA, horaCaraDHA, diaMasBarato, diaMasCaro);
    }
    
    public static void register(){
    	render();
    }

}