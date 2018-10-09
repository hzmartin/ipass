#!/bin/sh

git pull

REL_DIR=/root/ipass


sh mvnw clean 

cp conf/* $REL_DIR/

sh mvnw package

cp target/ipass-0.0.1-SNAPSHOT.jar $REL_DIR/
cp scripts/start.sh $REL_DIR/
cp conf/* $REL_DIR/
