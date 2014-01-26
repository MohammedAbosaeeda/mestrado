int Tn(int n, RTC::Microsecond wcet) {
   RTC::Microsecond now,  miliexec;
   int activations = 0;

   while (1) {
      now =  Alarm::elapsed();

      // waste time in CPU
      miliexec = 0;
      do {
         while (now == Alarm::elapsed());
         miliexec++;
         now = Alarm::elapsed();
      } while (miliexec < wcet);

      activations++;
      Periodic_Thread::wait_next();
   }
   return 0;
}