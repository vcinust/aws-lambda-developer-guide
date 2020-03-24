#!/bin/bash
set -eo pipefail
FUNCTION=$(aws cloudformation describe-stack-resource --stack-name java-events --logical-resource-id function --query 'StackResourceDetail.PhysicalResourceId' --output text)
if [ $1 ]
then
  case $1 in
    apig)
      PAYLOAD='file://events/apigateway-v1.json'
      ;;
    cws)
      PAYLOAD='file://events/cloudwatch-scheduled.json'
      ;;
    sns)
      PAYLOAD='file://events/sns.json'
      ;;
    *)
      echo -n "Unknown event type"
      ;;
  esac
fi
while true; do
  if [ $PAYLOAD ]
  then
    aws lambda invoke --function-name $FUNCTION --payload $PAYLOAD out.json
  else
    aws lambda invoke --function-name $FUNCTION --payload file://event.json out.json
  fi
  cat out.json
  echo ""
  sleep 2
done
