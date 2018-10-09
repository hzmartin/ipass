#!/bin/sh

git pull

REL_DIR=/root/ipass


sh mvnw clean 

cp conf/logback.xml $REL_DIR/

sh mvnw package

cp target/ipass-0.0.1-SNAPSHOT.jar $REL_DIR/
cp conf/application.properties $REL_DIR/
cp scripts/start.sh $REL_DIR/
chmod +x $REL_DIR/start.sh
