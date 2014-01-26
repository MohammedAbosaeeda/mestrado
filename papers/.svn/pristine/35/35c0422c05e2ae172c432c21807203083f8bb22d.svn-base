import sys
import numpy
import matplotlib.pyplot as plt
from matplotlib import rc
rc('font', family='monospace')
plt.rcParams['pdf.use14corefonts'] = True

label_cr_ia32       = 'Original JM / JM+DMEC@PC'
label_cr_cell       = 'Original JM / JM+DMEC@CELL'
label_cr_hw         = 'Original JM / JM+DMEC@HW'

label_x             = 'Bit rate (kbit/s)'
label_y             = 'Speedup (X)'

br_cr_ia32          = [181874.11,139769.78,99082.86,67998.22]
speedup_cr_ia32     = [9.0377141901,8.9978193999,9.003716282,9.020231281]
br_cr_cell          = [172006.56,132707.9,95134.2,65309.62]
speedup_cr_cell     = [2.1546960189,2.1309140799,1.9944053127,2.127670219]

br_cr_hw            = [181874.11,139769.78,99082.86,67998.22]
speedup_cr_hw       = [8.8494,8.9078,8.8294,8.8461]

plt.figure(figsize=(8, 5)) # size in inches
plt.plot(br_cr_ia32, speedup_cr_ia32, linewidth=2.0, color='blue',   marker='d', label=label_cr_ia32, alpha=0.8)
plt.plot(br_cr_cell, speedup_cr_cell, linewidth=2.0, color='red', marker='v', label=label_cr_cell, alpha=0.8, linestyle='--')
plt.plot(br_cr_hw, speedup_cr_hw, linewidth=2.0, color='brown', marker='.', label=label_cr_hw, alpha=0.8)


plt.xlabel(label_x,fontsize=14)
plt.ylabel(label_y,fontsize=14)
# plt.yscale('log')
plt.legend(loc='best')
plt.savefig('crowd-bitrate_speedup.pdf')
plt.close()

