/*
 * node.h
 *
 *  Created on: 25 juin 2013
 *      Author: thibault
 *
 *  This file contains the data structure of a node.
 *  A node has a type and can be equipped with several hardware components
 *  (RSSI measurement, NFC, ...).
 */

#ifndef NODE_H_
#define NODE_H_

#include <utility/malloc.h>
#include <radio.h>
#include <display.h>
#include <nic.h>
#include <utility/math.h>

__USING_SYS

// forward declaration of Location_Algorithm to avoid circular including
class Location_Algorithm;

// Convention: Node x is ANCHOR if 'A' <= x.id <= 'Z'
//             Node x is MOBILE if 'a' <= x.id <= 'z'

const char BASE      = '#';
const char EVERYBODY = '*';

inline bool is_base(char who){
    return who == 'A';
}

inline bool is_anchor(char who){
    return (who >= 'A' && who <= 'Z');
}

namespace enumerated
{
    enum Type { Anchor, Mobile, RSSI, Ultrasound };
}

struct Coordinates {
    long x,y;
    Coordinates(){}
    ~Coordinates(){};
};

class Node {
    // TODO change to private (write getters/setters)
public:
    char id, id_tri;
    long x, y;
    int dev;
    NIC* nic;
    // algorithm used for the geographic localization (strategy design pattern)
    Location_Algorithm* strategy;
    bool hasRSSI, hasUS;
public:
    virtual ~Node() {};
    bool equals(Node *n);
    virtual void location(Node *wrapper) = 0;
    virtual enumerated::Type getType() =0;
protected:
    Node(char id, long x, long y);
    Node(char id, long x, long y, Location_Algorithm* strategy, NIC* nic);
    Node(char id, Location_Algorithm* strategy, NIC* nic);
    Node();
};

class Anchor : public Node {
public:
    Anchor();
    Anchor(char id, long x, long y);
    Anchor(char id, long x, long y, Location_Algorithm* strategy, NIC* nic);
    Anchor(char id, Location_Algorithm* strategy, NIC* nic);
    Anchor(Anchor &n);
    Anchor(Node *n);
    ~Anchor();
    void location(Node *wrapper);
    enumerated::Type getType() ;
};

class Mobile : public Node {
public:
    Mobile();
    Mobile(char id, long x, long y);
    Mobile(char id, long x, long y, Location_Algorithm* strategy, NIC* nic);
    Mobile(char id, Location_Algorithm* strategy, NIC* nic);
    Mobile(Mobile &n);
    Mobile(Node *n);
    ~Mobile();
    void location(Node *wrapper) ;
    enumerated::Type getType() ;
};

class Decorator : public Node {
public:
    Decorator( Node* inner );
    ~Decorator();
    void location(Node *wrapper);
    Node* getWrappee() {
        return m_wrappee;
    }
protected:
    Node*  m_wrappee;
};

/*
 * A node can be decorated with the following classes, representing
 * hardware components that can be found on the physical node.
 */
class RSSI : public Decorator {
public:
    // TODO constructors/destructors
    //long rssi;
    //unsigned long rssi_sum, n;
    Radio_Wrapper _radio;
    long d0, Pd0;
    float path_loss;
public:
    RSSI( Node* core );
    RSSI( Node* core, int d0, int Pd0, float path_loss );
    ~RSSI();
    void location(Node *wrapper);
    //void new_rssi_reading(int reading);
    int getRSSI() ;
    enumerated::Type getType() ;
    double rssi_distance(long rssi) ;
};

class Ultrasound : public Decorator {
    int maxRange; // meters
    int minRange; // centimeters
    int alpha; // measuring angle, degrees
    Coordinates R, L; // points forming the triangle of range
    Coordinates* orientation; // orientation of the sensor
    /* Coordinates of the vector corresponding to the right side of the triangle
     * example:
     *(0,3)______(3,6)
     *     \    /
     *      \  /^   orientation=(1,1)
     *       \//
     *      (3,0)
     */
public:
    Ultrasound( Node* core );
    ~Ultrasound();
    Ultrasound( Node* core, int maxRange, int minRange, int alpha, Coordinates* orientation );
    void location(Node *wrapper);
    int distance(Coordinates node); // returns -1 if not concerned; meters
    enumerated::Type getType() ;
    void display() {
        kout << "\n+===================+\n";
        kout << "maxRange=" << maxRange << endl;
        kout << "minRange=" << minRange << endl;
        kout << "alpha=" << alpha << endl;
        kout << "R=(" << R.x << "," << R.y << ")\n";
        kout << "L=(" << L.x << "," << L.y << ")\n";
        kout << "orientation=(" << orientation->x << "," << orientation->y << ")\n";
        kout << "+===================+\n";
    }
private:
    bool inRangeTriangle(Coordinates dest);
    void rotate(Coordinates src, Coordinates * dst, int phi);
    float area(int x1, int y1, int x2, int y2, int x3, int y3);
    void setRL();
};

class NFC : public Decorator {
public:
    NFC( Node* core );
    ~NFC();
    int getNFC();
private:
};

/* Structure useful to send all type of information */
template <typename T>
struct Message{
    int rssi;
    int distance;
    char src;
    char dst;
    struct NodeInfo{ // node (decorated, anchor or mobile)
        char id;
        long x, y;
    } n;

    Message(){}

    Message(char src, char dst): rssi(0), distance(0){
        this->src = src;
        this->dst = dst;
    }

    Message(char src, char dst, T *n): rssi(0), distance(0){
        this->n.id = n->id;
        this->n.x = n->x;
        this->n.y = n->y;
        this->src = src;
        this->dst = dst;
    }

    Message(char src, char dst, NodeInfo *n): rssi(0), distance(0){
        this->n.id = n->id;
        this->n.x = n->x;
        this->n.y = n->y;
        this->src = src;
        this->dst = dst;
    }

    Message(char dst, T *n): rssi(0), distance(0){
        this->n.id = n->id;
        this->n.x = n->x;
        this->n.y = n->y;
        this->src = n->id;
        this->dst = dst;
    }

    void display() {
        kout << "____________________\n";
        kout << "| RSSI:" << rssi << endl;
        kout << "| distance:" << distance << endl;
        kout << "| src: " << src << endl;
        kout << "| dst: " << dst << endl;
        kout << "| node: " << n.id << "(" << n.x << "," << n.y << ")\n";
        kout << "|___________________\n";
    }
};




#endif /* NODE_H_ */
