#include <tcp.h>

__BEGIN_SYS
TCP* TCP::manager = 0;
TCP::PDU* TCP::PDU::instance = 0;
Mutex* TCP::PDU::mutex = 0;

void TCP::init()
{
	if (!manager) {
		manager = new TCP();
		manager->_arp->arp(IP::Address(CPU::htonl(Traits<IP>::GATEWAY)));
		db<TCP>(TRC) << "TCP subsystem started\n";
	}
}

TCP::TCP() : _big_lock(Mutex())
{
	_nic = new NIC;
	_ip  = new IP;
	_arp = new ARP(*_nic, _ip->self());

	IP::attach(ID_TCP,*this);
}

TCP::Socket* TCP::connect(const Address &remote)
{
	TCP::init();

	u16 escolhida = manager->get_free_port();

	if (!escolhida) return 0;

	Pseudo_Random::random(remote.port());

	Address local = Address(escolhida);
	Socket * con = new Socket(remote,local);
	if ( con->connect_helper() )
		return con;

	delete con;
	return 0;
}

//
TCP::Socket* TCP::listen(const Address &local)
{
	TCP::init();
	Address remote = Address(0);

	//verify if local.port() is available
	if(manager->busy(local.port())) {
		db<TCP>(ERR) << "Address "<<local<<" already in use. Take care!\n";
		//return 0;
	}

	Socket * con = new Socket(remote,local);
	if (con->listen_helper())
		return con;

	delete con;
	return 0;
}


bool TCP::busy(int porta){
	_big_lock.lock();
	Simple_List<Socket>::Element * e = _busy.head();
	for(;e;e=e->next()){
		if(e->object()->_local.port() == porta)	{
			_big_lock.unlock();
			return true;
		}
	}
	_big_lock.unlock();
	return false;
}

TCP::~TCP()
{
	//IP::dettach(*this);
}


TCP::u16 TCP::get_free_port() {
	u16 psobe,porta = (Pseudo_Random::random(0) % 65535);
	if (porta < 5000) porta += 5000;
	for(psobe = porta+1;psobe < 65535;psobe++)
		if (!manager->busy(psobe))
			return psobe;

	for(psobe = porta+1;psobe > 5000;psobe--)
		if (!manager->busy(psobe))
			return psobe;
	return 0;
}

// --- PDU stuff

TCP::PDU& TCP::PDU::grab()
{
	if (!instance) {
		instance = new PDU();
		mutex = new Mutex();
	}
	mutex->lock();
	return *instance;
}
void TCP::PDU::release()
{
	mutex->unlock();
}


__END_SYS
