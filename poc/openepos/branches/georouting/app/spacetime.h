#ifndef __packets_h
#define __packets_h

#include "packet.h"

class Interest : public Packet{
private:
    char scale;                 //Protocol packet scale (specifies how many bits are used for each field))  
    float x1,y1,z1; char t1;    //Interest spacetime region
    float x2,y2,z2; char t2;    
    char variable;              //m/s^2, %RH, etc.
    char precision;             //sensor precision
    char frequency;             //sensor minimum update frequency             
    float sink_x,sink_y,sink_z; //sink location
public:
    Interest();
    ~Interest();       
    char* getBytes();           //translates the object into a byte vector
};

class Response : public Interest {
private:
    float data;
    float sensor_x,sensor_y,sensor_z;
public:
    Response();
    ~Response();
    char* getBytes();           //translates the object into a byte vector
};

class SpaceTimeManager {
    
};

#endif