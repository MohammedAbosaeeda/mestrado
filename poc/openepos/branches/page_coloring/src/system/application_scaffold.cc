// EPOS Application Scaffold and Application Abstraction Implementation

#include <utility/ostream.h>
#include <application.h>
#include <segment.h>

__BEGIN_SYS

// Global objects
OStream cout;
OStream cerr;

// Application class attributes
Multi_Heap<Traits<MMU>::page_coloring> Application::_heap[IF_INT<Traits<Heap>::priority_alloc, 2, 1>::Result];
Segment Application::_heap_segment[IF_INT<Traits<MMU>::page_coloring, Traits<MMU>::colors, 1>::Result];

__END_SYS
