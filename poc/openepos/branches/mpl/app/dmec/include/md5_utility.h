/*
 * md5_utility.h
 *
 *  Created on: May 21, 2012
 *  Author: Mateus Krepsky Ludwich
 */

#include "traits.h"

class MD5_Utility
{

public:

    static void printSum(String msg);

private:
    static void __printSum(unsigned char* md);
    
    
    static unsigned char* __computeMD5(String data);
    
};
