'''
Created on Apr 28, 2011

@author: arliones
'''
#!usr/bin/env python
#This has the myGraph class, which allows graphing on the fly.
#See the main() function at the bottom of this file for examples on usage
 
 
import Gnuplot
from numpy import inf
 
class MyGraph(object):
    """class for graphing experimental data on the fly"""
 
    def __init__(self,xname,yname,title=None,logx=False,logy=False,
    xlabel=None,ylabel=None):
        """xname and yname are the dictionary label items to be plotted.
        Title is the graph title.
        logx and logy are by default set to False.  Changing to True will
        plot semi-log or log-log graph.
        xlabel and ylabel can be set.  If not the xname and yname will
        be used.
        maxDataPoints is the maximum number of data points to be plotted.
        When this number is reached, early points are removed.  This only
        applies for appendplot."""
        self.xname=xname
        self.yname=yname
        if title==None:
            self.title = yname+" vs "+xname
        else:
            self.title=title    
 
        if xlabel:
            self.xlabel=xlabel
        else:
            self.xlabel=xname
        if ylabel:
            self.ylabel=ylabel
        else:
            self.ylabel=yname
        print self.xlabel, self.ylabel
        self.initgraph()
        if logx: self.G('set logscale x')
        if logy: self.G('set logscale y')
        self.x=[]
        self.y=[]
 
    def initgraph(self):
        """initialise gnuplot graph"""
        self.G = Gnuplot.Gnuplot()  #persist=1)  #for linux
        self.G.title(self.title)
        self.G.xlabel(self.xlabel)
        self.G.ylabel(self.ylabel)
        if self.ylabel == "Temperatura":
	    self.G.set_range("yrange", (15,35))
        if self.ylabel == "Bateria":
	    self.G.set_range("yrange", (0,100))
 
 
    def plot(self,datadict):
        """Plots from a dictionary of lists of the whole data set"""
        D=Gnuplot.Data(datadict[self.xname],datadict[self.yname])
        self.G.plot(D)
 
    def listplot(self,xlist,ylist):
        """Plots xlist against ylist"""
        D=Gnuplot.Data(xlist,ylist)
        self.G.plot(D)
 
    def appendplot(self,newdict):
        """Plots from a dictionary of most recent data set"""
        self.x.append(newdict[self.xname])
        self.y.append(newdict[self.yname])
        self.listplot(self.x,self.y)
 
    def appendplot2(self,newdict,maxpoints):
        """Plots from a dictionary of most recent data set.  Deletes
        earliest points when number of points is more than maxpoints"""
        self.x.append(newdict[self.xname])
        self.y.append(newdict[self.yname])
        if len(self.x)>maxpoints:
            self.x.pop(0)
            self.y.pop(0)
        self.listplot(self.x,self.y)
