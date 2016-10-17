package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import javax.persistence.Query;

import com.jamonapi.utils.Logger;

import models.PConsumo;
import models.PVPCHora;
import models.Person;
import models.Person.Role;
import models.CoeficientePerfiladoHora;
import models.DesgloseHorario;
import models.Dia;
import play.db.jpa.JPA;
import play.i18n.Messages;
import play.mvc.With;
import service.ReeClient;
import util.CalendarUtil;


//@With(Secure.class)
//@CRUD
public class Dias extends AbstractBaseController{

    public static void list(int page, String orderBy, String order) {
        List<Dia> objects = getPaginatedList(Dia.find(getOrderByStatment(orderBy, order)), page);
        Long count = Dia.count();
//        String chartDiasData = Charts.getChartDiaString(new Date());
//        String chartDiasDiscriminacionData = Charts.getChartDiaDiscriminacionString(new Date());
//        String chartMeses2014Data = Charts.getChartMesesString(2014);
//        String chartMeses2015Data = Charts.getChartMesesString(2015);
//        String chartMediasDiasData = Charts.getChartMediaDiasString();
//        String[] chartHorasMenorMayor = Charts.getChartHorasBarataACaraStringArray();
//        String chartHoras = Charts.getChartHorasBarataACaraString();
//        String chartPreciosMenorMayor = Charts.getChartPreciosBaratoACaroString();
//        String precioBarato = Charts.getChartPrecioBaratoString(new Date());
//        String precioCaro = Charts.getChartPrecioCaroString(new Date());
//        String precioActual = Charts.getChartPrecioActualString();
//        Integer horaBarata = Charts.getChartHoraBarataString(new Date());
//        Integer horaCara = Charts.getChartHoraCaraString(new Date());
//        Integer horaActual = Charts.getChartHoraActualString();
////        String[] diaPrecioMasBarato = Charts.getDiaMasBaratoString();
////        String[] diaPrecioMasCaro = Charts.getDiaMasCaroString();
//        String precioMedioDia = Charts.getChartPrecioMedioDiaString(new Date(),24);
//        String precioMedioDia12 = Charts.getChartPrecioMedioDiaString(new Date(),12);
//        String precioMedioDia7 = Charts.getChartPrecioMedioDiaString(new Date(),7);
//        
//        String heatMapData = Charts.getMeansHeatMap(false);
//        
//        render(objects, count, page, orderBy, order,chartDiasData,chartMeses2014Data,chartMeses2015Data,chartMediasDiasData,
//        		chartHorasMenorMayor, chartPreciosMenorMayor, chartHoras, precioBarato, precioCaro, precioActual,
//        		horaBarata,horaCara,horaActual,precioMedioDia,precioMedioDia12,precioMedioDia7,
//        		chartDiasDiscriminacionData, heatMapData);
    }
	
    public static void show(Integer id) {
        notFoundIfNull(id);
        Dia object = Dia.findById(id);
        
        renderShow(object);
    }
	
	private static void renderShow(Dia object) {
        notFoundIfNull(object);
        List<Dia> coeficientes = Dia.findAll();
        
        render(request.controller + "/show.html", object, coeficientes);
    }
	
	public static void create (){
		renderShow(new Dia());
	}
	
    public static void delete(Integer id) {
        deleteModel(Dia.class, id);
    }

    public static void save(Integer id, Dia entry, boolean goBack, Integer objectId) {
    	Dia object = bindModel(Dia.class, id);
        
        validation.valid(object);
        if (validation.hasErrors()) {
            renderShow(object);
        }
        object.save();
        flash.success(Messages.get("crud.saved", object.getLabel()));
        if (goBack) {
            show(object.id);
        } else {
            redirect(request.controller + ".list");
        }
    }
    
    public static boolean existsInDB(Dia d){
		
		if(!Dia.find("byFecha", d.fecha).fetch().isEmpty()){
			return true;
		}else{
			return false;
		}
	}
    
    
}
