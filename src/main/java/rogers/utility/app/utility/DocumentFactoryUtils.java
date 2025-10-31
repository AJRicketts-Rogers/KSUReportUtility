package rogers.utility.app.utility;


import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



public class DocumentFactoryUtils {
	
	public static DocumentBuilderFactory getDbFactory() {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		}
		catch (ParserConfigurationException e) {
			 //	logger.logDebug("ParserConfigurationException - the feature is not supported by the XML processor: " + e.getMessage());
		}
		try {
			dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		}
		catch (IllegalArgumentException e) {
			//jaxp 1.5 feature not supported
		}
		dbFactory.setXIncludeAware(false);
		dbFactory.setExpandEntityReferences(false);
		return dbFactory;
			

	}

	




}
