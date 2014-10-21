/* $Id: psi.h,v 1.1.2.1 2006-02-01 18:38:40 augusto Exp $
 *
 * Copyright (C) 2005  Pesquisa em Redes de Alta Velocidade (PRAV)
 *                     Universidade do Vale do Rio dos Sinos (UNISINOS)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111, USA.
 */

#ifndef _PSI_H
#define _PSI_H

#include "MuxWrapper.h"


#define PSI_PA	0x00	/**< Program Association Section */
#define PSI_CA	0x01	/**< Conditional Access Section */
#define PSI_PM	0x02	/**< TS Program Map Section */
#define PSI_DS	0x03	/**< TS Description Section */

typedef struct PSI_section_hdr_s PSI_section_hdr_t;
typedef struct PA_s PA_t;
typedef struct PA_section_s PA_section_t;
typedef struct PM_s PM_t;
typedef struct PM_section_s PM_section_t;

/**
 * \struct PSI_section_hdr_s
 * \brief Cabeçalho comum às seções de pacotes TS com PSI.
 */ 
struct PSI_section_hdr_s {
	uint8_t table_id;
	uint8_t section_length_2 : 4;		/**< Bits 9 a 12 do section_length */
	uint8_t reserved_1 : 2;			/**< reserved (2 bits, 0x3 sempre) */
	uint8_t private_indicator : 1;		/**< '0' quando não for private_section() */
	uint8_t section_syntax_indicator : 1;
	uint8_t section_length_1;		/**< Bits 1 a 8 do section_length */
	uint8_t multi_2;			/**< Bits 9 a 16 do campo multi */
	uint8_t multi_1;			/**< Bits 1 a 8 do multi */
	uint8_t current_next_indicator : 1;
	uint8_t version_number : 5;
	uint8_t reserved_2 : 2;			/**< reserved do multi (2 bits, 0x3 sempre) */
	uint8_t section_number;
	uint8_t last_section_number;
};

/**
 * \struct PA_s
 * \brief Associação entre program_number e PID.
 *
 * É um elemento da lista encadeada que armazena a lista de programas de uma PAT.
 */
struct PA_s {
	uint16_t program_number;
	uint16_t program_map_PID;
	PA_t *prev, *next;
};

/**
 * \struct PA_section_s
 * \brief Program association section.
 */
struct PA_section_s {
	PA_t *pa;
	uint16_t network_PID;
	uint32_t CRC_32;
	int crc_error;		/**< CRC32 da seção não bate com o valor de CRC_32 */
};

/**
 * \struct PM_s
 * \brief Mapeamento de elementary streams de um programa.
 *
 * É um elemento da lista encadeada que armazena a lista de ESs de um programa.
 */
struct PM_s {
	uint8_t stream_type;
	uint16_t elementary_PID;
	PM_t *prev, *next;
};

/**
 * \struct PM_section_s
 * \brief Program map section.
 */
struct PM_section_s {
	PM_t *pm;
	uint16_t PCR_PID;
	uint32_t CRC_32;
	int crc_error;		/**< CRC32 da seção não bate com o valor de CRC_32 */
};

/* Protótipos */
void PA_section_extract(unsigned char*, PA_section_t*);
void PA_section_free(PA_section_t*);
void PA_section_create(unsigned char*, PA_section_t*);
void PM_section_extract(unsigned char*, PM_section_t*);
void PM_section_free(PM_section_t*);
void PM_section_create(unsigned char*, PM_section_t*);
void PSI_section_set_length(PSI_section_hdr_t*, uint16_t);
uint16_t PSI_section_get_length(PSI_section_hdr_t*);
void PSI_section_set_multi(PSI_section_hdr_t*, uint16_t);
uint16_t PSI_section_get_multi(PSI_section_hdr_t*);

#endif /* psi.h */
