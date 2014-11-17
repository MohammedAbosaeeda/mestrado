#!/bin/bash

hook=`ls *.txt | head -1`
nomeArquivo=`echo ${hook}`

if [ -e ${nomeArquivo} ]; then
  echo ${nomeArquivo} >> bla
  rm ${nomeArquivo}
fi
