// C2 definition
class C2 :
  public MAP<C2_Impl, HW_Proxy_C2, SW_Proxy_C2,
             Traits<C2>::imp_domain>::Result {};

//MAP metaprogram             
template<typename Impl, typename HW_Proxy, typename SW_Proxy, 
         bool comp_imp_domain>
struct MAP {
  typedef IF<comp_imp_domain==Traits<Sys>::curr_imp_domain,
            Impl,
          IF<Traits<Sys>::curr_imp_domain==HARDWARE,
            HW_Proxy,
            SW_Proxy>::Result> >::Result
  Result;
};             
