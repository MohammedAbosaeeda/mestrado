//Program code

template<class T>
class X{
  T _member;
public:
  T member(){return _member;}
};

int main()
{
 X<int> x;
 cout<< x.member() <<endl;
};

//Aspect code

aspect SimpleAction {
  advice call("int %::member(...)") : before()  {
     cout << "Simple Action" ;
  }
  advice call("int X<int>::member(...)") : after()  {
     cout << "Another Simple Action" ;
  }
};

// Pre-processed code

class X_int_{
  int _member;
public:
  int member(){return _member;}
};

int main()
{
  X_int_ x;
  cout<< x.member(); //the join points would match at this function call
}