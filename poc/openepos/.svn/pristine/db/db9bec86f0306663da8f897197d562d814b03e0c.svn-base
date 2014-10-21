#!/usr/bin/perl

$plain = 0;
if ($ARGV[0] eq "-plain") {
	shift @ARGV;
	$plain = 1;
}

$objdump = $ARGV[0];
$executable = $ARGV[1];
$output = $ARGV[2];
$output = "exception_table.inc" unless $output;

%bc2addr = ();
%catch_blk = ();

sub read_exp_syms {
	open(INFILE, "$objdump --syms $executable | grep .text |") || die "Can`t read $executable.\n";

	while (<INFILE>) {
		($addr, $g, $sec, $size, $name) = split(/\s+/);
		next if ($g ne "g");
		next if ($sec ne ".text");

		if ($name=~/^E(c.+)_B(\d+)\$(\d+)_(\d+)_(\d+)/) {
			print "$addr: $1 $2 $3 - $4 type $5\n";
			$catch_blk{$1} = [] if $catch_blk{$1} eq '';
			unshift @{$catch_blk{$1}}, "$addr,$1,$2,$3,$4,$5";
			next;
		}
		if ($name=~/^[E|B]T(c.+)\$(\d+)/) {
			#print "$addr: $1 $2\n";
			$bc2addr{$1} = [] if $bc2addr{$1} eq '';
			unshift @{$bc2addr{$1}}, "$addr,$1,$2";
			next;
		}
	}

	close INFILE;
}

sub find_gt {
	my $bc_addr = shift;
	my $bc_addr_lst = shift;

	my $r_bc=-1;
	my $r_addr=-1;
	foreach $bc_t (@{$bc_addr_lst}) {
		my ($addr, $hc, $bc) = split(',',$bc_t);
		#print "$addr $bc\n";
		if ($bc>=$bc_addr) {
			if ($r_addr==-1 || $bc<$r_bc) {
				$r_bc = $bc;
				$r_addr = $addr;
			}
		}
	}

	return $r_addr;
}

if (!$plain) {
	read_exp_syms();
}

open OUTFILE, ">$output" || die "can't open \n";

print OUTFILE "typedef struct {\n\tvoid* start;\n\tvoid* end;\n\tvoid* handler;\n\tclass_id_t cls_id;\n} exp_entry_t;\n";

foreach $c (keys %catch_blk) {
	print "\n$c:\n";

	$handler = $catch_blk{$c};
	$bc_addr_lst = $bc2addr{$c};

	foreach $h (@{$handler}) {
		#print "$h\n";
		($haddr,$hc,$bb,$start,$end,$type) = split(',',$h);

		$start_addr = find_gt($start, $bc_addr_lst);
		$end_addr = find_gt($end, $bc_addr_lst);

		#print "$haddr $start:$start_addr - $end:$end_addr $type\n";

		print OUTFILE "/* $c $bb 0x$haddr ($start - $end) cls:$type */\n";

		my $entry = "$start_addr,$end_addr,$haddr,$type";
		$all{$entry} = hex $start_addr;
	}
}

$i=0;
print OUTFILE "\nexp_entry_t __const__ exception_table[] = {\n";
foreach $entry (sort {$all{$a} <=> $all{$b}} keys %all) {
	my ($s,$e,$h,$t) = split(',',$entry);
	print OUTFILE "\t{.start=(void*)0x".$s.", .end=(void*)0x".$e.", .handler=(void*)0x".$h.", .cls_id=".$t."},\n";
	$i++;
}

print OUTFILE "};\n";
print OUTFILE "#define EXP_TABLE_SIZE $i\n";
if ($i<256) {
	print OUTFILE "typedef unsigned char exp_ndx_t;\n";
} elsif ($i<0xffff) {
	print OUTFILE "typedef unsigned short exp_ndx_t;\n";
} else {
	print OUTFILE "typedef unsigned long exp_ndx_t;\n";
}

close OUTFILE;
