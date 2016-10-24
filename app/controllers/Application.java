package controllers;

import play.*;
import play.mvc.*;
import service.PreciosService;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void precios(){
        String preciosHoy = PreciosService.getChartDiaString(new Date());
    	String mediasDiasSemana = PreciosService.getChartMediaDiasString();
    	
    	render(preciosHoy,mediasDiasSemana);
    }
    
    public static void consumos(){
    	render();
    }
    
    public static void resumen(){
    	render();
    }

}