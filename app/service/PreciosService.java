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
	
}
