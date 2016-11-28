package jobs;

import java.util.ArrayList;
import java.util.List;

import com.jamonapi.utils.Logger;

import controllers.PerfilesConsumo;
import models.PConsumo;
import models.ree.perfiles.PerfilConsumo;
import play.Play;
import play.jobs.Job;
import service.ReeClient;
import util.CalendarUtil;
import util.XML;

public class UpdateAllCoeficientesJob extends Job<List<PerfilConsumo>>{

	@Override
	public List<PerfilConsumo> doJobWithResult(){
		
		List<PerfilConsumo> perfiles = new ArrayList<PerfilConsumo> ();
    	List<String> listfechas = CalendarUtil.getFechas7Dias(CalendarUtil.sumarRestarDiasFecha(CalendarUtil.FECHA_INICIAL_REE,2));
    	int i = 0;
    	for(String fecha : listfechas){
    		String url = String.format(Play.configuration.getProperty("ReeClient.PerfilesConsumo.baseUrl"), fecha);    	
    		PerfilConsumo object = ReeClient.getPerfiles(url);
          	
    		perfiles.add(object);
    		
          	PConsumo pc = new PConsumo();
            String xml = XML.writeValueAsString(object, false);
            String idMensaje = object.getIdentificacionMensaje().getV();        		
            pc.idArchivo =idMensaje;
            pc.fecha=object.getFechaHoraMensaje().getV().toGregorianCalendar().getTime();
            pc.xml = xml;
           	
           	if(PerfilesConsumo.existsInDB(pc)){
    			Logger.log("ELEMENTO YA EXISTE EN BD");
           	}else{
           		pc.save();
            }
           	Logger.log(i+" AÑADIDO UN ELEMENTO A LA TABLA!!");
           	i++;
    	}

		return perfiles;
	}
	
}
