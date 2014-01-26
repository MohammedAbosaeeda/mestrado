class IA32: public CPU_Common
{
  // ...

  static int finc(volatile int & value) 
  {
    register int old = 1;
    ASMV("lock xadd %0, %2"
	     : "=a"(old)
	     : "a"(old), "m"(value)
	     : "memory"); 
    return old;
  }

  static int fdec(volatile int & value) 
  {
    register int old = -1;
    ASMV("lock xadd %0, %2"
	     : "=a"(old)
	     : "a"(old), "m"(value)
	     : "memory"); 
    return old;
  }
  
  // ...
};

