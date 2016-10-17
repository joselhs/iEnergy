package util;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateSerializer extends JsonSerializer<Date> {
    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void serialize(Date o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (o != null) {
            jsonGenerator.writeString(DF.format(o));
        }
    }
}