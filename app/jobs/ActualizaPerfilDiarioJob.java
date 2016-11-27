package jobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.javatuples.Quartet;
import org.javatuples.Septet;

import controllers.PerfilesConsumo;
import models.DesgloseHorario;
import models.PConsumo;
import models.ree.desgloses.PVPCDesgloseHorario;
import models.ree.perfiles.PerfilConsumo;
import play.Logger;
import play.Play;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.On;
import service.ReeClient;
import util.CalendarUtil;
import util.XML;

@On("0 02 0 * * ?")
public class ActualizaPerfilDiarioJob extends Job {
	
	public void doJob() {	
		getPerfil();
	}
	
	
	private void getPerfil(){
		
		Logger.info("\nInicio función getPerfil()\n");
		String url = String.format(Play.configuration.getProperty("ReeClient.PerfilesConsumo.baseUrl"), getDayDate());
		Logger.info("\nURL solicitada="+url+"\n");		
		PerfilConsumo object = ReeClient.getPerfiles("https://api.esios.ree.es/archives/78/download?date=2016-02-01");
		
		PConsumo pc = new PConsumo();
		
		
		String xml = XML.writeValueAsString(object, false);
		String idMensaje = object.getIdentificacionMensaje().getV();
		
		pc.idArchivo =idMensaje;
		pc.fecha=object.getFechaHoraMensaje().getV().toGregorianCalendar().getTime();
		pc.xml = xml;
		
		if(PerfilesConsumo.existsInDB(pc)){
			Logger.info("\nPerfil YA existe en BD\n");
		}else{
			pc.save();
			Logger.info("\nPerfil guardado en BD\n");
		}
		
		List<Quartet<String,String,String,String>> coeficientes = ReeClient.getCoeficientesPerfilado(object);
		ReeClient.setCoeficientesPerfilado(coeficientes);
    }
	
	private String getDayDate(){
		DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		return formatoFecha.format(new Date());
	}
	

}
