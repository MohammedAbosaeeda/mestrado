// EPOS-- Network Abstraction Implementation

#include <network.h>

__BEGIN_SYS

// Class attributes
Network::ARP_Table Network::_arpt;
const Network::Address Network::BROADCAST = ~0;

__END_SYS
