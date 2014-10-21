// Page Coloring test 

#include <utility/ostream.h>
#include <display.h>
#include <periodic_thread.h>
#include <rtc.h>
#include <chronometer.h>
#include <semaphore.h>

__USING_SYS

Display d;
OStream cout;
Semaphore s;

#define SIZE 10

int main() 
{
  //d.clear();
  cout << "Page coloring allocation test\n";
  
  char *array[SIZE];
  
  for(int i = 0; i < SIZE; i++) {
    if(i == 0) {
    cout << "Allocating array " << i;
    //array[i] = new ((alloc_priority) (i % Traits<MMU>::colors)) char[4092];
    array[i] = new ((alloc_priority) (i % Traits<MMU>::colors)) char[4088];
    cout << " done. ";
    cout << "Accessing array " << i << " address = " << (void *) &array[i][0];
    array[i][0] = 'A' + i;
    cout << " done. ";
    cout << "Printing array.. ";
    cout << array[i][0];
    cout << " done\n";
    } else {
      array[i] = new ((alloc_priority) (i % Traits<MMU>::colors)) char[4088];
      array[i][0] = 'A' + i;
      cout << " " << array[0][0];
    }
  }
  cout << "\n";
  
  //cout << "Heap size = " << Application::heap(2)->grouped_size() << "\n";
  
  //while(1);
  //cout << "Physical address = " << (void *) MMU::physical(&array[0][0]) << "\n";
  
  cout << "Printing array.. ";
  for(int i = 0; i < SIZE; i++) {
    //cout << " address = " << (void *) &array[i][0] << " mapped into = " << (void *) MMU::physical(&array[i][0]); 
    cout << " value = " << array[i][0] << " ";
  }
  
  cout << "done\n";
  
  cout << "[0][0] " << array[0][0] << "\n";
  
  cout << "deleting arrays..";
  for(int i = 0; i < SIZE; i++) {
    delete array[i];
  }
  cout << "done\n";
  
  //cout << "CR4 = " << (void *) CPU::cr4();
  
  while(1);
}