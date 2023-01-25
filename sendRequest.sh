#!/bin/bash

# cat testfile.json | gzip | curl -i --data-binary @- -H "Content-Encoding: gzip" -H "Transfer-Encoding: chunked" -X POST "http://localhost:8080/Bug" -H "accept: application/json; charset=UTF-8" -H "Content-Type:application/json; charset=UTF-8"


#DEPLOYMENT_URL="https://jetty9-work.appspot.com"
DEPLOYMENT_URL="http://localhost:8080"
#DEPLOYMENT_URL="https://www.google.com/"

# cat testfile.json | gzip | curl -i -v --data-binary @- -H "Content-Encoding: gzip" -H "Transfer-Encoding: chunked" -X DELETE $DEPLOYMENT_URL/bug -H "accept: application/json; charset=UTF-8" -H "Content-Type:application/json; charset=UTF-8"


curl -v -H "Content-Type: text/plain" -H "User-Agent: Opera" -H "Accept-Encoding: gzip" $DEPLOYMENT_URL?size=1234