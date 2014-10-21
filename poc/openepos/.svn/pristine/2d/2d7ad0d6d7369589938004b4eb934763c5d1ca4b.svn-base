/****************************************************************
*                                                               *
* Copyright (C) 1991-2005 Celoxica Ltd. All rights reserved.    *
*                                                               *
*****************************************************************
*                                                               *
* Project   :   DK                                              *
* Date      :   09 OCT 2001                                     *
* File      :   celoxica.v                                      *
* Author    :   Johan Ditmar (JD)                               *
* Contributors:                                                 *
*                                                               *
* Description                                                   *
*     Support library for Celoxica generated Verilog            *
*                                                               *
* Date         Version  Author  Reason for change               *
*                                                               *
* 09 OCT 2001    1.00    JD     Created                         *
* 29 OCT 2004    2.00    JD     Updated for DK3.1               *
* 22 JUN 2005    3.00    JD     Updated for DK4                 *
*                                                               *
****************************************************************/

module clockdiv (ckout, ckin, rst);

  parameter high1 = 1;
  parameter low = 1;
  parameter high2 = 0;

  output ckout;
  input ckin;
  input rst;

  reg CommonResetInv;
  wire CommonReset;

  reg [(high1+low+high2)/2-1:0] RisingChain;
  reg [(high1+low+high2)/2-1:0] FallingChain;

  // exemplar attribute CommonResetInv preserve_driver TRUE

  function [(high1+low+high2)/2-1:0] InitRisingReset;

    input [7:0] high1;
    input [7:0] low;
    input [7:0] high2;
    integer I;

    begin
      // synthesis loop_limit 512
      for (I=0; I<=high1+low+high2-1; I=I+1)
        begin
          if (I%2==0)
            begin
                if (!((high1<=I || high1<=(I+1)) && (high1+low)>I))
                  InitRisingReset[I/2] = 1'b1;
                else
                  InitRisingReset[I/2] = 1'b0;
              end
        end
    end
  endfunction

  function [(high1+low+high2)/2-1:0] InitFallingReset;

    input [7:0] high1;
    input [7:0] low;
    input [7:0] high2;
    integer I;

    begin
      // synthesis loop_limit 512
      for (I=0; I<=high1+low+high2-1; I=I+1)
        begin
            if (I%2==1)
              begin
                if (!((high1<=I || high1<=(I+1)) && (high1+low)>I))
                  InitFallingReset[I/2] = 1'b1;
                else
                  InitFallingReset[I/2] = 1'b0;
              end
        end
    end
  endfunction

  initial
    begin
        CommonResetInv <= 0;
    end

  always
    @(posedge CommonReset or posedge ckin)
    begin
      if (CommonReset==1'b1)
        RisingChain <= InitRisingReset(high1, low, high2);
      else
        RisingChain <= {RisingChain[0], RisingChain[(high1+low+high2)/2-1:1]};
    end

  always
    @(posedge CommonReset or negedge ckin)
    begin
      if (CommonReset==1'b1)
        FallingChain <= InitFallingReset(high1, low, high2);
      else
        FallingChain <= {FallingChain[0], FallingChain[(high1+low+high2)/2-1:1]};
    end

  always
    @(posedge rst or posedge ckin)
    begin
      if (rst==1)
        CommonResetInv <= 1'b0;
      else
        CommonResetInv <= 1'b1;
      end

  assign CommonReset = ~CommonResetInv;

  assign ckout = RisingChain[0] | FallingChain[(high1+low+high2)/2-1];

endmodule

module pulsegen (ckout, ckin, rst);

  parameter pulse = 7;
  parameter length = 4;

  output ckout;
  input ckin;
  input rst;

  wire [(length-1)/2:0] A;
  wire [(length-1)/2:0] B;
  wire [(length-1)/2:0] C;
  wire [(length-1)/2:0] D;

  reg [(length-1)/2:0] shiftA;
  reg [(length-1)/2:0] shiftB;
  reg [(length-1)/2:0] shiftC;
  reg [(length-1)/2:0] shiftD;

  reg resetffinv;
  wire resetff;

  // exemplar attribute resetffinv preserve_driver TRUE

  function [(length-1)/2:0] InitA;
    input [length-1:0] revpulse;
    integer I;

    begin
      // synthesis loop_limit 512
      for (I=(length-1)/4; I>=0; I=I-1)
        begin
          InitA[I*2] = revpulse[I*4] | revpulse[I*4+1];

          if (I*2+1 <= (length-1)/2)
        begin
          InitA[I*2+1] = 1'b0;
        end
        end
     end
  endfunction

  function [(length-1)/2:0] InitB;
    input [length-1:0] revpulse;
    integer I;

    begin
      // synthesis loop_limit 512
      for (I=(length-1)/4; I>=0; I=I-1)
        begin
      InitB[I*2] = revpulse[I*4];

          if (I*2+1 <= (length-1)/2)
        begin
          InitB[I*2+1] = revpulse[I*4+1];
        end
        end
    end
  endfunction

  function [(length-1)/2:0] InitC;
    input [length-1:0] revpulse;
    integer I;

    begin
      // synthesis loop_limit 512
      for (I=(length-1)/4; I>=0; I=I-1)
        begin
            InitC[I*2] = 1'b0;

            if (I*2+1 <= (length-1)/2)
              InitC[I*2+1] = revpulse[I*4+2] | revpulse[I*4+3];

        end
    end
  endfunction

  function [(length-1)/2:0] InitD;
    input [length-1:0] revpulse;
    integer I;

    begin
      // synthesis loop_limit 512
      for (I=(length-1)/4; I>=0; I=I-1)
        begin
          if (I*4+2 <= length-1)
        begin
          InitD[I*2] = revpulse[I*4+2];
        end

          if (I*4+2 > length-1)
        begin
          InitD[I*2] = 1'b0;
        end

      if (I*2+1 <= (length-1)/2)
        begin
          InitD[I*2+1] = revpulse[I*4+3];
        end
        end
    end
  endfunction

  assign A = InitA(pulse);
  assign B = InitB(pulse);
  assign C = InitC(pulse);
  assign D = InitD(pulse);

  // Synchronisation
  always
    @(negedge ckin or posedge rst)
      begin
       if (rst)
         resetffinv <= 1'b0;
       else
         resetffinv <= 1'b1;
      end

  assign resetff=~resetffinv;

  // shift A on positive flank
  always
    @(negedge ckin or posedge resetff)
      begin
        if (resetff)
          begin
            shiftA <= A;
        shiftC <= C;
      end
        else
          begin
            shiftA <= {shiftA[(length-1)/2-1:0], shiftA[(length-1)/2]};
            shiftC <= {shiftC[(length-1)/2-1:0], shiftC[(length-1)/2]};
          end
      end

  // shift B on negative flank
  always
    @(posedge ckin or posedge resetff)
      begin
        if (resetff)
          begin
            shiftB <= B;
            shiftD <= D;
      end
        else
          begin
            shiftB <= {shiftB[(length-1)/2-1:0], shiftB[(length-1)/2]};
            shiftD <= {shiftD[(length-1)/2-1:0], shiftD[(length-1)/2]};
          end
      end

  // Make output clock
  assign ckout = (shiftA[(length-1)/2] & shiftB[0]) | (shiftC[(length-1)/2] & shiftD[(length-1)/2]);

endmodule
