
semaphore_test:     file format elf32-avr


Disassembly of section .text:

00000000 <__vectors>:
       0:	0c 94 52 00 	jmp	0xa4	; 0xa4 <__dtors_end>
       4:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
       8:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
       c:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      10:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      14:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      18:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      1c:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      20:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      24:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      28:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      2c:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      30:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      34:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      38:	0e 94 70 33 	call	0x66e0	; 0x66e0 <__vector_14>
      3c:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      40:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      44:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      48:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      4c:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      50:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      54:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      58:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      5c:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      60:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      64:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      68:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      6c:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      70:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      74:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      78:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      7c:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      80:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      84:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>
      88:	0e 94 7e 00 	call	0xfc	; 0xfc <__epos_dynamic_handler>

0000008c <__ctors_start>:
      8c:	bb 00       	.word	0x00bb	; ????
      8e:	e8 00       	.word	0x00e8	; ????
      90:	10 01       	movw	r2, r0
      92:	8a 01       	movw	r16, r20
      94:	82 12       	cpse	r8, r18
      96:	b8 12       	cpse	r11, r24
      98:	b6 1a       	sub	r11, r22
      9a:	1d 20       	and	r1, r13
      9c:	df 2c       	mov	r13, r15
      9e:	eb 31       	cpi	r30, 0x1B	; 27

000000a0 <__ctors_end>:
      a0:	83 01       	movw	r16, r6
      a2:	d8 2c       	mov	r13, r8

000000a4 <__dtors_end>:
      a4:	11 24       	eor	r1, r1
      a6:	1f be       	out	0x3f, r1	; 63
      a8:	cf ef       	ldi	r28, 0xFF	; 255
      aa:	d0 e1       	ldi	r29, 0x10	; 16
      ac:	de bf       	out	0x3e, r29	; 62
      ae:	cd bf       	out	0x3d, r28	; 61

000000b0 <__do_copy_data>:
      b0:	13 e0       	ldi	r17, 0x03	; 3
      b2:	a0 e5       	ldi	r26, 0x50	; 80
      b4:	b1 e0       	ldi	r27, 0x01	; 1
      b6:	e8 ef       	ldi	r30, 0xF8	; 248
      b8:	f3 e8       	ldi	r31, 0x83	; 131
      ba:	00 e0       	ldi	r16, 0x00	; 0
      bc:	0b bf       	out	0x3b, r16	; 59
      be:	02 c0       	rjmp	.+4      	; 0xc4 <.__do_copy_data_start>

000000c0 <.__do_copy_data_loop>:
      c0:	07 90       	elpm	r0, Z+
      c2:	0d 92       	st	X+, r0

000000c4 <.__do_copy_data_start>:
      c4:	aa 36       	cpi	r26, 0x6A	; 106
      c6:	b1 07       	cpc	r27, r17
      c8:	d9 f7       	brne	.-10     	; 0xc0 <.__do_copy_data_loop>

000000ca <__do_clear_bss>:
      ca:	14 e0       	ldi	r17, 0x04	; 4
      cc:	aa e6       	ldi	r26, 0x6A	; 106
      ce:	b3 e0       	ldi	r27, 0x03	; 3
      d0:	01 c0       	rjmp	.+2      	; 0xd4 <.do_clear_bss_start>

000000d2 <.do_clear_bss_loop>:
      d2:	1d 92       	st	X+, r1

000000d4 <.do_clear_bss_start>:
      d4:	a8 39       	cpi	r26, 0x98	; 152
      d6:	b1 07       	cpc	r27, r17
      d8:	e1 f7       	brne	.-8      	; 0xd2 <.do_clear_bss_loop>

000000da <__do_global_ctors>:
      da:	10 e0       	ldi	r17, 0x00	; 0
      dc:	c0 ea       	ldi	r28, 0xA0	; 160
      de:	d0 e0       	ldi	r29, 0x00	; 0
      e0:	04 c0       	rjmp	.+8      	; 0xea <.do_global_ctors_start>

000000e2 <.do_global_ctors_loop>:
      e2:	22 97       	sbiw	r28, 0x02	; 2
      e4:	fe 01       	movw	r30, r28
      e6:	0e 94 ed 41 	call	0x83da	; 0x83da <__tablejump__>

000000ea <.do_global_ctors_start>:
      ea:	cc 38       	cpi	r28, 0x8C	; 140
      ec:	d1 07       	cpc	r29, r17
      ee:	c9 f7       	brne	.-14     	; 0xe2 <.do_global_ctors_loop>
      f0:	0c 94 d0 06 	jmp	0xda0	; 0xda0 <main>

000000f4 <__epos_library_app_entry>:
      f4:	0e 94 da 2e 	call	0x5db4	; 0x5db4 <__epos_free_init_mem>
      f8:	0e 94 d0 06 	call	0xda0	; 0xda0 <main>

000000fc <__epos_dynamic_handler>:
      fc:	1f 92       	push	r1
      fe:	0f 92       	push	r0
     100:	0f b6       	in	r0, 0x3f	; 63
     102:	0f 92       	push	r0
     104:	8f 93       	push	r24
     106:	cf 93       	push	r28
     108:	df 93       	push	r29
     10a:	de b7       	in	r29, 0x3e	; 62
     10c:	cd b7       	in	r28, 0x3d	; 61
     10e:	88 85       	ldd	r24, Y+8	; 0x08
     110:	1a 80       	ldd	r1, Y+2	; 0x02
     112:	18 86       	std	Y+8, r1	; 0x08
     114:	19 80       	ldd	r1, Y+1	; 0x01
     116:	1f 82       	std	Y+7, r1	; 0x07
     118:	22 96       	adiw	r28, 0x02	; 2
     11a:	de bf       	out	0x3e, r29	; 62
     11c:	cd bf       	out	0x3d, r28	; 61
     11e:	11 24       	eor	r1, r1
     120:	2f 93       	push	r18
     122:	3f 93       	push	r19
     124:	4f 93       	push	r20
     126:	5f 93       	push	r21
     128:	6f 93       	push	r22
     12a:	7f 93       	push	r23
     12c:	9f 93       	push	r25
     12e:	af 93       	push	r26
     130:	bf 93       	push	r27
     132:	ef 93       	push	r30
     134:	ff 93       	push	r31
     136:	86 95       	lsr	r24
     138:	81 50       	subi	r24, 0x01	; 1
     13a:	0e 94 c6 2e 	call	0x5d8c	; 0x5d8c <__epos_call_handler>
     13e:	ff 91       	pop	r31
     140:	ef 91       	pop	r30
     142:	bf 91       	pop	r27
     144:	af 91       	pop	r26
     146:	9f 91       	pop	r25
     148:	7f 91       	pop	r23
     14a:	6f 91       	pop	r22
     14c:	5f 91       	pop	r21
     14e:	4f 91       	pop	r20
     150:	3f 91       	pop	r19
     152:	2f 91       	pop	r18
     154:	8f 91       	pop	r24
     156:	0f 90       	pop	r0
     158:	0f be       	out	0x3f, r0	; 63
     15a:	0f 90       	pop	r0
     15c:	1f 90       	pop	r1
     15e:	df 91       	pop	r29
     160:	cf 91       	pop	r28
     162:	18 95       	reti

00000164 <_Z41__static_initialization_and_destruction_0ii>:
     164:	6f 5f       	subi	r22, 0xFF	; 255
     166:	7f 4f       	sbci	r23, 0xFF	; 255
     168:	09 f0       	breq	.+2      	; 0x16c <_Z41__static_initialization_and_destruction_0ii+0x8>
     16a:	08 95       	ret
     16c:	01 97       	sbiw	r24, 0x01	; 1
     16e:	e9 f7       	brne	.-6      	; 0x16a <_Z41__static_initialization_and_destruction_0ii+0x6>
     170:	0e 94 df 18 	call	0x31be	; 0x31be <_ZN6System6Thread4initEv>
     174:	08 95       	ret

00000176 <_GLOBAL__I__ZN6System10init_firstE>:
     176:	6f ef       	ldi	r22, 0xFF	; 255
     178:	7f ef       	ldi	r23, 0xFF	; 255
     17a:	81 e0       	ldi	r24, 0x01	; 1
     17c:	90 e0       	ldi	r25, 0x00	; 0
     17e:	0e 94 b2 00 	call	0x164	; 0x164 <_Z41__static_initialization_and_destruction_0ii>
     182:	08 95       	ret

00000184 <_Z41__static_initialization_and_destruction_0ii>:
     184:	cf 93       	push	r28
     186:	df 93       	push	r29
     188:	cd b7       	in	r28, 0x3d	; 61
     18a:	de b7       	in	r29, 0x3e	; 62
     18c:	22 97       	sbiw	r28, 0x02	; 2
     18e:	0f b6       	in	r0, 0x3f	; 63
     190:	f8 94       	cli
     192:	de bf       	out	0x3e, r29	; 62
     194:	0f be       	out	0x3f, r0	; 63
     196:	cd bf       	out	0x3d, r28	; 61
     198:	6f 5f       	subi	r22, 0xFF	; 255
     19a:	7f 4f       	sbci	r23, 0xFF	; 255
     19c:	81 f4       	brne	.+32     	; 0x1be <_Z41__static_initialization_and_destruction_0ii+0x3a>
     19e:	01 97       	sbiw	r24, 0x01	; 1
     1a0:	71 f4       	brne	.+28     	; 0x1be <_Z41__static_initialization_and_destruction_0ii+0x3a>
     1a2:	60 e0       	ldi	r22, 0x00	; 0
     1a4:	72 e0       	ldi	r23, 0x02	; 2
     1a6:	ce 01       	movw	r24, r28
     1a8:	01 96       	adiw	r24, 0x01	; 1
     1aa:	0e 94 ec 32 	call	0x65d8	; 0x65d8 <_ZN6System8AVR8_MMU5allocEj>
     1ae:	69 81       	ldd	r22, Y+1	; 0x01
     1b0:	7a 81       	ldd	r23, Y+2	; 0x02
     1b2:	40 e0       	ldi	r20, 0x00	; 0
     1b4:	52 e0       	ldi	r21, 0x02	; 2
     1b6:	8c e6       	ldi	r24, 0x6C	; 108
     1b8:	93 e0       	ldi	r25, 0x03	; 3
     1ba:	0e 94 c1 12 	call	0x2582	; 0x2582 <_ZN6System4Heap4freeEPvj>
     1be:	22 96       	adiw	r28, 0x02	; 2
     1c0:	0f b6       	in	r0, 0x3f	; 63
     1c2:	f8 94       	cli
     1c4:	de bf       	out	0x3e, r29	; 62
     1c6:	0f be       	out	0x3f, r0	; 63
     1c8:	cd bf       	out	0x3d, r28	; 61
     1ca:	df 91       	pop	r29
     1cc:	cf 91       	pop	r28
     1ce:	08 95       	ret

000001d0 <_GLOBAL__I__ZN6System16init_applicationE>:
     1d0:	6f ef       	ldi	r22, 0xFF	; 255
     1d2:	7f ef       	ldi	r23, 0xFF	; 255
     1d4:	81 e0       	ldi	r24, 0x01	; 1
     1d6:	90 e0       	ldi	r25, 0x00	; 0
     1d8:	0e 94 c2 00 	call	0x184	; 0x184 <_Z41__static_initialization_and_destruction_0ii>
     1dc:	08 95       	ret

000001de <_Z41__static_initialization_and_destruction_0ii>:
     1de:	6f 5f       	subi	r22, 0xFF	; 255
     1e0:	7f 4f       	sbci	r23, 0xFF	; 255
     1e2:	09 f0       	breq	.+2      	; 0x1e6 <_Z41__static_initialization_and_destruction_0ii+0x8>
     1e4:	08 95       	ret
     1e6:	01 97       	sbiw	r24, 0x01	; 1
     1e8:	e9 f7       	brne	.-6      	; 0x1e4 <_Z41__static_initialization_and_destruction_0ii+0x6>
     1ea:	8a e0       	ldi	r24, 0x0A	; 10
     1ec:	90 e0       	ldi	r25, 0x00	; 0
     1ee:	90 93 77 03 	sts	0x0377, r25
     1f2:	80 93 76 03 	sts	0x0376, r24
     1f6:	90 93 75 03 	sts	0x0375, r25
     1fa:	80 93 74 03 	sts	0x0374, r24
     1fe:	10 92 6d 03 	sts	0x036D, r1
     202:	10 92 6c 03 	sts	0x036C, r1
     206:	10 92 6f 03 	sts	0x036F, r1
     20a:	10 92 6e 03 	sts	0x036E, r1
     20e:	10 92 71 03 	sts	0x0371, r1
     212:	10 92 70 03 	sts	0x0370, r1
     216:	10 92 73 03 	sts	0x0373, r1
     21a:	10 92 72 03 	sts	0x0372, r1
     21e:	08 95       	ret

00000220 <_GLOBAL__I__ZN6System4coutE>:
     220:	6f ef       	ldi	r22, 0xFF	; 255
     222:	7f ef       	ldi	r23, 0xFF	; 255
     224:	81 e0       	ldi	r24, 0x01	; 1
     226:	90 e0       	ldi	r25, 0x00	; 0
     228:	0e 94 ef 00 	call	0x1de	; 0x1de <_Z41__static_initialization_and_destruction_0ii>
     22c:	08 95       	ret

0000022e <_Z41__static_initialization_and_destruction_0ii>:
     22e:	6f 5f       	subi	r22, 0xFF	; 255
     230:	7f 4f       	sbci	r23, 0xFF	; 255
     232:	09 f0       	breq	.+2      	; 0x236 <_Z41__static_initialization_and_destruction_0ii+0x8>
     234:	08 95       	ret
     236:	81 30       	cpi	r24, 0x01	; 1
     238:	91 05       	cpc	r25, r1
     23a:	21 f1       	breq	.+72     	; 0x284 <_Z41__static_initialization_and_destruction_0ii+0x56>
     23c:	89 2b       	or	r24, r25
     23e:	d1 f7       	brne	.-12     	; 0x234 <_Z41__static_initialization_and_destruction_0ii+0x6>
     240:	8e e8       	ldi	r24, 0x8E	; 142
     242:	93 e0       	ldi	r25, 0x03	; 3
     244:	0e 94 78 21 	call	0x42f0	; 0x42f0 <_ZN6System6Thread10wakeup_allEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
     248:	80 91 96 03 	lds	r24, 0x0396
     24c:	90 91 97 03 	lds	r25, 0x0397
     250:	89 2b       	or	r24, r25
     252:	09 f0       	breq	.+2      	; 0x256 <_Z41__static_initialization_and_destruction_0ii+0x28>
     254:	4e c0       	rjmp	.+156    	; 0x2f2 <_Z41__static_initialization_and_destruction_0ii+0xc4>
     256:	e0 e7       	ldi	r30, 0x70	; 112
     258:	f0 e0       	ldi	r31, 0x00	; 0
     25a:	10 a2       	std	Z+32, r1	; 0x20
     25c:	80 91 96 03 	lds	r24, 0x0396
     260:	90 91 97 03 	lds	r25, 0x0397
     264:	89 2b       	or	r24, r25
     266:	09 f0       	breq	.+2      	; 0x26a <_Z41__static_initialization_and_destruction_0ii+0x3c>
     268:	4b c0       	rjmp	.+150    	; 0x300 <_Z41__static_initialization_and_destruction_0ii+0xd2>
     26a:	e9 e0       	ldi	r30, 0x09	; 9
     26c:	f0 e0       	ldi	r31, 0x00	; 0
     26e:	10 a2       	std	Z+32, r1	; 0x20
     270:	80 91 96 03 	lds	r24, 0x0396
     274:	90 91 97 03 	lds	r25, 0x0397
     278:	89 2b       	or	r24, r25
     27a:	f1 f5       	brne	.+124    	; 0x2f8 <_Z41__static_initialization_and_destruction_0ii+0xca>
     27c:	ea e0       	ldi	r30, 0x0A	; 10
     27e:	f0 e0       	ldi	r31, 0x00	; 0
     280:	10 a2       	std	Z+32, r1	; 0x20
     282:	08 95       	ret
     284:	10 92 97 03 	sts	0x0397, r1
     288:	10 92 96 03 	sts	0x0396, r1
     28c:	10 92 90 00 	sts	0x0090, r1
     290:	8f e2       	ldi	r24, 0x2F	; 47
     292:	89 b9       	out	0x09, r24	; 9
     294:	86 e0       	ldi	r24, 0x06	; 6
     296:	80 93 98 03 	sts	0x0398, r24
     29a:	86 e8       	ldi	r24, 0x86	; 134
     29c:	80 93 95 00 	sts	0x0095, r24
     2a0:	1b b8       	out	0x0b, r1	; 11
     2a2:	10 92 99 03 	sts	0x0399, r1
     2a6:	ea e0       	ldi	r30, 0x0A	; 10
     2a8:	f0 e0       	ldi	r31, 0x00	; 0
     2aa:	80 a1       	ldd	r24, Z+32	; 0x20
     2ac:	88 61       	ori	r24, 0x18	; 24
     2ae:	80 a3       	std	Z+32, r24	; 0x20
     2b0:	10 92 9b 03 	sts	0x039B, r1
     2b4:	10 92 9a 03 	sts	0x039A, r1
     2b8:	10 92 9d 03 	sts	0x039D, r1
     2bc:	10 92 9c 03 	sts	0x039C, r1
     2c0:	10 92 8f 03 	sts	0x038F, r1
     2c4:	10 92 8e 03 	sts	0x038E, r1
     2c8:	10 92 91 03 	sts	0x0391, r1
     2cc:	10 92 90 03 	sts	0x0390, r1
     2d0:	10 92 93 03 	sts	0x0393, r1
     2d4:	10 92 92 03 	sts	0x0392, r1
     2d8:	81 e0       	ldi	r24, 0x01	; 1
     2da:	90 e0       	ldi	r25, 0x00	; 0
     2dc:	90 93 95 03 	sts	0x0395, r25
     2e0:	80 93 94 03 	sts	0x0394, r24
     2e4:	8a e0       	ldi	r24, 0x0A	; 10
     2e6:	90 e0       	ldi	r25, 0x00	; 0
     2e8:	90 93 79 03 	sts	0x0379, r25
     2ec:	80 93 78 03 	sts	0x0378, r24
     2f0:	08 95       	ret
     2f2:	e8 e7       	ldi	r30, 0x78	; 120
     2f4:	f0 e0       	ldi	r31, 0x00	; 0
     2f6:	b1 cf       	rjmp	.-158    	; 0x25a <_Z41__static_initialization_and_destruction_0ii+0x2c>
     2f8:	ea e7       	ldi	r30, 0x7A	; 122
     2fa:	f0 e0       	ldi	r31, 0x00	; 0
     2fc:	10 a2       	std	Z+32, r1	; 0x20
     2fe:	08 95       	ret
     300:	e9 e7       	ldi	r30, 0x79	; 121
     302:	f0 e0       	ldi	r31, 0x00	; 0
     304:	b4 cf       	rjmp	.-152    	; 0x26e <_Z41__static_initialization_and_destruction_0ii+0x40>

00000306 <_GLOBAL__D_display>:
     306:	6f ef       	ldi	r22, 0xFF	; 255
     308:	7f ef       	ldi	r23, 0xFF	; 255
     30a:	80 e0       	ldi	r24, 0x00	; 0
     30c:	90 e0       	ldi	r25, 0x00	; 0
     30e:	0e 94 17 01 	call	0x22e	; 0x22e <_Z41__static_initialization_and_destruction_0ii>
     312:	08 95       	ret

00000314 <_GLOBAL__I_display>:
     314:	6f ef       	ldi	r22, 0xFF	; 255
     316:	7f ef       	ldi	r23, 0xFF	; 255
     318:	81 e0       	ldi	r24, 0x01	; 1
     31a:	90 e0       	ldi	r25, 0x00	; 0
     31c:	0e 94 17 01 	call	0x22e	; 0x22e <_Z41__static_initialization_and_destruction_0ii>
     320:	08 95       	ret

00000322 <_Z11philosopheriii>:
     322:	6f 92       	push	r6
     324:	7f 92       	push	r7
     326:	8f 92       	push	r8
     328:	9f 92       	push	r9
     32a:	af 92       	push	r10
     32c:	bf 92       	push	r11
     32e:	cf 92       	push	r12
     330:	df 92       	push	r13
     332:	ef 92       	push	r14
     334:	ff 92       	push	r15
     336:	0f 93       	push	r16
     338:	1f 93       	push	r17
     33a:	cf 93       	push	r28
     33c:	df 93       	push	r29
     33e:	cd b7       	in	r28, 0x3d	; 61
     340:	de b7       	in	r29, 0x3e	; 62
     342:	28 97       	sbiw	r28, 0x08	; 8
     344:	0f b6       	in	r0, 0x3f	; 63
     346:	f8 94       	cli
     348:	de bf       	out	0x3e, r29	; 62
     34a:	0f be       	out	0x3f, r0	; 63
     34c:	cd bf       	out	0x3d, r28	; 61
     34e:	8b 01       	movw	r16, r22
     350:	7a 01       	movw	r14, r20
     352:	84 30       	cpi	r24, 0x04	; 4
     354:	91 05       	cpc	r25, r1
     356:	0c f4       	brge	.+2      	; 0x35a <_Z11philosopheriii+0x38>
     358:	ff c4       	rjmp	.+2558   	; 0xd58 <_Z11philosopheriii+0xa36>
     35a:	e4 e0       	ldi	r30, 0x04	; 4
     35c:	f0 e0       	ldi	r31, 0x00	; 0
     35e:	80 e0       	ldi	r24, 0x00	; 0
     360:	90 e0       	ldi	r25, 0x00	; 0
     362:	3a e0       	ldi	r19, 0x0A	; 10
     364:	c3 2e       	mov	r12, r19
     366:	d1 2c       	mov	r13, r1
     368:	25 e0       	ldi	r18, 0x05	; 5
     36a:	62 2e       	mov	r6, r18
     36c:	71 2c       	mov	r7, r1
     36e:	6c 0e       	add	r6, r28
     370:	7d 1e       	adc	r7, r29
     372:	4c 01       	movw	r8, r24
     374:	88 0c       	add	r8, r8
     376:	99 1c       	adc	r9, r9
     378:	5f 01       	movw	r10, r30
     37a:	aa 0c       	add	r10, r10
     37c:	bb 1c       	adc	r11, r11
     37e:	f8 94       	cli
     380:	20 91 94 03 	lds	r18, 0x0394
     384:	30 91 95 03 	lds	r19, 0x0395
     388:	80 91 94 03 	lds	r24, 0x0394
     38c:	90 91 95 03 	lds	r25, 0x0395
     390:	01 97       	sbiw	r24, 0x01	; 1
     392:	90 93 95 03 	sts	0x0395, r25
     396:	80 93 94 03 	sts	0x0394, r24
     39a:	78 94       	sei
     39c:	12 16       	cp	r1, r18
     39e:	13 06       	cpc	r1, r19
     3a0:	0c f0       	brlt	.+2      	; 0x3a4 <_Z11philosopheriii+0x82>
     3a2:	7c c4       	rjmp	.+2296   	; 0xc9c <_Z11philosopheriii+0x97a>
     3a4:	10 93 9b 03 	sts	0x039B, r17
     3a8:	00 93 9a 03 	sts	0x039A, r16
     3ac:	f0 92 9d 03 	sts	0x039D, r15
     3b0:	e0 92 9c 03 	sts	0x039C, r14
     3b4:	20 91 96 03 	lds	r18, 0x0396
     3b8:	30 91 97 03 	lds	r19, 0x0397
     3bc:	05 c0       	rjmp	.+10     	; 0x3c8 <_Z11philosopheriii+0xa6>
     3be:	eb e0       	ldi	r30, 0x0B	; 11
     3c0:	f0 e0       	ldi	r31, 0x00	; 0
     3c2:	80 a1       	ldd	r24, Z+32	; 0x20
     3c4:	85 fd       	sbrc	r24, 5
     3c6:	08 c0       	rjmp	.+16     	; 0x3d8 <_Z11philosopheriii+0xb6>
     3c8:	21 15       	cp	r18, r1
     3ca:	31 05       	cpc	r19, r1
     3cc:	c1 f3       	breq	.-16     	; 0x3be <_Z11philosopheriii+0x9c>
     3ce:	eb e7       	ldi	r30, 0x7B	; 123
     3d0:	f0 e0       	ldi	r31, 0x00	; 0
     3d2:	80 a1       	ldd	r24, Z+32	; 0x20
     3d4:	85 ff       	sbrs	r24, 5
     3d6:	f8 cf       	rjmp	.-16     	; 0x3c8 <_Z11philosopheriii+0xa6>
     3d8:	23 2b       	or	r18, r19
     3da:	09 f0       	breq	.+2      	; 0x3de <_Z11philosopheriii+0xbc>
     3dc:	48 c4       	rjmp	.+2192   	; 0xc6e <_Z11philosopheriii+0x94c>
     3de:	ec e0       	ldi	r30, 0x0C	; 12
     3e0:	f0 e0       	ldi	r31, 0x00	; 0
     3e2:	8b e1       	ldi	r24, 0x1B	; 27
     3e4:	80 a3       	std	Z+32, r24	; 0x20
     3e6:	20 91 96 03 	lds	r18, 0x0396
     3ea:	30 91 97 03 	lds	r19, 0x0397
     3ee:	05 c0       	rjmp	.+10     	; 0x3fa <_Z11philosopheriii+0xd8>
     3f0:	eb e0       	ldi	r30, 0x0B	; 11
     3f2:	f0 e0       	ldi	r31, 0x00	; 0
     3f4:	80 a1       	ldd	r24, Z+32	; 0x20
     3f6:	85 fd       	sbrc	r24, 5
     3f8:	08 c0       	rjmp	.+16     	; 0x40a <_Z11philosopheriii+0xe8>
     3fa:	21 15       	cp	r18, r1
     3fc:	31 05       	cpc	r19, r1
     3fe:	c1 f3       	breq	.-16     	; 0x3f0 <_Z11philosopheriii+0xce>
     400:	eb e7       	ldi	r30, 0x7B	; 123
     402:	f0 e0       	ldi	r31, 0x00	; 0
     404:	80 a1       	ldd	r24, Z+32	; 0x20
     406:	85 ff       	sbrs	r24, 5
     408:	f8 cf       	rjmp	.-16     	; 0x3fa <_Z11philosopheriii+0xd8>
     40a:	23 2b       	or	r18, r19
     40c:	09 f0       	breq	.+2      	; 0x410 <_Z11philosopheriii+0xee>
     40e:	43 c4       	rjmp	.+2182   	; 0xc96 <_Z11philosopheriii+0x974>
     410:	ec e0       	ldi	r30, 0x0C	; 12
     412:	f0 e0       	ldi	r31, 0x00	; 0
     414:	8b e5       	ldi	r24, 0x5B	; 91
     416:	80 a3       	std	Z+32, r24	; 0x20
     418:	90 91 9a 03 	lds	r25, 0x039A
     41c:	94 36       	cpi	r25, 0x64	; 100
     41e:	08 f0       	brcs	.+2      	; 0x422 <_Z11philosopheriii+0x100>
     420:	33 c4       	rjmp	.+2150   	; 0xc88 <_Z11philosopheriii+0x966>
     422:	40 e3       	ldi	r20, 0x30	; 48
     424:	20 91 96 03 	lds	r18, 0x0396
     428:	30 91 97 03 	lds	r19, 0x0397
     42c:	05 c0       	rjmp	.+10     	; 0x438 <_Z11philosopheriii+0x116>
     42e:	eb e0       	ldi	r30, 0x0B	; 11
     430:	f0 e0       	ldi	r31, 0x00	; 0
     432:	80 a1       	ldd	r24, Z+32	; 0x20
     434:	85 fd       	sbrc	r24, 5
     436:	08 c0       	rjmp	.+16     	; 0x448 <_Z11philosopheriii+0x126>
     438:	21 15       	cp	r18, r1
     43a:	31 05       	cpc	r19, r1
     43c:	c1 f3       	breq	.-16     	; 0x42e <_Z11philosopheriii+0x10c>
     43e:	eb e7       	ldi	r30, 0x7B	; 123
     440:	f0 e0       	ldi	r31, 0x00	; 0
     442:	80 a1       	ldd	r24, Z+32	; 0x20
     444:	85 ff       	sbrs	r24, 5
     446:	f8 cf       	rjmp	.-16     	; 0x438 <_Z11philosopheriii+0x116>
     448:	23 2b       	or	r18, r19
     44a:	09 f0       	breq	.+2      	; 0x44e <_Z11philosopheriii+0x12c>
     44c:	1a c4       	rjmp	.+2100   	; 0xc82 <_Z11philosopheriii+0x960>
     44e:	ec e0       	ldi	r30, 0x0C	; 12
     450:	f0 e0       	ldi	r31, 0x00	; 0
     452:	40 a3       	std	Z+32, r20	; 0x20
     454:	9a 30       	cpi	r25, 0x0A	; 10
     456:	08 f0       	brcs	.+2      	; 0x45a <_Z11philosopheriii+0x138>
     458:	0d c4       	rjmp	.+2074   	; 0xc74 <_Z11philosopheriii+0x952>
     45a:	40 e3       	ldi	r20, 0x30	; 48
     45c:	20 91 96 03 	lds	r18, 0x0396
     460:	30 91 97 03 	lds	r19, 0x0397
     464:	05 c0       	rjmp	.+10     	; 0x470 <_Z11philosopheriii+0x14e>
     466:	eb e0       	ldi	r30, 0x0B	; 11
     468:	f0 e0       	ldi	r31, 0x00	; 0
     46a:	80 a1       	ldd	r24, Z+32	; 0x20
     46c:	85 fd       	sbrc	r24, 5
     46e:	08 c0       	rjmp	.+16     	; 0x480 <_Z11philosopheriii+0x15e>
     470:	21 15       	cp	r18, r1
     472:	31 05       	cpc	r19, r1
     474:	c1 f3       	breq	.-16     	; 0x466 <_Z11philosopheriii+0x144>
     476:	eb e7       	ldi	r30, 0x7B	; 123
     478:	f0 e0       	ldi	r31, 0x00	; 0
     47a:	80 a1       	ldd	r24, Z+32	; 0x20
     47c:	85 ff       	sbrs	r24, 5
     47e:	f8 cf       	rjmp	.-16     	; 0x470 <_Z11philosopheriii+0x14e>
     480:	23 2b       	or	r18, r19
     482:	09 f0       	breq	.+2      	; 0x486 <_Z11philosopheriii+0x164>
     484:	f1 c3       	rjmp	.+2018   	; 0xc68 <_Z11philosopheriii+0x946>
     486:	ec e0       	ldi	r30, 0x0C	; 12
     488:	f0 e0       	ldi	r31, 0x00	; 0
     48a:	40 a3       	std	Z+32, r20	; 0x20
     48c:	90 5d       	subi	r25, 0xD0	; 208
     48e:	20 91 96 03 	lds	r18, 0x0396
     492:	30 91 97 03 	lds	r19, 0x0397
     496:	05 c0       	rjmp	.+10     	; 0x4a2 <_Z11philosopheriii+0x180>
     498:	eb e0       	ldi	r30, 0x0B	; 11
     49a:	f0 e0       	ldi	r31, 0x00	; 0
     49c:	80 a1       	ldd	r24, Z+32	; 0x20
     49e:	85 fd       	sbrc	r24, 5
     4a0:	08 c0       	rjmp	.+16     	; 0x4b2 <_Z11philosopheriii+0x190>
     4a2:	21 15       	cp	r18, r1
     4a4:	31 05       	cpc	r19, r1
     4a6:	c1 f3       	breq	.-16     	; 0x498 <_Z11philosopheriii+0x176>
     4a8:	eb e7       	ldi	r30, 0x7B	; 123
     4aa:	f0 e0       	ldi	r31, 0x00	; 0
     4ac:	80 a1       	ldd	r24, Z+32	; 0x20
     4ae:	85 ff       	sbrs	r24, 5
     4b0:	f8 cf       	rjmp	.-16     	; 0x4a2 <_Z11philosopheriii+0x180>
     4b2:	23 2b       	or	r18, r19
     4b4:	09 f0       	breq	.+2      	; 0x4b8 <_Z11philosopheriii+0x196>
     4b6:	c1 c3       	rjmp	.+1922   	; 0xc3a <_Z11philosopheriii+0x918>
     4b8:	ec e0       	ldi	r30, 0x0C	; 12
     4ba:	f0 e0       	ldi	r31, 0x00	; 0
     4bc:	90 a3       	std	Z+32, r25	; 0x20
     4be:	20 91 96 03 	lds	r18, 0x0396
     4c2:	30 91 97 03 	lds	r19, 0x0397
     4c6:	05 c0       	rjmp	.+10     	; 0x4d2 <_Z11philosopheriii+0x1b0>
     4c8:	eb e0       	ldi	r30, 0x0B	; 11
     4ca:	f0 e0       	ldi	r31, 0x00	; 0
     4cc:	80 a1       	ldd	r24, Z+32	; 0x20
     4ce:	85 fd       	sbrc	r24, 5
     4d0:	08 c0       	rjmp	.+16     	; 0x4e2 <_Z11philosopheriii+0x1c0>
     4d2:	21 15       	cp	r18, r1
     4d4:	31 05       	cpc	r19, r1
     4d6:	c1 f3       	breq	.-16     	; 0x4c8 <_Z11philosopheriii+0x1a6>
     4d8:	eb e7       	ldi	r30, 0x7B	; 123
     4da:	f0 e0       	ldi	r31, 0x00	; 0
     4dc:	80 a1       	ldd	r24, Z+32	; 0x20
     4de:	85 ff       	sbrs	r24, 5
     4e0:	f8 cf       	rjmp	.-16     	; 0x4d2 <_Z11philosopheriii+0x1b0>
     4e2:	23 2b       	or	r18, r19
     4e4:	09 f0       	breq	.+2      	; 0x4e8 <_Z11philosopheriii+0x1c6>
     4e6:	bd c3       	rjmp	.+1914   	; 0xc62 <_Z11philosopheriii+0x940>
     4e8:	ec e0       	ldi	r30, 0x0C	; 12
     4ea:	f0 e0       	ldi	r31, 0x00	; 0
     4ec:	8b e3       	ldi	r24, 0x3B	; 59
     4ee:	80 a3       	std	Z+32, r24	; 0x20
     4f0:	90 91 9c 03 	lds	r25, 0x039C
     4f4:	94 36       	cpi	r25, 0x64	; 100
     4f6:	08 f0       	brcs	.+2      	; 0x4fa <_Z11philosopheriii+0x1d8>
     4f8:	ad c3       	rjmp	.+1882   	; 0xc54 <_Z11philosopheriii+0x932>
     4fa:	40 e3       	ldi	r20, 0x30	; 48
     4fc:	20 91 96 03 	lds	r18, 0x0396
     500:	30 91 97 03 	lds	r19, 0x0397
     504:	05 c0       	rjmp	.+10     	; 0x510 <_Z11philosopheriii+0x1ee>
     506:	eb e0       	ldi	r30, 0x0B	; 11
     508:	f0 e0       	ldi	r31, 0x00	; 0
     50a:	80 a1       	ldd	r24, Z+32	; 0x20
     50c:	85 fd       	sbrc	r24, 5
     50e:	08 c0       	rjmp	.+16     	; 0x520 <_Z11philosopheriii+0x1fe>
     510:	21 15       	cp	r18, r1
     512:	31 05       	cpc	r19, r1
     514:	c1 f3       	breq	.-16     	; 0x506 <_Z11philosopheriii+0x1e4>
     516:	eb e7       	ldi	r30, 0x7B	; 123
     518:	f0 e0       	ldi	r31, 0x00	; 0
     51a:	80 a1       	ldd	r24, Z+32	; 0x20
     51c:	85 ff       	sbrs	r24, 5
     51e:	f8 cf       	rjmp	.-16     	; 0x510 <_Z11philosopheriii+0x1ee>
     520:	23 2b       	or	r18, r19
     522:	09 f0       	breq	.+2      	; 0x526 <_Z11philosopheriii+0x204>
     524:	94 c3       	rjmp	.+1832   	; 0xc4e <_Z11philosopheriii+0x92c>
     526:	ec e0       	ldi	r30, 0x0C	; 12
     528:	f0 e0       	ldi	r31, 0x00	; 0
     52a:	40 a3       	std	Z+32, r20	; 0x20
     52c:	9a 30       	cpi	r25, 0x0A	; 10
     52e:	08 f0       	brcs	.+2      	; 0x532 <_Z11philosopheriii+0x210>
     530:	87 c3       	rjmp	.+1806   	; 0xc40 <_Z11philosopheriii+0x91e>
     532:	40 e3       	ldi	r20, 0x30	; 48
     534:	20 91 96 03 	lds	r18, 0x0396
     538:	30 91 97 03 	lds	r19, 0x0397
     53c:	05 c0       	rjmp	.+10     	; 0x548 <_Z11philosopheriii+0x226>
     53e:	eb e0       	ldi	r30, 0x0B	; 11
     540:	f0 e0       	ldi	r31, 0x00	; 0
     542:	80 a1       	ldd	r24, Z+32	; 0x20
     544:	85 fd       	sbrc	r24, 5
     546:	08 c0       	rjmp	.+16     	; 0x558 <_Z11philosopheriii+0x236>
     548:	21 15       	cp	r18, r1
     54a:	31 05       	cpc	r19, r1
     54c:	c1 f3       	breq	.-16     	; 0x53e <_Z11philosopheriii+0x21c>
     54e:	eb e7       	ldi	r30, 0x7B	; 123
     550:	f0 e0       	ldi	r31, 0x00	; 0
     552:	80 a1       	ldd	r24, Z+32	; 0x20
     554:	85 ff       	sbrs	r24, 5
     556:	f8 cf       	rjmp	.-16     	; 0x548 <_Z11philosopheriii+0x226>
     558:	23 2b       	or	r18, r19
     55a:	09 f0       	breq	.+2      	; 0x55e <_Z11philosopheriii+0x23c>
     55c:	6b c3       	rjmp	.+1750   	; 0xc34 <_Z11philosopheriii+0x912>
     55e:	ec e0       	ldi	r30, 0x0C	; 12
     560:	f0 e0       	ldi	r31, 0x00	; 0
     562:	40 a3       	std	Z+32, r20	; 0x20
     564:	90 5d       	subi	r25, 0xD0	; 208
     566:	20 91 96 03 	lds	r18, 0x0396
     56a:	30 91 97 03 	lds	r19, 0x0397
     56e:	05 c0       	rjmp	.+10     	; 0x57a <_Z11philosopheriii+0x258>
     570:	eb e0       	ldi	r30, 0x0B	; 11
     572:	f0 e0       	ldi	r31, 0x00	; 0
     574:	80 a1       	ldd	r24, Z+32	; 0x20
     576:	85 fd       	sbrc	r24, 5
     578:	08 c0       	rjmp	.+16     	; 0x58a <_Z11philosopheriii+0x268>
     57a:	21 15       	cp	r18, r1
     57c:	31 05       	cpc	r19, r1
     57e:	c1 f3       	breq	.-16     	; 0x570 <_Z11philosopheriii+0x24e>
     580:	eb e7       	ldi	r30, 0x7B	; 123
     582:	f0 e0       	ldi	r31, 0x00	; 0
     584:	80 a1       	ldd	r24, Z+32	; 0x20
     586:	85 ff       	sbrs	r24, 5
     588:	f8 cf       	rjmp	.-16     	; 0x57a <_Z11philosopheriii+0x258>
     58a:	23 2b       	or	r18, r19
     58c:	09 f0       	breq	.+2      	; 0x590 <_Z11philosopheriii+0x26e>
     58e:	4f c3       	rjmp	.+1694   	; 0xc2e <_Z11philosopheriii+0x90c>
     590:	ec e0       	ldi	r30, 0x0C	; 12
     592:	f0 e0       	ldi	r31, 0x00	; 0
     594:	90 a3       	std	Z+32, r25	; 0x20
     596:	20 91 96 03 	lds	r18, 0x0396
     59a:	30 91 97 03 	lds	r19, 0x0397
     59e:	05 c0       	rjmp	.+10     	; 0x5aa <_Z11philosopheriii+0x288>
     5a0:	eb e0       	ldi	r30, 0x0B	; 11
     5a2:	f0 e0       	ldi	r31, 0x00	; 0
     5a4:	80 a1       	ldd	r24, Z+32	; 0x20
     5a6:	85 fd       	sbrc	r24, 5
     5a8:	08 c0       	rjmp	.+16     	; 0x5ba <_Z11philosopheriii+0x298>
     5aa:	21 15       	cp	r18, r1
     5ac:	31 05       	cpc	r19, r1
     5ae:	c1 f3       	breq	.-16     	; 0x5a0 <_Z11philosopheriii+0x27e>
     5b0:	eb e7       	ldi	r30, 0x7B	; 123
     5b2:	f0 e0       	ldi	r31, 0x00	; 0
     5b4:	80 a1       	ldd	r24, Z+32	; 0x20
     5b6:	85 ff       	sbrs	r24, 5
     5b8:	f8 cf       	rjmp	.-16     	; 0x5aa <_Z11philosopheriii+0x288>
     5ba:	23 2b       	or	r18, r19
     5bc:	09 f0       	breq	.+2      	; 0x5c0 <_Z11philosopheriii+0x29e>
     5be:	34 c3       	rjmp	.+1640   	; 0xc28 <_Z11philosopheriii+0x906>
     5c0:	ec e0       	ldi	r30, 0x0C	; 12
     5c2:	f0 e0       	ldi	r31, 0x00	; 0
     5c4:	88 e4       	ldi	r24, 0x48	; 72
     5c6:	80 a3       	std	Z+32, r24	; 0x20
     5c8:	60 e5       	ldi	r22, 0x50	; 80
     5ca:	71 e0       	ldi	r23, 0x01	; 1
     5cc:	88 e7       	ldi	r24, 0x78	; 120
     5ce:	93 e0       	ldi	r25, 0x03	; 3
     5d0:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
     5d4:	f8 94       	cli
     5d6:	20 91 94 03 	lds	r18, 0x0394
     5da:	30 91 95 03 	lds	r19, 0x0395
     5de:	80 91 94 03 	lds	r24, 0x0394
     5e2:	90 91 95 03 	lds	r25, 0x0395
     5e6:	01 96       	adiw	r24, 0x01	; 1
     5e8:	90 93 95 03 	sts	0x0395, r25
     5ec:	80 93 94 03 	sts	0x0394, r24
     5f0:	78 94       	sei
     5f2:	37 fd       	sbrc	r19, 7
     5f4:	6d c3       	rjmp	.+1754   	; 0xcd0 <_Z11philosopheriii+0x9ae>
     5f6:	80 ea       	ldi	r24, 0xA0	; 160
     5f8:	96 e8       	ldi	r25, 0x86	; 134
     5fa:	a1 e0       	ldi	r26, 0x01	; 1
     5fc:	b0 e0       	ldi	r27, 0x00	; 0
     5fe:	8d 83       	std	Y+5, r24	; 0x05
     600:	9e 83       	std	Y+6, r25	; 0x06
     602:	af 83       	std	Y+7, r26	; 0x07
     604:	b8 87       	std	Y+8, r27	; 0x08
     606:	c3 01       	movw	r24, r6
     608:	0e 94 b8 1f 	call	0x3f70	; 0x3f70 <_ZN6System5Alarm5delayERKm>
     60c:	f4 01       	movw	r30, r8
     60e:	e6 58       	subi	r30, 0x86	; 134
     610:	fc 4f       	sbci	r31, 0xFC	; 252
     612:	01 90       	ld	r0, Z+
     614:	f0 81       	ld	r31, Z
     616:	e0 2d       	mov	r30, r0
     618:	f8 94       	cli
     61a:	26 81       	ldd	r18, Z+6	; 0x06
     61c:	37 81       	ldd	r19, Z+7	; 0x07
     61e:	86 81       	ldd	r24, Z+6	; 0x06
     620:	97 81       	ldd	r25, Z+7	; 0x07
     622:	01 97       	sbiw	r24, 0x01	; 1
     624:	97 83       	std	Z+7, r25	; 0x07
     626:	86 83       	std	Z+6, r24	; 0x06
     628:	78 94       	sei
     62a:	12 16       	cp	r1, r18
     62c:	13 06       	cpc	r1, r19
     62e:	0c f0       	brlt	.+2      	; 0x632 <_Z11philosopheriii+0x310>
     630:	4b c3       	rjmp	.+1686   	; 0xcc8 <_Z11philosopheriii+0x9a6>
     632:	f5 01       	movw	r30, r10
     634:	e6 58       	subi	r30, 0x86	; 134
     636:	fc 4f       	sbci	r31, 0xFC	; 252
     638:	01 90       	ld	r0, Z+
     63a:	f0 81       	ld	r31, Z
     63c:	e0 2d       	mov	r30, r0
     63e:	f8 94       	cli
     640:	26 81       	ldd	r18, Z+6	; 0x06
     642:	37 81       	ldd	r19, Z+7	; 0x07
     644:	86 81       	ldd	r24, Z+6	; 0x06
     646:	97 81       	ldd	r25, Z+7	; 0x07
     648:	01 97       	sbiw	r24, 0x01	; 1
     64a:	97 83       	std	Z+7, r25	; 0x07
     64c:	86 83       	std	Z+6, r24	; 0x06
     64e:	78 94       	sei
     650:	12 16       	cp	r1, r18
     652:	13 06       	cpc	r1, r19
     654:	0c f0       	brlt	.+2      	; 0x658 <_Z11philosopheriii+0x336>
     656:	34 c3       	rjmp	.+1640   	; 0xcc0 <_Z11philosopheriii+0x99e>
     658:	f8 94       	cli
     65a:	20 91 94 03 	lds	r18, 0x0394
     65e:	30 91 95 03 	lds	r19, 0x0395
     662:	80 91 94 03 	lds	r24, 0x0394
     666:	90 91 95 03 	lds	r25, 0x0395
     66a:	01 97       	sbiw	r24, 0x01	; 1
     66c:	90 93 95 03 	sts	0x0395, r25
     670:	80 93 94 03 	sts	0x0394, r24
     674:	78 94       	sei
     676:	12 16       	cp	r1, r18
     678:	13 06       	cpc	r1, r19
     67a:	0c f0       	brlt	.+2      	; 0x67e <_Z11philosopheriii+0x35c>
     67c:	2e c3       	rjmp	.+1628   	; 0xcda <_Z11philosopheriii+0x9b8>
     67e:	10 93 9b 03 	sts	0x039B, r17
     682:	00 93 9a 03 	sts	0x039A, r16
     686:	f0 92 9d 03 	sts	0x039D, r15
     68a:	e0 92 9c 03 	sts	0x039C, r14
     68e:	20 91 96 03 	lds	r18, 0x0396
     692:	30 91 97 03 	lds	r19, 0x0397
     696:	05 c0       	rjmp	.+10     	; 0x6a2 <_Z11philosopheriii+0x380>
     698:	eb e0       	ldi	r30, 0x0B	; 11
     69a:	f0 e0       	ldi	r31, 0x00	; 0
     69c:	80 a1       	ldd	r24, Z+32	; 0x20
     69e:	85 fd       	sbrc	r24, 5
     6a0:	08 c0       	rjmp	.+16     	; 0x6b2 <_Z11philosopheriii+0x390>
     6a2:	21 15       	cp	r18, r1
     6a4:	31 05       	cpc	r19, r1
     6a6:	c1 f3       	breq	.-16     	; 0x698 <_Z11philosopheriii+0x376>
     6a8:	eb e7       	ldi	r30, 0x7B	; 123
     6aa:	f0 e0       	ldi	r31, 0x00	; 0
     6ac:	80 a1       	ldd	r24, Z+32	; 0x20
     6ae:	85 ff       	sbrs	r24, 5
     6b0:	f8 cf       	rjmp	.-16     	; 0x6a2 <_Z11philosopheriii+0x380>
     6b2:	23 2b       	or	r18, r19
     6b4:	09 f0       	breq	.+2      	; 0x6b8 <_Z11philosopheriii+0x396>
     6b6:	a1 c2       	rjmp	.+1346   	; 0xbfa <_Z11philosopheriii+0x8d8>
     6b8:	ec e0       	ldi	r30, 0x0C	; 12
     6ba:	f0 e0       	ldi	r31, 0x00	; 0
     6bc:	8b e1       	ldi	r24, 0x1B	; 27
     6be:	80 a3       	std	Z+32, r24	; 0x20
     6c0:	20 91 96 03 	lds	r18, 0x0396
     6c4:	30 91 97 03 	lds	r19, 0x0397
     6c8:	05 c0       	rjmp	.+10     	; 0x6d4 <_Z11philosopheriii+0x3b2>
     6ca:	eb e0       	ldi	r30, 0x0B	; 11
     6cc:	f0 e0       	ldi	r31, 0x00	; 0
     6ce:	80 a1       	ldd	r24, Z+32	; 0x20
     6d0:	85 fd       	sbrc	r24, 5
     6d2:	08 c0       	rjmp	.+16     	; 0x6e4 <_Z11philosopheriii+0x3c2>
     6d4:	21 15       	cp	r18, r1
     6d6:	31 05       	cpc	r19, r1
     6d8:	c1 f3       	breq	.-16     	; 0x6ca <_Z11philosopheriii+0x3a8>
     6da:	eb e7       	ldi	r30, 0x7B	; 123
     6dc:	f0 e0       	ldi	r31, 0x00	; 0
     6de:	80 a1       	ldd	r24, Z+32	; 0x20
     6e0:	85 ff       	sbrs	r24, 5
     6e2:	f8 cf       	rjmp	.-16     	; 0x6d4 <_Z11philosopheriii+0x3b2>
     6e4:	23 2b       	or	r18, r19
     6e6:	09 f0       	breq	.+2      	; 0x6ea <_Z11philosopheriii+0x3c8>
     6e8:	9c c2       	rjmp	.+1336   	; 0xc22 <_Z11philosopheriii+0x900>
     6ea:	ec e0       	ldi	r30, 0x0C	; 12
     6ec:	f0 e0       	ldi	r31, 0x00	; 0
     6ee:	8b e5       	ldi	r24, 0x5B	; 91
     6f0:	80 a3       	std	Z+32, r24	; 0x20
     6f2:	90 91 9a 03 	lds	r25, 0x039A
     6f6:	94 36       	cpi	r25, 0x64	; 100
     6f8:	08 f0       	brcs	.+2      	; 0x6fc <_Z11philosopheriii+0x3da>
     6fa:	8c c2       	rjmp	.+1304   	; 0xc14 <_Z11philosopheriii+0x8f2>
     6fc:	40 e3       	ldi	r20, 0x30	; 48
     6fe:	20 91 96 03 	lds	r18, 0x0396
     702:	30 91 97 03 	lds	r19, 0x0397
     706:	05 c0       	rjmp	.+10     	; 0x712 <_Z11philosopheriii+0x3f0>
     708:	eb e0       	ldi	r30, 0x0B	; 11
     70a:	f0 e0       	ldi	r31, 0x00	; 0
     70c:	80 a1       	ldd	r24, Z+32	; 0x20
     70e:	85 fd       	sbrc	r24, 5
     710:	08 c0       	rjmp	.+16     	; 0x722 <_Z11philosopheriii+0x400>
     712:	21 15       	cp	r18, r1
     714:	31 05       	cpc	r19, r1
     716:	c1 f3       	breq	.-16     	; 0x708 <_Z11philosopheriii+0x3e6>
     718:	eb e7       	ldi	r30, 0x7B	; 123
     71a:	f0 e0       	ldi	r31, 0x00	; 0
     71c:	80 a1       	ldd	r24, Z+32	; 0x20
     71e:	85 ff       	sbrs	r24, 5
     720:	f8 cf       	rjmp	.-16     	; 0x712 <_Z11philosopheriii+0x3f0>
     722:	23 2b       	or	r18, r19
     724:	09 f0       	breq	.+2      	; 0x728 <_Z11philosopheriii+0x406>
     726:	73 c2       	rjmp	.+1254   	; 0xc0e <_Z11philosopheriii+0x8ec>
     728:	ec e0       	ldi	r30, 0x0C	; 12
     72a:	f0 e0       	ldi	r31, 0x00	; 0
     72c:	40 a3       	std	Z+32, r20	; 0x20
     72e:	9a 30       	cpi	r25, 0x0A	; 10
     730:	08 f0       	brcs	.+2      	; 0x734 <_Z11philosopheriii+0x412>
     732:	66 c2       	rjmp	.+1228   	; 0xc00 <_Z11philosopheriii+0x8de>
     734:	40 e3       	ldi	r20, 0x30	; 48
     736:	20 91 96 03 	lds	r18, 0x0396
     73a:	30 91 97 03 	lds	r19, 0x0397
     73e:	05 c0       	rjmp	.+10     	; 0x74a <_Z11philosopheriii+0x428>
     740:	eb e0       	ldi	r30, 0x0B	; 11
     742:	f0 e0       	ldi	r31, 0x00	; 0
     744:	80 a1       	ldd	r24, Z+32	; 0x20
     746:	85 fd       	sbrc	r24, 5
     748:	08 c0       	rjmp	.+16     	; 0x75a <_Z11philosopheriii+0x438>
     74a:	21 15       	cp	r18, r1
     74c:	31 05       	cpc	r19, r1
     74e:	c1 f3       	breq	.-16     	; 0x740 <_Z11philosopheriii+0x41e>
     750:	eb e7       	ldi	r30, 0x7B	; 123
     752:	f0 e0       	ldi	r31, 0x00	; 0
     754:	80 a1       	ldd	r24, Z+32	; 0x20
     756:	85 ff       	sbrs	r24, 5
     758:	f8 cf       	rjmp	.-16     	; 0x74a <_Z11philosopheriii+0x428>
     75a:	23 2b       	or	r18, r19
     75c:	09 f0       	breq	.+2      	; 0x760 <_Z11philosopheriii+0x43e>
     75e:	4a c2       	rjmp	.+1172   	; 0xbf4 <_Z11philosopheriii+0x8d2>
     760:	ec e0       	ldi	r30, 0x0C	; 12
     762:	f0 e0       	ldi	r31, 0x00	; 0
     764:	40 a3       	std	Z+32, r20	; 0x20
     766:	90 5d       	subi	r25, 0xD0	; 208
     768:	20 91 96 03 	lds	r18, 0x0396
     76c:	30 91 97 03 	lds	r19, 0x0397
     770:	05 c0       	rjmp	.+10     	; 0x77c <_Z11philosopheriii+0x45a>
     772:	eb e0       	ldi	r30, 0x0B	; 11
     774:	f0 e0       	ldi	r31, 0x00	; 0
     776:	80 a1       	ldd	r24, Z+32	; 0x20
     778:	85 fd       	sbrc	r24, 5
     77a:	08 c0       	rjmp	.+16     	; 0x78c <_Z11philosopheriii+0x46a>
     77c:	21 15       	cp	r18, r1
     77e:	31 05       	cpc	r19, r1
     780:	c1 f3       	breq	.-16     	; 0x772 <_Z11philosopheriii+0x450>
     782:	eb e7       	ldi	r30, 0x7B	; 123
     784:	f0 e0       	ldi	r31, 0x00	; 0
     786:	80 a1       	ldd	r24, Z+32	; 0x20
     788:	85 ff       	sbrs	r24, 5
     78a:	f8 cf       	rjmp	.-16     	; 0x77c <_Z11philosopheriii+0x45a>
     78c:	23 2b       	or	r18, r19
     78e:	09 f0       	breq	.+2      	; 0x792 <_Z11philosopheriii+0x470>
     790:	1a c2       	rjmp	.+1076   	; 0xbc6 <_Z11philosopheriii+0x8a4>
     792:	ec e0       	ldi	r30, 0x0C	; 12
     794:	f0 e0       	ldi	r31, 0x00	; 0
     796:	90 a3       	std	Z+32, r25	; 0x20
     798:	20 91 96 03 	lds	r18, 0x0396
     79c:	30 91 97 03 	lds	r19, 0x0397
     7a0:	05 c0       	rjmp	.+10     	; 0x7ac <_Z11philosopheriii+0x48a>
     7a2:	eb e0       	ldi	r30, 0x0B	; 11
     7a4:	f0 e0       	ldi	r31, 0x00	; 0
     7a6:	80 a1       	ldd	r24, Z+32	; 0x20
     7a8:	85 fd       	sbrc	r24, 5
     7aa:	08 c0       	rjmp	.+16     	; 0x7bc <_Z11philosopheriii+0x49a>
     7ac:	21 15       	cp	r18, r1
     7ae:	31 05       	cpc	r19, r1
     7b0:	c1 f3       	breq	.-16     	; 0x7a2 <_Z11philosopheriii+0x480>
     7b2:	eb e7       	ldi	r30, 0x7B	; 123
     7b4:	f0 e0       	ldi	r31, 0x00	; 0
     7b6:	80 a1       	ldd	r24, Z+32	; 0x20
     7b8:	85 ff       	sbrs	r24, 5
     7ba:	f8 cf       	rjmp	.-16     	; 0x7ac <_Z11philosopheriii+0x48a>
     7bc:	23 2b       	or	r18, r19
     7be:	09 f0       	breq	.+2      	; 0x7c2 <_Z11philosopheriii+0x4a0>
     7c0:	16 c2       	rjmp	.+1068   	; 0xbee <_Z11philosopheriii+0x8cc>
     7c2:	ec e0       	ldi	r30, 0x0C	; 12
     7c4:	f0 e0       	ldi	r31, 0x00	; 0
     7c6:	8b e3       	ldi	r24, 0x3B	; 59
     7c8:	80 a3       	std	Z+32, r24	; 0x20
     7ca:	90 91 9c 03 	lds	r25, 0x039C
     7ce:	94 36       	cpi	r25, 0x64	; 100
     7d0:	08 f0       	brcs	.+2      	; 0x7d4 <_Z11philosopheriii+0x4b2>
     7d2:	06 c2       	rjmp	.+1036   	; 0xbe0 <_Z11philosopheriii+0x8be>
     7d4:	40 e3       	ldi	r20, 0x30	; 48
     7d6:	20 91 96 03 	lds	r18, 0x0396
     7da:	30 91 97 03 	lds	r19, 0x0397
     7de:	05 c0       	rjmp	.+10     	; 0x7ea <_Z11philosopheriii+0x4c8>
     7e0:	eb e0       	ldi	r30, 0x0B	; 11
     7e2:	f0 e0       	ldi	r31, 0x00	; 0
     7e4:	80 a1       	ldd	r24, Z+32	; 0x20
     7e6:	85 fd       	sbrc	r24, 5
     7e8:	08 c0       	rjmp	.+16     	; 0x7fa <_Z11philosopheriii+0x4d8>
     7ea:	21 15       	cp	r18, r1
     7ec:	31 05       	cpc	r19, r1
     7ee:	c1 f3       	breq	.-16     	; 0x7e0 <_Z11philosopheriii+0x4be>
     7f0:	eb e7       	ldi	r30, 0x7B	; 123
     7f2:	f0 e0       	ldi	r31, 0x00	; 0
     7f4:	80 a1       	ldd	r24, Z+32	; 0x20
     7f6:	85 ff       	sbrs	r24, 5
     7f8:	f8 cf       	rjmp	.-16     	; 0x7ea <_Z11philosopheriii+0x4c8>
     7fa:	23 2b       	or	r18, r19
     7fc:	09 f0       	breq	.+2      	; 0x800 <_Z11philosopheriii+0x4de>
     7fe:	ed c1       	rjmp	.+986    	; 0xbda <_Z11philosopheriii+0x8b8>
     800:	ec e0       	ldi	r30, 0x0C	; 12
     802:	f0 e0       	ldi	r31, 0x00	; 0
     804:	40 a3       	std	Z+32, r20	; 0x20
     806:	9a 30       	cpi	r25, 0x0A	; 10
     808:	08 f0       	brcs	.+2      	; 0x80c <_Z11philosopheriii+0x4ea>
     80a:	e0 c1       	rjmp	.+960    	; 0xbcc <_Z11philosopheriii+0x8aa>
     80c:	40 e3       	ldi	r20, 0x30	; 48
     80e:	20 91 96 03 	lds	r18, 0x0396
     812:	30 91 97 03 	lds	r19, 0x0397
     816:	05 c0       	rjmp	.+10     	; 0x822 <_Z11philosopheriii+0x500>
     818:	eb e0       	ldi	r30, 0x0B	; 11
     81a:	f0 e0       	ldi	r31, 0x00	; 0
     81c:	80 a1       	ldd	r24, Z+32	; 0x20
     81e:	85 fd       	sbrc	r24, 5
     820:	08 c0       	rjmp	.+16     	; 0x832 <_Z11philosopheriii+0x510>
     822:	21 15       	cp	r18, r1
     824:	31 05       	cpc	r19, r1
     826:	c1 f3       	breq	.-16     	; 0x818 <_Z11philosopheriii+0x4f6>
     828:	eb e7       	ldi	r30, 0x7B	; 123
     82a:	f0 e0       	ldi	r31, 0x00	; 0
     82c:	80 a1       	ldd	r24, Z+32	; 0x20
     82e:	85 ff       	sbrs	r24, 5
     830:	f8 cf       	rjmp	.-16     	; 0x822 <_Z11philosopheriii+0x500>
     832:	23 2b       	or	r18, r19
     834:	09 f0       	breq	.+2      	; 0x838 <_Z11philosopheriii+0x516>
     836:	c4 c1       	rjmp	.+904    	; 0xbc0 <_Z11philosopheriii+0x89e>
     838:	ec e0       	ldi	r30, 0x0C	; 12
     83a:	f0 e0       	ldi	r31, 0x00	; 0
     83c:	40 a3       	std	Z+32, r20	; 0x20
     83e:	90 5d       	subi	r25, 0xD0	; 208
     840:	20 91 96 03 	lds	r18, 0x0396
     844:	30 91 97 03 	lds	r19, 0x0397
     848:	05 c0       	rjmp	.+10     	; 0x854 <_Z11philosopheriii+0x532>
     84a:	eb e0       	ldi	r30, 0x0B	; 11
     84c:	f0 e0       	ldi	r31, 0x00	; 0
     84e:	80 a1       	ldd	r24, Z+32	; 0x20
     850:	85 fd       	sbrc	r24, 5
     852:	08 c0       	rjmp	.+16     	; 0x864 <_Z11philosopheriii+0x542>
     854:	21 15       	cp	r18, r1
     856:	31 05       	cpc	r19, r1
     858:	c1 f3       	breq	.-16     	; 0x84a <_Z11philosopheriii+0x528>
     85a:	eb e7       	ldi	r30, 0x7B	; 123
     85c:	f0 e0       	ldi	r31, 0x00	; 0
     85e:	80 a1       	ldd	r24, Z+32	; 0x20
     860:	85 ff       	sbrs	r24, 5
     862:	f8 cf       	rjmp	.-16     	; 0x854 <_Z11philosopheriii+0x532>
     864:	23 2b       	or	r18, r19
     866:	09 f0       	breq	.+2      	; 0x86a <_Z11philosopheriii+0x548>
     868:	a8 c1       	rjmp	.+848    	; 0xbba <_Z11philosopheriii+0x898>
     86a:	ec e0       	ldi	r30, 0x0C	; 12
     86c:	f0 e0       	ldi	r31, 0x00	; 0
     86e:	90 a3       	std	Z+32, r25	; 0x20
     870:	20 91 96 03 	lds	r18, 0x0396
     874:	30 91 97 03 	lds	r19, 0x0397
     878:	05 c0       	rjmp	.+10     	; 0x884 <_Z11philosopheriii+0x562>
     87a:	eb e0       	ldi	r30, 0x0B	; 11
     87c:	f0 e0       	ldi	r31, 0x00	; 0
     87e:	80 a1       	ldd	r24, Z+32	; 0x20
     880:	85 fd       	sbrc	r24, 5
     882:	08 c0       	rjmp	.+16     	; 0x894 <_Z11philosopheriii+0x572>
     884:	21 15       	cp	r18, r1
     886:	31 05       	cpc	r19, r1
     888:	c1 f3       	breq	.-16     	; 0x87a <_Z11philosopheriii+0x558>
     88a:	eb e7       	ldi	r30, 0x7B	; 123
     88c:	f0 e0       	ldi	r31, 0x00	; 0
     88e:	80 a1       	ldd	r24, Z+32	; 0x20
     890:	85 ff       	sbrs	r24, 5
     892:	f8 cf       	rjmp	.-16     	; 0x884 <_Z11philosopheriii+0x562>
     894:	23 2b       	or	r18, r19
     896:	09 f0       	breq	.+2      	; 0x89a <_Z11philosopheriii+0x578>
     898:	8d c1       	rjmp	.+794    	; 0xbb4 <_Z11philosopheriii+0x892>
     89a:	ec e0       	ldi	r30, 0x0C	; 12
     89c:	f0 e0       	ldi	r31, 0x00	; 0
     89e:	88 e4       	ldi	r24, 0x48	; 72
     8a0:	80 a3       	std	Z+32, r24	; 0x20
     8a2:	69 e5       	ldi	r22, 0x59	; 89
     8a4:	71 e0       	ldi	r23, 0x01	; 1
     8a6:	88 e7       	ldi	r24, 0x78	; 120
     8a8:	93 e0       	ldi	r25, 0x03	; 3
     8aa:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
     8ae:	f8 94       	cli
     8b0:	20 91 94 03 	lds	r18, 0x0394
     8b4:	30 91 95 03 	lds	r19, 0x0395
     8b8:	80 91 94 03 	lds	r24, 0x0394
     8bc:	90 91 95 03 	lds	r25, 0x0395
     8c0:	01 96       	adiw	r24, 0x01	; 1
     8c2:	90 93 95 03 	sts	0x0395, r25
     8c6:	80 93 94 03 	sts	0x0394, r24
     8ca:	78 94       	sei
     8cc:	37 fd       	sbrc	r19, 7
     8ce:	f3 c1       	rjmp	.+998    	; 0xcb6 <_Z11philosopheriii+0x994>
     8d0:	80 e2       	ldi	r24, 0x20	; 32
     8d2:	91 ea       	ldi	r25, 0xA1	; 161
     8d4:	a7 e0       	ldi	r26, 0x07	; 7
     8d6:	b0 e0       	ldi	r27, 0x00	; 0
     8d8:	89 83       	std	Y+1, r24	; 0x01
     8da:	9a 83       	std	Y+2, r25	; 0x02
     8dc:	ab 83       	std	Y+3, r26	; 0x03
     8de:	bc 83       	std	Y+4, r27	; 0x04
     8e0:	ce 01       	movw	r24, r28
     8e2:	01 96       	adiw	r24, 0x01	; 1
     8e4:	0e 94 b8 1f 	call	0x3f70	; 0x3f70 <_ZN6System5Alarm5delayERKm>
     8e8:	f4 01       	movw	r30, r8
     8ea:	e6 58       	subi	r30, 0x86	; 134
     8ec:	fc 4f       	sbci	r31, 0xFC	; 252
     8ee:	01 90       	ld	r0, Z+
     8f0:	f0 81       	ld	r31, Z
     8f2:	e0 2d       	mov	r30, r0
     8f4:	f8 94       	cli
     8f6:	26 81       	ldd	r18, Z+6	; 0x06
     8f8:	37 81       	ldd	r19, Z+7	; 0x07
     8fa:	86 81       	ldd	r24, Z+6	; 0x06
     8fc:	97 81       	ldd	r25, Z+7	; 0x07
     8fe:	01 96       	adiw	r24, 0x01	; 1
     900:	97 83       	std	Z+7, r25	; 0x07
     902:	86 83       	std	Z+6, r24	; 0x06
     904:	78 94       	sei
     906:	37 fd       	sbrc	r19, 7
     908:	d2 c1       	rjmp	.+932    	; 0xcae <_Z11philosopheriii+0x98c>
     90a:	f5 01       	movw	r30, r10
     90c:	e6 58       	subi	r30, 0x86	; 134
     90e:	fc 4f       	sbci	r31, 0xFC	; 252
     910:	01 90       	ld	r0, Z+
     912:	f0 81       	ld	r31, Z
     914:	e0 2d       	mov	r30, r0
     916:	f8 94       	cli
     918:	26 81       	ldd	r18, Z+6	; 0x06
     91a:	37 81       	ldd	r19, Z+7	; 0x07
     91c:	86 81       	ldd	r24, Z+6	; 0x06
     91e:	97 81       	ldd	r25, Z+7	; 0x07
     920:	01 96       	adiw	r24, 0x01	; 1
     922:	97 83       	std	Z+7, r25	; 0x07
     924:	86 83       	std	Z+6, r24	; 0x06
     926:	78 94       	sei
     928:	37 fd       	sbrc	r19, 7
     92a:	bd c1       	rjmp	.+890    	; 0xca6 <_Z11philosopheriii+0x984>
     92c:	08 94       	sec
     92e:	c1 08       	sbc	r12, r1
     930:	d1 08       	sbc	r13, r1
     932:	c1 14       	cp	r12, r1
     934:	d1 04       	cpc	r13, r1
     936:	09 f0       	breq	.+2      	; 0x93a <_Z11philosopheriii+0x618>
     938:	22 cd       	rjmp	.-1468   	; 0x37e <_Z11philosopheriii+0x5c>
     93a:	f8 94       	cli
     93c:	20 91 94 03 	lds	r18, 0x0394
     940:	30 91 95 03 	lds	r19, 0x0395
     944:	80 91 94 03 	lds	r24, 0x0394
     948:	90 91 95 03 	lds	r25, 0x0395
     94c:	01 97       	sbiw	r24, 0x01	; 1
     94e:	90 93 95 03 	sts	0x0395, r25
     952:	80 93 94 03 	sts	0x0394, r24
     956:	78 94       	sei
     958:	12 16       	cp	r1, r18
     95a:	13 06       	cpc	r1, r19
     95c:	0c f0       	brlt	.+2      	; 0x960 <_Z11philosopheriii+0x63e>
     95e:	04 c2       	rjmp	.+1032   	; 0xd68 <_Z11philosopheriii+0xa46>
     960:	10 93 9b 03 	sts	0x039B, r17
     964:	00 93 9a 03 	sts	0x039A, r16
     968:	f0 92 9d 03 	sts	0x039D, r15
     96c:	e0 92 9c 03 	sts	0x039C, r14
     970:	20 91 96 03 	lds	r18, 0x0396
     974:	30 91 97 03 	lds	r19, 0x0397
     978:	05 c0       	rjmp	.+10     	; 0x984 <_Z11philosopheriii+0x662>
     97a:	eb e0       	ldi	r30, 0x0B	; 11
     97c:	f0 e0       	ldi	r31, 0x00	; 0
     97e:	80 a1       	ldd	r24, Z+32	; 0x20
     980:	85 fd       	sbrc	r24, 5
     982:	08 c0       	rjmp	.+16     	; 0x994 <_Z11philosopheriii+0x672>
     984:	21 15       	cp	r18, r1
     986:	31 05       	cpc	r19, r1
     988:	c1 f3       	breq	.-16     	; 0x97a <_Z11philosopheriii+0x658>
     98a:	eb e7       	ldi	r30, 0x7B	; 123
     98c:	f0 e0       	ldi	r31, 0x00	; 0
     98e:	80 a1       	ldd	r24, Z+32	; 0x20
     990:	85 ff       	sbrs	r24, 5
     992:	f8 cf       	rjmp	.-16     	; 0x984 <_Z11philosopheriii+0x662>
     994:	23 2b       	or	r18, r19
     996:	09 f0       	breq	.+2      	; 0x99a <_Z11philosopheriii+0x678>
     998:	dc c1       	rjmp	.+952    	; 0xd52 <_Z11philosopheriii+0xa30>
     99a:	ec e0       	ldi	r30, 0x0C	; 12
     99c:	f0 e0       	ldi	r31, 0x00	; 0
     99e:	8b e1       	ldi	r24, 0x1B	; 27
     9a0:	80 a3       	std	Z+32, r24	; 0x20
     9a2:	20 91 96 03 	lds	r18, 0x0396
     9a6:	30 91 97 03 	lds	r19, 0x0397
     9aa:	05 c0       	rjmp	.+10     	; 0x9b6 <_Z11philosopheriii+0x694>
     9ac:	eb e0       	ldi	r30, 0x0B	; 11
     9ae:	f0 e0       	ldi	r31, 0x00	; 0
     9b0:	80 a1       	ldd	r24, Z+32	; 0x20
     9b2:	85 fd       	sbrc	r24, 5
     9b4:	08 c0       	rjmp	.+16     	; 0x9c6 <_Z11philosopheriii+0x6a4>
     9b6:	21 15       	cp	r18, r1
     9b8:	31 05       	cpc	r19, r1
     9ba:	c1 f3       	breq	.-16     	; 0x9ac <_Z11philosopheriii+0x68a>
     9bc:	eb e7       	ldi	r30, 0x7B	; 123
     9be:	f0 e0       	ldi	r31, 0x00	; 0
     9c0:	80 a1       	ldd	r24, Z+32	; 0x20
     9c2:	85 ff       	sbrs	r24, 5
     9c4:	f8 cf       	rjmp	.-16     	; 0x9b6 <_Z11philosopheriii+0x694>
     9c6:	23 2b       	or	r18, r19
     9c8:	09 f0       	breq	.+2      	; 0x9cc <_Z11philosopheriii+0x6aa>
     9ca:	c0 c1       	rjmp	.+896    	; 0xd4c <_Z11philosopheriii+0xa2a>
     9cc:	ec e0       	ldi	r30, 0x0C	; 12
     9ce:	f0 e0       	ldi	r31, 0x00	; 0
     9d0:	8b e5       	ldi	r24, 0x5B	; 91
     9d2:	80 a3       	std	Z+32, r24	; 0x20
     9d4:	90 91 9a 03 	lds	r25, 0x039A
     9d8:	94 36       	cpi	r25, 0x64	; 100
     9da:	08 f0       	brcs	.+2      	; 0x9de <_Z11philosopheriii+0x6bc>
     9dc:	b0 c1       	rjmp	.+864    	; 0xd3e <_Z11philosopheriii+0xa1c>
     9de:	40 e3       	ldi	r20, 0x30	; 48
     9e0:	20 91 96 03 	lds	r18, 0x0396
     9e4:	30 91 97 03 	lds	r19, 0x0397
     9e8:	05 c0       	rjmp	.+10     	; 0x9f4 <_Z11philosopheriii+0x6d2>
     9ea:	eb e0       	ldi	r30, 0x0B	; 11
     9ec:	f0 e0       	ldi	r31, 0x00	; 0
     9ee:	80 a1       	ldd	r24, Z+32	; 0x20
     9f0:	85 fd       	sbrc	r24, 5
     9f2:	08 c0       	rjmp	.+16     	; 0xa04 <_Z11philosopheriii+0x6e2>
     9f4:	21 15       	cp	r18, r1
     9f6:	31 05       	cpc	r19, r1
     9f8:	c1 f3       	breq	.-16     	; 0x9ea <_Z11philosopheriii+0x6c8>
     9fa:	eb e7       	ldi	r30, 0x7B	; 123
     9fc:	f0 e0       	ldi	r31, 0x00	; 0
     9fe:	80 a1       	ldd	r24, Z+32	; 0x20
     a00:	85 ff       	sbrs	r24, 5
     a02:	f8 cf       	rjmp	.-16     	; 0x9f4 <_Z11philosopheriii+0x6d2>
     a04:	23 2b       	or	r18, r19
     a06:	09 f0       	breq	.+2      	; 0xa0a <_Z11philosopheriii+0x6e8>
     a08:	97 c1       	rjmp	.+814    	; 0xd38 <_Z11philosopheriii+0xa16>
     a0a:	ec e0       	ldi	r30, 0x0C	; 12
     a0c:	f0 e0       	ldi	r31, 0x00	; 0
     a0e:	40 a3       	std	Z+32, r20	; 0x20
     a10:	9a 30       	cpi	r25, 0x0A	; 10
     a12:	08 f0       	brcs	.+2      	; 0xa16 <_Z11philosopheriii+0x6f4>
     a14:	8a c1       	rjmp	.+788    	; 0xd2a <_Z11philosopheriii+0xa08>
     a16:	40 e3       	ldi	r20, 0x30	; 48
     a18:	20 91 96 03 	lds	r18, 0x0396
     a1c:	30 91 97 03 	lds	r19, 0x0397
     a20:	05 c0       	rjmp	.+10     	; 0xa2c <_Z11philosopheriii+0x70a>
     a22:	eb e0       	ldi	r30, 0x0B	; 11
     a24:	f0 e0       	ldi	r31, 0x00	; 0
     a26:	80 a1       	ldd	r24, Z+32	; 0x20
     a28:	85 fd       	sbrc	r24, 5
     a2a:	08 c0       	rjmp	.+16     	; 0xa3c <_Z11philosopheriii+0x71a>
     a2c:	21 15       	cp	r18, r1
     a2e:	31 05       	cpc	r19, r1
     a30:	c1 f3       	breq	.-16     	; 0xa22 <_Z11philosopheriii+0x700>
     a32:	eb e7       	ldi	r30, 0x7B	; 123
     a34:	f0 e0       	ldi	r31, 0x00	; 0
     a36:	80 a1       	ldd	r24, Z+32	; 0x20
     a38:	85 ff       	sbrs	r24, 5
     a3a:	f8 cf       	rjmp	.-16     	; 0xa2c <_Z11philosopheriii+0x70a>
     a3c:	23 2b       	or	r18, r19
     a3e:	09 f0       	breq	.+2      	; 0xa42 <_Z11philosopheriii+0x720>
     a40:	71 c1       	rjmp	.+738    	; 0xd24 <_Z11philosopheriii+0xa02>
     a42:	ec e0       	ldi	r30, 0x0C	; 12
     a44:	f0 e0       	ldi	r31, 0x00	; 0
     a46:	40 a3       	std	Z+32, r20	; 0x20
     a48:	90 5d       	subi	r25, 0xD0	; 208
     a4a:	20 91 96 03 	lds	r18, 0x0396
     a4e:	30 91 97 03 	lds	r19, 0x0397
     a52:	05 c0       	rjmp	.+10     	; 0xa5e <_Z11philosopheriii+0x73c>
     a54:	eb e0       	ldi	r30, 0x0B	; 11
     a56:	f0 e0       	ldi	r31, 0x00	; 0
     a58:	80 a1       	ldd	r24, Z+32	; 0x20
     a5a:	85 fd       	sbrc	r24, 5
     a5c:	08 c0       	rjmp	.+16     	; 0xa6e <_Z11philosopheriii+0x74c>
     a5e:	21 15       	cp	r18, r1
     a60:	31 05       	cpc	r19, r1
     a62:	c1 f3       	breq	.-16     	; 0xa54 <_Z11philosopheriii+0x732>
     a64:	eb e7       	ldi	r30, 0x7B	; 123
     a66:	f0 e0       	ldi	r31, 0x00	; 0
     a68:	80 a1       	ldd	r24, Z+32	; 0x20
     a6a:	85 ff       	sbrs	r24, 5
     a6c:	f8 cf       	rjmp	.-16     	; 0xa5e <_Z11philosopheriii+0x73c>
     a6e:	23 2b       	or	r18, r19
     a70:	09 f0       	breq	.+2      	; 0xa74 <_Z11philosopheriii+0x752>
     a72:	55 c1       	rjmp	.+682    	; 0xd1e <_Z11philosopheriii+0x9fc>
     a74:	ec e0       	ldi	r30, 0x0C	; 12
     a76:	f0 e0       	ldi	r31, 0x00	; 0
     a78:	90 a3       	std	Z+32, r25	; 0x20
     a7a:	20 91 96 03 	lds	r18, 0x0396
     a7e:	30 91 97 03 	lds	r19, 0x0397
     a82:	05 c0       	rjmp	.+10     	; 0xa8e <_Z11philosopheriii+0x76c>
     a84:	eb e0       	ldi	r30, 0x0B	; 11
     a86:	f0 e0       	ldi	r31, 0x00	; 0
     a88:	80 a1       	ldd	r24, Z+32	; 0x20
     a8a:	85 fd       	sbrc	r24, 5
     a8c:	08 c0       	rjmp	.+16     	; 0xa9e <_Z11philosopheriii+0x77c>
     a8e:	21 15       	cp	r18, r1
     a90:	31 05       	cpc	r19, r1
     a92:	c1 f3       	breq	.-16     	; 0xa84 <_Z11philosopheriii+0x762>
     a94:	eb e7       	ldi	r30, 0x7B	; 123
     a96:	f0 e0       	ldi	r31, 0x00	; 0
     a98:	80 a1       	ldd	r24, Z+32	; 0x20
     a9a:	85 ff       	sbrs	r24, 5
     a9c:	f8 cf       	rjmp	.-16     	; 0xa8e <_Z11philosopheriii+0x76c>
     a9e:	23 2b       	or	r18, r19
     aa0:	09 f0       	breq	.+2      	; 0xaa4 <_Z11philosopheriii+0x782>
     aa2:	3a c1       	rjmp	.+628    	; 0xd18 <_Z11philosopheriii+0x9f6>
     aa4:	ec e0       	ldi	r30, 0x0C	; 12
     aa6:	f0 e0       	ldi	r31, 0x00	; 0
     aa8:	8b e3       	ldi	r24, 0x3B	; 59
     aaa:	80 a3       	std	Z+32, r24	; 0x20
     aac:	90 91 9c 03 	lds	r25, 0x039C
     ab0:	94 36       	cpi	r25, 0x64	; 100
     ab2:	08 f0       	brcs	.+2      	; 0xab6 <_Z11philosopheriii+0x794>
     ab4:	2a c1       	rjmp	.+596    	; 0xd0a <_Z11philosopheriii+0x9e8>
     ab6:	40 e3       	ldi	r20, 0x30	; 48
     ab8:	20 91 96 03 	lds	r18, 0x0396
     abc:	30 91 97 03 	lds	r19, 0x0397
     ac0:	05 c0       	rjmp	.+10     	; 0xacc <_Z11philosopheriii+0x7aa>
     ac2:	eb e0       	ldi	r30, 0x0B	; 11
     ac4:	f0 e0       	ldi	r31, 0x00	; 0
     ac6:	80 a1       	ldd	r24, Z+32	; 0x20
     ac8:	85 fd       	sbrc	r24, 5
     aca:	08 c0       	rjmp	.+16     	; 0xadc <_Z11philosopheriii+0x7ba>
     acc:	21 15       	cp	r18, r1
     ace:	31 05       	cpc	r19, r1
     ad0:	c1 f3       	breq	.-16     	; 0xac2 <_Z11philosopheriii+0x7a0>
     ad2:	eb e7       	ldi	r30, 0x7B	; 123
     ad4:	f0 e0       	ldi	r31, 0x00	; 0
     ad6:	80 a1       	ldd	r24, Z+32	; 0x20
     ad8:	85 ff       	sbrs	r24, 5
     ada:	f8 cf       	rjmp	.-16     	; 0xacc <_Z11philosopheriii+0x7aa>
     adc:	23 2b       	or	r18, r19
     ade:	09 f0       	breq	.+2      	; 0xae2 <_Z11philosopheriii+0x7c0>
     ae0:	11 c1       	rjmp	.+546    	; 0xd04 <_Z11philosopheriii+0x9e2>
     ae2:	ec e0       	ldi	r30, 0x0C	; 12
     ae4:	f0 e0       	ldi	r31, 0x00	; 0
     ae6:	40 a3       	std	Z+32, r20	; 0x20
     ae8:	9a 30       	cpi	r25, 0x0A	; 10
     aea:	08 f0       	brcs	.+2      	; 0xaee <_Z11philosopheriii+0x7cc>
     aec:	04 c1       	rjmp	.+520    	; 0xcf6 <_Z11philosopheriii+0x9d4>
     aee:	40 e3       	ldi	r20, 0x30	; 48
     af0:	20 91 96 03 	lds	r18, 0x0396
     af4:	30 91 97 03 	lds	r19, 0x0397
     af8:	05 c0       	rjmp	.+10     	; 0xb04 <_Z11philosopheriii+0x7e2>
     afa:	eb e0       	ldi	r30, 0x0B	; 11
     afc:	f0 e0       	ldi	r31, 0x00	; 0
     afe:	80 a1       	ldd	r24, Z+32	; 0x20
     b00:	85 fd       	sbrc	r24, 5
     b02:	08 c0       	rjmp	.+16     	; 0xb14 <_Z11philosopheriii+0x7f2>
     b04:	21 15       	cp	r18, r1
     b06:	31 05       	cpc	r19, r1
     b08:	c1 f3       	breq	.-16     	; 0xafa <_Z11philosopheriii+0x7d8>
     b0a:	eb e7       	ldi	r30, 0x7B	; 123
     b0c:	f0 e0       	ldi	r31, 0x00	; 0
     b0e:	80 a1       	ldd	r24, Z+32	; 0x20
     b10:	85 ff       	sbrs	r24, 5
     b12:	f8 cf       	rjmp	.-16     	; 0xb04 <_Z11philosopheriii+0x7e2>
     b14:	23 2b       	or	r18, r19
     b16:	09 f0       	breq	.+2      	; 0xb1a <_Z11philosopheriii+0x7f8>
     b18:	e8 c0       	rjmp	.+464    	; 0xcea <_Z11philosopheriii+0x9c8>
     b1a:	ec e0       	ldi	r30, 0x0C	; 12
     b1c:	f0 e0       	ldi	r31, 0x00	; 0
     b1e:	40 a3       	std	Z+32, r20	; 0x20
     b20:	90 5d       	subi	r25, 0xD0	; 208
     b22:	20 91 96 03 	lds	r18, 0x0396
     b26:	30 91 97 03 	lds	r19, 0x0397
     b2a:	05 c0       	rjmp	.+10     	; 0xb36 <_Z11philosopheriii+0x814>
     b2c:	eb e0       	ldi	r30, 0x0B	; 11
     b2e:	f0 e0       	ldi	r31, 0x00	; 0
     b30:	80 a1       	ldd	r24, Z+32	; 0x20
     b32:	85 fd       	sbrc	r24, 5
     b34:	08 c0       	rjmp	.+16     	; 0xb46 <_Z11philosopheriii+0x824>
     b36:	21 15       	cp	r18, r1
     b38:	31 05       	cpc	r19, r1
     b3a:	c1 f3       	breq	.-16     	; 0xb2c <_Z11philosopheriii+0x80a>
     b3c:	eb e7       	ldi	r30, 0x7B	; 123
     b3e:	f0 e0       	ldi	r31, 0x00	; 0
     b40:	80 a1       	ldd	r24, Z+32	; 0x20
     b42:	85 ff       	sbrs	r24, 5
     b44:	f8 cf       	rjmp	.-16     	; 0xb36 <_Z11philosopheriii+0x814>
     b46:	23 2b       	or	r18, r19
     b48:	09 f0       	breq	.+2      	; 0xb4c <_Z11philosopheriii+0x82a>
     b4a:	cc c0       	rjmp	.+408    	; 0xce4 <_Z11philosopheriii+0x9c2>
     b4c:	ec e0       	ldi	r30, 0x0C	; 12
     b4e:	f0 e0       	ldi	r31, 0x00	; 0
     b50:	90 a3       	std	Z+32, r25	; 0x20
     b52:	20 91 96 03 	lds	r18, 0x0396
     b56:	30 91 97 03 	lds	r19, 0x0397
     b5a:	05 c0       	rjmp	.+10     	; 0xb66 <_Z11philosopheriii+0x844>
     b5c:	eb e0       	ldi	r30, 0x0B	; 11
     b5e:	f0 e0       	ldi	r31, 0x00	; 0
     b60:	80 a1       	ldd	r24, Z+32	; 0x20
     b62:	85 fd       	sbrc	r24, 5
     b64:	08 c0       	rjmp	.+16     	; 0xb76 <_Z11philosopheriii+0x854>
     b66:	21 15       	cp	r18, r1
     b68:	31 05       	cpc	r19, r1
     b6a:	c1 f3       	breq	.-16     	; 0xb5c <_Z11philosopheriii+0x83a>
     b6c:	eb e7       	ldi	r30, 0x7B	; 123
     b6e:	f0 e0       	ldi	r31, 0x00	; 0
     b70:	80 a1       	ldd	r24, Z+32	; 0x20
     b72:	85 ff       	sbrs	r24, 5
     b74:	f8 cf       	rjmp	.-16     	; 0xb66 <_Z11philosopheriii+0x844>
     b76:	23 2b       	or	r18, r19
     b78:	09 f0       	breq	.+2      	; 0xb7c <_Z11philosopheriii+0x85a>
     b7a:	ba c0       	rjmp	.+372    	; 0xcf0 <_Z11philosopheriii+0x9ce>
     b7c:	ec e0       	ldi	r30, 0x0C	; 12
     b7e:	f0 e0       	ldi	r31, 0x00	; 0
     b80:	88 e4       	ldi	r24, 0x48	; 72
     b82:	80 a3       	std	Z+32, r24	; 0x20
     b84:	62 e6       	ldi	r22, 0x62	; 98
     b86:	71 e0       	ldi	r23, 0x01	; 1
     b88:	88 e7       	ldi	r24, 0x78	; 120
     b8a:	93 e0       	ldi	r25, 0x03	; 3
     b8c:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
     b90:	f8 94       	cli
     b92:	20 91 94 03 	lds	r18, 0x0394
     b96:	30 91 95 03 	lds	r19, 0x0395
     b9a:	80 91 94 03 	lds	r24, 0x0394
     b9e:	90 91 95 03 	lds	r25, 0x0395
     ba2:	01 96       	adiw	r24, 0x01	; 1
     ba4:	90 93 95 03 	sts	0x0395, r25
     ba8:	80 93 94 03 	sts	0x0394, r24
     bac:	78 94       	sei
     bae:	37 ff       	sbrs	r19, 7
     bb0:	e0 c0       	rjmp	.+448    	; 0xd72 <_Z11philosopheriii+0xa50>
     bb2:	d5 c0       	rjmp	.+426    	; 0xd5e <_Z11philosopheriii+0xa3c>
     bb4:	ec e7       	ldi	r30, 0x7C	; 124
     bb6:	f0 e0       	ldi	r31, 0x00	; 0
     bb8:	72 ce       	rjmp	.-796    	; 0x89e <_Z11philosopheriii+0x57c>
     bba:	ec e7       	ldi	r30, 0x7C	; 124
     bbc:	f0 e0       	ldi	r31, 0x00	; 0
     bbe:	57 ce       	rjmp	.-850    	; 0x86e <_Z11philosopheriii+0x54c>
     bc0:	ec e7       	ldi	r30, 0x7C	; 124
     bc2:	f0 e0       	ldi	r31, 0x00	; 0
     bc4:	3b ce       	rjmp	.-906    	; 0x83c <_Z11philosopheriii+0x51a>
     bc6:	ec e7       	ldi	r30, 0x7C	; 124
     bc8:	f0 e0       	ldi	r31, 0x00	; 0
     bca:	e5 cd       	rjmp	.-1078   	; 0x796 <_Z11philosopheriii+0x474>
     bcc:	80 e3       	ldi	r24, 0x30	; 48
     bce:	8f 5f       	subi	r24, 0xFF	; 255
     bd0:	9a 50       	subi	r25, 0x0A	; 10
     bd2:	9a 30       	cpi	r25, 0x0A	; 10
     bd4:	e0 f7       	brcc	.-8      	; 0xbce <_Z11philosopheriii+0x8ac>
     bd6:	48 2f       	mov	r20, r24
     bd8:	1a ce       	rjmp	.-972    	; 0x80e <_Z11philosopheriii+0x4ec>
     bda:	ec e7       	ldi	r30, 0x7C	; 124
     bdc:	f0 e0       	ldi	r31, 0x00	; 0
     bde:	12 ce       	rjmp	.-988    	; 0x804 <_Z11philosopheriii+0x4e2>
     be0:	80 e3       	ldi	r24, 0x30	; 48
     be2:	8f 5f       	subi	r24, 0xFF	; 255
     be4:	94 56       	subi	r25, 0x64	; 100
     be6:	94 36       	cpi	r25, 0x64	; 100
     be8:	e0 f7       	brcc	.-8      	; 0xbe2 <_Z11philosopheriii+0x8c0>
     bea:	48 2f       	mov	r20, r24
     bec:	f4 cd       	rjmp	.-1048   	; 0x7d6 <_Z11philosopheriii+0x4b4>
     bee:	ec e7       	ldi	r30, 0x7C	; 124
     bf0:	f0 e0       	ldi	r31, 0x00	; 0
     bf2:	e9 cd       	rjmp	.-1070   	; 0x7c6 <_Z11philosopheriii+0x4a4>
     bf4:	ec e7       	ldi	r30, 0x7C	; 124
     bf6:	f0 e0       	ldi	r31, 0x00	; 0
     bf8:	b5 cd       	rjmp	.-1174   	; 0x764 <_Z11philosopheriii+0x442>
     bfa:	ec e7       	ldi	r30, 0x7C	; 124
     bfc:	f0 e0       	ldi	r31, 0x00	; 0
     bfe:	5e cd       	rjmp	.-1348   	; 0x6bc <_Z11philosopheriii+0x39a>
     c00:	80 e3       	ldi	r24, 0x30	; 48
     c02:	8f 5f       	subi	r24, 0xFF	; 255
     c04:	9a 50       	subi	r25, 0x0A	; 10
     c06:	9a 30       	cpi	r25, 0x0A	; 10
     c08:	e0 f7       	brcc	.-8      	; 0xc02 <_Z11philosopheriii+0x8e0>
     c0a:	48 2f       	mov	r20, r24
     c0c:	94 cd       	rjmp	.-1240   	; 0x736 <_Z11philosopheriii+0x414>
     c0e:	ec e7       	ldi	r30, 0x7C	; 124
     c10:	f0 e0       	ldi	r31, 0x00	; 0
     c12:	8c cd       	rjmp	.-1256   	; 0x72c <_Z11philosopheriii+0x40a>
     c14:	80 e3       	ldi	r24, 0x30	; 48
     c16:	8f 5f       	subi	r24, 0xFF	; 255
     c18:	94 56       	subi	r25, 0x64	; 100
     c1a:	94 36       	cpi	r25, 0x64	; 100
     c1c:	e0 f7       	brcc	.-8      	; 0xc16 <_Z11philosopheriii+0x8f4>
     c1e:	48 2f       	mov	r20, r24
     c20:	6e cd       	rjmp	.-1316   	; 0x6fe <_Z11philosopheriii+0x3dc>
     c22:	ec e7       	ldi	r30, 0x7C	; 124
     c24:	f0 e0       	ldi	r31, 0x00	; 0
     c26:	63 cd       	rjmp	.-1338   	; 0x6ee <_Z11philosopheriii+0x3cc>
     c28:	ec e7       	ldi	r30, 0x7C	; 124
     c2a:	f0 e0       	ldi	r31, 0x00	; 0
     c2c:	cb cc       	rjmp	.-1642   	; 0x5c4 <_Z11philosopheriii+0x2a2>
     c2e:	ec e7       	ldi	r30, 0x7C	; 124
     c30:	f0 e0       	ldi	r31, 0x00	; 0
     c32:	b0 cc       	rjmp	.-1696   	; 0x594 <_Z11philosopheriii+0x272>
     c34:	ec e7       	ldi	r30, 0x7C	; 124
     c36:	f0 e0       	ldi	r31, 0x00	; 0
     c38:	94 cc       	rjmp	.-1752   	; 0x562 <_Z11philosopheriii+0x240>
     c3a:	ec e7       	ldi	r30, 0x7C	; 124
     c3c:	f0 e0       	ldi	r31, 0x00	; 0
     c3e:	3e cc       	rjmp	.-1924   	; 0x4bc <_Z11philosopheriii+0x19a>
     c40:	80 e3       	ldi	r24, 0x30	; 48
     c42:	8f 5f       	subi	r24, 0xFF	; 255
     c44:	9a 50       	subi	r25, 0x0A	; 10
     c46:	9a 30       	cpi	r25, 0x0A	; 10
     c48:	e0 f7       	brcc	.-8      	; 0xc42 <_Z11philosopheriii+0x920>
     c4a:	48 2f       	mov	r20, r24
     c4c:	73 cc       	rjmp	.-1818   	; 0x534 <_Z11philosopheriii+0x212>
     c4e:	ec e7       	ldi	r30, 0x7C	; 124
     c50:	f0 e0       	ldi	r31, 0x00	; 0
     c52:	6b cc       	rjmp	.-1834   	; 0x52a <_Z11philosopheriii+0x208>
     c54:	80 e3       	ldi	r24, 0x30	; 48
     c56:	8f 5f       	subi	r24, 0xFF	; 255
     c58:	94 56       	subi	r25, 0x64	; 100
     c5a:	94 36       	cpi	r25, 0x64	; 100
     c5c:	e0 f7       	brcc	.-8      	; 0xc56 <_Z11philosopheriii+0x934>
     c5e:	48 2f       	mov	r20, r24
     c60:	4d cc       	rjmp	.-1894   	; 0x4fc <_Z11philosopheriii+0x1da>
     c62:	ec e7       	ldi	r30, 0x7C	; 124
     c64:	f0 e0       	ldi	r31, 0x00	; 0
     c66:	42 cc       	rjmp	.-1916   	; 0x4ec <_Z11philosopheriii+0x1ca>
     c68:	ec e7       	ldi	r30, 0x7C	; 124
     c6a:	f0 e0       	ldi	r31, 0x00	; 0
     c6c:	0e cc       	rjmp	.-2020   	; 0x48a <_Z11philosopheriii+0x168>
     c6e:	ec e7       	ldi	r30, 0x7C	; 124
     c70:	f0 e0       	ldi	r31, 0x00	; 0
     c72:	b7 cb       	rjmp	.-2194   	; 0x3e2 <_Z11philosopheriii+0xc0>
     c74:	80 e3       	ldi	r24, 0x30	; 48
     c76:	8f 5f       	subi	r24, 0xFF	; 255
     c78:	9a 50       	subi	r25, 0x0A	; 10
     c7a:	9a 30       	cpi	r25, 0x0A	; 10
     c7c:	e0 f7       	brcc	.-8      	; 0xc76 <_Z11philosopheriii+0x954>
     c7e:	48 2f       	mov	r20, r24
     c80:	ed cb       	rjmp	.-2086   	; 0x45c <_Z11philosopheriii+0x13a>
     c82:	ec e7       	ldi	r30, 0x7C	; 124
     c84:	f0 e0       	ldi	r31, 0x00	; 0
     c86:	e5 cb       	rjmp	.-2102   	; 0x452 <_Z11philosopheriii+0x130>
     c88:	80 e3       	ldi	r24, 0x30	; 48
     c8a:	8f 5f       	subi	r24, 0xFF	; 255
     c8c:	94 56       	subi	r25, 0x64	; 100
     c8e:	94 36       	cpi	r25, 0x64	; 100
     c90:	e0 f7       	brcc	.-8      	; 0xc8a <_Z11philosopheriii+0x968>
     c92:	48 2f       	mov	r20, r24
     c94:	c7 cb       	rjmp	.-2162   	; 0x424 <_Z11philosopheriii+0x102>
     c96:	ec e7       	ldi	r30, 0x7C	; 124
     c98:	f0 e0       	ldi	r31, 0x00	; 0
     c9a:	bc cb       	rjmp	.-2184   	; 0x414 <_Z11philosopheriii+0xf2>
     c9c:	8e e8       	ldi	r24, 0x8E	; 142
     c9e:	93 e0       	ldi	r25, 0x03	; 3
     ca0:	0e 94 14 2a 	call	0x5428	; 0x5428 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
     ca4:	7f cb       	rjmp	.-2306   	; 0x3a4 <_Z11philosopheriii+0x82>
     ca6:	cf 01       	movw	r24, r30
     ca8:	0e 94 e1 22 	call	0x45c2	; 0x45c2 <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
     cac:	3f ce       	rjmp	.-898    	; 0x92c <_Z11philosopheriii+0x60a>
     cae:	cf 01       	movw	r24, r30
     cb0:	0e 94 e1 22 	call	0x45c2	; 0x45c2 <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
     cb4:	2a ce       	rjmp	.-940    	; 0x90a <_Z11philosopheriii+0x5e8>
     cb6:	8e e8       	ldi	r24, 0x8E	; 142
     cb8:	93 e0       	ldi	r25, 0x03	; 3
     cba:	0e 94 e1 22 	call	0x45c2	; 0x45c2 <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
     cbe:	08 ce       	rjmp	.-1008   	; 0x8d0 <_Z11philosopheriii+0x5ae>
     cc0:	cf 01       	movw	r24, r30
     cc2:	0e 94 14 2a 	call	0x5428	; 0x5428 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
     cc6:	c8 cc       	rjmp	.-1648   	; 0x658 <_Z11philosopheriii+0x336>
     cc8:	cf 01       	movw	r24, r30
     cca:	0e 94 14 2a 	call	0x5428	; 0x5428 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
     cce:	b1 cc       	rjmp	.-1694   	; 0x632 <_Z11philosopheriii+0x310>
     cd0:	8e e8       	ldi	r24, 0x8E	; 142
     cd2:	93 e0       	ldi	r25, 0x03	; 3
     cd4:	0e 94 e1 22 	call	0x45c2	; 0x45c2 <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
     cd8:	8e cc       	rjmp	.-1764   	; 0x5f6 <_Z11philosopheriii+0x2d4>
     cda:	8e e8       	ldi	r24, 0x8E	; 142
     cdc:	93 e0       	ldi	r25, 0x03	; 3
     cde:	0e 94 14 2a 	call	0x5428	; 0x5428 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
     ce2:	cd cc       	rjmp	.-1638   	; 0x67e <_Z11philosopheriii+0x35c>
     ce4:	ec e7       	ldi	r30, 0x7C	; 124
     ce6:	f0 e0       	ldi	r31, 0x00	; 0
     ce8:	33 cf       	rjmp	.-410    	; 0xb50 <_Z11philosopheriii+0x82e>
     cea:	ec e7       	ldi	r30, 0x7C	; 124
     cec:	f0 e0       	ldi	r31, 0x00	; 0
     cee:	17 cf       	rjmp	.-466    	; 0xb1e <_Z11philosopheriii+0x7fc>
     cf0:	ec e7       	ldi	r30, 0x7C	; 124
     cf2:	f0 e0       	ldi	r31, 0x00	; 0
     cf4:	45 cf       	rjmp	.-374    	; 0xb80 <_Z11philosopheriii+0x85e>
     cf6:	80 e3       	ldi	r24, 0x30	; 48
     cf8:	8f 5f       	subi	r24, 0xFF	; 255
     cfa:	9a 50       	subi	r25, 0x0A	; 10
     cfc:	9a 30       	cpi	r25, 0x0A	; 10
     cfe:	e0 f7       	brcc	.-8      	; 0xcf8 <_Z11philosopheriii+0x9d6>
     d00:	48 2f       	mov	r20, r24
     d02:	f6 ce       	rjmp	.-532    	; 0xaf0 <_Z11philosopheriii+0x7ce>
     d04:	ec e7       	ldi	r30, 0x7C	; 124
     d06:	f0 e0       	ldi	r31, 0x00	; 0
     d08:	ee ce       	rjmp	.-548    	; 0xae6 <_Z11philosopheriii+0x7c4>
     d0a:	80 e3       	ldi	r24, 0x30	; 48
     d0c:	8f 5f       	subi	r24, 0xFF	; 255
     d0e:	94 56       	subi	r25, 0x64	; 100
     d10:	94 36       	cpi	r25, 0x64	; 100
     d12:	e0 f7       	brcc	.-8      	; 0xd0c <_Z11philosopheriii+0x9ea>
     d14:	48 2f       	mov	r20, r24
     d16:	d0 ce       	rjmp	.-608    	; 0xab8 <_Z11philosopheriii+0x796>
     d18:	ec e7       	ldi	r30, 0x7C	; 124
     d1a:	f0 e0       	ldi	r31, 0x00	; 0
     d1c:	c5 ce       	rjmp	.-630    	; 0xaa8 <_Z11philosopheriii+0x786>
     d1e:	ec e7       	ldi	r30, 0x7C	; 124
     d20:	f0 e0       	ldi	r31, 0x00	; 0
     d22:	aa ce       	rjmp	.-684    	; 0xa78 <_Z11philosopheriii+0x756>
     d24:	ec e7       	ldi	r30, 0x7C	; 124
     d26:	f0 e0       	ldi	r31, 0x00	; 0
     d28:	8e ce       	rjmp	.-740    	; 0xa46 <_Z11philosopheriii+0x724>
     d2a:	80 e3       	ldi	r24, 0x30	; 48
     d2c:	8f 5f       	subi	r24, 0xFF	; 255
     d2e:	9a 50       	subi	r25, 0x0A	; 10
     d30:	9a 30       	cpi	r25, 0x0A	; 10
     d32:	e0 f7       	brcc	.-8      	; 0xd2c <_Z11philosopheriii+0xa0a>
     d34:	48 2f       	mov	r20, r24
     d36:	70 ce       	rjmp	.-800    	; 0xa18 <_Z11philosopheriii+0x6f6>
     d38:	ec e7       	ldi	r30, 0x7C	; 124
     d3a:	f0 e0       	ldi	r31, 0x00	; 0
     d3c:	68 ce       	rjmp	.-816    	; 0xa0e <_Z11philosopheriii+0x6ec>
     d3e:	80 e3       	ldi	r24, 0x30	; 48
     d40:	8f 5f       	subi	r24, 0xFF	; 255
     d42:	94 56       	subi	r25, 0x64	; 100
     d44:	94 36       	cpi	r25, 0x64	; 100
     d46:	e0 f7       	brcc	.-8      	; 0xd40 <_Z11philosopheriii+0xa1e>
     d48:	48 2f       	mov	r20, r24
     d4a:	4a ce       	rjmp	.-876    	; 0x9e0 <_Z11philosopheriii+0x6be>
     d4c:	ec e7       	ldi	r30, 0x7C	; 124
     d4e:	f0 e0       	ldi	r31, 0x00	; 0
     d50:	3f ce       	rjmp	.-898    	; 0x9d0 <_Z11philosopheriii+0x6ae>
     d52:	ec e7       	ldi	r30, 0x7C	; 124
     d54:	f0 e0       	ldi	r31, 0x00	; 0
     d56:	23 ce       	rjmp	.-954    	; 0x99e <_Z11philosopheriii+0x67c>
     d58:	fc 01       	movw	r30, r24
     d5a:	31 96       	adiw	r30, 0x01	; 1
     d5c:	02 cb       	rjmp	.-2556   	; 0x362 <_Z11philosopheriii+0x40>
     d5e:	8e e8       	ldi	r24, 0x8E	; 142
     d60:	93 e0       	ldi	r25, 0x03	; 3
     d62:	0e 94 e1 22 	call	0x45c2	; 0x45c2 <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
     d66:	05 c0       	rjmp	.+10     	; 0xd72 <_Z11philosopheriii+0xa50>
     d68:	8e e8       	ldi	r24, 0x8E	; 142
     d6a:	93 e0       	ldi	r25, 0x03	; 3
     d6c:	0e 94 14 2a 	call	0x5428	; 0x5428 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
     d70:	f7 cd       	rjmp	.-1042   	; 0x960 <_Z11philosopheriii+0x63e>
     d72:	8a e0       	ldi	r24, 0x0A	; 10
     d74:	90 e0       	ldi	r25, 0x00	; 0
     d76:	28 96       	adiw	r28, 0x08	; 8
     d78:	0f b6       	in	r0, 0x3f	; 63
     d7a:	f8 94       	cli
     d7c:	de bf       	out	0x3e, r29	; 62
     d7e:	0f be       	out	0x3f, r0	; 63
     d80:	cd bf       	out	0x3d, r28	; 61
     d82:	df 91       	pop	r29
     d84:	cf 91       	pop	r28
     d86:	1f 91       	pop	r17
     d88:	0f 91       	pop	r16
     d8a:	ff 90       	pop	r15
     d8c:	ef 90       	pop	r14
     d8e:	df 90       	pop	r13
     d90:	cf 90       	pop	r12
     d92:	bf 90       	pop	r11
     d94:	af 90       	pop	r10
     d96:	9f 90       	pop	r9
     d98:	8f 90       	pop	r8
     d9a:	7f 90       	pop	r7
     d9c:	6f 90       	pop	r6
     d9e:	08 95       	ret

00000da0 <main>:
     da0:	f8 94       	cli
     da2:	20 91 94 03 	lds	r18, 0x0394
     da6:	30 91 95 03 	lds	r19, 0x0395
     daa:	80 91 94 03 	lds	r24, 0x0394
     dae:	90 91 95 03 	lds	r25, 0x0395
     db2:	01 97       	sbiw	r24, 0x01	; 1
     db4:	90 93 95 03 	sts	0x0395, r25
     db8:	80 93 94 03 	sts	0x0394, r24
     dbc:	78 94       	sei
     dbe:	12 16       	cp	r1, r18
     dc0:	13 06       	cpc	r1, r19
     dc2:	14 f0       	brlt	.+4      	; 0xdc8 <main+0x28>
     dc4:	0c 94 30 12 	jmp	0x2460	; 0x2460 <__stack+0x1361>
     dc8:	20 91 96 03 	lds	r18, 0x0396
     dcc:	30 91 97 03 	lds	r19, 0x0397
     dd0:	05 c0       	rjmp	.+10     	; 0xddc <main+0x3c>
     dd2:	eb e0       	ldi	r30, 0x0B	; 11
     dd4:	f0 e0       	ldi	r31, 0x00	; 0
     dd6:	80 a1       	ldd	r24, Z+32	; 0x20
     dd8:	85 fd       	sbrc	r24, 5
     dda:	08 c0       	rjmp	.+16     	; 0xdec <main+0x4c>
     ddc:	21 15       	cp	r18, r1
     dde:	31 05       	cpc	r19, r1
     de0:	c1 f3       	breq	.-16     	; 0xdd2 <main+0x32>
     de2:	eb e7       	ldi	r30, 0x7B	; 123
     de4:	f0 e0       	ldi	r31, 0x00	; 0
     de6:	80 a1       	ldd	r24, Z+32	; 0x20
     de8:	85 ff       	sbrs	r24, 5
     dea:	f8 cf       	rjmp	.-16     	; 0xddc <main+0x3c>
     dec:	23 2b       	or	r18, r19
     dee:	11 f0       	breq	.+4      	; 0xdf4 <main+0x54>
     df0:	0c 94 2c 12 	jmp	0x2458	; 0x2458 <__stack+0x1359>
     df4:	ec e0       	ldi	r30, 0x0C	; 12
     df6:	f0 e0       	ldi	r31, 0x00	; 0
     df8:	8b e1       	ldi	r24, 0x1B	; 27
     dfa:	80 a3       	std	Z+32, r24	; 0x20
     dfc:	20 91 96 03 	lds	r18, 0x0396
     e00:	30 91 97 03 	lds	r19, 0x0397
     e04:	05 c0       	rjmp	.+10     	; 0xe10 <main+0x70>
     e06:	eb e0       	ldi	r30, 0x0B	; 11
     e08:	f0 e0       	ldi	r31, 0x00	; 0
     e0a:	80 a1       	ldd	r24, Z+32	; 0x20
     e0c:	85 fd       	sbrc	r24, 5
     e0e:	08 c0       	rjmp	.+16     	; 0xe20 <main+0x80>
     e10:	21 15       	cp	r18, r1
     e12:	31 05       	cpc	r19, r1
     e14:	c1 f3       	breq	.-16     	; 0xe06 <main+0x66>
     e16:	eb e7       	ldi	r30, 0x7B	; 123
     e18:	f0 e0       	ldi	r31, 0x00	; 0
     e1a:	80 a1       	ldd	r24, Z+32	; 0x20
     e1c:	85 ff       	sbrs	r24, 5
     e1e:	f8 cf       	rjmp	.-16     	; 0xe10 <main+0x70>
     e20:	23 2b       	or	r18, r19
     e22:	11 f0       	breq	.+4      	; 0xe28 <main+0x88>
     e24:	0c 94 28 12 	jmp	0x2450	; 0x2450 <__stack+0x1351>
     e28:	ec e0       	ldi	r30, 0x0C	; 12
     e2a:	f0 e0       	ldi	r31, 0x00	; 0
     e2c:	8b e5       	ldi	r24, 0x5B	; 91
     e2e:	80 a3       	std	Z+32, r24	; 0x20
     e30:	20 91 96 03 	lds	r18, 0x0396
     e34:	30 91 97 03 	lds	r19, 0x0397
     e38:	05 c0       	rjmp	.+10     	; 0xe44 <main+0xa4>
     e3a:	eb e0       	ldi	r30, 0x0B	; 11
     e3c:	f0 e0       	ldi	r31, 0x00	; 0
     e3e:	80 a1       	ldd	r24, Z+32	; 0x20
     e40:	85 fd       	sbrc	r24, 5
     e42:	08 c0       	rjmp	.+16     	; 0xe54 <main+0xb4>
     e44:	21 15       	cp	r18, r1
     e46:	31 05       	cpc	r19, r1
     e48:	c1 f3       	breq	.-16     	; 0xe3a <main+0x9a>
     e4a:	eb e7       	ldi	r30, 0x7B	; 123
     e4c:	f0 e0       	ldi	r31, 0x00	; 0
     e4e:	80 a1       	ldd	r24, Z+32	; 0x20
     e50:	85 ff       	sbrs	r24, 5
     e52:	f8 cf       	rjmp	.-16     	; 0xe44 <main+0xa4>
     e54:	23 2b       	or	r18, r19
     e56:	11 f0       	breq	.+4      	; 0xe5c <main+0xbc>
     e58:	0c 94 24 12 	jmp	0x2448	; 0x2448 <__stack+0x1349>
     e5c:	ec e0       	ldi	r30, 0x0C	; 12
     e5e:	f0 e0       	ldi	r31, 0x00	; 0
     e60:	82 e3       	ldi	r24, 0x32	; 50
     e62:	80 a3       	std	Z+32, r24	; 0x20
     e64:	20 91 96 03 	lds	r18, 0x0396
     e68:	30 91 97 03 	lds	r19, 0x0397
     e6c:	05 c0       	rjmp	.+10     	; 0xe78 <main+0xd8>
     e6e:	eb e0       	ldi	r30, 0x0B	; 11
     e70:	f0 e0       	ldi	r31, 0x00	; 0
     e72:	80 a1       	ldd	r24, Z+32	; 0x20
     e74:	85 fd       	sbrc	r24, 5
     e76:	08 c0       	rjmp	.+16     	; 0xe88 <main+0xe8>
     e78:	21 15       	cp	r18, r1
     e7a:	31 05       	cpc	r19, r1
     e7c:	c1 f3       	breq	.-16     	; 0xe6e <main+0xce>
     e7e:	eb e7       	ldi	r30, 0x7B	; 123
     e80:	f0 e0       	ldi	r31, 0x00	; 0
     e82:	80 a1       	ldd	r24, Z+32	; 0x20
     e84:	85 ff       	sbrs	r24, 5
     e86:	f8 cf       	rjmp	.-16     	; 0xe78 <main+0xd8>
     e88:	23 2b       	or	r18, r19
     e8a:	11 f0       	breq	.+4      	; 0xe90 <main+0xf0>
     e8c:	0c 94 20 12 	jmp	0x2440	; 0x2440 <__stack+0x1341>
     e90:	ec e0       	ldi	r30, 0x0C	; 12
     e92:	f0 e0       	ldi	r31, 0x00	; 0
     e94:	8a e4       	ldi	r24, 0x4A	; 74
     e96:	80 a3       	std	Z+32, r24	; 0x20
     e98:	6b e6       	ldi	r22, 0x6B	; 107
     e9a:	71 e0       	ldi	r23, 0x01	; 1
     e9c:	88 e7       	ldi	r24, 0x78	; 120
     e9e:	93 e0       	ldi	r25, 0x03	; 3
     ea0:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
     ea4:	0a e7       	ldi	r16, 0x7A	; 122
     ea6:	13 e0       	ldi	r17, 0x03	; 3
     ea8:	fa e0       	ldi	r31, 0x0A	; 10
     eaa:	ef 2e       	mov	r14, r31
     eac:	f1 2c       	mov	r15, r1
     eae:	e0 0e       	add	r14, r16
     eb0:	f1 1e       	adc	r15, r17
     eb2:	68 e0       	ldi	r22, 0x08	; 8
     eb4:	70 e0       	ldi	r23, 0x00	; 0
     eb6:	8c e6       	ldi	r24, 0x6C	; 108
     eb8:	93 e0       	ldi	r25, 0x03	; 3
     eba:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
     ebe:	fc 01       	movw	r30, r24
     ec0:	11 82       	std	Z+1, r1	; 0x01
     ec2:	10 82       	st	Z, r1
     ec4:	13 82       	std	Z+3, r1	; 0x03
     ec6:	12 82       	std	Z+2, r1	; 0x02
     ec8:	15 82       	std	Z+5, r1	; 0x05
     eca:	14 82       	std	Z+4, r1	; 0x04
     ecc:	81 e0       	ldi	r24, 0x01	; 1
     ece:	90 e0       	ldi	r25, 0x00	; 0
     ed0:	97 83       	std	Z+7, r25	; 0x07
     ed2:	86 83       	std	Z+6, r24	; 0x06
     ed4:	d8 01       	movw	r26, r16
     ed6:	ed 93       	st	X+, r30
     ed8:	fd 93       	st	X+, r31
     eda:	8d 01       	movw	r16, r26
     edc:	ea 16       	cp	r14, r26
     ede:	fb 06       	cpc	r15, r27
     ee0:	41 f7       	brne	.-48     	; 0xeb2 <main+0x112>
     ee2:	ed ef       	ldi	r30, 0xFD	; 253
     ee4:	6e 2e       	mov	r6, r30
     ee6:	ef e7       	ldi	r30, 0x7F	; 127
     ee8:	7e 2e       	mov	r7, r30
     eea:	7c 8a       	std	Y+20, r7	; 0x14
     eec:	6b 8a       	std	Y+19, r6	; 0x13
     eee:	62 e1       	ldi	r22, 0x12	; 18
     ef0:	70 e0       	ldi	r23, 0x00	; 0
     ef2:	8c e6       	ldi	r24, 0x6C	; 108
     ef4:	93 e0       	ldi	r25, 0x03	; 3
     ef6:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
     efa:	8c 01       	movw	r16, r24
     efc:	81 e0       	ldi	r24, 0x01	; 1
     efe:	90 e0       	ldi	r25, 0x00	; 0
     f00:	f8 01       	movw	r30, r16
     f02:	95 83       	std	Z+5, r25	; 0x05
     f04:	84 83       	std	Z+4, r24	; 0x04
     f06:	17 82       	std	Z+7, r1	; 0x07
     f08:	16 82       	std	Z+6, r1	; 0x06
     f0a:	11 86       	std	Z+9, r1	; 0x09
     f0c:	10 86       	std	Z+8, r1	; 0x08
     f0e:	d8 01       	movw	r26, r16
     f10:	1a 96       	adiw	r26, 0x0a	; 10
     f12:	13 87       	std	Z+11, r17	; 0x0b
     f14:	02 87       	std	Z+10, r16	; 0x0a
     f16:	8b 89       	ldd	r24, Y+19	; 0x13
     f18:	9c 89       	ldd	r25, Y+20	; 0x14
     f1a:	9a 8b       	std	Y+18, r25	; 0x12
     f1c:	89 8b       	std	Y+17, r24	; 0x11
     f1e:	89 89       	ldd	r24, Y+17	; 0x11
     f20:	9a 89       	ldd	r25, Y+18	; 0x12
     f22:	fd 01       	movw	r30, r26
     f24:	93 83       	std	Z+3, r25	; 0x03
     f26:	82 83       	std	Z+2, r24	; 0x02
     f28:	15 82       	std	Z+5, r1	; 0x05
     f2a:	14 82       	std	Z+4, r1	; 0x04
     f2c:	17 82       	std	Z+7, r1	; 0x07
     f2e:	16 82       	std	Z+6, r1	; 0x06
     f30:	f8 94       	cli
     f32:	60 e0       	ldi	r22, 0x00	; 0
     f34:	71 e0       	ldi	r23, 0x01	; 1
     f36:	8f e9       	ldi	r24, 0x9F	; 159
     f38:	93 e0       	ldi	r25, 0x03	; 3
     f3a:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
     f3e:	fc 01       	movw	r30, r24
     f40:	d8 01       	movw	r26, r16
     f42:	8d 93       	st	X+, r24
     f44:	9c 93       	st	X, r25
     f46:	e2 50       	subi	r30, 0x02	; 2
     f48:	ff 4f       	sbci	r31, 0xFF	; 255
     f4a:	87 e6       	ldi	r24, 0x67	; 103
     f4c:	97 e2       	ldi	r25, 0x27	; 39
     f4e:	d8 2e       	mov	r13, r24
     f50:	cc 24       	eor	r12, r12
     f52:	89 2f       	mov	r24, r25
     f54:	99 27       	eor	r25, r25
     f56:	c8 2a       	or	r12, r24
     f58:	d9 2a       	or	r13, r25
     f5a:	d1 82       	std	Z+1, r13	; 0x01
     f5c:	c0 82       	st	Z, r12
     f5e:	51 e9       	ldi	r21, 0x91	; 145
     f60:	a5 2e       	mov	r10, r21
     f62:	51 e0       	ldi	r21, 0x01	; 1
     f64:	b5 2e       	mov	r11, r21
     f66:	df 01       	movw	r26, r30
     f68:	92 97       	sbiw	r26, 0x22	; 34
     f6a:	40 e8       	ldi	r20, 0x80	; 128
     f6c:	54 2e       	mov	r5, r20
     f6e:	5c 92       	st	X, r5
     f70:	fa 2c       	mov	r15, r10
     f72:	ee 24       	eor	r14, r14
     f74:	8b 2d       	mov	r24, r11
     f76:	99 27       	eor	r25, r25
     f78:	e8 2a       	or	r14, r24
     f7a:	f9 2a       	or	r15, r25
     f7c:	fd 01       	movw	r30, r26
     f7e:	f1 a2       	std	Z+33, r15	; 0x21
     f80:	e0 a2       	std	Z+32, r14	; 0x20
     f82:	11 8e       	std	Z+25, r1	; 0x19
     f84:	10 8e       	std	Z+24, r1	; 0x18
     f86:	85 e0       	ldi	r24, 0x05	; 5
     f88:	90 e0       	ldi	r25, 0x00	; 0
     f8a:	97 8b       	std	Z+23, r25	; 0x17
     f8c:	86 8b       	std	Z+22, r24	; 0x16
     f8e:	80 e2       	ldi	r24, 0x20	; 32
     f90:	90 e0       	ldi	r25, 0x00	; 0
     f92:	95 8b       	std	Z+21, r25	; 0x15
     f94:	84 8b       	std	Z+20, r24	; 0x14
     f96:	f8 01       	movw	r30, r16
     f98:	b3 83       	std	Z+3, r27	; 0x03
     f9a:	a2 83       	std	Z+2, r26	; 0x02
     f9c:	be 8e       	std	Y+30, r11	; 0x1e
     f9e:	ad 8e       	std	Y+29, r10	; 0x1d
     fa0:	40 e0       	ldi	r20, 0x00	; 0
     fa2:	51 e0       	ldi	r21, 0x01	; 1
     fa4:	be 01       	movw	r22, r28
     fa6:	63 5e       	subi	r22, 0xE3	; 227
     fa8:	7f 4f       	sbci	r23, 0xFF	; 255
     faa:	c8 01       	movw	r24, r16
     fac:	0e 94 f3 21 	call	0x43e6	; 0x43e6 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj>
     fb0:	10 93 85 03 	sts	0x0385, r17
     fb4:	00 93 84 03 	sts	0x0384, r16
     fb8:	78 8a       	std	Y+16, r7	; 0x10
     fba:	6f 86       	std	Y+15, r6	; 0x0f
     fbc:	62 e1       	ldi	r22, 0x12	; 18
     fbe:	70 e0       	ldi	r23, 0x00	; 0
     fc0:	8c e6       	ldi	r24, 0x6C	; 108
     fc2:	93 e0       	ldi	r25, 0x03	; 3
     fc4:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
     fc8:	8c 01       	movw	r16, r24
     fca:	81 e0       	ldi	r24, 0x01	; 1
     fcc:	90 e0       	ldi	r25, 0x00	; 0
     fce:	f8 01       	movw	r30, r16
     fd0:	95 83       	std	Z+5, r25	; 0x05
     fd2:	84 83       	std	Z+4, r24	; 0x04
     fd4:	17 82       	std	Z+7, r1	; 0x07
     fd6:	16 82       	std	Z+6, r1	; 0x06
     fd8:	11 86       	std	Z+9, r1	; 0x09
     fda:	10 86       	std	Z+8, r1	; 0x08
     fdc:	d8 01       	movw	r26, r16
     fde:	1a 96       	adiw	r26, 0x0a	; 10
     fe0:	13 87       	std	Z+11, r17	; 0x0b
     fe2:	02 87       	std	Z+10, r16	; 0x0a
     fe4:	8f 85       	ldd	r24, Y+15	; 0x0f
     fe6:	98 89       	ldd	r25, Y+16	; 0x10
     fe8:	9e 87       	std	Y+14, r25	; 0x0e
     fea:	8d 87       	std	Y+13, r24	; 0x0d
     fec:	8d 85       	ldd	r24, Y+13	; 0x0d
     fee:	9e 85       	ldd	r25, Y+14	; 0x0e
     ff0:	fd 01       	movw	r30, r26
     ff2:	93 83       	std	Z+3, r25	; 0x03
     ff4:	82 83       	std	Z+2, r24	; 0x02
     ff6:	15 82       	std	Z+5, r1	; 0x05
     ff8:	14 82       	std	Z+4, r1	; 0x04
     ffa:	17 82       	std	Z+7, r1	; 0x07
     ffc:	16 82       	std	Z+6, r1	; 0x06
     ffe:	f8 94       	cli
    1000:	60 e0       	ldi	r22, 0x00	; 0
    1002:	71 e0       	ldi	r23, 0x01	; 1
    1004:	8f e9       	ldi	r24, 0x9F	; 159
    1006:	93 e0       	ldi	r25, 0x03	; 3
    1008:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
    100c:	fc 01       	movw	r30, r24
    100e:	d8 01       	movw	r26, r16
    1010:	8d 93       	st	X+, r24
    1012:	9c 93       	st	X, r25
    1014:	e2 50       	subi	r30, 0x02	; 2
    1016:	ff 4f       	sbci	r31, 0xFF	; 255
    1018:	d1 82       	std	Z+1, r13	; 0x01
    101a:	c0 82       	st	Z, r12
    101c:	df 01       	movw	r26, r30
    101e:	92 97       	sbiw	r26, 0x22	; 34
    1020:	5c 92       	st	X, r5
    1022:	fd 01       	movw	r30, r26
    1024:	f1 a2       	std	Z+33, r15	; 0x21
    1026:	e0 a2       	std	Z+32, r14	; 0x20
    1028:	81 e0       	ldi	r24, 0x01	; 1
    102a:	90 e0       	ldi	r25, 0x00	; 0
    102c:	91 8f       	std	Z+25, r25	; 0x19
    102e:	80 8f       	std	Z+24, r24	; 0x18
    1030:	9a e0       	ldi	r25, 0x0A	; 10
    1032:	29 2e       	mov	r2, r25
    1034:	31 2c       	mov	r3, r1
    1036:	37 8a       	std	Z+23, r3	; 0x17
    1038:	26 8a       	std	Z+22, r2	; 0x16
    103a:	8c e2       	ldi	r24, 0x2C	; 44
    103c:	90 e0       	ldi	r25, 0x00	; 0
    103e:	95 8b       	std	Z+21, r25	; 0x15
    1040:	84 8b       	std	Z+20, r24	; 0x14
    1042:	f8 01       	movw	r30, r16
    1044:	b3 83       	std	Z+3, r27	; 0x03
    1046:	a2 83       	std	Z+2, r26	; 0x02
    1048:	bc 8e       	std	Y+28, r11	; 0x1c
    104a:	ab 8e       	std	Y+27, r10	; 0x1b
    104c:	40 e0       	ldi	r20, 0x00	; 0
    104e:	51 e0       	ldi	r21, 0x01	; 1
    1050:	be 01       	movw	r22, r28
    1052:	65 5e       	subi	r22, 0xE5	; 229
    1054:	7f 4f       	sbci	r23, 0xFF	; 255
    1056:	c8 01       	movw	r24, r16
    1058:	0e 94 f3 21 	call	0x43e6	; 0x43e6 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj>
    105c:	10 93 87 03 	sts	0x0387, r17
    1060:	00 93 86 03 	sts	0x0386, r16
    1064:	7c 86       	std	Y+12, r7	; 0x0c
    1066:	6b 86       	std	Y+11, r6	; 0x0b
    1068:	62 e1       	ldi	r22, 0x12	; 18
    106a:	70 e0       	ldi	r23, 0x00	; 0
    106c:	8c e6       	ldi	r24, 0x6C	; 108
    106e:	93 e0       	ldi	r25, 0x03	; 3
    1070:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
    1074:	8c 01       	movw	r16, r24
    1076:	81 e0       	ldi	r24, 0x01	; 1
    1078:	90 e0       	ldi	r25, 0x00	; 0
    107a:	f8 01       	movw	r30, r16
    107c:	95 83       	std	Z+5, r25	; 0x05
    107e:	84 83       	std	Z+4, r24	; 0x04
    1080:	17 82       	std	Z+7, r1	; 0x07
    1082:	16 82       	std	Z+6, r1	; 0x06
    1084:	11 86       	std	Z+9, r1	; 0x09
    1086:	10 86       	std	Z+8, r1	; 0x08
    1088:	d8 01       	movw	r26, r16
    108a:	1a 96       	adiw	r26, 0x0a	; 10
    108c:	13 87       	std	Z+11, r17	; 0x0b
    108e:	02 87       	std	Z+10, r16	; 0x0a
    1090:	8b 85       	ldd	r24, Y+11	; 0x0b
    1092:	9c 85       	ldd	r25, Y+12	; 0x0c
    1094:	9a 87       	std	Y+10, r25	; 0x0a
    1096:	89 87       	std	Y+9, r24	; 0x09
    1098:	89 85       	ldd	r24, Y+9	; 0x09
    109a:	9a 85       	ldd	r25, Y+10	; 0x0a
    109c:	fd 01       	movw	r30, r26
    109e:	93 83       	std	Z+3, r25	; 0x03
    10a0:	82 83       	std	Z+2, r24	; 0x02
    10a2:	15 82       	std	Z+5, r1	; 0x05
    10a4:	14 82       	std	Z+4, r1	; 0x04
    10a6:	17 82       	std	Z+7, r1	; 0x07
    10a8:	16 82       	std	Z+6, r1	; 0x06
    10aa:	f8 94       	cli
    10ac:	60 e0       	ldi	r22, 0x00	; 0
    10ae:	71 e0       	ldi	r23, 0x01	; 1
    10b0:	8f e9       	ldi	r24, 0x9F	; 159
    10b2:	93 e0       	ldi	r25, 0x03	; 3
    10b4:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
    10b8:	fc 01       	movw	r30, r24
    10ba:	d8 01       	movw	r26, r16
    10bc:	8d 93       	st	X+, r24
    10be:	9c 93       	st	X, r25
    10c0:	e2 50       	subi	r30, 0x02	; 2
    10c2:	ff 4f       	sbci	r31, 0xFF	; 255
    10c4:	d1 82       	std	Z+1, r13	; 0x01
    10c6:	c0 82       	st	Z, r12
    10c8:	df 01       	movw	r26, r30
    10ca:	92 97       	sbiw	r26, 0x22	; 34
    10cc:	5c 92       	st	X, r5
    10ce:	fd 01       	movw	r30, r26
    10d0:	f1 a2       	std	Z+33, r15	; 0x21
    10d2:	e0 a2       	std	Z+32, r14	; 0x20
    10d4:	82 e0       	ldi	r24, 0x02	; 2
    10d6:	90 e0       	ldi	r25, 0x00	; 0
    10d8:	91 8f       	std	Z+25, r25	; 0x19
    10da:	80 8f       	std	Z+24, r24	; 0x18
    10dc:	80 e1       	ldi	r24, 0x10	; 16
    10de:	88 2e       	mov	r8, r24
    10e0:	91 2c       	mov	r9, r1
    10e2:	97 8a       	std	Z+23, r9	; 0x17
    10e4:	86 8a       	std	Z+22, r8	; 0x16
    10e6:	87 e2       	ldi	r24, 0x27	; 39
    10e8:	90 e0       	ldi	r25, 0x00	; 0
    10ea:	95 8b       	std	Z+21, r25	; 0x15
    10ec:	84 8b       	std	Z+20, r24	; 0x14
    10ee:	f8 01       	movw	r30, r16
    10f0:	b3 83       	std	Z+3, r27	; 0x03
    10f2:	a2 83       	std	Z+2, r26	; 0x02
    10f4:	ba 8e       	std	Y+26, r11	; 0x1a
    10f6:	a9 8e       	std	Y+25, r10	; 0x19
    10f8:	40 e0       	ldi	r20, 0x00	; 0
    10fa:	51 e0       	ldi	r21, 0x01	; 1
    10fc:	be 01       	movw	r22, r28
    10fe:	67 5e       	subi	r22, 0xE7	; 231
    1100:	7f 4f       	sbci	r23, 0xFF	; 255
    1102:	c8 01       	movw	r24, r16
    1104:	0e 94 f3 21 	call	0x43e6	; 0x43e6 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj>
    1108:	10 93 89 03 	sts	0x0389, r17
    110c:	00 93 88 03 	sts	0x0388, r16
    1110:	78 86       	std	Y+8, r7	; 0x08
    1112:	6f 82       	std	Y+7, r6	; 0x07
    1114:	62 e1       	ldi	r22, 0x12	; 18
    1116:	70 e0       	ldi	r23, 0x00	; 0
    1118:	8c e6       	ldi	r24, 0x6C	; 108
    111a:	93 e0       	ldi	r25, 0x03	; 3
    111c:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
    1120:	8c 01       	movw	r16, r24
    1122:	81 e0       	ldi	r24, 0x01	; 1
    1124:	90 e0       	ldi	r25, 0x00	; 0
    1126:	f8 01       	movw	r30, r16
    1128:	95 83       	std	Z+5, r25	; 0x05
    112a:	84 83       	std	Z+4, r24	; 0x04
    112c:	17 82       	std	Z+7, r1	; 0x07
    112e:	16 82       	std	Z+6, r1	; 0x06
    1130:	11 86       	std	Z+9, r1	; 0x09
    1132:	10 86       	std	Z+8, r1	; 0x08
    1134:	d8 01       	movw	r26, r16
    1136:	1a 96       	adiw	r26, 0x0a	; 10
    1138:	13 87       	std	Z+11, r17	; 0x0b
    113a:	02 87       	std	Z+10, r16	; 0x0a
    113c:	8f 81       	ldd	r24, Y+7	; 0x07
    113e:	98 85       	ldd	r25, Y+8	; 0x08
    1140:	9e 83       	std	Y+6, r25	; 0x06
    1142:	8d 83       	std	Y+5, r24	; 0x05
    1144:	8d 81       	ldd	r24, Y+5	; 0x05
    1146:	9e 81       	ldd	r25, Y+6	; 0x06
    1148:	fd 01       	movw	r30, r26
    114a:	93 83       	std	Z+3, r25	; 0x03
    114c:	82 83       	std	Z+2, r24	; 0x02
    114e:	15 82       	std	Z+5, r1	; 0x05
    1150:	14 82       	std	Z+4, r1	; 0x04
    1152:	17 82       	std	Z+7, r1	; 0x07
    1154:	16 82       	std	Z+6, r1	; 0x06
    1156:	f8 94       	cli
    1158:	60 e0       	ldi	r22, 0x00	; 0
    115a:	71 e0       	ldi	r23, 0x01	; 1
    115c:	8f e9       	ldi	r24, 0x9F	; 159
    115e:	93 e0       	ldi	r25, 0x03	; 3
    1160:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
    1164:	fc 01       	movw	r30, r24
    1166:	d8 01       	movw	r26, r16
    1168:	8d 93       	st	X+, r24
    116a:	9c 93       	st	X, r25
    116c:	e2 50       	subi	r30, 0x02	; 2
    116e:	ff 4f       	sbci	r31, 0xFF	; 255
    1170:	d1 82       	std	Z+1, r13	; 0x01
    1172:	c0 82       	st	Z, r12
    1174:	df 01       	movw	r26, r30
    1176:	92 97       	sbiw	r26, 0x22	; 34
    1178:	5c 92       	st	X, r5
    117a:	fd 01       	movw	r30, r26
    117c:	f1 a2       	std	Z+33, r15	; 0x21
    117e:	e0 a2       	std	Z+32, r14	; 0x20
    1180:	83 e0       	ldi	r24, 0x03	; 3
    1182:	90 e0       	ldi	r25, 0x00	; 0
    1184:	91 8f       	std	Z+25, r25	; 0x19
    1186:	80 8f       	std	Z+24, r24	; 0x18
    1188:	97 8a       	std	Z+23, r9	; 0x17
    118a:	86 8a       	std	Z+22, r8	; 0x16
    118c:	88 e1       	ldi	r24, 0x18	; 24
    118e:	90 e0       	ldi	r25, 0x00	; 0
    1190:	95 8b       	std	Z+21, r25	; 0x15
    1192:	84 8b       	std	Z+20, r24	; 0x14
    1194:	f8 01       	movw	r30, r16
    1196:	b3 83       	std	Z+3, r27	; 0x03
    1198:	a2 83       	std	Z+2, r26	; 0x02
    119a:	b8 8e       	std	Y+24, r11	; 0x18
    119c:	af 8a       	std	Y+23, r10	; 0x17
    119e:	40 e0       	ldi	r20, 0x00	; 0
    11a0:	51 e0       	ldi	r21, 0x01	; 1
    11a2:	be 01       	movw	r22, r28
    11a4:	69 5e       	subi	r22, 0xE9	; 233
    11a6:	7f 4f       	sbci	r23, 0xFF	; 255
    11a8:	c8 01       	movw	r24, r16
    11aa:	0e 94 f3 21 	call	0x43e6	; 0x43e6 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj>
    11ae:	10 93 8b 03 	sts	0x038B, r17
    11b2:	00 93 8a 03 	sts	0x038A, r16
    11b6:	7c 82       	std	Y+4, r7	; 0x04
    11b8:	6b 82       	std	Y+3, r6	; 0x03
    11ba:	62 e1       	ldi	r22, 0x12	; 18
    11bc:	70 e0       	ldi	r23, 0x00	; 0
    11be:	8c e6       	ldi	r24, 0x6C	; 108
    11c0:	93 e0       	ldi	r25, 0x03	; 3
    11c2:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
    11c6:	8c 01       	movw	r16, r24
    11c8:	81 e0       	ldi	r24, 0x01	; 1
    11ca:	90 e0       	ldi	r25, 0x00	; 0
    11cc:	f8 01       	movw	r30, r16
    11ce:	95 83       	std	Z+5, r25	; 0x05
    11d0:	84 83       	std	Z+4, r24	; 0x04
    11d2:	17 82       	std	Z+7, r1	; 0x07
    11d4:	16 82       	std	Z+6, r1	; 0x06
    11d6:	11 86       	std	Z+9, r1	; 0x09
    11d8:	10 86       	std	Z+8, r1	; 0x08
    11da:	d8 01       	movw	r26, r16
    11dc:	1a 96       	adiw	r26, 0x0a	; 10
    11de:	13 87       	std	Z+11, r17	; 0x0b
    11e0:	02 87       	std	Z+10, r16	; 0x0a
    11e2:	8b 81       	ldd	r24, Y+3	; 0x03
    11e4:	9c 81       	ldd	r25, Y+4	; 0x04
    11e6:	9a 83       	std	Y+2, r25	; 0x02
    11e8:	89 83       	std	Y+1, r24	; 0x01
    11ea:	89 81       	ldd	r24, Y+1	; 0x01
    11ec:	9a 81       	ldd	r25, Y+2	; 0x02
    11ee:	fd 01       	movw	r30, r26
    11f0:	93 83       	std	Z+3, r25	; 0x03
    11f2:	82 83       	std	Z+2, r24	; 0x02
    11f4:	15 82       	std	Z+5, r1	; 0x05
    11f6:	14 82       	std	Z+4, r1	; 0x04
    11f8:	17 82       	std	Z+7, r1	; 0x07
    11fa:	16 82       	std	Z+6, r1	; 0x06
    11fc:	f8 94       	cli
    11fe:	60 e0       	ldi	r22, 0x00	; 0
    1200:	71 e0       	ldi	r23, 0x01	; 1
    1202:	8f e9       	ldi	r24, 0x9F	; 159
    1204:	93 e0       	ldi	r25, 0x03	; 3
    1206:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
    120a:	fc 01       	movw	r30, r24
    120c:	d8 01       	movw	r26, r16
    120e:	8d 93       	st	X+, r24
    1210:	9c 93       	st	X, r25
    1212:	e2 50       	subi	r30, 0x02	; 2
    1214:	ff 4f       	sbci	r31, 0xFF	; 255
    1216:	d1 82       	std	Z+1, r13	; 0x01
    1218:	c0 82       	st	Z, r12
    121a:	df 01       	movw	r26, r30
    121c:	92 97       	sbiw	r26, 0x22	; 34
    121e:	5c 92       	st	X, r5
    1220:	fd 01       	movw	r30, r26
    1222:	f1 a2       	std	Z+33, r15	; 0x21
    1224:	e0 a2       	std	Z+32, r14	; 0x20
    1226:	84 e0       	ldi	r24, 0x04	; 4
    1228:	90 e0       	ldi	r25, 0x00	; 0
    122a:	91 8f       	std	Z+25, r25	; 0x19
    122c:	80 8f       	std	Z+24, r24	; 0x18
    122e:	37 8a       	std	Z+23, r3	; 0x17
    1230:	26 8a       	std	Z+22, r2	; 0x16
    1232:	84 e1       	ldi	r24, 0x14	; 20
    1234:	90 e0       	ldi	r25, 0x00	; 0
    1236:	95 8b       	std	Z+21, r25	; 0x15
    1238:	84 8b       	std	Z+20, r24	; 0x14
    123a:	f8 01       	movw	r30, r16
    123c:	b3 83       	std	Z+3, r27	; 0x03
    123e:	a2 83       	std	Z+2, r26	; 0x02
    1240:	be 8a       	std	Y+22, r11	; 0x16
    1242:	ad 8a       	std	Y+21, r10	; 0x15
    1244:	40 e0       	ldi	r20, 0x00	; 0
    1246:	51 e0       	ldi	r21, 0x01	; 1
    1248:	be 01       	movw	r22, r28
    124a:	6b 5e       	subi	r22, 0xEB	; 235
    124c:	7f 4f       	sbci	r23, 0xFF	; 255
    124e:	c8 01       	movw	r24, r16
    1250:	0e 94 f3 21 	call	0x43e6	; 0x43e6 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj>
    1254:	10 93 8d 03 	sts	0x038D, r17
    1258:	00 93 8c 03 	sts	0x038C, r16
    125c:	66 e8       	ldi	r22, 0x86	; 134
    125e:	71 e0       	ldi	r23, 0x01	; 1
    1260:	88 e7       	ldi	r24, 0x78	; 120
    1262:	93 e0       	ldi	r25, 0x03	; 3
    1264:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    1268:	6a ea       	ldi	r22, 0xAA	; 170
    126a:	71 e0       	ldi	r23, 0x01	; 1
    126c:	88 e7       	ldi	r24, 0x78	; 120
    126e:	93 e0       	ldi	r25, 0x03	; 3
    1270:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    1274:	87 e0       	ldi	r24, 0x07	; 7
    1276:	90 e0       	ldi	r25, 0x00	; 0
    1278:	90 93 9b 03 	sts	0x039B, r25
    127c:	80 93 9a 03 	sts	0x039A, r24
    1280:	8c e2       	ldi	r24, 0x2C	; 44
    1282:	90 e0       	ldi	r25, 0x00	; 0
    1284:	90 93 9d 03 	sts	0x039D, r25
    1288:	80 93 9c 03 	sts	0x039C, r24
    128c:	20 91 96 03 	lds	r18, 0x0396
    1290:	30 91 97 03 	lds	r19, 0x0397
    1294:	05 c0       	rjmp	.+10     	; 0x12a0 <__stack+0x1a1>
    1296:	eb e0       	ldi	r30, 0x0B	; 11
    1298:	f0 e0       	ldi	r31, 0x00	; 0
    129a:	80 a1       	ldd	r24, Z+32	; 0x20
    129c:	85 fd       	sbrc	r24, 5
    129e:	08 c0       	rjmp	.+16     	; 0x12b0 <__stack+0x1b1>
    12a0:	21 15       	cp	r18, r1
    12a2:	31 05       	cpc	r19, r1
    12a4:	c1 f3       	breq	.-16     	; 0x1296 <__stack+0x197>
    12a6:	eb e7       	ldi	r30, 0x7B	; 123
    12a8:	f0 e0       	ldi	r31, 0x00	; 0
    12aa:	80 a1       	ldd	r24, Z+32	; 0x20
    12ac:	85 ff       	sbrs	r24, 5
    12ae:	f8 cf       	rjmp	.-16     	; 0x12a0 <__stack+0x1a1>
    12b0:	23 2b       	or	r18, r19
    12b2:	11 f0       	breq	.+4      	; 0x12b8 <__stack+0x1b9>
    12b4:	0c 94 04 12 	jmp	0x2408	; 0x2408 <__stack+0x1309>
    12b8:	ec e0       	ldi	r30, 0x0C	; 12
    12ba:	f0 e0       	ldi	r31, 0x00	; 0
    12bc:	8b e1       	ldi	r24, 0x1B	; 27
    12be:	80 a3       	std	Z+32, r24	; 0x20
    12c0:	20 91 96 03 	lds	r18, 0x0396
    12c4:	30 91 97 03 	lds	r19, 0x0397
    12c8:	05 c0       	rjmp	.+10     	; 0x12d4 <__stack+0x1d5>
    12ca:	eb e0       	ldi	r30, 0x0B	; 11
    12cc:	f0 e0       	ldi	r31, 0x00	; 0
    12ce:	80 a1       	ldd	r24, Z+32	; 0x20
    12d0:	85 fd       	sbrc	r24, 5
    12d2:	08 c0       	rjmp	.+16     	; 0x12e4 <__stack+0x1e5>
    12d4:	21 15       	cp	r18, r1
    12d6:	31 05       	cpc	r19, r1
    12d8:	c1 f3       	breq	.-16     	; 0x12ca <__stack+0x1cb>
    12da:	eb e7       	ldi	r30, 0x7B	; 123
    12dc:	f0 e0       	ldi	r31, 0x00	; 0
    12de:	80 a1       	ldd	r24, Z+32	; 0x20
    12e0:	85 ff       	sbrs	r24, 5
    12e2:	f8 cf       	rjmp	.-16     	; 0x12d4 <__stack+0x1d5>
    12e4:	23 2b       	or	r18, r19
    12e6:	11 f0       	breq	.+4      	; 0x12ec <__stack+0x1ed>
    12e8:	0c 94 1c 12 	jmp	0x2438	; 0x2438 <__stack+0x1339>
    12ec:	ec e0       	ldi	r30, 0x0C	; 12
    12ee:	f0 e0       	ldi	r31, 0x00	; 0
    12f0:	8b e5       	ldi	r24, 0x5B	; 91
    12f2:	80 a3       	std	Z+32, r24	; 0x20
    12f4:	90 91 9a 03 	lds	r25, 0x039A
    12f8:	94 36       	cpi	r25, 0x64	; 100
    12fa:	10 f0       	brcs	.+4      	; 0x1300 <__stack+0x201>
    12fc:	0c 94 14 12 	jmp	0x2428	; 0x2428 <__stack+0x1329>
    1300:	40 e3       	ldi	r20, 0x30	; 48
    1302:	20 91 96 03 	lds	r18, 0x0396
    1306:	30 91 97 03 	lds	r19, 0x0397
    130a:	05 c0       	rjmp	.+10     	; 0x1316 <__stack+0x217>
    130c:	eb e0       	ldi	r30, 0x0B	; 11
    130e:	f0 e0       	ldi	r31, 0x00	; 0
    1310:	80 a1       	ldd	r24, Z+32	; 0x20
    1312:	85 fd       	sbrc	r24, 5
    1314:	08 c0       	rjmp	.+16     	; 0x1326 <__stack+0x227>
    1316:	21 15       	cp	r18, r1
    1318:	31 05       	cpc	r19, r1
    131a:	c1 f3       	breq	.-16     	; 0x130c <__stack+0x20d>
    131c:	eb e7       	ldi	r30, 0x7B	; 123
    131e:	f0 e0       	ldi	r31, 0x00	; 0
    1320:	80 a1       	ldd	r24, Z+32	; 0x20
    1322:	85 ff       	sbrs	r24, 5
    1324:	f8 cf       	rjmp	.-16     	; 0x1316 <__stack+0x217>
    1326:	23 2b       	or	r18, r19
    1328:	11 f0       	breq	.+4      	; 0x132e <__stack+0x22f>
    132a:	0c 94 10 12 	jmp	0x2420	; 0x2420 <__stack+0x1321>
    132e:	ec e0       	ldi	r30, 0x0C	; 12
    1330:	f0 e0       	ldi	r31, 0x00	; 0
    1332:	40 a3       	std	Z+32, r20	; 0x20
    1334:	9a 30       	cpi	r25, 0x0A	; 10
    1336:	10 f0       	brcs	.+4      	; 0x133c <__stack+0x23d>
    1338:	0c 94 08 12 	jmp	0x2410	; 0x2410 <__stack+0x1311>
    133c:	40 e3       	ldi	r20, 0x30	; 48
    133e:	20 91 96 03 	lds	r18, 0x0396
    1342:	30 91 97 03 	lds	r19, 0x0397
    1346:	05 c0       	rjmp	.+10     	; 0x1352 <__stack+0x253>
    1348:	eb e0       	ldi	r30, 0x0B	; 11
    134a:	f0 e0       	ldi	r31, 0x00	; 0
    134c:	80 a1       	ldd	r24, Z+32	; 0x20
    134e:	85 fd       	sbrc	r24, 5
    1350:	08 c0       	rjmp	.+16     	; 0x1362 <__stack+0x263>
    1352:	21 15       	cp	r18, r1
    1354:	31 05       	cpc	r19, r1
    1356:	c1 f3       	breq	.-16     	; 0x1348 <__stack+0x249>
    1358:	eb e7       	ldi	r30, 0x7B	; 123
    135a:	f0 e0       	ldi	r31, 0x00	; 0
    135c:	80 a1       	ldd	r24, Z+32	; 0x20
    135e:	85 ff       	sbrs	r24, 5
    1360:	f8 cf       	rjmp	.-16     	; 0x1352 <__stack+0x253>
    1362:	23 2b       	or	r18, r19
    1364:	11 f0       	breq	.+4      	; 0x136a <__stack+0x26b>
    1366:	0c 94 00 12 	jmp	0x2400	; 0x2400 <__stack+0x1301>
    136a:	ec e0       	ldi	r30, 0x0C	; 12
    136c:	f0 e0       	ldi	r31, 0x00	; 0
    136e:	40 a3       	std	Z+32, r20	; 0x20
    1370:	90 5d       	subi	r25, 0xD0	; 208
    1372:	20 91 96 03 	lds	r18, 0x0396
    1376:	30 91 97 03 	lds	r19, 0x0397
    137a:	05 c0       	rjmp	.+10     	; 0x1386 <__stack+0x287>
    137c:	eb e0       	ldi	r30, 0x0B	; 11
    137e:	f0 e0       	ldi	r31, 0x00	; 0
    1380:	80 a1       	ldd	r24, Z+32	; 0x20
    1382:	85 fd       	sbrc	r24, 5
    1384:	08 c0       	rjmp	.+16     	; 0x1396 <__stack+0x297>
    1386:	21 15       	cp	r18, r1
    1388:	31 05       	cpc	r19, r1
    138a:	c1 f3       	breq	.-16     	; 0x137c <__stack+0x27d>
    138c:	eb e7       	ldi	r30, 0x7B	; 123
    138e:	f0 e0       	ldi	r31, 0x00	; 0
    1390:	80 a1       	ldd	r24, Z+32	; 0x20
    1392:	85 ff       	sbrs	r24, 5
    1394:	f8 cf       	rjmp	.-16     	; 0x1386 <__stack+0x287>
    1396:	23 2b       	or	r18, r19
    1398:	11 f0       	breq	.+4      	; 0x139e <__stack+0x29f>
    139a:	0c 94 e6 11 	jmp	0x23cc	; 0x23cc <__stack+0x12cd>
    139e:	ec e0       	ldi	r30, 0x0C	; 12
    13a0:	f0 e0       	ldi	r31, 0x00	; 0
    13a2:	90 a3       	std	Z+32, r25	; 0x20
    13a4:	20 91 96 03 	lds	r18, 0x0396
    13a8:	30 91 97 03 	lds	r19, 0x0397
    13ac:	05 c0       	rjmp	.+10     	; 0x13b8 <__stack+0x2b9>
    13ae:	eb e0       	ldi	r30, 0x0B	; 11
    13b0:	f0 e0       	ldi	r31, 0x00	; 0
    13b2:	80 a1       	ldd	r24, Z+32	; 0x20
    13b4:	85 fd       	sbrc	r24, 5
    13b6:	08 c0       	rjmp	.+16     	; 0x13c8 <__stack+0x2c9>
    13b8:	21 15       	cp	r18, r1
    13ba:	31 05       	cpc	r19, r1
    13bc:	c1 f3       	breq	.-16     	; 0x13ae <__stack+0x2af>
    13be:	eb e7       	ldi	r30, 0x7B	; 123
    13c0:	f0 e0       	ldi	r31, 0x00	; 0
    13c2:	80 a1       	ldd	r24, Z+32	; 0x20
    13c4:	85 ff       	sbrs	r24, 5
    13c6:	f8 cf       	rjmp	.-16     	; 0x13b8 <__stack+0x2b9>
    13c8:	23 2b       	or	r18, r19
    13ca:	11 f0       	breq	.+4      	; 0x13d0 <__stack+0x2d1>
    13cc:	0c 94 fc 11 	jmp	0x23f8	; 0x23f8 <__stack+0x12f9>
    13d0:	ec e0       	ldi	r30, 0x0C	; 12
    13d2:	f0 e0       	ldi	r31, 0x00	; 0
    13d4:	8b e3       	ldi	r24, 0x3B	; 59
    13d6:	80 a3       	std	Z+32, r24	; 0x20
    13d8:	90 91 9c 03 	lds	r25, 0x039C
    13dc:	94 36       	cpi	r25, 0x64	; 100
    13de:	10 f0       	brcs	.+4      	; 0x13e4 <__stack+0x2e5>
    13e0:	0c 94 f4 11 	jmp	0x23e8	; 0x23e8 <__stack+0x12e9>
    13e4:	40 e3       	ldi	r20, 0x30	; 48
    13e6:	20 91 96 03 	lds	r18, 0x0396
    13ea:	30 91 97 03 	lds	r19, 0x0397
    13ee:	05 c0       	rjmp	.+10     	; 0x13fa <__stack+0x2fb>
    13f0:	eb e0       	ldi	r30, 0x0B	; 11
    13f2:	f0 e0       	ldi	r31, 0x00	; 0
    13f4:	80 a1       	ldd	r24, Z+32	; 0x20
    13f6:	85 fd       	sbrc	r24, 5
    13f8:	08 c0       	rjmp	.+16     	; 0x140a <__stack+0x30b>
    13fa:	21 15       	cp	r18, r1
    13fc:	31 05       	cpc	r19, r1
    13fe:	c1 f3       	breq	.-16     	; 0x13f0 <__stack+0x2f1>
    1400:	eb e7       	ldi	r30, 0x7B	; 123
    1402:	f0 e0       	ldi	r31, 0x00	; 0
    1404:	80 a1       	ldd	r24, Z+32	; 0x20
    1406:	85 ff       	sbrs	r24, 5
    1408:	f8 cf       	rjmp	.-16     	; 0x13fa <__stack+0x2fb>
    140a:	23 2b       	or	r18, r19
    140c:	09 f0       	breq	.+2      	; 0x1410 <__stack+0x311>
    140e:	e9 c7       	rjmp	.+4050   	; 0x23e2 <__stack+0x12e3>
    1410:	ec e0       	ldi	r30, 0x0C	; 12
    1412:	f0 e0       	ldi	r31, 0x00	; 0
    1414:	40 a3       	std	Z+32, r20	; 0x20
    1416:	9a 30       	cpi	r25, 0x0A	; 10
    1418:	08 f0       	brcs	.+2      	; 0x141c <__stack+0x31d>
    141a:	dc c7       	rjmp	.+4024   	; 0x23d4 <__stack+0x12d5>
    141c:	40 e3       	ldi	r20, 0x30	; 48
    141e:	20 91 96 03 	lds	r18, 0x0396
    1422:	30 91 97 03 	lds	r19, 0x0397
    1426:	05 c0       	rjmp	.+10     	; 0x1432 <__stack+0x333>
    1428:	eb e0       	ldi	r30, 0x0B	; 11
    142a:	f0 e0       	ldi	r31, 0x00	; 0
    142c:	80 a1       	ldd	r24, Z+32	; 0x20
    142e:	85 fd       	sbrc	r24, 5
    1430:	08 c0       	rjmp	.+16     	; 0x1442 <__stack+0x343>
    1432:	21 15       	cp	r18, r1
    1434:	31 05       	cpc	r19, r1
    1436:	c1 f3       	breq	.-16     	; 0x1428 <__stack+0x329>
    1438:	eb e7       	ldi	r30, 0x7B	; 123
    143a:	f0 e0       	ldi	r31, 0x00	; 0
    143c:	80 a1       	ldd	r24, Z+32	; 0x20
    143e:	85 ff       	sbrs	r24, 5
    1440:	f8 cf       	rjmp	.-16     	; 0x1432 <__stack+0x333>
    1442:	23 2b       	or	r18, r19
    1444:	09 f0       	breq	.+2      	; 0x1448 <__stack+0x349>
    1446:	bf c7       	rjmp	.+3966   	; 0x23c6 <__stack+0x12c7>
    1448:	ec e0       	ldi	r30, 0x0C	; 12
    144a:	f0 e0       	ldi	r31, 0x00	; 0
    144c:	40 a3       	std	Z+32, r20	; 0x20
    144e:	90 5d       	subi	r25, 0xD0	; 208
    1450:	20 91 96 03 	lds	r18, 0x0396
    1454:	30 91 97 03 	lds	r19, 0x0397
    1458:	05 c0       	rjmp	.+10     	; 0x1464 <__stack+0x365>
    145a:	eb e0       	ldi	r30, 0x0B	; 11
    145c:	f0 e0       	ldi	r31, 0x00	; 0
    145e:	80 a1       	ldd	r24, Z+32	; 0x20
    1460:	85 fd       	sbrc	r24, 5
    1462:	08 c0       	rjmp	.+16     	; 0x1474 <__stack+0x375>
    1464:	21 15       	cp	r18, r1
    1466:	31 05       	cpc	r19, r1
    1468:	c1 f3       	breq	.-16     	; 0x145a <__stack+0x35b>
    146a:	eb e7       	ldi	r30, 0x7B	; 123
    146c:	f0 e0       	ldi	r31, 0x00	; 0
    146e:	80 a1       	ldd	r24, Z+32	; 0x20
    1470:	85 ff       	sbrs	r24, 5
    1472:	f8 cf       	rjmp	.-16     	; 0x1464 <__stack+0x365>
    1474:	23 2b       	or	r18, r19
    1476:	09 f0       	breq	.+2      	; 0x147a <__stack+0x37b>
    1478:	a3 c7       	rjmp	.+3910   	; 0x23c0 <__stack+0x12c1>
    147a:	ec e0       	ldi	r30, 0x0C	; 12
    147c:	f0 e0       	ldi	r31, 0x00	; 0
    147e:	90 a3       	std	Z+32, r25	; 0x20
    1480:	20 91 96 03 	lds	r18, 0x0396
    1484:	30 91 97 03 	lds	r19, 0x0397
    1488:	05 c0       	rjmp	.+10     	; 0x1494 <__stack+0x395>
    148a:	eb e0       	ldi	r30, 0x0B	; 11
    148c:	f0 e0       	ldi	r31, 0x00	; 0
    148e:	80 a1       	ldd	r24, Z+32	; 0x20
    1490:	85 fd       	sbrc	r24, 5
    1492:	08 c0       	rjmp	.+16     	; 0x14a4 <__stack+0x3a5>
    1494:	21 15       	cp	r18, r1
    1496:	31 05       	cpc	r19, r1
    1498:	c1 f3       	breq	.-16     	; 0x148a <__stack+0x38b>
    149a:	eb e7       	ldi	r30, 0x7B	; 123
    149c:	f0 e0       	ldi	r31, 0x00	; 0
    149e:	80 a1       	ldd	r24, Z+32	; 0x20
    14a0:	85 ff       	sbrs	r24, 5
    14a2:	f8 cf       	rjmp	.-16     	; 0x1494 <__stack+0x395>
    14a4:	23 2b       	or	r18, r19
    14a6:	09 f0       	breq	.+2      	; 0x14aa <__stack+0x3ab>
    14a8:	88 c7       	rjmp	.+3856   	; 0x23ba <__stack+0x12bb>
    14aa:	ec e0       	ldi	r30, 0x0C	; 12
    14ac:	f0 e0       	ldi	r31, 0x00	; 0
    14ae:	88 e4       	ldi	r24, 0x48	; 72
    14b0:	80 a3       	std	Z+32, r24	; 0x20
    14b2:	8f e2       	ldi	r24, 0x2F	; 47
    14b4:	8f 8f       	std	Y+31, r24	; 0x1f
    14b6:	18 a2       	std	Y+32, r1	; 0x20
    14b8:	ef e1       	ldi	r30, 0x1F	; 31
    14ba:	ce 2e       	mov	r12, r30
    14bc:	d1 2c       	mov	r13, r1
    14be:	cc 0e       	add	r12, r28
    14c0:	dd 1e       	adc	r13, r29
    14c2:	b6 01       	movw	r22, r12
    14c4:	88 e7       	ldi	r24, 0x78	; 120
    14c6:	93 e0       	ldi	r25, 0x03	; 3
    14c8:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    14cc:	8d e0       	ldi	r24, 0x0D	; 13
    14ce:	90 e0       	ldi	r25, 0x00	; 0
    14d0:	90 93 9b 03 	sts	0x039B, r25
    14d4:	80 93 9a 03 	sts	0x039A, r24
    14d8:	8c e2       	ldi	r24, 0x2C	; 44
    14da:	90 e0       	ldi	r25, 0x00	; 0
    14dc:	90 93 9d 03 	sts	0x039D, r25
    14e0:	80 93 9c 03 	sts	0x039C, r24
    14e4:	20 91 96 03 	lds	r18, 0x0396
    14e8:	30 91 97 03 	lds	r19, 0x0397
    14ec:	05 c0       	rjmp	.+10     	; 0x14f8 <__stack+0x3f9>
    14ee:	eb e0       	ldi	r30, 0x0B	; 11
    14f0:	f0 e0       	ldi	r31, 0x00	; 0
    14f2:	80 a1       	ldd	r24, Z+32	; 0x20
    14f4:	85 fd       	sbrc	r24, 5
    14f6:	08 c0       	rjmp	.+16     	; 0x1508 <__stack+0x409>
    14f8:	21 15       	cp	r18, r1
    14fa:	31 05       	cpc	r19, r1
    14fc:	c1 f3       	breq	.-16     	; 0x14ee <__stack+0x3ef>
    14fe:	eb e7       	ldi	r30, 0x7B	; 123
    1500:	f0 e0       	ldi	r31, 0x00	; 0
    1502:	80 a1       	ldd	r24, Z+32	; 0x20
    1504:	85 ff       	sbrs	r24, 5
    1506:	f8 cf       	rjmp	.-16     	; 0x14f8 <__stack+0x3f9>
    1508:	23 2b       	or	r18, r19
    150a:	09 f0       	breq	.+2      	; 0x150e <__stack+0x40f>
    150c:	3f c7       	rjmp	.+3710   	; 0x238c <__stack+0x128d>
    150e:	ec e0       	ldi	r30, 0x0C	; 12
    1510:	f0 e0       	ldi	r31, 0x00	; 0
    1512:	8b e1       	ldi	r24, 0x1B	; 27
    1514:	80 a3       	std	Z+32, r24	; 0x20
    1516:	20 91 96 03 	lds	r18, 0x0396
    151a:	30 91 97 03 	lds	r19, 0x0397
    151e:	05 c0       	rjmp	.+10     	; 0x152a <__stack+0x42b>
    1520:	eb e0       	ldi	r30, 0x0B	; 11
    1522:	f0 e0       	ldi	r31, 0x00	; 0
    1524:	80 a1       	ldd	r24, Z+32	; 0x20
    1526:	85 fd       	sbrc	r24, 5
    1528:	08 c0       	rjmp	.+16     	; 0x153a <__stack+0x43b>
    152a:	21 15       	cp	r18, r1
    152c:	31 05       	cpc	r19, r1
    152e:	c1 f3       	breq	.-16     	; 0x1520 <__stack+0x421>
    1530:	eb e7       	ldi	r30, 0x7B	; 123
    1532:	f0 e0       	ldi	r31, 0x00	; 0
    1534:	80 a1       	ldd	r24, Z+32	; 0x20
    1536:	85 ff       	sbrs	r24, 5
    1538:	f8 cf       	rjmp	.-16     	; 0x152a <__stack+0x42b>
    153a:	23 2b       	or	r18, r19
    153c:	09 f0       	breq	.+2      	; 0x1540 <__stack+0x441>
    153e:	3a c7       	rjmp	.+3700   	; 0x23b4 <__stack+0x12b5>
    1540:	ec e0       	ldi	r30, 0x0C	; 12
    1542:	f0 e0       	ldi	r31, 0x00	; 0
    1544:	8b e5       	ldi	r24, 0x5B	; 91
    1546:	80 a3       	std	Z+32, r24	; 0x20
    1548:	90 91 9a 03 	lds	r25, 0x039A
    154c:	94 36       	cpi	r25, 0x64	; 100
    154e:	08 f0       	brcs	.+2      	; 0x1552 <__stack+0x453>
    1550:	2a c7       	rjmp	.+3668   	; 0x23a6 <__stack+0x12a7>
    1552:	40 e3       	ldi	r20, 0x30	; 48
    1554:	20 91 96 03 	lds	r18, 0x0396
    1558:	30 91 97 03 	lds	r19, 0x0397
    155c:	05 c0       	rjmp	.+10     	; 0x1568 <__stack+0x469>
    155e:	eb e0       	ldi	r30, 0x0B	; 11
    1560:	f0 e0       	ldi	r31, 0x00	; 0
    1562:	80 a1       	ldd	r24, Z+32	; 0x20
    1564:	85 fd       	sbrc	r24, 5
    1566:	08 c0       	rjmp	.+16     	; 0x1578 <__stack+0x479>
    1568:	21 15       	cp	r18, r1
    156a:	31 05       	cpc	r19, r1
    156c:	c1 f3       	breq	.-16     	; 0x155e <__stack+0x45f>
    156e:	eb e7       	ldi	r30, 0x7B	; 123
    1570:	f0 e0       	ldi	r31, 0x00	; 0
    1572:	80 a1       	ldd	r24, Z+32	; 0x20
    1574:	85 ff       	sbrs	r24, 5
    1576:	f8 cf       	rjmp	.-16     	; 0x1568 <__stack+0x469>
    1578:	23 2b       	or	r18, r19
    157a:	09 f0       	breq	.+2      	; 0x157e <__stack+0x47f>
    157c:	11 c7       	rjmp	.+3618   	; 0x23a0 <__stack+0x12a1>
    157e:	ec e0       	ldi	r30, 0x0C	; 12
    1580:	f0 e0       	ldi	r31, 0x00	; 0
    1582:	40 a3       	std	Z+32, r20	; 0x20
    1584:	9a 30       	cpi	r25, 0x0A	; 10
    1586:	08 f0       	brcs	.+2      	; 0x158a <__stack+0x48b>
    1588:	04 c7       	rjmp	.+3592   	; 0x2392 <__stack+0x1293>
    158a:	40 e3       	ldi	r20, 0x30	; 48
    158c:	20 91 96 03 	lds	r18, 0x0396
    1590:	30 91 97 03 	lds	r19, 0x0397
    1594:	05 c0       	rjmp	.+10     	; 0x15a0 <__stack+0x4a1>
    1596:	eb e0       	ldi	r30, 0x0B	; 11
    1598:	f0 e0       	ldi	r31, 0x00	; 0
    159a:	80 a1       	ldd	r24, Z+32	; 0x20
    159c:	85 fd       	sbrc	r24, 5
    159e:	08 c0       	rjmp	.+16     	; 0x15b0 <__stack+0x4b1>
    15a0:	21 15       	cp	r18, r1
    15a2:	31 05       	cpc	r19, r1
    15a4:	c1 f3       	breq	.-16     	; 0x1596 <__stack+0x497>
    15a6:	eb e7       	ldi	r30, 0x7B	; 123
    15a8:	f0 e0       	ldi	r31, 0x00	; 0
    15aa:	80 a1       	ldd	r24, Z+32	; 0x20
    15ac:	85 ff       	sbrs	r24, 5
    15ae:	f8 cf       	rjmp	.-16     	; 0x15a0 <__stack+0x4a1>
    15b0:	23 2b       	or	r18, r19
    15b2:	09 f0       	breq	.+2      	; 0x15b6 <__stack+0x4b7>
    15b4:	e8 c6       	rjmp	.+3536   	; 0x2386 <__stack+0x1287>
    15b6:	ec e0       	ldi	r30, 0x0C	; 12
    15b8:	f0 e0       	ldi	r31, 0x00	; 0
    15ba:	40 a3       	std	Z+32, r20	; 0x20
    15bc:	90 5d       	subi	r25, 0xD0	; 208
    15be:	20 91 96 03 	lds	r18, 0x0396
    15c2:	30 91 97 03 	lds	r19, 0x0397
    15c6:	05 c0       	rjmp	.+10     	; 0x15d2 <__stack+0x4d3>
    15c8:	eb e0       	ldi	r30, 0x0B	; 11
    15ca:	f0 e0       	ldi	r31, 0x00	; 0
    15cc:	80 a1       	ldd	r24, Z+32	; 0x20
    15ce:	85 fd       	sbrc	r24, 5
    15d0:	08 c0       	rjmp	.+16     	; 0x15e2 <__stack+0x4e3>
    15d2:	21 15       	cp	r18, r1
    15d4:	31 05       	cpc	r19, r1
    15d6:	c1 f3       	breq	.-16     	; 0x15c8 <__stack+0x4c9>
    15d8:	eb e7       	ldi	r30, 0x7B	; 123
    15da:	f0 e0       	ldi	r31, 0x00	; 0
    15dc:	80 a1       	ldd	r24, Z+32	; 0x20
    15de:	85 ff       	sbrs	r24, 5
    15e0:	f8 cf       	rjmp	.-16     	; 0x15d2 <__stack+0x4d3>
    15e2:	23 2b       	or	r18, r19
    15e4:	09 f0       	breq	.+2      	; 0x15e8 <__stack+0x4e9>
    15e6:	b8 c6       	rjmp	.+3440   	; 0x2358 <__stack+0x1259>
    15e8:	ec e0       	ldi	r30, 0x0C	; 12
    15ea:	f0 e0       	ldi	r31, 0x00	; 0
    15ec:	90 a3       	std	Z+32, r25	; 0x20
    15ee:	20 91 96 03 	lds	r18, 0x0396
    15f2:	30 91 97 03 	lds	r19, 0x0397
    15f6:	05 c0       	rjmp	.+10     	; 0x1602 <__stack+0x503>
    15f8:	eb e0       	ldi	r30, 0x0B	; 11
    15fa:	f0 e0       	ldi	r31, 0x00	; 0
    15fc:	80 a1       	ldd	r24, Z+32	; 0x20
    15fe:	85 fd       	sbrc	r24, 5
    1600:	08 c0       	rjmp	.+16     	; 0x1612 <__stack+0x513>
    1602:	21 15       	cp	r18, r1
    1604:	31 05       	cpc	r19, r1
    1606:	c1 f3       	breq	.-16     	; 0x15f8 <__stack+0x4f9>
    1608:	eb e7       	ldi	r30, 0x7B	; 123
    160a:	f0 e0       	ldi	r31, 0x00	; 0
    160c:	80 a1       	ldd	r24, Z+32	; 0x20
    160e:	85 ff       	sbrs	r24, 5
    1610:	f8 cf       	rjmp	.-16     	; 0x1602 <__stack+0x503>
    1612:	23 2b       	or	r18, r19
    1614:	09 f0       	breq	.+2      	; 0x1618 <__stack+0x519>
    1616:	b4 c6       	rjmp	.+3432   	; 0x2380 <__stack+0x1281>
    1618:	ec e0       	ldi	r30, 0x0C	; 12
    161a:	f0 e0       	ldi	r31, 0x00	; 0
    161c:	8b e3       	ldi	r24, 0x3B	; 59
    161e:	80 a3       	std	Z+32, r24	; 0x20
    1620:	90 91 9c 03 	lds	r25, 0x039C
    1624:	94 36       	cpi	r25, 0x64	; 100
    1626:	08 f0       	brcs	.+2      	; 0x162a <__stack+0x52b>
    1628:	a4 c6       	rjmp	.+3400   	; 0x2372 <__stack+0x1273>
    162a:	40 e3       	ldi	r20, 0x30	; 48
    162c:	20 91 96 03 	lds	r18, 0x0396
    1630:	30 91 97 03 	lds	r19, 0x0397
    1634:	05 c0       	rjmp	.+10     	; 0x1640 <__stack+0x541>
    1636:	eb e0       	ldi	r30, 0x0B	; 11
    1638:	f0 e0       	ldi	r31, 0x00	; 0
    163a:	80 a1       	ldd	r24, Z+32	; 0x20
    163c:	85 fd       	sbrc	r24, 5
    163e:	08 c0       	rjmp	.+16     	; 0x1650 <__stack+0x551>
    1640:	21 15       	cp	r18, r1
    1642:	31 05       	cpc	r19, r1
    1644:	c1 f3       	breq	.-16     	; 0x1636 <__stack+0x537>
    1646:	eb e7       	ldi	r30, 0x7B	; 123
    1648:	f0 e0       	ldi	r31, 0x00	; 0
    164a:	80 a1       	ldd	r24, Z+32	; 0x20
    164c:	85 ff       	sbrs	r24, 5
    164e:	f8 cf       	rjmp	.-16     	; 0x1640 <__stack+0x541>
    1650:	23 2b       	or	r18, r19
    1652:	09 f0       	breq	.+2      	; 0x1656 <__stack+0x557>
    1654:	8b c6       	rjmp	.+3350   	; 0x236c <__stack+0x126d>
    1656:	ec e0       	ldi	r30, 0x0C	; 12
    1658:	f0 e0       	ldi	r31, 0x00	; 0
    165a:	40 a3       	std	Z+32, r20	; 0x20
    165c:	9a 30       	cpi	r25, 0x0A	; 10
    165e:	08 f0       	brcs	.+2      	; 0x1662 <__stack+0x563>
    1660:	7e c6       	rjmp	.+3324   	; 0x235e <__stack+0x125f>
    1662:	40 e3       	ldi	r20, 0x30	; 48
    1664:	20 91 96 03 	lds	r18, 0x0396
    1668:	30 91 97 03 	lds	r19, 0x0397
    166c:	05 c0       	rjmp	.+10     	; 0x1678 <__stack+0x579>
    166e:	eb e0       	ldi	r30, 0x0B	; 11
    1670:	f0 e0       	ldi	r31, 0x00	; 0
    1672:	80 a1       	ldd	r24, Z+32	; 0x20
    1674:	85 fd       	sbrc	r24, 5
    1676:	08 c0       	rjmp	.+16     	; 0x1688 <__stack+0x589>
    1678:	21 15       	cp	r18, r1
    167a:	31 05       	cpc	r19, r1
    167c:	c1 f3       	breq	.-16     	; 0x166e <__stack+0x56f>
    167e:	eb e7       	ldi	r30, 0x7B	; 123
    1680:	f0 e0       	ldi	r31, 0x00	; 0
    1682:	80 a1       	ldd	r24, Z+32	; 0x20
    1684:	85 ff       	sbrs	r24, 5
    1686:	f8 cf       	rjmp	.-16     	; 0x1678 <__stack+0x579>
    1688:	23 2b       	or	r18, r19
    168a:	09 f0       	breq	.+2      	; 0x168e <__stack+0x58f>
    168c:	62 c6       	rjmp	.+3268   	; 0x2352 <__stack+0x1253>
    168e:	ec e0       	ldi	r30, 0x0C	; 12
    1690:	f0 e0       	ldi	r31, 0x00	; 0
    1692:	40 a3       	std	Z+32, r20	; 0x20
    1694:	90 5d       	subi	r25, 0xD0	; 208
    1696:	20 91 96 03 	lds	r18, 0x0396
    169a:	30 91 97 03 	lds	r19, 0x0397
    169e:	05 c0       	rjmp	.+10     	; 0x16aa <__stack+0x5ab>
    16a0:	eb e0       	ldi	r30, 0x0B	; 11
    16a2:	f0 e0       	ldi	r31, 0x00	; 0
    16a4:	80 a1       	ldd	r24, Z+32	; 0x20
    16a6:	85 fd       	sbrc	r24, 5
    16a8:	08 c0       	rjmp	.+16     	; 0x16ba <__stack+0x5bb>
    16aa:	21 15       	cp	r18, r1
    16ac:	31 05       	cpc	r19, r1
    16ae:	c1 f3       	breq	.-16     	; 0x16a0 <__stack+0x5a1>
    16b0:	eb e7       	ldi	r30, 0x7B	; 123
    16b2:	f0 e0       	ldi	r31, 0x00	; 0
    16b4:	80 a1       	ldd	r24, Z+32	; 0x20
    16b6:	85 ff       	sbrs	r24, 5
    16b8:	f8 cf       	rjmp	.-16     	; 0x16aa <__stack+0x5ab>
    16ba:	23 2b       	or	r18, r19
    16bc:	09 f0       	breq	.+2      	; 0x16c0 <__stack+0x5c1>
    16be:	46 c6       	rjmp	.+3212   	; 0x234c <__stack+0x124d>
    16c0:	ec e0       	ldi	r30, 0x0C	; 12
    16c2:	f0 e0       	ldi	r31, 0x00	; 0
    16c4:	90 a3       	std	Z+32, r25	; 0x20
    16c6:	20 91 96 03 	lds	r18, 0x0396
    16ca:	30 91 97 03 	lds	r19, 0x0397
    16ce:	05 c0       	rjmp	.+10     	; 0x16da <__stack+0x5db>
    16d0:	eb e0       	ldi	r30, 0x0B	; 11
    16d2:	f0 e0       	ldi	r31, 0x00	; 0
    16d4:	80 a1       	ldd	r24, Z+32	; 0x20
    16d6:	85 fd       	sbrc	r24, 5
    16d8:	08 c0       	rjmp	.+16     	; 0x16ea <__stack+0x5eb>
    16da:	21 15       	cp	r18, r1
    16dc:	31 05       	cpc	r19, r1
    16de:	c1 f3       	breq	.-16     	; 0x16d0 <__stack+0x5d1>
    16e0:	eb e7       	ldi	r30, 0x7B	; 123
    16e2:	f0 e0       	ldi	r31, 0x00	; 0
    16e4:	80 a1       	ldd	r24, Z+32	; 0x20
    16e6:	85 ff       	sbrs	r24, 5
    16e8:	f8 cf       	rjmp	.-16     	; 0x16da <__stack+0x5db>
    16ea:	23 2b       	or	r18, r19
    16ec:	09 f0       	breq	.+2      	; 0x16f0 <__stack+0x5f1>
    16ee:	2b c6       	rjmp	.+3158   	; 0x2346 <__stack+0x1247>
    16f0:	ec e0       	ldi	r30, 0x0C	; 12
    16f2:	f0 e0       	ldi	r31, 0x00	; 0
    16f4:	88 e4       	ldi	r24, 0x48	; 72
    16f6:	80 a3       	std	Z+32, r24	; 0x20
    16f8:	8c e5       	ldi	r24, 0x5C	; 92
    16fa:	8f 8f       	std	Y+31, r24	; 0x1f
    16fc:	18 a2       	std	Y+32, r1	; 0x20
    16fe:	b6 01       	movw	r22, r12
    1700:	88 e7       	ldi	r24, 0x78	; 120
    1702:	93 e0       	ldi	r25, 0x03	; 3
    1704:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    1708:	80 e1       	ldi	r24, 0x10	; 16
    170a:	90 e0       	ldi	r25, 0x00	; 0
    170c:	90 93 9b 03 	sts	0x039B, r25
    1710:	80 93 9a 03 	sts	0x039A, r24
    1714:	83 e2       	ldi	r24, 0x23	; 35
    1716:	90 e0       	ldi	r25, 0x00	; 0
    1718:	90 93 9d 03 	sts	0x039D, r25
    171c:	80 93 9c 03 	sts	0x039C, r24
    1720:	20 91 96 03 	lds	r18, 0x0396
    1724:	30 91 97 03 	lds	r19, 0x0397
    1728:	05 c0       	rjmp	.+10     	; 0x1734 <__stack+0x635>
    172a:	eb e0       	ldi	r30, 0x0B	; 11
    172c:	f0 e0       	ldi	r31, 0x00	; 0
    172e:	80 a1       	ldd	r24, Z+32	; 0x20
    1730:	85 fd       	sbrc	r24, 5
    1732:	08 c0       	rjmp	.+16     	; 0x1744 <__stack+0x645>
    1734:	21 15       	cp	r18, r1
    1736:	31 05       	cpc	r19, r1
    1738:	c1 f3       	breq	.-16     	; 0x172a <__stack+0x62b>
    173a:	eb e7       	ldi	r30, 0x7B	; 123
    173c:	f0 e0       	ldi	r31, 0x00	; 0
    173e:	80 a1       	ldd	r24, Z+32	; 0x20
    1740:	85 ff       	sbrs	r24, 5
    1742:	f8 cf       	rjmp	.-16     	; 0x1734 <__stack+0x635>
    1744:	23 2b       	or	r18, r19
    1746:	09 f0       	breq	.+2      	; 0x174a <__stack+0x64b>
    1748:	e7 c5       	rjmp	.+3022   	; 0x2318 <__stack+0x1219>
    174a:	ec e0       	ldi	r30, 0x0C	; 12
    174c:	f0 e0       	ldi	r31, 0x00	; 0
    174e:	8b e1       	ldi	r24, 0x1B	; 27
    1750:	80 a3       	std	Z+32, r24	; 0x20
    1752:	20 91 96 03 	lds	r18, 0x0396
    1756:	30 91 97 03 	lds	r19, 0x0397
    175a:	05 c0       	rjmp	.+10     	; 0x1766 <__stack+0x667>
    175c:	eb e0       	ldi	r30, 0x0B	; 11
    175e:	f0 e0       	ldi	r31, 0x00	; 0
    1760:	80 a1       	ldd	r24, Z+32	; 0x20
    1762:	85 fd       	sbrc	r24, 5
    1764:	08 c0       	rjmp	.+16     	; 0x1776 <__stack+0x677>
    1766:	21 15       	cp	r18, r1
    1768:	31 05       	cpc	r19, r1
    176a:	c1 f3       	breq	.-16     	; 0x175c <__stack+0x65d>
    176c:	eb e7       	ldi	r30, 0x7B	; 123
    176e:	f0 e0       	ldi	r31, 0x00	; 0
    1770:	80 a1       	ldd	r24, Z+32	; 0x20
    1772:	85 ff       	sbrs	r24, 5
    1774:	f8 cf       	rjmp	.-16     	; 0x1766 <__stack+0x667>
    1776:	23 2b       	or	r18, r19
    1778:	09 f0       	breq	.+2      	; 0x177c <__stack+0x67d>
    177a:	e2 c5       	rjmp	.+3012   	; 0x2340 <__stack+0x1241>
    177c:	ec e0       	ldi	r30, 0x0C	; 12
    177e:	f0 e0       	ldi	r31, 0x00	; 0
    1780:	8b e5       	ldi	r24, 0x5B	; 91
    1782:	80 a3       	std	Z+32, r24	; 0x20
    1784:	90 91 9a 03 	lds	r25, 0x039A
    1788:	94 36       	cpi	r25, 0x64	; 100
    178a:	08 f0       	brcs	.+2      	; 0x178e <__stack+0x68f>
    178c:	d2 c5       	rjmp	.+2980   	; 0x2332 <__stack+0x1233>
    178e:	40 e3       	ldi	r20, 0x30	; 48
    1790:	20 91 96 03 	lds	r18, 0x0396
    1794:	30 91 97 03 	lds	r19, 0x0397
    1798:	05 c0       	rjmp	.+10     	; 0x17a4 <__stack+0x6a5>
    179a:	eb e0       	ldi	r30, 0x0B	; 11
    179c:	f0 e0       	ldi	r31, 0x00	; 0
    179e:	80 a1       	ldd	r24, Z+32	; 0x20
    17a0:	85 fd       	sbrc	r24, 5
    17a2:	08 c0       	rjmp	.+16     	; 0x17b4 <__stack+0x6b5>
    17a4:	21 15       	cp	r18, r1
    17a6:	31 05       	cpc	r19, r1
    17a8:	c1 f3       	breq	.-16     	; 0x179a <__stack+0x69b>
    17aa:	eb e7       	ldi	r30, 0x7B	; 123
    17ac:	f0 e0       	ldi	r31, 0x00	; 0
    17ae:	80 a1       	ldd	r24, Z+32	; 0x20
    17b0:	85 ff       	sbrs	r24, 5
    17b2:	f8 cf       	rjmp	.-16     	; 0x17a4 <__stack+0x6a5>
    17b4:	23 2b       	or	r18, r19
    17b6:	09 f0       	breq	.+2      	; 0x17ba <__stack+0x6bb>
    17b8:	b9 c5       	rjmp	.+2930   	; 0x232c <__stack+0x122d>
    17ba:	ec e0       	ldi	r30, 0x0C	; 12
    17bc:	f0 e0       	ldi	r31, 0x00	; 0
    17be:	40 a3       	std	Z+32, r20	; 0x20
    17c0:	9a 30       	cpi	r25, 0x0A	; 10
    17c2:	08 f0       	brcs	.+2      	; 0x17c6 <__stack+0x6c7>
    17c4:	ac c5       	rjmp	.+2904   	; 0x231e <__stack+0x121f>
    17c6:	40 e3       	ldi	r20, 0x30	; 48
    17c8:	20 91 96 03 	lds	r18, 0x0396
    17cc:	30 91 97 03 	lds	r19, 0x0397
    17d0:	05 c0       	rjmp	.+10     	; 0x17dc <__stack+0x6dd>
    17d2:	eb e0       	ldi	r30, 0x0B	; 11
    17d4:	f0 e0       	ldi	r31, 0x00	; 0
    17d6:	80 a1       	ldd	r24, Z+32	; 0x20
    17d8:	85 fd       	sbrc	r24, 5
    17da:	08 c0       	rjmp	.+16     	; 0x17ec <__stack+0x6ed>
    17dc:	21 15       	cp	r18, r1
    17de:	31 05       	cpc	r19, r1
    17e0:	c1 f3       	breq	.-16     	; 0x17d2 <__stack+0x6d3>
    17e2:	eb e7       	ldi	r30, 0x7B	; 123
    17e4:	f0 e0       	ldi	r31, 0x00	; 0
    17e6:	80 a1       	ldd	r24, Z+32	; 0x20
    17e8:	85 ff       	sbrs	r24, 5
    17ea:	f8 cf       	rjmp	.-16     	; 0x17dc <__stack+0x6dd>
    17ec:	23 2b       	or	r18, r19
    17ee:	09 f0       	breq	.+2      	; 0x17f2 <__stack+0x6f3>
    17f0:	90 c5       	rjmp	.+2848   	; 0x2312 <__stack+0x1213>
    17f2:	ec e0       	ldi	r30, 0x0C	; 12
    17f4:	f0 e0       	ldi	r31, 0x00	; 0
    17f6:	40 a3       	std	Z+32, r20	; 0x20
    17f8:	90 5d       	subi	r25, 0xD0	; 208
    17fa:	20 91 96 03 	lds	r18, 0x0396
    17fe:	30 91 97 03 	lds	r19, 0x0397
    1802:	05 c0       	rjmp	.+10     	; 0x180e <__stack+0x70f>
    1804:	eb e0       	ldi	r30, 0x0B	; 11
    1806:	f0 e0       	ldi	r31, 0x00	; 0
    1808:	80 a1       	ldd	r24, Z+32	; 0x20
    180a:	85 fd       	sbrc	r24, 5
    180c:	08 c0       	rjmp	.+16     	; 0x181e <__stack+0x71f>
    180e:	21 15       	cp	r18, r1
    1810:	31 05       	cpc	r19, r1
    1812:	c1 f3       	breq	.-16     	; 0x1804 <__stack+0x705>
    1814:	eb e7       	ldi	r30, 0x7B	; 123
    1816:	f0 e0       	ldi	r31, 0x00	; 0
    1818:	80 a1       	ldd	r24, Z+32	; 0x20
    181a:	85 ff       	sbrs	r24, 5
    181c:	f8 cf       	rjmp	.-16     	; 0x180e <__stack+0x70f>
    181e:	23 2b       	or	r18, r19
    1820:	09 f0       	breq	.+2      	; 0x1824 <__stack+0x725>
    1822:	60 c5       	rjmp	.+2752   	; 0x22e4 <__stack+0x11e5>
    1824:	ec e0       	ldi	r30, 0x0C	; 12
    1826:	f0 e0       	ldi	r31, 0x00	; 0
    1828:	90 a3       	std	Z+32, r25	; 0x20
    182a:	20 91 96 03 	lds	r18, 0x0396
    182e:	30 91 97 03 	lds	r19, 0x0397
    1832:	05 c0       	rjmp	.+10     	; 0x183e <__stack+0x73f>
    1834:	eb e0       	ldi	r30, 0x0B	; 11
    1836:	f0 e0       	ldi	r31, 0x00	; 0
    1838:	80 a1       	ldd	r24, Z+32	; 0x20
    183a:	85 fd       	sbrc	r24, 5
    183c:	08 c0       	rjmp	.+16     	; 0x184e <__stack+0x74f>
    183e:	21 15       	cp	r18, r1
    1840:	31 05       	cpc	r19, r1
    1842:	c1 f3       	breq	.-16     	; 0x1834 <__stack+0x735>
    1844:	eb e7       	ldi	r30, 0x7B	; 123
    1846:	f0 e0       	ldi	r31, 0x00	; 0
    1848:	80 a1       	ldd	r24, Z+32	; 0x20
    184a:	85 ff       	sbrs	r24, 5
    184c:	f8 cf       	rjmp	.-16     	; 0x183e <__stack+0x73f>
    184e:	23 2b       	or	r18, r19
    1850:	09 f0       	breq	.+2      	; 0x1854 <__stack+0x755>
    1852:	5c c5       	rjmp	.+2744   	; 0x230c <__stack+0x120d>
    1854:	ec e0       	ldi	r30, 0x0C	; 12
    1856:	f0 e0       	ldi	r31, 0x00	; 0
    1858:	8b e3       	ldi	r24, 0x3B	; 59
    185a:	80 a3       	std	Z+32, r24	; 0x20
    185c:	90 91 9c 03 	lds	r25, 0x039C
    1860:	94 36       	cpi	r25, 0x64	; 100
    1862:	08 f0       	brcs	.+2      	; 0x1866 <__stack+0x767>
    1864:	4c c5       	rjmp	.+2712   	; 0x22fe <__stack+0x11ff>
    1866:	40 e3       	ldi	r20, 0x30	; 48
    1868:	20 91 96 03 	lds	r18, 0x0396
    186c:	30 91 97 03 	lds	r19, 0x0397
    1870:	05 c0       	rjmp	.+10     	; 0x187c <__stack+0x77d>
    1872:	eb e0       	ldi	r30, 0x0B	; 11
    1874:	f0 e0       	ldi	r31, 0x00	; 0
    1876:	80 a1       	ldd	r24, Z+32	; 0x20
    1878:	85 fd       	sbrc	r24, 5
    187a:	08 c0       	rjmp	.+16     	; 0x188c <__stack+0x78d>
    187c:	21 15       	cp	r18, r1
    187e:	31 05       	cpc	r19, r1
    1880:	c1 f3       	breq	.-16     	; 0x1872 <__stack+0x773>
    1882:	eb e7       	ldi	r30, 0x7B	; 123
    1884:	f0 e0       	ldi	r31, 0x00	; 0
    1886:	80 a1       	ldd	r24, Z+32	; 0x20
    1888:	85 ff       	sbrs	r24, 5
    188a:	f8 cf       	rjmp	.-16     	; 0x187c <__stack+0x77d>
    188c:	23 2b       	or	r18, r19
    188e:	09 f0       	breq	.+2      	; 0x1892 <__stack+0x793>
    1890:	33 c5       	rjmp	.+2662   	; 0x22f8 <__stack+0x11f9>
    1892:	ec e0       	ldi	r30, 0x0C	; 12
    1894:	f0 e0       	ldi	r31, 0x00	; 0
    1896:	40 a3       	std	Z+32, r20	; 0x20
    1898:	9a 30       	cpi	r25, 0x0A	; 10
    189a:	08 f0       	brcs	.+2      	; 0x189e <__stack+0x79f>
    189c:	26 c5       	rjmp	.+2636   	; 0x22ea <__stack+0x11eb>
    189e:	40 e3       	ldi	r20, 0x30	; 48
    18a0:	20 91 96 03 	lds	r18, 0x0396
    18a4:	30 91 97 03 	lds	r19, 0x0397
    18a8:	05 c0       	rjmp	.+10     	; 0x18b4 <__stack+0x7b5>
    18aa:	eb e0       	ldi	r30, 0x0B	; 11
    18ac:	f0 e0       	ldi	r31, 0x00	; 0
    18ae:	80 a1       	ldd	r24, Z+32	; 0x20
    18b0:	85 fd       	sbrc	r24, 5
    18b2:	08 c0       	rjmp	.+16     	; 0x18c4 <__stack+0x7c5>
    18b4:	21 15       	cp	r18, r1
    18b6:	31 05       	cpc	r19, r1
    18b8:	c1 f3       	breq	.-16     	; 0x18aa <__stack+0x7ab>
    18ba:	eb e7       	ldi	r30, 0x7B	; 123
    18bc:	f0 e0       	ldi	r31, 0x00	; 0
    18be:	80 a1       	ldd	r24, Z+32	; 0x20
    18c0:	85 ff       	sbrs	r24, 5
    18c2:	f8 cf       	rjmp	.-16     	; 0x18b4 <__stack+0x7b5>
    18c4:	23 2b       	or	r18, r19
    18c6:	09 f0       	breq	.+2      	; 0x18ca <__stack+0x7cb>
    18c8:	0a c5       	rjmp	.+2580   	; 0x22de <__stack+0x11df>
    18ca:	ec e0       	ldi	r30, 0x0C	; 12
    18cc:	f0 e0       	ldi	r31, 0x00	; 0
    18ce:	40 a3       	std	Z+32, r20	; 0x20
    18d0:	90 5d       	subi	r25, 0xD0	; 208
    18d2:	20 91 96 03 	lds	r18, 0x0396
    18d6:	30 91 97 03 	lds	r19, 0x0397
    18da:	05 c0       	rjmp	.+10     	; 0x18e6 <__stack+0x7e7>
    18dc:	eb e0       	ldi	r30, 0x0B	; 11
    18de:	f0 e0       	ldi	r31, 0x00	; 0
    18e0:	80 a1       	ldd	r24, Z+32	; 0x20
    18e2:	85 fd       	sbrc	r24, 5
    18e4:	08 c0       	rjmp	.+16     	; 0x18f6 <__stack+0x7f7>
    18e6:	21 15       	cp	r18, r1
    18e8:	31 05       	cpc	r19, r1
    18ea:	c1 f3       	breq	.-16     	; 0x18dc <__stack+0x7dd>
    18ec:	eb e7       	ldi	r30, 0x7B	; 123
    18ee:	f0 e0       	ldi	r31, 0x00	; 0
    18f0:	80 a1       	ldd	r24, Z+32	; 0x20
    18f2:	85 ff       	sbrs	r24, 5
    18f4:	f8 cf       	rjmp	.-16     	; 0x18e6 <__stack+0x7e7>
    18f6:	23 2b       	or	r18, r19
    18f8:	09 f0       	breq	.+2      	; 0x18fc <__stack+0x7fd>
    18fa:	ee c4       	rjmp	.+2524   	; 0x22d8 <__stack+0x11d9>
    18fc:	ec e0       	ldi	r30, 0x0C	; 12
    18fe:	f0 e0       	ldi	r31, 0x00	; 0
    1900:	90 a3       	std	Z+32, r25	; 0x20
    1902:	20 91 96 03 	lds	r18, 0x0396
    1906:	30 91 97 03 	lds	r19, 0x0397
    190a:	05 c0       	rjmp	.+10     	; 0x1916 <__stack+0x817>
    190c:	eb e0       	ldi	r30, 0x0B	; 11
    190e:	f0 e0       	ldi	r31, 0x00	; 0
    1910:	80 a1       	ldd	r24, Z+32	; 0x20
    1912:	85 fd       	sbrc	r24, 5
    1914:	08 c0       	rjmp	.+16     	; 0x1926 <__stack+0x827>
    1916:	21 15       	cp	r18, r1
    1918:	31 05       	cpc	r19, r1
    191a:	c1 f3       	breq	.-16     	; 0x190c <__stack+0x80d>
    191c:	eb e7       	ldi	r30, 0x7B	; 123
    191e:	f0 e0       	ldi	r31, 0x00	; 0
    1920:	80 a1       	ldd	r24, Z+32	; 0x20
    1922:	85 ff       	sbrs	r24, 5
    1924:	f8 cf       	rjmp	.-16     	; 0x1916 <__stack+0x817>
    1926:	23 2b       	or	r18, r19
    1928:	09 f0       	breq	.+2      	; 0x192c <__stack+0x82d>
    192a:	d3 c4       	rjmp	.+2470   	; 0x22d2 <__stack+0x11d3>
    192c:	ec e0       	ldi	r30, 0x0C	; 12
    192e:	f0 e0       	ldi	r31, 0x00	; 0
    1930:	88 e4       	ldi	r24, 0x48	; 72
    1932:	80 a3       	std	Z+32, r24	; 0x20
    1934:	8c e7       	ldi	r24, 0x7C	; 124
    1936:	8f 8f       	std	Y+31, r24	; 0x1f
    1938:	18 a2       	std	Y+32, r1	; 0x20
    193a:	b6 01       	movw	r22, r12
    193c:	88 e7       	ldi	r24, 0x78	; 120
    193e:	93 e0       	ldi	r25, 0x03	; 3
    1940:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    1944:	8d e0       	ldi	r24, 0x0D	; 13
    1946:	90 e0       	ldi	r25, 0x00	; 0
    1948:	90 93 9b 03 	sts	0x039B, r25
    194c:	80 93 9a 03 	sts	0x039A, r24
    1950:	8b e1       	ldi	r24, 0x1B	; 27
    1952:	90 e0       	ldi	r25, 0x00	; 0
    1954:	90 93 9d 03 	sts	0x039D, r25
    1958:	80 93 9c 03 	sts	0x039C, r24
    195c:	20 91 96 03 	lds	r18, 0x0396
    1960:	30 91 97 03 	lds	r19, 0x0397
    1964:	05 c0       	rjmp	.+10     	; 0x1970 <__stack+0x871>
    1966:	eb e0       	ldi	r30, 0x0B	; 11
    1968:	f0 e0       	ldi	r31, 0x00	; 0
    196a:	80 a1       	ldd	r24, Z+32	; 0x20
    196c:	85 fd       	sbrc	r24, 5
    196e:	08 c0       	rjmp	.+16     	; 0x1980 <__stack+0x881>
    1970:	21 15       	cp	r18, r1
    1972:	31 05       	cpc	r19, r1
    1974:	c1 f3       	breq	.-16     	; 0x1966 <__stack+0x867>
    1976:	eb e7       	ldi	r30, 0x7B	; 123
    1978:	f0 e0       	ldi	r31, 0x00	; 0
    197a:	80 a1       	ldd	r24, Z+32	; 0x20
    197c:	85 ff       	sbrs	r24, 5
    197e:	f8 cf       	rjmp	.-16     	; 0x1970 <__stack+0x871>
    1980:	23 2b       	or	r18, r19
    1982:	09 f0       	breq	.+2      	; 0x1986 <__stack+0x887>
    1984:	8f c4       	rjmp	.+2334   	; 0x22a4 <__stack+0x11a5>
    1986:	ec e0       	ldi	r30, 0x0C	; 12
    1988:	f0 e0       	ldi	r31, 0x00	; 0
    198a:	8b e1       	ldi	r24, 0x1B	; 27
    198c:	80 a3       	std	Z+32, r24	; 0x20
    198e:	20 91 96 03 	lds	r18, 0x0396
    1992:	30 91 97 03 	lds	r19, 0x0397
    1996:	05 c0       	rjmp	.+10     	; 0x19a2 <__stack+0x8a3>
    1998:	eb e0       	ldi	r30, 0x0B	; 11
    199a:	f0 e0       	ldi	r31, 0x00	; 0
    199c:	80 a1       	ldd	r24, Z+32	; 0x20
    199e:	85 fd       	sbrc	r24, 5
    19a0:	08 c0       	rjmp	.+16     	; 0x19b2 <__stack+0x8b3>
    19a2:	21 15       	cp	r18, r1
    19a4:	31 05       	cpc	r19, r1
    19a6:	c1 f3       	breq	.-16     	; 0x1998 <__stack+0x899>
    19a8:	eb e7       	ldi	r30, 0x7B	; 123
    19aa:	f0 e0       	ldi	r31, 0x00	; 0
    19ac:	80 a1       	ldd	r24, Z+32	; 0x20
    19ae:	85 ff       	sbrs	r24, 5
    19b0:	f8 cf       	rjmp	.-16     	; 0x19a2 <__stack+0x8a3>
    19b2:	23 2b       	or	r18, r19
    19b4:	09 f0       	breq	.+2      	; 0x19b8 <__stack+0x8b9>
    19b6:	8a c4       	rjmp	.+2324   	; 0x22cc <__stack+0x11cd>
    19b8:	ec e0       	ldi	r30, 0x0C	; 12
    19ba:	f0 e0       	ldi	r31, 0x00	; 0
    19bc:	8b e5       	ldi	r24, 0x5B	; 91
    19be:	80 a3       	std	Z+32, r24	; 0x20
    19c0:	90 91 9a 03 	lds	r25, 0x039A
    19c4:	94 36       	cpi	r25, 0x64	; 100
    19c6:	08 f0       	brcs	.+2      	; 0x19ca <__stack+0x8cb>
    19c8:	7a c4       	rjmp	.+2292   	; 0x22be <__stack+0x11bf>
    19ca:	40 e3       	ldi	r20, 0x30	; 48
    19cc:	20 91 96 03 	lds	r18, 0x0396
    19d0:	30 91 97 03 	lds	r19, 0x0397
    19d4:	05 c0       	rjmp	.+10     	; 0x19e0 <__stack+0x8e1>
    19d6:	eb e0       	ldi	r30, 0x0B	; 11
    19d8:	f0 e0       	ldi	r31, 0x00	; 0
    19da:	80 a1       	ldd	r24, Z+32	; 0x20
    19dc:	85 fd       	sbrc	r24, 5
    19de:	08 c0       	rjmp	.+16     	; 0x19f0 <__stack+0x8f1>
    19e0:	21 15       	cp	r18, r1
    19e2:	31 05       	cpc	r19, r1
    19e4:	c1 f3       	breq	.-16     	; 0x19d6 <__stack+0x8d7>
    19e6:	eb e7       	ldi	r30, 0x7B	; 123
    19e8:	f0 e0       	ldi	r31, 0x00	; 0
    19ea:	80 a1       	ldd	r24, Z+32	; 0x20
    19ec:	85 ff       	sbrs	r24, 5
    19ee:	f8 cf       	rjmp	.-16     	; 0x19e0 <__stack+0x8e1>
    19f0:	23 2b       	or	r18, r19
    19f2:	09 f0       	breq	.+2      	; 0x19f6 <__stack+0x8f7>
    19f4:	61 c4       	rjmp	.+2242   	; 0x22b8 <__stack+0x11b9>
    19f6:	ec e0       	ldi	r30, 0x0C	; 12
    19f8:	f0 e0       	ldi	r31, 0x00	; 0
    19fa:	40 a3       	std	Z+32, r20	; 0x20
    19fc:	9a 30       	cpi	r25, 0x0A	; 10
    19fe:	08 f0       	brcs	.+2      	; 0x1a02 <__stack+0x903>
    1a00:	54 c4       	rjmp	.+2216   	; 0x22aa <__stack+0x11ab>
    1a02:	40 e3       	ldi	r20, 0x30	; 48
    1a04:	20 91 96 03 	lds	r18, 0x0396
    1a08:	30 91 97 03 	lds	r19, 0x0397
    1a0c:	05 c0       	rjmp	.+10     	; 0x1a18 <__stack+0x919>
    1a0e:	eb e0       	ldi	r30, 0x0B	; 11
    1a10:	f0 e0       	ldi	r31, 0x00	; 0
    1a12:	80 a1       	ldd	r24, Z+32	; 0x20
    1a14:	85 fd       	sbrc	r24, 5
    1a16:	08 c0       	rjmp	.+16     	; 0x1a28 <__stack+0x929>
    1a18:	21 15       	cp	r18, r1
    1a1a:	31 05       	cpc	r19, r1
    1a1c:	c1 f3       	breq	.-16     	; 0x1a0e <__stack+0x90f>
    1a1e:	eb e7       	ldi	r30, 0x7B	; 123
    1a20:	f0 e0       	ldi	r31, 0x00	; 0
    1a22:	80 a1       	ldd	r24, Z+32	; 0x20
    1a24:	85 ff       	sbrs	r24, 5
    1a26:	f8 cf       	rjmp	.-16     	; 0x1a18 <__stack+0x919>
    1a28:	23 2b       	or	r18, r19
    1a2a:	09 f0       	breq	.+2      	; 0x1a2e <__stack+0x92f>
    1a2c:	38 c4       	rjmp	.+2160   	; 0x229e <__stack+0x119f>
    1a2e:	ec e0       	ldi	r30, 0x0C	; 12
    1a30:	f0 e0       	ldi	r31, 0x00	; 0
    1a32:	40 a3       	std	Z+32, r20	; 0x20
    1a34:	90 5d       	subi	r25, 0xD0	; 208
    1a36:	20 91 96 03 	lds	r18, 0x0396
    1a3a:	30 91 97 03 	lds	r19, 0x0397
    1a3e:	05 c0       	rjmp	.+10     	; 0x1a4a <__stack+0x94b>
    1a40:	eb e0       	ldi	r30, 0x0B	; 11
    1a42:	f0 e0       	ldi	r31, 0x00	; 0
    1a44:	80 a1       	ldd	r24, Z+32	; 0x20
    1a46:	85 fd       	sbrc	r24, 5
    1a48:	08 c0       	rjmp	.+16     	; 0x1a5a <__stack+0x95b>
    1a4a:	21 15       	cp	r18, r1
    1a4c:	31 05       	cpc	r19, r1
    1a4e:	c1 f3       	breq	.-16     	; 0x1a40 <__stack+0x941>
    1a50:	eb e7       	ldi	r30, 0x7B	; 123
    1a52:	f0 e0       	ldi	r31, 0x00	; 0
    1a54:	80 a1       	ldd	r24, Z+32	; 0x20
    1a56:	85 ff       	sbrs	r24, 5
    1a58:	f8 cf       	rjmp	.-16     	; 0x1a4a <__stack+0x94b>
    1a5a:	23 2b       	or	r18, r19
    1a5c:	09 f0       	breq	.+2      	; 0x1a60 <__stack+0x961>
    1a5e:	08 c4       	rjmp	.+2064   	; 0x2270 <__stack+0x1171>
    1a60:	ec e0       	ldi	r30, 0x0C	; 12
    1a62:	f0 e0       	ldi	r31, 0x00	; 0
    1a64:	90 a3       	std	Z+32, r25	; 0x20
    1a66:	20 91 96 03 	lds	r18, 0x0396
    1a6a:	30 91 97 03 	lds	r19, 0x0397
    1a6e:	05 c0       	rjmp	.+10     	; 0x1a7a <__stack+0x97b>
    1a70:	eb e0       	ldi	r30, 0x0B	; 11
    1a72:	f0 e0       	ldi	r31, 0x00	; 0
    1a74:	80 a1       	ldd	r24, Z+32	; 0x20
    1a76:	85 fd       	sbrc	r24, 5
    1a78:	08 c0       	rjmp	.+16     	; 0x1a8a <__stack+0x98b>
    1a7a:	21 15       	cp	r18, r1
    1a7c:	31 05       	cpc	r19, r1
    1a7e:	c1 f3       	breq	.-16     	; 0x1a70 <__stack+0x971>
    1a80:	eb e7       	ldi	r30, 0x7B	; 123
    1a82:	f0 e0       	ldi	r31, 0x00	; 0
    1a84:	80 a1       	ldd	r24, Z+32	; 0x20
    1a86:	85 ff       	sbrs	r24, 5
    1a88:	f8 cf       	rjmp	.-16     	; 0x1a7a <__stack+0x97b>
    1a8a:	23 2b       	or	r18, r19
    1a8c:	09 f0       	breq	.+2      	; 0x1a90 <__stack+0x991>
    1a8e:	04 c4       	rjmp	.+2056   	; 0x2298 <__stack+0x1199>
    1a90:	ec e0       	ldi	r30, 0x0C	; 12
    1a92:	f0 e0       	ldi	r31, 0x00	; 0
    1a94:	8b e3       	ldi	r24, 0x3B	; 59
    1a96:	80 a3       	std	Z+32, r24	; 0x20
    1a98:	90 91 9c 03 	lds	r25, 0x039C
    1a9c:	94 36       	cpi	r25, 0x64	; 100
    1a9e:	08 f0       	brcs	.+2      	; 0x1aa2 <__stack+0x9a3>
    1aa0:	f4 c3       	rjmp	.+2024   	; 0x228a <__stack+0x118b>
    1aa2:	40 e3       	ldi	r20, 0x30	; 48
    1aa4:	20 91 96 03 	lds	r18, 0x0396
    1aa8:	30 91 97 03 	lds	r19, 0x0397
    1aac:	05 c0       	rjmp	.+10     	; 0x1ab8 <__stack+0x9b9>
    1aae:	eb e0       	ldi	r30, 0x0B	; 11
    1ab0:	f0 e0       	ldi	r31, 0x00	; 0
    1ab2:	80 a1       	ldd	r24, Z+32	; 0x20
    1ab4:	85 fd       	sbrc	r24, 5
    1ab6:	08 c0       	rjmp	.+16     	; 0x1ac8 <__stack+0x9c9>
    1ab8:	21 15       	cp	r18, r1
    1aba:	31 05       	cpc	r19, r1
    1abc:	c1 f3       	breq	.-16     	; 0x1aae <__stack+0x9af>
    1abe:	eb e7       	ldi	r30, 0x7B	; 123
    1ac0:	f0 e0       	ldi	r31, 0x00	; 0
    1ac2:	80 a1       	ldd	r24, Z+32	; 0x20
    1ac4:	85 ff       	sbrs	r24, 5
    1ac6:	f8 cf       	rjmp	.-16     	; 0x1ab8 <__stack+0x9b9>
    1ac8:	23 2b       	or	r18, r19
    1aca:	09 f0       	breq	.+2      	; 0x1ace <__stack+0x9cf>
    1acc:	db c3       	rjmp	.+1974   	; 0x2284 <__stack+0x1185>
    1ace:	ec e0       	ldi	r30, 0x0C	; 12
    1ad0:	f0 e0       	ldi	r31, 0x00	; 0
    1ad2:	40 a3       	std	Z+32, r20	; 0x20
    1ad4:	9a 30       	cpi	r25, 0x0A	; 10
    1ad6:	08 f0       	brcs	.+2      	; 0x1ada <__stack+0x9db>
    1ad8:	ce c3       	rjmp	.+1948   	; 0x2276 <__stack+0x1177>
    1ada:	40 e3       	ldi	r20, 0x30	; 48
    1adc:	20 91 96 03 	lds	r18, 0x0396
    1ae0:	30 91 97 03 	lds	r19, 0x0397
    1ae4:	05 c0       	rjmp	.+10     	; 0x1af0 <__stack+0x9f1>
    1ae6:	eb e0       	ldi	r30, 0x0B	; 11
    1ae8:	f0 e0       	ldi	r31, 0x00	; 0
    1aea:	80 a1       	ldd	r24, Z+32	; 0x20
    1aec:	85 fd       	sbrc	r24, 5
    1aee:	08 c0       	rjmp	.+16     	; 0x1b00 <__stack+0xa01>
    1af0:	21 15       	cp	r18, r1
    1af2:	31 05       	cpc	r19, r1
    1af4:	c1 f3       	breq	.-16     	; 0x1ae6 <__stack+0x9e7>
    1af6:	eb e7       	ldi	r30, 0x7B	; 123
    1af8:	f0 e0       	ldi	r31, 0x00	; 0
    1afa:	80 a1       	ldd	r24, Z+32	; 0x20
    1afc:	85 ff       	sbrs	r24, 5
    1afe:	f8 cf       	rjmp	.-16     	; 0x1af0 <__stack+0x9f1>
    1b00:	23 2b       	or	r18, r19
    1b02:	09 f0       	breq	.+2      	; 0x1b06 <__stack+0xa07>
    1b04:	b2 c3       	rjmp	.+1892   	; 0x226a <__stack+0x116b>
    1b06:	ec e0       	ldi	r30, 0x0C	; 12
    1b08:	f0 e0       	ldi	r31, 0x00	; 0
    1b0a:	40 a3       	std	Z+32, r20	; 0x20
    1b0c:	90 5d       	subi	r25, 0xD0	; 208
    1b0e:	20 91 96 03 	lds	r18, 0x0396
    1b12:	30 91 97 03 	lds	r19, 0x0397
    1b16:	05 c0       	rjmp	.+10     	; 0x1b22 <__stack+0xa23>
    1b18:	eb e0       	ldi	r30, 0x0B	; 11
    1b1a:	f0 e0       	ldi	r31, 0x00	; 0
    1b1c:	80 a1       	ldd	r24, Z+32	; 0x20
    1b1e:	85 fd       	sbrc	r24, 5
    1b20:	08 c0       	rjmp	.+16     	; 0x1b32 <__stack+0xa33>
    1b22:	21 15       	cp	r18, r1
    1b24:	31 05       	cpc	r19, r1
    1b26:	c1 f3       	breq	.-16     	; 0x1b18 <__stack+0xa19>
    1b28:	eb e7       	ldi	r30, 0x7B	; 123
    1b2a:	f0 e0       	ldi	r31, 0x00	; 0
    1b2c:	80 a1       	ldd	r24, Z+32	; 0x20
    1b2e:	85 ff       	sbrs	r24, 5
    1b30:	f8 cf       	rjmp	.-16     	; 0x1b22 <__stack+0xa23>
    1b32:	23 2b       	or	r18, r19
    1b34:	09 f0       	breq	.+2      	; 0x1b38 <__stack+0xa39>
    1b36:	96 c3       	rjmp	.+1836   	; 0x2264 <__stack+0x1165>
    1b38:	ec e0       	ldi	r30, 0x0C	; 12
    1b3a:	f0 e0       	ldi	r31, 0x00	; 0
    1b3c:	90 a3       	std	Z+32, r25	; 0x20
    1b3e:	20 91 96 03 	lds	r18, 0x0396
    1b42:	30 91 97 03 	lds	r19, 0x0397
    1b46:	05 c0       	rjmp	.+10     	; 0x1b52 <__stack+0xa53>
    1b48:	eb e0       	ldi	r30, 0x0B	; 11
    1b4a:	f0 e0       	ldi	r31, 0x00	; 0
    1b4c:	80 a1       	ldd	r24, Z+32	; 0x20
    1b4e:	85 fd       	sbrc	r24, 5
    1b50:	08 c0       	rjmp	.+16     	; 0x1b62 <__stack+0xa63>
    1b52:	21 15       	cp	r18, r1
    1b54:	31 05       	cpc	r19, r1
    1b56:	c1 f3       	breq	.-16     	; 0x1b48 <__stack+0xa49>
    1b58:	eb e7       	ldi	r30, 0x7B	; 123
    1b5a:	f0 e0       	ldi	r31, 0x00	; 0
    1b5c:	80 a1       	ldd	r24, Z+32	; 0x20
    1b5e:	85 ff       	sbrs	r24, 5
    1b60:	f8 cf       	rjmp	.-16     	; 0x1b52 <__stack+0xa53>
    1b62:	23 2b       	or	r18, r19
    1b64:	09 f0       	breq	.+2      	; 0x1b68 <__stack+0xa69>
    1b66:	7b c3       	rjmp	.+1782   	; 0x225e <__stack+0x115f>
    1b68:	ec e0       	ldi	r30, 0x0C	; 12
    1b6a:	f0 e0       	ldi	r31, 0x00	; 0
    1b6c:	88 e4       	ldi	r24, 0x48	; 72
    1b6e:	80 a3       	std	Z+32, r24	; 0x20
    1b70:	8f e2       	ldi	r24, 0x2F	; 47
    1b72:	8f 8f       	std	Y+31, r24	; 0x1f
    1b74:	18 a2       	std	Y+32, r1	; 0x20
    1b76:	b6 01       	movw	r22, r12
    1b78:	88 e7       	ldi	r24, 0x78	; 120
    1b7a:	93 e0       	ldi	r25, 0x03	; 3
    1b7c:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    1b80:	87 e0       	ldi	r24, 0x07	; 7
    1b82:	90 e0       	ldi	r25, 0x00	; 0
    1b84:	90 93 9b 03 	sts	0x039B, r25
    1b88:	80 93 9a 03 	sts	0x039A, r24
    1b8c:	8b e1       	ldi	r24, 0x1B	; 27
    1b8e:	90 e0       	ldi	r25, 0x00	; 0
    1b90:	90 93 9d 03 	sts	0x039D, r25
    1b94:	80 93 9c 03 	sts	0x039C, r24
    1b98:	20 91 96 03 	lds	r18, 0x0396
    1b9c:	30 91 97 03 	lds	r19, 0x0397
    1ba0:	05 c0       	rjmp	.+10     	; 0x1bac <__stack+0xaad>
    1ba2:	eb e0       	ldi	r30, 0x0B	; 11
    1ba4:	f0 e0       	ldi	r31, 0x00	; 0
    1ba6:	80 a1       	ldd	r24, Z+32	; 0x20
    1ba8:	85 fd       	sbrc	r24, 5
    1baa:	08 c0       	rjmp	.+16     	; 0x1bbc <__stack+0xabd>
    1bac:	21 15       	cp	r18, r1
    1bae:	31 05       	cpc	r19, r1
    1bb0:	c1 f3       	breq	.-16     	; 0x1ba2 <__stack+0xaa3>
    1bb2:	eb e7       	ldi	r30, 0x7B	; 123
    1bb4:	f0 e0       	ldi	r31, 0x00	; 0
    1bb6:	80 a1       	ldd	r24, Z+32	; 0x20
    1bb8:	85 ff       	sbrs	r24, 5
    1bba:	f8 cf       	rjmp	.-16     	; 0x1bac <__stack+0xaad>
    1bbc:	23 2b       	or	r18, r19
    1bbe:	09 f0       	breq	.+2      	; 0x1bc2 <__stack+0xac3>
    1bc0:	37 c3       	rjmp	.+1646   	; 0x2230 <__stack+0x1131>
    1bc2:	ec e0       	ldi	r30, 0x0C	; 12
    1bc4:	f0 e0       	ldi	r31, 0x00	; 0
    1bc6:	8b e1       	ldi	r24, 0x1B	; 27
    1bc8:	80 a3       	std	Z+32, r24	; 0x20
    1bca:	20 91 96 03 	lds	r18, 0x0396
    1bce:	30 91 97 03 	lds	r19, 0x0397
    1bd2:	05 c0       	rjmp	.+10     	; 0x1bde <__stack+0xadf>
    1bd4:	eb e0       	ldi	r30, 0x0B	; 11
    1bd6:	f0 e0       	ldi	r31, 0x00	; 0
    1bd8:	80 a1       	ldd	r24, Z+32	; 0x20
    1bda:	85 fd       	sbrc	r24, 5
    1bdc:	08 c0       	rjmp	.+16     	; 0x1bee <__stack+0xaef>
    1bde:	21 15       	cp	r18, r1
    1be0:	31 05       	cpc	r19, r1
    1be2:	c1 f3       	breq	.-16     	; 0x1bd4 <__stack+0xad5>
    1be4:	eb e7       	ldi	r30, 0x7B	; 123
    1be6:	f0 e0       	ldi	r31, 0x00	; 0
    1be8:	80 a1       	ldd	r24, Z+32	; 0x20
    1bea:	85 ff       	sbrs	r24, 5
    1bec:	f8 cf       	rjmp	.-16     	; 0x1bde <__stack+0xadf>
    1bee:	23 2b       	or	r18, r19
    1bf0:	09 f0       	breq	.+2      	; 0x1bf4 <__stack+0xaf5>
    1bf2:	32 c3       	rjmp	.+1636   	; 0x2258 <__stack+0x1159>
    1bf4:	ec e0       	ldi	r30, 0x0C	; 12
    1bf6:	f0 e0       	ldi	r31, 0x00	; 0
    1bf8:	8b e5       	ldi	r24, 0x5B	; 91
    1bfa:	80 a3       	std	Z+32, r24	; 0x20
    1bfc:	90 91 9a 03 	lds	r25, 0x039A
    1c00:	94 36       	cpi	r25, 0x64	; 100
    1c02:	08 f0       	brcs	.+2      	; 0x1c06 <__stack+0xb07>
    1c04:	22 c3       	rjmp	.+1604   	; 0x224a <__stack+0x114b>
    1c06:	40 e3       	ldi	r20, 0x30	; 48
    1c08:	20 91 96 03 	lds	r18, 0x0396
    1c0c:	30 91 97 03 	lds	r19, 0x0397
    1c10:	05 c0       	rjmp	.+10     	; 0x1c1c <__stack+0xb1d>
    1c12:	eb e0       	ldi	r30, 0x0B	; 11
    1c14:	f0 e0       	ldi	r31, 0x00	; 0
    1c16:	80 a1       	ldd	r24, Z+32	; 0x20
    1c18:	85 fd       	sbrc	r24, 5
    1c1a:	08 c0       	rjmp	.+16     	; 0x1c2c <__stack+0xb2d>
    1c1c:	21 15       	cp	r18, r1
    1c1e:	31 05       	cpc	r19, r1
    1c20:	c1 f3       	breq	.-16     	; 0x1c12 <__stack+0xb13>
    1c22:	eb e7       	ldi	r30, 0x7B	; 123
    1c24:	f0 e0       	ldi	r31, 0x00	; 0
    1c26:	80 a1       	ldd	r24, Z+32	; 0x20
    1c28:	85 ff       	sbrs	r24, 5
    1c2a:	f8 cf       	rjmp	.-16     	; 0x1c1c <__stack+0xb1d>
    1c2c:	23 2b       	or	r18, r19
    1c2e:	09 f0       	breq	.+2      	; 0x1c32 <__stack+0xb33>
    1c30:	09 c3       	rjmp	.+1554   	; 0x2244 <__stack+0x1145>
    1c32:	ec e0       	ldi	r30, 0x0C	; 12
    1c34:	f0 e0       	ldi	r31, 0x00	; 0
    1c36:	40 a3       	std	Z+32, r20	; 0x20
    1c38:	9a 30       	cpi	r25, 0x0A	; 10
    1c3a:	08 f0       	brcs	.+2      	; 0x1c3e <__stack+0xb3f>
    1c3c:	fc c2       	rjmp	.+1528   	; 0x2236 <__stack+0x1137>
    1c3e:	40 e3       	ldi	r20, 0x30	; 48
    1c40:	20 91 96 03 	lds	r18, 0x0396
    1c44:	30 91 97 03 	lds	r19, 0x0397
    1c48:	05 c0       	rjmp	.+10     	; 0x1c54 <__stack+0xb55>
    1c4a:	eb e0       	ldi	r30, 0x0B	; 11
    1c4c:	f0 e0       	ldi	r31, 0x00	; 0
    1c4e:	80 a1       	ldd	r24, Z+32	; 0x20
    1c50:	85 fd       	sbrc	r24, 5
    1c52:	08 c0       	rjmp	.+16     	; 0x1c64 <__stack+0xb65>
    1c54:	21 15       	cp	r18, r1
    1c56:	31 05       	cpc	r19, r1
    1c58:	c1 f3       	breq	.-16     	; 0x1c4a <__stack+0xb4b>
    1c5a:	eb e7       	ldi	r30, 0x7B	; 123
    1c5c:	f0 e0       	ldi	r31, 0x00	; 0
    1c5e:	80 a1       	ldd	r24, Z+32	; 0x20
    1c60:	85 ff       	sbrs	r24, 5
    1c62:	f8 cf       	rjmp	.-16     	; 0x1c54 <__stack+0xb55>
    1c64:	23 2b       	or	r18, r19
    1c66:	09 f0       	breq	.+2      	; 0x1c6a <__stack+0xb6b>
    1c68:	e0 c2       	rjmp	.+1472   	; 0x222a <__stack+0x112b>
    1c6a:	ec e0       	ldi	r30, 0x0C	; 12
    1c6c:	f0 e0       	ldi	r31, 0x00	; 0
    1c6e:	40 a3       	std	Z+32, r20	; 0x20
    1c70:	90 5d       	subi	r25, 0xD0	; 208
    1c72:	20 91 96 03 	lds	r18, 0x0396
    1c76:	30 91 97 03 	lds	r19, 0x0397
    1c7a:	05 c0       	rjmp	.+10     	; 0x1c86 <__stack+0xb87>
    1c7c:	eb e0       	ldi	r30, 0x0B	; 11
    1c7e:	f0 e0       	ldi	r31, 0x00	; 0
    1c80:	80 a1       	ldd	r24, Z+32	; 0x20
    1c82:	85 fd       	sbrc	r24, 5
    1c84:	08 c0       	rjmp	.+16     	; 0x1c96 <__stack+0xb97>
    1c86:	21 15       	cp	r18, r1
    1c88:	31 05       	cpc	r19, r1
    1c8a:	c1 f3       	breq	.-16     	; 0x1c7c <__stack+0xb7d>
    1c8c:	eb e7       	ldi	r30, 0x7B	; 123
    1c8e:	f0 e0       	ldi	r31, 0x00	; 0
    1c90:	80 a1       	ldd	r24, Z+32	; 0x20
    1c92:	85 ff       	sbrs	r24, 5
    1c94:	f8 cf       	rjmp	.-16     	; 0x1c86 <__stack+0xb87>
    1c96:	23 2b       	or	r18, r19
    1c98:	09 f0       	breq	.+2      	; 0x1c9c <__stack+0xb9d>
    1c9a:	b0 c2       	rjmp	.+1376   	; 0x21fc <__stack+0x10fd>
    1c9c:	ec e0       	ldi	r30, 0x0C	; 12
    1c9e:	f0 e0       	ldi	r31, 0x00	; 0
    1ca0:	90 a3       	std	Z+32, r25	; 0x20
    1ca2:	20 91 96 03 	lds	r18, 0x0396
    1ca6:	30 91 97 03 	lds	r19, 0x0397
    1caa:	05 c0       	rjmp	.+10     	; 0x1cb6 <__stack+0xbb7>
    1cac:	eb e0       	ldi	r30, 0x0B	; 11
    1cae:	f0 e0       	ldi	r31, 0x00	; 0
    1cb0:	80 a1       	ldd	r24, Z+32	; 0x20
    1cb2:	85 fd       	sbrc	r24, 5
    1cb4:	08 c0       	rjmp	.+16     	; 0x1cc6 <__stack+0xbc7>
    1cb6:	21 15       	cp	r18, r1
    1cb8:	31 05       	cpc	r19, r1
    1cba:	c1 f3       	breq	.-16     	; 0x1cac <__stack+0xbad>
    1cbc:	eb e7       	ldi	r30, 0x7B	; 123
    1cbe:	f0 e0       	ldi	r31, 0x00	; 0
    1cc0:	80 a1       	ldd	r24, Z+32	; 0x20
    1cc2:	85 ff       	sbrs	r24, 5
    1cc4:	f8 cf       	rjmp	.-16     	; 0x1cb6 <__stack+0xbb7>
    1cc6:	23 2b       	or	r18, r19
    1cc8:	09 f0       	breq	.+2      	; 0x1ccc <__stack+0xbcd>
    1cca:	ac c2       	rjmp	.+1368   	; 0x2224 <__stack+0x1125>
    1ccc:	ec e0       	ldi	r30, 0x0C	; 12
    1cce:	f0 e0       	ldi	r31, 0x00	; 0
    1cd0:	8b e3       	ldi	r24, 0x3B	; 59
    1cd2:	80 a3       	std	Z+32, r24	; 0x20
    1cd4:	90 91 9c 03 	lds	r25, 0x039C
    1cd8:	94 36       	cpi	r25, 0x64	; 100
    1cda:	08 f0       	brcs	.+2      	; 0x1cde <__stack+0xbdf>
    1cdc:	9c c2       	rjmp	.+1336   	; 0x2216 <__stack+0x1117>
    1cde:	40 e3       	ldi	r20, 0x30	; 48
    1ce0:	20 91 96 03 	lds	r18, 0x0396
    1ce4:	30 91 97 03 	lds	r19, 0x0397
    1ce8:	05 c0       	rjmp	.+10     	; 0x1cf4 <__stack+0xbf5>
    1cea:	eb e0       	ldi	r30, 0x0B	; 11
    1cec:	f0 e0       	ldi	r31, 0x00	; 0
    1cee:	80 a1       	ldd	r24, Z+32	; 0x20
    1cf0:	85 fd       	sbrc	r24, 5
    1cf2:	08 c0       	rjmp	.+16     	; 0x1d04 <__stack+0xc05>
    1cf4:	21 15       	cp	r18, r1
    1cf6:	31 05       	cpc	r19, r1
    1cf8:	c1 f3       	breq	.-16     	; 0x1cea <__stack+0xbeb>
    1cfa:	eb e7       	ldi	r30, 0x7B	; 123
    1cfc:	f0 e0       	ldi	r31, 0x00	; 0
    1cfe:	80 a1       	ldd	r24, Z+32	; 0x20
    1d00:	85 ff       	sbrs	r24, 5
    1d02:	f8 cf       	rjmp	.-16     	; 0x1cf4 <__stack+0xbf5>
    1d04:	23 2b       	or	r18, r19
    1d06:	09 f0       	breq	.+2      	; 0x1d0a <__stack+0xc0b>
    1d08:	83 c2       	rjmp	.+1286   	; 0x2210 <__stack+0x1111>
    1d0a:	ec e0       	ldi	r30, 0x0C	; 12
    1d0c:	f0 e0       	ldi	r31, 0x00	; 0
    1d0e:	40 a3       	std	Z+32, r20	; 0x20
    1d10:	9a 30       	cpi	r25, 0x0A	; 10
    1d12:	08 f0       	brcs	.+2      	; 0x1d16 <__stack+0xc17>
    1d14:	76 c2       	rjmp	.+1260   	; 0x2202 <__stack+0x1103>
    1d16:	40 e3       	ldi	r20, 0x30	; 48
    1d18:	20 91 96 03 	lds	r18, 0x0396
    1d1c:	30 91 97 03 	lds	r19, 0x0397
    1d20:	05 c0       	rjmp	.+10     	; 0x1d2c <__stack+0xc2d>
    1d22:	eb e0       	ldi	r30, 0x0B	; 11
    1d24:	f0 e0       	ldi	r31, 0x00	; 0
    1d26:	80 a1       	ldd	r24, Z+32	; 0x20
    1d28:	85 fd       	sbrc	r24, 5
    1d2a:	08 c0       	rjmp	.+16     	; 0x1d3c <__stack+0xc3d>
    1d2c:	21 15       	cp	r18, r1
    1d2e:	31 05       	cpc	r19, r1
    1d30:	c1 f3       	breq	.-16     	; 0x1d22 <__stack+0xc23>
    1d32:	eb e7       	ldi	r30, 0x7B	; 123
    1d34:	f0 e0       	ldi	r31, 0x00	; 0
    1d36:	80 a1       	ldd	r24, Z+32	; 0x20
    1d38:	85 ff       	sbrs	r24, 5
    1d3a:	f8 cf       	rjmp	.-16     	; 0x1d2c <__stack+0xc2d>
    1d3c:	23 2b       	or	r18, r19
    1d3e:	09 f0       	breq	.+2      	; 0x1d42 <__stack+0xc43>
    1d40:	5a c2       	rjmp	.+1204   	; 0x21f6 <__stack+0x10f7>
    1d42:	ec e0       	ldi	r30, 0x0C	; 12
    1d44:	f0 e0       	ldi	r31, 0x00	; 0
    1d46:	40 a3       	std	Z+32, r20	; 0x20
    1d48:	90 5d       	subi	r25, 0xD0	; 208
    1d4a:	20 91 96 03 	lds	r18, 0x0396
    1d4e:	30 91 97 03 	lds	r19, 0x0397
    1d52:	05 c0       	rjmp	.+10     	; 0x1d5e <__stack+0xc5f>
    1d54:	eb e0       	ldi	r30, 0x0B	; 11
    1d56:	f0 e0       	ldi	r31, 0x00	; 0
    1d58:	80 a1       	ldd	r24, Z+32	; 0x20
    1d5a:	85 fd       	sbrc	r24, 5
    1d5c:	08 c0       	rjmp	.+16     	; 0x1d6e <__stack+0xc6f>
    1d5e:	21 15       	cp	r18, r1
    1d60:	31 05       	cpc	r19, r1
    1d62:	c1 f3       	breq	.-16     	; 0x1d54 <__stack+0xc55>
    1d64:	eb e7       	ldi	r30, 0x7B	; 123
    1d66:	f0 e0       	ldi	r31, 0x00	; 0
    1d68:	80 a1       	ldd	r24, Z+32	; 0x20
    1d6a:	85 ff       	sbrs	r24, 5
    1d6c:	f8 cf       	rjmp	.-16     	; 0x1d5e <__stack+0xc5f>
    1d6e:	23 2b       	or	r18, r19
    1d70:	09 f0       	breq	.+2      	; 0x1d74 <__stack+0xc75>
    1d72:	3b c2       	rjmp	.+1142   	; 0x21ea <__stack+0x10eb>
    1d74:	ec e0       	ldi	r30, 0x0C	; 12
    1d76:	f0 e0       	ldi	r31, 0x00	; 0
    1d78:	90 a3       	std	Z+32, r25	; 0x20
    1d7a:	20 91 96 03 	lds	r18, 0x0396
    1d7e:	30 91 97 03 	lds	r19, 0x0397
    1d82:	05 c0       	rjmp	.+10     	; 0x1d8e <__stack+0xc8f>
    1d84:	eb e0       	ldi	r30, 0x0B	; 11
    1d86:	f0 e0       	ldi	r31, 0x00	; 0
    1d88:	80 a1       	ldd	r24, Z+32	; 0x20
    1d8a:	85 fd       	sbrc	r24, 5
    1d8c:	08 c0       	rjmp	.+16     	; 0x1d9e <__stack+0xc9f>
    1d8e:	21 15       	cp	r18, r1
    1d90:	31 05       	cpc	r19, r1
    1d92:	c1 f3       	breq	.-16     	; 0x1d84 <__stack+0xc85>
    1d94:	eb e7       	ldi	r30, 0x7B	; 123
    1d96:	f0 e0       	ldi	r31, 0x00	; 0
    1d98:	80 a1       	ldd	r24, Z+32	; 0x20
    1d9a:	85 ff       	sbrs	r24, 5
    1d9c:	f8 cf       	rjmp	.-16     	; 0x1d8e <__stack+0xc8f>
    1d9e:	23 2b       	or	r18, r19
    1da0:	09 f0       	breq	.+2      	; 0x1da4 <__stack+0xca5>
    1da2:	26 c2       	rjmp	.+1100   	; 0x21f0 <__stack+0x10f1>
    1da4:	ec e0       	ldi	r30, 0x0C	; 12
    1da6:	f0 e0       	ldi	r31, 0x00	; 0
    1da8:	88 e4       	ldi	r24, 0x48	; 72
    1daa:	80 a3       	std	Z+32, r24	; 0x20
    1dac:	8c e5       	ldi	r24, 0x5C	; 92
    1dae:	8f 8f       	std	Y+31, r24	; 0x1f
    1db0:	18 a2       	std	Y+32, r1	; 0x20
    1db2:	b6 01       	movw	r22, r12
    1db4:	88 e7       	ldi	r24, 0x78	; 120
    1db6:	93 e0       	ldi	r25, 0x03	; 3
    1db8:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    1dbc:	f8 94       	cli
    1dbe:	20 91 94 03 	lds	r18, 0x0394
    1dc2:	30 91 95 03 	lds	r19, 0x0395
    1dc6:	80 91 94 03 	lds	r24, 0x0394
    1dca:	90 91 95 03 	lds	r25, 0x0395
    1dce:	01 96       	adiw	r24, 0x01	; 1
    1dd0:	90 93 95 03 	sts	0x0395, r25
    1dd4:	80 93 94 03 	sts	0x0394, r24
    1dd8:	78 94       	sei
    1dda:	37 fd       	sbrc	r19, 7
    1ddc:	47 c3       	rjmp	.+1678   	; 0x246c <__stack+0x136d>
    1dde:	00 e0       	ldi	r16, 0x00	; 0
    1de0:	10 e0       	ldi	r17, 0x00	; 0
    1de2:	74 e8       	ldi	r23, 0x84	; 132
    1de4:	e7 2e       	mov	r14, r23
    1de6:	73 e0       	ldi	r23, 0x03	; 3
    1de8:	f7 2e       	mov	r15, r23
    1dea:	d7 01       	movw	r26, r14
    1dec:	8d 91       	ld	r24, X+
    1dee:	9c 91       	ld	r25, X
    1df0:	0e 94 ed 24 	call	0x49da	; 0x49da <_ZN6System6Thread4joinEv>
    1df4:	5c 01       	movw	r10, r24
    1df6:	f8 94       	cli
    1df8:	20 91 94 03 	lds	r18, 0x0394
    1dfc:	30 91 95 03 	lds	r19, 0x0395
    1e00:	80 91 94 03 	lds	r24, 0x0394
    1e04:	90 91 95 03 	lds	r25, 0x0395
    1e08:	01 97       	sbiw	r24, 0x01	; 1
    1e0a:	90 93 95 03 	sts	0x0395, r25
    1e0e:	80 93 94 03 	sts	0x0394, r24
    1e12:	78 94       	sei
    1e14:	12 16       	cp	r1, r18
    1e16:	13 06       	cpc	r1, r19
    1e18:	0c f0       	brlt	.+2      	; 0x1e1c <__stack+0xd1d>
    1e1a:	e2 c1       	rjmp	.+964    	; 0x21e0 <__stack+0x10e1>
    1e1c:	0c 5e       	subi	r16, 0xEC	; 236
    1e1e:	1f 4f       	sbci	r17, 0xFF	; 255
    1e20:	10 93 9b 03 	sts	0x039B, r17
    1e24:	00 93 9a 03 	sts	0x039A, r16
    1e28:	04 51       	subi	r16, 0x14	; 20
    1e2a:	10 40       	sbci	r17, 0x00	; 0
    1e2c:	10 92 9d 03 	sts	0x039D, r1
    1e30:	10 92 9c 03 	sts	0x039C, r1
    1e34:	20 91 96 03 	lds	r18, 0x0396
    1e38:	30 91 97 03 	lds	r19, 0x0397
    1e3c:	05 c0       	rjmp	.+10     	; 0x1e48 <__stack+0xd49>
    1e3e:	eb e0       	ldi	r30, 0x0B	; 11
    1e40:	f0 e0       	ldi	r31, 0x00	; 0
    1e42:	80 a1       	ldd	r24, Z+32	; 0x20
    1e44:	85 fd       	sbrc	r24, 5
    1e46:	08 c0       	rjmp	.+16     	; 0x1e58 <__stack+0xd59>
    1e48:	21 15       	cp	r18, r1
    1e4a:	31 05       	cpc	r19, r1
    1e4c:	c1 f3       	breq	.-16     	; 0x1e3e <__stack+0xd3f>
    1e4e:	eb e7       	ldi	r30, 0x7B	; 123
    1e50:	f0 e0       	ldi	r31, 0x00	; 0
    1e52:	80 a1       	ldd	r24, Z+32	; 0x20
    1e54:	85 ff       	sbrs	r24, 5
    1e56:	f8 cf       	rjmp	.-16     	; 0x1e48 <__stack+0xd49>
    1e58:	23 2b       	or	r18, r19
    1e5a:	09 f0       	breq	.+2      	; 0x1e5e <__stack+0xd5f>
    1e5c:	b9 c1       	rjmp	.+882    	; 0x21d0 <__stack+0x10d1>
    1e5e:	ec e0       	ldi	r30, 0x0C	; 12
    1e60:	f0 e0       	ldi	r31, 0x00	; 0
    1e62:	8b e1       	ldi	r24, 0x1B	; 27
    1e64:	80 a3       	std	Z+32, r24	; 0x20
    1e66:	20 91 96 03 	lds	r18, 0x0396
    1e6a:	30 91 97 03 	lds	r19, 0x0397
    1e6e:	05 c0       	rjmp	.+10     	; 0x1e7a <__stack+0xd7b>
    1e70:	eb e0       	ldi	r30, 0x0B	; 11
    1e72:	f0 e0       	ldi	r31, 0x00	; 0
    1e74:	80 a1       	ldd	r24, Z+32	; 0x20
    1e76:	85 fd       	sbrc	r24, 5
    1e78:	08 c0       	rjmp	.+16     	; 0x1e8a <__stack+0xd8b>
    1e7a:	21 15       	cp	r18, r1
    1e7c:	31 05       	cpc	r19, r1
    1e7e:	c1 f3       	breq	.-16     	; 0x1e70 <__stack+0xd71>
    1e80:	eb e7       	ldi	r30, 0x7B	; 123
    1e82:	f0 e0       	ldi	r31, 0x00	; 0
    1e84:	80 a1       	ldd	r24, Z+32	; 0x20
    1e86:	85 ff       	sbrs	r24, 5
    1e88:	f8 cf       	rjmp	.-16     	; 0x1e7a <__stack+0xd7b>
    1e8a:	23 2b       	or	r18, r19
    1e8c:	09 f0       	breq	.+2      	; 0x1e90 <__stack+0xd91>
    1e8e:	9d c1       	rjmp	.+826    	; 0x21ca <__stack+0x10cb>
    1e90:	ec e0       	ldi	r30, 0x0C	; 12
    1e92:	f0 e0       	ldi	r31, 0x00	; 0
    1e94:	8b e5       	ldi	r24, 0x5B	; 91
    1e96:	80 a3       	std	Z+32, r24	; 0x20
    1e98:	90 91 9a 03 	lds	r25, 0x039A
    1e9c:	94 36       	cpi	r25, 0x64	; 100
    1e9e:	08 f0       	brcs	.+2      	; 0x1ea2 <__stack+0xda3>
    1ea0:	8d c1       	rjmp	.+794    	; 0x21bc <__stack+0x10bd>
    1ea2:	40 e3       	ldi	r20, 0x30	; 48
    1ea4:	20 91 96 03 	lds	r18, 0x0396
    1ea8:	30 91 97 03 	lds	r19, 0x0397
    1eac:	05 c0       	rjmp	.+10     	; 0x1eb8 <__stack+0xdb9>
    1eae:	eb e0       	ldi	r30, 0x0B	; 11
    1eb0:	f0 e0       	ldi	r31, 0x00	; 0
    1eb2:	80 a1       	ldd	r24, Z+32	; 0x20
    1eb4:	85 fd       	sbrc	r24, 5
    1eb6:	08 c0       	rjmp	.+16     	; 0x1ec8 <__stack+0xdc9>
    1eb8:	21 15       	cp	r18, r1
    1eba:	31 05       	cpc	r19, r1
    1ebc:	c1 f3       	breq	.-16     	; 0x1eae <__stack+0xdaf>
    1ebe:	eb e7       	ldi	r30, 0x7B	; 123
    1ec0:	f0 e0       	ldi	r31, 0x00	; 0
    1ec2:	80 a1       	ldd	r24, Z+32	; 0x20
    1ec4:	85 ff       	sbrs	r24, 5
    1ec6:	f8 cf       	rjmp	.-16     	; 0x1eb8 <__stack+0xdb9>
    1ec8:	23 2b       	or	r18, r19
    1eca:	09 f0       	breq	.+2      	; 0x1ece <__stack+0xdcf>
    1ecc:	74 c1       	rjmp	.+744    	; 0x21b6 <__stack+0x10b7>
    1ece:	ec e0       	ldi	r30, 0x0C	; 12
    1ed0:	f0 e0       	ldi	r31, 0x00	; 0
    1ed2:	40 a3       	std	Z+32, r20	; 0x20
    1ed4:	9a 30       	cpi	r25, 0x0A	; 10
    1ed6:	08 f0       	brcs	.+2      	; 0x1eda <__stack+0xddb>
    1ed8:	67 c1       	rjmp	.+718    	; 0x21a8 <__stack+0x10a9>
    1eda:	40 e3       	ldi	r20, 0x30	; 48
    1edc:	20 91 96 03 	lds	r18, 0x0396
    1ee0:	30 91 97 03 	lds	r19, 0x0397
    1ee4:	05 c0       	rjmp	.+10     	; 0x1ef0 <__stack+0xdf1>
    1ee6:	eb e0       	ldi	r30, 0x0B	; 11
    1ee8:	f0 e0       	ldi	r31, 0x00	; 0
    1eea:	80 a1       	ldd	r24, Z+32	; 0x20
    1eec:	85 fd       	sbrc	r24, 5
    1eee:	08 c0       	rjmp	.+16     	; 0x1f00 <__stack+0xe01>
    1ef0:	21 15       	cp	r18, r1
    1ef2:	31 05       	cpc	r19, r1
    1ef4:	c1 f3       	breq	.-16     	; 0x1ee6 <__stack+0xde7>
    1ef6:	eb e7       	ldi	r30, 0x7B	; 123
    1ef8:	f0 e0       	ldi	r31, 0x00	; 0
    1efa:	80 a1       	ldd	r24, Z+32	; 0x20
    1efc:	85 ff       	sbrs	r24, 5
    1efe:	f8 cf       	rjmp	.-16     	; 0x1ef0 <__stack+0xdf1>
    1f00:	23 2b       	or	r18, r19
    1f02:	09 f0       	breq	.+2      	; 0x1f06 <__stack+0xe07>
    1f04:	4e c1       	rjmp	.+668    	; 0x21a2 <__stack+0x10a3>
    1f06:	ec e0       	ldi	r30, 0x0C	; 12
    1f08:	f0 e0       	ldi	r31, 0x00	; 0
    1f0a:	40 a3       	std	Z+32, r20	; 0x20
    1f0c:	90 5d       	subi	r25, 0xD0	; 208
    1f0e:	20 91 96 03 	lds	r18, 0x0396
    1f12:	30 91 97 03 	lds	r19, 0x0397
    1f16:	05 c0       	rjmp	.+10     	; 0x1f22 <__stack+0xe23>
    1f18:	eb e0       	ldi	r30, 0x0B	; 11
    1f1a:	f0 e0       	ldi	r31, 0x00	; 0
    1f1c:	80 a1       	ldd	r24, Z+32	; 0x20
    1f1e:	85 fd       	sbrc	r24, 5
    1f20:	08 c0       	rjmp	.+16     	; 0x1f32 <__stack+0xe33>
    1f22:	21 15       	cp	r18, r1
    1f24:	31 05       	cpc	r19, r1
    1f26:	c1 f3       	breq	.-16     	; 0x1f18 <__stack+0xe19>
    1f28:	eb e7       	ldi	r30, 0x7B	; 123
    1f2a:	f0 e0       	ldi	r31, 0x00	; 0
    1f2c:	80 a1       	ldd	r24, Z+32	; 0x20
    1f2e:	85 ff       	sbrs	r24, 5
    1f30:	f8 cf       	rjmp	.-16     	; 0x1f22 <__stack+0xe23>
    1f32:	23 2b       	or	r18, r19
    1f34:	09 f0       	breq	.+2      	; 0x1f38 <__stack+0xe39>
    1f36:	32 c1       	rjmp	.+612    	; 0x219c <__stack+0x109d>
    1f38:	ec e0       	ldi	r30, 0x0C	; 12
    1f3a:	f0 e0       	ldi	r31, 0x00	; 0
    1f3c:	90 a3       	std	Z+32, r25	; 0x20
    1f3e:	20 91 96 03 	lds	r18, 0x0396
    1f42:	30 91 97 03 	lds	r19, 0x0397
    1f46:	05 c0       	rjmp	.+10     	; 0x1f52 <__stack+0xe53>
    1f48:	eb e0       	ldi	r30, 0x0B	; 11
    1f4a:	f0 e0       	ldi	r31, 0x00	; 0
    1f4c:	80 a1       	ldd	r24, Z+32	; 0x20
    1f4e:	85 fd       	sbrc	r24, 5
    1f50:	08 c0       	rjmp	.+16     	; 0x1f62 <__stack+0xe63>
    1f52:	21 15       	cp	r18, r1
    1f54:	31 05       	cpc	r19, r1
    1f56:	c1 f3       	breq	.-16     	; 0x1f48 <__stack+0xe49>
    1f58:	eb e7       	ldi	r30, 0x7B	; 123
    1f5a:	f0 e0       	ldi	r31, 0x00	; 0
    1f5c:	80 a1       	ldd	r24, Z+32	; 0x20
    1f5e:	85 ff       	sbrs	r24, 5
    1f60:	f8 cf       	rjmp	.-16     	; 0x1f52 <__stack+0xe53>
    1f62:	23 2b       	or	r18, r19
    1f64:	09 f0       	breq	.+2      	; 0x1f68 <__stack+0xe69>
    1f66:	17 c1       	rjmp	.+558    	; 0x2196 <__stack+0x1097>
    1f68:	ec e0       	ldi	r30, 0x0C	; 12
    1f6a:	f0 e0       	ldi	r31, 0x00	; 0
    1f6c:	8b e3       	ldi	r24, 0x3B	; 59
    1f6e:	80 a3       	std	Z+32, r24	; 0x20
    1f70:	90 91 9c 03 	lds	r25, 0x039C
    1f74:	94 36       	cpi	r25, 0x64	; 100
    1f76:	08 f0       	brcs	.+2      	; 0x1f7a <__stack+0xe7b>
    1f78:	07 c1       	rjmp	.+526    	; 0x2188 <__stack+0x1089>
    1f7a:	40 e3       	ldi	r20, 0x30	; 48
    1f7c:	20 91 96 03 	lds	r18, 0x0396
    1f80:	30 91 97 03 	lds	r19, 0x0397
    1f84:	05 c0       	rjmp	.+10     	; 0x1f90 <__stack+0xe91>
    1f86:	eb e0       	ldi	r30, 0x0B	; 11
    1f88:	f0 e0       	ldi	r31, 0x00	; 0
    1f8a:	80 a1       	ldd	r24, Z+32	; 0x20
    1f8c:	85 fd       	sbrc	r24, 5
    1f8e:	08 c0       	rjmp	.+16     	; 0x1fa0 <__stack+0xea1>
    1f90:	21 15       	cp	r18, r1
    1f92:	31 05       	cpc	r19, r1
    1f94:	c1 f3       	breq	.-16     	; 0x1f86 <__stack+0xe87>
    1f96:	eb e7       	ldi	r30, 0x7B	; 123
    1f98:	f0 e0       	ldi	r31, 0x00	; 0
    1f9a:	80 a1       	ldd	r24, Z+32	; 0x20
    1f9c:	85 ff       	sbrs	r24, 5
    1f9e:	f8 cf       	rjmp	.-16     	; 0x1f90 <__stack+0xe91>
    1fa0:	23 2b       	or	r18, r19
    1fa2:	09 f0       	breq	.+2      	; 0x1fa6 <__stack+0xea7>
    1fa4:	ee c0       	rjmp	.+476    	; 0x2182 <__stack+0x1083>
    1fa6:	ec e0       	ldi	r30, 0x0C	; 12
    1fa8:	f0 e0       	ldi	r31, 0x00	; 0
    1faa:	40 a3       	std	Z+32, r20	; 0x20
    1fac:	9a 30       	cpi	r25, 0x0A	; 10
    1fae:	08 f0       	brcs	.+2      	; 0x1fb2 <__stack+0xeb3>
    1fb0:	e1 c0       	rjmp	.+450    	; 0x2174 <__stack+0x1075>
    1fb2:	40 e3       	ldi	r20, 0x30	; 48
    1fb4:	20 91 96 03 	lds	r18, 0x0396
    1fb8:	30 91 97 03 	lds	r19, 0x0397
    1fbc:	05 c0       	rjmp	.+10     	; 0x1fc8 <__stack+0xec9>
    1fbe:	eb e0       	ldi	r30, 0x0B	; 11
    1fc0:	f0 e0       	ldi	r31, 0x00	; 0
    1fc2:	80 a1       	ldd	r24, Z+32	; 0x20
    1fc4:	85 fd       	sbrc	r24, 5
    1fc6:	08 c0       	rjmp	.+16     	; 0x1fd8 <__stack+0xed9>
    1fc8:	21 15       	cp	r18, r1
    1fca:	31 05       	cpc	r19, r1
    1fcc:	c1 f3       	breq	.-16     	; 0x1fbe <__stack+0xebf>
    1fce:	eb e7       	ldi	r30, 0x7B	; 123
    1fd0:	f0 e0       	ldi	r31, 0x00	; 0
    1fd2:	80 a1       	ldd	r24, Z+32	; 0x20
    1fd4:	85 ff       	sbrs	r24, 5
    1fd6:	f8 cf       	rjmp	.-16     	; 0x1fc8 <__stack+0xec9>
    1fd8:	23 2b       	or	r18, r19
    1fda:	09 f0       	breq	.+2      	; 0x1fde <__stack+0xedf>
    1fdc:	c8 c0       	rjmp	.+400    	; 0x216e <__stack+0x106f>
    1fde:	ec e0       	ldi	r30, 0x0C	; 12
    1fe0:	f0 e0       	ldi	r31, 0x00	; 0
    1fe2:	40 a3       	std	Z+32, r20	; 0x20
    1fe4:	90 5d       	subi	r25, 0xD0	; 208
    1fe6:	20 91 96 03 	lds	r18, 0x0396
    1fea:	30 91 97 03 	lds	r19, 0x0397
    1fee:	05 c0       	rjmp	.+10     	; 0x1ffa <__stack+0xefb>
    1ff0:	eb e0       	ldi	r30, 0x0B	; 11
    1ff2:	f0 e0       	ldi	r31, 0x00	; 0
    1ff4:	80 a1       	ldd	r24, Z+32	; 0x20
    1ff6:	85 fd       	sbrc	r24, 5
    1ff8:	08 c0       	rjmp	.+16     	; 0x200a <__stack+0xf0b>
    1ffa:	21 15       	cp	r18, r1
    1ffc:	31 05       	cpc	r19, r1
    1ffe:	c1 f3       	breq	.-16     	; 0x1ff0 <__stack+0xef1>
    2000:	eb e7       	ldi	r30, 0x7B	; 123
    2002:	f0 e0       	ldi	r31, 0x00	; 0
    2004:	80 a1       	ldd	r24, Z+32	; 0x20
    2006:	85 ff       	sbrs	r24, 5
    2008:	f8 cf       	rjmp	.-16     	; 0x1ffa <__stack+0xefb>
    200a:	23 2b       	or	r18, r19
    200c:	09 f0       	breq	.+2      	; 0x2010 <__stack+0xf11>
    200e:	ac c0       	rjmp	.+344    	; 0x2168 <__stack+0x1069>
    2010:	ec e0       	ldi	r30, 0x0C	; 12
    2012:	f0 e0       	ldi	r31, 0x00	; 0
    2014:	90 a3       	std	Z+32, r25	; 0x20
    2016:	20 91 96 03 	lds	r18, 0x0396
    201a:	30 91 97 03 	lds	r19, 0x0397
    201e:	05 c0       	rjmp	.+10     	; 0x202a <__stack+0xf2b>
    2020:	eb e0       	ldi	r30, 0x0B	; 11
    2022:	f0 e0       	ldi	r31, 0x00	; 0
    2024:	80 a1       	ldd	r24, Z+32	; 0x20
    2026:	85 fd       	sbrc	r24, 5
    2028:	08 c0       	rjmp	.+16     	; 0x203a <__stack+0xf3b>
    202a:	21 15       	cp	r18, r1
    202c:	31 05       	cpc	r19, r1
    202e:	c1 f3       	breq	.-16     	; 0x2020 <__stack+0xf21>
    2030:	eb e7       	ldi	r30, 0x7B	; 123
    2032:	f0 e0       	ldi	r31, 0x00	; 0
    2034:	80 a1       	ldd	r24, Z+32	; 0x20
    2036:	85 ff       	sbrs	r24, 5
    2038:	f8 cf       	rjmp	.-16     	; 0x202a <__stack+0xf2b>
    203a:	23 2b       	or	r18, r19
    203c:	09 f0       	breq	.+2      	; 0x2040 <__stack+0xf41>
    203e:	91 c0       	rjmp	.+290    	; 0x2162 <__stack+0x1063>
    2040:	ec e0       	ldi	r30, 0x0C	; 12
    2042:	f0 e0       	ldi	r31, 0x00	; 0
    2044:	88 e4       	ldi	r24, 0x48	; 72
    2046:	80 a3       	std	Z+32, r24	; 0x20
    2048:	64 ec       	ldi	r22, 0xC4	; 196
    204a:	71 e0       	ldi	r23, 0x01	; 1
    204c:	88 e7       	ldi	r24, 0x78	; 120
    204e:	93 e0       	ldi	r25, 0x03	; 3
    2050:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    2054:	a6 01       	movw	r20, r12
    2056:	b8 01       	movw	r22, r16
    2058:	88 e7       	ldi	r24, 0x78	; 120
    205a:	93 e0       	ldi	r25, 0x03	; 3
    205c:	0e 94 d4 14 	call	0x29a8	; 0x29a8 <_ZN6System7OStream4itoaEiPc>
    2060:	f6 01       	movw	r30, r12
    2062:	e8 0f       	add	r30, r24
    2064:	f9 1f       	adc	r31, r25
    2066:	10 82       	st	Z, r1
    2068:	b6 01       	movw	r22, r12
    206a:	88 e7       	ldi	r24, 0x78	; 120
    206c:	93 e0       	ldi	r25, 0x03	; 3
    206e:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    2072:	61 ed       	ldi	r22, 0xD1	; 209
    2074:	71 e0       	ldi	r23, 0x01	; 1
    2076:	88 e7       	ldi	r24, 0x78	; 120
    2078:	93 e0       	ldi	r25, 0x03	; 3
    207a:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    207e:	a6 01       	movw	r20, r12
    2080:	b5 01       	movw	r22, r10
    2082:	88 e7       	ldi	r24, 0x78	; 120
    2084:	93 e0       	ldi	r25, 0x03	; 3
    2086:	0e 94 d4 14 	call	0x29a8	; 0x29a8 <_ZN6System7OStream4itoaEiPc>
    208a:	f6 01       	movw	r30, r12
    208c:	e8 0f       	add	r30, r24
    208e:	f9 1f       	adc	r31, r25
    2090:	10 82       	st	Z, r1
    2092:	b6 01       	movw	r22, r12
    2094:	88 e7       	ldi	r24, 0x78	; 120
    2096:	93 e0       	ldi	r25, 0x03	; 3
    2098:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    209c:	67 ed       	ldi	r22, 0xD7	; 215
    209e:	71 e0       	ldi	r23, 0x01	; 1
    20a0:	88 e7       	ldi	r24, 0x78	; 120
    20a2:	93 e0       	ldi	r25, 0x03	; 3
    20a4:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    20a8:	f8 94       	cli
    20aa:	20 91 94 03 	lds	r18, 0x0394
    20ae:	30 91 95 03 	lds	r19, 0x0395
    20b2:	80 91 94 03 	lds	r24, 0x0394
    20b6:	90 91 95 03 	lds	r25, 0x0395
    20ba:	01 96       	adiw	r24, 0x01	; 1
    20bc:	90 93 95 03 	sts	0x0395, r25
    20c0:	80 93 94 03 	sts	0x0394, r24
    20c4:	78 94       	sei
    20c6:	37 fd       	sbrc	r19, 7
    20c8:	86 c0       	rjmp	.+268    	; 0x21d6 <__stack+0x10d7>
    20ca:	0f 5f       	subi	r16, 0xFF	; 255
    20cc:	1f 4f       	sbci	r17, 0xFF	; 255
    20ce:	e2 e0       	ldi	r30, 0x02	; 2
    20d0:	f0 e0       	ldi	r31, 0x00	; 0
    20d2:	ee 0e       	add	r14, r30
    20d4:	ff 1e       	adc	r15, r31
    20d6:	05 30       	cpi	r16, 0x05	; 5
    20d8:	11 05       	cpc	r17, r1
    20da:	09 f0       	breq	.+2      	; 0x20de <__stack+0xfdf>
    20dc:	86 ce       	rjmp	.-756    	; 0x1dea <__stack+0xceb>
    20de:	6a e7       	ldi	r22, 0x7A	; 122
    20e0:	e6 2e       	mov	r14, r22
    20e2:	63 e0       	ldi	r22, 0x03	; 3
    20e4:	f6 2e       	mov	r15, r22
    20e6:	d7 01       	movw	r26, r14
    20e8:	0d 91       	ld	r16, X+
    20ea:	1c 91       	ld	r17, X
    20ec:	01 15       	cp	r16, r1
    20ee:	11 05       	cpc	r17, r1
    20f0:	61 f0       	breq	.+24     	; 0x210a <__stack+0x100b>
    20f2:	c8 01       	movw	r24, r16
    20f4:	0e 94 78 21 	call	0x42f0	; 0x42f0 <_ZN6System6Thread10wakeup_allEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
    20f8:	f8 01       	movw	r30, r16
    20fa:	32 97       	sbiw	r30, 0x02	; 2
    20fc:	40 81       	ld	r20, Z
    20fe:	51 81       	ldd	r21, Z+1	; 0x01
    2100:	bf 01       	movw	r22, r30
    2102:	8c e6       	ldi	r24, 0x6C	; 108
    2104:	93 e0       	ldi	r25, 0x03	; 3
    2106:	0e 94 c1 12 	call	0x2582	; 0x2582 <_ZN6System4Heap4freeEPvj>
    210a:	e2 e0       	ldi	r30, 0x02	; 2
    210c:	f0 e0       	ldi	r31, 0x00	; 0
    210e:	ee 0e       	add	r14, r30
    2110:	ff 1e       	adc	r15, r31
    2112:	f4 e8       	ldi	r31, 0x84	; 132
    2114:	ef 16       	cp	r14, r31
    2116:	f3 e0       	ldi	r31, 0x03	; 3
    2118:	ff 06       	cpc	r15, r31
    211a:	29 f7       	brne	.-54     	; 0x20e6 <__stack+0xfe7>
    211c:	54 e8       	ldi	r21, 0x84	; 132
    211e:	e5 2e       	mov	r14, r21
    2120:	53 e0       	ldi	r21, 0x03	; 3
    2122:	f5 2e       	mov	r15, r21
    2124:	4a e0       	ldi	r20, 0x0A	; 10
    2126:	c4 2e       	mov	r12, r20
    2128:	d1 2c       	mov	r13, r1
    212a:	ce 0c       	add	r12, r14
    212c:	df 1c       	adc	r13, r15
    212e:	d7 01       	movw	r26, r14
    2130:	0d 91       	ld	r16, X+
    2132:	1c 91       	ld	r17, X
    2134:	01 15       	cp	r16, r1
    2136:	11 05       	cpc	r17, r1
    2138:	61 f0       	breq	.+24     	; 0x2152 <__stack+0x1053>
    213a:	c8 01       	movw	r24, r16
    213c:	0e 94 6c 27 	call	0x4ed8	; 0x4ed8 <_ZN6System6ThreadD1Ev>
    2140:	f8 01       	movw	r30, r16
    2142:	32 97       	sbiw	r30, 0x02	; 2
    2144:	40 81       	ld	r20, Z
    2146:	51 81       	ldd	r21, Z+1	; 0x01
    2148:	bf 01       	movw	r22, r30
    214a:	8c e6       	ldi	r24, 0x6C	; 108
    214c:	93 e0       	ldi	r25, 0x03	; 3
    214e:	0e 94 c1 12 	call	0x2582	; 0x2582 <_ZN6System4Heap4freeEPvj>
    2152:	e2 e0       	ldi	r30, 0x02	; 2
    2154:	f0 e0       	ldi	r31, 0x00	; 0
    2156:	ee 0e       	add	r14, r30
    2158:	ff 1e       	adc	r15, r31
    215a:	ce 14       	cp	r12, r14
    215c:	df 04       	cpc	r13, r15
    215e:	39 f7       	brne	.-50     	; 0x212e <__stack+0x102f>
    2160:	8a c1       	rjmp	.+788    	; 0x2476 <__stack+0x1377>
    2162:	ec e7       	ldi	r30, 0x7C	; 124
    2164:	f0 e0       	ldi	r31, 0x00	; 0
    2166:	6e cf       	rjmp	.-292    	; 0x2044 <__stack+0xf45>
    2168:	ec e7       	ldi	r30, 0x7C	; 124
    216a:	f0 e0       	ldi	r31, 0x00	; 0
    216c:	53 cf       	rjmp	.-346    	; 0x2014 <__stack+0xf15>
    216e:	ec e7       	ldi	r30, 0x7C	; 124
    2170:	f0 e0       	ldi	r31, 0x00	; 0
    2172:	37 cf       	rjmp	.-402    	; 0x1fe2 <__stack+0xee3>
    2174:	80 e3       	ldi	r24, 0x30	; 48
    2176:	8f 5f       	subi	r24, 0xFF	; 255
    2178:	9a 50       	subi	r25, 0x0A	; 10
    217a:	9a 30       	cpi	r25, 0x0A	; 10
    217c:	e0 f7       	brcc	.-8      	; 0x2176 <__stack+0x1077>
    217e:	48 2f       	mov	r20, r24
    2180:	19 cf       	rjmp	.-462    	; 0x1fb4 <__stack+0xeb5>
    2182:	ec e7       	ldi	r30, 0x7C	; 124
    2184:	f0 e0       	ldi	r31, 0x00	; 0
    2186:	11 cf       	rjmp	.-478    	; 0x1faa <__stack+0xeab>
    2188:	80 e3       	ldi	r24, 0x30	; 48
    218a:	8f 5f       	subi	r24, 0xFF	; 255
    218c:	94 56       	subi	r25, 0x64	; 100
    218e:	94 36       	cpi	r25, 0x64	; 100
    2190:	e0 f7       	brcc	.-8      	; 0x218a <__stack+0x108b>
    2192:	48 2f       	mov	r20, r24
    2194:	f3 ce       	rjmp	.-538    	; 0x1f7c <__stack+0xe7d>
    2196:	ec e7       	ldi	r30, 0x7C	; 124
    2198:	f0 e0       	ldi	r31, 0x00	; 0
    219a:	e8 ce       	rjmp	.-560    	; 0x1f6c <__stack+0xe6d>
    219c:	ec e7       	ldi	r30, 0x7C	; 124
    219e:	f0 e0       	ldi	r31, 0x00	; 0
    21a0:	cd ce       	rjmp	.-614    	; 0x1f3c <__stack+0xe3d>
    21a2:	ec e7       	ldi	r30, 0x7C	; 124
    21a4:	f0 e0       	ldi	r31, 0x00	; 0
    21a6:	b1 ce       	rjmp	.-670    	; 0x1f0a <__stack+0xe0b>
    21a8:	80 e3       	ldi	r24, 0x30	; 48
    21aa:	8f 5f       	subi	r24, 0xFF	; 255
    21ac:	9a 50       	subi	r25, 0x0A	; 10
    21ae:	9a 30       	cpi	r25, 0x0A	; 10
    21b0:	e0 f7       	brcc	.-8      	; 0x21aa <__stack+0x10ab>
    21b2:	48 2f       	mov	r20, r24
    21b4:	93 ce       	rjmp	.-730    	; 0x1edc <__stack+0xddd>
    21b6:	ec e7       	ldi	r30, 0x7C	; 124
    21b8:	f0 e0       	ldi	r31, 0x00	; 0
    21ba:	8b ce       	rjmp	.-746    	; 0x1ed2 <__stack+0xdd3>
    21bc:	80 e3       	ldi	r24, 0x30	; 48
    21be:	8f 5f       	subi	r24, 0xFF	; 255
    21c0:	94 56       	subi	r25, 0x64	; 100
    21c2:	94 36       	cpi	r25, 0x64	; 100
    21c4:	e0 f7       	brcc	.-8      	; 0x21be <__stack+0x10bf>
    21c6:	48 2f       	mov	r20, r24
    21c8:	6d ce       	rjmp	.-806    	; 0x1ea4 <__stack+0xda5>
    21ca:	ec e7       	ldi	r30, 0x7C	; 124
    21cc:	f0 e0       	ldi	r31, 0x00	; 0
    21ce:	62 ce       	rjmp	.-828    	; 0x1e94 <__stack+0xd95>
    21d0:	ec e7       	ldi	r30, 0x7C	; 124
    21d2:	f0 e0       	ldi	r31, 0x00	; 0
    21d4:	46 ce       	rjmp	.-884    	; 0x1e62 <__stack+0xd63>
    21d6:	8e e8       	ldi	r24, 0x8E	; 142
    21d8:	93 e0       	ldi	r25, 0x03	; 3
    21da:	0e 94 e1 22 	call	0x45c2	; 0x45c2 <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
    21de:	75 cf       	rjmp	.-278    	; 0x20ca <__stack+0xfcb>
    21e0:	8e e8       	ldi	r24, 0x8E	; 142
    21e2:	93 e0       	ldi	r25, 0x03	; 3
    21e4:	0e 94 14 2a 	call	0x5428	; 0x5428 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
    21e8:	19 ce       	rjmp	.-974    	; 0x1e1c <__stack+0xd1d>
    21ea:	ec e7       	ldi	r30, 0x7C	; 124
    21ec:	f0 e0       	ldi	r31, 0x00	; 0
    21ee:	c4 cd       	rjmp	.-1144   	; 0x1d78 <__stack+0xc79>
    21f0:	ec e7       	ldi	r30, 0x7C	; 124
    21f2:	f0 e0       	ldi	r31, 0x00	; 0
    21f4:	d9 cd       	rjmp	.-1102   	; 0x1da8 <__stack+0xca9>
    21f6:	ec e7       	ldi	r30, 0x7C	; 124
    21f8:	f0 e0       	ldi	r31, 0x00	; 0
    21fa:	a5 cd       	rjmp	.-1206   	; 0x1d46 <__stack+0xc47>
    21fc:	ec e7       	ldi	r30, 0x7C	; 124
    21fe:	f0 e0       	ldi	r31, 0x00	; 0
    2200:	4f cd       	rjmp	.-1378   	; 0x1ca0 <__stack+0xba1>
    2202:	80 e3       	ldi	r24, 0x30	; 48
    2204:	8f 5f       	subi	r24, 0xFF	; 255
    2206:	9a 50       	subi	r25, 0x0A	; 10
    2208:	9a 30       	cpi	r25, 0x0A	; 10
    220a:	e0 f7       	brcc	.-8      	; 0x2204 <__stack+0x1105>
    220c:	48 2f       	mov	r20, r24
    220e:	84 cd       	rjmp	.-1272   	; 0x1d18 <__stack+0xc19>
    2210:	ec e7       	ldi	r30, 0x7C	; 124
    2212:	f0 e0       	ldi	r31, 0x00	; 0
    2214:	7c cd       	rjmp	.-1288   	; 0x1d0e <__stack+0xc0f>
    2216:	80 e3       	ldi	r24, 0x30	; 48
    2218:	8f 5f       	subi	r24, 0xFF	; 255
    221a:	94 56       	subi	r25, 0x64	; 100
    221c:	94 36       	cpi	r25, 0x64	; 100
    221e:	e0 f7       	brcc	.-8      	; 0x2218 <__stack+0x1119>
    2220:	48 2f       	mov	r20, r24
    2222:	5e cd       	rjmp	.-1348   	; 0x1ce0 <__stack+0xbe1>
    2224:	ec e7       	ldi	r30, 0x7C	; 124
    2226:	f0 e0       	ldi	r31, 0x00	; 0
    2228:	53 cd       	rjmp	.-1370   	; 0x1cd0 <__stack+0xbd1>
    222a:	ec e7       	ldi	r30, 0x7C	; 124
    222c:	f0 e0       	ldi	r31, 0x00	; 0
    222e:	1f cd       	rjmp	.-1474   	; 0x1c6e <__stack+0xb6f>
    2230:	ec e7       	ldi	r30, 0x7C	; 124
    2232:	f0 e0       	ldi	r31, 0x00	; 0
    2234:	c8 cc       	rjmp	.-1648   	; 0x1bc6 <__stack+0xac7>
    2236:	80 e3       	ldi	r24, 0x30	; 48
    2238:	8f 5f       	subi	r24, 0xFF	; 255
    223a:	9a 50       	subi	r25, 0x0A	; 10
    223c:	9a 30       	cpi	r25, 0x0A	; 10
    223e:	e0 f7       	brcc	.-8      	; 0x2238 <__stack+0x1139>
    2240:	48 2f       	mov	r20, r24
    2242:	fe cc       	rjmp	.-1540   	; 0x1c40 <__stack+0xb41>
    2244:	ec e7       	ldi	r30, 0x7C	; 124
    2246:	f0 e0       	ldi	r31, 0x00	; 0
    2248:	f6 cc       	rjmp	.-1556   	; 0x1c36 <__stack+0xb37>
    224a:	80 e3       	ldi	r24, 0x30	; 48
    224c:	8f 5f       	subi	r24, 0xFF	; 255
    224e:	94 56       	subi	r25, 0x64	; 100
    2250:	94 36       	cpi	r25, 0x64	; 100
    2252:	e0 f7       	brcc	.-8      	; 0x224c <__stack+0x114d>
    2254:	48 2f       	mov	r20, r24
    2256:	d8 cc       	rjmp	.-1616   	; 0x1c08 <__stack+0xb09>
    2258:	ec e7       	ldi	r30, 0x7C	; 124
    225a:	f0 e0       	ldi	r31, 0x00	; 0
    225c:	cd cc       	rjmp	.-1638   	; 0x1bf8 <__stack+0xaf9>
    225e:	ec e7       	ldi	r30, 0x7C	; 124
    2260:	f0 e0       	ldi	r31, 0x00	; 0
    2262:	84 cc       	rjmp	.-1784   	; 0x1b6c <__stack+0xa6d>
    2264:	ec e7       	ldi	r30, 0x7C	; 124
    2266:	f0 e0       	ldi	r31, 0x00	; 0
    2268:	69 cc       	rjmp	.-1838   	; 0x1b3c <__stack+0xa3d>
    226a:	ec e7       	ldi	r30, 0x7C	; 124
    226c:	f0 e0       	ldi	r31, 0x00	; 0
    226e:	4d cc       	rjmp	.-1894   	; 0x1b0a <__stack+0xa0b>
    2270:	ec e7       	ldi	r30, 0x7C	; 124
    2272:	f0 e0       	ldi	r31, 0x00	; 0
    2274:	f7 cb       	rjmp	.-2066   	; 0x1a64 <__stack+0x965>
    2276:	80 e3       	ldi	r24, 0x30	; 48
    2278:	8f 5f       	subi	r24, 0xFF	; 255
    227a:	9a 50       	subi	r25, 0x0A	; 10
    227c:	9a 30       	cpi	r25, 0x0A	; 10
    227e:	e0 f7       	brcc	.-8      	; 0x2278 <__stack+0x1179>
    2280:	48 2f       	mov	r20, r24
    2282:	2c cc       	rjmp	.-1960   	; 0x1adc <__stack+0x9dd>
    2284:	ec e7       	ldi	r30, 0x7C	; 124
    2286:	f0 e0       	ldi	r31, 0x00	; 0
    2288:	24 cc       	rjmp	.-1976   	; 0x1ad2 <__stack+0x9d3>
    228a:	80 e3       	ldi	r24, 0x30	; 48
    228c:	8f 5f       	subi	r24, 0xFF	; 255
    228e:	94 56       	subi	r25, 0x64	; 100
    2290:	94 36       	cpi	r25, 0x64	; 100
    2292:	e0 f7       	brcc	.-8      	; 0x228c <__stack+0x118d>
    2294:	48 2f       	mov	r20, r24
    2296:	06 cc       	rjmp	.-2036   	; 0x1aa4 <__stack+0x9a5>
    2298:	ec e7       	ldi	r30, 0x7C	; 124
    229a:	f0 e0       	ldi	r31, 0x00	; 0
    229c:	fb cb       	rjmp	.-2058   	; 0x1a94 <__stack+0x995>
    229e:	ec e7       	ldi	r30, 0x7C	; 124
    22a0:	f0 e0       	ldi	r31, 0x00	; 0
    22a2:	c7 cb       	rjmp	.-2162   	; 0x1a32 <__stack+0x933>
    22a4:	ec e7       	ldi	r30, 0x7C	; 124
    22a6:	f0 e0       	ldi	r31, 0x00	; 0
    22a8:	70 cb       	rjmp	.-2336   	; 0x198a <__stack+0x88b>
    22aa:	80 e3       	ldi	r24, 0x30	; 48
    22ac:	8f 5f       	subi	r24, 0xFF	; 255
    22ae:	9a 50       	subi	r25, 0x0A	; 10
    22b0:	9a 30       	cpi	r25, 0x0A	; 10
    22b2:	e0 f7       	brcc	.-8      	; 0x22ac <__stack+0x11ad>
    22b4:	48 2f       	mov	r20, r24
    22b6:	a6 cb       	rjmp	.-2228   	; 0x1a04 <__stack+0x905>
    22b8:	ec e7       	ldi	r30, 0x7C	; 124
    22ba:	f0 e0       	ldi	r31, 0x00	; 0
    22bc:	9e cb       	rjmp	.-2244   	; 0x19fa <__stack+0x8fb>
    22be:	80 e3       	ldi	r24, 0x30	; 48
    22c0:	8f 5f       	subi	r24, 0xFF	; 255
    22c2:	94 56       	subi	r25, 0x64	; 100
    22c4:	94 36       	cpi	r25, 0x64	; 100
    22c6:	e0 f7       	brcc	.-8      	; 0x22c0 <__stack+0x11c1>
    22c8:	48 2f       	mov	r20, r24
    22ca:	80 cb       	rjmp	.-2304   	; 0x19cc <__stack+0x8cd>
    22cc:	ec e7       	ldi	r30, 0x7C	; 124
    22ce:	f0 e0       	ldi	r31, 0x00	; 0
    22d0:	75 cb       	rjmp	.-2326   	; 0x19bc <__stack+0x8bd>
    22d2:	ec e7       	ldi	r30, 0x7C	; 124
    22d4:	f0 e0       	ldi	r31, 0x00	; 0
    22d6:	2c cb       	rjmp	.-2472   	; 0x1930 <__stack+0x831>
    22d8:	ec e7       	ldi	r30, 0x7C	; 124
    22da:	f0 e0       	ldi	r31, 0x00	; 0
    22dc:	11 cb       	rjmp	.-2526   	; 0x1900 <__stack+0x801>
    22de:	ec e7       	ldi	r30, 0x7C	; 124
    22e0:	f0 e0       	ldi	r31, 0x00	; 0
    22e2:	f5 ca       	rjmp	.-2582   	; 0x18ce <__stack+0x7cf>
    22e4:	ec e7       	ldi	r30, 0x7C	; 124
    22e6:	f0 e0       	ldi	r31, 0x00	; 0
    22e8:	9f ca       	rjmp	.-2754   	; 0x1828 <__stack+0x729>
    22ea:	80 e3       	ldi	r24, 0x30	; 48
    22ec:	8f 5f       	subi	r24, 0xFF	; 255
    22ee:	9a 50       	subi	r25, 0x0A	; 10
    22f0:	9a 30       	cpi	r25, 0x0A	; 10
    22f2:	e0 f7       	brcc	.-8      	; 0x22ec <__stack+0x11ed>
    22f4:	48 2f       	mov	r20, r24
    22f6:	d4 ca       	rjmp	.-2648   	; 0x18a0 <__stack+0x7a1>
    22f8:	ec e7       	ldi	r30, 0x7C	; 124
    22fa:	f0 e0       	ldi	r31, 0x00	; 0
    22fc:	cc ca       	rjmp	.-2664   	; 0x1896 <__stack+0x797>
    22fe:	80 e3       	ldi	r24, 0x30	; 48
    2300:	8f 5f       	subi	r24, 0xFF	; 255
    2302:	94 56       	subi	r25, 0x64	; 100
    2304:	94 36       	cpi	r25, 0x64	; 100
    2306:	e0 f7       	brcc	.-8      	; 0x2300 <__stack+0x1201>
    2308:	48 2f       	mov	r20, r24
    230a:	ae ca       	rjmp	.-2724   	; 0x1868 <__stack+0x769>
    230c:	ec e7       	ldi	r30, 0x7C	; 124
    230e:	f0 e0       	ldi	r31, 0x00	; 0
    2310:	a3 ca       	rjmp	.-2746   	; 0x1858 <__stack+0x759>
    2312:	ec e7       	ldi	r30, 0x7C	; 124
    2314:	f0 e0       	ldi	r31, 0x00	; 0
    2316:	6f ca       	rjmp	.-2850   	; 0x17f6 <__stack+0x6f7>
    2318:	ec e7       	ldi	r30, 0x7C	; 124
    231a:	f0 e0       	ldi	r31, 0x00	; 0
    231c:	18 ca       	rjmp	.-3024   	; 0x174e <__stack+0x64f>
    231e:	80 e3       	ldi	r24, 0x30	; 48
    2320:	8f 5f       	subi	r24, 0xFF	; 255
    2322:	9a 50       	subi	r25, 0x0A	; 10
    2324:	9a 30       	cpi	r25, 0x0A	; 10
    2326:	e0 f7       	brcc	.-8      	; 0x2320 <__stack+0x1221>
    2328:	48 2f       	mov	r20, r24
    232a:	4e ca       	rjmp	.-2916   	; 0x17c8 <__stack+0x6c9>
    232c:	ec e7       	ldi	r30, 0x7C	; 124
    232e:	f0 e0       	ldi	r31, 0x00	; 0
    2330:	46 ca       	rjmp	.-2932   	; 0x17be <__stack+0x6bf>
    2332:	80 e3       	ldi	r24, 0x30	; 48
    2334:	8f 5f       	subi	r24, 0xFF	; 255
    2336:	94 56       	subi	r25, 0x64	; 100
    2338:	94 36       	cpi	r25, 0x64	; 100
    233a:	e0 f7       	brcc	.-8      	; 0x2334 <__stack+0x1235>
    233c:	48 2f       	mov	r20, r24
    233e:	28 ca       	rjmp	.-2992   	; 0x1790 <__stack+0x691>
    2340:	ec e7       	ldi	r30, 0x7C	; 124
    2342:	f0 e0       	ldi	r31, 0x00	; 0
    2344:	1d ca       	rjmp	.-3014   	; 0x1780 <__stack+0x681>
    2346:	ec e7       	ldi	r30, 0x7C	; 124
    2348:	f0 e0       	ldi	r31, 0x00	; 0
    234a:	d4 c9       	rjmp	.-3160   	; 0x16f4 <__stack+0x5f5>
    234c:	ec e7       	ldi	r30, 0x7C	; 124
    234e:	f0 e0       	ldi	r31, 0x00	; 0
    2350:	b9 c9       	rjmp	.-3214   	; 0x16c4 <__stack+0x5c5>
    2352:	ec e7       	ldi	r30, 0x7C	; 124
    2354:	f0 e0       	ldi	r31, 0x00	; 0
    2356:	9d c9       	rjmp	.-3270   	; 0x1692 <__stack+0x593>
    2358:	ec e7       	ldi	r30, 0x7C	; 124
    235a:	f0 e0       	ldi	r31, 0x00	; 0
    235c:	47 c9       	rjmp	.-3442   	; 0x15ec <__stack+0x4ed>
    235e:	80 e3       	ldi	r24, 0x30	; 48
    2360:	8f 5f       	subi	r24, 0xFF	; 255
    2362:	9a 50       	subi	r25, 0x0A	; 10
    2364:	9a 30       	cpi	r25, 0x0A	; 10
    2366:	e0 f7       	brcc	.-8      	; 0x2360 <__stack+0x1261>
    2368:	48 2f       	mov	r20, r24
    236a:	7c c9       	rjmp	.-3336   	; 0x1664 <__stack+0x565>
    236c:	ec e7       	ldi	r30, 0x7C	; 124
    236e:	f0 e0       	ldi	r31, 0x00	; 0
    2370:	74 c9       	rjmp	.-3352   	; 0x165a <__stack+0x55b>
    2372:	80 e3       	ldi	r24, 0x30	; 48
    2374:	8f 5f       	subi	r24, 0xFF	; 255
    2376:	94 56       	subi	r25, 0x64	; 100
    2378:	94 36       	cpi	r25, 0x64	; 100
    237a:	e0 f7       	brcc	.-8      	; 0x2374 <__stack+0x1275>
    237c:	48 2f       	mov	r20, r24
    237e:	56 c9       	rjmp	.-3412   	; 0x162c <__stack+0x52d>
    2380:	ec e7       	ldi	r30, 0x7C	; 124
    2382:	f0 e0       	ldi	r31, 0x00	; 0
    2384:	4b c9       	rjmp	.-3434   	; 0x161c <__stack+0x51d>
    2386:	ec e7       	ldi	r30, 0x7C	; 124
    2388:	f0 e0       	ldi	r31, 0x00	; 0
    238a:	17 c9       	rjmp	.-3538   	; 0x15ba <__stack+0x4bb>
    238c:	ec e7       	ldi	r30, 0x7C	; 124
    238e:	f0 e0       	ldi	r31, 0x00	; 0
    2390:	c0 c8       	rjmp	.-3712   	; 0x1512 <__stack+0x413>
    2392:	80 e3       	ldi	r24, 0x30	; 48
    2394:	8f 5f       	subi	r24, 0xFF	; 255
    2396:	9a 50       	subi	r25, 0x0A	; 10
    2398:	9a 30       	cpi	r25, 0x0A	; 10
    239a:	e0 f7       	brcc	.-8      	; 0x2394 <__stack+0x1295>
    239c:	48 2f       	mov	r20, r24
    239e:	f6 c8       	rjmp	.-3604   	; 0x158c <__stack+0x48d>
    23a0:	ec e7       	ldi	r30, 0x7C	; 124
    23a2:	f0 e0       	ldi	r31, 0x00	; 0
    23a4:	ee c8       	rjmp	.-3620   	; 0x1582 <__stack+0x483>
    23a6:	80 e3       	ldi	r24, 0x30	; 48
    23a8:	8f 5f       	subi	r24, 0xFF	; 255
    23aa:	94 56       	subi	r25, 0x64	; 100
    23ac:	94 36       	cpi	r25, 0x64	; 100
    23ae:	e0 f7       	brcc	.-8      	; 0x23a8 <__stack+0x12a9>
    23b0:	48 2f       	mov	r20, r24
    23b2:	d0 c8       	rjmp	.-3680   	; 0x1554 <__stack+0x455>
    23b4:	ec e7       	ldi	r30, 0x7C	; 124
    23b6:	f0 e0       	ldi	r31, 0x00	; 0
    23b8:	c5 c8       	rjmp	.-3702   	; 0x1544 <__stack+0x445>
    23ba:	ec e7       	ldi	r30, 0x7C	; 124
    23bc:	f0 e0       	ldi	r31, 0x00	; 0
    23be:	77 c8       	rjmp	.-3858   	; 0x14ae <__stack+0x3af>
    23c0:	ec e7       	ldi	r30, 0x7C	; 124
    23c2:	f0 e0       	ldi	r31, 0x00	; 0
    23c4:	5c c8       	rjmp	.-3912   	; 0x147e <__stack+0x37f>
    23c6:	ec e7       	ldi	r30, 0x7C	; 124
    23c8:	f0 e0       	ldi	r31, 0x00	; 0
    23ca:	40 c8       	rjmp	.-3968   	; 0x144c <__stack+0x34d>
    23cc:	ec e7       	ldi	r30, 0x7C	; 124
    23ce:	f0 e0       	ldi	r31, 0x00	; 0
    23d0:	0c 94 d1 09 	jmp	0x13a2	; 0x13a2 <__stack+0x2a3>
    23d4:	80 e3       	ldi	r24, 0x30	; 48
    23d6:	8f 5f       	subi	r24, 0xFF	; 255
    23d8:	9a 50       	subi	r25, 0x0A	; 10
    23da:	9a 30       	cpi	r25, 0x0A	; 10
    23dc:	e0 f7       	brcc	.-8      	; 0x23d6 <__stack+0x12d7>
    23de:	48 2f       	mov	r20, r24
    23e0:	1e c8       	rjmp	.-4036   	; 0x141e <__stack+0x31f>
    23e2:	ec e7       	ldi	r30, 0x7C	; 124
    23e4:	f0 e0       	ldi	r31, 0x00	; 0
    23e6:	16 c8       	rjmp	.-4052   	; 0x1414 <__stack+0x315>
    23e8:	80 e3       	ldi	r24, 0x30	; 48
    23ea:	8f 5f       	subi	r24, 0xFF	; 255
    23ec:	94 56       	subi	r25, 0x64	; 100
    23ee:	94 36       	cpi	r25, 0x64	; 100
    23f0:	e0 f7       	brcc	.-8      	; 0x23ea <__stack+0x12eb>
    23f2:	48 2f       	mov	r20, r24
    23f4:	0c 94 f3 09 	jmp	0x13e6	; 0x13e6 <__stack+0x2e7>
    23f8:	ec e7       	ldi	r30, 0x7C	; 124
    23fa:	f0 e0       	ldi	r31, 0x00	; 0
    23fc:	0c 94 ea 09 	jmp	0x13d4	; 0x13d4 <__stack+0x2d5>
    2400:	ec e7       	ldi	r30, 0x7C	; 124
    2402:	f0 e0       	ldi	r31, 0x00	; 0
    2404:	0c 94 b7 09 	jmp	0x136e	; 0x136e <__stack+0x26f>
    2408:	ec e7       	ldi	r30, 0x7C	; 124
    240a:	f0 e0       	ldi	r31, 0x00	; 0
    240c:	0c 94 5e 09 	jmp	0x12bc	; 0x12bc <__stack+0x1bd>
    2410:	80 e3       	ldi	r24, 0x30	; 48
    2412:	8f 5f       	subi	r24, 0xFF	; 255
    2414:	9a 50       	subi	r25, 0x0A	; 10
    2416:	9a 30       	cpi	r25, 0x0A	; 10
    2418:	e0 f7       	brcc	.-8      	; 0x2412 <__stack+0x1313>
    241a:	48 2f       	mov	r20, r24
    241c:	0c 94 9f 09 	jmp	0x133e	; 0x133e <__stack+0x23f>
    2420:	ec e7       	ldi	r30, 0x7C	; 124
    2422:	f0 e0       	ldi	r31, 0x00	; 0
    2424:	0c 94 99 09 	jmp	0x1332	; 0x1332 <__stack+0x233>
    2428:	80 e3       	ldi	r24, 0x30	; 48
    242a:	8f 5f       	subi	r24, 0xFF	; 255
    242c:	94 56       	subi	r25, 0x64	; 100
    242e:	94 36       	cpi	r25, 0x64	; 100
    2430:	e0 f7       	brcc	.-8      	; 0x242a <__stack+0x132b>
    2432:	48 2f       	mov	r20, r24
    2434:	0c 94 81 09 	jmp	0x1302	; 0x1302 <__stack+0x203>
    2438:	ec e7       	ldi	r30, 0x7C	; 124
    243a:	f0 e0       	ldi	r31, 0x00	; 0
    243c:	0c 94 78 09 	jmp	0x12f0	; 0x12f0 <__stack+0x1f1>
    2440:	ec e7       	ldi	r30, 0x7C	; 124
    2442:	f0 e0       	ldi	r31, 0x00	; 0
    2444:	0c 94 4a 07 	jmp	0xe94	; 0xe94 <main+0xf4>
    2448:	ec e7       	ldi	r30, 0x7C	; 124
    244a:	f0 e0       	ldi	r31, 0x00	; 0
    244c:	0c 94 30 07 	jmp	0xe60	; 0xe60 <main+0xc0>
    2450:	ec e7       	ldi	r30, 0x7C	; 124
    2452:	f0 e0       	ldi	r31, 0x00	; 0
    2454:	0c 94 16 07 	jmp	0xe2c	; 0xe2c <main+0x8c>
    2458:	ec e7       	ldi	r30, 0x7C	; 124
    245a:	f0 e0       	ldi	r31, 0x00	; 0
    245c:	0c 94 fc 06 	jmp	0xdf8	; 0xdf8 <main+0x58>
    2460:	8e e8       	ldi	r24, 0x8E	; 142
    2462:	93 e0       	ldi	r25, 0x03	; 3
    2464:	0e 94 14 2a 	call	0x5428	; 0x5428 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
    2468:	0c 94 e4 06 	jmp	0xdc8	; 0xdc8 <main+0x28>
    246c:	8e e8       	ldi	r24, 0x8E	; 142
    246e:	93 e0       	ldi	r25, 0x03	; 3
    2470:	0e 94 e1 22 	call	0x45c2	; 0x45c2 <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>
    2474:	b4 cc       	rjmp	.-1688   	; 0x1dde <__stack+0xcdf>
    2476:	60 ee       	ldi	r22, 0xE0	; 224
    2478:	71 e0       	ldi	r23, 0x01	; 1
    247a:	88 e7       	ldi	r24, 0x78	; 120
    247c:	93 e0       	ldi	r25, 0x03	; 3
    247e:	0e 94 40 17 	call	0x2e80	; 0x2e80 <_ZN6System7OStream5printEPKc>
    2482:	80 e0       	ldi	r24, 0x00	; 0
    2484:	90 e0       	ldi	r25, 0x00	; 0
    2486:	0c 94 bf 12 	jmp	0x257e	; 0x257e <exit>

0000248a <_ZN6System11Init_SystemC1Ev>:
    248a:	cf 93       	push	r28
    248c:	df 93       	push	r29
    248e:	cd b7       	in	r28, 0x3d	; 61
    2490:	de b7       	in	r29, 0x3e	; 62
    2492:	22 97       	sbiw	r28, 0x02	; 2
    2494:	0f b6       	in	r0, 0x3f	; 63
    2496:	f8 94       	cli
    2498:	de bf       	out	0x3e, r29	; 62
    249a:	0f be       	out	0x3f, r0	; 63
    249c:	cd bf       	out	0x3d, r28	; 61
    249e:	0e 94 84 18 	call	0x3108	; 0x3108 <_ZN6System4AVR84initEv>
    24a2:	e0 91 ea 01 	lds	r30, 0x01EA
    24a6:	f0 91 eb 01 	lds	r31, 0x01EB
    24aa:	84 a9       	ldd	r24, Z+52	; 0x34
    24ac:	88 23       	and	r24, r24
    24ae:	21 f4       	brne	.+8      	; 0x24b8 <_ZN6System11Init_SystemC1Ev+0x2e>
    24b0:	8a e7       	ldi	r24, 0x7A	; 122
    24b2:	90 e0       	ldi	r25, 0x00	; 0
    24b4:	93 ab       	std	Z+51, r25	; 0x33
    24b6:	82 ab       	std	Z+50, r24	; 0x32
    24b8:	6c ef       	ldi	r22, 0xFC	; 252
    24ba:	78 e0       	ldi	r23, 0x08	; 8
    24bc:	ce 01       	movw	r24, r28
    24be:	01 96       	adiw	r24, 0x01	; 1
    24c0:	0e 94 ec 32 	call	0x65d8	; 0x65d8 <_ZN6System8AVR8_MMU5allocEj>
    24c4:	69 81       	ldd	r22, Y+1	; 0x01
    24c6:	7a 81       	ldd	r23, Y+2	; 0x02
    24c8:	4c ef       	ldi	r20, 0xFC	; 252
    24ca:	58 e0       	ldi	r21, 0x08	; 8
    24cc:	8f e9       	ldi	r24, 0x9F	; 159
    24ce:	93 e0       	ldi	r25, 0x03	; 3
    24d0:	0e 94 c1 12 	call	0x2582	; 0x2582 <_ZN6System4Heap4freeEPvj>
    24d4:	0e 94 b9 18 	call	0x3172	; 0x3172 <_ZN6System9ATMega1284initEv>
    24d8:	0e 94 d9 18 	call	0x31b2	; 0x31b2 <_ZN6System6System4initEv>
    24dc:	22 96       	adiw	r28, 0x02	; 2
    24de:	0f b6       	in	r0, 0x3f	; 63
    24e0:	f8 94       	cli
    24e2:	de bf       	out	0x3e, r29	; 62
    24e4:	0f be       	out	0x3f, r0	; 63
    24e6:	cd bf       	out	0x3d, r28	; 61
    24e8:	df 91       	pop	r29
    24ea:	cf 91       	pop	r28
    24ec:	08 95       	ret

000024ee <_Z41__static_initialization_and_destruction_0ii>:
    24ee:	6f 5f       	subi	r22, 0xFF	; 255
    24f0:	7f 4f       	sbci	r23, 0xFF	; 255
    24f2:	09 f0       	breq	.+2      	; 0x24f6 <_Z41__static_initialization_and_destruction_0ii+0x8>
    24f4:	08 95       	ret
    24f6:	01 97       	sbiw	r24, 0x01	; 1
    24f8:	e9 f7       	brne	.-6      	; 0x24f4 <_Z41__static_initialization_and_destruction_0ii+0x6>
    24fa:	8e e9       	ldi	r24, 0x9E	; 158
    24fc:	93 e0       	ldi	r25, 0x03	; 3
    24fe:	0e 94 45 12 	call	0x248a	; 0x248a <_ZN6System11Init_SystemC1Ev>
    2502:	08 95       	ret

00002504 <_GLOBAL__I__ZN6System11init_systemE>:
    2504:	6f ef       	ldi	r22, 0xFF	; 255
    2506:	7f ef       	ldi	r23, 0xFF	; 255
    2508:	81 e0       	ldi	r24, 0x01	; 1
    250a:	90 e0       	ldi	r25, 0x00	; 0
    250c:	0e 94 77 12 	call	0x24ee	; 0x24ee <_Z41__static_initialization_and_destruction_0ii>
    2510:	08 95       	ret

00002512 <_exit>:
    2512:	0e 94 d8 25 	call	0x4bb0	; 0x4bb0 <_ZN6System6Thread4exitEi>
    2516:	ff cf       	rjmp	.-2      	; 0x2516 <_exit+0x4>

00002518 <__cxa_pure_virtual>:
    2518:	83 e0       	ldi	r24, 0x03	; 3
    251a:	80 93 8b 04 	sts	0x048B, r24
    251e:	85 b7       	in	r24, 0x35	; 53
    2520:	83 7f       	andi	r24, 0xF3	; 243
    2522:	85 bf       	out	0x35, r24	; 53
    2524:	85 b7       	in	r24, 0x35	; 53
    2526:	80 61       	ori	r24, 0x10	; 16
    2528:	85 bf       	out	0x35, r24	; 53
    252a:	88 95       	sleep
    252c:	08 95       	ret

0000252e <_Z41__static_initialization_and_destruction_0ii>:
    252e:	6f 5f       	subi	r22, 0xFF	; 255
    2530:	7f 4f       	sbci	r23, 0xFF	; 255
    2532:	09 f0       	breq	.+2      	; 0x2536 <_Z41__static_initialization_and_destruction_0ii+0x8>
    2534:	08 95       	ret
    2536:	01 97       	sbiw	r24, 0x01	; 1
    2538:	e9 f7       	brne	.-6      	; 0x2534 <_Z41__static_initialization_and_destruction_0ii+0x6>
    253a:	8a e0       	ldi	r24, 0x0A	; 10
    253c:	90 e0       	ldi	r25, 0x00	; 0
    253e:	90 93 aa 03 	sts	0x03AA, r25
    2542:	80 93 a9 03 	sts	0x03A9, r24
    2546:	90 93 a8 03 	sts	0x03A8, r25
    254a:	80 93 a7 03 	sts	0x03A7, r24
    254e:	10 92 a0 03 	sts	0x03A0, r1
    2552:	10 92 9f 03 	sts	0x039F, r1
    2556:	10 92 a2 03 	sts	0x03A2, r1
    255a:	10 92 a1 03 	sts	0x03A1, r1
    255e:	10 92 a4 03 	sts	0x03A4, r1
    2562:	10 92 a3 03 	sts	0x03A3, r1
    2566:	10 92 a6 03 	sts	0x03A6, r1
    256a:	10 92 a5 03 	sts	0x03A5, r1
    256e:	08 95       	ret

00002570 <_GLOBAL__I__exit>:
    2570:	6f ef       	ldi	r22, 0xFF	; 255
    2572:	7f ef       	ldi	r23, 0xFF	; 255
    2574:	81 e0       	ldi	r24, 0x01	; 1
    2576:	90 e0       	ldi	r25, 0x00	; 0
    2578:	0e 94 97 12 	call	0x252e	; 0x252e <_Z41__static_initialization_and_destruction_0ii>
    257c:	08 95       	ret

0000257e <exit>:
    257e:	0c 94 89 12 	jmp	0x2512	; 0x2512 <_exit>

00002582 <_ZN6System4Heap4freeEPvj>:
    2582:	cf 92       	push	r12
    2584:	df 92       	push	r13
    2586:	ef 92       	push	r14
    2588:	ff 92       	push	r15
    258a:	0f 93       	push	r16
    258c:	1f 93       	push	r17
    258e:	cf 93       	push	r28
    2590:	df 93       	push	r29
    2592:	7c 01       	movw	r14, r24
    2594:	6b 01       	movw	r12, r22
    2596:	61 15       	cp	r22, r1
    2598:	71 05       	cpc	r23, r1
    259a:	09 f4       	brne	.+2      	; 0x259e <_ZN6System4Heap4freeEPvj+0x1c>
    259c:	f6 c0       	rjmp	.+492    	; 0x278a <_ZN6System4Heap4freeEPvj+0x208>
    259e:	48 30       	cpi	r20, 0x08	; 8
    25a0:	51 05       	cpc	r21, r1
    25a2:	08 f4       	brcc	.+2      	; 0x25a6 <_ZN6System4Heap4freeEPvj+0x24>
    25a4:	f2 c0       	rjmp	.+484    	; 0x278a <_ZN6System4Heap4freeEPvj+0x208>
    25a6:	db 01       	movw	r26, r22
    25a8:	6d 93       	st	X+, r22
    25aa:	7c 93       	st	X, r23
    25ac:	eb 01       	movw	r28, r22
    25ae:	5b 83       	std	Y+3, r21	; 0x03
    25b0:	4a 83       	std	Y+2, r20	; 0x02
    25b2:	1d 82       	std	Y+5, r1	; 0x05
    25b4:	1c 82       	std	Y+4, r1	; 0x04
    25b6:	1f 82       	std	Y+7, r1	; 0x07
    25b8:	1e 82       	std	Y+6, r1	; 0x06
    25ba:	fc 01       	movw	r30, r24
    25bc:	86 81       	ldd	r24, Z+6	; 0x06
    25be:	97 81       	ldd	r25, Z+7	; 0x07
    25c0:	84 0f       	add	r24, r20
    25c2:	95 1f       	adc	r25, r21
    25c4:	97 83       	std	Z+7, r25	; 0x07
    25c6:	86 83       	std	Z+6, r24	; 0x06
    25c8:	4a 81       	ldd	r20, Y+2	; 0x02
    25ca:	5b 81       	ldd	r21, Y+3	; 0x03
    25cc:	9b 01       	movw	r18, r22
    25ce:	24 0f       	add	r18, r20
    25d0:	35 1f       	adc	r19, r21
    25d2:	62 81       	ldd	r22, Z+2	; 0x02
    25d4:	73 81       	ldd	r23, Z+3	; 0x03
    25d6:	61 15       	cp	r22, r1
    25d8:	71 05       	cpc	r23, r1
    25da:	09 f4       	brne	.+2      	; 0x25de <_ZN6System4Heap4freeEPvj+0x5c>
    25dc:	6d c0       	rjmp	.+218    	; 0x26b8 <_ZN6System4Heap4freeEPvj+0x136>
    25de:	eb 01       	movw	r28, r22
    25e0:	88 81       	ld	r24, Y
    25e2:	99 81       	ldd	r25, Y+1	; 0x01
    25e4:	28 17       	cp	r18, r24
    25e6:	39 07       	cpc	r19, r25
    25e8:	09 f4       	brne	.+2      	; 0x25ec <_ZN6System4Heap4freeEPvj+0x6a>
    25ea:	66 c0       	rjmp	.+204    	; 0x26b8 <_ZN6System4Heap4freeEPvj+0x136>
    25ec:	fb 01       	movw	r30, r22
    25ee:	06 80       	ldd	r0, Z+6	; 0x06
    25f0:	f7 81       	ldd	r31, Z+7	; 0x07
    25f2:	e0 2d       	mov	r30, r0
    25f4:	30 97       	sbiw	r30, 0x00	; 0
    25f6:	29 f0       	breq	.+10     	; 0x2602 <_ZN6System4Heap4freeEPvj+0x80>
    25f8:	80 81       	ld	r24, Z
    25fa:	91 81       	ldd	r25, Z+1	; 0x01
    25fc:	28 17       	cp	r18, r24
    25fe:	39 07       	cpc	r19, r25
    2600:	b1 f7       	brne	.-20     	; 0x25ee <_ZN6System4Heap4freeEPvj+0x6c>
    2602:	d6 01       	movw	r26, r12
    2604:	0d 91       	ld	r16, X+
    2606:	1c 91       	ld	r17, X
    2608:	61 15       	cp	r22, r1
    260a:	71 05       	cpc	r23, r1
    260c:	09 f4       	brne	.+2      	; 0x2610 <_ZN6System4Heap4freeEPvj+0x8e>
    260e:	5c c0       	rjmp	.+184    	; 0x26c8 <_ZN6System4Heap4freeEPvj+0x146>
    2610:	db 01       	movw	r26, r22
    2612:	8d 91       	ld	r24, X+
    2614:	9c 91       	ld	r25, X
    2616:	11 97       	sbiw	r26, 0x01	; 1
    2618:	eb 01       	movw	r28, r22
    261a:	2a 81       	ldd	r18, Y+2	; 0x02
    261c:	3b 81       	ldd	r19, Y+3	; 0x03
    261e:	82 0f       	add	r24, r18
    2620:	93 1f       	adc	r25, r19
    2622:	08 17       	cp	r16, r24
    2624:	19 07       	cpc	r17, r25
    2626:	09 f4       	brne	.+2      	; 0x262a <_ZN6System4Heap4freeEPvj+0xa8>
    2628:	4f c0       	rjmp	.+158    	; 0x26c8 <_ZN6System4Heap4freeEPvj+0x146>
    262a:	ed 01       	movw	r28, r26
    262c:	ae 81       	ldd	r26, Y+6	; 0x06
    262e:	bf 81       	ldd	r27, Y+7	; 0x07
    2630:	10 97       	sbiw	r26, 0x00	; 0
    2632:	59 f0       	breq	.+22     	; 0x264a <_ZN6System4Heap4freeEPvj+0xc8>
    2634:	ed 01       	movw	r28, r26
    2636:	8a 81       	ldd	r24, Y+2	; 0x02
    2638:	9b 81       	ldd	r25, Y+3	; 0x03
    263a:	2d 91       	ld	r18, X+
    263c:	3c 91       	ld	r19, X
    263e:	11 97       	sbiw	r26, 0x01	; 1
    2640:	82 0f       	add	r24, r18
    2642:	93 1f       	adc	r25, r19
    2644:	08 17       	cp	r16, r24
    2646:	19 07       	cpc	r17, r25
    2648:	81 f7       	brne	.-32     	; 0x262a <_ZN6System4Heap4freeEPvj+0xa8>
    264a:	30 97       	sbiw	r30, 0x00	; 0
    264c:	09 f4       	brne	.+2      	; 0x2650 <_ZN6System4Heap4freeEPvj+0xce>
    264e:	3f c0       	rjmp	.+126    	; 0x26ce <_ZN6System4Heap4freeEPvj+0x14c>
    2650:	82 81       	ldd	r24, Z+2	; 0x02
    2652:	93 81       	ldd	r25, Z+3	; 0x03
    2654:	84 0f       	add	r24, r20
    2656:	95 1f       	adc	r25, r21
    2658:	e6 01       	movw	r28, r12
    265a:	9b 83       	std	Y+3, r25	; 0x03
    265c:	8a 83       	std	Y+2, r24	; 0x02
    265e:	e7 01       	movw	r28, r14
    2660:	48 81       	ld	r20, Y
    2662:	59 81       	ldd	r21, Y+1	; 0x01
    2664:	41 30       	cpi	r20, 0x01	; 1
    2666:	51 05       	cpc	r21, r1
    2668:	09 f4       	brne	.+2      	; 0x266c <_ZN6System4Heap4freeEPvj+0xea>
    266a:	47 c0       	rjmp	.+142    	; 0x26fa <_ZN6System4Heap4freeEPvj+0x178>
    266c:	84 81       	ldd	r24, Z+4	; 0x04
    266e:	95 81       	ldd	r25, Z+5	; 0x05
    2670:	00 97       	sbiw	r24, 0x00	; 0
    2672:	09 f4       	brne	.+2      	; 0x2676 <_ZN6System4Heap4freeEPvj+0xf4>
    2674:	49 c0       	rjmp	.+146    	; 0x2708 <_ZN6System4Heap4freeEPvj+0x186>
    2676:	26 81       	ldd	r18, Z+6	; 0x06
    2678:	37 81       	ldd	r19, Z+7	; 0x07
    267a:	21 15       	cp	r18, r1
    267c:	31 05       	cpc	r19, r1
    267e:	09 f4       	brne	.+2      	; 0x2682 <_ZN6System4Heap4freeEPvj+0x100>
    2680:	56 c0       	rjmp	.+172    	; 0x272e <_ZN6System4Heap4freeEPvj+0x1ac>
    2682:	ec 01       	movw	r28, r24
    2684:	3f 83       	std	Y+7, r19	; 0x07
    2686:	2e 83       	std	Y+6, r18	; 0x06
    2688:	06 80       	ldd	r0, Z+6	; 0x06
    268a:	f7 81       	ldd	r31, Z+7	; 0x07
    268c:	e0 2d       	mov	r30, r0
    268e:	95 83       	std	Z+5, r25	; 0x05
    2690:	84 83       	std	Z+4, r24	; 0x04
    2692:	41 50       	subi	r20, 0x01	; 1
    2694:	50 40       	sbci	r21, 0x00	; 0
    2696:	f7 01       	movw	r30, r14
    2698:	51 83       	std	Z+1, r21	; 0x01
    269a:	40 83       	st	Z, r20
    269c:	10 97       	sbiw	r26, 0x00	; 0
    269e:	09 f4       	brne	.+2      	; 0x26a2 <_ZN6System4Heap4freeEPvj+0x120>
    26a0:	74 c0       	rjmp	.+232    	; 0x278a <_ZN6System4Heap4freeEPvj+0x208>
    26a2:	e6 01       	movw	r28, r12
    26a4:	4a 81       	ldd	r20, Y+2	; 0x02
    26a6:	5b 81       	ldd	r21, Y+3	; 0x03
    26a8:	fd 01       	movw	r30, r26
    26aa:	82 81       	ldd	r24, Z+2	; 0x02
    26ac:	93 81       	ldd	r25, Z+3	; 0x03
    26ae:	84 0f       	add	r24, r20
    26b0:	95 1f       	adc	r25, r21
    26b2:	93 83       	std	Z+3, r25	; 0x03
    26b4:	82 83       	std	Z+2, r24	; 0x02
    26b6:	69 c0       	rjmp	.+210    	; 0x278a <_ZN6System4Heap4freeEPvj+0x208>
    26b8:	fb 01       	movw	r30, r22
    26ba:	d6 01       	movw	r26, r12
    26bc:	0d 91       	ld	r16, X+
    26be:	1c 91       	ld	r17, X
    26c0:	61 15       	cp	r22, r1
    26c2:	71 05       	cpc	r23, r1
    26c4:	09 f0       	breq	.+2      	; 0x26c8 <_ZN6System4Heap4freeEPvj+0x146>
    26c6:	a4 cf       	rjmp	.-184    	; 0x2610 <_ZN6System4Heap4freeEPvj+0x8e>
    26c8:	db 01       	movw	r26, r22
    26ca:	30 97       	sbiw	r30, 0x00	; 0
    26cc:	09 f6       	brne	.-126    	; 0x2650 <_ZN6System4Heap4freeEPvj+0xce>
    26ce:	10 97       	sbiw	r26, 0x00	; 0
    26d0:	59 f7       	brne	.-42     	; 0x26a8 <_ZN6System4Heap4freeEPvj+0x126>
    26d2:	f7 01       	movw	r30, r14
    26d4:	80 81       	ld	r24, Z
    26d6:	91 81       	ldd	r25, Z+1	; 0x01
    26d8:	00 97       	sbiw	r24, 0x00	; 0
    26da:	09 f0       	breq	.+2      	; 0x26de <_ZN6System4Heap4freeEPvj+0x15c>
    26dc:	46 c0       	rjmp	.+140    	; 0x276a <_ZN6System4Heap4freeEPvj+0x1e8>
    26de:	e6 01       	movw	r28, r12
    26e0:	1d 82       	std	Y+5, r1	; 0x05
    26e2:	1c 82       	std	Y+4, r1	; 0x04
    26e4:	1f 82       	std	Y+7, r1	; 0x07
    26e6:	1e 82       	std	Y+6, r1	; 0x06
    26e8:	d3 82       	std	Z+3, r13	; 0x03
    26ea:	c2 82       	std	Z+2, r12	; 0x02
    26ec:	d5 82       	std	Z+5, r13	; 0x05
    26ee:	c4 82       	std	Z+4, r12	; 0x04
    26f0:	81 e0       	ldi	r24, 0x01	; 1
    26f2:	90 e0       	ldi	r25, 0x00	; 0
    26f4:	91 83       	std	Z+1, r25	; 0x01
    26f6:	80 83       	st	Z, r24
    26f8:	48 c0       	rjmp	.+144    	; 0x278a <_ZN6System4Heap4freeEPvj+0x208>
    26fa:	1b 82       	std	Y+3, r1	; 0x03
    26fc:	1a 82       	std	Y+2, r1	; 0x02
    26fe:	1d 82       	std	Y+5, r1	; 0x05
    2700:	1c 82       	std	Y+4, r1	; 0x04
    2702:	19 82       	std	Y+1, r1	; 0x01
    2704:	18 82       	st	Y, r1
    2706:	ca cf       	rjmp	.-108    	; 0x269c <_ZN6System4Heap4freeEPvj+0x11a>
    2708:	41 15       	cp	r20, r1
    270a:	51 05       	cpc	r21, r1
    270c:	39 f2       	breq	.-114    	; 0x269c <_ZN6System4Heap4freeEPvj+0x11a>
    270e:	41 30       	cpi	r20, 0x01	; 1
    2710:	51 05       	cpc	r21, r1
    2712:	19 f1       	breq	.+70     	; 0x275a <_ZN6System4Heap4freeEPvj+0x1d8>
    2714:	eb 01       	movw	r28, r22
    2716:	ee 81       	ldd	r30, Y+6	; 0x06
    2718:	ff 81       	ldd	r31, Y+7	; 0x07
    271a:	e7 01       	movw	r28, r14
    271c:	fb 83       	std	Y+3, r31	; 0x03
    271e:	ea 83       	std	Y+2, r30	; 0x02
    2720:	15 82       	std	Z+5, r1	; 0x05
    2722:	14 82       	std	Z+4, r1	; 0x04
    2724:	41 50       	subi	r20, 0x01	; 1
    2726:	50 40       	sbci	r21, 0x00	; 0
    2728:	59 83       	std	Y+1, r21	; 0x01
    272a:	48 83       	st	Y, r20
    272c:	b7 cf       	rjmp	.-146    	; 0x269c <_ZN6System4Heap4freeEPvj+0x11a>
    272e:	41 15       	cp	r20, r1
    2730:	51 05       	cpc	r21, r1
    2732:	09 f4       	brne	.+2      	; 0x2736 <_ZN6System4Heap4freeEPvj+0x1b4>
    2734:	b3 cf       	rjmp	.-154    	; 0x269c <_ZN6System4Heap4freeEPvj+0x11a>
    2736:	41 30       	cpi	r20, 0x01	; 1
    2738:	51 05       	cpc	r21, r1
    273a:	79 f0       	breq	.+30     	; 0x275a <_ZN6System4Heap4freeEPvj+0x1d8>
    273c:	e7 01       	movw	r28, r14
    273e:	ec 81       	ldd	r30, Y+4	; 0x04
    2740:	fd 81       	ldd	r31, Y+5	; 0x05
    2742:	04 80       	ldd	r0, Z+4	; 0x04
    2744:	f5 81       	ldd	r31, Z+5	; 0x05
    2746:	e0 2d       	mov	r30, r0
    2748:	fd 83       	std	Y+5, r31	; 0x05
    274a:	ec 83       	std	Y+4, r30	; 0x04
    274c:	17 82       	std	Z+7, r1	; 0x07
    274e:	16 82       	std	Z+6, r1	; 0x06
    2750:	41 50       	subi	r20, 0x01	; 1
    2752:	50 40       	sbci	r21, 0x00	; 0
    2754:	59 83       	std	Y+1, r21	; 0x01
    2756:	48 83       	st	Y, r20
    2758:	a1 cf       	rjmp	.-190    	; 0x269c <_ZN6System4Heap4freeEPvj+0x11a>
    275a:	f7 01       	movw	r30, r14
    275c:	13 82       	std	Z+3, r1	; 0x03
    275e:	12 82       	std	Z+2, r1	; 0x02
    2760:	15 82       	std	Z+5, r1	; 0x05
    2762:	14 82       	std	Z+4, r1	; 0x04
    2764:	11 82       	std	Z+1, r1	; 0x01
    2766:	10 82       	st	Z, r1
    2768:	99 cf       	rjmp	.-206    	; 0x269c <_ZN6System4Heap4freeEPvj+0x11a>
    276a:	e7 01       	movw	r28, r14
    276c:	ec 81       	ldd	r30, Y+4	; 0x04
    276e:	fd 81       	ldd	r31, Y+5	; 0x05
    2770:	d7 82       	std	Z+7, r13	; 0x07
    2772:	c6 82       	std	Z+6, r12	; 0x06
    2774:	e6 01       	movw	r28, r12
    2776:	fd 83       	std	Y+5, r31	; 0x05
    2778:	ec 83       	std	Y+4, r30	; 0x04
    277a:	1f 82       	std	Y+7, r1	; 0x07
    277c:	1e 82       	std	Y+6, r1	; 0x06
    277e:	f7 01       	movw	r30, r14
    2780:	d5 82       	std	Z+5, r13	; 0x05
    2782:	c4 82       	std	Z+4, r12	; 0x04
    2784:	01 96       	adiw	r24, 0x01	; 1
    2786:	91 83       	std	Z+1, r25	; 0x01
    2788:	80 83       	st	Z, r24
    278a:	df 91       	pop	r29
    278c:	cf 91       	pop	r28
    278e:	1f 91       	pop	r17
    2790:	0f 91       	pop	r16
    2792:	ff 90       	pop	r15
    2794:	ef 90       	pop	r14
    2796:	df 90       	pop	r13
    2798:	cf 90       	pop	r12
    279a:	08 95       	ret

0000279c <_ZN6System4Heap5allocEj>:
    279c:	0f 93       	push	r16
    279e:	1f 93       	push	r17
    27a0:	cf 93       	push	r28
    27a2:	df 93       	push	r29
    27a4:	ec 01       	movw	r28, r24
    27a6:	61 15       	cp	r22, r1
    27a8:	71 05       	cpc	r23, r1
    27aa:	e1 f0       	breq	.+56     	; 0x27e4 <_ZN6System4Heap5allocEj+0x48>
    27ac:	6e 5f       	subi	r22, 0xFE	; 254
    27ae:	7f 4f       	sbci	r23, 0xFF	; 255
    27b0:	aa 81       	ldd	r26, Y+2	; 0x02
    27b2:	bb 81       	ldd	r27, Y+3	; 0x03
    27b4:	ad 01       	movw	r20, r26
    27b6:	10 97       	sbiw	r26, 0x00	; 0
    27b8:	59 f0       	breq	.+22     	; 0x27d0 <_ZN6System4Heap5allocEj+0x34>
    27ba:	fd 01       	movw	r30, r26
    27bc:	82 81       	ldd	r24, Z+2	; 0x02
    27be:	93 81       	ldd	r25, Z+3	; 0x03
    27c0:	86 17       	cp	r24, r22
    27c2:	97 07       	cpc	r25, r23
    27c4:	90 f4       	brcc	.+36     	; 0x27ea <_ZN6System4Heap5allocEj+0x4e>
    27c6:	fd 01       	movw	r30, r26
    27c8:	a6 81       	ldd	r26, Z+6	; 0x06
    27ca:	b7 81       	ldd	r27, Z+7	; 0x07
    27cc:	10 97       	sbiw	r26, 0x00	; 0
    27ce:	a9 f7       	brne	.-22     	; 0x27ba <_ZN6System4Heap5allocEj+0x1e>
    27d0:	83 e0       	ldi	r24, 0x03	; 3
    27d2:	80 93 8b 04 	sts	0x048B, r24
    27d6:	85 b7       	in	r24, 0x35	; 53
    27d8:	83 7f       	andi	r24, 0xF3	; 243
    27da:	85 bf       	out	0x35, r24	; 53
    27dc:	85 b7       	in	r24, 0x35	; 53
    27de:	80 61       	ori	r24, 0x10	; 16
    27e0:	85 bf       	out	0x35, r24	; 53
    27e2:	88 95       	sleep
    27e4:	80 e0       	ldi	r24, 0x00	; 0
    27e6:	90 e0       	ldi	r25, 0x00	; 0
    27e8:	7a c0       	rjmp	.+244    	; 0x28de <_ZN6System4Heap5allocEj+0x142>
    27ea:	86 1b       	sub	r24, r22
    27ec:	97 0b       	sbc	r25, r23
    27ee:	fd 01       	movw	r30, r26
    27f0:	93 83       	std	Z+3, r25	; 0x03
    27f2:	82 83       	std	Z+2, r24	; 0x02
    27f4:	8e 81       	ldd	r24, Y+6	; 0x06
    27f6:	9f 81       	ldd	r25, Y+7	; 0x07
    27f8:	86 1b       	sub	r24, r22
    27fa:	97 0b       	sbc	r25, r23
    27fc:	9f 83       	std	Y+7, r25	; 0x07
    27fe:	8e 83       	std	Y+6, r24	; 0x06
    2800:	82 81       	ldd	r24, Z+2	; 0x02
    2802:	93 81       	ldd	r25, Z+3	; 0x03
    2804:	00 97       	sbiw	r24, 0x00	; 0
    2806:	41 f0       	breq	.+16     	; 0x2818 <_ZN6System4Heap5allocEj+0x7c>
    2808:	ed 91       	ld	r30, X+
    280a:	fc 91       	ld	r31, X
    280c:	e8 0f       	add	r30, r24
    280e:	f9 1f       	adc	r31, r25
    2810:	61 93       	st	Z+, r22
    2812:	71 93       	st	Z+, r23
    2814:	cf 01       	movw	r24, r30
    2816:	63 c0       	rjmp	.+198    	; 0x28de <_ZN6System4Heap5allocEj+0x142>
    2818:	08 81       	ld	r16, Y
    281a:	19 81       	ldd	r17, Y+1	; 0x01
    281c:	01 30       	cpi	r16, 0x01	; 1
    281e:	11 05       	cpc	r17, r1
    2820:	e9 f0       	breq	.+58     	; 0x285c <_ZN6System4Heap5allocEj+0xc0>
    2822:	fd 01       	movw	r30, r26
    2824:	24 81       	ldd	r18, Z+4	; 0x04
    2826:	35 81       	ldd	r19, Z+5	; 0x05
    2828:	21 15       	cp	r18, r1
    282a:	31 05       	cpc	r19, r1
    282c:	09 f1       	breq	.+66     	; 0x2870 <_ZN6System4Heap5allocEj+0xd4>
    282e:	fd 01       	movw	r30, r26
    2830:	46 81       	ldd	r20, Z+6	; 0x06
    2832:	57 81       	ldd	r21, Z+7	; 0x07
    2834:	41 15       	cp	r20, r1
    2836:	51 05       	cpc	r21, r1
    2838:	89 f1       	breq	.+98     	; 0x289c <_ZN6System4Heap5allocEj+0x100>
    283a:	f9 01       	movw	r30, r18
    283c:	57 83       	std	Z+7, r21	; 0x07
    283e:	46 83       	std	Z+6, r20	; 0x06
    2840:	fd 01       	movw	r30, r26
    2842:	86 81       	ldd	r24, Z+6	; 0x06
    2844:	97 81       	ldd	r25, Z+7	; 0x07
    2846:	fc 01       	movw	r30, r24
    2848:	35 83       	std	Z+5, r19	; 0x05
    284a:	24 83       	std	Z+4, r18	; 0x04
    284c:	01 50       	subi	r16, 0x01	; 1
    284e:	10 40       	sbci	r17, 0x00	; 0
    2850:	19 83       	std	Y+1, r17	; 0x01
    2852:	08 83       	st	Y, r16
    2854:	fd 01       	movw	r30, r26
    2856:	82 81       	ldd	r24, Z+2	; 0x02
    2858:	93 81       	ldd	r25, Z+3	; 0x03
    285a:	d6 cf       	rjmp	.-84     	; 0x2808 <_ZN6System4Heap5allocEj+0x6c>
    285c:	1b 82       	std	Y+3, r1	; 0x03
    285e:	1a 82       	std	Y+2, r1	; 0x02
    2860:	1d 82       	std	Y+5, r1	; 0x05
    2862:	1c 82       	std	Y+4, r1	; 0x04
    2864:	19 82       	std	Y+1, r1	; 0x01
    2866:	18 82       	st	Y, r1
    2868:	fd 01       	movw	r30, r26
    286a:	82 81       	ldd	r24, Z+2	; 0x02
    286c:	93 81       	ldd	r25, Z+3	; 0x03
    286e:	cc cf       	rjmp	.-104    	; 0x2808 <_ZN6System4Heap5allocEj+0x6c>
    2870:	01 15       	cp	r16, r1
    2872:	11 05       	cpc	r17, r1
    2874:	49 f2       	breq	.-110    	; 0x2808 <_ZN6System4Heap5allocEj+0x6c>
    2876:	01 30       	cpi	r16, 0x01	; 1
    2878:	11 05       	cpc	r17, r1
    287a:	41 f1       	breq	.+80     	; 0x28cc <_ZN6System4Heap5allocEj+0x130>
    287c:	fa 01       	movw	r30, r20
    287e:	46 81       	ldd	r20, Z+6	; 0x06
    2880:	57 81       	ldd	r21, Z+7	; 0x07
    2882:	5b 83       	std	Y+3, r21	; 0x03
    2884:	4a 83       	std	Y+2, r20	; 0x02
    2886:	fa 01       	movw	r30, r20
    2888:	15 82       	std	Z+5, r1	; 0x05
    288a:	14 82       	std	Z+4, r1	; 0x04
    288c:	01 50       	subi	r16, 0x01	; 1
    288e:	10 40       	sbci	r17, 0x00	; 0
    2890:	19 83       	std	Y+1, r17	; 0x01
    2892:	08 83       	st	Y, r16
    2894:	fd 01       	movw	r30, r26
    2896:	82 81       	ldd	r24, Z+2	; 0x02
    2898:	93 81       	ldd	r25, Z+3	; 0x03
    289a:	b6 cf       	rjmp	.-148    	; 0x2808 <_ZN6System4Heap5allocEj+0x6c>
    289c:	01 15       	cp	r16, r1
    289e:	11 05       	cpc	r17, r1
    28a0:	09 f4       	brne	.+2      	; 0x28a4 <_ZN6System4Heap5allocEj+0x108>
    28a2:	b2 cf       	rjmp	.-156    	; 0x2808 <_ZN6System4Heap5allocEj+0x6c>
    28a4:	01 30       	cpi	r16, 0x01	; 1
    28a6:	11 05       	cpc	r17, r1
    28a8:	89 f0       	breq	.+34     	; 0x28cc <_ZN6System4Heap5allocEj+0x130>
    28aa:	ec 81       	ldd	r30, Y+4	; 0x04
    28ac:	fd 81       	ldd	r31, Y+5	; 0x05
    28ae:	04 80       	ldd	r0, Z+4	; 0x04
    28b0:	f5 81       	ldd	r31, Z+5	; 0x05
    28b2:	e0 2d       	mov	r30, r0
    28b4:	fd 83       	std	Y+5, r31	; 0x05
    28b6:	ec 83       	std	Y+4, r30	; 0x04
    28b8:	17 82       	std	Z+7, r1	; 0x07
    28ba:	16 82       	std	Z+6, r1	; 0x06
    28bc:	01 50       	subi	r16, 0x01	; 1
    28be:	10 40       	sbci	r17, 0x00	; 0
    28c0:	19 83       	std	Y+1, r17	; 0x01
    28c2:	08 83       	st	Y, r16
    28c4:	fd 01       	movw	r30, r26
    28c6:	82 81       	ldd	r24, Z+2	; 0x02
    28c8:	93 81       	ldd	r25, Z+3	; 0x03
    28ca:	9e cf       	rjmp	.-196    	; 0x2808 <_ZN6System4Heap5allocEj+0x6c>
    28cc:	1b 82       	std	Y+3, r1	; 0x03
    28ce:	1a 82       	std	Y+2, r1	; 0x02
    28d0:	1d 82       	std	Y+5, r1	; 0x05
    28d2:	1c 82       	std	Y+4, r1	; 0x04
    28d4:	19 82       	std	Y+1, r1	; 0x01
    28d6:	18 82       	st	Y, r1
    28d8:	82 81       	ldd	r24, Z+2	; 0x02
    28da:	93 81       	ldd	r25, Z+3	; 0x03
    28dc:	95 cf       	rjmp	.-214    	; 0x2808 <_ZN6System4Heap5allocEj+0x6c>
    28de:	df 91       	pop	r29
    28e0:	cf 91       	pop	r28
    28e2:	1f 91       	pop	r17
    28e4:	0f 91       	pop	r16
    28e6:	08 95       	ret

000028e8 <_ZN6System7OStream4utoaEjPcj>:
    28e8:	ef 92       	push	r14
    28ea:	ff 92       	push	r15
    28ec:	0f 93       	push	r16
    28ee:	1f 93       	push	r17
    28f0:	cf 93       	push	r28
    28f2:	df 93       	push	r29
    28f4:	7c 01       	movw	r14, r24
    28f6:	8b 01       	movw	r16, r22
    28f8:	ea 01       	movw	r28, r20
    28fa:	67 2b       	or	r22, r23
    28fc:	39 f4       	brne	.+14     	; 0x290c <_ZN6System7OStream4utoaEjPcj+0x24>
    28fe:	c2 0f       	add	r28, r18
    2900:	d3 1f       	adc	r29, r19
    2902:	80 e3       	ldi	r24, 0x30	; 48
    2904:	88 83       	st	Y, r24
    2906:	2f 5f       	subi	r18, 0xFF	; 255
    2908:	3f 4f       	sbci	r19, 0xFF	; 255
    290a:	46 c0       	rjmp	.+140    	; 0x2998 <_ZN6System7OStream4utoaEjPcj+0xb0>
    290c:	dc 01       	movw	r26, r24
    290e:	ed 91       	ld	r30, X+
    2910:	fc 91       	ld	r31, X
    2912:	e8 30       	cpi	r30, 0x08	; 8
    2914:	f1 05       	cpc	r31, r1
    2916:	29 f1       	breq	.+74     	; 0x2962 <_ZN6System7OStream4utoaEjPcj+0x7a>
    2918:	e0 31       	cpi	r30, 0x10	; 16
    291a:	f1 05       	cpc	r31, r1
    291c:	11 f1       	breq	.+68     	; 0x2962 <_ZN6System7OStream4utoaEjPcj+0x7a>
    291e:	b8 01       	movw	r22, r16
    2920:	2f 5f       	subi	r18, 0xFF	; 255
    2922:	3f 4f       	sbci	r19, 0xFF	; 255
    2924:	cb 01       	movw	r24, r22
    2926:	bf 01       	movw	r22, r30
    2928:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    292c:	61 15       	cp	r22, r1
    292e:	71 05       	cpc	r23, r1
    2930:	b9 f7       	brne	.-18     	; 0x2920 <_ZN6System7OStream4utoaEjPcj+0x38>
    2932:	c2 0f       	add	r28, r18
    2934:	d3 1f       	adc	r29, r19
    2936:	21 97       	sbiw	r28, 0x01	; 1
    2938:	c8 01       	movw	r24, r16
    293a:	bf 01       	movw	r22, r30
    293c:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    2940:	fc 01       	movw	r30, r24
    2942:	e4 51       	subi	r30, 0x14	; 20
    2944:	fe 4f       	sbci	r31, 0xFE	; 254
    2946:	80 81       	ld	r24, Z
    2948:	88 83       	st	Y, r24
    294a:	d7 01       	movw	r26, r14
    294c:	ed 91       	ld	r30, X+
    294e:	fc 91       	ld	r31, X
    2950:	c8 01       	movw	r24, r16
    2952:	bf 01       	movw	r22, r30
    2954:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    2958:	8b 01       	movw	r16, r22
    295a:	21 97       	sbiw	r28, 0x01	; 1
    295c:	67 2b       	or	r22, r23
    295e:	61 f7       	brne	.-40     	; 0x2938 <_ZN6System7OStream4utoaEjPcj+0x50>
    2960:	1b c0       	rjmp	.+54     	; 0x2998 <_ZN6System7OStream4utoaEjPcj+0xb0>
    2962:	fe 01       	movw	r30, r28
    2964:	e2 0f       	add	r30, r18
    2966:	f3 1f       	adc	r31, r19
    2968:	80 e3       	ldi	r24, 0x30	; 48
    296a:	80 83       	st	Z, r24
    296c:	a9 01       	movw	r20, r18
    296e:	4f 5f       	subi	r20, 0xFF	; 255
    2970:	5f 4f       	sbci	r21, 0xFF	; 255
    2972:	d7 01       	movw	r26, r14
    2974:	ed 91       	ld	r30, X+
    2976:	fc 91       	ld	r31, X
    2978:	e0 31       	cpi	r30, 0x10	; 16
    297a:	f1 05       	cpc	r31, r1
    297c:	59 f4       	brne	.+22     	; 0x2994 <_ZN6System7OStream4utoaEjPcj+0xac>
    297e:	4c 0f       	add	r20, r28
    2980:	5d 1f       	adc	r21, r29
    2982:	88 e7       	ldi	r24, 0x78	; 120
    2984:	fa 01       	movw	r30, r20
    2986:	80 83       	st	Z, r24
    2988:	2e 5f       	subi	r18, 0xFE	; 254
    298a:	3f 4f       	sbci	r19, 0xFF	; 255
    298c:	d7 01       	movw	r26, r14
    298e:	ed 91       	ld	r30, X+
    2990:	fc 91       	ld	r31, X
    2992:	c5 cf       	rjmp	.-118    	; 0x291e <_ZN6System7OStream4utoaEjPcj+0x36>
    2994:	9a 01       	movw	r18, r20
    2996:	c3 cf       	rjmp	.-122    	; 0x291e <_ZN6System7OStream4utoaEjPcj+0x36>
    2998:	c9 01       	movw	r24, r18
    299a:	df 91       	pop	r29
    299c:	cf 91       	pop	r28
    299e:	1f 91       	pop	r17
    29a0:	0f 91       	pop	r16
    29a2:	ff 90       	pop	r15
    29a4:	ef 90       	pop	r14
    29a6:	08 95       	ret

000029a8 <_ZN6System7OStream4itoaEiPc>:
    29a8:	dc 01       	movw	r26, r24
    29aa:	fa 01       	movw	r30, r20
    29ac:	77 fd       	sbrc	r23, 7
    29ae:	03 c0       	rjmp	.+6      	; 0x29b6 <_ZN6System7OStream4itoaEiPc+0xe>
    29b0:	20 e0       	ldi	r18, 0x00	; 0
    29b2:	30 e0       	ldi	r19, 0x00	; 0
    29b4:	07 c0       	rjmp	.+14     	; 0x29c4 <_ZN6System7OStream4itoaEiPc+0x1c>
    29b6:	70 95       	com	r23
    29b8:	61 95       	neg	r22
    29ba:	7f 4f       	sbci	r23, 0xFF	; 255
    29bc:	8d e2       	ldi	r24, 0x2D	; 45
    29be:	80 83       	st	Z, r24
    29c0:	21 e0       	ldi	r18, 0x01	; 1
    29c2:	30 e0       	ldi	r19, 0x00	; 0
    29c4:	af 01       	movw	r20, r30
    29c6:	cd 01       	movw	r24, r26
    29c8:	0e 94 74 14 	call	0x28e8	; 0x28e8 <_ZN6System7OStream4utoaEjPcj>
    29cc:	08 95       	ret

000029ce <_ZN6System7OStream6llutoaEyPcj>:
    29ce:	2f 92       	push	r2
    29d0:	3f 92       	push	r3
    29d2:	4f 92       	push	r4
    29d4:	5f 92       	push	r5
    29d6:	6f 92       	push	r6
    29d8:	7f 92       	push	r7
    29da:	8f 92       	push	r8
    29dc:	9f 92       	push	r9
    29de:	af 92       	push	r10
    29e0:	bf 92       	push	r11
    29e2:	cf 92       	push	r12
    29e4:	df 92       	push	r13
    29e6:	ef 92       	push	r14
    29e8:	ff 92       	push	r15
    29ea:	0f 93       	push	r16
    29ec:	1f 93       	push	r17
    29ee:	cf 93       	push	r28
    29f0:	df 93       	push	r29
    29f2:	cd b7       	in	r28, 0x3d	; 61
    29f4:	de b7       	in	r29, 0x3e	; 62
    29f6:	28 97       	sbiw	r28, 0x08	; 8
    29f8:	0f b6       	in	r0, 0x3f	; 63
    29fa:	f8 94       	cli
    29fc:	de bf       	out	0x3e, r29	; 62
    29fe:	0f be       	out	0x3f, r0	; 63
    2a00:	cd bf       	out	0x3d, r28	; 61
    2a02:	9c 83       	std	Y+4, r25	; 0x04
    2a04:	8b 83       	std	Y+3, r24	; 0x03
    2a06:	20 2e       	mov	r2, r16
    2a08:	31 2e       	mov	r3, r17
    2a0a:	42 2e       	mov	r4, r18
    2a0c:	53 2e       	mov	r5, r19
    2a0e:	64 2e       	mov	r6, r20
    2a10:	75 2e       	mov	r7, r21
    2a12:	86 2e       	mov	r8, r22
    2a14:	97 2e       	mov	r9, r23
    2a16:	97 01       	movw	r18, r14
    2a18:	de 82       	std	Y+6, r13	; 0x06
    2a1a:	cd 82       	std	Y+5, r12	; 0x05
    2a1c:	80 2f       	mov	r24, r16
    2a1e:	81 2b       	or	r24, r17
    2a20:	84 29       	or	r24, r4
    2a22:	85 29       	or	r24, r5
    2a24:	84 2b       	or	r24, r20
    2a26:	85 2b       	or	r24, r21
    2a28:	86 2b       	or	r24, r22
    2a2a:	87 2b       	or	r24, r23
    2a2c:	59 f4       	brne	.+22     	; 0x2a44 <_ZN6System7OStream6llutoaEyPcj+0x76>
    2a2e:	2c 0d       	add	r18, r12
    2a30:	3d 1d       	adc	r19, r13
    2a32:	80 e3       	ldi	r24, 0x30	; 48
    2a34:	f9 01       	movw	r30, r18
    2a36:	80 83       	st	Z, r24
    2a38:	cd 80       	ldd	r12, Y+5	; 0x05
    2a3a:	de 80       	ldd	r13, Y+6	; 0x06
    2a3c:	08 94       	sec
    2a3e:	c1 1c       	adc	r12, r1
    2a40:	d1 1c       	adc	r13, r1
    2a42:	b0 c0       	rjmp	.+352    	; 0x2ba4 <_ZN6System7OStream6llutoaEyPcj+0x1d6>
    2a44:	eb 81       	ldd	r30, Y+3	; 0x03
    2a46:	fc 81       	ldd	r31, Y+4	; 0x04
    2a48:	01 90       	ld	r0, Z+
    2a4a:	f0 81       	ld	r31, Z
    2a4c:	e0 2d       	mov	r30, r0
    2a4e:	f8 87       	std	Y+8, r31	; 0x08
    2a50:	ef 83       	std	Y+7, r30	; 0x07
    2a52:	e8 30       	cpi	r30, 0x08	; 8
    2a54:	f1 05       	cpc	r31, r1
    2a56:	09 f4       	brne	.+2      	; 0x2a5a <_ZN6System7OStream6llutoaEyPcj+0x8c>
    2a58:	7f c0       	rjmp	.+254    	; 0x2b58 <_ZN6System7OStream6llutoaEyPcj+0x18a>
    2a5a:	70 97       	sbiw	r30, 0x10	; 16
    2a5c:	09 f4       	brne	.+2      	; 0x2a60 <_ZN6System7OStream6llutoaEyPcj+0x92>
    2a5e:	7c c0       	rjmp	.+248    	; 0x2b58 <_ZN6System7OStream6llutoaEyPcj+0x18a>
    2a60:	b1 01       	movw	r22, r2
    2a62:	21 14       	cp	r2, r1
    2a64:	31 04       	cpc	r3, r1
    2a66:	69 f0       	breq	.+26     	; 0x2a82 <_ZN6System7OStream6llutoaEyPcj+0xb4>
    2a68:	8d 81       	ldd	r24, Y+5	; 0x05
    2a6a:	9e 81       	ldd	r25, Y+6	; 0x06
    2a6c:	01 96       	adiw	r24, 0x01	; 1
    2a6e:	9e 83       	std	Y+6, r25	; 0x06
    2a70:	8d 83       	std	Y+5, r24	; 0x05
    2a72:	cb 01       	movw	r24, r22
    2a74:	6f 81       	ldd	r22, Y+7	; 0x07
    2a76:	78 85       	ldd	r23, Y+8	; 0x08
    2a78:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    2a7c:	61 15       	cp	r22, r1
    2a7e:	71 05       	cpc	r23, r1
    2a80:	99 f7       	brne	.-26     	; 0x2a68 <_ZN6System7OStream6llutoaEyPcj+0x9a>
    2a82:	ed 81       	ldd	r30, Y+5	; 0x05
    2a84:	fe 81       	ldd	r31, Y+6	; 0x06
    2a86:	e2 0f       	add	r30, r18
    2a88:	f3 1f       	adc	r31, r19
    2a8a:	31 97       	sbiw	r30, 0x01	; 1
    2a8c:	fa 83       	std	Y+2, r31	; 0x02
    2a8e:	e9 83       	std	Y+1, r30	; 0x01
    2a90:	ef 81       	ldd	r30, Y+7	; 0x07
    2a92:	f8 85       	ldd	r31, Y+8	; 0x08
    2a94:	ff 0f       	add	r31, r31
    2a96:	ee 0b       	sbc	r30, r30
    2a98:	fe 2f       	mov	r31, r30
    2a9a:	2f 81       	ldd	r18, Y+7	; 0x07
    2a9c:	38 85       	ldd	r19, Y+8	; 0x08
    2a9e:	a2 2e       	mov	r10, r18
    2aa0:	b3 2e       	mov	r11, r19
    2aa2:	ce 2e       	mov	r12, r30
    2aa4:	de 2e       	mov	r13, r30
    2aa6:	ee 2e       	mov	r14, r30
    2aa8:	fe 2e       	mov	r15, r30
    2aaa:	0e 2f       	mov	r16, r30
    2aac:	1e 2f       	mov	r17, r30
    2aae:	22 2d       	mov	r18, r2
    2ab0:	33 2d       	mov	r19, r3
    2ab2:	44 2d       	mov	r20, r4
    2ab4:	55 2d       	mov	r21, r5
    2ab6:	66 2d       	mov	r22, r6
    2ab8:	77 2d       	mov	r23, r7
    2aba:	88 2d       	mov	r24, r8
    2abc:	99 2d       	mov	r25, r9
    2abe:	0e 94 c8 3a 	call	0x7590	; 0x7590 <__umoddi3>
    2ac2:	a2 2e       	mov	r10, r18
    2ac4:	b3 2e       	mov	r11, r19
    2ac6:	8c ee       	ldi	r24, 0xEC	; 236
    2ac8:	91 e0       	ldi	r25, 0x01	; 1
    2aca:	a8 0e       	add	r10, r24
    2acc:	b9 1e       	adc	r11, r25
    2ace:	f5 01       	movw	r30, r10
    2ad0:	80 81       	ld	r24, Z
    2ad2:	e9 81       	ldd	r30, Y+1	; 0x01
    2ad4:	fa 81       	ldd	r31, Y+2	; 0x02
    2ad6:	80 83       	st	Z, r24
    2ad8:	eb 81       	ldd	r30, Y+3	; 0x03
    2ada:	fc 81       	ldd	r31, Y+4	; 0x04
    2adc:	01 90       	ld	r0, Z+
    2ade:	f0 81       	ld	r31, Z
    2ae0:	e0 2d       	mov	r30, r0
    2ae2:	f8 87       	std	Y+8, r31	; 0x08
    2ae4:	ef 83       	std	Y+7, r30	; 0x07
    2ae6:	ff 0f       	add	r31, r31
    2ae8:	ee 0b       	sbc	r30, r30
    2aea:	fe 2f       	mov	r31, r30
    2aec:	2f 81       	ldd	r18, Y+7	; 0x07
    2aee:	38 85       	ldd	r19, Y+8	; 0x08
    2af0:	a2 2e       	mov	r10, r18
    2af2:	b3 2e       	mov	r11, r19
    2af4:	ce 2e       	mov	r12, r30
    2af6:	de 2e       	mov	r13, r30
    2af8:	ee 2e       	mov	r14, r30
    2afa:	fe 2e       	mov	r15, r30
    2afc:	0e 2f       	mov	r16, r30
    2afe:	1e 2f       	mov	r17, r30
    2b00:	22 2d       	mov	r18, r2
    2b02:	33 2d       	mov	r19, r3
    2b04:	44 2d       	mov	r20, r4
    2b06:	55 2d       	mov	r21, r5
    2b08:	66 2d       	mov	r22, r6
    2b0a:	77 2d       	mov	r23, r7
    2b0c:	88 2d       	mov	r24, r8
    2b0e:	99 2d       	mov	r25, r9
    2b10:	0e 94 95 33 	call	0x672a	; 0x672a <__udivdi3>
    2b14:	a2 2e       	mov	r10, r18
    2b16:	b3 2e       	mov	r11, r19
    2b18:	c4 2e       	mov	r12, r20
    2b1a:	d5 2e       	mov	r13, r21
    2b1c:	e6 2e       	mov	r14, r22
    2b1e:	f7 2e       	mov	r15, r23
    2b20:	08 2f       	mov	r16, r24
    2b22:	19 2f       	mov	r17, r25
    2b24:	22 2e       	mov	r2, r18
    2b26:	33 2e       	mov	r3, r19
    2b28:	44 2e       	mov	r4, r20
    2b2a:	55 2e       	mov	r5, r21
    2b2c:	66 2e       	mov	r6, r22
    2b2e:	77 2e       	mov	r7, r23
    2b30:	88 2e       	mov	r8, r24
    2b32:	99 2e       	mov	r9, r25
    2b34:	89 81       	ldd	r24, Y+1	; 0x01
    2b36:	9a 81       	ldd	r25, Y+2	; 0x02
    2b38:	01 97       	sbiw	r24, 0x01	; 1
    2b3a:	9a 83       	std	Y+2, r25	; 0x02
    2b3c:	89 83       	std	Y+1, r24	; 0x01
    2b3e:	8a 2d       	mov	r24, r10
    2b40:	8b 29       	or	r24, r11
    2b42:	8c 29       	or	r24, r12
    2b44:	8d 29       	or	r24, r13
    2b46:	8e 29       	or	r24, r14
    2b48:	8f 29       	or	r24, r15
    2b4a:	80 2b       	or	r24, r16
    2b4c:	81 2b       	or	r24, r17
    2b4e:	09 f0       	breq	.+2      	; 0x2b52 <_ZN6System7OStream6llutoaEyPcj+0x184>
    2b50:	9f cf       	rjmp	.-194    	; 0x2a90 <_ZN6System7OStream6llutoaEyPcj+0xc2>
    2b52:	cd 80       	ldd	r12, Y+5	; 0x05
    2b54:	de 80       	ldd	r13, Y+6	; 0x06
    2b56:	26 c0       	rjmp	.+76     	; 0x2ba4 <_ZN6System7OStream6llutoaEyPcj+0x1d6>
    2b58:	ad 81       	ldd	r26, Y+5	; 0x05
    2b5a:	be 81       	ldd	r27, Y+6	; 0x06
    2b5c:	a2 0f       	add	r26, r18
    2b5e:	b3 1f       	adc	r27, r19
    2b60:	80 e3       	ldi	r24, 0x30	; 48
    2b62:	8c 93       	st	X, r24
    2b64:	ad 81       	ldd	r26, Y+5	; 0x05
    2b66:	be 81       	ldd	r27, Y+6	; 0x06
    2b68:	11 96       	adiw	r26, 0x01	; 1
    2b6a:	eb 81       	ldd	r30, Y+3	; 0x03
    2b6c:	fc 81       	ldd	r31, Y+4	; 0x04
    2b6e:	01 90       	ld	r0, Z+
    2b70:	f0 81       	ld	r31, Z
    2b72:	e0 2d       	mov	r30, r0
    2b74:	f8 87       	std	Y+8, r31	; 0x08
    2b76:	ef 83       	std	Y+7, r30	; 0x07
    2b78:	70 97       	sbiw	r30, 0x10	; 16
    2b7a:	89 f4       	brne	.+34     	; 0x2b9e <_ZN6System7OStream6llutoaEyPcj+0x1d0>
    2b7c:	a2 0f       	add	r26, r18
    2b7e:	b3 1f       	adc	r27, r19
    2b80:	88 e7       	ldi	r24, 0x78	; 120
    2b82:	8c 93       	st	X, r24
    2b84:	8d 81       	ldd	r24, Y+5	; 0x05
    2b86:	9e 81       	ldd	r25, Y+6	; 0x06
    2b88:	02 96       	adiw	r24, 0x02	; 2
    2b8a:	9e 83       	std	Y+6, r25	; 0x06
    2b8c:	8d 83       	std	Y+5, r24	; 0x05
    2b8e:	eb 81       	ldd	r30, Y+3	; 0x03
    2b90:	fc 81       	ldd	r31, Y+4	; 0x04
    2b92:	01 90       	ld	r0, Z+
    2b94:	f0 81       	ld	r31, Z
    2b96:	e0 2d       	mov	r30, r0
    2b98:	f8 87       	std	Y+8, r31	; 0x08
    2b9a:	ef 83       	std	Y+7, r30	; 0x07
    2b9c:	61 cf       	rjmp	.-318    	; 0x2a60 <_ZN6System7OStream6llutoaEyPcj+0x92>
    2b9e:	be 83       	std	Y+6, r27	; 0x06
    2ba0:	ad 83       	std	Y+5, r26	; 0x05
    2ba2:	5e cf       	rjmp	.-324    	; 0x2a60 <_ZN6System7OStream6llutoaEyPcj+0x92>
    2ba4:	c6 01       	movw	r24, r12
    2ba6:	28 96       	adiw	r28, 0x08	; 8
    2ba8:	0f b6       	in	r0, 0x3f	; 63
    2baa:	f8 94       	cli
    2bac:	de bf       	out	0x3e, r29	; 62
    2bae:	0f be       	out	0x3f, r0	; 63
    2bb0:	cd bf       	out	0x3d, r28	; 61
    2bb2:	df 91       	pop	r29
    2bb4:	cf 91       	pop	r28
    2bb6:	1f 91       	pop	r17
    2bb8:	0f 91       	pop	r16
    2bba:	ff 90       	pop	r15
    2bbc:	ef 90       	pop	r14
    2bbe:	df 90       	pop	r13
    2bc0:	cf 90       	pop	r12
    2bc2:	bf 90       	pop	r11
    2bc4:	af 90       	pop	r10
    2bc6:	9f 90       	pop	r9
    2bc8:	8f 90       	pop	r8
    2bca:	7f 90       	pop	r7
    2bcc:	6f 90       	pop	r6
    2bce:	5f 90       	pop	r5
    2bd0:	4f 90       	pop	r4
    2bd2:	3f 90       	pop	r3
    2bd4:	2f 90       	pop	r2
    2bd6:	08 95       	ret

00002bd8 <_ZN6System7OStream6llitoaExPc>:
    2bd8:	2f 92       	push	r2
    2bda:	3f 92       	push	r3
    2bdc:	4f 92       	push	r4
    2bde:	5f 92       	push	r5
    2be0:	6f 92       	push	r6
    2be2:	7f 92       	push	r7
    2be4:	8f 92       	push	r8
    2be6:	9f 92       	push	r9
    2be8:	af 92       	push	r10
    2bea:	bf 92       	push	r11
    2bec:	cf 92       	push	r12
    2bee:	df 92       	push	r13
    2bf0:	ef 92       	push	r14
    2bf2:	ff 92       	push	r15
    2bf4:	0f 93       	push	r16
    2bf6:	1f 93       	push	r17
    2bf8:	cf 93       	push	r28
    2bfa:	df 93       	push	r29
    2bfc:	cd b7       	in	r28, 0x3d	; 61
    2bfe:	de b7       	in	r29, 0x3e	; 62
    2c00:	28 97       	sbiw	r28, 0x08	; 8
    2c02:	0f b6       	in	r0, 0x3f	; 63
    2c04:	f8 94       	cli
    2c06:	de bf       	out	0x3e, r29	; 62
    2c08:	0f be       	out	0x3f, r0	; 63
    2c0a:	cd bf       	out	0x3d, r28	; 61
    2c0c:	1c 01       	movw	r2, r24
    2c0e:	40 2e       	mov	r4, r16
    2c10:	51 2e       	mov	r5, r17
    2c12:	62 2e       	mov	r6, r18
    2c14:	73 2e       	mov	r7, r19
    2c16:	84 2e       	mov	r8, r20
    2c18:	95 2e       	mov	r9, r21
    2c1a:	a6 2e       	mov	r10, r22
    2c1c:	b7 2e       	mov	r11, r23
    2c1e:	77 fd       	sbrc	r23, 7
    2c20:	03 c0       	rjmp	.+6      	; 0x2c28 <_ZN6System7OStream6llitoaExPc+0x50>
    2c22:	20 e0       	ldi	r18, 0x00	; 0
    2c24:	30 e0       	ldi	r19, 0x00	; 0
    2c26:	e8 c0       	rjmp	.+464    	; 0x2df8 <_ZN6System7OStream6llitoaExPc+0x220>
    2c28:	19 82       	std	Y+1, r1	; 0x01
    2c2a:	1a 82       	std	Y+2, r1	; 0x02
    2c2c:	1b 82       	std	Y+3, r1	; 0x03
    2c2e:	1c 82       	std	Y+4, r1	; 0x04
    2c30:	1d 82       	std	Y+5, r1	; 0x05
    2c32:	1e 82       	std	Y+6, r1	; 0x06
    2c34:	1f 82       	std	Y+7, r1	; 0x07
    2c36:	18 86       	std	Y+8, r1	; 0x08
    2c38:	00 e0       	ldi	r16, 0x00	; 0
    2c3a:	04 19       	sub	r16, r4
    2c3c:	81 e0       	ldi	r24, 0x01	; 1
    2c3e:	90 e0       	ldi	r25, 0x00	; 0
    2c40:	90 17       	cp	r25, r16
    2c42:	08 f0       	brcs	.+2      	; 0x2c46 <_ZN6System7OStream6llitoaExPc+0x6e>
    2c44:	d0 c0       	rjmp	.+416    	; 0x2de6 <_ZN6System7OStream6llitoaExPc+0x20e>
    2c46:	1a 81       	ldd	r17, Y+2	; 0x02
    2c48:	15 19       	sub	r17, r5
    2c4a:	a1 e0       	ldi	r26, 0x01	; 1
    2c4c:	ea 81       	ldd	r30, Y+2	; 0x02
    2c4e:	e1 17       	cp	r30, r17
    2c50:	08 f0       	brcs	.+2      	; 0x2c54 <_ZN6System7OStream6llitoaExPc+0x7c>
    2c52:	c0 c0       	rjmp	.+384    	; 0x2dd4 <_ZN6System7OStream6llitoaExPc+0x1fc>
    2c54:	f1 2f       	mov	r31, r17
    2c56:	f8 1b       	sub	r31, r24
    2c58:	8f 2f       	mov	r24, r31
    2c5a:	f1 e0       	ldi	r31, 0x01	; 1
    2c5c:	18 17       	cp	r17, r24
    2c5e:	08 f0       	brcs	.+2      	; 0x2c62 <_ZN6System7OStream6llitoaExPc+0x8a>
    2c60:	ae c0       	rjmp	.+348    	; 0x2dbe <_ZN6System7OStream6llitoaExPc+0x1e6>
    2c62:	af 2b       	or	r26, r31
    2c64:	18 2f       	mov	r17, r24
    2c66:	2b 81       	ldd	r18, Y+3	; 0x03
    2c68:	26 19       	sub	r18, r6
    2c6a:	b1 e0       	ldi	r27, 0x01	; 1
    2c6c:	8b 81       	ldd	r24, Y+3	; 0x03
    2c6e:	82 17       	cp	r24, r18
    2c70:	08 f0       	brcs	.+2      	; 0x2c74 <_ZN6System7OStream6llitoaExPc+0x9c>
    2c72:	9d c0       	rjmp	.+314    	; 0x2dae <_ZN6System7OStream6llitoaExPc+0x1d6>
    2c74:	82 2f       	mov	r24, r18
    2c76:	8a 1b       	sub	r24, r26
    2c78:	e1 e0       	ldi	r30, 0x01	; 1
    2c7a:	28 17       	cp	r18, r24
    2c7c:	08 f0       	brcs	.+2      	; 0x2c80 <_ZN6System7OStream6llitoaExPc+0xa8>
    2c7e:	8c c0       	rjmp	.+280    	; 0x2d98 <_ZN6System7OStream6llitoaExPc+0x1c0>
    2c80:	be 2b       	or	r27, r30
    2c82:	28 2f       	mov	r18, r24
    2c84:	3c 81       	ldd	r19, Y+4	; 0x04
    2c86:	37 19       	sub	r19, r7
    2c88:	a1 e0       	ldi	r26, 0x01	; 1
    2c8a:	9c 81       	ldd	r25, Y+4	; 0x04
    2c8c:	93 17       	cp	r25, r19
    2c8e:	08 f0       	brcs	.+2      	; 0x2c92 <_ZN6System7OStream6llitoaExPc+0xba>
    2c90:	7b c0       	rjmp	.+246    	; 0x2d88 <_ZN6System7OStream6llitoaExPc+0x1b0>
    2c92:	83 2f       	mov	r24, r19
    2c94:	8b 1b       	sub	r24, r27
    2c96:	f1 e0       	ldi	r31, 0x01	; 1
    2c98:	38 17       	cp	r19, r24
    2c9a:	08 f0       	brcs	.+2      	; 0x2c9e <_ZN6System7OStream6llitoaExPc+0xc6>
    2c9c:	6a c0       	rjmp	.+212    	; 0x2d72 <_ZN6System7OStream6llitoaExPc+0x19a>
    2c9e:	af 2b       	or	r26, r31
    2ca0:	38 2f       	mov	r19, r24
    2ca2:	4d 81       	ldd	r20, Y+5	; 0x05
    2ca4:	48 19       	sub	r20, r8
    2ca6:	b1 e0       	ldi	r27, 0x01	; 1
    2ca8:	ed 81       	ldd	r30, Y+5	; 0x05
    2caa:	e4 17       	cp	r30, r20
    2cac:	08 f0       	brcs	.+2      	; 0x2cb0 <_ZN6System7OStream6llitoaExPc+0xd8>
    2cae:	59 c0       	rjmp	.+178    	; 0x2d62 <_ZN6System7OStream6llitoaExPc+0x18a>
    2cb0:	84 2f       	mov	r24, r20
    2cb2:	8a 1b       	sub	r24, r26
    2cb4:	e1 e0       	ldi	r30, 0x01	; 1
    2cb6:	48 17       	cp	r20, r24
    2cb8:	08 f0       	brcs	.+2      	; 0x2cbc <_ZN6System7OStream6llitoaExPc+0xe4>
    2cba:	48 c0       	rjmp	.+144    	; 0x2d4c <_ZN6System7OStream6llitoaExPc+0x174>
    2cbc:	be 2b       	or	r27, r30
    2cbe:	48 2f       	mov	r20, r24
    2cc0:	5e 81       	ldd	r21, Y+6	; 0x06
    2cc2:	59 19       	sub	r21, r9
    2cc4:	a1 e0       	ldi	r26, 0x01	; 1
    2cc6:	fe 81       	ldd	r31, Y+6	; 0x06
    2cc8:	f5 17       	cp	r31, r21
    2cca:	c8 f5       	brcc	.+114    	; 0x2d3e <_ZN6System7OStream6llitoaExPc+0x166>
    2ccc:	85 2f       	mov	r24, r21
    2cce:	8b 1b       	sub	r24, r27
    2cd0:	e1 e0       	ldi	r30, 0x01	; 1
    2cd2:	58 17       	cp	r21, r24
    2cd4:	50 f5       	brcc	.+84     	; 0x2d2a <_ZN6System7OStream6llitoaExPc+0x152>
    2cd6:	ae 2b       	or	r26, r30
    2cd8:	58 2f       	mov	r21, r24
    2cda:	6f 81       	ldd	r22, Y+7	; 0x07
    2cdc:	6a 19       	sub	r22, r10
    2cde:	b1 e0       	ldi	r27, 0x01	; 1
    2ce0:	8f 81       	ldd	r24, Y+7	; 0x07
    2ce2:	86 17       	cp	r24, r22
    2ce4:	d8 f4       	brcc	.+54     	; 0x2d1c <_ZN6System7OStream6llitoaExPc+0x144>
    2ce6:	86 2f       	mov	r24, r22
    2ce8:	8a 1b       	sub	r24, r26
    2cea:	f1 e0       	ldi	r31, 0x01	; 1
    2cec:	68 17       	cp	r22, r24
    2cee:	a0 f4       	brcc	.+40     	; 0x2d18 <_ZN6System7OStream6llitoaExPc+0x140>
    2cf0:	bf 2b       	or	r27, r31
    2cf2:	68 2f       	mov	r22, r24
    2cf4:	78 85       	ldd	r23, Y+8	; 0x08
    2cf6:	7b 19       	sub	r23, r11
    2cf8:	87 2f       	mov	r24, r23
    2cfa:	8b 1b       	sub	r24, r27
    2cfc:	40 2e       	mov	r4, r16
    2cfe:	51 2e       	mov	r5, r17
    2d00:	62 2e       	mov	r6, r18
    2d02:	73 2e       	mov	r7, r19
    2d04:	84 2e       	mov	r8, r20
    2d06:	95 2e       	mov	r9, r21
    2d08:	a6 2e       	mov	r10, r22
    2d0a:	b8 2e       	mov	r11, r24
    2d0c:	8d e2       	ldi	r24, 0x2D	; 45
    2d0e:	f7 01       	movw	r30, r14
    2d10:	80 83       	st	Z, r24
    2d12:	21 e0       	ldi	r18, 0x01	; 1
    2d14:	30 e0       	ldi	r19, 0x00	; 0
    2d16:	70 c0       	rjmp	.+224    	; 0x2df8 <_ZN6System7OStream6llitoaExPc+0x220>
    2d18:	f0 e0       	ldi	r31, 0x00	; 0
    2d1a:	ea cf       	rjmp	.-44     	; 0x2cf0 <_ZN6System7OStream6llitoaExPc+0x118>
    2d1c:	b0 e0       	ldi	r27, 0x00	; 0
    2d1e:	86 2f       	mov	r24, r22
    2d20:	8a 1b       	sub	r24, r26
    2d22:	f1 e0       	ldi	r31, 0x01	; 1
    2d24:	68 17       	cp	r22, r24
    2d26:	20 f3       	brcs	.-56     	; 0x2cf0 <_ZN6System7OStream6llitoaExPc+0x118>
    2d28:	f7 cf       	rjmp	.-18     	; 0x2d18 <_ZN6System7OStream6llitoaExPc+0x140>
    2d2a:	e0 e0       	ldi	r30, 0x00	; 0
    2d2c:	ae 2b       	or	r26, r30
    2d2e:	58 2f       	mov	r21, r24
    2d30:	6f 81       	ldd	r22, Y+7	; 0x07
    2d32:	6a 19       	sub	r22, r10
    2d34:	b1 e0       	ldi	r27, 0x01	; 1
    2d36:	8f 81       	ldd	r24, Y+7	; 0x07
    2d38:	86 17       	cp	r24, r22
    2d3a:	a8 f2       	brcs	.-86     	; 0x2ce6 <_ZN6System7OStream6llitoaExPc+0x10e>
    2d3c:	ef cf       	rjmp	.-34     	; 0x2d1c <_ZN6System7OStream6llitoaExPc+0x144>
    2d3e:	a0 e0       	ldi	r26, 0x00	; 0
    2d40:	85 2f       	mov	r24, r21
    2d42:	8b 1b       	sub	r24, r27
    2d44:	e1 e0       	ldi	r30, 0x01	; 1
    2d46:	58 17       	cp	r21, r24
    2d48:	30 f2       	brcs	.-116    	; 0x2cd6 <_ZN6System7OStream6llitoaExPc+0xfe>
    2d4a:	ef cf       	rjmp	.-34     	; 0x2d2a <_ZN6System7OStream6llitoaExPc+0x152>
    2d4c:	e0 e0       	ldi	r30, 0x00	; 0
    2d4e:	be 2b       	or	r27, r30
    2d50:	48 2f       	mov	r20, r24
    2d52:	5e 81       	ldd	r21, Y+6	; 0x06
    2d54:	59 19       	sub	r21, r9
    2d56:	a1 e0       	ldi	r26, 0x01	; 1
    2d58:	fe 81       	ldd	r31, Y+6	; 0x06
    2d5a:	f5 17       	cp	r31, r21
    2d5c:	08 f4       	brcc	.+2      	; 0x2d60 <_ZN6System7OStream6llitoaExPc+0x188>
    2d5e:	b6 cf       	rjmp	.-148    	; 0x2ccc <_ZN6System7OStream6llitoaExPc+0xf4>
    2d60:	ee cf       	rjmp	.-36     	; 0x2d3e <_ZN6System7OStream6llitoaExPc+0x166>
    2d62:	b0 e0       	ldi	r27, 0x00	; 0
    2d64:	84 2f       	mov	r24, r20
    2d66:	8a 1b       	sub	r24, r26
    2d68:	e1 e0       	ldi	r30, 0x01	; 1
    2d6a:	48 17       	cp	r20, r24
    2d6c:	08 f4       	brcc	.+2      	; 0x2d70 <_ZN6System7OStream6llitoaExPc+0x198>
    2d6e:	a6 cf       	rjmp	.-180    	; 0x2cbc <_ZN6System7OStream6llitoaExPc+0xe4>
    2d70:	ed cf       	rjmp	.-38     	; 0x2d4c <_ZN6System7OStream6llitoaExPc+0x174>
    2d72:	f0 e0       	ldi	r31, 0x00	; 0
    2d74:	af 2b       	or	r26, r31
    2d76:	38 2f       	mov	r19, r24
    2d78:	4d 81       	ldd	r20, Y+5	; 0x05
    2d7a:	48 19       	sub	r20, r8
    2d7c:	b1 e0       	ldi	r27, 0x01	; 1
    2d7e:	ed 81       	ldd	r30, Y+5	; 0x05
    2d80:	e4 17       	cp	r30, r20
    2d82:	08 f4       	brcc	.+2      	; 0x2d86 <_ZN6System7OStream6llitoaExPc+0x1ae>
    2d84:	95 cf       	rjmp	.-214    	; 0x2cb0 <_ZN6System7OStream6llitoaExPc+0xd8>
    2d86:	ed cf       	rjmp	.-38     	; 0x2d62 <_ZN6System7OStream6llitoaExPc+0x18a>
    2d88:	a0 e0       	ldi	r26, 0x00	; 0
    2d8a:	83 2f       	mov	r24, r19
    2d8c:	8b 1b       	sub	r24, r27
    2d8e:	f1 e0       	ldi	r31, 0x01	; 1
    2d90:	38 17       	cp	r19, r24
    2d92:	08 f4       	brcc	.+2      	; 0x2d96 <_ZN6System7OStream6llitoaExPc+0x1be>
    2d94:	84 cf       	rjmp	.-248    	; 0x2c9e <_ZN6System7OStream6llitoaExPc+0xc6>
    2d96:	ed cf       	rjmp	.-38     	; 0x2d72 <_ZN6System7OStream6llitoaExPc+0x19a>
    2d98:	e0 e0       	ldi	r30, 0x00	; 0
    2d9a:	be 2b       	or	r27, r30
    2d9c:	28 2f       	mov	r18, r24
    2d9e:	3c 81       	ldd	r19, Y+4	; 0x04
    2da0:	37 19       	sub	r19, r7
    2da2:	a1 e0       	ldi	r26, 0x01	; 1
    2da4:	9c 81       	ldd	r25, Y+4	; 0x04
    2da6:	93 17       	cp	r25, r19
    2da8:	08 f4       	brcc	.+2      	; 0x2dac <_ZN6System7OStream6llitoaExPc+0x1d4>
    2daa:	73 cf       	rjmp	.-282    	; 0x2c92 <_ZN6System7OStream6llitoaExPc+0xba>
    2dac:	ed cf       	rjmp	.-38     	; 0x2d88 <_ZN6System7OStream6llitoaExPc+0x1b0>
    2dae:	b0 e0       	ldi	r27, 0x00	; 0
    2db0:	82 2f       	mov	r24, r18
    2db2:	8a 1b       	sub	r24, r26
    2db4:	e1 e0       	ldi	r30, 0x01	; 1
    2db6:	28 17       	cp	r18, r24
    2db8:	08 f4       	brcc	.+2      	; 0x2dbc <_ZN6System7OStream6llitoaExPc+0x1e4>
    2dba:	62 cf       	rjmp	.-316    	; 0x2c80 <_ZN6System7OStream6llitoaExPc+0xa8>
    2dbc:	ed cf       	rjmp	.-38     	; 0x2d98 <_ZN6System7OStream6llitoaExPc+0x1c0>
    2dbe:	f0 e0       	ldi	r31, 0x00	; 0
    2dc0:	af 2b       	or	r26, r31
    2dc2:	18 2f       	mov	r17, r24
    2dc4:	2b 81       	ldd	r18, Y+3	; 0x03
    2dc6:	26 19       	sub	r18, r6
    2dc8:	b1 e0       	ldi	r27, 0x01	; 1
    2dca:	8b 81       	ldd	r24, Y+3	; 0x03
    2dcc:	82 17       	cp	r24, r18
    2dce:	08 f4       	brcc	.+2      	; 0x2dd2 <_ZN6System7OStream6llitoaExPc+0x1fa>
    2dd0:	51 cf       	rjmp	.-350    	; 0x2c74 <_ZN6System7OStream6llitoaExPc+0x9c>
    2dd2:	ed cf       	rjmp	.-38     	; 0x2dae <_ZN6System7OStream6llitoaExPc+0x1d6>
    2dd4:	a0 e0       	ldi	r26, 0x00	; 0
    2dd6:	f1 2f       	mov	r31, r17
    2dd8:	f8 1b       	sub	r31, r24
    2dda:	8f 2f       	mov	r24, r31
    2ddc:	f1 e0       	ldi	r31, 0x01	; 1
    2dde:	18 17       	cp	r17, r24
    2de0:	08 f4       	brcc	.+2      	; 0x2de4 <_ZN6System7OStream6llitoaExPc+0x20c>
    2de2:	3f cf       	rjmp	.-386    	; 0x2c62 <_ZN6System7OStream6llitoaExPc+0x8a>
    2de4:	ec cf       	rjmp	.-40     	; 0x2dbe <_ZN6System7OStream6llitoaExPc+0x1e6>
    2de6:	80 e0       	ldi	r24, 0x00	; 0
    2de8:	1a 81       	ldd	r17, Y+2	; 0x02
    2dea:	15 19       	sub	r17, r5
    2dec:	a1 e0       	ldi	r26, 0x01	; 1
    2dee:	ea 81       	ldd	r30, Y+2	; 0x02
    2df0:	e1 17       	cp	r30, r17
    2df2:	08 f4       	brcc	.+2      	; 0x2df6 <_ZN6System7OStream6llitoaExPc+0x21e>
    2df4:	2f cf       	rjmp	.-418    	; 0x2c54 <_ZN6System7OStream6llitoaExPc+0x7c>
    2df6:	ee cf       	rjmp	.-36     	; 0x2dd4 <_ZN6System7OStream6llitoaExPc+0x1fc>
    2df8:	69 01       	movw	r12, r18
    2dfa:	04 2d       	mov	r16, r4
    2dfc:	15 2d       	mov	r17, r5
    2dfe:	26 2d       	mov	r18, r6
    2e00:	37 2d       	mov	r19, r7
    2e02:	48 2d       	mov	r20, r8
    2e04:	59 2d       	mov	r21, r9
    2e06:	6a 2d       	mov	r22, r10
    2e08:	7b 2d       	mov	r23, r11
    2e0a:	c1 01       	movw	r24, r2
    2e0c:	0e 94 e7 14 	call	0x29ce	; 0x29ce <_ZN6System7OStream6llutoaEyPcj>
    2e10:	28 96       	adiw	r28, 0x08	; 8
    2e12:	0f b6       	in	r0, 0x3f	; 63
    2e14:	f8 94       	cli
    2e16:	de bf       	out	0x3e, r29	; 62
    2e18:	0f be       	out	0x3f, r0	; 63
    2e1a:	cd bf       	out	0x3d, r28	; 61
    2e1c:	df 91       	pop	r29
    2e1e:	cf 91       	pop	r28
    2e20:	1f 91       	pop	r17
    2e22:	0f 91       	pop	r16
    2e24:	ff 90       	pop	r15
    2e26:	ef 90       	pop	r14
    2e28:	df 90       	pop	r13
    2e2a:	cf 90       	pop	r12
    2e2c:	bf 90       	pop	r11
    2e2e:	af 90       	pop	r10
    2e30:	9f 90       	pop	r9
    2e32:	8f 90       	pop	r8
    2e34:	7f 90       	pop	r7
    2e36:	6f 90       	pop	r6
    2e38:	5f 90       	pop	r5
    2e3a:	4f 90       	pop	r4
    2e3c:	3f 90       	pop	r3
    2e3e:	2f 90       	pop	r2
    2e40:	08 95       	ret

00002e42 <_ZN6System7OStream4ptoaEPKvPc>:
    2e42:	fa 01       	movw	r30, r20
    2e44:	80 e3       	ldi	r24, 0x30	; 48
    2e46:	80 83       	st	Z, r24
    2e48:	88 e7       	ldi	r24, 0x78	; 120
    2e4a:	81 83       	std	Z+1, r24	; 0x01
    2e4c:	da 01       	movw	r26, r20
    2e4e:	20 e0       	ldi	r18, 0x00	; 0
    2e50:	30 e0       	ldi	r19, 0x00	; 0
    2e52:	fb 01       	movw	r30, r22
    2e54:	ef 70       	andi	r30, 0x0F	; 15
    2e56:	f0 70       	andi	r31, 0x00	; 0
    2e58:	e4 51       	subi	r30, 0x14	; 20
    2e5a:	fe 4f       	sbci	r31, 0xFE	; 254
    2e5c:	80 81       	ld	r24, Z
    2e5e:	fd 01       	movw	r30, r26
    2e60:	85 83       	std	Z+5, r24	; 0x05
    2e62:	2f 5f       	subi	r18, 0xFF	; 255
    2e64:	3f 4f       	sbci	r19, 0xFF	; 255
    2e66:	72 95       	swap	r23
    2e68:	62 95       	swap	r22
    2e6a:	6f 70       	andi	r22, 0x0F	; 15
    2e6c:	67 27       	eor	r22, r23
    2e6e:	7f 70       	andi	r23, 0x0F	; 15
    2e70:	67 27       	eor	r22, r23
    2e72:	11 97       	sbiw	r26, 0x01	; 1
    2e74:	24 30       	cpi	r18, 0x04	; 4
    2e76:	31 05       	cpc	r19, r1
    2e78:	61 f7       	brne	.-40     	; 0x2e52 <_ZN6System7OStream4ptoaEPKvPc+0x10>
    2e7a:	86 e0       	ldi	r24, 0x06	; 6
    2e7c:	90 e0       	ldi	r25, 0x00	; 0
    2e7e:	08 95       	ret

00002e80 <_ZN6System7OStream5printEPKc>:
    2e80:	cf 93       	push	r28
    2e82:	df 93       	push	r29
    2e84:	cd b7       	in	r28, 0x3d	; 61
    2e86:	de b7       	in	r29, 0x3e	; 62
    2e88:	28 97       	sbiw	r28, 0x08	; 8
    2e8a:	0f b6       	in	r0, 0x3f	; 63
    2e8c:	f8 94       	cli
    2e8e:	de bf       	out	0x3e, r29	; 62
    2e90:	0f be       	out	0x3f, r0	; 63
    2e92:	cd bf       	out	0x3d, r28	; 61
    2e94:	db 01       	movw	r26, r22
    2e96:	1a 82       	std	Y+2, r1	; 0x02
    2e98:	19 82       	std	Y+1, r1	; 0x01
    2e9a:	10 92 90 00 	sts	0x0090, r1
    2e9e:	8f e2       	ldi	r24, 0x2F	; 47
    2ea0:	89 b9       	out	0x09, r24	; 9
    2ea2:	86 e0       	ldi	r24, 0x06	; 6
    2ea4:	8b 83       	std	Y+3, r24	; 0x03
    2ea6:	89 81       	ldd	r24, Y+1	; 0x01
    2ea8:	9a 81       	ldd	r25, Y+2	; 0x02
    2eaa:	89 2b       	or	r24, r25
    2eac:	09 f0       	breq	.+2      	; 0x2eb0 <_ZN6System7OStream5printEPKc+0x30>
    2eae:	fc c0       	rjmp	.+504    	; 0x30a8 <_ZN6System7OStream5printEPKc+0x228>
    2eb0:	e5 e7       	ldi	r30, 0x75	; 117
    2eb2:	f0 e0       	ldi	r31, 0x00	; 0
    2eb4:	86 e8       	ldi	r24, 0x86	; 134
    2eb6:	80 a3       	std	Z+32, r24	; 0x20
    2eb8:	89 81       	ldd	r24, Y+1	; 0x01
    2eba:	9a 81       	ldd	r25, Y+2	; 0x02
    2ebc:	89 2b       	or	r24, r25
    2ebe:	09 f0       	breq	.+2      	; 0x2ec2 <_ZN6System7OStream5printEPKc+0x42>
    2ec0:	fc c0       	rjmp	.+504    	; 0x30ba <_ZN6System7OStream5printEPKc+0x23a>
    2ec2:	eb e0       	ldi	r30, 0x0B	; 11
    2ec4:	f0 e0       	ldi	r31, 0x00	; 0
    2ec6:	10 a2       	std	Z+32, r1	; 0x20
    2ec8:	1c 82       	std	Y+4, r1	; 0x04
    2eca:	29 81       	ldd	r18, Y+1	; 0x01
    2ecc:	3a 81       	ldd	r19, Y+2	; 0x02
    2ece:	21 15       	cp	r18, r1
    2ed0:	31 05       	cpc	r19, r1
    2ed2:	09 f0       	breq	.+2      	; 0x2ed6 <_ZN6System7OStream5printEPKc+0x56>
    2ed4:	fc c0       	rjmp	.+504    	; 0x30ce <_ZN6System7OStream5printEPKc+0x24e>
    2ed6:	ea e0       	ldi	r30, 0x0A	; 10
    2ed8:	f0 e0       	ldi	r31, 0x00	; 0
    2eda:	80 a1       	ldd	r24, Z+32	; 0x20
    2edc:	88 61       	ori	r24, 0x18	; 24
    2ede:	23 2b       	or	r18, r19
    2ee0:	09 f0       	breq	.+2      	; 0x2ee4 <_ZN6System7OStream5printEPKc+0x64>
    2ee2:	fc c0       	rjmp	.+504    	; 0x30dc <_ZN6System7OStream5printEPKc+0x25c>
    2ee4:	ea e0       	ldi	r30, 0x0A	; 10
    2ee6:	f0 e0       	ldi	r31, 0x00	; 0
    2ee8:	80 a3       	std	Z+32, r24	; 0x20
    2eea:	1e 82       	std	Y+6, r1	; 0x06
    2eec:	1d 82       	std	Y+5, r1	; 0x05
    2eee:	18 86       	std	Y+8, r1	; 0x08
    2ef0:	1f 82       	std	Y+7, r1	; 0x07
    2ef2:	5d e0       	ldi	r21, 0x0D	; 13
    2ef4:	6a e0       	ldi	r22, 0x0A	; 10
    2ef6:	4c 91       	ld	r20, X
    2ef8:	44 23       	and	r20, r20
    2efa:	09 f4       	brne	.+2      	; 0x2efe <_ZN6System7OStream5printEPKc+0x7e>
    2efc:	59 c0       	rjmp	.+178    	; 0x2fb0 <_ZN6System7OStream5printEPKc+0x130>
    2efe:	11 96       	adiw	r26, 0x01	; 1
    2f00:	49 30       	cpi	r20, 0x09	; 9
    2f02:	09 f4       	brne	.+2      	; 0x2f06 <_ZN6System7OStream5printEPKc+0x86>
    2f04:	70 c0       	rjmp	.+224    	; 0x2fe6 <_ZN6System7OStream5printEPKc+0x166>
    2f06:	4a 30       	cpi	r20, 0x0A	; 10
    2f08:	09 f4       	brne	.+2      	; 0x2f0c <_ZN6System7OStream5printEPKc+0x8c>
    2f0a:	8d c0       	rjmp	.+282    	; 0x3026 <_ZN6System7OStream5printEPKc+0x1a6>
    2f0c:	8f 81       	ldd	r24, Y+7	; 0x07
    2f0e:	98 85       	ldd	r25, Y+8	; 0x08
    2f10:	01 96       	adiw	r24, 0x01	; 1
    2f12:	98 87       	std	Y+8, r25	; 0x08
    2f14:	8f 83       	std	Y+7, r24	; 0x07
    2f16:	29 81       	ldd	r18, Y+1	; 0x01
    2f18:	3a 81       	ldd	r19, Y+2	; 0x02
    2f1a:	05 c0       	rjmp	.+10     	; 0x2f26 <_ZN6System7OStream5printEPKc+0xa6>
    2f1c:	eb e0       	ldi	r30, 0x0B	; 11
    2f1e:	f0 e0       	ldi	r31, 0x00	; 0
    2f20:	80 a1       	ldd	r24, Z+32	; 0x20
    2f22:	85 fd       	sbrc	r24, 5
    2f24:	08 c0       	rjmp	.+16     	; 0x2f36 <_ZN6System7OStream5printEPKc+0xb6>
    2f26:	21 15       	cp	r18, r1
    2f28:	31 05       	cpc	r19, r1
    2f2a:	c1 f3       	breq	.-16     	; 0x2f1c <_ZN6System7OStream5printEPKc+0x9c>
    2f2c:	eb e7       	ldi	r30, 0x7B	; 123
    2f2e:	f0 e0       	ldi	r31, 0x00	; 0
    2f30:	80 a1       	ldd	r24, Z+32	; 0x20
    2f32:	85 ff       	sbrs	r24, 5
    2f34:	f8 cf       	rjmp	.-16     	; 0x2f26 <_ZN6System7OStream5printEPKc+0xa6>
    2f36:	23 2b       	or	r18, r19
    2f38:	09 f0       	breq	.+2      	; 0x2f3c <_ZN6System7OStream5printEPKc+0xbc>
    2f3a:	72 c0       	rjmp	.+228    	; 0x3020 <_ZN6System7OStream5printEPKc+0x1a0>
    2f3c:	ec e0       	ldi	r30, 0x0C	; 12
    2f3e:	f0 e0       	ldi	r31, 0x00	; 0
    2f40:	40 a3       	std	Z+32, r20	; 0x20
    2f42:	8f 81       	ldd	r24, Y+7	; 0x07
    2f44:	98 85       	ldd	r25, Y+8	; 0x08
    2f46:	80 35       	cpi	r24, 0x50	; 80
    2f48:	91 05       	cpc	r25, r1
    2f4a:	ac f2       	brlt	.-86     	; 0x2ef6 <_ZN6System7OStream5printEPKc+0x76>
    2f4c:	29 81       	ldd	r18, Y+1	; 0x01
    2f4e:	3a 81       	ldd	r19, Y+2	; 0x02
    2f50:	05 c0       	rjmp	.+10     	; 0x2f5c <_ZN6System7OStream5printEPKc+0xdc>
    2f52:	eb e0       	ldi	r30, 0x0B	; 11
    2f54:	f0 e0       	ldi	r31, 0x00	; 0
    2f56:	80 a1       	ldd	r24, Z+32	; 0x20
    2f58:	85 fd       	sbrc	r24, 5
    2f5a:	08 c0       	rjmp	.+16     	; 0x2f6c <_ZN6System7OStream5printEPKc+0xec>
    2f5c:	21 15       	cp	r18, r1
    2f5e:	31 05       	cpc	r19, r1
    2f60:	c1 f3       	breq	.-16     	; 0x2f52 <_ZN6System7OStream5printEPKc+0xd2>
    2f62:	eb e7       	ldi	r30, 0x7B	; 123
    2f64:	f0 e0       	ldi	r31, 0x00	; 0
    2f66:	80 a1       	ldd	r24, Z+32	; 0x20
    2f68:	85 ff       	sbrs	r24, 5
    2f6a:	f8 cf       	rjmp	.-16     	; 0x2f5c <_ZN6System7OStream5printEPKc+0xdc>
    2f6c:	23 2b       	or	r18, r19
    2f6e:	09 f0       	breq	.+2      	; 0x2f72 <_ZN6System7OStream5printEPKc+0xf2>
    2f70:	8f c0       	rjmp	.+286    	; 0x3090 <_ZN6System7OStream5printEPKc+0x210>
    2f72:	ec e0       	ldi	r30, 0x0C	; 12
    2f74:	f0 e0       	ldi	r31, 0x00	; 0
    2f76:	50 a3       	std	Z+32, r21	; 0x20
    2f78:	29 81       	ldd	r18, Y+1	; 0x01
    2f7a:	3a 81       	ldd	r19, Y+2	; 0x02
    2f7c:	05 c0       	rjmp	.+10     	; 0x2f88 <_ZN6System7OStream5printEPKc+0x108>
    2f7e:	eb e0       	ldi	r30, 0x0B	; 11
    2f80:	f0 e0       	ldi	r31, 0x00	; 0
    2f82:	80 a1       	ldd	r24, Z+32	; 0x20
    2f84:	85 fd       	sbrc	r24, 5
    2f86:	08 c0       	rjmp	.+16     	; 0x2f98 <_ZN6System7OStream5printEPKc+0x118>
    2f88:	21 15       	cp	r18, r1
    2f8a:	31 05       	cpc	r19, r1
    2f8c:	c1 f3       	breq	.-16     	; 0x2f7e <_ZN6System7OStream5printEPKc+0xfe>
    2f8e:	eb e7       	ldi	r30, 0x7B	; 123
    2f90:	f0 e0       	ldi	r31, 0x00	; 0
    2f92:	80 a1       	ldd	r24, Z+32	; 0x20
    2f94:	85 ff       	sbrs	r24, 5
    2f96:	f8 cf       	rjmp	.-16     	; 0x2f88 <_ZN6System7OStream5printEPKc+0x108>
    2f98:	23 2b       	or	r18, r19
    2f9a:	09 f0       	breq	.+2      	; 0x2f9e <_ZN6System7OStream5printEPKc+0x11e>
    2f9c:	7c c0       	rjmp	.+248    	; 0x3096 <_ZN6System7OStream5printEPKc+0x216>
    2f9e:	ec e0       	ldi	r30, 0x0C	; 12
    2fa0:	f0 e0       	ldi	r31, 0x00	; 0
    2fa2:	60 a3       	std	Z+32, r22	; 0x20
    2fa4:	18 86       	std	Y+8, r1	; 0x08
    2fa6:	1f 82       	std	Y+7, r1	; 0x07
    2fa8:	4c 91       	ld	r20, X
    2faa:	44 23       	and	r20, r20
    2fac:	09 f0       	breq	.+2      	; 0x2fb0 <_ZN6System7OStream5printEPKc+0x130>
    2fae:	a7 cf       	rjmp	.-178    	; 0x2efe <_ZN6System7OStream5printEPKc+0x7e>
    2fb0:	de 01       	movw	r26, r28
    2fb2:	11 96       	adiw	r26, 0x01	; 1
    2fb4:	89 81       	ldd	r24, Y+1	; 0x01
    2fb6:	9a 81       	ldd	r25, Y+2	; 0x02
    2fb8:	89 2b       	or	r24, r25
    2fba:	09 f0       	breq	.+2      	; 0x2fbe <_ZN6System7OStream5printEPKc+0x13e>
    2fbc:	98 c0       	rjmp	.+304    	; 0x30ee <_ZN6System7OStream5printEPKc+0x26e>
    2fbe:	e0 e7       	ldi	r30, 0x70	; 112
    2fc0:	f0 e0       	ldi	r31, 0x00	; 0
    2fc2:	10 a2       	std	Z+32, r1	; 0x20
    2fc4:	8d 91       	ld	r24, X+
    2fc6:	9c 91       	ld	r25, X
    2fc8:	11 97       	sbiw	r26, 0x01	; 1
    2fca:	89 2b       	or	r24, r25
    2fcc:	09 f0       	breq	.+2      	; 0x2fd0 <_ZN6System7OStream5printEPKc+0x150>
    2fce:	8c c0       	rjmp	.+280    	; 0x30e8 <_ZN6System7OStream5printEPKc+0x268>
    2fd0:	e9 e0       	ldi	r30, 0x09	; 9
    2fd2:	f0 e0       	ldi	r31, 0x00	; 0
    2fd4:	10 a2       	std	Z+32, r1	; 0x20
    2fd6:	8d 91       	ld	r24, X+
    2fd8:	9c 91       	ld	r25, X
    2fda:	89 2b       	or	r24, r25
    2fdc:	09 f0       	breq	.+2      	; 0x2fe0 <_ZN6System7OStream5printEPKc+0x160>
    2fde:	81 c0       	rjmp	.+258    	; 0x30e2 <_ZN6System7OStream5printEPKc+0x262>
    2fe0:	ea e0       	ldi	r30, 0x0A	; 10
    2fe2:	f0 e0       	ldi	r31, 0x00	; 0
    2fe4:	87 c0       	rjmp	.+270    	; 0x30f4 <_ZN6System7OStream5printEPKc+0x274>
    2fe6:	29 81       	ldd	r18, Y+1	; 0x01
    2fe8:	3a 81       	ldd	r19, Y+2	; 0x02
    2fea:	05 c0       	rjmp	.+10     	; 0x2ff6 <_ZN6System7OStream5printEPKc+0x176>
    2fec:	eb e0       	ldi	r30, 0x0B	; 11
    2fee:	f0 e0       	ldi	r31, 0x00	; 0
    2ff0:	80 a1       	ldd	r24, Z+32	; 0x20
    2ff2:	85 fd       	sbrc	r24, 5
    2ff4:	08 c0       	rjmp	.+16     	; 0x3006 <_ZN6System7OStream5printEPKc+0x186>
    2ff6:	21 15       	cp	r18, r1
    2ff8:	31 05       	cpc	r19, r1
    2ffa:	c1 f3       	breq	.-16     	; 0x2fec <_ZN6System7OStream5printEPKc+0x16c>
    2ffc:	eb e7       	ldi	r30, 0x7B	; 123
    2ffe:	f0 e0       	ldi	r31, 0x00	; 0
    3000:	80 a1       	ldd	r24, Z+32	; 0x20
    3002:	85 ff       	sbrs	r24, 5
    3004:	f8 cf       	rjmp	.-16     	; 0x2ff6 <_ZN6System7OStream5printEPKc+0x176>
    3006:	23 2b       	or	r18, r19
    3008:	09 f0       	breq	.+2      	; 0x300c <_ZN6System7OStream5printEPKc+0x18c>
    300a:	3f c0       	rjmp	.+126    	; 0x308a <_ZN6System7OStream5printEPKc+0x20a>
    300c:	ec e0       	ldi	r30, 0x0C	; 12
    300e:	f0 e0       	ldi	r31, 0x00	; 0
    3010:	89 e0       	ldi	r24, 0x09	; 9
    3012:	80 a3       	std	Z+32, r24	; 0x20
    3014:	8f 81       	ldd	r24, Y+7	; 0x07
    3016:	98 85       	ldd	r25, Y+8	; 0x08
    3018:	08 96       	adiw	r24, 0x08	; 8
    301a:	98 87       	std	Y+8, r25	; 0x08
    301c:	8f 83       	std	Y+7, r24	; 0x07
    301e:	6b cf       	rjmp	.-298    	; 0x2ef6 <_ZN6System7OStream5printEPKc+0x76>
    3020:	ec e7       	ldi	r30, 0x7C	; 124
    3022:	f0 e0       	ldi	r31, 0x00	; 0
    3024:	8d cf       	rjmp	.-230    	; 0x2f40 <_ZN6System7OStream5printEPKc+0xc0>
    3026:	29 81       	ldd	r18, Y+1	; 0x01
    3028:	3a 81       	ldd	r19, Y+2	; 0x02
    302a:	05 c0       	rjmp	.+10     	; 0x3036 <_ZN6System7OStream5printEPKc+0x1b6>
    302c:	eb e0       	ldi	r30, 0x0B	; 11
    302e:	f0 e0       	ldi	r31, 0x00	; 0
    3030:	80 a1       	ldd	r24, Z+32	; 0x20
    3032:	85 fd       	sbrc	r24, 5
    3034:	08 c0       	rjmp	.+16     	; 0x3046 <_ZN6System7OStream5printEPKc+0x1c6>
    3036:	21 15       	cp	r18, r1
    3038:	31 05       	cpc	r19, r1
    303a:	c1 f3       	breq	.-16     	; 0x302c <_ZN6System7OStream5printEPKc+0x1ac>
    303c:	eb e7       	ldi	r30, 0x7B	; 123
    303e:	f0 e0       	ldi	r31, 0x00	; 0
    3040:	80 a1       	ldd	r24, Z+32	; 0x20
    3042:	85 ff       	sbrs	r24, 5
    3044:	f8 cf       	rjmp	.-16     	; 0x3036 <_ZN6System7OStream5printEPKc+0x1b6>
    3046:	23 2b       	or	r18, r19
    3048:	61 f5       	brne	.+88     	; 0x30a2 <_ZN6System7OStream5printEPKc+0x222>
    304a:	ec e0       	ldi	r30, 0x0C	; 12
    304c:	f0 e0       	ldi	r31, 0x00	; 0
    304e:	50 a3       	std	Z+32, r21	; 0x20
    3050:	29 81       	ldd	r18, Y+1	; 0x01
    3052:	3a 81       	ldd	r19, Y+2	; 0x02
    3054:	05 c0       	rjmp	.+10     	; 0x3060 <_ZN6System7OStream5printEPKc+0x1e0>
    3056:	eb e0       	ldi	r30, 0x0B	; 11
    3058:	f0 e0       	ldi	r31, 0x00	; 0
    305a:	80 a1       	ldd	r24, Z+32	; 0x20
    305c:	85 fd       	sbrc	r24, 5
    305e:	08 c0       	rjmp	.+16     	; 0x3070 <_ZN6System7OStream5printEPKc+0x1f0>
    3060:	21 15       	cp	r18, r1
    3062:	31 05       	cpc	r19, r1
    3064:	c1 f3       	breq	.-16     	; 0x3056 <_ZN6System7OStream5printEPKc+0x1d6>
    3066:	eb e7       	ldi	r30, 0x7B	; 123
    3068:	f0 e0       	ldi	r31, 0x00	; 0
    306a:	80 a1       	ldd	r24, Z+32	; 0x20
    306c:	85 ff       	sbrs	r24, 5
    306e:	f8 cf       	rjmp	.-16     	; 0x3060 <_ZN6System7OStream5printEPKc+0x1e0>
    3070:	23 2b       	or	r18, r19
    3072:	a1 f4       	brne	.+40     	; 0x309c <_ZN6System7OStream5printEPKc+0x21c>
    3074:	ec e0       	ldi	r30, 0x0C	; 12
    3076:	f0 e0       	ldi	r31, 0x00	; 0
    3078:	60 a3       	std	Z+32, r22	; 0x20
    307a:	18 86       	std	Y+8, r1	; 0x08
    307c:	1f 82       	std	Y+7, r1	; 0x07
    307e:	8d 81       	ldd	r24, Y+5	; 0x05
    3080:	9e 81       	ldd	r25, Y+6	; 0x06
    3082:	01 96       	adiw	r24, 0x01	; 1
    3084:	9e 83       	std	Y+6, r25	; 0x06
    3086:	8d 83       	std	Y+5, r24	; 0x05
    3088:	36 cf       	rjmp	.-404    	; 0x2ef6 <_ZN6System7OStream5printEPKc+0x76>
    308a:	ec e7       	ldi	r30, 0x7C	; 124
    308c:	f0 e0       	ldi	r31, 0x00	; 0
    308e:	c0 cf       	rjmp	.-128    	; 0x3010 <_ZN6System7OStream5printEPKc+0x190>
    3090:	ec e7       	ldi	r30, 0x7C	; 124
    3092:	f0 e0       	ldi	r31, 0x00	; 0
    3094:	70 cf       	rjmp	.-288    	; 0x2f76 <_ZN6System7OStream5printEPKc+0xf6>
    3096:	ec e7       	ldi	r30, 0x7C	; 124
    3098:	f0 e0       	ldi	r31, 0x00	; 0
    309a:	83 cf       	rjmp	.-250    	; 0x2fa2 <_ZN6System7OStream5printEPKc+0x122>
    309c:	ec e7       	ldi	r30, 0x7C	; 124
    309e:	f0 e0       	ldi	r31, 0x00	; 0
    30a0:	eb cf       	rjmp	.-42     	; 0x3078 <_ZN6System7OStream5printEPKc+0x1f8>
    30a2:	ec e7       	ldi	r30, 0x7C	; 124
    30a4:	f0 e0       	ldi	r31, 0x00	; 0
    30a6:	d3 cf       	rjmp	.-90     	; 0x304e <_ZN6System7OStream5printEPKc+0x1ce>
    30a8:	ed e7       	ldi	r30, 0x7D	; 125
    30aa:	f0 e0       	ldi	r31, 0x00	; 0
    30ac:	86 e8       	ldi	r24, 0x86	; 134
    30ae:	80 a3       	std	Z+32, r24	; 0x20
    30b0:	89 81       	ldd	r24, Y+1	; 0x01
    30b2:	9a 81       	ldd	r25, Y+2	; 0x02
    30b4:	89 2b       	or	r24, r25
    30b6:	09 f4       	brne	.+2      	; 0x30ba <_ZN6System7OStream5printEPKc+0x23a>
    30b8:	04 cf       	rjmp	.-504    	; 0x2ec2 <_ZN6System7OStream5printEPKc+0x42>
    30ba:	eb e7       	ldi	r30, 0x7B	; 123
    30bc:	f0 e0       	ldi	r31, 0x00	; 0
    30be:	10 a2       	std	Z+32, r1	; 0x20
    30c0:	1c 82       	std	Y+4, r1	; 0x04
    30c2:	29 81       	ldd	r18, Y+1	; 0x01
    30c4:	3a 81       	ldd	r19, Y+2	; 0x02
    30c6:	21 15       	cp	r18, r1
    30c8:	31 05       	cpc	r19, r1
    30ca:	09 f4       	brne	.+2      	; 0x30ce <_ZN6System7OStream5printEPKc+0x24e>
    30cc:	04 cf       	rjmp	.-504    	; 0x2ed6 <_ZN6System7OStream5printEPKc+0x56>
    30ce:	ea e7       	ldi	r30, 0x7A	; 122
    30d0:	f0 e0       	ldi	r31, 0x00	; 0
    30d2:	80 a1       	ldd	r24, Z+32	; 0x20
    30d4:	88 61       	ori	r24, 0x18	; 24
    30d6:	23 2b       	or	r18, r19
    30d8:	09 f4       	brne	.+2      	; 0x30dc <_ZN6System7OStream5printEPKc+0x25c>
    30da:	04 cf       	rjmp	.-504    	; 0x2ee4 <_ZN6System7OStream5printEPKc+0x64>
    30dc:	ea e7       	ldi	r30, 0x7A	; 122
    30de:	f0 e0       	ldi	r31, 0x00	; 0
    30e0:	03 cf       	rjmp	.-506    	; 0x2ee8 <_ZN6System7OStream5printEPKc+0x68>
    30e2:	ea e7       	ldi	r30, 0x7A	; 122
    30e4:	f0 e0       	ldi	r31, 0x00	; 0
    30e6:	06 c0       	rjmp	.+12     	; 0x30f4 <_ZN6System7OStream5printEPKc+0x274>
    30e8:	e9 e7       	ldi	r30, 0x79	; 121
    30ea:	f0 e0       	ldi	r31, 0x00	; 0
    30ec:	73 cf       	rjmp	.-282    	; 0x2fd4 <_ZN6System7OStream5printEPKc+0x154>
    30ee:	e8 e7       	ldi	r30, 0x78	; 120
    30f0:	f0 e0       	ldi	r31, 0x00	; 0
    30f2:	67 cf       	rjmp	.-306    	; 0x2fc2 <_ZN6System7OStream5printEPKc+0x142>
    30f4:	10 a2       	std	Z+32, r1	; 0x20
    30f6:	28 96       	adiw	r28, 0x08	; 8
    30f8:	0f b6       	in	r0, 0x3f	; 63
    30fa:	f8 94       	cli
    30fc:	de bf       	out	0x3e, r29	; 62
    30fe:	0f be       	out	0x3f, r0	; 63
    3100:	cd bf       	out	0x3d, r28	; 61
    3102:	df 91       	pop	r29
    3104:	cf 91       	pop	r28
    3106:	08 95       	ret

00003108 <_ZN6System4AVR84initEv>:
    3108:	85 b7       	in	r24, 0x35	; 53
    310a:	8f 7d       	andi	r24, 0xDF	; 223
    310c:	85 bf       	out	0x35, r24	; 53
    310e:	0e 94 8c 18 	call	0x3118	; 0x3118 <_ZN6System8AVR8_MMU4initEv>
    3112:	0e 94 ab 18 	call	0x3156	; 0x3156 <_ZN6System8AVR8_TSC4initEv>
    3116:	08 95       	ret

00003118 <_ZN6System8AVR8_MMU4initEv>:
    3118:	cf 93       	push	r28
    311a:	df 93       	push	r29
    311c:	cd b7       	in	r28, 0x3d	; 61
    311e:	de b7       	in	r29, 0x3e	; 62
    3120:	22 97       	sbiw	r28, 0x02	; 2
    3122:	0f b6       	in	r0, 0x3f	; 63
    3124:	f8 94       	cli
    3126:	de bf       	out	0x3e, r29	; 62
    3128:	0f be       	out	0x3f, r0	; 63
    312a:	cd bf       	out	0x3d, r28	; 61
    312c:	89 e9       	ldi	r24, 0x99	; 153
    312e:	94 e0       	ldi	r25, 0x04	; 4
    3130:	9a 83       	std	Y+2, r25	; 0x02
    3132:	89 83       	std	Y+1, r24	; 0x01
    3134:	60 e0       	ldi	r22, 0x00	; 0
    3136:	70 e1       	ldi	r23, 0x10	; 16
    3138:	68 1b       	sub	r22, r24
    313a:	79 0b       	sbc	r23, r25
    313c:	ce 01       	movw	r24, r28
    313e:	01 96       	adiw	r24, 0x01	; 1
    3140:	0e 94 f2 31 	call	0x63e4	; 0x63e4 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi>
    3144:	22 96       	adiw	r28, 0x02	; 2
    3146:	0f b6       	in	r0, 0x3f	; 63
    3148:	f8 94       	cli
    314a:	de bf       	out	0x3e, r29	; 62
    314c:	0f be       	out	0x3f, r0	; 63
    314e:	cd bf       	out	0x3d, r28	; 61
    3150:	df 91       	pop	r29
    3152:	cf 91       	pop	r28
    3154:	08 95       	ret

00003156 <_ZN6System8AVR8_TSC4initEv>:
    3156:	85 e0       	ldi	r24, 0x05	; 5
    3158:	8e bd       	out	0x2e, r24	; 46
    315a:	10 92 94 04 	sts	0x0494, r1
    315e:	10 92 95 04 	sts	0x0495, r1
    3162:	10 92 96 04 	sts	0x0496, r1
    3166:	10 92 97 04 	sts	0x0497, r1
    316a:	87 b7       	in	r24, 0x37	; 55
    316c:	84 60       	ori	r24, 0x04	; 4
    316e:	87 bf       	out	0x37, r24	; 55
    3170:	08 95       	ret

00003172 <_ZN6System9ATMega1284initEv>:
    3172:	e0 91 ea 01 	lds	r30, 0x01EA
    3176:	f0 91 eb 01 	lds	r31, 0x01EB
    317a:	8a e7       	ldi	r24, 0x7A	; 122
    317c:	90 e0       	ldi	r25, 0x00	; 0
    317e:	93 ab       	std	Z+51, r25	; 0x33
    3180:	82 ab       	std	Z+50, r24	; 0x32
    3182:	19 be       	out	0x39, r1	; 57
    3184:	17 be       	out	0x37, r1	; 55
    3186:	10 92 7d 00 	sts	0x007D, r1
    318a:	6f 98       	cbi	0x0d, 7	; 13
    318c:	8a b1       	in	r24, 0x0a	; 10
    318e:	8f 71       	andi	r24, 0x1F	; 31
    3190:	8a b9       	out	0x0a, r24	; 10
    3192:	ea e9       	ldi	r30, 0x9A	; 154
    3194:	f0 e0       	ldi	r31, 0x00	; 0
    3196:	80 81       	ld	r24, Z
    3198:	8f 71       	andi	r24, 0x1F	; 31
    319a:	80 83       	st	Z, r24
    319c:	37 98       	cbi	0x06, 7	; 6
    319e:	47 98       	cbi	0x08, 7	; 8
    31a0:	e3 98       	cbi	0x1c, 3	; 28
    31a2:	0e 94 d4 18 	call	0x31a8	; 0x31a8 <_ZN6System13ATMega128_NIC4initEv>
    31a6:	08 95       	ret

000031a8 <_ZN6System13ATMega128_NIC4initEv>:
    31a8:	80 e0       	ldi	r24, 0x00	; 0
    31aa:	90 e0       	ldi	r25, 0x00	; 0
    31ac:	0e 94 d9 19 	call	0x33b2	; 0x33b2 <_ZN6System4CMAC4initEj>
    31b0:	08 95       	ret

000031b2 <_ZN6System6System4initEv>:
    31b2:	0e 94 8a 1a 	call	0x3514	; 0x3514 <_ZN6System5Alarm4initEv>
    31b6:	0e 94 de 18 	call	0x31bc	; 0x31bc <_ZN6System4Task4initEv>
    31ba:	08 95       	ret

000031bc <_ZN6System4Task4initEv>:
    31bc:	08 95       	ret

000031be <_ZN6System6Thread4initEv>:
    31be:	9f 92       	push	r9
    31c0:	af 92       	push	r10
    31c2:	bf 92       	push	r11
    31c4:	cf 92       	push	r12
    31c6:	df 92       	push	r13
    31c8:	ef 92       	push	r14
    31ca:	ff 92       	push	r15
    31cc:	0f 93       	push	r16
    31ce:	1f 93       	push	r17
    31d0:	cf 93       	push	r28
    31d2:	df 93       	push	r29
    31d4:	cd b7       	in	r28, 0x3d	; 61
    31d6:	de b7       	in	r29, 0x3e	; 62
    31d8:	60 97       	sbiw	r28, 0x10	; 16
    31da:	0f b6       	in	r0, 0x3f	; 63
    31dc:	f8 94       	cli
    31de:	de bf       	out	0x3e, r29	; 62
    31e0:	0f be       	out	0x3f, r0	; 63
    31e2:	cd bf       	out	0x3d, r28	; 61
    31e4:	e0 91 ea 01 	lds	r30, 0x01EA
    31e8:	f0 91 eb 01 	lds	r31, 0x01EB
    31ec:	c2 a8       	ldd	r12, Z+50	; 0x32
    31ee:	d3 a8       	ldd	r13, Z+51	; 0x33
    31f0:	f8 94       	cli
    31f2:	80 e1       	ldi	r24, 0x10	; 16
    31f4:	97 e2       	ldi	r25, 0x27	; 39
    31f6:	a0 e0       	ldi	r26, 0x00	; 0
    31f8:	b0 e0       	ldi	r27, 0x00	; 0
    31fa:	8d 87       	std	Y+13, r24	; 0x0d
    31fc:	9e 87       	std	Y+14, r25	; 0x0e
    31fe:	af 87       	std	Y+15, r26	; 0x0f
    3200:	b8 8b       	std	Y+16, r27	; 0x10
    3202:	6f e0       	ldi	r22, 0x0F	; 15
    3204:	7b e2       	ldi	r23, 0x2B	; 43
    3206:	ce 01       	movw	r24, r28
    3208:	0d 96       	adiw	r24, 0x0d	; 13
    320a:	0e 94 94 1c 	call	0x3928	; 0x3928 <_ZN6System5Alarm6masterERKmPFvvE>
    320e:	18 86       	std	Y+8, r1	; 0x08
    3210:	1f 82       	std	Y+7, r1	; 0x07
    3212:	1f e9       	ldi	r17, 0x9F	; 159
    3214:	a1 2e       	mov	r10, r17
    3216:	13 e0       	ldi	r17, 0x03	; 3
    3218:	b1 2e       	mov	r11, r17
    321a:	62 e1       	ldi	r22, 0x12	; 18
    321c:	70 e0       	ldi	r23, 0x00	; 0
    321e:	c5 01       	movw	r24, r10
    3220:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
    3224:	8c 01       	movw	r16, r24
    3226:	82 e0       	ldi	r24, 0x02	; 2
    3228:	90 e0       	ldi	r25, 0x00	; 0
    322a:	f8 01       	movw	r30, r16
    322c:	95 83       	std	Z+5, r25	; 0x05
    322e:	84 83       	std	Z+4, r24	; 0x04
    3230:	17 82       	std	Z+7, r1	; 0x07
    3232:	16 82       	std	Z+6, r1	; 0x06
    3234:	11 86       	std	Z+9, r1	; 0x09
    3236:	10 86       	std	Z+8, r1	; 0x08
    3238:	d8 01       	movw	r26, r16
    323a:	1a 96       	adiw	r26, 0x0a	; 10
    323c:	13 87       	std	Z+11, r17	; 0x0b
    323e:	02 87       	std	Z+10, r16	; 0x0a
    3240:	8f 81       	ldd	r24, Y+7	; 0x07
    3242:	98 85       	ldd	r25, Y+8	; 0x08
    3244:	9e 83       	std	Y+6, r25	; 0x06
    3246:	8d 83       	std	Y+5, r24	; 0x05
    3248:	8d 81       	ldd	r24, Y+5	; 0x05
    324a:	9e 81       	ldd	r25, Y+6	; 0x06
    324c:	fd 01       	movw	r30, r26
    324e:	93 83       	std	Z+3, r25	; 0x03
    3250:	82 83       	std	Z+2, r24	; 0x02
    3252:	15 82       	std	Z+5, r1	; 0x05
    3254:	14 82       	std	Z+4, r1	; 0x04
    3256:	17 82       	std	Z+7, r1	; 0x07
    3258:	16 82       	std	Z+6, r1	; 0x06
    325a:	f8 94       	cli
    325c:	60 e0       	ldi	r22, 0x00	; 0
    325e:	71 e0       	ldi	r23, 0x01	; 1
    3260:	c5 01       	movw	r24, r10
    3262:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
    3266:	ac 01       	movw	r20, r24
    3268:	d8 01       	movw	r26, r16
    326a:	8d 93       	st	X+, r24
    326c:	9c 93       	st	X, r25
    326e:	87 e6       	ldi	r24, 0x67	; 103
    3270:	97 e2       	ldi	r25, 0x27	; 39
    3272:	f8 2e       	mov	r15, r24
    3274:	ee 24       	eor	r14, r14
    3276:	89 2f       	mov	r24, r25
    3278:	99 27       	eor	r25, r25
    327a:	e8 2a       	or	r14, r24
    327c:	f9 2a       	or	r15, r25
    327e:	42 50       	subi	r20, 0x02	; 2
    3280:	5f 4f       	sbci	r21, 0xFF	; 255
    3282:	fa 01       	movw	r30, r20
    3284:	f1 82       	std	Z+1, r15	; 0x01
    3286:	e0 82       	st	Z, r14
    3288:	4e 5f       	subi	r20, 0xFE	; 254
    328a:	50 40       	sbci	r21, 0x00	; 0
    328c:	da 01       	movw	r26, r20
    328e:	a4 52       	subi	r26, 0x24	; 36
    3290:	bf 4f       	sbci	r27, 0xFF	; 255
    3292:	f0 e8       	ldi	r31, 0x80	; 128
    3294:	9f 2e       	mov	r9, r31
    3296:	9c 92       	st	X, r9
    3298:	9c 2d       	mov	r25, r12
    329a:	88 27       	eor	r24, r24
    329c:	2d 2d       	mov	r18, r13
    329e:	33 27       	eor	r19, r19
    32a0:	82 2b       	or	r24, r18
    32a2:	93 2b       	or	r25, r19
    32a4:	fd 01       	movw	r30, r26
    32a6:	91 a3       	std	Z+33, r25	; 0x21
    32a8:	80 a3       	std	Z+32, r24	; 0x20
    32aa:	44 8f       	std	Z+28, r20	; 0x1c
    32ac:	85 2f       	mov	r24, r21
    32ae:	99 27       	eor	r25, r25
    32b0:	85 8f       	std	Z+29, r24	; 0x1d
    32b2:	f8 01       	movw	r30, r16
    32b4:	b3 83       	std	Z+3, r27	; 0x03
    32b6:	a2 83       	std	Z+2, r26	; 0x02
    32b8:	dc 86       	std	Y+12, r13	; 0x0c
    32ba:	cb 86       	std	Y+11, r12	; 0x0b
    32bc:	40 e0       	ldi	r20, 0x00	; 0
    32be:	51 e0       	ldi	r21, 0x01	; 1
    32c0:	be 01       	movw	r22, r28
    32c2:	65 5f       	subi	r22, 0xF5	; 245
    32c4:	7f 4f       	sbci	r23, 0xFF	; 255
    32c6:	c8 01       	movw	r24, r16
    32c8:	0e 94 f3 21 	call	0x43e6	; 0x43e6 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj>
    32cc:	8f ef       	ldi	r24, 0xFF	; 255
    32ce:	9f e7       	ldi	r25, 0x7F	; 127
    32d0:	9c 83       	std	Y+4, r25	; 0x04
    32d2:	8b 83       	std	Y+3, r24	; 0x03
    32d4:	62 e1       	ldi	r22, 0x12	; 18
    32d6:	70 e0       	ldi	r23, 0x00	; 0
    32d8:	c5 01       	movw	r24, r10
    32da:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
    32de:	8c 01       	movw	r16, r24
    32e0:	81 e0       	ldi	r24, 0x01	; 1
    32e2:	90 e0       	ldi	r25, 0x00	; 0
    32e4:	f8 01       	movw	r30, r16
    32e6:	95 83       	std	Z+5, r25	; 0x05
    32e8:	84 83       	std	Z+4, r24	; 0x04
    32ea:	17 82       	std	Z+7, r1	; 0x07
    32ec:	16 82       	std	Z+6, r1	; 0x06
    32ee:	11 86       	std	Z+9, r1	; 0x09
    32f0:	10 86       	std	Z+8, r1	; 0x08
    32f2:	d8 01       	movw	r26, r16
    32f4:	1a 96       	adiw	r26, 0x0a	; 10
    32f6:	13 87       	std	Z+11, r17	; 0x0b
    32f8:	02 87       	std	Z+10, r16	; 0x0a
    32fa:	8b 81       	ldd	r24, Y+3	; 0x03
    32fc:	9c 81       	ldd	r25, Y+4	; 0x04
    32fe:	9a 83       	std	Y+2, r25	; 0x02
    3300:	89 83       	std	Y+1, r24	; 0x01
    3302:	89 81       	ldd	r24, Y+1	; 0x01
    3304:	9a 81       	ldd	r25, Y+2	; 0x02
    3306:	fd 01       	movw	r30, r26
    3308:	93 83       	std	Z+3, r25	; 0x03
    330a:	82 83       	std	Z+2, r24	; 0x02
    330c:	15 82       	std	Z+5, r1	; 0x05
    330e:	14 82       	std	Z+4, r1	; 0x04
    3310:	17 82       	std	Z+7, r1	; 0x07
    3312:	16 82       	std	Z+6, r1	; 0x06
    3314:	f8 94       	cli
    3316:	60 e0       	ldi	r22, 0x00	; 0
    3318:	71 e0       	ldi	r23, 0x01	; 1
    331a:	c5 01       	movw	r24, r10
    331c:	0e 94 ce 13 	call	0x279c	; 0x279c <_ZN6System4Heap5allocEj>
    3320:	bc 01       	movw	r22, r24
    3322:	d8 01       	movw	r26, r16
    3324:	8d 93       	st	X+, r24
    3326:	9c 93       	st	X, r25
    3328:	62 50       	subi	r22, 0x02	; 2
    332a:	7f 4f       	sbci	r23, 0xFF	; 255
    332c:	fb 01       	movw	r30, r22
    332e:	f1 82       	std	Z+1, r15	; 0x01
    3330:	e0 82       	st	Z, r14
    3332:	6e 5f       	subi	r22, 0xFE	; 254
    3334:	70 40       	sbci	r23, 0x00	; 0
    3336:	41 eb       	ldi	r20, 0xB1	; 177
    3338:	55 e2       	ldi	r21, 0x25	; 37
    333a:	db 01       	movw	r26, r22
    333c:	a4 52       	subi	r26, 0x24	; 36
    333e:	bf 4f       	sbci	r27, 0xFF	; 255
    3340:	9c 92       	st	X, r9
    3342:	94 2f       	mov	r25, r20
    3344:	88 27       	eor	r24, r24
    3346:	25 2f       	mov	r18, r21
    3348:	33 27       	eor	r19, r19
    334a:	82 2b       	or	r24, r18
    334c:	93 2b       	or	r25, r19
    334e:	fd 01       	movw	r30, r26
    3350:	91 a3       	std	Z+33, r25	; 0x21
    3352:	80 a3       	std	Z+32, r24	; 0x20
    3354:	64 8f       	std	Z+28, r22	; 0x1c
    3356:	87 2f       	mov	r24, r23
    3358:	99 27       	eor	r25, r25
    335a:	85 8f       	std	Z+29, r24	; 0x1d
    335c:	f8 01       	movw	r30, r16
    335e:	b3 83       	std	Z+3, r27	; 0x03
    3360:	a2 83       	std	Z+2, r26	; 0x02
    3362:	5a 87       	std	Y+10, r21	; 0x0a
    3364:	49 87       	std	Y+9, r20	; 0x09
    3366:	40 e0       	ldi	r20, 0x00	; 0
    3368:	51 e0       	ldi	r21, 0x01	; 1
    336a:	be 01       	movw	r22, r28
    336c:	67 5f       	subi	r22, 0xF7	; 247
    336e:	7f 4f       	sbci	r23, 0xFF	; 255
    3370:	c8 01       	movw	r24, r16
    3372:	0e 94 f3 21 	call	0x43e6	; 0x43e6 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj>
    3376:	e0 91 c6 03 	lds	r30, 0x03C6
    337a:	f0 91 c7 03 	lds	r31, 0x03C7
    337e:	01 90       	ld	r0, Z+
    3380:	f0 81       	ld	r31, Z
    3382:	e0 2d       	mov	r30, r0
    3384:	82 81       	ldd	r24, Z+2	; 0x02
    3386:	93 81       	ldd	r25, Z+3	; 0x03
    3388:	0e 94 ad 31 	call	0x635a	; 0x635a <_ZNVK6System4AVR87Context4loadEv>
    338c:	78 94       	sei
    338e:	60 96       	adiw	r28, 0x10	; 16
    3390:	0f b6       	in	r0, 0x3f	; 63
    3392:	f8 94       	cli
    3394:	de bf       	out	0x3e, r29	; 62
    3396:	0f be       	out	0x3f, r0	; 63
    3398:	cd bf       	out	0x3d, r28	; 61
    339a:	df 91       	pop	r29
    339c:	cf 91       	pop	r28
    339e:	1f 91       	pop	r17
    33a0:	0f 91       	pop	r16
    33a2:	ff 90       	pop	r15
    33a4:	ef 90       	pop	r14
    33a6:	df 90       	pop	r13
    33a8:	cf 90       	pop	r12
    33aa:	bf 90       	pop	r11
    33ac:	af 90       	pop	r10
    33ae:	9f 90       	pop	r9
    33b0:	08 95       	ret

000033b2 <_ZN6System4CMAC4initEj>:
    33b2:	8a ed       	ldi	r24, 0xDA	; 218
    33b4:	9d e2       	ldi	r25, 0x2D	; 45
    33b6:	90 93 54 04 	sts	0x0454, r25
    33ba:	80 93 53 04 	sts	0x0453, r24
    33be:	86 eb       	ldi	r24, 0xB6	; 182
    33c0:	9e e2       	ldi	r25, 0x2E	; 46
    33c2:	90 93 64 04 	sts	0x0464, r25
    33c6:	80 93 63 04 	sts	0x0463, r24
    33ca:	80 ef       	ldi	r24, 0xF0	; 240
    33cc:	83 bd       	out	0x23, r24	; 35
    33ce:	8d e0       	ldi	r24, 0x0D	; 13
    33d0:	85 bd       	out	0x25, r24	; 37
    33d2:	87 b7       	in	r24, 0x37	; 55
    33d4:	80 68       	ori	r24, 0x80	; 128
    33d6:	87 bf       	out	0x37, r24	; 55
    33d8:	08 95       	ret

000033da <_ZN6System12ATMega128_IC6enableEj>:
    33da:	bc 01       	movw	r22, r24
    33dc:	89 30       	cpi	r24, 0x09	; 9
    33de:	91 05       	cpc	r25, r1
    33e0:	68 f4       	brcc	.+26     	; 0x33fc <_ZN6System12ATMega128_IC6enableEj+0x22>
    33e2:	29 b7       	in	r18, 0x39	; 57
    33e4:	61 50       	subi	r22, 0x01	; 1
    33e6:	70 40       	sbci	r23, 0x00	; 0
    33e8:	81 e0       	ldi	r24, 0x01	; 1
    33ea:	90 e0       	ldi	r25, 0x00	; 0
    33ec:	02 c0       	rjmp	.+4      	; 0x33f2 <_ZN6System12ATMega128_IC6enableEj+0x18>
    33ee:	88 0f       	add	r24, r24
    33f0:	99 1f       	adc	r25, r25
    33f2:	6a 95       	dec	r22
    33f4:	e2 f7       	brpl	.-8      	; 0x33ee <_ZN6System12ATMega128_IC6enableEj+0x14>
    33f6:	28 2b       	or	r18, r24
    33f8:	29 bf       	out	0x39, r18	; 57
    33fa:	08 95       	ret
    33fc:	81 31       	cpi	r24, 0x11	; 17
    33fe:	91 05       	cpc	r25, r1
    3400:	b8 f0       	brcs	.+46     	; 0x3430 <_ZN6System12ATMega128_IC6enableEj+0x56>
    3402:	88 31       	cpi	r24, 0x18	; 24
    3404:	91 05       	cpc	r25, r1
    3406:	19 f1       	breq	.+70     	; 0x344e <_ZN6System12ATMega128_IC6enableEj+0x74>
    3408:	89 31       	cpi	r24, 0x19	; 25
    340a:	91 05       	cpc	r25, r1
    340c:	30 f5       	brcc	.+76     	; 0x345a <_ZN6System12ATMega128_IC6enableEj+0x80>
    340e:	84 31       	cpi	r24, 0x14	; 20
    3410:	91 05       	cpc	r25, r1
    3412:	09 f4       	brne	.+2      	; 0x3416 <_ZN6System12ATMega128_IC6enableEj+0x3c>
    3414:	57 c0       	rjmp	.+174    	; 0x34c4 <_ZN6System12ATMega128_IC6enableEj+0xea>
    3416:	85 31       	cpi	r24, 0x15	; 21
    3418:	91 05       	cpc	r25, r1
    341a:	08 f4       	brcc	.+2      	; 0x341e <_ZN6System12ATMega128_IC6enableEj+0x44>
    341c:	3d c0       	rjmp	.+122    	; 0x3498 <_ZN6System12ATMega128_IC6enableEj+0xbe>
    341e:	86 31       	cpi	r24, 0x16	; 22
    3420:	91 05       	cpc	r25, r1
    3422:	09 f4       	brne	.+2      	; 0x3426 <_ZN6System12ATMega128_IC6enableEj+0x4c>
    3424:	61 c0       	rjmp	.+194    	; 0x34e8 <_ZN6System12ATMega128_IC6enableEj+0x10e>
    3426:	47 97       	sbiw	r24, 0x17	; 23
    3428:	08 f4       	brcc	.+2      	; 0x342c <_ZN6System12ATMega128_IC6enableEj+0x52>
    342a:	5c c0       	rjmp	.+184    	; 0x34e4 <_ZN6System12ATMega128_IC6enableEj+0x10a>
    342c:	47 9a       	sbi	0x08, 7	; 8
    342e:	08 95       	ret
    3430:	47 b7       	in	r20, 0x37	; 55
    3432:	20 e1       	ldi	r18, 0x10	; 16
    3434:	30 e0       	ldi	r19, 0x00	; 0
    3436:	28 1b       	sub	r18, r24
    3438:	39 0b       	sbc	r19, r25
    343a:	81 e0       	ldi	r24, 0x01	; 1
    343c:	90 e0       	ldi	r25, 0x00	; 0
    343e:	02 c0       	rjmp	.+4      	; 0x3444 <_ZN6System12ATMega128_IC6enableEj+0x6a>
    3440:	88 0f       	add	r24, r24
    3442:	99 1f       	adc	r25, r25
    3444:	2a 95       	dec	r18
    3446:	e2 f7       	brpl	.-8      	; 0x3440 <_ZN6System12ATMega128_IC6enableEj+0x66>
    3448:	48 2b       	or	r20, r24
    344a:	47 bf       	out	0x37, r20	; 55
    344c:	08 95       	ret
    344e:	80 91 7d 00 	lds	r24, 0x007D
    3452:	81 60       	ori	r24, 0x01	; 1
    3454:	80 93 7d 00 	sts	0x007D, r24
    3458:	08 95       	ret
    345a:	8c 31       	cpi	r24, 0x1C	; 28
    345c:	91 05       	cpc	r25, r1
    345e:	61 f1       	breq	.+88     	; 0x34b8 <_ZN6System12ATMega128_IC6enableEj+0xde>
    3460:	8d 31       	cpi	r24, 0x1D	; 29
    3462:	91 05       	cpc	r25, r1
    3464:	70 f0       	brcs	.+28     	; 0x3482 <_ZN6System12ATMega128_IC6enableEj+0xa8>
    3466:	8e 31       	cpi	r24, 0x1E	; 30
    3468:	91 05       	cpc	r25, r1
    346a:	09 f4       	brne	.+2      	; 0x346e <_ZN6System12ATMega128_IC6enableEj+0x94>
    346c:	3f c0       	rjmp	.+126    	; 0x34ec <_ZN6System12ATMega128_IC6enableEj+0x112>
    346e:	8e 31       	cpi	r24, 0x1E	; 30
    3470:	91 05       	cpc	r25, r1
    3472:	50 f1       	brcs	.+84     	; 0x34c8 <_ZN6System12ATMega128_IC6enableEj+0xee>
    3474:	8f 31       	cpi	r24, 0x1F	; 31
    3476:	91 05       	cpc	r25, r1
    3478:	09 f4       	brne	.+2      	; 0x347c <_ZN6System12ATMega128_IC6enableEj+0xa2>
    347a:	46 c0       	rjmp	.+140    	; 0x3508 <_ZN6System12ATMega128_IC6enableEj+0x12e>
    347c:	80 97       	sbiw	r24, 0x20	; 32
    347e:	b1 f0       	breq	.+44     	; 0x34ac <_ZN6System12ATMega128_IC6enableEj+0xd2>
    3480:	08 95       	ret
    3482:	8a 31       	cpi	r24, 0x1A	; 26
    3484:	91 05       	cpc	r25, r1
    3486:	d1 f1       	breq	.+116    	; 0x34fc <_ZN6System12ATMega128_IC6enableEj+0x122>
    3488:	4b 97       	sbiw	r24, 0x1b	; 27
    348a:	30 f1       	brcs	.+76     	; 0x34d8 <_ZN6System12ATMega128_IC6enableEj+0xfe>
    348c:	80 91 7d 00 	lds	r24, 0x007D
    3490:	88 60       	ori	r24, 0x08	; 8
    3492:	80 93 7d 00 	sts	0x007D, r24
    3496:	08 95       	ret
    3498:	82 31       	cpi	r24, 0x12	; 18
    349a:	91 05       	cpc	r25, r1
    349c:	69 f1       	breq	.+90     	; 0x34f8 <_ZN6System12ATMega128_IC6enableEj+0x11e>
    349e:	83 31       	cpi	r24, 0x13	; 19
    34a0:	91 05       	cpc	r25, r1
    34a2:	c0 f4       	brcc	.+48     	; 0x34d4 <_ZN6System12ATMega128_IC6enableEj+0xfa>
    34a4:	41 97       	sbiw	r24, 0x11	; 17
    34a6:	61 f7       	brne	.-40     	; 0x3480 <_ZN6System12ATMega128_IC6enableEj+0xa6>
    34a8:	6f 9a       	sbi	0x0d, 7	; 13
    34aa:	08 95       	ret
    34ac:	80 91 9a 00 	lds	r24, 0x009A
    34b0:	80 64       	ori	r24, 0x40	; 64
    34b2:	80 93 9a 00 	sts	0x009A, r24
    34b6:	08 95       	ret
    34b8:	80 91 7d 00 	lds	r24, 0x007D
    34bc:	82 60       	ori	r24, 0x02	; 2
    34be:	80 93 7d 00 	sts	0x007D, r24
    34c2:	08 95       	ret
    34c4:	56 9a       	sbi	0x0a, 6	; 10
    34c6:	08 95       	ret
    34c8:	80 91 7d 00 	lds	r24, 0x007D
    34cc:	84 60       	ori	r24, 0x04	; 4
    34ce:	80 93 7d 00 	sts	0x007D, r24
    34d2:	08 95       	ret
    34d4:	55 9a       	sbi	0x0a, 5	; 10
    34d6:	08 95       	ret
    34d8:	80 91 7d 00 	lds	r24, 0x007D
    34dc:	80 62       	ori	r24, 0x20	; 32
    34de:	80 93 7d 00 	sts	0x007D, r24
    34e2:	08 95       	ret
    34e4:	37 9a       	sbi	0x06, 7	; 6
    34e6:	08 95       	ret
    34e8:	e3 9a       	sbi	0x1c, 3	; 28
    34ea:	08 95       	ret
    34ec:	80 91 9a 00 	lds	r24, 0x009A
    34f0:	80 68       	ori	r24, 0x80	; 128
    34f2:	80 93 9a 00 	sts	0x009A, r24
    34f6:	08 95       	ret
    34f8:	57 9a       	sbi	0x0a, 7	; 10
    34fa:	08 95       	ret
    34fc:	80 91 7d 00 	lds	r24, 0x007D
    3500:	80 61       	ori	r24, 0x10	; 16
    3502:	80 93 7d 00 	sts	0x007D, r24
    3506:	08 95       	ret
    3508:	80 91 9a 00 	lds	r24, 0x009A
    350c:	80 62       	ori	r24, 0x20	; 32
    350e:	80 93 9a 00 	sts	0x009A, r24
    3512:	08 95       	ret

00003514 <_ZN6System5Alarm4initEv>:
    3514:	f8 94       	cli
    3516:	87 e0       	ldi	r24, 0x07	; 7
    3518:	9d e1       	ldi	r25, 0x1D	; 29
    351a:	90 93 60 04 	sts	0x0460, r25
    351e:	80 93 5f 04 	sts	0x045F, r24
    3522:	8a e0       	ldi	r24, 0x0A	; 10
    3524:	81 bf       	out	0x31, r24	; 49
    3526:	8f e0       	ldi	r24, 0x0F	; 15
    3528:	83 bf       	out	0x33, r24	; 51
    352a:	8f e0       	ldi	r24, 0x0F	; 15
    352c:	90 e0       	ldi	r25, 0x00	; 0
    352e:	0e 94 ed 19 	call	0x33da	; 0x33da <_ZN6System12ATMega128_IC6enableEj>
    3532:	78 94       	sei
    3534:	80 e0       	ldi	r24, 0x00	; 0
    3536:	90 e0       	ldi	r25, 0x00	; 0
    3538:	08 95       	ret

0000353a <_ZN6System14Handler_ThreadclEv>:
    353a:	fc 01       	movw	r30, r24
    353c:	82 81       	ldd	r24, Z+2	; 0x02
    353e:	93 81       	ldd	r25, Z+3	; 0x03
    3540:	0e 94 bb 22 	call	0x4576	; 0x4576 <_ZN6System6Thread6resumeEv>
    3544:	08 95       	ret

00003546 <_Z41__static_initialization_and_destruction_0ii>:
    3546:	6f 5f       	subi	r22, 0xFF	; 255
    3548:	7f 4f       	sbci	r23, 0xFF	; 255
    354a:	09 f0       	breq	.+2      	; 0x354e <_Z41__static_initialization_and_destruction_0ii+0x8>
    354c:	08 95       	ret
    354e:	01 97       	sbiw	r24, 0x01	; 1
    3550:	e9 f7       	brne	.-6      	; 0x354c <_Z41__static_initialization_and_destruction_0ii+0x6>
    3552:	10 92 ac 03 	sts	0x03AC, r1
    3556:	10 92 ab 03 	sts	0x03AB, r1
    355a:	10 92 ae 03 	sts	0x03AE, r1
    355e:	10 92 ad 03 	sts	0x03AD, r1
    3562:	10 92 b0 03 	sts	0x03B0, r1
    3566:	10 92 af 03 	sts	0x03AF, r1
    356a:	08 95       	ret

0000356c <_GLOBAL__I__ZN6System5Alarm6_timerE>:
    356c:	6f ef       	ldi	r22, 0xFF	; 255
    356e:	7f ef       	ldi	r23, 0xFF	; 255
    3570:	81 e0       	ldi	r24, 0x01	; 1
    3572:	90 e0       	ldi	r25, 0x00	; 0
    3574:	0e 94 a3 1a 	call	0x3546	; 0x3546 <_Z41__static_initialization_and_destruction_0ii>
    3578:	08 95       	ret

0000357a <_ZN6System7HandlerD1Ev>:
    357a:	fc 01       	movw	r30, r24
    357c:	8b e0       	ldi	r24, 0x0B	; 11
    357e:	92 e0       	ldi	r25, 0x02	; 2
    3580:	91 83       	std	Z+1, r25	; 0x01
    3582:	80 83       	st	Z, r24
    3584:	08 95       	ret

00003586 <_ZN6System7HandlerD0Ev>:
    3586:	fc 01       	movw	r30, r24
    3588:	8b e0       	ldi	r24, 0x0B	; 11
    358a:	92 e0       	ldi	r25, 0x02	; 2
    358c:	91 83       	std	Z+1, r25	; 0x01
    358e:	80 83       	st	Z, r24
    3590:	08 95       	ret

00003592 <_ZN6System14Handler_ThreadD1Ev>:
    3592:	fc 01       	movw	r30, r24
    3594:	8b e0       	ldi	r24, 0x0B	; 11
    3596:	92 e0       	ldi	r25, 0x02	; 2
    3598:	91 83       	std	Z+1, r25	; 0x01
    359a:	80 83       	st	Z, r24
    359c:	08 95       	ret

0000359e <_ZN6System14Handler_ThreadD0Ev>:
    359e:	fc 01       	movw	r30, r24
    35a0:	8b e0       	ldi	r24, 0x0B	; 11
    35a2:	92 e0       	ldi	r25, 0x02	; 2
    35a4:	91 83       	std	Z+1, r25	; 0x01
    35a6:	80 83       	st	Z, r24
    35a8:	08 95       	ret

000035aa <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_>:
    35aa:	cf 93       	push	r28
    35ac:	df 93       	push	r29
    35ae:	ac 01       	movw	r20, r24
    35b0:	db 01       	movw	r26, r22
    35b2:	ec 01       	movw	r28, r24
    35b4:	88 81       	ld	r24, Y
    35b6:	99 81       	ldd	r25, Y+1	; 0x01
    35b8:	89 2b       	or	r24, r25
    35ba:	e9 f1       	breq	.+122    	; 0x3636 <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0x8c>
    35bc:	fa 01       	movw	r30, r20
    35be:	c2 81       	ldd	r28, Z+2	; 0x02
    35c0:	d3 81       	ldd	r29, Z+3	; 0x03
    35c2:	2a 81       	ldd	r18, Y+2	; 0x02
    35c4:	3b 81       	ldd	r19, Y+3	; 0x03
    35c6:	fb 01       	movw	r30, r22
    35c8:	82 81       	ldd	r24, Z+2	; 0x02
    35ca:	93 81       	ldd	r25, Z+3	; 0x03
    35cc:	82 17       	cp	r24, r18
    35ce:	93 07       	cpc	r25, r19
    35d0:	a4 f0       	brlt	.+40     	; 0x35fa <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0x50>
    35d2:	ee 81       	ldd	r30, Y+6	; 0x06
    35d4:	ff 81       	ldd	r31, Y+7	; 0x07
    35d6:	30 97       	sbiw	r30, 0x00	; 0
    35d8:	81 f0       	breq	.+32     	; 0x35fa <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0x50>
    35da:	82 1b       	sub	r24, r18
    35dc:	93 0b       	sbc	r25, r19
    35de:	ed 01       	movw	r28, r26
    35e0:	9b 83       	std	Y+3, r25	; 0x03
    35e2:	8a 83       	std	Y+2, r24	; 0x02
    35e4:	ef 01       	movw	r28, r30
    35e6:	22 81       	ldd	r18, Z+2	; 0x02
    35e8:	33 81       	ldd	r19, Z+3	; 0x03
    35ea:	82 17       	cp	r24, r18
    35ec:	93 07       	cpc	r25, r19
    35ee:	2c f0       	brlt	.+10     	; 0x35fa <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0x50>
    35f0:	06 80       	ldd	r0, Z+6	; 0x06
    35f2:	f7 81       	ldd	r31, Z+7	; 0x07
    35f4:	e0 2d       	mov	r30, r0
    35f6:	30 97       	sbiw	r30, 0x00	; 0
    35f8:	81 f7       	brne	.-32     	; 0x35da <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0x30>
    35fa:	82 17       	cp	r24, r18
    35fc:	93 07       	cpc	r25, r19
    35fe:	4c f5       	brge	.+82     	; 0x3652 <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0xa8>
    3600:	6c 81       	ldd	r22, Y+4	; 0x04
    3602:	7d 81       	ldd	r23, Y+5	; 0x05
    3604:	61 15       	cp	r22, r1
    3606:	71 05       	cpc	r23, r1
    3608:	09 f4       	brne	.+2      	; 0x360c <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0x62>
    360a:	41 c0       	rjmp	.+130    	; 0x368e <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0xe4>
    360c:	28 1b       	sub	r18, r24
    360e:	39 0b       	sbc	r19, r25
    3610:	3b 83       	std	Y+3, r19	; 0x03
    3612:	2a 83       	std	Y+2, r18	; 0x02
    3614:	fb 01       	movw	r30, r22
    3616:	b7 83       	std	Z+7, r27	; 0x07
    3618:	a6 83       	std	Z+6, r26	; 0x06
    361a:	bd 83       	std	Y+5, r27	; 0x05
    361c:	ac 83       	std	Y+4, r26	; 0x04
    361e:	fd 01       	movw	r30, r26
    3620:	75 83       	std	Z+5, r23	; 0x05
    3622:	64 83       	std	Z+4, r22	; 0x04
    3624:	d7 83       	std	Z+7, r29	; 0x07
    3626:	c6 83       	std	Z+6, r28	; 0x06
    3628:	ea 01       	movw	r28, r20
    362a:	88 81       	ld	r24, Y
    362c:	99 81       	ldd	r25, Y+1	; 0x01
    362e:	01 96       	adiw	r24, 0x01	; 1
    3630:	99 83       	std	Y+1, r25	; 0x01
    3632:	88 83       	st	Y, r24
    3634:	4a c0       	rjmp	.+148    	; 0x36ca <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0x120>
    3636:	fd 01       	movw	r30, r26
    3638:	15 82       	std	Z+5, r1	; 0x05
    363a:	14 82       	std	Z+4, r1	; 0x04
    363c:	17 82       	std	Z+7, r1	; 0x07
    363e:	16 82       	std	Z+6, r1	; 0x06
    3640:	bb 83       	std	Y+3, r27	; 0x03
    3642:	aa 83       	std	Y+2, r26	; 0x02
    3644:	bd 83       	std	Y+5, r27	; 0x05
    3646:	ac 83       	std	Y+4, r26	; 0x04
    3648:	81 e0       	ldi	r24, 0x01	; 1
    364a:	90 e0       	ldi	r25, 0x00	; 0
    364c:	99 83       	std	Y+1, r25	; 0x01
    364e:	88 83       	st	Y, r24
    3650:	3c c0       	rjmp	.+120    	; 0x36ca <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0x120>
    3652:	82 1b       	sub	r24, r18
    3654:	93 0b       	sbc	r25, r19
    3656:	fd 01       	movw	r30, r26
    3658:	93 83       	std	Z+3, r25	; 0x03
    365a:	82 83       	std	Z+2, r24	; 0x02
    365c:	ea 01       	movw	r28, r20
    365e:	28 81       	ld	r18, Y
    3660:	39 81       	ldd	r19, Y+1	; 0x01
    3662:	21 15       	cp	r18, r1
    3664:	31 05       	cpc	r19, r1
    3666:	41 f3       	breq	.-48     	; 0x3638 <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0x8e>
    3668:	ea 01       	movw	r28, r20
    366a:	ec 81       	ldd	r30, Y+4	; 0x04
    366c:	fd 81       	ldd	r31, Y+5	; 0x05
    366e:	b7 83       	std	Z+7, r27	; 0x07
    3670:	a6 83       	std	Z+6, r26	; 0x06
    3672:	8c 81       	ldd	r24, Y+4	; 0x04
    3674:	9d 81       	ldd	r25, Y+5	; 0x05
    3676:	fd 01       	movw	r30, r26
    3678:	95 83       	std	Z+5, r25	; 0x05
    367a:	84 83       	std	Z+4, r24	; 0x04
    367c:	17 82       	std	Z+7, r1	; 0x07
    367e:	16 82       	std	Z+6, r1	; 0x06
    3680:	bd 83       	std	Y+5, r27	; 0x05
    3682:	ac 83       	std	Y+4, r26	; 0x04
    3684:	2f 5f       	subi	r18, 0xFF	; 255
    3686:	3f 4f       	sbci	r19, 0xFF	; 255
    3688:	39 83       	std	Y+1, r19	; 0x01
    368a:	28 83       	st	Y, r18
    368c:	1e c0       	rjmp	.+60     	; 0x36ca <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0x120>
    368e:	28 1b       	sub	r18, r24
    3690:	39 0b       	sbc	r19, r25
    3692:	3b 83       	std	Y+3, r19	; 0x03
    3694:	2a 83       	std	Y+2, r18	; 0x02
    3696:	ea 01       	movw	r28, r20
    3698:	28 81       	ld	r18, Y
    369a:	39 81       	ldd	r19, Y+1	; 0x01
    369c:	21 15       	cp	r18, r1
    369e:	31 05       	cpc	r19, r1
    36a0:	51 f2       	breq	.-108    	; 0x3636 <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_+0x8c>
    36a2:	ed 01       	movw	r28, r26
    36a4:	1d 82       	std	Y+5, r1	; 0x05
    36a6:	1c 82       	std	Y+4, r1	; 0x04
    36a8:	fa 01       	movw	r30, r20
    36aa:	82 81       	ldd	r24, Z+2	; 0x02
    36ac:	93 81       	ldd	r25, Z+3	; 0x03
    36ae:	9f 83       	std	Y+7, r25	; 0x07
    36b0:	8e 83       	std	Y+6, r24	; 0x06
    36b2:	02 80       	ldd	r0, Z+2	; 0x02
    36b4:	f3 81       	ldd	r31, Z+3	; 0x03
    36b6:	e0 2d       	mov	r30, r0
    36b8:	b5 83       	std	Z+5, r27	; 0x05
    36ba:	a4 83       	std	Z+4, r26	; 0x04
    36bc:	ea 01       	movw	r28, r20
    36be:	bb 83       	std	Y+3, r27	; 0x03
    36c0:	aa 83       	std	Y+2, r26	; 0x02
    36c2:	2f 5f       	subi	r18, 0xFF	; 255
    36c4:	3f 4f       	sbci	r19, 0xFF	; 255
    36c6:	39 83       	std	Y+1, r19	; 0x01
    36c8:	28 83       	st	Y, r18
    36ca:	df 91       	pop	r29
    36cc:	cf 91       	pop	r28
    36ce:	08 95       	ret

000036d0 <_ZN6System5AlarmD1Ev>:
    36d0:	cf 93       	push	r28
    36d2:	df 93       	push	r29
    36d4:	9c 01       	movw	r18, r24
    36d6:	f8 94       	cli
    36d8:	e0 91 ad 03 	lds	r30, 0x03AD
    36dc:	f0 91 ae 03 	lds	r31, 0x03AE
    36e0:	30 97       	sbiw	r30, 0x00	; 0
    36e2:	09 f4       	brne	.+2      	; 0x36e6 <_ZN6System5AlarmD1Ev+0x16>
    36e4:	87 c0       	rjmp	.+270    	; 0x37f4 <_ZN6System5AlarmD1Ev+0x124>
    36e6:	80 81       	ld	r24, Z
    36e8:	91 81       	ldd	r25, Z+1	; 0x01
    36ea:	28 17       	cp	r18, r24
    36ec:	39 07       	cpc	r19, r25
    36ee:	09 f4       	brne	.+2      	; 0x36f2 <_ZN6System5AlarmD1Ev+0x22>
    36f0:	4a c0       	rjmp	.+148    	; 0x3786 <_ZN6System5AlarmD1Ev+0xb6>
    36f2:	df 01       	movw	r26, r30
    36f4:	ed 01       	movw	r28, r26
    36f6:	ae 81       	ldd	r26, Y+6	; 0x06
    36f8:	bf 81       	ldd	r27, Y+7	; 0x07
    36fa:	10 97       	sbiw	r26, 0x00	; 0
    36fc:	09 f4       	brne	.+2      	; 0x3700 <_ZN6System5AlarmD1Ev+0x30>
    36fe:	7a c0       	rjmp	.+244    	; 0x37f4 <_ZN6System5AlarmD1Ev+0x124>
    3700:	8d 91       	ld	r24, X+
    3702:	9c 91       	ld	r25, X
    3704:	11 97       	sbiw	r26, 0x01	; 1
    3706:	28 17       	cp	r18, r24
    3708:	39 07       	cpc	r19, r25
    370a:	a1 f7       	brne	.-24     	; 0x36f4 <_ZN6System5AlarmD1Ev+0x24>
    370c:	80 91 ab 03 	lds	r24, 0x03AB
    3710:	90 91 ac 03 	lds	r25, 0x03AC
    3714:	81 30       	cpi	r24, 0x01	; 1
    3716:	91 05       	cpc	r25, r1
    3718:	29 f1       	breq	.+74     	; 0x3764 <_ZN6System5AlarmD1Ev+0x94>
    371a:	ed 01       	movw	r28, r26
    371c:	2c 81       	ldd	r18, Y+4	; 0x04
    371e:	3d 81       	ldd	r19, Y+5	; 0x05
    3720:	21 15       	cp	r18, r1
    3722:	31 05       	cpc	r19, r1
    3724:	c9 f1       	breq	.+114    	; 0x3798 <_ZN6System5AlarmD1Ev+0xc8>
    3726:	ed 01       	movw	r28, r26
    3728:	ee 81       	ldd	r30, Y+6	; 0x06
    372a:	ff 81       	ldd	r31, Y+7	; 0x07
    372c:	30 97       	sbiw	r30, 0x00	; 0
    372e:	d1 f1       	breq	.+116    	; 0x37a4 <_ZN6System5AlarmD1Ev+0xd4>
    3730:	e9 01       	movw	r28, r18
    3732:	ff 83       	std	Y+7, r31	; 0x07
    3734:	ee 83       	std	Y+6, r30	; 0x06
    3736:	ed 01       	movw	r28, r26
    3738:	ee 81       	ldd	r30, Y+6	; 0x06
    373a:	ff 81       	ldd	r31, Y+7	; 0x07
    373c:	35 83       	std	Z+5, r19	; 0x05
    373e:	24 83       	std	Z+4, r18	; 0x04
    3740:	01 97       	sbiw	r24, 0x01	; 1
    3742:	90 93 ac 03 	sts	0x03AC, r25
    3746:	80 93 ab 03 	sts	0x03AB, r24
    374a:	30 97       	sbiw	r30, 0x00	; 0
    374c:	09 f4       	brne	.+2      	; 0x3750 <_ZN6System5AlarmD1Ev+0x80>
    374e:	52 c0       	rjmp	.+164    	; 0x37f4 <_ZN6System5AlarmD1Ev+0x124>
    3750:	82 81       	ldd	r24, Z+2	; 0x02
    3752:	93 81       	ldd	r25, Z+3	; 0x03
    3754:	ed 01       	movw	r28, r26
    3756:	2a 81       	ldd	r18, Y+2	; 0x02
    3758:	3b 81       	ldd	r19, Y+3	; 0x03
    375a:	82 0f       	add	r24, r18
    375c:	93 1f       	adc	r25, r19
    375e:	93 83       	std	Z+3, r25	; 0x03
    3760:	82 83       	std	Z+2, r24	; 0x02
    3762:	48 c0       	rjmp	.+144    	; 0x37f4 <_ZN6System5AlarmD1Ev+0x124>
    3764:	10 92 ae 03 	sts	0x03AE, r1
    3768:	10 92 ad 03 	sts	0x03AD, r1
    376c:	10 92 b0 03 	sts	0x03B0, r1
    3770:	10 92 af 03 	sts	0x03AF, r1
    3774:	01 97       	sbiw	r24, 0x01	; 1
    3776:	90 93 ac 03 	sts	0x03AC, r25
    377a:	80 93 ab 03 	sts	0x03AB, r24
    377e:	ed 01       	movw	r28, r26
    3780:	ee 81       	ldd	r30, Y+6	; 0x06
    3782:	ff 81       	ldd	r31, Y+7	; 0x07
    3784:	e2 cf       	rjmp	.-60     	; 0x374a <_ZN6System5AlarmD1Ev+0x7a>
    3786:	df 01       	movw	r26, r30
    3788:	80 91 ab 03 	lds	r24, 0x03AB
    378c:	90 91 ac 03 	lds	r25, 0x03AC
    3790:	81 30       	cpi	r24, 0x01	; 1
    3792:	91 05       	cpc	r25, r1
    3794:	11 f6       	brne	.-124    	; 0x371a <_ZN6System5AlarmD1Ev+0x4a>
    3796:	e6 cf       	rjmp	.-52     	; 0x3764 <_ZN6System5AlarmD1Ev+0x94>
    3798:	00 97       	sbiw	r24, 0x00	; 0
    379a:	d9 f4       	brne	.+54     	; 0x37d2 <_ZN6System5AlarmD1Ev+0x102>
    379c:	ed 01       	movw	r28, r26
    379e:	ee 81       	ldd	r30, Y+6	; 0x06
    37a0:	ff 81       	ldd	r31, Y+7	; 0x07
    37a2:	d3 cf       	rjmp	.-90     	; 0x374a <_ZN6System5AlarmD1Ev+0x7a>
    37a4:	00 97       	sbiw	r24, 0x00	; 0
    37a6:	31 f1       	breq	.+76     	; 0x37f4 <_ZN6System5AlarmD1Ev+0x124>
    37a8:	e0 91 af 03 	lds	r30, 0x03AF
    37ac:	f0 91 b0 03 	lds	r31, 0x03B0
    37b0:	04 80       	ldd	r0, Z+4	; 0x04
    37b2:	f5 81       	ldd	r31, Z+5	; 0x05
    37b4:	e0 2d       	mov	r30, r0
    37b6:	f0 93 b0 03 	sts	0x03B0, r31
    37ba:	e0 93 af 03 	sts	0x03AF, r30
    37be:	17 82       	std	Z+7, r1	; 0x07
    37c0:	16 82       	std	Z+6, r1	; 0x06
    37c2:	01 97       	sbiw	r24, 0x01	; 1
    37c4:	90 93 ac 03 	sts	0x03AC, r25
    37c8:	80 93 ab 03 	sts	0x03AB, r24
    37cc:	ee 81       	ldd	r30, Y+6	; 0x06
    37ce:	ff 81       	ldd	r31, Y+7	; 0x07
    37d0:	bc cf       	rjmp	.-136    	; 0x374a <_ZN6System5AlarmD1Ev+0x7a>
    37d2:	06 80       	ldd	r0, Z+6	; 0x06
    37d4:	f7 81       	ldd	r31, Z+7	; 0x07
    37d6:	e0 2d       	mov	r30, r0
    37d8:	f0 93 ae 03 	sts	0x03AE, r31
    37dc:	e0 93 ad 03 	sts	0x03AD, r30
    37e0:	15 82       	std	Z+5, r1	; 0x05
    37e2:	14 82       	std	Z+4, r1	; 0x04
    37e4:	01 97       	sbiw	r24, 0x01	; 1
    37e6:	90 93 ac 03 	sts	0x03AC, r25
    37ea:	80 93 ab 03 	sts	0x03AB, r24
    37ee:	ee 81       	ldd	r30, Y+6	; 0x06
    37f0:	ff 81       	ldd	r31, Y+7	; 0x07
    37f2:	ab cf       	rjmp	.-170    	; 0x374a <_ZN6System5AlarmD1Ev+0x7a>
    37f4:	78 94       	sei
    37f6:	df 91       	pop	r29
    37f8:	cf 91       	pop	r28
    37fa:	08 95       	ret

000037fc <_ZN6System5AlarmD2Ev>:
    37fc:	cf 93       	push	r28
    37fe:	df 93       	push	r29
    3800:	9c 01       	movw	r18, r24
    3802:	f8 94       	cli
    3804:	e0 91 ad 03 	lds	r30, 0x03AD
    3808:	f0 91 ae 03 	lds	r31, 0x03AE
    380c:	30 97       	sbiw	r30, 0x00	; 0
    380e:	09 f4       	brne	.+2      	; 0x3812 <_ZN6System5AlarmD2Ev+0x16>
    3810:	87 c0       	rjmp	.+270    	; 0x3920 <_ZN6System5AlarmD2Ev+0x124>
    3812:	80 81       	ld	r24, Z
    3814:	91 81       	ldd	r25, Z+1	; 0x01
    3816:	28 17       	cp	r18, r24
    3818:	39 07       	cpc	r19, r25
    381a:	09 f4       	brne	.+2      	; 0x381e <_ZN6System5AlarmD2Ev+0x22>
    381c:	4a c0       	rjmp	.+148    	; 0x38b2 <_ZN6System5AlarmD2Ev+0xb6>
    381e:	df 01       	movw	r26, r30
    3820:	ed 01       	movw	r28, r26
    3822:	ae 81       	ldd	r26, Y+6	; 0x06
    3824:	bf 81       	ldd	r27, Y+7	; 0x07
    3826:	10 97       	sbiw	r26, 0x00	; 0
    3828:	09 f4       	brne	.+2      	; 0x382c <_ZN6System5AlarmD2Ev+0x30>
    382a:	7a c0       	rjmp	.+244    	; 0x3920 <_ZN6System5AlarmD2Ev+0x124>
    382c:	8d 91       	ld	r24, X+
    382e:	9c 91       	ld	r25, X
    3830:	11 97       	sbiw	r26, 0x01	; 1
    3832:	28 17       	cp	r18, r24
    3834:	39 07       	cpc	r19, r25
    3836:	a1 f7       	brne	.-24     	; 0x3820 <_ZN6System5AlarmD2Ev+0x24>
    3838:	80 91 ab 03 	lds	r24, 0x03AB
    383c:	90 91 ac 03 	lds	r25, 0x03AC
    3840:	81 30       	cpi	r24, 0x01	; 1
    3842:	91 05       	cpc	r25, r1
    3844:	29 f1       	breq	.+74     	; 0x3890 <_ZN6System5AlarmD2Ev+0x94>
    3846:	ed 01       	movw	r28, r26
    3848:	2c 81       	ldd	r18, Y+4	; 0x04
    384a:	3d 81       	ldd	r19, Y+5	; 0x05
    384c:	21 15       	cp	r18, r1
    384e:	31 05       	cpc	r19, r1
    3850:	c9 f1       	breq	.+114    	; 0x38c4 <_ZN6System5AlarmD2Ev+0xc8>
    3852:	ed 01       	movw	r28, r26
    3854:	ee 81       	ldd	r30, Y+6	; 0x06
    3856:	ff 81       	ldd	r31, Y+7	; 0x07
    3858:	30 97       	sbiw	r30, 0x00	; 0
    385a:	d1 f1       	breq	.+116    	; 0x38d0 <_ZN6System5AlarmD2Ev+0xd4>
    385c:	e9 01       	movw	r28, r18
    385e:	ff 83       	std	Y+7, r31	; 0x07
    3860:	ee 83       	std	Y+6, r30	; 0x06
    3862:	ed 01       	movw	r28, r26
    3864:	ee 81       	ldd	r30, Y+6	; 0x06
    3866:	ff 81       	ldd	r31, Y+7	; 0x07
    3868:	35 83       	std	Z+5, r19	; 0x05
    386a:	24 83       	std	Z+4, r18	; 0x04
    386c:	01 97       	sbiw	r24, 0x01	; 1
    386e:	90 93 ac 03 	sts	0x03AC, r25
    3872:	80 93 ab 03 	sts	0x03AB, r24
    3876:	30 97       	sbiw	r30, 0x00	; 0
    3878:	09 f4       	brne	.+2      	; 0x387c <_ZN6System5AlarmD2Ev+0x80>
    387a:	52 c0       	rjmp	.+164    	; 0x3920 <_ZN6System5AlarmD2Ev+0x124>
    387c:	82 81       	ldd	r24, Z+2	; 0x02
    387e:	93 81       	ldd	r25, Z+3	; 0x03
    3880:	ed 01       	movw	r28, r26
    3882:	2a 81       	ldd	r18, Y+2	; 0x02
    3884:	3b 81       	ldd	r19, Y+3	; 0x03
    3886:	82 0f       	add	r24, r18
    3888:	93 1f       	adc	r25, r19
    388a:	93 83       	std	Z+3, r25	; 0x03
    388c:	82 83       	std	Z+2, r24	; 0x02
    388e:	48 c0       	rjmp	.+144    	; 0x3920 <_ZN6System5AlarmD2Ev+0x124>
    3890:	10 92 ae 03 	sts	0x03AE, r1
    3894:	10 92 ad 03 	sts	0x03AD, r1
    3898:	10 92 b0 03 	sts	0x03B0, r1
    389c:	10 92 af 03 	sts	0x03AF, r1
    38a0:	01 97       	sbiw	r24, 0x01	; 1
    38a2:	90 93 ac 03 	sts	0x03AC, r25
    38a6:	80 93 ab 03 	sts	0x03AB, r24
    38aa:	ed 01       	movw	r28, r26
    38ac:	ee 81       	ldd	r30, Y+6	; 0x06
    38ae:	ff 81       	ldd	r31, Y+7	; 0x07
    38b0:	e2 cf       	rjmp	.-60     	; 0x3876 <_ZN6System5AlarmD2Ev+0x7a>
    38b2:	df 01       	movw	r26, r30
    38b4:	80 91 ab 03 	lds	r24, 0x03AB
    38b8:	90 91 ac 03 	lds	r25, 0x03AC
    38bc:	81 30       	cpi	r24, 0x01	; 1
    38be:	91 05       	cpc	r25, r1
    38c0:	11 f6       	brne	.-124    	; 0x3846 <_ZN6System5AlarmD2Ev+0x4a>
    38c2:	e6 cf       	rjmp	.-52     	; 0x3890 <_ZN6System5AlarmD2Ev+0x94>
    38c4:	00 97       	sbiw	r24, 0x00	; 0
    38c6:	d9 f4       	brne	.+54     	; 0x38fe <_ZN6System5AlarmD2Ev+0x102>
    38c8:	ed 01       	movw	r28, r26
    38ca:	ee 81       	ldd	r30, Y+6	; 0x06
    38cc:	ff 81       	ldd	r31, Y+7	; 0x07
    38ce:	d3 cf       	rjmp	.-90     	; 0x3876 <_ZN6System5AlarmD2Ev+0x7a>
    38d0:	00 97       	sbiw	r24, 0x00	; 0
    38d2:	31 f1       	breq	.+76     	; 0x3920 <_ZN6System5AlarmD2Ev+0x124>
    38d4:	e0 91 af 03 	lds	r30, 0x03AF
    38d8:	f0 91 b0 03 	lds	r31, 0x03B0
    38dc:	04 80       	ldd	r0, Z+4	; 0x04
    38de:	f5 81       	ldd	r31, Z+5	; 0x05
    38e0:	e0 2d       	mov	r30, r0
    38e2:	f0 93 b0 03 	sts	0x03B0, r31
    38e6:	e0 93 af 03 	sts	0x03AF, r30
    38ea:	17 82       	std	Z+7, r1	; 0x07
    38ec:	16 82       	std	Z+6, r1	; 0x06
    38ee:	01 97       	sbiw	r24, 0x01	; 1
    38f0:	90 93 ac 03 	sts	0x03AC, r25
    38f4:	80 93 ab 03 	sts	0x03AB, r24
    38f8:	ee 81       	ldd	r30, Y+6	; 0x06
    38fa:	ff 81       	ldd	r31, Y+7	; 0x07
    38fc:	bc cf       	rjmp	.-136    	; 0x3876 <_ZN6System5AlarmD2Ev+0x7a>
    38fe:	06 80       	ldd	r0, Z+6	; 0x06
    3900:	f7 81       	ldd	r31, Z+7	; 0x07
    3902:	e0 2d       	mov	r30, r0
    3904:	f0 93 ae 03 	sts	0x03AE, r31
    3908:	e0 93 ad 03 	sts	0x03AD, r30
    390c:	15 82       	std	Z+5, r1	; 0x05
    390e:	14 82       	std	Z+4, r1	; 0x04
    3910:	01 97       	sbiw	r24, 0x01	; 1
    3912:	90 93 ac 03 	sts	0x03AC, r25
    3916:	80 93 ab 03 	sts	0x03AB, r24
    391a:	ee 81       	ldd	r30, Y+6	; 0x06
    391c:	ff 81       	ldd	r31, Y+7	; 0x07
    391e:	ab cf       	rjmp	.-170    	; 0x3876 <_ZN6System5AlarmD2Ev+0x7a>
    3920:	78 94       	sei
    3922:	df 91       	pop	r29
    3924:	cf 91       	pop	r28
    3926:	08 95       	ret

00003928 <_ZN6System5Alarm6masterERKmPFvvE>:
    3928:	af 92       	push	r10
    392a:	bf 92       	push	r11
    392c:	cf 92       	push	r12
    392e:	df 92       	push	r13
    3930:	ef 92       	push	r14
    3932:	ff 92       	push	r15
    3934:	0f 93       	push	r16
    3936:	1f 93       	push	r17
    3938:	cf 93       	push	r28
    393a:	df 93       	push	r29
    393c:	cd b7       	in	r28, 0x3d	; 61
    393e:	de b7       	in	r29, 0x3e	; 62
    3940:	24 97       	sbiw	r28, 0x04	; 4
    3942:	0f b6       	in	r0, 0x3f	; 63
    3944:	f8 94       	cli
    3946:	de bf       	out	0x3e, r29	; 62
    3948:	0f be       	out	0x3f, r0	; 63
    394a:	cd bf       	out	0x3d, r28	; 61
    394c:	8c 01       	movw	r16, r24
    394e:	5b 01       	movw	r10, r22
    3950:	f8 94       	cli
    3952:	61 b7       	in	r22, 0x31	; 49
    3954:	c1 b6       	in	r12, 0x31	; 49
    3956:	77 27       	eor	r23, r23
    3958:	80 e2       	ldi	r24, 0x20	; 32
    395a:	9c e1       	ldi	r25, 0x1C	; 28
    395c:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    3960:	9b 01       	movw	r18, r22
    3962:	44 27       	eor	r20, r20
    3964:	55 27       	eor	r21, r21
    3966:	60 e4       	ldi	r22, 0x40	; 64
    3968:	72 e4       	ldi	r23, 0x42	; 66
    396a:	8f e0       	ldi	r24, 0x0F	; 15
    396c:	90 e0       	ldi	r25, 0x00	; 0
    396e:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3972:	56 95       	lsr	r21
    3974:	47 95       	ror	r20
    3976:	37 95       	ror	r19
    3978:	27 95       	ror	r18
    397a:	f8 01       	movw	r30, r16
    397c:	e0 80       	ld	r14, Z
    397e:	f1 80       	ldd	r15, Z+1	; 0x01
    3980:	02 81       	ldd	r16, Z+2	; 0x02
    3982:	13 81       	ldd	r17, Z+3	; 0x03
    3984:	e2 0e       	add	r14, r18
    3986:	f3 1e       	adc	r15, r19
    3988:	04 1f       	adc	r16, r20
    398a:	15 1f       	adc	r17, r21
    398c:	dd 24       	eor	r13, r13
    398e:	80 e2       	ldi	r24, 0x20	; 32
    3990:	9c e1       	ldi	r25, 0x1C	; 28
    3992:	b6 01       	movw	r22, r12
    3994:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    3998:	9b 01       	movw	r18, r22
    399a:	44 27       	eor	r20, r20
    399c:	55 27       	eor	r21, r21
    399e:	60 e4       	ldi	r22, 0x40	; 64
    39a0:	72 e4       	ldi	r23, 0x42	; 66
    39a2:	8f e0       	ldi	r24, 0x0F	; 15
    39a4:	90 e0       	ldi	r25, 0x00	; 0
    39a6:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    39aa:	c8 01       	movw	r24, r16
    39ac:	b7 01       	movw	r22, r14
    39ae:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    39b2:	29 83       	std	Y+1, r18	; 0x01
    39b4:	3a 83       	std	Y+2, r19	; 0x02
    39b6:	4b 83       	std	Y+3, r20	; 0x03
    39b8:	5c 83       	std	Y+4, r21	; 0x04
    39ba:	b0 92 ba 03 	sts	0x03BA, r11
    39be:	a0 92 b9 03 	sts	0x03B9, r10
    39c2:	89 81       	ldd	r24, Y+1	; 0x01
    39c4:	9a 81       	ldd	r25, Y+2	; 0x02
    39c6:	ab 81       	ldd	r26, Y+3	; 0x03
    39c8:	bc 81       	ldd	r27, Y+4	; 0x04
    39ca:	80 93 b5 03 	sts	0x03B5, r24
    39ce:	90 93 b6 03 	sts	0x03B6, r25
    39d2:	a0 93 b7 03 	sts	0x03B7, r26
    39d6:	b0 93 b8 03 	sts	0x03B8, r27
    39da:	20 93 b1 03 	sts	0x03B1, r18
    39de:	30 93 b2 03 	sts	0x03B2, r19
    39e2:	40 93 b3 03 	sts	0x03B3, r20
    39e6:	50 93 b4 03 	sts	0x03B4, r21
    39ea:	78 94       	sei
    39ec:	24 96       	adiw	r28, 0x04	; 4
    39ee:	0f b6       	in	r0, 0x3f	; 63
    39f0:	f8 94       	cli
    39f2:	de bf       	out	0x3e, r29	; 62
    39f4:	0f be       	out	0x3f, r0	; 63
    39f6:	cd bf       	out	0x3d, r28	; 61
    39f8:	df 91       	pop	r29
    39fa:	cf 91       	pop	r28
    39fc:	1f 91       	pop	r17
    39fe:	0f 91       	pop	r16
    3a00:	ff 90       	pop	r15
    3a02:	ef 90       	pop	r14
    3a04:	df 90       	pop	r13
    3a06:	cf 90       	pop	r12
    3a08:	bf 90       	pop	r11
    3a0a:	af 90       	pop	r10
    3a0c:	08 95       	ret

00003a0e <_ZN6System5Alarm11int_handlerEj>:
    3a0e:	cf 93       	push	r28
    3a10:	df 93       	push	r29
    3a12:	f8 94       	cli
    3a14:	80 91 bb 03 	lds	r24, 0x03BB
    3a18:	90 91 bc 03 	lds	r25, 0x03BC
    3a1c:	a0 91 bd 03 	lds	r26, 0x03BD
    3a20:	b0 91 be 03 	lds	r27, 0x03BE
    3a24:	01 96       	adiw	r24, 0x01	; 1
    3a26:	a1 1d       	adc	r26, r1
    3a28:	b1 1d       	adc	r27, r1
    3a2a:	80 93 bb 03 	sts	0x03BB, r24
    3a2e:	90 93 bc 03 	sts	0x03BC, r25
    3a32:	a0 93 bd 03 	sts	0x03BD, r26
    3a36:	b0 93 be 03 	sts	0x03BE, r27
    3a3a:	20 91 ab 03 	lds	r18, 0x03AB
    3a3e:	30 91 ac 03 	lds	r19, 0x03AC
    3a42:	21 15       	cp	r18, r1
    3a44:	31 05       	cpc	r19, r1
    3a46:	71 f0       	breq	.+28     	; 0x3a64 <_ZN6System5Alarm11int_handlerEj+0x56>
    3a48:	a0 91 ad 03 	lds	r26, 0x03AD
    3a4c:	b0 91 ae 03 	lds	r27, 0x03AE
    3a50:	ed 01       	movw	r28, r26
    3a52:	8a 81       	ldd	r24, Y+2	; 0x02
    3a54:	9b 81       	ldd	r25, Y+3	; 0x03
    3a56:	01 97       	sbiw	r24, 0x01	; 1
    3a58:	9b 83       	std	Y+3, r25	; 0x03
    3a5a:	8a 83       	std	Y+2, r24	; 0x02
    3a5c:	18 16       	cp	r1, r24
    3a5e:	19 06       	cpc	r1, r25
    3a60:	0c f0       	brlt	.+2      	; 0x3a64 <_ZN6System5Alarm11int_handlerEj+0x56>
    3a62:	3b c0       	rjmp	.+118    	; 0x3ada <_ZN6System5Alarm11int_handlerEj+0xcc>
    3a64:	c0 e0       	ldi	r28, 0x00	; 0
    3a66:	d0 e0       	ldi	r29, 0x00	; 0
    3a68:	78 94       	sei
    3a6a:	20 91 b1 03 	lds	r18, 0x03B1
    3a6e:	30 91 b2 03 	lds	r19, 0x03B2
    3a72:	40 91 b3 03 	lds	r20, 0x03B3
    3a76:	50 91 b4 03 	lds	r21, 0x03B4
    3a7a:	21 15       	cp	r18, r1
    3a7c:	31 05       	cpc	r19, r1
    3a7e:	41 05       	cpc	r20, r1
    3a80:	51 05       	cpc	r21, r1
    3a82:	01 f1       	breq	.+64     	; 0x3ac4 <_ZN6System5Alarm11int_handlerEj+0xb6>
    3a84:	80 91 b5 03 	lds	r24, 0x03B5
    3a88:	90 91 b6 03 	lds	r25, 0x03B6
    3a8c:	a0 91 b7 03 	lds	r26, 0x03B7
    3a90:	b0 91 b8 03 	lds	r27, 0x03B8
    3a94:	01 97       	sbiw	r24, 0x01	; 1
    3a96:	a1 09       	sbc	r26, r1
    3a98:	b1 09       	sbc	r27, r1
    3a9a:	80 93 b5 03 	sts	0x03B5, r24
    3a9e:	90 93 b6 03 	sts	0x03B6, r25
    3aa2:	a0 93 b7 03 	sts	0x03B7, r26
    3aa6:	b0 93 b8 03 	sts	0x03B8, r27
    3aaa:	80 91 b5 03 	lds	r24, 0x03B5
    3aae:	90 91 b6 03 	lds	r25, 0x03B6
    3ab2:	a0 91 b7 03 	lds	r26, 0x03B7
    3ab6:	b0 91 b8 03 	lds	r27, 0x03B8
    3aba:	8f 5f       	subi	r24, 0xFF	; 255
    3abc:	9f 4f       	sbci	r25, 0xFF	; 255
    3abe:	af 4f       	sbci	r26, 0xFF	; 255
    3ac0:	bf 4f       	sbci	r27, 0xFF	; 255
    3ac2:	91 f1       	breq	.+100    	; 0x3b28 <_ZN6System5Alarm11int_handlerEj+0x11a>
    3ac4:	20 97       	sbiw	r28, 0x00	; 0
    3ac6:	09 f4       	brne	.+2      	; 0x3aca <_ZN6System5Alarm11int_handlerEj+0xbc>
    3ac8:	5a c0       	rjmp	.+180    	; 0x3b7e <_ZN6System5Alarm11int_handlerEj+0x170>
    3aca:	e8 81       	ld	r30, Y
    3acc:	f9 81       	ldd	r31, Y+1	; 0x01
    3ace:	04 80       	ldd	r0, Z+4	; 0x04
    3ad0:	f5 81       	ldd	r31, Z+5	; 0x05
    3ad2:	e0 2d       	mov	r30, r0
    3ad4:	ce 01       	movw	r24, r28
    3ad6:	09 95       	icall
    3ad8:	52 c0       	rjmp	.+164    	; 0x3b7e <_ZN6System5Alarm11int_handlerEj+0x170>
    3ada:	21 30       	cpi	r18, 0x01	; 1
    3adc:	31 05       	cpc	r19, r1
    3ade:	e9 f1       	breq	.+122    	; 0x3b5a <_ZN6System5Alarm11int_handlerEj+0x14c>
    3ae0:	ed 01       	movw	r28, r26
    3ae2:	ee 81       	ldd	r30, Y+6	; 0x06
    3ae4:	ff 81       	ldd	r31, Y+7	; 0x07
    3ae6:	f0 93 ae 03 	sts	0x03AE, r31
    3aea:	e0 93 ad 03 	sts	0x03AD, r30
    3aee:	15 82       	std	Z+5, r1	; 0x05
    3af0:	14 82       	std	Z+4, r1	; 0x04
    3af2:	80 91 ab 03 	lds	r24, 0x03AB
    3af6:	90 91 ac 03 	lds	r25, 0x03AC
    3afa:	01 97       	sbiw	r24, 0x01	; 1
    3afc:	90 93 ac 03 	sts	0x03AC, r25
    3b00:	80 93 ab 03 	sts	0x03AB, r24
    3b04:	cd 91       	ld	r28, X+
    3b06:	dc 91       	ld	r29, X
    3b08:	11 97       	sbiw	r26, 0x01	; 1
    3b0a:	8e 81       	ldd	r24, Y+6	; 0x06
    3b0c:	9f 81       	ldd	r25, Y+7	; 0x07
    3b0e:	ef ef       	ldi	r30, 0xFF	; 255
    3b10:	8f 3f       	cpi	r24, 0xFF	; 255
    3b12:	9e 07       	cpc	r25, r30
    3b14:	19 f0       	breq	.+6      	; 0x3b1c <_ZN6System5Alarm11int_handlerEj+0x10e>
    3b16:	01 97       	sbiw	r24, 0x01	; 1
    3b18:	9f 83       	std	Y+7, r25	; 0x07
    3b1a:	8e 83       	std	Y+6, r24	; 0x06
    3b1c:	89 2b       	or	r24, r25
    3b1e:	91 f4       	brne	.+36     	; 0x3b44 <_ZN6System5Alarm11int_handlerEj+0x136>
    3b20:	0c 80       	ldd	r0, Y+4	; 0x04
    3b22:	dd 81       	ldd	r29, Y+5	; 0x05
    3b24:	c0 2d       	mov	r28, r0
    3b26:	a0 cf       	rjmp	.-192    	; 0x3a68 <_ZN6System5Alarm11int_handlerEj+0x5a>
    3b28:	20 93 b5 03 	sts	0x03B5, r18
    3b2c:	30 93 b6 03 	sts	0x03B6, r19
    3b30:	40 93 b7 03 	sts	0x03B7, r20
    3b34:	50 93 b8 03 	sts	0x03B8, r21
    3b38:	e0 91 b9 03 	lds	r30, 0x03B9
    3b3c:	f0 91 ba 03 	lds	r31, 0x03BA
    3b40:	09 95       	icall
    3b42:	c0 cf       	rjmp	.-128    	; 0x3ac4 <_ZN6System5Alarm11int_handlerEj+0xb6>
    3b44:	88 81       	ld	r24, Y
    3b46:	99 81       	ldd	r25, Y+1	; 0x01
    3b48:	fd 01       	movw	r30, r26
    3b4a:	93 83       	std	Z+3, r25	; 0x03
    3b4c:	82 83       	std	Z+2, r24	; 0x02
    3b4e:	bd 01       	movw	r22, r26
    3b50:	8b ea       	ldi	r24, 0xAB	; 171
    3b52:	93 e0       	ldi	r25, 0x03	; 3
    3b54:	0e 94 d5 1a 	call	0x35aa	; 0x35aa <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_>
    3b58:	e3 cf       	rjmp	.-58     	; 0x3b20 <_ZN6System5Alarm11int_handlerEj+0x112>
    3b5a:	10 92 ae 03 	sts	0x03AE, r1
    3b5e:	10 92 ad 03 	sts	0x03AD, r1
    3b62:	10 92 b0 03 	sts	0x03B0, r1
    3b66:	10 92 af 03 	sts	0x03AF, r1
    3b6a:	80 91 ab 03 	lds	r24, 0x03AB
    3b6e:	90 91 ac 03 	lds	r25, 0x03AC
    3b72:	01 97       	sbiw	r24, 0x01	; 1
    3b74:	90 93 ac 03 	sts	0x03AC, r25
    3b78:	80 93 ab 03 	sts	0x03AB, r24
    3b7c:	c3 cf       	rjmp	.-122    	; 0x3b04 <_ZN6System5Alarm11int_handlerEj+0xf6>
    3b7e:	df 91       	pop	r29
    3b80:	cf 91       	pop	r28
    3b82:	08 95       	ret

00003b84 <_ZN6System5AlarmC2ERKmPNS_7HandlerEi>:
    3b84:	6f 92       	push	r6
    3b86:	7f 92       	push	r7
    3b88:	8f 92       	push	r8
    3b8a:	9f 92       	push	r9
    3b8c:	af 92       	push	r10
    3b8e:	bf 92       	push	r11
    3b90:	cf 92       	push	r12
    3b92:	df 92       	push	r13
    3b94:	ef 92       	push	r14
    3b96:	ff 92       	push	r15
    3b98:	0f 93       	push	r16
    3b9a:	1f 93       	push	r17
    3b9c:	cf 93       	push	r28
    3b9e:	df 93       	push	r29
    3ba0:	6c 01       	movw	r12, r24
    3ba2:	8b 01       	movw	r16, r22
    3ba4:	3a 01       	movw	r6, r20
    3ba6:	49 01       	movw	r8, r18
    3ba8:	61 b7       	in	r22, 0x31	; 49
    3baa:	a1 b6       	in	r10, 0x31	; 49
    3bac:	77 27       	eor	r23, r23
    3bae:	80 e2       	ldi	r24, 0x20	; 32
    3bb0:	9c e1       	ldi	r25, 0x1C	; 28
    3bb2:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    3bb6:	9b 01       	movw	r18, r22
    3bb8:	44 27       	eor	r20, r20
    3bba:	55 27       	eor	r21, r21
    3bbc:	60 e4       	ldi	r22, 0x40	; 64
    3bbe:	72 e4       	ldi	r23, 0x42	; 66
    3bc0:	8f e0       	ldi	r24, 0x0F	; 15
    3bc2:	90 e0       	ldi	r25, 0x00	; 0
    3bc4:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3bc8:	56 95       	lsr	r21
    3bca:	47 95       	ror	r20
    3bcc:	37 95       	ror	r19
    3bce:	27 95       	ror	r18
    3bd0:	d8 01       	movw	r26, r16
    3bd2:	ed 90       	ld	r14, X+
    3bd4:	fd 90       	ld	r15, X+
    3bd6:	0d 91       	ld	r16, X+
    3bd8:	1c 91       	ld	r17, X
    3bda:	e2 0e       	add	r14, r18
    3bdc:	f3 1e       	adc	r15, r19
    3bde:	04 1f       	adc	r16, r20
    3be0:	15 1f       	adc	r17, r21
    3be2:	bb 24       	eor	r11, r11
    3be4:	80 e2       	ldi	r24, 0x20	; 32
    3be6:	9c e1       	ldi	r25, 0x1C	; 28
    3be8:	b5 01       	movw	r22, r10
    3bea:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    3bee:	9b 01       	movw	r18, r22
    3bf0:	44 27       	eor	r20, r20
    3bf2:	55 27       	eor	r21, r21
    3bf4:	60 e4       	ldi	r22, 0x40	; 64
    3bf6:	72 e4       	ldi	r23, 0x42	; 66
    3bf8:	8f e0       	ldi	r24, 0x0F	; 15
    3bfa:	90 e0       	ldi	r25, 0x00	; 0
    3bfc:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3c00:	c8 01       	movw	r24, r16
    3c02:	b7 01       	movw	r22, r14
    3c04:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3c08:	e6 01       	movw	r28, r12
    3c0a:	28 83       	st	Y, r18
    3c0c:	39 83       	std	Y+1, r19	; 0x01
    3c0e:	4a 83       	std	Y+2, r20	; 0x02
    3c10:	5b 83       	std	Y+3, r21	; 0x03
    3c12:	7d 82       	std	Y+5, r7	; 0x05
    3c14:	6c 82       	std	Y+4, r6	; 0x04
    3c16:	9f 82       	std	Y+7, r9	; 0x07
    3c18:	8e 82       	std	Y+6, r8	; 0x06
    3c1a:	88 81       	ld	r24, Y
    3c1c:	99 81       	ldd	r25, Y+1	; 0x01
    3c1e:	f6 01       	movw	r30, r12
    3c20:	38 96       	adiw	r30, 0x08	; 8
    3c22:	d9 86       	std	Y+9, r13	; 0x09
    3c24:	c8 86       	std	Y+8, r12	; 0x08
    3c26:	93 83       	std	Z+3, r25	; 0x03
    3c28:	82 83       	std	Z+2, r24	; 0x02
    3c2a:	15 82       	std	Z+5, r1	; 0x05
    3c2c:	14 82       	std	Z+4, r1	; 0x04
    3c2e:	17 82       	std	Z+7, r1	; 0x07
    3c30:	16 82       	std	Z+6, r1	; 0x06
    3c32:	21 15       	cp	r18, r1
    3c34:	31 05       	cpc	r19, r1
    3c36:	41 05       	cpc	r20, r1
    3c38:	51 05       	cpc	r21, r1
    3c3a:	41 f0       	breq	.+16     	; 0x3c4c <_ZN6System5AlarmC2ERKmPNS_7HandlerEi+0xc8>
    3c3c:	f8 94       	cli
    3c3e:	bf 01       	movw	r22, r30
    3c40:	8b ea       	ldi	r24, 0xAB	; 171
    3c42:	93 e0       	ldi	r25, 0x03	; 3
    3c44:	0e 94 d5 1a 	call	0x35aa	; 0x35aa <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_>
    3c48:	78 94       	sei
    3c4a:	08 c0       	rjmp	.+16     	; 0x3c5c <_ZN6System5AlarmC2ERKmPNS_7HandlerEi+0xd8>
    3c4c:	d3 01       	movw	r26, r6
    3c4e:	ed 91       	ld	r30, X+
    3c50:	fc 91       	ld	r31, X
    3c52:	04 80       	ldd	r0, Z+4	; 0x04
    3c54:	f5 81       	ldd	r31, Z+5	; 0x05
    3c56:	e0 2d       	mov	r30, r0
    3c58:	c3 01       	movw	r24, r6
    3c5a:	09 95       	icall
    3c5c:	df 91       	pop	r29
    3c5e:	cf 91       	pop	r28
    3c60:	1f 91       	pop	r17
    3c62:	0f 91       	pop	r16
    3c64:	ff 90       	pop	r15
    3c66:	ef 90       	pop	r14
    3c68:	df 90       	pop	r13
    3c6a:	cf 90       	pop	r12
    3c6c:	bf 90       	pop	r11
    3c6e:	af 90       	pop	r10
    3c70:	9f 90       	pop	r9
    3c72:	8f 90       	pop	r8
    3c74:	7f 90       	pop	r7
    3c76:	6f 90       	pop	r6
    3c78:	08 95       	ret

00003c7a <_ZN6System5AlarmC1ERKmPNS_7HandlerEi>:
    3c7a:	6f 92       	push	r6
    3c7c:	7f 92       	push	r7
    3c7e:	8f 92       	push	r8
    3c80:	9f 92       	push	r9
    3c82:	af 92       	push	r10
    3c84:	bf 92       	push	r11
    3c86:	cf 92       	push	r12
    3c88:	df 92       	push	r13
    3c8a:	ef 92       	push	r14
    3c8c:	ff 92       	push	r15
    3c8e:	0f 93       	push	r16
    3c90:	1f 93       	push	r17
    3c92:	cf 93       	push	r28
    3c94:	df 93       	push	r29
    3c96:	6c 01       	movw	r12, r24
    3c98:	8b 01       	movw	r16, r22
    3c9a:	3a 01       	movw	r6, r20
    3c9c:	49 01       	movw	r8, r18
    3c9e:	61 b7       	in	r22, 0x31	; 49
    3ca0:	a1 b6       	in	r10, 0x31	; 49
    3ca2:	77 27       	eor	r23, r23
    3ca4:	80 e2       	ldi	r24, 0x20	; 32
    3ca6:	9c e1       	ldi	r25, 0x1C	; 28
    3ca8:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    3cac:	9b 01       	movw	r18, r22
    3cae:	44 27       	eor	r20, r20
    3cb0:	55 27       	eor	r21, r21
    3cb2:	60 e4       	ldi	r22, 0x40	; 64
    3cb4:	72 e4       	ldi	r23, 0x42	; 66
    3cb6:	8f e0       	ldi	r24, 0x0F	; 15
    3cb8:	90 e0       	ldi	r25, 0x00	; 0
    3cba:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3cbe:	56 95       	lsr	r21
    3cc0:	47 95       	ror	r20
    3cc2:	37 95       	ror	r19
    3cc4:	27 95       	ror	r18
    3cc6:	d8 01       	movw	r26, r16
    3cc8:	ed 90       	ld	r14, X+
    3cca:	fd 90       	ld	r15, X+
    3ccc:	0d 91       	ld	r16, X+
    3cce:	1c 91       	ld	r17, X
    3cd0:	e2 0e       	add	r14, r18
    3cd2:	f3 1e       	adc	r15, r19
    3cd4:	04 1f       	adc	r16, r20
    3cd6:	15 1f       	adc	r17, r21
    3cd8:	bb 24       	eor	r11, r11
    3cda:	80 e2       	ldi	r24, 0x20	; 32
    3cdc:	9c e1       	ldi	r25, 0x1C	; 28
    3cde:	b5 01       	movw	r22, r10
    3ce0:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    3ce4:	9b 01       	movw	r18, r22
    3ce6:	44 27       	eor	r20, r20
    3ce8:	55 27       	eor	r21, r21
    3cea:	60 e4       	ldi	r22, 0x40	; 64
    3cec:	72 e4       	ldi	r23, 0x42	; 66
    3cee:	8f e0       	ldi	r24, 0x0F	; 15
    3cf0:	90 e0       	ldi	r25, 0x00	; 0
    3cf2:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3cf6:	c8 01       	movw	r24, r16
    3cf8:	b7 01       	movw	r22, r14
    3cfa:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3cfe:	e6 01       	movw	r28, r12
    3d00:	28 83       	st	Y, r18
    3d02:	39 83       	std	Y+1, r19	; 0x01
    3d04:	4a 83       	std	Y+2, r20	; 0x02
    3d06:	5b 83       	std	Y+3, r21	; 0x03
    3d08:	7d 82       	std	Y+5, r7	; 0x05
    3d0a:	6c 82       	std	Y+4, r6	; 0x04
    3d0c:	9f 82       	std	Y+7, r9	; 0x07
    3d0e:	8e 82       	std	Y+6, r8	; 0x06
    3d10:	88 81       	ld	r24, Y
    3d12:	99 81       	ldd	r25, Y+1	; 0x01
    3d14:	f6 01       	movw	r30, r12
    3d16:	38 96       	adiw	r30, 0x08	; 8
    3d18:	d9 86       	std	Y+9, r13	; 0x09
    3d1a:	c8 86       	std	Y+8, r12	; 0x08
    3d1c:	93 83       	std	Z+3, r25	; 0x03
    3d1e:	82 83       	std	Z+2, r24	; 0x02
    3d20:	15 82       	std	Z+5, r1	; 0x05
    3d22:	14 82       	std	Z+4, r1	; 0x04
    3d24:	17 82       	std	Z+7, r1	; 0x07
    3d26:	16 82       	std	Z+6, r1	; 0x06
    3d28:	21 15       	cp	r18, r1
    3d2a:	31 05       	cpc	r19, r1
    3d2c:	41 05       	cpc	r20, r1
    3d2e:	51 05       	cpc	r21, r1
    3d30:	41 f0       	breq	.+16     	; 0x3d42 <_ZN6System5AlarmC1ERKmPNS_7HandlerEi+0xc8>
    3d32:	f8 94       	cli
    3d34:	bf 01       	movw	r22, r30
    3d36:	8b ea       	ldi	r24, 0xAB	; 171
    3d38:	93 e0       	ldi	r25, 0x03	; 3
    3d3a:	0e 94 d5 1a 	call	0x35aa	; 0x35aa <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_>
    3d3e:	78 94       	sei
    3d40:	08 c0       	rjmp	.+16     	; 0x3d52 <_ZN6System5AlarmC1ERKmPNS_7HandlerEi+0xd8>
    3d42:	d3 01       	movw	r26, r6
    3d44:	ed 91       	ld	r30, X+
    3d46:	fc 91       	ld	r31, X
    3d48:	04 80       	ldd	r0, Z+4	; 0x04
    3d4a:	f5 81       	ldd	r31, Z+5	; 0x05
    3d4c:	e0 2d       	mov	r30, r0
    3d4e:	c3 01       	movw	r24, r6
    3d50:	09 95       	icall
    3d52:	df 91       	pop	r29
    3d54:	cf 91       	pop	r28
    3d56:	1f 91       	pop	r17
    3d58:	0f 91       	pop	r16
    3d5a:	ff 90       	pop	r15
    3d5c:	ef 90       	pop	r14
    3d5e:	df 90       	pop	r13
    3d60:	cf 90       	pop	r12
    3d62:	bf 90       	pop	r11
    3d64:	af 90       	pop	r10
    3d66:	9f 90       	pop	r9
    3d68:	8f 90       	pop	r8
    3d6a:	7f 90       	pop	r7
    3d6c:	6f 90       	pop	r6
    3d6e:	08 95       	ret

00003d70 <_ZN6System5AlarmC2ERKmPNS_7HandlerEib>:
    3d70:	4f 92       	push	r4
    3d72:	5f 92       	push	r5
    3d74:	7f 92       	push	r7
    3d76:	8f 92       	push	r8
    3d78:	9f 92       	push	r9
    3d7a:	af 92       	push	r10
    3d7c:	bf 92       	push	r11
    3d7e:	cf 92       	push	r12
    3d80:	df 92       	push	r13
    3d82:	ef 92       	push	r14
    3d84:	ff 92       	push	r15
    3d86:	0f 93       	push	r16
    3d88:	1f 93       	push	r17
    3d8a:	cf 93       	push	r28
    3d8c:	df 93       	push	r29
    3d8e:	6c 01       	movw	r12, r24
    3d90:	7b 01       	movw	r14, r22
    3d92:	2a 01       	movw	r4, r20
    3d94:	49 01       	movw	r8, r18
    3d96:	70 2e       	mov	r7, r16
    3d98:	61 b7       	in	r22, 0x31	; 49
    3d9a:	a1 b6       	in	r10, 0x31	; 49
    3d9c:	77 27       	eor	r23, r23
    3d9e:	80 e2       	ldi	r24, 0x20	; 32
    3da0:	9c e1       	ldi	r25, 0x1C	; 28
    3da2:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    3da6:	9b 01       	movw	r18, r22
    3da8:	44 27       	eor	r20, r20
    3daa:	55 27       	eor	r21, r21
    3dac:	60 e4       	ldi	r22, 0x40	; 64
    3dae:	72 e4       	ldi	r23, 0x42	; 66
    3db0:	8f e0       	ldi	r24, 0x0F	; 15
    3db2:	90 e0       	ldi	r25, 0x00	; 0
    3db4:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3db8:	56 95       	lsr	r21
    3dba:	47 95       	ror	r20
    3dbc:	37 95       	ror	r19
    3dbe:	27 95       	ror	r18
    3dc0:	d7 01       	movw	r26, r14
    3dc2:	ed 90       	ld	r14, X+
    3dc4:	fd 90       	ld	r15, X+
    3dc6:	0d 91       	ld	r16, X+
    3dc8:	1c 91       	ld	r17, X
    3dca:	e2 0e       	add	r14, r18
    3dcc:	f3 1e       	adc	r15, r19
    3dce:	04 1f       	adc	r16, r20
    3dd0:	15 1f       	adc	r17, r21
    3dd2:	bb 24       	eor	r11, r11
    3dd4:	80 e2       	ldi	r24, 0x20	; 32
    3dd6:	9c e1       	ldi	r25, 0x1C	; 28
    3dd8:	b5 01       	movw	r22, r10
    3dda:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    3dde:	9b 01       	movw	r18, r22
    3de0:	44 27       	eor	r20, r20
    3de2:	55 27       	eor	r21, r21
    3de4:	60 e4       	ldi	r22, 0x40	; 64
    3de6:	72 e4       	ldi	r23, 0x42	; 66
    3de8:	8f e0       	ldi	r24, 0x0F	; 15
    3dea:	90 e0       	ldi	r25, 0x00	; 0
    3dec:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3df0:	c8 01       	movw	r24, r16
    3df2:	b7 01       	movw	r22, r14
    3df4:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3df8:	e6 01       	movw	r28, r12
    3dfa:	28 83       	st	Y, r18
    3dfc:	39 83       	std	Y+1, r19	; 0x01
    3dfe:	4a 83       	std	Y+2, r20	; 0x02
    3e00:	5b 83       	std	Y+3, r21	; 0x03
    3e02:	5d 82       	std	Y+5, r5	; 0x05
    3e04:	4c 82       	std	Y+4, r4	; 0x04
    3e06:	9f 82       	std	Y+7, r9	; 0x07
    3e08:	8e 82       	std	Y+6, r8	; 0x06
    3e0a:	88 81       	ld	r24, Y
    3e0c:	99 81       	ldd	r25, Y+1	; 0x01
    3e0e:	f6 01       	movw	r30, r12
    3e10:	38 96       	adiw	r30, 0x08	; 8
    3e12:	d9 86       	std	Y+9, r13	; 0x09
    3e14:	c8 86       	std	Y+8, r12	; 0x08
    3e16:	93 83       	std	Z+3, r25	; 0x03
    3e18:	82 83       	std	Z+2, r24	; 0x02
    3e1a:	15 82       	std	Z+5, r1	; 0x05
    3e1c:	14 82       	std	Z+4, r1	; 0x04
    3e1e:	17 82       	std	Z+7, r1	; 0x07
    3e20:	16 82       	std	Z+6, r1	; 0x06
    3e22:	21 15       	cp	r18, r1
    3e24:	31 05       	cpc	r19, r1
    3e26:	41 05       	cpc	r20, r1
    3e28:	51 05       	cpc	r21, r1
    3e2a:	51 f0       	breq	.+20     	; 0x3e40 <_ZN6System5AlarmC2ERKmPNS_7HandlerEib+0xd0>
    3e2c:	f8 94       	cli
    3e2e:	bf 01       	movw	r22, r30
    3e30:	8b ea       	ldi	r24, 0xAB	; 171
    3e32:	93 e0       	ldi	r25, 0x03	; 3
    3e34:	0e 94 d5 1a 	call	0x35aa	; 0x35aa <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_>
    3e38:	77 20       	and	r7, r7
    3e3a:	51 f0       	breq	.+20     	; 0x3e50 <_ZN6System5AlarmC2ERKmPNS_7HandlerEib+0xe0>
    3e3c:	78 94       	sei
    3e3e:	08 c0       	rjmp	.+16     	; 0x3e50 <_ZN6System5AlarmC2ERKmPNS_7HandlerEib+0xe0>
    3e40:	d2 01       	movw	r26, r4
    3e42:	ed 91       	ld	r30, X+
    3e44:	fc 91       	ld	r31, X
    3e46:	04 80       	ldd	r0, Z+4	; 0x04
    3e48:	f5 81       	ldd	r31, Z+5	; 0x05
    3e4a:	e0 2d       	mov	r30, r0
    3e4c:	c2 01       	movw	r24, r4
    3e4e:	09 95       	icall
    3e50:	df 91       	pop	r29
    3e52:	cf 91       	pop	r28
    3e54:	1f 91       	pop	r17
    3e56:	0f 91       	pop	r16
    3e58:	ff 90       	pop	r15
    3e5a:	ef 90       	pop	r14
    3e5c:	df 90       	pop	r13
    3e5e:	cf 90       	pop	r12
    3e60:	bf 90       	pop	r11
    3e62:	af 90       	pop	r10
    3e64:	9f 90       	pop	r9
    3e66:	8f 90       	pop	r8
    3e68:	7f 90       	pop	r7
    3e6a:	5f 90       	pop	r5
    3e6c:	4f 90       	pop	r4
    3e6e:	08 95       	ret

00003e70 <_ZN6System5AlarmC1ERKmPNS_7HandlerEib>:
    3e70:	4f 92       	push	r4
    3e72:	5f 92       	push	r5
    3e74:	7f 92       	push	r7
    3e76:	8f 92       	push	r8
    3e78:	9f 92       	push	r9
    3e7a:	af 92       	push	r10
    3e7c:	bf 92       	push	r11
    3e7e:	cf 92       	push	r12
    3e80:	df 92       	push	r13
    3e82:	ef 92       	push	r14
    3e84:	ff 92       	push	r15
    3e86:	0f 93       	push	r16
    3e88:	1f 93       	push	r17
    3e8a:	cf 93       	push	r28
    3e8c:	df 93       	push	r29
    3e8e:	6c 01       	movw	r12, r24
    3e90:	7b 01       	movw	r14, r22
    3e92:	2a 01       	movw	r4, r20
    3e94:	49 01       	movw	r8, r18
    3e96:	70 2e       	mov	r7, r16
    3e98:	61 b7       	in	r22, 0x31	; 49
    3e9a:	a1 b6       	in	r10, 0x31	; 49
    3e9c:	77 27       	eor	r23, r23
    3e9e:	80 e2       	ldi	r24, 0x20	; 32
    3ea0:	9c e1       	ldi	r25, 0x1C	; 28
    3ea2:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    3ea6:	9b 01       	movw	r18, r22
    3ea8:	44 27       	eor	r20, r20
    3eaa:	55 27       	eor	r21, r21
    3eac:	60 e4       	ldi	r22, 0x40	; 64
    3eae:	72 e4       	ldi	r23, 0x42	; 66
    3eb0:	8f e0       	ldi	r24, 0x0F	; 15
    3eb2:	90 e0       	ldi	r25, 0x00	; 0
    3eb4:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3eb8:	56 95       	lsr	r21
    3eba:	47 95       	ror	r20
    3ebc:	37 95       	ror	r19
    3ebe:	27 95       	ror	r18
    3ec0:	d7 01       	movw	r26, r14
    3ec2:	ed 90       	ld	r14, X+
    3ec4:	fd 90       	ld	r15, X+
    3ec6:	0d 91       	ld	r16, X+
    3ec8:	1c 91       	ld	r17, X
    3eca:	e2 0e       	add	r14, r18
    3ecc:	f3 1e       	adc	r15, r19
    3ece:	04 1f       	adc	r16, r20
    3ed0:	15 1f       	adc	r17, r21
    3ed2:	bb 24       	eor	r11, r11
    3ed4:	80 e2       	ldi	r24, 0x20	; 32
    3ed6:	9c e1       	ldi	r25, 0x1C	; 28
    3ed8:	b5 01       	movw	r22, r10
    3eda:	0e 94 5f 41 	call	0x82be	; 0x82be <__udivmodhi4>
    3ede:	9b 01       	movw	r18, r22
    3ee0:	44 27       	eor	r20, r20
    3ee2:	55 27       	eor	r21, r21
    3ee4:	60 e4       	ldi	r22, 0x40	; 64
    3ee6:	72 e4       	ldi	r23, 0x42	; 66
    3ee8:	8f e0       	ldi	r24, 0x0F	; 15
    3eea:	90 e0       	ldi	r25, 0x00	; 0
    3eec:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3ef0:	c8 01       	movw	r24, r16
    3ef2:	b7 01       	movw	r22, r14
    3ef4:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    3ef8:	e6 01       	movw	r28, r12
    3efa:	28 83       	st	Y, r18
    3efc:	39 83       	std	Y+1, r19	; 0x01
    3efe:	4a 83       	std	Y+2, r20	; 0x02
    3f00:	5b 83       	std	Y+3, r21	; 0x03
    3f02:	5d 82       	std	Y+5, r5	; 0x05
    3f04:	4c 82       	std	Y+4, r4	; 0x04
    3f06:	9f 82       	std	Y+7, r9	; 0x07
    3f08:	8e 82       	std	Y+6, r8	; 0x06
    3f0a:	88 81       	ld	r24, Y
    3f0c:	99 81       	ldd	r25, Y+1	; 0x01
    3f0e:	f6 01       	movw	r30, r12
    3f10:	38 96       	adiw	r30, 0x08	; 8
    3f12:	d9 86       	std	Y+9, r13	; 0x09
    3f14:	c8 86       	std	Y+8, r12	; 0x08
    3f16:	93 83       	std	Z+3, r25	; 0x03
    3f18:	82 83       	std	Z+2, r24	; 0x02
    3f1a:	15 82       	std	Z+5, r1	; 0x05
    3f1c:	14 82       	std	Z+4, r1	; 0x04
    3f1e:	17 82       	std	Z+7, r1	; 0x07
    3f20:	16 82       	std	Z+6, r1	; 0x06
    3f22:	21 15       	cp	r18, r1
    3f24:	31 05       	cpc	r19, r1
    3f26:	41 05       	cpc	r20, r1
    3f28:	51 05       	cpc	r21, r1
    3f2a:	51 f0       	breq	.+20     	; 0x3f40 <_ZN6System5AlarmC1ERKmPNS_7HandlerEib+0xd0>
    3f2c:	f8 94       	cli
    3f2e:	bf 01       	movw	r22, r30
    3f30:	8b ea       	ldi	r24, 0xAB	; 171
    3f32:	93 e0       	ldi	r25, 0x03	; 3
    3f34:	0e 94 d5 1a 	call	0x35aa	; 0x35aa <_ZN6System12Ordered_ListINS_5AlarmEiNS_13List_Elements21Doubly_Linked_OrderedIS1_iEELb1EE6insertEPS4_>
    3f38:	77 20       	and	r7, r7
    3f3a:	51 f0       	breq	.+20     	; 0x3f50 <_ZN6System5AlarmC1ERKmPNS_7HandlerEib+0xe0>
    3f3c:	78 94       	sei
    3f3e:	08 c0       	rjmp	.+16     	; 0x3f50 <_ZN6System5AlarmC1ERKmPNS_7HandlerEib+0xe0>
    3f40:	d2 01       	movw	r26, r4
    3f42:	ed 91       	ld	r30, X+
    3f44:	fc 91       	ld	r31, X
    3f46:	04 80       	ldd	r0, Z+4	; 0x04
    3f48:	f5 81       	ldd	r31, Z+5	; 0x05
    3f4a:	e0 2d       	mov	r30, r0
    3f4c:	c2 01       	movw	r24, r4
    3f4e:	09 95       	icall
    3f50:	df 91       	pop	r29
    3f52:	cf 91       	pop	r28
    3f54:	1f 91       	pop	r17
    3f56:	0f 91       	pop	r16
    3f58:	ff 90       	pop	r15
    3f5a:	ef 90       	pop	r14
    3f5c:	df 90       	pop	r13
    3f5e:	cf 90       	pop	r12
    3f60:	bf 90       	pop	r11
    3f62:	af 90       	pop	r10
    3f64:	9f 90       	pop	r9
    3f66:	8f 90       	pop	r8
    3f68:	7f 90       	pop	r7
    3f6a:	5f 90       	pop	r5
    3f6c:	4f 90       	pop	r4
    3f6e:	08 95       	ret

00003f70 <_ZN6System5Alarm5delayERKm>:
    3f70:	ef 92       	push	r14
    3f72:	ff 92       	push	r15
    3f74:	0f 93       	push	r16
    3f76:	cf 93       	push	r28
    3f78:	df 93       	push	r29
    3f7a:	cd b7       	in	r28, 0x3d	; 61
    3f7c:	de b7       	in	r29, 0x3e	; 62
    3f7e:	64 97       	sbiw	r28, 0x14	; 20
    3f80:	0f b6       	in	r0, 0x3f	; 63
    3f82:	f8 94       	cli
    3f84:	de bf       	out	0x3e, r29	; 62
    3f86:	0f be       	out	0x3f, r0	; 63
    3f88:	cd bf       	out	0x3d, r28	; 61
    3f8a:	bc 01       	movw	r22, r24
    3f8c:	fc 01       	movw	r30, r24
    3f8e:	80 81       	ld	r24, Z
    3f90:	91 81       	ldd	r25, Z+1	; 0x01
    3f92:	a2 81       	ldd	r26, Z+2	; 0x02
    3f94:	b3 81       	ldd	r27, Z+3	; 0x03
    3f96:	00 97       	sbiw	r24, 0x00	; 0
    3f98:	a1 05       	cpc	r26, r1
    3f9a:	b1 05       	cpc	r27, r1
    3f9c:	59 f1       	breq	.+86     	; 0x3ff4 <_ZN6System5Alarm5delayERKm+0x84>
    3f9e:	f8 94       	cli
    3fa0:	e0 91 c6 03 	lds	r30, 0x03C6
    3fa4:	f0 91 c7 03 	lds	r31, 0x03C7
    3fa8:	20 81       	ld	r18, Z
    3faa:	31 81       	ldd	r19, Z+1	; 0x01
    3fac:	81 e0       	ldi	r24, 0x01	; 1
    3fae:	92 e0       	ldi	r25, 0x02	; 2
    3fb0:	9a 83       	std	Y+2, r25	; 0x02
    3fb2:	89 83       	std	Y+1, r24	; 0x01
    3fb4:	3c 83       	std	Y+4, r19	; 0x04
    3fb6:	2b 83       	std	Y+3, r18	; 0x03
    3fb8:	f5 e0       	ldi	r31, 0x05	; 5
    3fba:	ef 2e       	mov	r14, r31
    3fbc:	f1 2c       	mov	r15, r1
    3fbe:	ec 0e       	add	r14, r28
    3fc0:	fd 1e       	adc	r15, r29
    3fc2:	00 e0       	ldi	r16, 0x00	; 0
    3fc4:	21 e0       	ldi	r18, 0x01	; 1
    3fc6:	30 e0       	ldi	r19, 0x00	; 0
    3fc8:	ae 01       	movw	r20, r28
    3fca:	4f 5f       	subi	r20, 0xFF	; 255
    3fcc:	5f 4f       	sbci	r21, 0xFF	; 255
    3fce:	c7 01       	movw	r24, r14
    3fd0:	0e 94 38 1f 	call	0x3e70	; 0x3e70 <_ZN6System5AlarmC1ERKmPNS_7HandlerEib>
    3fd4:	e0 91 c6 03 	lds	r30, 0x03C6
    3fd8:	f0 91 c7 03 	lds	r31, 0x03C7
    3fdc:	80 81       	ld	r24, Z
    3fde:	91 81       	ldd	r25, Z+1	; 0x01
    3fe0:	0e 94 06 24 	call	0x480c	; 0x480c <_ZN6System6Thread7suspendEv>
    3fe4:	78 94       	sei
    3fe6:	c7 01       	movw	r24, r14
    3fe8:	0e 94 68 1b 	call	0x36d0	; 0x36d0 <_ZN6System5AlarmD1Ev>
    3fec:	8b e0       	ldi	r24, 0x0B	; 11
    3fee:	92 e0       	ldi	r25, 0x02	; 2
    3ff0:	9a 83       	std	Y+2, r25	; 0x02
    3ff2:	89 83       	std	Y+1, r24	; 0x01
    3ff4:	64 96       	adiw	r28, 0x14	; 20
    3ff6:	0f b6       	in	r0, 0x3f	; 63
    3ff8:	f8 94       	cli
    3ffa:	de bf       	out	0x3e, r29	; 62
    3ffc:	0f be       	out	0x3f, r0	; 63
    3ffe:	cd bf       	out	0x3d, r28	; 61
    4000:	df 91       	pop	r29
    4002:	cf 91       	pop	r28
    4004:	0f 91       	pop	r16
    4006:	ff 90       	pop	r15
    4008:	ef 90       	pop	r14
    400a:	08 95       	ret

0000400c <_Z41__static_initialization_and_destruction_0ii>:
    400c:	6f 5f       	subi	r22, 0xFF	; 255
    400e:	7f 4f       	sbci	r23, 0xFF	; 255
    4010:	09 f0       	breq	.+2      	; 0x4014 <_Z41__static_initialization_and_destruction_0ii+0x8>
    4012:	08 95       	ret
    4014:	01 97       	sbiw	r24, 0x01	; 1
    4016:	e9 f7       	brne	.-6      	; 0x4012 <_Z41__static_initialization_and_destruction_0ii+0x6>
    4018:	10 92 c1 03 	sts	0x03C1, r1
    401c:	10 92 c0 03 	sts	0x03C0, r1
    4020:	10 92 c3 03 	sts	0x03C3, r1
    4024:	10 92 c2 03 	sts	0x03C2, r1
    4028:	10 92 c5 03 	sts	0x03C5, r1
    402c:	10 92 c4 03 	sts	0x03C4, r1
    4030:	10 92 c7 03 	sts	0x03C7, r1
    4034:	10 92 c6 03 	sts	0x03C6, r1
    4038:	08 95       	ret

0000403a <_GLOBAL__I__ZN6System6Thread13_thread_countE>:
    403a:	6f ef       	ldi	r22, 0xFF	; 255
    403c:	7f ef       	ldi	r23, 0xFF	; 255
    403e:	81 e0       	ldi	r24, 0x01	; 1
    4040:	90 e0       	ldi	r25, 0x00	; 0
    4042:	0e 94 06 20 	call	0x400c	; 0x400c <_Z41__static_initialization_and_destruction_0ii>
    4046:	08 95       	ret

00004048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>:
    4048:	ef 92       	push	r14
    404a:	ff 92       	push	r15
    404c:	0f 93       	push	r16
    404e:	1f 93       	push	r17
    4050:	cf 93       	push	r28
    4052:	df 93       	push	r29
    4054:	7c 01       	movw	r14, r24
    4056:	db 01       	movw	r26, r22
    4058:	ec 01       	movw	r28, r24
    405a:	48 81       	ld	r20, Y
    405c:	59 81       	ldd	r21, Y+1	; 0x01
    405e:	41 15       	cp	r20, r1
    4060:	51 05       	cpc	r21, r1
    4062:	71 f4       	brne	.+28     	; 0x4080 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0x38>
    4064:	fb 01       	movw	r30, r22
    4066:	15 82       	std	Z+5, r1	; 0x05
    4068:	14 82       	std	Z+4, r1	; 0x04
    406a:	17 82       	std	Z+7, r1	; 0x07
    406c:	16 82       	std	Z+6, r1	; 0x06
    406e:	7b 83       	std	Y+3, r23	; 0x03
    4070:	6a 83       	std	Y+2, r22	; 0x02
    4072:	7d 83       	std	Y+5, r23	; 0x05
    4074:	6c 83       	std	Y+4, r22	; 0x04
    4076:	41 e0       	ldi	r20, 0x01	; 1
    4078:	50 e0       	ldi	r21, 0x00	; 0
    407a:	59 83       	std	Y+1, r21	; 0x01
    407c:	48 83       	st	Y, r20
    407e:	7e c0       	rjmp	.+252    	; 0x417c <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0x134>
    4080:	ec 01       	movw	r28, r24
    4082:	0a 81       	ldd	r16, Y+2	; 0x02
    4084:	1b 81       	ldd	r17, Y+3	; 0x03
    4086:	f8 01       	movw	r30, r16
    4088:	22 81       	ldd	r18, Z+2	; 0x02
    408a:	33 81       	ldd	r19, Z+3	; 0x03
    408c:	eb 01       	movw	r28, r22
    408e:	8a 81       	ldd	r24, Y+2	; 0x02
    4090:	9b 81       	ldd	r25, Y+3	; 0x03
    4092:	82 17       	cp	r24, r18
    4094:	93 07       	cpc	r25, r19
    4096:	94 f1       	brlt	.+100    	; 0x40fc <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0xb4>
    4098:	e8 01       	movw	r28, r16
    409a:	8e 81       	ldd	r24, Y+6	; 0x06
    409c:	9f 81       	ldd	r25, Y+7	; 0x07
    409e:	00 97       	sbiw	r24, 0x00	; 0
    40a0:	69 f1       	breq	.+90     	; 0x40fc <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0xb4>
    40a2:	fc 01       	movw	r30, r24
    40a4:	bf 01       	movw	r22, r30
    40a6:	22 81       	ldd	r18, Z+2	; 0x02
    40a8:	33 81       	ldd	r19, Z+3	; 0x03
    40aa:	ed 01       	movw	r28, r26
    40ac:	8a 81       	ldd	r24, Y+2	; 0x02
    40ae:	9b 81       	ldd	r25, Y+3	; 0x03
    40b0:	82 17       	cp	r24, r18
    40b2:	93 07       	cpc	r25, r19
    40b4:	24 f1       	brlt	.+72     	; 0x40fe <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0xb6>
    40b6:	06 80       	ldd	r0, Z+6	; 0x06
    40b8:	f7 81       	ldd	r31, Z+7	; 0x07
    40ba:	e0 2d       	mov	r30, r0
    40bc:	30 97       	sbiw	r30, 0x00	; 0
    40be:	91 f7       	brne	.-28     	; 0x40a4 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0x5c>
    40c0:	eb 01       	movw	r28, r22
    40c2:	2a 81       	ldd	r18, Y+2	; 0x02
    40c4:	3b 81       	ldd	r19, Y+3	; 0x03
    40c6:	fd 01       	movw	r30, r26
    40c8:	82 81       	ldd	r24, Z+2	; 0x02
    40ca:	93 81       	ldd	r25, Z+3	; 0x03
    40cc:	82 17       	cp	r24, r18
    40ce:	93 07       	cpc	r25, r19
    40d0:	fc f4       	brge	.+62     	; 0x4110 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0xc8>
    40d2:	eb 01       	movw	r28, r22
    40d4:	ec 81       	ldd	r30, Y+4	; 0x04
    40d6:	fd 81       	ldd	r31, Y+5	; 0x05
    40d8:	30 97       	sbiw	r30, 0x00	; 0
    40da:	f1 f1       	breq	.+124    	; 0x4158 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0x110>
    40dc:	b7 83       	std	Z+7, r27	; 0x07
    40de:	a6 83       	std	Z+6, r26	; 0x06
    40e0:	eb 01       	movw	r28, r22
    40e2:	bd 83       	std	Y+5, r27	; 0x05
    40e4:	ac 83       	std	Y+4, r26	; 0x04
    40e6:	ed 01       	movw	r28, r26
    40e8:	fd 83       	std	Y+5, r31	; 0x05
    40ea:	ec 83       	std	Y+4, r30	; 0x04
    40ec:	7f 83       	std	Y+7, r23	; 0x07
    40ee:	6e 83       	std	Y+6, r22	; 0x06
    40f0:	4f 5f       	subi	r20, 0xFF	; 255
    40f2:	5f 4f       	sbci	r21, 0xFF	; 255
    40f4:	f7 01       	movw	r30, r14
    40f6:	51 83       	std	Z+1, r21	; 0x01
    40f8:	40 83       	st	Z, r20
    40fa:	40 c0       	rjmp	.+128    	; 0x417c <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0x134>
    40fc:	b8 01       	movw	r22, r16
    40fe:	eb 01       	movw	r28, r22
    4100:	2a 81       	ldd	r18, Y+2	; 0x02
    4102:	3b 81       	ldd	r19, Y+3	; 0x03
    4104:	fd 01       	movw	r30, r26
    4106:	82 81       	ldd	r24, Z+2	; 0x02
    4108:	93 81       	ldd	r25, Z+3	; 0x03
    410a:	82 17       	cp	r24, r18
    410c:	93 07       	cpc	r25, r19
    410e:	0c f3       	brlt	.-62     	; 0x40d2 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0x8a>
    4110:	41 15       	cp	r20, r1
    4112:	51 05       	cpc	r21, r1
    4114:	91 f0       	breq	.+36     	; 0x413a <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0xf2>
    4116:	e7 01       	movw	r28, r14
    4118:	ec 81       	ldd	r30, Y+4	; 0x04
    411a:	fd 81       	ldd	r31, Y+5	; 0x05
    411c:	b7 83       	std	Z+7, r27	; 0x07
    411e:	a6 83       	std	Z+6, r26	; 0x06
    4120:	ed 01       	movw	r28, r26
    4122:	fd 83       	std	Y+5, r31	; 0x05
    4124:	ec 83       	std	Y+4, r30	; 0x04
    4126:	1f 82       	std	Y+7, r1	; 0x07
    4128:	1e 82       	std	Y+6, r1	; 0x06
    412a:	f7 01       	movw	r30, r14
    412c:	b5 83       	std	Z+5, r27	; 0x05
    412e:	a4 83       	std	Z+4, r26	; 0x04
    4130:	4f 5f       	subi	r20, 0xFF	; 255
    4132:	5f 4f       	sbci	r21, 0xFF	; 255
    4134:	51 83       	std	Z+1, r21	; 0x01
    4136:	40 83       	st	Z, r20
    4138:	21 c0       	rjmp	.+66     	; 0x417c <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0x134>
    413a:	fd 01       	movw	r30, r26
    413c:	15 82       	std	Z+5, r1	; 0x05
    413e:	14 82       	std	Z+4, r1	; 0x04
    4140:	17 82       	std	Z+7, r1	; 0x07
    4142:	16 82       	std	Z+6, r1	; 0x06
    4144:	e7 01       	movw	r28, r14
    4146:	bb 83       	std	Y+3, r27	; 0x03
    4148:	aa 83       	std	Y+2, r26	; 0x02
    414a:	bd 83       	std	Y+5, r27	; 0x05
    414c:	ac 83       	std	Y+4, r26	; 0x04
    414e:	41 e0       	ldi	r20, 0x01	; 1
    4150:	50 e0       	ldi	r21, 0x00	; 0
    4152:	59 83       	std	Y+1, r21	; 0x01
    4154:	48 83       	st	Y, r20
    4156:	12 c0       	rjmp	.+36     	; 0x417c <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0x134>
    4158:	41 15       	cp	r20, r1
    415a:	51 05       	cpc	r21, r1
    415c:	71 f3       	breq	.-36     	; 0x413a <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_+0xf2>
    415e:	fd 01       	movw	r30, r26
    4160:	15 82       	std	Z+5, r1	; 0x05
    4162:	14 82       	std	Z+4, r1	; 0x04
    4164:	17 83       	std	Z+7, r17	; 0x07
    4166:	06 83       	std	Z+6, r16	; 0x06
    4168:	e8 01       	movw	r28, r16
    416a:	bd 83       	std	Y+5, r27	; 0x05
    416c:	ac 83       	std	Y+4, r26	; 0x04
    416e:	f7 01       	movw	r30, r14
    4170:	b3 83       	std	Z+3, r27	; 0x03
    4172:	a2 83       	std	Z+2, r26	; 0x02
    4174:	4f 5f       	subi	r20, 0xFF	; 255
    4176:	5f 4f       	sbci	r21, 0xFF	; 255
    4178:	51 83       	std	Z+1, r21	; 0x01
    417a:	40 83       	st	Z, r20
    417c:	df 91       	pop	r29
    417e:	cf 91       	pop	r28
    4180:	1f 91       	pop	r17
    4182:	0f 91       	pop	r16
    4184:	ff 90       	pop	r15
    4186:	ef 90       	pop	r14
    4188:	08 95       	ret

0000418a <_ZN6System6Thread10rescheduleEv>:
    418a:	cf 93       	push	r28
    418c:	df 93       	push	r29
    418e:	e0 91 c6 03 	lds	r30, 0x03C6
    4192:	f0 91 c7 03 	lds	r31, 0x03C7
    4196:	c0 81       	ld	r28, Z
    4198:	d1 81       	ldd	r29, Z+1	; 0x01
    419a:	80 91 c6 03 	lds	r24, 0x03C6
    419e:	90 91 c7 03 	lds	r25, 0x03C7
    41a2:	80 91 c0 03 	lds	r24, 0x03C0
    41a6:	90 91 c1 03 	lds	r25, 0x03C1
    41aa:	00 97       	sbiw	r24, 0x00	; 0
    41ac:	99 f1       	breq	.+102    	; 0x4214 <_ZN6System6Thread10rescheduleEv+0x8a>
    41ae:	40 91 c6 03 	lds	r20, 0x03C6
    41b2:	50 91 c7 03 	lds	r21, 0x03C7
    41b6:	81 30       	cpi	r24, 0x01	; 1
    41b8:	91 05       	cpc	r25, r1
    41ba:	09 f4       	brne	.+2      	; 0x41be <_ZN6System6Thread10rescheduleEv+0x34>
    41bc:	62 c0       	rjmp	.+196    	; 0x4282 <_ZN6System6Thread10rescheduleEv+0xf8>
    41be:	fa 01       	movw	r30, r20
    41c0:	a4 81       	ldd	r26, Z+4	; 0x04
    41c2:	b5 81       	ldd	r27, Z+5	; 0x05
    41c4:	10 97       	sbiw	r26, 0x00	; 0
    41c6:	09 f4       	brne	.+2      	; 0x41ca <_ZN6System6Thread10rescheduleEv+0x40>
    41c8:	69 c0       	rjmp	.+210    	; 0x429c <_ZN6System6Thread10rescheduleEv+0x112>
    41ca:	fa 01       	movw	r30, r20
    41cc:	26 81       	ldd	r18, Z+6	; 0x06
    41ce:	37 81       	ldd	r19, Z+7	; 0x07
    41d0:	21 15       	cp	r18, r1
    41d2:	31 05       	cpc	r19, r1
    41d4:	09 f4       	brne	.+2      	; 0x41d8 <_ZN6System6Thread10rescheduleEv+0x4e>
    41d6:	75 c0       	rjmp	.+234    	; 0x42c2 <_ZN6System6Thread10rescheduleEv+0x138>
    41d8:	fd 01       	movw	r30, r26
    41da:	37 83       	std	Z+7, r19	; 0x07
    41dc:	26 83       	std	Z+6, r18	; 0x06
    41de:	fa 01       	movw	r30, r20
    41e0:	26 81       	ldd	r18, Z+6	; 0x06
    41e2:	37 81       	ldd	r19, Z+7	; 0x07
    41e4:	f9 01       	movw	r30, r18
    41e6:	b5 83       	std	Z+5, r27	; 0x05
    41e8:	a4 83       	std	Z+4, r26	; 0x04
    41ea:	01 97       	sbiw	r24, 0x01	; 1
    41ec:	90 93 c1 03 	sts	0x03C1, r25
    41f0:	80 93 c0 03 	sts	0x03C0, r24
    41f4:	60 91 c6 03 	lds	r22, 0x03C6
    41f8:	70 91 c7 03 	lds	r23, 0x03C7
    41fc:	80 ec       	ldi	r24, 0xC0	; 192
    41fe:	93 e0       	ldi	r25, 0x03	; 3
    4200:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    4204:	80 91 c2 03 	lds	r24, 0x03C2
    4208:	90 91 c3 03 	lds	r25, 0x03C3
    420c:	90 93 c7 03 	sts	0x03C7, r25
    4210:	80 93 c6 03 	sts	0x03C6, r24
    4214:	e0 91 c6 03 	lds	r30, 0x03C6
    4218:	f0 91 c7 03 	lds	r31, 0x03C7
    421c:	01 90       	ld	r0, Z+
    421e:	f0 81       	ld	r31, Z
    4220:	e0 2d       	mov	r30, r0
    4222:	80 91 b5 03 	lds	r24, 0x03B5
    4226:	90 91 b6 03 	lds	r25, 0x03B6
    422a:	a0 91 b7 03 	lds	r26, 0x03B7
    422e:	b0 91 b8 03 	lds	r27, 0x03B8
    4232:	80 91 b1 03 	lds	r24, 0x03B1
    4236:	90 91 b2 03 	lds	r25, 0x03B2
    423a:	a0 91 b3 03 	lds	r26, 0x03B3
    423e:	b0 91 b4 03 	lds	r27, 0x03B4
    4242:	80 93 b5 03 	sts	0x03B5, r24
    4246:	90 93 b6 03 	sts	0x03B6, r25
    424a:	a0 93 b7 03 	sts	0x03B7, r26
    424e:	b0 93 b8 03 	sts	0x03B8, r27
    4252:	ce 17       	cp	r28, r30
    4254:	df 07       	cpc	r29, r31
    4256:	09 f4       	brne	.+2      	; 0x425a <_ZN6System6Thread10rescheduleEv+0xd0>
    4258:	47 c0       	rjmp	.+142    	; 0x42e8 <_ZN6System6Thread10rescheduleEv+0x15e>
    425a:	8c 81       	ldd	r24, Y+4	; 0x04
    425c:	9d 81       	ldd	r25, Y+5	; 0x05
    425e:	02 97       	sbiw	r24, 0x02	; 2
    4260:	59 f0       	breq	.+22     	; 0x4278 <_ZN6System6Thread10rescheduleEv+0xee>
    4262:	82 e0       	ldi	r24, 0x02	; 2
    4264:	90 e0       	ldi	r25, 0x00	; 0
    4266:	95 83       	std	Z+5, r25	; 0x05
    4268:	84 83       	std	Z+4, r24	; 0x04
    426a:	62 81       	ldd	r22, Z+2	; 0x02
    426c:	73 81       	ldd	r23, Z+3	; 0x03
    426e:	ce 01       	movw	r24, r28
    4270:	02 96       	adiw	r24, 0x02	; 2
    4272:	0e 94 35 31 	call	0x626a	; 0x626a <_ZN6System4AVR814switch_contextEPVPNS0_7ContextES2_>
    4276:	38 c0       	rjmp	.+112    	; 0x42e8 <_ZN6System6Thread10rescheduleEv+0x15e>
    4278:	81 e0       	ldi	r24, 0x01	; 1
    427a:	90 e0       	ldi	r25, 0x00	; 0
    427c:	9d 83       	std	Y+5, r25	; 0x05
    427e:	8c 83       	std	Y+4, r24	; 0x04
    4280:	f0 cf       	rjmp	.-32     	; 0x4262 <_ZN6System6Thread10rescheduleEv+0xd8>
    4282:	10 92 c3 03 	sts	0x03C3, r1
    4286:	10 92 c2 03 	sts	0x03C2, r1
    428a:	10 92 c5 03 	sts	0x03C5, r1
    428e:	10 92 c4 03 	sts	0x03C4, r1
    4292:	10 92 c1 03 	sts	0x03C1, r1
    4296:	10 92 c0 03 	sts	0x03C0, r1
    429a:	ac cf       	rjmp	.-168    	; 0x41f4 <_ZN6System6Thread10rescheduleEv+0x6a>
    429c:	e0 91 c2 03 	lds	r30, 0x03C2
    42a0:	f0 91 c3 03 	lds	r31, 0x03C3
    42a4:	06 80       	ldd	r0, Z+6	; 0x06
    42a6:	f7 81       	ldd	r31, Z+7	; 0x07
    42a8:	e0 2d       	mov	r30, r0
    42aa:	f0 93 c3 03 	sts	0x03C3, r31
    42ae:	e0 93 c2 03 	sts	0x03C2, r30
    42b2:	15 82       	std	Z+5, r1	; 0x05
    42b4:	14 82       	std	Z+4, r1	; 0x04
    42b6:	01 97       	sbiw	r24, 0x01	; 1
    42b8:	90 93 c1 03 	sts	0x03C1, r25
    42bc:	80 93 c0 03 	sts	0x03C0, r24
    42c0:	99 cf       	rjmp	.-206    	; 0x41f4 <_ZN6System6Thread10rescheduleEv+0x6a>
    42c2:	e0 91 c4 03 	lds	r30, 0x03C4
    42c6:	f0 91 c5 03 	lds	r31, 0x03C5
    42ca:	04 80       	ldd	r0, Z+4	; 0x04
    42cc:	f5 81       	ldd	r31, Z+5	; 0x05
    42ce:	e0 2d       	mov	r30, r0
    42d0:	f0 93 c5 03 	sts	0x03C5, r31
    42d4:	e0 93 c4 03 	sts	0x03C4, r30
    42d8:	17 82       	std	Z+7, r1	; 0x07
    42da:	16 82       	std	Z+6, r1	; 0x06
    42dc:	01 97       	sbiw	r24, 0x01	; 1
    42de:	90 93 c1 03 	sts	0x03C1, r25
    42e2:	80 93 c0 03 	sts	0x03C0, r24
    42e6:	86 cf       	rjmp	.-244    	; 0x41f4 <_ZN6System6Thread10rescheduleEv+0x6a>
    42e8:	78 94       	sei
    42ea:	df 91       	pop	r29
    42ec:	cf 91       	pop	r28
    42ee:	08 95       	ret

000042f0 <_ZN6System6Thread10wakeup_allEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>:
    42f0:	0f 93       	push	r16
    42f2:	1f 93       	push	r17
    42f4:	cf 93       	push	r28
    42f6:	df 93       	push	r29
    42f8:	ec 01       	movw	r28, r24
    42fa:	f8 94       	cli
    42fc:	80 91 c6 03 	lds	r24, 0x03C6
    4300:	90 91 c7 03 	lds	r25, 0x03C7
    4304:	01 e0       	ldi	r16, 0x01	; 1
    4306:	10 e0       	ldi	r17, 0x00	; 0
    4308:	88 81       	ld	r24, Y
    430a:	99 81       	ldd	r25, Y+1	; 0x01
    430c:	00 97       	sbiw	r24, 0x00	; 0
    430e:	e1 f1       	breq	.+120    	; 0x4388 <_ZN6System6Thread10wakeup_allEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x98>
    4310:	81 30       	cpi	r24, 0x01	; 1
    4312:	91 05       	cpc	r25, r1
    4314:	81 f1       	breq	.+96     	; 0x4376 <_ZN6System6Thread10wakeup_allEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x86>
    4316:	aa 81       	ldd	r26, Y+2	; 0x02
    4318:	bb 81       	ldd	r27, Y+3	; 0x03
    431a:	fd 01       	movw	r30, r26
    431c:	26 81       	ldd	r18, Z+6	; 0x06
    431e:	37 81       	ldd	r19, Z+7	; 0x07
    4320:	3b 83       	std	Y+3, r19	; 0x03
    4322:	2a 83       	std	Y+2, r18	; 0x02
    4324:	f9 01       	movw	r30, r18
    4326:	15 82       	std	Z+5, r1	; 0x05
    4328:	14 82       	std	Z+4, r1	; 0x04
    432a:	01 97       	sbiw	r24, 0x01	; 1
    432c:	99 83       	std	Y+1, r25	; 0x01
    432e:	88 83       	st	Y, r24
    4330:	ed 91       	ld	r30, X+
    4332:	fc 91       	ld	r31, X
    4334:	15 83       	std	Z+5, r17	; 0x05
    4336:	04 83       	std	Z+4, r16	; 0x04
    4338:	17 82       	std	Z+7, r1	; 0x07
    433a:	16 82       	std	Z+6, r1	; 0x06
    433c:	80 91 c6 03 	lds	r24, 0x03C6
    4340:	90 91 c7 03 	lds	r25, 0x03C7
    4344:	3a 96       	adiw	r30, 0x0a	; 10
    4346:	bf 01       	movw	r22, r30
    4348:	80 ec       	ldi	r24, 0xC0	; 192
    434a:	93 e0       	ldi	r25, 0x03	; 3
    434c:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    4350:	80 91 c6 03 	lds	r24, 0x03C6
    4354:	90 91 c7 03 	lds	r25, 0x03C7
    4358:	89 2b       	or	r24, r25
    435a:	b1 f6       	brne	.-84     	; 0x4308 <_ZN6System6Thread10wakeup_allEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x18>
    435c:	80 91 c2 03 	lds	r24, 0x03C2
    4360:	90 91 c3 03 	lds	r25, 0x03C3
    4364:	90 93 c7 03 	sts	0x03C7, r25
    4368:	80 93 c6 03 	sts	0x03C6, r24
    436c:	88 81       	ld	r24, Y
    436e:	99 81       	ldd	r25, Y+1	; 0x01
    4370:	00 97       	sbiw	r24, 0x00	; 0
    4372:	71 f6       	brne	.-100    	; 0x4310 <_ZN6System6Thread10wakeup_allEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x20>
    4374:	09 c0       	rjmp	.+18     	; 0x4388 <_ZN6System6Thread10wakeup_allEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x98>
    4376:	aa 81       	ldd	r26, Y+2	; 0x02
    4378:	bb 81       	ldd	r27, Y+3	; 0x03
    437a:	1b 82       	std	Y+3, r1	; 0x03
    437c:	1a 82       	std	Y+2, r1	; 0x02
    437e:	1d 82       	std	Y+5, r1	; 0x05
    4380:	1c 82       	std	Y+4, r1	; 0x04
    4382:	19 82       	std	Y+1, r1	; 0x01
    4384:	18 82       	st	Y, r1
    4386:	d4 cf       	rjmp	.-88     	; 0x4330 <_ZN6System6Thread10wakeup_allEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x40>
    4388:	0e 94 c5 20 	call	0x418a	; 0x418a <_ZN6System6Thread10rescheduleEv>
    438c:	78 94       	sei
    438e:	df 91       	pop	r29
    4390:	cf 91       	pop	r28
    4392:	1f 91       	pop	r17
    4394:	0f 91       	pop	r16
    4396:	08 95       	ret

00004398 <_ZN6System6Thread8priorityERKNS_19Scheduling_Criteria8PriorityE>:
    4398:	cf 93       	push	r28
    439a:	df 93       	push	r29
    439c:	cd b7       	in	r28, 0x3d	; 61
    439e:	de b7       	in	r29, 0x3e	; 62
    43a0:	24 97       	sbiw	r28, 0x04	; 4
    43a2:	0f b6       	in	r0, 0x3f	; 63
    43a4:	f8 94       	cli
    43a6:	de bf       	out	0x3e, r29	; 62
    43a8:	0f be       	out	0x3f, r0	; 63
    43aa:	cd bf       	out	0x3d, r28	; 61
    43ac:	dc 01       	movw	r26, r24
    43ae:	fb 01       	movw	r30, r22
    43b0:	f8 94       	cli
    43b2:	80 81       	ld	r24, Z
    43b4:	91 81       	ldd	r25, Z+1	; 0x01
    43b6:	9c 83       	std	Y+4, r25	; 0x04
    43b8:	8b 83       	std	Y+3, r24	; 0x03
    43ba:	1a 96       	adiw	r26, 0x0a	; 10
    43bc:	8b 81       	ldd	r24, Y+3	; 0x03
    43be:	9c 81       	ldd	r25, Y+4	; 0x04
    43c0:	9a 83       	std	Y+2, r25	; 0x02
    43c2:	89 83       	std	Y+1, r24	; 0x01
    43c4:	89 81       	ldd	r24, Y+1	; 0x01
    43c6:	9a 81       	ldd	r25, Y+2	; 0x02
    43c8:	fd 01       	movw	r30, r26
    43ca:	93 83       	std	Z+3, r25	; 0x03
    43cc:	82 83       	std	Z+2, r24	; 0x02
    43ce:	0e 94 c5 20 	call	0x418a	; 0x418a <_ZN6System6Thread10rescheduleEv>
    43d2:	78 94       	sei
    43d4:	24 96       	adiw	r28, 0x04	; 4
    43d6:	0f b6       	in	r0, 0x3f	; 63
    43d8:	f8 94       	cli
    43da:	de bf       	out	0x3e, r29	; 62
    43dc:	0f be       	out	0x3f, r0	; 63
    43de:	cd bf       	out	0x3d, r28	; 61
    43e0:	df 91       	pop	r29
    43e2:	cf 91       	pop	r28
    43e4:	08 95       	ret

000043e6 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj>:
    43e6:	cf 93       	push	r28
    43e8:	df 93       	push	r29
    43ea:	ec 01       	movw	r28, r24
    43ec:	8a 81       	ldd	r24, Y+2	; 0x02
    43ee:	9b 81       	ldd	r25, Y+3	; 0x03
    43f0:	80 91 c8 03 	lds	r24, 0x03C8
    43f4:	90 91 c9 03 	lds	r25, 0x03C9
    43f8:	01 96       	adiw	r24, 0x01	; 1
    43fa:	90 93 c9 03 	sts	0x03C9, r25
    43fe:	80 93 c8 03 	sts	0x03C8, r24
    4402:	80 91 c6 03 	lds	r24, 0x03C6
    4406:	90 91 c7 03 	lds	r25, 0x03C7
    440a:	be 01       	movw	r22, r28
    440c:	66 5f       	subi	r22, 0xF6	; 246
    440e:	7f 4f       	sbci	r23, 0xFF	; 255
    4410:	80 ec       	ldi	r24, 0xC0	; 192
    4412:	93 e0       	ldi	r25, 0x03	; 3
    4414:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    4418:	80 91 c6 03 	lds	r24, 0x03C6
    441c:	90 91 c7 03 	lds	r25, 0x03C7
    4420:	89 2b       	or	r24, r25
    4422:	09 f4       	brne	.+2      	; 0x4426 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x40>
    4424:	72 c0       	rjmp	.+228    	; 0x450a <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x124>
    4426:	8c 81       	ldd	r24, Y+4	; 0x04
    4428:	9d 81       	ldd	r25, Y+5	; 0x05
    442a:	01 97       	sbiw	r24, 0x01	; 1
    442c:	09 f4       	brne	.+2      	; 0x4430 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x4a>
    442e:	9d c0       	rjmp	.+314    	; 0x456a <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x184>
    4430:	8c 81       	ldd	r24, Y+4	; 0x04
    4432:	9d 81       	ldd	r25, Y+5	; 0x05
    4434:	02 97       	sbiw	r24, 0x02	; 2
    4436:	09 f4       	brne	.+2      	; 0x443a <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x54>
    4438:	98 c0       	rjmp	.+304    	; 0x456a <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x184>
    443a:	80 91 c6 03 	lds	r24, 0x03C6
    443e:	90 91 c7 03 	lds	r25, 0x03C7
    4442:	20 91 c2 03 	lds	r18, 0x03C2
    4446:	30 91 c3 03 	lds	r19, 0x03C3
    444a:	21 15       	cp	r18, r1
    444c:	31 05       	cpc	r19, r1
    444e:	09 f4       	brne	.+2      	; 0x4452 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x6c>
    4450:	8c c0       	rjmp	.+280    	; 0x456a <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x184>
    4452:	f9 01       	movw	r30, r18
    4454:	80 81       	ld	r24, Z
    4456:	91 81       	ldd	r25, Z+1	; 0x01
    4458:	c8 17       	cp	r28, r24
    445a:	d9 07       	cpc	r29, r25
    445c:	09 f4       	brne	.+2      	; 0x4460 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x7a>
    445e:	3f c0       	rjmp	.+126    	; 0x44de <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0xf8>
    4460:	d9 01       	movw	r26, r18
    4462:	fd 01       	movw	r30, r26
    4464:	a6 81       	ldd	r26, Z+6	; 0x06
    4466:	b7 81       	ldd	r27, Z+7	; 0x07
    4468:	10 97       	sbiw	r26, 0x00	; 0
    446a:	09 f4       	brne	.+2      	; 0x446e <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x88>
    446c:	7e c0       	rjmp	.+252    	; 0x456a <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x184>
    446e:	8d 91       	ld	r24, X+
    4470:	9c 91       	ld	r25, X
    4472:	11 97       	sbiw	r26, 0x01	; 1
    4474:	c8 17       	cp	r28, r24
    4476:	d9 07       	cpc	r29, r25
    4478:	a1 f7       	brne	.-24     	; 0x4462 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x7c>
    447a:	80 91 c0 03 	lds	r24, 0x03C0
    447e:	90 91 c1 03 	lds	r25, 0x03C1
    4482:	81 30       	cpi	r24, 0x01	; 1
    4484:	91 05       	cpc	r25, r1
    4486:	99 f1       	breq	.+102    	; 0x44ee <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x108>
    4488:	fd 01       	movw	r30, r26
    448a:	c4 81       	ldd	r28, Z+4	; 0x04
    448c:	d5 81       	ldd	r29, Z+5	; 0x05
    448e:	20 97       	sbiw	r28, 0x00	; 0
    4490:	09 f4       	brne	.+2      	; 0x4494 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0xae>
    4492:	44 c0       	rjmp	.+136    	; 0x451c <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x136>
    4494:	fd 01       	movw	r30, r26
    4496:	26 81       	ldd	r18, Z+6	; 0x06
    4498:	37 81       	ldd	r19, Z+7	; 0x07
    449a:	21 15       	cp	r18, r1
    449c:	31 05       	cpc	r19, r1
    449e:	09 f4       	brne	.+2      	; 0x44a2 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0xbc>
    44a0:	4e c0       	rjmp	.+156    	; 0x453e <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x158>
    44a2:	3f 83       	std	Y+7, r19	; 0x07
    44a4:	2e 83       	std	Y+6, r18	; 0x06
    44a6:	fd 01       	movw	r30, r26
    44a8:	26 81       	ldd	r18, Z+6	; 0x06
    44aa:	37 81       	ldd	r19, Z+7	; 0x07
    44ac:	f9 01       	movw	r30, r18
    44ae:	d5 83       	std	Z+5, r29	; 0x05
    44b0:	c4 83       	std	Z+4, r28	; 0x04
    44b2:	01 97       	sbiw	r24, 0x01	; 1
    44b4:	90 93 c1 03 	sts	0x03C1, r25
    44b8:	80 93 c0 03 	sts	0x03C0, r24
    44bc:	80 91 c6 03 	lds	r24, 0x03C6
    44c0:	90 91 c7 03 	lds	r25, 0x03C7
    44c4:	8a 17       	cp	r24, r26
    44c6:	9b 07       	cpc	r25, r27
    44c8:	09 f0       	breq	.+2      	; 0x44cc <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0xe6>
    44ca:	4f c0       	rjmp	.+158    	; 0x456a <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x184>
    44cc:	80 91 c2 03 	lds	r24, 0x03C2
    44d0:	90 91 c3 03 	lds	r25, 0x03C3
    44d4:	90 93 c7 03 	sts	0x03C7, r25
    44d8:	80 93 c6 03 	sts	0x03C6, r24
    44dc:	46 c0       	rjmp	.+140    	; 0x456a <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x184>
    44de:	d9 01       	movw	r26, r18
    44e0:	80 91 c0 03 	lds	r24, 0x03C0
    44e4:	90 91 c1 03 	lds	r25, 0x03C1
    44e8:	81 30       	cpi	r24, 0x01	; 1
    44ea:	91 05       	cpc	r25, r1
    44ec:	69 f6       	brne	.-102    	; 0x4488 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0xa2>
    44ee:	10 92 c3 03 	sts	0x03C3, r1
    44f2:	10 92 c2 03 	sts	0x03C2, r1
    44f6:	10 92 c5 03 	sts	0x03C5, r1
    44fa:	10 92 c4 03 	sts	0x03C4, r1
    44fe:	01 97       	sbiw	r24, 0x01	; 1
    4500:	90 93 c1 03 	sts	0x03C1, r25
    4504:	80 93 c0 03 	sts	0x03C0, r24
    4508:	d9 cf       	rjmp	.-78     	; 0x44bc <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0xd6>
    450a:	80 91 c2 03 	lds	r24, 0x03C2
    450e:	90 91 c3 03 	lds	r25, 0x03C3
    4512:	90 93 c7 03 	sts	0x03C7, r25
    4516:	80 93 c6 03 	sts	0x03C6, r24
    451a:	85 cf       	rjmp	.-246    	; 0x4426 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x40>
    451c:	00 97       	sbiw	r24, 0x00	; 0
    451e:	71 f2       	breq	.-100    	; 0x44bc <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0xd6>
    4520:	e9 01       	movw	r28, r18
    4522:	ee 81       	ldd	r30, Y+6	; 0x06
    4524:	ff 81       	ldd	r31, Y+7	; 0x07
    4526:	f0 93 c3 03 	sts	0x03C3, r31
    452a:	e0 93 c2 03 	sts	0x03C2, r30
    452e:	15 82       	std	Z+5, r1	; 0x05
    4530:	14 82       	std	Z+4, r1	; 0x04
    4532:	01 97       	sbiw	r24, 0x01	; 1
    4534:	90 93 c1 03 	sts	0x03C1, r25
    4538:	80 93 c0 03 	sts	0x03C0, r24
    453c:	bf cf       	rjmp	.-130    	; 0x44bc <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0xd6>
    453e:	00 97       	sbiw	r24, 0x00	; 0
    4540:	09 f4       	brne	.+2      	; 0x4544 <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0x15e>
    4542:	bc cf       	rjmp	.-136    	; 0x44bc <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0xd6>
    4544:	e0 91 c4 03 	lds	r30, 0x03C4
    4548:	f0 91 c5 03 	lds	r31, 0x03C5
    454c:	04 80       	ldd	r0, Z+4	; 0x04
    454e:	f5 81       	ldd	r31, Z+5	; 0x05
    4550:	e0 2d       	mov	r30, r0
    4552:	f0 93 c5 03 	sts	0x03C5, r31
    4556:	e0 93 c4 03 	sts	0x03C4, r30
    455a:	17 82       	std	Z+7, r1	; 0x07
    455c:	16 82       	std	Z+6, r1	; 0x06
    455e:	01 97       	sbiw	r24, 0x01	; 1
    4560:	90 93 c1 03 	sts	0x03C1, r25
    4564:	80 93 c0 03 	sts	0x03C0, r24
    4568:	a9 cf       	rjmp	.-174    	; 0x44bc <_ZN6System6Thread18common_constructorENS_10CPU_Common8Log_AddrEj+0xd6>
    456a:	0e 94 c5 20 	call	0x418a	; 0x418a <_ZN6System6Thread10rescheduleEv>
    456e:	78 94       	sei
    4570:	df 91       	pop	r29
    4572:	cf 91       	pop	r28
    4574:	08 95       	ret

00004576 <_ZN6System6Thread6resumeEv>:
    4576:	fc 01       	movw	r30, r24
    4578:	f8 94       	cli
    457a:	84 81       	ldd	r24, Z+4	; 0x04
    457c:	95 81       	ldd	r25, Z+5	; 0x05
    457e:	03 97       	sbiw	r24, 0x03	; 3
    4580:	e1 f4       	brne	.+56     	; 0x45ba <_ZN6System6Thread6resumeEv+0x44>
    4582:	81 e0       	ldi	r24, 0x01	; 1
    4584:	90 e0       	ldi	r25, 0x00	; 0
    4586:	95 83       	std	Z+5, r25	; 0x05
    4588:	84 83       	std	Z+4, r24	; 0x04
    458a:	80 91 c6 03 	lds	r24, 0x03C6
    458e:	90 91 c7 03 	lds	r25, 0x03C7
    4592:	3a 96       	adiw	r30, 0x0a	; 10
    4594:	bf 01       	movw	r22, r30
    4596:	80 ec       	ldi	r24, 0xC0	; 192
    4598:	93 e0       	ldi	r25, 0x03	; 3
    459a:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    459e:	80 91 c6 03 	lds	r24, 0x03C6
    45a2:	90 91 c7 03 	lds	r25, 0x03C7
    45a6:	89 2b       	or	r24, r25
    45a8:	41 f4       	brne	.+16     	; 0x45ba <_ZN6System6Thread6resumeEv+0x44>
    45aa:	80 91 c2 03 	lds	r24, 0x03C2
    45ae:	90 91 c3 03 	lds	r25, 0x03C3
    45b2:	90 93 c7 03 	sts	0x03C7, r25
    45b6:	80 93 c6 03 	sts	0x03C6, r24
    45ba:	0e 94 c5 20 	call	0x418a	; 0x418a <_ZN6System6Thread10rescheduleEv>
    45be:	78 94       	sei
    45c0:	08 95       	ret

000045c2 <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>:
    45c2:	cf 93       	push	r28
    45c4:	df 93       	push	r29
    45c6:	dc 01       	movw	r26, r24
    45c8:	f8 94       	cli
    45ca:	80 91 c6 03 	lds	r24, 0x03C6
    45ce:	90 91 c7 03 	lds	r25, 0x03C7
    45d2:	8d 91       	ld	r24, X+
    45d4:	9c 91       	ld	r25, X
    45d6:	11 97       	sbiw	r26, 0x01	; 1
    45d8:	00 97       	sbiw	r24, 0x00	; 0
    45da:	d9 f1       	breq	.+118    	; 0x4652 <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x90>
    45dc:	81 30       	cpi	r24, 0x01	; 1
    45de:	91 05       	cpc	r25, r1
    45e0:	71 f1       	breq	.+92     	; 0x463e <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x7c>
    45e2:	fd 01       	movw	r30, r26
    45e4:	c2 81       	ldd	r28, Z+2	; 0x02
    45e6:	d3 81       	ldd	r29, Z+3	; 0x03
    45e8:	2e 81       	ldd	r18, Y+6	; 0x06
    45ea:	3f 81       	ldd	r19, Y+7	; 0x07
    45ec:	33 83       	std	Z+3, r19	; 0x03
    45ee:	22 83       	std	Z+2, r18	; 0x02
    45f0:	f9 01       	movw	r30, r18
    45f2:	15 82       	std	Z+5, r1	; 0x05
    45f4:	14 82       	std	Z+4, r1	; 0x04
    45f6:	01 97       	sbiw	r24, 0x01	; 1
    45f8:	8d 93       	st	X+, r24
    45fa:	9c 93       	st	X, r25
    45fc:	e8 81       	ld	r30, Y
    45fe:	f9 81       	ldd	r31, Y+1	; 0x01
    4600:	81 e0       	ldi	r24, 0x01	; 1
    4602:	90 e0       	ldi	r25, 0x00	; 0
    4604:	95 83       	std	Z+5, r25	; 0x05
    4606:	84 83       	std	Z+4, r24	; 0x04
    4608:	17 82       	std	Z+7, r1	; 0x07
    460a:	16 82       	std	Z+6, r1	; 0x06
    460c:	80 91 c6 03 	lds	r24, 0x03C6
    4610:	90 91 c7 03 	lds	r25, 0x03C7
    4614:	3a 96       	adiw	r30, 0x0a	; 10
    4616:	bf 01       	movw	r22, r30
    4618:	80 ec       	ldi	r24, 0xC0	; 192
    461a:	93 e0       	ldi	r25, 0x03	; 3
    461c:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    4620:	80 91 c6 03 	lds	r24, 0x03C6
    4624:	90 91 c7 03 	lds	r25, 0x03C7
    4628:	89 2b       	or	r24, r25
    462a:	99 f4       	brne	.+38     	; 0x4652 <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x90>
    462c:	80 91 c2 03 	lds	r24, 0x03C2
    4630:	90 91 c3 03 	lds	r25, 0x03C3
    4634:	90 93 c7 03 	sts	0x03C7, r25
    4638:	80 93 c6 03 	sts	0x03C6, r24
    463c:	0a c0       	rjmp	.+20     	; 0x4652 <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x90>
    463e:	fd 01       	movw	r30, r26
    4640:	c2 81       	ldd	r28, Z+2	; 0x02
    4642:	d3 81       	ldd	r29, Z+3	; 0x03
    4644:	13 82       	std	Z+3, r1	; 0x03
    4646:	12 82       	std	Z+2, r1	; 0x02
    4648:	15 82       	std	Z+5, r1	; 0x05
    464a:	14 82       	std	Z+4, r1	; 0x04
    464c:	1d 92       	st	X+, r1
    464e:	1c 92       	st	X, r1
    4650:	d5 cf       	rjmp	.-86     	; 0x45fc <_ZN6System6Thread6wakeupEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x3a>
    4652:	0e 94 c5 20 	call	0x418a	; 0x418a <_ZN6System6Thread10rescheduleEv>
    4656:	78 94       	sei
    4658:	df 91       	pop	r29
    465a:	cf 91       	pop	r28
    465c:	08 95       	ret

0000465e <_ZN6System6Thread4passEv>:
    465e:	ef 92       	push	r14
    4660:	ff 92       	push	r15
    4662:	0f 93       	push	r16
    4664:	1f 93       	push	r17
    4666:	cf 93       	push	r28
    4668:	df 93       	push	r29
    466a:	8c 01       	movw	r16, r24
    466c:	f8 94       	cli
    466e:	e0 91 c6 03 	lds	r30, 0x03C6
    4672:	f0 91 c7 03 	lds	r31, 0x03C7
    4676:	e0 80       	ld	r14, Z
    4678:	f1 80       	ldd	r15, Z+1	; 0x01
    467a:	80 91 c6 03 	lds	r24, 0x03C6
    467e:	90 91 c7 03 	lds	r25, 0x03C7
    4682:	20 91 c2 03 	lds	r18, 0x03C2
    4686:	30 91 c3 03 	lds	r19, 0x03C3
    468a:	21 15       	cp	r18, r1
    468c:	31 05       	cpc	r19, r1
    468e:	09 f4       	brne	.+2      	; 0x4692 <_ZN6System6Thread4passEv+0x34>
    4690:	b5 c0       	rjmp	.+362    	; 0x47fc <_ZN6System6Thread4passEv+0x19e>
    4692:	f9 01       	movw	r30, r18
    4694:	80 81       	ld	r24, Z
    4696:	91 81       	ldd	r25, Z+1	; 0x01
    4698:	08 17       	cp	r16, r24
    469a:	19 07       	cpc	r17, r25
    469c:	09 f4       	brne	.+2      	; 0x46a0 <_ZN6System6Thread4passEv+0x42>
    469e:	66 c0       	rjmp	.+204    	; 0x476c <_ZN6System6Thread4passEv+0x10e>
    46a0:	e9 01       	movw	r28, r18
    46a2:	0e 80       	ldd	r0, Y+6	; 0x06
    46a4:	df 81       	ldd	r29, Y+7	; 0x07
    46a6:	c0 2d       	mov	r28, r0
    46a8:	20 97       	sbiw	r28, 0x00	; 0
    46aa:	09 f4       	brne	.+2      	; 0x46ae <_ZN6System6Thread4passEv+0x50>
    46ac:	a7 c0       	rjmp	.+334    	; 0x47fc <_ZN6System6Thread4passEv+0x19e>
    46ae:	88 81       	ld	r24, Y
    46b0:	99 81       	ldd	r25, Y+1	; 0x01
    46b2:	08 17       	cp	r16, r24
    46b4:	19 07       	cpc	r17, r25
    46b6:	a9 f7       	brne	.-22     	; 0x46a2 <_ZN6System6Thread4passEv+0x44>
    46b8:	80 91 c6 03 	lds	r24, 0x03C6
    46bc:	90 91 c7 03 	lds	r25, 0x03C7
    46c0:	89 2b       	or	r24, r25
    46c2:	81 f1       	breq	.+96     	; 0x4724 <_ZN6System6Thread4passEv+0xc6>
    46c4:	80 91 c6 03 	lds	r24, 0x03C6
    46c8:	90 91 c7 03 	lds	r25, 0x03C7
    46cc:	40 91 c0 03 	lds	r20, 0x03C0
    46d0:	50 91 c1 03 	lds	r21, 0x03C1
    46d4:	41 30       	cpi	r20, 0x01	; 1
    46d6:	51 05       	cpc	r21, r1
    46d8:	09 f4       	brne	.+2      	; 0x46dc <_ZN6System6Thread4passEv+0x7e>
    46da:	51 c0       	rjmp	.+162    	; 0x477e <_ZN6System6Thread4passEv+0x120>
    46dc:	fc 01       	movw	r30, r24
    46de:	a4 81       	ldd	r26, Z+4	; 0x04
    46e0:	b5 81       	ldd	r27, Z+5	; 0x05
    46e2:	10 97       	sbiw	r26, 0x00	; 0
    46e4:	09 f4       	brne	.+2      	; 0x46e8 <_ZN6System6Thread4passEv+0x8a>
    46e6:	5d c0       	rjmp	.+186    	; 0x47a2 <_ZN6System6Thread4passEv+0x144>
    46e8:	fc 01       	movw	r30, r24
    46ea:	26 81       	ldd	r18, Z+6	; 0x06
    46ec:	37 81       	ldd	r19, Z+7	; 0x07
    46ee:	21 15       	cp	r18, r1
    46f0:	31 05       	cpc	r19, r1
    46f2:	09 f4       	brne	.+2      	; 0x46f6 <_ZN6System6Thread4passEv+0x98>
    46f4:	6b c0       	rjmp	.+214    	; 0x47cc <_ZN6System6Thread4passEv+0x16e>
    46f6:	fd 01       	movw	r30, r26
    46f8:	37 83       	std	Z+7, r19	; 0x07
    46fa:	26 83       	std	Z+6, r18	; 0x06
    46fc:	fc 01       	movw	r30, r24
    46fe:	86 81       	ldd	r24, Z+6	; 0x06
    4700:	97 81       	ldd	r25, Z+7	; 0x07
    4702:	fc 01       	movw	r30, r24
    4704:	b5 83       	std	Z+5, r27	; 0x05
    4706:	a4 83       	std	Z+4, r26	; 0x04
    4708:	41 50       	subi	r20, 0x01	; 1
    470a:	50 40       	sbci	r21, 0x00	; 0
    470c:	50 93 c1 03 	sts	0x03C1, r21
    4710:	40 93 c0 03 	sts	0x03C0, r20
    4714:	60 91 c6 03 	lds	r22, 0x03C6
    4718:	70 91 c7 03 	lds	r23, 0x03C7
    471c:	80 ec       	ldi	r24, 0xC0	; 192
    471e:	93 e0       	ldi	r25, 0x03	; 3
    4720:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    4724:	d0 93 c7 03 	sts	0x03C7, r29
    4728:	c0 93 c6 03 	sts	0x03C6, r28
    472c:	80 91 c6 03 	lds	r24, 0x03C6
    4730:	90 91 c7 03 	lds	r25, 0x03C7
    4734:	89 2b       	or	r24, r25
    4736:	09 f4       	brne	.+2      	; 0x473a <_ZN6System6Thread4passEv+0xdc>
    4738:	61 c0       	rjmp	.+194    	; 0x47fc <_ZN6System6Thread4passEv+0x19e>
    473a:	01 15       	cp	r16, r1
    473c:	11 05       	cpc	r17, r1
    473e:	09 f4       	brne	.+2      	; 0x4742 <_ZN6System6Thread4passEv+0xe4>
    4740:	5d c0       	rjmp	.+186    	; 0x47fc <_ZN6System6Thread4passEv+0x19e>
    4742:	0e 15       	cp	r16, r14
    4744:	1f 05       	cpc	r17, r15
    4746:	81 f0       	breq	.+32     	; 0x4768 <_ZN6System6Thread4passEv+0x10a>
    4748:	f7 01       	movw	r30, r14
    474a:	84 81       	ldd	r24, Z+4	; 0x04
    474c:	95 81       	ldd	r25, Z+5	; 0x05
    474e:	02 97       	sbiw	r24, 0x02	; 2
    4750:	19 f1       	breq	.+70     	; 0x4798 <_ZN6System6Thread4passEv+0x13a>
    4752:	82 e0       	ldi	r24, 0x02	; 2
    4754:	90 e0       	ldi	r25, 0x00	; 0
    4756:	f8 01       	movw	r30, r16
    4758:	95 83       	std	Z+5, r25	; 0x05
    475a:	84 83       	std	Z+4, r24	; 0x04
    475c:	62 81       	ldd	r22, Z+2	; 0x02
    475e:	73 81       	ldd	r23, Z+3	; 0x03
    4760:	c7 01       	movw	r24, r14
    4762:	02 96       	adiw	r24, 0x02	; 2
    4764:	0e 94 35 31 	call	0x626a	; 0x626a <_ZN6System4AVR814switch_contextEPVPNS0_7ContextES2_>
    4768:	78 94       	sei
    476a:	48 c0       	rjmp	.+144    	; 0x47fc <_ZN6System6Thread4passEv+0x19e>
    476c:	e9 01       	movw	r28, r18
    476e:	80 91 c6 03 	lds	r24, 0x03C6
    4772:	90 91 c7 03 	lds	r25, 0x03C7
    4776:	89 2b       	or	r24, r25
    4778:	09 f0       	breq	.+2      	; 0x477c <_ZN6System6Thread4passEv+0x11e>
    477a:	a4 cf       	rjmp	.-184    	; 0x46c4 <_ZN6System6Thread4passEv+0x66>
    477c:	d3 cf       	rjmp	.-90     	; 0x4724 <_ZN6System6Thread4passEv+0xc6>
    477e:	10 92 c3 03 	sts	0x03C3, r1
    4782:	10 92 c2 03 	sts	0x03C2, r1
    4786:	10 92 c5 03 	sts	0x03C5, r1
    478a:	10 92 c4 03 	sts	0x03C4, r1
    478e:	10 92 c1 03 	sts	0x03C1, r1
    4792:	10 92 c0 03 	sts	0x03C0, r1
    4796:	be cf       	rjmp	.-132    	; 0x4714 <_ZN6System6Thread4passEv+0xb6>
    4798:	81 e0       	ldi	r24, 0x01	; 1
    479a:	90 e0       	ldi	r25, 0x00	; 0
    479c:	95 83       	std	Z+5, r25	; 0x05
    479e:	84 83       	std	Z+4, r24	; 0x04
    47a0:	d8 cf       	rjmp	.-80     	; 0x4752 <_ZN6System6Thread4passEv+0xf4>
    47a2:	41 15       	cp	r20, r1
    47a4:	51 05       	cpc	r21, r1
    47a6:	09 f4       	brne	.+2      	; 0x47aa <_ZN6System6Thread4passEv+0x14c>
    47a8:	b5 cf       	rjmp	.-150    	; 0x4714 <_ZN6System6Thread4passEv+0xb6>
    47aa:	f9 01       	movw	r30, r18
    47ac:	a6 81       	ldd	r26, Z+6	; 0x06
    47ae:	b7 81       	ldd	r27, Z+7	; 0x07
    47b0:	b0 93 c3 03 	sts	0x03C3, r27
    47b4:	a0 93 c2 03 	sts	0x03C2, r26
    47b8:	fd 01       	movw	r30, r26
    47ba:	15 82       	std	Z+5, r1	; 0x05
    47bc:	14 82       	std	Z+4, r1	; 0x04
    47be:	41 50       	subi	r20, 0x01	; 1
    47c0:	50 40       	sbci	r21, 0x00	; 0
    47c2:	50 93 c1 03 	sts	0x03C1, r21
    47c6:	40 93 c0 03 	sts	0x03C0, r20
    47ca:	a4 cf       	rjmp	.-184    	; 0x4714 <_ZN6System6Thread4passEv+0xb6>
    47cc:	41 15       	cp	r20, r1
    47ce:	51 05       	cpc	r21, r1
    47d0:	09 f4       	brne	.+2      	; 0x47d4 <_ZN6System6Thread4passEv+0x176>
    47d2:	a0 cf       	rjmp	.-192    	; 0x4714 <_ZN6System6Thread4passEv+0xb6>
    47d4:	e0 91 c4 03 	lds	r30, 0x03C4
    47d8:	f0 91 c5 03 	lds	r31, 0x03C5
    47dc:	04 80       	ldd	r0, Z+4	; 0x04
    47de:	f5 81       	ldd	r31, Z+5	; 0x05
    47e0:	e0 2d       	mov	r30, r0
    47e2:	f0 93 c5 03 	sts	0x03C5, r31
    47e6:	e0 93 c4 03 	sts	0x03C4, r30
    47ea:	17 82       	std	Z+7, r1	; 0x07
    47ec:	16 82       	std	Z+6, r1	; 0x06
    47ee:	41 50       	subi	r20, 0x01	; 1
    47f0:	50 40       	sbci	r21, 0x00	; 0
    47f2:	50 93 c1 03 	sts	0x03C1, r21
    47f6:	40 93 c0 03 	sts	0x03C0, r20
    47fa:	8c cf       	rjmp	.-232    	; 0x4714 <_ZN6System6Thread4passEv+0xb6>
    47fc:	78 94       	sei
    47fe:	df 91       	pop	r29
    4800:	cf 91       	pop	r28
    4802:	1f 91       	pop	r17
    4804:	0f 91       	pop	r16
    4806:	ff 90       	pop	r15
    4808:	ef 90       	pop	r14
    480a:	08 95       	ret

0000480c <_ZN6System6Thread7suspendEv>:
    480c:	0f 93       	push	r16
    480e:	1f 93       	push	r17
    4810:	cf 93       	push	r28
    4812:	df 93       	push	r29
    4814:	ac 01       	movw	r20, r24
    4816:	f8 94       	cli
    4818:	e0 91 c6 03 	lds	r30, 0x03C6
    481c:	f0 91 c7 03 	lds	r31, 0x03C7
    4820:	00 81       	ld	r16, Z
    4822:	11 81       	ldd	r17, Z+1	; 0x01
    4824:	80 91 c6 03 	lds	r24, 0x03C6
    4828:	90 91 c7 03 	lds	r25, 0x03C7
    482c:	e0 91 c2 03 	lds	r30, 0x03C2
    4830:	f0 91 c3 03 	lds	r31, 0x03C3
    4834:	30 97       	sbiw	r30, 0x00	; 0
    4836:	e9 f1       	breq	.+122    	; 0x48b2 <_ZN6System6Thread7suspendEv+0xa6>
    4838:	80 81       	ld	r24, Z
    483a:	91 81       	ldd	r25, Z+1	; 0x01
    483c:	48 17       	cp	r20, r24
    483e:	59 07       	cpc	r21, r25
    4840:	09 f4       	brne	.+2      	; 0x4844 <_ZN6System6Thread7suspendEv+0x38>
    4842:	92 c0       	rjmp	.+292    	; 0x4968 <_ZN6System6Thread7suspendEv+0x15c>
    4844:	df 01       	movw	r26, r30
    4846:	ed 01       	movw	r28, r26
    4848:	ae 81       	ldd	r26, Y+6	; 0x06
    484a:	bf 81       	ldd	r27, Y+7	; 0x07
    484c:	10 97       	sbiw	r26, 0x00	; 0
    484e:	89 f1       	breq	.+98     	; 0x48b2 <_ZN6System6Thread7suspendEv+0xa6>
    4850:	8d 91       	ld	r24, X+
    4852:	9c 91       	ld	r25, X
    4854:	11 97       	sbiw	r26, 0x01	; 1
    4856:	48 17       	cp	r20, r24
    4858:	59 07       	cpc	r21, r25
    485a:	a9 f7       	brne	.-22     	; 0x4846 <_ZN6System6Thread7suspendEv+0x3a>
    485c:	80 91 c0 03 	lds	r24, 0x03C0
    4860:	90 91 c1 03 	lds	r25, 0x03C1
    4864:	81 30       	cpi	r24, 0x01	; 1
    4866:	91 05       	cpc	r25, r1
    4868:	09 f4       	brne	.+2      	; 0x486c <_ZN6System6Thread7suspendEv+0x60>
    486a:	60 c0       	rjmp	.+192    	; 0x492c <_ZN6System6Thread7suspendEv+0x120>
    486c:	ed 01       	movw	r28, r26
    486e:	6c 81       	ldd	r22, Y+4	; 0x04
    4870:	7d 81       	ldd	r23, Y+5	; 0x05
    4872:	61 15       	cp	r22, r1
    4874:	71 05       	cpc	r23, r1
    4876:	09 f4       	brne	.+2      	; 0x487a <_ZN6System6Thread7suspendEv+0x6e>
    4878:	81 c0       	rjmp	.+258    	; 0x497c <_ZN6System6Thread7suspendEv+0x170>
    487a:	fd 01       	movw	r30, r26
    487c:	26 81       	ldd	r18, Z+6	; 0x06
    487e:	37 81       	ldd	r19, Z+7	; 0x07
    4880:	21 15       	cp	r18, r1
    4882:	31 05       	cpc	r19, r1
    4884:	09 f4       	brne	.+2      	; 0x4888 <_ZN6System6Thread7suspendEv+0x7c>
    4886:	8c c0       	rjmp	.+280    	; 0x49a0 <_ZN6System6Thread7suspendEv+0x194>
    4888:	eb 01       	movw	r28, r22
    488a:	3f 83       	std	Y+7, r19	; 0x07
    488c:	2e 83       	std	Y+6, r18	; 0x06
    488e:	ed 01       	movw	r28, r26
    4890:	ee 81       	ldd	r30, Y+6	; 0x06
    4892:	ff 81       	ldd	r31, Y+7	; 0x07
    4894:	75 83       	std	Z+5, r23	; 0x05
    4896:	64 83       	std	Z+4, r22	; 0x04
    4898:	01 97       	sbiw	r24, 0x01	; 1
    489a:	90 93 c1 03 	sts	0x03C1, r25
    489e:	80 93 c0 03 	sts	0x03C0, r24
    48a2:	80 91 c6 03 	lds	r24, 0x03C6
    48a6:	90 91 c7 03 	lds	r25, 0x03C7
    48aa:	8a 17       	cp	r24, r26
    48ac:	9b 07       	cpc	r25, r27
    48ae:	09 f4       	brne	.+2      	; 0x48b2 <_ZN6System6Thread7suspendEv+0xa6>
    48b0:	52 c0       	rjmp	.+164    	; 0x4956 <_ZN6System6Thread7suspendEv+0x14a>
    48b2:	83 e0       	ldi	r24, 0x03	; 3
    48b4:	90 e0       	ldi	r25, 0x00	; 0
    48b6:	fa 01       	movw	r30, r20
    48b8:	95 83       	std	Z+5, r25	; 0x05
    48ba:	84 83       	std	Z+4, r24	; 0x04
    48bc:	e0 91 c6 03 	lds	r30, 0x03C6
    48c0:	f0 91 c7 03 	lds	r31, 0x03C7
    48c4:	01 90       	ld	r0, Z+
    48c6:	f0 81       	ld	r31, Z
    48c8:	e0 2d       	mov	r30, r0
    48ca:	80 91 b5 03 	lds	r24, 0x03B5
    48ce:	90 91 b6 03 	lds	r25, 0x03B6
    48d2:	a0 91 b7 03 	lds	r26, 0x03B7
    48d6:	b0 91 b8 03 	lds	r27, 0x03B8
    48da:	80 91 b1 03 	lds	r24, 0x03B1
    48de:	90 91 b2 03 	lds	r25, 0x03B2
    48e2:	a0 91 b3 03 	lds	r26, 0x03B3
    48e6:	b0 91 b4 03 	lds	r27, 0x03B4
    48ea:	80 93 b5 03 	sts	0x03B5, r24
    48ee:	90 93 b6 03 	sts	0x03B6, r25
    48f2:	a0 93 b7 03 	sts	0x03B7, r26
    48f6:	b0 93 b8 03 	sts	0x03B8, r27
    48fa:	0e 17       	cp	r16, r30
    48fc:	1f 07       	cpc	r17, r31
    48fe:	09 f4       	brne	.+2      	; 0x4902 <_ZN6System6Thread7suspendEv+0xf6>
    4900:	65 c0       	rjmp	.+202    	; 0x49cc <_ZN6System6Thread7suspendEv+0x1c0>
    4902:	e8 01       	movw	r28, r16
    4904:	8c 81       	ldd	r24, Y+4	; 0x04
    4906:	9d 81       	ldd	r25, Y+5	; 0x05
    4908:	02 97       	sbiw	r24, 0x02	; 2
    490a:	59 f0       	breq	.+22     	; 0x4922 <_ZN6System6Thread7suspendEv+0x116>
    490c:	82 e0       	ldi	r24, 0x02	; 2
    490e:	90 e0       	ldi	r25, 0x00	; 0
    4910:	95 83       	std	Z+5, r25	; 0x05
    4912:	84 83       	std	Z+4, r24	; 0x04
    4914:	62 81       	ldd	r22, Z+2	; 0x02
    4916:	73 81       	ldd	r23, Z+3	; 0x03
    4918:	c8 01       	movw	r24, r16
    491a:	02 96       	adiw	r24, 0x02	; 2
    491c:	0e 94 35 31 	call	0x626a	; 0x626a <_ZN6System4AVR814switch_contextEPVPNS0_7ContextES2_>
    4920:	55 c0       	rjmp	.+170    	; 0x49cc <_ZN6System6Thread7suspendEv+0x1c0>
    4922:	81 e0       	ldi	r24, 0x01	; 1
    4924:	90 e0       	ldi	r25, 0x00	; 0
    4926:	9d 83       	std	Y+5, r25	; 0x05
    4928:	8c 83       	std	Y+4, r24	; 0x04
    492a:	f0 cf       	rjmp	.-32     	; 0x490c <_ZN6System6Thread7suspendEv+0x100>
    492c:	10 92 c3 03 	sts	0x03C3, r1
    4930:	10 92 c2 03 	sts	0x03C2, r1
    4934:	10 92 c5 03 	sts	0x03C5, r1
    4938:	10 92 c4 03 	sts	0x03C4, r1
    493c:	01 97       	sbiw	r24, 0x01	; 1
    493e:	90 93 c1 03 	sts	0x03C1, r25
    4942:	80 93 c0 03 	sts	0x03C0, r24
    4946:	80 91 c6 03 	lds	r24, 0x03C6
    494a:	90 91 c7 03 	lds	r25, 0x03C7
    494e:	8a 17       	cp	r24, r26
    4950:	9b 07       	cpc	r25, r27
    4952:	09 f0       	breq	.+2      	; 0x4956 <_ZN6System6Thread7suspendEv+0x14a>
    4954:	ae cf       	rjmp	.-164    	; 0x48b2 <_ZN6System6Thread7suspendEv+0xa6>
    4956:	80 91 c2 03 	lds	r24, 0x03C2
    495a:	90 91 c3 03 	lds	r25, 0x03C3
    495e:	90 93 c7 03 	sts	0x03C7, r25
    4962:	80 93 c6 03 	sts	0x03C6, r24
    4966:	a5 cf       	rjmp	.-182    	; 0x48b2 <_ZN6System6Thread7suspendEv+0xa6>
    4968:	df 01       	movw	r26, r30
    496a:	80 91 c0 03 	lds	r24, 0x03C0
    496e:	90 91 c1 03 	lds	r25, 0x03C1
    4972:	81 30       	cpi	r24, 0x01	; 1
    4974:	91 05       	cpc	r25, r1
    4976:	09 f0       	breq	.+2      	; 0x497a <_ZN6System6Thread7suspendEv+0x16e>
    4978:	79 cf       	rjmp	.-270    	; 0x486c <_ZN6System6Thread7suspendEv+0x60>
    497a:	d8 cf       	rjmp	.-80     	; 0x492c <_ZN6System6Thread7suspendEv+0x120>
    497c:	00 97       	sbiw	r24, 0x00	; 0
    497e:	09 f4       	brne	.+2      	; 0x4982 <_ZN6System6Thread7suspendEv+0x176>
    4980:	90 cf       	rjmp	.-224    	; 0x48a2 <_ZN6System6Thread7suspendEv+0x96>
    4982:	06 80       	ldd	r0, Z+6	; 0x06
    4984:	f7 81       	ldd	r31, Z+7	; 0x07
    4986:	e0 2d       	mov	r30, r0
    4988:	f0 93 c3 03 	sts	0x03C3, r31
    498c:	e0 93 c2 03 	sts	0x03C2, r30
    4990:	15 82       	std	Z+5, r1	; 0x05
    4992:	14 82       	std	Z+4, r1	; 0x04
    4994:	01 97       	sbiw	r24, 0x01	; 1
    4996:	90 93 c1 03 	sts	0x03C1, r25
    499a:	80 93 c0 03 	sts	0x03C0, r24
    499e:	81 cf       	rjmp	.-254    	; 0x48a2 <_ZN6System6Thread7suspendEv+0x96>
    49a0:	00 97       	sbiw	r24, 0x00	; 0
    49a2:	09 f4       	brne	.+2      	; 0x49a6 <_ZN6System6Thread7suspendEv+0x19a>
    49a4:	7e cf       	rjmp	.-260    	; 0x48a2 <_ZN6System6Thread7suspendEv+0x96>
    49a6:	e0 91 c4 03 	lds	r30, 0x03C4
    49aa:	f0 91 c5 03 	lds	r31, 0x03C5
    49ae:	04 80       	ldd	r0, Z+4	; 0x04
    49b0:	f5 81       	ldd	r31, Z+5	; 0x05
    49b2:	e0 2d       	mov	r30, r0
    49b4:	f0 93 c5 03 	sts	0x03C5, r31
    49b8:	e0 93 c4 03 	sts	0x03C4, r30
    49bc:	17 82       	std	Z+7, r1	; 0x07
    49be:	16 82       	std	Z+6, r1	; 0x06
    49c0:	01 97       	sbiw	r24, 0x01	; 1
    49c2:	90 93 c1 03 	sts	0x03C1, r25
    49c6:	80 93 c0 03 	sts	0x03C0, r24
    49ca:	6b cf       	rjmp	.-298    	; 0x48a2 <_ZN6System6Thread7suspendEv+0x96>
    49cc:	78 94       	sei
    49ce:	78 94       	sei
    49d0:	df 91       	pop	r29
    49d2:	cf 91       	pop	r28
    49d4:	1f 91       	pop	r17
    49d6:	0f 91       	pop	r16
    49d8:	08 95       	ret

000049da <_ZN6System6Thread4joinEv>:
    49da:	cf 93       	push	r28
    49dc:	df 93       	push	r29
    49de:	ec 01       	movw	r28, r24
    49e0:	f8 94       	cli
    49e2:	8c 81       	ldd	r24, Y+4	; 0x04
    49e4:	9d 81       	ldd	r25, Y+5	; 0x05
    49e6:	05 97       	sbiw	r24, 0x05	; 5
    49e8:	61 f0       	breq	.+24     	; 0x4a02 <_ZN6System6Thread4joinEv+0x28>
    49ea:	e0 91 c6 03 	lds	r30, 0x03C6
    49ee:	f0 91 c7 03 	lds	r31, 0x03C7
    49f2:	80 81       	ld	r24, Z
    49f4:	91 81       	ldd	r25, Z+1	; 0x01
    49f6:	99 87       	std	Y+9, r25	; 0x09
    49f8:	88 87       	std	Y+8, r24	; 0x08
    49fa:	88 85       	ldd	r24, Y+8	; 0x08
    49fc:	99 85       	ldd	r25, Y+9	; 0x09
    49fe:	0e 94 06 24 	call	0x480c	; 0x480c <_ZN6System6Thread7suspendEv>
    4a02:	78 94       	sei
    4a04:	e8 81       	ld	r30, Y
    4a06:	f9 81       	ldd	r31, Y+1	; 0x01
    4a08:	80 81       	ld	r24, Z
    4a0a:	91 81       	ldd	r25, Z+1	; 0x01
    4a0c:	df 91       	pop	r29
    4a0e:	cf 91       	pop	r28
    4a10:	08 95       	ret

00004a12 <_ZN6System6Thread5yieldEv>:
    4a12:	0f 93       	push	r16
    4a14:	1f 93       	push	r17
    4a16:	cf 93       	push	r28
    4a18:	df 93       	push	r29
    4a1a:	f8 94       	cli
    4a1c:	80 91 c6 03 	lds	r24, 0x03C6
    4a20:	90 91 c7 03 	lds	r25, 0x03C7
    4a24:	e0 91 c6 03 	lds	r30, 0x03C6
    4a28:	f0 91 c7 03 	lds	r31, 0x03C7
    4a2c:	00 81       	ld	r16, Z
    4a2e:	11 81       	ldd	r17, Z+1	; 0x01
    4a30:	80 91 c6 03 	lds	r24, 0x03C6
    4a34:	90 91 c7 03 	lds	r25, 0x03C7
    4a38:	20 91 c0 03 	lds	r18, 0x03C0
    4a3c:	30 91 c1 03 	lds	r19, 0x03C1
    4a40:	22 30       	cpi	r18, 0x02	; 2
    4a42:	31 05       	cpc	r19, r1
    4a44:	40 f1       	brcs	.+80     	; 0x4a96 <_ZN6System6Thread5yieldEv+0x84>
    4a46:	c0 91 c6 03 	lds	r28, 0x03C6
    4a4a:	d0 91 c7 03 	lds	r29, 0x03C7
    4a4e:	ac 81       	ldd	r26, Y+4	; 0x04
    4a50:	bd 81       	ldd	r27, Y+5	; 0x05
    4a52:	10 97       	sbiw	r26, 0x00	; 0
    4a54:	09 f4       	brne	.+2      	; 0x4a58 <_ZN6System6Thread5yieldEv+0x46>
    4a56:	56 c0       	rjmp	.+172    	; 0x4b04 <_ZN6System6Thread5yieldEv+0xf2>
    4a58:	8e 81       	ldd	r24, Y+6	; 0x06
    4a5a:	9f 81       	ldd	r25, Y+7	; 0x07
    4a5c:	00 97       	sbiw	r24, 0x00	; 0
    4a5e:	09 f4       	brne	.+2      	; 0x4a62 <_ZN6System6Thread5yieldEv+0x50>
    4a60:	65 c0       	rjmp	.+202    	; 0x4b2c <_ZN6System6Thread5yieldEv+0x11a>
    4a62:	fd 01       	movw	r30, r26
    4a64:	97 83       	std	Z+7, r25	; 0x07
    4a66:	86 83       	std	Z+6, r24	; 0x06
    4a68:	ee 81       	ldd	r30, Y+6	; 0x06
    4a6a:	ff 81       	ldd	r31, Y+7	; 0x07
    4a6c:	b5 83       	std	Z+5, r27	; 0x05
    4a6e:	a4 83       	std	Z+4, r26	; 0x04
    4a70:	21 50       	subi	r18, 0x01	; 1
    4a72:	30 40       	sbci	r19, 0x00	; 0
    4a74:	30 93 c1 03 	sts	0x03C1, r19
    4a78:	20 93 c0 03 	sts	0x03C0, r18
    4a7c:	80 91 c2 03 	lds	r24, 0x03C2
    4a80:	90 91 c3 03 	lds	r25, 0x03C3
    4a84:	90 93 c7 03 	sts	0x03C7, r25
    4a88:	80 93 c6 03 	sts	0x03C6, r24
    4a8c:	be 01       	movw	r22, r28
    4a8e:	80 ec       	ldi	r24, 0xC0	; 192
    4a90:	93 e0       	ldi	r25, 0x03	; 3
    4a92:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    4a96:	e0 91 c6 03 	lds	r30, 0x03C6
    4a9a:	f0 91 c7 03 	lds	r31, 0x03C7
    4a9e:	01 90       	ld	r0, Z+
    4aa0:	f0 81       	ld	r31, Z
    4aa2:	e0 2d       	mov	r30, r0
    4aa4:	80 91 b5 03 	lds	r24, 0x03B5
    4aa8:	90 91 b6 03 	lds	r25, 0x03B6
    4aac:	a0 91 b7 03 	lds	r26, 0x03B7
    4ab0:	b0 91 b8 03 	lds	r27, 0x03B8
    4ab4:	80 91 b1 03 	lds	r24, 0x03B1
    4ab8:	90 91 b2 03 	lds	r25, 0x03B2
    4abc:	a0 91 b3 03 	lds	r26, 0x03B3
    4ac0:	b0 91 b4 03 	lds	r27, 0x03B4
    4ac4:	80 93 b5 03 	sts	0x03B5, r24
    4ac8:	90 93 b6 03 	sts	0x03B6, r25
    4acc:	a0 93 b7 03 	sts	0x03B7, r26
    4ad0:	b0 93 b8 03 	sts	0x03B8, r27
    4ad4:	0e 17       	cp	r16, r30
    4ad6:	1f 07       	cpc	r17, r31
    4ad8:	e9 f1       	breq	.+122    	; 0x4b54 <_ZN6System6Thread5yieldEv+0x142>
    4ada:	e8 01       	movw	r28, r16
    4adc:	8c 81       	ldd	r24, Y+4	; 0x04
    4ade:	9d 81       	ldd	r25, Y+5	; 0x05
    4ae0:	02 97       	sbiw	r24, 0x02	; 2
    4ae2:	59 f0       	breq	.+22     	; 0x4afa <_ZN6System6Thread5yieldEv+0xe8>
    4ae4:	82 e0       	ldi	r24, 0x02	; 2
    4ae6:	90 e0       	ldi	r25, 0x00	; 0
    4ae8:	95 83       	std	Z+5, r25	; 0x05
    4aea:	84 83       	std	Z+4, r24	; 0x04
    4aec:	62 81       	ldd	r22, Z+2	; 0x02
    4aee:	73 81       	ldd	r23, Z+3	; 0x03
    4af0:	c8 01       	movw	r24, r16
    4af2:	02 96       	adiw	r24, 0x02	; 2
    4af4:	0e 94 35 31 	call	0x626a	; 0x626a <_ZN6System4AVR814switch_contextEPVPNS0_7ContextES2_>
    4af8:	2d c0       	rjmp	.+90     	; 0x4b54 <_ZN6System6Thread5yieldEv+0x142>
    4afa:	81 e0       	ldi	r24, 0x01	; 1
    4afc:	90 e0       	ldi	r25, 0x00	; 0
    4afe:	9d 83       	std	Y+5, r25	; 0x05
    4b00:	8c 83       	std	Y+4, r24	; 0x04
    4b02:	f0 cf       	rjmp	.-32     	; 0x4ae4 <_ZN6System6Thread5yieldEv+0xd2>
    4b04:	e0 91 c2 03 	lds	r30, 0x03C2
    4b08:	f0 91 c3 03 	lds	r31, 0x03C3
    4b0c:	06 80       	ldd	r0, Z+6	; 0x06
    4b0e:	f7 81       	ldd	r31, Z+7	; 0x07
    4b10:	e0 2d       	mov	r30, r0
    4b12:	f0 93 c3 03 	sts	0x03C3, r31
    4b16:	e0 93 c2 03 	sts	0x03C2, r30
    4b1a:	15 82       	std	Z+5, r1	; 0x05
    4b1c:	14 82       	std	Z+4, r1	; 0x04
    4b1e:	21 50       	subi	r18, 0x01	; 1
    4b20:	30 40       	sbci	r19, 0x00	; 0
    4b22:	30 93 c1 03 	sts	0x03C1, r19
    4b26:	20 93 c0 03 	sts	0x03C0, r18
    4b2a:	a8 cf       	rjmp	.-176    	; 0x4a7c <_ZN6System6Thread5yieldEv+0x6a>
    4b2c:	e0 91 c4 03 	lds	r30, 0x03C4
    4b30:	f0 91 c5 03 	lds	r31, 0x03C5
    4b34:	04 80       	ldd	r0, Z+4	; 0x04
    4b36:	f5 81       	ldd	r31, Z+5	; 0x05
    4b38:	e0 2d       	mov	r30, r0
    4b3a:	f0 93 c5 03 	sts	0x03C5, r31
    4b3e:	e0 93 c4 03 	sts	0x03C4, r30
    4b42:	17 82       	std	Z+7, r1	; 0x07
    4b44:	16 82       	std	Z+6, r1	; 0x06
    4b46:	21 50       	subi	r18, 0x01	; 1
    4b48:	30 40       	sbci	r19, 0x00	; 0
    4b4a:	30 93 c1 03 	sts	0x03C1, r19
    4b4e:	20 93 c0 03 	sts	0x03C0, r18
    4b52:	94 cf       	rjmp	.-216    	; 0x4a7c <_ZN6System6Thread5yieldEv+0x6a>
    4b54:	78 94       	sei
    4b56:	78 94       	sei
    4b58:	df 91       	pop	r29
    4b5a:	cf 91       	pop	r28
    4b5c:	1f 91       	pop	r17
    4b5e:	0f 91       	pop	r16
    4b60:	08 95       	ret

00004b62 <_ZN6System6Thread4idleEv>:
    4b62:	40 91 c8 03 	lds	r20, 0x03C8
    4b66:	50 91 c9 03 	lds	r21, 0x03C9
    4b6a:	20 91 c0 03 	lds	r18, 0x03C0
    4b6e:	30 91 c1 03 	lds	r19, 0x03C1
    4b72:	42 30       	cpi	r20, 0x02	; 2
    4b74:	51 05       	cpc	r21, r1
    4b76:	d0 f0       	brcs	.+52     	; 0x4bac <_ZN6System6Thread4idleEv+0x4a>
    4b78:	83 e0       	ldi	r24, 0x03	; 3
    4b7a:	80 93 8b 04 	sts	0x048B, r24
    4b7e:	85 b7       	in	r24, 0x35	; 53
    4b80:	83 7f       	andi	r24, 0xF3	; 243
    4b82:	85 bf       	out	0x35, r24	; 53
    4b84:	85 b7       	in	r24, 0x35	; 53
    4b86:	80 61       	ori	r24, 0x10	; 16
    4b88:	85 bf       	out	0x35, r24	; 53
    4b8a:	88 95       	sleep
    4b8c:	22 30       	cpi	r18, 0x02	; 2
    4b8e:	31 05       	cpc	r19, r1
    4b90:	80 f3       	brcs	.-32     	; 0x4b72 <_ZN6System6Thread4idleEv+0x10>
    4b92:	0e 94 09 25 	call	0x4a12	; 0x4a12 <_ZN6System6Thread5yieldEv>
    4b96:	40 91 c8 03 	lds	r20, 0x03C8
    4b9a:	50 91 c9 03 	lds	r21, 0x03C9
    4b9e:	20 91 c0 03 	lds	r18, 0x03C0
    4ba2:	30 91 c1 03 	lds	r19, 0x03C1
    4ba6:	42 30       	cpi	r20, 0x02	; 2
    4ba8:	51 05       	cpc	r21, r1
    4baa:	30 f7       	brcc	.-52     	; 0x4b78 <_ZN6System6Thread4idleEv+0x16>
    4bac:	f8 94       	cli
    4bae:	e4 cf       	rjmp	.-56     	; 0x4b78 <_ZN6System6Thread4idleEv+0x16>

00004bb0 <_ZN6System6Thread4exitEi>:
    4bb0:	0f 93       	push	r16
    4bb2:	1f 93       	push	r17
    4bb4:	cf 93       	push	r28
    4bb6:	df 93       	push	r29
    4bb8:	ac 01       	movw	r20, r24
    4bba:	f8 94       	cli
    4bbc:	80 91 c6 03 	lds	r24, 0x03C6
    4bc0:	90 91 c7 03 	lds	r25, 0x03C7
    4bc4:	e0 91 c6 03 	lds	r30, 0x03C6
    4bc8:	f0 91 c7 03 	lds	r31, 0x03C7
    4bcc:	00 81       	ld	r16, Z
    4bce:	11 81       	ldd	r17, Z+1	; 0x01
    4bd0:	80 91 c6 03 	lds	r24, 0x03C6
    4bd4:	90 91 c7 03 	lds	r25, 0x03C7
    4bd8:	e0 91 c2 03 	lds	r30, 0x03C2
    4bdc:	f0 91 c3 03 	lds	r31, 0x03C3
    4be0:	30 97       	sbiw	r30, 0x00	; 0
    4be2:	e9 f1       	breq	.+122    	; 0x4c5e <_ZN6System6Thread4exitEi+0xae>
    4be4:	80 81       	ld	r24, Z
    4be6:	91 81       	ldd	r25, Z+1	; 0x01
    4be8:	08 17       	cp	r16, r24
    4bea:	19 07       	cpc	r17, r25
    4bec:	09 f4       	brne	.+2      	; 0x4bf0 <_ZN6System6Thread4exitEi+0x40>
    4bee:	07 c1       	rjmp	.+526    	; 0x4dfe <_ZN6System6Thread4exitEi+0x24e>
    4bf0:	df 01       	movw	r26, r30
    4bf2:	ed 01       	movw	r28, r26
    4bf4:	ae 81       	ldd	r26, Y+6	; 0x06
    4bf6:	bf 81       	ldd	r27, Y+7	; 0x07
    4bf8:	10 97       	sbiw	r26, 0x00	; 0
    4bfa:	89 f1       	breq	.+98     	; 0x4c5e <_ZN6System6Thread4exitEi+0xae>
    4bfc:	8d 91       	ld	r24, X+
    4bfe:	9c 91       	ld	r25, X
    4c00:	11 97       	sbiw	r26, 0x01	; 1
    4c02:	08 17       	cp	r16, r24
    4c04:	19 07       	cpc	r17, r25
    4c06:	a9 f7       	brne	.-22     	; 0x4bf2 <_ZN6System6Thread4exitEi+0x42>
    4c08:	80 91 c0 03 	lds	r24, 0x03C0
    4c0c:	90 91 c1 03 	lds	r25, 0x03C1
    4c10:	81 30       	cpi	r24, 0x01	; 1
    4c12:	91 05       	cpc	r25, r1
    4c14:	09 f4       	brne	.+2      	; 0x4c18 <_ZN6System6Thread4exitEi+0x68>
    4c16:	c8 c0       	rjmp	.+400    	; 0x4da8 <_ZN6System6Thread4exitEi+0x1f8>
    4c18:	ed 01       	movw	r28, r26
    4c1a:	6c 81       	ldd	r22, Y+4	; 0x04
    4c1c:	7d 81       	ldd	r23, Y+5	; 0x05
    4c1e:	61 15       	cp	r22, r1
    4c20:	71 05       	cpc	r23, r1
    4c22:	09 f4       	brne	.+2      	; 0x4c26 <_ZN6System6Thread4exitEi+0x76>
    4c24:	ff c0       	rjmp	.+510    	; 0x4e24 <_ZN6System6Thread4exitEi+0x274>
    4c26:	fd 01       	movw	r30, r26
    4c28:	26 81       	ldd	r18, Z+6	; 0x06
    4c2a:	37 81       	ldd	r19, Z+7	; 0x07
    4c2c:	21 15       	cp	r18, r1
    4c2e:	31 05       	cpc	r19, r1
    4c30:	09 f4       	brne	.+2      	; 0x4c34 <_ZN6System6Thread4exitEi+0x84>
    4c32:	1d c1       	rjmp	.+570    	; 0x4e6e <_ZN6System6Thread4exitEi+0x2be>
    4c34:	eb 01       	movw	r28, r22
    4c36:	3f 83       	std	Y+7, r19	; 0x07
    4c38:	2e 83       	std	Y+6, r18	; 0x06
    4c3a:	ed 01       	movw	r28, r26
    4c3c:	ee 81       	ldd	r30, Y+6	; 0x06
    4c3e:	ff 81       	ldd	r31, Y+7	; 0x07
    4c40:	75 83       	std	Z+5, r23	; 0x05
    4c42:	64 83       	std	Z+4, r22	; 0x04
    4c44:	01 97       	sbiw	r24, 0x01	; 1
    4c46:	90 93 c1 03 	sts	0x03C1, r25
    4c4a:	80 93 c0 03 	sts	0x03C0, r24
    4c4e:	80 91 c6 03 	lds	r24, 0x03C6
    4c52:	90 91 c7 03 	lds	r25, 0x03C7
    4c56:	a8 17       	cp	r26, r24
    4c58:	b9 07       	cpc	r27, r25
    4c5a:	09 f4       	brne	.+2      	; 0x4c5e <_ZN6System6Thread4exitEi+0xae>
    4c5c:	ba c0       	rjmp	.+372    	; 0x4dd2 <_ZN6System6Thread4exitEi+0x222>
    4c5e:	d8 01       	movw	r26, r16
    4c60:	ed 91       	ld	r30, X+
    4c62:	fc 91       	ld	r31, X
    4c64:	51 83       	std	Z+1, r21	; 0x01
    4c66:	40 83       	st	Z, r20
    4c68:	85 e0       	ldi	r24, 0x05	; 5
    4c6a:	90 e0       	ldi	r25, 0x00	; 0
    4c6c:	e8 01       	movw	r28, r16
    4c6e:	9d 83       	std	Y+5, r25	; 0x05
    4c70:	8c 83       	std	Y+4, r24	; 0x04
    4c72:	80 91 c8 03 	lds	r24, 0x03C8
    4c76:	90 91 c9 03 	lds	r25, 0x03C9
    4c7a:	01 97       	sbiw	r24, 0x01	; 1
    4c7c:	90 93 c9 03 	sts	0x03C9, r25
    4c80:	80 93 c8 03 	sts	0x03C8, r24
    4c84:	88 85       	ldd	r24, Y+8	; 0x08
    4c86:	99 85       	ldd	r25, Y+9	; 0x09
    4c88:	89 2b       	or	r24, r25
    4c8a:	e1 f0       	breq	.+56     	; 0x4cc4 <_ZN6System6Thread4exitEi+0x114>
    4c8c:	e8 85       	ldd	r30, Y+8	; 0x08
    4c8e:	f9 85       	ldd	r31, Y+9	; 0x09
    4c90:	81 e0       	ldi	r24, 0x01	; 1
    4c92:	90 e0       	ldi	r25, 0x00	; 0
    4c94:	95 83       	std	Z+5, r25	; 0x05
    4c96:	84 83       	std	Z+4, r24	; 0x04
    4c98:	68 85       	ldd	r22, Y+8	; 0x08
    4c9a:	79 85       	ldd	r23, Y+9	; 0x09
    4c9c:	80 91 c6 03 	lds	r24, 0x03C6
    4ca0:	90 91 c7 03 	lds	r25, 0x03C7
    4ca4:	66 5f       	subi	r22, 0xF6	; 246
    4ca6:	7f 4f       	sbci	r23, 0xFF	; 255
    4ca8:	80 ec       	ldi	r24, 0xC0	; 192
    4caa:	93 e0       	ldi	r25, 0x03	; 3
    4cac:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    4cb0:	80 91 c6 03 	lds	r24, 0x03C6
    4cb4:	90 91 c7 03 	lds	r25, 0x03C7
    4cb8:	89 2b       	or	r24, r25
    4cba:	09 f4       	brne	.+2      	; 0x4cbe <_ZN6System6Thread4exitEi+0x10e>
    4cbc:	aa c0       	rjmp	.+340    	; 0x4e12 <_ZN6System6Thread4exitEi+0x262>
    4cbe:	f8 01       	movw	r30, r16
    4cc0:	11 86       	std	Z+9, r1	; 0x09
    4cc2:	10 86       	std	Z+8, r1	; 0x08
    4cc4:	80 91 b5 03 	lds	r24, 0x03B5
    4cc8:	90 91 b6 03 	lds	r25, 0x03B6
    4ccc:	a0 91 b7 03 	lds	r26, 0x03B7
    4cd0:	b0 91 b8 03 	lds	r27, 0x03B8
    4cd4:	80 91 b1 03 	lds	r24, 0x03B1
    4cd8:	90 91 b2 03 	lds	r25, 0x03B2
    4cdc:	a0 91 b3 03 	lds	r26, 0x03B3
    4ce0:	b0 91 b4 03 	lds	r27, 0x03B4
    4ce4:	80 93 b5 03 	sts	0x03B5, r24
    4ce8:	90 93 b6 03 	sts	0x03B6, r25
    4cec:	a0 93 b7 03 	sts	0x03B7, r26
    4cf0:	b0 93 b8 03 	sts	0x03B8, r27
    4cf4:	80 91 c6 03 	lds	r24, 0x03C6
    4cf8:	90 91 c7 03 	lds	r25, 0x03C7
    4cfc:	80 91 c0 03 	lds	r24, 0x03C0
    4d00:	90 91 c1 03 	lds	r25, 0x03C1
    4d04:	00 97       	sbiw	r24, 0x00	; 0
    4d06:	81 f1       	breq	.+96     	; 0x4d68 <_ZN6System6Thread4exitEi+0x1b8>
    4d08:	e0 91 c6 03 	lds	r30, 0x03C6
    4d0c:	f0 91 c7 03 	lds	r31, 0x03C7
    4d10:	81 30       	cpi	r24, 0x01	; 1
    4d12:	91 05       	cpc	r25, r1
    4d14:	09 f4       	brne	.+2      	; 0x4d18 <_ZN6System6Thread4exitEi+0x168>
    4d16:	66 c0       	rjmp	.+204    	; 0x4de4 <_ZN6System6Thread4exitEi+0x234>
    4d18:	a4 81       	ldd	r26, Z+4	; 0x04
    4d1a:	b5 81       	ldd	r27, Z+5	; 0x05
    4d1c:	10 97       	sbiw	r26, 0x00	; 0
    4d1e:	09 f4       	brne	.+2      	; 0x4d22 <_ZN6System6Thread4exitEi+0x172>
    4d20:	93 c0       	rjmp	.+294    	; 0x4e48 <_ZN6System6Thread4exitEi+0x298>
    4d22:	26 81       	ldd	r18, Z+6	; 0x06
    4d24:	37 81       	ldd	r19, Z+7	; 0x07
    4d26:	21 15       	cp	r18, r1
    4d28:	31 05       	cpc	r19, r1
    4d2a:	09 f4       	brne	.+2      	; 0x4d2e <_ZN6System6Thread4exitEi+0x17e>
    4d2c:	b6 c0       	rjmp	.+364    	; 0x4e9a <_ZN6System6Thread4exitEi+0x2ea>
    4d2e:	ed 01       	movw	r28, r26
    4d30:	3f 83       	std	Y+7, r19	; 0x07
    4d32:	2e 83       	std	Y+6, r18	; 0x06
    4d34:	06 80       	ldd	r0, Z+6	; 0x06
    4d36:	f7 81       	ldd	r31, Z+7	; 0x07
    4d38:	e0 2d       	mov	r30, r0
    4d3a:	b5 83       	std	Z+5, r27	; 0x05
    4d3c:	a4 83       	std	Z+4, r26	; 0x04
    4d3e:	01 97       	sbiw	r24, 0x01	; 1
    4d40:	90 93 c1 03 	sts	0x03C1, r25
    4d44:	80 93 c0 03 	sts	0x03C0, r24
    4d48:	60 91 c6 03 	lds	r22, 0x03C6
    4d4c:	70 91 c7 03 	lds	r23, 0x03C7
    4d50:	80 ec       	ldi	r24, 0xC0	; 192
    4d52:	93 e0       	ldi	r25, 0x03	; 3
    4d54:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    4d58:	80 91 c2 03 	lds	r24, 0x03C2
    4d5c:	90 91 c3 03 	lds	r25, 0x03C3
    4d60:	90 93 c7 03 	sts	0x03C7, r25
    4d64:	80 93 c6 03 	sts	0x03C6, r24
    4d68:	e0 91 c6 03 	lds	r30, 0x03C6
    4d6c:	f0 91 c7 03 	lds	r31, 0x03C7
    4d70:	01 90       	ld	r0, Z+
    4d72:	f0 81       	ld	r31, Z
    4d74:	e0 2d       	mov	r30, r0
    4d76:	0e 17       	cp	r16, r30
    4d78:	1f 07       	cpc	r17, r31
    4d7a:	09 f4       	brne	.+2      	; 0x4d7e <_ZN6System6Thread4exitEi+0x1ce>
    4d7c:	a1 c0       	rjmp	.+322    	; 0x4ec0 <_ZN6System6Thread4exitEi+0x310>
    4d7e:	e8 01       	movw	r28, r16
    4d80:	8c 81       	ldd	r24, Y+4	; 0x04
    4d82:	9d 81       	ldd	r25, Y+5	; 0x05
    4d84:	02 97       	sbiw	r24, 0x02	; 2
    4d86:	59 f0       	breq	.+22     	; 0x4d9e <_ZN6System6Thread4exitEi+0x1ee>
    4d88:	82 e0       	ldi	r24, 0x02	; 2
    4d8a:	90 e0       	ldi	r25, 0x00	; 0
    4d8c:	95 83       	std	Z+5, r25	; 0x05
    4d8e:	84 83       	std	Z+4, r24	; 0x04
    4d90:	62 81       	ldd	r22, Z+2	; 0x02
    4d92:	73 81       	ldd	r23, Z+3	; 0x03
    4d94:	c8 01       	movw	r24, r16
    4d96:	02 96       	adiw	r24, 0x02	; 2
    4d98:	0e 94 35 31 	call	0x626a	; 0x626a <_ZN6System4AVR814switch_contextEPVPNS0_7ContextES2_>
    4d9c:	91 c0       	rjmp	.+290    	; 0x4ec0 <_ZN6System6Thread4exitEi+0x310>
    4d9e:	81 e0       	ldi	r24, 0x01	; 1
    4da0:	90 e0       	ldi	r25, 0x00	; 0
    4da2:	9d 83       	std	Y+5, r25	; 0x05
    4da4:	8c 83       	std	Y+4, r24	; 0x04
    4da6:	f0 cf       	rjmp	.-32     	; 0x4d88 <_ZN6System6Thread4exitEi+0x1d8>
    4da8:	10 92 c3 03 	sts	0x03C3, r1
    4dac:	10 92 c2 03 	sts	0x03C2, r1
    4db0:	10 92 c5 03 	sts	0x03C5, r1
    4db4:	10 92 c4 03 	sts	0x03C4, r1
    4db8:	01 97       	sbiw	r24, 0x01	; 1
    4dba:	90 93 c1 03 	sts	0x03C1, r25
    4dbe:	80 93 c0 03 	sts	0x03C0, r24
    4dc2:	80 91 c6 03 	lds	r24, 0x03C6
    4dc6:	90 91 c7 03 	lds	r25, 0x03C7
    4dca:	a8 17       	cp	r26, r24
    4dcc:	b9 07       	cpc	r27, r25
    4dce:	09 f0       	breq	.+2      	; 0x4dd2 <_ZN6System6Thread4exitEi+0x222>
    4dd0:	46 cf       	rjmp	.-372    	; 0x4c5e <_ZN6System6Thread4exitEi+0xae>
    4dd2:	80 91 c2 03 	lds	r24, 0x03C2
    4dd6:	90 91 c3 03 	lds	r25, 0x03C3
    4dda:	90 93 c7 03 	sts	0x03C7, r25
    4dde:	80 93 c6 03 	sts	0x03C6, r24
    4de2:	3d cf       	rjmp	.-390    	; 0x4c5e <_ZN6System6Thread4exitEi+0xae>
    4de4:	10 92 c3 03 	sts	0x03C3, r1
    4de8:	10 92 c2 03 	sts	0x03C2, r1
    4dec:	10 92 c5 03 	sts	0x03C5, r1
    4df0:	10 92 c4 03 	sts	0x03C4, r1
    4df4:	10 92 c1 03 	sts	0x03C1, r1
    4df8:	10 92 c0 03 	sts	0x03C0, r1
    4dfc:	a5 cf       	rjmp	.-182    	; 0x4d48 <_ZN6System6Thread4exitEi+0x198>
    4dfe:	df 01       	movw	r26, r30
    4e00:	80 91 c0 03 	lds	r24, 0x03C0
    4e04:	90 91 c1 03 	lds	r25, 0x03C1
    4e08:	81 30       	cpi	r24, 0x01	; 1
    4e0a:	91 05       	cpc	r25, r1
    4e0c:	09 f0       	breq	.+2      	; 0x4e10 <_ZN6System6Thread4exitEi+0x260>
    4e0e:	04 cf       	rjmp	.-504    	; 0x4c18 <_ZN6System6Thread4exitEi+0x68>
    4e10:	cb cf       	rjmp	.-106    	; 0x4da8 <_ZN6System6Thread4exitEi+0x1f8>
    4e12:	80 91 c2 03 	lds	r24, 0x03C2
    4e16:	90 91 c3 03 	lds	r25, 0x03C3
    4e1a:	90 93 c7 03 	sts	0x03C7, r25
    4e1e:	80 93 c6 03 	sts	0x03C6, r24
    4e22:	4d cf       	rjmp	.-358    	; 0x4cbe <_ZN6System6Thread4exitEi+0x10e>
    4e24:	00 97       	sbiw	r24, 0x00	; 0
    4e26:	09 f4       	brne	.+2      	; 0x4e2a <_ZN6System6Thread4exitEi+0x27a>
    4e28:	12 cf       	rjmp	.-476    	; 0x4c4e <_ZN6System6Thread4exitEi+0x9e>
    4e2a:	06 80       	ldd	r0, Z+6	; 0x06
    4e2c:	f7 81       	ldd	r31, Z+7	; 0x07
    4e2e:	e0 2d       	mov	r30, r0
    4e30:	f0 93 c3 03 	sts	0x03C3, r31
    4e34:	e0 93 c2 03 	sts	0x03C2, r30
    4e38:	15 82       	std	Z+5, r1	; 0x05
    4e3a:	14 82       	std	Z+4, r1	; 0x04
    4e3c:	01 97       	sbiw	r24, 0x01	; 1
    4e3e:	90 93 c1 03 	sts	0x03C1, r25
    4e42:	80 93 c0 03 	sts	0x03C0, r24
    4e46:	03 cf       	rjmp	.-506    	; 0x4c4e <_ZN6System6Thread4exitEi+0x9e>
    4e48:	e0 91 c2 03 	lds	r30, 0x03C2
    4e4c:	f0 91 c3 03 	lds	r31, 0x03C3
    4e50:	06 80       	ldd	r0, Z+6	; 0x06
    4e52:	f7 81       	ldd	r31, Z+7	; 0x07
    4e54:	e0 2d       	mov	r30, r0
    4e56:	f0 93 c3 03 	sts	0x03C3, r31
    4e5a:	e0 93 c2 03 	sts	0x03C2, r30
    4e5e:	15 82       	std	Z+5, r1	; 0x05
    4e60:	14 82       	std	Z+4, r1	; 0x04
    4e62:	01 97       	sbiw	r24, 0x01	; 1
    4e64:	90 93 c1 03 	sts	0x03C1, r25
    4e68:	80 93 c0 03 	sts	0x03C0, r24
    4e6c:	6d cf       	rjmp	.-294    	; 0x4d48 <_ZN6System6Thread4exitEi+0x198>
    4e6e:	00 97       	sbiw	r24, 0x00	; 0
    4e70:	09 f4       	brne	.+2      	; 0x4e74 <_ZN6System6Thread4exitEi+0x2c4>
    4e72:	ed ce       	rjmp	.-550    	; 0x4c4e <_ZN6System6Thread4exitEi+0x9e>
    4e74:	e0 91 c4 03 	lds	r30, 0x03C4
    4e78:	f0 91 c5 03 	lds	r31, 0x03C5
    4e7c:	04 80       	ldd	r0, Z+4	; 0x04
    4e7e:	f5 81       	ldd	r31, Z+5	; 0x05
    4e80:	e0 2d       	mov	r30, r0
    4e82:	f0 93 c5 03 	sts	0x03C5, r31
    4e86:	e0 93 c4 03 	sts	0x03C4, r30
    4e8a:	17 82       	std	Z+7, r1	; 0x07
    4e8c:	16 82       	std	Z+6, r1	; 0x06
    4e8e:	01 97       	sbiw	r24, 0x01	; 1
    4e90:	90 93 c1 03 	sts	0x03C1, r25
    4e94:	80 93 c0 03 	sts	0x03C0, r24
    4e98:	da ce       	rjmp	.-588    	; 0x4c4e <_ZN6System6Thread4exitEi+0x9e>
    4e9a:	e0 91 c4 03 	lds	r30, 0x03C4
    4e9e:	f0 91 c5 03 	lds	r31, 0x03C5
    4ea2:	04 80       	ldd	r0, Z+4	; 0x04
    4ea4:	f5 81       	ldd	r31, Z+5	; 0x05
    4ea6:	e0 2d       	mov	r30, r0
    4ea8:	f0 93 c5 03 	sts	0x03C5, r31
    4eac:	e0 93 c4 03 	sts	0x03C4, r30
    4eb0:	17 82       	std	Z+7, r1	; 0x07
    4eb2:	16 82       	std	Z+6, r1	; 0x06
    4eb4:	01 97       	sbiw	r24, 0x01	; 1
    4eb6:	90 93 c1 03 	sts	0x03C1, r25
    4eba:	80 93 c0 03 	sts	0x03C0, r24
    4ebe:	44 cf       	rjmp	.-376    	; 0x4d48 <_ZN6System6Thread4exitEi+0x198>
    4ec0:	78 94       	sei
    4ec2:	78 94       	sei
    4ec4:	df 91       	pop	r29
    4ec6:	cf 91       	pop	r28
    4ec8:	1f 91       	pop	r17
    4eca:	0f 91       	pop	r16
    4ecc:	08 95       	ret

00004ece <_ZN6System6Thread13implicit_exitEv>:
    4ece:	88 2f       	mov	r24, r24
    4ed0:	99 2f       	mov	r25, r25
    4ed2:	0e 94 d8 25 	call	0x4bb0	; 0x4bb0 <_ZN6System6Thread4exitEi>
    4ed6:	08 95       	ret

00004ed8 <_ZN6System6ThreadD1Ev>:
    4ed8:	0f 93       	push	r16
    4eda:	1f 93       	push	r17
    4edc:	cf 93       	push	r28
    4ede:	df 93       	push	r29
    4ee0:	8c 01       	movw	r16, r24
    4ee2:	f8 94       	cli
    4ee4:	ec 01       	movw	r28, r24
    4ee6:	8a 81       	ldd	r24, Y+2	; 0x02
    4ee8:	9b 81       	ldd	r25, Y+3	; 0x03
    4eea:	8c 81       	ldd	r24, Y+4	; 0x04
    4eec:	9d 81       	ldd	r25, Y+5	; 0x05
    4eee:	82 30       	cpi	r24, 0x02	; 2
    4ef0:	91 05       	cpc	r25, r1
    4ef2:	09 f4       	brne	.+2      	; 0x4ef6 <_ZN6System6ThreadD1Ev+0x1e>
    4ef4:	73 c0       	rjmp	.+230    	; 0x4fdc <_ZN6System6ThreadD1Ev+0x104>
    4ef6:	83 30       	cpi	r24, 0x03	; 3
    4ef8:	91 05       	cpc	r25, r1
    4efa:	0c f4       	brge	.+2      	; 0x4efe <_ZN6System6ThreadD1Ev+0x26>
    4efc:	51 c0       	rjmp	.+162    	; 0x4fa0 <_ZN6System6ThreadD1Ev+0xc8>
    4efe:	83 30       	cpi	r24, 0x03	; 3
    4f00:	91 05       	cpc	r25, r1
    4f02:	09 f4       	brne	.+2      	; 0x4f06 <_ZN6System6ThreadD1Ev+0x2e>
    4f04:	50 c0       	rjmp	.+160    	; 0x4fa6 <_ZN6System6ThreadD1Ev+0xce>
    4f06:	04 97       	sbiw	r24, 0x04	; 4
    4f08:	09 f4       	brne	.+2      	; 0x4f0c <_ZN6System6ThreadD1Ev+0x34>
    4f0a:	71 c0       	rjmp	.+226    	; 0x4fee <_ZN6System6ThreadD1Ev+0x116>
    4f0c:	e0 91 c2 03 	lds	r30, 0x03C2
    4f10:	f0 91 c3 03 	lds	r31, 0x03C3
    4f14:	80 91 c6 03 	lds	r24, 0x03C6
    4f18:	90 91 c7 03 	lds	r25, 0x03C7
    4f1c:	30 97       	sbiw	r30, 0x00	; 0
    4f1e:	09 f4       	brne	.+2      	; 0x4f22 <_ZN6System6ThreadD1Ev+0x4a>
    4f20:	1e c1       	rjmp	.+572    	; 0x515e <_ZN6System6ThreadD1Ev+0x286>
    4f22:	80 81       	ld	r24, Z
    4f24:	91 81       	ldd	r25, Z+1	; 0x01
    4f26:	08 17       	cp	r16, r24
    4f28:	19 07       	cpc	r17, r25
    4f2a:	09 f4       	brne	.+2      	; 0x4f2e <_ZN6System6ThreadD1Ev+0x56>
    4f2c:	b6 c0       	rjmp	.+364    	; 0x509a <_ZN6System6ThreadD1Ev+0x1c2>
    4f2e:	df 01       	movw	r26, r30
    4f30:	ed 01       	movw	r28, r26
    4f32:	ae 81       	ldd	r26, Y+6	; 0x06
    4f34:	bf 81       	ldd	r27, Y+7	; 0x07
    4f36:	10 97       	sbiw	r26, 0x00	; 0
    4f38:	09 f4       	brne	.+2      	; 0x4f3c <_ZN6System6ThreadD1Ev+0x64>
    4f3a:	11 c1       	rjmp	.+546    	; 0x515e <_ZN6System6ThreadD1Ev+0x286>
    4f3c:	8d 91       	ld	r24, X+
    4f3e:	9c 91       	ld	r25, X
    4f40:	11 97       	sbiw	r26, 0x01	; 1
    4f42:	08 17       	cp	r16, r24
    4f44:	19 07       	cpc	r17, r25
    4f46:	a1 f7       	brne	.-24     	; 0x4f30 <_ZN6System6ThreadD1Ev+0x58>
    4f48:	80 91 c0 03 	lds	r24, 0x03C0
    4f4c:	90 91 c1 03 	lds	r25, 0x03C1
    4f50:	81 30       	cpi	r24, 0x01	; 1
    4f52:	91 05       	cpc	r25, r1
    4f54:	09 f4       	brne	.+2      	; 0x4f58 <_ZN6System6ThreadD1Ev+0x80>
    4f56:	83 c0       	rjmp	.+262    	; 0x505e <_ZN6System6ThreadD1Ev+0x186>
    4f58:	ed 01       	movw	r28, r26
    4f5a:	4c 81       	ldd	r20, Y+4	; 0x04
    4f5c:	5d 81       	ldd	r21, Y+5	; 0x05
    4f5e:	41 15       	cp	r20, r1
    4f60:	51 05       	cpc	r21, r1
    4f62:	09 f4       	brne	.+2      	; 0x4f66 <_ZN6System6ThreadD1Ev+0x8e>
    4f64:	9c c0       	rjmp	.+312    	; 0x509e <_ZN6System6ThreadD1Ev+0x1c6>
    4f66:	fd 01       	movw	r30, r26
    4f68:	26 81       	ldd	r18, Z+6	; 0x06
    4f6a:	37 81       	ldd	r19, Z+7	; 0x07
    4f6c:	21 15       	cp	r18, r1
    4f6e:	31 05       	cpc	r19, r1
    4f70:	09 f4       	brne	.+2      	; 0x4f74 <_ZN6System6ThreadD1Ev+0x9c>
    4f72:	a7 c0       	rjmp	.+334    	; 0x50c2 <_ZN6System6ThreadD1Ev+0x1ea>
    4f74:	ea 01       	movw	r28, r20
    4f76:	3f 83       	std	Y+7, r19	; 0x07
    4f78:	2e 83       	std	Y+6, r18	; 0x06
    4f7a:	ed 01       	movw	r28, r26
    4f7c:	ee 81       	ldd	r30, Y+6	; 0x06
    4f7e:	ff 81       	ldd	r31, Y+7	; 0x07
    4f80:	55 83       	std	Z+5, r21	; 0x05
    4f82:	44 83       	std	Z+4, r20	; 0x04
    4f84:	01 97       	sbiw	r24, 0x01	; 1
    4f86:	90 93 c1 03 	sts	0x03C1, r25
    4f8a:	80 93 c0 03 	sts	0x03C0, r24
    4f8e:	80 91 c6 03 	lds	r24, 0x03C6
    4f92:	90 91 c7 03 	lds	r25, 0x03C7
    4f96:	8a 17       	cp	r24, r26
    4f98:	9b 07       	cpc	r25, r27
    4f9a:	09 f0       	breq	.+2      	; 0x4f9e <_ZN6System6ThreadD1Ev+0xc6>
    4f9c:	e0 c0       	rjmp	.+448    	; 0x515e <_ZN6System6ThreadD1Ev+0x286>
    4f9e:	74 c0       	rjmp	.+232    	; 0x5088 <_ZN6System6ThreadD1Ev+0x1b0>
    4fa0:	89 2b       	or	r24, r25
    4fa2:	09 f0       	breq	.+2      	; 0x4fa6 <_ZN6System6ThreadD1Ev+0xce>
    4fa4:	b3 cf       	rjmp	.-154    	; 0x4f0c <_ZN6System6ThreadD1Ev+0x34>
    4fa6:	80 91 c6 03 	lds	r24, 0x03C6
    4faa:	90 91 c7 03 	lds	r25, 0x03C7
    4fae:	b8 01       	movw	r22, r16
    4fb0:	66 5f       	subi	r22, 0xF6	; 246
    4fb2:	7f 4f       	sbci	r23, 0xFF	; 255
    4fb4:	80 ec       	ldi	r24, 0xC0	; 192
    4fb6:	93 e0       	ldi	r25, 0x03	; 3
    4fb8:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    4fbc:	80 91 c6 03 	lds	r24, 0x03C6
    4fc0:	90 91 c7 03 	lds	r25, 0x03C7
    4fc4:	89 2b       	or	r24, r25
    4fc6:	09 f0       	breq	.+2      	; 0x4fca <_ZN6System6ThreadD1Ev+0xf2>
    4fc8:	a1 cf       	rjmp	.-190    	; 0x4f0c <_ZN6System6ThreadD1Ev+0x34>
    4fca:	e0 91 c2 03 	lds	r30, 0x03C2
    4fce:	f0 91 c3 03 	lds	r31, 0x03C3
    4fd2:	f0 93 c7 03 	sts	0x03C7, r31
    4fd6:	e0 93 c6 03 	sts	0x03C6, r30
    4fda:	9c cf       	rjmp	.-200    	; 0x4f14 <_ZN6System6ThreadD1Ev+0x3c>
    4fdc:	8f ef       	ldi	r24, 0xFF	; 255
    4fde:	9f ef       	ldi	r25, 0xFF	; 255
    4fe0:	0e 94 d8 25 	call	0x4bb0	; 0x4bb0 <_ZN6System6Thread4exitEi>
    4fe4:	e0 91 c2 03 	lds	r30, 0x03C2
    4fe8:	f0 91 c3 03 	lds	r31, 0x03C3
    4fec:	93 cf       	rjmp	.-218    	; 0x4f14 <_ZN6System6ThreadD1Ev+0x3c>
    4fee:	f8 01       	movw	r30, r16
    4ff0:	26 81       	ldd	r18, Z+6	; 0x06
    4ff2:	37 81       	ldd	r19, Z+7	; 0x07
    4ff4:	f9 01       	movw	r30, r18
    4ff6:	c2 81       	ldd	r28, Z+2	; 0x02
    4ff8:	d3 81       	ldd	r29, Z+3	; 0x03
    4ffa:	20 97       	sbiw	r28, 0x00	; 0
    4ffc:	a1 f2       	breq	.-88     	; 0x4fa6 <_ZN6System6ThreadD1Ev+0xce>
    4ffe:	88 81       	ld	r24, Y
    5000:	99 81       	ldd	r25, Y+1	; 0x01
    5002:	80 17       	cp	r24, r16
    5004:	91 07       	cpc	r25, r17
    5006:	09 f4       	brne	.+2      	; 0x500a <_ZN6System6ThreadD1Ev+0x132>
    5008:	72 c0       	rjmp	.+228    	; 0x50ee <_ZN6System6ThreadD1Ev+0x216>
    500a:	fe 01       	movw	r30, r28
    500c:	06 80       	ldd	r0, Z+6	; 0x06
    500e:	f7 81       	ldd	r31, Z+7	; 0x07
    5010:	e0 2d       	mov	r30, r0
    5012:	30 97       	sbiw	r30, 0x00	; 0
    5014:	41 f2       	breq	.-112    	; 0x4fa6 <_ZN6System6ThreadD1Ev+0xce>
    5016:	80 81       	ld	r24, Z
    5018:	91 81       	ldd	r25, Z+1	; 0x01
    501a:	80 17       	cp	r24, r16
    501c:	91 07       	cpc	r25, r17
    501e:	b1 f7       	brne	.-20     	; 0x500c <_ZN6System6ThreadD1Ev+0x134>
    5020:	d9 01       	movw	r26, r18
    5022:	8d 91       	ld	r24, X+
    5024:	9c 91       	ld	r25, X
    5026:	81 30       	cpi	r24, 0x01	; 1
    5028:	91 05       	cpc	r25, r1
    502a:	09 f4       	brne	.+2      	; 0x502e <_ZN6System6ThreadD1Ev+0x156>
    502c:	62 c0       	rjmp	.+196    	; 0x50f2 <_ZN6System6ThreadD1Ev+0x21a>
    502e:	a4 81       	ldd	r26, Z+4	; 0x04
    5030:	b5 81       	ldd	r27, Z+5	; 0x05
    5032:	10 97       	sbiw	r26, 0x00	; 0
    5034:	09 f4       	brne	.+2      	; 0x5038 <_ZN6System6ThreadD1Ev+0x160>
    5036:	66 c0       	rjmp	.+204    	; 0x5104 <_ZN6System6ThreadD1Ev+0x22c>
    5038:	46 81       	ldd	r20, Z+6	; 0x06
    503a:	57 81       	ldd	r21, Z+7	; 0x07
    503c:	41 15       	cp	r20, r1
    503e:	51 05       	cpc	r21, r1
    5040:	09 f4       	brne	.+2      	; 0x5044 <_ZN6System6ThreadD1Ev+0x16c>
    5042:	71 c0       	rjmp	.+226    	; 0x5126 <_ZN6System6ThreadD1Ev+0x24e>
    5044:	ed 01       	movw	r28, r26
    5046:	5f 83       	std	Y+7, r21	; 0x07
    5048:	4e 83       	std	Y+6, r20	; 0x06
    504a:	06 80       	ldd	r0, Z+6	; 0x06
    504c:	f7 81       	ldd	r31, Z+7	; 0x07
    504e:	e0 2d       	mov	r30, r0
    5050:	b5 83       	std	Z+5, r27	; 0x05
    5052:	a4 83       	std	Z+4, r26	; 0x04
    5054:	01 97       	sbiw	r24, 0x01	; 1
    5056:	f9 01       	movw	r30, r18
    5058:	91 83       	std	Z+1, r25	; 0x01
    505a:	80 83       	st	Z, r24
    505c:	a4 cf       	rjmp	.-184    	; 0x4fa6 <_ZN6System6ThreadD1Ev+0xce>
    505e:	10 92 c3 03 	sts	0x03C3, r1
    5062:	10 92 c2 03 	sts	0x03C2, r1
    5066:	10 92 c5 03 	sts	0x03C5, r1
    506a:	10 92 c4 03 	sts	0x03C4, r1
    506e:	01 97       	sbiw	r24, 0x01	; 1
    5070:	90 93 c1 03 	sts	0x03C1, r25
    5074:	80 93 c0 03 	sts	0x03C0, r24
    5078:	80 91 c6 03 	lds	r24, 0x03C6
    507c:	90 91 c7 03 	lds	r25, 0x03C7
    5080:	8a 17       	cp	r24, r26
    5082:	9b 07       	cpc	r25, r27
    5084:	09 f0       	breq	.+2      	; 0x5088 <_ZN6System6ThreadD1Ev+0x1b0>
    5086:	6b c0       	rjmp	.+214    	; 0x515e <_ZN6System6ThreadD1Ev+0x286>
    5088:	80 91 c2 03 	lds	r24, 0x03C2
    508c:	90 91 c3 03 	lds	r25, 0x03C3
    5090:	90 93 c7 03 	sts	0x03C7, r25
    5094:	80 93 c6 03 	sts	0x03C6, r24
    5098:	62 c0       	rjmp	.+196    	; 0x515e <_ZN6System6ThreadD1Ev+0x286>
    509a:	df 01       	movw	r26, r30
    509c:	55 cf       	rjmp	.-342    	; 0x4f48 <_ZN6System6ThreadD1Ev+0x70>
    509e:	00 97       	sbiw	r24, 0x00	; 0
    50a0:	09 f4       	brne	.+2      	; 0x50a4 <_ZN6System6ThreadD1Ev+0x1cc>
    50a2:	75 cf       	rjmp	.-278    	; 0x4f8e <_ZN6System6ThreadD1Ev+0xb6>
    50a4:	06 80       	ldd	r0, Z+6	; 0x06
    50a6:	f7 81       	ldd	r31, Z+7	; 0x07
    50a8:	e0 2d       	mov	r30, r0
    50aa:	f0 93 c3 03 	sts	0x03C3, r31
    50ae:	e0 93 c2 03 	sts	0x03C2, r30
    50b2:	15 82       	std	Z+5, r1	; 0x05
    50b4:	14 82       	std	Z+4, r1	; 0x04
    50b6:	01 97       	sbiw	r24, 0x01	; 1
    50b8:	90 93 c1 03 	sts	0x03C1, r25
    50bc:	80 93 c0 03 	sts	0x03C0, r24
    50c0:	66 cf       	rjmp	.-308    	; 0x4f8e <_ZN6System6ThreadD1Ev+0xb6>
    50c2:	00 97       	sbiw	r24, 0x00	; 0
    50c4:	09 f4       	brne	.+2      	; 0x50c8 <_ZN6System6ThreadD1Ev+0x1f0>
    50c6:	63 cf       	rjmp	.-314    	; 0x4f8e <_ZN6System6ThreadD1Ev+0xb6>
    50c8:	e0 91 c4 03 	lds	r30, 0x03C4
    50cc:	f0 91 c5 03 	lds	r31, 0x03C5
    50d0:	04 80       	ldd	r0, Z+4	; 0x04
    50d2:	f5 81       	ldd	r31, Z+5	; 0x05
    50d4:	e0 2d       	mov	r30, r0
    50d6:	f0 93 c5 03 	sts	0x03C5, r31
    50da:	e0 93 c4 03 	sts	0x03C4, r30
    50de:	17 82       	std	Z+7, r1	; 0x07
    50e0:	16 82       	std	Z+6, r1	; 0x06
    50e2:	01 97       	sbiw	r24, 0x01	; 1
    50e4:	90 93 c1 03 	sts	0x03C1, r25
    50e8:	80 93 c0 03 	sts	0x03C0, r24
    50ec:	50 cf       	rjmp	.-352    	; 0x4f8e <_ZN6System6ThreadD1Ev+0xb6>
    50ee:	fe 01       	movw	r30, r28
    50f0:	97 cf       	rjmp	.-210    	; 0x5020 <_ZN6System6ThreadD1Ev+0x148>
    50f2:	e9 01       	movw	r28, r18
    50f4:	1b 82       	std	Y+3, r1	; 0x03
    50f6:	1a 82       	std	Y+2, r1	; 0x02
    50f8:	1d 82       	std	Y+5, r1	; 0x05
    50fa:	1c 82       	std	Y+4, r1	; 0x04
    50fc:	01 97       	sbiw	r24, 0x01	; 1
    50fe:	99 83       	std	Y+1, r25	; 0x01
    5100:	88 83       	st	Y, r24
    5102:	51 cf       	rjmp	.-350    	; 0x4fa6 <_ZN6System6ThreadD1Ev+0xce>
    5104:	00 97       	sbiw	r24, 0x00	; 0
    5106:	09 f4       	brne	.+2      	; 0x510a <_ZN6System6ThreadD1Ev+0x232>
    5108:	4e cf       	rjmp	.-356    	; 0x4fa6 <_ZN6System6ThreadD1Ev+0xce>
    510a:	81 30       	cpi	r24, 0x01	; 1
    510c:	91 05       	cpc	r25, r1
    510e:	f9 f0       	breq	.+62     	; 0x514e <_ZN6System6ThreadD1Ev+0x276>
    5110:	ee 81       	ldd	r30, Y+6	; 0x06
    5112:	ff 81       	ldd	r31, Y+7	; 0x07
    5114:	e9 01       	movw	r28, r18
    5116:	fb 83       	std	Y+3, r31	; 0x03
    5118:	ea 83       	std	Y+2, r30	; 0x02
    511a:	15 82       	std	Z+5, r1	; 0x05
    511c:	14 82       	std	Z+4, r1	; 0x04
    511e:	01 97       	sbiw	r24, 0x01	; 1
    5120:	99 83       	std	Y+1, r25	; 0x01
    5122:	88 83       	st	Y, r24
    5124:	40 cf       	rjmp	.-384    	; 0x4fa6 <_ZN6System6ThreadD1Ev+0xce>
    5126:	00 97       	sbiw	r24, 0x00	; 0
    5128:	09 f4       	brne	.+2      	; 0x512c <_ZN6System6ThreadD1Ev+0x254>
    512a:	3d cf       	rjmp	.-390    	; 0x4fa6 <_ZN6System6ThreadD1Ev+0xce>
    512c:	81 30       	cpi	r24, 0x01	; 1
    512e:	91 05       	cpc	r25, r1
    5130:	71 f0       	breq	.+28     	; 0x514e <_ZN6System6ThreadD1Ev+0x276>
    5132:	e9 01       	movw	r28, r18
    5134:	ec 81       	ldd	r30, Y+4	; 0x04
    5136:	fd 81       	ldd	r31, Y+5	; 0x05
    5138:	04 80       	ldd	r0, Z+4	; 0x04
    513a:	f5 81       	ldd	r31, Z+5	; 0x05
    513c:	e0 2d       	mov	r30, r0
    513e:	fd 83       	std	Y+5, r31	; 0x05
    5140:	ec 83       	std	Y+4, r30	; 0x04
    5142:	17 82       	std	Z+7, r1	; 0x07
    5144:	16 82       	std	Z+6, r1	; 0x06
    5146:	01 97       	sbiw	r24, 0x01	; 1
    5148:	99 83       	std	Y+1, r25	; 0x01
    514a:	88 83       	st	Y, r24
    514c:	2c cf       	rjmp	.-424    	; 0x4fa6 <_ZN6System6ThreadD1Ev+0xce>
    514e:	f9 01       	movw	r30, r18
    5150:	13 82       	std	Z+3, r1	; 0x03
    5152:	12 82       	std	Z+2, r1	; 0x02
    5154:	15 82       	std	Z+5, r1	; 0x05
    5156:	14 82       	std	Z+4, r1	; 0x04
    5158:	11 82       	std	Z+1, r1	; 0x01
    515a:	10 82       	st	Z, r1
    515c:	24 cf       	rjmp	.-440    	; 0x4fa6 <_ZN6System6ThreadD1Ev+0xce>
    515e:	78 94       	sei
    5160:	d8 01       	movw	r26, r16
    5162:	ed 91       	ld	r30, X+
    5164:	fc 91       	ld	r31, X
    5166:	32 97       	sbiw	r30, 0x02	; 2
    5168:	40 81       	ld	r20, Z
    516a:	51 81       	ldd	r21, Z+1	; 0x01
    516c:	bf 01       	movw	r22, r30
    516e:	8f e9       	ldi	r24, 0x9F	; 159
    5170:	93 e0       	ldi	r25, 0x03	; 3
    5172:	0e 94 c1 12 	call	0x2582	; 0x2582 <_ZN6System4Heap4freeEPvj>
    5176:	df 91       	pop	r29
    5178:	cf 91       	pop	r28
    517a:	1f 91       	pop	r17
    517c:	0f 91       	pop	r16
    517e:	08 95       	ret

00005180 <_ZN6System6ThreadD2Ev>:
    5180:	0f 93       	push	r16
    5182:	1f 93       	push	r17
    5184:	cf 93       	push	r28
    5186:	df 93       	push	r29
    5188:	8c 01       	movw	r16, r24
    518a:	f8 94       	cli
    518c:	ec 01       	movw	r28, r24
    518e:	8a 81       	ldd	r24, Y+2	; 0x02
    5190:	9b 81       	ldd	r25, Y+3	; 0x03
    5192:	8c 81       	ldd	r24, Y+4	; 0x04
    5194:	9d 81       	ldd	r25, Y+5	; 0x05
    5196:	82 30       	cpi	r24, 0x02	; 2
    5198:	91 05       	cpc	r25, r1
    519a:	09 f4       	brne	.+2      	; 0x519e <_ZN6System6ThreadD2Ev+0x1e>
    519c:	73 c0       	rjmp	.+230    	; 0x5284 <_ZN6System6ThreadD2Ev+0x104>
    519e:	83 30       	cpi	r24, 0x03	; 3
    51a0:	91 05       	cpc	r25, r1
    51a2:	0c f4       	brge	.+2      	; 0x51a6 <_ZN6System6ThreadD2Ev+0x26>
    51a4:	51 c0       	rjmp	.+162    	; 0x5248 <_ZN6System6ThreadD2Ev+0xc8>
    51a6:	83 30       	cpi	r24, 0x03	; 3
    51a8:	91 05       	cpc	r25, r1
    51aa:	09 f4       	brne	.+2      	; 0x51ae <_ZN6System6ThreadD2Ev+0x2e>
    51ac:	50 c0       	rjmp	.+160    	; 0x524e <_ZN6System6ThreadD2Ev+0xce>
    51ae:	04 97       	sbiw	r24, 0x04	; 4
    51b0:	09 f4       	brne	.+2      	; 0x51b4 <_ZN6System6ThreadD2Ev+0x34>
    51b2:	71 c0       	rjmp	.+226    	; 0x5296 <_ZN6System6ThreadD2Ev+0x116>
    51b4:	e0 91 c2 03 	lds	r30, 0x03C2
    51b8:	f0 91 c3 03 	lds	r31, 0x03C3
    51bc:	80 91 c6 03 	lds	r24, 0x03C6
    51c0:	90 91 c7 03 	lds	r25, 0x03C7
    51c4:	30 97       	sbiw	r30, 0x00	; 0
    51c6:	09 f4       	brne	.+2      	; 0x51ca <_ZN6System6ThreadD2Ev+0x4a>
    51c8:	1e c1       	rjmp	.+572    	; 0x5406 <_ZN6System6ThreadD2Ev+0x286>
    51ca:	80 81       	ld	r24, Z
    51cc:	91 81       	ldd	r25, Z+1	; 0x01
    51ce:	08 17       	cp	r16, r24
    51d0:	19 07       	cpc	r17, r25
    51d2:	09 f4       	brne	.+2      	; 0x51d6 <_ZN6System6ThreadD2Ev+0x56>
    51d4:	b6 c0       	rjmp	.+364    	; 0x5342 <_ZN6System6ThreadD2Ev+0x1c2>
    51d6:	df 01       	movw	r26, r30
    51d8:	ed 01       	movw	r28, r26
    51da:	ae 81       	ldd	r26, Y+6	; 0x06
    51dc:	bf 81       	ldd	r27, Y+7	; 0x07
    51de:	10 97       	sbiw	r26, 0x00	; 0
    51e0:	09 f4       	brne	.+2      	; 0x51e4 <_ZN6System6ThreadD2Ev+0x64>
    51e2:	11 c1       	rjmp	.+546    	; 0x5406 <_ZN6System6ThreadD2Ev+0x286>
    51e4:	8d 91       	ld	r24, X+
    51e6:	9c 91       	ld	r25, X
    51e8:	11 97       	sbiw	r26, 0x01	; 1
    51ea:	08 17       	cp	r16, r24
    51ec:	19 07       	cpc	r17, r25
    51ee:	a1 f7       	brne	.-24     	; 0x51d8 <_ZN6System6ThreadD2Ev+0x58>
    51f0:	80 91 c0 03 	lds	r24, 0x03C0
    51f4:	90 91 c1 03 	lds	r25, 0x03C1
    51f8:	81 30       	cpi	r24, 0x01	; 1
    51fa:	91 05       	cpc	r25, r1
    51fc:	09 f4       	brne	.+2      	; 0x5200 <_ZN6System6ThreadD2Ev+0x80>
    51fe:	83 c0       	rjmp	.+262    	; 0x5306 <_ZN6System6ThreadD2Ev+0x186>
    5200:	ed 01       	movw	r28, r26
    5202:	4c 81       	ldd	r20, Y+4	; 0x04
    5204:	5d 81       	ldd	r21, Y+5	; 0x05
    5206:	41 15       	cp	r20, r1
    5208:	51 05       	cpc	r21, r1
    520a:	09 f4       	brne	.+2      	; 0x520e <_ZN6System6ThreadD2Ev+0x8e>
    520c:	9c c0       	rjmp	.+312    	; 0x5346 <_ZN6System6ThreadD2Ev+0x1c6>
    520e:	fd 01       	movw	r30, r26
    5210:	26 81       	ldd	r18, Z+6	; 0x06
    5212:	37 81       	ldd	r19, Z+7	; 0x07
    5214:	21 15       	cp	r18, r1
    5216:	31 05       	cpc	r19, r1
    5218:	09 f4       	brne	.+2      	; 0x521c <_ZN6System6ThreadD2Ev+0x9c>
    521a:	a7 c0       	rjmp	.+334    	; 0x536a <_ZN6System6ThreadD2Ev+0x1ea>
    521c:	ea 01       	movw	r28, r20
    521e:	3f 83       	std	Y+7, r19	; 0x07
    5220:	2e 83       	std	Y+6, r18	; 0x06
    5222:	ed 01       	movw	r28, r26
    5224:	ee 81       	ldd	r30, Y+6	; 0x06
    5226:	ff 81       	ldd	r31, Y+7	; 0x07
    5228:	55 83       	std	Z+5, r21	; 0x05
    522a:	44 83       	std	Z+4, r20	; 0x04
    522c:	01 97       	sbiw	r24, 0x01	; 1
    522e:	90 93 c1 03 	sts	0x03C1, r25
    5232:	80 93 c0 03 	sts	0x03C0, r24
    5236:	80 91 c6 03 	lds	r24, 0x03C6
    523a:	90 91 c7 03 	lds	r25, 0x03C7
    523e:	8a 17       	cp	r24, r26
    5240:	9b 07       	cpc	r25, r27
    5242:	09 f0       	breq	.+2      	; 0x5246 <_ZN6System6ThreadD2Ev+0xc6>
    5244:	e0 c0       	rjmp	.+448    	; 0x5406 <_ZN6System6ThreadD2Ev+0x286>
    5246:	74 c0       	rjmp	.+232    	; 0x5330 <_ZN6System6ThreadD2Ev+0x1b0>
    5248:	89 2b       	or	r24, r25
    524a:	09 f0       	breq	.+2      	; 0x524e <_ZN6System6ThreadD2Ev+0xce>
    524c:	b3 cf       	rjmp	.-154    	; 0x51b4 <_ZN6System6ThreadD2Ev+0x34>
    524e:	80 91 c6 03 	lds	r24, 0x03C6
    5252:	90 91 c7 03 	lds	r25, 0x03C7
    5256:	b8 01       	movw	r22, r16
    5258:	66 5f       	subi	r22, 0xF6	; 246
    525a:	7f 4f       	sbci	r23, 0xFF	; 255
    525c:	80 ec       	ldi	r24, 0xC0	; 192
    525e:	93 e0       	ldi	r25, 0x03	; 3
    5260:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    5264:	80 91 c6 03 	lds	r24, 0x03C6
    5268:	90 91 c7 03 	lds	r25, 0x03C7
    526c:	89 2b       	or	r24, r25
    526e:	09 f0       	breq	.+2      	; 0x5272 <_ZN6System6ThreadD2Ev+0xf2>
    5270:	a1 cf       	rjmp	.-190    	; 0x51b4 <_ZN6System6ThreadD2Ev+0x34>
    5272:	e0 91 c2 03 	lds	r30, 0x03C2
    5276:	f0 91 c3 03 	lds	r31, 0x03C3
    527a:	f0 93 c7 03 	sts	0x03C7, r31
    527e:	e0 93 c6 03 	sts	0x03C6, r30
    5282:	9c cf       	rjmp	.-200    	; 0x51bc <_ZN6System6ThreadD2Ev+0x3c>
    5284:	8f ef       	ldi	r24, 0xFF	; 255
    5286:	9f ef       	ldi	r25, 0xFF	; 255
    5288:	0e 94 d8 25 	call	0x4bb0	; 0x4bb0 <_ZN6System6Thread4exitEi>
    528c:	e0 91 c2 03 	lds	r30, 0x03C2
    5290:	f0 91 c3 03 	lds	r31, 0x03C3
    5294:	93 cf       	rjmp	.-218    	; 0x51bc <_ZN6System6ThreadD2Ev+0x3c>
    5296:	f8 01       	movw	r30, r16
    5298:	26 81       	ldd	r18, Z+6	; 0x06
    529a:	37 81       	ldd	r19, Z+7	; 0x07
    529c:	f9 01       	movw	r30, r18
    529e:	c2 81       	ldd	r28, Z+2	; 0x02
    52a0:	d3 81       	ldd	r29, Z+3	; 0x03
    52a2:	20 97       	sbiw	r28, 0x00	; 0
    52a4:	a1 f2       	breq	.-88     	; 0x524e <_ZN6System6ThreadD2Ev+0xce>
    52a6:	88 81       	ld	r24, Y
    52a8:	99 81       	ldd	r25, Y+1	; 0x01
    52aa:	80 17       	cp	r24, r16
    52ac:	91 07       	cpc	r25, r17
    52ae:	09 f4       	brne	.+2      	; 0x52b2 <_ZN6System6ThreadD2Ev+0x132>
    52b0:	72 c0       	rjmp	.+228    	; 0x5396 <_ZN6System6ThreadD2Ev+0x216>
    52b2:	fe 01       	movw	r30, r28
    52b4:	06 80       	ldd	r0, Z+6	; 0x06
    52b6:	f7 81       	ldd	r31, Z+7	; 0x07
    52b8:	e0 2d       	mov	r30, r0
    52ba:	30 97       	sbiw	r30, 0x00	; 0
    52bc:	41 f2       	breq	.-112    	; 0x524e <_ZN6System6ThreadD2Ev+0xce>
    52be:	80 81       	ld	r24, Z
    52c0:	91 81       	ldd	r25, Z+1	; 0x01
    52c2:	80 17       	cp	r24, r16
    52c4:	91 07       	cpc	r25, r17
    52c6:	b1 f7       	brne	.-20     	; 0x52b4 <_ZN6System6ThreadD2Ev+0x134>
    52c8:	d9 01       	movw	r26, r18
    52ca:	8d 91       	ld	r24, X+
    52cc:	9c 91       	ld	r25, X
    52ce:	81 30       	cpi	r24, 0x01	; 1
    52d0:	91 05       	cpc	r25, r1
    52d2:	09 f4       	brne	.+2      	; 0x52d6 <_ZN6System6ThreadD2Ev+0x156>
    52d4:	62 c0       	rjmp	.+196    	; 0x539a <_ZN6System6ThreadD2Ev+0x21a>
    52d6:	a4 81       	ldd	r26, Z+4	; 0x04
    52d8:	b5 81       	ldd	r27, Z+5	; 0x05
    52da:	10 97       	sbiw	r26, 0x00	; 0
    52dc:	09 f4       	brne	.+2      	; 0x52e0 <_ZN6System6ThreadD2Ev+0x160>
    52de:	66 c0       	rjmp	.+204    	; 0x53ac <_ZN6System6ThreadD2Ev+0x22c>
    52e0:	46 81       	ldd	r20, Z+6	; 0x06
    52e2:	57 81       	ldd	r21, Z+7	; 0x07
    52e4:	41 15       	cp	r20, r1
    52e6:	51 05       	cpc	r21, r1
    52e8:	09 f4       	brne	.+2      	; 0x52ec <_ZN6System6ThreadD2Ev+0x16c>
    52ea:	71 c0       	rjmp	.+226    	; 0x53ce <_ZN6System6ThreadD2Ev+0x24e>
    52ec:	ed 01       	movw	r28, r26
    52ee:	5f 83       	std	Y+7, r21	; 0x07
    52f0:	4e 83       	std	Y+6, r20	; 0x06
    52f2:	06 80       	ldd	r0, Z+6	; 0x06
    52f4:	f7 81       	ldd	r31, Z+7	; 0x07
    52f6:	e0 2d       	mov	r30, r0
    52f8:	b5 83       	std	Z+5, r27	; 0x05
    52fa:	a4 83       	std	Z+4, r26	; 0x04
    52fc:	01 97       	sbiw	r24, 0x01	; 1
    52fe:	f9 01       	movw	r30, r18
    5300:	91 83       	std	Z+1, r25	; 0x01
    5302:	80 83       	st	Z, r24
    5304:	a4 cf       	rjmp	.-184    	; 0x524e <_ZN6System6ThreadD2Ev+0xce>
    5306:	10 92 c3 03 	sts	0x03C3, r1
    530a:	10 92 c2 03 	sts	0x03C2, r1
    530e:	10 92 c5 03 	sts	0x03C5, r1
    5312:	10 92 c4 03 	sts	0x03C4, r1
    5316:	01 97       	sbiw	r24, 0x01	; 1
    5318:	90 93 c1 03 	sts	0x03C1, r25
    531c:	80 93 c0 03 	sts	0x03C0, r24
    5320:	80 91 c6 03 	lds	r24, 0x03C6
    5324:	90 91 c7 03 	lds	r25, 0x03C7
    5328:	8a 17       	cp	r24, r26
    532a:	9b 07       	cpc	r25, r27
    532c:	09 f0       	breq	.+2      	; 0x5330 <_ZN6System6ThreadD2Ev+0x1b0>
    532e:	6b c0       	rjmp	.+214    	; 0x5406 <_ZN6System6ThreadD2Ev+0x286>
    5330:	80 91 c2 03 	lds	r24, 0x03C2
    5334:	90 91 c3 03 	lds	r25, 0x03C3
    5338:	90 93 c7 03 	sts	0x03C7, r25
    533c:	80 93 c6 03 	sts	0x03C6, r24
    5340:	62 c0       	rjmp	.+196    	; 0x5406 <_ZN6System6ThreadD2Ev+0x286>
    5342:	df 01       	movw	r26, r30
    5344:	55 cf       	rjmp	.-342    	; 0x51f0 <_ZN6System6ThreadD2Ev+0x70>
    5346:	00 97       	sbiw	r24, 0x00	; 0
    5348:	09 f4       	brne	.+2      	; 0x534c <_ZN6System6ThreadD2Ev+0x1cc>
    534a:	75 cf       	rjmp	.-278    	; 0x5236 <_ZN6System6ThreadD2Ev+0xb6>
    534c:	06 80       	ldd	r0, Z+6	; 0x06
    534e:	f7 81       	ldd	r31, Z+7	; 0x07
    5350:	e0 2d       	mov	r30, r0
    5352:	f0 93 c3 03 	sts	0x03C3, r31
    5356:	e0 93 c2 03 	sts	0x03C2, r30
    535a:	15 82       	std	Z+5, r1	; 0x05
    535c:	14 82       	std	Z+4, r1	; 0x04
    535e:	01 97       	sbiw	r24, 0x01	; 1
    5360:	90 93 c1 03 	sts	0x03C1, r25
    5364:	80 93 c0 03 	sts	0x03C0, r24
    5368:	66 cf       	rjmp	.-308    	; 0x5236 <_ZN6System6ThreadD2Ev+0xb6>
    536a:	00 97       	sbiw	r24, 0x00	; 0
    536c:	09 f4       	brne	.+2      	; 0x5370 <_ZN6System6ThreadD2Ev+0x1f0>
    536e:	63 cf       	rjmp	.-314    	; 0x5236 <_ZN6System6ThreadD2Ev+0xb6>
    5370:	e0 91 c4 03 	lds	r30, 0x03C4
    5374:	f0 91 c5 03 	lds	r31, 0x03C5
    5378:	04 80       	ldd	r0, Z+4	; 0x04
    537a:	f5 81       	ldd	r31, Z+5	; 0x05
    537c:	e0 2d       	mov	r30, r0
    537e:	f0 93 c5 03 	sts	0x03C5, r31
    5382:	e0 93 c4 03 	sts	0x03C4, r30
    5386:	17 82       	std	Z+7, r1	; 0x07
    5388:	16 82       	std	Z+6, r1	; 0x06
    538a:	01 97       	sbiw	r24, 0x01	; 1
    538c:	90 93 c1 03 	sts	0x03C1, r25
    5390:	80 93 c0 03 	sts	0x03C0, r24
    5394:	50 cf       	rjmp	.-352    	; 0x5236 <_ZN6System6ThreadD2Ev+0xb6>
    5396:	fe 01       	movw	r30, r28
    5398:	97 cf       	rjmp	.-210    	; 0x52c8 <_ZN6System6ThreadD2Ev+0x148>
    539a:	e9 01       	movw	r28, r18
    539c:	1b 82       	std	Y+3, r1	; 0x03
    539e:	1a 82       	std	Y+2, r1	; 0x02
    53a0:	1d 82       	std	Y+5, r1	; 0x05
    53a2:	1c 82       	std	Y+4, r1	; 0x04
    53a4:	01 97       	sbiw	r24, 0x01	; 1
    53a6:	99 83       	std	Y+1, r25	; 0x01
    53a8:	88 83       	st	Y, r24
    53aa:	51 cf       	rjmp	.-350    	; 0x524e <_ZN6System6ThreadD2Ev+0xce>
    53ac:	00 97       	sbiw	r24, 0x00	; 0
    53ae:	09 f4       	brne	.+2      	; 0x53b2 <_ZN6System6ThreadD2Ev+0x232>
    53b0:	4e cf       	rjmp	.-356    	; 0x524e <_ZN6System6ThreadD2Ev+0xce>
    53b2:	81 30       	cpi	r24, 0x01	; 1
    53b4:	91 05       	cpc	r25, r1
    53b6:	f9 f0       	breq	.+62     	; 0x53f6 <_ZN6System6ThreadD2Ev+0x276>
    53b8:	ee 81       	ldd	r30, Y+6	; 0x06
    53ba:	ff 81       	ldd	r31, Y+7	; 0x07
    53bc:	e9 01       	movw	r28, r18
    53be:	fb 83       	std	Y+3, r31	; 0x03
    53c0:	ea 83       	std	Y+2, r30	; 0x02
    53c2:	15 82       	std	Z+5, r1	; 0x05
    53c4:	14 82       	std	Z+4, r1	; 0x04
    53c6:	01 97       	sbiw	r24, 0x01	; 1
    53c8:	99 83       	std	Y+1, r25	; 0x01
    53ca:	88 83       	st	Y, r24
    53cc:	40 cf       	rjmp	.-384    	; 0x524e <_ZN6System6ThreadD2Ev+0xce>
    53ce:	00 97       	sbiw	r24, 0x00	; 0
    53d0:	09 f4       	brne	.+2      	; 0x53d4 <_ZN6System6ThreadD2Ev+0x254>
    53d2:	3d cf       	rjmp	.-390    	; 0x524e <_ZN6System6ThreadD2Ev+0xce>
    53d4:	81 30       	cpi	r24, 0x01	; 1
    53d6:	91 05       	cpc	r25, r1
    53d8:	71 f0       	breq	.+28     	; 0x53f6 <_ZN6System6ThreadD2Ev+0x276>
    53da:	e9 01       	movw	r28, r18
    53dc:	ec 81       	ldd	r30, Y+4	; 0x04
    53de:	fd 81       	ldd	r31, Y+5	; 0x05
    53e0:	04 80       	ldd	r0, Z+4	; 0x04
    53e2:	f5 81       	ldd	r31, Z+5	; 0x05
    53e4:	e0 2d       	mov	r30, r0
    53e6:	fd 83       	std	Y+5, r31	; 0x05
    53e8:	ec 83       	std	Y+4, r30	; 0x04
    53ea:	17 82       	std	Z+7, r1	; 0x07
    53ec:	16 82       	std	Z+6, r1	; 0x06
    53ee:	01 97       	sbiw	r24, 0x01	; 1
    53f0:	99 83       	std	Y+1, r25	; 0x01
    53f2:	88 83       	st	Y, r24
    53f4:	2c cf       	rjmp	.-424    	; 0x524e <_ZN6System6ThreadD2Ev+0xce>
    53f6:	f9 01       	movw	r30, r18
    53f8:	13 82       	std	Z+3, r1	; 0x03
    53fa:	12 82       	std	Z+2, r1	; 0x02
    53fc:	15 82       	std	Z+5, r1	; 0x05
    53fe:	14 82       	std	Z+4, r1	; 0x04
    5400:	11 82       	std	Z+1, r1	; 0x01
    5402:	10 82       	st	Z, r1
    5404:	24 cf       	rjmp	.-440    	; 0x524e <_ZN6System6ThreadD2Ev+0xce>
    5406:	78 94       	sei
    5408:	d8 01       	movw	r26, r16
    540a:	ed 91       	ld	r30, X+
    540c:	fc 91       	ld	r31, X
    540e:	32 97       	sbiw	r30, 0x02	; 2
    5410:	40 81       	ld	r20, Z
    5412:	51 81       	ldd	r21, Z+1	; 0x01
    5414:	bf 01       	movw	r22, r30
    5416:	8f e9       	ldi	r24, 0x9F	; 159
    5418:	93 e0       	ldi	r25, 0x03	; 3
    541a:	0e 94 c1 12 	call	0x2582	; 0x2582 <_ZN6System4Heap4freeEPvj>
    541e:	df 91       	pop	r29
    5420:	cf 91       	pop	r28
    5422:	1f 91       	pop	r17
    5424:	0f 91       	pop	r16
    5426:	08 95       	ret

00005428 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE>:
    5428:	0f 93       	push	r16
    542a:	1f 93       	push	r17
    542c:	cf 93       	push	r28
    542e:	df 93       	push	r29
    5430:	8c 01       	movw	r16, r24
    5432:	f8 94       	cli
    5434:	80 91 c6 03 	lds	r24, 0x03C6
    5438:	90 91 c7 03 	lds	r25, 0x03C7
    543c:	e0 91 c6 03 	lds	r30, 0x03C6
    5440:	f0 91 c7 03 	lds	r31, 0x03C7
    5444:	c0 81       	ld	r28, Z
    5446:	d1 81       	ldd	r29, Z+1	; 0x01
    5448:	80 91 c6 03 	lds	r24, 0x03C6
    544c:	90 91 c7 03 	lds	r25, 0x03C7
    5450:	40 91 c2 03 	lds	r20, 0x03C2
    5454:	50 91 c3 03 	lds	r21, 0x03C3
    5458:	41 15       	cp	r20, r1
    545a:	51 05       	cpc	r21, r1
    545c:	09 f4       	brne	.+2      	; 0x5460 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x38>
    545e:	3f c0       	rjmp	.+126    	; 0x54de <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0xb6>
    5460:	fa 01       	movw	r30, r20
    5462:	80 81       	ld	r24, Z
    5464:	91 81       	ldd	r25, Z+1	; 0x01
    5466:	c8 17       	cp	r28, r24
    5468:	d9 07       	cpc	r29, r25
    546a:	09 f4       	brne	.+2      	; 0x546e <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x46>
    546c:	9a c0       	rjmp	.+308    	; 0x55a2 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x17a>
    546e:	da 01       	movw	r26, r20
    5470:	fd 01       	movw	r30, r26
    5472:	a6 81       	ldd	r26, Z+6	; 0x06
    5474:	b7 81       	ldd	r27, Z+7	; 0x07
    5476:	10 97       	sbiw	r26, 0x00	; 0
    5478:	91 f1       	breq	.+100    	; 0x54de <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0xb6>
    547a:	8d 91       	ld	r24, X+
    547c:	9c 91       	ld	r25, X
    547e:	11 97       	sbiw	r26, 0x01	; 1
    5480:	c8 17       	cp	r28, r24
    5482:	d9 07       	cpc	r29, r25
    5484:	a9 f7       	brne	.-22     	; 0x5470 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x48>
    5486:	20 91 c0 03 	lds	r18, 0x03C0
    548a:	30 91 c1 03 	lds	r19, 0x03C1
    548e:	21 30       	cpi	r18, 0x01	; 1
    5490:	31 05       	cpc	r19, r1
    5492:	09 f4       	brne	.+2      	; 0x5496 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x6e>
    5494:	67 c0       	rjmp	.+206    	; 0x5564 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x13c>
    5496:	fd 01       	movw	r30, r26
    5498:	84 81       	ldd	r24, Z+4	; 0x04
    549a:	95 81       	ldd	r25, Z+5	; 0x05
    549c:	00 97       	sbiw	r24, 0x00	; 0
    549e:	09 f4       	brne	.+2      	; 0x54a2 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x7a>
    54a0:	8a c0       	rjmp	.+276    	; 0x55b6 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x18e>
    54a2:	fd 01       	movw	r30, r26
    54a4:	46 81       	ldd	r20, Z+6	; 0x06
    54a6:	57 81       	ldd	r21, Z+7	; 0x07
    54a8:	41 15       	cp	r20, r1
    54aa:	51 05       	cpc	r21, r1
    54ac:	09 f4       	brne	.+2      	; 0x54b0 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x88>
    54ae:	98 c0       	rjmp	.+304    	; 0x55e0 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x1b8>
    54b0:	fc 01       	movw	r30, r24
    54b2:	57 83       	std	Z+7, r21	; 0x07
    54b4:	46 83       	std	Z+6, r20	; 0x06
    54b6:	fd 01       	movw	r30, r26
    54b8:	46 81       	ldd	r20, Z+6	; 0x06
    54ba:	57 81       	ldd	r21, Z+7	; 0x07
    54bc:	fa 01       	movw	r30, r20
    54be:	95 83       	std	Z+5, r25	; 0x05
    54c0:	84 83       	std	Z+4, r24	; 0x04
    54c2:	21 50       	subi	r18, 0x01	; 1
    54c4:	30 40       	sbci	r19, 0x00	; 0
    54c6:	30 93 c1 03 	sts	0x03C1, r19
    54ca:	20 93 c0 03 	sts	0x03C0, r18
    54ce:	80 91 c6 03 	lds	r24, 0x03C6
    54d2:	90 91 c7 03 	lds	r25, 0x03C7
    54d6:	8a 17       	cp	r24, r26
    54d8:	9b 07       	cpc	r25, r27
    54da:	09 f4       	brne	.+2      	; 0x54de <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0xb6>
    54dc:	59 c0       	rjmp	.+178    	; 0x5590 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x168>
    54de:	84 e0       	ldi	r24, 0x04	; 4
    54e0:	90 e0       	ldi	r25, 0x00	; 0
    54e2:	9d 83       	std	Y+5, r25	; 0x05
    54e4:	8c 83       	std	Y+4, r24	; 0x04
    54e6:	be 01       	movw	r22, r28
    54e8:	66 5f       	subi	r22, 0xF6	; 246
    54ea:	7f 4f       	sbci	r23, 0xFF	; 255
    54ec:	c8 01       	movw	r24, r16
    54ee:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    54f2:	1f 83       	std	Y+7, r17	; 0x07
    54f4:	0e 83       	std	Y+6, r16	; 0x06
    54f6:	80 91 b5 03 	lds	r24, 0x03B5
    54fa:	90 91 b6 03 	lds	r25, 0x03B6
    54fe:	a0 91 b7 03 	lds	r26, 0x03B7
    5502:	b0 91 b8 03 	lds	r27, 0x03B8
    5506:	80 91 b1 03 	lds	r24, 0x03B1
    550a:	90 91 b2 03 	lds	r25, 0x03B2
    550e:	a0 91 b3 03 	lds	r26, 0x03B3
    5512:	b0 91 b4 03 	lds	r27, 0x03B4
    5516:	80 93 b5 03 	sts	0x03B5, r24
    551a:	90 93 b6 03 	sts	0x03B6, r25
    551e:	a0 93 b7 03 	sts	0x03B7, r26
    5522:	b0 93 b8 03 	sts	0x03B8, r27
    5526:	e0 91 c6 03 	lds	r30, 0x03C6
    552a:	f0 91 c7 03 	lds	r31, 0x03C7
    552e:	01 90       	ld	r0, Z+
    5530:	f0 81       	ld	r31, Z
    5532:	e0 2d       	mov	r30, r0
    5534:	ce 17       	cp	r28, r30
    5536:	df 07       	cpc	r29, r31
    5538:	09 f4       	brne	.+2      	; 0x553c <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x114>
    553a:	6a c0       	rjmp	.+212    	; 0x5610 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x1e8>
    553c:	8c 81       	ldd	r24, Y+4	; 0x04
    553e:	9d 81       	ldd	r25, Y+5	; 0x05
    5540:	02 97       	sbiw	r24, 0x02	; 2
    5542:	59 f0       	breq	.+22     	; 0x555a <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x132>
    5544:	82 e0       	ldi	r24, 0x02	; 2
    5546:	90 e0       	ldi	r25, 0x00	; 0
    5548:	95 83       	std	Z+5, r25	; 0x05
    554a:	84 83       	std	Z+4, r24	; 0x04
    554c:	62 81       	ldd	r22, Z+2	; 0x02
    554e:	73 81       	ldd	r23, Z+3	; 0x03
    5550:	ce 01       	movw	r24, r28
    5552:	02 96       	adiw	r24, 0x02	; 2
    5554:	0e 94 35 31 	call	0x626a	; 0x626a <_ZN6System4AVR814switch_contextEPVPNS0_7ContextES2_>
    5558:	5b c0       	rjmp	.+182    	; 0x5610 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x1e8>
    555a:	81 e0       	ldi	r24, 0x01	; 1
    555c:	90 e0       	ldi	r25, 0x00	; 0
    555e:	9d 83       	std	Y+5, r25	; 0x05
    5560:	8c 83       	std	Y+4, r24	; 0x04
    5562:	f0 cf       	rjmp	.-32     	; 0x5544 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x11c>
    5564:	10 92 c3 03 	sts	0x03C3, r1
    5568:	10 92 c2 03 	sts	0x03C2, r1
    556c:	10 92 c5 03 	sts	0x03C5, r1
    5570:	10 92 c4 03 	sts	0x03C4, r1
    5574:	21 50       	subi	r18, 0x01	; 1
    5576:	30 40       	sbci	r19, 0x00	; 0
    5578:	30 93 c1 03 	sts	0x03C1, r19
    557c:	20 93 c0 03 	sts	0x03C0, r18
    5580:	80 91 c6 03 	lds	r24, 0x03C6
    5584:	90 91 c7 03 	lds	r25, 0x03C7
    5588:	8a 17       	cp	r24, r26
    558a:	9b 07       	cpc	r25, r27
    558c:	09 f0       	breq	.+2      	; 0x5590 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x168>
    558e:	a7 cf       	rjmp	.-178    	; 0x54de <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0xb6>
    5590:	80 91 c2 03 	lds	r24, 0x03C2
    5594:	90 91 c3 03 	lds	r25, 0x03C3
    5598:	90 93 c7 03 	sts	0x03C7, r25
    559c:	80 93 c6 03 	sts	0x03C6, r24
    55a0:	9e cf       	rjmp	.-196    	; 0x54de <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0xb6>
    55a2:	da 01       	movw	r26, r20
    55a4:	20 91 c0 03 	lds	r18, 0x03C0
    55a8:	30 91 c1 03 	lds	r19, 0x03C1
    55ac:	21 30       	cpi	r18, 0x01	; 1
    55ae:	31 05       	cpc	r19, r1
    55b0:	09 f0       	breq	.+2      	; 0x55b4 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x18c>
    55b2:	71 cf       	rjmp	.-286    	; 0x5496 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x6e>
    55b4:	d7 cf       	rjmp	.-82     	; 0x5564 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x13c>
    55b6:	21 15       	cp	r18, r1
    55b8:	31 05       	cpc	r19, r1
    55ba:	09 f4       	brne	.+2      	; 0x55be <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x196>
    55bc:	88 cf       	rjmp	.-240    	; 0x54ce <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0xa6>
    55be:	fa 01       	movw	r30, r20
    55c0:	86 81       	ldd	r24, Z+6	; 0x06
    55c2:	97 81       	ldd	r25, Z+7	; 0x07
    55c4:	90 93 c3 03 	sts	0x03C3, r25
    55c8:	80 93 c2 03 	sts	0x03C2, r24
    55cc:	fc 01       	movw	r30, r24
    55ce:	15 82       	std	Z+5, r1	; 0x05
    55d0:	14 82       	std	Z+4, r1	; 0x04
    55d2:	21 50       	subi	r18, 0x01	; 1
    55d4:	30 40       	sbci	r19, 0x00	; 0
    55d6:	30 93 c1 03 	sts	0x03C1, r19
    55da:	20 93 c0 03 	sts	0x03C0, r18
    55de:	77 cf       	rjmp	.-274    	; 0x54ce <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0xa6>
    55e0:	21 15       	cp	r18, r1
    55e2:	31 05       	cpc	r19, r1
    55e4:	09 f4       	brne	.+2      	; 0x55e8 <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0x1c0>
    55e6:	73 cf       	rjmp	.-282    	; 0x54ce <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0xa6>
    55e8:	e0 91 c4 03 	lds	r30, 0x03C4
    55ec:	f0 91 c5 03 	lds	r31, 0x03C5
    55f0:	04 80       	ldd	r0, Z+4	; 0x04
    55f2:	f5 81       	ldd	r31, Z+5	; 0x05
    55f4:	e0 2d       	mov	r30, r0
    55f6:	f0 93 c5 03 	sts	0x03C5, r31
    55fa:	e0 93 c4 03 	sts	0x03C4, r30
    55fe:	17 82       	std	Z+7, r1	; 0x07
    5600:	16 82       	std	Z+6, r1	; 0x06
    5602:	21 50       	subi	r18, 0x01	; 1
    5604:	30 40       	sbci	r19, 0x00	; 0
    5606:	30 93 c1 03 	sts	0x03C1, r19
    560a:	20 93 c0 03 	sts	0x03C0, r18
    560e:	5f cf       	rjmp	.-322    	; 0x54ce <_ZN6System6Thread5sleepEPNS_13Ordered_QueueIS0_NS_19Scheduling_Criteria8PriorityELb0ENS_13List_Elements21Doubly_Linked_OrderedIS0_S3_EEEE+0xa6>
    5610:	78 94       	sei
    5612:	78 94       	sei
    5614:	df 91       	pop	r29
    5616:	cf 91       	pop	r28
    5618:	1f 91       	pop	r17
    561a:	0f 91       	pop	r16
    561c:	08 95       	ret

0000561e <_ZN6System6Thread15time_rescheduleEv>:
    561e:	cf 93       	push	r28
    5620:	df 93       	push	r29
    5622:	f8 94       	cli
    5624:	e0 91 c6 03 	lds	r30, 0x03C6
    5628:	f0 91 c7 03 	lds	r31, 0x03C7
    562c:	c0 81       	ld	r28, Z
    562e:	d1 81       	ldd	r29, Z+1	; 0x01
    5630:	80 91 c6 03 	lds	r24, 0x03C6
    5634:	90 91 c7 03 	lds	r25, 0x03C7
    5638:	80 91 c0 03 	lds	r24, 0x03C0
    563c:	90 91 c1 03 	lds	r25, 0x03C1
    5640:	00 97       	sbiw	r24, 0x00	; 0
    5642:	99 f1       	breq	.+102    	; 0x56aa <_ZN6System6Thread15time_rescheduleEv+0x8c>
    5644:	40 91 c6 03 	lds	r20, 0x03C6
    5648:	50 91 c7 03 	lds	r21, 0x03C7
    564c:	81 30       	cpi	r24, 0x01	; 1
    564e:	91 05       	cpc	r25, r1
    5650:	09 f4       	brne	.+2      	; 0x5654 <_ZN6System6Thread15time_rescheduleEv+0x36>
    5652:	4a c0       	rjmp	.+148    	; 0x56e8 <_ZN6System6Thread15time_rescheduleEv+0xca>
    5654:	fa 01       	movw	r30, r20
    5656:	a4 81       	ldd	r26, Z+4	; 0x04
    5658:	b5 81       	ldd	r27, Z+5	; 0x05
    565a:	10 97       	sbiw	r26, 0x00	; 0
    565c:	09 f4       	brne	.+2      	; 0x5660 <_ZN6System6Thread15time_rescheduleEv+0x42>
    565e:	51 c0       	rjmp	.+162    	; 0x5702 <_ZN6System6Thread15time_rescheduleEv+0xe4>
    5660:	fa 01       	movw	r30, r20
    5662:	26 81       	ldd	r18, Z+6	; 0x06
    5664:	37 81       	ldd	r19, Z+7	; 0x07
    5666:	21 15       	cp	r18, r1
    5668:	31 05       	cpc	r19, r1
    566a:	09 f4       	brne	.+2      	; 0x566e <_ZN6System6Thread15time_rescheduleEv+0x50>
    566c:	5d c0       	rjmp	.+186    	; 0x5728 <_ZN6System6Thread15time_rescheduleEv+0x10a>
    566e:	fd 01       	movw	r30, r26
    5670:	37 83       	std	Z+7, r19	; 0x07
    5672:	26 83       	std	Z+6, r18	; 0x06
    5674:	fa 01       	movw	r30, r20
    5676:	26 81       	ldd	r18, Z+6	; 0x06
    5678:	37 81       	ldd	r19, Z+7	; 0x07
    567a:	f9 01       	movw	r30, r18
    567c:	b5 83       	std	Z+5, r27	; 0x05
    567e:	a4 83       	std	Z+4, r26	; 0x04
    5680:	01 97       	sbiw	r24, 0x01	; 1
    5682:	90 93 c1 03 	sts	0x03C1, r25
    5686:	80 93 c0 03 	sts	0x03C0, r24
    568a:	60 91 c6 03 	lds	r22, 0x03C6
    568e:	70 91 c7 03 	lds	r23, 0x03C7
    5692:	80 ec       	ldi	r24, 0xC0	; 192
    5694:	93 e0       	ldi	r25, 0x03	; 3
    5696:	0e 94 24 20 	call	0x4048	; 0x4048 <_ZN6System12Ordered_ListINS_6ThreadENS_19Scheduling_Criteria8PriorityENS_13List_Elements21Doubly_Linked_OrderedIS1_S3_EELb0EE6insertEPS6_>
    569a:	80 91 c2 03 	lds	r24, 0x03C2
    569e:	90 91 c3 03 	lds	r25, 0x03C3
    56a2:	90 93 c7 03 	sts	0x03C7, r25
    56a6:	80 93 c6 03 	sts	0x03C6, r24
    56aa:	e0 91 c6 03 	lds	r30, 0x03C6
    56ae:	f0 91 c7 03 	lds	r31, 0x03C7
    56b2:	01 90       	ld	r0, Z+
    56b4:	f0 81       	ld	r31, Z
    56b6:	e0 2d       	mov	r30, r0
    56b8:	ce 17       	cp	r28, r30
    56ba:	df 07       	cpc	r29, r31
    56bc:	09 f4       	brne	.+2      	; 0x56c0 <_ZN6System6Thread15time_rescheduleEv+0xa2>
    56be:	47 c0       	rjmp	.+142    	; 0x574e <_ZN6System6Thread15time_rescheduleEv+0x130>
    56c0:	8c 81       	ldd	r24, Y+4	; 0x04
    56c2:	9d 81       	ldd	r25, Y+5	; 0x05
    56c4:	02 97       	sbiw	r24, 0x02	; 2
    56c6:	59 f0       	breq	.+22     	; 0x56de <_ZN6System6Thread15time_rescheduleEv+0xc0>
    56c8:	82 e0       	ldi	r24, 0x02	; 2
    56ca:	90 e0       	ldi	r25, 0x00	; 0
    56cc:	95 83       	std	Z+5, r25	; 0x05
    56ce:	84 83       	std	Z+4, r24	; 0x04
    56d0:	62 81       	ldd	r22, Z+2	; 0x02
    56d2:	73 81       	ldd	r23, Z+3	; 0x03
    56d4:	ce 01       	movw	r24, r28
    56d6:	02 96       	adiw	r24, 0x02	; 2
    56d8:	0e 94 35 31 	call	0x626a	; 0x626a <_ZN6System4AVR814switch_contextEPVPNS0_7ContextES2_>
    56dc:	38 c0       	rjmp	.+112    	; 0x574e <_ZN6System6Thread15time_rescheduleEv+0x130>
    56de:	81 e0       	ldi	r24, 0x01	; 1
    56e0:	90 e0       	ldi	r25, 0x00	; 0
    56e2:	9d 83       	std	Y+5, r25	; 0x05
    56e4:	8c 83       	std	Y+4, r24	; 0x04
    56e6:	f0 cf       	rjmp	.-32     	; 0x56c8 <_ZN6System6Thread15time_rescheduleEv+0xaa>
    56e8:	10 92 c3 03 	sts	0x03C3, r1
    56ec:	10 92 c2 03 	sts	0x03C2, r1
    56f0:	10 92 c5 03 	sts	0x03C5, r1
    56f4:	10 92 c4 03 	sts	0x03C4, r1
    56f8:	10 92 c1 03 	sts	0x03C1, r1
    56fc:	10 92 c0 03 	sts	0x03C0, r1
    5700:	c4 cf       	rjmp	.-120    	; 0x568a <_ZN6System6Thread15time_rescheduleEv+0x6c>
    5702:	e0 91 c2 03 	lds	r30, 0x03C2
    5706:	f0 91 c3 03 	lds	r31, 0x03C3
    570a:	06 80       	ldd	r0, Z+6	; 0x06
    570c:	f7 81       	ldd	r31, Z+7	; 0x07
    570e:	e0 2d       	mov	r30, r0
    5710:	f0 93 c3 03 	sts	0x03C3, r31
    5714:	e0 93 c2 03 	sts	0x03C2, r30
    5718:	15 82       	std	Z+5, r1	; 0x05
    571a:	14 82       	std	Z+4, r1	; 0x04
    571c:	01 97       	sbiw	r24, 0x01	; 1
    571e:	90 93 c1 03 	sts	0x03C1, r25
    5722:	80 93 c0 03 	sts	0x03C0, r24
    5726:	b1 cf       	rjmp	.-158    	; 0x568a <_ZN6System6Thread15time_rescheduleEv+0x6c>
    5728:	e0 91 c4 03 	lds	r30, 0x03C4
    572c:	f0 91 c5 03 	lds	r31, 0x03C5
    5730:	04 80       	ldd	r0, Z+4	; 0x04
    5732:	f5 81       	ldd	r31, Z+5	; 0x05
    5734:	e0 2d       	mov	r30, r0
    5736:	f0 93 c5 03 	sts	0x03C5, r31
    573a:	e0 93 c4 03 	sts	0x03C4, r30
    573e:	17 82       	std	Z+7, r1	; 0x07
    5740:	16 82       	std	Z+6, r1	; 0x06
    5742:	01 97       	sbiw	r24, 0x01	; 1
    5744:	90 93 c1 03 	sts	0x03C1, r25
    5748:	80 93 c0 03 	sts	0x03C0, r24
    574c:	9e cf       	rjmp	.-196    	; 0x568a <_ZN6System6Thread15time_rescheduleEv+0x6c>
    574e:	78 94       	sei
    5750:	df 91       	pop	r29
    5752:	cf 91       	pop	r28
    5754:	08 95       	ret

00005756 <_ZN6System4CMAC11rx_preambleEv>:
    5756:	80 91 cf 03 	lds	r24, 0x03CF
    575a:	8a 3a       	cpi	r24, 0xAA	; 170
    575c:	39 f0       	breq	.+14     	; 0x576c <_ZN6System4CMAC11rx_preambleEv+0x16>
    575e:	80 91 cf 03 	lds	r24, 0x03CF
    5762:	85 35       	cpi	r24, 0x55	; 85
    5764:	19 f0       	breq	.+6      	; 0x576c <_ZN6System4CMAC11rx_preambleEv+0x16>
    5766:	80 e0       	ldi	r24, 0x00	; 0
    5768:	90 e0       	ldi	r25, 0x00	; 0
    576a:	08 95       	ret
    576c:	80 91 d3 03 	lds	r24, 0x03D3
    5770:	8f 5f       	subi	r24, 0xFF	; 255
    5772:	80 93 d3 03 	sts	0x03D3, r24
    5776:	80 91 d3 03 	lds	r24, 0x03D3
    577a:	83 30       	cpi	r24, 0x03	; 3
    577c:	a0 f3       	brcs	.-24     	; 0x5766 <_ZN6System4CMAC11rx_preambleEv+0x10>
    577e:	10 92 d2 03 	sts	0x03D2, r1
    5782:	80 91 d2 03 	lds	r24, 0x03D2
    5786:	80 93 d3 03 	sts	0x03D3, r24
    578a:	10 92 d0 03 	sts	0x03D0, r1
    578e:	81 e0       	ldi	r24, 0x01	; 1
    5790:	90 e0       	ldi	r25, 0x00	; 0
    5792:	08 95       	ret

00005794 <_ZN6System4CMAC7rx_dataEv>:
    5794:	80 91 cd 03 	lds	r24, 0x03CD
    5798:	80 93 ce 03 	sts	0x03CE, r24
    579c:	80 91 cf 03 	lds	r24, 0x03CF
    57a0:	80 93 cd 03 	sts	0x03CD, r24
    57a4:	e0 91 d5 03 	lds	r30, 0x03D5
    57a8:	f0 91 d6 03 	lds	r31, 0x03D6
    57ac:	80 91 cd 03 	lds	r24, 0x03CD
    57b0:	90 91 ce 03 	lds	r25, 0x03CE
    57b4:	20 91 d0 03 	lds	r18, 0x03D0
    57b8:	02 c0       	rjmp	.+4      	; 0x57be <_ZN6System4CMAC7rx_dataEv+0x2a>
    57ba:	96 95       	lsr	r25
    57bc:	87 95       	ror	r24
    57be:	2a 95       	dec	r18
    57c0:	e2 f7       	brpl	.-8      	; 0x57ba <_ZN6System4CMAC7rx_dataEv+0x26>
    57c2:	81 93       	st	Z+, r24
    57c4:	f0 93 d6 03 	sts	0x03D6, r31
    57c8:	e0 93 d5 03 	sts	0x03D5, r30
    57cc:	80 91 cc 03 	lds	r24, 0x03CC
    57d0:	8f 5f       	subi	r24, 0xFF	; 255
    57d2:	80 93 cc 03 	sts	0x03CC, r24
    57d6:	80 91 cc 03 	lds	r24, 0x03CC
    57da:	20 e0       	ldi	r18, 0x00	; 0
    57dc:	30 e0       	ldi	r19, 0x00	; 0
    57de:	88 32       	cpi	r24, 0x28	; 40
    57e0:	11 f4       	brne	.+4      	; 0x57e6 <_ZN6System4CMAC7rx_dataEv+0x52>
    57e2:	21 e0       	ldi	r18, 0x01	; 1
    57e4:	30 e0       	ldi	r19, 0x00	; 0
    57e6:	c9 01       	movw	r24, r18
    57e8:	08 95       	ret

000057ea <_ZN6System4CMAC7receiveEPhS1_Pvj>:
    57ea:	0f 93       	push	r16
    57ec:	1f 93       	push	r17
    57ee:	fb 01       	movw	r30, r22
    57f0:	da 01       	movw	r26, r20
    57f2:	80 91 d4 03 	lds	r24, 0x03D4
    57f6:	88 23       	and	r24, r24
    57f8:	19 f4       	brne	.+6      	; 0x5800 <_ZN6System4CMAC7receiveEPhS1_Pvj+0x16>
    57fa:	00 e0       	ldi	r16, 0x00	; 0
    57fc:	10 e0       	ldi	r17, 0x00	; 0
    57fe:	10 c0       	rjmp	.+32     	; 0x5820 <_ZN6System4CMAC7receiveEPhS1_Pvj+0x36>
    5800:	80 91 d7 03 	lds	r24, 0x03D7
    5804:	80 83       	st	Z, r24
    5806:	80 91 d9 03 	lds	r24, 0x03D9
    580a:	8c 93       	st	X, r24
    580c:	a8 01       	movw	r20, r16
    580e:	6d ed       	ldi	r22, 0xDD	; 221
    5810:	73 e0       	ldi	r23, 0x03	; 3
    5812:	c9 01       	movw	r24, r18
    5814:	0e 94 54 41 	call	0x82a8	; 0x82a8 <memcpy>
    5818:	f8 94       	cli
    581a:	10 92 d4 03 	sts	0x03D4, r1
    581e:	78 94       	sei
    5820:	c8 01       	movw	r24, r16
    5822:	1f 91       	pop	r17
    5824:	0f 91       	pop	r16
    5826:	08 95       	ret

00005828 <_ZN6System4CMAC4sendERKhS2_PKvj>:
    5828:	0f 93       	push	r16
    582a:	1f 93       	push	r17
    582c:	cf 93       	push	r28
    582e:	df 93       	push	r29
    5830:	fb 01       	movw	r30, r22
    5832:	da 01       	movw	r26, r20
    5834:	80 91 11 02 	lds	r24, 0x0211
    5838:	88 23       	and	r24, r24
    583a:	19 f4       	brne	.+6      	; 0x5842 <_ZN6System4CMAC4sendERKhS2_PKvj+0x1a>
    583c:	00 e0       	ldi	r16, 0x00	; 0
    583e:	10 e0       	ldi	r17, 0x00	; 0
    5840:	48 c0       	rjmp	.+144    	; 0x58d2 <_ZN6System4CMAC4sendERKhS2_PKvj+0xaa>
    5842:	10 92 11 02 	sts	0x0211, r1
    5846:	c5 e0       	ldi	r28, 0x05	; 5
    5848:	d4 e0       	ldi	r29, 0x04	; 4
    584a:	80 91 3b 04 	lds	r24, 0x043B
    584e:	80 93 05 04 	sts	0x0405, r24
    5852:	80 81       	ld	r24, Z
    5854:	80 93 06 04 	sts	0x0406, r24
    5858:	8c 91       	ld	r24, X
    585a:	80 93 07 04 	sts	0x0407, r24
    585e:	00 93 08 04 	sts	0x0408, r16
    5862:	40 2f       	mov	r20, r16
    5864:	55 27       	eor	r21, r21
    5866:	b9 01       	movw	r22, r18
    5868:	ce 01       	movw	r24, r28
    586a:	06 96       	adiw	r24, 0x06	; 6
    586c:	0e 94 54 41 	call	0x82a8	; 0x82a8 <memcpy>
    5870:	10 92 09 04 	sts	0x0409, r1
    5874:	10 92 0a 04 	sts	0x040A, r1
    5878:	fe 01       	movw	r30, r28
    587a:	46 e2       	ldi	r20, 0x26	; 38
    587c:	50 e0       	ldi	r21, 0x00	; 0
    587e:	20 e0       	ldi	r18, 0x00	; 0
    5880:	30 e0       	ldi	r19, 0x00	; 0
    5882:	41 50       	subi	r20, 0x01	; 1
    5884:	50 40       	sbci	r21, 0x00	; 0
    5886:	8f ef       	ldi	r24, 0xFF	; 255
    5888:	4f 3f       	cpi	r20, 0xFF	; 255
    588a:	58 07       	cpc	r21, r24
    588c:	c1 f0       	breq	.+48     	; 0x58be <_ZN6System4CMAC4sendERKhS2_PKvj+0x96>
    588e:	81 91       	ld	r24, Z+
    5890:	99 27       	eor	r25, r25
    5892:	87 fd       	sbrc	r24, 7
    5894:	90 95       	com	r25
    5896:	98 2f       	mov	r25, r24
    5898:	88 27       	eor	r24, r24
    589a:	28 27       	eor	r18, r24
    589c:	39 27       	eor	r19, r25
    589e:	88 e0       	ldi	r24, 0x08	; 8
    58a0:	90 e0       	ldi	r25, 0x00	; 0
    58a2:	61 e2       	ldi	r22, 0x21	; 33
    58a4:	70 e1       	ldi	r23, 0x10	; 16
    58a6:	04 c0       	rjmp	.+8      	; 0x58b0 <_ZN6System4CMAC4sendERKhS2_PKvj+0x88>
    58a8:	22 0f       	add	r18, r18
    58aa:	33 1f       	adc	r19, r19
    58ac:	01 97       	sbiw	r24, 0x01	; 1
    58ae:	49 f3       	breq	.-46     	; 0x5882 <_ZN6System4CMAC4sendERKhS2_PKvj+0x5a>
    58b0:	37 ff       	sbrs	r19, 7
    58b2:	fa cf       	rjmp	.-12     	; 0x58a8 <_ZN6System4CMAC4sendERKhS2_PKvj+0x80>
    58b4:	22 0f       	add	r18, r18
    58b6:	33 1f       	adc	r19, r19
    58b8:	26 27       	eor	r18, r22
    58ba:	37 27       	eor	r19, r23
    58bc:	f7 cf       	rjmp	.-18     	; 0x58ac <_ZN6System4CMAC4sendERKhS2_PKvj+0x84>
    58be:	3f a3       	std	Y+39, r19	; 0x27
    58c0:	2e a3       	std	Y+38, r18	; 0x26
    58c2:	d0 93 04 04 	sts	0x0404, r29
    58c6:	c0 93 03 04 	sts	0x0403, r28
    58ca:	10 92 01 04 	sts	0x0401, r1
    58ce:	10 92 02 04 	sts	0x0402, r1
    58d2:	c8 01       	movw	r24, r16
    58d4:	df 91       	pop	r29
    58d6:	cf 91       	pop	r28
    58d8:	1f 91       	pop	r17
    58da:	0f 91       	pop	r16
    58dc:	08 95       	ret

000058de <_ZN6System4CMAC7tx_dataEv>:
    58de:	80 91 01 04 	lds	r24, 0x0401
    58e2:	8f 5f       	subi	r24, 0xFF	; 255
    58e4:	80 93 01 04 	sts	0x0401, r24
    58e8:	81 50       	subi	r24, 0x01	; 1
    58ea:	88 32       	cpi	r24, 0x28	; 40
    58ec:	70 f4       	brcc	.+28     	; 0x590a <_ZN6System4CMAC7tx_dataEv+0x2c>
    58ee:	e0 91 03 04 	lds	r30, 0x0403
    58f2:	f0 91 04 04 	lds	r31, 0x0404
    58f6:	81 91       	ld	r24, Z+
    58f8:	f0 93 04 04 	sts	0x0404, r31
    58fc:	e0 93 03 04 	sts	0x0403, r30
    5900:	80 95       	com	r24
    5902:	8f b9       	out	0x0f, r24	; 15
    5904:	80 e0       	ldi	r24, 0x00	; 0
    5906:	90 e0       	ldi	r25, 0x00	; 0
    5908:	08 95       	ret
    590a:	8a ea       	ldi	r24, 0xAA	; 170
    590c:	8f b9       	out	0x0f, r24	; 15
    590e:	81 e0       	ldi	r24, 0x01	; 1
    5910:	90 e0       	ldi	r25, 0x00	; 0
    5912:	08 95       	ret

00005914 <_ZN6System4CMAC11tx_preambleEv>:
    5914:	80 91 02 04 	lds	r24, 0x0402
    5918:	8f 5f       	subi	r24, 0xFF	; 255
    591a:	80 93 02 04 	sts	0x0402, r24
    591e:	81 50       	subi	r24, 0x01	; 1
    5920:	80 35       	cpi	r24, 0x50	; 80
    5922:	28 f4       	brcc	.+10     	; 0x592e <_ZN6System4CMAC11tx_preambleEv+0x1a>
    5924:	8a ea       	ldi	r24, 0xAA	; 170
    5926:	8f b9       	out	0x0f, r24	; 15
    5928:	80 e0       	ldi	r24, 0x00	; 0
    592a:	90 e0       	ldi	r25, 0x00	; 0
    592c:	08 95       	ret
    592e:	80 91 02 04 	lds	r24, 0x0402
    5932:	8f 5f       	subi	r24, 0xFF	; 255
    5934:	80 93 02 04 	sts	0x0402, r24
    5938:	80 91 02 04 	lds	r24, 0x0402
    593c:	82 35       	cpi	r24, 0x52	; 82
    593e:	39 f0       	breq	.+14     	; 0x594e <_ZN6System4CMAC11tx_preambleEv+0x3a>
    5940:	8c ec       	ldi	r24, 0xCC	; 204
    5942:	8f b9       	out	0x0f, r24	; 15
    5944:	10 92 02 04 	sts	0x0402, r1
    5948:	81 e0       	ldi	r24, 0x01	; 1
    594a:	90 e0       	ldi	r25, 0x00	; 0
    594c:	08 95       	ret
    594e:	83 e3       	ldi	r24, 0x33	; 51
    5950:	8f b9       	out	0x0f, r24	; 15
    5952:	80 e0       	ldi	r24, 0x00	; 0
    5954:	90 e0       	ldi	r25, 0x00	; 0
    5956:	08 95       	ret

00005958 <_Z41__static_initialization_and_destruction_0ii>:
    5958:	6f 5f       	subi	r22, 0xFF	; 255
    595a:	7f 4f       	sbci	r23, 0xFF	; 255
    595c:	09 f0       	breq	.+2      	; 0x5960 <_Z41__static_initialization_and_destruction_0ii+0x8>
    595e:	08 95       	ret
    5960:	81 30       	cpi	r24, 0x01	; 1
    5962:	91 05       	cpc	r25, r1
    5964:	29 f0       	breq	.+10     	; 0x5970 <_Z41__static_initialization_and_destruction_0ii+0x18>
    5966:	89 2b       	or	r24, r25
    5968:	d1 f7       	brne	.-12     	; 0x595e <_Z41__static_initialization_and_destruction_0ii+0x6>
    596a:	17 b8       	out	0x07, r1	; 7
    596c:	16 b8       	out	0x06, r1	; 6
    596e:	08 95       	ret
    5970:	bb 9a       	sbi	0x17, 3	; 23
    5972:	80 e4       	ldi	r24, 0x40	; 64
    5974:	8d b9       	out	0x0d, r24	; 13
    5976:	10 92 3d 04 	sts	0x043D, r1
    597a:	87 e0       	ldi	r24, 0x07	; 7
    597c:	80 93 3e 04 	sts	0x043E, r24
    5980:	8f ef       	ldi	r24, 0xFF	; 255
    5982:	80 93 40 04 	sts	0x0440, r24
    5986:	10 92 32 04 	sts	0x0432, r1
    598a:	10 92 31 04 	sts	0x0431, r1
    598e:	10 92 34 04 	sts	0x0434, r1
    5992:	10 92 33 04 	sts	0x0433, r1
    5996:	10 92 36 04 	sts	0x0436, r1
    599a:	10 92 35 04 	sts	0x0435, r1
    599e:	10 92 38 04 	sts	0x0438, r1
    59a2:	10 92 37 04 	sts	0x0437, r1
    59a6:	10 92 3a 04 	sts	0x043A, r1
    59aa:	10 92 39 04 	sts	0x0439, r1
    59ae:	08 95       	ret

000059b0 <_GLOBAL__D__ZN6System4CMAC4sendERKhS2_PKvj>:
    59b0:	6f ef       	ldi	r22, 0xFF	; 255
    59b2:	7f ef       	ldi	r23, 0xFF	; 255
    59b4:	80 e0       	ldi	r24, 0x00	; 0
    59b6:	90 e0       	ldi	r25, 0x00	; 0
    59b8:	0e 94 ac 2c 	call	0x5958	; 0x5958 <_Z41__static_initialization_and_destruction_0ii>
    59bc:	08 95       	ret

000059be <_GLOBAL__I__ZN6System4CMAC4sendERKhS2_PKvj>:
    59be:	6f ef       	ldi	r22, 0xFF	; 255
    59c0:	7f ef       	ldi	r23, 0xFF	; 255
    59c2:	81 e0       	ldi	r24, 0x01	; 1
    59c4:	90 e0       	ldi	r25, 0x00	; 0
    59c6:	0e 94 ac 2c 	call	0x5958	; 0x5958 <_Z41__static_initialization_and_destruction_0ii>
    59ca:	08 95       	ret

000059cc <_ZN6System4CMAC9rx_giveupEv>:
    59cc:	6f 98       	cbi	0x0d, 7	; 13
    59ce:	60 e0       	ldi	r22, 0x00	; 0
    59d0:	8b e0       	ldi	r24, 0x0B	; 11
    59d2:	90 e0       	ldi	r25, 0x00	; 0
    59d4:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    59d8:	6f e3       	ldi	r22, 0x3F	; 63
    59da:	80 e0       	ldi	r24, 0x00	; 0
    59dc:	90 e0       	ldi	r25, 0x00	; 0
    59de:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    59e2:	10 92 00 04 	sts	0x0400, r1
    59e6:	10 92 ff 03 	sts	0x03FF, r1
    59ea:	87 b7       	in	r24, 0x37	; 55
    59ec:	80 68       	ori	r24, 0x80	; 128
    59ee:	87 bf       	out	0x37, r24	; 55
    59f0:	08 95       	ret

000059f2 <_ZN6System4CMAC9rx_handleEv>:
    59f2:	0e 94 e6 2c 	call	0x59cc	; 0x59cc <_ZN6System4CMAC9rx_giveupEv>
    59f6:	e7 ed       	ldi	r30, 0xD7	; 215
    59f8:	f3 e0       	ldi	r31, 0x03	; 3
    59fa:	46 e2       	ldi	r20, 0x26	; 38
    59fc:	50 e0       	ldi	r21, 0x00	; 0
    59fe:	20 e0       	ldi	r18, 0x00	; 0
    5a00:	30 e0       	ldi	r19, 0x00	; 0
    5a02:	41 50       	subi	r20, 0x01	; 1
    5a04:	50 40       	sbci	r21, 0x00	; 0
    5a06:	8f ef       	ldi	r24, 0xFF	; 255
    5a08:	4f 3f       	cpi	r20, 0xFF	; 255
    5a0a:	58 07       	cpc	r21, r24
    5a0c:	c1 f0       	breq	.+48     	; 0x5a3e <_ZN6System4CMAC9rx_handleEv+0x4c>
    5a0e:	81 91       	ld	r24, Z+
    5a10:	99 27       	eor	r25, r25
    5a12:	87 fd       	sbrc	r24, 7
    5a14:	90 95       	com	r25
    5a16:	98 2f       	mov	r25, r24
    5a18:	88 27       	eor	r24, r24
    5a1a:	28 27       	eor	r18, r24
    5a1c:	39 27       	eor	r19, r25
    5a1e:	88 e0       	ldi	r24, 0x08	; 8
    5a20:	90 e0       	ldi	r25, 0x00	; 0
    5a22:	61 e2       	ldi	r22, 0x21	; 33
    5a24:	70 e1       	ldi	r23, 0x10	; 16
    5a26:	04 c0       	rjmp	.+8      	; 0x5a30 <_ZN6System4CMAC9rx_handleEv+0x3e>
    5a28:	22 0f       	add	r18, r18
    5a2a:	33 1f       	adc	r19, r19
    5a2c:	01 97       	sbiw	r24, 0x01	; 1
    5a2e:	49 f3       	breq	.-46     	; 0x5a02 <_ZN6System4CMAC9rx_handleEv+0x10>
    5a30:	37 ff       	sbrs	r19, 7
    5a32:	fa cf       	rjmp	.-12     	; 0x5a28 <_ZN6System4CMAC9rx_handleEv+0x36>
    5a34:	22 0f       	add	r18, r18
    5a36:	33 1f       	adc	r19, r19
    5a38:	26 27       	eor	r18, r22
    5a3a:	37 27       	eor	r19, r23
    5a3c:	f7 cf       	rjmp	.-18     	; 0x5a2c <_ZN6System4CMAC9rx_handleEv+0x3a>
    5a3e:	80 91 35 04 	lds	r24, 0x0435
    5a42:	90 91 36 04 	lds	r25, 0x0436
    5a46:	88 96       	adiw	r24, 0x28	; 40
    5a48:	90 93 36 04 	sts	0x0436, r25
    5a4c:	80 93 35 04 	sts	0x0435, r24
    5a50:	80 91 31 04 	lds	r24, 0x0431
    5a54:	90 91 32 04 	lds	r25, 0x0432
    5a58:	01 96       	adiw	r24, 0x01	; 1
    5a5a:	90 93 32 04 	sts	0x0432, r25
    5a5e:	80 93 31 04 	sts	0x0431, r24
    5a62:	80 91 fd 03 	lds	r24, 0x03FD
    5a66:	90 91 fe 03 	lds	r25, 0x03FE
    5a6a:	28 17       	cp	r18, r24
    5a6c:	39 07       	cpc	r19, r25
    5a6e:	61 f0       	breq	.+24     	; 0x5a88 <_ZN6System4CMAC9rx_handleEv+0x96>
    5a70:	80 91 39 04 	lds	r24, 0x0439
    5a74:	90 91 3a 04 	lds	r25, 0x043A
    5a78:	01 96       	adiw	r24, 0x01	; 1
    5a7a:	90 93 3a 04 	sts	0x043A, r25
    5a7e:	80 93 39 04 	sts	0x0439, r24
    5a82:	80 e0       	ldi	r24, 0x00	; 0
    5a84:	90 e0       	ldi	r25, 0x00	; 0
    5a86:	08 95       	ret
    5a88:	f8 94       	cli
    5a8a:	81 e0       	ldi	r24, 0x01	; 1
    5a8c:	80 93 d4 03 	sts	0x03D4, r24
    5a90:	78 94       	sei
    5a92:	80 91 da 03 	lds	r24, 0x03DA
    5a96:	99 27       	eor	r25, r25
    5a98:	08 95       	ret

00005a9a <_ZN6System4CMAC7rx_syncEv>:
    5a9a:	80 91 cf 03 	lds	r24, 0x03CF
    5a9e:	8a 3a       	cpi	r24, 0xAA	; 170
    5aa0:	d9 f1       	breq	.+118    	; 0x5b18 <_ZN6System4CMAC7rx_syncEv+0x7e>
    5aa2:	80 91 cf 03 	lds	r24, 0x03CF
    5aa6:	85 35       	cpi	r24, 0x55	; 85
    5aa8:	b9 f1       	breq	.+110    	; 0x5b18 <_ZN6System4CMAC7rx_syncEv+0x7e>
    5aaa:	80 91 d2 03 	lds	r24, 0x03D2
    5aae:	88 23       	and	r24, r24
    5ab0:	61 f4       	brne	.+24     	; 0x5aca <_ZN6System4CMAC7rx_syncEv+0x30>
    5ab2:	80 91 cf 03 	lds	r24, 0x03CF
    5ab6:	80 93 cd 03 	sts	0x03CD, r24
    5aba:	80 91 d2 03 	lds	r24, 0x03D2
    5abe:	8f 5f       	subi	r24, 0xFF	; 255
    5ac0:	80 93 d2 03 	sts	0x03D2, r24
    5ac4:	80 e0       	ldi	r24, 0x00	; 0
    5ac6:	90 e0       	ldi	r25, 0x00	; 0
    5ac8:	08 95       	ret
    5aca:	83 30       	cpi	r24, 0x03	; 3
    5acc:	60 f5       	brcc	.+88     	; 0x5b26 <_ZN6System4CMAC7rx_syncEv+0x8c>
    5ace:	20 91 cd 03 	lds	r18, 0x03CD
    5ad2:	30 91 ce 03 	lds	r19, 0x03CE
    5ad6:	80 91 cd 03 	lds	r24, 0x03CD
    5ada:	80 93 ce 03 	sts	0x03CE, r24
    5ade:	80 91 cf 03 	lds	r24, 0x03CF
    5ae2:	80 93 cd 03 	sts	0x03CD, r24
    5ae6:	40 e0       	ldi	r20, 0x00	; 0
    5ae8:	50 e0       	ldi	r21, 0x00	; 0
    5aea:	0e c0       	rjmp	.+28     	; 0x5b08 <_ZN6System4CMAC7rx_syncEv+0x6e>
    5aec:	80 91 cf 03 	lds	r24, 0x03CF
    5af0:	88 0f       	add	r24, r24
    5af2:	80 93 cf 03 	sts	0x03CF, r24
    5af6:	8c ec       	ldi	r24, 0xCC	; 204
    5af8:	23 33       	cpi	r18, 0x33	; 51
    5afa:	38 07       	cpc	r19, r24
    5afc:	01 f1       	breq	.+64     	; 0x5b3e <_ZN6System4CMAC7rx_syncEv+0xa4>
    5afe:	4f 5f       	subi	r20, 0xFF	; 255
    5b00:	5f 4f       	sbci	r21, 0xFF	; 255
    5b02:	48 30       	cpi	r20, 0x08	; 8
    5b04:	51 05       	cpc	r21, r1
    5b06:	c9 f2       	breq	.-78     	; 0x5aba <_ZN6System4CMAC7rx_syncEv+0x20>
    5b08:	22 0f       	add	r18, r18
    5b0a:	33 1f       	adc	r19, r19
    5b0c:	80 91 cf 03 	lds	r24, 0x03CF
    5b10:	87 ff       	sbrs	r24, 7
    5b12:	ec cf       	rjmp	.-40     	; 0x5aec <_ZN6System4CMAC7rx_syncEv+0x52>
    5b14:	21 60       	ori	r18, 0x01	; 1
    5b16:	ea cf       	rjmp	.-44     	; 0x5aec <_ZN6System4CMAC7rx_syncEv+0x52>
    5b18:	80 91 cf 03 	lds	r24, 0x03CF
    5b1c:	80 93 ce 03 	sts	0x03CE, r24
    5b20:	80 e0       	ldi	r24, 0x00	; 0
    5b22:	90 e0       	ldi	r25, 0x00	; 0
    5b24:	08 95       	ret
    5b26:	10 92 d3 03 	sts	0x03D3, r1
    5b2a:	0e 94 e6 2c 	call	0x59cc	; 0x59cc <_ZN6System4CMAC9rx_giveupEv>
    5b2e:	80 91 d2 03 	lds	r24, 0x03D2
    5b32:	8f 5f       	subi	r24, 0xFF	; 255
    5b34:	80 93 d2 03 	sts	0x03D2, r24
    5b38:	80 e0       	ldi	r24, 0x00	; 0
    5b3a:	90 e0       	ldi	r25, 0x00	; 0
    5b3c:	08 95       	ret
    5b3e:	87 e0       	ldi	r24, 0x07	; 7
    5b40:	84 1b       	sub	r24, r20
    5b42:	80 93 d0 03 	sts	0x03D0, r24
    5b46:	81 e0       	ldi	r24, 0x01	; 1
    5b48:	90 e0       	ldi	r25, 0x00	; 0
    5b4a:	08 95       	ret

00005b4c <_ZN6System4CMAC16rx_state_machineEv>:
    5b4c:	8f b1       	in	r24, 0x0f	; 15
    5b4e:	99 27       	eor	r25, r25
    5b50:	80 93 cf 03 	sts	0x03CF, r24
    5b54:	80 91 ff 03 	lds	r24, 0x03FF
    5b58:	90 91 00 04 	lds	r25, 0x0400
    5b5c:	81 30       	cpi	r24, 0x01	; 1
    5b5e:	91 05       	cpc	r25, r1
    5b60:	89 f0       	breq	.+34     	; 0x5b84 <_ZN6System4CMAC16rx_state_machineEv+0x38>
    5b62:	82 30       	cpi	r24, 0x02	; 2
    5b64:	91 05       	cpc	r25, r1
    5b66:	c9 f0       	breq	.+50     	; 0x5b9a <_ZN6System4CMAC16rx_state_machineEv+0x4e>
    5b68:	89 2b       	or	r24, r25
    5b6a:	09 f0       	breq	.+2      	; 0x5b6e <_ZN6System4CMAC16rx_state_machineEv+0x22>
    5b6c:	08 95       	ret
    5b6e:	0e 94 ab 2b 	call	0x5756	; 0x5756 <_ZN6System4CMAC11rx_preambleEv>
    5b72:	88 23       	and	r24, r24
    5b74:	d9 f3       	breq	.-10     	; 0x5b6c <_ZN6System4CMAC16rx_state_machineEv+0x20>
    5b76:	81 e0       	ldi	r24, 0x01	; 1
    5b78:	90 e0       	ldi	r25, 0x00	; 0
    5b7a:	90 93 00 04 	sts	0x0400, r25
    5b7e:	80 93 ff 03 	sts	0x03FF, r24
    5b82:	08 95       	ret
    5b84:	0e 94 4d 2d 	call	0x5a9a	; 0x5a9a <_ZN6System4CMAC7rx_syncEv>
    5b88:	88 23       	and	r24, r24
    5b8a:	81 f3       	breq	.-32     	; 0x5b6c <_ZN6System4CMAC16rx_state_machineEv+0x20>
    5b8c:	82 e0       	ldi	r24, 0x02	; 2
    5b8e:	90 e0       	ldi	r25, 0x00	; 0
    5b90:	90 93 00 04 	sts	0x0400, r25
    5b94:	80 93 ff 03 	sts	0x03FF, r24
    5b98:	08 95       	ret
    5b9a:	0e 94 ca 2b 	call	0x5794	; 0x5794 <_ZN6System4CMAC7rx_dataEv>
    5b9e:	88 23       	and	r24, r24
    5ba0:	29 f3       	breq	.-54     	; 0x5b6c <_ZN6System4CMAC16rx_state_machineEv+0x20>
    5ba2:	83 e0       	ldi	r24, 0x03	; 3
    5ba4:	90 e0       	ldi	r25, 0x00	; 0
    5ba6:	90 93 00 04 	sts	0x0400, r25
    5baa:	80 93 ff 03 	sts	0x03FF, r24
    5bae:	0e 94 f9 2c 	call	0x59f2	; 0x59f2 <_ZN6System4CMAC9rx_handleEv>
    5bb2:	08 95       	ret

00005bb4 <_ZN6System4CMAC13timer_handlerEj>:
    5bb4:	80 91 11 02 	lds	r24, 0x0211
    5bb8:	88 23       	and	r24, r24
    5bba:	09 f4       	brne	.+2      	; 0x5bbe <_ZN6System4CMAC13timer_handlerEj+0xa>
    5bbc:	45 c0       	rjmp	.+138    	; 0x5c48 <_ZN6System4CMAC13timer_handlerEj+0x94>
    5bbe:	80 91 d4 03 	lds	r24, 0x03D4
    5bc2:	81 11       	cpse	r24, r1
    5bc4:	08 95       	ret
    5bc6:	6b e3       	ldi	r22, 0x3B	; 59
    5bc8:	80 e0       	ldi	r24, 0x00	; 0
    5bca:	90 e0       	ldi	r25, 0x00	; 0
    5bcc:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5bd0:	87 b7       	in	r24, 0x37	; 55
    5bd2:	8f 77       	andi	r24, 0x7F	; 127
    5bd4:	87 bf       	out	0x37, r24	; 55
    5bd6:	61 e1       	ldi	r22, 0x11	; 17
    5bd8:	80 e0       	ldi	r24, 0x00	; 0
    5bda:	90 e0       	ldi	r25, 0x00	; 0
    5bdc:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5be0:	80 91 89 04 	lds	r24, 0x0489
    5be4:	90 91 8a 04 	lds	r25, 0x048A
    5be8:	88 0f       	add	r24, r24
    5bea:	99 1f       	adc	r25, r25
    5bec:	fc 01       	movw	r30, r24
    5bee:	ee 0f       	add	r30, r30
    5bf0:	ff 1f       	adc	r31, r31
    5bf2:	ee 0f       	add	r30, r30
    5bf4:	ff 1f       	adc	r31, r31
    5bf6:	ee 0f       	add	r30, r30
    5bf8:	ff 1f       	adc	r31, r31
    5bfa:	e8 0f       	add	r30, r24
    5bfc:	f9 1f       	adc	r31, r25
    5bfe:	ee 5e       	subi	r30, 0xEE	; 238
    5c00:	fd 4f       	sbci	r31, 0xFD	; 253
    5c02:	60 85       	ldd	r22, Z+8	; 0x08
    5c04:	77 27       	eor	r23, r23
    5c06:	89 e0       	ldi	r24, 0x09	; 9
    5c08:	90 e0       	ldi	r25, 0x00	; 0
    5c0a:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5c0e:	60 e0       	ldi	r22, 0x00	; 0
    5c10:	8b e0       	ldi	r24, 0x0B	; 11
    5c12:	90 e0       	ldi	r25, 0x00	; 0
    5c14:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5c18:	87 ed       	ldi	r24, 0xD7	; 215
    5c1a:	93 e0       	ldi	r25, 0x03	; 3
    5c1c:	90 93 d6 03 	sts	0x03D6, r25
    5c20:	80 93 d5 03 	sts	0x03D5, r24
    5c24:	10 92 cc 03 	sts	0x03CC, r1
    5c28:	10 92 d2 03 	sts	0x03D2, r1
    5c2c:	10 92 d1 03 	sts	0x03D1, r1
    5c30:	81 e0       	ldi	r24, 0x01	; 1
    5c32:	90 e0       	ldi	r25, 0x00	; 0
    5c34:	90 93 30 04 	sts	0x0430, r25
    5c38:	80 93 2f 04 	sts	0x042F, r24
    5c3c:	10 92 00 04 	sts	0x0400, r1
    5c40:	10 92 ff 03 	sts	0x03FF, r1
    5c44:	6f 9a       	sbi	0x0d, 7	; 13
    5c46:	08 95       	ret
    5c48:	6b e3       	ldi	r22, 0x3B	; 59
    5c4a:	80 e0       	ldi	r24, 0x00	; 0
    5c4c:	90 e0       	ldi	r25, 0x00	; 0
    5c4e:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5c52:	87 b7       	in	r24, 0x37	; 55
    5c54:	8f 77       	andi	r24, 0x7F	; 127
    5c56:	87 bf       	out	0x37, r24	; 55
    5c58:	82 e0       	ldi	r24, 0x02	; 2
    5c5a:	90 e0       	ldi	r25, 0x00	; 0
    5c5c:	90 93 30 04 	sts	0x0430, r25
    5c60:	80 93 2f 04 	sts	0x042F, r24
    5c64:	81 e0       	ldi	r24, 0x01	; 1
    5c66:	90 e0       	ldi	r25, 0x00	; 0
    5c68:	90 93 2e 04 	sts	0x042E, r25
    5c6c:	80 93 2d 04 	sts	0x042D, r24
    5c70:	61 ee       	ldi	r22, 0xE1	; 225
    5c72:	80 e0       	ldi	r24, 0x00	; 0
    5c74:	90 e0       	ldi	r25, 0x00	; 0
    5c76:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5c7a:	80 91 89 04 	lds	r24, 0x0489
    5c7e:	90 91 8a 04 	lds	r25, 0x048A
    5c82:	88 0f       	add	r24, r24
    5c84:	99 1f       	adc	r25, r25
    5c86:	fc 01       	movw	r30, r24
    5c88:	ee 0f       	add	r30, r30
    5c8a:	ff 1f       	adc	r31, r31
    5c8c:	ee 0f       	add	r30, r30
    5c8e:	ff 1f       	adc	r31, r31
    5c90:	ee 0f       	add	r30, r30
    5c92:	ff 1f       	adc	r31, r31
    5c94:	e8 0f       	add	r30, r24
    5c96:	f9 1f       	adc	r31, r25
    5c98:	ee 5e       	subi	r30, 0xEE	; 238
    5c9a:	fd 4f       	sbci	r31, 0xFD	; 253
    5c9c:	61 85       	ldd	r22, Z+9	; 0x09
    5c9e:	77 27       	eor	r23, r23
    5ca0:	89 e0       	ldi	r24, 0x09	; 9
    5ca2:	90 e0       	ldi	r25, 0x00	; 0
    5ca4:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5ca8:	e0 91 87 04 	lds	r30, 0x0487
    5cac:	f0 91 88 04 	lds	r31, 0x0488
    5cb0:	ec 5d       	subi	r30, 0xDC	; 220
    5cb2:	fd 4f       	sbci	r31, 0xFD	; 253
    5cb4:	60 81       	ld	r22, Z
    5cb6:	77 27       	eor	r23, r23
    5cb8:	8b e0       	ldi	r24, 0x0B	; 11
    5cba:	90 e0       	ldi	r25, 0x00	; 0
    5cbc:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5cc0:	6f 9a       	sbi	0x0d, 7	; 13
    5cc2:	08 95       	ret

00005cc4 <_ZN6System4CMAC9tx_handleEv>:
    5cc4:	6f 98       	cbi	0x0d, 7	; 13
    5cc6:	60 e0       	ldi	r22, 0x00	; 0
    5cc8:	8b e0       	ldi	r24, 0x0B	; 11
    5cca:	90 e0       	ldi	r25, 0x00	; 0
    5ccc:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5cd0:	6f e3       	ldi	r22, 0x3F	; 63
    5cd2:	80 e0       	ldi	r24, 0x00	; 0
    5cd4:	90 e0       	ldi	r25, 0x00	; 0
    5cd6:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5cda:	80 91 37 04 	lds	r24, 0x0437
    5cde:	90 91 38 04 	lds	r25, 0x0438
    5ce2:	88 96       	adiw	r24, 0x28	; 40
    5ce4:	90 93 38 04 	sts	0x0438, r25
    5ce8:	80 93 37 04 	sts	0x0437, r24
    5cec:	80 91 33 04 	lds	r24, 0x0433
    5cf0:	90 91 34 04 	lds	r25, 0x0434
    5cf4:	01 96       	adiw	r24, 0x01	; 1
    5cf6:	90 93 34 04 	sts	0x0434, r25
    5cfa:	80 93 33 04 	sts	0x0433, r24
    5cfe:	81 e0       	ldi	r24, 0x01	; 1
    5d00:	80 93 11 02 	sts	0x0211, r24
    5d04:	87 b7       	in	r24, 0x37	; 55
    5d06:	80 68       	ori	r24, 0x80	; 128
    5d08:	87 bf       	out	0x37, r24	; 55
    5d0a:	08 95       	ret

00005d0c <_ZN6System4CMAC16tx_state_machineEv>:
    5d0c:	80 91 2d 04 	lds	r24, 0x042D
    5d10:	90 91 2e 04 	lds	r25, 0x042E
    5d14:	82 30       	cpi	r24, 0x02	; 2
    5d16:	91 05       	cpc	r25, r1
    5d18:	31 f0       	breq	.+12     	; 0x5d26 <_ZN6System4CMAC16tx_state_machineEv+0x1a>
    5d1a:	83 30       	cpi	r24, 0x03	; 3
    5d1c:	91 05       	cpc	r25, r1
    5d1e:	71 f0       	breq	.+28     	; 0x5d3c <_ZN6System4CMAC16tx_state_machineEv+0x30>
    5d20:	01 97       	sbiw	r24, 0x01	; 1
    5d22:	c9 f0       	breq	.+50     	; 0x5d56 <_ZN6System4CMAC16tx_state_machineEv+0x4a>
    5d24:	08 95       	ret
    5d26:	0e 94 8a 2c 	call	0x5914	; 0x5914 <_ZN6System4CMAC11tx_preambleEv>
    5d2a:	88 23       	and	r24, r24
    5d2c:	d9 f3       	breq	.-10     	; 0x5d24 <_ZN6System4CMAC16tx_state_machineEv+0x18>
    5d2e:	83 e0       	ldi	r24, 0x03	; 3
    5d30:	90 e0       	ldi	r25, 0x00	; 0
    5d32:	90 93 2e 04 	sts	0x042E, r25
    5d36:	80 93 2d 04 	sts	0x042D, r24
    5d3a:	08 95       	ret
    5d3c:	0e 94 6f 2c 	call	0x58de	; 0x58de <_ZN6System4CMAC7tx_dataEv>
    5d40:	88 23       	and	r24, r24
    5d42:	81 f3       	breq	.-32     	; 0x5d24 <_ZN6System4CMAC16tx_state_machineEv+0x18>
    5d44:	84 e0       	ldi	r24, 0x04	; 4
    5d46:	90 e0       	ldi	r25, 0x00	; 0
    5d48:	90 93 2e 04 	sts	0x042E, r25
    5d4c:	80 93 2d 04 	sts	0x042D, r24
    5d50:	0e 94 62 2e 	call	0x5cc4	; 0x5cc4 <_ZN6System4CMAC9tx_handleEv>
    5d54:	08 95       	ret
    5d56:	8f ef       	ldi	r24, 0xFF	; 255
    5d58:	90 e0       	ldi	r25, 0x00	; 0
    5d5a:	01 97       	sbiw	r24, 0x01	; 1
    5d5c:	f1 f7       	brne	.-4      	; 0x5d5a <_ZN6System4CMAC16tx_state_machineEv+0x4e>
    5d5e:	82 e0       	ldi	r24, 0x02	; 2
    5d60:	90 e0       	ldi	r25, 0x00	; 0
    5d62:	90 93 2e 04 	sts	0x042E, r25
    5d66:	80 93 2d 04 	sts	0x042D, r24
    5d6a:	08 95       	ret

00005d6c <_ZN6System4CMAC11spi_handlerEj>:
    5d6c:	80 91 2f 04 	lds	r24, 0x042F
    5d70:	90 91 30 04 	lds	r25, 0x0430
    5d74:	81 30       	cpi	r24, 0x01	; 1
    5d76:	91 05       	cpc	r25, r1
    5d78:	31 f0       	breq	.+12     	; 0x5d86 <_ZN6System4CMAC11spi_handlerEj+0x1a>
    5d7a:	02 97       	sbiw	r24, 0x02	; 2
    5d7c:	09 f0       	breq	.+2      	; 0x5d80 <_ZN6System4CMAC11spi_handlerEj+0x14>
    5d7e:	08 95       	ret
    5d80:	0e 94 86 2e 	call	0x5d0c	; 0x5d0c <_ZN6System4CMAC16tx_state_machineEv>
    5d84:	08 95       	ret
    5d86:	0e 94 a6 2d 	call	0x5b4c	; 0x5b4c <_ZN6System4CMAC16rx_state_machineEv>
    5d8a:	08 95       	ret

00005d8c <__epos_call_handler>:
    5d8c:	e8 2f       	mov	r30, r24
    5d8e:	ff 27       	eor	r31, r31
    5d90:	e7 fd       	sbrc	r30, 7
    5d92:	f0 95       	com	r31
    5d94:	e3 32       	cpi	r30, 0x23	; 35
    5d96:	f1 05       	cpc	r31, r1
    5d98:	60 f4       	brcc	.+24     	; 0x5db2 <__epos_call_handler+0x26>
    5d9a:	ee 0f       	add	r30, r30
    5d9c:	ff 1f       	adc	r31, r31
    5d9e:	ef 5b       	subi	r30, 0xBF	; 191
    5da0:	fb 4f       	sbci	r31, 0xFB	; 251
    5da2:	01 90       	ld	r0, Z+
    5da4:	f0 81       	ld	r31, Z
    5da6:	e0 2d       	mov	r30, r0
    5da8:	30 97       	sbiw	r30, 0x00	; 0
    5daa:	19 f0       	breq	.+6      	; 0x5db2 <__epos_call_handler+0x26>
    5dac:	80 e0       	ldi	r24, 0x00	; 0
    5dae:	90 e0       	ldi	r25, 0x00	; 0
    5db0:	09 95       	icall
    5db2:	08 95       	ret

00005db4 <__epos_free_init_mem>:
    5db4:	cf 93       	push	r28
    5db6:	df 93       	push	r29
    5db8:	cd b7       	in	r28, 0x3d	; 61
    5dba:	de b7       	in	r29, 0x3e	; 62
    5dbc:	22 97       	sbiw	r28, 0x02	; 2
    5dbe:	0f b6       	in	r0, 0x3f	; 63
    5dc0:	f8 94       	cli
    5dc2:	de bf       	out	0x3e, r29	; 62
    5dc4:	0f be       	out	0x3f, r0	; 63
    5dc6:	cd bf       	out	0x3d, r28	; 61
    5dc8:	80 e0       	ldi	r24, 0x00	; 0
    5dca:	90 e1       	ldi	r25, 0x10	; 16
    5dcc:	9a 83       	std	Y+2, r25	; 0x02
    5dce:	89 83       	std	Y+1, r24	; 0x01
    5dd0:	60 e0       	ldi	r22, 0x00	; 0
    5dd2:	71 e0       	ldi	r23, 0x01	; 1
    5dd4:	ce 01       	movw	r24, r28
    5dd6:	01 96       	adiw	r24, 0x01	; 1
    5dd8:	0e 94 f2 31 	call	0x63e4	; 0x63e4 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi>
    5ddc:	22 96       	adiw	r28, 0x02	; 2
    5dde:	0f b6       	in	r0, 0x3f	; 63
    5de0:	f8 94       	cli
    5de2:	de bf       	out	0x3e, r29	; 62
    5de4:	0f be       	out	0x3f, r0	; 63
    5de6:	cd bf       	out	0x3d, r28	; 61
    5de8:	df 91       	pop	r29
    5dea:	cf 91       	pop	r28
    5dec:	08 95       	ret

00005dee <_ZN6System16CC1000_Registers12receive_byteEv>:
    5dee:	8f 98       	cbi	0x11, 7	; 17
    5df0:	80 e0       	ldi	r24, 0x00	; 0
    5df2:	28 e0       	ldi	r18, 0x08	; 8
    5df4:	30 e0       	ldi	r19, 0x00	; 0
    5df6:	05 c0       	rjmp	.+10     	; 0x5e02 <_ZN6System16CC1000_Registers12receive_byteEv+0x14>
    5df8:	88 0f       	add	r24, r24
    5dfa:	96 9a       	sbi	0x12, 6	; 18
    5dfc:	21 50       	subi	r18, 0x01	; 1
    5dfe:	30 40       	sbci	r19, 0x00	; 0
    5e00:	49 f0       	breq	.+18     	; 0x5e14 <_ZN6System16CC1000_Registers12receive_byteEv+0x26>
    5e02:	96 98       	cbi	0x12, 6	; 18
    5e04:	87 9b       	sbis	0x10, 7	; 16
    5e06:	f8 cf       	rjmp	.-16     	; 0x5df8 <_ZN6System16CC1000_Registers12receive_byteEv+0xa>
    5e08:	88 0f       	add	r24, r24
    5e0a:	81 60       	ori	r24, 0x01	; 1
    5e0c:	96 9a       	sbi	0x12, 6	; 18
    5e0e:	21 50       	subi	r18, 0x01	; 1
    5e10:	30 40       	sbci	r19, 0x00	; 0
    5e12:	b9 f7       	brne	.-18     	; 0x5e02 <_ZN6System16CC1000_Registers12receive_byteEv+0x14>
    5e14:	99 27       	eor	r25, r25
    5e16:	08 95       	ret

00005e18 <_ZN6System16CC1000_Registers9send_byteEh>:
    5e18:	28 e0       	ldi	r18, 0x08	; 8
    5e1a:	30 e0       	ldi	r19, 0x00	; 0
    5e1c:	02 c0       	rjmp	.+4      	; 0x5e22 <_ZN6System16CC1000_Registers9send_byteEh+0xa>
    5e1e:	97 98       	cbi	0x12, 7	; 18
    5e20:	03 c0       	rjmp	.+6      	; 0x5e28 <_ZN6System16CC1000_Registers9send_byteEh+0x10>
    5e22:	87 ff       	sbrs	r24, 7
    5e24:	fc cf       	rjmp	.-8      	; 0x5e1e <_ZN6System16CC1000_Registers9send_byteEh+0x6>
    5e26:	97 9a       	sbi	0x12, 7	; 18
    5e28:	96 98       	cbi	0x12, 6	; 18
    5e2a:	96 9a       	sbi	0x12, 6	; 18
    5e2c:	88 0f       	add	r24, r24
    5e2e:	21 50       	subi	r18, 0x01	; 1
    5e30:	30 40       	sbci	r19, 0x00	; 0
    5e32:	b9 f7       	brne	.-18     	; 0x5e22 <_ZN6System16CC1000_Registers9send_byteEh+0xa>
    5e34:	08 95       	ret

00005e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>:
    5e36:	1f 93       	push	r17
    5e38:	16 2f       	mov	r17, r22
    5e3a:	f8 94       	cli
    5e3c:	21 b3       	in	r18, 0x11	; 17
    5e3e:	20 6d       	ori	r18, 0xD0	; 208
    5e40:	21 bb       	out	0x11, r18	; 17
    5e42:	94 98       	cbi	0x12, 4	; 18
    5e44:	88 0f       	add	r24, r24
    5e46:	81 60       	ori	r24, 0x01	; 1
    5e48:	0e 94 0c 2f 	call	0x5e18	; 0x5e18 <_ZN6System16CC1000_Registers9send_byteEh>
    5e4c:	94 9a       	sbi	0x12, 4	; 18
    5e4e:	81 2f       	mov	r24, r17
    5e50:	0e 94 0c 2f 	call	0x5e18	; 0x5e18 <_ZN6System16CC1000_Registers9send_byteEh>
    5e54:	78 94       	sei
    5e56:	1f 91       	pop	r17
    5e58:	08 95       	ret

00005e5a <_ZN6System16CC1000_Registers4readENS0_8RegisterE>:
    5e5a:	f8 94       	cli
    5e5c:	21 b3       	in	r18, 0x11	; 17
    5e5e:	20 6d       	ori	r18, 0xD0	; 208
    5e60:	21 bb       	out	0x11, r18	; 17
    5e62:	94 98       	cbi	0x12, 4	; 18
    5e64:	88 0f       	add	r24, r24
    5e66:	0e 94 0c 2f 	call	0x5e18	; 0x5e18 <_ZN6System16CC1000_Registers9send_byteEh>
    5e6a:	94 9a       	sbi	0x12, 4	; 18
    5e6c:	0e 94 f7 2e 	call	0x5dee	; 0x5dee <_ZN6System16CC1000_Registers12receive_byteEv>
    5e70:	78 94       	sei
    5e72:	99 27       	eor	r25, r25
    5e74:	08 95       	ret

00005e76 <_ZN6System6CC10004initEv>:
    5e76:	6a e3       	ldi	r22, 0x3A	; 58
    5e78:	80 e0       	ldi	r24, 0x00	; 0
    5e7a:	90 e0       	ldi	r25, 0x00	; 0
    5e7c:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5e80:	6b e3       	ldi	r22, 0x3B	; 59
    5e82:	80 e0       	ldi	r24, 0x00	; 0
    5e84:	90 e0       	ldi	r25, 0x00	; 0
    5e86:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5e8a:	e0 91 87 04 	lds	r30, 0x0487
    5e8e:	f0 91 88 04 	lds	r31, 0x0488
    5e92:	e2 5c       	subi	r30, 0xC2	; 194
    5e94:	fd 4f       	sbci	r31, 0xFD	; 253
    5e96:	60 81       	ld	r22, Z
    5e98:	77 27       	eor	r23, r23
    5e9a:	8b e0       	ldi	r24, 0x0B	; 11
    5e9c:	90 e0       	ldi	r25, 0x00	; 0
    5e9e:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5ea2:	80 91 89 04 	lds	r24, 0x0489
    5ea6:	90 91 8a 04 	lds	r25, 0x048A
    5eaa:	88 0f       	add	r24, r24
    5eac:	99 1f       	adc	r25, r25
    5eae:	fc 01       	movw	r30, r24
    5eb0:	ee 0f       	add	r30, r30
    5eb2:	ff 1f       	adc	r31, r31
    5eb4:	ee 0f       	add	r30, r30
    5eb6:	ff 1f       	adc	r31, r31
    5eb8:	ee 0f       	add	r30, r30
    5eba:	ff 1f       	adc	r31, r31
    5ebc:	e8 0f       	add	r30, r24
    5ebe:	f9 1f       	adc	r31, r25
    5ec0:	e8 5a       	subi	r30, 0xA8	; 168
    5ec2:	fd 4f       	sbci	r31, 0xFD	; 253
    5ec4:	64 85       	ldd	r22, Z+12	; 0x0c
    5ec6:	77 27       	eor	r23, r23
    5ec8:	8d e0       	ldi	r24, 0x0D	; 13
    5eca:	90 e0       	ldi	r25, 0x00	; 0
    5ecc:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5ed0:	80 91 89 04 	lds	r24, 0x0489
    5ed4:	90 91 8a 04 	lds	r25, 0x048A
    5ed8:	88 0f       	add	r24, r24
    5eda:	99 1f       	adc	r25, r25
    5edc:	fc 01       	movw	r30, r24
    5ede:	ee 0f       	add	r30, r30
    5ee0:	ff 1f       	adc	r31, r31
    5ee2:	ee 0f       	add	r30, r30
    5ee4:	ff 1f       	adc	r31, r31
    5ee6:	ee 0f       	add	r30, r30
    5ee8:	ff 1f       	adc	r31, r31
    5eea:	e8 0f       	add	r30, r24
    5eec:	f9 1f       	adc	r31, r25
    5eee:	e8 5a       	subi	r30, 0xA8	; 168
    5ef0:	fd 4f       	sbci	r31, 0xFD	; 253
    5ef2:	65 85       	ldd	r22, Z+13	; 0x0d
    5ef4:	77 27       	eor	r23, r23
    5ef6:	8f e0       	ldi	r24, 0x0F	; 15
    5ef8:	90 e0       	ldi	r25, 0x00	; 0
    5efa:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5efe:	80 91 89 04 	lds	r24, 0x0489
    5f02:	90 91 8a 04 	lds	r25, 0x048A
    5f06:	88 0f       	add	r24, r24
    5f08:	99 1f       	adc	r25, r25
    5f0a:	fc 01       	movw	r30, r24
    5f0c:	ee 0f       	add	r30, r30
    5f0e:	ff 1f       	adc	r31, r31
    5f10:	ee 0f       	add	r30, r30
    5f12:	ff 1f       	adc	r31, r31
    5f14:	ee 0f       	add	r30, r30
    5f16:	ff 1f       	adc	r31, r31
    5f18:	e8 0f       	add	r30, r24
    5f1a:	f9 1f       	adc	r31, r25
    5f1c:	e8 5a       	subi	r30, 0xA8	; 168
    5f1e:	fd 4f       	sbci	r31, 0xFD	; 253
    5f20:	66 85       	ldd	r22, Z+14	; 0x0e
    5f22:	77 27       	eor	r23, r23
    5f24:	80 e1       	ldi	r24, 0x10	; 16
    5f26:	90 e0       	ldi	r25, 0x00	; 0
    5f28:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5f2c:	80 91 89 04 	lds	r24, 0x0489
    5f30:	90 91 8a 04 	lds	r25, 0x048A
    5f34:	88 0f       	add	r24, r24
    5f36:	99 1f       	adc	r25, r25
    5f38:	fc 01       	movw	r30, r24
    5f3a:	ee 0f       	add	r30, r30
    5f3c:	ff 1f       	adc	r31, r31
    5f3e:	ee 0f       	add	r30, r30
    5f40:	ff 1f       	adc	r31, r31
    5f42:	ee 0f       	add	r30, r30
    5f44:	ff 1f       	adc	r31, r31
    5f46:	e8 0f       	add	r30, r24
    5f48:	f9 1f       	adc	r31, r25
    5f4a:	e8 5a       	subi	r30, 0xA8	; 168
    5f4c:	fd 4f       	sbci	r31, 0xFD	; 253
    5f4e:	67 85       	ldd	r22, Z+15	; 0x0f
    5f50:	77 27       	eor	r23, r23
    5f52:	81 e1       	ldi	r24, 0x11	; 17
    5f54:	90 e0       	ldi	r25, 0x00	; 0
    5f56:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5f5a:	80 91 89 04 	lds	r24, 0x0489
    5f5e:	90 91 8a 04 	lds	r25, 0x048A
    5f62:	88 0f       	add	r24, r24
    5f64:	99 1f       	adc	r25, r25
    5f66:	fc 01       	movw	r30, r24
    5f68:	ee 0f       	add	r30, r30
    5f6a:	ff 1f       	adc	r31, r31
    5f6c:	ee 0f       	add	r30, r30
    5f6e:	ff 1f       	adc	r31, r31
    5f70:	ee 0f       	add	r30, r30
    5f72:	ff 1f       	adc	r31, r31
    5f74:	e8 0f       	add	r30, r24
    5f76:	f9 1f       	adc	r31, r25
    5f78:	e8 5a       	subi	r30, 0xA8	; 168
    5f7a:	fd 4f       	sbci	r31, 0xFD	; 253
    5f7c:	61 89       	ldd	r22, Z+17	; 0x11
    5f7e:	77 27       	eor	r23, r23
    5f80:	83 e1       	ldi	r24, 0x13	; 19
    5f82:	90 e0       	ldi	r25, 0x00	; 0
    5f84:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5f88:	80 91 89 04 	lds	r24, 0x0489
    5f8c:	90 91 8a 04 	lds	r25, 0x048A
    5f90:	88 0f       	add	r24, r24
    5f92:	99 1f       	adc	r25, r25
    5f94:	fc 01       	movw	r30, r24
    5f96:	ee 0f       	add	r30, r30
    5f98:	ff 1f       	adc	r31, r31
    5f9a:	ee 0f       	add	r30, r30
    5f9c:	ff 1f       	adc	r31, r31
    5f9e:	ee 0f       	add	r30, r30
    5fa0:	ff 1f       	adc	r31, r31
    5fa2:	e8 0f       	add	r30, r24
    5fa4:	f9 1f       	adc	r31, r25
    5fa6:	e8 5a       	subi	r30, 0xA8	; 168
    5fa8:	fd 4f       	sbci	r31, 0xFD	; 253
    5faa:	60 81       	ld	r22, Z
    5fac:	77 27       	eor	r23, r23
    5fae:	81 e0       	ldi	r24, 0x01	; 1
    5fb0:	90 e0       	ldi	r25, 0x00	; 0
    5fb2:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5fb6:	80 91 89 04 	lds	r24, 0x0489
    5fba:	90 91 8a 04 	lds	r25, 0x048A
    5fbe:	88 0f       	add	r24, r24
    5fc0:	99 1f       	adc	r25, r25
    5fc2:	fc 01       	movw	r30, r24
    5fc4:	ee 0f       	add	r30, r30
    5fc6:	ff 1f       	adc	r31, r31
    5fc8:	ee 0f       	add	r30, r30
    5fca:	ff 1f       	adc	r31, r31
    5fcc:	ee 0f       	add	r30, r30
    5fce:	ff 1f       	adc	r31, r31
    5fd0:	e8 0f       	add	r30, r24
    5fd2:	f9 1f       	adc	r31, r25
    5fd4:	e8 5a       	subi	r30, 0xA8	; 168
    5fd6:	fd 4f       	sbci	r31, 0xFD	; 253
    5fd8:	61 81       	ldd	r22, Z+1	; 0x01
    5fda:	77 27       	eor	r23, r23
    5fdc:	82 e0       	ldi	r24, 0x02	; 2
    5fde:	90 e0       	ldi	r25, 0x00	; 0
    5fe0:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    5fe4:	80 91 89 04 	lds	r24, 0x0489
    5fe8:	90 91 8a 04 	lds	r25, 0x048A
    5fec:	88 0f       	add	r24, r24
    5fee:	99 1f       	adc	r25, r25
    5ff0:	fc 01       	movw	r30, r24
    5ff2:	ee 0f       	add	r30, r30
    5ff4:	ff 1f       	adc	r31, r31
    5ff6:	ee 0f       	add	r30, r30
    5ff8:	ff 1f       	adc	r31, r31
    5ffa:	ee 0f       	add	r30, r30
    5ffc:	ff 1f       	adc	r31, r31
    5ffe:	e8 0f       	add	r30, r24
    6000:	f9 1f       	adc	r31, r25
    6002:	e8 5a       	subi	r30, 0xA8	; 168
    6004:	fd 4f       	sbci	r31, 0xFD	; 253
    6006:	62 81       	ldd	r22, Z+2	; 0x02
    6008:	77 27       	eor	r23, r23
    600a:	83 e0       	ldi	r24, 0x03	; 3
    600c:	90 e0       	ldi	r25, 0x00	; 0
    600e:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    6012:	80 91 89 04 	lds	r24, 0x0489
    6016:	90 91 8a 04 	lds	r25, 0x048A
    601a:	88 0f       	add	r24, r24
    601c:	99 1f       	adc	r25, r25
    601e:	fc 01       	movw	r30, r24
    6020:	ee 0f       	add	r30, r30
    6022:	ff 1f       	adc	r31, r31
    6024:	ee 0f       	add	r30, r30
    6026:	ff 1f       	adc	r31, r31
    6028:	ee 0f       	add	r30, r30
    602a:	ff 1f       	adc	r31, r31
    602c:	e8 0f       	add	r30, r24
    602e:	f9 1f       	adc	r31, r25
    6030:	e8 5a       	subi	r30, 0xA8	; 168
    6032:	fd 4f       	sbci	r31, 0xFD	; 253
    6034:	63 81       	ldd	r22, Z+3	; 0x03
    6036:	77 27       	eor	r23, r23
    6038:	84 e0       	ldi	r24, 0x04	; 4
    603a:	90 e0       	ldi	r25, 0x00	; 0
    603c:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    6040:	80 91 89 04 	lds	r24, 0x0489
    6044:	90 91 8a 04 	lds	r25, 0x048A
    6048:	88 0f       	add	r24, r24
    604a:	99 1f       	adc	r25, r25
    604c:	fc 01       	movw	r30, r24
    604e:	ee 0f       	add	r30, r30
    6050:	ff 1f       	adc	r31, r31
    6052:	ee 0f       	add	r30, r30
    6054:	ff 1f       	adc	r31, r31
    6056:	ee 0f       	add	r30, r30
    6058:	ff 1f       	adc	r31, r31
    605a:	e8 0f       	add	r30, r24
    605c:	f9 1f       	adc	r31, r25
    605e:	e8 5a       	subi	r30, 0xA8	; 168
    6060:	fd 4f       	sbci	r31, 0xFD	; 253
    6062:	64 81       	ldd	r22, Z+4	; 0x04
    6064:	77 27       	eor	r23, r23
    6066:	85 e0       	ldi	r24, 0x05	; 5
    6068:	90 e0       	ldi	r25, 0x00	; 0
    606a:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    606e:	80 91 89 04 	lds	r24, 0x0489
    6072:	90 91 8a 04 	lds	r25, 0x048A
    6076:	88 0f       	add	r24, r24
    6078:	99 1f       	adc	r25, r25
    607a:	fc 01       	movw	r30, r24
    607c:	ee 0f       	add	r30, r30
    607e:	ff 1f       	adc	r31, r31
    6080:	ee 0f       	add	r30, r30
    6082:	ff 1f       	adc	r31, r31
    6084:	ee 0f       	add	r30, r30
    6086:	ff 1f       	adc	r31, r31
    6088:	e8 0f       	add	r30, r24
    608a:	f9 1f       	adc	r31, r25
    608c:	e8 5a       	subi	r30, 0xA8	; 168
    608e:	fd 4f       	sbci	r31, 0xFD	; 253
    6090:	65 81       	ldd	r22, Z+5	; 0x05
    6092:	77 27       	eor	r23, r23
    6094:	86 e0       	ldi	r24, 0x06	; 6
    6096:	90 e0       	ldi	r25, 0x00	; 0
    6098:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    609c:	80 91 89 04 	lds	r24, 0x0489
    60a0:	90 91 8a 04 	lds	r25, 0x048A
    60a4:	88 0f       	add	r24, r24
    60a6:	99 1f       	adc	r25, r25
    60a8:	fc 01       	movw	r30, r24
    60aa:	ee 0f       	add	r30, r30
    60ac:	ff 1f       	adc	r31, r31
    60ae:	ee 0f       	add	r30, r30
    60b0:	ff 1f       	adc	r31, r31
    60b2:	ee 0f       	add	r30, r30
    60b4:	ff 1f       	adc	r31, r31
    60b6:	e8 0f       	add	r30, r24
    60b8:	f9 1f       	adc	r31, r25
    60ba:	e8 5a       	subi	r30, 0xA8	; 168
    60bc:	fd 4f       	sbci	r31, 0xFD	; 253
    60be:	66 81       	ldd	r22, Z+6	; 0x06
    60c0:	77 27       	eor	r23, r23
    60c2:	87 e0       	ldi	r24, 0x07	; 7
    60c4:	90 e0       	ldi	r25, 0x00	; 0
    60c6:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    60ca:	80 91 89 04 	lds	r24, 0x0489
    60ce:	90 91 8a 04 	lds	r25, 0x048A
    60d2:	88 0f       	add	r24, r24
    60d4:	99 1f       	adc	r25, r25
    60d6:	fc 01       	movw	r30, r24
    60d8:	ee 0f       	add	r30, r30
    60da:	ff 1f       	adc	r31, r31
    60dc:	ee 0f       	add	r30, r30
    60de:	ff 1f       	adc	r31, r31
    60e0:	ee 0f       	add	r30, r30
    60e2:	ff 1f       	adc	r31, r31
    60e4:	e8 0f       	add	r30, r24
    60e6:	f9 1f       	adc	r31, r25
    60e8:	e8 5a       	subi	r30, 0xA8	; 168
    60ea:	fd 4f       	sbci	r31, 0xFD	; 253
    60ec:	67 81       	ldd	r22, Z+7	; 0x07
    60ee:	77 27       	eor	r23, r23
    60f0:	88 e0       	ldi	r24, 0x08	; 8
    60f2:	90 e0       	ldi	r25, 0x00	; 0
    60f4:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    60f8:	80 91 89 04 	lds	r24, 0x0489
    60fc:	90 91 8a 04 	lds	r25, 0x048A
    6100:	88 0f       	add	r24, r24
    6102:	99 1f       	adc	r25, r25
    6104:	fc 01       	movw	r30, r24
    6106:	ee 0f       	add	r30, r30
    6108:	ff 1f       	adc	r31, r31
    610a:	ee 0f       	add	r30, r30
    610c:	ff 1f       	adc	r31, r31
    610e:	ee 0f       	add	r30, r30
    6110:	ff 1f       	adc	r31, r31
    6112:	e8 0f       	add	r30, r24
    6114:	f9 1f       	adc	r31, r25
    6116:	e8 5a       	subi	r30, 0xA8	; 168
    6118:	fd 4f       	sbci	r31, 0xFD	; 253
    611a:	60 85       	ldd	r22, Z+8	; 0x08
    611c:	77 27       	eor	r23, r23
    611e:	89 e0       	ldi	r24, 0x09	; 9
    6120:	90 e0       	ldi	r25, 0x00	; 0
    6122:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    6126:	80 91 89 04 	lds	r24, 0x0489
    612a:	90 91 8a 04 	lds	r25, 0x048A
    612e:	88 0f       	add	r24, r24
    6130:	99 1f       	adc	r25, r25
    6132:	fc 01       	movw	r30, r24
    6134:	ee 0f       	add	r30, r30
    6136:	ff 1f       	adc	r31, r31
    6138:	ee 0f       	add	r30, r30
    613a:	ff 1f       	adc	r31, r31
    613c:	ee 0f       	add	r30, r30
    613e:	ff 1f       	adc	r31, r31
    6140:	e8 0f       	add	r30, r24
    6142:	f9 1f       	adc	r31, r25
    6144:	e8 5a       	subi	r30, 0xA8	; 168
    6146:	fd 4f       	sbci	r31, 0xFD	; 253
    6148:	62 85       	ldd	r22, Z+10	; 0x0a
    614a:	77 27       	eor	r23, r23
    614c:	8a e0       	ldi	r24, 0x0A	; 10
    614e:	90 e0       	ldi	r25, 0x00	; 0
    6150:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    6154:	e0 91 87 04 	lds	r30, 0x0487
    6158:	f0 91 88 04 	lds	r31, 0x0488
    615c:	e2 5c       	subi	r30, 0xC2	; 194
    615e:	fd 4f       	sbci	r31, 0xFD	; 253
    6160:	60 81       	ld	r22, Z
    6162:	77 27       	eor	r23, r23
    6164:	8b e0       	ldi	r24, 0x0B	; 11
    6166:	90 e0       	ldi	r25, 0x00	; 0
    6168:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    616c:	80 91 89 04 	lds	r24, 0x0489
    6170:	90 91 8a 04 	lds	r25, 0x048A
    6174:	88 0f       	add	r24, r24
    6176:	99 1f       	adc	r25, r25
    6178:	fc 01       	movw	r30, r24
    617a:	ee 0f       	add	r30, r30
    617c:	ff 1f       	adc	r31, r31
    617e:	ee 0f       	add	r30, r30
    6180:	ff 1f       	adc	r31, r31
    6182:	ee 0f       	add	r30, r30
    6184:	ff 1f       	adc	r31, r31
    6186:	e8 0f       	add	r30, r24
    6188:	f9 1f       	adc	r31, r25
    618a:	e8 5a       	subi	r30, 0xA8	; 168
    618c:	fd 4f       	sbci	r31, 0xFD	; 253
    618e:	63 85       	ldd	r22, Z+11	; 0x0b
    6190:	77 27       	eor	r23, r23
    6192:	8c e0       	ldi	r24, 0x0C	; 12
    6194:	90 e0       	ldi	r25, 0x00	; 0
    6196:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    619a:	80 91 89 04 	lds	r24, 0x0489
    619e:	90 91 8a 04 	lds	r25, 0x048A
    61a2:	88 0f       	add	r24, r24
    61a4:	99 1f       	adc	r25, r25
    61a6:	fc 01       	movw	r30, r24
    61a8:	ee 0f       	add	r30, r30
    61aa:	ff 1f       	adc	r31, r31
    61ac:	ee 0f       	add	r30, r30
    61ae:	ff 1f       	adc	r31, r31
    61b0:	ee 0f       	add	r30, r30
    61b2:	ff 1f       	adc	r31, r31
    61b4:	e8 0f       	add	r30, r24
    61b6:	f9 1f       	adc	r31, r25
    61b8:	e8 5a       	subi	r30, 0xA8	; 168
    61ba:	fd 4f       	sbci	r31, 0xFD	; 253
    61bc:	60 89       	ldd	r22, Z+16	; 0x10
    61be:	77 27       	eor	r23, r23
    61c0:	82 e1       	ldi	r24, 0x12	; 18
    61c2:	90 e0       	ldi	r25, 0x00	; 0
    61c4:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    61c8:	60 e0       	ldi	r22, 0x00	; 0
    61ca:	8b e0       	ldi	r24, 0x0B	; 11
    61cc:	90 e0       	ldi	r25, 0x00	; 0
    61ce:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    61d2:	6f e3       	ldi	r22, 0x3F	; 63
    61d4:	82 e4       	ldi	r24, 0x42	; 66
    61d6:	90 e0       	ldi	r25, 0x00	; 0
    61d8:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    61dc:	61 e1       	ldi	r22, 0x11	; 17
    61de:	80 e0       	ldi	r24, 0x00	; 0
    61e0:	90 e0       	ldi	r25, 0x00	; 0
    61e2:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    61e6:	66 ea       	ldi	r22, 0xA6	; 166
    61e8:	8e e0       	ldi	r24, 0x0E	; 14
    61ea:	90 e0       	ldi	r25, 0x00	; 0
    61ec:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    61f0:	8e e0       	ldi	r24, 0x0E	; 14
    61f2:	90 e0       	ldi	r25, 0x00	; 0
    61f4:	0e 94 2d 2f 	call	0x5e5a	; 0x5e5a <_ZN6System16CC1000_Registers4readENS0_8RegisterE>
    61f8:	83 ff       	sbrs	r24, 3
    61fa:	fa cf       	rjmp	.-12     	; 0x61f0 <_ZN6System6CC10004initEv+0x37a>
    61fc:	66 e2       	ldi	r22, 0x26	; 38
    61fe:	8e e0       	ldi	r24, 0x0E	; 14
    6200:	90 e0       	ldi	r25, 0x00	; 0
    6202:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    6206:	61 ee       	ldi	r22, 0xE1	; 225
    6208:	80 e0       	ldi	r24, 0x00	; 0
    620a:	90 e0       	ldi	r25, 0x00	; 0
    620c:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    6210:	80 91 89 04 	lds	r24, 0x0489
    6214:	90 91 8a 04 	lds	r25, 0x048A
    6218:	88 0f       	add	r24, r24
    621a:	99 1f       	adc	r25, r25
    621c:	fc 01       	movw	r30, r24
    621e:	ee 0f       	add	r30, r30
    6220:	ff 1f       	adc	r31, r31
    6222:	ee 0f       	add	r30, r30
    6224:	ff 1f       	adc	r31, r31
    6226:	ee 0f       	add	r30, r30
    6228:	ff 1f       	adc	r31, r31
    622a:	e8 0f       	add	r30, r24
    622c:	f9 1f       	adc	r31, r25
    622e:	e8 5a       	subi	r30, 0xA8	; 168
    6230:	fd 4f       	sbci	r31, 0xFD	; 253
    6232:	61 85       	ldd	r22, Z+9	; 0x09
    6234:	77 27       	eor	r23, r23
    6236:	89 e0       	ldi	r24, 0x09	; 9
    6238:	90 e0       	ldi	r25, 0x00	; 0
    623a:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    623e:	60 e0       	ldi	r22, 0x00	; 0
    6240:	8b e0       	ldi	r24, 0x0B	; 11
    6242:	90 e0       	ldi	r25, 0x00	; 0
    6244:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    6248:	66 ea       	ldi	r22, 0xA6	; 166
    624a:	8e e0       	ldi	r24, 0x0E	; 14
    624c:	90 e0       	ldi	r25, 0x00	; 0
    624e:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    6252:	8e e0       	ldi	r24, 0x0E	; 14
    6254:	90 e0       	ldi	r25, 0x00	; 0
    6256:	0e 94 2d 2f 	call	0x5e5a	; 0x5e5a <_ZN6System16CC1000_Registers4readENS0_8RegisterE>
    625a:	83 ff       	sbrs	r24, 3
    625c:	fa cf       	rjmp	.-12     	; 0x6252 <_ZN6System6CC10004initEv+0x3dc>
    625e:	66 e2       	ldi	r22, 0x26	; 38
    6260:	8e e0       	ldi	r24, 0x0E	; 14
    6262:	90 e0       	ldi	r25, 0x00	; 0
    6264:	0e 94 1b 2f 	call	0x5e36	; 0x5e36 <_ZN6System16CC1000_Registers5writeENS0_8RegisterEh>
    6268:	08 95       	ret

0000626a <_ZN6System4AVR814switch_contextEPVPNS0_7ContextES2_>:
    626a:	1f b6       	in	r1, 0x3f	; 63
    626c:	ff 93       	push	r31
    626e:	ef 93       	push	r30
    6270:	df 93       	push	r29
    6272:	cf 93       	push	r28
    6274:	bf 93       	push	r27
    6276:	af 93       	push	r26
    6278:	9f 93       	push	r25
    627a:	8f 93       	push	r24
    627c:	7f 93       	push	r23
    627e:	6f 93       	push	r22
    6280:	5f 93       	push	r21
    6282:	4f 93       	push	r20
    6284:	3f 93       	push	r19
    6286:	2f 93       	push	r18
    6288:	1f 93       	push	r17
    628a:	0f 93       	push	r16
    628c:	ff 92       	push	r15
    628e:	ef 92       	push	r14
    6290:	df 92       	push	r13
    6292:	cf 92       	push	r12
    6294:	bf 92       	push	r11
    6296:	af 92       	push	r10
    6298:	9f 92       	push	r9
    629a:	8f 92       	push	r8
    629c:	7f 92       	push	r7
    629e:	6f 92       	push	r6
    62a0:	5f 92       	push	r5
    62a2:	4f 92       	push	r4
    62a4:	3f 92       	push	r3
    62a6:	2f 92       	push	r2
    62a8:	0f 92       	push	r0
    62aa:	1f 92       	push	r1
    62ac:	11 24       	eor	r1, r1
    62ae:	b9 2f       	mov	r27, r25
    62b0:	a8 2f       	mov	r26, r24
    62b2:	9e b7       	in	r25, 0x3e	; 62
    62b4:	8d b7       	in	r24, 0x3d	; 61
    62b6:	01 96       	adiw	r24, 0x01	; 1
    62b8:	8d 93       	st	X+, r24
    62ba:	9c 93       	st	X, r25
    62bc:	97 2f       	mov	r25, r23
    62be:	86 2f       	mov	r24, r22
    62c0:	01 97       	sbiw	r24, 0x01	; 1
    62c2:	9e bf       	out	0x3e, r25	; 62
    62c4:	8d bf       	out	0x3d, r24	; 61
    62c6:	1f 90       	pop	r1
    62c8:	0f 90       	pop	r0
    62ca:	2f 90       	pop	r2
    62cc:	3f 90       	pop	r3
    62ce:	4f 90       	pop	r4
    62d0:	5f 90       	pop	r5
    62d2:	6f 90       	pop	r6
    62d4:	7f 90       	pop	r7
    62d6:	8f 90       	pop	r8
    62d8:	9f 90       	pop	r9
    62da:	af 90       	pop	r10
    62dc:	bf 90       	pop	r11
    62de:	cf 90       	pop	r12
    62e0:	df 90       	pop	r13
    62e2:	ef 90       	pop	r14
    62e4:	ff 90       	pop	r15
    62e6:	0f 91       	pop	r16
    62e8:	1f 91       	pop	r17
    62ea:	2f 91       	pop	r18
    62ec:	3f 91       	pop	r19
    62ee:	4f 91       	pop	r20
    62f0:	5f 91       	pop	r21
    62f2:	6f 91       	pop	r22
    62f4:	7f 91       	pop	r23
    62f6:	8f 91       	pop	r24
    62f8:	9f 91       	pop	r25
    62fa:	af 91       	pop	r26
    62fc:	bf 91       	pop	r27
    62fe:	cf 91       	pop	r28
    6300:	df 91       	pop	r29
    6302:	ef 91       	pop	r30
    6304:	ff 91       	pop	r31
    6306:	1f be       	out	0x3f, r1	; 63
    6308:	11 24       	eor	r1, r1
    630a:	08 95       	ret

0000630c <_ZNV6System4AVR87Context4saveEv>:
    630c:	bf 91       	pop	r27
    630e:	af 91       	pop	r26
    6310:	1f b6       	in	r1, 0x3f	; 63
    6312:	ff 93       	push	r31
    6314:	ef 93       	push	r30
    6316:	df 93       	push	r29
    6318:	cf 93       	push	r28
    631a:	bf 93       	push	r27
    631c:	af 93       	push	r26
    631e:	9f 93       	push	r25
    6320:	8f 93       	push	r24
    6322:	7f 93       	push	r23
    6324:	6f 93       	push	r22
    6326:	5f 93       	push	r21
    6328:	4f 93       	push	r20
    632a:	3f 93       	push	r19
    632c:	2f 93       	push	r18
    632e:	1f 93       	push	r17
    6330:	0f 93       	push	r16
    6332:	ff 92       	push	r15
    6334:	ef 92       	push	r14
    6336:	df 92       	push	r13
    6338:	cf 92       	push	r12
    633a:	bf 92       	push	r11
    633c:	af 92       	push	r10
    633e:	9f 92       	push	r9
    6340:	8f 92       	push	r8
    6342:	7f 92       	push	r7
    6344:	6f 92       	push	r6
    6346:	5f 92       	push	r5
    6348:	4f 92       	push	r4
    634a:	3f 92       	push	r3
    634c:	2f 92       	push	r2
    634e:	0f 92       	push	r0
    6350:	1f 92       	push	r1
    6352:	11 24       	eor	r1, r1
    6354:	af 93       	push	r26
    6356:	bf 93       	push	r27
    6358:	08 95       	ret

0000635a <_ZNVK6System4AVR87Context4loadEv>:
    635a:	01 97       	sbiw	r24, 0x01	; 1
    635c:	9e bf       	out	0x3e, r25	; 62
    635e:	8d bf       	out	0x3d, r24	; 61
    6360:	1f 90       	pop	r1
    6362:	0f 90       	pop	r0
    6364:	2f 90       	pop	r2
    6366:	3f 90       	pop	r3
    6368:	4f 90       	pop	r4
    636a:	5f 90       	pop	r5
    636c:	6f 90       	pop	r6
    636e:	7f 90       	pop	r7
    6370:	8f 90       	pop	r8
    6372:	9f 90       	pop	r9
    6374:	af 90       	pop	r10
    6376:	bf 90       	pop	r11
    6378:	cf 90       	pop	r12
    637a:	df 90       	pop	r13
    637c:	ef 90       	pop	r14
    637e:	ff 90       	pop	r15
    6380:	0f 91       	pop	r16
    6382:	1f 91       	pop	r17
    6384:	2f 91       	pop	r18
    6386:	3f 91       	pop	r19
    6388:	4f 91       	pop	r20
    638a:	5f 91       	pop	r21
    638c:	6f 91       	pop	r22
    638e:	7f 91       	pop	r23
    6390:	8f 91       	pop	r24
    6392:	9f 91       	pop	r25
    6394:	af 91       	pop	r26
    6396:	bf 91       	pop	r27
    6398:	cf 91       	pop	r28
    639a:	df 91       	pop	r29
    639c:	ef 91       	pop	r30
    639e:	ff 91       	pop	r31
    63a0:	1f be       	out	0x3f, r1	; 63
    63a2:	11 24       	eor	r1, r1
    63a4:	08 95       	ret
    63a6:	08 95       	ret

000063a8 <_Z41__static_initialization_and_destruction_0ii>:
    63a8:	6f 5f       	subi	r22, 0xFF	; 255
    63aa:	7f 4f       	sbci	r23, 0xFF	; 255
    63ac:	09 f0       	breq	.+2      	; 0x63b0 <_Z41__static_initialization_and_destruction_0ii+0x8>
    63ae:	08 95       	ret
    63b0:	01 97       	sbiw	r24, 0x01	; 1
    63b2:	e9 f7       	brne	.-6      	; 0x63ae <_Z41__static_initialization_and_destruction_0ii+0x6>
    63b4:	10 92 8d 04 	sts	0x048D, r1
    63b8:	10 92 8c 04 	sts	0x048C, r1
    63bc:	10 92 8f 04 	sts	0x048F, r1
    63c0:	10 92 8e 04 	sts	0x048E, r1
    63c4:	10 92 91 04 	sts	0x0491, r1
    63c8:	10 92 90 04 	sts	0x0490, r1
    63cc:	10 92 93 04 	sts	0x0493, r1
    63d0:	10 92 92 04 	sts	0x0492, r1
    63d4:	08 95       	ret

000063d6 <_GLOBAL__I__ZN6System8AVR8_MMU5_freeE>:
    63d6:	6f ef       	ldi	r22, 0xFF	; 255
    63d8:	7f ef       	ldi	r23, 0xFF	; 255
    63da:	81 e0       	ldi	r24, 0x01	; 1
    63dc:	90 e0       	ldi	r25, 0x00	; 0
    63de:	0e 94 d4 31 	call	0x63a8	; 0x63a8 <_Z41__static_initialization_and_destruction_0ii>
    63e2:	08 95       	ret

000063e4 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi>:
    63e4:	ef 92       	push	r14
    63e6:	ff 92       	push	r15
    63e8:	0f 93       	push	r16
    63ea:	1f 93       	push	r17
    63ec:	cf 93       	push	r28
    63ee:	df 93       	push	r29
    63f0:	fc 01       	movw	r30, r24
    63f2:	00 81       	ld	r16, Z
    63f4:	11 81       	ldd	r17, Z+1	; 0x01
    63f6:	01 15       	cp	r16, r1
    63f8:	11 05       	cpc	r17, r1
    63fa:	09 f4       	brne	.+2      	; 0x63fe <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x1a>
    63fc:	e6 c0       	rjmp	.+460    	; 0x65ca <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x1e6>
    63fe:	61 15       	cp	r22, r1
    6400:	71 05       	cpc	r23, r1
    6402:	09 f4       	brne	.+2      	; 0x6406 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x22>
    6404:	e2 c0       	rjmp	.+452    	; 0x65ca <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x1e6>
    6406:	e8 01       	movw	r28, r16
    6408:	19 83       	std	Y+1, r17	; 0x01
    640a:	08 83       	st	Y, r16
    640c:	7b 83       	std	Y+3, r23	; 0x03
    640e:	6a 83       	std	Y+2, r22	; 0x02
    6410:	1d 82       	std	Y+5, r1	; 0x05
    6412:	1c 82       	std	Y+4, r1	; 0x04
    6414:	80 91 92 04 	lds	r24, 0x0492
    6418:	90 91 93 04 	lds	r25, 0x0493
    641c:	86 0f       	add	r24, r22
    641e:	97 1f       	adc	r25, r23
    6420:	90 93 93 04 	sts	0x0493, r25
    6424:	80 93 92 04 	sts	0x0492, r24
    6428:	6a 81       	ldd	r22, Y+2	; 0x02
    642a:	7b 81       	ldd	r23, Y+3	; 0x03
    642c:	98 01       	movw	r18, r16
    642e:	26 0f       	add	r18, r22
    6430:	37 1f       	adc	r19, r23
    6432:	40 91 8e 04 	lds	r20, 0x048E
    6436:	50 91 8f 04 	lds	r21, 0x048F
    643a:	41 15       	cp	r20, r1
    643c:	51 05       	cpc	r21, r1
    643e:	09 f4       	brne	.+2      	; 0x6442 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x5e>
    6440:	79 c0       	rjmp	.+242    	; 0x6534 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x150>
    6442:	fa 01       	movw	r30, r20
    6444:	80 81       	ld	r24, Z
    6446:	91 81       	ldd	r25, Z+1	; 0x01
    6448:	28 17       	cp	r18, r24
    644a:	39 07       	cpc	r19, r25
    644c:	09 f4       	brne	.+2      	; 0x6450 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x6c>
    644e:	72 c0       	rjmp	.+228    	; 0x6534 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x150>
    6450:	ea 01       	movw	r28, r20
    6452:	0c 80       	ldd	r0, Y+4	; 0x04
    6454:	dd 81       	ldd	r29, Y+5	; 0x05
    6456:	c0 2d       	mov	r28, r0
    6458:	20 97       	sbiw	r28, 0x00	; 0
    645a:	29 f0       	breq	.+10     	; 0x6466 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x82>
    645c:	88 81       	ld	r24, Y
    645e:	99 81       	ldd	r25, Y+1	; 0x01
    6460:	82 17       	cp	r24, r18
    6462:	93 07       	cpc	r25, r19
    6464:	b1 f7       	brne	.-20     	; 0x6452 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x6e>
    6466:	f8 01       	movw	r30, r16
    6468:	e0 80       	ld	r14, Z
    646a:	f1 80       	ldd	r15, Z+1	; 0x01
    646c:	41 15       	cp	r20, r1
    646e:	51 05       	cpc	r21, r1
    6470:	09 f4       	brne	.+2      	; 0x6474 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x90>
    6472:	5e c0       	rjmp	.+188    	; 0x6530 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x14c>
    6474:	fa 01       	movw	r30, r20
    6476:	82 81       	ldd	r24, Z+2	; 0x02
    6478:	93 81       	ldd	r25, Z+3	; 0x03
    647a:	20 81       	ld	r18, Z
    647c:	31 81       	ldd	r19, Z+1	; 0x01
    647e:	82 0f       	add	r24, r18
    6480:	93 1f       	adc	r25, r19
    6482:	e8 16       	cp	r14, r24
    6484:	f9 06       	cpc	r15, r25
    6486:	09 f4       	brne	.+2      	; 0x648a <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0xa6>
    6488:	53 c0       	rjmp	.+166    	; 0x6530 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x14c>
    648a:	da 01       	movw	r26, r20
    648c:	fd 01       	movw	r30, r26
    648e:	a4 81       	ldd	r26, Z+4	; 0x04
    6490:	b5 81       	ldd	r27, Z+5	; 0x05
    6492:	10 97       	sbiw	r26, 0x00	; 0
    6494:	59 f0       	breq	.+22     	; 0x64ac <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0xc8>
    6496:	8d 91       	ld	r24, X+
    6498:	9c 91       	ld	r25, X
    649a:	11 97       	sbiw	r26, 0x01	; 1
    649c:	fd 01       	movw	r30, r26
    649e:	22 81       	ldd	r18, Z+2	; 0x02
    64a0:	33 81       	ldd	r19, Z+3	; 0x03
    64a2:	82 0f       	add	r24, r18
    64a4:	93 1f       	adc	r25, r19
    64a6:	e8 16       	cp	r14, r24
    64a8:	f9 06       	cpc	r15, r25
    64aa:	81 f7       	brne	.-32     	; 0x648c <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0xa8>
    64ac:	20 97       	sbiw	r28, 0x00	; 0
    64ae:	09 f4       	brne	.+2      	; 0x64b2 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0xce>
    64b0:	50 c0       	rjmp	.+160    	; 0x6552 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x16e>
    64b2:	8a 81       	ldd	r24, Y+2	; 0x02
    64b4:	9b 81       	ldd	r25, Y+3	; 0x03
    64b6:	86 0f       	add	r24, r22
    64b8:	97 1f       	adc	r25, r23
    64ba:	f8 01       	movw	r30, r16
    64bc:	93 83       	std	Z+3, r25	; 0x03
    64be:	82 83       	std	Z+2, r24	; 0x02
    64c0:	20 91 8c 04 	lds	r18, 0x048C
    64c4:	30 91 8d 04 	lds	r19, 0x048D
    64c8:	21 30       	cpi	r18, 0x01	; 1
    64ca:	31 05       	cpc	r19, r1
    64cc:	a9 f1       	breq	.+106    	; 0x6538 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x154>
    64ce:	4c 17       	cp	r20, r28
    64d0:	5d 07       	cpc	r21, r29
    64d2:	09 f4       	brne	.+2      	; 0x64d6 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0xf2>
    64d4:	58 c0       	rjmp	.+176    	; 0x6586 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x1a2>
    64d6:	41 15       	cp	r20, r1
    64d8:	51 05       	cpc	r21, r1
    64da:	b1 f0       	breq	.+44     	; 0x6508 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x124>
    64dc:	fa 01       	movw	r30, r20
    64de:	84 81       	ldd	r24, Z+4	; 0x04
    64e0:	95 81       	ldd	r25, Z+5	; 0x05
    64e2:	00 97       	sbiw	r24, 0x00	; 0
    64e4:	49 f4       	brne	.+18     	; 0x64f8 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x114>
    64e6:	0b c0       	rjmp	.+22     	; 0x64fe <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x11a>
    64e8:	ac 01       	movw	r20, r24
    64ea:	00 97       	sbiw	r24, 0x00	; 0
    64ec:	69 f0       	breq	.+26     	; 0x6508 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x124>
    64ee:	fc 01       	movw	r30, r24
    64f0:	84 81       	ldd	r24, Z+4	; 0x04
    64f2:	95 81       	ldd	r25, Z+5	; 0x05
    64f4:	00 97       	sbiw	r24, 0x00	; 0
    64f6:	19 f0       	breq	.+6      	; 0x64fe <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x11a>
    64f8:	c8 17       	cp	r28, r24
    64fa:	d9 07       	cpc	r29, r25
    64fc:	a9 f7       	brne	.-22     	; 0x64e8 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x104>
    64fe:	8c 81       	ldd	r24, Y+4	; 0x04
    6500:	9d 81       	ldd	r25, Y+5	; 0x05
    6502:	ea 01       	movw	r28, r20
    6504:	9d 83       	std	Y+5, r25	; 0x05
    6506:	8c 83       	std	Y+4, r24	; 0x04
    6508:	21 50       	subi	r18, 0x01	; 1
    650a:	30 40       	sbci	r19, 0x00	; 0
    650c:	30 93 8d 04 	sts	0x048D, r19
    6510:	20 93 8c 04 	sts	0x048C, r18
    6514:	10 97       	sbiw	r26, 0x00	; 0
    6516:	09 f4       	brne	.+2      	; 0x651a <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x136>
    6518:	58 c0       	rjmp	.+176    	; 0x65ca <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x1e6>
    651a:	e8 01       	movw	r28, r16
    651c:	6a 81       	ldd	r22, Y+2	; 0x02
    651e:	7b 81       	ldd	r23, Y+3	; 0x03
    6520:	fd 01       	movw	r30, r26
    6522:	82 81       	ldd	r24, Z+2	; 0x02
    6524:	93 81       	ldd	r25, Z+3	; 0x03
    6526:	86 0f       	add	r24, r22
    6528:	97 1f       	adc	r25, r23
    652a:	93 83       	std	Z+3, r25	; 0x03
    652c:	82 83       	std	Z+2, r24	; 0x02
    652e:	4d c0       	rjmp	.+154    	; 0x65ca <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x1e6>
    6530:	da 01       	movw	r26, r20
    6532:	bc cf       	rjmp	.-136    	; 0x64ac <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0xc8>
    6534:	ea 01       	movw	r28, r20
    6536:	97 cf       	rjmp	.-210    	; 0x6466 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x82>
    6538:	10 92 8f 04 	sts	0x048F, r1
    653c:	10 92 8e 04 	sts	0x048E, r1
    6540:	10 92 91 04 	sts	0x0491, r1
    6544:	10 92 90 04 	sts	0x0490, r1
    6548:	10 92 8d 04 	sts	0x048D, r1
    654c:	10 92 8c 04 	sts	0x048C, r1
    6550:	e1 cf       	rjmp	.-62     	; 0x6514 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x130>
    6552:	10 97       	sbiw	r26, 0x00	; 0
    6554:	29 f7       	brne	.-54     	; 0x6520 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x13c>
    6556:	80 91 8c 04 	lds	r24, 0x048C
    655a:	90 91 8d 04 	lds	r25, 0x048D
    655e:	00 97       	sbiw	r24, 0x00	; 0
    6560:	11 f5       	brne	.+68     	; 0x65a6 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x1c2>
    6562:	e8 01       	movw	r28, r16
    6564:	1d 82       	std	Y+5, r1	; 0x05
    6566:	1c 82       	std	Y+4, r1	; 0x04
    6568:	10 93 8f 04 	sts	0x048F, r17
    656c:	00 93 8e 04 	sts	0x048E, r16
    6570:	10 93 91 04 	sts	0x0491, r17
    6574:	00 93 90 04 	sts	0x0490, r16
    6578:	81 e0       	ldi	r24, 0x01	; 1
    657a:	90 e0       	ldi	r25, 0x00	; 0
    657c:	90 93 8d 04 	sts	0x048D, r25
    6580:	80 93 8c 04 	sts	0x048C, r24
    6584:	22 c0       	rjmp	.+68     	; 0x65ca <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x1e6>
    6586:	21 15       	cp	r18, r1
    6588:	31 05       	cpc	r19, r1
    658a:	21 f2       	breq	.-120    	; 0x6514 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x130>
    658c:	8c 81       	ldd	r24, Y+4	; 0x04
    658e:	9d 81       	ldd	r25, Y+5	; 0x05
    6590:	90 93 8f 04 	sts	0x048F, r25
    6594:	80 93 8e 04 	sts	0x048E, r24
    6598:	21 50       	subi	r18, 0x01	; 1
    659a:	30 40       	sbci	r19, 0x00	; 0
    659c:	30 93 8d 04 	sts	0x048D, r19
    65a0:	20 93 8c 04 	sts	0x048C, r18
    65a4:	b7 cf       	rjmp	.-146    	; 0x6514 <_ZN6System8AVR8_MMU4freeENS_10CPU_Common8Log_AddrEi+0x130>
    65a6:	e0 91 90 04 	lds	r30, 0x0490
    65aa:	f0 91 91 04 	lds	r31, 0x0491
    65ae:	15 83       	std	Z+5, r17	; 0x05
    65b0:	04 83       	std	Z+4, r16	; 0x04
    65b2:	f8 01       	movw	r30, r16
    65b4:	15 82       	std	Z+5, r1	; 0x05
    65b6:	14 82       	std	Z+4, r1	; 0x04
    65b8:	10 93 91 04 	sts	0x0491, r17
    65bc:	00 93 90 04 	sts	0x0490, r16
    65c0:	01 96       	adiw	r24, 0x01	; 1
    65c2:	90 93 8d 04 	sts	0x048D, r25
    65c6:	80 93 8c 04 	sts	0x048C, r24
    65ca:	df 91       	pop	r29
    65cc:	cf 91       	pop	r28
    65ce:	1f 91       	pop	r17
    65d0:	0f 91       	pop	r16
    65d2:	ff 90       	pop	r15
    65d4:	ef 90       	pop	r14
    65d6:	08 95       	ret

000065d8 <_ZN6System8AVR8_MMU5allocEj>:
    65d8:	0f 93       	push	r16
    65da:	1f 93       	push	r17
    65dc:	cf 93       	push	r28
    65de:	df 93       	push	r29
    65e0:	8c 01       	movw	r16, r24
    65e2:	ec 01       	movw	r28, r24
    65e4:	19 82       	std	Y+1, r1	; 0x01
    65e6:	18 82       	st	Y, r1
    65e8:	61 15       	cp	r22, r1
    65ea:	71 05       	cpc	r23, r1
    65ec:	09 f4       	brne	.+2      	; 0x65f0 <_ZN6System8AVR8_MMU5allocEj+0x18>
    65ee:	72 c0       	rjmp	.+228    	; 0x66d4 <_ZN6System8AVR8_MMU5allocEj+0xfc>
    65f0:	e0 91 8e 04 	lds	r30, 0x048E
    65f4:	f0 91 8f 04 	lds	r31, 0x048F
    65f8:	df 01       	movw	r26, r30
    65fa:	30 97       	sbiw	r30, 0x00	; 0
    65fc:	09 f4       	brne	.+2      	; 0x6600 <_ZN6System8AVR8_MMU5allocEj+0x28>
    65fe:	6a c0       	rjmp	.+212    	; 0x66d4 <_ZN6System8AVR8_MMU5allocEj+0xfc>
    6600:	22 81       	ldd	r18, Z+2	; 0x02
    6602:	33 81       	ldd	r19, Z+3	; 0x03
    6604:	26 17       	cp	r18, r22
    6606:	37 07       	cpc	r19, r23
    6608:	30 f4       	brcc	.+12     	; 0x6616 <_ZN6System8AVR8_MMU5allocEj+0x3e>
    660a:	04 80       	ldd	r0, Z+4	; 0x04
    660c:	f5 81       	ldd	r31, Z+5	; 0x05
    660e:	e0 2d       	mov	r30, r0
    6610:	30 97       	sbiw	r30, 0x00	; 0
    6612:	b1 f7       	brne	.-20     	; 0x6600 <_ZN6System8AVR8_MMU5allocEj+0x28>
    6614:	5f c0       	rjmp	.+190    	; 0x66d4 <_ZN6System8AVR8_MMU5allocEj+0xfc>
    6616:	26 1b       	sub	r18, r22
    6618:	37 0b       	sbc	r19, r23
    661a:	33 83       	std	Z+3, r19	; 0x03
    661c:	22 83       	std	Z+2, r18	; 0x02
    661e:	80 91 92 04 	lds	r24, 0x0492
    6622:	90 91 93 04 	lds	r25, 0x0493
    6626:	86 1b       	sub	r24, r22
    6628:	97 0b       	sbc	r25, r23
    662a:	90 93 93 04 	sts	0x0493, r25
    662e:	80 93 92 04 	sts	0x0492, r24
    6632:	22 81       	ldd	r18, Z+2	; 0x02
    6634:	33 81       	ldd	r19, Z+3	; 0x03
    6636:	21 15       	cp	r18, r1
    6638:	31 05       	cpc	r19, r1
    663a:	f9 f4       	brne	.+62     	; 0x667a <_ZN6System8AVR8_MMU5allocEj+0xa2>
    663c:	80 91 8c 04 	lds	r24, 0x048C
    6640:	90 91 8d 04 	lds	r25, 0x048D
    6644:	81 30       	cpi	r24, 0x01	; 1
    6646:	91 05       	cpc	r25, r1
    6648:	01 f1       	breq	.+64     	; 0x668a <_ZN6System8AVR8_MMU5allocEj+0xb2>
    664a:	ad 01       	movw	r20, r26
    664c:	ae 17       	cp	r26, r30
    664e:	bf 07       	cpc	r27, r31
    6650:	89 f1       	breq	.+98     	; 0x66b4 <_ZN6System8AVR8_MMU5allocEj+0xdc>
    6652:	10 97       	sbiw	r26, 0x00	; 0
    6654:	59 f0       	breq	.+22     	; 0x666c <_ZN6System8AVR8_MMU5allocEj+0x94>
    6656:	ed 01       	movw	r28, r26
    6658:	ac 81       	ldd	r26, Y+4	; 0x04
    665a:	bd 81       	ldd	r27, Y+5	; 0x05
    665c:	10 97       	sbiw	r26, 0x00	; 0
    665e:	21 f1       	breq	.+72     	; 0x66a8 <_ZN6System8AVR8_MMU5allocEj+0xd0>
    6660:	ae 17       	cp	r26, r30
    6662:	bf 07       	cpc	r27, r31
    6664:	09 f1       	breq	.+66     	; 0x66a8 <_ZN6System8AVR8_MMU5allocEj+0xd0>
    6666:	ad 01       	movw	r20, r26
    6668:	10 97       	sbiw	r26, 0x00	; 0
    666a:	a9 f7       	brne	.-22     	; 0x6656 <_ZN6System8AVR8_MMU5allocEj+0x7e>
    666c:	01 97       	sbiw	r24, 0x01	; 1
    666e:	90 93 8d 04 	sts	0x048D, r25
    6672:	80 93 8c 04 	sts	0x048C, r24
    6676:	22 81       	ldd	r18, Z+2	; 0x02
    6678:	33 81       	ldd	r19, Z+3	; 0x03
    667a:	80 81       	ld	r24, Z
    667c:	91 81       	ldd	r25, Z+1	; 0x01
    667e:	82 0f       	add	r24, r18
    6680:	93 1f       	adc	r25, r19
    6682:	f8 01       	movw	r30, r16
    6684:	91 83       	std	Z+1, r25	; 0x01
    6686:	80 83       	st	Z, r24
    6688:	25 c0       	rjmp	.+74     	; 0x66d4 <_ZN6System8AVR8_MMU5allocEj+0xfc>
    668a:	10 92 8f 04 	sts	0x048F, r1
    668e:	10 92 8e 04 	sts	0x048E, r1
    6692:	10 92 91 04 	sts	0x0491, r1
    6696:	10 92 90 04 	sts	0x0490, r1
    669a:	10 92 8d 04 	sts	0x048D, r1
    669e:	10 92 8c 04 	sts	0x048C, r1
    66a2:	22 81       	ldd	r18, Z+2	; 0x02
    66a4:	33 81       	ldd	r19, Z+3	; 0x03
    66a6:	e9 cf       	rjmp	.-46     	; 0x667a <_ZN6System8AVR8_MMU5allocEj+0xa2>
    66a8:	a4 81       	ldd	r26, Z+4	; 0x04
    66aa:	b5 81       	ldd	r27, Z+5	; 0x05
    66ac:	ea 01       	movw	r28, r20
    66ae:	bd 83       	std	Y+5, r27	; 0x05
    66b0:	ac 83       	std	Y+4, r26	; 0x04
    66b2:	dc cf       	rjmp	.-72     	; 0x666c <_ZN6System8AVR8_MMU5allocEj+0x94>
    66b4:	00 97       	sbiw	r24, 0x00	; 0
    66b6:	09 f3       	breq	.-62     	; 0x667a <_ZN6System8AVR8_MMU5allocEj+0xa2>
    66b8:	a4 81       	ldd	r26, Z+4	; 0x04
    66ba:	b5 81       	ldd	r27, Z+5	; 0x05
    66bc:	b0 93 8f 04 	sts	0x048F, r27
    66c0:	a0 93 8e 04 	sts	0x048E, r26
    66c4:	01 97       	sbiw	r24, 0x01	; 1
    66c6:	90 93 8d 04 	sts	0x048D, r25
    66ca:	80 93 8c 04 	sts	0x048C, r24
    66ce:	22 81       	ldd	r18, Z+2	; 0x02
    66d0:	33 81       	ldd	r19, Z+3	; 0x03
    66d2:	d3 cf       	rjmp	.-90     	; 0x667a <_ZN6System8AVR8_MMU5allocEj+0xa2>
    66d4:	c8 01       	movw	r24, r16
    66d6:	df 91       	pop	r29
    66d8:	cf 91       	pop	r28
    66da:	1f 91       	pop	r17
    66dc:	0f 91       	pop	r16
    66de:	08 95       	ret

000066e0 <__vector_14>:
    66e0:	1f 92       	push	r1
    66e2:	0f 92       	push	r0
    66e4:	0f b6       	in	r0, 0x3f	; 63
    66e6:	0f 92       	push	r0
    66e8:	11 24       	eor	r1, r1
    66ea:	8f 93       	push	r24
    66ec:	9f 93       	push	r25
    66ee:	af 93       	push	r26
    66f0:	bf 93       	push	r27
    66f2:	80 91 94 04 	lds	r24, 0x0494
    66f6:	90 91 95 04 	lds	r25, 0x0495
    66fa:	a0 91 96 04 	lds	r26, 0x0496
    66fe:	b0 91 97 04 	lds	r27, 0x0497
    6702:	01 96       	adiw	r24, 0x01	; 1
    6704:	a1 1d       	adc	r26, r1
    6706:	b1 1d       	adc	r27, r1
    6708:	80 93 94 04 	sts	0x0494, r24
    670c:	90 93 95 04 	sts	0x0495, r25
    6710:	a0 93 96 04 	sts	0x0496, r26
    6714:	b0 93 97 04 	sts	0x0497, r27
    6718:	bf 91       	pop	r27
    671a:	af 91       	pop	r26
    671c:	9f 91       	pop	r25
    671e:	8f 91       	pop	r24
    6720:	0f 90       	pop	r0
    6722:	0f be       	out	0x3f, r0	; 63
    6724:	0f 90       	pop	r0
    6726:	1f 90       	pop	r1
    6728:	18 95       	reti

0000672a <__udivdi3>:
#endif

#ifdef L_udivdi3
UDWtype
__udivdi3 (UDWtype n, UDWtype d)
{
    672a:	ac e6       	ldi	r26, 0x6C	; 108
    672c:	b0 e0       	ldi	r27, 0x00	; 0
    672e:	eb e9       	ldi	r30, 0x9B	; 155
    6730:	f3 e3       	ldi	r31, 0x33	; 51
    6732:	0c 94 b4 41 	jmp	0x8368	; 0x8368 <__prologue_saves__>
    6736:	22 2e       	mov	r2, r18
    6738:	33 2e       	mov	r3, r19
    673a:	44 2e       	mov	r4, r20
    673c:	55 2e       	mov	r5, r21
    673e:	66 2e       	mov	r6, r22
    6740:	77 2e       	mov	r7, r23
    6742:	88 2e       	mov	r8, r24
    6744:	99 2e       	mov	r9, r25
    6746:	6e 2d       	mov	r22, r14
static inline __attribute__ ((__always_inline__))
#endif
UDWtype
__udivmoddi4 (UDWtype n, UDWtype d, UDWtype *rp)
{
  const DWunion nn = {.ll = n};
    6748:	a8 e0       	ldi	r26, 0x08	; 8
    674a:	fe 01       	movw	r30, r28
    674c:	31 96       	adiw	r30, 0x01	; 1
    674e:	ea 2e       	mov	r14, r26
    6750:	11 92       	st	Z+, r1
    6752:	ea 94       	dec	r14
    6754:	e9 f7       	brne	.-6      	; 0x6750 <__udivdi3+0x26>
    6756:	29 82       	std	Y+1, r2	; 0x01
    6758:	3a 82       	std	Y+2, r3	; 0x02
    675a:	4b 82       	std	Y+3, r4	; 0x03
    675c:	5c 82       	std	Y+4, r5	; 0x04
    675e:	6d 82       	std	Y+5, r6	; 0x05
    6760:	7e 82       	std	Y+6, r7	; 0x06
    6762:	8f 82       	std	Y+7, r8	; 0x07
    6764:	98 86       	std	Y+8, r9	; 0x08
  const DWunion dd = {.ll = d};
    6766:	fe 01       	movw	r30, r28
    6768:	39 96       	adiw	r30, 0x09	; 9
    676a:	11 92       	st	Z+, r1
    676c:	aa 95       	dec	r26
    676e:	e9 f7       	brne	.-6      	; 0x676a <__udivdi3+0x40>
    6770:	a9 86       	std	Y+9, r10	; 0x09
    6772:	ba 86       	std	Y+10, r11	; 0x0a
    6774:	cb 86       	std	Y+11, r12	; 0x0b
    6776:	dc 86       	std	Y+12, r13	; 0x0c
    6778:	6d 87       	std	Y+13, r22	; 0x0d
    677a:	fe 86       	std	Y+14, r15	; 0x0e
    677c:	0f 87       	std	Y+15, r16	; 0x0f
    677e:	18 8b       	std	Y+16, r17	; 0x10
  DWunion rr;
  UWtype d0, d1, n0, n1, n2;
  UWtype q0, q1;
  UWtype b, bm;

  d0 = dd.s.low;
    6780:	a9 84       	ldd	r10, Y+9	; 0x09
    6782:	ba 84       	ldd	r11, Y+10	; 0x0a
    6784:	cb 84       	ldd	r12, Y+11	; 0x0b
    6786:	dc 84       	ldd	r13, Y+12	; 0x0c
  d1 = dd.s.high;
    6788:	ed 84       	ldd	r14, Y+13	; 0x0d
    678a:	fe 84       	ldd	r15, Y+14	; 0x0e
    678c:	0f 85       	ldd	r16, Y+15	; 0x0f
    678e:	18 89       	ldd	r17, Y+16	; 0x10
  n0 = nn.s.low;
    6790:	29 81       	ldd	r18, Y+1	; 0x01
    6792:	3a 81       	ldd	r19, Y+2	; 0x02
    6794:	4b 81       	ldd	r20, Y+3	; 0x03
    6796:	5c 81       	ldd	r21, Y+4	; 0x04
    6798:	a9 96       	adiw	r28, 0x29	; 41
    679a:	2c af       	std	Y+60, r18	; 0x3c
    679c:	3d af       	std	Y+61, r19	; 0x3d
    679e:	4e af       	std	Y+62, r20	; 0x3e
    67a0:	5f af       	std	Y+63, r21	; 0x3f
    67a2:	a9 97       	sbiw	r28, 0x29	; 41
  n1 = nn.s.high;
    67a4:	6d 80       	ldd	r6, Y+5	; 0x05
    67a6:	7e 80       	ldd	r7, Y+6	; 0x06
    67a8:	8f 80       	ldd	r8, Y+7	; 0x07
    67aa:	98 84       	ldd	r9, Y+8	; 0x08
	}
    }

#else /* UDIV_NEEDS_NORMALIZATION */

  if (d1 == 0)
    67ac:	e1 14       	cp	r14, r1
    67ae:	f1 04       	cpc	r15, r1
    67b0:	01 05       	cpc	r16, r1
    67b2:	11 05       	cpc	r17, r1
    67b4:	09 f0       	breq	.+2      	; 0x67b8 <__udivdi3+0x8e>
    67b6:	f5 c0       	rjmp	.+490    	; 0x69a2 <__udivdi3+0x278>
    {
      if (d0 > n1)
    67b8:	6a 14       	cp	r6, r10
    67ba:	7b 04       	cpc	r7, r11
    67bc:	8c 04       	cpc	r8, r12
    67be:	9d 04       	cpc	r9, r13
    67c0:	08 f4       	brcc	.+2      	; 0x67c4 <__udivdi3+0x9a>
    67c2:	4a c1       	rjmp	.+660    	; 0x6a58 <__udivdi3+0x32e>
	}
      else
	{
	  /* qq = NN / 0d */

	  if (d0 == 0)
    67c4:	a1 14       	cp	r10, r1
    67c6:	b1 04       	cpc	r11, r1
    67c8:	c1 04       	cpc	r12, r1
    67ca:	d1 04       	cpc	r13, r1
    67cc:	09 f4       	brne	.+2      	; 0x67d0 <__udivdi3+0xa6>
    67ce:	84 c2       	rjmp	.+1288   	; 0x6cd8 <__udivdi3+0x5ae>
	    d0 = 1 / d0;	/* Divide intentionally by zero.  */

	  count_leading_zeros (bm, d0);
    67d0:	00 e0       	ldi	r16, 0x00	; 0
    67d2:	a0 16       	cp	r10, r16
    67d4:	00 e0       	ldi	r16, 0x00	; 0
    67d6:	b0 06       	cpc	r11, r16
    67d8:	01 e0       	ldi	r16, 0x01	; 1
    67da:	c0 06       	cpc	r12, r16
    67dc:	00 e0       	ldi	r16, 0x00	; 0
    67de:	d0 06       	cpc	r13, r16
    67e0:	08 f0       	brcs	.+2      	; 0x67e4 <__udivdi3+0xba>
    67e2:	69 c2       	rjmp	.+1234   	; 0x6cb6 <__udivdi3+0x58c>
    67e4:	1f ef       	ldi	r17, 0xFF	; 255
    67e6:	a1 16       	cp	r10, r17
    67e8:	b1 04       	cpc	r11, r1
    67ea:	c1 04       	cpc	r12, r1
    67ec:	d1 04       	cpc	r13, r1
    67ee:	11 f0       	breq	.+4      	; 0x67f4 <__udivdi3+0xca>
    67f0:	08 f0       	brcs	.+2      	; 0x67f4 <__udivdi3+0xca>
    67f2:	42 c6       	rjmp	.+3204   	; 0x7478 <__udivdi3+0xd4e>
    67f4:	20 e0       	ldi	r18, 0x00	; 0
    67f6:	30 e0       	ldi	r19, 0x00	; 0
    67f8:	40 e0       	ldi	r20, 0x00	; 0
    67fa:	50 e0       	ldi	r21, 0x00	; 0
    67fc:	80 e0       	ldi	r24, 0x00	; 0
    67fe:	90 e0       	ldi	r25, 0x00	; 0
    6800:	15 01       	movw	r2, r10
    6802:	26 01       	movw	r4, r12
    6804:	04 c0       	rjmp	.+8      	; 0x680e <__udivdi3+0xe4>
    6806:	56 94       	lsr	r5
    6808:	47 94       	ror	r4
    680a:	37 94       	ror	r3
    680c:	27 94       	ror	r2
    680e:	8a 95       	dec	r24
    6810:	d2 f7       	brpl	.-12     	; 0x6806 <__udivdi3+0xdc>
    6812:	d2 01       	movw	r26, r4
    6814:	c1 01       	movw	r24, r2
    6816:	86 59       	subi	r24, 0x96	; 150
    6818:	9d 4f       	sbci	r25, 0xFD	; 253
    681a:	dc 01       	movw	r26, r24
    681c:	8c 91       	ld	r24, X
    681e:	28 0f       	add	r18, r24
    6820:	31 1d       	adc	r19, r1
    6822:	41 1d       	adc	r20, r1
    6824:	51 1d       	adc	r21, r1
    6826:	da 01       	movw	r26, r20
    6828:	c9 01       	movw	r24, r18
    682a:	20 e2       	ldi	r18, 0x20	; 32
    682c:	30 e0       	ldi	r19, 0x00	; 0
    682e:	40 e0       	ldi	r20, 0x00	; 0
    6830:	50 e0       	ldi	r21, 0x00	; 0
    6832:	19 01       	movw	r2, r18
    6834:	2a 01       	movw	r4, r20
    6836:	28 1a       	sub	r2, r24
    6838:	39 0a       	sbc	r3, r25
    683a:	4a 0a       	sbc	r4, r26
    683c:	5b 0a       	sbc	r5, r27

	  if (bm == 0)
    683e:	09 f0       	breq	.+2      	; 0x6842 <__udivdi3+0x118>
    6840:	b6 c4       	rjmp	.+2412   	; 0x71ae <__udivdi3+0xa84>
		 leading quotient digit q1 = 1).

		 This special case is necessary, not an optimization.
		 (Shifts counts of W_TYPE_SIZE are undefined.)  */

	      n1 -= d0;
    6842:	84 01       	movw	r16, r8
    6844:	73 01       	movw	r14, r6
    6846:	ea 18       	sub	r14, r10
    6848:	fb 08       	sbc	r15, r11
    684a:	0c 09       	sbc	r16, r12
    684c:	1d 09       	sbc	r17, r13
    684e:	26 01       	movw	r4, r12
    6850:	66 24       	eor	r6, r6
    6852:	77 24       	eor	r7, r7
    6854:	61 96       	adiw	r28, 0x11	; 17
    6856:	4c ae       	std	Y+60, r4	; 0x3c
    6858:	5d ae       	std	Y+61, r5	; 0x3d
    685a:	6e ae       	std	Y+62, r6	; 0x3e
    685c:	7f ae       	std	Y+63, r7	; 0x3f
    685e:	61 97       	sbiw	r28, 0x11	; 17
    6860:	a6 01       	movw	r20, r12
    6862:	95 01       	movw	r18, r10
    6864:	40 70       	andi	r20, 0x00	; 0
    6866:	50 70       	andi	r21, 0x00	; 0
    6868:	2d 96       	adiw	r28, 0x0d	; 13
    686a:	2c af       	std	Y+60, r18	; 0x3c
    686c:	3d af       	std	Y+61, r19	; 0x3d
    686e:	4e af       	std	Y+62, r20	; 0x3e
    6870:	5f af       	std	Y+63, r21	; 0x3f
    6872:	2d 97       	sbiw	r28, 0x0d	; 13
    6874:	81 e0       	ldi	r24, 0x01	; 1
    6876:	90 e0       	ldi	r25, 0x00	; 0
    6878:	a0 e0       	ldi	r26, 0x00	; 0
    687a:	b0 e0       	ldi	r27, 0x00	; 0
    687c:	a1 96       	adiw	r28, 0x21	; 33
    687e:	8c af       	std	Y+60, r24	; 0x3c
    6880:	9d af       	std	Y+61, r25	; 0x3d
    6882:	ae af       	std	Y+62, r26	; 0x3e
    6884:	bf af       	std	Y+63, r27	; 0x3f
    6886:	a1 97       	sbiw	r28, 0x21	; 33
	      udiv_qrnnd (q1, n1, n2, n1, d0);
	    }

	  /* n1 != d0...  */

	  udiv_qrnnd (q0, n0, n1, n0, d0);
    6888:	c8 01       	movw	r24, r16
    688a:	b7 01       	movw	r22, r14
    688c:	61 96       	adiw	r28, 0x11	; 17
    688e:	2c ad       	ldd	r18, Y+60	; 0x3c
    6890:	3d ad       	ldd	r19, Y+61	; 0x3d
    6892:	4e ad       	ldd	r20, Y+62	; 0x3e
    6894:	5f ad       	ldd	r21, Y+63	; 0x3f
    6896:	61 97       	sbiw	r28, 0x11	; 17
    6898:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    689c:	19 01       	movw	r2, r18
    689e:	2a 01       	movw	r4, r20
    68a0:	2d 96       	adiw	r28, 0x0d	; 13
    68a2:	6c ad       	ldd	r22, Y+60	; 0x3c
    68a4:	7d ad       	ldd	r23, Y+61	; 0x3d
    68a6:	8e ad       	ldd	r24, Y+62	; 0x3e
    68a8:	9f ad       	ldd	r25, Y+63	; 0x3f
    68aa:	2d 97       	sbiw	r28, 0x0d	; 13
    68ac:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    68b0:	3b 01       	movw	r6, r22
    68b2:	4c 01       	movw	r8, r24
    68b4:	c8 01       	movw	r24, r16
    68b6:	b7 01       	movw	r22, r14
    68b8:	61 96       	adiw	r28, 0x11	; 17
    68ba:	2c ad       	ldd	r18, Y+60	; 0x3c
    68bc:	3d ad       	ldd	r19, Y+61	; 0x3d
    68be:	4e ad       	ldd	r20, Y+62	; 0x3e
    68c0:	5f ad       	ldd	r21, Y+63	; 0x3f
    68c2:	61 97       	sbiw	r28, 0x11	; 17
    68c4:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    68c8:	cb 01       	movw	r24, r22
    68ca:	77 27       	eor	r23, r23
    68cc:	66 27       	eor	r22, r22
    68ce:	a9 96       	adiw	r28, 0x29	; 41
    68d0:	ec ac       	ldd	r14, Y+60	; 0x3c
    68d2:	fd ac       	ldd	r15, Y+61	; 0x3d
    68d4:	0e ad       	ldd	r16, Y+62	; 0x3e
    68d6:	1f ad       	ldd	r17, Y+63	; 0x3f
    68d8:	a9 97       	sbiw	r28, 0x29	; 41
    68da:	98 01       	movw	r18, r16
    68dc:	44 27       	eor	r20, r20
    68de:	55 27       	eor	r21, r21
    68e0:	7b 01       	movw	r14, r22
    68e2:	8c 01       	movw	r16, r24
    68e4:	e2 2a       	or	r14, r18
    68e6:	f3 2a       	or	r15, r19
    68e8:	04 2b       	or	r16, r20
    68ea:	15 2b       	or	r17, r21
    68ec:	e6 14       	cp	r14, r6
    68ee:	f7 04       	cpc	r15, r7
    68f0:	08 05       	cpc	r16, r8
    68f2:	19 05       	cpc	r17, r9
    68f4:	08 f4       	brcc	.+2      	; 0x68f8 <__udivdi3+0x1ce>
    68f6:	2f c4       	rjmp	.+2142   	; 0x7156 <__udivdi3+0xa2c>
    68f8:	25 96       	adiw	r28, 0x05	; 5
    68fa:	2c ae       	std	Y+60, r2	; 0x3c
    68fc:	3d ae       	std	Y+61, r3	; 0x3d
    68fe:	4e ae       	std	Y+62, r4	; 0x3e
    6900:	5f ae       	std	Y+63, r5	; 0x3f
    6902:	25 97       	sbiw	r28, 0x05	; 5
    6904:	e6 18       	sub	r14, r6
    6906:	f7 08       	sbc	r15, r7
    6908:	08 09       	sbc	r16, r8
    690a:	19 09       	sbc	r17, r9
    690c:	c8 01       	movw	r24, r16
    690e:	b7 01       	movw	r22, r14
    6910:	61 96       	adiw	r28, 0x11	; 17
    6912:	2c ad       	ldd	r18, Y+60	; 0x3c
    6914:	3d ad       	ldd	r19, Y+61	; 0x3d
    6916:	4e ad       	ldd	r20, Y+62	; 0x3e
    6918:	5f ad       	ldd	r21, Y+63	; 0x3f
    691a:	61 97       	sbiw	r28, 0x11	; 17
    691c:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    6920:	39 01       	movw	r6, r18
    6922:	4a 01       	movw	r8, r20
    6924:	2d 96       	adiw	r28, 0x0d	; 13
    6926:	6c ad       	ldd	r22, Y+60	; 0x3c
    6928:	7d ad       	ldd	r23, Y+61	; 0x3d
    692a:	8e ad       	ldd	r24, Y+62	; 0x3e
    692c:	9f ad       	ldd	r25, Y+63	; 0x3f
    692e:	2d 97       	sbiw	r28, 0x0d	; 13
    6930:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    6934:	1b 01       	movw	r2, r22
    6936:	2c 01       	movw	r4, r24
    6938:	c8 01       	movw	r24, r16
    693a:	b7 01       	movw	r22, r14
    693c:	61 96       	adiw	r28, 0x11	; 17
    693e:	2c ad       	ldd	r18, Y+60	; 0x3c
    6940:	3d ad       	ldd	r19, Y+61	; 0x3d
    6942:	4e ad       	ldd	r20, Y+62	; 0x3e
    6944:	5f ad       	ldd	r21, Y+63	; 0x3f
    6946:	61 97       	sbiw	r28, 0x11	; 17
    6948:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    694c:	cb 01       	movw	r24, r22
    694e:	77 27       	eor	r23, r23
    6950:	66 27       	eor	r22, r22
    6952:	a9 96       	adiw	r28, 0x29	; 41
    6954:	2c ad       	ldd	r18, Y+60	; 0x3c
    6956:	3d ad       	ldd	r19, Y+61	; 0x3d
    6958:	4e ad       	ldd	r20, Y+62	; 0x3e
    695a:	5f ad       	ldd	r21, Y+63	; 0x3f
    695c:	a9 97       	sbiw	r28, 0x29	; 41
    695e:	40 70       	andi	r20, 0x00	; 0
    6960:	50 70       	andi	r21, 0x00	; 0
    6962:	26 2b       	or	r18, r22
    6964:	37 2b       	or	r19, r23
    6966:	48 2b       	or	r20, r24
    6968:	59 2b       	or	r21, r25
    696a:	22 15       	cp	r18, r2
    696c:	33 05       	cpc	r19, r3
    696e:	44 05       	cpc	r20, r4
    6970:	55 05       	cpc	r21, r5
    6972:	08 f4       	brcc	.+2      	; 0x6976 <__udivdi3+0x24c>
    6974:	cc c3       	rjmp	.+1944   	; 0x710e <__udivdi3+0x9e4>
    6976:	84 01       	movw	r16, r8
    6978:	73 01       	movw	r14, r6
    697a:	25 96       	adiw	r28, 0x05	; 5
    697c:	2c ac       	ldd	r2, Y+60	; 0x3c
    697e:	3d ac       	ldd	r3, Y+61	; 0x3d
    6980:	4e ac       	ldd	r4, Y+62	; 0x3e
    6982:	5f ac       	ldd	r5, Y+63	; 0x3f
    6984:	25 97       	sbiw	r28, 0x05	; 5
    6986:	d1 01       	movw	r26, r2
    6988:	99 27       	eor	r25, r25
    698a:	88 27       	eor	r24, r24
    698c:	e8 2a       	or	r14, r24
    698e:	f9 2a       	or	r15, r25
    6990:	0a 2b       	or	r16, r26
    6992:	1b 2b       	or	r17, r27
    6994:	a1 96       	adiw	r28, 0x21	; 33
    6996:	2c ad       	ldd	r18, Y+60	; 0x3c
    6998:	3d ad       	ldd	r19, Y+61	; 0x3d
    699a:	4e ad       	ldd	r20, Y+62	; 0x3e
    699c:	5f ad       	ldd	r21, Y+63	; 0x3f
    699e:	a1 97       	sbiw	r28, 0x21	; 33
    69a0:	d4 c5       	rjmp	.+2984   	; 0x754a <__udivdi3+0xe20>
    }
#endif /* UDIV_NEEDS_NORMALIZATION */

  else
    {
      if (d1 > n1)
    69a2:	6e 14       	cp	r6, r14
    69a4:	7f 04       	cpc	r7, r15
    69a6:	80 06       	cpc	r8, r16
    69a8:	91 06       	cpc	r9, r17
    69aa:	08 f4       	brcc	.+2      	; 0x69ae <__udivdi3+0x284>
    69ac:	7c c1       	rjmp	.+760    	; 0x6ca6 <__udivdi3+0x57c>
	}
      else
	{
	  /* 0q = NN / dd */

	  count_leading_zeros (bm, d1);
    69ae:	20 e0       	ldi	r18, 0x00	; 0
    69b0:	e2 16       	cp	r14, r18
    69b2:	20 e0       	ldi	r18, 0x00	; 0
    69b4:	f2 06       	cpc	r15, r18
    69b6:	21 e0       	ldi	r18, 0x01	; 1
    69b8:	02 07       	cpc	r16, r18
    69ba:	20 e0       	ldi	r18, 0x00	; 0
    69bc:	12 07       	cpc	r17, r18
    69be:	08 f0       	brcs	.+2      	; 0x69c2 <__udivdi3+0x298>
    69c0:	61 c1       	rjmp	.+706    	; 0x6c84 <__udivdi3+0x55a>
    69c2:	3f ef       	ldi	r19, 0xFF	; 255
    69c4:	e3 16       	cp	r14, r19
    69c6:	f1 04       	cpc	r15, r1
    69c8:	01 05       	cpc	r16, r1
    69ca:	11 05       	cpc	r17, r1
    69cc:	11 f0       	breq	.+4      	; 0x69d2 <__udivdi3+0x2a8>
    69ce:	08 f0       	brcs	.+2      	; 0x69d2 <__udivdi3+0x2a8>
    69d0:	1d c5       	rjmp	.+2618   	; 0x740c <__udivdi3+0xce2>
    69d2:	20 e0       	ldi	r18, 0x00	; 0
    69d4:	30 e0       	ldi	r19, 0x00	; 0
    69d6:	40 e0       	ldi	r20, 0x00	; 0
    69d8:	50 e0       	ldi	r21, 0x00	; 0
    69da:	80 e0       	ldi	r24, 0x00	; 0
    69dc:	90 e0       	ldi	r25, 0x00	; 0
    69de:	17 01       	movw	r2, r14
    69e0:	28 01       	movw	r4, r16
    69e2:	04 c0       	rjmp	.+8      	; 0x69ec <__udivdi3+0x2c2>
    69e4:	56 94       	lsr	r5
    69e6:	47 94       	ror	r4
    69e8:	37 94       	ror	r3
    69ea:	27 94       	ror	r2
    69ec:	8a 95       	dec	r24
    69ee:	d2 f7       	brpl	.-12     	; 0x69e4 <__udivdi3+0x2ba>
    69f0:	d2 01       	movw	r26, r4
    69f2:	c1 01       	movw	r24, r2
    69f4:	86 59       	subi	r24, 0x96	; 150
    69f6:	9d 4f       	sbci	r25, 0xFD	; 253
    69f8:	dc 01       	movw	r26, r24
    69fa:	8c 91       	ld	r24, X
    69fc:	28 0f       	add	r18, r24
    69fe:	31 1d       	adc	r19, r1
    6a00:	41 1d       	adc	r20, r1
    6a02:	51 1d       	adc	r21, r1
    6a04:	da 01       	movw	r26, r20
    6a06:	c9 01       	movw	r24, r18
    6a08:	60 e2       	ldi	r22, 0x20	; 32
    6a0a:	26 2e       	mov	r2, r22
    6a0c:	31 2c       	mov	r3, r1
    6a0e:	41 2c       	mov	r4, r1
    6a10:	51 2c       	mov	r5, r1
    6a12:	a2 01       	movw	r20, r4
    6a14:	91 01       	movw	r18, r2
    6a16:	28 1b       	sub	r18, r24
    6a18:	39 0b       	sbc	r19, r25
    6a1a:	4a 0b       	sbc	r20, r26
    6a1c:	5b 0b       	sbc	r21, r27
	  if (bm == 0)
    6a1e:	09 f0       	breq	.+2      	; 0x6a22 <__udivdi3+0x2f8>
    6a20:	82 c1       	rjmp	.+772    	; 0x6d26 <__udivdi3+0x5fc>

		 This special case is necessary, not an optimization.  */

	      /* The condition on the next line takes advantage of that
		 n1 >= d1 (true due to program flow).  */
	      if (n1 > d1 || n0 >= d0)
    6a22:	e6 14       	cp	r14, r6
    6a24:	f7 04       	cpc	r15, r7
    6a26:	08 05       	cpc	r16, r8
    6a28:	19 05       	cpc	r17, r9
    6a2a:	60 f0       	brcs	.+24     	; 0x6a44 <__udivdi3+0x31a>
    6a2c:	a9 96       	adiw	r28, 0x29	; 41
    6a2e:	2c ac       	ldd	r2, Y+60	; 0x3c
    6a30:	3d ac       	ldd	r3, Y+61	; 0x3d
    6a32:	4e ac       	ldd	r4, Y+62	; 0x3e
    6a34:	5f ac       	ldd	r5, Y+63	; 0x3f
    6a36:	a9 97       	sbiw	r28, 0x29	; 41
    6a38:	2a 14       	cp	r2, r10
    6a3a:	3b 04       	cpc	r3, r11
    6a3c:	4c 04       	cpc	r4, r12
    6a3e:	5d 04       	cpc	r5, r13
    6a40:	08 f4       	brcc	.+2      	; 0x6a44 <__udivdi3+0x31a>
    6a42:	31 c1       	rjmp	.+610    	; 0x6ca6 <__udivdi3+0x57c>
	      n0 = n0 << bm;

	      udiv_qrnnd (q0, n1, n2, n1, d1);
	      umul_ppmm (m1, m0, q0, d0);

	      if (m1 > n1 || (m1 == n1 && m0 > n0))
    6a44:	81 e0       	ldi	r24, 0x01	; 1
    6a46:	e8 2e       	mov	r14, r24
    6a48:	f1 2c       	mov	r15, r1
    6a4a:	01 2d       	mov	r16, r1
    6a4c:	11 2d       	mov	r17, r1
    6a4e:	20 e0       	ldi	r18, 0x00	; 0
    6a50:	30 e0       	ldi	r19, 0x00	; 0
    6a52:	40 e0       	ldi	r20, 0x00	; 0
    6a54:	50 e0       	ldi	r21, 0x00	; 0
    6a56:	79 c5       	rjmp	.+2802   	; 0x754a <__udivdi3+0xe20>
    {
      if (d0 > n1)
	{
	  /* 0q = nn / 0D */

	  count_leading_zeros (bm, d0);
    6a58:	30 e0       	ldi	r19, 0x00	; 0
    6a5a:	a3 16       	cp	r10, r19
    6a5c:	30 e0       	ldi	r19, 0x00	; 0
    6a5e:	b3 06       	cpc	r11, r19
    6a60:	31 e0       	ldi	r19, 0x01	; 1
    6a62:	c3 06       	cpc	r12, r19
    6a64:	30 e0       	ldi	r19, 0x00	; 0
    6a66:	d3 06       	cpc	r13, r19
    6a68:	08 f0       	brcs	.+2      	; 0x6a6c <__udivdi3+0x342>
    6a6a:	4c c1       	rjmp	.+664    	; 0x6d04 <__udivdi3+0x5da>
    6a6c:	4f ef       	ldi	r20, 0xFF	; 255
    6a6e:	a4 16       	cp	r10, r20
    6a70:	b1 04       	cpc	r11, r1
    6a72:	c1 04       	cpc	r12, r1
    6a74:	d1 04       	cpc	r13, r1
    6a76:	11 f0       	breq	.+4      	; 0x6a7c <__udivdi3+0x352>
    6a78:	08 f0       	brcs	.+2      	; 0x6a7c <__udivdi3+0x352>
    6a7a:	05 c5       	rjmp	.+2570   	; 0x7486 <__udivdi3+0xd5c>
    6a7c:	20 e0       	ldi	r18, 0x00	; 0
    6a7e:	30 e0       	ldi	r19, 0x00	; 0
    6a80:	40 e0       	ldi	r20, 0x00	; 0
    6a82:	50 e0       	ldi	r21, 0x00	; 0
    6a84:	80 e0       	ldi	r24, 0x00	; 0
    6a86:	90 e0       	ldi	r25, 0x00	; 0
    6a88:	15 01       	movw	r2, r10
    6a8a:	26 01       	movw	r4, r12
    6a8c:	04 c0       	rjmp	.+8      	; 0x6a96 <__udivdi3+0x36c>
    6a8e:	56 94       	lsr	r5
    6a90:	47 94       	ror	r4
    6a92:	37 94       	ror	r3
    6a94:	27 94       	ror	r2
    6a96:	8a 95       	dec	r24
    6a98:	d2 f7       	brpl	.-12     	; 0x6a8e <__udivdi3+0x364>
    6a9a:	d2 01       	movw	r26, r4
    6a9c:	c1 01       	movw	r24, r2
    6a9e:	86 59       	subi	r24, 0x96	; 150
    6aa0:	9d 4f       	sbci	r25, 0xFD	; 253
    6aa2:	dc 01       	movw	r26, r24
    6aa4:	8c 91       	ld	r24, X
    6aa6:	28 0f       	add	r18, r24
    6aa8:	31 1d       	adc	r19, r1
    6aaa:	41 1d       	adc	r20, r1
    6aac:	51 1d       	adc	r21, r1
    6aae:	80 e2       	ldi	r24, 0x20	; 32
    6ab0:	90 e0       	ldi	r25, 0x00	; 0
    6ab2:	a0 e0       	ldi	r26, 0x00	; 0
    6ab4:	b0 e0       	ldi	r27, 0x00	; 0
    6ab6:	82 1b       	sub	r24, r18
    6ab8:	93 0b       	sbc	r25, r19
    6aba:	a4 0b       	sbc	r26, r20
    6abc:	b5 0b       	sbc	r27, r21
    6abe:	ac 01       	movw	r20, r24
    6ac0:	bd 01       	movw	r22, r26

	  if (bm != 0)
    6ac2:	e1 f1       	breq	.+120    	; 0x6b3c <__udivdi3+0x412>
	    {
	      /* Normalize, i.e. make the most significant bit of the
		 denominator set.  */

	      d0 = d0 << bm;
    6ac4:	08 2e       	mov	r0, r24
    6ac6:	04 c0       	rjmp	.+8      	; 0x6ad0 <__udivdi3+0x3a6>
    6ac8:	aa 0c       	add	r10, r10
    6aca:	bb 1c       	adc	r11, r11
    6acc:	cc 1c       	adc	r12, r12
    6ace:	dd 1c       	adc	r13, r13
    6ad0:	0a 94       	dec	r0
    6ad2:	d2 f7       	brpl	.-12     	; 0x6ac8 <__udivdi3+0x39e>
	      n1 = (n1 << bm) | (n0 >> (W_TYPE_SIZE - bm));
    6ad4:	84 01       	movw	r16, r8
    6ad6:	73 01       	movw	r14, r6
    6ad8:	08 2e       	mov	r0, r24
    6ada:	04 c0       	rjmp	.+8      	; 0x6ae4 <__udivdi3+0x3ba>
    6adc:	ee 0c       	add	r14, r14
    6ade:	ff 1c       	adc	r15, r15
    6ae0:	00 1f       	adc	r16, r16
    6ae2:	11 1f       	adc	r17, r17
    6ae4:	0a 94       	dec	r0
    6ae6:	d2 f7       	brpl	.-12     	; 0x6adc <__udivdi3+0x3b2>
    6ae8:	20 e2       	ldi	r18, 0x20	; 32
    6aea:	30 e0       	ldi	r19, 0x00	; 0
    6aec:	24 1b       	sub	r18, r20
    6aee:	35 0b       	sbc	r19, r21
    6af0:	a9 96       	adiw	r28, 0x29	; 41
    6af2:	2c ac       	ldd	r2, Y+60	; 0x3c
    6af4:	3d ac       	ldd	r3, Y+61	; 0x3d
    6af6:	4e ac       	ldd	r4, Y+62	; 0x3e
    6af8:	5f ac       	ldd	r5, Y+63	; 0x3f
    6afa:	a9 97       	sbiw	r28, 0x29	; 41
    6afc:	04 c0       	rjmp	.+8      	; 0x6b06 <__udivdi3+0x3dc>
    6afe:	56 94       	lsr	r5
    6b00:	47 94       	ror	r4
    6b02:	37 94       	ror	r3
    6b04:	27 94       	ror	r2
    6b06:	2a 95       	dec	r18
    6b08:	d2 f7       	brpl	.-12     	; 0x6afe <__udivdi3+0x3d4>
    6b0a:	37 01       	movw	r6, r14
    6b0c:	48 01       	movw	r8, r16
    6b0e:	62 28       	or	r6, r2
    6b10:	73 28       	or	r7, r3
    6b12:	84 28       	or	r8, r4
    6b14:	95 28       	or	r9, r5
	      n0 = n0 << bm;
    6b16:	a9 96       	adiw	r28, 0x29	; 41
    6b18:	ec ac       	ldd	r14, Y+60	; 0x3c
    6b1a:	fd ac       	ldd	r15, Y+61	; 0x3d
    6b1c:	0e ad       	ldd	r16, Y+62	; 0x3e
    6b1e:	1f ad       	ldd	r17, Y+63	; 0x3f
    6b20:	a9 97       	sbiw	r28, 0x29	; 41
    6b22:	04 c0       	rjmp	.+8      	; 0x6b2c <__udivdi3+0x402>
    6b24:	ee 0c       	add	r14, r14
    6b26:	ff 1c       	adc	r15, r15
    6b28:	00 1f       	adc	r16, r16
    6b2a:	11 1f       	adc	r17, r17
    6b2c:	8a 95       	dec	r24
    6b2e:	d2 f7       	brpl	.-12     	; 0x6b24 <__udivdi3+0x3fa>
    6b30:	a9 96       	adiw	r28, 0x29	; 41
    6b32:	ec ae       	std	Y+60, r14	; 0x3c
    6b34:	fd ae       	std	Y+61, r15	; 0x3d
    6b36:	0e af       	std	Y+62, r16	; 0x3e
    6b38:	1f af       	std	Y+63, r17	; 0x3f
    6b3a:	a9 97       	sbiw	r28, 0x29	; 41
	    }

	  udiv_qrnnd (q0, n0, n1, n0, d0);
    6b3c:	86 01       	movw	r16, r12
    6b3e:	22 27       	eor	r18, r18
    6b40:	33 27       	eor	r19, r19
    6b42:	6d 96       	adiw	r28, 0x1d	; 29
    6b44:	0c af       	std	Y+60, r16	; 0x3c
    6b46:	1d af       	std	Y+61, r17	; 0x3d
    6b48:	2e af       	std	Y+62, r18	; 0x3e
    6b4a:	3f af       	std	Y+63, r19	; 0x3f
    6b4c:	6d 97       	sbiw	r28, 0x1d	; 29
    6b4e:	a6 01       	movw	r20, r12
    6b50:	95 01       	movw	r18, r10
    6b52:	40 70       	andi	r20, 0x00	; 0
    6b54:	50 70       	andi	r21, 0x00	; 0
    6b56:	69 96       	adiw	r28, 0x19	; 25
    6b58:	2c af       	std	Y+60, r18	; 0x3c
    6b5a:	3d af       	std	Y+61, r19	; 0x3d
    6b5c:	4e af       	std	Y+62, r20	; 0x3e
    6b5e:	5f af       	std	Y+63, r21	; 0x3f
    6b60:	69 97       	sbiw	r28, 0x19	; 25
    6b62:	c4 01       	movw	r24, r8
    6b64:	b3 01       	movw	r22, r6
    6b66:	6d 96       	adiw	r28, 0x1d	; 29
    6b68:	2c ad       	ldd	r18, Y+60	; 0x3c
    6b6a:	3d ad       	ldd	r19, Y+61	; 0x3d
    6b6c:	4e ad       	ldd	r20, Y+62	; 0x3e
    6b6e:	5f ad       	ldd	r21, Y+63	; 0x3f
    6b70:	6d 97       	sbiw	r28, 0x1d	; 29
    6b72:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    6b76:	2d a7       	std	Y+45, r18	; 0x2d
    6b78:	3e a7       	std	Y+46, r19	; 0x2e
    6b7a:	4f a7       	std	Y+47, r20	; 0x2f
    6b7c:	58 ab       	std	Y+48, r21	; 0x30
    6b7e:	69 96       	adiw	r28, 0x19	; 25
    6b80:	6c ad       	ldd	r22, Y+60	; 0x3c
    6b82:	7d ad       	ldd	r23, Y+61	; 0x3d
    6b84:	8e ad       	ldd	r24, Y+62	; 0x3e
    6b86:	9f ad       	ldd	r25, Y+63	; 0x3f
    6b88:	69 97       	sbiw	r28, 0x19	; 25
    6b8a:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    6b8e:	1b 01       	movw	r2, r22
    6b90:	2c 01       	movw	r4, r24
    6b92:	c4 01       	movw	r24, r8
    6b94:	b3 01       	movw	r22, r6
    6b96:	6d 96       	adiw	r28, 0x1d	; 29
    6b98:	2c ad       	ldd	r18, Y+60	; 0x3c
    6b9a:	3d ad       	ldd	r19, Y+61	; 0x3d
    6b9c:	4e ad       	ldd	r20, Y+62	; 0x3e
    6b9e:	5f ad       	ldd	r21, Y+63	; 0x3f
    6ba0:	6d 97       	sbiw	r28, 0x1d	; 29
    6ba2:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    6ba6:	cb 01       	movw	r24, r22
    6ba8:	77 27       	eor	r23, r23
    6baa:	66 27       	eor	r22, r22
    6bac:	a9 96       	adiw	r28, 0x29	; 41
    6bae:	6c ac       	ldd	r6, Y+60	; 0x3c
    6bb0:	7d ac       	ldd	r7, Y+61	; 0x3d
    6bb2:	8e ac       	ldd	r8, Y+62	; 0x3e
    6bb4:	9f ac       	ldd	r9, Y+63	; 0x3f
    6bb6:	a9 97       	sbiw	r28, 0x29	; 41
    6bb8:	94 01       	movw	r18, r8
    6bba:	44 27       	eor	r20, r20
    6bbc:	55 27       	eor	r21, r21
    6bbe:	7b 01       	movw	r14, r22
    6bc0:	8c 01       	movw	r16, r24
    6bc2:	e2 2a       	or	r14, r18
    6bc4:	f3 2a       	or	r15, r19
    6bc6:	04 2b       	or	r16, r20
    6bc8:	15 2b       	or	r17, r21
    6bca:	e2 14       	cp	r14, r2
    6bcc:	f3 04       	cpc	r15, r3
    6bce:	04 05       	cpc	r16, r4
    6bd0:	15 05       	cpc	r17, r5
    6bd2:	08 f4       	brcc	.+2      	; 0x6bd6 <__udivdi3+0x4ac>
    6bd4:	6c c2       	rjmp	.+1240   	; 0x70ae <__udivdi3+0x984>
    6bd6:	2d a5       	ldd	r18, Y+45	; 0x2d
    6bd8:	3e a5       	ldd	r19, Y+46	; 0x2e
    6bda:	4f a5       	ldd	r20, Y+47	; 0x2f
    6bdc:	58 a9       	ldd	r21, Y+48	; 0x30
    6bde:	65 96       	adiw	r28, 0x15	; 21
    6be0:	2c af       	std	Y+60, r18	; 0x3c
    6be2:	3d af       	std	Y+61, r19	; 0x3d
    6be4:	4e af       	std	Y+62, r20	; 0x3e
    6be6:	5f af       	std	Y+63, r21	; 0x3f
    6be8:	65 97       	sbiw	r28, 0x15	; 21
    6bea:	e2 18       	sub	r14, r2
    6bec:	f3 08       	sbc	r15, r3
    6bee:	04 09       	sbc	r16, r4
    6bf0:	15 09       	sbc	r17, r5
    6bf2:	c8 01       	movw	r24, r16
    6bf4:	b7 01       	movw	r22, r14
    6bf6:	6d 96       	adiw	r28, 0x1d	; 29
    6bf8:	2c ad       	ldd	r18, Y+60	; 0x3c
    6bfa:	3d ad       	ldd	r19, Y+61	; 0x3d
    6bfc:	4e ad       	ldd	r20, Y+62	; 0x3e
    6bfe:	5f ad       	ldd	r21, Y+63	; 0x3f
    6c00:	6d 97       	sbiw	r28, 0x1d	; 29
    6c02:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    6c06:	39 01       	movw	r6, r18
    6c08:	4a 01       	movw	r8, r20
    6c0a:	69 96       	adiw	r28, 0x19	; 25
    6c0c:	6c ad       	ldd	r22, Y+60	; 0x3c
    6c0e:	7d ad       	ldd	r23, Y+61	; 0x3d
    6c10:	8e ad       	ldd	r24, Y+62	; 0x3e
    6c12:	9f ad       	ldd	r25, Y+63	; 0x3f
    6c14:	69 97       	sbiw	r28, 0x19	; 25
    6c16:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    6c1a:	1b 01       	movw	r2, r22
    6c1c:	2c 01       	movw	r4, r24
    6c1e:	c8 01       	movw	r24, r16
    6c20:	b7 01       	movw	r22, r14
    6c22:	6d 96       	adiw	r28, 0x1d	; 29
    6c24:	2c ad       	ldd	r18, Y+60	; 0x3c
    6c26:	3d ad       	ldd	r19, Y+61	; 0x3d
    6c28:	4e ad       	ldd	r20, Y+62	; 0x3e
    6c2a:	5f ad       	ldd	r21, Y+63	; 0x3f
    6c2c:	6d 97       	sbiw	r28, 0x1d	; 29
    6c2e:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    6c32:	cb 01       	movw	r24, r22
    6c34:	77 27       	eor	r23, r23
    6c36:	66 27       	eor	r22, r22
    6c38:	a9 96       	adiw	r28, 0x29	; 41
    6c3a:	2c ad       	ldd	r18, Y+60	; 0x3c
    6c3c:	3d ad       	ldd	r19, Y+61	; 0x3d
    6c3e:	4e ad       	ldd	r20, Y+62	; 0x3e
    6c40:	5f ad       	ldd	r21, Y+63	; 0x3f
    6c42:	a9 97       	sbiw	r28, 0x29	; 41
    6c44:	40 70       	andi	r20, 0x00	; 0
    6c46:	50 70       	andi	r21, 0x00	; 0
    6c48:	26 2b       	or	r18, r22
    6c4a:	37 2b       	or	r19, r23
    6c4c:	48 2b       	or	r20, r24
    6c4e:	59 2b       	or	r21, r25
    6c50:	22 15       	cp	r18, r2
    6c52:	33 05       	cpc	r19, r3
    6c54:	44 05       	cpc	r20, r4
    6c56:	55 05       	cpc	r21, r5
    6c58:	08 f4       	brcc	.+2      	; 0x6c5c <__udivdi3+0x532>
    6c5a:	05 c2       	rjmp	.+1034   	; 0x7066 <__udivdi3+0x93c>
    6c5c:	84 01       	movw	r16, r8
    6c5e:	73 01       	movw	r14, r6
    6c60:	65 96       	adiw	r28, 0x15	; 21
    6c62:	2c ac       	ldd	r2, Y+60	; 0x3c
    6c64:	3d ac       	ldd	r3, Y+61	; 0x3d
    6c66:	4e ac       	ldd	r4, Y+62	; 0x3e
    6c68:	5f ac       	ldd	r5, Y+63	; 0x3f
    6c6a:	65 97       	sbiw	r28, 0x15	; 21
    6c6c:	d1 01       	movw	r26, r2
    6c6e:	99 27       	eor	r25, r25
    6c70:	88 27       	eor	r24, r24
    6c72:	e8 2a       	or	r14, r24
    6c74:	f9 2a       	or	r15, r25
    6c76:	0a 2b       	or	r16, r26
    6c78:	1b 2b       	or	r17, r27
    6c7a:	20 e0       	ldi	r18, 0x00	; 0
    6c7c:	30 e0       	ldi	r19, 0x00	; 0
    6c7e:	40 e0       	ldi	r20, 0x00	; 0
    6c80:	50 e0       	ldi	r21, 0x00	; 0
    6c82:	63 c4       	rjmp	.+2246   	; 0x754a <__udivdi3+0xe20>
	}
      else
	{
	  /* 0q = NN / dd */

	  count_leading_zeros (bm, d1);
    6c84:	40 e0       	ldi	r20, 0x00	; 0
    6c86:	e4 16       	cp	r14, r20
    6c88:	40 e0       	ldi	r20, 0x00	; 0
    6c8a:	f4 06       	cpc	r15, r20
    6c8c:	40 e0       	ldi	r20, 0x00	; 0
    6c8e:	04 07       	cpc	r16, r20
    6c90:	41 e0       	ldi	r20, 0x01	; 1
    6c92:	14 07       	cpc	r17, r20
    6c94:	08 f0       	brcs	.+2      	; 0x6c98 <__udivdi3+0x56e>
    6c96:	b3 c3       	rjmp	.+1894   	; 0x73fe <__udivdi3+0xcd4>
    6c98:	20 e1       	ldi	r18, 0x10	; 16
    6c9a:	30 e0       	ldi	r19, 0x00	; 0
    6c9c:	40 e0       	ldi	r20, 0x00	; 0
    6c9e:	50 e0       	ldi	r21, 0x00	; 0
    6ca0:	80 e1       	ldi	r24, 0x10	; 16
    6ca2:	90 e0       	ldi	r25, 0x00	; 0
    6ca4:	9c ce       	rjmp	.-712    	; 0x69de <__udivdi3+0x2b4>
	      n0 = n0 << bm;

	      udiv_qrnnd (q0, n1, n2, n1, d1);
	      umul_ppmm (m1, m0, q0, d0);

	      if (m1 > n1 || (m1 == n1 && m0 > n0))
    6ca6:	ee 24       	eor	r14, r14
    6ca8:	ff 24       	eor	r15, r15
    6caa:	87 01       	movw	r16, r14
    6cac:	20 e0       	ldi	r18, 0x00	; 0
    6cae:	30 e0       	ldi	r19, 0x00	; 0
    6cb0:	40 e0       	ldi	r20, 0x00	; 0
    6cb2:	50 e0       	ldi	r21, 0x00	; 0
    6cb4:	4a c4       	rjmp	.+2196   	; 0x754a <__udivdi3+0xe20>
	  /* qq = NN / 0d */

	  if (d0 == 0)
	    d0 = 1 / d0;	/* Divide intentionally by zero.  */

	  count_leading_zeros (bm, d0);
    6cb6:	20 e0       	ldi	r18, 0x00	; 0
    6cb8:	a2 16       	cp	r10, r18
    6cba:	20 e0       	ldi	r18, 0x00	; 0
    6cbc:	b2 06       	cpc	r11, r18
    6cbe:	20 e0       	ldi	r18, 0x00	; 0
    6cc0:	c2 06       	cpc	r12, r18
    6cc2:	21 e0       	ldi	r18, 0x01	; 1
    6cc4:	d2 06       	cpc	r13, r18
    6cc6:	08 f0       	brcs	.+2      	; 0x6cca <__udivdi3+0x5a0>
    6cc8:	ec c3       	rjmp	.+2008   	; 0x74a2 <__udivdi3+0xd78>
    6cca:	20 e1       	ldi	r18, 0x10	; 16
    6ccc:	30 e0       	ldi	r19, 0x00	; 0
    6cce:	40 e0       	ldi	r20, 0x00	; 0
    6cd0:	50 e0       	ldi	r21, 0x00	; 0
    6cd2:	80 e1       	ldi	r24, 0x10	; 16
    6cd4:	90 e0       	ldi	r25, 0x00	; 0
    6cd6:	94 cd       	rjmp	.-1240   	; 0x6800 <__udivdi3+0xd6>
      else
	{
	  /* qq = NN / 0d */

	  if (d0 == 0)
	    d0 = 1 / d0;	/* Divide intentionally by zero.  */
    6cd8:	61 e0       	ldi	r22, 0x01	; 1
    6cda:	70 e0       	ldi	r23, 0x00	; 0
    6cdc:	80 e0       	ldi	r24, 0x00	; 0
    6cde:	90 e0       	ldi	r25, 0x00	; 0
    6ce0:	20 e0       	ldi	r18, 0x00	; 0
    6ce2:	30 e0       	ldi	r19, 0x00	; 0
    6ce4:	40 e0       	ldi	r20, 0x00	; 0
    6ce6:	50 e0       	ldi	r21, 0x00	; 0
    6ce8:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    6cec:	59 01       	movw	r10, r18
    6cee:	6a 01       	movw	r12, r20

	  count_leading_zeros (bm, d0);
    6cf0:	00 e0       	ldi	r16, 0x00	; 0
    6cf2:	a0 16       	cp	r10, r16
    6cf4:	00 e0       	ldi	r16, 0x00	; 0
    6cf6:	b0 06       	cpc	r11, r16
    6cf8:	01 e0       	ldi	r16, 0x01	; 1
    6cfa:	c0 06       	cpc	r12, r16
    6cfc:	00 e0       	ldi	r16, 0x00	; 0
    6cfe:	d0 06       	cpc	r13, r16
    6d00:	d0 f6       	brcc	.-76     	; 0x6cb6 <__udivdi3+0x58c>
    6d02:	70 cd       	rjmp	.-1312   	; 0x67e4 <__udivdi3+0xba>
    {
      if (d0 > n1)
	{
	  /* 0q = nn / 0D */

	  count_leading_zeros (bm, d0);
    6d04:	50 e0       	ldi	r21, 0x00	; 0
    6d06:	a5 16       	cp	r10, r21
    6d08:	50 e0       	ldi	r21, 0x00	; 0
    6d0a:	b5 06       	cpc	r11, r21
    6d0c:	50 e0       	ldi	r21, 0x00	; 0
    6d0e:	c5 06       	cpc	r12, r21
    6d10:	51 e0       	ldi	r21, 0x01	; 1
    6d12:	d5 06       	cpc	r13, r21
    6d14:	08 f0       	brcs	.+2      	; 0x6d18 <__udivdi3+0x5ee>
    6d16:	be c3       	rjmp	.+1916   	; 0x7494 <__udivdi3+0xd6a>
    6d18:	20 e1       	ldi	r18, 0x10	; 16
    6d1a:	30 e0       	ldi	r19, 0x00	; 0
    6d1c:	40 e0       	ldi	r20, 0x00	; 0
    6d1e:	50 e0       	ldi	r21, 0x00	; 0
    6d20:	80 e1       	ldi	r24, 0x10	; 16
    6d22:	90 e0       	ldi	r25, 0x00	; 0
    6d24:	b1 ce       	rjmp	.-670    	; 0x6a88 <__udivdi3+0x35e>
	      UWtype m1, m0;
	      /* Normalize.  */

	      b = W_TYPE_SIZE - bm;

	      d1 = (d1 << bm) | (d0 >> b);
    6d26:	b9 01       	movw	r22, r18
    6d28:	22 1a       	sub	r2, r18
    6d2a:	33 0a       	sbc	r3, r19
    6d2c:	44 0a       	sbc	r4, r20
    6d2e:	55 0a       	sbc	r5, r21
    6d30:	06 2e       	mov	r0, r22
    6d32:	04 c0       	rjmp	.+8      	; 0x6d3c <__udivdi3+0x612>
    6d34:	ee 0c       	add	r14, r14
    6d36:	ff 1c       	adc	r15, r15
    6d38:	00 1f       	adc	r16, r16
    6d3a:	11 1f       	adc	r17, r17
    6d3c:	0a 94       	dec	r0
    6d3e:	d2 f7       	brpl	.-12     	; 0x6d34 <__udivdi3+0x60a>
    6d40:	d6 01       	movw	r26, r12
    6d42:	c5 01       	movw	r24, r10
    6d44:	02 2c       	mov	r0, r2
    6d46:	04 c0       	rjmp	.+8      	; 0x6d50 <__udivdi3+0x626>
    6d48:	b6 95       	lsr	r27
    6d4a:	a7 95       	ror	r26
    6d4c:	97 95       	ror	r25
    6d4e:	87 95       	ror	r24
    6d50:	0a 94       	dec	r0
    6d52:	d2 f7       	brpl	.-12     	; 0x6d48 <__udivdi3+0x61e>
    6d54:	e8 2a       	or	r14, r24
    6d56:	f9 2a       	or	r15, r25
    6d58:	0a 2b       	or	r16, r26
    6d5a:	1b 2b       	or	r17, r27
    6d5c:	ad 96       	adiw	r28, 0x2d	; 45
    6d5e:	ec ae       	std	Y+60, r14	; 0x3c
    6d60:	fd ae       	std	Y+61, r15	; 0x3d
    6d62:	0e af       	std	Y+62, r16	; 0x3e
    6d64:	1f af       	std	Y+63, r17	; 0x3f
    6d66:	ad 97       	sbiw	r28, 0x2d	; 45
	      d0 = d0 << bm;
    6d68:	06 2e       	mov	r0, r22
    6d6a:	04 c0       	rjmp	.+8      	; 0x6d74 <__udivdi3+0x64a>
    6d6c:	aa 0c       	add	r10, r10
    6d6e:	bb 1c       	adc	r11, r11
    6d70:	cc 1c       	adc	r12, r12
    6d72:	dd 1c       	adc	r13, r13
    6d74:	0a 94       	dec	r0
    6d76:	d2 f7       	brpl	.-12     	; 0x6d6c <__udivdi3+0x642>
    6d78:	a9 8e       	std	Y+25, r10	; 0x19
    6d7a:	ba 8e       	std	Y+26, r11	; 0x1a
    6d7c:	cb 8e       	std	Y+27, r12	; 0x1b
    6d7e:	dc 8e       	std	Y+28, r13	; 0x1c
	      n2 = n1 >> b;
    6d80:	64 01       	movw	r12, r8
    6d82:	53 01       	movw	r10, r6
    6d84:	02 2c       	mov	r0, r2
    6d86:	04 c0       	rjmp	.+8      	; 0x6d90 <__udivdi3+0x666>
    6d88:	d6 94       	lsr	r13
    6d8a:	c7 94       	ror	r12
    6d8c:	b7 94       	ror	r11
    6d8e:	a7 94       	ror	r10
    6d90:	0a 94       	dec	r0
    6d92:	d2 f7       	brpl	.-12     	; 0x6d88 <__udivdi3+0x65e>
	      n1 = (n1 << bm) | (n0 >> b);
    6d94:	84 01       	movw	r16, r8
    6d96:	73 01       	movw	r14, r6
    6d98:	06 2e       	mov	r0, r22
    6d9a:	04 c0       	rjmp	.+8      	; 0x6da4 <__udivdi3+0x67a>
    6d9c:	ee 0c       	add	r14, r14
    6d9e:	ff 1c       	adc	r15, r15
    6da0:	00 1f       	adc	r16, r16
    6da2:	11 1f       	adc	r17, r17
    6da4:	0a 94       	dec	r0
    6da6:	d2 f7       	brpl	.-12     	; 0x6d9c <__udivdi3+0x672>
    6da8:	a9 96       	adiw	r28, 0x29	; 41
    6daa:	8c ad       	ldd	r24, Y+60	; 0x3c
    6dac:	9d ad       	ldd	r25, Y+61	; 0x3d
    6dae:	ae ad       	ldd	r26, Y+62	; 0x3e
    6db0:	bf ad       	ldd	r27, Y+63	; 0x3f
    6db2:	a9 97       	sbiw	r28, 0x29	; 41
    6db4:	04 c0       	rjmp	.+8      	; 0x6dbe <__udivdi3+0x694>
    6db6:	b6 95       	lsr	r27
    6db8:	a7 95       	ror	r26
    6dba:	97 95       	ror	r25
    6dbc:	87 95       	ror	r24
    6dbe:	2a 94       	dec	r2
    6dc0:	d2 f7       	brpl	.-12     	; 0x6db6 <__udivdi3+0x68c>
    6dc2:	e8 2a       	or	r14, r24
    6dc4:	f9 2a       	or	r15, r25
    6dc6:	0a 2b       	or	r16, r26
    6dc8:	1b 2b       	or	r17, r27
    6dca:	e9 a6       	std	Y+41, r14	; 0x29
    6dcc:	fa a6       	std	Y+42, r15	; 0x2a
    6dce:	0b a7       	std	Y+43, r16	; 0x2b
    6dd0:	1c a7       	std	Y+44, r17	; 0x2c
	      n0 = n0 << bm;
    6dd2:	a9 96       	adiw	r28, 0x29	; 41
    6dd4:	4c ac       	ldd	r4, Y+60	; 0x3c
    6dd6:	5d ac       	ldd	r5, Y+61	; 0x3d
    6dd8:	6e ac       	ldd	r6, Y+62	; 0x3e
    6dda:	7f ac       	ldd	r7, Y+63	; 0x3f
    6ddc:	a9 97       	sbiw	r28, 0x29	; 41
    6dde:	04 c0       	rjmp	.+8      	; 0x6de8 <__udivdi3+0x6be>
    6de0:	44 0c       	add	r4, r4
    6de2:	55 1c       	adc	r5, r5
    6de4:	66 1c       	adc	r6, r6
    6de6:	77 1c       	adc	r7, r7
    6de8:	6a 95       	dec	r22
    6dea:	d2 f7       	brpl	.-12     	; 0x6de0 <__udivdi3+0x6b6>
    6dec:	4d 8e       	std	Y+29, r4	; 0x1d
    6dee:	5e 8e       	std	Y+30, r5	; 0x1e
    6df0:	6f 8e       	std	Y+31, r6	; 0x1f
    6df2:	78 a2       	std	Y+32, r7	; 0x20

	      udiv_qrnnd (q0, n1, n2, n1, d1);
    6df4:	ad 96       	adiw	r28, 0x2d	; 45
    6df6:	6c ac       	ldd	r6, Y+60	; 0x3c
    6df8:	7d ac       	ldd	r7, Y+61	; 0x3d
    6dfa:	8e ac       	ldd	r8, Y+62	; 0x3e
    6dfc:	9f ac       	ldd	r9, Y+63	; 0x3f
    6dfe:	ad 97       	sbiw	r28, 0x2d	; 45
    6e00:	34 01       	movw	r6, r8
    6e02:	88 24       	eor	r8, r8
    6e04:	99 24       	eor	r9, r9
    6e06:	21 96       	adiw	r28, 0x01	; 1
    6e08:	6c ae       	std	Y+60, r6	; 0x3c
    6e0a:	7d ae       	std	Y+61, r7	; 0x3d
    6e0c:	8e ae       	std	Y+62, r8	; 0x3e
    6e0e:	9f ae       	std	Y+63, r9	; 0x3f
    6e10:	21 97       	sbiw	r28, 0x01	; 1
    6e12:	ad 96       	adiw	r28, 0x2d	; 45
    6e14:	0c ad       	ldd	r16, Y+60	; 0x3c
    6e16:	1d ad       	ldd	r17, Y+61	; 0x3d
    6e18:	2e ad       	ldd	r18, Y+62	; 0x3e
    6e1a:	3f ad       	ldd	r19, Y+63	; 0x3f
    6e1c:	ad 97       	sbiw	r28, 0x2d	; 45
    6e1e:	20 70       	andi	r18, 0x00	; 0
    6e20:	30 70       	andi	r19, 0x00	; 0
    6e22:	09 af       	std	Y+57, r16	; 0x39
    6e24:	1a af       	std	Y+58, r17	; 0x3a
    6e26:	2b af       	std	Y+59, r18	; 0x3b
    6e28:	3c af       	std	Y+60, r19	; 0x3c
    6e2a:	c6 01       	movw	r24, r12
    6e2c:	b5 01       	movw	r22, r10
    6e2e:	a4 01       	movw	r20, r8
    6e30:	93 01       	movw	r18, r6
    6e32:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    6e36:	19 01       	movw	r2, r18
    6e38:	2a 01       	movw	r4, r20
    6e3a:	69 ad       	ldd	r22, Y+57	; 0x39
    6e3c:	7a ad       	ldd	r23, Y+58	; 0x3a
    6e3e:	8b ad       	ldd	r24, Y+59	; 0x3b
    6e40:	9c ad       	ldd	r25, Y+60	; 0x3c
    6e42:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    6e46:	3b 01       	movw	r6, r22
    6e48:	4c 01       	movw	r8, r24
    6e4a:	c6 01       	movw	r24, r12
    6e4c:	b5 01       	movw	r22, r10
    6e4e:	21 96       	adiw	r28, 0x01	; 1
    6e50:	2c ad       	ldd	r18, Y+60	; 0x3c
    6e52:	3d ad       	ldd	r19, Y+61	; 0x3d
    6e54:	4e ad       	ldd	r20, Y+62	; 0x3e
    6e56:	5f ad       	ldd	r21, Y+63	; 0x3f
    6e58:	21 97       	sbiw	r28, 0x01	; 1
    6e5a:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    6e5e:	cb 01       	movw	r24, r22
    6e60:	77 27       	eor	r23, r23
    6e62:	66 27       	eor	r22, r22
    6e64:	e9 a4       	ldd	r14, Y+41	; 0x29
    6e66:	fa a4       	ldd	r15, Y+42	; 0x2a
    6e68:	0b a5       	ldd	r16, Y+43	; 0x2b
    6e6a:	1c a5       	ldd	r17, Y+44	; 0x2c
    6e6c:	98 01       	movw	r18, r16
    6e6e:	44 27       	eor	r20, r20
    6e70:	55 27       	eor	r21, r21
    6e72:	7b 01       	movw	r14, r22
    6e74:	8c 01       	movw	r16, r24
    6e76:	e2 2a       	or	r14, r18
    6e78:	f3 2a       	or	r15, r19
    6e7a:	04 2b       	or	r16, r20
    6e7c:	15 2b       	or	r17, r21
    6e7e:	e6 14       	cp	r14, r6
    6e80:	f7 04       	cpc	r15, r7
    6e82:	08 05       	cpc	r16, r8
    6e84:	19 05       	cpc	r17, r9
    6e86:	08 f4       	brcc	.+2      	; 0x6e8a <__udivdi3+0x760>
    6e88:	c8 c2       	rjmp	.+1424   	; 0x741a <__udivdi3+0xcf0>
    6e8a:	2d aa       	std	Y+53, r2	; 0x35
    6e8c:	3e aa       	std	Y+54, r3	; 0x36
    6e8e:	4f aa       	std	Y+55, r4	; 0x37
    6e90:	58 ae       	std	Y+56, r5	; 0x38
    6e92:	e6 18       	sub	r14, r6
    6e94:	f7 08       	sbc	r15, r7
    6e96:	08 09       	sbc	r16, r8
    6e98:	19 09       	sbc	r17, r9
    6e9a:	c8 01       	movw	r24, r16
    6e9c:	b7 01       	movw	r22, r14
    6e9e:	21 96       	adiw	r28, 0x01	; 1
    6ea0:	2c ad       	ldd	r18, Y+60	; 0x3c
    6ea2:	3d ad       	ldd	r19, Y+61	; 0x3d
    6ea4:	4e ad       	ldd	r20, Y+62	; 0x3e
    6ea6:	5f ad       	ldd	r21, Y+63	; 0x3f
    6ea8:	21 97       	sbiw	r28, 0x01	; 1
    6eaa:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    6eae:	19 01       	movw	r2, r18
    6eb0:	2a 01       	movw	r4, r20
    6eb2:	69 ad       	ldd	r22, Y+57	; 0x39
    6eb4:	7a ad       	ldd	r23, Y+58	; 0x3a
    6eb6:	8b ad       	ldd	r24, Y+59	; 0x3b
    6eb8:	9c ad       	ldd	r25, Y+60	; 0x3c
    6eba:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    6ebe:	3b 01       	movw	r6, r22
    6ec0:	4c 01       	movw	r8, r24
    6ec2:	c8 01       	movw	r24, r16
    6ec4:	b7 01       	movw	r22, r14
    6ec6:	21 96       	adiw	r28, 0x01	; 1
    6ec8:	2c ad       	ldd	r18, Y+60	; 0x3c
    6eca:	3d ad       	ldd	r19, Y+61	; 0x3d
    6ecc:	4e ad       	ldd	r20, Y+62	; 0x3e
    6ece:	5f ad       	ldd	r21, Y+63	; 0x3f
    6ed0:	21 97       	sbiw	r28, 0x01	; 1
    6ed2:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    6ed6:	cb 01       	movw	r24, r22
    6ed8:	77 27       	eor	r23, r23
    6eda:	66 27       	eor	r22, r22
    6edc:	29 a5       	ldd	r18, Y+41	; 0x29
    6ede:	3a a5       	ldd	r19, Y+42	; 0x2a
    6ee0:	4b a5       	ldd	r20, Y+43	; 0x2b
    6ee2:	5c a5       	ldd	r21, Y+44	; 0x2c
    6ee4:	40 70       	andi	r20, 0x00	; 0
    6ee6:	50 70       	andi	r21, 0x00	; 0
    6ee8:	5b 01       	movw	r10, r22
    6eea:	6c 01       	movw	r12, r24
    6eec:	a2 2a       	or	r10, r18
    6eee:	b3 2a       	or	r11, r19
    6ef0:	c4 2a       	or	r12, r20
    6ef2:	d5 2a       	or	r13, r21
    6ef4:	a6 14       	cp	r10, r6
    6ef6:	b7 04       	cpc	r11, r7
    6ef8:	c8 04       	cpc	r12, r8
    6efa:	d9 04       	cpc	r13, r9
    6efc:	08 f4       	brcc	.+2      	; 0x6f00 <__udivdi3+0x7d6>
    6efe:	58 c2       	rjmp	.+1200   	; 0x73b0 <__udivdi3+0xc86>
    6f00:	a2 01       	movw	r20, r4
    6f02:	91 01       	movw	r18, r2
    6f04:	a6 18       	sub	r10, r6
    6f06:	b7 08       	sbc	r11, r7
    6f08:	c8 08       	sbc	r12, r8
    6f0a:	d9 08       	sbc	r13, r9
    6f0c:	ad a2       	std	Y+37, r10	; 0x25
    6f0e:	be a2       	std	Y+38, r11	; 0x26
    6f10:	cf a2       	std	Y+39, r12	; 0x27
    6f12:	d8 a6       	std	Y+40, r13	; 0x28
    6f14:	2d a8       	ldd	r2, Y+53	; 0x35
    6f16:	3e a8       	ldd	r3, Y+54	; 0x36
    6f18:	4f a8       	ldd	r4, Y+55	; 0x37
    6f1a:	58 ac       	ldd	r5, Y+56	; 0x38
    6f1c:	d1 01       	movw	r26, r2
    6f1e:	99 27       	eor	r25, r25
    6f20:	88 27       	eor	r24, r24
    6f22:	28 2b       	or	r18, r24
    6f24:	39 2b       	or	r19, r25
    6f26:	4a 2b       	or	r20, r26
    6f28:	5b 2b       	or	r21, r27
    6f2a:	a5 96       	adiw	r28, 0x25	; 37
    6f2c:	2c af       	std	Y+60, r18	; 0x3c
    6f2e:	3d af       	std	Y+61, r19	; 0x3d
    6f30:	4e af       	std	Y+62, r20	; 0x3e
    6f32:	5f af       	std	Y+63, r21	; 0x3f
    6f34:	a5 97       	sbiw	r28, 0x25	; 37
	      umul_ppmm (m1, m0, q0, d0);
    6f36:	79 01       	movw	r14, r18
    6f38:	8a 01       	movw	r16, r20
    6f3a:	8f ef       	ldi	r24, 0xFF	; 255
    6f3c:	48 2e       	mov	r4, r24
    6f3e:	8f ef       	ldi	r24, 0xFF	; 255
    6f40:	58 2e       	mov	r5, r24
    6f42:	61 2c       	mov	r6, r1
    6f44:	71 2c       	mov	r7, r1
    6f46:	e4 20       	and	r14, r4
    6f48:	f5 20       	and	r15, r5
    6f4a:	06 21       	and	r16, r6
    6f4c:	17 21       	and	r17, r7
    6f4e:	1a 01       	movw	r2, r20
    6f50:	44 24       	eor	r4, r4
    6f52:	55 24       	eor	r5, r5
    6f54:	a9 8c       	ldd	r10, Y+25	; 0x19
    6f56:	ba 8c       	ldd	r11, Y+26	; 0x1a
    6f58:	cb 8c       	ldd	r12, Y+27	; 0x1b
    6f5a:	dc 8c       	ldd	r13, Y+28	; 0x1c
    6f5c:	af ef       	ldi	r26, 0xFF	; 255
    6f5e:	6a 2e       	mov	r6, r26
    6f60:	af ef       	ldi	r26, 0xFF	; 255
    6f62:	7a 2e       	mov	r7, r26
    6f64:	81 2c       	mov	r8, r1
    6f66:	91 2c       	mov	r9, r1
    6f68:	a6 20       	and	r10, r6
    6f6a:	b7 20       	and	r11, r7
    6f6c:	c8 20       	and	r12, r8
    6f6e:	d9 20       	and	r13, r9
    6f70:	29 8d       	ldd	r18, Y+25	; 0x19
    6f72:	3a 8d       	ldd	r19, Y+26	; 0x1a
    6f74:	4b 8d       	ldd	r20, Y+27	; 0x1b
    6f76:	5c 8d       	ldd	r21, Y+28	; 0x1c
    6f78:	3a 01       	movw	r6, r20
    6f7a:	88 24       	eor	r8, r8
    6f7c:	99 24       	eor	r9, r9
    6f7e:	c8 01       	movw	r24, r16
    6f80:	b7 01       	movw	r22, r14
    6f82:	a6 01       	movw	r20, r12
    6f84:	95 01       	movw	r18, r10
    6f86:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    6f8a:	69 ab       	std	Y+49, r22	; 0x31
    6f8c:	7a ab       	std	Y+50, r23	; 0x32
    6f8e:	8b ab       	std	Y+51, r24	; 0x33
    6f90:	9c ab       	std	Y+52, r25	; 0x34
    6f92:	c8 01       	movw	r24, r16
    6f94:	b7 01       	movw	r22, r14
    6f96:	a4 01       	movw	r20, r8
    6f98:	93 01       	movw	r18, r6
    6f9a:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    6f9e:	7b 01       	movw	r14, r22
    6fa0:	8c 01       	movw	r16, r24
    6fa2:	c2 01       	movw	r24, r4
    6fa4:	b1 01       	movw	r22, r2
    6fa6:	a6 01       	movw	r20, r12
    6fa8:	95 01       	movw	r18, r10
    6faa:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    6fae:	5b 01       	movw	r10, r22
    6fb0:	6c 01       	movw	r12, r24
    6fb2:	c2 01       	movw	r24, r4
    6fb4:	b1 01       	movw	r22, r2
    6fb6:	a4 01       	movw	r20, r8
    6fb8:	93 01       	movw	r18, r6
    6fba:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    6fbe:	3b 01       	movw	r6, r22
    6fc0:	4c 01       	movw	r8, r24
    6fc2:	29 a8       	ldd	r2, Y+49	; 0x31
    6fc4:	3a a8       	ldd	r3, Y+50	; 0x32
    6fc6:	4b a8       	ldd	r4, Y+51	; 0x33
    6fc8:	5c a8       	ldd	r5, Y+52	; 0x34
    6fca:	c2 01       	movw	r24, r4
    6fcc:	aa 27       	eor	r26, r26
    6fce:	bb 27       	eor	r27, r27
    6fd0:	e8 0e       	add	r14, r24
    6fd2:	f9 1e       	adc	r15, r25
    6fd4:	0a 1f       	adc	r16, r26
    6fd6:	1b 1f       	adc	r17, r27
    6fd8:	a6 01       	movw	r20, r12
    6fda:	95 01       	movw	r18, r10
    6fdc:	2e 0d       	add	r18, r14
    6fde:	3f 1d       	adc	r19, r15
    6fe0:	40 1f       	adc	r20, r16
    6fe2:	51 1f       	adc	r21, r17
    6fe4:	2a 15       	cp	r18, r10
    6fe6:	3b 05       	cpc	r19, r11
    6fe8:	4c 05       	cpc	r20, r12
    6fea:	5d 05       	cpc	r21, r13
    6fec:	48 f4       	brcc	.+18     	; 0x7000 <__udivdi3+0x8d6>
    6fee:	e1 2c       	mov	r14, r1
    6ff0:	f1 2c       	mov	r15, r1
    6ff2:	71 e0       	ldi	r23, 0x01	; 1
    6ff4:	07 2f       	mov	r16, r23
    6ff6:	11 2d       	mov	r17, r1
    6ff8:	6e 0c       	add	r6, r14
    6ffa:	7f 1c       	adc	r7, r15
    6ffc:	80 1e       	adc	r8, r16
    6ffe:	91 1e       	adc	r9, r17
    7000:	ca 01       	movw	r24, r20
    7002:	aa 27       	eor	r26, r26
    7004:	bb 27       	eor	r27, r27
    7006:	84 01       	movw	r16, r8
    7008:	73 01       	movw	r14, r6
    700a:	e8 0e       	add	r14, r24
    700c:	f9 1e       	adc	r15, r25
    700e:	0a 1f       	adc	r16, r26
    7010:	1b 1f       	adc	r17, r27
    7012:	a9 01       	movw	r20, r18
    7014:	33 27       	eor	r19, r19
    7016:	22 27       	eor	r18, r18
    7018:	89 a9       	ldd	r24, Y+49	; 0x31
    701a:	9a a9       	ldd	r25, Y+50	; 0x32
    701c:	ab a9       	ldd	r26, Y+51	; 0x33
    701e:	bc a9       	ldd	r27, Y+52	; 0x34
    7020:	a0 70       	andi	r26, 0x00	; 0
    7022:	b0 70       	andi	r27, 0x00	; 0
    7024:	59 01       	movw	r10, r18
    7026:	6a 01       	movw	r12, r20
    7028:	a8 0e       	add	r10, r24
    702a:	b9 1e       	adc	r11, r25
    702c:	ca 1e       	adc	r12, r26
    702e:	db 1e       	adc	r13, r27

	      if (m1 > n1 || (m1 == n1 && m0 > n0))
    7030:	2d a1       	ldd	r18, Y+37	; 0x25
    7032:	3e a1       	ldd	r19, Y+38	; 0x26
    7034:	4f a1       	ldd	r20, Y+39	; 0x27
    7036:	58 a5       	ldd	r21, Y+40	; 0x28
    7038:	2e 15       	cp	r18, r14
    703a:	3f 05       	cpc	r19, r15
    703c:	40 07       	cpc	r20, r16
    703e:	51 07       	cpc	r21, r17
    7040:	08 f4       	brcc	.+2      	; 0x7044 <__udivdi3+0x91a>
    7042:	a6 c1       	rjmp	.+844    	; 0x7390 <__udivdi3+0xc66>
    7044:	2e 15       	cp	r18, r14
    7046:	3f 05       	cpc	r19, r15
    7048:	40 07       	cpc	r20, r16
    704a:	51 07       	cpc	r21, r17
    704c:	09 f4       	brne	.+2      	; 0x7050 <__udivdi3+0x926>
    704e:	96 c1       	rjmp	.+812    	; 0x737c <__udivdi3+0xc52>
    7050:	a5 96       	adiw	r28, 0x25	; 37
    7052:	ec ac       	ldd	r14, Y+60	; 0x3c
    7054:	fd ac       	ldd	r15, Y+61	; 0x3d
    7056:	0e ad       	ldd	r16, Y+62	; 0x3e
    7058:	1f ad       	ldd	r17, Y+63	; 0x3f
    705a:	a5 97       	sbiw	r28, 0x25	; 37
    705c:	20 e0       	ldi	r18, 0x00	; 0
    705e:	30 e0       	ldi	r19, 0x00	; 0
    7060:	40 e0       	ldi	r20, 0x00	; 0
    7062:	50 e0       	ldi	r21, 0x00	; 0
    7064:	72 c2       	rjmp	.+1252   	; 0x754a <__udivdi3+0xe20>
	      d0 = d0 << bm;
	      n1 = (n1 << bm) | (n0 >> (W_TYPE_SIZE - bm));
	      n0 = n0 << bm;
	    }

	  udiv_qrnnd (q0, n0, n1, n0, d0);
    7066:	84 01       	movw	r16, r8
    7068:	73 01       	movw	r14, r6
    706a:	08 94       	sec
    706c:	e1 08       	sbc	r14, r1
    706e:	f1 08       	sbc	r15, r1
    7070:	01 09       	sbc	r16, r1
    7072:	11 09       	sbc	r17, r1
    7074:	2a 0d       	add	r18, r10
    7076:	3b 1d       	adc	r19, r11
    7078:	4c 1d       	adc	r20, r12
    707a:	5d 1d       	adc	r21, r13
    707c:	2a 15       	cp	r18, r10
    707e:	3b 05       	cpc	r19, r11
    7080:	4c 05       	cpc	r20, r12
    7082:	5d 05       	cpc	r21, r13
    7084:	08 f4       	brcc	.+2      	; 0x7088 <__udivdi3+0x95e>
    7086:	ec cd       	rjmp	.-1064   	; 0x6c60 <__udivdi3+0x536>
    7088:	22 15       	cp	r18, r2
    708a:	33 05       	cpc	r19, r3
    708c:	44 05       	cpc	r20, r4
    708e:	55 05       	cpc	r21, r5
    7090:	08 f0       	brcs	.+2      	; 0x7094 <__udivdi3+0x96a>
    7092:	e6 cd       	rjmp	.-1076   	; 0x6c60 <__udivdi3+0x536>
    7094:	ee ef       	ldi	r30, 0xFE	; 254
    7096:	ee 2e       	mov	r14, r30
    7098:	ef ef       	ldi	r30, 0xFF	; 255
    709a:	fe 2e       	mov	r15, r30
    709c:	ef ef       	ldi	r30, 0xFF	; 255
    709e:	0e 2f       	mov	r16, r30
    70a0:	ef ef       	ldi	r30, 0xFF	; 255
    70a2:	1e 2f       	mov	r17, r30
    70a4:	e6 0c       	add	r14, r6
    70a6:	f7 1c       	adc	r15, r7
    70a8:	08 1d       	adc	r16, r8
    70aa:	19 1d       	adc	r17, r9
    70ac:	d9 cd       	rjmp	.-1102   	; 0x6c60 <__udivdi3+0x536>
    70ae:	8d a5       	ldd	r24, Y+45	; 0x2d
    70b0:	9e a5       	ldd	r25, Y+46	; 0x2e
    70b2:	af a5       	ldd	r26, Y+47	; 0x2f
    70b4:	b8 a9       	ldd	r27, Y+48	; 0x30
    70b6:	01 97       	sbiw	r24, 0x01	; 1
    70b8:	a1 09       	sbc	r26, r1
    70ba:	b1 09       	sbc	r27, r1
    70bc:	65 96       	adiw	r28, 0x15	; 21
    70be:	8c af       	std	Y+60, r24	; 0x3c
    70c0:	9d af       	std	Y+61, r25	; 0x3d
    70c2:	ae af       	std	Y+62, r26	; 0x3e
    70c4:	bf af       	std	Y+63, r27	; 0x3f
    70c6:	65 97       	sbiw	r28, 0x15	; 21
    70c8:	ea 0c       	add	r14, r10
    70ca:	fb 1c       	adc	r15, r11
    70cc:	0c 1d       	adc	r16, r12
    70ce:	1d 1d       	adc	r17, r13
    70d0:	ea 14       	cp	r14, r10
    70d2:	fb 04       	cpc	r15, r11
    70d4:	0c 05       	cpc	r16, r12
    70d6:	1d 05       	cpc	r17, r13
    70d8:	08 f4       	brcc	.+2      	; 0x70dc <__udivdi3+0x9b2>
    70da:	87 cd       	rjmp	.-1266   	; 0x6bea <__udivdi3+0x4c0>
    70dc:	e2 14       	cp	r14, r2
    70de:	f3 04       	cpc	r15, r3
    70e0:	04 05       	cpc	r16, r4
    70e2:	15 05       	cpc	r17, r5
    70e4:	08 f0       	brcs	.+2      	; 0x70e8 <__udivdi3+0x9be>
    70e6:	81 cd       	rjmp	.-1278   	; 0x6bea <__udivdi3+0x4c0>
    70e8:	2d a5       	ldd	r18, Y+45	; 0x2d
    70ea:	3e a5       	ldd	r19, Y+46	; 0x2e
    70ec:	4f a5       	ldd	r20, Y+47	; 0x2f
    70ee:	58 a9       	ldd	r21, Y+48	; 0x30
    70f0:	22 50       	subi	r18, 0x02	; 2
    70f2:	30 40       	sbci	r19, 0x00	; 0
    70f4:	40 40       	sbci	r20, 0x00	; 0
    70f6:	50 40       	sbci	r21, 0x00	; 0
    70f8:	65 96       	adiw	r28, 0x15	; 21
    70fa:	2c af       	std	Y+60, r18	; 0x3c
    70fc:	3d af       	std	Y+61, r19	; 0x3d
    70fe:	4e af       	std	Y+62, r20	; 0x3e
    7100:	5f af       	std	Y+63, r21	; 0x3f
    7102:	65 97       	sbiw	r28, 0x15	; 21
    7104:	ea 0c       	add	r14, r10
    7106:	fb 1c       	adc	r15, r11
    7108:	0c 1d       	adc	r16, r12
    710a:	1d 1d       	adc	r17, r13
    710c:	6e cd       	rjmp	.-1316   	; 0x6bea <__udivdi3+0x4c0>
	      udiv_qrnnd (q1, n1, n2, n1, d0);
	    }

	  /* n1 != d0...  */

	  udiv_qrnnd (q0, n0, n1, n0, d0);
    710e:	84 01       	movw	r16, r8
    7110:	73 01       	movw	r14, r6
    7112:	08 94       	sec
    7114:	e1 08       	sbc	r14, r1
    7116:	f1 08       	sbc	r15, r1
    7118:	01 09       	sbc	r16, r1
    711a:	11 09       	sbc	r17, r1
    711c:	2a 0d       	add	r18, r10
    711e:	3b 1d       	adc	r19, r11
    7120:	4c 1d       	adc	r20, r12
    7122:	5d 1d       	adc	r21, r13
    7124:	2a 15       	cp	r18, r10
    7126:	3b 05       	cpc	r19, r11
    7128:	4c 05       	cpc	r20, r12
    712a:	5d 05       	cpc	r21, r13
    712c:	08 f4       	brcc	.+2      	; 0x7130 <__udivdi3+0xa06>
    712e:	25 cc       	rjmp	.-1974   	; 0x697a <__udivdi3+0x250>
    7130:	22 15       	cp	r18, r2
    7132:	33 05       	cpc	r19, r3
    7134:	44 05       	cpc	r20, r4
    7136:	55 05       	cpc	r21, r5
    7138:	08 f0       	brcs	.+2      	; 0x713c <__udivdi3+0xa12>
    713a:	1f cc       	rjmp	.-1986   	; 0x697a <__udivdi3+0x250>
    713c:	ee ef       	ldi	r30, 0xFE	; 254
    713e:	ee 2e       	mov	r14, r30
    7140:	ef ef       	ldi	r30, 0xFF	; 255
    7142:	fe 2e       	mov	r15, r30
    7144:	ef ef       	ldi	r30, 0xFF	; 255
    7146:	0e 2f       	mov	r16, r30
    7148:	ef ef       	ldi	r30, 0xFF	; 255
    714a:	1e 2f       	mov	r17, r30
    714c:	e6 0c       	add	r14, r6
    714e:	f7 1c       	adc	r15, r7
    7150:	08 1d       	adc	r16, r8
    7152:	19 1d       	adc	r17, r9
    7154:	12 cc       	rjmp	.-2012   	; 0x697a <__udivdi3+0x250>
    7156:	a2 01       	movw	r20, r4
    7158:	91 01       	movw	r18, r2
    715a:	21 50       	subi	r18, 0x01	; 1
    715c:	30 40       	sbci	r19, 0x00	; 0
    715e:	40 40       	sbci	r20, 0x00	; 0
    7160:	50 40       	sbci	r21, 0x00	; 0
    7162:	25 96       	adiw	r28, 0x05	; 5
    7164:	2c af       	std	Y+60, r18	; 0x3c
    7166:	3d af       	std	Y+61, r19	; 0x3d
    7168:	4e af       	std	Y+62, r20	; 0x3e
    716a:	5f af       	std	Y+63, r21	; 0x3f
    716c:	25 97       	sbiw	r28, 0x05	; 5
    716e:	ea 0c       	add	r14, r10
    7170:	fb 1c       	adc	r15, r11
    7172:	0c 1d       	adc	r16, r12
    7174:	1d 1d       	adc	r17, r13
    7176:	ea 14       	cp	r14, r10
    7178:	fb 04       	cpc	r15, r11
    717a:	0c 05       	cpc	r16, r12
    717c:	1d 05       	cpc	r17, r13
    717e:	08 f4       	brcc	.+2      	; 0x7182 <__udivdi3+0xa58>
    7180:	c1 cb       	rjmp	.-2174   	; 0x6904 <__udivdi3+0x1da>
    7182:	e6 14       	cp	r14, r6
    7184:	f7 04       	cpc	r15, r7
    7186:	08 05       	cpc	r16, r8
    7188:	19 05       	cpc	r17, r9
    718a:	08 f0       	brcs	.+2      	; 0x718e <__udivdi3+0xa64>
    718c:	bb cb       	rjmp	.-2186   	; 0x6904 <__udivdi3+0x1da>
    718e:	d2 01       	movw	r26, r4
    7190:	c1 01       	movw	r24, r2
    7192:	02 97       	sbiw	r24, 0x02	; 2
    7194:	a1 09       	sbc	r26, r1
    7196:	b1 09       	sbc	r27, r1
    7198:	25 96       	adiw	r28, 0x05	; 5
    719a:	8c af       	std	Y+60, r24	; 0x3c
    719c:	9d af       	std	Y+61, r25	; 0x3d
    719e:	ae af       	std	Y+62, r26	; 0x3e
    71a0:	bf af       	std	Y+63, r27	; 0x3f
    71a2:	25 97       	sbiw	r28, 0x05	; 5
    71a4:	ea 0c       	add	r14, r10
    71a6:	fb 1c       	adc	r15, r11
    71a8:	0c 1d       	adc	r16, r12
    71aa:	1d 1d       	adc	r17, r13
    71ac:	ab cb       	rjmp	.-2218   	; 0x6904 <__udivdi3+0x1da>
	    {
	      /* Normalize.  */

	      b = W_TYPE_SIZE - bm;

	      d0 = d0 << bm;
    71ae:	b1 01       	movw	r22, r2
    71b0:	02 2c       	mov	r0, r2
    71b2:	04 c0       	rjmp	.+8      	; 0x71bc <__udivdi3+0xa92>
    71b4:	aa 0c       	add	r10, r10
    71b6:	bb 1c       	adc	r11, r11
    71b8:	cc 1c       	adc	r12, r12
    71ba:	dd 1c       	adc	r13, r13
    71bc:	0a 94       	dec	r0
    71be:	d2 f7       	brpl	.-12     	; 0x71b4 <__udivdi3+0xa8a>
	      n2 = n1 >> b;
    71c0:	22 19       	sub	r18, r2
    71c2:	33 09       	sbc	r19, r3
    71c4:	44 09       	sbc	r20, r4
    71c6:	55 09       	sbc	r21, r5
    71c8:	da 01       	movw	r26, r20
    71ca:	c9 01       	movw	r24, r18
    71cc:	84 01       	movw	r16, r8
    71ce:	73 01       	movw	r14, r6
    71d0:	04 c0       	rjmp	.+8      	; 0x71da <__udivdi3+0xab0>
    71d2:	16 95       	lsr	r17
    71d4:	07 95       	ror	r16
    71d6:	f7 94       	ror	r15
    71d8:	e7 94       	ror	r14
    71da:	2a 95       	dec	r18
    71dc:	d2 f7       	brpl	.-12     	; 0x71d2 <__udivdi3+0xaa8>
	      n1 = (n1 << bm) | (n0 >> b);
    71de:	a4 01       	movw	r20, r8
    71e0:	93 01       	movw	r18, r6
    71e2:	04 c0       	rjmp	.+8      	; 0x71ec <__udivdi3+0xac2>
    71e4:	22 0f       	add	r18, r18
    71e6:	33 1f       	adc	r19, r19
    71e8:	44 1f       	adc	r20, r20
    71ea:	55 1f       	adc	r21, r21
    71ec:	2a 94       	dec	r2
    71ee:	d2 f7       	brpl	.-12     	; 0x71e4 <__udivdi3+0xaba>
    71f0:	a9 96       	adiw	r28, 0x29	; 41
    71f2:	2c ac       	ldd	r2, Y+60	; 0x3c
    71f4:	3d ac       	ldd	r3, Y+61	; 0x3d
    71f6:	4e ac       	ldd	r4, Y+62	; 0x3e
    71f8:	5f ac       	ldd	r5, Y+63	; 0x3f
    71fa:	a9 97       	sbiw	r28, 0x29	; 41
    71fc:	04 c0       	rjmp	.+8      	; 0x7206 <__udivdi3+0xadc>
    71fe:	56 94       	lsr	r5
    7200:	47 94       	ror	r4
    7202:	37 94       	ror	r3
    7204:	27 94       	ror	r2
    7206:	8a 95       	dec	r24
    7208:	d2 f7       	brpl	.-12     	; 0x71fe <__udivdi3+0xad4>
    720a:	22 29       	or	r18, r2
    720c:	33 29       	or	r19, r3
    720e:	44 29       	or	r20, r4
    7210:	55 29       	or	r21, r5
    7212:	29 a3       	std	Y+33, r18	; 0x21
    7214:	3a a3       	std	Y+34, r19	; 0x22
    7216:	4b a3       	std	Y+35, r20	; 0x23
    7218:	5c a3       	std	Y+36, r21	; 0x24
	      n0 = n0 << bm;
    721a:	a9 96       	adiw	r28, 0x29	; 41
    721c:	4c ac       	ldd	r4, Y+60	; 0x3c
    721e:	5d ac       	ldd	r5, Y+61	; 0x3d
    7220:	6e ac       	ldd	r6, Y+62	; 0x3e
    7222:	7f ac       	ldd	r7, Y+63	; 0x3f
    7224:	a9 97       	sbiw	r28, 0x29	; 41
    7226:	04 c0       	rjmp	.+8      	; 0x7230 <__udivdi3+0xb06>
    7228:	44 0c       	add	r4, r4
    722a:	55 1c       	adc	r5, r5
    722c:	66 1c       	adc	r6, r6
    722e:	77 1c       	adc	r7, r7
    7230:	6a 95       	dec	r22
    7232:	d2 f7       	brpl	.-12     	; 0x7228 <__udivdi3+0xafe>
    7234:	a9 96       	adiw	r28, 0x29	; 41
    7236:	4c ae       	std	Y+60, r4	; 0x3c
    7238:	5d ae       	std	Y+61, r5	; 0x3d
    723a:	6e ae       	std	Y+62, r6	; 0x3e
    723c:	7f ae       	std	Y+63, r7	; 0x3f
    723e:	a9 97       	sbiw	r28, 0x29	; 41

	      udiv_qrnnd (q1, n1, n2, n1, d0);
    7240:	36 01       	movw	r6, r12
    7242:	88 24       	eor	r8, r8
    7244:	99 24       	eor	r9, r9
    7246:	61 96       	adiw	r28, 0x11	; 17
    7248:	6c ae       	std	Y+60, r6	; 0x3c
    724a:	7d ae       	std	Y+61, r7	; 0x3d
    724c:	8e ae       	std	Y+62, r8	; 0x3e
    724e:	9f ae       	std	Y+63, r9	; 0x3f
    7250:	61 97       	sbiw	r28, 0x11	; 17
    7252:	a6 01       	movw	r20, r12
    7254:	95 01       	movw	r18, r10
    7256:	40 70       	andi	r20, 0x00	; 0
    7258:	50 70       	andi	r21, 0x00	; 0
    725a:	2d 96       	adiw	r28, 0x0d	; 13
    725c:	2c af       	std	Y+60, r18	; 0x3c
    725e:	3d af       	std	Y+61, r19	; 0x3d
    7260:	4e af       	std	Y+62, r20	; 0x3e
    7262:	5f af       	std	Y+63, r21	; 0x3f
    7264:	2d 97       	sbiw	r28, 0x0d	; 13
    7266:	c8 01       	movw	r24, r16
    7268:	b7 01       	movw	r22, r14
    726a:	a4 01       	movw	r20, r8
    726c:	93 01       	movw	r18, r6
    726e:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7272:	19 01       	movw	r2, r18
    7274:	2a 01       	movw	r4, r20
    7276:	2d 96       	adiw	r28, 0x0d	; 13
    7278:	6c ad       	ldd	r22, Y+60	; 0x3c
    727a:	7d ad       	ldd	r23, Y+61	; 0x3d
    727c:	8e ad       	ldd	r24, Y+62	; 0x3e
    727e:	9f ad       	ldd	r25, Y+63	; 0x3f
    7280:	2d 97       	sbiw	r28, 0x0d	; 13
    7282:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7286:	3b 01       	movw	r6, r22
    7288:	4c 01       	movw	r8, r24
    728a:	c8 01       	movw	r24, r16
    728c:	b7 01       	movw	r22, r14
    728e:	61 96       	adiw	r28, 0x11	; 17
    7290:	2c ad       	ldd	r18, Y+60	; 0x3c
    7292:	3d ad       	ldd	r19, Y+61	; 0x3d
    7294:	4e ad       	ldd	r20, Y+62	; 0x3e
    7296:	5f ad       	ldd	r21, Y+63	; 0x3f
    7298:	61 97       	sbiw	r28, 0x11	; 17
    729a:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    729e:	cb 01       	movw	r24, r22
    72a0:	77 27       	eor	r23, r23
    72a2:	66 27       	eor	r22, r22
    72a4:	e9 a0       	ldd	r14, Y+33	; 0x21
    72a6:	fa a0       	ldd	r15, Y+34	; 0x22
    72a8:	0b a1       	ldd	r16, Y+35	; 0x23
    72aa:	1c a1       	ldd	r17, Y+36	; 0x24
    72ac:	98 01       	movw	r18, r16
    72ae:	44 27       	eor	r20, r20
    72b0:	55 27       	eor	r21, r21
    72b2:	7b 01       	movw	r14, r22
    72b4:	8c 01       	movw	r16, r24
    72b6:	e2 2a       	or	r14, r18
    72b8:	f3 2a       	or	r15, r19
    72ba:	04 2b       	or	r16, r20
    72bc:	15 2b       	or	r17, r21
    72be:	e6 14       	cp	r14, r6
    72c0:	f7 04       	cpc	r15, r7
    72c2:	08 05       	cpc	r16, r8
    72c4:	19 05       	cpc	r17, r9
    72c6:	08 f4       	brcc	.+2      	; 0x72ca <__udivdi3+0xba0>
    72c8:	14 c1       	rjmp	.+552    	; 0x74f2 <__udivdi3+0xdc8>
    72ca:	29 96       	adiw	r28, 0x09	; 9
    72cc:	2c ae       	std	Y+60, r2	; 0x3c
    72ce:	3d ae       	std	Y+61, r3	; 0x3d
    72d0:	4e ae       	std	Y+62, r4	; 0x3e
    72d2:	5f ae       	std	Y+63, r5	; 0x3f
    72d4:	29 97       	sbiw	r28, 0x09	; 9
    72d6:	e6 18       	sub	r14, r6
    72d8:	f7 08       	sbc	r15, r7
    72da:	08 09       	sbc	r16, r8
    72dc:	19 09       	sbc	r17, r9
    72de:	c8 01       	movw	r24, r16
    72e0:	b7 01       	movw	r22, r14
    72e2:	61 96       	adiw	r28, 0x11	; 17
    72e4:	2c ad       	ldd	r18, Y+60	; 0x3c
    72e6:	3d ad       	ldd	r19, Y+61	; 0x3d
    72e8:	4e ad       	ldd	r20, Y+62	; 0x3e
    72ea:	5f ad       	ldd	r21, Y+63	; 0x3f
    72ec:	61 97       	sbiw	r28, 0x11	; 17
    72ee:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    72f2:	19 01       	movw	r2, r18
    72f4:	2a 01       	movw	r4, r20
    72f6:	2d 96       	adiw	r28, 0x0d	; 13
    72f8:	6c ad       	ldd	r22, Y+60	; 0x3c
    72fa:	7d ad       	ldd	r23, Y+61	; 0x3d
    72fc:	8e ad       	ldd	r24, Y+62	; 0x3e
    72fe:	9f ad       	ldd	r25, Y+63	; 0x3f
    7300:	2d 97       	sbiw	r28, 0x0d	; 13
    7302:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7306:	3b 01       	movw	r6, r22
    7308:	4c 01       	movw	r8, r24
    730a:	c8 01       	movw	r24, r16
    730c:	b7 01       	movw	r22, r14
    730e:	61 96       	adiw	r28, 0x11	; 17
    7310:	2c ad       	ldd	r18, Y+60	; 0x3c
    7312:	3d ad       	ldd	r19, Y+61	; 0x3d
    7314:	4e ad       	ldd	r20, Y+62	; 0x3e
    7316:	5f ad       	ldd	r21, Y+63	; 0x3f
    7318:	61 97       	sbiw	r28, 0x11	; 17
    731a:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    731e:	cb 01       	movw	r24, r22
    7320:	77 27       	eor	r23, r23
    7322:	66 27       	eor	r22, r22
    7324:	29 a1       	ldd	r18, Y+33	; 0x21
    7326:	3a a1       	ldd	r19, Y+34	; 0x22
    7328:	4b a1       	ldd	r20, Y+35	; 0x23
    732a:	5c a1       	ldd	r21, Y+36	; 0x24
    732c:	40 70       	andi	r20, 0x00	; 0
    732e:	50 70       	andi	r21, 0x00	; 0
    7330:	7b 01       	movw	r14, r22
    7332:	8c 01       	movw	r16, r24
    7334:	e2 2a       	or	r14, r18
    7336:	f3 2a       	or	r15, r19
    7338:	04 2b       	or	r16, r20
    733a:	15 2b       	or	r17, r21
    733c:	e6 14       	cp	r14, r6
    733e:	f7 04       	cpc	r15, r7
    7340:	08 05       	cpc	r16, r8
    7342:	19 05       	cpc	r17, r9
    7344:	08 f4       	brcc	.+2      	; 0x7348 <__udivdi3+0xc1e>
    7346:	b4 c0       	rjmp	.+360    	; 0x74b0 <__udivdi3+0xd86>
    7348:	a2 01       	movw	r20, r4
    734a:	91 01       	movw	r18, r2
    734c:	e6 18       	sub	r14, r6
    734e:	f7 08       	sbc	r15, r7
    7350:	08 09       	sbc	r16, r8
    7352:	19 09       	sbc	r17, r9
    7354:	29 96       	adiw	r28, 0x09	; 9
    7356:	2c ac       	ldd	r2, Y+60	; 0x3c
    7358:	3d ac       	ldd	r3, Y+61	; 0x3d
    735a:	4e ac       	ldd	r4, Y+62	; 0x3e
    735c:	5f ac       	ldd	r5, Y+63	; 0x3f
    735e:	29 97       	sbiw	r28, 0x09	; 9
    7360:	d1 01       	movw	r26, r2
    7362:	99 27       	eor	r25, r25
    7364:	88 27       	eor	r24, r24
    7366:	28 2b       	or	r18, r24
    7368:	39 2b       	or	r19, r25
    736a:	4a 2b       	or	r20, r26
    736c:	5b 2b       	or	r21, r27
    736e:	a1 96       	adiw	r28, 0x21	; 33
    7370:	2c af       	std	Y+60, r18	; 0x3c
    7372:	3d af       	std	Y+61, r19	; 0x3d
    7374:	4e af       	std	Y+62, r20	; 0x3e
    7376:	5f af       	std	Y+63, r21	; 0x3f
    7378:	a1 97       	sbiw	r28, 0x21	; 33
    737a:	86 ca       	rjmp	.-2804   	; 0x6888 <__udivdi3+0x15e>
	      n0 = n0 << bm;

	      udiv_qrnnd (q0, n1, n2, n1, d1);
	      umul_ppmm (m1, m0, q0, d0);

	      if (m1 > n1 || (m1 == n1 && m0 > n0))
    737c:	8d 8d       	ldd	r24, Y+29	; 0x1d
    737e:	9e 8d       	ldd	r25, Y+30	; 0x1e
    7380:	af 8d       	ldd	r26, Y+31	; 0x1f
    7382:	b8 a1       	ldd	r27, Y+32	; 0x20
    7384:	8a 15       	cp	r24, r10
    7386:	9b 05       	cpc	r25, r11
    7388:	ac 05       	cpc	r26, r12
    738a:	bd 05       	cpc	r27, r13
    738c:	08 f0       	brcs	.+2      	; 0x7390 <__udivdi3+0xc66>
    738e:	60 ce       	rjmp	.-832    	; 0x7050 <__udivdi3+0x926>
    7390:	a5 96       	adiw	r28, 0x25	; 37
    7392:	ec ac       	ldd	r14, Y+60	; 0x3c
    7394:	fd ac       	ldd	r15, Y+61	; 0x3d
    7396:	0e ad       	ldd	r16, Y+62	; 0x3e
    7398:	1f ad       	ldd	r17, Y+63	; 0x3f
    739a:	a5 97       	sbiw	r28, 0x25	; 37
    739c:	08 94       	sec
    739e:	e1 08       	sbc	r14, r1
    73a0:	f1 08       	sbc	r15, r1
    73a2:	01 09       	sbc	r16, r1
    73a4:	11 09       	sbc	r17, r1
    73a6:	20 e0       	ldi	r18, 0x00	; 0
    73a8:	30 e0       	ldi	r19, 0x00	; 0
    73aa:	40 e0       	ldi	r20, 0x00	; 0
    73ac:	50 e0       	ldi	r21, 0x00	; 0
    73ae:	cd c0       	rjmp	.+410    	; 0x754a <__udivdi3+0xe20>
	      d0 = d0 << bm;
	      n2 = n1 >> b;
	      n1 = (n1 << bm) | (n0 >> b);
	      n0 = n0 << bm;

	      udiv_qrnnd (q0, n1, n2, n1, d1);
    73b0:	a2 01       	movw	r20, r4
    73b2:	91 01       	movw	r18, r2
    73b4:	21 50       	subi	r18, 0x01	; 1
    73b6:	30 40       	sbci	r19, 0x00	; 0
    73b8:	40 40       	sbci	r20, 0x00	; 0
    73ba:	50 40       	sbci	r21, 0x00	; 0
    73bc:	ad 96       	adiw	r28, 0x2d	; 45
    73be:	8c ad       	ldd	r24, Y+60	; 0x3c
    73c0:	9d ad       	ldd	r25, Y+61	; 0x3d
    73c2:	ae ad       	ldd	r26, Y+62	; 0x3e
    73c4:	bf ad       	ldd	r27, Y+63	; 0x3f
    73c6:	ad 97       	sbiw	r28, 0x2d	; 45
    73c8:	a8 0e       	add	r10, r24
    73ca:	b9 1e       	adc	r11, r25
    73cc:	ca 1e       	adc	r12, r26
    73ce:	db 1e       	adc	r13, r27
    73d0:	a8 16       	cp	r10, r24
    73d2:	b9 06       	cpc	r11, r25
    73d4:	ca 06       	cpc	r12, r26
    73d6:	db 06       	cpc	r13, r27
    73d8:	08 f4       	brcc	.+2      	; 0x73dc <__udivdi3+0xcb2>
    73da:	94 cd       	rjmp	.-1240   	; 0x6f04 <__udivdi3+0x7da>
    73dc:	a6 14       	cp	r10, r6
    73de:	b7 04       	cpc	r11, r7
    73e0:	c8 04       	cpc	r12, r8
    73e2:	d9 04       	cpc	r13, r9
    73e4:	08 f0       	brcs	.+2      	; 0x73e8 <__udivdi3+0xcbe>
    73e6:	8e cd       	rjmp	.-1252   	; 0x6f04 <__udivdi3+0x7da>
    73e8:	a2 01       	movw	r20, r4
    73ea:	91 01       	movw	r18, r2
    73ec:	22 50       	subi	r18, 0x02	; 2
    73ee:	30 40       	sbci	r19, 0x00	; 0
    73f0:	40 40       	sbci	r20, 0x00	; 0
    73f2:	50 40       	sbci	r21, 0x00	; 0
    73f4:	a8 0e       	add	r10, r24
    73f6:	b9 1e       	adc	r11, r25
    73f8:	ca 1e       	adc	r12, r26
    73fa:	db 1e       	adc	r13, r27
    73fc:	83 cd       	rjmp	.-1274   	; 0x6f04 <__udivdi3+0x7da>
	}
      else
	{
	  /* 0q = NN / dd */

	  count_leading_zeros (bm, d1);
    73fe:	28 e1       	ldi	r18, 0x18	; 24
    7400:	30 e0       	ldi	r19, 0x00	; 0
    7402:	40 e0       	ldi	r20, 0x00	; 0
    7404:	50 e0       	ldi	r21, 0x00	; 0
    7406:	88 e1       	ldi	r24, 0x18	; 24
    7408:	90 e0       	ldi	r25, 0x00	; 0
    740a:	e9 ca       	rjmp	.-2606   	; 0x69de <__udivdi3+0x2b4>
    740c:	28 e0       	ldi	r18, 0x08	; 8
    740e:	30 e0       	ldi	r19, 0x00	; 0
    7410:	40 e0       	ldi	r20, 0x00	; 0
    7412:	50 e0       	ldi	r21, 0x00	; 0
    7414:	88 e0       	ldi	r24, 0x08	; 8
    7416:	90 e0       	ldi	r25, 0x00	; 0
    7418:	e2 ca       	rjmp	.-2620   	; 0x69de <__udivdi3+0x2b4>
	      d0 = d0 << bm;
	      n2 = n1 >> b;
	      n1 = (n1 << bm) | (n0 >> b);
	      n0 = n0 << bm;

	      udiv_qrnnd (q0, n1, n2, n1, d1);
    741a:	a2 01       	movw	r20, r4
    741c:	91 01       	movw	r18, r2
    741e:	21 50       	subi	r18, 0x01	; 1
    7420:	30 40       	sbci	r19, 0x00	; 0
    7422:	40 40       	sbci	r20, 0x00	; 0
    7424:	50 40       	sbci	r21, 0x00	; 0
    7426:	2d ab       	std	Y+53, r18	; 0x35
    7428:	3e ab       	std	Y+54, r19	; 0x36
    742a:	4f ab       	std	Y+55, r20	; 0x37
    742c:	58 af       	std	Y+56, r21	; 0x38
    742e:	ad 96       	adiw	r28, 0x2d	; 45
    7430:	8c ad       	ldd	r24, Y+60	; 0x3c
    7432:	9d ad       	ldd	r25, Y+61	; 0x3d
    7434:	ae ad       	ldd	r26, Y+62	; 0x3e
    7436:	bf ad       	ldd	r27, Y+63	; 0x3f
    7438:	ad 97       	sbiw	r28, 0x2d	; 45
    743a:	e8 0e       	add	r14, r24
    743c:	f9 1e       	adc	r15, r25
    743e:	0a 1f       	adc	r16, r26
    7440:	1b 1f       	adc	r17, r27
    7442:	e8 16       	cp	r14, r24
    7444:	f9 06       	cpc	r15, r25
    7446:	0a 07       	cpc	r16, r26
    7448:	1b 07       	cpc	r17, r27
    744a:	08 f4       	brcc	.+2      	; 0x744e <__udivdi3+0xd24>
    744c:	22 cd       	rjmp	.-1468   	; 0x6e92 <__udivdi3+0x768>
    744e:	e6 14       	cp	r14, r6
    7450:	f7 04       	cpc	r15, r7
    7452:	08 05       	cpc	r16, r8
    7454:	19 05       	cpc	r17, r9
    7456:	08 f0       	brcs	.+2      	; 0x745a <__udivdi3+0xd30>
    7458:	1c cd       	rjmp	.-1480   	; 0x6e92 <__udivdi3+0x768>
    745a:	a2 01       	movw	r20, r4
    745c:	91 01       	movw	r18, r2
    745e:	22 50       	subi	r18, 0x02	; 2
    7460:	30 40       	sbci	r19, 0x00	; 0
    7462:	40 40       	sbci	r20, 0x00	; 0
    7464:	50 40       	sbci	r21, 0x00	; 0
    7466:	2d ab       	std	Y+53, r18	; 0x35
    7468:	3e ab       	std	Y+54, r19	; 0x36
    746a:	4f ab       	std	Y+55, r20	; 0x37
    746c:	58 af       	std	Y+56, r21	; 0x38
    746e:	e8 0e       	add	r14, r24
    7470:	f9 1e       	adc	r15, r25
    7472:	0a 1f       	adc	r16, r26
    7474:	1b 1f       	adc	r17, r27
    7476:	0d cd       	rjmp	.-1510   	; 0x6e92 <__udivdi3+0x768>
	  /* qq = NN / 0d */

	  if (d0 == 0)
	    d0 = 1 / d0;	/* Divide intentionally by zero.  */

	  count_leading_zeros (bm, d0);
    7478:	28 e0       	ldi	r18, 0x08	; 8
    747a:	30 e0       	ldi	r19, 0x00	; 0
    747c:	40 e0       	ldi	r20, 0x00	; 0
    747e:	50 e0       	ldi	r21, 0x00	; 0
    7480:	88 e0       	ldi	r24, 0x08	; 8
    7482:	90 e0       	ldi	r25, 0x00	; 0
    7484:	bd c9       	rjmp	.-3206   	; 0x6800 <__udivdi3+0xd6>
    {
      if (d0 > n1)
	{
	  /* 0q = nn / 0D */

	  count_leading_zeros (bm, d0);
    7486:	28 e0       	ldi	r18, 0x08	; 8
    7488:	30 e0       	ldi	r19, 0x00	; 0
    748a:	40 e0       	ldi	r20, 0x00	; 0
    748c:	50 e0       	ldi	r21, 0x00	; 0
    748e:	88 e0       	ldi	r24, 0x08	; 8
    7490:	90 e0       	ldi	r25, 0x00	; 0
    7492:	fa ca       	rjmp	.-2572   	; 0x6a88 <__udivdi3+0x35e>
    7494:	28 e1       	ldi	r18, 0x18	; 24
    7496:	30 e0       	ldi	r19, 0x00	; 0
    7498:	40 e0       	ldi	r20, 0x00	; 0
    749a:	50 e0       	ldi	r21, 0x00	; 0
    749c:	88 e1       	ldi	r24, 0x18	; 24
    749e:	90 e0       	ldi	r25, 0x00	; 0
    74a0:	f3 ca       	rjmp	.-2586   	; 0x6a88 <__udivdi3+0x35e>
	  /* qq = NN / 0d */

	  if (d0 == 0)
	    d0 = 1 / d0;	/* Divide intentionally by zero.  */

	  count_leading_zeros (bm, d0);
    74a2:	28 e1       	ldi	r18, 0x18	; 24
    74a4:	30 e0       	ldi	r19, 0x00	; 0
    74a6:	40 e0       	ldi	r20, 0x00	; 0
    74a8:	50 e0       	ldi	r21, 0x00	; 0
    74aa:	88 e1       	ldi	r24, 0x18	; 24
    74ac:	90 e0       	ldi	r25, 0x00	; 0
    74ae:	a8 c9       	rjmp	.-3248   	; 0x6800 <__udivdi3+0xd6>
	      d0 = d0 << bm;
	      n2 = n1 >> b;
	      n1 = (n1 << bm) | (n0 >> b);
	      n0 = n0 << bm;

	      udiv_qrnnd (q1, n1, n2, n1, d0);
    74b0:	a2 01       	movw	r20, r4
    74b2:	91 01       	movw	r18, r2
    74b4:	21 50       	subi	r18, 0x01	; 1
    74b6:	30 40       	sbci	r19, 0x00	; 0
    74b8:	40 40       	sbci	r20, 0x00	; 0
    74ba:	50 40       	sbci	r21, 0x00	; 0
    74bc:	ea 0c       	add	r14, r10
    74be:	fb 1c       	adc	r15, r11
    74c0:	0c 1d       	adc	r16, r12
    74c2:	1d 1d       	adc	r17, r13
    74c4:	ea 14       	cp	r14, r10
    74c6:	fb 04       	cpc	r15, r11
    74c8:	0c 05       	cpc	r16, r12
    74ca:	1d 05       	cpc	r17, r13
    74cc:	08 f4       	brcc	.+2      	; 0x74d0 <__udivdi3+0xda6>
    74ce:	3e cf       	rjmp	.-388    	; 0x734c <__udivdi3+0xc22>
    74d0:	e6 14       	cp	r14, r6
    74d2:	f7 04       	cpc	r15, r7
    74d4:	08 05       	cpc	r16, r8
    74d6:	19 05       	cpc	r17, r9
    74d8:	08 f0       	brcs	.+2      	; 0x74dc <__udivdi3+0xdb2>
    74da:	38 cf       	rjmp	.-400    	; 0x734c <__udivdi3+0xc22>
    74dc:	a2 01       	movw	r20, r4
    74de:	91 01       	movw	r18, r2
    74e0:	22 50       	subi	r18, 0x02	; 2
    74e2:	30 40       	sbci	r19, 0x00	; 0
    74e4:	40 40       	sbci	r20, 0x00	; 0
    74e6:	50 40       	sbci	r21, 0x00	; 0
    74e8:	ea 0c       	add	r14, r10
    74ea:	fb 1c       	adc	r15, r11
    74ec:	0c 1d       	adc	r16, r12
    74ee:	1d 1d       	adc	r17, r13
    74f0:	2d cf       	rjmp	.-422    	; 0x734c <__udivdi3+0xc22>
    74f2:	a2 01       	movw	r20, r4
    74f4:	91 01       	movw	r18, r2
    74f6:	21 50       	subi	r18, 0x01	; 1
    74f8:	30 40       	sbci	r19, 0x00	; 0
    74fa:	40 40       	sbci	r20, 0x00	; 0
    74fc:	50 40       	sbci	r21, 0x00	; 0
    74fe:	29 96       	adiw	r28, 0x09	; 9
    7500:	2c af       	std	Y+60, r18	; 0x3c
    7502:	3d af       	std	Y+61, r19	; 0x3d
    7504:	4e af       	std	Y+62, r20	; 0x3e
    7506:	5f af       	std	Y+63, r21	; 0x3f
    7508:	29 97       	sbiw	r28, 0x09	; 9
    750a:	ea 0c       	add	r14, r10
    750c:	fb 1c       	adc	r15, r11
    750e:	0c 1d       	adc	r16, r12
    7510:	1d 1d       	adc	r17, r13
    7512:	ea 14       	cp	r14, r10
    7514:	fb 04       	cpc	r15, r11
    7516:	0c 05       	cpc	r16, r12
    7518:	1d 05       	cpc	r17, r13
    751a:	08 f4       	brcc	.+2      	; 0x751e <__udivdi3+0xdf4>
    751c:	dc ce       	rjmp	.-584    	; 0x72d6 <__udivdi3+0xbac>
    751e:	e6 14       	cp	r14, r6
    7520:	f7 04       	cpc	r15, r7
    7522:	08 05       	cpc	r16, r8
    7524:	19 05       	cpc	r17, r9
    7526:	08 f0       	brcs	.+2      	; 0x752a <__udivdi3+0xe00>
    7528:	d6 ce       	rjmp	.-596    	; 0x72d6 <__udivdi3+0xbac>
    752a:	d2 01       	movw	r26, r4
    752c:	c1 01       	movw	r24, r2
    752e:	02 97       	sbiw	r24, 0x02	; 2
    7530:	a1 09       	sbc	r26, r1
    7532:	b1 09       	sbc	r27, r1
    7534:	29 96       	adiw	r28, 0x09	; 9
    7536:	8c af       	std	Y+60, r24	; 0x3c
    7538:	9d af       	std	Y+61, r25	; 0x3d
    753a:	ae af       	std	Y+62, r26	; 0x3e
    753c:	bf af       	std	Y+63, r27	; 0x3f
    753e:	29 97       	sbiw	r28, 0x09	; 9
    7540:	ea 0c       	add	r14, r10
    7542:	fb 1c       	adc	r15, r11
    7544:	0c 1d       	adc	r16, r12
    7546:	1d 1d       	adc	r17, r13
    7548:	c6 ce       	rjmp	.-628    	; 0x72d6 <__udivdi3+0xbac>
		}
	    }
	}
    }

  const DWunion ww = {{.low = q0, .high = q1}};
    754a:	88 e0       	ldi	r24, 0x08	; 8
    754c:	fe 01       	movw	r30, r28
    754e:	71 96       	adiw	r30, 0x11	; 17
    7550:	11 92       	st	Z+, r1
    7552:	8a 95       	dec	r24
    7554:	e9 f7       	brne	.-6      	; 0x7550 <__udivdi3+0xe26>
    7556:	e9 8a       	std	Y+17, r14	; 0x11
    7558:	fa 8a       	std	Y+18, r15	; 0x12
    755a:	0b 8b       	std	Y+19, r16	; 0x13
    755c:	1c 8b       	std	Y+20, r17	; 0x14
    755e:	2d 8b       	std	Y+21, r18	; 0x15
    7560:	3e 8b       	std	Y+22, r19	; 0x16
    7562:	4f 8b       	std	Y+23, r20	; 0x17
    7564:	58 8f       	std	Y+24, r21	; 0x18
    7566:	29 88       	ldd	r2, Y+17	; 0x11
    7568:	3a 88       	ldd	r3, Y+18	; 0x12
    756a:	4b 88       	ldd	r4, Y+19	; 0x13
    756c:	5c 88       	ldd	r5, Y+20	; 0x14
    756e:	6d 88       	ldd	r6, Y+21	; 0x15
    7570:	7e 88       	ldd	r7, Y+22	; 0x16
    7572:	8f 88       	ldd	r8, Y+23	; 0x17
    7574:	98 8c       	ldd	r9, Y+24	; 0x18
#ifdef L_udivdi3
UDWtype
__udivdi3 (UDWtype n, UDWtype d)
{
  return __udivmoddi4 (n, d, (UDWtype *) 0);
}
    7576:	22 2d       	mov	r18, r2
    7578:	33 2d       	mov	r19, r3
    757a:	44 2d       	mov	r20, r4
    757c:	55 2d       	mov	r21, r5
    757e:	66 2d       	mov	r22, r6
    7580:	77 2d       	mov	r23, r7
    7582:	88 2d       	mov	r24, r8
    7584:	99 2d       	mov	r25, r9
    7586:	e2 e1       	ldi	r30, 0x12	; 18
    7588:	c4 59       	subi	r28, 0x94	; 148
    758a:	df 4f       	sbci	r29, 0xFF	; 255
    758c:	0c 94 d0 41 	jmp	0x83a0	; 0x83a0 <__epilogue_restores__>

00007590 <__umoddi3>:
#endif

#ifdef L_umoddi3
UDWtype
__umoddi3 (UDWtype u, UDWtype v)
{
    7590:	a2 e5       	ldi	r26, 0x52	; 82
    7592:	b0 e0       	ldi	r27, 0x00	; 0
    7594:	ee ec       	ldi	r30, 0xCE	; 206
    7596:	fa e3       	ldi	r31, 0x3A	; 58
    7598:	0c 94 b4 41 	jmp	0x8368	; 0x8368 <__prologue_saves__>
    759c:	22 2e       	mov	r2, r18
    759e:	33 2e       	mov	r3, r19
    75a0:	44 2e       	mov	r4, r20
    75a2:	55 2e       	mov	r5, r21
    75a4:	66 2e       	mov	r6, r22
    75a6:	77 2e       	mov	r7, r23
    75a8:	88 2e       	mov	r8, r24
    75aa:	99 2e       	mov	r9, r25
    75ac:	2a 2d       	mov	r18, r10
static inline __attribute__ ((__always_inline__))
#endif
UDWtype
__udivmoddi4 (UDWtype n, UDWtype d, UDWtype *rp)
{
  const DWunion nn = {.ll = n};
    75ae:	a8 e0       	ldi	r26, 0x08	; 8
    75b0:	fe 01       	movw	r30, r28
    75b2:	31 96       	adiw	r30, 0x01	; 1
    75b4:	aa 2e       	mov	r10, r26
    75b6:	11 92       	st	Z+, r1
    75b8:	aa 94       	dec	r10
    75ba:	e9 f7       	brne	.-6      	; 0x75b6 <__umoddi3+0x26>
    75bc:	29 82       	std	Y+1, r2	; 0x01
    75be:	3a 82       	std	Y+2, r3	; 0x02
    75c0:	4b 82       	std	Y+3, r4	; 0x03
    75c2:	5c 82       	std	Y+4, r5	; 0x04
    75c4:	6d 82       	std	Y+5, r6	; 0x05
    75c6:	7e 82       	std	Y+6, r7	; 0x06
    75c8:	8f 82       	std	Y+7, r8	; 0x07
    75ca:	98 86       	std	Y+8, r9	; 0x08
  const DWunion dd = {.ll = d};
    75cc:	fe 01       	movw	r30, r28
    75ce:	39 96       	adiw	r30, 0x09	; 9
    75d0:	11 92       	st	Z+, r1
    75d2:	aa 95       	dec	r26
    75d4:	e9 f7       	brne	.-6      	; 0x75d0 <__umoddi3+0x40>
    75d6:	29 87       	std	Y+9, r18	; 0x09
    75d8:	ba 86       	std	Y+10, r11	; 0x0a
    75da:	cb 86       	std	Y+11, r12	; 0x0b
    75dc:	dc 86       	std	Y+12, r13	; 0x0c
    75de:	ed 86       	std	Y+13, r14	; 0x0d
    75e0:	fe 86       	std	Y+14, r15	; 0x0e
    75e2:	0f 87       	std	Y+15, r16	; 0x0f
    75e4:	18 8b       	std	Y+16, r17	; 0x10
  DWunion rr;
  UWtype d0, d1, n0, n1, n2;
  UWtype q0, q1;
  UWtype b, bm;

  d0 = dd.s.low;
    75e6:	69 84       	ldd	r6, Y+9	; 0x09
    75e8:	7a 84       	ldd	r7, Y+10	; 0x0a
    75ea:	8b 84       	ldd	r8, Y+11	; 0x0b
    75ec:	9c 84       	ldd	r9, Y+12	; 0x0c
  d1 = dd.s.high;
    75ee:	ed 84       	ldd	r14, Y+13	; 0x0d
    75f0:	fe 84       	ldd	r15, Y+14	; 0x0e
    75f2:	0f 85       	ldd	r16, Y+15	; 0x0f
    75f4:	18 89       	ldd	r17, Y+16	; 0x10
  n0 = nn.s.low;
    75f6:	29 81       	ldd	r18, Y+1	; 0x01
    75f8:	3a 81       	ldd	r19, Y+2	; 0x02
    75fa:	4b 81       	ldd	r20, Y+3	; 0x03
    75fc:	5c 81       	ldd	r21, Y+4	; 0x04
    75fe:	29 a3       	std	Y+33, r18	; 0x21
    7600:	3a a3       	std	Y+34, r19	; 0x22
    7602:	4b a3       	std	Y+35, r20	; 0x23
    7604:	5c a3       	std	Y+36, r21	; 0x24
  n1 = nn.s.high;
    7606:	2d 80       	ldd	r2, Y+5	; 0x05
    7608:	3e 80       	ldd	r3, Y+6	; 0x06
    760a:	4f 80       	ldd	r4, Y+7	; 0x07
    760c:	58 84       	ldd	r5, Y+8	; 0x08
	}
    }

#else /* UDIV_NEEDS_NORMALIZATION */

  if (d1 == 0)
    760e:	e1 14       	cp	r14, r1
    7610:	f1 04       	cpc	r15, r1
    7612:	01 05       	cpc	r16, r1
    7614:	11 05       	cpc	r17, r1
    7616:	09 f0       	breq	.+2      	; 0x761a <__umoddi3+0x8a>
    7618:	ad c0       	rjmp	.+346    	; 0x7774 <__umoddi3+0x1e4>
    {
      if (d0 > n1)
    761a:	26 14       	cp	r2, r6
    761c:	37 04       	cpc	r3, r7
    761e:	48 04       	cpc	r4, r8
    7620:	59 04       	cpc	r5, r9
    7622:	08 f4       	brcc	.+2      	; 0x7626 <__umoddi3+0x96>
    7624:	0f c1       	rjmp	.+542    	; 0x7844 <__umoddi3+0x2b4>
	}
      else
	{
	  /* qq = NN / 0d */

	  if (d0 == 0)
    7626:	61 14       	cp	r6, r1
    7628:	71 04       	cpc	r7, r1
    762a:	81 04       	cpc	r8, r1
    762c:	91 04       	cpc	r9, r1
    762e:	09 f4       	brne	.+2      	; 0x7632 <__umoddi3+0xa2>
    7630:	05 c2       	rjmp	.+1034   	; 0x7a3c <__umoddi3+0x4ac>
	    d0 = 1 / d0;	/* Divide intentionally by zero.  */

	  count_leading_zeros (bm, d0);
    7632:	00 e0       	ldi	r16, 0x00	; 0
    7634:	60 16       	cp	r6, r16
    7636:	00 e0       	ldi	r16, 0x00	; 0
    7638:	70 06       	cpc	r7, r16
    763a:	01 e0       	ldi	r16, 0x01	; 1
    763c:	80 06       	cpc	r8, r16
    763e:	00 e0       	ldi	r16, 0x00	; 0
    7640:	90 06       	cpc	r9, r16
    7642:	08 f0       	brcs	.+2      	; 0x7646 <__umoddi3+0xb6>
    7644:	ea c1       	rjmp	.+980    	; 0x7a1a <__umoddi3+0x48a>
    7646:	1f ef       	ldi	r17, 0xFF	; 255
    7648:	61 16       	cp	r6, r17
    764a:	71 04       	cpc	r7, r1
    764c:	81 04       	cpc	r8, r1
    764e:	91 04       	cpc	r9, r1
    7650:	11 f0       	breq	.+4      	; 0x7656 <__umoddi3+0xc6>
    7652:	08 f0       	brcs	.+2      	; 0x7656 <__umoddi3+0xc6>
    7654:	e0 c5       	rjmp	.+3008   	; 0x8216 <__umoddi3+0xc86>
    7656:	20 e0       	ldi	r18, 0x00	; 0
    7658:	30 e0       	ldi	r19, 0x00	; 0
    765a:	40 e0       	ldi	r20, 0x00	; 0
    765c:	50 e0       	ldi	r21, 0x00	; 0
    765e:	80 e0       	ldi	r24, 0x00	; 0
    7660:	90 e0       	ldi	r25, 0x00	; 0
    7662:	64 01       	movw	r12, r8
    7664:	53 01       	movw	r10, r6
    7666:	04 c0       	rjmp	.+8      	; 0x7670 <__umoddi3+0xe0>
    7668:	d6 94       	lsr	r13
    766a:	c7 94       	ror	r12
    766c:	b7 94       	ror	r11
    766e:	a7 94       	ror	r10
    7670:	8a 95       	dec	r24
    7672:	d2 f7       	brpl	.-12     	; 0x7668 <__umoddi3+0xd8>
    7674:	d6 01       	movw	r26, r12
    7676:	c5 01       	movw	r24, r10
    7678:	86 59       	subi	r24, 0x96	; 150
    767a:	9d 4f       	sbci	r25, 0xFD	; 253
    767c:	dc 01       	movw	r26, r24
    767e:	8c 91       	ld	r24, X
    7680:	28 0f       	add	r18, r24
    7682:	31 1d       	adc	r19, r1
    7684:	41 1d       	adc	r20, r1
    7686:	51 1d       	adc	r21, r1
    7688:	da 01       	movw	r26, r20
    768a:	c9 01       	movw	r24, r18
    768c:	20 e2       	ldi	r18, 0x20	; 32
    768e:	30 e0       	ldi	r19, 0x00	; 0
    7690:	40 e0       	ldi	r20, 0x00	; 0
    7692:	50 e0       	ldi	r21, 0x00	; 0
    7694:	59 01       	movw	r10, r18
    7696:	6a 01       	movw	r12, r20
    7698:	a8 1a       	sub	r10, r24
    769a:	b9 0a       	sbc	r11, r25
    769c:	ca 0a       	sbc	r12, r26
    769e:	db 0a       	sbc	r13, r27

	  if (bm == 0)
    76a0:	09 f0       	breq	.+2      	; 0x76a4 <__umoddi3+0x114>
    76a2:	2b c2       	rjmp	.+1110   	; 0x7afa <__umoddi3+0x56a>
		 leading quotient digit q1 = 1).

		 This special case is necessary, not an optimization.
		 (Shifts counts of W_TYPE_SIZE are undefined.)  */

	      n1 -= d0;
    76a4:	82 01       	movw	r16, r4
    76a6:	71 01       	movw	r14, r2
    76a8:	e6 18       	sub	r14, r6
    76aa:	f7 08       	sbc	r15, r7
    76ac:	08 09       	sbc	r16, r8
    76ae:	19 09       	sbc	r17, r9
    76b0:	14 01       	movw	r2, r8
    76b2:	44 24       	eor	r4, r4
    76b4:	55 24       	eor	r5, r5
    76b6:	a4 01       	movw	r20, r8
    76b8:	93 01       	movw	r18, r6
    76ba:	40 70       	andi	r20, 0x00	; 0
    76bc:	50 70       	andi	r21, 0x00	; 0
    76be:	23 96       	adiw	r28, 0x03	; 3
    76c0:	2c af       	std	Y+60, r18	; 0x3c
    76c2:	3d af       	std	Y+61, r19	; 0x3d
    76c4:	4e af       	std	Y+62, r20	; 0x3e
    76c6:	5f af       	std	Y+63, r21	; 0x3f
    76c8:	23 97       	sbiw	r28, 0x03	; 3
    76ca:	1e a6       	std	Y+46, r1	; 0x2e
    76cc:	1d a6       	std	Y+45, r1	; 0x2d
	      udiv_qrnnd (q1, n1, n2, n1, d0);
	    }

	  /* n1 != d0...  */

	  udiv_qrnnd (q0, n0, n1, n0, d0);
    76ce:	c8 01       	movw	r24, r16
    76d0:	b7 01       	movw	r22, r14
    76d2:	a2 01       	movw	r20, r4
    76d4:	91 01       	movw	r18, r2
    76d6:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    76da:	23 96       	adiw	r28, 0x03	; 3
    76dc:	6c ad       	ldd	r22, Y+60	; 0x3c
    76de:	7d ad       	ldd	r23, Y+61	; 0x3d
    76e0:	8e ad       	ldd	r24, Y+62	; 0x3e
    76e2:	9f ad       	ldd	r25, Y+63	; 0x3f
    76e4:	23 97       	sbiw	r28, 0x03	; 3
    76e6:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    76ea:	5b 01       	movw	r10, r22
    76ec:	6c 01       	movw	r12, r24
    76ee:	c8 01       	movw	r24, r16
    76f0:	b7 01       	movw	r22, r14
    76f2:	a2 01       	movw	r20, r4
    76f4:	91 01       	movw	r18, r2
    76f6:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    76fa:	cb 01       	movw	r24, r22
    76fc:	77 27       	eor	r23, r23
    76fe:	66 27       	eor	r22, r22
    7700:	e9 a0       	ldd	r14, Y+33	; 0x21
    7702:	fa a0       	ldd	r15, Y+34	; 0x22
    7704:	0b a1       	ldd	r16, Y+35	; 0x23
    7706:	1c a1       	ldd	r17, Y+36	; 0x24
    7708:	98 01       	movw	r18, r16
    770a:	44 27       	eor	r20, r20
    770c:	55 27       	eor	r21, r21
    770e:	7b 01       	movw	r14, r22
    7710:	8c 01       	movw	r16, r24
    7712:	e2 2a       	or	r14, r18
    7714:	f3 2a       	or	r15, r19
    7716:	04 2b       	or	r16, r20
    7718:	15 2b       	or	r17, r21
    771a:	ea 14       	cp	r14, r10
    771c:	fb 04       	cpc	r15, r11
    771e:	0c 05       	cpc	r16, r12
    7720:	1d 05       	cpc	r17, r13
    7722:	78 f4       	brcc	.+30     	; 0x7742 <__umoddi3+0x1b2>
    7724:	e6 0c       	add	r14, r6
    7726:	f7 1c       	adc	r15, r7
    7728:	08 1d       	adc	r16, r8
    772a:	19 1d       	adc	r17, r9
    772c:	e6 14       	cp	r14, r6
    772e:	f7 04       	cpc	r15, r7
    7730:	08 05       	cpc	r16, r8
    7732:	19 05       	cpc	r17, r9
    7734:	30 f0       	brcs	.+12     	; 0x7742 <__umoddi3+0x1b2>
    7736:	ea 14       	cp	r14, r10
    7738:	fb 04       	cpc	r15, r11
    773a:	0c 05       	cpc	r16, r12
    773c:	1d 05       	cpc	r17, r13
    773e:	08 f4       	brcc	.+2      	; 0x7742 <__umoddi3+0x1b2>
    7740:	7b c5       	rjmp	.+2806   	; 0x8238 <__umoddi3+0xca8>
    7742:	ea 18       	sub	r14, r10
    7744:	fb 08       	sbc	r15, r11
    7746:	0c 09       	sbc	r16, r12
    7748:	1d 09       	sbc	r17, r13
    774a:	c8 01       	movw	r24, r16
    774c:	b7 01       	movw	r22, r14
    774e:	a2 01       	movw	r20, r4
    7750:	91 01       	movw	r18, r2
    7752:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7756:	23 96       	adiw	r28, 0x03	; 3
    7758:	6c ad       	ldd	r22, Y+60	; 0x3c
    775a:	7d ad       	ldd	r23, Y+61	; 0x3d
    775c:	8e ad       	ldd	r24, Y+62	; 0x3e
    775e:	9f ad       	ldd	r25, Y+63	; 0x3f
    7760:	23 97       	sbiw	r28, 0x03	; 3
    7762:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7766:	5b 01       	movw	r10, r22
    7768:	6c 01       	movw	r12, r24
    776a:	c8 01       	movw	r24, r16
    776c:	b7 01       	movw	r22, r14
    776e:	a2 01       	movw	r20, r4
    7770:	91 01       	movw	r18, r2
    7772:	16 c1       	rjmp	.+556    	; 0x79a0 <__umoddi3+0x410>
    }
#endif /* UDIV_NEEDS_NORMALIZATION */

  else
    {
      if (d1 > n1)
    7774:	2e 14       	cp	r2, r14
    7776:	3f 04       	cpc	r3, r15
    7778:	40 06       	cpc	r4, r16
    777a:	51 06       	cpc	r5, r17
    777c:	68 f4       	brcc	.+26     	; 0x7798 <__umoddi3+0x208>
	  q1 = 0;

	  /* Remainder in n1n0.  */
	  if (rp != 0)
	    {
	      rr.s.low = n0;
    777e:	09 a1       	ldd	r16, Y+33	; 0x21
    7780:	1a a1       	ldd	r17, Y+34	; 0x22
    7782:	2b a1       	ldd	r18, Y+35	; 0x23
    7784:	3c a1       	ldd	r19, Y+36	; 0x24
    7786:	09 8b       	std	Y+17, r16	; 0x11
    7788:	1a 8b       	std	Y+18, r17	; 0x12
    778a:	2b 8b       	std	Y+19, r18	; 0x13
    778c:	3c 8b       	std	Y+20, r19	; 0x14
	      rr.s.high = n1;
    778e:	2d 8a       	std	Y+21, r2	; 0x15
    7790:	3e 8a       	std	Y+22, r3	; 0x16
    7792:	4f 8a       	std	Y+23, r4	; 0x17
    7794:	58 8e       	std	Y+24, r5	; 0x18
    7796:	73 c5       	rjmp	.+2790   	; 0x827e <__umoddi3+0xcee>
	}
      else
	{
	  /* 0q = NN / dd */

	  count_leading_zeros (bm, d1);
    7798:	20 e0       	ldi	r18, 0x00	; 0
    779a:	e2 16       	cp	r14, r18
    779c:	20 e0       	ldi	r18, 0x00	; 0
    779e:	f2 06       	cpc	r15, r18
    77a0:	21 e0       	ldi	r18, 0x01	; 1
    77a2:	02 07       	cpc	r16, r18
    77a4:	20 e0       	ldi	r18, 0x00	; 0
    77a6:	12 07       	cpc	r17, r18
    77a8:	08 f0       	brcs	.+2      	; 0x77ac <__umoddi3+0x21c>
    77aa:	6f c1       	rjmp	.+734    	; 0x7a8a <__umoddi3+0x4fa>
    77ac:	3f ef       	ldi	r19, 0xFF	; 255
    77ae:	e3 16       	cp	r14, r19
    77b0:	f1 04       	cpc	r15, r1
    77b2:	01 05       	cpc	r16, r1
    77b4:	11 05       	cpc	r17, r1
    77b6:	11 f0       	breq	.+4      	; 0x77bc <__umoddi3+0x22c>
    77b8:	08 f0       	brcs	.+2      	; 0x77bc <__umoddi3+0x22c>
    77ba:	1f c5       	rjmp	.+2622   	; 0x81fa <__umoddi3+0xc6a>
    77bc:	20 e0       	ldi	r18, 0x00	; 0
    77be:	30 e0       	ldi	r19, 0x00	; 0
    77c0:	40 e0       	ldi	r20, 0x00	; 0
    77c2:	50 e0       	ldi	r21, 0x00	; 0
    77c4:	80 e0       	ldi	r24, 0x00	; 0
    77c6:	90 e0       	ldi	r25, 0x00	; 0
    77c8:	57 01       	movw	r10, r14
    77ca:	68 01       	movw	r12, r16
    77cc:	04 c0       	rjmp	.+8      	; 0x77d6 <__umoddi3+0x246>
    77ce:	d6 94       	lsr	r13
    77d0:	c7 94       	ror	r12
    77d2:	b7 94       	ror	r11
    77d4:	a7 94       	ror	r10
    77d6:	8a 95       	dec	r24
    77d8:	d2 f7       	brpl	.-12     	; 0x77ce <__umoddi3+0x23e>
    77da:	d6 01       	movw	r26, r12
    77dc:	c5 01       	movw	r24, r10
    77de:	86 59       	subi	r24, 0x96	; 150
    77e0:	9d 4f       	sbci	r25, 0xFD	; 253
    77e2:	dc 01       	movw	r26, r24
    77e4:	8c 91       	ld	r24, X
    77e6:	28 0f       	add	r18, r24
    77e8:	31 1d       	adc	r19, r1
    77ea:	41 1d       	adc	r20, r1
    77ec:	51 1d       	adc	r21, r1
    77ee:	da 01       	movw	r26, r20
    77f0:	c9 01       	movw	r24, r18
    77f2:	20 e2       	ldi	r18, 0x20	; 32
    77f4:	30 e0       	ldi	r19, 0x00	; 0
    77f6:	40 e0       	ldi	r20, 0x00	; 0
    77f8:	50 e0       	ldi	r21, 0x00	; 0
    77fa:	59 01       	movw	r10, r18
    77fc:	6a 01       	movw	r12, r20
    77fe:	a8 1a       	sub	r10, r24
    7800:	b9 0a       	sbc	r11, r25
    7802:	ca 0a       	sbc	r12, r26
    7804:	db 0a       	sbc	r13, r27
	  if (bm == 0)
    7806:	09 f0       	breq	.+2      	; 0x780a <__umoddi3+0x27a>
    7808:	45 c2       	rjmp	.+1162   	; 0x7c94 <__umoddi3+0x704>

		 This special case is necessary, not an optimization.  */

	      /* The condition on the next line takes advantage of that
		 n1 >= d1 (true due to program flow).  */
	      if (n1 > d1 || n0 >= d0)
    780a:	e2 14       	cp	r14, r2
    780c:	f3 04       	cpc	r15, r3
    780e:	04 05       	cpc	r16, r4
    7810:	15 05       	cpc	r17, r5
    7812:	08 f4       	brcc	.+2      	; 0x7816 <__umoddi3+0x286>
    7814:	4b c1       	rjmp	.+662    	; 0x7aac <__umoddi3+0x51c>
    7816:	29 a1       	ldd	r18, Y+33	; 0x21
    7818:	3a a1       	ldd	r19, Y+34	; 0x22
    781a:	4b a1       	ldd	r20, Y+35	; 0x23
    781c:	5c a1       	ldd	r21, Y+36	; 0x24
    781e:	26 15       	cp	r18, r6
    7820:	37 05       	cpc	r19, r7
    7822:	48 05       	cpc	r20, r8
    7824:	59 05       	cpc	r21, r9
    7826:	08 f0       	brcs	.+2      	; 0x782a <__umoddi3+0x29a>
    7828:	41 c1       	rjmp	.+642    	; 0x7aac <__umoddi3+0x51c>

	      q1 = 0;

	      if (rp != 0)
		{
		  rr.s.low = n0;
    782a:	c9 a0       	ldd	r12, Y+33	; 0x21
    782c:	da a0       	ldd	r13, Y+34	; 0x22
    782e:	eb a0       	ldd	r14, Y+35	; 0x23
    7830:	fc a0       	ldd	r15, Y+36	; 0x24
    7832:	c9 8a       	std	Y+17, r12	; 0x11
    7834:	da 8a       	std	Y+18, r13	; 0x12
    7836:	eb 8a       	std	Y+19, r14	; 0x13
    7838:	fc 8a       	std	Y+20, r15	; 0x14
		  rr.s.high = n1;
    783a:	2d 8a       	std	Y+21, r2	; 0x15
    783c:	3e 8a       	std	Y+22, r3	; 0x16
    783e:	4f 8a       	std	Y+23, r4	; 0x17
    7840:	58 8e       	std	Y+24, r5	; 0x18
    7842:	1d c5       	rjmp	.+2618   	; 0x827e <__umoddi3+0xcee>
    {
      if (d0 > n1)
	{
	  /* 0q = nn / 0D */

	  count_leading_zeros (bm, d0);
    7844:	30 e0       	ldi	r19, 0x00	; 0
    7846:	63 16       	cp	r6, r19
    7848:	30 e0       	ldi	r19, 0x00	; 0
    784a:	73 06       	cpc	r7, r19
    784c:	31 e0       	ldi	r19, 0x01	; 1
    784e:	83 06       	cpc	r8, r19
    7850:	30 e0       	ldi	r19, 0x00	; 0
    7852:	93 06       	cpc	r9, r19
    7854:	08 f0       	brcs	.+2      	; 0x7858 <__umoddi3+0x2c8>
    7856:	08 c1       	rjmp	.+528    	; 0x7a68 <__umoddi3+0x4d8>
    7858:	4f ef       	ldi	r20, 0xFF	; 255
    785a:	64 16       	cp	r6, r20
    785c:	71 04       	cpc	r7, r1
    785e:	81 04       	cpc	r8, r1
    7860:	91 04       	cpc	r9, r1
    7862:	11 f0       	breq	.+4      	; 0x7868 <__umoddi3+0x2d8>
    7864:	08 f0       	brcs	.+2      	; 0x7868 <__umoddi3+0x2d8>
    7866:	85 c4       	rjmp	.+2314   	; 0x8172 <__umoddi3+0xbe2>
    7868:	20 e0       	ldi	r18, 0x00	; 0
    786a:	30 e0       	ldi	r19, 0x00	; 0
    786c:	40 e0       	ldi	r20, 0x00	; 0
    786e:	50 e0       	ldi	r21, 0x00	; 0
    7870:	80 e0       	ldi	r24, 0x00	; 0
    7872:	90 e0       	ldi	r25, 0x00	; 0
    7874:	64 01       	movw	r12, r8
    7876:	53 01       	movw	r10, r6
    7878:	04 c0       	rjmp	.+8      	; 0x7882 <__umoddi3+0x2f2>
    787a:	d6 94       	lsr	r13
    787c:	c7 94       	ror	r12
    787e:	b7 94       	ror	r11
    7880:	a7 94       	ror	r10
    7882:	8a 95       	dec	r24
    7884:	d2 f7       	brpl	.-12     	; 0x787a <__umoddi3+0x2ea>
    7886:	d6 01       	movw	r26, r12
    7888:	c5 01       	movw	r24, r10
    788a:	86 59       	subi	r24, 0x96	; 150
    788c:	9d 4f       	sbci	r25, 0xFD	; 253
    788e:	dc 01       	movw	r26, r24
    7890:	8c 91       	ld	r24, X
    7892:	28 0f       	add	r18, r24
    7894:	31 1d       	adc	r19, r1
    7896:	41 1d       	adc	r20, r1
    7898:	51 1d       	adc	r21, r1
    789a:	80 e2       	ldi	r24, 0x20	; 32
    789c:	90 e0       	ldi	r25, 0x00	; 0
    789e:	a0 e0       	ldi	r26, 0x00	; 0
    78a0:	b0 e0       	ldi	r27, 0x00	; 0
    78a2:	5c 01       	movw	r10, r24
    78a4:	6d 01       	movw	r12, r26
    78a6:	a2 1a       	sub	r10, r18
    78a8:	b3 0a       	sbc	r11, r19
    78aa:	c4 0a       	sbc	r12, r20
    78ac:	d5 0a       	sbc	r13, r21

	  if (bm != 0)
    78ae:	09 f0       	breq	.+2      	; 0x78b2 <__umoddi3+0x322>
    78b0:	81 c3       	rjmp	.+1794   	; 0x7fb4 <__umoddi3+0xa24>
    78b2:	1e a6       	std	Y+46, r1	; 0x2e
    78b4:	1d a6       	std	Y+45, r1	; 0x2d
	      d0 = d0 << bm;
	      n1 = (n1 << bm) | (n0 >> (W_TYPE_SIZE - bm));
	      n0 = n0 << bm;
	    }

	  udiv_qrnnd (q0, n0, n1, n0, d0);
    78b6:	74 01       	movw	r14, r8
    78b8:	00 27       	eor	r16, r16
    78ba:	11 27       	eor	r17, r17
    78bc:	2b 96       	adiw	r28, 0x0b	; 11
    78be:	ec ae       	std	Y+60, r14	; 0x3c
    78c0:	fd ae       	std	Y+61, r15	; 0x3d
    78c2:	0e af       	std	Y+62, r16	; 0x3e
    78c4:	1f af       	std	Y+63, r17	; 0x3f
    78c6:	2b 97       	sbiw	r28, 0x0b	; 11
    78c8:	94 01       	movw	r18, r8
    78ca:	83 01       	movw	r16, r6
    78cc:	20 70       	andi	r18, 0x00	; 0
    78ce:	30 70       	andi	r19, 0x00	; 0
    78d0:	27 96       	adiw	r28, 0x07	; 7
    78d2:	0c af       	std	Y+60, r16	; 0x3c
    78d4:	1d af       	std	Y+61, r17	; 0x3d
    78d6:	2e af       	std	Y+62, r18	; 0x3e
    78d8:	3f af       	std	Y+63, r19	; 0x3f
    78da:	27 97       	sbiw	r28, 0x07	; 7
    78dc:	c2 01       	movw	r24, r4
    78de:	b1 01       	movw	r22, r2
    78e0:	2b 96       	adiw	r28, 0x0b	; 11
    78e2:	2c ad       	ldd	r18, Y+60	; 0x3c
    78e4:	3d ad       	ldd	r19, Y+61	; 0x3d
    78e6:	4e ad       	ldd	r20, Y+62	; 0x3e
    78e8:	5f ad       	ldd	r21, Y+63	; 0x3f
    78ea:	2b 97       	sbiw	r28, 0x0b	; 11
    78ec:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    78f0:	27 96       	adiw	r28, 0x07	; 7
    78f2:	6c ad       	ldd	r22, Y+60	; 0x3c
    78f4:	7d ad       	ldd	r23, Y+61	; 0x3d
    78f6:	8e ad       	ldd	r24, Y+62	; 0x3e
    78f8:	9f ad       	ldd	r25, Y+63	; 0x3f
    78fa:	27 97       	sbiw	r28, 0x07	; 7
    78fc:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7900:	5b 01       	movw	r10, r22
    7902:	6c 01       	movw	r12, r24
    7904:	c2 01       	movw	r24, r4
    7906:	b1 01       	movw	r22, r2
    7908:	2b 96       	adiw	r28, 0x0b	; 11
    790a:	2c ad       	ldd	r18, Y+60	; 0x3c
    790c:	3d ad       	ldd	r19, Y+61	; 0x3d
    790e:	4e ad       	ldd	r20, Y+62	; 0x3e
    7910:	5f ad       	ldd	r21, Y+63	; 0x3f
    7912:	2b 97       	sbiw	r28, 0x0b	; 11
    7914:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7918:	cb 01       	movw	r24, r22
    791a:	77 27       	eor	r23, r23
    791c:	66 27       	eor	r22, r22
    791e:	e9 a0       	ldd	r14, Y+33	; 0x21
    7920:	fa a0       	ldd	r15, Y+34	; 0x22
    7922:	0b a1       	ldd	r16, Y+35	; 0x23
    7924:	1c a1       	ldd	r17, Y+36	; 0x24
    7926:	98 01       	movw	r18, r16
    7928:	44 27       	eor	r20, r20
    792a:	55 27       	eor	r21, r21
    792c:	7b 01       	movw	r14, r22
    792e:	8c 01       	movw	r16, r24
    7930:	e2 2a       	or	r14, r18
    7932:	f3 2a       	or	r15, r19
    7934:	04 2b       	or	r16, r20
    7936:	15 2b       	or	r17, r21
    7938:	ea 14       	cp	r14, r10
    793a:	fb 04       	cpc	r15, r11
    793c:	0c 05       	cpc	r16, r12
    793e:	1d 05       	cpc	r17, r13
    7940:	78 f4       	brcc	.+30     	; 0x7960 <__umoddi3+0x3d0>
    7942:	e6 0c       	add	r14, r6
    7944:	f7 1c       	adc	r15, r7
    7946:	08 1d       	adc	r16, r8
    7948:	19 1d       	adc	r17, r9
    794a:	e6 14       	cp	r14, r6
    794c:	f7 04       	cpc	r15, r7
    794e:	08 05       	cpc	r16, r8
    7950:	19 05       	cpc	r17, r9
    7952:	30 f0       	brcs	.+12     	; 0x7960 <__umoddi3+0x3d0>
    7954:	ea 14       	cp	r14, r10
    7956:	fb 04       	cpc	r15, r11
    7958:	0c 05       	cpc	r16, r12
    795a:	1d 05       	cpc	r17, r13
    795c:	08 f4       	brcc	.+2      	; 0x7960 <__umoddi3+0x3d0>
    795e:	71 c4       	rjmp	.+2274   	; 0x8242 <__umoddi3+0xcb2>
    7960:	ea 18       	sub	r14, r10
    7962:	fb 08       	sbc	r15, r11
    7964:	0c 09       	sbc	r16, r12
    7966:	1d 09       	sbc	r17, r13
    7968:	c8 01       	movw	r24, r16
    796a:	b7 01       	movw	r22, r14
    796c:	2b 96       	adiw	r28, 0x0b	; 11
    796e:	2c ad       	ldd	r18, Y+60	; 0x3c
    7970:	3d ad       	ldd	r19, Y+61	; 0x3d
    7972:	4e ad       	ldd	r20, Y+62	; 0x3e
    7974:	5f ad       	ldd	r21, Y+63	; 0x3f
    7976:	2b 97       	sbiw	r28, 0x0b	; 11
    7978:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    797c:	27 96       	adiw	r28, 0x07	; 7
    797e:	6c ad       	ldd	r22, Y+60	; 0x3c
    7980:	7d ad       	ldd	r23, Y+61	; 0x3d
    7982:	8e ad       	ldd	r24, Y+62	; 0x3e
    7984:	9f ad       	ldd	r25, Y+63	; 0x3f
    7986:	27 97       	sbiw	r28, 0x07	; 7
    7988:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    798c:	5b 01       	movw	r10, r22
    798e:	6c 01       	movw	r12, r24
    7990:	c8 01       	movw	r24, r16
    7992:	b7 01       	movw	r22, r14
    7994:	2b 96       	adiw	r28, 0x0b	; 11
    7996:	2c ad       	ldd	r18, Y+60	; 0x3c
    7998:	3d ad       	ldd	r19, Y+61	; 0x3d
    799a:	4e ad       	ldd	r20, Y+62	; 0x3e
    799c:	5f ad       	ldd	r21, Y+63	; 0x3f
    799e:	2b 97       	sbiw	r28, 0x0b	; 11
	      udiv_qrnnd (q1, n1, n2, n1, d0);
	    }

	  /* n1 != d0...  */

	  udiv_qrnnd (q0, n0, n1, n0, d0);
    79a0:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    79a4:	cb 01       	movw	r24, r22
    79a6:	77 27       	eor	r23, r23
    79a8:	66 27       	eor	r22, r22
    79aa:	29 a1       	ldd	r18, Y+33	; 0x21
    79ac:	3a a1       	ldd	r19, Y+34	; 0x22
    79ae:	4b a1       	ldd	r20, Y+35	; 0x23
    79b0:	5c a1       	ldd	r21, Y+36	; 0x24
    79b2:	40 70       	andi	r20, 0x00	; 0
    79b4:	50 70       	andi	r21, 0x00	; 0
    79b6:	26 2b       	or	r18, r22
    79b8:	37 2b       	or	r19, r23
    79ba:	48 2b       	or	r20, r24
    79bc:	59 2b       	or	r21, r25
    79be:	2a 15       	cp	r18, r10
    79c0:	3b 05       	cpc	r19, r11
    79c2:	4c 05       	cpc	r20, r12
    79c4:	5d 05       	cpc	r21, r13
    79c6:	90 f4       	brcc	.+36     	; 0x79ec <__umoddi3+0x45c>
    79c8:	26 0d       	add	r18, r6
    79ca:	37 1d       	adc	r19, r7
    79cc:	48 1d       	adc	r20, r8
    79ce:	59 1d       	adc	r21, r9
    79d0:	26 15       	cp	r18, r6
    79d2:	37 05       	cpc	r19, r7
    79d4:	48 05       	cpc	r20, r8
    79d6:	59 05       	cpc	r21, r9
    79d8:	48 f0       	brcs	.+18     	; 0x79ec <__umoddi3+0x45c>
    79da:	2a 15       	cp	r18, r10
    79dc:	3b 05       	cpc	r19, r11
    79de:	4c 05       	cpc	r20, r12
    79e0:	5d 05       	cpc	r21, r13
    79e2:	20 f4       	brcc	.+8      	; 0x79ec <__umoddi3+0x45c>
    79e4:	26 0d       	add	r18, r6
    79e6:	37 1d       	adc	r19, r7
    79e8:	48 1d       	adc	r20, r8
    79ea:	59 1d       	adc	r21, r9
    79ec:	2a 19       	sub	r18, r10
    79ee:	3b 09       	sbc	r19, r11
    79f0:	4c 09       	sbc	r20, r12
    79f2:	5d 09       	sbc	r21, r13
	  /* Remainder in n0 >> bm.  */
	}

      if (rp != 0)
	{
	  rr.s.low = n0 >> bm;
    79f4:	da 01       	movw	r26, r20
    79f6:	c9 01       	movw	r24, r18
    79f8:	0d a4       	ldd	r0, Y+45	; 0x2d
    79fa:	04 c0       	rjmp	.+8      	; 0x7a04 <__umoddi3+0x474>
    79fc:	b6 95       	lsr	r27
    79fe:	a7 95       	ror	r26
    7a00:	97 95       	ror	r25
    7a02:	87 95       	ror	r24
    7a04:	0a 94       	dec	r0
    7a06:	d2 f7       	brpl	.-12     	; 0x79fc <__umoddi3+0x46c>
    7a08:	89 8b       	std	Y+17, r24	; 0x11
    7a0a:	9a 8b       	std	Y+18, r25	; 0x12
    7a0c:	ab 8b       	std	Y+19, r26	; 0x13
    7a0e:	bc 8b       	std	Y+20, r27	; 0x14
	  rr.s.high = 0;
    7a10:	1d 8a       	std	Y+21, r1	; 0x15
    7a12:	1e 8a       	std	Y+22, r1	; 0x16
    7a14:	1f 8a       	std	Y+23, r1	; 0x17
    7a16:	18 8e       	std	Y+24, r1	; 0x18
    7a18:	32 c4       	rjmp	.+2148   	; 0x827e <__umoddi3+0xcee>
	  /* qq = NN / 0d */

	  if (d0 == 0)
	    d0 = 1 / d0;	/* Divide intentionally by zero.  */

	  count_leading_zeros (bm, d0);
    7a1a:	20 e0       	ldi	r18, 0x00	; 0
    7a1c:	62 16       	cp	r6, r18
    7a1e:	20 e0       	ldi	r18, 0x00	; 0
    7a20:	72 06       	cpc	r7, r18
    7a22:	20 e0       	ldi	r18, 0x00	; 0
    7a24:	82 06       	cpc	r8, r18
    7a26:	21 e0       	ldi	r18, 0x01	; 1
    7a28:	92 06       	cpc	r9, r18
    7a2a:	08 f0       	brcs	.+2      	; 0x7a2e <__umoddi3+0x49e>
    7a2c:	ed c3       	rjmp	.+2010   	; 0x8208 <__umoddi3+0xc78>
    7a2e:	20 e1       	ldi	r18, 0x10	; 16
    7a30:	30 e0       	ldi	r19, 0x00	; 0
    7a32:	40 e0       	ldi	r20, 0x00	; 0
    7a34:	50 e0       	ldi	r21, 0x00	; 0
    7a36:	80 e1       	ldi	r24, 0x10	; 16
    7a38:	90 e0       	ldi	r25, 0x00	; 0
    7a3a:	13 ce       	rjmp	.-986    	; 0x7662 <__umoddi3+0xd2>
      else
	{
	  /* qq = NN / 0d */

	  if (d0 == 0)
	    d0 = 1 / d0;	/* Divide intentionally by zero.  */
    7a3c:	61 e0       	ldi	r22, 0x01	; 1
    7a3e:	70 e0       	ldi	r23, 0x00	; 0
    7a40:	80 e0       	ldi	r24, 0x00	; 0
    7a42:	90 e0       	ldi	r25, 0x00	; 0
    7a44:	20 e0       	ldi	r18, 0x00	; 0
    7a46:	30 e0       	ldi	r19, 0x00	; 0
    7a48:	40 e0       	ldi	r20, 0x00	; 0
    7a4a:	50 e0       	ldi	r21, 0x00	; 0
    7a4c:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7a50:	39 01       	movw	r6, r18
    7a52:	4a 01       	movw	r8, r20

	  count_leading_zeros (bm, d0);
    7a54:	00 e0       	ldi	r16, 0x00	; 0
    7a56:	60 16       	cp	r6, r16
    7a58:	00 e0       	ldi	r16, 0x00	; 0
    7a5a:	70 06       	cpc	r7, r16
    7a5c:	01 e0       	ldi	r16, 0x01	; 1
    7a5e:	80 06       	cpc	r8, r16
    7a60:	00 e0       	ldi	r16, 0x00	; 0
    7a62:	90 06       	cpc	r9, r16
    7a64:	d0 f6       	brcc	.-76     	; 0x7a1a <__umoddi3+0x48a>
    7a66:	ef cd       	rjmp	.-1058   	; 0x7646 <__umoddi3+0xb6>
    {
      if (d0 > n1)
	{
	  /* 0q = nn / 0D */

	  count_leading_zeros (bm, d0);
    7a68:	50 e0       	ldi	r21, 0x00	; 0
    7a6a:	65 16       	cp	r6, r21
    7a6c:	50 e0       	ldi	r21, 0x00	; 0
    7a6e:	75 06       	cpc	r7, r21
    7a70:	50 e0       	ldi	r21, 0x00	; 0
    7a72:	85 06       	cpc	r8, r21
    7a74:	51 e0       	ldi	r21, 0x01	; 1
    7a76:	95 06       	cpc	r9, r21
    7a78:	08 f0       	brcs	.+2      	; 0x7a7c <__umoddi3+0x4ec>
    7a7a:	82 c3       	rjmp	.+1796   	; 0x8180 <__umoddi3+0xbf0>
    7a7c:	20 e1       	ldi	r18, 0x10	; 16
    7a7e:	30 e0       	ldi	r19, 0x00	; 0
    7a80:	40 e0       	ldi	r20, 0x00	; 0
    7a82:	50 e0       	ldi	r21, 0x00	; 0
    7a84:	80 e1       	ldi	r24, 0x10	; 16
    7a86:	90 e0       	ldi	r25, 0x00	; 0
    7a88:	f5 ce       	rjmp	.-534    	; 0x7874 <__umoddi3+0x2e4>
	}
      else
	{
	  /* 0q = NN / dd */

	  count_leading_zeros (bm, d1);
    7a8a:	40 e0       	ldi	r20, 0x00	; 0
    7a8c:	e4 16       	cp	r14, r20
    7a8e:	40 e0       	ldi	r20, 0x00	; 0
    7a90:	f4 06       	cpc	r15, r20
    7a92:	40 e0       	ldi	r20, 0x00	; 0
    7a94:	04 07       	cpc	r16, r20
    7a96:	41 e0       	ldi	r20, 0x01	; 1
    7a98:	14 07       	cpc	r17, r20
    7a9a:	08 f0       	brcs	.+2      	; 0x7a9e <__umoddi3+0x50e>
    7a9c:	78 c3       	rjmp	.+1776   	; 0x818e <__umoddi3+0xbfe>
    7a9e:	20 e1       	ldi	r18, 0x10	; 16
    7aa0:	30 e0       	ldi	r19, 0x00	; 0
    7aa2:	40 e0       	ldi	r20, 0x00	; 0
    7aa4:	50 e0       	ldi	r21, 0x00	; 0
    7aa6:	80 e1       	ldi	r24, 0x10	; 16
    7aa8:	90 e0       	ldi	r25, 0x00	; 0
    7aaa:	8e ce       	rjmp	.-740    	; 0x77c8 <__umoddi3+0x238>
	      /* The condition on the next line takes advantage of that
		 n1 >= d1 (true due to program flow).  */
	      if (n1 > d1 || n0 >= d0)
		{
		  q0 = 1;
		  sub_ddmmss (n1, n0, n1, n0, d1, d0);
    7aac:	29 a1       	ldd	r18, Y+33	; 0x21
    7aae:	3a a1       	ldd	r19, Y+34	; 0x22
    7ab0:	4b a1       	ldd	r20, Y+35	; 0x23
    7ab2:	5c a1       	ldd	r21, Y+36	; 0x24
    7ab4:	26 19       	sub	r18, r6
    7ab6:	37 09       	sbc	r19, r7
    7ab8:	48 09       	sbc	r20, r8
    7aba:	59 09       	sbc	r21, r9
    7abc:	2e 18       	sub	r2, r14
    7abe:	3f 08       	sbc	r3, r15
    7ac0:	40 0a       	sbc	r4, r16
    7ac2:	51 0a       	sbc	r5, r17
    7ac4:	82 01       	movw	r16, r4
    7ac6:	71 01       	movw	r14, r2
    7ac8:	60 e0       	ldi	r22, 0x00	; 0
    7aca:	70 e0       	ldi	r23, 0x00	; 0
    7acc:	80 e0       	ldi	r24, 0x00	; 0
    7ace:	90 e0       	ldi	r25, 0x00	; 0
    7ad0:	a9 a0       	ldd	r10, Y+33	; 0x21
    7ad2:	ba a0       	ldd	r11, Y+34	; 0x22
    7ad4:	cb a0       	ldd	r12, Y+35	; 0x23
    7ad6:	dc a0       	ldd	r13, Y+36	; 0x24
    7ad8:	a2 16       	cp	r10, r18
    7ada:	b3 06       	cpc	r11, r19
    7adc:	c4 06       	cpc	r12, r20
    7ade:	d5 06       	cpc	r13, r21
    7ae0:	08 f4       	brcc	.+2      	; 0x7ae4 <__umoddi3+0x554>
    7ae2:	a0 c3       	rjmp	.+1856   	; 0x8224 <__umoddi3+0xc94>
    7ae4:	17 01       	movw	r2, r14
    7ae6:	28 01       	movw	r4, r16
    7ae8:	26 1a       	sub	r2, r22
    7aea:	37 0a       	sbc	r3, r23
    7aec:	48 0a       	sbc	r4, r24
    7aee:	59 0a       	sbc	r5, r25
    7af0:	29 a3       	std	Y+33, r18	; 0x21
    7af2:	3a a3       	std	Y+34, r19	; 0x22
    7af4:	4b a3       	std	Y+35, r20	; 0x23
    7af6:	5c a3       	std	Y+36, r21	; 0x24
    7af8:	98 ce       	rjmp	.-720    	; 0x782a <__umoddi3+0x29a>
	    {
	      /* Normalize.  */

	      b = W_TYPE_SIZE - bm;

	      d0 = d0 << bm;
    7afa:	be a6       	std	Y+46, r11	; 0x2e
    7afc:	ad a6       	std	Y+45, r10	; 0x2d
    7afe:	0d a4       	ldd	r0, Y+45	; 0x2d
    7b00:	04 c0       	rjmp	.+8      	; 0x7b0a <__umoddi3+0x57a>
    7b02:	66 0c       	add	r6, r6
    7b04:	77 1c       	adc	r7, r7
    7b06:	88 1c       	adc	r8, r8
    7b08:	99 1c       	adc	r9, r9
    7b0a:	0a 94       	dec	r0
    7b0c:	d2 f7       	brpl	.-12     	; 0x7b02 <__umoddi3+0x572>
	      n2 = n1 >> b;
    7b0e:	2a 19       	sub	r18, r10
    7b10:	3b 09       	sbc	r19, r11
    7b12:	4c 09       	sbc	r20, r12
    7b14:	5d 09       	sbc	r21, r13
    7b16:	da 01       	movw	r26, r20
    7b18:	c9 01       	movw	r24, r18
    7b1a:	82 01       	movw	r16, r4
    7b1c:	71 01       	movw	r14, r2
    7b1e:	04 c0       	rjmp	.+8      	; 0x7b28 <__umoddi3+0x598>
    7b20:	16 95       	lsr	r17
    7b22:	07 95       	ror	r16
    7b24:	f7 94       	ror	r15
    7b26:	e7 94       	ror	r14
    7b28:	2a 95       	dec	r18
    7b2a:	d2 f7       	brpl	.-12     	; 0x7b20 <__umoddi3+0x590>
	      n1 = (n1 << bm) | (n0 >> b);
    7b2c:	a2 01       	movw	r20, r4
    7b2e:	91 01       	movw	r18, r2
    7b30:	0d a4       	ldd	r0, Y+45	; 0x2d
    7b32:	04 c0       	rjmp	.+8      	; 0x7b3c <__umoddi3+0x5ac>
    7b34:	22 0f       	add	r18, r18
    7b36:	33 1f       	adc	r19, r19
    7b38:	44 1f       	adc	r20, r20
    7b3a:	55 1f       	adc	r21, r21
    7b3c:	0a 94       	dec	r0
    7b3e:	d2 f7       	brpl	.-12     	; 0x7b34 <__umoddi3+0x5a4>
    7b40:	a9 a0       	ldd	r10, Y+33	; 0x21
    7b42:	ba a0       	ldd	r11, Y+34	; 0x22
    7b44:	cb a0       	ldd	r12, Y+35	; 0x23
    7b46:	dc a0       	ldd	r13, Y+36	; 0x24
    7b48:	04 c0       	rjmp	.+8      	; 0x7b52 <__umoddi3+0x5c2>
    7b4a:	d6 94       	lsr	r13
    7b4c:	c7 94       	ror	r12
    7b4e:	b7 94       	ror	r11
    7b50:	a7 94       	ror	r10
    7b52:	8a 95       	dec	r24
    7b54:	d2 f7       	brpl	.-12     	; 0x7b4a <__umoddi3+0x5ba>
    7b56:	2a 29       	or	r18, r10
    7b58:	3b 29       	or	r19, r11
    7b5a:	4c 29       	or	r20, r12
    7b5c:	5d 29       	or	r21, r13
    7b5e:	2d a3       	std	Y+37, r18	; 0x25
    7b60:	3e a3       	std	Y+38, r19	; 0x26
    7b62:	4f a3       	std	Y+39, r20	; 0x27
    7b64:	58 a7       	std	Y+40, r21	; 0x28
	      n0 = n0 << bm;
    7b66:	29 a1       	ldd	r18, Y+33	; 0x21
    7b68:	3a a1       	ldd	r19, Y+34	; 0x22
    7b6a:	4b a1       	ldd	r20, Y+35	; 0x23
    7b6c:	5c a1       	ldd	r21, Y+36	; 0x24
    7b6e:	0d a4       	ldd	r0, Y+45	; 0x2d
    7b70:	04 c0       	rjmp	.+8      	; 0x7b7a <__umoddi3+0x5ea>
    7b72:	22 0f       	add	r18, r18
    7b74:	33 1f       	adc	r19, r19
    7b76:	44 1f       	adc	r20, r20
    7b78:	55 1f       	adc	r21, r21
    7b7a:	0a 94       	dec	r0
    7b7c:	d2 f7       	brpl	.-12     	; 0x7b72 <__umoddi3+0x5e2>
    7b7e:	29 a3       	std	Y+33, r18	; 0x21
    7b80:	3a a3       	std	Y+34, r19	; 0x22
    7b82:	4b a3       	std	Y+35, r20	; 0x23
    7b84:	5c a3       	std	Y+36, r21	; 0x24

	      udiv_qrnnd (q1, n1, n2, n1, d0);
    7b86:	14 01       	movw	r2, r8
    7b88:	44 24       	eor	r4, r4
    7b8a:	55 24       	eor	r5, r5
    7b8c:	d4 01       	movw	r26, r8
    7b8e:	c3 01       	movw	r24, r6
    7b90:	a0 70       	andi	r26, 0x00	; 0
    7b92:	b0 70       	andi	r27, 0x00	; 0
    7b94:	23 96       	adiw	r28, 0x03	; 3
    7b96:	8c af       	std	Y+60, r24	; 0x3c
    7b98:	9d af       	std	Y+61, r25	; 0x3d
    7b9a:	ae af       	std	Y+62, r26	; 0x3e
    7b9c:	bf af       	std	Y+63, r27	; 0x3f
    7b9e:	23 97       	sbiw	r28, 0x03	; 3
    7ba0:	c8 01       	movw	r24, r16
    7ba2:	b7 01       	movw	r22, r14
    7ba4:	a2 01       	movw	r20, r4
    7ba6:	91 01       	movw	r18, r2
    7ba8:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7bac:	23 96       	adiw	r28, 0x03	; 3
    7bae:	6c ad       	ldd	r22, Y+60	; 0x3c
    7bb0:	7d ad       	ldd	r23, Y+61	; 0x3d
    7bb2:	8e ad       	ldd	r24, Y+62	; 0x3e
    7bb4:	9f ad       	ldd	r25, Y+63	; 0x3f
    7bb6:	23 97       	sbiw	r28, 0x03	; 3
    7bb8:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7bbc:	5b 01       	movw	r10, r22
    7bbe:	6c 01       	movw	r12, r24
    7bc0:	c8 01       	movw	r24, r16
    7bc2:	b7 01       	movw	r22, r14
    7bc4:	a2 01       	movw	r20, r4
    7bc6:	91 01       	movw	r18, r2
    7bc8:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7bcc:	cb 01       	movw	r24, r22
    7bce:	77 27       	eor	r23, r23
    7bd0:	66 27       	eor	r22, r22
    7bd2:	ed a0       	ldd	r14, Y+37	; 0x25
    7bd4:	fe a0       	ldd	r15, Y+38	; 0x26
    7bd6:	0f a1       	ldd	r16, Y+39	; 0x27
    7bd8:	18 a5       	ldd	r17, Y+40	; 0x28
    7bda:	98 01       	movw	r18, r16
    7bdc:	44 27       	eor	r20, r20
    7bde:	55 27       	eor	r21, r21
    7be0:	7b 01       	movw	r14, r22
    7be2:	8c 01       	movw	r16, r24
    7be4:	e2 2a       	or	r14, r18
    7be6:	f3 2a       	or	r15, r19
    7be8:	04 2b       	or	r16, r20
    7bea:	15 2b       	or	r17, r21
    7bec:	ea 14       	cp	r14, r10
    7bee:	fb 04       	cpc	r15, r11
    7bf0:	0c 05       	cpc	r16, r12
    7bf2:	1d 05       	cpc	r17, r13
    7bf4:	50 f4       	brcc	.+20     	; 0x7c0a <__umoddi3+0x67a>
    7bf6:	e6 0c       	add	r14, r6
    7bf8:	f7 1c       	adc	r15, r7
    7bfa:	08 1d       	adc	r16, r8
    7bfc:	19 1d       	adc	r17, r9
    7bfe:	e6 14       	cp	r14, r6
    7c00:	f7 04       	cpc	r15, r7
    7c02:	08 05       	cpc	r16, r8
    7c04:	19 05       	cpc	r17, r9
    7c06:	08 f0       	brcs	.+2      	; 0x7c0a <__umoddi3+0x67a>
    7c08:	21 c3       	rjmp	.+1602   	; 0x824c <__umoddi3+0xcbc>
    7c0a:	ea 18       	sub	r14, r10
    7c0c:	fb 08       	sbc	r15, r11
    7c0e:	0c 09       	sbc	r16, r12
    7c10:	1d 09       	sbc	r17, r13
    7c12:	c8 01       	movw	r24, r16
    7c14:	b7 01       	movw	r22, r14
    7c16:	a2 01       	movw	r20, r4
    7c18:	91 01       	movw	r18, r2
    7c1a:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7c1e:	23 96       	adiw	r28, 0x03	; 3
    7c20:	6c ad       	ldd	r22, Y+60	; 0x3c
    7c22:	7d ad       	ldd	r23, Y+61	; 0x3d
    7c24:	8e ad       	ldd	r24, Y+62	; 0x3e
    7c26:	9f ad       	ldd	r25, Y+63	; 0x3f
    7c28:	23 97       	sbiw	r28, 0x03	; 3
    7c2a:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7c2e:	5b 01       	movw	r10, r22
    7c30:	6c 01       	movw	r12, r24
    7c32:	c8 01       	movw	r24, r16
    7c34:	b7 01       	movw	r22, r14
    7c36:	a2 01       	movw	r20, r4
    7c38:	91 01       	movw	r18, r2
    7c3a:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7c3e:	cb 01       	movw	r24, r22
    7c40:	77 27       	eor	r23, r23
    7c42:	66 27       	eor	r22, r22
    7c44:	2d a1       	ldd	r18, Y+37	; 0x25
    7c46:	3e a1       	ldd	r19, Y+38	; 0x26
    7c48:	4f a1       	ldd	r20, Y+39	; 0x27
    7c4a:	58 a5       	ldd	r21, Y+40	; 0x28
    7c4c:	40 70       	andi	r20, 0x00	; 0
    7c4e:	50 70       	andi	r21, 0x00	; 0
    7c50:	7b 01       	movw	r14, r22
    7c52:	8c 01       	movw	r16, r24
    7c54:	e2 2a       	or	r14, r18
    7c56:	f3 2a       	or	r15, r19
    7c58:	04 2b       	or	r16, r20
    7c5a:	15 2b       	or	r17, r21
    7c5c:	ea 14       	cp	r14, r10
    7c5e:	fb 04       	cpc	r15, r11
    7c60:	0c 05       	cpc	r16, r12
    7c62:	1d 05       	cpc	r17, r13
    7c64:	90 f4       	brcc	.+36     	; 0x7c8a <__umoddi3+0x6fa>
    7c66:	e6 0c       	add	r14, r6
    7c68:	f7 1c       	adc	r15, r7
    7c6a:	08 1d       	adc	r16, r8
    7c6c:	19 1d       	adc	r17, r9
    7c6e:	e6 14       	cp	r14, r6
    7c70:	f7 04       	cpc	r15, r7
    7c72:	08 05       	cpc	r16, r8
    7c74:	19 05       	cpc	r17, r9
    7c76:	48 f0       	brcs	.+18     	; 0x7c8a <__umoddi3+0x6fa>
    7c78:	ea 14       	cp	r14, r10
    7c7a:	fb 04       	cpc	r15, r11
    7c7c:	0c 05       	cpc	r16, r12
    7c7e:	1d 05       	cpc	r17, r13
    7c80:	20 f4       	brcc	.+8      	; 0x7c8a <__umoddi3+0x6fa>
    7c82:	e6 0c       	add	r14, r6
    7c84:	f7 1c       	adc	r15, r7
    7c86:	08 1d       	adc	r16, r8
    7c88:	19 1d       	adc	r17, r9
    7c8a:	ea 18       	sub	r14, r10
    7c8c:	fb 08       	sbc	r15, r11
    7c8e:	0c 09       	sbc	r16, r12
    7c90:	1d 09       	sbc	r17, r13
    7c92:	1d cd       	rjmp	.-1478   	; 0x76ce <__umoddi3+0x13e>
	      UWtype m1, m0;
	      /* Normalize.  */

	      b = W_TYPE_SIZE - bm;

	      d1 = (d1 << bm) | (d0 >> b);
    7c94:	63 96       	adiw	r28, 0x13	; 19
    7c96:	bf ae       	std	Y+63, r11	; 0x3f
    7c98:	ae ae       	std	Y+62, r10	; 0x3e
    7c9a:	63 97       	sbiw	r28, 0x13	; 19
    7c9c:	2a 19       	sub	r18, r10
    7c9e:	3b 09       	sbc	r19, r11
    7ca0:	4c 09       	sbc	r20, r12
    7ca2:	5d 09       	sbc	r21, r13
    7ca4:	61 96       	adiw	r28, 0x11	; 17
    7ca6:	3f af       	std	Y+63, r19	; 0x3f
    7ca8:	2e af       	std	Y+62, r18	; 0x3e
    7caa:	61 97       	sbiw	r28, 0x11	; 17
    7cac:	a8 01       	movw	r20, r16
    7cae:	97 01       	movw	r18, r14
    7cb0:	62 96       	adiw	r28, 0x12	; 18
    7cb2:	0f ac       	ldd	r0, Y+63	; 0x3f
    7cb4:	62 97       	sbiw	r28, 0x12	; 18
    7cb6:	04 c0       	rjmp	.+8      	; 0x7cc0 <__umoddi3+0x730>
    7cb8:	22 0f       	add	r18, r18
    7cba:	33 1f       	adc	r19, r19
    7cbc:	44 1f       	adc	r20, r20
    7cbe:	55 1f       	adc	r21, r21
    7cc0:	0a 94       	dec	r0
    7cc2:	d2 f7       	brpl	.-12     	; 0x7cb8 <__umoddi3+0x728>
    7cc4:	d4 01       	movw	r26, r8
    7cc6:	c3 01       	movw	r24, r6
    7cc8:	60 96       	adiw	r28, 0x10	; 16
    7cca:	0f ac       	ldd	r0, Y+63	; 0x3f
    7ccc:	60 97       	sbiw	r28, 0x10	; 16
    7cce:	04 c0       	rjmp	.+8      	; 0x7cd8 <__umoddi3+0x748>
    7cd0:	b6 95       	lsr	r27
    7cd2:	a7 95       	ror	r26
    7cd4:	97 95       	ror	r25
    7cd6:	87 95       	ror	r24
    7cd8:	0a 94       	dec	r0
    7cda:	d2 f7       	brpl	.-12     	; 0x7cd0 <__umoddi3+0x740>
    7cdc:	28 2b       	or	r18, r24
    7cde:	39 2b       	or	r19, r25
    7ce0:	4a 2b       	or	r20, r26
    7ce2:	5b 2b       	or	r21, r27
    7ce4:	2f 96       	adiw	r28, 0x0f	; 15
    7ce6:	2c af       	std	Y+60, r18	; 0x3c
    7ce8:	3d af       	std	Y+61, r19	; 0x3d
    7cea:	4e af       	std	Y+62, r20	; 0x3e
    7cec:	5f af       	std	Y+63, r21	; 0x3f
    7cee:	2f 97       	sbiw	r28, 0x0f	; 15
	      d0 = d0 << bm;
    7cf0:	62 96       	adiw	r28, 0x12	; 18
    7cf2:	0f ac       	ldd	r0, Y+63	; 0x3f
    7cf4:	62 97       	sbiw	r28, 0x12	; 18
    7cf6:	04 c0       	rjmp	.+8      	; 0x7d00 <__umoddi3+0x770>
    7cf8:	66 0c       	add	r6, r6
    7cfa:	77 1c       	adc	r7, r7
    7cfc:	88 1c       	adc	r8, r8
    7cfe:	99 1c       	adc	r9, r9
    7d00:	0a 94       	dec	r0
    7d02:	d2 f7       	brpl	.-12     	; 0x7cf8 <__umoddi3+0x768>
    7d04:	69 8e       	std	Y+25, r6	; 0x19
    7d06:	7a 8e       	std	Y+26, r7	; 0x1a
    7d08:	8b 8e       	std	Y+27, r8	; 0x1b
    7d0a:	9c 8e       	std	Y+28, r9	; 0x1c
	      n2 = n1 >> b;
    7d0c:	82 01       	movw	r16, r4
    7d0e:	71 01       	movw	r14, r2
    7d10:	60 96       	adiw	r28, 0x10	; 16
    7d12:	0f ac       	ldd	r0, Y+63	; 0x3f
    7d14:	60 97       	sbiw	r28, 0x10	; 16
    7d16:	04 c0       	rjmp	.+8      	; 0x7d20 <__umoddi3+0x790>
    7d18:	16 95       	lsr	r17
    7d1a:	07 95       	ror	r16
    7d1c:	f7 94       	ror	r15
    7d1e:	e7 94       	ror	r14
    7d20:	0a 94       	dec	r0
    7d22:	d2 f7       	brpl	.-12     	; 0x7d18 <__umoddi3+0x788>
	      n1 = (n1 << bm) | (n0 >> b);
    7d24:	a2 01       	movw	r20, r4
    7d26:	91 01       	movw	r18, r2
    7d28:	62 96       	adiw	r28, 0x12	; 18
    7d2a:	0f ac       	ldd	r0, Y+63	; 0x3f
    7d2c:	62 97       	sbiw	r28, 0x12	; 18
    7d2e:	04 c0       	rjmp	.+8      	; 0x7d38 <__umoddi3+0x7a8>
    7d30:	22 0f       	add	r18, r18
    7d32:	33 1f       	adc	r19, r19
    7d34:	44 1f       	adc	r20, r20
    7d36:	55 1f       	adc	r21, r21
    7d38:	0a 94       	dec	r0
    7d3a:	d2 f7       	brpl	.-12     	; 0x7d30 <__umoddi3+0x7a0>
    7d3c:	89 a1       	ldd	r24, Y+33	; 0x21
    7d3e:	9a a1       	ldd	r25, Y+34	; 0x22
    7d40:	ab a1       	ldd	r26, Y+35	; 0x23
    7d42:	bc a1       	ldd	r27, Y+36	; 0x24
    7d44:	60 96       	adiw	r28, 0x10	; 16
    7d46:	0f ac       	ldd	r0, Y+63	; 0x3f
    7d48:	60 97       	sbiw	r28, 0x10	; 16
    7d4a:	04 c0       	rjmp	.+8      	; 0x7d54 <__umoddi3+0x7c4>
    7d4c:	b6 95       	lsr	r27
    7d4e:	a7 95       	ror	r26
    7d50:	97 95       	ror	r25
    7d52:	87 95       	ror	r24
    7d54:	0a 94       	dec	r0
    7d56:	d2 f7       	brpl	.-12     	; 0x7d4c <__umoddi3+0x7bc>
    7d58:	28 2b       	or	r18, r24
    7d5a:	39 2b       	or	r19, r25
    7d5c:	4a 2b       	or	r20, r26
    7d5e:	5b 2b       	or	r21, r27
    7d60:	29 a7       	std	Y+41, r18	; 0x29
    7d62:	3a a7       	std	Y+42, r19	; 0x2a
    7d64:	4b a7       	std	Y+43, r20	; 0x2b
    7d66:	5c a7       	std	Y+44, r21	; 0x2c
	      n0 = n0 << bm;
    7d68:	29 a1       	ldd	r18, Y+33	; 0x21
    7d6a:	3a a1       	ldd	r19, Y+34	; 0x22
    7d6c:	4b a1       	ldd	r20, Y+35	; 0x23
    7d6e:	5c a1       	ldd	r21, Y+36	; 0x24
    7d70:	62 96       	adiw	r28, 0x12	; 18
    7d72:	0f ac       	ldd	r0, Y+63	; 0x3f
    7d74:	62 97       	sbiw	r28, 0x12	; 18
    7d76:	04 c0       	rjmp	.+8      	; 0x7d80 <__umoddi3+0x7f0>
    7d78:	22 0f       	add	r18, r18
    7d7a:	33 1f       	adc	r19, r19
    7d7c:	44 1f       	adc	r20, r20
    7d7e:	55 1f       	adc	r21, r21
    7d80:	0a 94       	dec	r0
    7d82:	d2 f7       	brpl	.-12     	; 0x7d78 <__umoddi3+0x7e8>
    7d84:	2d 8f       	std	Y+29, r18	; 0x1d
    7d86:	3e 8f       	std	Y+30, r19	; 0x1e
    7d88:	4f 8f       	std	Y+31, r20	; 0x1f
    7d8a:	58 a3       	std	Y+32, r21	; 0x20

	      udiv_qrnnd (q0, n1, n2, n1, d1);
    7d8c:	2f 96       	adiw	r28, 0x0f	; 15
    7d8e:	8c ad       	ldd	r24, Y+60	; 0x3c
    7d90:	9d ad       	ldd	r25, Y+61	; 0x3d
    7d92:	ae ad       	ldd	r26, Y+62	; 0x3e
    7d94:	bf ad       	ldd	r27, Y+63	; 0x3f
    7d96:	2f 97       	sbiw	r28, 0x0f	; 15
    7d98:	1d 01       	movw	r2, r26
    7d9a:	44 24       	eor	r4, r4
    7d9c:	55 24       	eor	r5, r5
    7d9e:	a0 70       	andi	r26, 0x00	; 0
    7da0:	b0 70       	andi	r27, 0x00	; 0
    7da2:	8f ab       	std	Y+55, r24	; 0x37
    7da4:	98 af       	std	Y+56, r25	; 0x38
    7da6:	a9 af       	std	Y+57, r26	; 0x39
    7da8:	ba af       	std	Y+58, r27	; 0x3a
    7daa:	c8 01       	movw	r24, r16
    7dac:	b7 01       	movw	r22, r14
    7dae:	a2 01       	movw	r20, r4
    7db0:	91 01       	movw	r18, r2
    7db2:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7db6:	39 01       	movw	r6, r18
    7db8:	4a 01       	movw	r8, r20
    7dba:	6f a9       	ldd	r22, Y+55	; 0x37
    7dbc:	78 ad       	ldd	r23, Y+56	; 0x38
    7dbe:	89 ad       	ldd	r24, Y+57	; 0x39
    7dc0:	9a ad       	ldd	r25, Y+58	; 0x3a
    7dc2:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7dc6:	5b 01       	movw	r10, r22
    7dc8:	6c 01       	movw	r12, r24
    7dca:	c8 01       	movw	r24, r16
    7dcc:	b7 01       	movw	r22, r14
    7dce:	a2 01       	movw	r20, r4
    7dd0:	91 01       	movw	r18, r2
    7dd2:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7dd6:	cb 01       	movw	r24, r22
    7dd8:	77 27       	eor	r23, r23
    7dda:	66 27       	eor	r22, r22
    7ddc:	e9 a4       	ldd	r14, Y+41	; 0x29
    7dde:	fa a4       	ldd	r15, Y+42	; 0x2a
    7de0:	0b a5       	ldd	r16, Y+43	; 0x2b
    7de2:	1c a5       	ldd	r17, Y+44	; 0x2c
    7de4:	98 01       	movw	r18, r16
    7de6:	44 27       	eor	r20, r20
    7de8:	55 27       	eor	r21, r21
    7dea:	7b 01       	movw	r14, r22
    7dec:	8c 01       	movw	r16, r24
    7dee:	e2 2a       	or	r14, r18
    7df0:	f3 2a       	or	r15, r19
    7df2:	04 2b       	or	r16, r20
    7df4:	15 2b       	or	r17, r21
    7df6:	ea 14       	cp	r14, r10
    7df8:	fb 04       	cpc	r15, r11
    7dfa:	0c 05       	cpc	r16, r12
    7dfc:	1d 05       	cpc	r17, r13
    7dfe:	08 f4       	brcc	.+2      	; 0x7e02 <__umoddi3+0x872>
    7e00:	cd c1       	rjmp	.+922    	; 0x819c <__umoddi3+0xc0c>
    7e02:	6b aa       	std	Y+51, r6	; 0x33
    7e04:	7c aa       	std	Y+52, r7	; 0x34
    7e06:	8d aa       	std	Y+53, r8	; 0x35
    7e08:	9e aa       	std	Y+54, r9	; 0x36
    7e0a:	ea 18       	sub	r14, r10
    7e0c:	fb 08       	sbc	r15, r11
    7e0e:	0c 09       	sbc	r16, r12
    7e10:	1d 09       	sbc	r17, r13
    7e12:	c8 01       	movw	r24, r16
    7e14:	b7 01       	movw	r22, r14
    7e16:	a2 01       	movw	r20, r4
    7e18:	91 01       	movw	r18, r2
    7e1a:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7e1e:	39 01       	movw	r6, r18
    7e20:	4a 01       	movw	r8, r20
    7e22:	6f a9       	ldd	r22, Y+55	; 0x37
    7e24:	78 ad       	ldd	r23, Y+56	; 0x38
    7e26:	89 ad       	ldd	r24, Y+57	; 0x39
    7e28:	9a ad       	ldd	r25, Y+58	; 0x3a
    7e2a:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7e2e:	5b 01       	movw	r10, r22
    7e30:	6c 01       	movw	r12, r24
    7e32:	c8 01       	movw	r24, r16
    7e34:	b7 01       	movw	r22, r14
    7e36:	a2 01       	movw	r20, r4
    7e38:	91 01       	movw	r18, r2
    7e3a:	0e 94 73 41 	call	0x82e6	; 0x82e6 <__udivmodsi4>
    7e3e:	cb 01       	movw	r24, r22
    7e40:	77 27       	eor	r23, r23
    7e42:	66 27       	eor	r22, r22
    7e44:	29 a5       	ldd	r18, Y+41	; 0x29
    7e46:	3a a5       	ldd	r19, Y+42	; 0x2a
    7e48:	4b a5       	ldd	r20, Y+43	; 0x2b
    7e4a:	5c a5       	ldd	r21, Y+44	; 0x2c
    7e4c:	40 70       	andi	r20, 0x00	; 0
    7e4e:	50 70       	andi	r21, 0x00	; 0
    7e50:	7b 01       	movw	r14, r22
    7e52:	8c 01       	movw	r16, r24
    7e54:	e2 2a       	or	r14, r18
    7e56:	f3 2a       	or	r15, r19
    7e58:	04 2b       	or	r16, r20
    7e5a:	15 2b       	or	r17, r21
    7e5c:	ea 14       	cp	r14, r10
    7e5e:	fb 04       	cpc	r15, r11
    7e60:	0c 05       	cpc	r16, r12
    7e62:	1d 05       	cpc	r17, r13
    7e64:	08 f4       	brcc	.+2      	; 0x7e68 <__umoddi3+0x8d8>
    7e66:	5e c1       	rjmp	.+700    	; 0x8124 <__umoddi3+0xb94>
    7e68:	a4 01       	movw	r20, r8
    7e6a:	93 01       	movw	r18, r6
    7e6c:	ea 18       	sub	r14, r10
    7e6e:	fb 08       	sbc	r15, r11
    7e70:	0c 09       	sbc	r16, r12
    7e72:	1d 09       	sbc	r17, r13
    7e74:	eb ae       	std	Y+59, r14	; 0x3b
    7e76:	fc ae       	std	Y+60, r15	; 0x3c
    7e78:	0d af       	std	Y+61, r16	; 0x3d
    7e7a:	1e af       	std	Y+62, r17	; 0x3e
    7e7c:	ab a8       	ldd	r10, Y+51	; 0x33
    7e7e:	bc a8       	ldd	r11, Y+52	; 0x34
    7e80:	cd a8       	ldd	r12, Y+53	; 0x35
    7e82:	de a8       	ldd	r13, Y+54	; 0x36
    7e84:	d5 01       	movw	r26, r10
    7e86:	99 27       	eor	r25, r25
    7e88:	88 27       	eor	r24, r24
    7e8a:	59 01       	movw	r10, r18
    7e8c:	6a 01       	movw	r12, r20
    7e8e:	a8 2a       	or	r10, r24
    7e90:	b9 2a       	or	r11, r25
    7e92:	ca 2a       	or	r12, r26
    7e94:	db 2a       	or	r13, r27
	      umul_ppmm (m1, m0, q0, d0);
    7e96:	ff ef       	ldi	r31, 0xFF	; 255
    7e98:	ef 2e       	mov	r14, r31
    7e9a:	ff ef       	ldi	r31, 0xFF	; 255
    7e9c:	ff 2e       	mov	r15, r31
    7e9e:	01 2d       	mov	r16, r1
    7ea0:	11 2d       	mov	r17, r1
    7ea2:	ea 20       	and	r14, r10
    7ea4:	fb 20       	and	r15, r11
    7ea6:	0c 21       	and	r16, r12
    7ea8:	1d 21       	and	r17, r13
    7eaa:	56 01       	movw	r10, r12
    7eac:	cc 24       	eor	r12, r12
    7eae:	dd 24       	eor	r13, r13
    7eb0:	69 8c       	ldd	r6, Y+25	; 0x19
    7eb2:	7a 8c       	ldd	r7, Y+26	; 0x1a
    7eb4:	8b 8c       	ldd	r8, Y+27	; 0x1b
    7eb6:	9c 8c       	ldd	r9, Y+28	; 0x1c
    7eb8:	2f ef       	ldi	r18, 0xFF	; 255
    7eba:	3f ef       	ldi	r19, 0xFF	; 255
    7ebc:	40 e0       	ldi	r20, 0x00	; 0
    7ebe:	50 e0       	ldi	r21, 0x00	; 0
    7ec0:	62 22       	and	r6, r18
    7ec2:	73 22       	and	r7, r19
    7ec4:	84 22       	and	r8, r20
    7ec6:	95 22       	and	r9, r21
    7ec8:	89 8d       	ldd	r24, Y+25	; 0x19
    7eca:	9a 8d       	ldd	r25, Y+26	; 0x1a
    7ecc:	ab 8d       	ldd	r26, Y+27	; 0x1b
    7ece:	bc 8d       	ldd	r27, Y+28	; 0x1c
    7ed0:	1d 01       	movw	r2, r26
    7ed2:	44 24       	eor	r4, r4
    7ed4:	55 24       	eor	r5, r5
    7ed6:	c8 01       	movw	r24, r16
    7ed8:	b7 01       	movw	r22, r14
    7eda:	a4 01       	movw	r20, r8
    7edc:	93 01       	movw	r18, r6
    7ede:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7ee2:	6f a7       	std	Y+47, r22	; 0x2f
    7ee4:	78 ab       	std	Y+48, r23	; 0x30
    7ee6:	89 ab       	std	Y+49, r24	; 0x31
    7ee8:	9a ab       	std	Y+50, r25	; 0x32
    7eea:	c8 01       	movw	r24, r16
    7eec:	b7 01       	movw	r22, r14
    7eee:	a2 01       	movw	r20, r4
    7ef0:	91 01       	movw	r18, r2
    7ef2:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7ef6:	7b 01       	movw	r14, r22
    7ef8:	8c 01       	movw	r16, r24
    7efa:	c6 01       	movw	r24, r12
    7efc:	b5 01       	movw	r22, r10
    7efe:	a4 01       	movw	r20, r8
    7f00:	93 01       	movw	r18, r6
    7f02:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7f06:	3b 01       	movw	r6, r22
    7f08:	4c 01       	movw	r8, r24
    7f0a:	c6 01       	movw	r24, r12
    7f0c:	b5 01       	movw	r22, r10
    7f0e:	a2 01       	movw	r20, r4
    7f10:	91 01       	movw	r18, r2
    7f12:	0e 94 95 41 	call	0x832a	; 0x832a <__mulsi3>
    7f16:	5b 01       	movw	r10, r22
    7f18:	6c 01       	movw	r12, r24
    7f1a:	2f a5       	ldd	r18, Y+47	; 0x2f
    7f1c:	38 a9       	ldd	r19, Y+48	; 0x30
    7f1e:	49 a9       	ldd	r20, Y+49	; 0x31
    7f20:	5a a9       	ldd	r21, Y+50	; 0x32
    7f22:	ca 01       	movw	r24, r20
    7f24:	aa 27       	eor	r26, r26
    7f26:	bb 27       	eor	r27, r27
    7f28:	e8 0e       	add	r14, r24
    7f2a:	f9 1e       	adc	r15, r25
    7f2c:	0a 1f       	adc	r16, r26
    7f2e:	1b 1f       	adc	r17, r27
    7f30:	a4 01       	movw	r20, r8
    7f32:	93 01       	movw	r18, r6
    7f34:	2e 0d       	add	r18, r14
    7f36:	3f 1d       	adc	r19, r15
    7f38:	40 1f       	adc	r20, r16
    7f3a:	51 1f       	adc	r21, r17
    7f3c:	26 15       	cp	r18, r6
    7f3e:	37 05       	cpc	r19, r7
    7f40:	48 05       	cpc	r20, r8
    7f42:	59 05       	cpc	r21, r9
    7f44:	40 f4       	brcc	.+16     	; 0x7f56 <__umoddi3+0x9c6>
    7f46:	80 e0       	ldi	r24, 0x00	; 0
    7f48:	90 e0       	ldi	r25, 0x00	; 0
    7f4a:	a1 e0       	ldi	r26, 0x01	; 1
    7f4c:	b0 e0       	ldi	r27, 0x00	; 0
    7f4e:	a8 0e       	add	r10, r24
    7f50:	b9 1e       	adc	r11, r25
    7f52:	ca 1e       	adc	r12, r26
    7f54:	db 1e       	adc	r13, r27
    7f56:	ca 01       	movw	r24, r20
    7f58:	aa 27       	eor	r26, r26
    7f5a:	bb 27       	eor	r27, r27
    7f5c:	86 01       	movw	r16, r12
    7f5e:	75 01       	movw	r14, r10
    7f60:	e8 0e       	add	r14, r24
    7f62:	f9 1e       	adc	r15, r25
    7f64:	0a 1f       	adc	r16, r26
    7f66:	1b 1f       	adc	r17, r27
    7f68:	a9 01       	movw	r20, r18
    7f6a:	33 27       	eor	r19, r19
    7f6c:	22 27       	eor	r18, r18
    7f6e:	8f a5       	ldd	r24, Y+47	; 0x2f
    7f70:	98 a9       	ldd	r25, Y+48	; 0x30
    7f72:	a9 a9       	ldd	r26, Y+49	; 0x31
    7f74:	ba a9       	ldd	r27, Y+50	; 0x32
    7f76:	a0 70       	andi	r26, 0x00	; 0
    7f78:	b0 70       	andi	r27, 0x00	; 0
    7f7a:	39 01       	movw	r6, r18
    7f7c:	4a 01       	movw	r8, r20
    7f7e:	68 0e       	add	r6, r24
    7f80:	79 1e       	adc	r7, r25
    7f82:	8a 1e       	adc	r8, r26
    7f84:	9b 1e       	adc	r9, r27

	      if (m1 > n1 || (m1 == n1 && m0 > n0))
    7f86:	ab ac       	ldd	r10, Y+59	; 0x3b
    7f88:	bc ac       	ldd	r11, Y+60	; 0x3c
    7f8a:	cd ac       	ldd	r12, Y+61	; 0x3d
    7f8c:	de ac       	ldd	r13, Y+62	; 0x3e
    7f8e:	ae 14       	cp	r10, r14
    7f90:	bf 04       	cpc	r11, r15
    7f92:	c0 06       	cpc	r12, r16
    7f94:	d1 06       	cpc	r13, r17
    7f96:	08 f4       	brcc	.+2      	; 0x7f9a <__umoddi3+0xa0a>
    7f98:	47 c0       	rjmp	.+142    	; 0x8028 <__umoddi3+0xa98>
    7f9a:	ae 14       	cp	r10, r14
    7f9c:	bf 04       	cpc	r11, r15
    7f9e:	c0 06       	cpc	r12, r16
    7fa0:	d1 06       	cpc	r13, r17
    7fa2:	09 f4       	brne	.+2      	; 0x7fa6 <__umoddi3+0xa16>
    7fa4:	5e c1       	rjmp	.+700    	; 0x8262 <__umoddi3+0xcd2>
    7fa6:	ae 18       	sub	r10, r14
    7fa8:	bf 08       	sbc	r11, r15
    7faa:	c0 0a       	sbc	r12, r16
    7fac:	d1 0a       	sbc	r13, r17
    7fae:	86 01       	movw	r16, r12
    7fb0:	75 01       	movw	r14, r10
    7fb2:	68 c0       	rjmp	.+208    	; 0x8084 <__umoddi3+0xaf4>
	  if (bm != 0)
	    {
	      /* Normalize, i.e. make the most significant bit of the
		 denominator set.  */

	      d0 = d0 << bm;
    7fb4:	be a6       	std	Y+46, r11	; 0x2e
    7fb6:	ad a6       	std	Y+45, r10	; 0x2d
    7fb8:	0d a4       	ldd	r0, Y+45	; 0x2d
    7fba:	04 c0       	rjmp	.+8      	; 0x7fc4 <__umoddi3+0xa34>
    7fbc:	66 0c       	add	r6, r6
    7fbe:	77 1c       	adc	r7, r7
    7fc0:	88 1c       	adc	r8, r8
    7fc2:	99 1c       	adc	r9, r9
    7fc4:	0a 94       	dec	r0
    7fc6:	d2 f7       	brpl	.-12     	; 0x7fbc <__umoddi3+0xa2c>
	      n1 = (n1 << bm) | (n0 >> (W_TYPE_SIZE - bm));
    7fc8:	a2 01       	movw	r20, r4
    7fca:	91 01       	movw	r18, r2
    7fcc:	0d a4       	ldd	r0, Y+45	; 0x2d
    7fce:	04 c0       	rjmp	.+8      	; 0x7fd8 <__umoddi3+0xa48>
    7fd0:	22 0f       	add	r18, r18
    7fd2:	33 1f       	adc	r19, r19
    7fd4:	44 1f       	adc	r20, r20
    7fd6:	55 1f       	adc	r21, r21
    7fd8:	0a 94       	dec	r0
    7fda:	d2 f7       	brpl	.-12     	; 0x7fd0 <__umoddi3+0xa40>
    7fdc:	80 e2       	ldi	r24, 0x20	; 32
    7fde:	90 e0       	ldi	r25, 0x00	; 0
    7fe0:	8a 19       	sub	r24, r10
    7fe2:	9b 09       	sbc	r25, r11
    7fe4:	a9 a0       	ldd	r10, Y+33	; 0x21
    7fe6:	ba a0       	ldd	r11, Y+34	; 0x22
    7fe8:	cb a0       	ldd	r12, Y+35	; 0x23
    7fea:	dc a0       	ldd	r13, Y+36	; 0x24
    7fec:	04 c0       	rjmp	.+8      	; 0x7ff6 <__umoddi3+0xa66>
    7fee:	d6 94       	lsr	r13
    7ff0:	c7 94       	ror	r12
    7ff2:	b7 94       	ror	r11
    7ff4:	a7 94       	ror	r10
    7ff6:	8a 95       	dec	r24
    7ff8:	d2 f7       	brpl	.-12     	; 0x7fee <__umoddi3+0xa5e>
    7ffa:	19 01       	movw	r2, r18
    7ffc:	2a 01       	movw	r4, r20
    7ffe:	2a 28       	or	r2, r10
    8000:	3b 28       	or	r3, r11
    8002:	4c 28       	or	r4, r12
    8004:	5d 28       	or	r5, r13
	      n0 = n0 << bm;
    8006:	c9 a0       	ldd	r12, Y+33	; 0x21
    8008:	da a0       	ldd	r13, Y+34	; 0x22
    800a:	eb a0       	ldd	r14, Y+35	; 0x23
    800c:	fc a0       	ldd	r15, Y+36	; 0x24
    800e:	0d a4       	ldd	r0, Y+45	; 0x2d
    8010:	04 c0       	rjmp	.+8      	; 0x801a <__umoddi3+0xa8a>
    8012:	cc 0c       	add	r12, r12
    8014:	dd 1c       	adc	r13, r13
    8016:	ee 1c       	adc	r14, r14
    8018:	ff 1c       	adc	r15, r15
    801a:	0a 94       	dec	r0
    801c:	d2 f7       	brpl	.-12     	; 0x8012 <__umoddi3+0xa82>
    801e:	c9 a2       	std	Y+33, r12	; 0x21
    8020:	da a2       	std	Y+34, r13	; 0x22
    8022:	eb a2       	std	Y+35, r14	; 0x23
    8024:	fc a2       	std	Y+36, r15	; 0x24
    8026:	47 cc       	rjmp	.-1906   	; 0x78b6 <__umoddi3+0x326>
	      umul_ppmm (m1, m0, q0, d0);

	      if (m1 > n1 || (m1 == n1 && m0 > n0))
		{
		  q0--;
		  sub_ddmmss (m1, m0, m1, m0, d1, d0);
    8028:	13 01       	movw	r2, r6
    802a:	24 01       	movw	r4, r8
    802c:	89 8d       	ldd	r24, Y+25	; 0x19
    802e:	9a 8d       	ldd	r25, Y+26	; 0x1a
    8030:	ab 8d       	ldd	r26, Y+27	; 0x1b
    8032:	bc 8d       	ldd	r27, Y+28	; 0x1c
    8034:	28 1a       	sub	r2, r24
    8036:	39 0a       	sbc	r3, r25
    8038:	4a 0a       	sbc	r4, r26
    803a:	5b 0a       	sbc	r5, r27
    803c:	2f 96       	adiw	r28, 0x0f	; 15
    803e:	ac ac       	ldd	r10, Y+60	; 0x3c
    8040:	bd ac       	ldd	r11, Y+61	; 0x3d
    8042:	ce ac       	ldd	r12, Y+62	; 0x3e
    8044:	df ac       	ldd	r13, Y+63	; 0x3f
    8046:	2f 97       	sbiw	r28, 0x0f	; 15
    8048:	ea 18       	sub	r14, r10
    804a:	fb 08       	sbc	r15, r11
    804c:	0c 09       	sbc	r16, r12
    804e:	1d 09       	sbc	r17, r13
    8050:	20 e0       	ldi	r18, 0x00	; 0
    8052:	30 e0       	ldi	r19, 0x00	; 0
    8054:	40 e0       	ldi	r20, 0x00	; 0
    8056:	50 e0       	ldi	r21, 0x00	; 0
    8058:	62 14       	cp	r6, r2
    805a:	73 04       	cpc	r7, r3
    805c:	84 04       	cpc	r8, r4
    805e:	95 04       	cpc	r9, r5
    8060:	08 f4       	brcc	.+2      	; 0x8064 <__umoddi3+0xad4>
    8062:	e5 c0       	rjmp	.+458    	; 0x822e <__umoddi3+0xc9e>
    8064:	d8 01       	movw	r26, r16
    8066:	c7 01       	movw	r24, r14
    8068:	82 1b       	sub	r24, r18
    806a:	93 0b       	sbc	r25, r19
    806c:	a4 0b       	sbc	r26, r20
    806e:	b5 0b       	sbc	r27, r21
    8070:	eb ac       	ldd	r14, Y+59	; 0x3b
    8072:	fc ac       	ldd	r15, Y+60	; 0x3c
    8074:	0d ad       	ldd	r16, Y+61	; 0x3d
    8076:	1e ad       	ldd	r17, Y+62	; 0x3e
    8078:	e8 1a       	sub	r14, r24
    807a:	f9 0a       	sbc	r15, r25
    807c:	0a 0b       	sbc	r16, r26
    807e:	1b 0b       	sbc	r17, r27
    8080:	42 01       	movw	r8, r4
    8082:	31 01       	movw	r6, r2
	      q1 = 0;

	      /* Remainder in (n1n0 - m1m0) >> bm.  */
	      if (rp != 0)
		{
		  sub_ddmmss (n1, n0, n1, n0, m1, m0);
    8084:	2d 8d       	ldd	r18, Y+29	; 0x1d
    8086:	3e 8d       	ldd	r19, Y+30	; 0x1e
    8088:	4f 8d       	ldd	r20, Y+31	; 0x1f
    808a:	58 a1       	ldd	r21, Y+32	; 0x20
    808c:	26 19       	sub	r18, r6
    808e:	37 09       	sbc	r19, r7
    8090:	48 09       	sbc	r20, r8
    8092:	59 09       	sbc	r21, r9
    8094:	39 01       	movw	r6, r18
    8096:	4a 01       	movw	r8, r20
    8098:	20 e0       	ldi	r18, 0x00	; 0
    809a:	30 e0       	ldi	r19, 0x00	; 0
    809c:	40 e0       	ldi	r20, 0x00	; 0
    809e:	50 e0       	ldi	r21, 0x00	; 0
    80a0:	8d 8d       	ldd	r24, Y+29	; 0x1d
    80a2:	9e 8d       	ldd	r25, Y+30	; 0x1e
    80a4:	af 8d       	ldd	r26, Y+31	; 0x1f
    80a6:	b8 a1       	ldd	r27, Y+32	; 0x20
    80a8:	86 15       	cp	r24, r6
    80aa:	97 05       	cpc	r25, r7
    80ac:	a8 05       	cpc	r26, r8
    80ae:	b9 05       	cpc	r27, r9
    80b0:	20 f4       	brcc	.+8      	; 0x80ba <__umoddi3+0xb2a>
    80b2:	21 e0       	ldi	r18, 0x01	; 1
    80b4:	30 e0       	ldi	r19, 0x00	; 0
    80b6:	40 e0       	ldi	r20, 0x00	; 0
    80b8:	50 e0       	ldi	r21, 0x00	; 0
    80ba:	d8 01       	movw	r26, r16
    80bc:	c7 01       	movw	r24, r14
    80be:	82 1b       	sub	r24, r18
    80c0:	93 0b       	sbc	r25, r19
    80c2:	a4 0b       	sbc	r26, r20
    80c4:	b5 0b       	sbc	r27, r21
		  rr.s.low = (n1 << b) | (n0 >> bm);
    80c6:	9c 01       	movw	r18, r24
    80c8:	ad 01       	movw	r20, r26
    80ca:	60 96       	adiw	r28, 0x10	; 16
    80cc:	0f ac       	ldd	r0, Y+63	; 0x3f
    80ce:	60 97       	sbiw	r28, 0x10	; 16
    80d0:	04 c0       	rjmp	.+8      	; 0x80da <__umoddi3+0xb4a>
    80d2:	22 0f       	add	r18, r18
    80d4:	33 1f       	adc	r19, r19
    80d6:	44 1f       	adc	r20, r20
    80d8:	55 1f       	adc	r21, r21
    80da:	0a 94       	dec	r0
    80dc:	d2 f7       	brpl	.-12     	; 0x80d2 <__umoddi3+0xb42>
    80de:	84 01       	movw	r16, r8
    80e0:	73 01       	movw	r14, r6
    80e2:	62 96       	adiw	r28, 0x12	; 18
    80e4:	0f ac       	ldd	r0, Y+63	; 0x3f
    80e6:	62 97       	sbiw	r28, 0x12	; 18
    80e8:	04 c0       	rjmp	.+8      	; 0x80f2 <__umoddi3+0xb62>
    80ea:	16 95       	lsr	r17
    80ec:	07 95       	ror	r16
    80ee:	f7 94       	ror	r15
    80f0:	e7 94       	ror	r14
    80f2:	0a 94       	dec	r0
    80f4:	d2 f7       	brpl	.-12     	; 0x80ea <__umoddi3+0xb5a>
    80f6:	2e 29       	or	r18, r14
    80f8:	3f 29       	or	r19, r15
    80fa:	40 2b       	or	r20, r16
    80fc:	51 2b       	or	r21, r17
    80fe:	29 8b       	std	Y+17, r18	; 0x11
    8100:	3a 8b       	std	Y+18, r19	; 0x12
    8102:	4b 8b       	std	Y+19, r20	; 0x13
    8104:	5c 8b       	std	Y+20, r21	; 0x14
		  rr.s.high = n1 >> bm;
    8106:	62 96       	adiw	r28, 0x12	; 18
    8108:	0f ac       	ldd	r0, Y+63	; 0x3f
    810a:	62 97       	sbiw	r28, 0x12	; 18
    810c:	04 c0       	rjmp	.+8      	; 0x8116 <__umoddi3+0xb86>
    810e:	b6 95       	lsr	r27
    8110:	a7 95       	ror	r26
    8112:	97 95       	ror	r25
    8114:	87 95       	ror	r24
    8116:	0a 94       	dec	r0
    8118:	d2 f7       	brpl	.-12     	; 0x810e <__umoddi3+0xb7e>
    811a:	8d 8b       	std	Y+21, r24	; 0x15
    811c:	9e 8b       	std	Y+22, r25	; 0x16
    811e:	af 8b       	std	Y+23, r26	; 0x17
    8120:	b8 8f       	std	Y+24, r27	; 0x18
    8122:	ad c0       	rjmp	.+346    	; 0x827e <__umoddi3+0xcee>
	      d0 = d0 << bm;
	      n2 = n1 >> b;
	      n1 = (n1 << bm) | (n0 >> b);
	      n0 = n0 << bm;

	      udiv_qrnnd (q0, n1, n2, n1, d1);
    8124:	a4 01       	movw	r20, r8
    8126:	93 01       	movw	r18, r6
    8128:	21 50       	subi	r18, 0x01	; 1
    812a:	30 40       	sbci	r19, 0x00	; 0
    812c:	40 40       	sbci	r20, 0x00	; 0
    812e:	50 40       	sbci	r21, 0x00	; 0
    8130:	2f 96       	adiw	r28, 0x0f	; 15
    8132:	8c ad       	ldd	r24, Y+60	; 0x3c
    8134:	9d ad       	ldd	r25, Y+61	; 0x3d
    8136:	ae ad       	ldd	r26, Y+62	; 0x3e
    8138:	bf ad       	ldd	r27, Y+63	; 0x3f
    813a:	2f 97       	sbiw	r28, 0x0f	; 15
    813c:	e8 0e       	add	r14, r24
    813e:	f9 1e       	adc	r15, r25
    8140:	0a 1f       	adc	r16, r26
    8142:	1b 1f       	adc	r17, r27
    8144:	e8 16       	cp	r14, r24
    8146:	f9 06       	cpc	r15, r25
    8148:	0a 07       	cpc	r16, r26
    814a:	1b 07       	cpc	r17, r27
    814c:	08 f4       	brcc	.+2      	; 0x8150 <__umoddi3+0xbc0>
    814e:	8e ce       	rjmp	.-740    	; 0x7e6c <__umoddi3+0x8dc>
    8150:	ea 14       	cp	r14, r10
    8152:	fb 04       	cpc	r15, r11
    8154:	0c 05       	cpc	r16, r12
    8156:	1d 05       	cpc	r17, r13
    8158:	08 f0       	brcs	.+2      	; 0x815c <__umoddi3+0xbcc>
    815a:	88 ce       	rjmp	.-752    	; 0x7e6c <__umoddi3+0x8dc>
    815c:	a4 01       	movw	r20, r8
    815e:	93 01       	movw	r18, r6
    8160:	22 50       	subi	r18, 0x02	; 2
    8162:	30 40       	sbci	r19, 0x00	; 0
    8164:	40 40       	sbci	r20, 0x00	; 0
    8166:	50 40       	sbci	r21, 0x00	; 0
    8168:	e8 0e       	add	r14, r24
    816a:	f9 1e       	adc	r15, r25
    816c:	0a 1f       	adc	r16, r26
    816e:	1b 1f       	adc	r17, r27
    8170:	7d ce       	rjmp	.-774    	; 0x7e6c <__umoddi3+0x8dc>
    {
      if (d0 > n1)
	{
	  /* 0q = nn / 0D */

	  count_leading_zeros (bm, d0);
    8172:	28 e0       	ldi	r18, 0x08	; 8
    8174:	30 e0       	ldi	r19, 0x00	; 0
    8176:	40 e0       	ldi	r20, 0x00	; 0
    8178:	50 e0       	ldi	r21, 0x00	; 0
    817a:	88 e0       	ldi	r24, 0x08	; 8
    817c:	90 e0       	ldi	r25, 0x00	; 0
    817e:	7a cb       	rjmp	.-2316   	; 0x7874 <__umoddi3+0x2e4>
    8180:	28 e1       	ldi	r18, 0x18	; 24
    8182:	30 e0       	ldi	r19, 0x00	; 0
    8184:	40 e0       	ldi	r20, 0x00	; 0
    8186:	50 e0       	ldi	r21, 0x00	; 0
    8188:	88 e1       	ldi	r24, 0x18	; 24
    818a:	90 e0       	ldi	r25, 0x00	; 0
    818c:	73 cb       	rjmp	.-2330   	; 0x7874 <__umoddi3+0x2e4>
	}
      else
	{
	  /* 0q = NN / dd */

	  count_leading_zeros (bm, d1);
    818e:	28 e1       	ldi	r18, 0x18	; 24
    8190:	30 e0       	ldi	r19, 0x00	; 0
    8192:	40 e0       	ldi	r20, 0x00	; 0
    8194:	50 e0       	ldi	r21, 0x00	; 0
    8196:	88 e1       	ldi	r24, 0x18	; 24
    8198:	90 e0       	ldi	r25, 0x00	; 0
    819a:	16 cb       	rjmp	.-2516   	; 0x77c8 <__umoddi3+0x238>
	      d0 = d0 << bm;
	      n2 = n1 >> b;
	      n1 = (n1 << bm) | (n0 >> b);
	      n0 = n0 << bm;

	      udiv_qrnnd (q0, n1, n2, n1, d1);
    819c:	a4 01       	movw	r20, r8
    819e:	93 01       	movw	r18, r6
    81a0:	21 50       	subi	r18, 0x01	; 1
    81a2:	30 40       	sbci	r19, 0x00	; 0
    81a4:	40 40       	sbci	r20, 0x00	; 0
    81a6:	50 40       	sbci	r21, 0x00	; 0
    81a8:	2b ab       	std	Y+51, r18	; 0x33
    81aa:	3c ab       	std	Y+52, r19	; 0x34
    81ac:	4d ab       	std	Y+53, r20	; 0x35
    81ae:	5e ab       	std	Y+54, r21	; 0x36
    81b0:	2f 96       	adiw	r28, 0x0f	; 15
    81b2:	8c ad       	ldd	r24, Y+60	; 0x3c
    81b4:	9d ad       	ldd	r25, Y+61	; 0x3d
    81b6:	ae ad       	ldd	r26, Y+62	; 0x3e
    81b8:	bf ad       	ldd	r27, Y+63	; 0x3f
    81ba:	2f 97       	sbiw	r28, 0x0f	; 15
    81bc:	e8 0e       	add	r14, r24
    81be:	f9 1e       	adc	r15, r25
    81c0:	0a 1f       	adc	r16, r26
    81c2:	1b 1f       	adc	r17, r27
    81c4:	e8 16       	cp	r14, r24
    81c6:	f9 06       	cpc	r15, r25
    81c8:	0a 07       	cpc	r16, r26
    81ca:	1b 07       	cpc	r17, r27
    81cc:	08 f4       	brcc	.+2      	; 0x81d0 <__umoddi3+0xc40>
    81ce:	1d ce       	rjmp	.-966    	; 0x7e0a <__umoddi3+0x87a>
    81d0:	ea 14       	cp	r14, r10
    81d2:	fb 04       	cpc	r15, r11
    81d4:	0c 05       	cpc	r16, r12
    81d6:	1d 05       	cpc	r17, r13
    81d8:	08 f0       	brcs	.+2      	; 0x81dc <__umoddi3+0xc4c>
    81da:	17 ce       	rjmp	.-978    	; 0x7e0a <__umoddi3+0x87a>
    81dc:	a4 01       	movw	r20, r8
    81de:	93 01       	movw	r18, r6
    81e0:	22 50       	subi	r18, 0x02	; 2
    81e2:	30 40       	sbci	r19, 0x00	; 0
    81e4:	40 40       	sbci	r20, 0x00	; 0
    81e6:	50 40       	sbci	r21, 0x00	; 0
    81e8:	2b ab       	std	Y+51, r18	; 0x33
    81ea:	3c ab       	std	Y+52, r19	; 0x34
    81ec:	4d ab       	std	Y+53, r20	; 0x35
    81ee:	5e ab       	std	Y+54, r21	; 0x36
    81f0:	e8 0e       	add	r14, r24
    81f2:	f9 1e       	adc	r15, r25
    81f4:	0a 1f       	adc	r16, r26
    81f6:	1b 1f       	adc	r17, r27
    81f8:	08 ce       	rjmp	.-1008   	; 0x7e0a <__umoddi3+0x87a>
	}
      else
	{
	  /* 0q = NN / dd */

	  count_leading_zeros (bm, d1);
    81fa:	28 e0       	ldi	r18, 0x08	; 8
    81fc:	30 e0       	ldi	r19, 0x00	; 0
    81fe:	40 e0       	ldi	r20, 0x00	; 0
    8200:	50 e0       	ldi	r21, 0x00	; 0
    8202:	88 e0       	ldi	r24, 0x08	; 8
    8204:	90 e0       	ldi	r25, 0x00	; 0
    8206:	e0 ca       	rjmp	.-2624   	; 0x77c8 <__umoddi3+0x238>
	  /* qq = NN / 0d */

	  if (d0 == 0)
	    d0 = 1 / d0;	/* Divide intentionally by zero.  */

	  count_leading_zeros (bm, d0);
    8208:	28 e1       	ldi	r18, 0x18	; 24
    820a:	30 e0       	ldi	r19, 0x00	; 0
    820c:	40 e0       	ldi	r20, 0x00	; 0
    820e:	50 e0       	ldi	r21, 0x00	; 0
    8210:	88 e1       	ldi	r24, 0x18	; 24
    8212:	90 e0       	ldi	r25, 0x00	; 0
    8214:	26 ca       	rjmp	.-2996   	; 0x7662 <__umoddi3+0xd2>
    8216:	28 e0       	ldi	r18, 0x08	; 8
    8218:	30 e0       	ldi	r19, 0x00	; 0
    821a:	40 e0       	ldi	r20, 0x00	; 0
    821c:	50 e0       	ldi	r21, 0x00	; 0
    821e:	88 e0       	ldi	r24, 0x08	; 8
    8220:	90 e0       	ldi	r25, 0x00	; 0
    8222:	1f ca       	rjmp	.-3010   	; 0x7662 <__umoddi3+0xd2>
	      /* The condition on the next line takes advantage of that
		 n1 >= d1 (true due to program flow).  */
	      if (n1 > d1 || n0 >= d0)
		{
		  q0 = 1;
		  sub_ddmmss (n1, n0, n1, n0, d1, d0);
    8224:	61 e0       	ldi	r22, 0x01	; 1
    8226:	70 e0       	ldi	r23, 0x00	; 0
    8228:	80 e0       	ldi	r24, 0x00	; 0
    822a:	90 e0       	ldi	r25, 0x00	; 0
    822c:	5b cc       	rjmp	.-1866   	; 0x7ae4 <__umoddi3+0x554>
	      umul_ppmm (m1, m0, q0, d0);

	      if (m1 > n1 || (m1 == n1 && m0 > n0))
		{
		  q0--;
		  sub_ddmmss (m1, m0, m1, m0, d1, d0);
    822e:	21 e0       	ldi	r18, 0x01	; 1
    8230:	30 e0       	ldi	r19, 0x00	; 0
    8232:	40 e0       	ldi	r20, 0x00	; 0
    8234:	50 e0       	ldi	r21, 0x00	; 0
    8236:	16 cf       	rjmp	.-468    	; 0x8064 <__umoddi3+0xad4>
	      udiv_qrnnd (q1, n1, n2, n1, d0);
	    }

	  /* n1 != d0...  */

	  udiv_qrnnd (q0, n0, n1, n0, d0);
    8238:	e6 0c       	add	r14, r6
    823a:	f7 1c       	adc	r15, r7
    823c:	08 1d       	adc	r16, r8
    823e:	19 1d       	adc	r17, r9
    8240:	80 ca       	rjmp	.-2816   	; 0x7742 <__umoddi3+0x1b2>
	      d0 = d0 << bm;
	      n1 = (n1 << bm) | (n0 >> (W_TYPE_SIZE - bm));
	      n0 = n0 << bm;
	    }

	  udiv_qrnnd (q0, n0, n1, n0, d0);
    8242:	e6 0c       	add	r14, r6
    8244:	f7 1c       	adc	r15, r7
    8246:	08 1d       	adc	r16, r8
    8248:	19 1d       	adc	r17, r9
    824a:	8a cb       	rjmp	.-2284   	; 0x7960 <__umoddi3+0x3d0>
	      d0 = d0 << bm;
	      n2 = n1 >> b;
	      n1 = (n1 << bm) | (n0 >> b);
	      n0 = n0 << bm;

	      udiv_qrnnd (q1, n1, n2, n1, d0);
    824c:	ea 14       	cp	r14, r10
    824e:	fb 04       	cpc	r15, r11
    8250:	0c 05       	cpc	r16, r12
    8252:	1d 05       	cpc	r17, r13
    8254:	08 f0       	brcs	.+2      	; 0x8258 <__umoddi3+0xcc8>
    8256:	d9 cc       	rjmp	.-1614   	; 0x7c0a <__umoddi3+0x67a>
    8258:	e6 0c       	add	r14, r6
    825a:	f7 1c       	adc	r15, r7
    825c:	08 1d       	adc	r16, r8
    825e:	19 1d       	adc	r17, r9
    8260:	d4 cc       	rjmp	.-1624   	; 0x7c0a <__umoddi3+0x67a>
	      n0 = n0 << bm;

	      udiv_qrnnd (q0, n1, n2, n1, d1);
	      umul_ppmm (m1, m0, q0, d0);

	      if (m1 > n1 || (m1 == n1 && m0 > n0))
    8262:	2d 8d       	ldd	r18, Y+29	; 0x1d
    8264:	3e 8d       	ldd	r19, Y+30	; 0x1e
    8266:	4f 8d       	ldd	r20, Y+31	; 0x1f
    8268:	58 a1       	ldd	r21, Y+32	; 0x20
    826a:	26 15       	cp	r18, r6
    826c:	37 05       	cpc	r19, r7
    826e:	48 05       	cpc	r20, r8
    8270:	59 05       	cpc	r21, r9
    8272:	08 f4       	brcc	.+2      	; 0x8276 <__umoddi3+0xce6>
    8274:	d9 ce       	rjmp	.-590    	; 0x8028 <__umoddi3+0xa98>
    8276:	ee 24       	eor	r14, r14
    8278:	ff 24       	eor	r15, r15
    827a:	87 01       	movw	r16, r14
    827c:	03 cf       	rjmp	.-506    	; 0x8084 <__umoddi3+0xaf4>
	      if (rp != 0)
		{
		  sub_ddmmss (n1, n0, n1, n0, m1, m0);
		  rr.s.low = (n1 << b) | (n0 >> bm);
		  rr.s.high = n1 >> bm;
		  *rp = rr.ll;
    827e:	29 88       	ldd	r2, Y+17	; 0x11
    8280:	3a 88       	ldd	r3, Y+18	; 0x12
    8282:	4b 88       	ldd	r4, Y+19	; 0x13
    8284:	5c 88       	ldd	r5, Y+20	; 0x14
    8286:	6d 88       	ldd	r6, Y+21	; 0x15
    8288:	7e 88       	ldd	r7, Y+22	; 0x16
    828a:	8f 88       	ldd	r8, Y+23	; 0x17
    828c:	98 8c       	ldd	r9, Y+24	; 0x18
  UDWtype w;

  (void) __udivmoddi4 (u, v, &w);

  return w;
}
    828e:	22 2d       	mov	r18, r2
    8290:	33 2d       	mov	r19, r3
    8292:	44 2d       	mov	r20, r4
    8294:	55 2d       	mov	r21, r5
    8296:	66 2d       	mov	r22, r6
    8298:	77 2d       	mov	r23, r7
    829a:	88 2d       	mov	r24, r8
    829c:	99 2d       	mov	r25, r9
    829e:	e2 e1       	ldi	r30, 0x12	; 18
    82a0:	ce 5a       	subi	r28, 0xAE	; 174
    82a2:	df 4f       	sbci	r29, 0xFF	; 255
    82a4:	0c 94 d0 41 	jmp	0x83a0	; 0x83a0 <__epilogue_restores__>

000082a8 <memcpy>:
    82a8:	e6 2f       	mov	r30, r22
    82aa:	f7 2f       	mov	r31, r23
    82ac:	a8 2f       	mov	r26, r24
    82ae:	b9 2f       	mov	r27, r25
    82b0:	02 c0       	rjmp	.+4      	; 0x82b6 <memcpy+0xe>
    82b2:	01 90       	ld	r0, Z+
    82b4:	0d 92       	st	X+, r0
    82b6:	41 50       	subi	r20, 0x01	; 1
    82b8:	50 40       	sbci	r21, 0x00	; 0
    82ba:	d8 f7       	brcc	.-10     	; 0x82b2 <memcpy+0xa>
    82bc:	08 95       	ret

000082be <__udivmodhi4>:
    82be:	aa 1b       	sub	r26, r26
    82c0:	bb 1b       	sub	r27, r27
    82c2:	51 e1       	ldi	r21, 0x11	; 17
    82c4:	07 c0       	rjmp	.+14     	; 0x82d4 <__udivmodhi4_ep>

000082c6 <__udivmodhi4_loop>:
    82c6:	aa 1f       	adc	r26, r26
    82c8:	bb 1f       	adc	r27, r27
    82ca:	a6 17       	cp	r26, r22
    82cc:	b7 07       	cpc	r27, r23
    82ce:	10 f0       	brcs	.+4      	; 0x82d4 <__udivmodhi4_ep>
    82d0:	a6 1b       	sub	r26, r22
    82d2:	b7 0b       	sbc	r27, r23

000082d4 <__udivmodhi4_ep>:
    82d4:	88 1f       	adc	r24, r24
    82d6:	99 1f       	adc	r25, r25
    82d8:	5a 95       	dec	r21
    82da:	a9 f7       	brne	.-22     	; 0x82c6 <__udivmodhi4_loop>
    82dc:	80 95       	com	r24
    82de:	90 95       	com	r25
    82e0:	bc 01       	movw	r22, r24
    82e2:	cd 01       	movw	r24, r26
    82e4:	08 95       	ret

000082e6 <__udivmodsi4>:
    82e6:	a1 e2       	ldi	r26, 0x21	; 33
    82e8:	1a 2e       	mov	r1, r26
    82ea:	aa 1b       	sub	r26, r26
    82ec:	bb 1b       	sub	r27, r27
    82ee:	fd 01       	movw	r30, r26
    82f0:	0d c0       	rjmp	.+26     	; 0x830c <__udivmodsi4_ep>

000082f2 <__udivmodsi4_loop>:
    82f2:	aa 1f       	adc	r26, r26
    82f4:	bb 1f       	adc	r27, r27
    82f6:	ee 1f       	adc	r30, r30
    82f8:	ff 1f       	adc	r31, r31
    82fa:	a2 17       	cp	r26, r18
    82fc:	b3 07       	cpc	r27, r19
    82fe:	e4 07       	cpc	r30, r20
    8300:	f5 07       	cpc	r31, r21
    8302:	20 f0       	brcs	.+8      	; 0x830c <__udivmodsi4_ep>
    8304:	a2 1b       	sub	r26, r18
    8306:	b3 0b       	sbc	r27, r19
    8308:	e4 0b       	sbc	r30, r20
    830a:	f5 0b       	sbc	r31, r21

0000830c <__udivmodsi4_ep>:
    830c:	66 1f       	adc	r22, r22
    830e:	77 1f       	adc	r23, r23
    8310:	88 1f       	adc	r24, r24
    8312:	99 1f       	adc	r25, r25
    8314:	1a 94       	dec	r1
    8316:	69 f7       	brne	.-38     	; 0x82f2 <__udivmodsi4_loop>
    8318:	60 95       	com	r22
    831a:	70 95       	com	r23
    831c:	80 95       	com	r24
    831e:	90 95       	com	r25
    8320:	9b 01       	movw	r18, r22
    8322:	ac 01       	movw	r20, r24
    8324:	bd 01       	movw	r22, r26
    8326:	cf 01       	movw	r24, r30
    8328:	08 95       	ret

0000832a <__mulsi3>:
    832a:	62 9f       	mul	r22, r18
    832c:	d0 01       	movw	r26, r0
    832e:	73 9f       	mul	r23, r19
    8330:	f0 01       	movw	r30, r0
    8332:	82 9f       	mul	r24, r18
    8334:	e0 0d       	add	r30, r0
    8336:	f1 1d       	adc	r31, r1
    8338:	64 9f       	mul	r22, r20
    833a:	e0 0d       	add	r30, r0
    833c:	f1 1d       	adc	r31, r1
    833e:	92 9f       	mul	r25, r18
    8340:	f0 0d       	add	r31, r0
    8342:	83 9f       	mul	r24, r19
    8344:	f0 0d       	add	r31, r0
    8346:	74 9f       	mul	r23, r20
    8348:	f0 0d       	add	r31, r0
    834a:	65 9f       	mul	r22, r21
    834c:	f0 0d       	add	r31, r0
    834e:	99 27       	eor	r25, r25
    8350:	72 9f       	mul	r23, r18
    8352:	b0 0d       	add	r27, r0
    8354:	e1 1d       	adc	r30, r1
    8356:	f9 1f       	adc	r31, r25
    8358:	63 9f       	mul	r22, r19
    835a:	b0 0d       	add	r27, r0
    835c:	e1 1d       	adc	r30, r1
    835e:	f9 1f       	adc	r31, r25
    8360:	bd 01       	movw	r22, r26
    8362:	cf 01       	movw	r24, r30
    8364:	11 24       	eor	r1, r1
    8366:	08 95       	ret

00008368 <__prologue_saves__>:
    8368:	2f 92       	push	r2
    836a:	3f 92       	push	r3
    836c:	4f 92       	push	r4
    836e:	5f 92       	push	r5
    8370:	6f 92       	push	r6
    8372:	7f 92       	push	r7
    8374:	8f 92       	push	r8
    8376:	9f 92       	push	r9
    8378:	af 92       	push	r10
    837a:	bf 92       	push	r11
    837c:	cf 92       	push	r12
    837e:	df 92       	push	r13
    8380:	ef 92       	push	r14
    8382:	ff 92       	push	r15
    8384:	0f 93       	push	r16
    8386:	1f 93       	push	r17
    8388:	cf 93       	push	r28
    838a:	df 93       	push	r29
    838c:	cd b7       	in	r28, 0x3d	; 61
    838e:	de b7       	in	r29, 0x3e	; 62
    8390:	ca 1b       	sub	r28, r26
    8392:	db 0b       	sbc	r29, r27
    8394:	0f b6       	in	r0, 0x3f	; 63
    8396:	f8 94       	cli
    8398:	de bf       	out	0x3e, r29	; 62
    839a:	0f be       	out	0x3f, r0	; 63
    839c:	cd bf       	out	0x3d, r28	; 61
    839e:	09 94       	ijmp

000083a0 <__epilogue_restores__>:
    83a0:	2a 88       	ldd	r2, Y+18	; 0x12
    83a2:	39 88       	ldd	r3, Y+17	; 0x11
    83a4:	48 88       	ldd	r4, Y+16	; 0x10
    83a6:	5f 84       	ldd	r5, Y+15	; 0x0f
    83a8:	6e 84       	ldd	r6, Y+14	; 0x0e
    83aa:	7d 84       	ldd	r7, Y+13	; 0x0d
    83ac:	8c 84       	ldd	r8, Y+12	; 0x0c
    83ae:	9b 84       	ldd	r9, Y+11	; 0x0b
    83b0:	aa 84       	ldd	r10, Y+10	; 0x0a
    83b2:	b9 84       	ldd	r11, Y+9	; 0x09
    83b4:	c8 84       	ldd	r12, Y+8	; 0x08
    83b6:	df 80       	ldd	r13, Y+7	; 0x07
    83b8:	ee 80       	ldd	r14, Y+6	; 0x06
    83ba:	fd 80       	ldd	r15, Y+5	; 0x05
    83bc:	0c 81       	ldd	r16, Y+4	; 0x04
    83be:	1b 81       	ldd	r17, Y+3	; 0x03
    83c0:	aa 81       	ldd	r26, Y+2	; 0x02
    83c2:	b9 81       	ldd	r27, Y+1	; 0x01
    83c4:	ce 0f       	add	r28, r30
    83c6:	d1 1d       	adc	r29, r1
    83c8:	0f b6       	in	r0, 0x3f	; 63
    83ca:	f8 94       	cli
    83cc:	de bf       	out	0x3e, r29	; 62
    83ce:	0f be       	out	0x3f, r0	; 63
    83d0:	cd bf       	out	0x3d, r28	; 61
    83d2:	ed 01       	movw	r28, r26
    83d4:	08 95       	ret

000083d6 <__tablejump2__>:
    83d6:	ee 0f       	add	r30, r30
    83d8:	ff 1f       	adc	r31, r31

000083da <__tablejump__>:
    83da:	05 90       	lpm	r0, Z+
    83dc:	f4 91       	lpm	r31, Z
    83de:	e0 2d       	mov	r30, r0
    83e0:	09 94       	ijmp

000083e2 <__do_global_dtors>:
    83e2:	10 e0       	ldi	r17, 0x00	; 0
    83e4:	c0 ea       	ldi	r28, 0xA0	; 160
    83e6:	d0 e0       	ldi	r29, 0x00	; 0
    83e8:	04 c0       	rjmp	.+8      	; 0x83f2 <.do_global_dtors_start>

000083ea <.do_global_dtors_loop>:
    83ea:	fe 01       	movw	r30, r28
    83ec:	0e 94 ed 41 	call	0x83da	; 0x83da <__tablejump__>
    83f0:	22 96       	adiw	r28, 0x02	; 2

000083f2 <.do_global_dtors_start>:
    83f2:	c4 3a       	cpi	r28, 0xA4	; 164
    83f4:	d1 07       	cpc	r29, r17
    83f6:	c9 f7       	brne	.-14     	; 0x83ea <.do_global_dtors_loop>
