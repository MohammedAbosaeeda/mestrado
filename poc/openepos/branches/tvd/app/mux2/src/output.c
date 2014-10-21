/* $Id: output.c,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $
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
 * \file output.c
 * \brief Implementação da thread de saída de pacotes do MUX.
 */

#include "mux.h"

/**
 * \fn void output_thread(void *data)
 * \brief Thread de geração de Program Association Table (PAT).
 *
 * Abre o dispositivo de saída (arquivo, device, FIFO, etc) e entra em
 * loop que dura até o final da execução do sistema. Enquanto existem
 * pacotes na fila eles são enviados para a stream de saída.
 *
 * \param data ponteiro para os dados passados para a thread
 */
int output_thread(void *data)
{
        wprintf("Output Thread Running!!\n");

	mux_t *mux = (mux_t*)data;
	TS_pkt_t *pkt;
	WFILE *out;

	if (!(out = wfopen(mux->conf.ts_output, "w"))) {
		die("output_thread: erro ao abrir dispositivo de saída!");
	}

	int numiter = 0;

	while (1) {
		/*
                if(!(++numiter % 100))
                        wprintf("O");   
		*/

		/* Pega um pacote da fila de pacotes de saída */
                wsem_wait(&mux->out_queue_sem);

		wpthread_mutex_lock(&mux->out_queue_mutex);
		pkt = dequeue_pkt(&mux->out_queue);
		wpthread_mutex_unlock(&mux->out_queue_mutex);

		if (pkt == NULL) {
			wprintf("o: NEVER HAPPENS\n"); 
/*			wusleep(500000);*/
			wusleep(100);				/* só para não queimar a CPU nos testes */
		} else {

			wfwrite(pkt->data, TS_PKT_LEN, 1, out);

			/* Devolve pacotes para fila de pacotes livres */
			wpthread_mutex_lock(&mux->pkt_queue_mutex);
			queue_pkt(&mux->pkt_queue, pkt);
			wpthread_mutex_unlock(&mux->pkt_queue_mutex);

                        wsem_post(&mux->pkt_queue_sem);

		}
	}

	return (0);
}

