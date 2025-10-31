package rogers.utility.app.utility;

import java.util.UUID;

import com.google.gson.Gson;

import rogers.utility.app.model.Response;


public class JsonResponseGenerator {
	
	public static String generateResponse(Object object,String generatorSource) {
		Response response = new Response();
		response.setRequestId(UUID.randomUUID().toString());
		response.setGenerator(generatorSource);
		response.setData(object);

		Gson gson = new Gson();
		String json = gson.toJson(response);

		return json;
	}
	
	
	
	public static String generateResponse(Object object) {
		Gson gson = new Gson();
		String json = gson.toJson(object);

		return json;
	}
	

}
