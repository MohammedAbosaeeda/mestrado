/* $Id: es.c,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $
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
 * \file es.c
 * \brief Implementação da thread que gerencia a entrada de uma ES.
 */

#include "mux.h"

/**
 * \fn void es_thread(void *data)
 * \brief Thread de manipulação de uma ES de entrada.
 *
 * A ES de entrada está encapsulada dentro de uma TSsingle. A função
 * apenas preenche corretamente os campos dos pacotes ES da TSsingle de
 * entrada e enfileira o pacote na fila de saída. A ES que é fonte de
 * PCR deve gerá-lo a cada 100ms.
 *
 * \param data ponteiro para os dados passados para a thread
 */
int es_thread(void *data)
{
	wprintf("ES Thread Running!!\n");

	es_args_t *args = (es_args_t*)data;
	mux_t *mux = args->mux;
	ES_t *es = args->es;
	TS_pkt_t *pkt;
	TS_hdr_t *ts_hdr;
	WFILE *in;
	unsigned int continuity_counter=0;

	wfree(data);	/* libera es_args_t alocado */

	if (!(in = wfopen(es->source, "r"))) {
		die("es_thread: erro ao abrir dispositivo de entrada!\n");
	}

	int numiter = 0;

	while (1) {
		/*
                if(!(++numiter % 100))
                        wprintf("E");   
		*/

		/* Pega um pacote da fila de pacotes livres */
                wsem_wait(&mux->pkt_queue_sem);

		wpthread_mutex_lock(&mux->pkt_queue_mutex);
		pkt = dequeue_pkt(&mux->pkt_queue);
		wpthread_mutex_unlock(&mux->pkt_queue_mutex);

		if(pkt == NULL){
			wprintf("e: NEVER HAPPENS\n");
			/*usleep(1000);*/
			continue;
		}
				
		/* Ajusta campos do pacote de acordo */

		/*
		wfread(&pkt->data[0], 1, 1, in);
		while(pkt->data[0] != TS_SYNC_BYTE)
			wfread(&pkt->data[0], 1, 1, in);

		wfread(&pkt->data[1], TS_PKT_LEN - 1, 1, in);
		*/

		wfread_searching(&pkt->data[0], TS_PKT_LEN, 1, in, TS_SYNC_BYTE);
			
		ts_hdr = (TS_hdr_t*)pkt->data;
		TS_set_PID(ts_hdr, es->PID);

		/* Processa tudo. é contigo maiko! */
		
		/* Incrementa o continuity_counter */
		ts_hdr->continuity_counter = continuity_counter++ % 16;
		
		/* Ver como será implementado o adaptation field */

		/* Enfileira na fila de saída */
		wpthread_mutex_lock(&mux->out_queue_mutex);
		queue_pkt(&mux->out_queue, pkt);
		wpthread_mutex_unlock(&mux->out_queue_mutex);

                wsem_post(&mux->out_queue_sem);

	}
}

/**
 * \fn void es_audio_thread(void *data)
 * \brief Thread de manipulação de uma entrada de ES de áudio.
 *
 * A ES de entrada está encapsulada dentro de uma TSsingle. A função
 * apenas preenche corretamente os campos dos pacotes ES da TSsingle de
 * entrada e enfileira o pacote na fila de saída. A ES que é fonte de
 * PCR deve gerá-lo a cada 100ms.
 *
 * \param data ponteiro para os dados passados para a thread
 */
int es_audio_thread(void *data)
{
	es_args_t *args = (es_args_t*)data;
	mux_t *mux = args->mux;
	ES_t *es = args->es;
	TS_pkt_t *pkt, *pkt_start, *pkt_ant;
	TS_hdr_t *ts_hdr;
	WFILE *in;
	unsigned int continuity_counter=0, pkt_counter=0, pes_lenght;

	wfree(data);	/* libera es_args_t alocado */

	if (!(in = wfopen(es->source, "r"))) {
		die("es_thread: erro ao abrir dispositivo de entrada!");
	}

	/*wfprintf(stderr,"es_audio_thread: Dispositivo de entrada (%s)\n",es->source);*/
	
	while (1) {
			
		/* Pega um pacote da fila de pacotes livres */
		wpthread_mutex_lock(&mux->pkt_queue_mutex);
		pkt = dequeue_pkt(&mux->pkt_queue);
		wpthread_mutex_unlock(&mux->pkt_queue_mutex);
		if(pkt_start == NULL) pkt_start = pkt;

		if(pkt == NULL){
			continue;
		}

		/* Ajusta campos do pacote de acordo */
		wfread(&pkt->data[0], 1, 1, in);
		while(pkt->data[0] != TS_SYNC_BYTE)
			wfread(&pkt->data[0], 1, 1, in);
		wfread(&pkt->data[1], TS_PKT_LEN - 1, 1, in);
		ts_hdr = (TS_hdr_t*)pkt->data;
		TS_set_PID(ts_hdr, es->PID);

		/* Incrementa o continuity_counter */
		ts_hdr->continuity_counter = continuity_counter++ % 16;
		
		if(ts_hdr->payload_unit_start_indicator){	
			
			pes_lenght = (TS_PKT_LEN - 32) * pkt_counter;
			
			ts_hdr = (TS_hdr_t*)pkt_start->data;
			if(ts_hdr->payload_unit_start_indicator){
				pkt_start->data[9] = 0x00FF & (pes_lenght >> 8);
				pkt_start->data[10] = 0x00FF & pes_lenght;
			}
			
			while(pkt_start != NULL){
				/* Enfileira na fila de saída */
				wpthread_mutex_lock(&mux->out_queue_mutex);
				queue_pkt(&mux->out_queue, pkt_start);
				wpthread_mutex_unlock(&mux->out_queue_mutex);
				pkt_start = pkt_start->next;
			}
			
			pkt_start = pkt;
			pkt_counter = 0;
		}

		pkt_counter++;
		pkt_ant = pkt;
		
	}

	return (0);

}

