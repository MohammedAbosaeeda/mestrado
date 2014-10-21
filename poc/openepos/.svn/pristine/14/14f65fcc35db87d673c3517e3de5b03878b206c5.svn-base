#!/bin/bash
openssl dhparam -C $1 | sed -n '/={/,/};/ p' | head -n -3 | grep '0x' | tr -d '\n\t' | sed 's/\(.*\),/"[\1]"/' | xargs ./generate_network_parameters.py  | sed 's/\[\([0-9 ,][0-9 ,]*\)\]/{\1};/'
