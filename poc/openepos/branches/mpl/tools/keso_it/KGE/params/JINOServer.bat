@echo off
java  -classpath kge -Djava.rmi.server.codebase="file:/kge" keso.communication.jinoserver.KesoServer -workbench "serverworkbench" -name Server -aserver "rmi://localhost/KesoAuthServer" -jino "jino" -blacklist "blacklist" -wishlist "wishlist" -allfiles
