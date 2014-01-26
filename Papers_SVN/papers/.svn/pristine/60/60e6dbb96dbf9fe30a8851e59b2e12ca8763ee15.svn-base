template<typename T,
    bool shared = Traits<T>::shared,
    bool instances = Traits<T>::instances>
class Power_Manager;

template<typename T>
class Power_Manager<T,false,false>
    : public T {
public:
    //T::Constructors
    Power_Manager() : T(){}
    Power_Manager(unsigned int a) : T(a){}

    // ...

    // T::public_methods();
    char get();
    void put(char c);

    // ...

public:
    void power(char mode);
    char power(){ return _op_mode; };

private:
    char _op_mode;
    char _prev_op_mode;
};

// ...