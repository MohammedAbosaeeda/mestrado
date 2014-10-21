#! /usr/bin/python
# Author: Mateus Krepsky Ludwich

import sys

def fileExists(pathToFile):
    exists = False
    
    try:
        f = open(pathToFile, 'r')
        exists = True        
    except IOError:
        exists = False
        
    return exists
    

def __replaceVmultipleStrings(inputFile, outputFile):
    for line in inputFile:                        
        newLine = line.replace('"', '\\"')
        newLine = newLine.replace('\n', '\"\n') 
        newLine = '"' + newLine        
        
        outputFile.write(newLine)    

    
def __replaceVsingleLine(inputFile, outputFile):
    outputFile.write('"')
    
    for line in inputFile:                        
        newLine = line.replace('"', '\\"')
        newLine = newLine.replace('\n', ' ')
        # ignore lua comments
        if '--' in newLine:
            continue
        
        outputFile.write(newLine)
        
    outputFile.write('"')


def lua2header(pathToLuaFile, pathToHeaderFile):
    assert(fileExists(pathToLuaFile))
    inputFile = open(pathToLuaFile, 'r')
    outputFile = open(pathToHeaderFile, 'w')
    
    __replaceVsingleLine(inputFile, outputFile)
    # __replaceVmultipleStrings(inputFile, outputFile)
    
    inputFile.close()
    outputFile.close()


def main(args):
    if len(args) < 2:
        print 'lua2header LUA_FILE.lua'
        
    else:
        pathToLuaFile = args[1]
        assert('.lua' in pathToLuaFile)
        pathToHeaderFile = pathToLuaFile.replace('.lua', '_lua.h')
        
        if fileExists(pathToHeaderFile):
            overwrite = raw_input('file: ' + pathToHeaderFile + ' already exists. Overwrite? <y/N>')            
            if overwrite == 'y':
                lua2header(pathToLuaFile, pathToHeaderFile)
        else:
                lua2header(pathToLuaFile, pathToHeaderFile)
 
    
if __name__ == '__main__':
    main(sys.argv)

