void native_adder_invoke(u08_t mref)
{
    if (mref == NATIVE_METHOD_st_sum) {
        nvm_int_t a = stack_pop_int();
        nvm_int_t b = stack_pop_int();
        stack_push(nvm_int2stack(a + b));
    } 
    else {
        error(ERROR_NATIVE_UNKNOWN_METHOD);
    }
}
