#!/usr/bin/perl
#
# usage: cat.pl <bib-file> <category> 
#
# A small script to find cites by category.
#

sub read_bib_file {
	my $bib_file = shift;

	open BIB,"<$bib_file" || die "Can't open $bib_file\n";

	my %bib_lookup=();

	my $cite='';
	my %info=();
	while (<BIB>) {
		$line = $_;
		next if /^\%/;
		if (/\@([^{]+)\{(.*),/) {
			$cite = $2;
			$bib_lookup{$cite}->{'__type'}=$1;
			next;
		} 
		if ($cite ne '') {
			if ($line=~/^\s*([^\s]+)\s*=\s*(.*)\s*$/) {
				$entry = lc $1;
				$bib_lookup{$cite}->{$entry} = $2;
			} elsif ($entry ne '') {
				$text = $line;
				chop $text;
				$text=~s/^\s*/ /;
				$bib_lookup{$cite}->{$entry} .= $text;
			}
		}
	}

	close BIB;

	return %bib_lookup;
}

sub plain_asc {
	my $txt = shift;
	$txt=~s/\\"u/ü/g;
	$txt=~s/\\"a/ä/g;
	$txt=~s/\\"o/ö/g;
	$txt=~s/\\"s/ß/g;
	$txt=~s/\\texttrademark/(TM)/g;
	$txt=~s/(\{|\}|\")//g;
	$txt=~s/\$\\mu\$/micro/g;
	return $txt;
}

die "usage: cat.pl [<bib-file>] <category>\n" unless $ARGV[0] ne '';
if ($ARGV[1] ne '') {
	$cat = $ARGV[1];
	%bib_lookup = read_bib_file($ARGV[0]);
} else {
	$cat = $ARGV[0];
	%bib_lookup = read_bib_file("bib/all.bib") if -f "bib/all.bib";
	%bib_lookup = read_bib_file("all.bib") if -f "all.bib";
}

%dup_titles = ();

print "% Category: ".$cat."\n"; 
foreach $e (keys %bib_lookup) {
	my $entry = $bib_lookup{$e};
	my @categories = split(/,/,$entry->{'category'});

	foreach $category (@categories) {
		$category=~s/(\s|\{|\}|\")//g;
		if ($category=~/\Q$cat/) {
			if ($entry->{'nc'} ne '') {
				printf "%4d ",$entry->{'nc'};
			} else {
				print "     ";
			}
			print $e;
			for ($i=0;$i<(27-length $e);$i++) { print " "; }
			print "$category";
			for ($i=0;$i<(9-length $category);$i++) { print " "; }
			print "\t";
			print plain_asc($entry->{'title'})."\n"; 
			if ($dup_titles{plain_asc($entry->{'title'})} ne '') {
				print "warning: duplicated title! allready seen for $e\n";
			} else {
				$dup_titles{plain_asc($entry->{'title'})} = $e;
			}
			last;
		}
	}
}
