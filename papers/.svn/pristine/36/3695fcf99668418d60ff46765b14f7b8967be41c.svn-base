#!/usr/bin/perl
#
# usage: check_cites.pl [-all] [-url2note] [-nopages] [-dwarf] [-shrink] <tex-file> <bib-file>
#
# A small script to export used cites.
#
# -all: export all cites 
#
# -url2note: * add a note with the url
#
# -shrink:  * condensed form of publisher, booktitels, notes and journals
#           * remove location and editors
#
# -dwarf:   * short authors and limited list of authors (max. 2 authors)
#           (eigentlich kein korrektes Zitat mehr!)
#
# -nopages: * remove page information
#
# -nomonth: * remove month information 
#


$dwarf_string = '~et al.';

%skip = (
	'__type' => 1,
	'category' => 1,
	'nc' => 1,
	'bibsource' => 1,
	'bibdate' => 1,
	'doi' => 1,
	'xurl' => 1,
	'_crossref' => 1,
	'ee' => 1,
	'abstract' => 1,
	'isbn' => 1,
	'issn' => 1,
);

%rewrite = (
	'publisher' => 1,
	'note' => 1,
	'booktitle' => 1,
	'journal' => 1,
	'howpublished' => 1,
	'school' => 1,
	'institution' => 1,
	'series' => 1,
);

%strings = ();
sub read_bib_file {
	my $bib_file = shift;

	open BIB,"<$bib_file" || die "Can't open $bib_file\n";

	my %bib_lookup=();

	my $cite='';
	my $entry='';
	my %info=();
	while (<BIB>) {
		$line = $_;

		next if /^\%/;

		if (/\@string{([\w-]+)\s*=\s*(.+)}/) {
			#print "$line";
			$strings{$1} = $2 if $strings{$1} eq '';
			next;
		}

		if (/\@([^{]+)\{(.*),(.*)/) {
			$cite = $2;
			$entry='';
			$bib_lookup{$cite}->{'__type'}=$1;
			next if ($3=~/^\s+$/);
			$line = $3;
			$line =~ s/\}$//;
		} 

		if ($cite ne '') {
			if ($line=~/^\s*([^\s]+)\s*=\s*(.*)\s*$/) {
				$entry = lc $1;
				$bib_lookup{$cite}->{$entry} = $2;
			} elsif ($entry ne '') {
				$text = $line;
				chop $text;
				$text=~s/^\s*/ /;
				$text=~s/\s*$//;
				$bib_lookup{$cite}->{$entry}.=$text unless $text eq ' }';
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

sub used_string {
	my $e = shift;
	my $alias = $strings{$e};
	if ($shrink ne '') {
		if ($alias=~/\((.+)\)\"/) {
			$used_string{$e} = "\@string{".$e."={".$1."}}\n";
		} elsif ($alias=~/^\"([^\"]*):.*\"/) {
			$used_string{$e} = "\@string{".$e."={".$1."}}\n";
		} else {
			$alias = rewrite_field($alias);
			$alias =~ s/POT # //;
			$alias =~ s/\"\" # //;
			$used_string{$e} = "\@string{".$e."=".$alias."}\n" unless $alias eq '';
		}
	} else {
		$used_string{$e} = "\@string{".$e."=".$alias."}\n" unless $alias eq '';
	}
	return $e;
}

sub rewrite_field {
	my $field = shift;
	if ($shrink) {
		$field =~ s/Lecture Notes in Computer Science/{LNCS}/i;
		$field =~ s/Object-Oriented Programming/{OOP}/gi;
		$field =~ s/Proceedings of the //;
		$field =~ s/Proceedings of //;
		$field =~ s/Workshop/W'shop/gi;
		$field =~ s/Symposium/Symp./gi;
		$field =~ s/Conference/Conf./gi;
		$field =~ s/International /Int. /gi;
		$field =~ s/European /Eur. /gi;
		$field =~ s/Demonstration /Demo. /gi;
		$field =~ s/Software Engineering /Softw. Eng./gi;
		$field =~ s/Software /Softw. /gi;
		$field =~ s/System /Syst. /gi;
		$field =~ s/Engineering /Eng. /gi;
		$field =~ s/Technical /Tech. /gi;
		$field =~ s/Report /Rep. /gi;
		$field =~ s/University of /Univ. of /gi;
		$field =~ s/University /Univ. /gi;
		$field =~ s/Friedrich-Alexander-Universit[^\s]+t/{FAU}/gi;
		$field =~ s/Computer /Comp. /gi;
		$field =~ s/Sciences /Sci. /gi;
		$field =~ s/Virtual Machine/{VM}/gi;
		$field =~ s/Technologies/Techn./g;
		$field =~ s/Applications/Appl./g;
		$field =~ s/Implementation/Impl./g;

		$field =~ s/Twenty-eighth /28th /i;
		$field =~ s/First /1st /i;
		$field =~ s/Second /2nd /i;
		$field =~ s/Third /3rd /i;
		$field =~ s/Fourth /4th /i;
		$field =~ s/Fifth /5th /i;
	}
	return $field;
}

sub short_authors {
	my $authors = shift;

	$authors = $1 if $authors=~/^\s*{(.*)}\s*$/;
	$authors = $1 if $authors=~/^\s*\"(.*)\"\s*$/;

	my @author = split(/ and /,$authors);
	for (my $i=0;$i<scalar @author;$i++) {
		$author[$i]=~s/(^\s+|\s+$)//g;
		next if $author[$i]=~/^{.+}$/;
		my $v, @vs, $n;
		if ($author[$i]=~/,/) {
			($n,$v) = split(/,/,$author[$i]);
			@vs = split(/\s/,$v);
			push @vs, $n;
		} else {
			@vs = split(/\s/,$author[$i]);
		}
		for (my $j=0;$j<$#vs;$j++) {
			if ($vs[$j] eq '') {
				$vs[$j]='';
			} else {
				my @f = split('',$vs[$j]); 
				$vs[$j]=@f[0].'.' unless @f[0] eq '{';
			}
		}
		$author[$i] = '{'.join(' ',@vs).'}';
	}

	my $a ='';
	if ((scalar @author) <= 2) {
		$a = join(' and ',@author);
	} else {
		$a = $author[0].'{'.$dwarf_string.'}';
	}

	return '{'.$a.'}';
}

sub add_entry {
	my $cite = shift;

	if ($bib_lookup{$cite}) {
		my $entry='';
		my $url='';
		my $type = lc $bib_lookup{$cite}->{'__type'};
		$entry.="% Title: ".plain_asc($bib_lookup{$cite}->{'title'})."\n";
		$entry.="% Category: ".plain_asc($bib_lookup{$cite}->{'category'})."\n";
		$entry.="% doi: ".$bib_lookup{$cite}->{'doi'}."\n" if $bib_lookup{$cite}->{'doi'};
		#$entry.="% url: ".$bib_lookup{$cite}->{'bibsource'}."\n" if $bib_lookup{$cite}->{'bibsource'};
		$entry.="% url: ".$bib_lookup{$cite}->{'ee'}."\n" if $bib_lookup{$cite}->{'ee'};
		if ($bib_lookup{$cite}->{'xurl'}) {
			$url = $bib_lookup{$cite}->{'xurl'};
			$url=~s/,\s*$//;
			$url=~s/^\s*[{"](.*)["}]\s*$/$1/;
			$entry.="% url: ".$url."\n";
		}
		if ($bib_lookup{$cite}->{'url'}) {
			$url = $bib_lookup{$cite}->{'url'};
			$url=~s/,\s*$//;
			$url=~s/^\s*[{"](.*)["}]\s*$/$1/;
			$entry.="% url: ".$url."\n";
		}
		$entry.="@".$type."{".$cite.",\n";
		if ($noteurl && $url ne '') {
			my $note = $bib_lookup{$cite}->{'note'};
			if ($note eq '' || $note=~/http:\/\//) {
				$bib_lookup{$cite}->{'note'} = "{\\url{$url}}";
			} else {
				unless ($note=~/$url/) {
					$bib_lookup{$cite}->{'note'} =~ s/([}"]),$/, \\url{$url}$1/;
				}
			}
		}
		foreach $f (sort keys %{$bib_lookup{$cite}}) {
			next if $skip{$f} ne '';
			my $e = $bib_lookup{$cite}->{$f};
			$e=~s/,\s*$//;
			$e=used_string($e);
			$e = rewrite_field($e) if $rewrite{$f} ne '';
			$e = short_authors($e) if $dwarf && $f eq 'author';
			$entry.="\t$f = $e,\n";
		}
		$entry.="}\n\n";
		push @used_entry, $entry;

		if ($bib_lookup{$cite}->{'crossref'}) {
			my $cross = $bib_lookup{$cite}->{'crossref'};
			$cross=~s/(\{|\}|\"|,)//g;
			unless ($allready_seen{$cross}) {
				$allready_seen{$cross}=1;
				add_entry($cross);
			}
		}
	} else {
		if ($bib_lookup_old{$cite}) {
			print "% ".$cite." ".plain_asc($bib_lookup_old{$cite}->{'title'})." XXX\n";
		} else {
			print "% ".$cite." XXX\n";
		}
	}
}

sub parse_texfile {
	my $file = shift;
	my $intex;

	open $intex,"<$file" || die "Can't open $file\n";
	while (<$intex>) {
		$line = $_;
		if ($line=~/\\include{([^}]+)}/) {
			parse_texfile("$1.tex");
		}
		if ($line=~/\\input{([^}]+)}/) {
			parse_texfile("$1.tex");
		}
		while ($line=~s/\\(no)?cite[tp]?(\[[^\]]*\])?{([^}]+)}//) {
			foreach $cite (split(/,/,$3)) {
				$cite=~s/\s//g;
				next if $allready_seen{$cite};
				$allready_seen{$cite}=1;
				add_entry($cite);
				
			}
		}
	}
	close $intex;
}

while ($ARGV[0]=~/^-/) {
	if ($ARGV[0] eq '-shrink') {
		$skip{'location'}=1;
		$skip{'editor'}=1;
		$shrink=1;
	}
	$skip{'month'}=1 if $ARGV[0] eq '-nomonth';
	if ($ARGV[0] eq '-nopages') {
		$skip{'bookpages'}=1;
		$skip{'pages'}=1;
	}
	$all = 1 if $ARGV[0] eq '-all';
	$dwarf = 1 if $ARGV[0] eq '-dwarf';
	$noteurl = 1 if $ARGV[0] eq '-url2note';
	shift @ARGV;
}

die "usage: export [-all] [-url2note] [-nopages] [-nomonth] [-dwarf] [-shrink] <tex file> [<bib file>]\n" if $ARGV[0] eq '';

print "% This file is auto generated! %\n\n";

$bib_file = $ARGV[1];
$bib_file = "bib/all.bib" if $bib_file eq '' && -f "bib/all.bib";
$bib_file = "all.bib" if $bib_file eq '' && -f "all.bib";

print "% source: $bib_file\n";
%bib_lookup = read_bib_file($bib_file);

%bib_lookup_old = ();
if ($ARGV[2] ne '') {
	%bib_lookup_old = read_bib_file($ARGV[2]);
}

print "\n";
%allready_seen = ();
@used_entry = ();
push @used_entry, "\n";

if ($all eq '') {
	parse_texfile($ARGV[0]);
} else {
	foreach $cite (keys %bib_lookup) {
		next if $allready_seen{$cite};
		$allready_seen{$cite}=1;
		add_entry($cite);
	}
}


foreach $e (sort keys %used_string) {
	print "$used_string{$e}";
}

foreach $e (@used_entry) {
	print "$e";
}

