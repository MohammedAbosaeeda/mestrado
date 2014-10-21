#!/usr/bin/perl

foreach $arg (@ARGV) {

	if ( -f $arg && $arg=~/\.java$/) {

		my $file = $arg;
		my $ifile = $file;

		if (0) {
			$file=~s/\.java$/\.java.test/g;
		} else {
			$ifile=~s/\.java$/\.bak/g;
			rename $file, $ifile;
		}

		print "$ifile $file\n";

		open IN, "<$ifile" || die "Can`t open $ifile.\n";
		open OUT, ">$file" || die "Can`t write $file.\n";

		my $content='';
		while (<IN>) { $content.=$_; }

		close IN;

		unless ( ////*/*/(c/)/ ) {
		print OUT "/**(c)\n\n  Copyright (C) 2006 Christian Wawersich \n\n  This file is part of the KESO Operating System.\n\n  It is distributed in the hope that it will be useful, but\n  WITHOUT ANY WARRANTY; without even the implied warranty of\n  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.\n\n  Please contact wawi@cs.fau.de for more info.\n\n  (c)**/\n\n";
	}

		$content =~ s/package\s+jx./package keso./gs;
		$content =~ s/import\s+jx.zero.Debug;/import keso.util.Debug;/gs;
		$content =~ s/import\s+jx./import keso./gs;

		print OUT "$content";

		close OUT;
	}
}
