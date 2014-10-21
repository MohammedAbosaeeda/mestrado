/*
 * location_algorithm.h
 *
 *  Created on: 25 juin 2013
 *      Author: thibault
 *
 *  This header contains the different algorithms (strategies) implemented for
 *  the geographic location.
 */

#ifndef LOCATION_ALGORITHM_H_
#define LOCATION_ALGORITHM_H_

#include <node.h>
#include <utility/malloc.h>
#include <alarm.h>

#define NULL 0
__USING_SYS

struct SimpleNode{ /// Useful node information for localization
    char id;
    long x, y, rssi, rssi_sum;
    double distance;
    int n;
    int confidence;
    int dev;
    char id_tri;
    SimpleNode *next;

    SimpleNode(){}

    SimpleNode(char id, long x, long y){
        confidence = is_anchor(id) ? 100 : 0;
        this->x = x;
        this->y = y;
        this->id = id;
        dev = -1;
        id_tri = 0;
        rssi_sum = 0;
        n = 0;
    }

    SimpleNode(SimpleNode &n){
        *this = n;
    }

    void new_rssi_reading(long reading){
        if (n>3) {
            // we restrict the average to 3 measurements in case the node moves
            rssi_sum -= rssi;
            rssi_sum += reading;
            rssi = rssi_sum / n;
        } else {
            rssi_sum += reading;
            n++;
            rssi = rssi_sum / n;
        }
    }

    bool equals(SimpleNode *n){
        return (id == n->id && x == n->x && y == n->y &&
                confidence == n->confidence && rssi == n->rssi);
    }

    void display() {
        kout << "____________________\n";
        kout << "| node: " << id << "(" << x << "," << y << ")\n";
        kout << "| rssi: " << rssi << endl;
        kout << "| rssi_sum: " << rssi_sum << endl;
        kout << "| distance: " << (float)distance << endl;
        kout << "| n: " << n << endl;
        kout << "| confidence: " << confidence << endl;
        kout << "| dev: " << dev << endl;
        kout << "| id_tri: " << id_tri << endl;
        kout << "|___________________\n";
    }
};

struct NodeList{
    SimpleNode *first;
    int size;
    int sizeMax;

    NodeList(){
        first = NULL;
        size = 0;
        sizeMax=10;
    }

    bool insert(SimpleNode n){
        SimpleNode *x;

        // checking if the node is already in the list
        for(x = first; x!=NULL; x=x->next){
            if(n.id == x->id){
                if(n.equals(x)) {
                    return false;
                } else {
                    if(x->rssi != n.rssi)
                        x->new_rssi_reading(n.rssi);
                    n.x = x->x; n.y = x->y; n.confidence = x->confidence;
                    return true;
                }
            }
        }

        // checking if the list reached its max size
        if (size >= sizeMax) {
            // we eliminate the node with the least confidence aka,
            // either the last node of the table or the node being inserted
            for(x = first; x->next!=NULL; x=x->next); // no null access because size >= sizeMax
            if (n.confidence > x->confidence) remove(x);
            else return false;
        }


        // inserting the node
        SimpleNode *nw = new SimpleNode(n);
        if(first == NULL){
            first = nw; nw->next = NULL;
        }else{
            bool done = false; SimpleNode *ant = NULL;
            for(x = first; x!=NULL; x=x->next){
                if(nw->confidence > x->confidence){
                    nw->next = x;
                    if(x == first) first = nw;
                    else ant->next = nw;
                    done = true;
                }
                ant = x;
                if (done) break;
            }
            if(!done){ ant->next = nw; nw->next = NULL; }
        }
        size++; //printf("'%c' inserted\n", n.id);
        return true;
    }

    void remove(SimpleNode *n){
        if(size == 0) return;
        if(first == n){
            first = n->next;
        } else {
            SimpleNode *x, *ant = first;
            for(x=first->next; x!=NULL; x=x->next){
                if(x == n){
                    ant->next = x->next;
                    break;
                }
                ant = x;
            }
        }
        size--;
        delete n;
    }

    SimpleNode *get(char id){
        SimpleNode *x;
        for(x = first; x!=NULL; x=x->next){
            if(id == x->id) return x;
        }
        return NULL;
    }

    int get_size(){
        return size;
    }

    void display() {
        SimpleNode* tmp=first;
        kout << "##### NodeList ##### \n";
        while(tmp != NULL) {
            kout << "+++++++++++++++++++++\n";
            tmp->display();
            tmp=tmp->next;
            kout << "+++++++++++++++++++++\n";
        }
        kout << "##### -------- #####\n";
    }

};

class Location_Algorithm
{
public:
    Location_Algorithm();
    virtual ~Location_Algorithm();
    virtual void execute(Node *n) = 0;
};

class HECOPS: public Location_Algorithm
{
public:
    NodeList nl;
    int confidence;
    HECOPS(){};
    ~HECOPS(){};
    void execute(Node *n);
    void anchor(Node *node);
    void mobile(Node *node);
    template <class T>
    void calculate(T x[], T y[], T d[], int n, Coordinates *result);
};

class EHECOPS: public Location_Algorithm
{
public:
    NodeList nl;
    int confidence;
    EHECOPS(){};
    ~EHECOPS(){};
    void execute(Node *n);
    void anchor(Node *node);
    void mobile(Node *node);
    template <class T>
    void calculate(T x[], T y[], T d[], int n, Coordinates *result);
};

#endif /* LOCATION_ALGORITHM_H_ */
