#include <utility/crc.h>
#include <utility/ostream.h>

__USING_SYS

OStream cout;

struct message
{
	char * block;
	int size;
	int crc;
};

typedef struct message message_t;

int main() {
	cout << "\nCreating a block of data. It's just a string.\n";
	message_t m;
	m.block = "My block of data.";
	m.size = strlen(m.block);
	cout << "Let's print our block of data: \"" << m.block << "\".\n";
	cout << "Let's calculate our CRC code.\n";
	m.crc = CRC::crc16(m.block,m.size);
	cout << "We now have our CRC code. It's " << m.crc << " .\n\n";
	
	message_t received_m;
	int i;
	for (i = 0; i < m.size; i++) received_m.block[i] = m.block[i];
	received_m.size = strlen(received_m.block);
	cout << "Now we are making an undesired change on the data,\n"
		 << "simulating a noise in the transmission channel.\n\n";
	received_m.block[0] = 'B';
	received_m.block[15] = 'e';
	
	cout << "The old block of data is \"" << m.block << "\".\n"
		<< "And the changed block is \"" << received_m.block << "\".\n\n";
		
	cout << "Let's calculate the CRC code again.\n";
	received_m.crc = CRC::crc16(received_m.block,received_m.size);
	cout << "The new CRC code is " << received_m.crc << " .\n\n";
	
	if (m.crc != received_m.crc)
		cout << "The CRC codes don't match. The message was changed.\n\n";
	else
		cout << "The CRC codes are equal. The message has no error.\n\n";
    return 0;
}

