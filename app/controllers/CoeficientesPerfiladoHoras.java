package controllers;

import java.util.List;

import models.CoeficientePerfiladoHora;
import models.Person;
import models.Person.Role;
import play.i18n.Messages;
import play.mvc.With;


//@With(Secure.class)
//@CRUD
public class CoeficientesPerfiladoHoras extends AbstractBaseController{
	
	
    public static void delete(Integer id) {
        deleteModel(CoeficientePerfiladoHora.class, id);
    }

    public static void save(Integer id, CoeficientePerfiladoHora entry, boolean goBack, Integer objectId) {
    	CoeficientePerfiladoHora object = bindModel(CoeficientePerfiladoHora.class, id);
        
        validation.valid(object);
        if (validation.hasErrors()) {
//            renderShow(object);
        }
        object.save();
        flash.success(Messages.get("crud.saved", object.getLabel()));
        if (goBack) {
//            show(object.id);
        } else {
            redirect(request.controller + ".list");
        }
    }
    
    public static boolean existsInDB(CoeficientePerfiladoHora cph){
    	boolean res = false;
    	List<CoeficientePerfiladoHora> coeficientes = CoeficientePerfiladoHora.all().fetch();
    	
    	for(CoeficientePerfiladoHora coeficiente : coeficientes){
    		if(cph.fecha.equals(coeficiente.fecha) && cph.hora == coeficiente.hora){
    			res = true;
    			break;
    		}else{
    			res=false;
    		}
    	}
    	
    	return res;
	}

}
