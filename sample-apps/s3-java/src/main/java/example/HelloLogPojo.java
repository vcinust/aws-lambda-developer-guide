package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import example.model.CustomRequest;
import example.model.CustomResponse;

import java.util.ResourceBundle;

/**
 * Converts a Json Object directly into a pre-defined java object.
 * This one is adjusted for the default Hello World template.
 *
 */
public class HelloLogPojo implements RequestHandler<CustomRequest,CustomResponse> {

  @Override
  public CustomResponse handleRequest(CustomRequest input, Context context) {
    String version = ResourceBundle.getBundle("aws-config").getString("version");

    StringBuilder sb = new StringBuilder();
    sb.append("Hello from Lambda ").append(this.getClass().getName()).append(".")
        .append(System.lineSeparator());
    sb.append("Version: ").append(version).append(System.lineSeparator());
    sb.append("Input:").append(input).append(System.lineSeparator());

    LambdaLogger logger = context.getLogger();
    logger.log(sb.toString());
    return new CustomResponse() ;
  }
}