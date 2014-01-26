#!/usr/bin/perl
#
# usage: check_cites.pl <tex-file> <bib-file>
#
# A small script to find unresolved cites in a tex file.
#

sub read_bib_file {
	my $bib_file = shift;

	open BIB,"<$bib_file" || die "Can't open $bib_file\n";

	my %bib_lookup=();

	my $cite='';
	my %info=();
	while (<BIB>) {
		$line = $_;
		if (/\@([^{]+)\{(.*),/) {
			$cite = $2;
			$bib_lookup{$cite}->{'__type'}=$1;
			next;
		} 
		if ($cite ne '') {
			if ($line=~/^\s*([^\s]+)\s*=\s*(.*),\s*$/) {
				my $entry = lc $1;
				$bib_lookup{$cite}->{$entry} = $2;
			}
		}
	}

	close BIB;

	return %bib_lookup;
}

sub plain_asc {
	my $txt = shift;
	$txt=~s/,$//;
	$txt=~s/(\{|\}|\")//g;
	$txt=~s/\\"A/Ä/g;
	$txt=~s/\\"U/Ü/g;
	$txt=~s/\\"O/Ö/g;
	$txt=~s/\\"a/ä/g;
	$txt=~s/\\"u/ü/g;
	$txt=~s/\\"o/ö/g;
	$txt=~s/\\"s/ß/g;
	$txt=~s/\\texttrademark/(TM)/g;
	$txt=~s/\$\\mu\$/micro/g;
	return $txt;
}

open TEX,"<$ARGV[0]" || die "Can't open $ARGV[0]\n";

%bib_lookup = read_bib_file($ARGV[1]);

%bib_lookup_old = ();
if ($ARGV[2] ne '') {
	%bib_lookup_old = read_bib_file($ARGV[2]);
}

my %allready_seen = ();
while (<TEX>) {
	$line = $_;
	while ($line=~s/cite{([^}]+)}//) {
		foreach $cite (split(/,/,$1)) {
			$cite=~s/\s//g;
			next if $allready_seen{$cite};
			$allready_seen{$cite}=1;
			$ident ="\t";
			$ident.="\t" if (length($cite)<13);
			$ident.="\t" if (length($cite)<23);
			if ($bib_lookup{$cite}) {
				print "+ $cite $ident ".plain_asc($bib_lookup{$cite}->{'title'})."\n";
			} else {
				print "- $cite $ident -\n";
				if ($bib_lookup_old{$cite}) {
					print " => ".plain_asc($bib_lookup_old{$cite}->{'title'})."\n";
				}
			}
		}
	}
}

close TEX;
