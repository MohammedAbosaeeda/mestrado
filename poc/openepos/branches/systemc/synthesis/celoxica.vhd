-------------------------------------------------------------------
--                                                               --
-- Copyright (C) 1991-2005 Celoxica Ltd. All rights reserved.    --
--                                                               --
-------------------------------------------------------------------
--                                                               --
-- Project   :   DK                                              --
-- Date      :   22 MAR 2000                                     --
-- File      :   celoxica.vhd                                     --
-- Author    :   Andy Rushton (AR)                               --
-- Contributors:                                                 --
--     James Rowland (JR)                                        --
--     Carlos M. Rincón (CMR)                                    --
--     John Alexander (JA)                                       --
--     Johan Ditmar (JD)                                         --
--                                                               --
-- Description:                                                  --
--     Support library for Celoxica generated VHDL               --
--                                                               --
-- Date         Version  Author  Reason for change               --
--                                                               --
-- 22 MAR 2000    1.00    AR     Created                         --
-- 29 SEP 2000    1.01    JR     Added pulse sequence generator  --
--                               component                       --
-- 14 DEC 2000    1.02    CMR    Renamed package to avoid name   --
--                               conflicts in 3rd party VHDL     --
--                               tools                           --
-- 19 DEC 2000    1.03    JA     Modified clockdiv component to  --
--                               bring in line with the pulse    --
--                               generator component             --
-- 10 JAN 2001    1.04    JA     Further changes to clockdiv for --
--                               compatibility with the old      --
--                               version of clockdiv             --
-- 10 JAN 2002    2.00    JD     Updated for version 3.1         --
-- 2  MAR 2002    2.01    JD     Changed the reset statements to --
--                               make hardware run for Altera    --
-- 2  JUN 2004    3.00    JD     Updated for IEEE 1076.6         --
-- 29 OCT 2004    3.01    JD     Updated for DK3.1               --
-- 22 JUN 2005    4.00    JD     Updated for DK4                 --
--                                                               --
-------------------------------------------------------------------

LIBRARY ieee;
USE IEEE.std_logic_1164.ALL;
USE IEEE.numeric_std.ALL;
PACKAGE CeloxicaPackage IS

  -- Conditional Functions
  FUNCTION cond (c : std_logic; t, f : std_logic) RETURN std_logic;
  FUNCTION cond (c : std_logic; t, f : std_logic_vector) RETURN std_logic_vector;
  FUNCTION cond (c : std_logic; t, f : unsigned) RETURN unsigned;

  -- Synthesizable Arithmetic Functions
  FUNCTION div (x, y : unsigned) RETURN unsigned;
  FUNCTION div (x, y : signed) RETURN signed;
  FUNCTION modulus (x, y : unsigned) RETURN unsigned;
  FUNCTION modulus (x, y : signed) RETURN signed;

  -- Conversion Functions
  FUNCTION to_signed (x : std_logic) RETURN signed;
  FUNCTION to_signed (x : unsigned) RETURN signed;
  FUNCTION to_signed (x : std_logic_vector) RETURN signed;
  FUNCTION to_unsigned (x : std_logic) RETURN unsigned;
  FUNCTION to_unsigned (x : signed) RETURN unsigned;
  FUNCTION to_unsigned (x : std_logic_vector) RETURN unsigned;
  FUNCTION to_stdlogic (x : signed) RETURN std_logic;
  FUNCTION to_stdlogic (x : unsigned) RETURN std_logic;
  FUNCTION to_stdlogic (x : std_logic_vector) RETURN std_logic;
  FUNCTION to_stdlogic (x : boolean) RETURN std_logic;
  FUNCTION to_stdlogicvector (x : std_logic) RETURN std_logic_vector;
  FUNCTION to_integer (x : std_logic) RETURN integer;
  FUNCTION to_integer (x : std_logic_vector) RETURN integer;
  FUNCTION to_boolean (x : signed) RETURN boolean;
  FUNCTION to_boolean (x : unsigned) RETURN boolean;
  FUNCTION to_boolean (x : std_logic) RETURN boolean;

  COMPONENT clockdiv
      GENERIC (high1 : integer;
             low   : integer;
             high2 : integer);
      PORT (ckout : OUT std_logic;                   -- divided clock output
            ckin  : IN std_logic;                    -- original clock input
            rst   : IN std_logic := '0');            -- asynchronous reset (active high)
  END COMPONENT;

  COMPONENT pulsegen
    GENERIC(pulse : integer;
            length : integer);
      PORT (ckout : OUT std_logic;                   -- pulse sequence generated
            ckin  : IN std_logic;                    -- original clock input
            rst   : IN std_logic);                   -- asynchronous reset (active high)
  END COMPONENT;

END;

PACKAGE body CeloxicaPackage IS

  -- internal division procedure performs unsigned division with remainder
  -- compensates for the lack of synthesizable division operator in numeric_std
  PROCEDURE divmod (xnum, xdenom: unsigned; xquot, xremain: OUT unsigned) is
      VARIABLE denominator : unsigned(xdenom'LENGTH-1 DOWNTO 0);
      VARIABLE numerator : unsigned(xnum'LENGTH-1 DOWNTO 0);
      VARIABLE remainder : unsigned(xnum'LENGTH+xdenom'LENGTH-2 DOWNTO 0);
      VARIABLE quotient : unsigned(xnum'LENGTH-1 DOWNTO 0);

    BEGIN
        denominator := xdenom;
      numerator := xnum;

        -- synopsys synthesis_off
        ASSERT denominator /= to_unsigned(0,denominator'LENGTH) REPORT "divide by zero" SEVERITY ERROR;
      -- synopsys synthesis_on
        remainder := RESIZE(numerator, remainder'length);
        quotient := TO_UNSIGNED(0, quotient'length);

        FOR i IN quotient'range LOOP
          IF remainder(i+denominator'LEFT DOWNTO i) >= denominator THEN
              remainder(i+denominator'LEFT DOWNTO i) := remainder(i+denominator'LEFT DOWNTO i) - denominator;
              quotient(i) := '1';
          END IF;
        END LOOP;

      xquot := RESIZE(quotient, xquot'length);
        xremain := RESIZE(remainder, xremain'length);

    END divmod;

    FUNCTION div (x, y : unsigned) RETURN unsigned IS
        VARIABLE quotient : unsigned(x'LENGTH-1 DOWNTO 0);
        VARIABLE remainder : unsigned(x'LENGTH-1 DOWNTO 0);

      BEGIN
          -- synopsys synthesis_off
          ASSERT x'LENGTH = y'LENGTH REPORT "arguments must be the same size";
        -- synopsys synthesis_on
          divmod(x, y, quotient, remainder);
          RETURN quotient;
      END;

    -- signed divide is defined in terms of unsigned divide with negations
    FUNCTION div (x, y : signed) RETURN signed IS
        VARIABLE quotient : unsigned(x'LENGTH-1 DOWNTO 0);
        VARIABLE remainder : unsigned(x'LENGTH-1 DOWNTO 0);

      BEGIN
          -- synopsys synthesis_off
          ASSERT x'LENGTH = y'LENGTH report "arguments must be the same size";
        ASSERT x'LENGTH >= 1 and y'LENGTH >= 1 report "arguments must be at least one bit";
        -- synopsys synthesis_on

          divmod(unsigned(signed'(abs x)), unsigned(signed'(abs y)), quotient, remainder);
          -- result is negated if the signs are different
          IF to_boolean(x(x'LEFT)) /= to_boolean(y(y'LEFT)) THEN
          RETURN -signed(quotient);
          ELSE
            RETURN signed(quotient);
          END IF;
      END;

    -- the C % operator is equivalent to the VHDL rem operator
    FUNCTION modulus (x, y : unsigned) RETURN unsigned is
        VARIABLE quotient : unsigned(x'length-1 downto 0);
        VARIABLE remainder : unsigned(x'length-1 downto 0);

      BEGIN
          -- synopsys synthesis_off
          assert x'length = y'length report "arguments must be the same size";
        -- synopsys synthesis_on
        divmod(x, y, quotient, remainder);
          RETURN remainder;
      END;

    FUNCTION modulus (x, y : signed) RETURN signed is
        VARIABLE quotient : unsigned(x'LENGTH-1 downto 0);
        VARIABLE remainder : unsigned(x'LENGTH-1 downto 0);

      BEGIN
          -- synopsys synthesis_off
          ASSERT x'LENGTH = y'LENGTH report "arguments must be the same size";
          ASSERT x'LENGTH >= 1 and y'LENGTh >= 1 report "arguments must be at least one bit";
        -- synopsys synthesis_on

        divmod(unsigned(signed'(abs x)), unsigned(signed'(abs y)), quotient, remainder);
        -- result is negated if the numerator was negative
        IF to_boolean(x(x'left)) THEN
          RETURN -signed(remainder);
        ELSE
          RETURN signed(remainder);
        END IF;
      END;

    FUNCTION cond (c : std_logic; t, f : std_logic) RETURN std_logic IS
    BEGIN
        IF c = '1' or c = 'H' THEN
          RETURN t;
        ELSIF c = '0' or c = 'L' THEN
          RETURN f;
        ELSE
          RETURN 'X';
        END IF;
    END;

    FUNCTION cond (c : std_logic; t, f : std_logic_vector) RETURN std_logic_vector IS
    BEGIN
        IF c = '1' or c = 'H' THEN
          RETURN t;
        ELSIF c = '0' or c = 'L' THEN
          RETURN f;
        ELSE
          RETURN (t'RANGE => 'X');
        END IF;
    END;

    FUNCTION cond (c : std_logic; t, f : unsigned) RETURN unsigned IS
    BEGIN
        -- synopsys synthesis_off
        ASSERT t'LENGTH = f'LENGTH REPORT "arguments must be the same size";
      -- synopsys synthesis_on
      IF c = '1' or c = 'H' THEN
        RETURN t;
      ELSIF c = '0' or c = 'L' THEN
        RETURN f;
      ELSE
        RETURN (t'RANGE => 'X');
      END IF;
    END;

    FUNCTION to_signed (x : std_logic) RETURN signed IS
    BEGIN
      return SIGNED(to_stdlogicvector(x));
    END;

    FUNCTION to_signed (x : unsigned) RETURN signed IS
    BEGIN
      return SIGNED(x);
    END;

    FUNCTION to_signed (x : std_logic_vector) RETURN signed IS
    BEGIN
      return SIGNED(x);
    END;

    FUNCTION to_unsigned (x : std_logic) RETURN unsigned IS
    BEGIN
      return UNSIGNED(to_stdlogicvector(x));
    END;

    FUNCTION to_unsigned (x : signed) RETURN unsigned IS
    BEGIN
      return UNSIGNED(x);
    END;

    FUNCTION to_unsigned (x : std_logic_vector) RETURN unsigned IS
    BEGIN
      return UNSIGNED(x);
    END;

    FUNCTION to_stdlogic (x : signed) RETURN std_logic IS
      VARIABLE result : std_logic_vector(x'LENGTH-1 DOWNTO 0);
    BEGIN
      result := STD_LOGIC_VECTOR(x);
      RETURN result(0);
    END;

    FUNCTION test (x, y : signed) RETURN signed IS
    BEGIN
      RETURN RESIZE(x*y, 4);
    END;

    FUNCTION to_stdlogic (x : unsigned) RETURN std_logic IS
      VARIABLE result : std_logic_vector(x'LENGTH-1 DOWNTO 0);
    BEGIN
      result := STD_LOGIC_VECTOR(x);
      RETURN result(0);
    END;

    FUNCTION to_stdlogic (x : std_logic_vector) RETURN std_logic IS
    BEGIN
      RETURN x(0);
    END;

    FUNCTION to_stdlogic (x : boolean) RETURN std_logic IS
    BEGIN
        CASE x IS
          WHEN true => RETURN '1';
          WHEN false => RETURN '0';
      END CASE;
    END;

    FUNCTION to_stdlogicvector (x : std_logic) RETURN std_logic_vector IS
      VARIABLE result : std_logic_vector(0 DOWNTO 0);
    BEGIN
      result(0) := x;
      RETURN result;
    END;

    FUNCTION to_integer(x : std_logic_vector) RETURN integer IS
       VARIABLE result: integer;
    BEGIN
    --synopsys synthesis_off
       ASSERT x'length <= 31
       REPORT "x is too large in CONV_INTEGER"
       SEVERITY FAILURE;
    --synopsys synthesis_on
       result := 0;
       FOR i IN x'reverse_range LOOP
           IF x(i) = '1' THEN
               result := result + 2 ** i;
           END IF;
       END LOOP;
       RETURN result;
    END;

    FUNCTION to_integer (x : std_logic) RETURN integer IS
    BEGIN
        IF (x='1') THEN
            RETURN (1);
        ELSE
            RETURN (0);
        END IF;
    END;

    FUNCTION to_boolean (x : signed) RETURN boolean IS
    BEGIN
        RETURN to_boolean(to_stdlogic(x));
    END;

    FUNCTION to_boolean (x : unsigned) RETURN boolean IS
    BEGIN
        RETURN to_boolean(to_stdlogic(x));
    END;

    FUNCTION to_boolean (x : std_logic) RETURN boolean IS
    BEGIN
      CASE x IS
          WHEN '0' | 'L' => RETURN false;
          WHEN others => RETURN true;
      END CASE;
    END;

END;

--- Clock divider component ----

LIBRARY IEEE;
USE IEEE.std_logic_1164.ALL;
ENTITY clockdiv IS
    -- high1+low+high2 must equal an even number...
    -- high2 can equal zero...
    GENERIC (high1 : integer := 1;
             low   : integer := 1;
             high2 : integer := 0);
    PORT (ckout : out std_logic;
          ckin : in std_logic;
          rst : in std_logic := '0');
END;

ARCHITECTURE behaviour OF clockdiv IS

  -- a common reset is required to ensure the two flip flop chain are synchronised...
  SIGNAL CommonReset_inv: std_logic := '0';
  SIGNAL CommonReset: std_logic;

  -- Used to store the reset values (will be optimised away hopefully)...
  SIGNAL RisingResetChain: std_logic_vector(((high1+low+high2)/2)-1 downto 0);
  SIGNAL FallingResetChain: std_logic_vector(((high1+low+high2)/2)-1 downto 0);

  -- These will become the flip flops...
  SIGNAL RisingChain: std_logic_vector(((high1+low+high2)/2)-1 DOWNTO 0);
  SIGNAL FallingChain: std_logic_vector(((high1+low+high2)/2)-1 DOWNTO 0);

  ATTRIBUTE preserve_driver : boolean;
  ATTRIBUTE preserve_driver OF CommonReset_inv : signal IS true;

BEGIN

  -- If the provided settings describe a divided clock build the required logic...
  DividedClock: IF ( not(high1=1 and low=1 and high2=0) ) GENERATE

    -- Set the clock to the generated signal
    -- The derived clock is generated by the or operations of the first
    --   registers in each shift chain...
    ckout <= RisingChain(0) or FallingChain(((high1+low+high2)/2)-1);

    -- Ensure the two divider chains are synchronised by building a common reset...
    SyncReset: PROCESS (rst, ckin) BEGIN
      IF (rst='1') THEN  -- if the reset signal is active
        CommonReset_inv <= '0';  -- the common reset is active
      ELSIF (rising_edge(ckin)) THEN  -- if the clock source is rising
        CommonReset_inv <= '1';   -- clear the reset
      END IF;
    END PROCESS SyncReset;

    CommonReset <= not(CommonReset_inv);

    -- Build the reset states for the shift registers...
    reset_states: for I in 0 to (high1+low+high2-1) GENERATE

      -- The reset states for the rising edge triggered flip flops,
      -- calculated on even numbers ((I/2)*2)=I will test for an even number...
      RisingEdgeReset: IF ( (I mod 2)=0 ) GENERATE
        HighGenerationRE: IF not( ((high1<=I)or(high1<=(I+1))) and ((high1+low)>I) ) GENERATE
           RisingResetChain(I/2) <= '1';
        END GENERATE HighGenerationRE;
        LowGenerationRE: IF ( ((high1<=I)or(high1<=(I+1))) and ((high1+low)>I) ) GENERATE
           RisingResetChain(I/2) <= '0';
        END GENERATE LowGenerationRE;
      END GENERATE RisingEdgeReset;

      -- the reset states for the falling edge chain,
      -- calculated on odd numbers (((I/2)*2)+1)=I will test for even numbers...
      FallingEdgeReset: IF ( (I mod 2)=1 ) GENERATE
        HighGenerationFE: IF not( ((high1<=I)or(high1<=(I+1))) and ((high1+low)>I) ) GENERATE
          FallingResetChain(I/2) <= '1';
        END GENERATE HighGenerationFE;
        LowGenerationFE: IF ( ((high1<=I)or(high1<=(I+1))) and ((high1+low)>I) ) GENERATE
          FallingResetChain(I/2) <= '0';
        END GENERATE LowGenerationFE;
      END GENERATE FallingEdgeReset;

    END GENERATE reset_states;

    -- Build the rising edge triggered flip flop chain...
    RisingEdgeChain: PROCESS(CommonReset, ckin, RisingResetChain) BEGIN
      IF (CommonReset='1') THEN
        RisingChain <= RisingResetChain;
      ELSIF (ckin'event and ckin='1') THEN
        RisingChain <= RisingChain(0) & RisingChain(RisingChain'LENGTH-1 DOWNTO 1);
      END IF;
    END PROCESS RisingEdgeChain;

    -- Build the falling edge triggered flip flop chain...
    FallingEdgeChain: process(CommonReset, ckin, FallingResetChain) begin
      IF (CommonReset='1') THEN
        FallingChain <= FallingResetChain;
      ELSIF (ckin'event and ckin='0') THEN
        FallingChain <= FallingChain(0) & FallingChain(FallingChain'LENGTH-1 DOWNTO 1);
      END IF;
    END PROCESS FallingEdgeChain;

  END GENERATE DividedClock;

  -- If the clock is not divided build no logic at all...
  UnDividedClock: IF ( high1=1 and low=1 and high2=0 ) GENERATE
    ckout <= ckin;
  END GENERATE UnDividedClock;

END behaviour;


---- Pulse sequence generator component ----

LIBRARY IEEE;
USE IEEE.std_logic_1164.ALL;
USE IEEE.numeric_std.ALL;
ENTITY pulsegen IS
  GENERIC (pulse : integer;
          length : integer);
  PORT (ckout : OUT std_logic;
        ckin   : IN std_logic;
        rst : IN std_logic);
END;

ARCHITECTURE only OF pulsegen IS

  CONSTANT revpulse : std_logic_vector(length-1 DOWNTO 0) := STD_LOGIC_VECTOR(TO_UNSIGNED(pulse, length));

  SIGNAL A : std_logic_vector(revpulse'HIGH/2 DOWNTO 0);
  SIGNAL B : std_logic_vector(revpulse'HIGH/2 DOWNTO 0);
  SIGNAL C : std_logic_vector(revpulse'HIGH/2 DOWNTO 0);
  SIGNAL D : std_logic_vector(revpulse'HIGH/2 DOWNTO 0);

  SIGNAL shiftA : std_logic_vector(A'RANGE);
  SIGNAL shiftB : std_logic_vector(B'RANGE);
  SIGNAL shiftC : std_logic_vector(C'RANGE);
  SIGNAL shiftD : std_logic_vector(D'RANGE);

  SIGNAL resetff, resetff_inv : std_logic;

  ATTRIBUTE preserve_driver : boolean;
  ATTRIBUTE preserve_driver OF resetff_inv : signal IS true;

BEGIN

  test: FOR I IN revpulse'HIGH/4 DOWNTO 0 GENERATE

    A(I*2) <= revpulse(I*4) or revpulse(I*4+1);
    B(I*2) <= revpulse(I*4);
    C(I*2) <= '0';

    test3 : IF I*4+2 <= revpulse'HIGH GENERATE
      D(I*2) <= revpulse(I*4+2);
    END GENERATE test3;

    test4 : IF I*4+2 > revpulse'HIGH GENERATE
      D(I*2) <= '0';
    END GENERATE test4;

    test2 : IF I*2+1 <= A'HIGH GENERATE
      A(I*2+1) <= '0';
      B(I*2+1) <= revpulse(I*4+1);
      C(I*2+1) <= revpulse(I*4+2) or revpulse(I*4+3);
      D(I*2+1) <= revpulse(I*4+3);
    END GENERATE test2;

  END GENERATE test;

  ckout <= (shiftA(shiftA'HIGH) and shiftB(shiftB'LOW)) or (shiftC(shiftC'HIGH) and shiftD(shiftD'HIGH));

  sr1: PROCESS(ckin, resetff)
  BEGIN
    if (resetff = '1') THEN
      shiftA <= A;
      shiftC <= C;
    ELSIF falling_edge(ckin) THEN
      shiftA <= shiftA(shiftA'HIGH-1 downto shiftA'LOW) & shiftA(shiftA'HIGH);
      shiftC <= shiftC(shiftC'HIGH-1 downto shiftC'LOW) & shiftC(shiftC'HIGH);
    END IF;
  END PROCESS;

  sr2: PROCESS(ckin, resetff)
  BEGIN
    IF (resetff = '1') THEN
      shiftB <= B;
      shiftD <= D;
    ELSIF rising_edge(ckin) THEN
      shiftB <= shiftB(shiftB'HIGH-1 downto shiftB'LOW) & shiftB(shiftB'HIGH);
      shiftD <= shiftD(shiftD'HIGH-1 downto shiftD'LOW) & shiftD(shiftD'HIGH);
    END IF;
  END PROCESS;

  start: PROCESS(ckin, rst)
  BEGIN
    IF (rst = '1') THEN
      resetff_inv <= '0';
    ELSIF (falling_edge(ckin)) THEN
      resetff_inv <= '1';
    END IF;
  END PROCESS;

  resetff <= not(resetff_inv);

END only;
