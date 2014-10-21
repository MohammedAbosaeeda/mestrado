#include <tcp.h>
#include <machine.h> // panic()

__BEGIN_SYS 


/**
	Este método só é executado pelos tratadores da máquina
	de estados ao receberem um segmento.
	
	Juntamente com o wait() formam a base da sincronização
	entre os métodos disponíveis para a aplicação e os 
	métodos responsáveis por tratar os segmentos que chegam.
*/
void TCP::Socket::signal() {
	db<TCP>(TRC) << "TCP::Socket::signal()\n";
	if (!signaled) {
		signaled=true;
		//timeout=false;
		_sync.v();
	}
}

/**
	O wait() é executado para esperar um sinal oriundo
	de um dos tratadores da máquina de estados.

	Em muitos casos o signal() da máquina de estados
	é executado antes do wait() devido a uma interrupção
	ter chego na placa de rede logo após o envio de um
	pacote sem que a execução tenha retornado para o
	método que envio o pacote. Este fenomeno é ruim no
	sentido que o código de sincronização fica um pouco 
	mais complicado, porém é bom para o desempenho já 
	que evita um chaveamento de contexto no _sync.p().

	A escolha do tempo 2*rtt para o timeout de espera
	foi totalmente arbitrária, existem algoritmos
	estatísticamente melhores para se fazer isso.
*/
void TCP::Socket::wait() {
	db<TCP>(TRC) << "TCP::Socket::wait() duration: "<<(2*rtt)<<endl;
	if (signaled) {
		db<TCP>(TRC) << "Already signaled!"<<endl;
		signaled=false;
		return;
	}
	char al[sizeof(Alarm)];
	Handler_Semaphore h = Handler_Semaphore(&_sync);
	Alarm * alarm = new(al) Alarm(2 * rtt,&h);
	_sync.p();
	alarm->~Alarm();
	if (!signaled) {
		_error = ERR_TIMEOUT;
		db<TCP>(INF) << "TCP: Timeout at "<<Thread::self()<<endl;
	}
	signaled=false;
}

/**
	Este método garante que todos os métodos disponíveis 
	para a aplicação sejam mutuamente exclusivos no mesmo socket.
	Além disto as variáveis a "casa é limpa" dentro do enter()
	e dentro do leave() abaixo.

	Podemos dizer que o par enter()/leave() dá uma pequena
	noção de monitor de Hoare para a classe Socket.

	O contador iniciado aqui será usado em leave() para 
	estimativa do RTT.
*/
void TCP::Socket::enter() {
	_lock.lock();
	signaled=false;
	buffer = 0;
	buffer_size = 0;
	complete = 0;
	_error = 0;
	_sync = Semaphore(0);
	_counter.reset();
	_counter.start();
}

/**
	O calculo do RTT (round-trip time) nesse ponto
	é baseado no tempo total que o método levou
	para ser executado. Esta técnica não é precisa,
	porém simplificou bastante o código.

	O rtt de 5s em caso de erro e o rtt mínimo
	também são decisões arbitrárias.
*/
void TCP::Socket::leave() {
	if (!_error) rtt = _counter.read();
	else rtt = 5000000; // 5s
	if (rtt < 500000) rtt = 500000; // limite inferior para o RTT
	buffer = 0;
	buffer_size = 0;
	complete = 0;
	_lock.unlock();
}

/**
	Envia um pacote contendo cabeçalho IP, TCP e payload TCP para
	o endereço físico em _dst_mac.

	A utilização de um PDU compartilhado entre as várias camadas
	do protocolo ajuda a economizar memcpy's.

	Parte do cabeçalho do TCP e todo o cabeçalho IP são montados
	aqui. Além do calculo do checksum.
*/
void TCP::Socket::sendPDU(PDU& pdu,u16 data_len)
{
	IP::Header* ip = new(&pdu.getIP()) IP::Header(_local,_remote,ID_TCP,
					sizeof(TCP::Header) + data_len);
	Header& s = pdu.getTCP();
	s.src_port(_local.port());
	s.dst_port(_remote.port());
	s._hdr_off = 5;
	s.wnd(rcv_wnd);	
	
	s.checksum(_local,_remote,data_len);
	
	bool ok = s.validate_checksum(_local,_remote,data_len);
	if (!ok) {
		db<TCP>(ERR) << "TCP checksum failed for outgoing packet!\n";
		Machine::panic();
	}

	db<TCP>(TRC) << /**"IPhdr:"<< *ip <<*/"TCP: Sending segment to:"<<_remote<<" \n" << s;

	manager->_nic->send(_dst_mac, IP::ID, &pdu, ip->length());
}

/**
	Este método é usado apenas por TCP::listen();

	Apenas uma conexão é tratada por vez.
	A escolha de PI como semente do RNG é puramente
	arbitrária.

	Em caso de erro o método retornará false
	e caberá ao TCPP::listen() destruir o objeto e
	retornar NULL para a aplicação.
*/
bool TCP::Socket::listen_helper()
{
	enter();
	db<TCP>(INF) << "TCP listening on "<<_local<<"\n";

	CHANGE_STATE(LISTEN);
	rcv_wnd = 0;

	wait();
	if (in_state(SYN_RCVD)) { 
		mac_update();

		snd_ini = Pseudo_Random::random(314159) & 0x0000FFFF;
		snd_nxt = snd_ini+1;
		snd_una = snd_ini;
		
		int retry = 3;
		//int wait_time = 0;
		do {
			//if () Alarm::delay(wait_time);

			PDU& pdu = PDU::grab();
			Header& s = pdu.getTCP(); 
			s.init(snd_ini,rcv_nxt);
			s._syn = true;
			s._ack = true;
			sendPDU(pdu,0);
			pdu.release();
			wait();
			//wait_time += 500000;
		} while (in_state(SYN_RCVD) && retry--);
	}
	if (in_state(ESTABLISHED)) {
		db<TCP>(INF) << "TCP connection established on "
			<< _local <<" with "<<_remote<<"\n";
		leave();
		return true;	
	} else {
		db<TCP>(ERR) << "TCP::listen() error: "<<_error<<endl;
	}
	leave();
	return false;
}

/**
	Análogo ao listen_helper(), é usado apenas por
	TCP::connect()

	A janela inicial >0 é para enganar alguns hosts
	mais conservadores que não iniciam uma conexão
	com quem (a principio) não está disposto a receber
	dados.
*/

bool TCP::Socket::connect_helper()
{
	enter();
	mac_update();

	CHANGE_STATE(SYN_SENT);
	snd_ini = Pseudo_Random::random(314159) & 0x00FFFFFF;
	snd_una = snd_ini;
	snd_nxt = snd_ini + 1;
	//rcv_wnd = 512;

	PDU& pdu = PDU::grab();
	Header& s = pdu.getTCP();
	s.init(snd_ini, 0);    
	s._syn = true;
	sendPDU(pdu,0);
	pdu.release();

	wait();
	
	if (in_state(ESTABLISHED)) {
		send_ack();
		leave();
		return true;
	}
	db<TCP>(ERR) << "TCP::connect() error: "<<_error<<endl;
	leave();
	return false;
}

/**
	Recebimento de dados em uma conexão.

	Os dois random() são apenas para alimentar a semente
	do gerador de números aleatórios.

	
*/
int TCP::Socket::read(char *data,unsigned int size)
{
	enter();

	Pseudo_Random::random(size);
	Pseudo_Random::random((u32)data);

	// checking
	if ( in_state(CLOSE_WAIT) ) {
		leave();
		return ERR_CLOSING;
	}
	if ( !in_state(ESTABLISHED) ) {
		leave();
		return ERR_NOT_CONNECTED;
	}
	 

	set(data,size);
	int sent=0;
	rcv_wnd = size;
	_transfer = RCVING;
	send_ack();
	wait();
	_transfer = IDLE;

	sent += complete;

	if ( _error ) {
		leave();
		return _error;
	}

	// Reply
	rcv_wnd = 0; // change
	send_ack();
	
	// Clean up
	leave();
	return sent;
}

int TCP::Socket::send(const char *data,unsigned int size)
{
	enter();

	Pseudo_Random::random(size);

	if (in_state(CLOSED)) {
		leave();
		return ERR_NOT_CONNECTED;
	}

	/**
		Remote peer has no buffer space
	*/
	if (snd_wnd == 0) {
		leave();
		return 0;
	}

	set(const_cast<char*>(data),size);	

	unsigned int data_size = MIN(snd_wnd,size);
	data_size = MIN(data_size,512);

	PDU& pdu = PDU::grab();
	Header& s = pdu.getTCP();
	s.init(snd_nxt,rcv_nxt);
	s._ack = true;
	s._psh = true;
	memcpy(pdu.getData(),buffer,data_size);
	snd_nxt += data_size;
	snd_una = snd_nxt;

	_transfer = SNDING;
	sendPDU(pdu,data_size);
	pdu.release();
	wait();
	if (!_error) complete += data_size;
	_transfer = IDLE;
	
	db<TCP>(TRC) << "TCP::Socket::send() Sent "<<complete<<" bytes of "<<size<<endl;
	int ret = !_error ? complete : _error;

	leave();
	return ret;
}

void TCP::Socket::close()
{
	enter(); 
	db<TCP>(TRC) << "TCP::Socket::close()\n";
	if (in_state(ESTABLISHED)) {
		db<TCP>(TRC) << "state(-> FIN_WAIT1)\n";
		CHANGE_STATE(FIN_WAIT1);
		
		send_fin();

		wait();
		if (in_state(FIN_WAIT2)) {
			db<TCP>(TRC) << "state(FIN_WAIT2)\n";
			wait();		
		}
		if (in_state(CLOSING)) {
			db<TCP>(TRC) << "state(CLOSING)\n";
			send_ack();
		}
	}	
	else if (in_state(CLOSE_WAIT)) {
		db<TCP>(TRC) << "state(CLOSE_WAIT -> LAST_ACK)\n";
		CHANGE_STATE(LAST_ACK);
		rcv_nxt++;
		send_fin();
		wait();
	}
	
	manager->_big_lock.lock();
	manager->_busy.remove(&_link);
	manager->_big_lock.unlock();

	CHANGE_STATE(CLOSED);
	leave();
	//delete this;
}


// creation and destruction

TCP::Socket::~Socket()
{
	if (!in_state(CLOSED)) close();
}

TCP::Socket::Socket(const Address &remote,const Address &local) 
: _remote(remote), _local(local), _error(0), _lock(Mutex()), rtt(5000000), _sync(Semaphore(0)),
 signaled(false), _link(this)
{
	manager->_big_lock.lock();
	manager->_busy.insert(&_link);
	manager->_big_lock.unlock();

	Pseudo_Random::random(remote); 
	Pseudo_Random::random(local);

	CHANGE_STATE(CLOSED);
}

// misc

void TCP::Socket::mac_update()
{

	u32 mask = CPU::htonl(Traits<IP>::NETMASK);
	if ((_remote & mask) == (_local & mask)) {
		db<TCP>(TRC) << "Remote machine is on the same network\n";
		_dst_mac = manager->_arp->arp(_remote);
	}
	else {
		db<TCP>(TRC) << "Remote machine is on another network\n";
		_dst_mac = manager->_arp->arp(IP::Address(CPU::htonl(Traits<IP>::GATEWAY)));
	}
	if(_dst_mac == ARP::NULL_ADDR) {
		_error = ERR_NO_ROUTE;
	}


	// TCP com broadcast
	//_dst_mac = NIC::Address(CPU::htonl(Traits<IP>::BROADCAST));
}
void TCP::Socket::send_ack() {
	PDU& pdu = PDU::grab(); 
	Header& s = pdu.getTCP(); 
	s.init(snd_nxt,rcv_nxt);
	s._ack = true;
	sendPDU(pdu,0);
	pdu.release();
}

void TCP::Socket::send_fin() {
	PDU& pdu = PDU::grab();
	Header& s = pdu.getTCP();
	s.init(snd_nxt,rcv_nxt);
	s._fin = true;
	s._ack = true;
	sendPDU(pdu,0);	
	pdu.release();
}

bool TCP::Socket::check_seq(const Header &h,u16 len) {
	if ((len <= rcv_wnd) && 
		(h.seq_num() == rcv_nxt)) {
		db<TCP>(TRC) << "TCP: check_seq() == true\n";
		return true;
	}
	db<TCP>(TRC) << "TCP: check_seq() == false\n";
	return false;
}

__END_SYS
