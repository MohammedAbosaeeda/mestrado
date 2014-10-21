/* $Id: channel.c,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $
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
 * \brief Processa arquivo de informações sobre o canal. 
 */

#include <stdio.h>
#include "channel.h"
#include "utils.h"
LIBXML_UNICODE_ENABLED

static channel_t *work_channel; 
static xmlDoc *doc;


/**
 * \fn static void LCT_add(LCT_t **tail, LCT_t *lct)
 * \brief Adiciona uma LCT a uma lista de LCT.
 *
 * \param tail ponteiro duplo para o final da lista
 * \param lct LCT a ser inserido
 *
 * \note \a (*tail)->prev aponta para o primeiro elemento da lista.
 *
 * \relatesalso LCT_s
 */
static void LCT_add(LCT_t **tail, LCT_t *lct)
{
	lct->next = NULL;
	if (*tail == NULL) {
		*tail = lct;
		lct->prev = lct;
	} else {
		lct->prev = (*tail)->prev;
		lct->prev->next = lct;
		(*tail)->prev = lct;
	}
}

/**
 * \fn static void Descriptor_add(LC_descriptor_t **tail, LC_descriptor_t *lct)
 * \breif Adiciona um descritor na lista de descritores.
 *
 * \param tail ponteiro duplo para o final da lista
 * \param desc descritor a ser inserido.
 *
 * \relatesalso LC_descriptor_s
 */
static void Descriptor_add(LC_descriptor_t **tail, LC_descriptor_t *desc){
	desc->next = NULL;
	if (*tail == NULL) {
		*tail = desc;
		desc->prev = desc;
	} else {
		desc->prev = (*tail)->prev;
		desc->prev->next = desc;
		(*tail)->prev = desc;
	}
}

/**
 * \fn static void Event_add(event_t **tail, event_t *event)
 * \brief Adiciona um eventos em uma lista de eventos 
 *
 * \param tail ponteiro duplo para o final da lista
 * \param event evento a ser inserido
 * 
 * \relatesalso event_s 
 */
static void Event_add(event_t **tail, event_t *event)
{
	event->next = NULL;
	if (*tail == NULL) {
		*tail = event;
		event->prev = event;
	} else {
		event->prev = (*tail)->prev;
		event->prev->next = event;
		(*tail)->prev = event;
	}

}

/**
 * \fn static void parser_event(xmlNode *node, LC_descriptor_t *lcd)
 * \brief Faz o parser dos eventos e inclui na lista de eventos de um descritor.
 *
 * \param node nodo XML que esta sendo processado 
 * \param lcd Estrutura que contem as informações do descritor
 *
 * \relatesalso LC_descriptor_s 
 */
static void parser_event(xmlNode *node, LC_descriptor_t *lcd)
{
	xmlNode *cur_node = NULL;
	event_t *new_event;

	new_event = (event_t*)safe_malloc(sizeof(event_t));
	new_event->id = atoi(xmlGetProp(node,"id"));
	new_event->flags = atoi(xmlGetProp(node,"hidden")) << 4;
	new_event->description = NULL;
	new_event->next = NULL;
	new_event->prev = NULL;
	Event_add(&lcd->event,new_event);

	for(cur_node = node->children; cur_node; cur_node = cur_node->next) {
		if(cur_node->type == XML_ELEMENT_NODE){
			if(!xmlStrcmp(cur_node->name, (const xmlChar*)"date"))
				new_event->date = mjd(atoi(xmlNodeListGetString(doc, cur_node->xmlChildrenNode,1))); 
			else if(!xmlStrcmp(cur_node->name, (const xmlChar*)"starttime"))
				new_event->starttime = atoi(xmlNodeListGetString(doc, cur_node->xmlChildrenNode,1));
			else if(!xmlStrcmp(cur_node->name, (const xmlChar*)"duraction"))
				new_event->duraction = atoi(xmlNodeListGetString(doc, cur_node->xmlChildrenNode,1));
			else if(!xmlStrcmp(cur_node->name, (const xmlChar*)"program_type"))
				new_event->program_type = atoi(xmlNodeListGetString(doc, cur_node->xmlChildrenNode,1));
			else if(!xmlStrcmp(cur_node->name, (const xmlChar*)"classification"))
				new_event->classification = atoi(xmlNodeListGetString(doc, cur_node->xmlChildrenNode,1));
			else if(!xmlStrcmp(cur_node->name, (const xmlChar*)"description"))
				new_event->description = xmlNodeListGetString(doc, cur_node->xmlChildrenNode,1);

		}
	}
}


/**
 * \fn static void parser_descriptor(xmlNode *node, LCT_t *lct)
 * \brief Faz o parser do descriptor e armazena numa LCT. 
 * 
 * \param node nodo XML que esta sendo processado 
 * \param lct Estrutura que contem as informações da LCT
 *
 * \relatesalso LCT_s 
 */
static void parser_descriptor(xmlNode *node, LCT_t *lct)
{
	xmlNode *cur_node = NULL;
	LC_descriptor_t *new_descriptor;
	xmlChar *aux;

	new_descriptor = (LC_descriptor_t*)safe_malloc(sizeof(LC_descriptor_t));
	new_descriptor->ID = atoi(xmlGetProp(node,"ID"));
	Descriptor_add(&lct->lc_descriptor,new_descriptor);
	new_descriptor->full_name = NULL;
	new_descriptor->flags = 0xC;
	new_descriptor->event = NULL;
	
	for (cur_node = node->children; cur_node; cur_node = cur_node->next) {
		if (cur_node->type == XML_ELEMENT_NODE) {
			if (!xmlStrcmp(cur_node->name, (const xmlChar*)"content_type")){
				new_descriptor->content_type = atoi(xmlNodeListGetString(doc,cur_node->xmlChildrenNode,1)); 
			}
			else if (!xmlStrcmp(cur_node->name, (const xmlChar*)"event")){
				parser_event(cur_node,new_descriptor);
			}
			else if (!xmlStrcmp(cur_node->name, (const xmlChar*)"channel_type")){
				new_descriptor->channel_type = atoi(xmlNodeListGetString(doc,cur_node->xmlChildrenNode,1)); 
			}
		
			else if (!xmlStrcmp(cur_node->name, (const xmlChar*)"short_name")){
				new_descriptor->short_name = xmlNodeListGetString(doc,cur_node->xmlChildrenNode,1); 
			}

			else if (!xmlStrcmp(cur_node->name, (const xmlChar*)"full_name")){
				new_descriptor->full_name = xmlNodeListGetString(doc,cur_node->xmlChildrenNode,1); 
			}
	
			else if (!xmlStrcmp(cur_node->name, (const xmlChar*)"show_epg")){
				if(atoi(xmlNodeListGetString(doc,cur_node->xmlChildrenNode,1)))
					new_descriptor->flags |= 0x8;
				else
					new_descriptor->flags &= 0x7;
			}
			else if (!xmlStrcmp(cur_node->name, (const xmlChar*)"show_portal")){
				if(atoi(xmlNodeListGetString(doc,cur_node->xmlChildrenNode,1)))
					new_descriptor->flags |= 0x4;
				else
					new_descriptor->flags &= 0xB;
			}
			else if (!xmlStrcmp(cur_node->name, (const xmlChar*)"ca_mode")){
				if(atoi(xmlNodeListGetString(doc,cur_node->xmlChildrenNode,1)))
					new_descriptor->flags |= 0x2;
				else
					new_descriptor->flags &= 0xD;
			}
		}
	}
}

/**
 * \fn static void parser_logical_channel(xmlNode *node) 
 * \brief
 */
static void parser_logical_channel(xmlNode *node)
{
	xmlNode *cur_node = NULL;
	LCT_t *new_lc;

	new_lc = (LCT_t*)safe_malloc(sizeof(LCT_t)); 
	new_lc->ID = atoi(xmlGetProp(node,"ID"));
	new_lc->num_descriptor=0;
	new_lc->lc_descriptor = NULL;
	LCT_add(&work_channel->logical_channel,new_lc);

	for (cur_node = node->children; cur_node; cur_node = cur_node->next) {
		if (cur_node->type == XML_ELEMENT_NODE) {
			if (!xmlStrcmp(cur_node->name, (const xmlChar*)"descriptor")){
				new_lc->num_descriptor++;
				parser_descriptor(cur_node, new_lc);
			}
		}
	}
}

/**
 * \fn static void parser_channel(xmlNode *node)
 * \brief Percorre o arquivo XML.
 *
 * \param node Nodo do XML que será feito o parser.
 */
static void parser_channel(xmlNode *node)
{
	xmlNode *cur_node = NULL;

	for (cur_node = node->children; cur_node; cur_node = cur_node->next) {
		if (cur_node->type == XML_ELEMENT_NODE) {
			if (!xmlStrcmp(cur_node->name, (const xmlChar*)"logical_channel")){
				work_channel->num_logical_channel++;
				parser_logical_channel(cur_node);
			}
			else die("channel: tag de canal inválida (%s).", cur_node->name);
		}
	}
}

/**
 * \fn void channel_read(char *filename)
 * \brief Lê a configuração do canal de um arquivo e armazena em memória.
 *
 * Recebe como parâmetro um ponteiro para a estrutura \a channel_t que será
 * preenchida com a configuração lida do arquivo.
 *
 * \param filename nome do arquivo de entrada
 * \param channel ponteiro para \a channel_t destino
 *
 * \relatesalso channel_s
 */
void channel_read(char *filename,channel_t *channel)
{
	xmlNode *root_element=NULL;
	xmlNode *cur_node=NULL;

	doc=NULL;

	LIBXML_TEST_VERSION

	if ((doc = xmlReadFile(filename, NULL,0)) == NULL) {
		die("channel: Impossível fazer parse do arquivo de canais(%s)\n",filename);
	}

	root_element = xmlDocGetRootElement(doc);
	if (xmlStrcmp(root_element->name, (const xmlChar*)"channel")) {
		die("channel: erro no arquivo de canais(%s)\n", filename);
	}

	work_channel = channel;
	work_channel->num_logical_channel = 0;
	work_channel->logical_channel = NULL;

	parser_channel(root_element);

	xmlFreeDoc(doc);
	xmlCleanupParser();	

}

/**
 * \fn void channel_free(channel_t *channel)
 * \brief Libera recursos alocados pela estrutura de channel.
 *
 * Faz um free() recursivo de todas as estruturas alocadas que estão nas
 * listas do channel. 
 * 
 * \param channel ponteiro para a \a channel_t que guarda a configuração do canal
 *
 * \relatesalso channel_s
 */
void channel_free(channel_t *channel)
{	
	LCT_t *lc;
	LC_descriptor_t *desc;
	event_t *event;
		
	for(lc = channel->logical_channel;lc != NULL; lc = lc->next){
		for(desc = lc->lc_descriptor; desc != NULL; desc = desc->next){
			for(event = desc->event; event != NULL; event = event->next)
				free(event);
			free(desc);
		}
		free(lc);
	}
	channel->logical_channel = NULL;
}

/**
 * \fb void channel_print(channel_t *channel,FILE *out)
 * \brief Percorre todo o \a channel_t e mostra as informações em out 
 *
 * \param channel ponteiro para a \a channel_t que contem as informações do canal
 * \param out saida que será enviada as informações
 *
 * \relatesalso channel_s
 */
void channel_print(channel_t *channel, FILE *out)
{
	LCT_t *lc;
	LC_descriptor_t *desc;
	event_t *event;

	fprintf(out,"Channel:\n");
	fprintf(out,"Numero de canais lógicos: %d\n",channel->num_logical_channel);
	fprintf(out,"--------------------------------------------------------\n");

	for(lc = channel->logical_channel;lc != NULL; lc = lc->next){
		fprintf(out,"Canal Logico: %d\n",lc->ID);
		for(desc = lc->lc_descriptor; desc != NULL; desc = desc->next){
			fprintf(out,"\tID: %d\n",desc->ID);
			fprintf(out,"\tFlags: 0x%X\n",desc->flags);
			fprintf(out,"\tContent type: %d\n",desc->content_type);
			fprintf(out,"\tChannel type: %d\n",desc->channel_type);
			fprintf(out,"\tChannel short name: %s\n",desc->short_name);
			fprintf(out,"\tChannel full name: %s\n",desc->full_name);
			fprintf(out,"\t------------------------\n");
			fprintf(out,"\tEventos:\n");
			for(event = desc->event; event != NULL; event = event->next){
				fprintf(out,"\t\tEvent ID: %d\n",event->id);
				fprintf(out,"\t\tDate(mjd): %d\n",event->date);
				fprintf(out,"\t\tDuraction: %d\n",event->duraction);
				fprintf(out,"\t\tProgram type: %d\n",event->program_type);
				fprintf(out,"\t\tClassification: %d\n",event->classification);
				fprintf(out,"\t\tFlags: 0x%X\n",event->flags);
				fprintf(out,"\t\tDescription: %s\n",event->description);
				fprintf(out,"\t\t----------------------------\n");
			}
		}
	}

}
