#!/bin/bash
svn_partial=`head -n 1 < $1`
address=`tail -n 1 < $1`

svn checkout --username 'rita' --password 'Zp0pp1nsZ' $svn_partial

epos=`pwd`'/teaching'
cd $epos
mkdir -p log

sh ../poc.sh `pwd`
echo "Tem o enderessu da Tinha? $address"

cd $epos/log
zip -r $epos/report.zip *

sendmail -oi -t << EOF 
From: RITA@lisha.ufsc.br
To: ${address}
Subject: Tests results on `date` 

`cat $epos/report.log`

EOF


#mail -s "significa" $address < /dev/null
