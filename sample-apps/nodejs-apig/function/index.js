const AWS = require('aws-sdk')
// Create client outside of handler to reuse
const lambda = new AWS.Lambda()

// Handler
exports.handler = function(event, context) {
  console.log('ENVIRONMENT VARIABLES: ' + serialize(process.env))
  console.log('CONTEXT: ' + serialize(context))
  console.log('EVENT: ' + serialize(event))
  return getAccountSettings()
}

// Use SDK client
var getAccountSettings = function(){
  return lambda.getAccountSettings().promise()
}

var serialize = function(object) {
  return JSON.stringify(object, null, 2)
}