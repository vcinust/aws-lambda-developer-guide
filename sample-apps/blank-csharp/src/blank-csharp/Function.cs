using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Amazon;
using Amazon.Util;
using Amazon.Lambda;
using Amazon.Lambda.Model;
using Amazon.Lambda.Core;
using Amazon.XRay.Recorder.Core;
using Amazon.XRay.Recorder.Handlers.AwsSdk;

[assembly: LambdaSerializer(typeof(Amazon.Lambda.Serialization.Json.JsonSerializer))]

namespace blankCsharp
{
    public class Function
    {
        private static AmazonLambdaClient lambdaClient;

        static Function() {
          AWSSDKHandler.RegisterXRayForAllServices();
          lambdaClient = new AmazonLambdaClient();
        }

        public async Task<AccountUsage> FunctionHandler(Dictionary<string, string> input, ILambdaContext context)
        {
          GetAccountSettingsResponse accountSettings;
          try
          {
            accountSettings = await callLambda();
          }
          catch (AmazonLambdaException ex)
          {
            throw ex;
          }
          var accountUsage = accountSettings.AccountUsage;
          Console.WriteLine(accountUsage);
          return accountUsage;
        }

        public async Task<GetAccountSettingsResponse> callLambda()
        {
          var request = new GetAccountSettingsRequest();
          var response = await lambdaClient.GetAccountSettingsAsync(request);
          return response;
        }
    }
}
