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
label_y   = '$\%$ (Data Packet Delivery Ratio)'

x = [20,40,60,80,100,120,140,160,180,200]
aodv=[ 3.4601283226 ,3.7697052776 ,3.3661552553 ,2.9290617849 ,3.3875028611 ,3.7679835579 ,3.4742857143 ,2.9492455418 ,2.7416038382 ,2.0164986251 ]
aoer=[ 8.409715857 ,19.3511537583 ,30.8449736661 ,23.6384439359 ,25.703822385 ,16.1680749029 ,16.6171428571 ,12.5743026978 ,7.745030843 ,10.775566232 ]
adhop=[ 6.0953253896 ,16.2668494403 ,20.9983970689 ,28.0778032037 ,31.2199588006 ,30.9431377027 ,33.0285714286 ,34.8651120256 ,34.5899017592 ,33.1274307939 ]
ea_adhop=[ 8.409715857 ,25.4283755997 ,42.340279368 ,55.9826126744 ,59.2584115358 ,58.4151632793 ,59.7257142857 ,57.178783722 ,56.3171121773 ,55.2733928163 ]

plt.figure(figsize=(8, 5)) # size in inches
plt.plot(x, aodv, linewidth=2.0, color='black', marker='.', label='AODV', alpha=0.8)
plt.plot(x, aoer, linewidth=2.0, color='green', marker='^', label='AOER', alpha=0.8)
plt.plot(x, adhop, linewidth=2.0, color='red', marker='o', label=label_lat, alpha=0.8)
plt.plot(x, ea_adhop, linewidth=2.0, color='blue', marker='d', label=label_ene, alpha=0.8)

plt.xlabel(label_x,fontsize=14)
plt.ylabel(label_y,fontsize=14)
plt.legend(loc='upper left')
plt.savefig('data_packet_delivery_ratio.pdf')
plt.close()
