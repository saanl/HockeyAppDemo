#!/usr/bin/env bash
#
# For Android apps, update the contents of the google-service.json file.
# This can be used for an app that is open source, or to have a different configuration
# for different branches.
#
# Suppose in our project exists two branches: master and develop. 
# We can release an app for production push notifications from the master branch 
# and a version of the app for test push notifications from the develop branch. 
# We just need configure this behaviour with environment variable in each branch :)
#
# DECLARE THE GOOGLE_SERVICES_JSON ENVIRONMENT VARIABLE IN APP CENTER BUILD CONFIGURATION, SET
# TO THE CONTENTS OF YOUR google-services.json FILE

echo "##########################NPM INSTALL###########################"

npm i commander
npm i moment
npm i request


node script.txt -a 18434391078-163.com/HockeyApp1 -t e687aef8e656a7a0c98e3d8d5be86fe0ce17bb35 -v 1 --sep #

echo "#####################################################"



if [ -z "$str4" ]
then

	curl -i -X PATCH -H "X-API-Token:$APPCENTER_API_TOKEN" -H "Content-Type: application/json" -d "{\"status\":\"cancelling\"}" https://appcenter.ms/api/v0.1/apps/18434391078-163.com/HockeyApp1/builds/$APPCENTER_BUILD_ID	
	exit
fi


