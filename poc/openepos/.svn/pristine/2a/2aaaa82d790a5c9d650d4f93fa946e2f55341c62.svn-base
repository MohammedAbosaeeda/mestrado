/* $Id: utils.c,v 1.1.2.1 2006-02-01 18:39:06 augusto Exp $
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
 * \file utils.c
 * \brief Funções diversas para manipulação de bits, dump de estruturas, etc.
 */

#include "ts.h"
#include "psi.h"
#include "utils.h"
#include "config.h"

/**
 * \fn void die(char *format, ...)
 * \brief Sai do programa mostrando mensagem.
 *
 * Imprime a mensagem na saída de erro padrão (stderr) seguida de uma linha
 * em branco, saindo do programa com status de erro.
 *
 * \param format formato da mensagem de saída no estilo printf
 */
void die(char *format)
{
	wdie(format);
}

/**
 * \fn void *safe_malloc(size_t size)
 * \brief Aloca \a size bytes e retorna um ponteiro para a memória alocada.
 *
 * Se a memória não pode ser alocada, chama die() e sai do programa.
 *
 * \param size número de bytes a serem alocados
 *
 * \return Ponteiro para a área de memória alocada
 */
void *safe_malloc(size_t size)
{
	void *p = (void*)wmalloc(size);
	if (p == NULL) {
		die("falha ao alocar memória!");
		while(1);
	}
	return p;
}

/**
 * \fn unsigned int *axtoi(const char *digits)
 * \brief Converte uma string hexa para um valor decimal inteiro.
 *
 * \param digits string contendo os caracteres hexa
 *
 * \return Valor decimal inteiro da representação hexa
 */
unsigned int axtoi(const char *digits)
{
	unsigned int retval, i;

	if(!wstrncmp(digits, "0x",2))
		digits += 2;
	
	i = retval = 0;
	while ((digits[i] >= '0' && digits[i] <= '9') || (digits[i] >= 'A' && digits[i] <= 'F')) {
		retval = retval * 16;
		retval += (unsigned int)((digits[i] > '9') ? digits[i] - 0x37 : digits[i] - 0x30);
		i++;
	}
	
	return retval;
}

/**
 * \fn void dump_bits(unsigned char *p, size_t c)
 * \brief Faz dump binário de estrutura de dados.
 *
 * Imprime todos os bits da estrutura alinhados em bytes.
 *
 * \param p ponteiro para a estrutura
 * \param c tamanho da estrutura
 */

/*

void dump_bits(unsigned char *p, size_t c)
{
	int i, j;

	printf("   01234567\n   --------\n");
	for (i = 0; i < c; i++) {
		printf("%2d ", i);
		for (j = 7; j >= 0; j--)
			printf("%d", (p[i] >> j) & 0x1);
		printf("  0x%x (%d)\n", p[i], p[i]);
	}
	printf("\n");
}
*/

/**
 * \fn dump_config(config_t *conf)
 * \brief Faz dump da configuração do sistema.
 *
 * \param conf ponteiro para config_t que contém a configuração
 */

/*
void dump_config(config_t *conf)
{
	program_t *prog;
	ES_t *es;
	
	printf("ts\n");
	printf("  id = 0x%04X\n", conf->ts_id);
	printf("  output = %s\n", conf->ts_output);
	printf("  pat rate = %d\n", conf->pat_rate);
	printf("  pmt rate = %d\n", conf->pat_rate);
	for (prog = conf->programs; prog; prog = prog->next) {
		printf("  program\n");
		printf("    number = %d\n", prog->number);
		printf("    PID = 0x%04x\n", prog->PID);
		printf("    PCR_PID = 0x%04x\n", prog->PCR_PID);
		for (es = prog->elementary_streams; es; es = es->next) {
			printf("      es\n");
			printf("        stream_type = 0x%02x\n", es->stream_type);
			printf("        PID = 0x%02x\n", es->PID);
			printf("        source = %s\n", es->source);
		}
	}
	printf("\n");
}
*/
