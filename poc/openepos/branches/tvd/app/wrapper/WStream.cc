#include "../mux2/includes/MuxWrapper.h"
#include "WStream.h"

#include <utility/ostream.h>
#include <mach/pc/nic.h>

#define N_WSTREAM_BUF 1000

__USING_SYS

OStream wstream_o;

PC_NIC receive_nic(0);
PC_NIC send_nic(1);

//PC_NIC::Address receiver(0x00, 0x11, 0xD8, 0x17, 0xCC, 0x33, 0x00, 0x00);
PC_NIC::Address receiver(0x00, 0x02, 0x3f, 0x26, 0x1e, 0x92, 0x00, 0x00);
//PC_NIC::Address receiver(0x00, 0x11, 0xD8, 0x17, 0xCB, 0x79, 0x00, 0x00);

WStreamList * stream_list = 0;

WStream_Buf * ws_buffers = new WStream_Buf[N_WSTREAM_BUF];
unsigned int cur_ws_buffer = 0;
Mutex * ws_buffers_mut = new Mutex();

WFILE * wfopen(const char *path, const char *mode) {

        WStream * ws = new WStream();

        memcpy((void *) &ws->prot, path, sizeof(ws->prot));

        wstream_o << "Opening file " << path << ", protocol " << ws->prot << ".\n";

        if(stream_list) {  // global list
        	stream_list->insert(ws);
        } else {
        	stream_list = new WStreamList(ws);
        }

        return ((WFILE *) ws); // FILE agora é void pointer

}

size_t wfwrite(const  void  *ptr,  size_t  size,  size_t  nmemb,  WFILE *stream) {

        //d->puts("WRAPPER: Writing to file [a.k.a. DOING NOTHING].\n");
        WStream * ws = (WStream *) stream;

        memcpy(ws->send_buffer_pos, ptr, size * nmemb);
        ws->send_buffer_pos += size * nmemb;

        if (ws->send_buffer_pos == ws->send_buffer + (188 * 7)) { 

                send_nic.send(receiver, ws->prot, ws->send_buffer, 188 * 7);
                ws->send_buffer_pos = ws->send_buffer;

        }

        ws->n++;

        return(size * nmemb); // do nothing

}

size_t wfread(void * ptr, size_t size, size_t nmemb, WFILE *stream) {

        //o << "WRAPPER: Reading from file.\n";

        WStream * ws = (WStream *) stream;

	//wstream_o << "READING: vb bef fill: " << ws->valid_bytes();

	ws->fill(size * nmemb);

	//wstream_o << " after fill: " << ws->valid_bytes(); 

        ws->mutex->lock();
        ws->read((char *)ptr, size * nmemb);
        ws->mutex->unlock();

	//wstream_o << " after read " << ws->valid_bytes();

        return(size * nmemb);

}

size_t wfread_searching(void * ptr, size_t size, size_t nmemb, WFILE *stream, char needle) {

        //wstream_o << "WRAPPER: Reading from file.\n";

        WStream * ws = (WStream *) stream;

        //wstream_o << "READ: vb bf fill: " << ws->valid_bytes();

	ws->seek_chr(needle);

        //wstream_o << " aft seek: " << ws->valid_bytes();

        ws->fill(size * nmemb);

        //wstream_o << " aft fill: " << ws->valid_bytes();

        ws->mutex->lock();
        ws->read((char *)ptr, size * nmemb);
        ws->mutex->unlock();

        //wstream_o << " aft read: " << ws->valid_bytes() << '\n';

        return(size * nmemb);

}

WStream::WStream() {

	mutex = new Mutex();

	first_buf = 0;
	last_buf = 0;

	first_buf_offset = 0;
	n_buf = 0;

	send_buffer = (unsigned char *) malloc(PACKET_SIZE);

	if(!send_buffer) {
		wstream_o << "send_buffer malloc died!!!!!";
		while(1);
	}
		

	send_buffer_pos = send_buffer;

	n = 0;

}

void WStream::add_buffer(WStream_Buf * w) {

	if(n_buf) {
		last_buf->next = w;
		last_buf = w;

	} else {
		first_buf = w;
		last_buf = w;

		first_buf_offset = 0;
	}

	n_buf++;

}

void WStream::seek_chr(char needle) {

	fill(1);

	//wstream_o << "Searching for needle.. " << (void *) needle;

	int i = 0;
	while (*(first_buf->data + first_buf_offset) != needle) {

		i++;

		first_buf_offset++;

		if(first_buf_offset == PACKET_SIZE) {

			fill(1); // garante que existe um próximo buffer

                        WStream_Buf * tmp = first_buf;
                        first_buf = first_buf->next;

                        first_buf_offset = 0;
	
                        n_buf--;
		}

	}

        // wstream_o << " FOUND in " << i << '\n';

}

void WStream::read(void * ptr, unsigned int n) {

        OStream o;

	int valid_bytes_first;
	int left_bytes = n;

	unsigned char * target = (unsigned char *) ptr;

	int bytes_to_read;

	while(left_bytes) {

		valid_bytes_first = PACKET_SIZE - first_buf_offset;
		bytes_to_read = min(left_bytes, valid_bytes_first);

	        memcpy(target, first_buf->data + first_buf_offset, bytes_to_read);

		first_buf_offset += bytes_to_read;
		target += bytes_to_read;

		left_bytes -= bytes_to_read;

		if (first_buf_offset == PACKET_SIZE) {

			WStream_Buf * tmp = first_buf;
			first_buf = first_buf->next;

			first_buf_offset = 0;

			n_buf--;

		}

	}
}

void WStream::fill(unsigned int n) {

        PC_NIC::Protocol p;
        PC_NIC::Address mac;

	while( valid_bytes() < n ) {         // se existem menos dados validos do que eu preciso

		ws_buffers_mut->lock();

		WStream_Buf * buf = &ws_buffers[cur_ws_buffer];

		cur_ws_buffer++;
		if (cur_ws_buffer == N_WSTREAM_BUF)
			cur_ws_buffer = 0;

		ws_buffers_mut->unlock();		

                while (1) { // fica dando receive ateh chegar coisas no meu protocolo

                        //int s = receive_nic.receive(&mac, &p, (void *) buf->data, PACKET_SIZE);

			/* fake receive++ */
			memset(buf->data + 150, 'B', 200);			
			p = prot;
			/* end fake receive */

                        //wstream_o << "recebi: " << s << "\n";

                        if (p != prot) {                  // nao sou eu

                                WStreamList * ws_list = stream_list; // global list
                                WStream * target = 0;

                                do {                        // procura quem quer esse pacote
                                        if (p == ws_list->ws->prot) {
                                                target = ws_list->ws;
                                                break;

                                        } else {
                                                ws_list = ws_list->n;

                                        }

                                } while(ws_list);

                                if (target) {                // se achou, insere no buffer correto
                                        target->mutex->lock();
                                        target->add_buffer(buf);
                                        target->mutex->unlock();

                                        ws_buffers_mut->lock();
			                
					buf = &ws_buffers[cur_ws_buffer];

		        	        cur_ws_buffer++;
                			if (cur_ws_buffer == N_WSTREAM_BUF)
		                	        cur_ws_buffer = 0;

			                ws_buffers_mut->unlock();

                                } else {
					
					//wstream_o << "Pacote sem dono!\n";

				}

                        } else    // sou eu!
                                break;

                }

                mutex->lock();
                add_buffer(buf);
                mutex->unlock();

                //wstream_o << cb->end - cb->start << " valid bytes\n";
        }

}

unsigned int WStream::valid_bytes() {

	OStream vo;

	if (n_buf) {
		//vo << "vb: " << (PACKET_SIZE - (first_buf_offset - first_buf->data)) + ((n_buf - 1) * PACKET_SIZE) << '\n';
		return ((PACKET_SIZE - first_buf_offset) + ((n_buf - 1) * PACKET_SIZE));
	} else {
		//vo << "vb: 0- NO BUFS!\n";
		return 0;
	}

}
