package plugins;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

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

public class JSONBinderPlugin extends PlayPlugin {

    interface JsonMixIn {
        @JsonIgnore
        boolean isPersistent();
        @JsonIgnore
        JPAContext getJPAContext();
        @JsonIgnore
        Object getEntityId();
    }

    public static ObjectMapper OM;
    static {
        OM = new ObjectMapper();
        OM.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
        OM.getSerializationConfig().addMixInAnnotations(GenericModel.class, JsonMixIn.class);
        OM.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        OM.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
        OM.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, false);
        OM.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("excludeAllButIDFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"));
        filterProvider.addFilter("excludeIDFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));
        OM.setFilters(filterProvider);
    }

    @Override
    public Object bind(RootParamNode rootParamNode, String name, Class<?> clazz, Type type, Annotation[] annotations) {
        if (Http.Request.current().contentType.equals("application/json")) {
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(RequestBody.class)) {
                    return readValue(Scope.Params.current().get("body"), type, clazz);
                }
            }
        }
        return null;
    }

    public static String writeValueAsString(Object o) {
        try {
            return OM.writeValueAsString(o);
        } catch (IOException e) {
            Logger.error(e, "Could not serialize JSON");
            return null;
        }
    }

    public static Object readValue(String json, Type type, Class<?> contextClass) {
        try {
            JavaType t = OM.getTypeFactory().constructType(type, contextClass);
            return OM.readValue(json, t);
        } catch (IOException e) {
            Logger.error(e, "Could not deserialize JSON");
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

    @Override
    public void enhance(ApplicationClass applicationClass) throws Exception {
        if (!"plugins.JSONBinderPlugin$SyntheticBeanEnhancer".equals(applicationClass.name)) {
            new SyntheticBeanEnhancer().enhanceThisClass(applicationClass);
        }
    }

    /**
     * Add access flag "synthetic" to play!-generated getters and setters to make
     * Jackson work (it excludes synthetic methods and uses the fields instead, otherwise
     * the generated methods override the field access and generic types fail.
     */
    public static class SyntheticBeanEnhancer extends Enhancer {
        @Override
        public void enhanceThisClass(ApplicationClass applicationClass) throws Exception {
            CtClass ctClass = makeClass(applicationClass);
            for (CtField ctField : ctClass.getDeclaredFields()) {
                try {
                    if (isProperty(ctField)) {
                        String propertyName = ctField.getName().substring(0, 1).toUpperCase() + ctField.getName().substring(1);
                        CtMethod ctMethod = ctClass.getDeclaredMethod("get" + propertyName);
                        if (ctMethod.hasAnnotation(PlayPropertyAccessor.class)) {
                            ctMethod.setModifiers(ctMethod.getModifiers() | AccessFlag.SYNTHETIC);
                        }
                        if (!isFinal(ctField)) {
                            ctMethod = ctClass.getDeclaredMethod("set" + propertyName);
                            if (ctMethod.hasAnnotation(PlayPropertyAccessor.class)) {
                                ctMethod.setModifiers(ctMethod.getModifiers() | AccessFlag.SYNTHETIC);
                            }
                        }
                    }
                } catch (Exception e) {
                    Logger.error(e, "Error in SyntheticBeanEnhancer");
                    throw new play.exceptions.UnexpectedException("Error in SyntheticBeanEnhancer", e);
                }
            }
            applicationClass.enhancedByteCode = ctClass.toBytecode();
            ctClass.defrost();
        }

        boolean isProperty(CtField ctField) {
            if (ctField.getName().equals(ctField.getName().toUpperCase()) || ctField.getName().substring(0, 1).equals(ctField.getName().substring(0, 1).toUpperCase())) {
                return false;
            }
            return Modifier.isPublic(ctField.getModifiers()) && !Modifier.isStatic(ctField.getModifiers()) && Modifier.isPublic(ctField.getDeclaringClass().getModifiers());
        }

        boolean isFinal(CtField ctField) {
            return Modifier.isFinal(ctField.getModifiers());
        }
    }
}
