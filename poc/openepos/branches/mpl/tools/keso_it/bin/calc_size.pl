#!/usr/bin/perl    

open(INFILE, "objdump --syms epos.elf |") || die "Can`t read.\n";

$tsize = 0;
while (<INFILE>) {
	($addr, $g, $F, $sec, $size, $name) = split(/\s+/);
	next if ($g ne "g");
	next if ($sec ne ".text");

	#next if ($name!~/extC/);	

	#next if ($name=~/extC/);
	#next if ($name!~/^c/);

	#next if ($name=~/extC/);
	#next if ($name!~/keso_/);
	
	
	next if ($name=~/extC/);
	next if ($name=~/^c/);
	next if ($name=~/keso_/);
	
	$size = "0x".$size;
	$size = hex $size;
	$tsize += $size;

	print "$name\t$size byte\n";
}

print "total: $tsize byte\n";
