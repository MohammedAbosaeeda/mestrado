#ifndef _WSTREAM_H_
#define _WSTREAM_H_

#define min(a,b) (((a)<(b))?(a):(b))

#define PACKET_SIZE 1500

#include <mutex.h>
#include <utility/ostream.h>

//include "CircularBuf.h"

__USING_SYS

class WStream_Buf {

	public:
		WStream_Buf() {

			next = 0;

		}

		~WStream_Buf() {
			

		}

		WStream_Buf * next;
		unsigned char data[PACKET_SIZE];

};

class WStream {

        public:

                WStream();

		void add_buffer(WStream_Buf * w); 

		void read(void * ptr, unsigned int n);
		void fill(unsigned int n);
		void seek_chr(char needle);
		unsigned int valid_bytes();

                unsigned short prot;
	
		WStream_Buf * first_buf;
		WStream_Buf * last_buf;
		unsigned int first_buf_offset;

		unsigned int n_buf;
	
		unsigned char * send_buffer;
		unsigned char * send_buffer_pos;

                Mutex * mutex;

		int n; // debug: number of receives/sends

};

class WStreamList {

        public:
                WStreamList * n;
                WStream * ws;

                WStreamList(WStream * w) : n(0), ws(w) {};

                void insert(WStream * w) {     // sempre insere como segundo na lista (nao precisa atualizar pointer no wrapper)

                        WStreamList * tmpstream = new WStreamList(w);
                        tmpstream->n = n;

                        n = tmpstream;

                }

};

#endif

