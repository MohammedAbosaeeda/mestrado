//C2 definition
class C2 : 
  public MAP<Scenario_Adapter<C2>, HW_Proxy<C2>, SW_Proxy<C2>, 
         Traits<C2>::hardware>::Result {};
            
//MAP metaprogram            
template<typename Implementation, typename HW_Proxy, typename SW_Proxy, 
         bool hardware>
struct MAP {
  typedef IF<hardware==Traits<System>::hardware_domain, 
            Implementation,
          IF<Traits<System>::hardware_domain, 
            HW_Proxy,
            SW_Proxy>::Result> >::Result  
    Result; 
};
