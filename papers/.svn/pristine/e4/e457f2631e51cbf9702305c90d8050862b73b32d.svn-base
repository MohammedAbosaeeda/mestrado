import sys
import numpy
import matplotlib.pyplot as plt
from matplotlib import rc
rc('font', family='sans-serif')

label_ct_ia32       = 'City 4CIF - Original JM / JM+DMEC - PC'
label_ct_cell       = 'City 4CIF - Original JM / JM+DMEC - CELL'

label_x             = 'Bit rate (kbit/s)'
label_y             = 'Speedup (X)'

br_ct_ia32          = [30088.48,22781.83,16342.92,11557.35] 
speedup_ct_ia32     = [13.9301621302,13.9369622216,13.9326037931,13.9327712447]
br_ct_cell          = [30088.48,22781.83,16342.92,11557.35]
speedup_ct_cell     = [14.2483439304,14.1225871545,14.2089679412,14.5220040802]

plt.figure(figsize=(8, 5)) # size in inches
plt.plot(br_ct_ia32, speedup_ct_ia32, linewidth=2.0, color='blue',  marker='d', label=label_ct_ia32, alpha=0.8)
plt.plot(br_ct_cell, speedup_ct_cell, linewidth=2.0, color='red', marker='v', label=label_ct_cell, alpha=0.8, linestyle='--')

plt.xlabel(label_x,fontsize=14)
plt.ylabel(label_y,fontsize=14)
# plt.yscale('log')
plt.legend(loc='best')
plt.savefig('city-bitrate_speedup.pdf')
plt.close()

