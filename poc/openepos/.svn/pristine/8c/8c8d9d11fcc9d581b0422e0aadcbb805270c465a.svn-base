// OpenEPOS ARMv4TDMI Port Traits

#ifndef __armv4tdmi_traits_h
#define __armv4tdmi_traits_h

// Mediators - Architecture - ARMv4TDMI

__BEGIN_SYS

template<> struct Traits<ARMv4TDMI> : public Traits<void> {
	static const int BUSY_WAIT_10US_LOOP_COUNT = 14; // calibrated for ARMv4TDMI@24MHz
};

template<> struct Traits<ARMv4TDMI_FPU> : public Traits<void> {
	static const bool enabled = false;
};

template<> struct Traits<ARMv4TDMI_TSC> : public Traits<void> {
};

template<> struct Traits<ARMv4TDMI_MMU> : public Traits<void> {
};

__END_SYS

#endif
