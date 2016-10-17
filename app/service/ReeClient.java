package service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import models.ree.desgloses.PVPCDesgloseHorario;
import models.ree.perfiles.PerfilConsumoType;
import play.Logger;
import play.Play;
import play.data.validation.Validation;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
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
    
    public static PerfilConsumoType getPerfiles(String url) {
        WSRequest req = buildRequest(url);
        HttpResponse res = get(req);
        if (res.success()) {
        	PerfilConsumoType objPerfilConsumoType = XML.readValue(res.getString(), PerfilConsumoType.class, false);
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
}
