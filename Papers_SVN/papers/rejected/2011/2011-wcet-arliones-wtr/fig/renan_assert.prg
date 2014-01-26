subprogram "hash"
        loop  repeats 512 times; end loop;
end "hash";

subprogram "hash_insert"
        dynamic call calls "data_2_pre_function" or "data_3_pre_function"
                or "data_4_pre_function" or "data_5_pre_function";
        end call;
end "hash_insert";

subprogram "data_4_pre_function"
        dynamic call calls "function_1" or "function_2" or "function_3";
        end call;
end "data_4_pre_function";
