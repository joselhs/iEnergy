package service;

import java.util.Date;
import java.util.List;

import models.PVPCHora;
import util.CalendarUtil;

public class PreciosService {

	public static String getChartDiaString(Date date){
    	String data = "[";
    	
        String fecha = CalendarUtil.formatFecha(date, "dd/MM/yyyy");
		date = CalendarUtil.parseFecha(fecha);
		
        List<PVPCHora> precios = PVPCHora.find("byFecha", date).fetch();
        if(precios.isEmpty()){
        	data="[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]";
        }else{
        	for(PVPCHora precio : precios){
            	Double valor = precio.TCUhA+precio.TEUhA;
            	
            	String valorstring = valor.toString();
            	valorstring=valorstring.substring(0, 8);
            	data+=valorstring+",";
            }
        	
            data=data.substring(0,data.length()-1);
            data+="]";
        }
        
        
    	return data;
    }
	
	public static String getChartMediaDiasString(){
    	String mediaDias = "[";
    	Double media = new Double(0);
    	
    	for(int i=0;i<7;i++){
    		//Para calcular medias desde origen usar ReeClient.calculaMediaDiasAño
    		media = ReeClient.calculaMediaDiasAño(i);
    		if(media == null){
    			media=0.0;
    		}
    		String stringmedia = media.toString();
    		
    		if(media != 0.0){
    			stringmedia=stringmedia.substring(0, 8);
    		}
    		mediaDias+=stringmedia+",";
    	}
    	
    	mediaDias=mediaDias.substring(0,mediaDias.length()-1);
    	mediaDias+="]";
    	
    	return mediaDias;
    }
	
}
