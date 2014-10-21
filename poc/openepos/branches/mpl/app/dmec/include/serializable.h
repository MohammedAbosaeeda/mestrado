/*
 * serializable.h
 *
 *  Created on: May 21, 2012
 *  Author: Mateus Krepsky Ludwich
 */

#ifndef SERIALIZABLE_H_
#define SERIALIZABLE_H_

#include "traits.h"

// <<interface>>
template<class T>
class Serializable
{

// kind of virtual methods	
private:

    static String serialize(T* obj) { return "Not Implemented"; }
    
    /* static T* unserialize(String rep) { return 0; } */
    
};


template<class T>
class SerializableDummy : public Serializable<T>
{
};

#endif
