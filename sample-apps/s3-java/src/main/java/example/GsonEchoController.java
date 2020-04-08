package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * Output for API should follow this format
 *
 * https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html#api-gateway-simple-proxy-for-lambda-output-format
 *
 */
public class GsonEchoController implements RequestHandler<Object, Map<String, Object>> {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    @Override
    public Map<String, Object> handleRequest(Object input, Context context) {
        logger.info("INPUT: " + gson.toJson(input));


        Map<String, Object> response = new HashMap<String, Object>();
        response.put("statusCode", 200);
        response.put("body", gson.toJson(input));
//        return "Received message: Update " + gson.toJson(input);

        return response;
    }
}