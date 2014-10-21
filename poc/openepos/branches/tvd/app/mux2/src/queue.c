/* $Id: queue.c,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $
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
 * \file queue.c
 * \brief Funções para manipulação de filas de pacotes TS (política FIFO).
 */

#include "queue.h"

/**
 * \fn void queue_pkt(TS_pkt_t **tail, TS_pkt_t *pkt)
 * \brief Enfileira um pacote.
 * 
 * \param tail ponteiro duplo para o início da fila
 * \param pkt pacote a ser enfileirado
 *
 * \relatesalso TS_pkt_s
 */
void queue_pkt(TS_pkt_t **tail, TS_pkt_t *pkt)
{
	pkt->next = *tail;
	if (*tail == NULL) {
		pkt->prev = pkt;
	} else {
		pkt->prev = (*tail)->prev;
		(*tail)->prev = pkt;
	}
	*tail = pkt;
}

/**
 * \fn TS_pkt_t *dequeue_pkt(TS_pkt_t **tail)
 * \brief Desenfileira um pacote.
 * 
 * \param tail ponteiro duplo para o início da fila
 *
 * \return Ponteiro para o pacote desenfileirado ou NULL se fila vazia.
 * 
 * \relatesalso TS_pkt_s
 */
TS_pkt_t *dequeue_pkt(TS_pkt_t **tail)
{
	TS_pkt_t *pkt;

	if (*tail == NULL)
		return NULL;

	pkt = (*tail)->prev;

	/* Se tem só 1 elemento na fila */
	if (*tail == (*tail)->prev) {
		*tail = NULL;
	} else {
		(*tail)->prev->prev->next = NULL;
		(*tail)->prev = (*tail)->prev->prev;
	}

	return pkt;
}

/**
 * \fn void queue_init(TS_pkt_t **tail, size_t len)
 * \brief Inicializa uma fila de pacotes.
 * 
 * \param tail ponteiro duplo para o início da fila
 * \param len número de nodos iniciais na lista
 *
 * \relatesalso TS_pkt_s
 */
void queue_init(TS_pkt_t **tail, size_t len)
{
	TS_pkt_t *pkt;

	*tail = NULL;
	for (; len > 0; len--) {
		pkt = (TS_pkt_t*)safe_malloc(sizeof(TS_pkt_t));
		queue_pkt(tail, pkt);
	}
}

