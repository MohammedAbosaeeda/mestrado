#!/usr/bin/python
from BeautifulSoup import BeautifulSoup

configurationFile = open('philosopher_dinner_conf.xml', 'r')
config = BeautifulSoup(configurationFile.read())

print config.findAll("trait").lengh

print config
