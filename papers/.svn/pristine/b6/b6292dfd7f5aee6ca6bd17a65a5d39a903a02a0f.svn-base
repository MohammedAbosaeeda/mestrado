class HW_Agent_C2 : HW_Agent_Base<HW_Agent_C2>, C2 {
  void dispatch(int op){
    switch(op){
    case OP0_ID:{ 
      //call the operation implementation from C2
      unsigned int ret = C2::op0(
           //argument deserialization implemented in base class
           HW_Agent_Base::deserialise<unsigned int>()
      ); 
      //return serialization implemented in base class
      HW_Agent_Base::serialize(ret);
      break;}
    default: break;
    }
  }
};
