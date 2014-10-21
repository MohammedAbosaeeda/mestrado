/* $Id: readchannel.c,v 1.1.2.1 2006-02-01 18:37:17 augusto Exp $
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
 * \file channel.c
 * \brief Ponto de entrada do código que faz a leitura do arquivo de configuração do canal.
 */

#include "channel.h"


int main(int argc, char *argv[])
{
	
	channel_t channel;
	LCT_t *lc;
	LC_descriptor_t *desc;
	event_t *event;
	channel_read("../channel.conf",&channel);

	print_channel(&channel,stdout);

	channel_free(&channel);
	exit(EXIT_SUCCESS);
}

