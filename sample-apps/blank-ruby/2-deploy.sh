#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET=$(cat bucket-name.txt)
cd function
rm -rf vendor
rm function/Gemfile.lock
bundle config set path 'vendor/bundle'
bundle install
cd ../
aws cloudformation package --template-file template.yml --s3-bucket $ARTIFACT_BUCKET --output-template-file out.yml
aws cloudformation deploy --template-file out.yml --stack-name blank-ruby --capabilities CAPABILITY_NAMED_IAM
