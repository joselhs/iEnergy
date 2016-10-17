package util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;


public class PrinterHelper {

    public static String convertToXml(Object obj, String encoding, boolean partial) {
        String result;
        StringWriter sw = new StringWriter();
        try {
            JAXBContext carContext = JAXBContext.newInstance(obj.getClass());
            Marshaller carMarshaller = carContext.createMarshaller();
            if(StringUtils.isNotEmpty(encoding)) {
            	carMarshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            }
            carMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, partial);
            carMarshaller.marshal(obj, sw);
            result = sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
