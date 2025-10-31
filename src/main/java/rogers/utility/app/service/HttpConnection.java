package rogers.utility.app.service;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Class that handles GET, POST and DELETE HTTP connection requests
 * 
 * @author Tarini
 * @version 0.1
 *
 */
public class HttpConnection {

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String DELETE = "DELETE";
	public static String cokie=null;
	protected Log logger = LogFactory.getLog(HttpConnection.class);

	private String url;

	/**
	 * Contructor that initializes the url class variable
	 * 
	 * @param url
	 *            String object that contains the remote endpoint
	 */
	public HttpConnection(String url) {
		super();
		this.url = url;
	}

	/**
	 * Default constructor
	 */
	public HttpConnection() {
		super();
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public String makeHttpRequest(String sMethod, String sBody, String token) {
		String response = "";

		System.out.println("URL : " + url);

		// REST web service call
		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						if(cokie==null) {
						cokie=response.getHeaders("Set-Cookie")[0].getValue().split(";")[0];
						System.out.println("############# "+cokie);
						}
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						//cokie=response.getHeaders("Set-Cookie")[0].getValue().split(";")[0];
						System.out.println("############# "+EntityUtils.toString(response.getEntity()));
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};

			String responseBody = "";

			if (sMethod.equalsIgnoreCase("GET")) {
				HttpGet httpGet = new HttpGet(url);
				responseBody = httpclient.execute(httpGet, responseHandler);
			} else if (sMethod.equalsIgnoreCase("POST")) {
				String loginUrl=null;
				if (token != null) {
					loginUrl=url+"?"+token;
				}else {
					loginUrl=url;
				}
				HttpPost httpPost = new HttpPost(loginUrl);
				// HttpEntity entity = new ByteArrayEntity(sBody.getBytes("UTF-8"));
				
				if(cokie!=null) {
					httpPost.addHeader("Content-Type", "text/xml");
					System.out.println("Setting Cokkies "+cokie);
					httpPost.setHeader("Cookie", cokie.trim());	
					httpPost.setHeader("Cookie2", "$Version=1");
				}else {
					httpPost.addHeader("Content-Type", "application/xml");
				}
				// httpPost.addHeader("Authorization", "Bearer " + token);
				StringEntity entity = new StringEntity(sBody, ContentType.TEXT_PLAIN);
				httpPost.setEntity(entity);
				responseBody = httpclient.execute(httpPost, responseHandler);
			} else {
				System.out.println("Unknown HTTPS method");
			}

			response = responseBody;
		} catch (ClientProtocolException cp) {
			System.out.println(cp.getMessage());
			response = "";
		} catch (IOException io) {
			System.out.println(io.getMessage());
			response = "";
		} finally {
			try {
				httpclient.close();
			} catch (IOException ie) {
				System.out.println(ie.getMessage());
			}
		}

		return response;
	}

}
