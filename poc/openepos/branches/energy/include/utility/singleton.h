// OpenEPOS Singleton Utility Declarations

#ifndef __singleton_h
#define	__singleton_h

__BEGIN_SYS

template<class T>
class Singleton
{
protected: 
	Singleton() {}

public: 
    virtual ~Singleton() {}

    static T * instance() { return &_t; }

private: 
    static T _t;
}; 

__END_SYS
 
#endif
