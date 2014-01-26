class IA32 {
    // ...
public:
    static unsigned long long tsc() {
        unsigned long long tsc;
        __asm__ __volatile__ ("rdtsc" : "=A" (tsc) : );
        return tsc;
    }
    // ...
};
