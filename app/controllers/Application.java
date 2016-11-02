package controllers;

import play.*;
import play.mvc.*;
import service.PreciosService;

import java.util.*;

import models.*;

public class Application extends Controller {

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
    	render();
    }

}