/* $Id: config.h,v 1.1.2.1 2006-02-01 18:38:39 augusto Exp $
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

#ifndef _CONFIG_H
#define _CONFIG_H

#include "MuxWrapper.h"

#define CONFIG_TEXT_MAXLEN 256	/**< Tamanho máximo de uma string lida
				     do arquivo de configuração */

typedef struct config_s config_t;
typedef struct program_s program_t;
typedef struct ES_s ES_t;

/**
 * \struct config_s
 * \brief Estrutura que guarda a configuração do MUX.
 */
struct config_s {
	char ts_output[CONFIG_TEXT_MAXLEN];
	uint16_t ts_id;
	uint16_t pat_rate;
	uint16_t pmt_rate;
	uint16_t nprogs;
	program_t *programs;
};

/**
 * \struct program_s
 * \brief Estrutura da lista de programas configurados.
 */
struct program_s {
	uint16_t number;
	uint16_t PID;
	uint16_t PCR_PID; 
	uint16_t nES;
	ES_t *elementary_streams;
	program_t *prev, *next;
};

/**
 * \struct ES_s
 * \brief Estrutura da lista de ESs associadas a um programa.
 */
struct ES_s {
	uint16_t PID;
	uint8_t stream_type;
	char source[CONFIG_TEXT_MAXLEN];
	ES_t *prev, *next;
};

/* Protótipos */
void config_read(char*, config_t*);
void config_free(config_t*);

#endif /* config.h */
