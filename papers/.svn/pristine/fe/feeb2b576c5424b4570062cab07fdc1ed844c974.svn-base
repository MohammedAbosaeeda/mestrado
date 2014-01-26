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
label_y   = '$\%$ (Link Failures)'

x = [20,40,60,80,100,120,140,160,180,200]
aodv=[ 0.2711765563 ,1.7379135469 ,3.7096762208 ,6.7146598293 ,9.6933983126 ,13.2754592699 ,17.3364002149 ,19.9208934663 ,24.0878127557 ,26.8611592095 ]
aoer=[ 0.2816240319 ,2.406929504 ,3.94381031 ,5.2349056493 ,5.15186981 ,7.2124798511 ,5.844689231 ,6.7101926181 ,8.6687056496 ,9.2259036145 ]
adhop=[ 0.4632615962 ,3.2879647513 ,4.4762970337 ,6.9853610123 ,8.8908940037 ,11.3636211636 ,13.4357774598 ,15.714670242 ,18.2368867881 ,18.949329802 ]
ea_adhop=[ 0.2816240319 ,3.5151411609 ,5.8933212247 ,9.8300950316 ,13.2623866162 ,17.9895641052 ,22.4636929298 ,24.4841841613 ,28.7564679313 ,31.0748711446 ]

plt.figure(figsize=(8, 5)) # size in inches
plt.plot(x, aodv, linewidth=2.0, color='black', marker='.', label='AODV', alpha=0.8)
plt.plot(x, aoer, linewidth=2.0, color='green', marker='^', label='AOER', alpha=0.8)
plt.plot(x, adhop, linewidth=2.0, color='red', marker='o', label=label_lat, alpha=0.8)
plt.plot(x, ea_adhop, linewidth=2.0, color='blue', marker='d', label=label_ene, alpha=0.8)

plt.xlabel(label_x,fontsize=14)
plt.ylabel(label_y,fontsize=14)
plt.legend(loc='upper left')
plt.savefig('link_failures.pdf')
plt.close()
