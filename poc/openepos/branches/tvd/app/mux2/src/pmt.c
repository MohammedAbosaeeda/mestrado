/* $Id: pmt.c,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $
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
 * \file pmt.c
 * \brief Implementação da thread que gera a Program Map Table (PMT).
 */
 
#include "mux.h"

/**
 * \fn void pmt_thread(void *data)
 * \brief Thread de geração de Program Map Table (PMT).
 *
 * A thread primeiro cria a PMT com as informações que foram lidas do arquivo
 * de configuração e monta um pacote TS que é um template. A thread então
 * entra em loop indefinido e fica enviando os pacotes na freqüência que foi
 * configurada. Apenas o continuity_counter é incrementado.
 *
 * \param data ponteiro para os dados passados para a thread
 *
 * \todo Processar quando tiver mais de um programa.
 */
int pmt_thread(void *data)
{
	wprintf("PMT Thread Running!!\n");
	int epos_count = 0;
	
	mux_t *mux = (mux_t*)data;
	unsigned char *ptr;
	TS_pkt_t *pmt_pkts, *pkt;
	TS_hdr_t *ts; 
	PSI_section_hdr_t *section_hdr; 
	program_t *prog;
	PM_section_t pms;
	PM_t *pm;
	ES_t *es;
	int curprog;

	/* Aloca nprogs pacotes para conter a PMT de cada programa */
	pmt_pkts = (TS_pkt_t*)safe_malloc(sizeof(TS_pkt_t) * mux->conf.nprogs);

	curprog = 0;
	for (prog = mux->conf.programs; prog; prog = prog->next, curprog++) {

		ptr = pmt_pkts[curprog].data; /* ptr = início do pacote TS */
		TS_init_pkt(ptr);

		/* Monta header do pacote TS */
		ts = (TS_hdr_t*)ptr;
		ts->transport_error_indicator = 0x0;
		ts->payload_unit_start_indicator = 0x1;
		ts->transport_priority = 0x0;
		TS_set_PID(ts, prog->PID);
		ts->transport_scrambling_control = 0x0;
		ts->adaptation_field_control = 0x1;
		ts->continuity_counter = 0x0;

		ptr += 4;	/* pula header */
		ptr[0] = 0x0;	/* preencher pointer_field */
		ptr++;		/* pula pointer_field */

		/* Aponta cabeçalho da seção para o payload do pacote TS e preenche */
		section_hdr =
			(PSI_section_hdr_t*)(pmt_pkts[curprog].data + sizeof(TS_hdr_t) + ts->payload_unit_start_indicator);
		section_hdr->table_id = PSI_PM;
		section_hdr->section_syntax_indicator = 0x1;
		PSI_section_set_multi(section_hdr, prog->number);
		section_hdr->version_number = 0x0;
		section_hdr->current_next_indicator = 0x1;
		section_hdr->section_number = 0x0;
		section_hdr->last_section_number = 0x0;
	
		pms.PCR_PID = prog->PCR_PID;
	
		/* DEBUG */
		pms.pm = 0;

		for (es = prog->elementary_streams; es; es = es->next) {
		
			pm = (PM_t*)safe_malloc(sizeof(PM_t));

			pm->stream_type = es->stream_type;
			pm->elementary_PID = es->PID;		
			PM_section_addPM(&pms.pm,pm);

		}

		PSI_section_set_length(section_hdr, (9 + prog->nES * 5 + 4));
		PM_section_create((ptr + 8),&pms);
		crc32((ptr + 12 + prog->nES * 5),ptr, (12 + prog->nES * 5));
		PM_section_free(&pms);

	}

	int numiter = 0;

	while (1) {
		/*
                if(!(++numiter % 100))
                        wprintf("M");   
		*/

		for (curprog = 0; curprog < mux->conf.nprogs; curprog++) {
			/* Pega um pacote da fila */
	                wsem_wait(&mux->pkt_queue_sem);

			wpthread_mutex_lock(&mux->pkt_queue_mutex);
			pkt = dequeue_pkt(&mux->pkt_queue);
			wpthread_mutex_unlock(&mux->pkt_queue_mutex);
		
			if (pkt == NULL) {
				wprintf("m: NEVER HAPPENS\n");
			} else {
				/* Ajusta contador, copia TS e coloca na fila de saída */
				wmemcpy(pkt, &pmt_pkts[curprog], sizeof(TS_pkt_t));
				wpthread_mutex_lock(&mux->out_queue_mutex);
				queue_pkt(&mux->out_queue, pkt);
				wpthread_mutex_unlock(&mux->out_queue_mutex);
		
		                wsem_post(&mux->out_queue_sem);

				ts = (TS_hdr_t*)pmt_pkts[curprog].data;
				ts->continuity_counter++;
			}
		}

        /*      if(!(++epos_count % 4))
                        wprintf("PMT \n");

		wusleep(mux->conf.pmt_rate);
	*/
		wusleep(mux->conf.pmt_rate * 1000);

	}

	return (0);
}
