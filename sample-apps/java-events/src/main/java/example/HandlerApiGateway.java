package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.nashorn.internal.parser.JSONParser;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

// Handler value: example.HandlerApiGateway
public class HandlerApiGateway implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context)
	{
		LambdaLogger logger = context.getLogger();
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		response.setIsBase64Encoded(false);
		response.setStatusCode(200);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "text/html");
		response.setHeaders(headers);
		response.setBody("<!DOCTYPE html><html><head><title>AWS Lambda sample</title></head><body>"+
				"<h1>Welcome</h1><p>Page generated by a Lambda function.</p>" +
				"</body></html>");
		// log execution details
		Util.logEnvironment(event, context, gson);
		return response;
	}

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
		APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
		try {
			String requestString = apiGatewayProxyRequestEvent.getBody();
			JSONParser parser = new JSONParser();
			JSONObject requestJsonObject = (JSONObject) parser.parse(requestString);
			String requestMessage = null;
			String responseMessage = null;
			if (requestJsonObject != null) {
				if (requestJsonObject.get("requestMessage") != null) {
					requestMessage = requestJsonObject.get("requestMessage").toString();
				}
			}
			Map<String, String> responseBody = new HashMap<String, String>();
			responseBody.put("responseMessage", requestMessage);
			responseMessage = new JSONObject(responseBody).toJSONString();
			generateResponse(apiGatewayProxyResponseEvent, responseMessage);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return apiGatewayProxyResponseEvent;
	}
	private void generateResponse(APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent, String requestMessage) {
		apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("timeStamp", String.valueOf(System.currentTimeMillis())));
		apiGatewayProxyResponseEvent.setStatusCode(200);
		apiGatewayProxyResponseEvent.setBody(requestMessage);
	}
}