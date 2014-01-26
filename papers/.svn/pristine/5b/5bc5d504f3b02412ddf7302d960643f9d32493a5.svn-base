int main()  {
    cout << "Main will create the periodic threads...\n";

    Periodic_Thread *t1, *t2, *t3;
    t3 = new PeriodicThread(&Tn, 3, 60e3, 
                                    SchedulingCriteria(300e3,300e3,10)); 
    t2 = new PeriodicThread(&Tn, 2, 40e3, 
                                    SchedulingCriteria(200e3,200e3,10));
    t1 = new PeriodicThread(&Tn, 1, 20e3, 
                                    SchedulingCriteria(100e3,100e3,10));

    cout << "Main will wait for periodic threads to finish...\n";
    int status1 = t1->join();
    int status2 = t2->join();
    int status3 = t3->join();

    cout << "Main will destroy periodic threads...\n";
    delete t1; delete t2; delete t3;

    cout << "Main will finish...\n";
    return 0;
}