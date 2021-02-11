my $regexp = "a.*?a";

while (<>) {
    s/($regexp){3}/bad/g;
    print;
}
