#!/usr/bin/perl -l

$text = "2I7VjH)Q3RkPK*Sm3R!aY8!1P:5T6YL-`N4U5M,;xB#v1D%24U";
$hahaha = <>;
chop($hahaha);
@hahaha = split //,$hahaha;
for $i (0..length($text)-2){
    ($a,$b) = (substr($text,$i,1),substr($text,$i+1,1));
    if (ord($a) == (ord($b) ^ 97)){
        next if $a eq shift @hahaha;
        print "NOOO\n";
        exit;
    }
}
print "Congrats )\n";

# Answer:= 7H3K3Y15L4MBD4
