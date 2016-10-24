package util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.Logger;

public class XML {

	public static ObjectMapper OM;
	
	public static String writeValueAsString(Object o, boolean wrapped) {
		if (wrapped) {
			return writeValueAsString(o, new StringWriter(), new QName(
					"com.insinno.jaxb.model", "wrapper"));
		} else {
			return writeValueAsString(o, new StringWriter());
		}
	}

	public static <T> T readValue(String json, Class<?> clazz, boolean wrapped) {
		if (wrapped) {
			return readWrappedRootElement(json, clazz);
		} else {
			return readRootElement(json, clazz);
		}
	}

	public static <T> T readValue(File file, Class<?> clazz, boolean wrapped) {
		if (wrapped) {
			return readWrappedRootElement(file, clazz);
		} else {
			return readWrappedRootElement(file, clazz);
		}
	}

	private static String writeValueAsString(Object o, Writer sw) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(o, sw);

			return sw.toString();
		} catch (JAXBException e) {
			Logger.error(e, "Could not marshall to XML");
			return null;
		}
	}

	private static String writeValueAsString(Object o, Writer sw, QName qName) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			JAXBElement root = new JAXBElement(qName, o.getClass(), o);

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
			InputStream stream = new ByteArrayInputStream(
					json.getBytes(StandardCharsets.UTF_8));
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

			// InputStream stream = new
			// ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
			Source src = new StreamSource(new java.io.StringReader(json));

			JAXBElement<T> root = (JAXBElement<T>) jaxbUnmarshaller.unmarshal(
					src, clazz);
			return root.getValue();
		} catch (JAXBException e) {
			Logger.error(e, "Could not unmarshall XML");
			return null;
		}
	}

	private static <T> T readWrappedRootElement(File file, Class<?> clazz) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			// InputStream stream = new
			// ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
			Source src = new StreamSource(file);

			JAXBElement<T> root = (JAXBElement<T>) jaxbUnmarshaller.unmarshal(src, clazz);
			return root.getValue();
		} catch (JAXBException e) {
			Logger.error(e, "Could not unmarshall XML");
			return null;
		}
	}
	
	 public static <T> T readValue(String xml, TypeReference<?> typeReference) {
	        try {
	            return OM.readValue(xml, typeReference);
	        } catch (IOException e) {
	            Logger.error(e, "Could not deserialize JSON");
	            return null;
	        }
	    }
}