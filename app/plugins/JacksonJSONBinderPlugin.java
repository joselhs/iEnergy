package plugins;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXBElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.bytecode.AccessFlag;
import play.Logger;
import play.PlayPlugin;
import play.classloading.ApplicationClasses.ApplicationClass;
import play.classloading.enhancers.Enhancer;
import play.classloading.enhancers.PropertiesEnhancer.PlayPropertyAccessor;
import play.data.binding.RootParamNode;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA.JPAContext;
import play.mvc.Http;
import play.mvc.Scope;

public class JacksonJSONBinderPlugin extends PlayPlugin {

    interface JsonMixIn {
        @JsonIgnore
        boolean isPersistent();
        @JsonIgnore
        JPAContext getJPAContext();
        @JsonIgnore
        Object getEntityId();
    }
    
    interface JAXBElementMixin {
        @JsonValue
        Object getValue();
    }

    public static ObjectMapper OM;
    static {
        OM = new ObjectMapper();
        OM.addMixInAnnotations(JAXBElement.class, JAXBElementMixin.class);
        OM.addMixInAnnotations(GenericModel.class, JsonMixIn.class);
        OM.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String writeValueAsString(Object o) {
        try {
            return OM.writeValueAsString(o);
        } catch (IOException e) {
            Logger.error(e, "Could not serialize JSON");
            return null;
        }
    }

    public static Object readValue(String json, Class<?> clazz) {
        try {
            return OM.readValue(json, clazz);
        } catch (IOException e) {
            Logger.error(e, "Could not deserialize JSON");
            return null;
        }
    }

    public static <T> T readValue(String json, TypeReference typeReference) {
        try {
            return OM.readValue(json, typeReference);
        } catch (IOException e) {
            Logger.error(e, "Could not deserialize JSON");
            return null;
        }
    }

}
