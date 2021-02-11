use List::MoreUtils qw(uniq);

my $protocol = "(\\w+:\\/\\/)?";
my $href = "\\w+.*?";
my $regex = "<\\s*?a.*?\\shref=\"($protocol)($href)\".*?>";


@unhandled = ();
@handled = ();

while (<>) {

    while (/$regex/g) {
        push(@unhandled, $3);
    }

}


for (@unhandled) {
    s/(.*?)(?:\/|:)(?:.*)/$1/;
    push (@handled, $_);
}


for (uniq sort @handled) {
    print;
    print "\n";
}
