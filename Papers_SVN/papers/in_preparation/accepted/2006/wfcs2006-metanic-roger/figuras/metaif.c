template<typename NICS>
class Meta_NIC {
  //...	
  public:
  typedef typename IF<polymorphic,
	  NIC_Base, 
	  typename NICS::template 
	           Get<0>::Result>::Result Base;
  // ...
};
