#!/bin/bash
START=`date +%H:%M`
 
echo $EPOS
 
END=`date +%H:%M`

diff=$(  echo "$END - $START"  | sed 's%:%+(1/60)*%g' | bc -l )
echo $diff hours
