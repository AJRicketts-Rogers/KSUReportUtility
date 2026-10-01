package rogers.utility.app.utility;



import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.stream.*;
import java.util.Stack;

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

    
    public static HashMap<String, OrderItem> readXPathNew(String input) {
    	XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    	InputStream in;
    	HashMap<String, OrderItem> orderedProducts = new HashMap<>();
    	HashMap<String, OrderItem> transformedOrderItems = new HashMap<>();
    	
    	try {
			in = new ByteArrayInputStream(input.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new AssertionError("UFT-8 is unknown, (This is impossible).");
		}
    	
    	try {
			XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
			
			String transformedOrderItemPath = "Envelope/Body/GetOrderResponse/Data/_root/ControlData/TransformedOrderItem";
			String originalTarget = "Envelope/Body/GetOrderResponse/Data/_root/messageXmlData/OMSOrder/FulfillmentOrderSvcRequestFulfillmentOrderInputs/ProductOrder/orderItems/orderedProduct";
			Stack<String> elementPath = new Stack<>();
			
			
            while (streamReader.hasNext()) {
	            int event = streamReader.next();
	            
	            switch (event) {
	                case XMLStreamConstants.START_ELEMENT:
	                    elementPath.push(streamReader.getLocalName());
	                    String joinedPath = String.join("/", elementPath);
	                    
	                    if (joinedPath.equals(transformedOrderItemPath)) {
//	                    	logger.debug("\nCalling parseTransformedOrderItem");
	                    	OrderItem item = parseTransformedOrderItem(streamReader);

	                        if (item != null) {
	                            transformedOrderItems.put(item.getId(), item);
	                        }
	                        // The sub-parser consumed the END tag. Repair the stack.
	                        if (!elementPath.isEmpty()) {
	                        	elementPath.pop();
	                        }
	                        
	                        break; // Done with this START event.

	                    	
	                    }
	                    
	                    if (joinedPath.equals(originalTarget)) {
//	    		            logger.debug("\nCalling parseOrderedProduct");
	                    	OrderItem orderItem = parseOrderedProduct(streamReader);
	                    	
	                        if (orderItem != null) {
	                        	orderedProducts.put(orderItem.getId(), orderItem);
	                        }
	                        // The sub-parser consumed the END tag. Repair the stack.
	                        if (!elementPath.isEmpty()) {
	                        	elementPath.pop();
	                        }
	                        
	                        break; // Done with this START event.
	                    }
	                    
	                    break; // normal START without nested parse
	                    
	                case XMLStreamConstants.END_ELEMENT:
	                    elementPath.pop();
	                    break;
	            }
            }
            
            // All order items and child items have been built, now match them up
            for (OrderItem oi : orderedProducts.values()) {
            	ArrayList<OrderItem> mappedChildren = new ArrayList<OrderItem>();
            	
            	for (String childId : oi.getChildIdList()) {
            		if (transformedOrderItems.containsKey(childId)) {
            			mappedChildren.add(transformedOrderItems.get(childId));
            		}
            	}
            	
            	oi.setChildList(mappedChildren);
            }
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
			logger.error("Error reading XML, check that it is not malformed", e);
		} catch (Exception e) {
			logger.error("Error calling parseTransformedOrderItem() or parseOrderedProduct(), check logs", e);
			e.printStackTrace();
		}
    	
    	if (!orderedProducts.isEmpty()) {
    		logger.debug("Mapping Generated >> " + orderedProducts + "\n\n");    		
    	}
    	
    	return orderedProducts;
    }
    
    

    private static OrderItem parseTransformedOrderItem(XMLStreamReader streamReader) throws Exception {

        OrderItem item = new OrderItem();
        String currentElement = null;

        Stack<String> localPath = new Stack<>();
        localPath.push("transformedOrderItem");
//        logger.debug("INSIDE parseTransformedOrderItem()");

        
        try {
	        while (streamReader.hasNext()) {
	            int event = streamReader.next();
	
	            switch (event) {
	
	                case XMLStreamConstants.START_ELEMENT:
	                    currentElement = streamReader.getLocalName();
	                    localPath.push(currentElement);
	                    break;
	
	                case XMLStreamConstants.CHARACTERS:
	                    String text = streamReader.getText().trim();
	                    if (text.isEmpty()) {
	                    	break;
	                    }
	
	                    if ("BaseLineId".equals(currentElement)) {
	                        item.setId(text);
	                    } 
	                    else if ("LineName".equals(currentElement)) {
	                        item.setName(text);
	                    } 
	                    else if ("ServiceActionCode".equals(currentElement)) {
	                        item.setAction(text);
	                    }
	                    break;
	
	                case XMLStreamConstants.END_ELEMENT:
	                    // Pop the element name off the stack when the element closes
	                	localPath.pop();
	                    if (localPath.isEmpty()) {
//	                        logger.debug(item);
	                        return item;
	                    }
	                    break;
	            }
	        }
        } catch (XMLStreamException e) {
			e.printStackTrace();
			logger.error("Error reading XML, check that it is not malformed", e);
			return null;
		}
//        logger.debug(item);
        return item;
    }

    private static OrderItem parseOrderedProduct(XMLStreamReader streamReader) throws Exception {
        	
    	
        OrderItem item = new OrderItem();
        String currentElement = null;
        Stack<String> localPath = new Stack<>();
        localPath.push("orderedProduct");
        ArrayList<String> childList = new ArrayList<String>();
        
//        logger.debug("INSIDE parseOrderedProduct()");
   
        try {
	        while (streamReader.hasNext()) {
	            int event = streamReader.next();
	            
	            switch (event) {
	            	
	                case XMLStreamConstants.START_ELEMENT:
	                    currentElement = streamReader.getLocalName();
	                    localPath.push(currentElement);
	                    break;
	                    

	                case XMLStreamConstants.CHARACTERS:
	                    String text = streamReader.getText().trim();
	                    if (text.isEmpty()) break;

	                    String path = String.join("/", localPath);

	                    if (path.endsWith("orderedProduct/affectedProduct/ID")) {
	                        item.setId(text);
	                    }

	                    else if (path.endsWith("orderedProduct/action/code")) {
	                    	if (!text.equals("null")) {
	                    		item.setAction(text);	                    		
	                    	}
	                        else {
	                            item.setAction("ACTION Not SET");
	                        }
	                    }

	                    else if (path.endsWith("orderedProduct/affectedProduct/productSpec/code")) {
	                        item.setName(text);
	                    }

	                    else if (path.endsWith("orderedProduct/children/affectedProduct/ID")) {
	                        childList.add(text);
	                    }

	                    break;

	                case XMLStreamConstants.END_ELEMENT:
	                    localPath.pop();

	                    // finished consuming the subtree
	                    if (localPath.isEmpty()) {
	                    	childList.add(item.getId());
	                        item.setChildIdList(childList);
//	                        logger.debug(item);
	                        return item;
	                    }
	                    break;
	            }
	        }

        } catch (XMLStreamException e) {
			e.printStackTrace();
			logger.error("Error reading XML, check that it is not malformed", e);
			return null;
		}
        
        childList.add(item.getId());
        item.setChildIdList(childList);
//        logger.debug(item);
        return item;
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
                logger.debug("\n\nNode #" + a + "= " + node.getTextContent());
                logger.debug("item = " + item + "\n\n");
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