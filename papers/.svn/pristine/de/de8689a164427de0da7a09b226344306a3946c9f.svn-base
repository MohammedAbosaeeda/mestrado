    register unsigned int sum0;
    register unsigned int sum1;

    for(unsigned int i = 0; i <= REP; i++) {
        for(unsigned int j = 0; j < ROWS; j++) {
	        sum0 = 0;
	        sum1 = 0;
            for(unsigned int k = 0; k < COLS; k++) {
                sum0 += array0[j][k];
                sum1 += array1[j][k];
            }
            for(unsigned int k = 0; k < COLS; k++) {
                array0[j][0] = sum0;
                array1[j][0] = sum1;
            }
       }
    }
