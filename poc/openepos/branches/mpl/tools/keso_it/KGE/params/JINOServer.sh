#!/bin/sh

java -Djava.security.policy="security.policy" -Djava.rmi.server.codebase="file:/kge" -classpath "kge" keso.communication.jinoserver.KesoServer -workbench "serverworkbench" -name Server -aserver "rmi://localhost/KesoAuthServer" -jino "./jino" -blacklist "blacklist" -wishlist "wishlist" -allfiles
