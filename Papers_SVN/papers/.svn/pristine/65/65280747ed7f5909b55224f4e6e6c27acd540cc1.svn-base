import sys
import numpy
import matplotlib.pyplot as plt
from matplotlib import rc
rc('font', family='sans-serif')

label_ct_ia32       = 'City 4CIF - JM+DMEC - PC'
label_ct_cell       = 'City 4CIF - JM+DMEC - CELL'
label_ct_ia32_jm    = 'City 4CIF - Original JM - PC'
label_ct_cell_jm    = 'City 4CIF - Original JM - CELL'

label_x             = 'Bit rate (kbit/s)'
label_y             = 'PSNR (db)'

br_ct_ia32          = [30088.48,22781.83,16342.92,11557.35]
psnr_ct_ia32        = [40.86,41.06,39.32,36.79]
br_ct_cell          = [30088.48,22781.83,16342.92,11557.35]
psnr_ct_cell        = [40.86,41.06,39.32,36.79]
br_ct_ia32_jm       = [25186.47,19603.9,14553.02,10984.95]
psnr_ct_ia32_jm     = [38.15,38.74,38.02,36.42]
br_ct_cell_jm       = [13477.45,11841.56,9656.11,6577.18]
psnr_ct_cell_jm     = [35.3,36.37,37.09,35.82]


plt.figure(figsize=(8, 5)) # size in inches
plt.plot(br_ct_ia32, psnr_ct_ia32, linewidth=2.0, color='blue',  marker='d', label=label_ct_ia32, alpha=0.8)
plt.plot(br_ct_cell, psnr_ct_cell, linewidth=2.0, color='red', marker='v', label=label_ct_cell, alpha=0.8, linestyle='--')
plt.plot(br_ct_ia32_jm, psnr_ct_ia32_jm, linewidth=2.0, color='green',  marker='o', label=label_ct_ia32_jm, alpha=0.8)
plt.plot(br_ct_cell_jm, psnr_ct_cell_jm, linewidth=2.0, color='purple',  marker='^', label=label_ct_cell_jm, alpha=0.8)

plt.xlabel(label_x,fontsize=14)
plt.ylabel(label_y,fontsize=14)
# plt.yscale('log')
plt.legend(loc='best')
plt.savefig('city-bitrate_psnr.pdf')
plt.close()

