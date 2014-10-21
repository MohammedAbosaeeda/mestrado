unsigned char p[TS_PKT_LEN];
TS_AF_t af;

af.adaptation_field_length = 0x68;
af.discontinuity_indicator = 0x0;
af.random_access_indicator = 0x1;
af.elementary_stream_priority_indicator = 0x1;
af.PCR_flag = 0x0;
af.OPCR_flag = 0x1;
af.splicing_point_flag = 0x0;
af.transport_private_data_flag = 0x0;
af.adaptation_field_extension_flag = 0x1;
af.original_program_clock_reference_base = 0x01209a028;
af.original_program_clock_reference_extension = 0x001;
af.adaptation_field_extension_length = 0x92;
af.ltw_flag = 0x0;
af.piecewise_rate_flag = 0x1;
af.seamless_splice_flag = 0x1;
af.piecewise_rate = 0x0021a562;
af.splice_type = 0x1;
af.DTS_next_AU = 0x018e02725;

TS_create_AF(&p, &af);
