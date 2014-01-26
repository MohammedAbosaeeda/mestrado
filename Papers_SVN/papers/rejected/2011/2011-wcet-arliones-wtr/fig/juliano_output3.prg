arliones@hefe:~/$ boundt_arm7 -assert assert.txt -arm7 adpcm main
Warning:adpcm:main.c:main:77-:Dynamic call.
Warning:adpcm:main.c:main:84-:Dynamic call.
Loop_Bound:adpcm:g72x.c:g72x_init_state:106-109:2
Loop_Bound:adpcm:g72x.c:g72x_init_state:111-113:6
Loop_Bound:adpcm:g711.c:linear2ulaw@222-=>search:86-87:8
Loop_Bound:adpcm:g711.c:linear2alaw@128-=>search:86-87:8
Loop_Bound:adpcm:g72x.c:fmult@76-=>quan:54-55:15
/home/arliones/bound-t/bin//run_oc: line 27: 24021 Killed                  oc
Fault:adpcm:g72x.c:update:276-454:Calculator.Formulas.Check_Echo:Calculator did
                                                   not give the expected echo line.
Fault:adpcm:g72x.c:update:276-454:Calculator.Formulas.Check_Echo:Response line 2:''
Exception:adpcm:g72x.c:update:276-454:Bound_T.Main:Exception name:
                                             CALCULATOR.CALCULATOR_ERROR
Message: calculator-formulas.adb:3789