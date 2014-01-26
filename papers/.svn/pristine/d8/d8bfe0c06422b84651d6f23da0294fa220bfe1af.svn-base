template<typename Component_Agent> class HW_Agent_Base {  

  //agent top level interface
  #pragma hls_design top
  void top_level(ac_channel<char> &data_in, ac_channel<char> &data_out){
    int op_id = receive_call(data_in);
    static_cast<Component_Agent*>(this)->dispatch(op_id);
    send_return(data_out)
  }
  
  //serdes methods
  template<typename ARG0> void serialize(ARG0 &arg0){...};
  template<typename RET> RET deserialize(){...};
  
  //RMI msg handling
  int receive_call(ac_channel<char> &ch){...};
  void send_return(ac_channel<char> &ch){...};
  ...
};
