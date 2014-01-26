//Original code
template<class T>
struct X{
  T l;
        void foo()
        {
          l.g();
        }

};

struct Z{
  void f()
        {
          X<A> a;
        }

        struct A{
          void g(){};
        };

};

//pre-processed code
struct Z{
  class X_A_;
  void f();
  struct A{
          void g();
  };
  struct X_A_{
    A l;
    void foo();
  };
};

inline void Z::X_A_::foo()
{
  l.g();
}

inline void Z::f()
{
  X_A_ a;
}

inline void Z::A::g()
{
};
