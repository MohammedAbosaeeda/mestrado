/* $Id: psi.c,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $
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
 * \file psi.c
 * \brief Funções para manipulação de tabelas PSI.
 */

#include "psi.h"

/**
 * \fn void PA_section_addPA(PA_t **tail, PA_t *pa)
 * \brief Adiciona uma nova associação de programa à lista de associações.
 * 
 * \param tail ponteiro duplo para o final da lista
 * \param pa associação de programa a ser inserida
 *
 * \note \a (*tail)->prev aponta para o primeiro elemento da lista.
 *
 * \relatesalso PA_s
 */
void PA_section_addPA(PA_t **tail, PA_t *pa)
{
	pa->next = NULL;
	if (*tail == NULL) {
		*tail = pa;
		pa->prev = pa;
	} else {
		pa->prev = (*tail)->prev;
		pa->prev->next = pa;
		(*tail)->prev = pa;
	}
}

/**
 * \fn void PA_section_extract(unsigned char *p, PA_section_t *pas)
 * \brief Extrai uma program_association_section() do payload do pacote TS.
 *
 * \param p ponteiro para o início do payload do pacote TS
 * \param pas ponteiro para \a PA_section_t destino
 *
 * \relatesalso PA_section_s
 */
void PA_section_extract(unsigned char *p, PA_section_t *pas)
{
	uint16_t section_length, program_number, PID;
	int i;
	PA_t *pa;
	PSI_section_hdr_t *section_hdr;

	section_hdr = (PSI_section_hdr_t*)p;
	section_length = ((section_hdr->section_length_2 & 0x0f) << 8) | section_hdr->section_length_1;
	pas->pa = NULL;
	p += 8;

	/* (section_length - 9) = 5 bytes após section_length + 4 bytes do CRC */
	for (i = 0; i < (section_length - 9); i += 4) {
		program_number = (p[0] << 8) | p[1];
		PID = ((p[2] & 0x1f) << 8) | p[3];

		if (program_number == 0x0) {
			pas->network_PID = PID;
		} else {
			pa = (PA_t*)safe_malloc(sizeof(PA_t));
			pa->program_number = program_number;
			pa->program_map_PID = PID;
			PA_section_addPA(&pas->pa, pa);
		}

		p += 4;
	}

	pas->CRC_32 = (p[0] << 24) | (p[1] << 16) | (p[2] << 8) | p[3];
	if (pas->CRC_32 != crc32(NULL, p - section_length + 1, section_length - 1))
		pas->crc_error = 1;
	else pas->crc_error = 0;
}

/**
 * \fn void PA_section_create(unsigned char *p, PA_section_t *pas)
 * \brief Cria o conteúdo de uma Program Associaton Section.
 *
 * Percorre a lista de programas (PA_t) e preenche os bits na estrutura
 * apontada por \a p.
 *
 * \param p ponteiro para o payload do pacote TS
 * \param pas ponteiro para PA_section_t origem
 *
 * \relatesalso PA_section_s
 */
void PA_section_create(unsigned char *p, PA_section_t *pas)
{
	PA_t *pa;

	for (pa = pas->pa; pa; pa = pa->next) {
		p[0] = pa->program_number << 8;
		p[1] = pa->program_number;
		p[2] = (0x7 << 5) | (pa->program_map_PID >> 8);
		p[3] = pa->program_map_PID;
		p += 4;
	}
}

/**
 * \fn void PA_section_free(PA_section_t *pas)
 * \brief Libera recursos alocados pela estrutura da seção.
 *
 * Faz um free() recursivo de todas as estruturas alocadas que estão nas
 * listas. No final atribui NULL à lista de associação de programas.
 * 
 * \param pas ponteiro para a \a PA_section_t a ser liberada
 *
 * \relatesalso PA_section_s
 */
void PA_section_free(PA_section_t *pas)
{
	PA_t *pa, *next_pa;

	for (pa = pas->pa; pa; pa = next_pa) {
		next_pa = pa->next;
		wfree(pa);
	}

	pas->pa = NULL;
}

/**
 * \fn void PM_section_addPM(PM_t **tail, PM_t *pm)
 * \brief Adiciona um novo ES à lista de elementary streams de um mapa programa.
 * 
 * \param tail ponteiro duplo para o final da lista
 * \param pm associação de programa a ser inserida
 *
 * \note \a (*tail)->prev aponta para o primeiro elemento da lista.
 *
 * \relatesalso PM_s
 */
void PM_section_addPM(PM_t **tail, PM_t *pm)
{
	pm->next = NULL;

	if (*tail == NULL) {
		*tail = pm;
		pm->prev = pm;
	} else {
		pm->prev = (*tail)->prev;
		pm->prev->next = pm;
		(*tail)->prev = pm;
	}

}

/**
 * \fn void PM_section_extract(unsigned char *p, PM_section_t *pms)
 * \brief Extrai uma TS_program_map_section() do payload do pacote TS.
 *
 * \param p ponteiro para o início do payload do pacote TS
 * \param pms ponteiro para \a PM_section_t destino
 *
 * \relatesalso PM_section_s
 */
void PM_section_extract(unsigned char *p, PM_section_t *pms)
{
	uint16_t section_length;
	int i;
	PM_t *pm;
	PSI_section_hdr_t *section_hdr;

	section_hdr = (PSI_section_hdr_t*)p;
	section_length = ((section_hdr->section_length_2 & 0x0f) << 8) | section_hdr->section_length_1;
	pms->pm = NULL;
	p += 8;

	pms->PCR_PID = (p[0] >> 5) | p[1];
	/* ignora program_info_length */
	p += 4;

	/* (section_length - 13) = 5 bytes após section_length + 4 bytes antes do loop + 4 bytes do CRC */
	for (i = 0; i < (section_length - 13); i += 5) {
		pm = (PM_t*)safe_malloc(sizeof(PM_t));
		pm->stream_type = p[0];
		pm->elementary_PID = (p[1] >> 5) | p[2];
		/* ignora ES_info_length */
		PM_section_addPM(&pms->pm, pm);

		p += 5;
	}

	pms->CRC_32 = (p[0] << 24) | (p[1] << 16) | (p[2] << 8) | p[3];
	if (pms->CRC_32 != crc32(NULL, p - section_length + 1, section_length - 1))
		pms->crc_error = 1;
	else pms->crc_error = 0;
}

/**
 * \fn void PM_section_create(unsigned char *p, PM_section_t *pms)
 * \brief Cria o conteúdo de uma Program Map Section.
 *
 * Percorre a lista de ESs (PM_t) e preenche os bits na estrutura
 * apontada por \a p.
 *
 * \param p ponteiro para o payload do pacote TS
 * \param pms ponteiro para PM_section_t origem
 *
 * \relatesalso PM_section_s
 */
void PM_section_create(unsigned char *p, PM_section_t *pms)
{
	PM_t *pm;
	uint16_t program_info_length = 0x0;
	uint16_t ES_info_length = 0x0;

	p[0] = (0x7 << 5) | (pms->PCR_PID >> 8);
	p[1] = pms->PCR_PID;
	p[2] = (0xf << 4) | (program_info_length >> 8);
	p[3] = program_info_length;
	p += 4;

	for (pm = pms->pm; pm; pm = pm->next) {
		p[0] = pm->stream_type;
		p[1] = (0x7 << 5) | (pm->elementary_PID >> 8);
		p[2] = pm->elementary_PID;
		p[3] = (0xf << 4) | (ES_info_length >> 8);
		p[4] = ES_info_length;
		p += 5;
	}
}

/**
 * \fn void PM_section_free(PM_section_t *pms)
 * \brief Libera recursos alocados pela estrutura da seção.
 *
 * Faz um free() recursivo de todas as estruturas alocadas que estão nas
 * listas. No final atribui NULL à lista de mapa de programas.
 * 
 * \param pms ponteiro para a \a PM_section_t a ser liberada
 *
 * \relatesalso PM_section_s
 */
void PM_section_free(PM_section_t *pms)
{
	PM_t *pm, *next_pm;

	for (pm = pms->pm; pm; pm = next_pm) {
		next_pm = pm->next;
		wfree(pm);
	}

	pms->pm = NULL;
}

/**
 * \fn void PSI_section_set_length(PSI_section_hdr_t *section_hdr, uint16_t v)
 * \brief Seta o campo \a section_length do cabeçalho da seção PSI.
 * 
 * Preenche o 12 bits do \a section_length.
 *
 * \param section_hdr ponteiro para \a PSI_section_hdr_t
 * \param v novo valor do \a section_length
 *
 * \relatesalso PSI_section_hdr_s
 */
void PSI_section_set_length(PSI_section_hdr_t *section_hdr, uint16_t v)
{
	section_hdr->section_length_2 = v >> 8;
	section_hdr->section_length_1 = v;
}

/**
 * \fn uint16_t PSI_section_get_length(PSI_section_hdr_t *section_hdr)
 * \brief Pega o campo \a section_length do cabeçalho da seção PSI.
 *
 * \return Retorna os 12 bits do campo \a section_length.
 *
 * \relatesalso PSI_section_hdr_s
 */
uint16_t PSI_section_get_length(PSI_section_hdr_t *section_hdr)
{
	return (section_hdr->section_length_2 << 8) | section_hdr->section_length_1;
}

/**
 * \fn void PSI_section_set_multi(PSI_section_hdr_t *section_hdr, uint16_t v)
 * \brief Seta o campo \a multi do cabeçalho da seção PSI.
 *
 * Preenche os 16 bits do campo \a multi, que está na área de bits que pode
 * representar múltiplos campos, dependendo da seção em uso. Ele pode ser
 * transport_stream_id, reserved, program_number ou table_id_extesion.
 *
 * \param section_hdr ponteiro para PSI_section_hdr_t
 * \param v novo valor do transport_stream_id
 *
 * \relatesalso PSI_section_hdr_s
 */
void PSI_section_set_multi(PSI_section_hdr_t *section_hdr, uint16_t v)
{
	section_hdr->multi_2 = v >> 8;
	section_hdr->multi_1 = v;
}

/**
 * \fn uint16_t PSI_section_get_multi(PSI_section_hdr_t *section_hdr)
 * \brief Pega o campo \a multi do cabeçalho da seção PSI.
 *
 * O campo \a multi pode representar diversos campos diferentes. Ver
 * PSI_section_set_multi().
 *
 * \return Retorna os 16 bits do campo multi.
 *
 * \relatesalso PSI_section_hdr_s
 */
uint16_t PSI_section_get_multi(PSI_section_hdr_t *section_hdr)
{
	return (section_hdr->multi_2 << 8) | section_hdr->multi_1;
}

