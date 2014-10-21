setenv KESOROOTPATH `pwd`

setenv JINOFLAGS "-nobuild -X:ssa:omit_fields:inline_checkcast:dbg_src"
setenv KESOSRCPATH "$KESOROOTPATH/src"
setenv TRACE32PATH "/proj/i4ciao/tools/bin"
setenv JOSEKRULES ${KESOROOTPATH}/josek/josek/code/arch/

setenv EPOS "$KESOROOTPATH/epos--"
setenv SYSGENDEFS '-D PC_framebuffer'

setenv PATH ${KESOROOTPATH}/bin:${EPOS}/bin:${PATH}

set ctag_proc=`which ctags-exuberant`
if ( -f "${ctag_proc}" ) then
	setenv CTAGBIN $ctag_proc 
else 
	echo "ctags-exuberant not found!"
	setenv CTAGBIN "ctags"
endif

set avrdude_proc=`which avrdude`
if ( -f "${avrdude_proc}" ) then
	setenv AVRDUDE $avrdude_proc 
else 
	echo "avrdude not found!"
endif

setenv JAVACC javacc
if ( -d "/usr/lib/j2se/1.4/" ) then
  setenv JDK /usr/lib/j2se/1.4/
else
  if ( -d "/local/java-1.4/" ) then
    setenv JDK /local/java-1.4
    setenv JDKTOOLS ${JDK}/lib/tools.jar
    setenv JAVACC /local/javacc/bin/javacc

  # Ubuntu Feisty Fawn OR Ubuntu Gutsy Gibbon (7.10)
  else if ( -d "/usr/lib/jvm/java-6-sun/" ) then
    setenv JDK /usr/lib/jvm/java-6-sun
    setenv JDKTOOLS ${JDK}/lib/tools.jar
    setenv JAVACC /usr/bin/javacc
    echo "Found Java 6 Sun for Ubuntu!"

  # Gentoo
  else if ( -d "/opt/jdk4keso" ) then
    setenv JDK /opt/jdk4keso
    setenv JDKTOOLS ${JDK}/lib/tools.jar
    setenv JAVACC /usr/bin/javacc
  else
    echo "warn: JDK not found"
  endif
endif
setenv JAVA ${JDK}/bin/java
setenv JAVAC ${JDK}/bin/javac
setenv JDKTOOLS ${JDK}/lib/tools.jar

if ( -d "/usr/local/tricore-gcc/" ) then
	setenv TOOLPATH /usr/local/tricore-gcc/ 
	setenv PATH ${PATH}:${TOOLPATH}bin/
else
	if ( -d "/proj/i4ciao/tools/trigcc/" ) then
		setenv TOOLPATH /proj/i4ciao/tools/trigcc/
		setenv PATH ${PATH}:${TOOLPATH}bin/
	endif
endif

if ( -d "/usr/local/ProOSEK" ) then
	setenv OSEK_BASE /usr/local/ProOSEK
	setenv PATH ${PATH}:${OSEK_BASE}/bin
	echo "setting ProOSEK to /usr/local/ProOSEK"
else
	if ( -d "${HOME}/ProOSEK" ) then
		setenv OSEK_BASE ${HOME}/ProOSEK
		setenv PATH ${PATH}:${OSEK_BASE}/bin
		echo "setting ProOSEK to ${HOME}/ProOSEK"
else 
	echo "warn: ProOSEK base directory not found!"
endif

if ( -d $TRACE32PATH ) then
	setenv PATH ${PATH}:$TRACE32PATH
endif
