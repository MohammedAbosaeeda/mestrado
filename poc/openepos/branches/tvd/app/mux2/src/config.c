/* $Id: config.c,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $
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
 * \file config.c
 * \brief Rotinas de tratamento da leitura da configuração do sistema.
 */

#include "config.h"
#include "utils.h"

static config_t *work_config;	/**< config_t que está atualmente sendo
				     utilizada pelo parser */

/**
 * \fn static void config_addprogram(program_t **tail, program_t *prog)
 * \brief Adiciona um novo programa à lista de programas.
 * 
 * \param tail ponteiro duplo para o final da lista
 * \param prog programa a ser inserido
 *
 * \note \a (*tail)->prev aponta para o primeiro elemento da lista.
 *
 * \relatesalso program_s
 */
static void config_addprogram(program_t **tail, program_t *prog)
{
	prog->next = NULL;
	if (*tail == NULL) {
		*tail = prog;
		prog->prev = prog;
	} else {
		prog->prev = (*tail)->prev;
		prog->prev->next = prog;
		(*tail)->prev = prog;
	}
}

/**
 * \fn static void config_addes(ES_t **head, ES_t *new)
 * \brief Adiciona uma nova ES à lista de ESs de um programa.
 * 
 * \param head ponteiro duplo para o header da lista
 * \param new nova ES a ser inserida
 *
 * \relatesalso ES_s
 */
static void config_addes(ES_t **head, ES_t *new)
{
	if (*head == NULL) {
		*head = new;
		new->next = NULL;
		new->prev = new;
	} else {
		new->next = NULL;
		new->prev = (*head)->prev;
		new->prev->next = new;
		(*head)->prev = new;
	}
}

/**
 * \fn int config_read(char *filename, config_t *conf)
 * \brief Lê a configuração de um arquivo e armazena em memória.
 *
 * Recebe como parâmetro um ponteiro para a estrutura \a config_t que será
 * preenchida com a configuração lida do arquivo.
 * 
 * \param filename nome do arquivo de entrada
 * \param conf ponteiro para \a config_t destino
 *
 * \relatesalso config_s
 */

/* 
	fake config read
	estrutura transcrita de um .conf qualquer
        libxml não entra no epos!
*/

void config_read(char *filename, config_t *conf) {


        conf->programs = NULL;
        conf->nprogs = 0;

        conf->ts_id = 0x1234;

        char * outfile = "xa";
        wstrncpy(conf->ts_output, outfile, CONFIG_TEXT_MAXLEN);

        conf->pat_rate = 250;

        conf->pmt_rate = 250;

        program_t * new_program = (program_t*)safe_malloc(sizeof(program_t));

        new_program->number = 1;
        new_program->PID = 0x20;
        new_program->PCR_PID = 40;

        new_program->nES = 0;
        new_program->elementary_streams = NULL;

        config_addprogram(&conf->programs, new_program);
        conf->nprogs++;

        ES_t * new_es = (ES_t*)safe_malloc(sizeof(ES_t));
        new_es->PID = 0x40;
        new_es->stream_type = 0x02;

        char * sourcefile = "xu";

        wstrncpy(new_es->source, sourcefile, CONFIG_TEXT_MAXLEN);

        config_addes(&new_program->elementary_streams, new_es);

        new_program->nES++;

}


/**
 * \fn void config_free(config_t *conf)
 * \brief Libera recursos alocados pela estrutura de configuração.
 *
 * Faz um free() recursivo de todas as estruturas alocadas que estão nas
 * listas da configuração. No final atribui NULL à lista de programas.
 * 
 * \param conf ponteiro para a \a config_t que guarda a configuração
 *
 * \relatesalso config_s
 */
void config_free(config_t *conf)
{
	program_t *prog, *next_prog;
	ES_t *es, *next_es;

	for (prog = conf->programs; prog; prog = next_prog) {
		for (es = prog->elementary_streams; es; es = next_es) {
			next_es = es->next;
			wfree(es);
		}
		next_prog = prog->next;
		wfree(prog);
	}

	conf->programs = NULL;
}

