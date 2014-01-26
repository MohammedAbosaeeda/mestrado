import sys
import numpy
import matplotlib.pyplot as plt
from matplotlib import rc
rc('font', family='sans-serif')
plt.rcParams['ps.useafm'] = True
plt.rcParams['pdf.use14corefonts'] = True
plt.rcParams['text.usetex'] = True

label_lat = 'ADHOP'
label_ene = 'EA-ADHOP'
label_x   = 'Number of Nodes'
label_y   = 'Consumed Energy per Delivered Data Packet (mWh)'

x = [20,40,60,80,100,120,140,160,180,200]
aodv=[ 372.816137131 ,1649.837964443 ,4101.8180670435 ,8314.8086461044 ,11193.6597232839 ,13505.8852788162 ,19027.8705659511 ,27168.1060496726 ,35391.7830112076 ,56671.970585548 ]
aoer=[ 81.0091026777 ,202.0585621379 ,324.0174290199 ,620.4304510823 ,747.9525235944 ,1449.5089872971 ,1678.9973803129 ,2576.2653585694 ,4688.2223458017 ,3740.4180769079 ]
adhop=[ 138.0397227038 ,386.0966552308 ,592.7695541428 ,909.9338039987 ,1299.9307738755 ,1856.0177042039 ,2255.9814954285 ,2735.1257387773 ,3254.6838549245 ,4106.1266567817 ]
ea_adhop=[ 81.0091026777 ,197.1459251026 ,321.9608846888 ,514.9103978951 ,758.9563948248 ,1033.8178583987 ,1348.2302120605 ,1787.5521255043 ,2078.7847033598 ,2538.7900958749 ]

plt.figure(figsize=(8, 5)) # size in inches
plt.plot(x, aodv, linewidth=2.0, color='black', marker='.', label='AODV', alpha=0.8)
plt.plot(x, aoer, linewidth=2.0, color='green', marker='^', label='AOER', alpha=0.8)
plt.plot(x, adhop, linewidth=2.0, color='red', marker='o', label=label_lat, alpha=0.8)
plt.plot(x, ea_adhop, linewidth=2.0, color='blue', marker='d', label=label_ene, alpha=0.8)

plt.xlabel(label_x,fontsize=14)
plt.ylabel(label_y,fontsize=14)
plt.yscale('log')
plt.legend(loc='best')
plt.savefig('relation.pdf')
plt.close()

