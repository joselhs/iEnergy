package controllers;

import java.util.List;

import models.CoeficientePerfiladoHora;
import models.PVPCHora;
import models.Person;
import models.Person.Role;
import play.i18n.Messages;
import play.mvc.With;


//@With(Secure.class)
//@CRUD
public class PVPCHoras extends AbstractBaseController{
	
    public static void list(int page, String orderBy, String order) {
        List<PVPCHora> objects = getPaginatedList(PVPCHora.find(getOrderByStatment(orderBy, order)), page);
        Long count = PVPCHora.count();
        render(objects, count, page, orderBy, order);
    }
	
    public static void show(Integer id) {
        notFoundIfNull(id);
        PVPCHora object = PVPCHora.findById(id);
        
        renderShow(object);
    }
	
	private static void renderShow(PVPCHora object) {
        notFoundIfNull(object);
        List<PVPCHora> coeficientes = PVPCHora.findAll();
        render(request.controller + "/show.html", object, coeficientes);
    }
	
	public static void create (){
		renderShow(new PVPCHora());
	}
	
    public static void delete(Integer id) {
        deleteModel(PVPCHora.class, id);
    }

    public static void save(Integer id, PVPCHora entry, boolean goBack, Integer objectId) {
    	PVPCHora object = bindModel(PVPCHora.class, id);
        
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
    
    public static boolean existsInDB(PVPCHora pvpchora){
    	boolean res = false;
    	List<PVPCHora> precios = PVPCHora.all().fetch();
    	
    	for(PVPCHora precio : precios){
    		if(pvpchora.fecha.equals(precio.fecha) && pvpchora.hora == precio.hora){
    			res = true;
    			break;
    		}else{
    			res=false;
    		}
    	}
    	
    	return res;
	}
    

}
