/* $Id: mux.h,v 1.1.2.1 2006-02-01 18:38:39 augusto Exp $
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

#ifndef _MUX_H
#define _MUX_H

#include "MuxWrapper.h"

#include "ts.h"
#include "queue.h"
#include "config.h"
#include "crc32.h"
#include "psi.h"
#include "queue.h"
#include "ts.h"
#include "utils.h"

typedef struct mux_s mux_t;
typedef struct es_args_s es_args_t;

/**
 * \struct mux_s
 * \brief Estrutura que define o estado e a configuração do MUX.
 */
struct mux_s {
	config_t conf;				/**< configuração do MUX */
	TS_pkt_t *out_queue;			/**< fila de saída de pacotes */
	TS_pkt_t *pkt_queue;			/**< fila de pacotes livres */
	wpthread_mutex_t out_queue_mutex;	/**< mutex da fila de saída */
	wpthread_mutex_t pkt_queue_mutex;	/**< mutex da fila de pacotes livres */

	wsem_t out_queue_sem;
	wsem_t pkt_queue_sem;
};

/**
 * \struct es_args_s
 * \brief Estrutura que empacota os argumentos para as threads de ES.
 */
struct es_args_s {
	mux_t *mux;
	ES_t *es;
	int is_pcr;	/**< ES é fonte de PCR? */
};

/* Protótipos */
int pat_thread(void*);
int pmt_thread(void*);
int output_thread(void*);
int es_thread(void*);
int es_audio_thread(void*);

#endif /* mux.h */
