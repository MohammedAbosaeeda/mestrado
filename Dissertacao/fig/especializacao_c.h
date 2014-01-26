class Vector{
   char* v;
   int sz;
public:
   Vector();
   Vector(int);
   
   char* & elem (int i) {return v[i]; }
   char* & operator [] (int i);
   
   void swap(Vector &);
   
   // ...
};
