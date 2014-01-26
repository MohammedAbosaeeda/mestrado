class Scenario {
public:
  Scenario();	
  ~Scenario();	
  void enter();	
  void leave();	
};

class Interface {
protected:
  Interface(int) {}
private:
  int operation(int);
};

class Implementor: public Interface {
public:
  Implementor(int);
  int operation(int);
};

template <class Imp> class Adapter
: public Scenario, public Imp {
public:
  Adapter(int i)
    : Scenario(), Imp(i) {}
  int operation(int i) {
    enter();
    int ret = Imp::operation(i);
    leave();
    return ret;
  }
};

typedef Adapter<Implementor> Abstraction;
