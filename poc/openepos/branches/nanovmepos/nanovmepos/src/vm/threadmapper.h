
#ifndef THREADMAPPER_H
#define THREADMAPPER_H
#include <utility/hash.h>
#include <active.h>


__BEGIN_SYS

template<class T, int _maxNumber = 100>
class ThreadMapper
{
private:
    short int _i;
    typedef typename Simple_Hash<T , _maxNumber, unsigned int>::Element HashElement;
    HashElement * _e[_maxNumber];
    static ThreadMapper<T, _maxNumber> * _instance;
    Simple_Hash<T , _maxNumber , unsigned int> _hash;
    T  _o[_maxNumber];


public:
    ThreadMapper() : _i(0){}
    static ThreadMapper<T, _maxNumber> * instance();
    void insertElement(T th, unsigned int i);
    T search_key(unsigned int);

};

template<class T, int _max>
T ThreadMapper<T, _max>::search_key(unsigned int i)
{
   return  *(_hash.search_key(i)->object());
}



template<class T, int _max>
void ThreadMapper<T, _max>::insertElement(T  th, unsigned int i)
{
    _o[_i] = th;
    _e[_i] = new HashElement(&_o[_i], i ) ;
    _hash.insert(_e[_i]);
    _i++;
}


template<class T, int _max>
ThreadMapper<T, _max> * ThreadMapper<T, _max>::instance(){
    if(!_instance)
        _instance = new ThreadMapper();
    return _instance;
}

template<class T, int _max>
ThreadMapper<T, _max> * ThreadMapper<T, _max>::_instance = 0x0;
__END_SYS

#endif // THREADMAPPER_H
