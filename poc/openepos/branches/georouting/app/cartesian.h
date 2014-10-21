/* 
 * File:   cartesian.h
 * Author: sergio
 *
 * Created on May 29, 2014, 5:47 PM
 */

#ifndef CARTESIAN_H
#define	CARTESIAN_H

#include <utility/math.h>

namespace cartesian { 
    //constants
    const char SPHERE = 0x01;
        
    class Point{
    public:
        Point();
        Point(float x,float y,float z);
        float x();
        void  x(float x);
        float y();
        void  y(float y);
        float z();
        void  z(float z);
        friend OStream & operator << (OStream& cout, const Point& a) {
            cout<<a._x<<","<<a._y<<","<<a._z;
            return cout;
        }
    private:
        float _x,_y,_z;
    };

    class Region{
    public:
        
        virtual Point center()=0;
        virtual bool contains(Point p)=0;
        virtual char* toByteArray(char scale)=0;
        virtual void fromByteArray(char* bytes)=0;
    };

    class Sphere : Region{
    public:
        Sphere();
        Sphere(Point center,float radius);
        Sphere(char* bytes,char size);
        bool contains(Point p);
        Point center();
        void center(Point c);
        char* toByteArray(char scale);
        void fromByteArray(char* bytes);
        float radius();
        void radius(float r);
        friend OStream & operator << (OStream& cout, const Sphere& s) {
            cout<<"\nCenter: "<<s._center;
            cout<<"\nRadius: "<<s._radius;
            return cout;
        }
    private:
        Point _center;
        float _radius;
    };    
    
    //Global methods
    float distanceSquared(Point p1, Point p2);
    float distance(Point p1,Point p2);
    Point make_point(float x,float y,float z);
   
    
    //Classes' Implementation: should be in .cc file but it didnt work with this makefile
    Point::Point(){}
    Point::Point(float x,float y,float z) : _x(x),_y(y),_z(z)
        {}
    
    float Point::x()        { return _x;}
    void  Point::x(float x) { _x=x;     }
    float Point::y()        { return _y;}
    void  Point::y(float y) { _y=y;     }
    float Point::z()        { return _z;}
    void  Point::z(float z) { _z=z;     }    

    Sphere::Sphere(){}
    Sphere::Sphere(Point center,float radius) : _center(center), _radius(radius)
    {}
    bool Sphere::contains(Point p){
        if(distanceSquared(p,_center)<=(_radius*_radius))                
            return true;
        return false;
    }    
    Point Sphere::center(){ return _center; }
    void Sphere::center(Point c) { _center=c; }
    float Sphere::radius() { return _radius; }
    void Sphere::radius(float radius) {_radius = radius; }
    char* Sphere::toByteArray(char scale)
    {
        //scaler not implemented now
        
        ////1-char for Type[2]AndSize[6], 3 float for 'center' and 1 float for radius, , 3 float for 'center' and 1 float for radius, 
        int size=18;
        
        union {
            float f;
            char b[sizeof(float)];
        } float_byte;
        
        char typeAndSize=(SPHERE<<6)|(size & 0x3F);
        
        char bytes[size];
        bytes[0]=typeAndSize;
        float_byte.f=_radius;
        bytes[1]=float_byte.b[0];
        bytes[2]=float_byte.b[1];
        bytes[3]=float_byte.b[2];
        bytes[4]=float_byte.b[3];
        float_byte.f=_center.x();
        bytes[5]=float_byte.b[0];
        bytes[6]=float_byte.b[1];
        bytes[7]=float_byte.b[2];
        bytes[8]=float_byte.b[3];
        float_byte.f=_center.y();
        bytes[9]=float_byte.b[0];
        bytes[10]=float_byte.b[1];
        bytes[11]=float_byte.b[2];
        bytes[12]=float_byte.b[3];
        float_byte.f=_center.z();
        bytes[13]=float_byte.b[0];
        bytes[14]=float_byte.b[1];
        bytes[15]=float_byte.b[2];
        bytes[16]=float_byte.b[3];
    }
    void Sphere::fromByteArray(char* bytes)
    {
        //scaler not implemented now
        //1-char for scalerAndType, 3 float for 'center' and 1 float for radius, 
        char scalerAndType=bytes[0];
        char size=bytes[0]&0x3F;
        char type=(bytes[0]>>6) & 0x03;
        
        union {
            float f;
            char b[sizeof(float)];
        } float_byte;
        float_byte.b[0]=bytes[1];
        float_byte.b[1]=bytes[2];
        float_byte.b[2]=bytes[3];
        float_byte.b[3]=bytes[4];
        _radius=float_byte.f;
        float_byte.b[0]=bytes[5];
        float_byte.b[1]=bytes[6];
        float_byte.b[2]=bytes[7];
        float_byte.b[3]=bytes[8];
        _center.x(float_byte.f);
        float_byte.b[0]=bytes[9];
        float_byte.b[1]=bytes[10];
        float_byte.b[2]=bytes[11];
        float_byte.b[3]=bytes[12];
        _center.y(float_byte.f);
        float_byte.b[0]=bytes[13];
        float_byte.b[1]=bytes[14];
        float_byte.b[2]=bytes[15];
        float_byte.b[3]=bytes[16];
        _center.z(float_byte.f);
    }
    

    float distanceSquared(Point p1,Point p2){
        return ((p1.x()-p2.x())*(p1.x()-p2.x()) +
                (p1.y()-p2.y())*(p1.y()-p2.y()) +
                (p1.z()-p2.z())*(p1.z()-p2.z()));
    }
    
    float distance(Point p1,Point p2){
        return Math::sqrt(((p1.x()-p2.x())*(p1.x()-p2.x()) +
                (p1.y()-p2.y())*(p1.y()-p2.y()) +
                (p1.z()-p2.z())*(p1.z()-p2.z())));
    }    
}
#endif	/* CARTESIAN_H */

