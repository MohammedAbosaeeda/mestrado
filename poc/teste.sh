#!/bin/bash

grep 

if !grep -xq "****TAP - test successful" $EPOS/log/task_test.log; then
 echo "encontrei"
else
 echo "eita, falho"
fi
