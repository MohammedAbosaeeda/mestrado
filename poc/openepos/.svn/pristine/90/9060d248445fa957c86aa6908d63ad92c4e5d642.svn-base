#include "threadwrapper.h"
#include "array.h"
#include <alarm.h>


__BEGIN_SYS

ThreadWrapper::ThreadWrapper(unsigned int id,
                             unsigned int methodAddress
                             )
    : _id(id),
    _methodAddress(methodAddress) {

    db<VM>(TRC) << "Thread construct" << endl;

    //HeapVM::getInstance()->heap_show();
    if (id == 111){
    _stack = new StackVm;
    _stack->stack_init(NvmFile::instance()->nvmfile_get_static_fields());
    db<VM>(TRC) << "Stack sp => " << _stack->stack_get_sp() << endl;
    } else {
        _stack = new StackVm;
        db<VM>(TRC) << "Stack sp => " << _stack->stack_get_sp() << endl;
        _stack->init();

    }
    }


void ThreadWrapper::copy(StackVm *stack)
{
    db<VM> (TRC) << "Copiando stack +++++" << endl;

    NvmFile::nvm_method_hdr_t mhdr, *mhdr_ptr;
    NvmFile * _nvmFile = NvmFile::instance();

    mhdr_ptr = _nvmFile->nvmfile_get_method_hdr(_methodAddress);
    _nvmFile->nvmfile_read(&mhdr, mhdr_ptr, sizeof(NvmFile::nvm_method_hdr_t));

    int stackPcAdjust = _stack->copy(stack);
    int a, b, c;
    a = mhdr.max_locals;
    b = mhdr.max_stack;
    c = mhdr.args;
    db<VM>(INF) << "Allocating space for " <<
                   a << "locals and " << b
                << "stack elements - " <<
                   c << "args\n";

    int helper = a + b;
    _stack->stack_add_sp(stackPcAdjust-helper);
}

int ThreadWrapper::run()
{
   this->vm_run(_methodAddress) ;
}

void ThreadWrapper::vm_new(u16_t mref) {

    if(NATIVE_ID2CLASS(mref) < NATIVE_CLASS_BASE)
    {

        heap_id_t h;
        int x = NATIVE_ID2CLASS(mref);

        db<VM>(TRC) << "local new #" << x << endl;

        HeapVM * heapVm = HeapVM::getInstance();
        NvmFile * nvmFile = NvmFile::instance();
        int u = nvmFile->nvmfile_get_class_fields(x);

        db<VM> (TRC) << "non static fields: " << u << "  ";

        // create object with
        h = heapVm->heap_alloc(true, sizeof(nvm_word_t) *
                              (VM_CLASS_CONST_ALLOC+
                               nvmFile->nvmfile_get_class_fields(NATIVE_ID2CLASS(mref)))
                               );

        _stack->stack_push(NVM_TYPE_HEAP | h);

        // store reference in object, so we can later d _nvmFile->nvmfile_get_class_fields(NATIVE_ID2CLASS(mref));etermine which kind
        // of object this is. this is required for inheritance
        static_cast<nvm_ref_t*>(heapVm->heap_get_addr(h))[0] = mref;

        db<VM> (INF) << "INF sobre a heap após executar o vm new" << endl;
       // heapVm->heap_show();

        return;
    }
    native_new(mref, _stack);
}

int ThreadWrapper::adjustPC(){

    ThreadWrapper * th = ( (ThreadWrapper * ) Active::running());
    db<VM>(TRC) << "Wrapper PC -> " << hex << th->pc << endl;
    (th->pc) = ((th->pc)) + pc_magic_jump;
    u08_t * pcia = th->pc;
    db<VM>(TRC) << "Wrapper PC -> " << hex << (th->pc);
    vm_arg_t arg;
    arg.z.bh = nvmfile_read08(pcia+1);
    arg.z.bl = nvmfile_read08(pcia+2);
    db<VM> (TRC) << "NATIVE local method call from method " <<
              //  (int) mref << " to " <<
    (int) arg.w << endl;
    return (int)arg.w;

  //  Alarm::delay(5000000);
}

void  ThreadWrapper::vm_run(u16_t mref) {
    db<VM> (TRC) << "+++  Inicio do metodo run +++++++" << endl;
    #define VM_METHOD_CALL_REQUIREMENTS 3
  register u08_t instr, pc_inc;//, *pc;
  register nvm_int_t tmp1=0;
  register nvm_int_t tmp2;
 // register vm_arg_t arg0;
  NvmFile::nvm_method_hdr_t mhdr, *mhdr_ptr;
  HeapVM * __heapVm = HeapVM::getInstance();
  NvmFile * _nvmFile = NvmFile::instance();

  //STACK::??????
#ifdef NVM_USE_FLOAT
  nvm_float_t f0;
  nvm_float_t f1;
#endif
  db<VM>(TRC) << "Running method " << mref << endl;
#ifdef NVM_USE_STACK_CHECK
   _stack->stack_save_sp();
#endif

  // load method header into ram
  mhdr_ptr = _nvmFile->nvmfile_get_method_hdr(mref);
 _nvmFile->nvmfile_read(&mhdr, mhdr_ptr, sizeof(NvmFile::nvm_method_hdr_t));
 // NvmFile::nvmfile_r
  // determine method description address and code
  pc = (u08_t*)mhdr_ptr + mhdr.code_index;
  //teste
  // make space for locals on the stack
  int a, b, c;
  a = mhdr.max_locals;
  b = mhdr.max_stack;
  c = mhdr.args;
  db<VM>(TRC) << "Allocating space for " <<
          a << "locals and " << b
          << "stack elements - " <<
          c << "args\n";

  // increase stack space. locals will be put on the stack as
  // well. method arguments are part of the locals and are
  // already on the stack
  __heapVm->heap_steal(sizeof(nvm_stack_t) * (mhdr.max_locals + mhdr.max_stack + mhdr.args));

  // determine address of current locals (stack pointer + 1)
 // VM::getInstance()->locals(_stack->stack_get_sp() + 1);
  db<VM> (TRC) << " stack with locals => " << _stack->stack_get_sp()  << endl;
   _locals = _stack->stack_get_sp() +1;
  _stack->stack_add_sp(mhdr.max_locals);

  _stack->stack_save_base();
  db<VM> (TRC) << "Before do: => sp => " << _stack->stack_get_sp() << endl;

  db<VM>(INF) << (u08_t*)_stack->stack_get_sp() -(u08_t*)
                 _stack->sp_saved << " bytes on stack\n";

  do {


  //   Alarm::delay(2000000);
    instr = nvmfile_read08(pc);
    pc_inc= 1;
    // prefetch next args (in big endian order)
    arg0.z.bh = nvmfile_read08(pc+1);
    arg0.z.bl = nvmfile_read08(pc+2);

     db<VM> (TRC) << endl << hex << endl << hex <<
             "PC: " << (int) pc << endl << hex <<
             "instr: " << (int) instr << hex << endl << hex <<
             "arg1: " << (int) arg0.z.bh << hex << endl << hex <<
             "arg2  " << (int) arg0.z.bl << hex << endl << hex <<
             "Argw:" << (int) arg0.w << hex << endl << hex;

     HeapVM * heap =HeapVM::getInstance();
  /*   heap->heap_show();

     db<VM> (INF)<< " HEAP and Stack INFO:" << endl;
     heap->heap_show();

     db<VM> (INF) << "Heap base ->" << heap->heap_get_base() << endl;
     db<VM>(INF)<< "SP ->" << _stack->stack_get_sp() << endl;
*/
    if(instr == OP_NOP) {
      // // DEBUGF("nop\n");
    }

    else if(instr == OP_BIPUSH) {
      _stack->stack_push(arg0.z.bh); pc_inc = 2;
      // // DEBUGF("bipush #%d\n", stack_peek(0));
    }

    else if(instr == OP_SIPUSH) {
      _stack->stack_push(~NVM_IMMEDIATE_MASK & (arg0.w)); pc_inc = 3;
      // // DEBUGF("sipush #"DBG16"\n", stack_peek_int(0));
    }

    else if((instr >= OP_ICONST_M1) && (instr <= OP_ICONST_5)) {
        _stack->stack_push(instr - OP_ICONST_0);
      // // DEBUGF("iconst_%d\n", stack_peek(0));
    }

    // move integer from stack into locals
    else if(instr == OP_ISTORE) {
    //  VM::getInstance()->local(arg0.z.bh, _stack->stack_pop());
        _locals[arg0.z.bh] = _stack->stack_pop();

      pc_inc = 2;
      // // DEBUGF("istore %d (%d)\n", arg0.z.bh, nvm_stack2int(locals[arg0.z.bh]));
    }

    // move integer from stack into locals
    else if((instr >= OP_ISTORE_0) && (instr <= OP_ISTORE_3)) {
    //  VM::getInstance()->local(instr - OP_ISTORE_0, _stack->stack_pop());
        _locals[instr - OP_ISTORE_0] = _stack->stack_pop();
        // // DEBUGF("istore_%d (%d)\n", instr - OP_ISTORE_0, nvm_stack2int(locals[instr - OP_ISTORE_0]));
    }

    // load int from local variable (push local var)
    else if(instr == OP_ILOAD) {
        _stack->stack_push(_locals[arg0.z.bh]);
                  //;;VM::getInstance()->local(arg0.z.bh));
      pc_inc = 2;
      // // DEBUGF("iload %d (%d, "DBG_INT")\n", locals[arg0.z.bh], stack_peek_int(0), stack_peek_int(0));
    }

    // push local onto stack
    else if((instr >= OP_ILOAD_0) && (instr <= OP_ILOAD_3)) {
        _stack->stack_push(_locals[instr - OP_ILOAD_0]);
                  //::getInstance()->local(instr - OP_ILOAD_0));
      // // DEBUGF("iload_%d (%d, "DBG_INT")\n", instr-OP_ILOAD_0, stack_peek_int(0), stack_peek_int(0));
    }

    // immediate comparison / comparison with zero
    else if((instr >= OP_IFEQ) && (instr <= OP_IF_ICMPLE)) {
      // // DEBUGF("if");

      if((instr >= OP_IFEQ) && (instr <= OP_IFLE)) {
        // comparision with zero
        tmp2 = 0;
        instr -= OP_IFEQ - OP_IF_ICMPEQ;
      } else {
        // comparison with second argument
        // // DEBUGF("_cmp");
        tmp2 = _stack->stack_pop_int();
      }

      tmp1 = _stack->stack_pop_int();

      switch(instr) {
        case OP_IF_ICMPEQ: /* // DEBUGF("eq (%d %d)", tmp1, tmp2); */
          tmp1 = (tmp1 == tmp2); break;
        case OP_IF_ICMPNE: /* // DEBUGF("ne (%d %d)", tmp1, tmp2); */
          tmp1 = (tmp1 != tmp2); break;
        case OP_IF_ICMPLT: /* // DEBUGF("lt (%d %d)", tmp1, tmp2); */
          tmp1 = (tmp1 <  tmp2); break;
        case OP_IF_ICMPGE: /* // DEBUGF("ge (%d %d)", tmp1, tmp2); */
          tmp1 = (tmp1 >= tmp2); break;
        case OP_IF_ICMPGT: /* // DEBUGF("gt (%d %d)", tmp1, tmp2); */
          tmp1 = (tmp1 >  tmp2); break;
        case OP_IF_ICMPLE: /* // DEBUGF("le (%d %d)", tmp1, tmp2); */
          tmp1 = (tmp1 <= tmp2); break;
      }

      // change pc if jump has been taken
      if(tmp1) { /* // DEBUGF(" -> taken\n"); */ pc += arg0.w; pc_inc = 0; }
      else     { /* // DEBUGF(" -> not taken\n"); */ pc_inc = 3; }
    }

    else if(instr == OP_GOTO) {
      pc_inc = 3;
      int x = sizeof(short int);

      db<VM> (TRC) << "goto " <<  dec << (signed short int)arg0.w <<
                      "lalal:  " << x <<
                      endl;
      pc += /*(signed short int)*/ (arg0.w-3);
      db<VM>(TRC) << "PC goto " << pc << endl;
      //Alarm::delay(1000000);
    }

    // two operand arithmetic
    else if((instr >= OP_IADD) && (instr <= OP_IINC)) {
      // single operand arithmetic
      if(instr == OP_INEG) {
        tmp1 = - (_stack->stack_pop_int());
        _stack->stack_push(nvm_int2stack(tmp1));
        // // DEBUGF("ineg(%d)\n", -stack_peek_int(0));

      } else if(instr == OP_IINC) {
        // // DEBUGF("iinc %d,%d\n", arg0.z.bh, arg0.z.bl);
          _locals[arg0.z.bh] = (VM::getInstance()->
                                nvm_stack2int(_locals[arg0.z.bh]
                          + arg0.z.bl)) & ~NVM_IMMEDIATE_MASK;

               /*   VM::getInstance()->local(
                    arg0.z.bh
                    ,(
                      VM::getInstance()->nvm_stack2int(
                            VM::getInstance()->
                            local(arg0.z.bh)) + arg0.z.bl)
                    & ~NVM_IMMEDIATE_MASK);
*/
        pc_inc = 3;

#ifdef NVM_USE_FLOAT
      } else if(((instr & 0x03) == 0x02) && (instr <= OP_FNEG)) {
        if (instr == OP_FNEG) {
          f0 = - (_stack->stack_pop_float());
          _stack->stack_push(VM::getInstance()->
                             nvm_float2stack(f0));
          // // DEBUGF("fneg (%f)\n", stack_peek_float(0));
        }
        else {
          f0 = _stack->stack_pop_float();  // fetch operands from stack
          f1 = _stack->stack_pop_float();
          switch(instr) {
            case OP_FADD:  /* // DEBUGF("fadd(%f,%f)", f1, f0); */
              f1  += f0; break;
            case OP_FSUB: /* // DEBUGF("fsub(%f,%f)", f1, f0); */
              f1  -= f0; break;
            case OP_FMUL: /* // DEBUGF("fmul(%f,%f)", f1, f0); */
              f1  *= f0; break;
            case OP_FDIV: /* // DEBUGF("fdiv(%f,%f)", f1, f0); */
              if(!f0) error(ERROR_VM_DIVISION_BY_ZERO);
              f1  /= f0; break;
            case OP_IREM: /* // DEBUGF("frem(%f,%f)", f1, f0); */
              error(ERROR_VM_UNSUPPORTED_OPCODE);
              //f1  = f1%f0; break;
          }
          _stack->stack_push(VM::getInstance()->nvm_float2stack(f1));
          // // DEBUGF(" = %f\n", stack_peek_float(0));
        }
#endif

      } else {
        tmp1 = _stack->stack_pop_int();  // fetch operands from stack
        tmp2 = _stack->stack_pop_int();

        switch(instr) {
          case OP_IADD:  // DEBUGF("iadd(%d,%d)", tmp2, tmp1);
            tmp2  += tmp1; break;
          case OP_ISUB:  // DEBUGF("isub(%d,%d)", tmp2, tmp1);
            tmp2  -= tmp1; break;
          case OP_IMUL:  // DEBUGF("imul(%d,%d)", tmp2, tmp1);
            tmp2  *= tmp1; break;
          case OP_IDIV:  // DEBUGF("idiv(%d,%d)", tmp2, tmp1);
            if(!tmp1) error(ERROR_VM_DIVISION_BY_ZERO);
            tmp2  /= tmp1; break;
          case OP_IREM:  // DEBUGF("irem(%d,%d)", tmp2, tmp1);
            tmp2  %= tmp1; break;
          case OP_ISHL:  // DEBUGF("ishl(%d,%d)", tmp2, tmp1);
            tmp2 <<= tmp1; break;
          case OP_ISHR:  // DEBUGF("ishr(%d,%d)", tmp2, tmp1);
            tmp2 >>= tmp1; break;
          case OP_IAND:  // DEBUGF("iand(%d,%d)", tmp2, tmp1);
            tmp2  &= tmp1; break;
          case OP_IOR:   // DEBUGF("ior(%d,%d)",  tmp2, tmp1);
            tmp2  |= tmp1; break;
          case OP_IXOR:  // DEBUGF("ixor(%d,%d)", tmp2, tmp1);
            tmp2  ^= tmp1; break;
          case OP_IUSHR: // DEBUGF("iushr(%d,%d)", tmp2, tmp1);
            tmp2 = ((nvm_uint_t)tmp2 >> tmp1); break;
        }

        // and finally push result
        _stack->stack_push(nvm_int2stack(tmp2));
        // DEBUGF(" = %d\n", stack_peek_int(0));
      }
    }

    else if((instr == OP_IRETURN)
#ifdef NVM_USE_FLOAT
          ||(instr == OP_FRETURN)
#endif
          ||(instr == OP_RETURN)) {
      if((instr == OP_IRETURN)
#ifdef NVM_USE_FLOAT
       ||(instr == OP_FRETURN)
#endif
      ) {
        tmp1 = _stack->stack_pop();     // save result
        // DEBUGF("i");
      }

      // DEBUGF("return: ");

      // return from locally called method? other case: return
      // from main() -> end of program
      if(!(_stack->stack_is_empty())) {
        u08_t old_locals = mhdr.max_locals;
        u08_t old_unsteal = VM_METHOD_CALL_REQUIREMENTS +
          mhdr.max_locals + mhdr.max_stack + mhdr.args;
        u16_t old_localsoffset = _stack->stack_pop();

        // make space for locals on the stack
        /* DEBUGF("Return from method with %d local(s) and %d

                " "stack elements - %d args\n",
                mhdr.max_locals, mhdr.max_stack, mhdr.args);
        */

        db<VM> (TRC) << "Return from method with " <<
               (int) mhdr.max_locals <<
                " local(s) and "<< (int) mhdr.max_stack <<
                " stack elements - " << (int) mhdr.args <<
                " args" << endl;
        mref = _stack->stack_pop();

        db<VM> (TRC) << "mref: " << hex << mref << endl;
        // read header of method to return to
        mhdr_ptr = _nvmFile->nvmfile_get_method_hdr(mref);
        // load method header into ram
        _nvmFile->nvmfile_read(&mhdr, mhdr_ptr, sizeof(NvmFile::nvm_method_hdr_t));

        // restore pc
        pc = (u08_t*)mhdr_ptr + (_stack->stack_pop());
        pc_inc = 3; // continue _behind_ calling invoke instruction

        // and remove locals from stack and hope that method left
        // an uncorrupted stack
        _stack->stack_add_sp(-old_locals);
      //  VM::getInstance()->locals(_stack->stack_get_sp() - old_localsoffset);
        _locals = _stack->stack_get_sp() - old_localsoffset;
        db<VM> (TRC) << "Devolvendo para a heap" << endl;
        // give memory used by returning method back to heap
        __heapVm->heap_unsteal(sizeof(nvm_stack_t) * old_unsteal);


        if(instr == OP_IRETURN){
          _stack->stack_push(tmp1);
          // DEBUGF("ireturn val: %d\n", stack_peek_int(0));
        }
#ifdef NVM_USE_FLOAT
        else if(instr == OP_FRETURN){
          _stack->stack_push(tmp1);
          // DEBUGF("freturn val: %f\n", stack_peek_float(0));
        }
#endif
        instr = OP_NOP;  // make vm continue
      }
    }

    // discard both top stack items
    else if(instr == OP_POP2) {
      // DEBUGF("ipop\n");
      _stack->stack_pop(); _stack->stack_pop();
    }

    // discard top stack item
    else if(instr == OP_POP) {
      // DEBUGF("pop\n");
      _stack->stack_pop();
    }

    // duplicate top stack item
    else if(instr == OP_DUP) {
      _stack->stack_push(_stack->stack_peek(0));
      // DEBUGF("dup ("DBG16")\n", stack_peek(0) & 0xffff);
    }

    // duplicate top two stack items  (a,b -> a,b,a,b)
    else if(instr == OP_DUP2) {
      _stack->stack_push(_stack->stack_peek(1));
      _stack->stack_push(_stack->stack_peek(1));
      // DEBUGF("dup2 ("DBG16","DBG16")\n", stack_peek(0) & 0xffff, stack_peek(1) & 0xffff);
    }

#ifdef NVM_USE_EXTSTACKOPS

    // duplicate top stack item and put it under the second
    else if(instr == OP_DUP_X1) {
      nvm_stack_t w1 = _stack->stack_pop();
      nvm_stack_t w2 = _stack->stack_pop();
      _stack->stack_push(w1);
      _stack->stack_push(w2);
      _stack->stack_push(w1);
      // DEBUGF("dup_x1 ("DBG16")\n", stack_peek(0) & 0xffff);
    }

    // duplicate top stack item
    else if(instr == OP_DUP_X2) {
      nvm_stack_t w1 = _stack->stack_pop();
      nvm_stack_t w2 = _stack->stack_pop();
      nvm_stack_t w3 = _stack->stack_pop();
      _stack->stack_push(w1);
      _stack->stack_push(w2);
      _stack->stack_push(w3);
      _stack->stack_push(w1);
      // DEBUGF("dup ("DBG16")\n", stack_peek(0) & 0xffff);
    }

    // duplicate top two stack items  (a,b -> a,b,a,b)
    else if(instr == OP_DUP2_X1) {
      nvm_stack_t w1 = _stack->stack_pop();
      nvm_stack_t w2 = _stack->stack_pop();
      nvm_stack_t w3 = _stack->stack_pop();
      _stack->stack_push(w1);
      _stack->stack_push(w2);
      _stack->stack_push(w3);
      _stack->stack_push(w1);
      _stack->stack_push(w2);
      // DEBUGF("dup2 ("DBG16","DBG16")\n",
             _stack->stack_peek(0) & 0xffff, _stack->stack_peek(1)
                     & 0xffff);
    }

    // duplicate top two stack items  (a,b -> a,b,a,b)
    else if(instr == OP_DUP2_X2) {
      nvm_stack_t w1 = _stack->stack_pop();
      nvm_stack_t w2 = _stack->stack_pop();
      nvm_stack_t w3 = _stack->stack_pop();
      nvm_stack_t w4 = _stack->stack_pop();
      _stack->stack_push(w1);
      _stack->stack_push(w2);
      stack_push(w3);
      stack_push(w4);
      stack_push(w1);
      stack_push(w2);
      // DEBUGF("dup2 ("DBG16","DBG16")\n",
             stack_peek(0) & 0xffff, stack_peek(1) & 0xffff);
    }

    // swap top two stack items  (a,b -> b,a)
    else if(instr == OP_SWAP) {
      nvm_stack_t w1 = stack_pop();
      nvm_stack_t w2 = stack_pop();
      stack_push(w1);
      stack_push(w2);
      // DEBUGF("swap ("DBG16","DBG16")\n", stack_peek(0), stack_peek(1));
    }

#endif


#ifdef NVM_USE_TABLESWITCH
    else if(instr == OP_TABLESWITCH) {
      // DEBUGF("TABLESWITCH\n");
      // padding was eliminated by generator
      tmp1 = ((nvmfile_read08(pc+7)<<8) |
              nvmfile_read08(pc+8));        // get low value
      tmp2 = ((nvmfile_read08(pc+11)<<8) |
              nvmfile_read08(pc+12));       // get high value
      arg0.tmp = _stack->stack_pop();               // get actual value
      // DEBUGF("tableswitch %d-%d (%d)\n", tmp1, tmp2, arg0.w);

      // value within range?
      if((arg0.tmp < tmp1)||(arg0.tmp > tmp2))
        // no: use default
        tmp2 = 3;
      else
        // yes: get offset from table
        tmp2 = 3 + 12 + ((arg0.tmp - tmp1)<<2);

      // and do the jump
      pc += ((nvmfile_read08(pc+tmp2+0)<<8) |
             nvmfile_read08(pc+tmp2+1));
      pc_inc = 0;
    }
#endif

#ifdef NVM_USE_LOOKUPSWITCH
    else if(instr == OP_LOOKUPSWITCH) {
      u08_t size;

      // DEBUGF("LOOKUPSWITCH\n");
      // padding was eliminated by generator

      arg0.tmp = 1 + 4;
      size = nvmfile_read08(pc+arg0.tmp+3); // get table size (max for nvm is 30 cases!)
      // DEBUGF("  size: %d\n", size);
      arg0.tmp += 4;

      tmp1 = _stack->stack_pop_int();                        // get actual value
      // DEBUGF("  val=: %d\n", tmp1);

      while(size)
      {
        if (
#ifdef NVM_USE_32BIT_WORD
             nvmfile_read08(pc+arg0.tmp+0)==(u08_t)(tmp1>>24) &&
             nvmfile_read08(pc+arg0.tmp+1)==(u08_t)(tmp1>>16) &&
#endif
             nvmfile_read08(pc+arg0.tmp+2)==(u08_t)(tmp1>>8) &&
             nvmfile_read08(pc+arg0.tmp+3)==(u08_t)(tmp1>>0)
           )
        {
          // DEBUGF("  value found, index is %d\n", (int)(arg0.tmp-pc_inc-8)/8);
          arg0.tmp+=4;
          break;
        }
        arg0.tmp+=8;
        size--;
      }

      if (size==0)
      {
        // DEBUGF("  not found, using default!\n");
        arg0.tmp = 1;
      }
      pc += ((nvmfile_read08(pc+arg0.tmp+2)<<8) |
             nvmfile_read08(pc+arg0.tmp+3));
      pc_inc = 0;
    }
#endif

    // get static field from class
    else if(instr == OP_GETSTATIC) {
      pc_inc = 3;   // prefetched data used
      db<VM> (TRC)  << "getstatic " << arg0.w << endl;
      _stack->stack_push(_stack->stack_get_static(arg0.w));
    }

    else if(instr == OP_PUTSTATIC) {
      pc_inc = 3;
      _stack->stack_set_static(arg0.w, _stack->stack_pop());
      db<VM> (TRC) << "putstatic " << arg0.w << " -> " <<  _stack->stack_get_static(arg0.w) << endl;
    }

    // push item from constant pool
    else if(instr == OP_LDC) {
      pc_inc = 2;
      db<VM> (TRC) << "ldc " <<  arg0.z.bh << endl;
#ifdef NVM_USE_32BIT_WORD
      _stack->stack_push(_nvmFile->nvmfile_get_constant(arg0.z.bh));
#else
      _stack->stack_push(NVM_TYPE_CONST | (arg0.z.bh-nvmfile_constant_count));
#endif
    }

    else if((instr >= OP_INVOKEVIRTUAL)&&(instr <= OP_INVOKESTATIC)) {
      // DEBUGF("invoke");

#ifdef DEBUG
      if(instr == OP_INVOKEVIRTUAL) { /* DEBUGF("virtual"); */ }
      if(instr == OP_INVOKESPECIAL) { /* DEBUGF("special"); */ }
      if(instr == OP_INVOKESTATIC)  { /* DEBUGF("static"); */ }
#endif

      // DEBUGF(" #"DBG16"\n", 0xffff & arg0.w);

      // invoke a method. check if it's local (within the nvm file)
      // or native (implemented by the runtime environment)
      if(arg0.z.bh < NATIVE_CLASS_BASE) {
        db<VM> (TRC) << "local method call from method " <<
                (int) mref << " to " <<
                (int) arg0.w << endl;

        // save current pc (relative to method start)
        tmp1 = (u08_t*)pc-(u08_t*)mhdr_ptr;

        // get pointer to new method
        mhdr_ptr = _nvmFile->nvmfile_get_method_hdr(arg0.w);

        // load new method header into ram
        _nvmFile->nvmfile_read(&mhdr, mhdr_ptr, sizeof(NvmFile::nvm_method_hdr_t));

/*
#ifdef NVM_USE_INHERITANCE
        // check class on stack. it may be not the one we expect.
        // this happens due to inheritance
        if(instr == OP_INVOKEVIRTUAL) {
          nvm_ref_t mref;

           db<VM>(TRC) << "checking inheritance\n";

          // fetch class reference from stack and use it to address
          // the class instance on the heap. The first entry in this
          // object is the class id of it
          mref = ((nvm_ref_t*)__heapVm->
                  heap_get_addr(
                          _stack->stack_peek(0)ja   e
                          & ~NVM_TYPE_MASK))[0];
           db<VM>(TRC) << "class ref on stack/ref: " << hex <<
                 NATIVE_ID2CLASS(mref) << "/" <<
                 NATIVE_ID2CLASS(mhdr.id) << endl;
              //\n", NATIVE_ID2CLASS(mref), NATIVE_ID2CLASS(mhdr.id));

          if(NATIVE_ID2CLASS(mref) != ((unsigned) NATIVE_ID2CLASS(mhdr.id))) {
             db<VM>(ERR) << "stack/ref class mismatch -> inheritance\n";
            // get matching method in class on stack or its
            // super classes
            arg0.z.bl = _nvmFile->nvmfile_get_method_by_class_and_id(
              NATIVE_ID2CLASS(mref), NATIVE_ID2METHOD(mhdr.id));

            // get pointer to new method
            mhdr_ptr = _nvmFile->nvmfile_get_method_hdr(arg0.z.bl);

            // load new method header into ram
            _nvmFile->nvmfile_read(&mhdr, mhdr_ptr, sizeof(NvmFile::nvm_method_hdr_t));
          }
        }
#endif
*/
        // arguments are left on the stack by the calling
        // method and expected in the locals by the called
        // method. Thus we make this part of the old stack
        // be the locals part of the method
        // DEBUGF("Remove %d args from stack\n", mhdr.args);
        _stack->stack_add_sp(-mhdr.args);

        tmp2 = _stack->stack_get_sp() - _locals;//VM::getInstance()->locals();

        //VM::getInstance()->locals(_stack->stack_get_sp() + 1);
        _locals = _stack->stack_get_sp() +1 ;
//#ifdef DEBUG
        if(instr == OP_INVOKEVIRTUAL) {
           db<VM>(TRC) << "virtual call with object reference " << hex <<
                       _locals[0] << endl;
        }
//#endif

        // make space for locals on the stack
        // DEBUGF("Allocating space for
        //%d local(s) and %d " "stack elements - %d args\n", 		   mhdr.max_locals, mhdr.max_stack, mhdr.args);

        // increase stack space. locals will be put on the stack as
        // well. method arguments are part of the locals and are
        // already on the stack
        __heapVm->heap_steal(sizeof(nvm_stack_t) *
                   (VM_METHOD_CALL_REQUIREMENTS +
                    mhdr.max_locals + mhdr.max_stack + mhdr.args));

        // add space for locals on stack
        _stack->stack_add_sp(mhdr.max_locals);

        // push everything required to return onto the stack
        _stack->stack_push(tmp1);   // pc offset
        _stack->stack_push(mref);   // method reference
        _stack->stack_push(tmp2);   // locals offset

        // set new pc (this is the actual call)
        mref = arg0.w;
        pc = (u08_t*)mhdr_ptr + mhdr.code_index;
        pc_inc = 0;  // don't add further bytes to program counter
      } else {
          db<VM>(TRC) << "Native invoke. PC ->" <<
                  pc + 3 << endl;
        native_invoke(arg0.w, _stack);
        pc_inc = 3;   // prefetched data used
      }
    }

    else if(instr == OP_GETFIELD) {
      pc_inc = 3;
      db<VM> (TRC) << "getfield ->  " << (int) arg0.w << endl;
      unsigned int sp = (unsigned int)_stack->stack_get_sp();
      unsigned int value = _stack->stack_pop();
      nvm_word_t * addr = (nvm_word_t *) __heapVm->heap_get_addr(value & ~NVM_TYPE_MASK);
      db<VM> (TRC) << "Field value -> " << hex << value << endl <<
                    "stack sp: " << hex <<  sp <<endl   ;
      db<VM> (TRC) << "Addres of obj ->" << hex << addr[VM_CLASS_CONST_ALLOC + arg0.w] << endl;
      _stack->stack_push(addr[VM_CLASS_CONST_ALLOC + arg0.w]);
    //  Alarm::delay(5000000);
    /*  _stack->stack_push(((nvm_word_t*)__heapVm->
                          heap_get_addr(_stack->st'ack_pop()
                                        & ~NVM_TYPE_MASK))
              [VM_CLASS_CONST_ALLOC+arg0.w]);
    */
}

    else if(instr == OP_PUTFIELD) {
      pc_inc = 3;
      tmp1 = _stack->stack_pop();
      nvm_word_t obj = _stack->stack_pop();
      nvm_word_t* objRef = ((nvm_word_t*)__heapVm->heap_get_addr(obj & ~NVM_TYPE_MASK));
                                                   //   [VM_CLASS_CONST_ALLOC+arg0.w];

      db<VM> (TRC) << "Putfield: " << hex << (int)arg0.w << endl;
      db<VM> (TRC) << "Value: " << hex << (unsigned int) tmp1 << endl;
      db<VM> (TRC) << "Obj ref -> " << hex << obj << endl;

      //original
     // ((nvm_word_t*)__heapVm->heap_get_addr(
       //       _stack->stack_pop() & ~NVM_TYPE_MASK))
      //  [VM_CLASS_CONST_ALLOC+arg0.w] = tmp1;
      objRef[VM_CLASS_CONST_ALLOC + arg0.w] = tmp1;

//       Alarm::delay(5000000);
    }

    else if(instr == OP_NEW) {
      pc_inc = 3;
      //long temp2 = 0xffff & arg0.w;
      //int arg = arg0.w;
      //db<VM>(TRC) << "arg: " << arg << endl;
      //db<VM>(TRC) << "new #" << temp2<< endl;//DBG16"\n", 0xffff & arg0.w);
      vm_new(arg0.w);
    //  vm_new(gambArg16);
    }

#ifdef NVM_USE_ARRAY
    else if(instr == OP_NEWARRAY) {
      pc_inc = 2;
      _stack->stack_push(
              array_new(
                      _stack->stack_pop(),
                      arg0.z.bh) | NVM_TYPE_HEAP);
    }

    else if(instr == OP_ARRAYLENGTH) {
      _stack->stack_push(
              array_length(
                      _stack->stack_pop()
                      & ~NVM_TYPE_MASK));
    }

    else if(instr == OP_BASTORE) {
      tmp2 = _stack->stack_pop_int();       // value
      tmp1 = _stack->stack_pop_int();         // index
      // third parm on stack: array reference
      array_bastore(_stack->stack_pop() & ~NVM_TYPE_MASK, tmp1, tmp2);
    }

    else if(instr == OP_IASTORE) {
      tmp2 = _stack->stack_pop_int();       // value
      tmp1 = _stack->stack_pop_int();       // index
      // third parm on stack: array reference
      array_iastore(_stack->stack_pop() & ~NVM_TYPE_MASK, tmp1, tmp2);
    }

    else if(instr == OP_BALOAD) {
      tmp1 = _stack->stack_pop_int();       // index
      // second parm on stack: array reference
      _stack->stack_push(
              array_baload(
                      _stack->stack_pop() & ~NVM_TYPE_MASK, tmp1));
    }

    else if(instr == OP_IALOAD) {
      tmp1 = _stack->stack_pop_int();       // index
      // second parm on stack: array reference
      _stack->stack_push(
              array_iaload(
                      _stack->stack_pop()
                      & ~NVM_TYPE_MASK, tmp1));
    }
#endif

#ifdef NVM_USE_OBJ_ARRAY
    else if(instr == OP_ANEWARRAY) {
      // Object array is the same as int array...
      pc_inc = 3;
      stack_push(array_new(stack_pop(), T_INT) | NVM_TYPE_HEAP);
    }

    else if(instr == OP_AASTORE) {
      tmp2 = stack_pop_int();       // value
      tmp1 = stack_pop_int();       // index
      // third parm on stack: array reference
      array_iastore(stack_pop(), tmp1, tmp2);
    }

    else if(instr == OP_AALOAD) {
      tmp1 = stack_pop_int();       // index
      // second parm on stack: array reference
      stack_push(array_iaload(stack_pop(), tmp1));
    }
#endif

#ifdef NVM_USE_FLOAT
# ifdef NVM_USE_ARRAY
    else if(instr == OP_FALOAD) {
      tmp1 = _stack->stack_pop_int();       // index
      // second parm on stack: array reference
      _stack->stack_push(
              array_faload(
                      _stack->stack_pop()
                      & ~NVM_TYPE_MASK, tmp1));
    }
    else if(instr == OP_FASTORE) {
      f0 = _stack->stack_pop_float();       // value
      tmp1 = _stack->stack_pop_int();         // index
      // third parm on stack: array reference
      array_fastore(_stack->stack_pop()
                    & ~NVM_TYPE_MASK, tmp1, f0);
    }
# endif

    else if(instr == OP_FCONST_0) {
      _stack->stack_push(nvm_float2stack(0.0));
      // DEBUGF("fconst_%d\n", stack_peek_float(0));
    }
    else if(instr == OP_FCONST_1) {
      _stack->stack_push(nvm_float2stack(1.0));
      // DEBUGF("fconst_%d\n", stack_peek_float(0));
    }
    else if(instr == OP_FCONST_2) {
      _stack->stack_push(nvm_float2stack(2.0));
      // DEBUGF("fconst_%d\n", stack_peek_float(0));
    }
    else if(instr == OP_I2F) {
      tmp1 = _stack->stack_pop_int();
      _stack->stack_push(nvm_float2stack(tmp1));
      // DEBUGF("i2f %f\n", stack_peek_float(0));
    }
    else if(instr == OP_F2I) {
      tmp1 = _stack->stack_pop_float();
      _stack->stack_push(nvm_int2stack(tmp1));
      // DEBUGF("i2f %f\n", stack_peek_int(0));
    }

    // move float from stack into locals
    else if(instr == OP_FSTORE) {
      VM::getInstance()->local(arg0.z.bh, _stack->stack_pop());
      pc_inc = 2;
      // DEBUGF("fstore %d (%f)\n", arg0.z.bh, nvm_stack2float(locals[arg0.z.bh]));
    }

    // move integer from stack into locals
    else if((instr >= OP_FSTORE_0) && (instr <= OP_FSTORE_3)) {
      VM::getInstance()->local(instr - OP_FSTORE_0,
                               _stack->stack_pop());
      // DEBUGF("fstore_%d (%f)\n", instr - OP_FSTORE_0, nvm_stack2float(locals[instr - OP_FSTORE_0]));
    }

    // load float from local variable (push local var)
    else if(instr == OP_FLOAD) {
      _stack->stack_push(VM::getInstance()->local(arg0.z.bh));
      pc_inc = 2;
      // DEBUGF("fload %d (%f, "DBG16")\n", locals[arg0.z.bh], stack_peek_float(0), stack_peek_int(0));
    }

    // push local onto stack
    else if((instr >= OP_FLOAD_0) && (instr <= OP_FLOAD_3)) {
      _stack->stack_push(VM::getInstance()->local(instr - OP_FLOAD_0));
      // DEBUGF("fload_%d (%f, "DBG16")\n", instr-OP_FLOAD_0, stack_peek_float(0), stack_peek_int(0));
    }

    // compare top values on stack
    else if((instr == OP_FCMPL) || (instr == OP_FCMPG)) {
      f1 = _stack->stack_pop_float();
      f0 = _stack->stack_pop_float();
      tmp1=0;
      if (f0<f1)
        tmp1=-1;
      else if (f0>f1)
        tmp1=1;
      _stack->stack_push(nvm_int2stack(tmp1));
      // DEBUGF("fcmp%c (%f, %f, %i)\n", (instr==OP_FCMPL)?'l':'g', f0, f1, stack_peek_int(0));
    }
#endif

    else {
      error(ERROR_VM_UNSUPPORTED_OPCODE);
    }

    // reset watchdog here if present

    db<VM>(TRC) << (u08_t*)_stack->stack_get_sp() -(u08_t*)
                   _stack->sp_saved << " bytes on stack\n";

    db<VM> (TRC) << "SP->" << _stack->stack_get_sp() << endl;

    pc += pc_inc;
  } while((instr != OP_IRETURN)&&(instr != OP_RETURN));

  db<VM>(INF) << "and remove locals from stack and hope that method left " <<
                 "  an uncorrupted stack" << endl;
  _stack->stack_add_sp(-mhdr.max_locals);

  db<VM>(INF) << (u08_t*)_stack->stack_get_sp() -(u08_t*)
                 _stack->sp_saved << " bytes on stack\n";

#ifdef NVM_USE_STACK_CHECK
  _stack->stack_verify_sp();
#endif

  // give memory back to heap
  __heapVm->heap_unsteal(sizeof(nvm_stack_t) * (mhdr.max_locals + mhdr.max_stack + mhdr.args));
}


__END_SYS
