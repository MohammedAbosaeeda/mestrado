template<>
class Dispatch<Component> : 
            public Dispatch_Common<Dispatch<Component>,
                                   Traits<System>::Config> {
  void dispatch(...){
    switch(op){
    case OP_0: 
      static_cast<Scenario_Adapter<Component>*>(this)->op0();
      break;
    case OP_1: ...
    }
  }  
};


