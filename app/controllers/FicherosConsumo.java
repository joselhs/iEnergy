package controllers;

import java.util.Date;
import java.util.List;

import com.jamonapi.utils.Logger;

import models.Dia;
import models.FicheroConsumo;
import models.User;
import play.i18n.Messages;
import util.CalendarUtil;

public class FicherosConsumo extends AbstractBaseController{

	
	public static void delete(Integer id) {
        deleteModel(FicheroConsumo.class, id);
    }

    public static void save(Integer userId, String json, String fecha, String mes, String año, String consumoPorDia, 
    		Double consumoTotal, Double porcentajeTramoBarato, Double porcentajeLaborables) {
    		
    	User usuario = User.findById(userId);
    	Date date = CalendarUtil.parseFecha(fecha);
    	
    	FicheroConsumo f = FicheroConsumo.find("byFechaAndUsuario", date, usuario).first();
    	
    	if(f == null){
    		f = new FicheroConsumo();
        	List<FicheroConsumo> ficheros = usuario.ficheros;
        	
        	f.json=json;
        	f.mes=mes;
        	f.año=año;
        	f.fecha=date;
        	f.consumoPorDiaSemana=consumoPorDia;
        	f.consumoTotal=consumoTotal;
        	f.porcentajeLaborables=porcentajeLaborables;
        	f.porcentajeTramoBarato=porcentajeTramoBarato;
        	f.usuario=usuario;
        	
        	ficheros.add(f);
        	
        	f.save();
        	usuario.save();
    	}
    }
    
    public static boolean existsInDB(FicheroConsumo f){
		
		if(!FicheroConsumo.find("byFecha", f.fecha).fetch().isEmpty()){
			return true;
		}else{
			return false;
		}
	}
}
