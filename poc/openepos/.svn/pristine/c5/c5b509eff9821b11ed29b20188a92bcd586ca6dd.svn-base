#!/usr/bin/env python3
from sys import argv
from sys import exit
import ast

def egcd(a, b):
	if a == 0:
		return (b, 0, 1)
	else:
		g, y, x = egcd(b % a, a)
		return (g, x - (b // a) * y, y)

def modinv(a, m):
	g, x, y = egcd(a, m)
	if g != 1:
		print (a)
		raise Exception('modular inverse does not exist')
	else:
		return x % m

def hextodec(a):
	# a should be in big-endian
	t = 0
	for i in a:
		t *= 256
		t += i
	return t

def dectohex(a):	
	t = []
	while a != 0:
		t.append(a%256)
		a >>= 8
	#output is little-endian
	return t

if len(argv) < 2:
	print ("Usage: " + argv[0] + " <prime_number_in_hex>")
	exit()

num=ast.literal_eval(argv[1])
sz = len(num)
p = hextodec(num)

R = 256 ** sz
Rmodm = R % p
R2modm = (R*R) % p
mp = modinv(p, 256)
mp = (-mp) % 256


print(' ')
print(' ')
print('P:', p)
print('Rmodm:', Rmodm)
print('R2modm:', R2modm)
print('mp:', mp)
print(' ')
print(' ')

print('//', sz*8, end="")
print('-bit parameters')
print('unsigned char prime_raw_data[] =', dectohex(p))
print('unsigned char primitive_root_raw_data[] = [2', end="")
for a in range(sz-1):
	print(',0',end="")
print(']')
print('unsigned char Rmodm_raw_data[] =', dectohex(Rmodm))
print('unsigned char R2modm_raw_data[] =', dectohex(R2modm))
print('unsigned int _mp =', mp, end="")
print(';')
