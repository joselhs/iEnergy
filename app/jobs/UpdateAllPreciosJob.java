package jobs;

import java.util.ArrayList;
import java.util.List;

import com.jamonapi.utils.Logger;

import controllers.DesglosesHorarios;
import controllers.PerfilesConsumo;
import models.DesgloseHorario;
import models.PConsumo;
import models.ree.desgloses.PVPCDesgloseHorario;
import models.ree.perfiles.PerfilConsumo;
import play.Play;
import play.jobs.Job;
import service.ReeClient;
import util.CalendarUtil;
import util.XML;

public class UpdateAllPreciosJob extends Job<List<PVPCDesgloseHorario>>{

	@Override
	public List<PVPCDesgloseHorario> doJobWithResult(){
		
		List<String> listfechas = CalendarUtil.getFechas(CalendarUtil.FECHA_INICIAL_REE);
    	List<PVPCDesgloseHorario> desgloses = new ArrayList<PVPCDesgloseHorario> ();
    	int i = 0;
    	
    	for(String fecha : listfechas){
    		String url = String.format(Play.configuration.getProperty("ReeClient.baseUrl"), fecha);    	
           	
    		PVPCDesgloseHorario object = ReeClient.getDesgloses(url);
          	desgloses.add(object);
          	
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
           	
           	Logger.log(i+" AÑADIDO UN ELEMENTO A LA TABLA!!");
           	i++;	
    	}

		return desgloses;
	}
	
}
