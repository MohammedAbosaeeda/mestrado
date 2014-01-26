static void mpeg2_idct_copy_c (int16_t * block, uint8_t * dest, 
                               const int stride) 
{
    int i;
    /* Realização da IDCT */
    for (i = 0; i < 8; i++)
        idct_row (block + 8 * i);
    for (i = 0; i < 8; i++)
        idct_col (block + i);		
    // ... 		
}
