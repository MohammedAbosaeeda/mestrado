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
label_y   = '$\%$ (Routing Overhead)'

x = [20,40,60,80,100,120,140,160,180,200]
aodv=[ 81.5919348716 ,91.4270604826 ,95.2873253116 ,96.9250687814 ,97.8702557253 ,98.2217042238 ,98.5177078851 ,98.7085497315 ,98.875272702 ,98.9670541397 ]
aoer=[ 7.4443266172 ,16.0690316395 ,23.5603010677 ,19.0590850157 ,20.3463992707 ,13.9178297621 ,14.1651952129 ,11.1697806661 ,7.1489181162 ,9.6714197148 ]
adhop=[ 3.8130923518 ,11.2170385396 ,14.1368462446 ,19.9926766752 ,21.5619389587 ,22.1926083866 ,23.2456140351 ,24.7289623129 ,24.5864920744 ,23.5170603675 ]
ea_adhop=[ 7.4443266172 ,20.069393718 ,29.7005795235 ,35.8338226659 ,37.1547756041 ,36.8018473084 ,37.2759856631 ,36.2204724409 ,35.9150805271 ,35.473870682 ]

plt.figure(figsize=(8, 5)) # size in inches
plt.plot(x, aodv, linewidth=2.0, color='black', marker='.', label='AODV', alpha=0.8)
plt.plot(x, aoer, linewidth=2.0, color='green', marker='^', label='AOER', alpha=0.8)
plt.plot(x, adhop, linewidth=2.0, color='red', marker='o', label=label_lat, alpha=0.8)
plt.plot(x, ea_adhop, linewidth=2.0, color='blue', marker='d', label=label_ene, alpha=0.8)

plt.xlabel(label_x,fontsize=14)
plt.ylabel(label_y,fontsize=14)
plt.legend(loc='best')
plt.savefig('routing_overhead.pdf')
plt.close()
