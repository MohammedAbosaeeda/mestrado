import sys
import numpy
import matplotlib.pyplot as plt
from matplotlib import rc
rc('font', family='monospace')
plt.rcParams['pdf.use14corefonts'] = True

label_dmec_ia32           = 'DMEC@PC - 1080p'
label_dmec_cell           = 'DMEC@CELL - 1080p'
label_dmec_hw             = 'DMEC@HW - 1080p'

label_x                   = 'Number of Workers'
label_y                   = 'Speedup (X)'

worker_dmec          = [1,2           ,3           ,4           ,5           ,6] 
speedup_dmec_ia32    = [1,3.2619155065,5.5304033314,6.1512773508,7.8852022887,7.9407723906]
speedup_dmec_cell    = [1,4.0352782263,9.3654865359,11.4464942544,19.5366483065,19.7439960143]

speedup_dmec_hw      = [1,3.2835,5.49,6.07,7.75,7.78]

plt.figure(figsize=(8, 5)) # size in inches
plt.plot(worker_dmec, speedup_dmec_ia32, linewidth=2.0, color='blue', marker='d', label=label_dmec_ia32, alpha=0.8)
plt.plot(worker_dmec, speedup_dmec_cell, linewidth=2.0, color='red', marker='o', label=label_dmec_cell, alpha=0.8)
plt.plot(worker_dmec, speedup_dmec_hw, linewidth=2.0, color='brown', marker='.', label=label_dmec_hw, alpha=0.8)

plt.xlabel(label_x,fontsize=14)
plt.ylabel(label_y,fontsize=14)
# plt.yscale('log')
plt.legend(loc='best')
plt.savefig('dmec-speedup_workers.pdf')
plt.close()

