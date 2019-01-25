#!/bin/sh
echo "'./gen.sh' to build './gen.sh p' to build and preview"

java -jar ./static-app.jar --build

echo "Fixing the awful 3 May to 3 April fiasco"

cd html
find . -type f -name '*.html' -exec sed -i '' 's/3 May 2018/3 April 2018/' {} +
cd ..

if [ $# -eq 1 ] 
then
    if [ "$1" = "p" ]
    then
        cd html
        python -m SimpleHTTPServer
        cd ..
    fi
fi

