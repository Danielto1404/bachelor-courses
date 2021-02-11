my $inTextBlock = 0;
my $isFirstSentence = 1;


while (<>) {

	s/<.*?>//g;

	if (/^\s*$/) {
		$inTextBlock = 0;
	} else {
		unless ($inTextBlock) {
			if ($isFirstSentence) {
				$isFirstSentence = 0;
			} else {
				print "\n";
			}
		}
		s/^\s+//g;
        s/\s+$//g;
        s/(\s)+\1/ /g;
        print;
		print "\n";
        $inTextBlock = 1;
	}
}
