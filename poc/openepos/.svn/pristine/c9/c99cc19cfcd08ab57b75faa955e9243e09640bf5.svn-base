#include <tcp.h>

#include <cpu.h> // int_disable/enable()

__BEGIN_SYS

// ========= TCP state processing goes here

// Atencao: a maquina de estados roda dentro do tratador de interrupcoes
// isso significa que ela pode rodar dentro de qualquer thread e inclusiva
// da thread idle, logo estes tratadores não podem fazer ->suspend() de jeito nenhum


/***
	Método executado quando a conexão está no estado LISTEN
	e um pacote é destino a porta em que ela está ouvindo.

	Caso a flag SYN não esteja ativa _remote é zerado já que
	ele foi setado para o par IP:PORTA remoto pelo método
	TCP::received()
*/
void TCP::Socket::__LISTEN(const Header& r ,const char* data,u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __LISTEN\n"; 

	if (r._syn && !r._rst && !r._fin) {
		rcv_nxt = r.seq_num()+1;
		rcv_ini = r.seq_num();
		snd_wnd = r.wnd();

		CHANGE_STATE(SYN_RCVD);
		signal();
	} else {
		_remote = Address((u32)0,(u16)0);
	}
}

/**
	Este método é executado apenas em conexões iniciadas
	com TCP::connect() e seu objetivo é tratar o par SYN+ACK
	de resposta ao nosso SYN enviado anteriormente.
*/
void TCP::Socket::__SYN_SENT(const Header& r,const char* data,u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __SYN_SENT\n";

	short _old = _state;
	if (r._rst || r._fin) {
			_error = ERR_REFUSED;
			CHANGE_STATE(CLOSED);
	}	
	else if (r._ack) {
		if ((r.ack_num() <= snd_ini) || (r.ack_num() > snd_nxt)) {
			_error = ERR_RESET;
			CHANGE_STATE(CLOSED);
		} else if ((r.ack_num() >= snd_una) && (r.ack_num() <= snd_nxt)) {
			if (r._syn) {
				rcv_nxt = r.seq_num() + 1;
				rcv_ini = r.seq_num();
				snd_una = r.ack_num();
				snd_wnd = r.wnd();
				if (snd_una > snd_ini) {
					CHANGE_STATE(SYN_RCVD);
				} else {
					CHANGE_STATE(ESTABLISHED);
				}
			} else {
				
			}
		}
	} else if (!r._rst && r._syn) {
		rcv_nxt = r.seq_num() + 1;
		snd_ini = r.seq_num();
		snd_wnd = r.wnd();
		CHANGE_STATE(SYN_RCVD);
	}		

	if (_old != _state) {
		signal();
	}
 		
} 
/**
	Este estado geralmente só é alcançado após o LISTEN,
	já que o handshake de 3-vias é mais comum do que o de 4.

	Ao receber um ACK a conexão é estabelecida.
	Neste ponto a atualização da janela do host remoto (snd_wnd)
	é importante.
*/
void TCP::Socket::__SYN_RCVD(const Header& r ,const char* data,u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __SYN_RCVD\n";
	if (!check_seq(r,len)) return;
	if (r._rst || r._fin) {
		_error = ERR_RESET;
		CHANGE_STATE(CLOSED);
		signal();
		return;
	}
	if (r._ack) {
		snd_wnd = r.wnd();
		CHANGE_STATE(ESTABLISHED);
		signal();
		return;
	}
}

/**
	Este é um sub-estado de ESTABLISHED e indica que um método
	Socket->read() está esperando dados na conexão.
	Fazer a cópia dos dados aqui é um pouco feio porém não
	temos nenhuma garantia que o buffer com os dados estará
	inteiro depois que o tratador de pacotes retornar.
*/
void TCP::Socket::__RCVING(const Header &r,const char* data,u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __ESTABLISHED: RCVING\n";

	if (len <= rcv_wnd) { // ES3 pg 19
		db<TCP>(TRC) << "TCP: Data copy to user buffer: "<< len <<" bytes"<<endl;
		memcpy(buffer,data,len);
		buffer += len;
		rcv_nxt += len;
		complete += len;
		signal();
	} else {
		db<TCP>(TRC) << "TCP: Data bigger than buffer from "<<_remote <<endl;
	}
}

/**
	Este método é um sub-estado do estado ESTABLISHED e é 
	invocado quando a confirmação de um segmento enviado
	é recebida.

	A principal tarefa aqui é ajustar as variáveis de acordo
	com a quantidade efetiva de bytes recebida pelo host
	remoto, já que ela pode variar devido a fragmentações
	no meio do caminho, porém não é comum acontecer de só
	parte dos dados chegarem já que os protocolos inferiores
	cuidam bem (no geral) da entrega. 
*/
void TCP::Socket::__SNDING(const Header &r,const char* data, u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __ESTABLISHED: SENDING\n";
	if (r._ack) {
		signal();
	}
}

/**

*/
void TCP::Socket::__ESTABLISHED(const Header& r ,const char* data,u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __ESTABLISHED\n";
	if (!check_seq(r,len)) return;
	if (r._rst) {
		CHANGE_STATE(CLOSED);
		signal();
	}
	else if (r.seq_num() == rcv_nxt) { // implicit reject out-of-order segments
		snd_wnd = r.wnd();

		if (_transfer == RCVING)
			__RCVING(r,data,len);

		if (_transfer == SNDING)
			__SNDING(r,data,len);

		if (r._fin) CHANGE_STATE(CLOSE_WAIT);
	}
	else {
		db<TCP>(TRC) << "out of order segment received\n";
	}
}

 


void TCP::Socket::__FIN_WAIT1(const Header& r ,const char* data,u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __FIN_WAIT1\n";
	if (!check_seq(r,len)) return;

	if (r._ack && !r._fin) { // TODO: verificar snd_una
		CHANGE_STATE(FIN_WAIT2);
		//rcv_nxt = r.seq_num() + len;
		signal();
	}
	if (r._ack && r._fin) {
		CHANGE_STATE(TIME_WAIT);
		signal();
	}
	if (!r._ack && r._fin) {
		CHANGE_STATE(CLOSING);
		signal();
	}
}
void TCP::Socket::__FIN_WAIT2(const Header& r ,const char* data,u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __FIN_WAIT2\n";
	signal();
}
void TCP::Socket::__CLOSE_WAIT(const Header& r ,const char* data,u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __CLOSE_WAIT\n";
	if (!check_seq(r,len)) return;
	if (_transfer == SNDING)
		__SNDING(r,data,len);
}
void TCP::Socket::__CLOSING(const Header& r ,const char* data,u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __CLOSING\n";
	if (!check_seq(r,len)) return;
	signal();
}
void TCP::Socket::__LAST_ACK(const Header& r ,const char* data,u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __LAST_ACK\n";
	if (!check_seq(r,len)) return;
	if (r._ack) {
		CHANGE_STATE(CLOSED);
		signal();
	}
}
void TCP::Socket::__TIME_WAIT(const Header& r ,const char* data,u16 len)
{
	db<TCP>(TRC) << "TCP state handling: __TIME_WAIT\n";
	if (!check_seq(r,len)) return;
	if (r._fin && r._ack) {
		CHANGE_STATE(CLOSED);
		snd_nxt++;
		signal();
	}
}

/**
	Este método é chamado pelo IP::received quando um pacote TCP
	chega na interface de rede.

	Desabilitamos as interrupções para ele não ser preemptivo,
	ficou mais fácil assim.
*/
void TCP::received(u32 src, void *data, u16 size)
{
	CPU::int_disable();
	Header &r = *reinterpret_cast<Header*>(data);
	Pseudo_Random::random(r.seq_num());
	Pseudo_Random::random(src);
	Pseudo_Random::random(size);

	Socket* socket = 0;

	/**
		Os objetos da classe Socket estão armazenados
		em uma lista encadeada.
		Para um número pequeno de conexões uma varredura
		O(n) é suficiente.
		Para tratar um número grande de conexões, algo como
		100 ou mais, uma árvore balanceada cairia bem aqui.
	*/
	Simple_List<Socket>::Element *e = _busy.head();
	for(;e;e = e->next()) {
		Socket* s = e->object();
		// case: established
		if ((s->_remote == src) && 
		   (s->_remote.port() == r.src_port()) && 
		   (s->_local.port() == r.dst_port())) 
		{
			socket = s; 
			break;
		}
		// case: listening
		if ((s->in_state(Socket::LISTEN)) &&
		   (s->_local.port() == r.dst_port())) 
		{
			socket = s;
			socket->_remote = Address(src,r.src_port());
			db<TCP>(TRC) << "TCP _remote=" << socket->_remote << "\n";
			break;
		} 
	}

	if (socket) {
		int len = static_cast<int>(size) - static_cast<int>(r.size());

		db<TCP>(TRC) << "TCP: Incomming segment from: "<< socket->_remote <<"\n";
		db<TCP>(TRC) << "Header size:"<< r.size() << " Data size:" << len <<" :" << r;

		if (len < 0) {
			db<TCP>(INF) << "Misformed TCP packet received and discarded\n";
			return;
		}
		if (!(r.validate_checksum(socket->_remote,socket->_local,len))) {
			db<TCP>(INF) << "TCP checksum failed for incomming packet!\n" <<
			"_remote: "<< socket->_remote << " _local:"<< socket->_local << "\n";
			return;
		}
	
		/**
			A mágica aqui é a utilização do functor state_handler
			para evitar um switch-case. O ganho de desempenho não
			é lá grandes coisas, mas o tradeoff cpu-memoria é muito
			pequeno (1 instrução a mais em cada CHANGE_STATE)
		*/
		(socket->*(socket->state_handler))(r,&((char*)data)[r.size()],len);
	} else {
		db<TCP>(TRC) << "TCP: Unexpected segment arrived\n" << r;
	}
	CPU::int_enable();
}

__END_SYS
