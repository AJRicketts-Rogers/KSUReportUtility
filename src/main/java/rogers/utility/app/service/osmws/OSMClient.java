
package rogers.utility.app.service.osmws;

import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class OSMClient {
	// extends WebServiceGatewaySupport {

	private static final Logger logger = LogManager.getLogger(OSMClient.class);
	String userws = "";
	String passws = "";
	String urlws="";
	String urlwsSecondary="";
	public OSMClient(String url,String urlSecondary,String user, String pass) {
		this.urlws=url;
		this.urlwsSecondary=urlSecondary;
		this.userws = user;
		this.passws = pass;
		// TODO Auto-generated constructor stub
	}
	

	public String extractXMl(String osmId) throws Exception {
		String resp = null;

			String soapBody = "<soapenv:Envelope xmlns:ord=\"http://xmlns.oracle.com/communications/ordermanagement\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+ "   <soapenv:Header>"
					+ "      <wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
					+ "         <wsse:UsernameToken wsu:Id=\"UsernameToken-E426B4ED9B65B6829F167716727573911\">"
					+ "            <wsse:Username>" + userws + "</wsse:Username>"
					+ "            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"+passws + "</wsse:Password>" + "         </wsse:UsernameToken>" + "      </wsse:Security>"
					+ "   </soapenv:Header>" + "   <soapenv:Body>" + "      <ord:GetOrder>" + "         <ord:OrderId>"
					+ osmId + "</ord:OrderId>" + "         <ord:View>COM_SalesOrderFulfillment_Details</ord:View>"
					// +" <ord:View>COM_XMLView</ord:View>"

					+ "      </ord:GetOrder>" + "   </soapenv:Body>" + "</soapenv:Envelope>";

			org.apache.http.client.CredentialsProvider cred= new BasicCredentialsProvider();
			cred.setCredentials(AuthScope.ANY, new org.apache.http.auth.NTCredentials(userws,passws,"",""));
		
			HttpClient httpclient = HttpClientBuilder.create().setDefaultCredentialsProvider(cred).build();
			// tool
			StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
			// URL of request
			HttpPost post = new HttpPost("http://"+urlws+"/OrderManagement/wsapi");
			post.setHeader("SOAPAction", "http://xmlns.oracle.com/communications/ordermanagement/GetOrder");
	

			post.setEntity(strEntity);

			// Execute request
			HttpResponse response = httpclient.execute(post);
			HttpEntity respEntity = response.getEntity();

			if (respEntity != null) {
				resp = EntityUtils.toString(respEntity);

				// prints whole response
				// System.out.println(resp);

			} else {
				logger.error("Response is empty ");
			}

		
		return resp;
	}

	public String extractXMlSeconday(String osmId)  throws Exception {
		String resp = null;


			String soapBody = "<soapenv:Envelope xmlns:ord=\"http://xmlns.oracle.com/communications/ordermanagement\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+ "   <soapenv:Header>"
					+ "      <wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
					+ "         <wsse:UsernameToken wsu:Id=\"UsernameToken-E426B4ED9B65B6829F167716727573911\">"
					+ "            <wsse:Username>" + userws + "</wsse:Username>"
					+ "            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"+passws + "</wsse:Password>" + "         </wsse:UsernameToken>" + "      </wsse:Security>"
					+ "   </soapenv:Header>" + "   <soapenv:Body>" + "      <ord:GetOrder>" + "         <ord:OrderId>"
					+ osmId + "</ord:OrderId>" + "         <ord:View>COM_SalesOrderFulfillment_Details</ord:View>"
					// +" <ord:View>COM_XMLView</ord:View>"

					+ "      </ord:GetOrder>" + "   </soapenv:Body>" + "</soapenv:Envelope>";

			org.apache.http.client.CredentialsProvider cred= new BasicCredentialsProvider();
			cred.setCredentials(AuthScope.ANY, new org.apache.http.auth.NTCredentials(userws,passws,"",""));
		
			HttpClient httpclient = HttpClientBuilder.create().setDefaultCredentialsProvider(cred).build();
			// You can get below parameters from SoapUI's Raw request if you are using that
			// tool
			StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
			// URL of request
			HttpPost post = new HttpPost("http://"+urlwsSecondary+"/OrderManagement/wsapi");
			post.setHeader("SOAPAction", "http://xmlns.oracle.com/communications/ordermanagement/GetOrder");
			
			

			post.setEntity(strEntity);

			// Execute request
			HttpResponse response = httpclient.execute(post);
			HttpEntity respEntity = response.getEntity();

			if (respEntity != null) {
				resp = EntityUtils.toString(respEntity);

				// prints whole response
				// System.out.println(resp);

			} else {
				logger.error("Response is empty ");
			}

		
		return resp;
	}
}
