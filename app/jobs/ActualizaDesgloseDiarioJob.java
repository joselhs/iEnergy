package jobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.javatuples.Quartet;
import org.javatuples.Septet;

import controllers.DesglosesHorarios;
import models.DesgloseHorario;
import models.PConsumo;
import models.ree.desgloses.PVPCDesgloseHorario;
import play.Logger;
import play.Play;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.On;
import service.ReeClient;
import util.CalendarUtil;
import util.XML;

@On("0 04 0 * * ?")
public class ActualizaDesgloseDiarioJob extends Job {
	
	public void doJob() {	
		getDesglose();
	}
	
	private void getDesglose(){
		String url = String.format(Play.configuration.getProperty("ReeClient.baseUrl"), getDayDate());
				
		PVPCDesgloseHorario object = ReeClient.getDesgloses("https://api.esios.ree.es/archives/80/download?date=2016-02-01");
		
		DesgloseHorario pvpc = new DesgloseHorario();
		String xml = XML.writeValueAsString(object, false);
		String idMensaje = object.getIdentificacionMensaje().getV();
		
		//Formateado del intervalo para obtener fecha
		String intervalostring = object.getHorizonte().getV();
		String fecha = CalendarUtil.formatFechaHorizonte(intervalostring);
		
		pvpc.idArchivo =idMensaje;
		pvpc.fecha=CalendarUtil.parseFecha(fecha);
		pvpc.xml = xml;
		
		
		if(DesglosesHorarios.existsInDB(pvpc)){
			Logger.info("\nDesglose ya existe en la BD\n");
		}else{
			pvpc.save();
			Logger.info("\nDesglose guardado en DB\n");
		}
	
		List<Septet<String,String,String,String,String,String,Boolean>> diashorasprecios = ReeClient.getPreciosHoras(object);
		
		ReeClient.setPreciosHoras(diashorasprecios);
		ReeClient.calculaPrecioMedioDia(diashorasprecios);
		
	}
	
	
	private String getDayDate(){
		DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		return formatoFecha.format(new Date());
	}
	
}
