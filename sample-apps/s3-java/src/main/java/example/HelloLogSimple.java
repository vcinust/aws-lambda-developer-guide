package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * This class prints out basic information from the AWS Lambda function.
 *
 * Output for API should follow this format
 *
 * https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html#api-gateway-simple-proxy-for-lambda-output-format
 *
 *
 */
public class HelloLogSimple implements RequestHandler<Map<String,Object>,String> {

  /**
   * This method is called by AWS.
   * @param input Json Object from AWS
   * @param context Context Object from AWS
   * @return output value
   */
  @Override
  public String handleRequest(Map<String, Object> input, Context context) {
    String version = ResourceBundle.getBundle("aws-config").getString("version");

    StringBuilder sb = new StringBuilder();
    sb.append("Hello from Lambda ").append(this.getClass().getName()).append(".")
        .append(System.lineSeparator());
    sb.append("Version: ").append(version).append(System.lineSeparator());
    sb.append("Input:").append(input).append(System.lineSeparator());
    sb.append("Context: ").append(HelloLogFull.contextToString(context))
        .append(System.lineSeparator());
    sb.append("Reduced-Environment: ").append(reduceEnvironmentToString());

    LambdaLogger logger = context.getLogger();
    logger.log(sb.toString());
    return "HelloLogSimple";
  }

  /**
   * Removes most of the known environment variables, but should keep your own settings from
   * the AWS Management Console.
   * @return The reduced environment as a String.
   */
  private String reduceEnvironmentToString() {
    StringBuilder sb = new StringBuilder();

    // Keeping AWS_REGION and AWS_DEFAULT_REGION
    String [] ignoreVariables = new String[]{ "PATH","LAMBDA_TASK_ROOT",
        "AWS_LAMBDA_FUNCTION_MEMORY_SIZE","AWS_SECRET_ACCESS_KEY","AWS_LAMBDA_LOG_GROUP_NAME",
        "XFILESEARCHPATH","AWS_LAMBDA_LOG_STREAM_NAME","LANG","LAMBDA_RUNTIME",
        "LAMBDA_RUNTIME_DIR","AWS_SESSION_TOKEN", "AWS_ACCESS_KEY_ID","AWS_ACCESS_KEY",
        "LD_LIBRARY_PATH","NLSPATH","AWS_SECRET_KEY",
        "AWS_LAMBDA_FUNCTION_VERSION","AWS_LAMBDA_FUNCTION_NAME",
    };
    List<String> ignoreList = Arrays.asList(ignoreVariables);

    Map<String,String> environment = System.getenv();
    environment.entrySet()
        .stream()
        .filter(entry -> !ignoreList.contains(entry.getKey()))
        .forEach(entry -> {
          sb.append("{").append(entry.getKey()).append("=").append(entry.getValue()).append("},");
        });

    return sb.toString();
  }

}