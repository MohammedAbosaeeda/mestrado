template <typename derived_class> class Base {
  void interface() {
    static_cast<derived_class*>(this)->implementation();
  }
};

class Derived : public Base<Derived> {
  void implementation();
};
