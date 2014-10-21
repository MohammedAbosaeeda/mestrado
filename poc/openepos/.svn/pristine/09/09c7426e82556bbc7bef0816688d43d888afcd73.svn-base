/* $Id: mux.c,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $
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
 * \file mux.c
 * \brief Funções relacionadas ao núcleo do MUX.
 */

#include "mux.h"

/**
 * \fn void mux_init(mux_t *mux)
 * \brief Inicializa estruturas de controle do MUX.
 *
 * \param mux ponteiro para a estrutura de configuração do mux
 *
 * \relatesalso mux_s
 */
void mux_init(mux_t *mux)
{
	mux->conf.programs = NULL;
	mux->out_queue = NULL;
	queue_init(&mux->pkt_queue, TS_PKT_QUEUE_LEN);
	wpthread_mutex_init(&mux->out_queue_mutex);
	wpthread_mutex_init(&mux->pkt_queue_mutex);

	wsem_init(&mux->pkt_queue_sem, TS_PKT_QUEUE_LEN);
	wsem_init(&mux->out_queue_sem, 0);	

}

