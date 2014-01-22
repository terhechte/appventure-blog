#lein run --build
java -jar ./static-app.jar --build
scp -r html/* git@sport-news.de:/var/www_bene/appventure.me/new/