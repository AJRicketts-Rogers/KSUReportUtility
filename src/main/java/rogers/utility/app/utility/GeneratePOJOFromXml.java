package rogers.utility.app.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.nio.file.Files;

public class GeneratePOJOFromXml {

    public static Object convert(String xmlData,Class T){
        Object aa=null;

        try {
            XmlMapper xmlMapper = new XmlMapper();
            // xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            //xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            aa=   xmlMapper.readValue(xmlData, T);

        } catch(Exception e) {
           
        }
        return aa;
    }

    public static Object convert(File xmlData, Class T){
        Object aa=null;

        try {
            XmlMapper xmlMapper = new XmlMapper();
            // xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            //xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            aa=   xmlMapper.readValue(xmlData, T);

        } catch(Exception e) {
           
        }
        return aa;
    }
}
