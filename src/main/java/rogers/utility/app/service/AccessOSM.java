package rogers.utility.app.service;

public class AccessOSM {

	
	String session=null;
	String user="osmqa5_admin";
	String pass="welcome123";
	String osmUrl="http://osmqa5.ngpp.mgmt.vf.rogers.com:7001";
	private String osmLogin(){
		String sessionVal=null;
		String loginUrl=osmUrl+"/OrderManagement/XMLAPI/login";
		try {
			
			HttpConnection con = new HttpConnection(loginUrl);
			con.makeHttpRequest("POST", "", "username="+user+"&password="+pass);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		}
		
		
		return sessionVal;
	};
	private String osmLogout(){
		String sessionVal=null;
		String loginUrl=osmUrl+"/OrderManagement/XMLAPI/logout";
		try {
			
			HttpConnection con = new HttpConnection(loginUrl);
			con.makeHttpRequest("POST", "", "username="+user+"&password="+pass);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		}
		
		
		return sessionVal;
	};
	private String osmgetOrder(){
		String sessionVal=null;
		String loginUrl=osmUrl+"/OrderManagement/XMLAPI/worklist";
		try {
			
			HttpConnection con = new HttpConnection(loginUrl);
			String input="<GetOrderAtTask.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\">\r\n" + 
					"   <OrderID>1373263</OrderID>\r\n" +
					"   <Task>CaptureBITask</Task>\r\n" + 
					"</GetOrderAtTask.Request>";
		String  response=	con.makeHttpRequest("POST", input,null);
		System.out.println("Response "+response);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		
		
		return sessionVal;
	};
	
	public static void main(String[] args) {
		AccessOSM  osm=new AccessOSM();
		osm.osmLogin();
		osm.osmgetOrder();
		osm.osmLogout();
	}
}
