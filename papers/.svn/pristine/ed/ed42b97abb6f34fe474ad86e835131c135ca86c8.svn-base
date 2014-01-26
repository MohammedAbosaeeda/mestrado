%   Federal University of Santa Catarina        %
%   Software/Hardware Integration Lab           %
%                                               %
%   SolarEPOSMote Data Processing               %
%                                               %
%   Student: Leonardo Kessler Slongo            %
%   lkslongo@gmail.com                          %
%                                               %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

clear all
close all
clc

A = xlsread('original.xlsx');           % Open original data file  
A(isnan(A))=0;                          % Convert not-a-number to zero
M = A;                                  % Rename matrix to M

% Convert time from seconds to hours
for j=1:208865
    
  M(j,1) = M(j,1)./3600;
end

% Plot the instantaneous battery voltage
figure (1);
plot(M(:,1),M(:,2));
xlabel('Time [Hours]','FontSize',14);
ylabel('Voltage [V]','FontSize',14);
title('Battery Voltage','FontSize',16);
hold on;

% Plot the instantaneous battery current
figure (2);
plot(M(:,1),M(:,3));
xlabel('Time [Hours]','FontSize',14);
ylabel('Current [A]','FontSize',14);
title('Battery Current','FontSize',16);
hold on;