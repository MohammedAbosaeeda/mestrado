#!/bin/sh
java -Djava.security.policy="security.policy" -Djava.rmi.server.codebase="file:/kge" -classpath "kge" keso.communication.authserver.KesoAuthenticationServer -name KesoAuthServer
