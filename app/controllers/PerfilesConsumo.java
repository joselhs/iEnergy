package controllers;

import java.util.List;

import org.javatuples.Quartet;

import com.jamonapi.utils.Logger;

import jobs.UpdateAllCoeficientesJob;
import models.PConsumo;
import models.ree.perfiles.PerfilConsumo;
import play.Play;
import play.i18n.Messages;
import play.libs.F.Promise;
import play.mvc.With;
import service.ReeClient;
import util.CalendarUtil;
import util.XML;

public class PerfilesConsumo extends AbstractBaseController{


	public static boolean existsInDB(PConsumo pc){
		
		if(!PConsumo.find("byIdArchivo", pc.idArchivo).fetch().isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
    public static void delete(Integer id) {
        deleteModel(PConsumo.class, id);
    }

    public static void save(Integer id) {
    	PConsumo object = bindModel(PConsumo.class, id);
        
        validation.valid(object);
        if (validation.hasErrors()) {
        	Logger.log("ERROR");
        }
        object.save();
        flash.success(Messages.get("crud.saved", object.getLabel()));
        
    }
    
    //TODO Ejecutar funcion UpdateValues con las fechas pasadas desde el formulario updatevalues.html del CRUD
    
    public static void updateValues(Integer id) {
    	PConsumo object = bindModel(PConsumo.class, id);
    	updateDates(object);
    	    
    }
    
    public static void updateDates(PConsumo object){
    	notFoundIfNull(object);

    	String [] body = params.get("body").split("=");
    	if(body.length>2){
    		String paramfechas = body[2];
        	paramfechas = paramfechas.replace("%2F", "/").replace("%2C", ",");
        	String [] fechas = paramfechas.split(",");
        	
        	updateXML(fechas);
        	
    	}else{
    		flash.success(Messages.get("Inserte alguna fecha."));
    	}
    	
    	render();
        
    }
   
    public static void updateXML(String[] fechas){
    	
    	if(fechas != null){
	    	for(String fecha : fechas){
	    		
	    			fecha = CalendarUtil.formatFecha(CalendarUtil.parseFecha(fecha),"yyyyMMdd");
	        		
	        		String url = String.format(Play.configuration.getProperty("ReeClient.PerfilesConsumo.baseUrl"), fecha);    	
	        		PerfilConsumo object = ReeClient.getPerfiles(url);
	              		
	               	PConsumo pc = new PConsumo();
	                String xml = XML.writeValueAsString(object, false);
	                String idMensaje = object.getIdentificacionMensaje().getV();        		
	                pc.idArchivo =idMensaje;
	                pc.fecha=object.getFechaHoraMensaje().getV().toGregorianCalendar().getTime();
	                pc.xml = xml;
	               	
	               	if(PerfilesConsumo.existsInDB(pc)){

	               	}else{
	               		pc.save();
	               	}
	        			
	               	List<Quartet<String,String,String,String>> coeficientes = ReeClient.getCoeficientesPerfilado(object);
	        		ReeClient.setCoeficientesPerfilado(coeficientes);
	        		
	    	}
	    	flash.success(Messages.get("crud.updated"));
	    	redirect(request.controller + ".list");
	    			
    	}else{
    		redirect(request.controller + ".renderupdate");
    	}	
		 	
    }
    
    
    public static void updateAllXML(){

    	double t1, t2;
        t1 = System.currentTimeMillis();
    	
    	Promise<List<PerfilConsumo>> promise = new UpdateAllCoeficientesJob().now();
    	List<PerfilConsumo> lista = await(promise);
    	ReeClient.updateAllCoeficientesHoras(lista);
    	Logger.log("FINNNN!");
    	Logger.log("######################################################################");
    	t2 = System.currentTimeMillis();
    	
    	Logger.log("Tiempo de inicio: "+t1);
		Logger.log("Tiempo de fin: "+t2);
		Logger.log("Tiempo total: "+((t2-t1)/1000.0)/60+"min");
		Logger.log("######################################################################");
		flash.success(Messages.get("crud.updated"));
    	
    }
    
}
