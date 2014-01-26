NIC nic0(0);
NIC nic1(1);

//thead 0
while(1){
   // ...
   nic0.send(BROADCAST, PROTOCOL, "A", 1);
}

//tread 1
while(1){
   // ...
   nic1.send(BROADCAST, PROTOCOL, "A", 1);
}
