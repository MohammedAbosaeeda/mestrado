export KESOROOTPATH=`pwd`

export JINOFLAGS="-nobuild -X:ssa:omit_fields:inline_checkcast:dbg_src"
export KESOSRCPATH="$KESOROOTPATH/src"
export TRACE32PATH="/proj/i4ciao/tools/bin"
export JOSEKRULES="$KESOROOTPATH/josek/josek/code/arch/"

export EPOS="$KESOROOTPATH/epos--"

export PATH=${KESOROOTPATH}/bin:${EPOS}/bin:${PATH}

export CTAGBIN="ctags-exuberant"
export AVRDUDE="avrdude"

export JAVACC=javacc
if [ -d "/usr/lib/j2se/1.4/" ] ; then
  export JDK=/usr/lib/j2se/1.4/
else
  if [ -d "/local/java-1.4/" ] ; then
    export JDK=/local/java-1.4
    export JAVACC=/local/javacc/bin/javacc

  # Ubuntu Feisty Fawn OR Ubuntu Gutsy Gibbon (7.10)
  elif [ -d "/usr/lib/jvm/java-6-sun" ] ; then
    export JDK=/usr/lib/jvm/java-6-sun
    export JAVACC=/usr/bin/javacc
    echo "Found Java 6 Sun for Ubuntu!"

  # Gentoo (jdk4keso is a symlink that has to be created manually)
  elif [ -d "/opt/jdk4keso" ] ; then
    export JDK=/opt/jdk4keso
    export JAVACC=/usr/bin/javacc

  else
    echo "warn: JDK not found"
  fi
fi
export JAVA=${JDK}/bin/java
export JAVAC=${JDK}/bin/javac
export JDKTOOLS=${JDK}/lib/tools.jar

if [ -d "/proj/i4ciao/tools/trigcc/" ] ; then
	export TOOLPATH=/proj/i4ciao/tools/trigcc/
	export PATH=${PATH}:${TOOLPATH}bin/
fi

if [ -d "/usr/local/ProOSEK" ] ; then
	export OSEK_BASE=/usr/local/ProOSEK
	export PATH=${PATH}:${OSEK_BASE}/bin
elif [ -d "$HOME/ProOSEK" ] ; then
	export OSEK_BASE=$HOME/ProOSEK
	export PATH=${PATH}:${OSEK_BASE}/bin
else
    echo "warn: ProOSEK base directory not found!"
fi

if [ -d $TRACE32PATH ] ; then
	export PATH=${PATH}:$TRACE32PATH
fi
