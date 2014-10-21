#!/usr/bin/perl

@registers = ();

print "Usage: createAVRClass <headerfile> <javafile> <package> <class>\n";
print "Generating Java class from AVR header file...";

open HEADER, "$ARGV[0]" || die "Couldn't open header file.\n";
open JSOURCE, ">$ARGV[1]" || die "Couldn't open java source file.\n";

print JSOURCE "/* AUTO GENERATED FILE */\n\n";
print JSOURCE "package $ARGV[2];\n\n";
print JSOURCE "import keso.core.*;\n\n";
print JSOURCE "public class $ARGV[3] implements MemoryMappedObject {\n";

# Generic register addresses
$registers[0x3d] = "SPL";
$registers[0x3e] = "SPH";
$registers[0x3f] = "SREG";

$registers[0x1c] = "EECR";
$registers[0x1d] = "EEDR";
$registers[0x1e] = "EEAL";
$registers[0x1f] = "EEAH";
$registers[0x1c] = "EECR";
$registers[0x1c] = "EECR";
$registers[0x1c] = "EECR";


while (<HEADER>) {
   
    # Register address (only 8-bit registers)
    if (/^#define(\s*)(\w+)(\s*)_SFR_(IO|MEM)(8)\((\w+)\).*/) {
    
        print JSOURCE "\tprivate static final short __" . $2 . " = (short) " . hex($6) . ";\n";
        $registers[hex($6)] = $2;    

    } elsif (/^#define(\s*)(\w+)(\s+)(\d+)(\s*)((\/\*){0,1}).*((\/\*){0,1})/) {    # bit definition 
        print JSOURCE "\tpublic static final byte " . $2 . " = (byte) " . $4 . ";\n";
    
    } elsif (/\/\*.*\*\//) { # comment
        
        print JSOURCE "\n\t" . $& . "\n";
    
    }
    
}

print JSOURCE "\t/* Memory maped IO registers */\n\n"; 

for ($regaddr = 0; $regaddr < @registers; ++$regaddr) {

    print JSOURCE "\t/* Address offset $regaddr */\n";

    if (defined($registers[$regaddr])) { 
        print JSOURCE "\tpublic MT_U8 $registers[$regaddr];\n\n";
    } else {
        print JSOURCE "\tprivate MT_U8 REGISTER_$regaddr;\n\n";
    }
}

print JSOURCE "\tpublic final static $ARGV[3] registers = ($ARGV[3]) MemoryService.mapStaticDeviceMemory(0x20, \"$ARGV[2].$ARGV[3]\");\n\n"; 

print JSOURCE "}\n";



 
close JSOURCE;
close HEADER;

print " done\n";
