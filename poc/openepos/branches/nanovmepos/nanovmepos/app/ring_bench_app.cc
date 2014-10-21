
#include <utility/ostream.h>

// This work is licensed under the EPOS Software License v1.0.
// A copy of this license is available at the EPOS system source tree root.
// A copy of this license is also available online at:
// http://epos.lisha.ufsc.br/EPOS+Software+License+v1.0
// Note that EPOS Software License applies to both source code and executables.
#include <active.h>
#include <semaphore.h>
#include <utility/ostream.h>
#include<chronometer.h>

__USING_SYS

OStream cout;
//Chronometer* chron = new Chronometer;
//qunsigned int msg_pass_time = 0;
//unsigned int spawntime_total = 0;

void led3_on() {  CPU::out8(Machine::IO::PORTB, (1 << 5));
              //    chron->start();
               }//   chron->lap();}
unsigned int led3_off() {
    CPU::out8(Machine::IO::PORTB, (Machine::IO::PORTB,
                                   Machine::IO::PORTB & ~(1 << 5)));


   return 1;//chron->read();
}

class RingThread : public Active  {

public:
    int _id;
    RingThread(int id = 1) : _id(id), _message(9999)  {
        _semaphore = new Semaphore(0);
    }

    int run() {
        while (_message > 0) {
            _semaphore->p();

             if (_message <= 0) break;
            _next->send(_message -1);
        }
        return 0;
    }

    void link(RingThread * th) {
        _next = th;
    }

    void send(int message) {
        _message = message;
        _semaphore->v();
    }
protected:
    RingThread * _next;
    unsigned int _message;
    Semaphore * _semaphore;
};


class First : public RingThread  {

public:
    First(unsigned int n , unsigned int m) : _nProcess(n), _nMessages(m){}
    int run() {
        cout << "Begin" << endl;
   //     led3_on();
         RingThread::run();
        cout << "end" << endl;
     //   led3_off();

    }


private:
  unsigned   int _nProcess, _nMessages;
    //int t1, t2, t3;
};

unsigned int nproc =2;
unsigned short int  nmessages = nproc * 5000;

int main()
{


    RingThread * old;
    cout << nmessages << endl;

    First* first = new First(nproc, nmessages);
    (first)->start();


    old = first;
     led3_on();
    while (--nproc > 0) {
        //++id;

        RingThread * curr = new RingThread(2);
        curr->start();

        old->link(curr);
        old = curr;
     //   cout << "ALSI" << endl;
     }
led3_off();

  //  chron->stop();
   //   spawntime_total = chron->read();
   // cout << "Spawn time: " <<  spawntime_total << endl;
  //  cout << "Spwan for each: " << spawntime_total/5 << endl;
    old->link(first);
    first->send(nmessages);

 //

    return 0;
}
