/* $Id: channel.h,v 1.1.2.1 2006-02-01 18:38:39 augusto Exp $
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

#ifndef _CHANNEL_H
#define _CHANNEL_H

#include <stdio.h>
#include <stdint.h>
#include <libxml/parser.h>
#include <libxml/tree.h>

#define PROTOCOL_VERSION	0x01	/**< Protocol Version */
#define LCT_TABLE_ID		0x81	/**< Table ID da LCT  (Logical Channel Table) */
#define ETT_TABLE_ID		0x82	/**< Table ID of ETT (Event Information Table) */
#define ETT_TABLE_ID		0x83	/**< Table ID of ETT (Extended Text Table) */

#define LCT_PID		0x11	/**< PID da LCT (Logical Channel Table) */
#define EIT_PID		0x12	/**< PID EIT (Event Information Table) */
#define ETT_PID		0x13	/**< PID ETT (Extended Text Table) */

#define SHORT_NAME_MAX		6

typedef struct channel_s channel_t;
typedef struct LCT_s LCT_t;
typedef struct LC_descriptor_s LC_descriptor_t;

typedef struct event_s event_t;
typedef struct EIT_s EIT_t;


/**
 * \struct event_s
 * \brief Estrutura para armazenamento dos eventos; 
 */
struct event_s {

	uint16_t id;
	uint32_t date:18;
	uint32_t starttime:24;
	uint32_t duraction:24;
	uint8_t program_type;
	uint8_t classification:4;
	uint8_t flags:4;
	char *description;

	event_t *prev,*next;

};


struct channel_s {

	int num_logical_channel;
	LCT_t *logical_channel;
};

/**
 * \struct LCT_s
 * \brief Estrutura para a tabela LCT (Logical Channel Table)
 */
struct LCT_s {
	uint16_t ID;		/**< Logical channel ID */
	uint16_t num_descriptor;
	LC_descriptor_t *lc_descriptor;

	LCT_t *next, *prev;
};

/**
 * \struct LC_descriptor_s
 * \brief Estrutura complementar a tabela LCT. 
 */
struct LC_descriptor_s {
	uint16_t ID;
	uint8_t flags:4;
	uint8_t content_type:4;
	uint16_t channel_type;
	unsigned char *short_name;
	unsigned char *full_name;
	event_t *event;
	
	LC_descriptor_t *next,*prev;
};


		


/* Protótipos */
void channel_read(char *filename, channel_t *channel);
void channel_free(channel_t *channel);
void channel_print(channel_t *channel, FILE *out);

#endif /* channel.h */
