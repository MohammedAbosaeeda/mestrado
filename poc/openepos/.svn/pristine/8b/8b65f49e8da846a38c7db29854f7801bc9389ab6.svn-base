#ifndef _CIRCULARBUF_H_
#define _CIRCULARBUF_H_

#include "../mux2/includes/MuxWrapper.h"
#include <utility/string.h>

#define BUFFER_SIZE 512000 //500Kb

__USING_SYS

class CircularBuf {

        public:

        CircularBuf() : data((char *) malloc(BUFFER_SIZE)), start(0), end(0), max_size(BUFFER_SIZE) {

                if(!data)
                        wdie("CircBuf: Malloc Failed!!\n");

        };

        void insert(const char * ptr, int size) {

                if (end - start + size > max_size)
                        wdie("CircBuf: Overflow!!\n");

                int relative_end = end % max_size;
                int ceiling = max_size - relative_end; // number of bytes left till buffer 'ceiling'

                if (size > ceiling) {
                        memcpy(data + relative_end, ptr, ceiling);
                        memcpy(data, ptr + ceiling, size - ceiling);
                } else  {
                        memcpy(data + relative_end, ptr, size);
                }

                end += size;

        }

        void remove(char * ptr, int size) {

                if (end - start - size < 0)
                        wdie("CircBuf: Underflow!!\n");

                int relative_start = start % max_size;
                int ceiling = max_size - relative_start; // number of bytes left till buffer 'ceiling'

                if (size > ceiling) {
                        memcpy(ptr, data + relative_start, ceiling);
                        memcpy(ptr + ceiling, data, size - ceiling);
                } else {
                        memcpy(ptr, data + relative_start, size);
                }

                start += size;

        }

	void rollback(int n) { end -= n; };

        char * data;

        int start;
        int end;

        const int max_size;

};

#endif 
