template <typename Component> class Scenario_Adapter : 
  private
    IF<Traits<Component>::hardware,
       HW_Scenario<Component>,
       SW_Scenario<Component> >::Result,
  private 
    Component
{
   ...
};

//Trait class for a component
template <> struct Traits<Component> {
  static const bool hardware = true;
  static const bool static_alloc = true;
};
