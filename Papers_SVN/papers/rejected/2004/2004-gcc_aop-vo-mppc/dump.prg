
;; Function int main() (main)
;; enabled by -dump-tree-inlined

@1      function_decl    name: @2       type: @3       srcp: factorial.cc:7      
                         C              extern         body: @4      
@2      identifier_node  strg: main     lngt: 4       
@3      function_type    size: @5       algn: 64       retn: @6      
                         prms: @7      
@4      compound_stmt    line: 7        body: @8       next: @9      
@5      integer_cst      type: @10      low : 64      
@6      integer_type     name: @11      size: @12      algn: 32      
                         prec: 32       min : @13      max : @14     
@7      tree_list        valu: @15     
@8      scope_stmt       line: 7        begn           clnp          
                         next: @16     
@9      return_stmt      line: 7        expr: @17     
@10     integer_type     name: @18      size: @5       algn: 64      
                         prec: 36       unsigned       min : @19     
                         max : @20     
@11     type_decl        name: @21      type: @6       srcp: <internal>:0      
@12     integer_cst      type: @10      low : 32      
@13     integer_cst      type: @6       high: -1       low : -2147483648 
@14     integer_cst      type: @6       low : 2147483647 
@15     void_type        name: @22      algn: 8       
@16     compound_stmt    line: 7        body: @23      next: @24     
@17     init_expr        type: @6       op 0: @25      op 1: @26     
@18     identifier_node  strg: bit_size_type           lngt: 13      
@19     integer_cst      type: @10      low : 0       
@20     integer_cst      type: @10      high: 15       low : -1      
@21     identifier_node  strg: int      lngt: 3       
@22     type_decl        name: @27      type: @15      srcp: <internal>:0      
@23     return_stmt      line: 7        expr: @28     
@24     scope_stmt       line: 7        end            clnp          
@25     result_decl      type: @6       scpe: @1       srcp: factorial.cc:7      
                         size: @12      algn: 32      
@26     integer_cst      type: @6       low : 0       
@27     identifier_node  strg: void     lngt: 4       
@28     init_expr        type: @6       op 0: @25      op 1: @29     
@29     integer_cst      type: @6       low : 24      
