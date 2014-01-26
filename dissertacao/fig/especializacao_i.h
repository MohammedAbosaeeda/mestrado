class Vector{
   int* v;
   int sz;
public:
   Vector();
   Vector(int);
   
   int& elem (int i) {return v[i]; }
   int& operator [] (int i);
   
   void swap(Vector &);
   
   // ...
};
