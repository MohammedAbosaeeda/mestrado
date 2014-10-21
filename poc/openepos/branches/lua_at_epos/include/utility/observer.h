// EPOS-- Observer Utility Declarations

#ifndef __observer_h
#define	__observer_h

#include <utility/list.h>

__BEGIN_SYS

// Observer
class Observer;

class Observed // Subject
{ 
    friend class Observer;

private:
    typedef Simple_List<Observer>::Element Element;

protected: 
    Observed() {}

public: 
    virtual ~Observed() {}

    virtual void attach(Observer * o);
    virtual void detach(Observer * o);
    virtual void notify();

private: 
    Simple_List<Observer> _observers;
}; 

class Observer
{ 
    friend class Observed;

protected: 
    Observer(): _link(this) {} 

public: 
    virtual ~Observer() {}
    
    virtual void update(Observed * o) = 0;

private:
    Observed::Element _link;
};


// Conditionally Observed
class Conditional_Observer;

class Conditionally_Observed // Subject
{
    friend class Conditional_Observer;

private:
    typedef
    List_Elements::Singly_Linked_Ordered<Conditional_Observer> Element;

protected: 
    Conditionally_Observed() {}

public: 
    virtual ~Conditionally_Observed() {}

    virtual void attach(Conditional_Observer * o, int c);
    virtual void detach(Conditional_Observer * o, int c);
    virtual void notify(int c);

private: 
    Simple_List<Conditional_Observer, Element> _observers;
}; 

class Conditional_Observer
{
    friend class Conditionally_Observed;

protected: 
    Conditional_Observer(): _link(this) {} 

public: 
    virtual ~Conditional_Observer() {}
    
    virtual void update(Conditionally_Observed * o) = 0;

private:
    Conditionally_Observed::Element _link;
};

__END_SYS
 
#endif
