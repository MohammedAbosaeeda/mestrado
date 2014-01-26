procedure update_ebudget()
    Update E_volt from battery voltage model;

    //for all system components
    for i := 0 up to #components
        //Equation 3
        E_batt -= component[i].energy_total();
    end for;

    E_batt = max(E_volt, E_batt);

    //for all HRTT
    for i := 0 up to #tasks
        E_budget -= task[i].activations * task[i].wcec;
    end for;

    Update has_energy = (E_batt >= E_budget);
