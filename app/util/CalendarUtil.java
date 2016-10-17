package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.jamonapi.utils.Logger;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class CalendarUtil {
	
	final static long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
	public final static Date FECHA_INICIAL_REE = new Date(1396352994958L);
	
	private static SimpleDateFormat xsalesAttrSdf = new SimpleDateFormat("dd.MM.yyyy");;

	public static XMLGregorianCalendar getXMLGregorianCalendar(Date time) {
		if(time == null) {
			return null;
		}
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(time);
		return new XMLGregorianCalendarImpl(gcal);
	}
	
	public static String formatDate(Date date) {
		if(date == null) {
			return null;
		}
	    return xsalesAttrSdf.format(date);
    }
	
	public static Date parseDate(String str) {
		if(str == null) {
			return null;
		}
	    try {
	        return xsalesAttrSdf.parse(str);
        } catch (ParseException e) {
        	return null;
        }
    }
    
    public static Date parseFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } 
        catch (ParseException ex) 
        {
            System.out.println(ex);
        }
        return fechaDate;
    }
    
    public static String formatFecha(Date date, String formato){
    	SimpleDateFormat formatoFecha = new SimpleDateFormat(formato);
    	return formatoFecha.format(date);
    }
    
    public static Date sumarRestarDiasFecha(Date fecha, int dias){
    	 
    	Calendar calendar = Calendar.getInstance();
    
    	calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
    	
    	return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    
    	 
    		
   	}
    
    public static Integer getNumeroDias(Date fecha){
    	
    	Date hoy = new Date();
    	
    	Integer numeroDias= (int) ((hoy.getTime() - fecha.getTime() )/ MILLSECS_PER_DAY);
    	
    	return numeroDias;
    }
    

   
    public static List<String> getFechas(Date fechaInicio){
    	
    	List<String> fechas = new ArrayList<String>();
    	
    	Integer ndias = getNumeroDias(fechaInicio);
    	
    	for(int i=0;i<=ndias+1;i++){
    		
    		Date fecha = sumarRestarDiasFecha(fechaInicio,i);
    		fechas.add(formatFecha(fecha,"yyyMMdd"));
    	}
    	
//    	Logger.log(fechas);
    	
    	return fechas;
    }
    
    public static List<String> getFechas7Dias(Date fechaInicio){
    	
    	List<String> fechas = new ArrayList<String>();
    	
    	Integer ndias = getNumeroDias(fechaInicio);
    	
    	for(int i=0;i<=ndias+7;i+=7){
    		
    		Date fecha = sumarRestarDiasFecha(fechaInicio,i);
    		fechas.add(formatFecha(fecha,"yyyMMdd"));
    	}
    	
    	return fechas;
    }
    
    public static String formatFechaHorizonte(String fecha){
    	
		String[] intervalotiempo = fecha.split("/");
		String[] substring = intervalotiempo[1].split("T");
		String fechaFormateada = substring[0];
		fechaFormateada=fechaFormateada.replace("-", "/");
		String[] stringfecha = fechaFormateada.split("/");
		fechaFormateada=stringfecha[2]+"/"+stringfecha[1]+"/"+stringfecha[0];
		
		return fechaFormateada;
    }

    public static Boolean esVerano(String fecha){
    	Boolean verano; 
    	String[] intervalotiempoArray = fecha.split("/");
		 String[] substring1= intervalotiempoArray[1].split("T");
		 if( substring1[1].equals("22:00Z")){
			 verano = true;
		 }else{
			 verano = false;
		 }
		 
		 return verano;
    }
    
    public static int getYear(Date date){

    	if (null == date){
    		return 0;
    	}else{
    		String formato="yyyy";
    		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
    		return Integer.parseInt(dateFormat.format(date));
    	}

    }
    
    public static int getMonth(Date date){

    	if (null == date){
    		return 0;
    	}
    	else{
    		String formato="MM";
    		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
    		return Integer.parseInt(dateFormat.format(date));
    	}

    }
    
    //Devuelve los días indicados desde hace un número de días antes.
    public static List<String> getDiasDesde(int numeroDias){
    	List<String> dias = new ArrayList<String>();
    	
    	Date fechaOrigen = sumarRestarDiasFecha(new Date(),numeroDias);
    	
    	dias=getFechas(fechaOrigen);
    	dias.remove(dias.size()-1);

    	return dias;
    }
    
    //Devuelve el día de la semana de una fecha 
    //(1-->Domingo, 2-->Lunes,...,7-->Sabado)
    public static int getDiaSemana(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    //Devuelve todos los días indicados desde el origen, por ejemplo, todos los lunes desde el 01/04/2014
    //Si se mete un valor no válido, se cogerán los lunes
    public static List<String> getDiasAño(int dia){
    	List<String> dias = new ArrayList<String>();
    	List<String> diasAño = new ArrayList<String>();
    	
    	Date fechaOrigen = FECHA_INICIAL_REE;
    	
    	if(dia < 1 || dia >7){
    		dia = 1; 
    	}
    	
    	switch(dia){
    	case 1:
    		fechaOrigen = sumarRestarDiasFecha(fechaOrigen,6);
    		break;
    		
    	case 2:
    		fechaOrigen = fechaOrigen;
    		break;
    		
    	case 3:
    		fechaOrigen = sumarRestarDiasFecha(fechaOrigen,1);
    		break;
    		
    	case 4:
    		fechaOrigen = sumarRestarDiasFecha(fechaOrigen,2);
    		break;
    		
    	case 5:
    		fechaOrigen = sumarRestarDiasFecha(fechaOrigen,3);
    		break;
    		
    	case 6: 
    		fechaOrigen = sumarRestarDiasFecha(fechaOrigen,4);
    		break;
    		
    	case 7:
    		fechaOrigen = sumarRestarDiasFecha(fechaOrigen,5);
    		break;
    	}
    	
    	dias = getFechas7Dias(fechaOrigen);
    	dias.remove(dias.size()-1);
    	
    	for(String d : dias){
    		diasAño.add(formateaStringFechaToString(d));
    	}
    	
    	return diasAño;
    }
    
    //Cuando la fecha viene en formato yyyyMMdd ---> dd/MM/yyyy
    public static String formateaStringFechaToString(String fecha){
    	String año = fecha.substring(0, 4);
    	String mes = fecha.substring(4,6);
    	String dia = fecha.substring(6,8);
    	
    	String fechaFormateada = dia+"/"+mes+"/"+año;
    	
    	return fechaFormateada;
    }
    
    public static Double getNumeroDiasEntre(Date dateIni,Date dateFin){
    	long fechaInicial = dateIni.getTime(); //Tanto fecha inicial como fecha final son Date. 
    	long fechaFinal = dateFin.getTime(); 
    	long diferencia = fechaFinal - fechaInicial; 
    	return Math.floor(diferencia / (1000 * 60 * 60 * 24)); 
    }
}
