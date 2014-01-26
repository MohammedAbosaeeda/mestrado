template <class T> class Vector{
   T* v;
   int sz;
public:
   Vector();
   Vector(int);
   
   T& elem (int i) {return v[i]; }
   T& operator [] (int i);
   
   void swap(Vector &);
   
   // ...
};
