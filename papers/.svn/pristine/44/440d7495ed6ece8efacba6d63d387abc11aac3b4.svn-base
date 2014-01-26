procedure energy_migration_update()
    now = SystemTime();
    Battery -= (now - time_begin) * I[mode];  // (1)
    time_begin = now;
end procedure;

procedure energy_event_update(event)
    Battery -= E[event] * C[event];  // (2)
    C[event] = 0;
end procedure;

procedure energy_update_total()
    for each device do
        for each event of device do
            energy_event_update(event);  // (3)
        energy_migration_update();
    end for;
    Battery_Charge = from battery voltage model;
    Battery = max(Battery_Voltage, Battery);  // (4)
end procedure;
