%   Federal University of Santa Catarina                                    %
%   Software/Hardware Integration Lab                                       %
%                                                                           %
%   SolarEPOSMote Data Processing                                           %
%                                                                           %
%   This code processes the colected data (battery current and voltage)     %
%   from the SolarEPOSMote circuit. The goal is allowing the analisys       %     
%   of solar irradiance influence (which is given in averages of 5 min)     %   
%   in the energy colected by the solar panel.                              %                                  
%                                                                           %
%   Data colected 03/16/2012                                                %
%   Test start time: 21:15h                                                 %
%   Test duration: 3765min (62h45min)                                       %
%                                                                           %
%   Student: Leonardo Kessler Slongo                                        %
%   lkslongo@gmail.com                                                      %
%                                                                           %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

clear all
close all
clc

ORIG_SHIFTED = xlsread('original_shifted.xlsx');    % Open data file with already shifted time
ORIG_SHIFTED(isnan(ORIG_SHIFTED)) = 0;              % Convert not-a-number to zero
DATA = ORIG_SHIFTED;                                % Rename matrix to DATA
IRRAD = xlsread('irradiance');                      % Open irradiance file (points representing average of 5 min)

k = 1;                                              % Initialize indexer
current_sum = 0;                                    % Initialize variable for current's sum over a period of 5 min
voltage_sum = 0;                                    % Initialize variable for voltage's sum over a period of 5 min
old_time_avg_init = 0;                              % Initialize variable which sotores last value of time average initial 
time_avg = 1131;                                    % Set the first 5 min limit to the sum (test starts in time 831s)

for line=1:208865                                   % Scan all matrix lines
  
  DATA(line,3) = DATA(line,3)+0.056;                % This sum is done in order to subtract the current consumed by the system (which consumes 0.056 mA)
                                                    % It is a sum since the current consumed has negative values 
    if (DATA(line,1) >= time_avg)                   % Compare the time stamp with the 5 min limit
        
        time_avg = time_avg+300;                    % Increment the variable time_avg of min in order to get a new 5 min limit
        
        time_avg_init = old_time_avg_init+1;                        % Set an initial time for summing current and voltage  
        time_avg_end = line;                                        % Set a final time for summing current and voltage
        
        for line_index = time_avg_init:time_avg_end                 % Current and voltage sum over the 5 min period
            current_sum = DATA(line_index,3)+current_sum;
            voltage_sum = DATA(line_index,2)+voltage_sum;
        end
      
        CURRENT_AVG(k) = current_sum/(line-old_time_avg_init);      % Current and voltage average calculation
        VOLTAGE_AVG(k) = voltage_sum/(line-old_time_avg_init);
        
        current_sum = 0;                                            % Clear current sum variable
        voltage_sum = 0;                                            % Clear voltage sum variable
        
        k = k+1;                                                    % Increment of time index
        old_time_avg_init = line;                                   % Stores the final limit of the last 5 min period
    end
       
end

time = [1:1:k-1];               % Generate a time vector based on the incrementation of the loop above (one incrementatio every 5 min)
time = (time'*5)./60;           % Convert the time vector to hours

CURRENT_AVG = CURRENT_AVG'.*1000;
VOLTAGE_AVG = VOLTAGE_AVG';

% Plot the relation between delivered current and solar irradiation
figure (1);                                     
plot(CURRENT_AVG,IRRAD(:,2),'*','MarkerSize', 12);
xlabel('Current [mA]','FontSize',26);
ylabel('Solar Irradiance [W/m^2]','FontSize',26);
title('Solar Current and Irradiation Relation','FontSize',30);
set(gca,'FontSize',26);
hold on;

% Plot the temporal evolution of solar irradiation
figure (2);                       
plot(time,IRRAD(:,2),'*','MarkerSize', 12);
xlabel('Time [h]','FontSize',26);
ylabel('Solar Irradiance [W/m^2]','FontSize',26);
title('Solar Irradiance','FontSize',30);
set(gca,'FontSize',26);

% Plot the temporal evolution of delivered current (5 min average)
figure (3);                      
plot(time,CURRENT_AVG,'*','MarkerSize', 12);
xlabel('Time [h]','FontSize',26);
ylabel('Current [mA]','FontSize',26);
title('Current Delivered by the Solar Panel','FontSize',30);
set(gca,'FontSize',26);

% Plot the temporal evolution of battery voltage (5 min average) 
figure (4);                     
plot(time,VOLTAGE_AVG,'*','MarkerSize', 12);
xlabel('Time [h]','FontSize',26);
ylabel('Voltage [V]','FontSize',26);
title('Battery Voltage','FontSize',30);
set(gca,'FontSize',26);

% Rename variable for 3D plot
x=time;                         
y=CURRENT_AVG;
z=IRRAD(:,3);

% 3D plot of temperature dependence of delivered current (saccater format)
figure (5);
plot3(x,y,z,'.-');              

% Surface generation
tri = delaunay(x,y);            
plot(x,y,'.');
[r,c] = size(tri);
h = trisurf(tri, x, y, z);

% 3D plot settings
axis vis3d;                     
xlabel('Time [h]','FontSize',14);
ylabel('Current [mA]','FontSize',14);
zlabel(['Temperature[' 176 'C]'],'FontSize',14);
title('Temperature Dependence of Delivered Current','FontSize',16);
set(gca,'FontSize',14);
lighting phong;
shading interp;
colorbar EastOutside;
colorbar ('FontSize',12,'FontWeight','bold','YTickLabel',{['20[' 176 'C]'],['25[' 176 'C]'],['30[' 176 'C]'],['35[' 176 'C]']});

% Plot the temporal evolution of temperature (5 min average) 
figure (6);                     
plot(time,z,'*','MarkerSize', 12);
xlabel('Time [h]','FontSize',26);
ylabel('Voltage [V]','FontSize',26);
title('Environmental Temperature','FontSize',30);
set(gca,'FontSize',26);

% Concatenate all relevant information to a output matrix and generate a string vector for data output label
DATA_OUT = horzcat(time,CURRENT_AVG,VOLTAGE_AVG,IRRAD(:,2),IRRAD(:,3));                     
STRINGS = {'Time[min]' 'Current[mA]' 'Voltage[V]' 'Irradiance[W/m^2]' 'Temperature[C]'};     

% Save file with relevant information (time, current, voltage, irradiance and temperature)
fid = fopen('data_out.csv','w');                                                                        
fprintf(fid,'%s,',STRINGS{1:4});  
fprintf(fid,'%s\n',STRINGS{5});     
fclose(fid);                                
dlmwrite('data_out.csv',DATA_OUT,'-append');

x0 = 0;
y0 = 0;

x = CURRENT_AVG;
y = IRRAD(:,2);

x = x(:); %reshape the data into a column vector
y = y(:);

% 'C' is the Vandermonde matrix for 'x'
n = 1; % Degree of polynomial to fit
V(:,n+1) = ones(length(x),1,class(x));
for j = n:-1:1
V(:,j) = x.*V(:,j+1);
end
C = V;

% 'd' is the vector of target values, 'y'.
d = y;

%%
% There are no inequality constraints in this case, i.e., 
A = [];
b = [];

%%
% We use linear equality constraints to force the curve to hit the required point. In
% this case, 'Aeq' is the Vandermoonde matrix for 'x0'
Aeq = x0.^(n:-1:0);
% and 'beq' is the value the curve should take at that point
beq = y0;

%% 
p = lsqlin( C, d, A, b, Aeq, beq )

%%
% We can then use POLYVAL to evaluate the fitted curve
yhat = polyval( p, x );

%%
% Plot original data
figure (7);
plot(x,y,'o','MarkerSize',8,'LineWidth',2)
xlabel('Current [mA]','FontSize',26);
ylabel('Solar Irradiance [W/m^2]','FontSize',26);
title('Solar Current and Irradiation Relation','FontSize',30);
set(gca,'FontSize',26);
hold on
% Plot point to go through
plot(x0,y0,'.b','MarkerSize', 20)
% Plot fitted data
plot(x,yhat,'-r','LineWidth',2) 
hold off





