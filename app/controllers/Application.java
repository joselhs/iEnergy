package controllers;

import play.*;
import play.mvc.*;
import service.PreciosService;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void precios() {
        String preciosHoy = PreciosService.getChartDiaString(new Date());
    	
    	render(preciosHoy);
    }

}