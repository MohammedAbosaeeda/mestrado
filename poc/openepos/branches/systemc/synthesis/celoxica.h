/****************************************************************
*                                                               *
* Copyright (C) 1991-2005 Celoxica Ltd. All rights reserved.    *
*                                                               *
*****************************************************************
*                                                               *
* Project   :   DK                                              *
* Date      :   30 JUN 2004                                     *
* File      :   celoxica.h                                      *
* Author    :   Johan Ditmar (JD)                               *
* Contributors:                                                 *
*                                                               *
* Description                                                   *
*     Support library for Celoxica generated SystemC            *
*                                                               *
* Date         Version  Author  Reason for change               *
*                                                               *
* 30 JUN 2004    1.00    JD     Created                         *
* 22 JUN 2005    2.00    JD     Updated for DK4                 *
*                                                               *
****************************************************************/
#ifndef CELOXICA_H
#define CELOXICA_H

/*
 * check that first clock edge comes after time 0
 */
inline void check_clock_start()
{
    static bool first = true;

    if ( first && sc_time_stamp().value()==0 )
    {
      cout << "ERROR: Clock edge detected at time 0. Please delay start of clock for accurate simulation." << endl;
      first = false;
    }
}

/*
 * thread start pulse generator
 */
template<int delay, bool clk_polarity, bool aclr_polarity, bool sclr_polarity>
SC_MODULE( celstart )
{
    sc_in<bool> clk, aclr, sclr;
    sc_out<bool> start;

    sc_uint<delay+1> delayline;

    void process()
    {
        bool aclr_int = aclr_polarity ? aclr.read() : !aclr.read();
        bool sclr_int = sclr_polarity ? sclr.read() : !sclr.read();
        bool clk_edge = clk_polarity ? clk.posedge() : clk.negedge();

        if ( aclr_int || sclr_int )
        {
            // reset condition
            delayline = 1;
        }
        else if ( clk_edge )
        {
            delayline <<= 1;

#ifndef SYNTHESIS
            check_clock_start();
#endif
        }
        start = delayline[delay];
    }

    SC_CTOR( celstart ) : delayline( 1 )
    {
       SC_METHOD( process );
       if ( clk_polarity ) sensitive_pos << clk; else sensitive_neg << clk;
       if ( aclr_polarity ) sensitive_pos << aclr; else sensitive_neg << aclr;
    }
};

/*
 * Unsigned division
 */
template <int W>
inline sc_uint<W> divide(sc_uint<W> a, sc_uint<W> b)
{
  static uint64 prev_time;
  static bool prevZero = true;

  if ( prevZero && prev_time>0 && sc_time_stamp().value()-prev_time>0 )
  {
    /*
     * Divisor has been 0 for some amount of time.
     */
    cout << "Divisor is zero @ " << sc_time_stamp() << endl;
  }

  if (b==sc_uint<W>(0))
  {
    if ( !prevZero ) prev_time = sc_time_stamp().value();
    return 0;
  }
  else
  {
    return a/b;
  }

  prevZero = (b==sc_uint<W>(0));
}

template <int W>
inline sc_biguint<W> divide(sc_biguint<W> a, sc_biguint<W> b)
{
  static uint64 prev_time;
  static bool prevZero = true;

  if ( prevZero && prev_time>0 && sc_time_stamp().value()-prev_time>0 )
  {
    /*
     * Divisor has been 0 for some amount of time.
     */
    cout << "Divisor is zero @ " << sc_time_stamp() << endl;
  }

  if (b==sc_biguint<W>(0))
  {
    if ( !prevZero ) prev_time = sc_time_stamp().value();
    return 0;
  }
  else
  {
    return a/b;
  }

  prevZero = (b==sc_biguint<W>(0));
}

/*
 * Signed division
 */
template <int W>
inline sc_int<W> divide(sc_int<W> a, sc_int<W> b)
{
  static uint64 prev_time;
  static bool prevZero = true;

  if ( prevZero && prev_time>0 && sc_time_stamp().value()-prev_time>0 )
  {
    /*
     * Divisor has been 0 for some amount of time.
     */
    cout << "Divisor is zero @ " << sc_time_stamp() << endl;
  }

  if (b==sc_int<W>(0))
  {
    if ( !prevZero ) prev_time = sc_time_stamp().value();
    return 0;
  }
  else
  {
    return a/b;
  }

  prevZero = (b==sc_int<W>(0));
}

template <int W>
inline sc_bigint<W> divide(sc_bigint<W> a, sc_bigint<W> b)
{
  static uint64 prev_time;
  static bool prevZero = true;

  if ( prevZero && prev_time>0 && sc_time_stamp().value()-prev_time>0 )
  {
    /*
     * Divisor has been 0 for some amount of time.
     */
    cout << "Divisor is zero @ " << sc_time_stamp() << endl;
  }

  if (b==sc_bigint<W>(0))
  {
    if ( !prevZero ) prev_time = sc_time_stamp().value();
    return 0;
  }
  else
  {
    return a/b;
  }

  prevZero = (b==sc_bigint<W>(0));
}

/*
 * Unsigned modulus
 */
template <int W>
inline sc_uint<W> mod(sc_uint<W> a, sc_uint<W> b)
{
  static uint64 prev_time;
  static bool prevZero = true;

  if ( prevZero && prev_time>0 && sc_time_stamp().value()-prev_time>0 )
  {
    /*
     * Divisor has been 0 for some amount of time.
     */
    cout << "Divisor is zero @ " << sc_time_stamp() << endl;
  }

  if (b==sc_uint<W>(0))
  {
    if ( !prevZero ) prev_time = sc_time_stamp().value();
    return 0;
  }
  else
  {
    return a%b;
  }

  prevZero = (b==sc_uint<W>(0));
}

template <int W>
inline sc_biguint<W> mod(sc_biguint<W> a, sc_biguint<W> b)
{
  static uint64 prev_time;
  static bool prevZero = true;

  if ( prevZero && prev_time>0 && sc_time_stamp().value()-prev_time>0 )
  {
    /*
     * Divisor has been 0 for some amount of time.
     */
    cout << "Divisor is zero @ " << sc_time_stamp() << endl;
  }

  if (b==sc_biguint<W>(0))
  {
    if ( !prevZero ) prev_time = sc_time_stamp().value();
    return 0;
  }
  else
  {
    return a%b;
  }

  prevZero = (b==sc_biguint<W>(0));
}

/*
 * Signed modulus
 */
template <int W>
inline sc_int<W> mod(sc_int<W> a, sc_int<W> b)
{
  static uint64 prev_time;
  static bool prevZero = true;

  if ( prevZero && prev_time>0 && sc_time_stamp().value()-prev_time>0 )
  {
    /*
     * Divisor has been 0 for some amount of time.
     */
    cout << "Divisor is zero @ " << sc_time_stamp() << endl;
  }

  if (b==sc_int<W>(0))
  {
    if ( !prevZero ) prev_time = sc_time_stamp().value();
    return 0;
  }
  else
  {
    return a%b;
  }

  prevZero = (b==sc_int<W>(0));
}

template <int W>
inline sc_bigint<W> mod(sc_bigint<W> a, sc_bigint<W> b)
{
  static uint64 prev_time;
  static bool prevZero = true;

  if ( prevZero && prev_time>0 && sc_time_stamp().value()-prev_time>0 )
  {
    /*
     * Divisor has been 0 for some amount of time.
     */
    cout << "Divisor is zero @ " << sc_time_stamp() << endl;
  }

  if (b==sc_bigint<W>(0))
  {
    if ( !prevZero ) prev_time = sc_time_stamp().value();
    return 0;
  }
  else
  {
    return a%b;
  }

  prevZero = (b==sc_bigint<W>(0));
}


/*
 * Asynchronous memory
 */
template<class W, class A, int word_size, int mem_size, int num_ports, int num_init_elems, sc_lv<word_size> init[]>
SC_MODULE ( celram )
{
  sc_in<bool> clk[num_ports];
  sc_in<A> addr[num_ports];

  sc_in<bool> re[num_ports];
  sc_in<bool> we[num_ports];

  sc_out<W> data_out[num_ports];
  sc_in<W> data_in[num_ports];

  void mem_write();
  void mem_read();

  void mem_init()
  {
     sc_lv<word_size> allxs (sc_logic_X);

     for (unsigned i=0; i<mem_size; i++)
     {
       if (i<num_init_elems)
         ram[i] = init[i];
       else
         ram[i] = allxs;
     }
  }

  sc_signal<sc_lv<word_size> > ram [mem_size];

  SC_CTOR( celram )
  {
    mem_init();

    SC_METHOD ( mem_write );
    for ( int i=0; i<num_ports; ++i )
    {
      sensitive_pos << clk[i];
    }

    SC_METHOD ( mem_read );
    for ( int i=0; i<num_ports; ++i )
    {
      sensitive << re[i] << addr[i];
    }
  }
};

template<class W, class A, int word_size, int mem_size, int num_ports, int num_init_elems, sc_lv<word_size> init[]>
inline void celram<W, A, word_size, mem_size, num_ports, num_init_elems, init>::mem_write()
{
  for ( int i=0; i<num_ports; ++i )
  {
    if ( we[i] )
    {
      if ( (int)addr[i].read() >= mem_size )
      {
        /*
         * Address is out of range
         */
        cout << "Address " << addr[i].read() << " is out of range for write operation." << endl;
      }
      else
      {
        ram [ addr[i].read() ] = (sc_lv<word_size>)data_in[i];
        data_out[i] = data_in[i];
      }
    }
  }
}

template<class W, class A, int word_size, int mem_size, int num_ports, int num_init_elems, sc_lv<word_size> init[]>
inline void celram<W, A, word_size, mem_size, num_ports, num_init_elems, init>::mem_read()
{
  static uint64 prev_time[num_ports];
  static bool prevOutOfBounds[num_ports];

  for ( int i=0; i<num_ports; ++i )
  {
    if ( re[i] )
    {
      if ( prevOutOfBounds[i] && sc_time_stamp().value()-prev_time[i]>0 )
      {
        /*
         * Address has been out of range for some amount of time.
         */
        cout << "Address " << addr[i].read() << " is out of range for read operation." << endl;
      }

      prevOutOfBounds[i] = ( ((int)addr[i].read()) >= mem_size );

      if ( prevOutOfBounds[i] )
      {
        prev_time[i] = sc_time_stamp().value();
      }
      else
      {
        data_out[i] = (W) ram [ addr[i].read() ];
      }
    }
  }
}

/*
 * Synchronous memory
 */
template<class W, class A, int word_size, int mem_size, int num_ports, int num_init_elems, sc_lv<word_size> init[]>
SC_MODULE ( celsyncram )
{
  sc_in<bool> clk[num_ports];
  sc_in<A> addr[num_ports];

  sc_in<bool> re[num_ports];
  sc_in<bool> we[num_ports];

  sc_out<W> data_out[num_ports];
  sc_in<W> data_in[num_ports];

  void mem_write();
  void mem_read();

  void mem_init()
  {
     sc_lv<word_size> allxs (sc_logic_X);

     for (unsigned i=0; i<mem_size; i++)
     {
       if (i<num_init_elems)
         ram[i] = init[i];
       else
         ram[i] = allxs;
     }
  }

  sc_signal<sc_lv<word_size> > ram [mem_size];

  SC_CTOR( celsyncram )
  {
    mem_init();

    SC_METHOD ( mem_write );
    for ( int i=0; i<num_ports; ++i )
    {
      sensitive_pos << clk[i];
    }

    SC_METHOD ( mem_read );
    for ( int i=0; i<num_ports; ++i )
    {
      sensitive_pos << clk[i];
    }
  }
};

template<class W, class A, int word_size, int mem_size, int num_ports, int num_init_elems, sc_lv<word_size> init[]>
inline void celsyncram<W, A, word_size, mem_size, num_ports, num_init_elems, init>::mem_write()
{
  for ( int i=0; i<num_ports; ++i )
  {
    if ( we[i] )
    {
      if ( (int)addr[i].read() >= mem_size )
      {
        /*
         * Address is out of range
         */
        cout << "Address " << addr[i].read() << " is out of range for write operation." << endl;
      }
      else
      {
        ram [ addr[i].read() ] = (sc_lv<word_size>)data_in[i];
      }
    }
  }
}

template<class W, class A, int word_size, int mem_size, int num_ports, int num_init_elems, sc_lv<word_size> init[]>
inline void celsyncram<W, A, word_size, mem_size, num_ports, num_init_elems, init>::mem_read()
{
  static uint64 prev_time[num_ports];
  static bool prevOutOfBounds[num_ports];

  for ( int i=0; i<num_ports; ++i )
  {
    if ( re[i] )
    {
      if ( (int)addr[i].read() >= mem_size )
      {
        /*
         * Address is out of range
         */
        cout << "Address " << addr[i].read() << " is out of range for read operation." << endl;
      }
      else
      {
        data_out[i] = (W) ram [ addr[i].read() ];
      }
    }
  }
}

#endif
