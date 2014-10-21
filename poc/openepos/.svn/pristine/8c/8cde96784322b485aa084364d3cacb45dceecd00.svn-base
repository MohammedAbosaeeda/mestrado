
/*!
 *******************************************************************************
 * \file
 *
 * \brief
 *    -
 *
 * \author
 *      - Alexandre Massayuki Okazaki   (alexandre@lisha.ufsc.br)
 *      - Mateus Krepsky Ludwich        (mateus@lisha.ufsc.br)
 *      - Tiago de Albuqueque Reis      (reis@lisha.ufsc.br)
 *
 *******************************************************************************
 */

#ifndef PLANE_H_
#define PLANE_H_

#include "mec_sample.h"

class MEC_Plane
{
public:
    MEC_Plane(unsigned int width, unsigned int height, MEC_Sample** samples);
    
    ~MEC_Plane();


    MEC_Sample sample(int row, int column)
    {
        return __samples[row][column];
    }
    
    unsigned int width()
	{
        return __width;
	}

	unsigned int height()
	{
        return __height;
	}
    

private:
    
    unsigned int __width;
    unsigned int __height;

    MEC_Sample** __samples;
};


#endif /* PLANE_H_ */
