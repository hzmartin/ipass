#!/bin/sh

git pull

REL_DIR=/root/ipass

cp conf/* src/main/resources

sh mvnw package

cp target/ipass-0.0.1-SNAPSHOT.jar $REL_DIR/
