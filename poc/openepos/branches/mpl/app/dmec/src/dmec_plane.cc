
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

#include "../include/mec_plane.h"
#include "../include/traits.h"

#if LINUX
#include <stdlib.h>


#else
#include <utility/malloc.h>
#endif


MEC_Plane::MEC_Plane(unsigned int width, unsigned int height, MEC_Sample** samples) 
{
    __width = width;
    __height = height;
    __samples = samples;
}


MEC_Plane::~MEC_Plane() {}


