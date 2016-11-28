package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Quartet;
import org.javatuples.Septet;
import org.javatuples.Triplet;

import com.fasterxml.jackson.core.type.TypeReference;

import controllers.CoeficientesPerfiladoHoras;
import controllers.Dias;
import controllers.PVPCHoras;
import models.CoeficientePerfiladoHora;
import models.Dia;
import models.PVPCHora;
import models.ree.desgloses.IntervaloType;
import models.ree.desgloses.PVPCDesgloseHorario;
import models.ree.desgloses.SeriesTemporalesType;
import models.ree.perfiles.PerfilConsumo;
import models.ree.perfiles.PeriodoType;
import play.Logger;
import play.Play;
import play.data.validation.Validation;
import play.db.jpa.JPA;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
import util.CalendarUtil;
import util.XML;

public class ReeClient {
    
    public static final Map<Integer,String> horario;
    static {
        Map<Integer,String> horas = new HashMap<Integer,String>();
        horas.put(22,"22:00");horas.put(23,"23:00");horas.put(24,"00:00");horas.put(1,"1:00");
        horas.put(2,"2:00");horas.put(3,"3:00");horas.put(4,"4:00");horas.put(5,"5:00");
        horas.put(6,"6:00");horas.put(7,"7:00");horas.put(8,"8:00");horas.put(9,"9:00");
        horas.put(10,"10:00");horas.put(11,"11:00");horas.put(12,"12:00");horas.put(13,"13:00");
        horas.put(14,"14:00");horas.put(15,"15:00");horas.put(16,"16:00");horas.put(17,"17:00");
        horas.put(18,"18:00");horas.put(19,"19:00");horas.put(20,"20:00");horas.put(21,"21:00");
        
        horario = Collections.unmodifiableMap(horas);
    }
    
    public static PerfilConsumo getPerfiles(String url) {
        WSRequest req = buildRequest(url);
        HttpResponse res = get(req);
        if (res.success()) {
        	PerfilConsumo objPerfilConsumoType = XML.readValue(res.getString(), PerfilConsumo.class, false);
            return objPerfilConsumoType;
        } else {
            if (res.getStatus() == 400) {
            	 Logger.error("ERROR 400");
            }
            return null;
        }
    }
    
   
	public static PVPCDesgloseHorario getDesgloses(String url) {
        WSRequest req = buildRequest(url);
        HttpResponse res = get(req);
        if (res.success()) {
        	PVPCDesgloseHorario objPVPCDesgloseHorarioType = XML.readValue(res.getString(), PVPCDesgloseHorario.class, false);
            return objPVPCDesgloseHorarioType;
        } else {
            if (res.getStatus() == 400) {
            	 Logger.error("ERROR 400");
            }
            return null;
        }
    }
	
	private static WSRequest buildRequest(String url) {
        return WS.url(url);
    }
	
	private static HttpResponse get(WSRequest request) {
        HttpResponse res = request.get();
        return res;
    }
	
	public static List<Quartet<String,String,String,String>> getCoeficientesPerfilado(PerfilConsumo pc){
		 
		 List<Quartet<String,String,String,String>> fechashorascoeficientes = new ArrayList<Quartet<String,String,String,String>>();
		 
		 List<models.ree.perfiles.SeriesTemporalesType> series = pc.getSeriesTemporales();
		 String hora = null;
		 String coeficienteA = null;
		 String coeficienteDHA = null;
		 String fecha = null;
		 List<Triplet<String,String,String>> tripletsA = new ArrayList<Triplet<String,String,String>> ();
		 List<Triplet<String,String,String>> tripletsDHA = new ArrayList<Triplet<String,String,String>> ();
		 
		 for(models.ree.perfiles.SeriesTemporalesType serie : series){
			 
			 if(serie.getIdentificacionSeriesTemporales().getV().equals("ISTZ01")){
				 List<PeriodoType> periodos = serie.getPeriodo();
				 
				 for(PeriodoType periodo : periodos){
					 List<models.ree.perfiles.IntervaloType> intervalos = periodo.getIntervalo();
					 
					 //Formateado del intervalo para obtener fecha
					 String intervalostring = periodo.getIntervaloTiempo().getV();
					 String[] intervalotiempo = intervalostring.split("/");
					 String[] substring = intervalotiempo[1].split("T");
					 fecha = substring[0];
					 
					 for(models.ree.perfiles.IntervaloType intervalo : intervalos){
						 hora = intervalo.getPos().getV();
						 coeficienteA = intervalo.getCtd().getV();
						 Triplet<String,String,String> t = new Triplet<String,String,String>(fecha,hora,coeficienteA);
						 tripletsA.add(t);
					 } 
				 } 
			 }
			 
			 if(serie.getIdentificacionSeriesTemporales().getV().equals("ISTZ02")){
				 List<PeriodoType> periodos = serie.getPeriodo();
				 
				 for(PeriodoType periodo : periodos){
					 List<models.ree.perfiles.IntervaloType> intervalos = periodo.getIntervalo();
					 
					 //Formateado del intervalo para obtener fecha
					 String intervalostring = periodo.getIntervaloTiempo().getV();
					 String[] intervalotiempo = intervalostring.split("/");
					 String[] substring = intervalotiempo[1].split("T");
					 fecha = substring[0];
					 
					 for(models.ree.perfiles.IntervaloType intervalo : intervalos){
						 hora = intervalo.getPos().getV();
						 coeficienteDHA = intervalo.getCtd().getV();
						 Triplet<String,String,String> t = new Triplet<String,String,String>(fecha,hora,coeficienteDHA);
						 tripletsDHA.add(t);
					 } 
				 } 
			 } 
		 }
		 
		 for(Triplet<String,String,String> tripletA : tripletsA){
			 for(Triplet<String,String,String> tripletDHA : tripletsDHA){
				 if(tripletDHA.getValue0().equals(tripletA.getValue0()) &&
						 tripletDHA.getValue1().equals(tripletA.getValue1())){
					 Quartet<String,String,String,String> res = new Quartet<String,String,String,String>(tripletA.getValue0(),tripletA.getValue1(),tripletA.getValue2(),tripletDHA.getValue2());
					 fechashorascoeficientes.add(res);
					 break;
				 }
			 }
			 
		 }
		 
		 return fechashorascoeficientes;
	 }
	
	public static List<Septet<String,String,String,String,String,String,Boolean>> getPreciosHoras(PVPCDesgloseHorario pvpc){
		 List<Septet<String,String,String,String,String,String,Boolean>> fechashorasprecios = new ArrayList<Septet<String,String,String,String,String,String,Boolean>>();
		 
		 String fecha = null;
		 Boolean verano = null;
		 String hora = null;
		 String precioA = null;
		 String precioDHA = null;
		 String feuA = null;
		 String feuDHA = null;
		 
		 Map<String,String> preciosA = new HashMap<String,String>();
		 Map<String,String> preciosDHA = new HashMap<String,String>();
		 Map<String,String> feusA = new HashMap<String,String>();
		 Map<String,String> feusDHA = new HashMap<String,String>();
		 
		 
		 List<SeriesTemporalesType> series = pvpc.getSeriesTemporales();
		 
		//¿Es verano?
		 String intervalotiempo1 = series.get(0).getPeriodo().getIntervaloTiempo().getV();
		 verano = CalendarUtil.esVerano(intervalotiempo1);
		 
		 for(SeriesTemporalesType serie : series){
			 
			 
			 if(serie.getIdentificacionSeriesTemporales().getV().equals("IST7")){
				 List<IntervaloType> intervalos = serie.getPeriodo().getIntervalo();
				 
				 for(IntervaloType intervalo : intervalos){
					 hora = intervalo.getPos().getV();
					 feuA = intervalo.getCtd().getV(); 
					 feusA.put(hora,feuA);
				 }
			 }
			 
			 if(serie.getIdentificacionSeriesTemporales().getV().equals("IST8")){
				 List<IntervaloType> intervalos = serie.getPeriodo().getIntervalo();
				 
				 for(IntervaloType intervalo : intervalos){
					 hora = intervalo.getPos().getV();
					 feuDHA = intervalo.getCtd().getV(); 
					 feusDHA.put(hora,feuDHA);
				 }
			 }
			 
			 if(serie.getIdentificacionSeriesTemporales().getV().equals("IST17")){
				 List<IntervaloType> intervalos = serie.getPeriodo().getIntervalo();
				 
				 //Formateado del intervalo para obtener fecha
				 String intervalostring = serie.getPeriodo().getIntervaloTiempo().getV();
				 String[] intervalotiempo = intervalostring.split("/");
				 String[] substring = intervalotiempo[1].split("T");
				 fecha = substring[0];
				 
				 for(IntervaloType intervalo : intervalos){
					 hora = intervalo.getPos().getV();
					 precioA = intervalo.getCtd().getV(); 
					 preciosA.put(hora,precioA);
				 }
				 
			 }
			 
			 if(serie.getIdentificacionSeriesTemporales().getV().equals("IST18")){
				 List<IntervaloType> intervalos = serie.getPeriodo().getIntervalo();
				 for(IntervaloType intervalo : intervalos){
					 hora = intervalo.getPos().getV();
					 precioDHA = intervalo.getCtd().getV();
					 preciosDHA.put(hora, precioDHA);
				 }
			 }
			 
		 }
		 
		 for(int i=1;i<=24;i++){
			 Septet<String,String,String,String,String,String,Boolean> res = new Septet<String,String,String,String,String,String,Boolean>(fecha,Integer.toString(i),
					 preciosA.get(Integer.toString(i)),preciosDHA.get(Integer.toString(i)),
					 feusA.get(Integer.toString(i)),feusDHA.get(Integer.toString(i)),verano);
			 fechashorasprecios.add(res);
		 }
		 
		 return fechashorasprecios;
	 }
	
	public static void setPreciosHoras(List<Septet<String,String,String,String,String,String,Boolean>> precioshoras){
		 for(Septet<String,String,String,String,String,String,Boolean> q : precioshoras){
				Dia d = new Dia();
				PVPCHora pvpchora = new PVPCHora();
				
				//Formateando fecha
				String fecha1 = q.getValue0();
				fecha1=fecha1.replace("-", "/");
				String[] stringfecha = fecha1.split("/");
				fecha1=stringfecha[2]+"/"+stringfecha[1]+"/"+stringfecha[0];
				
				d.fecha=CalendarUtil.parseFecha(fecha1);
				
				pvpchora.fecha = CalendarUtil.parseFecha(fecha1);
				pvpchora.hora = Integer.parseInt(q.getValue1());
				
				if(q.getValue2() == null){
					pvpchora.TCUhA=0.0;
				}else{
					pvpchora.TCUhA = Double.parseDouble(q.getValue2());
				}
				
				if(q.getValue3() == null){
					pvpchora.TCUhDHA=0.0;
				}else{
					pvpchora.TCUhDHA = Double.parseDouble(q.getValue3());
				}
				
				if(q.getValue4() == null){
					pvpchora.TEUhA=0.0;
				}else{
					//TEUhA = FEUhA-TCUhA
					pvpchora.TEUhA = Double.parseDouble(q.getValue4())-Double.parseDouble(q.getValue2());
				}
				
				if(q.getValue5() == null){
					//TEUhDHA = FEUhDHA-TCUhDHA
					pvpchora.TEUhDHA=0.0;
				}else{
					pvpchora.TEUhDHA = Double.parseDouble(q.getValue5())-Double.parseDouble(q.getValue3());
				}
				
				if(!Dias.existsInDB(d)){
					d.save();
				}
				if(!PVPCHoras.existsInDB(pvpchora)){
					pvpchora.save();
				}
			}
		 Logger.info("\nFin de la función setPreciosHoras \n");
	 }
	
	public static void setCoeficientesPerfilado(List<Quartet<String,String,String,String>> coeficientes){
		 for(Quartet<String,String,String,String> q : coeficientes){
				Dia d = new Dia();
				CoeficientePerfiladoHora cph = new CoeficientePerfiladoHora();
				
				//Formateando fecha
				String fecha1 = q.getValue0();
				fecha1=fecha1.replace("-", "/");
				String[] stringfecha = fecha1.split("/");
				fecha1=stringfecha[2]+"/"+stringfecha[1]+"/"+stringfecha[0];
				
				d.fecha=CalendarUtil.parseFecha(fecha1);
				
				cph.fecha = CalendarUtil.parseFecha(fecha1);
				cph.hora = Integer.parseInt(q.getValue1());
				
				if(q.getValue2()==null){
					cph.coeficienteA =0.0;
				}else{
					cph.coeficienteA = Double.parseDouble(q.getValue2());
				}
				
				if(q.getValue3()==null){
					cph.coeficienteDHA =0.0;
				}else{
					cph.coeficienteDHA = Double.parseDouble(q.getValue3());
				}
				
				if(!CoeficientesPerfiladoHoras.existsInDB(cph)){
					cph.save();
				}
				
			}
		 Logger.info("\nFinalizada la función setCoeficientesPerfilado \n");
	 }
	
	//PRECIO MEDIO DIA 
	public static void calculaPrecioMedioDia (List<Septet<String,String,String,String,String,String,Boolean>> diashorasprecios){
			 
		Double [] tcuchA = new Double[24];
		Double [] tcuchDHAP2 = new Double[24];
		Double [] tcuchDHAP1 = new Double[24];
		int i = 0;
		Double sumatorioCoeficientesA = new Double(0);
		Double sumatorioCoeficientesDHAP2 = new Double(0);
		Double sumatorioCoeficientesDHAP1 = new Double(0);
		Dia diaMedia = null;
			 
		for(Septet<String,String,String,String,String,String,Boolean> q : diashorasprecios){
				 
			//Formateando fecha
			String fecha1 = q.getValue0();
			fecha1=fecha1.replace("-", "/");
			String[] stringfecha = fecha1.split("/");
			fecha1=stringfecha[2]+"/"+stringfecha[1]+"/"+stringfecha[0];
				 
			Date fecha = CalendarUtil.parseFecha(fecha1);
			Dia dia = Dia.find("byFecha", fecha).first();
			diaMedia = dia;
			Boolean verano = q.getValue6();
				
				
			//CALCULO SUMATORIOS COEFICIENTES
			List<CoeficientePerfiladoHora> coeficientes = CoeficientePerfiladoHora.find("byFecha", dia.fecha).fetch();
			for(CoeficientePerfiladoHora c : coeficientes){
				if(c.hora == Integer.parseInt(q.getValue1())){
					//TARIFA 2.0A
					Double res1 = 1.0;
					if(q.getValue2()==null){
						res1 = c.coeficienteA * 1;
					}else{
						res1 = c.coeficienteA * Double.parseDouble(q.getValue2());
					}
					sumatorioCoeficientesA+=c.coeficienteA;
					tcuchA[i] = res1;
						
					//TARIFA 2.0DHA
					if(verano){
						if((c.hora<14 && c.hora>=1) || c.hora == 24 ){
							Double res2 = c.coeficienteDHA * Double.parseDouble(q.getValue3());
							sumatorioCoeficientesDHAP2+=c.coeficienteDHA;
							tcuchDHAP2[i] = res2;
						}else{
							Double res2 = 1.0;
							if(q.getValue3()==null){
								res2 = c.coeficienteDHA * 1;
							}else{
								res2 = c.coeficienteDHA * Double.parseDouble(q.getValue3());
							}
								
							sumatorioCoeficientesDHAP1+=c.coeficienteDHA;
							tcuchDHAP1[i] = res2;
						}
					}else{
						if((c.hora<13 && c.hora>=1) || c.hora == 24 || c.hora==23 ){
							Double res2 = c.coeficienteDHA * Double.parseDouble(q.getValue3());
							sumatorioCoeficientesDHAP2+=c.coeficienteDHA;
							tcuchDHAP2[i] = res2;
						}else{
							Double res2 = 1.0;
							if(q.getValue3()==null){
								res2 = c.coeficienteDHA * 1;
							}else{
								res2 = c.coeficienteDHA * Double.parseDouble(q.getValue3());
							}
								
							sumatorioCoeficientesDHAP1+=c.coeficienteDHA;
							tcuchDHAP1[i] = res2;
						}
					}

					break;
				}
				
			}
				
			i++; 
		}
			 
			 
		//CALCULO SUMATORIOS TCU
		Double sumatorioTcuA=new Double(0);
		for (Double res:tcuchA){
			if(res != null){
				sumatorioTcuA+=res; 
			}else{
				sumatorioTcuA+=0.0;
			}
						 
		}
			 
		Double sumatorioTcuDHAP2=new Double(0);
		for (Double res:tcuchDHAP2){
			if(res != null){
				sumatorioTcuDHAP2+=res; 
			}else{
				sumatorioTcuDHAP2+=0.0;
			}
		}
			 
		Double sumatorioTcuDHAP1=new Double(0);
		for (Double res:tcuchDHAP1){
			if(res != null){
				sumatorioTcuDHAP1+=res; 
			}else{
				sumatorioTcuDHAP1+=0.0;
			}
		}
			 
		//CALCULO PRECIO MEDIO DIA
		Double teuA = Double.parseDouble(diashorasprecios.get(1).getValue4())-Double.parseDouble(diashorasprecios.get(1).getValue2());
		Double teuDHAP2 = Double.parseDouble(diashorasprecios.get(1).getValue5())-Double.parseDouble(diashorasprecios.get(1).getValue3());
		Double teuDHAP1 = Double.parseDouble(diashorasprecios.get(20).getValue5())-Double.parseDouble(diashorasprecios.get(20).getValue3());
			 
		Double precioMedioDiaA = teuA+(sumatorioTcuA/sumatorioCoeficientesA);
		Double precioMedioDiaDHAP2 = teuDHAP2+(sumatorioTcuDHAP2/sumatorioCoeficientesDHAP2);
		Double precioMedioDiaDHAP1 = teuDHAP1+(sumatorioTcuDHAP1/sumatorioCoeficientesDHAP1);
			 
		if(sumatorioCoeficientesA == 0.0){
			diaMedia.precioMedioDiaA=null;
			diaMedia.sumatorioCoeficientesA=0.0;
		}else{
			diaMedia.precioMedioDiaA=precioMedioDiaA;
			diaMedia.sumatorioCoeficientesA=sumatorioCoeficientesA;
		}
			 
		//Sumatorio total de coeficientes DHA (Periodo1+Periodo2)
		Double sumatorioCoeficientesDHA = 0.0;
			 
		if(sumatorioCoeficientesDHAP2 == 0.0){
			diaMedia.precioMedioDiaDHAP2=null;
			sumatorioCoeficientesDHA+=sumatorioCoeficientesDHAP2;
		}else{
			diaMedia.precioMedioDiaDHAP2=precioMedioDiaDHAP2;
			sumatorioCoeficientesDHA+=sumatorioCoeficientesDHAP2;
		}
			 
		if(sumatorioCoeficientesDHAP1 == 0.0){
			diaMedia.precioMedioDiaDHAP1=null;
			sumatorioCoeficientesDHA+=sumatorioCoeficientesDHAP1;
		}else{
			diaMedia.precioMedioDiaDHAP1=precioMedioDiaDHAP1;
			sumatorioCoeficientesDHA+=sumatorioCoeficientesDHAP1;
		}
			 
		diaMedia.sumatorioCoeficientesDHA=sumatorioCoeficientesDHA;
			 
		if(Dias.existsInDB(diaMedia)){
			updateDiaPrecioMedio(diaMedia);
		}else{
			diaMedia.save();
		}
		Logger.info("\nFin de la función calculaPrecioMedioDia \n");
			 
	}
		 
	public static void updateDiaPrecioMedio(Dia d){
		Dia object = Dia.find("byFecha", d.fecha).first();
				
		object.fecha=d.fecha;
		object.precioMedioDiaA=d.precioMedioDiaA;
		object.precioMedioDiaDHAP2=d.precioMedioDiaDHAP2;
		object.precioMedioDiaDHAP1=d.precioMedioDiaDHAP1;
				
		object.save();
	}
	 

	//ind dia = 0-->Lunes,...,6-->Domingo
	public static Double calculaMediaDiasAño(int diaSemana){
		Date fecha = CalendarUtil.sumarRestarDiasFecha(new Date(), -365);
			 
		Double mediaDia = (Double) JPA.em().createNativeQuery("select avg(precioMedioDiaA) from Dia d"+
				" where (d.fecha >= :fecha AND WEEKDAY(d.fecha) = :dia)").setParameter("fecha", fecha).setParameter("dia",diaSemana).getSingleResult();
			 
			return mediaDia;
	}
	
	public static Double calculaMediaPreciosMes(Integer mes, Integer año){
		 List<Dia> diasMes = getDiasMes(mes,año);
		 
		 
		 Double mediaMes = new Double(0);
		 Double sumatorioDias = new Double(0);
		 
		 if(diasMes.isEmpty()){
			 mediaMes = 0.0;
		 }else{
			 int i = 0;
			 
			 for(Dia dia : diasMes){
				 if(dia == null || dia.precioMedioDiaA==null){
					 sumatorioDias+=0;
				 }else{
					 sumatorioDias+=dia.precioMedioDiaA;
					 i++;
				 }
				 
			 }
			 
			 if(i==0){
				 i=1;
			 }
			 mediaMes = sumatorioDias/i; 
		 }
		 
		 
		 return mediaMes;
	 }
	
	public static List<Dia> getDiasMes(Integer mes, Integer año){
		 List<Dia> todosDias = Dia.findAll();
		 List<Dia> dias = new ArrayList<Dia>();
		 
		 for(Dia d : todosDias){
			 if(CalendarUtil.getYear(d.fecha) == año && CalendarUtil.getMonth(d.fecha) == mes){
				 dias.add(d);
			 }
		 }
		 
		 return dias;
	 }
	
	
	
	public static void updateAllPreciosHoras(List<PVPCDesgloseHorario> desgloses){
		 int i = 0;
		 for(PVPCDesgloseHorario desglose : desgloses){
				List<Septet<String,String,String,String,String,String,Boolean>> precios = getPreciosHoras(desglose);
				setPreciosHoras(precios);  
				calculaPrecioMedioDia(precios);
				Logger.info(i+" PRECIO ACTUALIZADO!!"+"- Dia: "+desglose.getHorizonte().getV());
				i++;
		 }
			 
			
	 }
	 
	 public static void updateAllCoeficientesHoras(List<PerfilConsumo> perfiles){
		 int i = 0;
		 for(PerfilConsumo perfil : perfiles){
			 List<Quartet<String,String,String,String>> coeficientes = getCoeficientesPerfilado(perfil);
			 setCoeficientesPerfilado(coeficientes);
			 Logger.info(i+" COEFICIENTE ACTUALIZADO!!"+"- Dia: "+perfil.getHorizonte().getV());
			 i++;
		 }
		 
	 }
	
}
