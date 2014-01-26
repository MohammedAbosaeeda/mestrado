%TDS M-Code for communicating with an instrument. 
%  
%   This is the machine generated representation of an instrument control 
%   session using a device object. The instrument control session comprises  
%   all the steps you are likely to take when communicating with your  
%   instrument. These steps are:
%       
%       1. Create a device object   
%       2. Connect to the instrument 
%       3. Configure properties 
%       4. Invoke functions 
%       5. Disconnect from the instrument 
%  
%   To run the instrument control session, type the name of the M-file,
%   tds, at the MATLAB command prompt.
% 
%   The M-file, TDS must be on your MATLAB PATH. For additional information
%   on setting your MATLAB PATH, type 'help addpath' at the MATLAB command
%   prompt.
%
%   Example:
%       tds;
%
%   See also ICDEVICE.
%

%   Creation time: 03-Jun-2008 12:05:50 


% Create a VISA-USB object.
interfaceObj = instrfind('Type', 'visa-usb', 'RsrcName', 'USB0::0x0699::0x036A::C036919::0::INSTR', 'Tag', '');

% Create the VISA-USB object if it does not exist
% otherwise use the object that was found.
if isempty(interfaceObj)
    interfaceObj = visa('tek', 'USB0::0x0699::0x036A::C036919::0::INSTR');
else
    fclose(interfaceObj);
    interfaceObj = interfaceObj(1);
end

% Create a device object. 
deviceObj = icdevice('tektronix_tds2024.mdd', interfaceObj);

% Connect device object to hardware.
connect(deviceObj);

% Execute device object function(s).
groupObj = get(deviceObj, 'Waveform');
groupObj = groupObj(1);

n = 0;
V_med = 0;
I_med = 0;
tic

while 1 %n < n_medidas
    
    % [y,x] - y - Tensao | Corrente | Potencia
    %         x - Tempo
    [Vm, t_v] = invoke(groupObj, 'readwaveform', 'channel1');
    [Vrm, t_i] = invoke(groupObj, 'readwaveform', 'channel2');
    
    V_med = V_med + mean(Vm);
    
    I_med = I_med + mean(Vm-Vrm);
    
    n = n+1;
    toc
    
end

% t_teste = toc
% 
% V_teste = V_med / n;
% I_teste = I_med / n;
% 
% E_m = V_teste * I_teste * t_teste
% 
% % Disconnect device object from hardware.
% disconnect(deviceObj);
% 
% % Delete objects.
% delete([deviceObj interfaceObj]);
