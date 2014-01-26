   typedef void (Dispatcher)(Message *);
   Dispatcher* services[LAST_TYPE_ID + 1][MAX_METHODS] = {
       { &Agent<Component 1>::create,
         &Agent<Component 1>:::destroy,
         &Agent<Component 1>::method1,
         &Agent<Component 1>::method2,
         // ...
         &Agent<Component 1>::update },
       { &Agent<Component 2>::create,
         &Agent<Component 2>::destroy,
         &Agent<Component 2>::method1,
         &Agent<Component 2>::method2,
         // ...
         &Agent<Component 2>::update, }
        // ...
   };
   static Semaphore quiescent_state[LAST_TYPE_ID + 1];
   void OS_Box(Message *msg) {
       Dispatcher *d;
       d = *services[msg->id().type()][msg->method()];
       quiescent_state[msg->id().type()].p();
       d(msg); // call a method
       quiescent_state[msg->id().type()].v();
   }