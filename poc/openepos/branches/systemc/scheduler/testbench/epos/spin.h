// EPOS-- Spin Lock Utility Declarations

#ifndef __spin_h
#define __spin_h

class Spin
{
public:
    Spin(): _lock(false) {}

    void acquire() { while(!_lock); _lock = true; }
    void release() { _lock = false; }

private:
    volatile bool _lock;
};


#endif
