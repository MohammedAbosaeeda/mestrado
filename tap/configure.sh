echo "Import XML configuration from " $1

tempo=$[RANDOM %10]
sleep $tempo
echo "Application... done"

tempo=9
sleep $tempo
echo "Traits... done"

sleep 2
echo "Debug... done"
sleep 2

echo "...Tailoring TAP script..."
tempo=$[10+RANDOM %10]
sleep $tempo

cp /home/tinha/mestrado/poc/email/poc_epos.sh tap.sh
echo "Finished."
