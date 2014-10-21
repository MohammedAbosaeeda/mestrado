/* $Id: main.c,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $
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
 * \file main.c
 * \brief Ponto de entrada do código.
 */

#include "MuxWrapper.h"
#include "mux.h"

mux_t mux; /**< estrutura principal de controle do mux */

int mux_main()
{
	wprintf("Mux code running!\n");

	wpthread_t *thread;
	program_t *prog;
	ES_t *es;
	es_args_t *args;
	
	mux_init(&mux);
	config_read("../mux.conf", &mux.conf);

	/* Cria thread da PMT */
	wpthread_create(pmt_thread, (void *) &mux);

	/* Cria thread da PAT */
	wpthread_create(pat_thread, (void *) &mux);

	/* Cria thread de saída */
	wpthread_create(output_thread, (void *) &mux);
	
	for (prog = mux.conf.programs; prog; prog = prog->next) {
		for (es = prog->elementary_streams; es; es = es->next) {
			
			args = (es_args_t*)safe_malloc(sizeof(es_args_t));
			args->mux = &mux;
			args->es = es;
			args->is_pcr = es->PID == prog->PCR_PID;
			
			/* Cria Thread para cada ES */ 
			wpthread_create(es_thread, (void *) args);
		}
	}

	wprintf("Main looping!\n");
	
	/*while(1);*/

	/*config_free(&mux.conf);*/

	return(0);
}

