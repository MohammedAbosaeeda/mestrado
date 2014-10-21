/*
 * nco.h
 *
 *  Created on: Jun 21, 2011
 *      Author: tiago
 */

#ifndef NCO_H_
#define NCO_H_

#include <cmath>
#include "../common/cplxopsphasor.sc.h"

/*
 * Numerically Controlled Oscillator (NCO)
 */
class NCO {
  double	d_phase;
  double	d_phase_inc;

public:
  NCO () : d_phase (0), d_phase_inc (0) {}

  ~NCO () {}

  // radians
  void set_phase (double angle) {
    d_phase = angle;
  }

  void adjust_phase (double delta_phase) {
    d_phase += delta_phase;
  }

  // angle_rate is in radians / step
  void set_freq (double angle_rate){
    d_phase_inc = angle_rate;
  }

  // angle_rate is a delta in radians / step
  void adjust_freq (double delta_angle_rate)
  {
    d_phase_inc += delta_angle_rate;
  }

  // increment current phase angle

  void step ()
  {
    d_phase += d_phase_inc;
  }

  void step (int n)
  {
    d_phase += d_phase_inc * n;
  }

  // units are radians / step
  double get_phase () const { return d_phase; }
  double get_freq () const { return d_phase_inc; }

  // compute sin and cos for current phase angle
  void sincos (double *sinx, double *cosx) const
  {
    *sinx = std::sin(d_phase);
    *cosx = std::cos(d_phase);
  }

  // compute cos and sin for a block of phase angles
  void sincos (complex<int> *output, int noutput_items, double ampl=1000.0)
  {
    for (int i = 0; i < noutput_items; i++){
      output[i]   = complex<int>((int)(std::cos(d_phase) * ampl), (int)(std::sin(d_phase) * ampl));
      step ();
    }
  }

  // compute sin for a block of phase angles
  void sin (double *output, int noutput_items, double ampl=1.0)
  {
    for (int i = 0; i < noutput_items; i++){
      output[i] = std::sin(d_phase) * ampl;
      step ();
    }
  }

  // compute cos for a block of phase angles
  void cos (double *output, int noutput_items, double ampl=1.0)
  {
    for (int i = 0; i < noutput_items; i++){
      output[i] = std::cos(d_phase) * ampl;
      step ();
    }
  }


  // compute sin for a block of phase angles
  void sin (int *output, int noutput_items, double ampl=1000.0)
  {
    for (int i = 0; i < noutput_items; i++){
      output[i] = (int)(std::sin(d_phase) * ampl);
      step ();
    }
  }

  // compute cos for a block of phase angles
  void cos (int *output, int noutput_items, double ampl=1000.0)
  {
    for (int i = 0; i < noutput_items; i++){
      output[i] = (int)(std::cos(d_phase) * ampl);
      step ();
    }
  }

  // compute cos or sin for current phase angle
  double cos () const { return std::cos (d_phase); }
  double sin () const { return std::sin (d_phase); }
};

#endif /* NCO_H_ */
