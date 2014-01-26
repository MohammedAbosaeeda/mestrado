template <> struct Traits<PC_NIC>: public Traits<PC_Common>
{
    typedef LIST<PCNet32, PCNet32> NICS;

    static const unsigned int PCNET32_UNITS = NICS::Count<PCNet32>::Result;
    static const unsigned int PCNET32_SEND_BUFFERS = 8; 
    static const unsigned int PCNET32_RECEIVE_BUFFERS = 8; 

    static const unsigned int E100_UNITS = NICS::Count<E100>::Result;
    static const unsigned int E100_SEND_BUFFERS = 8; 
    static const unsigned int E100_RECEIVE_BUFFERS = 8; 

    static const unsigned int C905_UNITS = NICS::Count<C905>::Result;
    static const unsigned int C905_SEND_BUFFERS = 8;
    static const unsigned int C905_RECEIVE_BUFFERS = 8;
};
