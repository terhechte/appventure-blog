#!/bin/sh
#lein run --build
java -jar ./static-app.jar --build
cd html
python -m SimpleHTTPServer
cd ..