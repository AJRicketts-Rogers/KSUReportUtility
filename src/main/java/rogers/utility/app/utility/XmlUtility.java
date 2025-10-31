package rogers.utility.app.utility;



import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import rogers.utility.app.model.OrderItem;

public class XmlUtility {
	private static final Logger logger = LogManager.getLogger(XmlUtility.class);
    public static String xmlInput="";
    

    private static List<OrderItem> findChild(Document xmlDocument, ArrayList<String> chIds) {
        List<OrderItem>  listCh=new ArrayList<>();
        try {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String  expression ="//TransformedOrderItem/BaseLineId";
        //productSpec
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        for (int a=0;a< nodeList.getLength();a++) {

            Node node=nodeList.item(a);
            if(chIds.contains(node.getTextContent())) {
                NodeList nodeList2 = (NodeList) xPath.compile("LineName").evaluate(node.getParentNode(), XPathConstants.NODESET);
                NodeList nodeList3 = (NodeList) xPath.compile("ServiceActionCode").evaluate(node.getParentNode(), XPathConstants.NODESET);

                OrderItem item = new OrderItem();
                item.setId(node.getTextContent());
                item.setName(nodeList2.item(0).getTextContent());
                if(nodeList3!=null && nodeList3.getLength()>0)
                item.setAction(nodeList3.item(0).getTextContent());
                else{
                    item.setAction("ACTION Not SET");
                }
                listCh.add(item);
            }
        }

        }catch (Exception er){
        	logger.error("Exception in Reading Child",er);
        }

        return listCh;
    }


    public static HashMap<String, OrderItem> readXPath(String input){
        HashMap<String, OrderItem>  mapper=new HashMap<>();
        try {

            Document xmlDocument = convertStringToDocument(input.trim());
            //System.out.println("response "+input.trim());
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression ="//messageXmlData//orderItems/orderedProduct/affectedProduct/ID";
            //productSpec
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int a=0;a< nodeList.getLength();a++) {
                Node node=nodeList.item(a);
                NodeList nodeList2 = (NodeList) xPath.compile("action/code").evaluate(node.getParentNode().getParentNode(), XPathConstants.NODESET);
                NodeList nodeList3 = (NodeList) xPath.compile("productSpec/code").evaluate(node.getParentNode(), XPathConstants.NODESET);
                NodeList nodeList4 = (NodeList) xPath.compile("children//affectedProduct/ID").evaluate(node.getParentNode().getParentNode(), XPathConstants.NODESET);
                NodeList nodeList5 = (NodeList) xPath.compile("orderItemReferenceNumber").evaluate(node.getParentNode().getParentNode().getParentNode(), XPathConstants.NODESET);

                String name="";

                OrderItem item=new OrderItem();
                item.setAction(nodeList2.item(0).getTextContent());
                item.setName(nodeList3.item(0).getTextContent());
                item.setId(node.getTextContent());
                item.setUnitofOrder(nodeList2.item(0).getTextContent());
                ArrayList<String> ch=new ArrayList<>();
                ch.add(node.getTextContent());
                for (int a1=0;a1< nodeList4.getLength();a1++) {
                    Node nodech = nodeList4.item(a1);
                    ch.add(nodech.getTextContent());
                }
               // System.out.println("Childs>> "+ch);
                item.setChildList(findChild(xmlDocument,ch));
                mapper.put(node.getTextContent(),item);
            }

            logger.debug("Mapping Generated >> "+mapper);
        }catch (Exception er){
        	logger.error("Exception in Xpath",er);
        }
        return mapper;
    }




    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentFactoryUtils.getDbFactory();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
        	logger.error("Exception in XML parsing",e);
        }
        return null;
    }
}