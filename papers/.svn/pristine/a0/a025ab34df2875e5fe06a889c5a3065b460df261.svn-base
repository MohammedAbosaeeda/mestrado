    //colors definitions
    typedef enum {
        COLOR_0,
        COLOR_1,
        ...
        COLOR_128,  
    } colored_alloc;
    //overload of the new operator
    void * operator new(size_t bytes, colored_alloc c = COLOR_0) {
         //perform memory allocation from the heap defined by c
    }
    //examples of dynamic memory allocation from the application
    int *data = new (COLOR_1) int[50];
    delete[] data;