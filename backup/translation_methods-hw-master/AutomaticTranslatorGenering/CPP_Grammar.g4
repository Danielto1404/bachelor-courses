grammar CPP_Grammar;

@header{
	import java.util.*;//Random
}

@parser::members{
	final Random random = new Random();
	/*var obfuscation mechanisms*/
	Map<String, String> var_obf = new HashMap<String,String>();

	int new_name_symbols_count = 10;
	Set<String> useless_names_set = new TreeSet<String>();
	
	Set<String> all_names_set = new HashSet<String>(/*Collections.singleton(IO)*/);

	String gen_unique_name (){
		if (all_names_set.size()>=(1<<(new_name_symbols_count-2)))
			new_name_symbols_count+=2;

		String answer = "IO";
		while (!all_names_set.add(answer)){
			answer = "I";
			for (int i=1;i<new_name_symbols_count;i++){
				boolean check = random.nextBoolean();
				if (check&&i%2==0)
					answer+="I";
				if (check&&i%2==1)
					answer+="O";
				if (!check&&i%2==0)
					answer+="1";
				if (!check&&i%2==1)
					answer+="0";
			}
		}
		return answer;
	};


	String obfus_var(String var){
		String name;
		if (var_obf.containsKey(var))
			name = var_obf.get(var);
		else{
			name = gen_unique_name();
			var_obf.put(var,name);
		}
		return name;
	};

	final String typesMas[] = {"int","char","long","short"};
	final String operMas[] = {"+","-","*"};
	String get_useless_name(){
		String answer="";
		int place = (random.nextInt())%useless_names_set.size();
		Iterator<String> iter = useless_names_set.iterator();
		for (int i=0;i<=place&&iter.hasNext();i++){
			answer=iter.next();
		}
		return answer;
	}
	String get_random_str(){
		if (random.nextBoolean()){

			int tmp = random.nextInt();
			if (tmp% 3 == 0){//inserting 
				String name = gen_unique_name();
				useless_names_set.add(name);
				String answer = typesMas[(random.nextInt()%4+4)%4];
				answer += (" " + name + ";\n");
				return answer;
			}
			if (tmp%3==1&&useless_names_set.size()>0){//assignment
				String name = gen_unique_name();
				useless_names_set.add(name);
				String answer = typesMas[(random.nextInt()%4+4)%4];
				answer += (" " +  name + " = " + get_useless_name() + ";\n");
				return answer;

			}
			if (tmp % 3 == 2&&useless_names_set.size()>0){
				return (get_useless_name() + " = " + get_useless_name() + operMas[(random.nextInt()%3+3)%3] + get_useless_name() + ";\n");
			}
		}
		return "";
	};

}

stat returns [String str]	: pc=pure_code {$str = $pc.str;};

pure_code returns [String str] 	: frst_i = include_part{$str = $frst_i.str;} (scnd =pure_code{$str += $scnd.str;})?
								| frst_f = function_part{$str = $frst_f.str;} (scnd = pure_code{$str +=$scnd.str;})? 
								;


include_part returns [String str]	: i=INCLUDE  s=STRING	
										{$str = $i.text + " " +$s.text+"\n";}
									;

function_part returns [String str, String argsStr] @init{$argsStr = "";}	:  typeName=STABLENAME funName=STABLENAME LPAREN (type=STABLENAME var=unstable_name{$argsStr+=$type.text + " " + $var.str;} (c=COMMA{$argsStr+=$c.text;})?)* RPAREN  LFPAREN fb=function_body RFPAREN 
																{$str = $typeName.text + " " + $funName.text + "("+$argsStr+")" + "{\n"+$fb.str+"}\n";}
															;

function_body returns [String str] @init{$str="";}	: (op = operation{$str += (get_random_str()) + $op.str + '\n';})*
													;

operation returns [String str]	: vd=var_declaraion SEMICOLON
									{$str = $vd.str + ";";}
								| va=var_assignment SEMICOLON
									{$str = $va.str + ";";}
								| fi=function_invoking SEMICOLON
									{$str = $fi.str + ";";}
								| is=if_statement
									{$str = $is.str;}
								| fs=for_statement
									{$str = $fs.str;}
								| ws=while_statement
									{$str = $ws.str;}
								| io=io_statement SEMICOLON
									{$str = $io.str + ";";}
								;

io_statement returns [String str]	: CIN RTPAREN varName=unstable_name {$str=("cin >> " + $varName.str);} (RTPAREN varName1=unstable_name {$str+=" >> " + $varName1.str;})*
									| COUT LTPAREN dc=data_container {$str= ("cout << " + $dc.str);} (LTPAREN dc1=data_container {$str+=" << " + $dc1.str;})*
									;

var_declaraion returns [String str, String argsStr] @init{$argsStr = ""; $str="";}	: (CONST{$str="const ";})? typeName=STABLENAME (varName=unstable_name {$argsStr+=$varName.str;} | varAssig=var_assignment {$argsStr+=$varAssig.str;}) 
															(COMMA varName=unstable_name {$argsStr+=(" ," + $varName.str);} | varAssig=var_assignment {$argsStr+=(" ,"+$varAssig.str);})*
																{$str += $typeName.text + " " + $argsStr;}
															;

var_assignment returns [String str]	: varName=unstable_name ASSIGMENT dc=data_container {$str = $varName.str + " = " + $dc.str;}
									| varName=unstable_name ASSIGMENT fi=function_invoking {$str = $varName.str + " = " + $fi.str;}
									;

data_container returns [String str]	: varName=unstable_name
										{$str=$varName.str;}
									| val=VALUE
										{$str=$val.text;}
									| st=STRING
										{$str=$st.text;}
									;

function_invoking returns [String str, String argsStr] @init{$argsStr = "";}	: funName=STABLENAME LPAREN (dc=data_container {$argsStr+=$dc.str;} /*varName=unstable_name c=(COMMA)?{$argsStr+=$varName.str+ " "+c.text;}|val=value c=(COMMA)?{$argsStr+=$val.str+ " "+c.text;}*/)* RPAREN
																					{$str=$funName.text + " (" + $argsStr + ")"; }
																				| dc1=data_container as =ARITH_SIGN dc2=data_container
																					{$str=$dc1.str + $as.text + $dc2.str;}
																				;

if_statement returns [String str]	: (IF LPAREN c=condition RPAREN LFPAREN fb=function_body RFPAREN) {$str="if (" + $c.str + "){\n" + $fb.str + "}";} (ELSE LFPAREN fb1=function_body RFPAREN {$str+="else {"+$fb1.str+"}";})?
									;

for_statement returns [String str, String argsStr] @init{$argsStr="";}	: FOR LPAREN (vd=var_declaraion{$argsStr+=$vd.str;}|vs=var_assignment{$argsStr+=$vs.str;}) SEMICOLON con=condition SEMICOLON vs1=var_assignment RPAREN LFPAREN fb=function_body RFPAREN
															{$str = "for (" + $argsStr + "; " + $con.str + "; " + $vs1.str + "){\n" + $fb.str+ "}";}
														;

while_statement returns [String str]	: WHILE LPAREN con=condition RPAREN LFPAREN fb=function_body RFPAREN 
											{$str= "while (" + $con.str + "){\n" + $fb.str + "}";}
										;


condition returns [String str]	: LPAREN c=condition RPAREN
									{$str= "("+ $c.str + ")";}
								| c1=condition cs=COND_SIGN c2=condition
									{$str= $c1.str + $cs.text + $c2.str;}
								| fi=function_invoking
									{$str= $fi.str;}
								| dc1=data_container cs=CHECK_SIGN dc2=data_container
									{$str = $dc1.str + $cs.text + $dc2.str;}
								| BOOL
									{$str = $BOOL.text;}
								;

unstable_name returns [String str] 	: name = STABLENAME
										{$str = obfus_var($name.text);}
									;

WS  :   [ \t\n]+ -> skip ; // toss out whitespace

STRING 	: ('"'.*?'"')
		;


INCLUDE : '#include'
		;

CONST 	: 'const'
		;


CHECK_SIGN	: '<'|'>'|'<='|'>='|'=='|'!='
			;

ARITH_SIGN	: '+'|'-'|'*'|'/'|'%'
			;	

COND_SIGN	: '&&'|'||'
			;

VALUE 	: [0-9]+
		;

BOOL 	: 'true'
		| 'false'
		;

IF 	: 'i' 'f'
	;

FOR : 'for'
	;

WHILE 	: 'while'
		;

ELSE 	: 'else'
		;

CIN 	: 'cin'
		;

COUT 	: 'cout'
		;

LTPAREN	: '<<'
		;

RTPAREN	: '>>'
		;

STABLENAME	: [a-zA-Z][a-zA-Z0-9]*
			;

LPAREN 	: '('
		;

RPAREN 	: ')'
		;

LFPAREN	: '{'
		;

RFPAREN	: '}'
		;


COMMA	: ','
		;

SEMICOLON	: ';'
			;


ASSIGMENT 	: '='
			;


LineComment
    :   '//' ~[\r\n]* -> skip
    ;