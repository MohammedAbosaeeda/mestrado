subprogram "__aeabi_ui2f" time 42 cycles; end "__aeabi_ui2f";
subprogram "__aeabi_i2f" time 40 cycles; end "__aeabi_i2f";
subprogram "__divsf3" time 0 cycles; end "__divsf3";
subprogram "__mulsf3" time 0 cycles; end "__mulsf3";

subprogram "factorial"
        loop on line 22 repeats 18 times; end loop;
end "factorial";

subprogram "power"
        loop on line 11 repeats 18 times; end loop;
end "power";

subprogram "cosine"
        loop on line 36 repeats 10 times; end loop;
end "cosine";

subprogram "main"
        dynamic call on line 77 calls "g721_encoder"; end call;
        dynamic call on line 84 calls "g721_decoder"; end call;
end "main";