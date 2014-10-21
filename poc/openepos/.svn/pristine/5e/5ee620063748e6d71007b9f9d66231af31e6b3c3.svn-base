unsigned char p[TS_PKT_LEN];
TS_hdr_t *ts;

TS_init_pkt(&p);
ts = (TS_hdr_t*)&p;
ts->transport_error_indicator = 0x1;
ts->payload_unit_start_indicator = 0x1;
ts->transport_priority = 0x0;
TS_set_PID(ts, 0x0fc6);
ts->transport_scrambling_control = 0x2;
ts->adaptation_field_control = 0x2;
ts->continuity_counter = 0x4;
