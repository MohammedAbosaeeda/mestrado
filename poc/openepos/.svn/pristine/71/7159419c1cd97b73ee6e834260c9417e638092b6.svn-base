/* $Id: pat.c,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $
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
 * \file pat.c
 * \brief Implementação da thread que gera a Program Association Table (PAT).
 */

#include "mux.h"

/**
 * \fn void pat_thread(void *data)
 * \brief Thread de geração de Program Association Table (PAT).
 *
 * A thread primeiro cria a PAT com as informações que foram lidas do arquivo
 * de configuração e monta um pacote TS que é um template. A thread então
 * entra em loop indefinido e fica enviando os pacotes na freqüência que foi
 * configurada. Apenas o continuity_counter é incrementado.
 *
 * \todo Só são considerados casos onde a PAT ocupa um pacote TS. No futuro
 * deve existir suporte a tabelas cujas seções ocupam mais de um pacote.
 *
 * \param data ponteiro para os dados passados para a thread
 */
int pat_thread(void *data)
{
	wprintf("PAT Thread Running!!\n");
	int epos_count = 0;

	mux_t *mux = (mux_t*)data;
	TS_pkt_t pat_pkt, *pkt;
	TS_hdr_t *ts; 
	PSI_section_hdr_t *section_hdr;

	PA_section_t pas;
	PA_t *pa;
	program_t *prog;
	int nprogs = 0;

	unsigned char *ptr = pat_pkt.data; /* ptr = início do pacote TS */
	TS_init_pkt(ptr);
	pas.pa = NULL;

	/* Monta header do pacote TS */
	ts = (TS_hdr_t*)ptr;
	ts->transport_error_indicator = 0x0;
	ts->payload_unit_start_indicator = 0x1;
	ts->transport_priority = 0x0;
	TS_set_PID(ts, TS_PAT_PID);
	ts->transport_scrambling_control = 0x0;
	ts->adaptation_field_control = 0x1;
	ts->continuity_counter = 0x0;

	ptr += 4;	/* pula header */
	ptr[0] = 0x0;	/* preencher pointer_field */
	ptr++;		/* pula pointer_field */

	/* Aponta cabeçalho da seção para o payload do pacote TS e preenche */
	section_hdr =
		(PSI_section_hdr_t*)(pat_pkt.data + sizeof(TS_hdr_t) + ts->payload_unit_start_indicator);
	section_hdr->table_id = PSI_PA;
	section_hdr->section_syntax_indicator = 0x1;
	PSI_section_set_multi(section_hdr, mux->conf.ts_id);
	section_hdr->version_number = 0x0;
	section_hdr->current_next_indicator = 0x1;
	section_hdr->section_number = 0x0;
	section_hdr->last_section_number = 0x0;

	/* Cria lista de associação de programas de acordo com a configuração */
	for (prog = mux->conf.programs; prog; prog = prog->next) {
		pa = (PA_t*)safe_malloc(sizeof(PA_t));
		pa->program_number = prog->number;
		pa->program_map_PID = prog->PID;
		PA_section_addPA(&pas.pa, pa);
		nprogs++;
	}

	PSI_section_set_length(section_hdr,	/* Tamanho da seção = 5 bytes + nprogs * 4 + */
			(5 + nprogs * 4 + 4));	/* 4 bytes do CRC32 */
	PA_section_create((ptr + 8), &pas);	/* Cria a seção em ptr + 8, pulando o cabeçalho da seção */
	crc32((ptr + 8 + nprogs * 4), ptr,	/* CRC32 vai nos últimos 4 bytes e é feito */
			(8 + nprogs * 4));	/* sobre toda seção */
	PA_section_free(&pas);

	/* DEBUG */
	int numiter = 0;

	while (1) {
		
		/*
		if(!(++numiter % 100))
			wprintf("A");	
		*/

		/* Pega um pacote da fila de pacotes livres */
                wsem_wait(&mux->pkt_queue_sem);

		wpthread_mutex_lock(&mux->pkt_queue_mutex);
		pkt = dequeue_pkt(&mux->pkt_queue);
		wpthread_mutex_unlock(&mux->pkt_queue_mutex);

		if (pkt == NULL) {
			wprintf("a: NEVER HAPPENS\n");
		} else {
			/* Copia TS e coloca na fila de saída e incrementa cc */

			wmemcpy(pkt, &pat_pkt, sizeof(TS_pkt_t));
			wpthread_mutex_lock(&mux->out_queue_mutex);
			queue_pkt(&mux->out_queue, pkt);
			wpthread_mutex_unlock(&mux->out_queue_mutex);

	                wsem_post(&mux->out_queue_sem);


			ts->continuity_counter++;
		}
/*
		if(!(++epos_count % 4))
			wprintf("PAT 4x\n");
		wusleep(mux->conf.pat_rate);
*/
		wusleep(mux->conf.pat_rate * 1000);

	}

	return (0);
}

