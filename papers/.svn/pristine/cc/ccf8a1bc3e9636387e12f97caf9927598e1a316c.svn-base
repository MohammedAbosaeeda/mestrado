class SW_Proxy_Base {
  template<unsigned int OP, typename RET>  
  RET call_r(){...}

  // implements a call for a one arg/one return method
  template<unsigned int OP, typename RET, typename ARG0>  
  RET call_r(ARG0 &arg0){
    //serialize arguments
    char data[(sizeof(RET)>sizeof(ARG0))?sizeof(RET)?sizeof(ARG0)];
    *reinterpret_cast<ARG0*>(data) = arg0;
    //RMI call
    Component_Manager::call(_type_id, _comp_id, OP, sizeof(ARG0), data);
    //deserialize arguments
    return *reinterpret_cast<RET*>(data);
  }
    ...
};
