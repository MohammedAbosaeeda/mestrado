class Vector{
   MyClass* v;
   int sz;
public:
   Vector();
   Vector(int);
   
   MyClass& elem (int i) {return v[i]; }
   MyClass& operator [] (int i);
   
   void swap(Vector &);
   
   // ...
};
