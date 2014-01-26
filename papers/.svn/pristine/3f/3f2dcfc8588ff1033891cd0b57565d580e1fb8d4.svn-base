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

k = 1;                                              % Initialize indexer
current_sum = 0;                                    % Initialize variable for current's sum over a period of 1 min
old_time_avg_init = 0;                              % Initialize variable which sotores last value of time average initial 
time_avg = 891;                                     % Set the first 1 min limit to the sum (test starts in time 831s)

for line=1:208865                                   % Scan all matrix lines
  

    if (DATA(line,1) >= time_avg)                                   % Compare the time stamp with the 1 min limit
        
        time_avg = time_avg+60;                                     % Increment the variable time_avg of min in order to get a new 1 min limit
        
        time_avg_init = old_time_avg_init+1;                        % Set an initial time for summing current 
        time_avg_end = line;                                        % Set a final time for summing current 
        
        for line_index = time_avg_init:time_avg_end                 % Current and sum over the 1 min period
            current_sum = DATA(line_index,3)+current_sum;
        end
      
        ENERGY(k) = current_sum*1.0828414325*1000/3600;             % ENERGY calculation
               
%        current_sum = DATA(line_index,3);                          % Clear current sum variable
                
        k = k+1;                                                    % Increment of time index
        old_time_avg_init = line;                                   % Stores the final limit of the last 5 min period
    end
       
end

time = [1:1:k-1];               % Generate a time vector based on the incrementation of the loop above (one incrementatio every 5 min)
time = (time')./60;             % Convert the time vector to hours

%ENERGY = ENERGY'.*1000;

% Plot the energy evolution over time
figure (2);                       
plot(time,ENERGY,'*','MarkerSize', 12);
xlabel('Time [h]','FontSize',26);
ylabel('Energy [mAh]','FontSize',26);
title('Energy Evolution','FontSize',30);
set(gca,'FontSize',26);
hold on;






