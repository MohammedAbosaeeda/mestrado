template<typename Dispatcher>
class Dispatch_Common<Dispatcher,Config::CatapultC_CPP> {
..
  #pragma hls_design top
  void top_level(ac_channel<char> &data_in,
                 ac_channel<char> &data_out){
    //deserialize input parameters
    ...
    static_cast<Dispatch<Component>*>(this)->dispatch(...);
    //serialize operation return value
    ...
  }
};
