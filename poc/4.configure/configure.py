#!/usr/bin/python

import xml.sax

class ConfigurationHandler( xml.sax.ContentHandler ):
   def __init__(self):
      self.name = ""
      self.min = ""
      self.max= ""
      self.CurrentData = ""
      self.apps = ""

      tap_file = open('tap', 'w')

      tap_file.write("#!/bin/bash\n")
      tap_file.write("EPOS_DIR=$1\n")
      tap_file.write("cd $EPOS_DIR/src/abstraction\n")

   # Call when an element starts
   def startElement(self, tag, attributes):
      self.CurrentData = tag
      if tag == "application":
         application = attributes["name"]
         self.apps = self.apps + "," + application
         print "apps:", apps

      if tag == "trait":
         print "*****trait*****"
         trait = attributes["id"]
         print "trait Name:", trait


   # Call when an elements ends
   def endElement(self, tag):
      if self.CurrentData == "min":
         print "min:", self.min

      elif self.CurrentData == "max":
         print "max:", self.max

      elif self.CurrentData == "year":
         print "Year:", self.year
      elif self.CurrentData == "rating":
         print "Rating:", self.rating
      elif self.CurrentData == "stars":
         print "Stars:", self.stars
      elif self.CurrentData == "description":
         print "Description:", self.description
      self.CurrentData = ""

   # Call when a character is read
   def characters(self, content):
      if self.CurrentData == "min":
         self.min = content
      elif self.CurrentData == "max":
         self.max = content
      elif self.CurrentData == "year":
         self.year = content
      elif self.CurrentData == "rating":
         self.rating = content
      elif self.CurrentData == "stars":
         self.stars = content
      elif self.CurrentData == "description":
         self.description = content
  
if ( __name__ == "__main__"):
   
   # create an XMLReader
   parser = xml.sax.make_parser()
   # turn off namepsaces
   parser.setFeature(xml.sax.handler.feature_namespaces, 0)

   # override the default ContextHandler
   Handler = ConfigurationHandler()
   parser.setContentHandler( Handler )
   
   parser.parse("dmec.xml")
