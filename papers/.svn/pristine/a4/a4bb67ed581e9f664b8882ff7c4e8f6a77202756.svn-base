//Original Code
template<class T>
class X{
  public:
    T* b;
    void foo()
    {
        b->foo2();
    }
};

class C {
  public:
          void foo2(){};
};

int function()
{ 
  X<C> xc;
        xc.foo();
        return 1;
}

//pre processed code

class C{
  public:
          void foo2(){};
};

class X_C_{
  public:
          C *b;
                void foo()
                {
                        b->foo2();
                }
};

int function()
{
  X_C_ xc;
        xc.foo();
        return 1;
}
