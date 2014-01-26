t_teste = toc

V_teste = V_med / n;
I_teste = I_med / n;

E_m = V_teste * I_teste * t_teste

% Disconnect device object from hardware.
disconnect(deviceObj);

% Delete objects.
delete([deviceObj interfaceObj]);