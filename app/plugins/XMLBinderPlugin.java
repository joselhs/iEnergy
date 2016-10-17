package plugins;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import play.Logger;
import play.PlayPlugin;

public class XMLBinderPlugin extends PlayPlugin {

    public static String writeValueAsString(Object o, boolean wrapped) {
    	if(wrapped) {
    		return writeValueAsString(o, new QName("com.insinno.jaxb.model", "wrapper"));
    	} else {
    		return writeValueAsString(o);
    	}
    }

    public static <T> T readValue(String json, Class<?> clazz, boolean wrapped) {
    	if(wrapped) {
    		return readWrappedRootElement(json, clazz);
    	} else {
    		return readRootElement(json, clazz);
    	}
    }
    
    private static String writeValueAsString(Object o) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
        	Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        	// output pretty printed
        	// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        	
        	StringWriter sw = new StringWriter();
        	
        	jaxbMarshaller.marshal(o, sw);
        	
        	return sw.toString();
        } catch (JAXBException e) {
            Logger.error(e, "Could not marshall to XML");
            return null;
        }
    }

    private static String writeValueAsString(Object o, QName qName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
        	Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        	// output pretty printed
        	// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        	JAXBElement root = new JAXBElement(qName, o.getClass(), o);
        	
        	StringWriter sw = new StringWriter();
        	
        	jaxbMarshaller.marshal(root, sw);
        	
        	return sw.toString();
        } catch (JAXBException e) {
            Logger.error(e, "Could not marshall to XML");
            return null;
        }
    }

    
    private static <T> T readRootElement(String json, Class<?> clazz) {
        try {
        	JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        	 
    		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    		InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
    		return (T) jaxbUnmarshaller.unmarshal(stream);
        } catch (JAXBException e) {
        	Logger.error(e, "Could not unmarshall XML");
        	return null;
        }
    }

    private static <T> T readWrappedRootElement(String json, Class<?> clazz) {
        try {
        	JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        	 
    		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    		
            
    		//InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
    		Source src = new StreamSource(new java.io.StringReader(json));

    		JAXBElement<T> root = (JAXBElement<T>) jaxbUnmarshaller.unmarshal(src, clazz);
    		return root.getValue();
        } catch (JAXBException e) {
        	Logger.error(e, "Could not unmarshall XML");
        	return null;
        }
    }

    
}
