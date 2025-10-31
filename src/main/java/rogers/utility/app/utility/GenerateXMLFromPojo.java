package rogers.utility.app.utility;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class GenerateXMLFromPojo {

    public static String convert(Object response){

        String respo=null;
        try {
            XmlMapper xmlMapper = new XmlMapper();

            respo = xmlMapper.writeValueAsString(response);

        } catch(Exception e) {
            
        }
        return respo;
    }

}
