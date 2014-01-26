class Native_Code_Adapter : public template <Mediator>, public template<FFI_Scenario>
{
    //...

    template<T1, T2, T3, T4>
    T4 operation(T1 inputs)
    {
        T2 hw_inputs = enter(inputs);
        T3 hw_outputs = Mediator::operation(hw_inputs);
        T4 outputs = leave(hw_outputs);
        return outputs;
    }

    //...
}
