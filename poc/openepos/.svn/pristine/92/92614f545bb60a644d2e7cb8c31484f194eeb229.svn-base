/* $Id: ts.h,v 1.1.2.1 2006-02-01 18:38:40 augusto Exp $
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

#ifndef _TS_H
#define _TS_H

#include "MuxWrapper.h"

#define TS_PKT_LEN		188	/**< Tamanho do pacote TS (bytes) */
#define TS_SYNC_BYTE		0x47	/**< sync_byte */
#define TS_NULL_PID		0x1FFF	/**< PID de pacotes nulos */
#define TS_PAT_PID		0x0000	/**< PID da Program Association Table */
#define TS_CAT_PID		0x0001	/**< PID da Conditional Access Table */
#define TS_TSDT_PID		0x0002	/**< PID da TS Description Table */

#define TS_PKT_QUEUE_LEN	40000	/**< Tamanho da fila de pacotes TS no sistema */
/*#define TS_PKT_QUEUE_LEN	100*/	/**< Tamanho da fila de pacotes TS no sistema */

typedef struct TS_pkt_s TS_pkt_t;
typedef struct TS_hdr_s TS_hdr_t;
typedef struct TS_AF_s TS_AF_t;

/**
 * \struct TS_pkt_s
 * \brief Pacote TS.
 */
struct TS_pkt_s {
	unsigned char data[TS_PKT_LEN];
	TS_pkt_t *prev, *next;
};

/**
 * \struct TS_hdr_s
 * \brief Cabeçalho do pacote TS.
 *
 * \par Exemplo de uso:
 *
 * \include TS_hdr_t.c
 */
struct TS_hdr_s {
	uint8_t sync_byte;			/**< Sempre 0x47 */
	uint8_t PID_2 : 5;			/**< Bits 9 a 13 do PID */
	uint8_t transport_priority : 1;
	uint8_t payload_unit_start_indicator : 1;
	uint8_t transport_error_indicator : 1;
	uint8_t PID_1;				/**< Bits 1 a 8 do PID */
	uint8_t continuity_counter : 4;
	uint8_t adaptation_field_control : 2;
	uint8_t transport_scrambling_control : 2;
};

/**
 * \struct TS_AF_s
 * \brief adaptation_field() do pacote TS.
 *
 * \warning
 * Utilizar apenas o campo DTS_next_AU na criação dos cabeçalhos.
 * 
 * \par Exemplo de uso:
 *
 * \include TS_AF_t.c
 */
struct TS_AF_s {
	uint8_t  adaptation_field_length;
	uint8_t  discontinuity_indicator;
	uint8_t  random_access_indicator;
	uint8_t  elementary_stream_priority_indicator;
	uint8_t  PCR_flag;
	uint8_t  OPCR_flag;
	uint8_t  splicing_point_flag;
	uint8_t  transport_private_data_flag;
	uint8_t  adaptation_field_extension_flag;
	uint64_t program_clock_reference_base;
	uint8_t  reserved_1;		/**< reserved do PCR (6 bits, 0x3f sempre) */
	uint16_t program_clock_reference_extension;
	uint64_t original_program_clock_reference_base;
	uint8_t  reserved_2;		/**< reserved do OPCR (6 bits, 0x3f sempre) */
	uint16_t original_program_clock_reference_extension;
	uint8_t  splice_countdown;
	uint8_t  transport_private_data_length;
	uint8_t  private_data_byte[TS_PKT_LEN - 8];
	uint8_t  adaptation_field_extension_length;
	uint8_t  ltw_flag;
	uint8_t  piecewise_rate_flag;
	uint8_t  seamless_splice_flag;
	uint8_t  reserved_3;		/**< reserved do adaptation_field_extension (5 bits, 0x1f sempre) */
	uint8_t  ltw_valid_flag;
	uint16_t ltw_offset;
	uint8_t  reserved_4;		/**< reserved do piecewise_rate (2 bits, 0x3 sempre) */
	uint32_t piecewise_rate;
	uint8_t  splice_type;
	uint8_t  DTS_next_AU_32_30;
	uint8_t  marker_bit_1;		/**< marker_bit (1 bit, 0x1 sempre) */
	uint16_t DTS_next_AU_29_15;
	uint8_t  marker_bit_2;		/**< marker_bit (1 bit, 0x1 sempre) */
	uint16_t DTS_next_AU_14_0;
	uint8_t  marker_bit_3;		/**< marker_bit (1 bit, 0x1 sempre) */
	uint64_t DTS_next_AU;
};

/* Protótipos */
void TS_init_pkt(unsigned char*);
void TS_set_PID(TS_hdr_t*, uint16_t);
uint16_t TS_get_PID(TS_hdr_t*);
void TS_extract_AF(unsigned char*, TS_AF_t*);
void TS_create_AF(unsigned char*, TS_AF_t*);

#endif /* ts.h */
