static void mpeg2_idct_copy_c (int16_t * block, uint8_t * dest,
                               const int stride) 
{
    int i = 8;
    libmpeg2ToNormal(block);
    /* Realiza��o da IDCT */
    c_idct2D(block);	
    // ...	
}
 
