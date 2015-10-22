#!/bin/sh
echo "'./gen.sh' to build './gen.sh p' to build and preview"

java -jar ./static-app.jar --build

if [ $# -eq 1 ] 
then
    if [ "$1" = "p" ]
    then
        cd html
        python -m SimpleHTTPServer
        cd ..
    fi
fi