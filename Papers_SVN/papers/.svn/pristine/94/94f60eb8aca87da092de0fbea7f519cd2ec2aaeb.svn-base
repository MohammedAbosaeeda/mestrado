const int COUNT = 16000, BUF_SIZE = 16;
short buffer[BUF_SIZE];
Semaphore empty(BUF_SIZE), full(0);
Serial_Communicator COM;

int consumer() {
    int count_c, out = count_c = 0;
    while (count_c < COUNT) {
	full.p();
        // deal with data in buffer[out]
	COM->send(&buffer[out],2);
	out = (out + 1) % BUF_SIZE;
	empty.v(); 
	count_c++;
    }
}

int main() {

    Thread * cons = new Thread(&consumer);

    // producer
    int count_p, in = count_p = 0;
    while (count_p < COUNT) {
	empty.p();
        COM->receive(&buffer[in],2);
	// deal with data in buffer[in]
	in = (in + 1) % BUF_SIZE;
	full.v();
	count_p++;
    }
}
