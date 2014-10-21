/**(c)

  Copyright (C) 2005 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

#ifndef __KESO_TYPES_H_
#define __KESO_TYPES_H_ 1

#ifndef NULL
#ifndef __cplusplus
#define NULL ((void*)0)
#else
#define NULL (0)
#endif
#endif

#if ! (defined TARGET_LINUX_JOSEK || defined TARGET_EPOS)
typedef unsigned long size_t;
typedef long off_t;
#endif

typedef char* code_t;

typedef int jboolean;
typedef signed char jchar;
typedef signed char jbyte;
typedef unsigned char jubyte;
typedef signed short jshort;
typedef signed long jint;
typedef unsigned long juint;
typedef signed long int jlong;
typedef unsigned long int julong;
typedef float jfloat;
typedef double jdouble;

typedef union { unsigned char __c[8]; double __d; } __huge_val_t;
extern __huge_val_t __huge_val;
#define INFINITY (__huge_val.__d)

/* TODO: */
#define NAN (1/0)

#endif
