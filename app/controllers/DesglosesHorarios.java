package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Check;
import org.javatuples.Septet;

import com.jamonapi.utils.Logger;

import models.DesgloseHorario;
import models.PVPCHora;
import models.Person;
import models.ree.desgloses.PVPCDesgloseHorario;
import play.Play;
import play.i18n.Messages;
import play.libs.F.Promise;
import play.mvc.With;
import service.ReeClient;
import util.CalendarUtil;
import util.XML;

//@With(Secure.class)
//@CRUD
public class DesglosesHorarios extends AbstractBaseController{

		
//	public static void getXML(String fileName,String fileType, String idioma){
//		PVPCDesgloseHorario object = ReeClient.getDesglose(fileName, fileType, idioma);
//		DesgloseHorario pvpc = new DesgloseHorario();
//		String xml = XML.writeValueAsString(object, false);
//		String idMensaje = object.getIdentificacionMensaje().getV();
//		
//		//Formateado del intervalo para obtener fecha
//		String intervalostring = object.getHorizonte().getV();
//		String fecha = CalendarUtil.formatFechaHorizonte(intervalostring);
//		
//		
//		pvpc.idArchivo =idMensaje;
//		pvpc.fecha=CalendarUtil.parseFecha(fecha);
//		pvpc.xml = xml;
//		
//		if(existsInDB(pvpc)){
//
//		}else{
//			pvpc.save();
//		}
		
		
//		List<Septet<String,String,String,String,String,String,Boolean>> diashorasprecios = ReeClient.getPreciosHoras(object);
//		ReeClient.setPreciosHoras(diashorasprecios);
//		ReeClient.setPrecioMedioDiaHora(diashorasprecios);	
//		ReeClient.calculaPrecioMedioDia(diashorasprecios);
		
		//Parámetro 1=Lunes,...,7=Domingo
//		Logger.log(ReeClient.calculaMediaDiasAño(7));
		
		
//		ReeClient.getPrecioMasBaratoCaroDia(pvpc.fecha);
//		ReeClient.getPrecioMasBaratoCaroDiaIntervalo(pvpc.fecha,17,20);
//		ReeClient.getPrecioActual();
//		ReeClient.getMaximaDiferenciaPrecios(pvpc.fecha);
//		
//		Collection horas = ReeClient.ordenaHorasPrecio().values();
//		for(Object o : horas){
//			Logger.log(ReeClient.horario.get(o));
//		}
//    }
	
	public static boolean existsInDB(DesgloseHorario pvpc){
		
		if(!DesgloseHorario.find("byIdArchivo", pvpc.idArchivo).fetch().isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	
    public static void delete(Integer id) {
        deleteModel(DesgloseHorario.class, id);
    }

    public static void save(Integer id, DesgloseHorario entry, boolean goBack, Integer objectId) {
    	DesgloseHorario object = bindModel(DesgloseHorario.class, id);
        
        validation.valid(object);
        if (validation.hasErrors()) {
//            renderShow(object);
        }
        object.save();
        flash.success(Messages.get("crud.saved", object.getLabel()));
        if (goBack) {
//            show(object.id);
        } else {
            redirect(request.controller + ".list");
        }
    }
    
    //TODO Ejecutar funcion UpdateValues con las fechas pasadas desde el formulario updatevalues.html del CRUD
    
    public static void updateValues(Integer id) {
    	DesgloseHorario object = bindModel(DesgloseHorario.class, id);
    	updateDates(object);
    	    
    }
    
    public static void updateDates(DesgloseHorario object){
    	notFoundIfNull(object);
    	String [] body = params.get("body").split("=");
    	if(body.length>2){
    		String paramfechas = body[2];
        	paramfechas = paramfechas.replace("%2F", "/").replace("%2C", ",");
//        	List<String> fechas = new ArrayList<String>();
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
	        		
	        		String url = String.format(Play.configuration.getProperty("ReeClient.baseUrl"), fecha);    	
	               	PVPCDesgloseHorario object = ReeClient.getDesgloses(url);
	        	
	              	DesgloseHorario pvpc = new DesgloseHorario();
	                String xml = XML.writeValueAsString(object, false);
	                String idMensaje = object.getIdentificacionMensaje().getV();  
	                
	                //Formateado del intervalo para obtener fecha
	        		String intervalostring = object.getHorizonte().getV();
	        		String fecha1 = CalendarUtil.formatFechaHorizonte(intervalostring);
	                
	               	pvpc.idArchivo =idMensaje;
	               	pvpc.fecha=CalendarUtil.parseFecha(fecha1);
	               	pvpc.xml = xml;
	               	
	               	if(DesglosesHorarios.existsInDB(pvpc)){
	        		
	               	}else{
	               		pvpc.save();
	                }
	               	
	               	List<Septet<String,String,String,String,String,String,Boolean>> diashorasprecios = ReeClient.getPreciosHoras(object);
	        		ReeClient.setPreciosHoras(diashorasprecios);
	        		ReeClient.calculaPrecioMedioDia(diashorasprecios);
	        		
	        		
	    	}
	    	flash.success(Messages.get("crud.updated"));
	    	redirect(request.controller + ".list");
	    			
    	}else{
    		redirect(request.controller + ".renderupdate");
    	}	
		 	
    }
    
//    public static void updateAllXML(){
//    	
//    	
//    	double t1, t2;
//        t1 = System.currentTimeMillis();
//    	
//    	Promise<List<PVPCDesgloseHorario>> promise = new UpdateAllPreciosJob().now();
//    	List<PVPCDesgloseHorario> lista = await(promise);
//    	ReeClient.updateAllPreciosHoras(lista);
//    	Logger.log("FINNNN!");
//    	Logger.log("######################################################################");
//    	t2 = System.currentTimeMillis();
//    	
//    	Logger.log("Tiempo de inicio: "+t1);
//		Logger.log("Tiempo de fin: "+t2);
//		Logger.log("Tiempo total: "+((t2-t1)/1000.0)/60+"min");
//		Logger.log("######################################################################");
//    	flash.success(Messages.get("crud.updated"));
//    	redirect(request.controller + ".list");
//    }

}
