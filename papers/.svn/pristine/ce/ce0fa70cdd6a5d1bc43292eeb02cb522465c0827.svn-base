#include <sentient.h>
#include <pm.h>

int main() {
  Atomic_PM pm;
  Component_Wrapper<Temperature_Sentient> t;
  Component_Wrapper<Communicator> comm;

  pm.bind(t);
  pm.bind(comm);

  unsigned char temp[60];

  while(1) {
    for(int i = 0; i < 60; i++)
      temperatures[i] = t.read();

    comm.send(temp,60);
  }
}
