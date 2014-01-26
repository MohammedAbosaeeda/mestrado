template <> struct Traits<Component>{
    static const bool static_alloc = true;
    ...
};

//IF metaprogram
template<bool condition, typename Then, typename Else>
struct IF { typedef Then Result; };
template<typename Then, typename Else>
struct IF<false, Then, Else> { typedef Else Result; };
