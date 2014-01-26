template <typename Component> public SW_Scenario : 
  public
    IF<Traits<Component>::static_alloc,
        Static_Alloc,
        Dyn_Alloc>::Result
