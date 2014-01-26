import sys
import numpy
import matplotlib.pyplot as plt
from matplotlib import rc
rc('font', family='monospace')
plt.rcParams['pdf.use14corefonts'] = True

label_cr_ia32       = 'JM+DMEC@PC'
label_cr_cell       = 'JM+DMEC@CELL'
label_cr_ia32_jm    = 'Original JM@PC'
label_cr_cell_jm    = 'Original JM@CELL'
label_cr_hw         = 'JM+DMEC@HW'

label_x             = 'Bit rate (kbit/s)'
label_y             = 'PSNR (db)'

br_cr_ia32          = [181874.11,139769.78,99082.86,67998.22]
psnr_cr_ia32        = [37.43,38.45,38.08,36.19]
br_cr_cell          = [181874.11,139769.78,99082.86,67998.22]
psnr_cr_cell        = [37.43,38.45,38.08,36.19]
br_cr_ia32_jm       = [179775.28,138232.11,100297.66,70649.06]
psnr_cr_ia32_jm     = [37.12,37.98,37.82,36.15]
br_cr_cell_jm       = [136865.28,103779.77,72538.73,48231.1]
psnr_cr_cell_jm     = [36.24,37.06,37.14,35.6]

br_cr_hw          = [181874.11,139769.78,99082.86,67998.22]
psnr_cr_hw         = [37.43,38.45,38.08,36.19]

plt.figure(figsize=(8, 5)) # size in inches
plt.plot(br_cr_ia32, psnr_cr_ia32, linewidth=2.0, color='blue',   marker='d', label=label_cr_ia32, alpha=0.8)
plt.plot(br_cr_cell, psnr_cr_cell, linewidth=2.0, color='red', marker='v', label=label_cr_cell, alpha=0.8, linestyle='--')
plt.plot(br_cr_ia32_jm, psnr_cr_ia32_jm, linewidth=2.0, color='green',   marker='o', label=label_cr_ia32_jm, alpha=0.8)
plt.plot(br_cr_cell_jm, psnr_cr_cell_jm, linewidth=2.0, color='purple',  marker='^', label=label_cr_cell_jm, alpha=0.8)
plt.plot(br_cr_hw, psnr_cr_hw, linewidth=2.0, color='brown', marker='.', label=label_cr_hw, alpha=0.8)

plt.xlabel(label_x,fontsize=14)
plt.ylabel(label_y,fontsize=14)
# plt.axis(ymax=40) # Define o valor maximo do eixo Y
# plt.yscale('log')
plt.legend(loc='best')
plt.savefig('crowd-bitrate_psnr.pdf')
plt.close()

