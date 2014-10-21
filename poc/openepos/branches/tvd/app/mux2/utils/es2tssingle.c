/* $Id: es2tssingle.c,v 1.1.2.1 2006-02-01 18:39:33 augusto Exp $
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
 * \file es2tssingle.c
 * \brief Programa de simulação de TS Single.
 * 
 * \par Utilização:
 * es2tssingle (a|v) arquivo_ES arquivo_PTS arquivo_TS
 *
 * \param a/v Indica se a TS é áudio (a) ou vídeo (v)
 * \param arquivo_ES Arquivo binário contendo a ES
 * \param arquivo_PTS Arquivo texto contendo o PTS para cada frame (um número de 32 bits por linha!)
 * \param arquivo_TS Arquivo a ser gerado contendo a TS Single
*/

#include <stdio.h>
#include "ts.h"

unsigned char TS[TS_PKT_LEN];
unsigned char visor[4];	/**< Visor na es, utilizado para encontrar start_codes */
int numts = 0;

/**
 * \fn unsigned char desloca_visor(FILE *es)
 * \brief Desloca visor 1 byte para a esquerda.
 *
 * \param es Descritor da stream de entrada do ES
 *
 * \return Byte deslocado
 */
unsigned char desloca_visor(FILE *es)
{
	unsigned char c;

	c = visor[0];
	visor[0] = visor[1];
	visor[1] = visor[2];
	visor[2] = visor[3];
	visor[3] = fgetc(es);

	return c;
}

/**
 * \fn int inclui_pts(FILE *pts)
 * \brief Preenche campo PTS no PES.
 *
 * \param pts Descritor do arquivo com os PTS
 *
 * \retval 0 Sucesso
 * \retval 1 EOF prematuro de \a pts
 *
 * \note O PTS possui 33 bits mas, para facilitar, são utilizados apenas
 * 32 bits. O 33o bit será sempre 0 (não fazer isso em HW).
 * 
 * \warning Funciona só em sistemas little-endian.
 */
int inclui_pts(FILE *pts)
{
	char pts_str[200];
	unsigned long pts_val;
	int i;

	if (feof(pts)) return 1;
	fgets(pts_str, 200, pts);
	pts_val = atol(pts_str);
	i = pts_val >> 30;				// bits 32..30
	TS[13] = (i << 1) + 0x21;			// '0010' + bits 32..30 + markbit
	TS[14] = (pts_val & 0x3fc00000) >> 22;		// bits 29..22
	TS[15] = ((pts_val & 0x3f8000) >> 14) + 1;	// bits 21..15 + markbit
	TS[16] = (pts_val & 0x7f80) >> 7;		// bits 15..8
	TS[17] = ((pts_val & 0x7f) << 1) + 1;		// bits 7..1 + markbit

	return 0;
}

/**
 * \fn int search_start_h262(FILE *es)
 * \brief Procura sequence_header em h262.
 *
 * \param es Descritor da stream de entrada do ES
 *
 * \retval 0 em caso EOF de \a es
 * \retval 1 caso contrário
 */
int search_start_h262(FILE * es)
{
	char c;
	unsigned char code[4] = { 0x00, 0x00, 0x01, 0xB3 };

	while (memcmp(visor, code, 4) && !feof(es)) {
		//printf("%.2x %.2x %.2x %.2x\n", visor[0], visor[1],visor[2],visor[3]);
		desloca_visor(es);
	}

	if (feof(es))
		return 0;
	else
		return 1;
}

/**
 * \fn int frame_start_h262()
 * \brief Compara o visor com o start_code do picture_header do h262.
 *
 * \retval TRUE start_code encontrado
 * \retval FALSE start_code não encontrado
 */
int frame_start_h262()
{
	unsigned char code[4] = { 0x00, 0x00, 0x01, 0x00 };
	return memcmp(visor, code, 4) == 0;
}

/**
 * \fn void gera_log()
 * \brief Gera log com dump hexa do pacote TS.
 */
void gera_log()
{
	int f;
	printf("***** dump TS %d \n", numts++);
	for (f = 0; f < TS_PKT_LEN; f++)
		printf("%.2X ", TS[f]);
	printf("\n\n");
}

int main(int argc, char *argv[])
{

	FILE *es;
	FILE *ts;
	FILE *pts;
	char start_pes;			// Usado para verificar inicio de uma picture (1 bit)
	int achou_picture;
	int numbytes;
	TS_hdr_t *ts_hdr = (TS_hdr_t*)&TS;

	if (argc != 5) {
		printf("%s (a|v) arquivo_ES arquivo_PTS arquivo_TS\n", argv[0]);
		return 1;
	}

	es = fopen(argv[2], "rb");
	if (!es) {
		printf("arquivo <%s> nao pode ser aberto\n", argv[2]);
		return 1;
	}

	pts = fopen(argv[3], "r");
	if (!pts) {
		printf("arquivo <%s> nao pode ser aberto\n", argv[3]);
		return 1;
	}

	ts = fopen(argv[4], "wb");
	if (!ts) {
		printf("arquivo <%s> nao pode ser aberto\n", argv[4]);
		return 1;
	}

	fread(visor, 1, 4, es);	// enche o visor com dados
	// posiciona a ES no primeiro sequence header que achar (apenas no caso de video)
	// isso eh necessario para eliminar sujeiras...
	// Para H.264, isto eh diferente... Preciso da 13818-1/Amd3
	if (argv[1][0] == 'v')
		if (!search_start_h262(es)) {
			printf("sequence_header nao encontrado na stream!\n");
			return 1;
		}

	start_pes = 1;
	TS_init_pkt(TS);
	// varre todo o ES
	while (!feof(es)) {
		if (start_pes)
			ts_hdr->payload_unit_start_indicator = 1;
		else
			ts_hdr->payload_unit_start_indicator = 0;
		if (argv[1][0] == 'a')
			TS_set_PID(ts_hdr, 0x21); // PID de áudio
		else
			TS_set_PID(ts_hdr, 0x20); // PID de vídeo
		if (start_pes || ts_hdr->continuity_counter >= 15)
			ts_hdr->continuity_counter = 0;
		else
			ts_hdr->continuity_counter++;
		numbytes = 4;

		if (start_pes) {
			// inclui cabecalho PES
			TS[4] = 0x00;
			TS[5] = 0x00;
			TS[6] = 0x01;	// start
			if (argv[1][0] == 'a')
				TS[7] = 0xC0;	// PID de audio 
			else
				TS[7] = 0xE0;	// PID de video 
			// header sempre 0, pois eh indiferente o tamanho         
			TS[8] = 0x00;
			TS[9] = 0x00;
			// data_align=1, original=1, PTS=1
			TS[10] = 0x85;
			TS[11] = 0x80;
			TS[12] = 0x05;	// tamanho (sempre fixo pois eh o tamanho do PES)
			if (inclui_pts(pts))
				return 1;
			numbytes = 18;
		}
		// coloca o ES no payload do TS
		if (start_pes) {
			// se estiver no inicio de uma PES, deve se colocar dados ate 
			// pular um picture_header. 
			while (!frame_start_h262() && numbytes < TS_PKT_LEN)
				TS[numbytes++] = desloca_visor(es);
			if (numbytes >= TS_PKT_LEN) {
				// isso nunca eh para acontecer em streams normais, mas sempre eh bom verificar...
				printf("FALHA! picture_header deveria estar dentro do PES!\n");
				return 1;
			}
		}
		// continua avancando ate chegar um picture ou encher o TS
		start_pes = 0;
		TS[numbytes++] = desloca_visor(es);
		while (!frame_start_h262() && numbytes < TS_PKT_LEN && !feof(es))
			TS[numbytes++] = desloca_visor(es);
		if (numbytes < TS_PKT_LEN) {
			// deve ter achado um picture ou terminou o arquivo...
			// mas ainda nao acabou de encher a TS, coloca "00" para preencher
			start_pes = 1;
			while (numbytes < TS_PKT_LEN)
				TS[numbytes++] = 0;
		}

		gera_log();
		fwrite(TS, 1, TS_PKT_LEN, ts);
	}

	fclose(es);
	fclose(pts);
	fclose(ts);
	return 0;
}
