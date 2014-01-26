#include <stdio.h>
#include "scenario_adapter.h"

inline Scenario::Scenario() {printf("Scenario()\n");}
inline Scenario::~Scenario() {printf("~Scenario()\n");}
inline void Scenario::enter() {printf("enter();\n");}
inline void Scenario::leave() {printf("leave();\n");}

inline Implementor::Implementor(int i): Interface(i)
{printf("Implementor(%d);\n", i);}
inline int Implementor::operation(int i)
{printf("operation(%d);\n", i);}

int main()
{
  Abstraction a(2);
  a.operation(3);
}
