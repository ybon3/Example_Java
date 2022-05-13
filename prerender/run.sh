#!/bin/sh
cd /root/prerender

mvn compile exec:java -Dstart-class=com.nkg.prerender.Application
