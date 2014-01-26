class KESO_FFI_Scenario : public KESO_FFI_Aspect
{
    //...

    template<T1, T2>
    T2 enter(T1 inputs)
    {
        T2 hw_inputs = KESO_FFI_Aspect::enter(inputs);
        return hw_inputs;
    }

    template<T3, T4>
    T4 leave(T3 hw_outputs)
    {
        T4 outputs = KESO_FFI_Aspect::leave(hw_outputs);
        return outputs;
    }


    //...
}
