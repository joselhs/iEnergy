package controllers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;

import javax.persistence.NoResultException;
import models.AbstractDomainModel;
import models.Person;
import play.Logger;
import play.Play;
import play.data.binding.Binder;
import play.db.Model;
import play.db.jpa.Blob;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.i18n.Messages;
import play.mvc.Before;
import play.mvc.Controller;
import plugins.JSONBinderPlugin;

/**
 * @author Sebastian Beigel
 *
 */
public class AbstractBaseController extends Controller {

//    @Before(priority = 0)
//    static void setConnectedUser() {
//        if (Security.isConnected() && renderArgs.get("user") == null) {
//            Person user = Person.find("byEmail", Security.connected()).first();
//            renderArgs.put("user", user);
//        }
//    }

    protected static void renderJSON(Object o) {
        try {
            renderJSON(JSONBinderPlugin.OM.writeValueAsString(o));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void renderBinary(Blob file) {
        if (!file.exists()) {
            response.status = 404;
        } else {
            response.contentType = file.type();
            renderBinary(file.getFile());
        }
    }

    static void renderBinary(Blob file, String name) {
        if (!file.exists()) {
            response.status = 404;
        } else {
            response.contentType = file.type();
            renderBinary(file.getFile(), name);
        }
    }

    protected static void returnFile(File f) {
        if (f.exists()) {
            renderBinary(f);
        } else {
            response.status = 404;
        }
    }

    static final TypeReference<HashMap<String, String[]>> MAP_TYPE_REF = new TypeReference<HashMap<String, String[]>>() {};

    static void restoreSavedParamsFromSession() {
        String storedParams = session.get(request.controller + "_storedParams");
        if (StringUtils.isNotEmpty(storedParams)) {
            Map<String, String[]> p = JSONBinderPlugin.readValue(storedParams, MAP_TYPE_REF);
            for (Map.Entry<String, String[]> e : p.entrySet()) {
                if (!params._contains(e.getKey())) {
                    params.put(e.getKey(), e.getValue());
                }
            }
        }
    }

    static void storeParamsInSession() {
        session.put(request.controller + "_storedParams", JSONBinderPlugin.writeValueAsString(params.all()));
    }


    protected static <T> List<T> getPaginatedList(GenericModel.JPAQuery query, int page) {
        return getPaginatedList(query, page, 20);
    }

    protected static <T> List<T> getPaginatedList(GenericModel.JPAQuery query, int page, int pageSize) {
        if (page < 1) {
            page = 1;
        }
        return query.from((page - 1) * pageSize).fetch(pageSize);
    }

    protected static String getOrderByStatment(String orderBy, String order) {
        return getOrderByStatment(orderBy, order, null);
    }
    protected static String getOrderByStatment(String orderBy, String order, String alias) {
        if (StringUtils.isNotEmpty(orderBy)) {
            order = (StringUtils.isNotEmpty(order) ? " " + order : "");
            if (orderBy.contains(", ")) {
                StringBuilder sb = new StringBuilder();
                for (String o : orderBy.split(", ")) {
                    if (alias != null) {
                        sb.append(alias);
                        sb.append(".");
                    }
                    sb.append(o);
                    sb.append(order);
                    sb.append(", ");
                }
                if (sb.length() > 2) {
                    sb.setLength(sb.length() - 2);
                }
                orderBy = sb.toString();
                order = "";
            } else {
                if (alias != null) {
                    orderBy = alias + "." + orderBy;
                }
            }
            return "order by " + orderBy + order;
        } else {
            return "";
        }
    }

    /* "find by id" implemented using a query to make hibernate filter work! */
    protected static <T extends Model> T findById(Class<T> modelClass, Integer id) {
        try {
            return (T) JPA.em().createQuery("select o from " + modelClass.getSimpleName() + " o where o.id = :id").setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            notFound(String.format("No model %s found for id %s", modelClass.getSimpleName(), id));
            return null;
        }
    }

    protected static <T extends Model> T bindModel(Class<T> modelClass, Integer id) {
        T object;
        if (id != null) {
            //object = (T) Model.Manager.factoryFor(modelClass).findById(id);
            object = findById(modelClass, id);
        } else {
            try {
                object = modelClass.newInstance();
            } catch (Exception e) {
                Logger.error(e, "could not instantiate model?!");
                object = null;
            }
        }
        notFoundIfNull(object);
        Binder.bindBean(params.getRootParamNode(), "object", object);
        return object;
    }

    protected static <T extends AbstractDomainModel> T getClonedModel(Class<T> modelClass, Integer id) {
        T object = findById(modelClass, id);
        // remove Id and show like a "pre-filled create operation"
        object.id = null;
        params.remove("id");
        return object;
    }


    protected static boolean saveModel(AbstractDomainModel object) {
        validation.valid(object);
        if (validation.hasErrors()) {
            return false;
        }

        object.save();
        flash.success(Messages.get("crud.saved", object.getLabel()));
        redirect(request.controller + ".list");
        return true;
    }

    protected static void deleteModel(Class<? extends Model> modelClass, Integer id) {
        doDeleteModel(modelClass, id);
        redirect(request.controller + ".list");
    }

    protected static <T extends Model> T doDeleteModel(Class<T> modelClass, Integer id) {
        T object = findById(modelClass, id);
        object._delete();
        flash.success(Messages.get("crud.deleted", ((AbstractDomainModel) object).getLabel()));
        return object;
    }

    protected static String getResourcesPath() {
        return Play.configuration.getProperty("resources.path");
    }

    protected static Person getLoggedInPerson() {
        return (Person) renderArgs.get("user");
    }
    
}