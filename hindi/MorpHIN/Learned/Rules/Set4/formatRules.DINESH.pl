#!/usr/bin/perl

#USAGE : scriptName <ordering - ord|unord> outputRuleFile

#changing the rule format into 
# ASID [(+-n CAT) AND] THEN CAT


$ordering = $ARGV[0];
$ruleDir = $ARGV[1];
@files = `ls $ruleDir/*.$ordering`;
#@files = `ls`;

#print `ls`;

foreach (@files)
#braces of the main foreach starts
{
$file = $_;

$inputFileName = $file;

open(RULEFILE, $inputFileName) || die("Cannot open rule file: $!");

if($inputFileName =~ /\/(.*)\/([0-9A-Za-z]*)\.($ordering)/)
	{
	#$outputFileName = "rules.ord";
	}
elsif($inputFileName =~ /\/(.*)\/([0-9A-Za-z]*)\.(unord)/)
	{
	#$outputFileName = "rules.unord";
	}

	
#$inputFileName =~ /\/(.*)\/([0-9A-Za-z]*)\.(exs)/;
$ASID = $2;

while (<RULEFILE>)
	{
	chomp;
	
	if($_ =~ /(^\s*IF)/)	#i.e if the sentence starts from IF
		{
		$str = "";
		$str = "$ASID [";

		($keyWord, $contextWord, $equalTo, $PosCategory) = split (/\s+/, $_);
		($junk, $plusMinus, $number) = split (/_/,$contextWord);

		if($plusMinus eq "plus")
			{
			$plusMinus = "+";
			}

		if($plusMinus eq "minus")
			{
			$plusMinus = "-";
			}

		$inBrackets = "($plusMinus$number $PosCategory)";
		$str = "$str$inBrackets";
		#print "$str \n";
		}

	elsif($_ =~ /(^\s*THEN)/)	#i.e if the sentence starts from IF
		{
		#print "$1 \n"; 
		($tmp, $vector) = split(/\[/,$_);
		$vector = "[$vector";
		($keyWord, $currWord, $equalTo, $PosCategory) = split (/\s+/, $tmp);
		$str = "$str] THEN $PosCategory VECTOR $vector";
		#print ("$vector \n $PosCategory \n");
		print ("$str \n");
		}


	elsif($_ =~ /(^\s*ELSE)/)	#i.e if the sentence starts from IF
		{
		#nothing to be done
		}

	elsif($_ =~ /(^\s*AND)/)	#i.e if the sentence starts from IF
		{
		($junk, $AND, $contextWord, $equalTo, $PosCategory) = split (/\s+/, $_);
		$str = "$str AND ";
		

		($junk, $plusMinus, $number) = split (/_/,$contextWord);

		if($plusMinus eq "plus")
			{
			#print "in AND plus \n";
			$plusMinus = "+";
			}

		if($plusMinus eq "minus")
			{
			$plusMinus = "-";
			}

		$inBrackets = "($plusMinus$number $PosCategory)";
		$str = "$str$inBrackets";
		}

	elsif($_ =~ /(^\s*\(DEFAULT\))/)	#i.e if the sentence starts from IF
		{
		$str = "$ASID [(DEFAULT)] THEN";
		($tmp, $vector) = split(/\[/,$_);
		$vector = "[$vector";
		($junk,$currWord, $equalTo, $PosCategory) = split (/\s+/, $tmp);
		$str = "$str $PosCategory VECTOR $vector";
		print ("$str \n");
		}
	}

}
#braces of the main foreach ends here
