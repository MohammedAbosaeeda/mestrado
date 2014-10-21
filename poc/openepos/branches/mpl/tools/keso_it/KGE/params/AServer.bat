@echo off
java  -classpath kge -Djava.rmi.server.codebase="file:/kge" keso.communication.authserver.KesoAuthenticationServer -name KesoAuthServer
