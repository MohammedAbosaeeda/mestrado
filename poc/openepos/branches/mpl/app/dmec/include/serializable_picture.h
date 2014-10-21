/*
 *  Created on: May 22, 2012
 *  Author: Mateus Krepsky Ludwich
 */
 
#ifndef SERIALIZABLE_PICTURE_H_
#define SERIALIZABLE_PICTURE_H_

#include "serializable.h"

// <<abstract class>>
template<class T>
class SerializablePicture : public Serializable<T>
{
public:    
    static String serialize(T* obj);
    
};


class MEC_Picture;

class SerializableMEC_Picture : public SerializablePicture<MEC_Picture>
{
public:    
    static String serialize(MEC_Picture* obj);
    
};


#endif
