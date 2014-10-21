/* $Id: ts.c,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $
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

/**
 * \file ts.c
 * \brief Funções para manipulação de pacotes TS.
 */

#include "ts.h"

/**
 * \fn void TS_init_pkt(unsigned char *p)
 * \brief Inicializa um pacote TS para uso.
 *
 * Inicializa um pacote TS com PID de pacote nulo e preenche o payload
 * com 0xFF.
 *
 * \param p ponteiro para a o pacote TS
 */
void TS_init_pkt(unsigned char *p)
{
	TS_hdr_t *ts;

	/* Preenche 32 bits com 0 (zero) e o resto com 0xFF */
	wmemset(p, 0x0, 4);
	wmemset(p+4, 0xFF, TS_PKT_LEN - 4);

	ts = (TS_hdr_t*)p;
	ts->sync_byte = TS_SYNC_BYTE;
	TS_set_PID(ts, TS_NULL_PID); /* Padrão é pacotes nulos */
}

/**
 * \fn void TS_set_PID(TS_hdr_t *ts_hdr, uint16_t v)
 * \brief Seta o campo PID do cabeçalho do pacote TS.
 *
 * Preenche os 13 bits do campo PID.
 *
 * \param ts_hdr ponteiro para TS_hdr_t
 * \param v novo valor do PID
 *
 * \relatesalso TS_hdr_s
 */
void TS_set_PID(TS_hdr_t *ts_hdr, uint16_t v)
{
	ts_hdr->PID_2 = v >> 8;
	ts_hdr->PID_1 = v;
}

/**
 * \fn uint16_t TS_get_PID(TS_hdr_t *ts_hdr)
 * \brief Pega o campo PID do cabeçalho do pacote TS.
 *
 * \return Retorna os 13 bits do campo PID.
 *
 * \relatesalso TS_hdr_s
 */
uint16_t TS_get_PID(TS_hdr_t *ts_hdr)
{
	return (ts_hdr->PID_2 << 8) | ts_hdr->PID_1;
}
 
/**
 * \fn void TS_extract_AF(unsigned char *p, TS_AF_t *af)
 * \brief Extrai o adaptation_field() de um pacote TS.
 *
 * \param p ponteiro para a o pacote TS
 * \param af ponteiro para TS_AF_t destino
 *
 * \relatesalso TS_AF_s
 */
void TS_extract_AF(unsigned char *p, TS_AF_t *af)
{
	p += 4; /* Pula 32 bits do cabeçalho TS */

	af->adaptation_field_length = p[0];
	af->discontinuity_indicator = (p[1] >> 7) & 0x1;
	af->random_access_indicator = (p[1] >> 6) & 0x1;
	af->elementary_stream_priority_indicator = (p[1] >> 5) & 0x1;
	af->PCR_flag = (p[1] >> 4) & 0x1;
	af->OPCR_flag = (p[1] >> 3) & 0x1;
	af->splicing_point_flag = (p[1] >> 2) & 0x1;
	af->transport_private_data_flag = (p[1] >> 1) & 0x1;
	af->adaptation_field_extension_flag = p[1] & 0x1;
	p += 2; /* Pula 16 bits do cabeçalho */

	if (af->PCR_flag == 0x1) {
		/* \todo Precisa zerar antes de fazer o shift? */
		af->program_clock_reference_base = (p[0] << 25) | (p[1] << 17) | (p[2] << 9) |
			(p[3] << 1) | (p[4] >> 7 & 0x1);
		af->reserved_1 = (p[4] >> 1) & 0x3f;
		af->program_clock_reference_extension = (p[4] & 0x1) | p[5];
		p += 6; /* Pula 48 bits do PCR */
	}

	if (af->OPCR_flag == 0x1) {
		af->original_program_clock_reference_base = (p[0] << 25) | (p[1] << 17) |
			(p[2] << 9) | (p[3] << 1) | ((p[4] >> 7) & 0x1);
		af->reserved_2 = (p[4] >> 1) & 0x3f;
		af->original_program_clock_reference_extension = (p[4] & 0x1) | p[5];
		p += 6; /* Pula 48 bits do OPCR */
	}

	if (af->splicing_point_flag == 0x1) {
		af->splice_countdown = p[0];
		p += 1; /* Pula 8 bits do splice_countdown */
	}

	if (af->transport_private_data_flag == 0x1) {
		af->transport_private_data_length = p[0];
		p += 1; /* Pula 8 bits do transport_private_data_length */
		wmemcpy(&af->private_data_byte, p, af->transport_private_data_length);
		/* Pula private_data_byte */
		p += af->transport_private_data_length;
	}

	if (af->adaptation_field_extension_flag == 0x1) {
		af->adaptation_field_extension_length = p[0];
		af->ltw_flag = (p[1] >> 7) & 0x1;
		af->piecewise_rate_flag = (p[1] >> 6) & 0x1;
		af->seamless_splice_flag = (p[1] >> 5) & 0x1;
		af->reserved_3 = p[1] & 0x1f;
		p += 2; /* Pula 16 bits do cabeçalho de extensão */

		if (af->ltw_flag == 0x1) {
			af->ltw_valid_flag = p[0] >> 7; 
			af->ltw_offset = ((p[0] & 0x7f) << 8) | p[1];
			p += 2; /* Pula os 16 bits dos campos */
		}

		if (af->piecewise_rate_flag == 0x1) {
			af->reserved_4 = p[0] >> 6;
			af->piecewise_rate = ((p[0] & 0x3f) << 16) | (p[1] << 8) | p[2];
			p += 3; /* Pula os 24 bits dos campos */
		}

		if (af->seamless_splice_flag == 0x1) {
			uint8_t marker_bit_1, marker_bit_2, marker_bit_3;

			af->splice_type = p[0] >> 4;
			af->DTS_next_AU_32_30 = (p[0] >> 1) & 0x7;
			af->marker_bit_1 = p[0] & 0x1;
			af->DTS_next_AU_29_15 = (p[1] << 7) | ((p[2] >> 1) & 0x7f);
			af->marker_bit_2 = p[2] & 0x1;
			af->DTS_next_AU_14_0 = (p[3] << 7) | ((p[4] >> 1) & 0x7f);
			af->marker_bit_3 = p[4] & 0x1;
			af->DTS_next_AU = (af->DTS_next_AU_32_30 << 30) |
				(af->DTS_next_AU_29_15 << 15) | af->DTS_next_AU_14_0;
		}
	}
}

/**
 * \fn void TS_create_AF(unsigned char *p, TS_AF_t *af)
 * \brief Cria o adaptation_field() de um pacote TS.
 *
 * \note Se na estrutura TS_AF_t de origem o campo adaptation_field_length
 * for igual a 0 (zero) então o campo é preenchido automaticamente com o
 * tamanho total da estrutura criada.
 *
 * \param p ponteiro para o pacote TS
 * \param af ponteiro para TS_AF_t origem
 *
 * \relatesalso TS_AF_s
 */
void TS_create_AF(unsigned char *p, TS_AF_t *af)
{
	unsigned char cont = 0;

	p += 4; /* Pula 32 bits do cabeçalho TS */

	p[1] = (af->discontinuity_indicator << 7) | (af->random_access_indicator << 6) |
		(af->elementary_stream_priority_indicator << 5) | (af->PCR_flag << 4) |
		(af->OPCR_flag << 3) | (af->splicing_point_flag << 2) |
		(af->transport_private_data_flag << 1) | (af->adaptation_field_extension_flag);
	p += 2; /* Pula 16 bits do cabeçalho */
	cont += 2;

	if (af->PCR_flag == 0x1) {
		p[0] = af->program_clock_reference_base >> 25;
		p[1] = af->program_clock_reference_base >> 17;
		p[2] = af->program_clock_reference_base >> 9;
		p[3] = af->program_clock_reference_base >> 1;
		/* 0x3f = 6 bits do campo reserved */
		p[4] = (af->program_clock_reference_base << 7) | (0x3f << 1) |
			(af->program_clock_reference_extension >> 8);
		p[5] = af->program_clock_reference_extension;
		p += 6; /* Pula 48 bits do PCR */
		cont += 6;
	}

	if (af->OPCR_flag == 0x1) {
		p[0] = af->original_program_clock_reference_base >> 25;
		p[1] = af->original_program_clock_reference_base >> 17;
		p[2] = af->original_program_clock_reference_base >> 9;
		p[3] = af->original_program_clock_reference_base >> 1;
		/* 0x3f = 6 bits do campo reserved */
		p[4] = (af->original_program_clock_reference_base << 7) | (0x3f << 1) |
			(af->original_program_clock_reference_extension >> 8);
		p[5] = af->original_program_clock_reference_extension;
		p += 6; /* Pula 48 bits do OPCR */
		cont += 6;
	}

	if (af->splicing_point_flag == 0x1) {
		p[0] = af->splice_countdown;
		p += 1; /* Pula 8 bits do splice_countdown */
		cont += 1;
	}

	if (af->transport_private_data_flag == 0x1) {
		p[0] = af->transport_private_data_length;
		p += 1; /* Pula 8 bits do transport_private_data_length */
		cont += 1;
		wmemcpy(p, af->private_data_byte, af->transport_private_data_length);
		/* Pula private_data_byte */
		p += af->transport_private_data_length;
		cont += af->transport_private_data_length;
	}

	if (af->adaptation_field_extension_flag == 0x1) {
		p[0] = af->adaptation_field_extension_length;
		/* 0x1f = 5 bits do campo reserved */
		p[1] = ((af->ltw_flag << 7) | (af->piecewise_rate_flag << 6) |
			(af->seamless_splice_flag << 5) | 0x1f); 
		p += 2; /* Pula 16 bits do cabeçalho de extensão */
		cont += 2;

		if (af->ltw_flag == 0x1) {
			p[0] = (af->ltw_valid_flag << 7) | (af->ltw_offset >> 8);
			p[1] = af->ltw_offset; 
			p += 2; /* Pula os 16 bits dos campos */
			cont += 2;
		}

		if (af->piecewise_rate_flag == 0x1) {
			/* 0x3 = 2 bits do campo reserved */
			p[0] = (0x3 << 6) | (af->piecewise_rate >> 16);
			p[1] = af->piecewise_rate >> 8;
			p[2] = af->piecewise_rate;
			p += 3; /* Pula os 24 bits dos campos */
			cont += 3;
		}

		if (af->seamless_splice_flag == 0x1) {
			/* 0x1 = marker_bit */
			p[0] = ((af->splice_type << 4) | (af->DTS_next_AU >> 29) | 0x1);
			p[1] = af->DTS_next_AU >> 22; 
			p[2] = ((af->DTS_next_AU >> 14) | 0x1);
			p[3] = af->DTS_next_AU >> 7; 
			p[4] = ((af->DTS_next_AU << 1) | 0x1);
			cont += 5;
		}
	}

	/* Ajusta length se valor passado em af é 0 */
	p[0] = (af->adaptation_field_length == 0) ? cont : af->adaptation_field_length;
}

