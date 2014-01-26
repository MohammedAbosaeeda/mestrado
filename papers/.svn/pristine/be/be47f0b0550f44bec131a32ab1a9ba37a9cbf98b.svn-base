%==============================================================================
% Estilo de referencia bibliografica da ABNT
% UFRGS TeX Users Group
% $Id: abnt.bst,v 4.1 2001/11/29 13:55:44 avila Exp $
%==============================================================================

%==============================================================================
% Definicao dos campos das entradas
%==============================================================================
ENTRY
{ 
	author
	editor
	title
	booktitle
	chapter
	pages
	address
	publisher
	year
	journal
	month
	number
	edition
	howpublished
	institution
	key
	note
	organization
	school
	series
	type
	volume
}
{}
{
	label
}

INTEGERS {i j n}
STRINGS {s t last.label current.letter names new.pages longest.label}

%==============================================================================
% Definicao dos nomes dos meses
%==============================================================================
MACRO {jan} {"Jan."}
MACRO {feb} {"Feb."}
MACRO {mar} {"Mar."}
MACRO {apr} {"Apr."}
MACRO {may} {"May"}
MACRO {jun} {"June"}
MACRO {jul} {"July"}
MACRO {aug} {"Aug."}
MACRO {sep} {"Sept."}
MACRO {oct} {"Oct."}
MACRO {nov} {"Nov."}
MACRO {dec} {"Dec."}

%==============================================================================
% Definicao de strings usados na elaboracao das referencias
%==============================================================================
FUNCTION {str:mastersthesis} {"Disserta{\c{c}}{\~a}o (Mestrado em Ci{\^e}ncia da Computa{\c{c}}{\~a}o)"}
FUNCTION {str:phdthesis} {"Tese (Doutorado em Ci{\^e}ncia da Computa{\c{c}}{\~a}o)"}
FUNCTION {str:chapter} {"Cap."}

%==============================================================================
% Definicao das abreviaturas padrao
%==============================================================================
MACRO {acmcs} {"ACM Computing Surveys"}
MACRO {acta} {"Acta Informatica"}
MACRO {cacm} {"Communications of the ACM"}
MACRO {ibmjrd} {"IBM Journal of Research and Development"}
MACRO {ibmsj} {"IBM Systems Journal"}
MACRO {ieeese} {"IEEE Transactions on Software Engineering"}
MACRO {ieeetc} {"IEEE Transactions on Computers"}
MACRO {ieeetcad}
	{"IEEE Transactions on Computer-Aided Design of Integrated Circuits"}
MACRO {ipl} {"Information Processing Letters"}
MACRO {jacm} {"Journal of the ACM"}
MACRO {jcss} {"Journal of Computer and System Sciences"}
MACRO {scp} {"Science of Computer Programming"}
MACRO {sicomp} {"SIAM Journal on Computing"}
MACRO {tocs} {"ACM Transactions on Computer Systems"}
MACRO {tods} {"ACM Transactions on Database Systems"}
MACRO {tog} {"ACM Transactions on Graphics"}
MACRO {toms} {"ACM Transactions on Mathematical Software"}
MACRO {toois} {"ACM Transactions on Office Information Systems"}
MACRO {toplas} {"ACM Transactions on Programming Languages and Systems"}
MACRO {tcs} {"Theoretical Computer Science"}

%==============================================================================
% Exibe warning quando um campo ``required'' esta' faltando. Recebe na pilha o
% tipo do campo.
%==============================================================================
FUNCTION {show.missing.field.warning}
{
	"Falta o campo " swap$ " em " cite$ * * * warning$
}

%==============================================================================
% Funcoes logicas
%==============================================================================
FUNCTION {not}
{
		{ #0 }
		{ #1 }
	if$
}

FUNCTION {and}
{
		'skip$
		{ pop$ #0 }
	if$
}

FUNCTION {or}
{
		{ pop$ #1 }
		'skip$
	if$
}

FUNCTION {is.numeric.char}
{
	#1 #1 substring$
	duplicate$ "" =
		{pop$ #0}
		{
			duplicate$ chr.to.int$ #47 >
			swap$ chr.to.int$ #58 <
			and
		}
	if$
}

FUNCTION {is.uppercase.char}
{
	#1 #1 substring$
	duplicate$ "" =
		{pop$ #0}
		{
			duplicate$ chr.to.int$ #64 >
			swap$ chr.to.int$ #91 <
			and
		}
	if$
}

%==============================================================================
% Escreve no arquivo o comando que gera o item
%
% Parm:
%	 um string com a formatacao do item
%==============================================================================
FUNCTION {write.item}
{
	"\bibitem[" label * "]{" * cite$ * "} " *
	swap$ *
	write$ newline$ newline$
}

%==============================================================================
% Separa o titulo em titulo e sub-titulo, empilhando o sub-titulo e o titulo,
% nessa ordem
%
% Parm:
%	 o titulo
%==============================================================================
FUNCTION {extract.subtitle}
{
	#1 'i :=

	duplicate$
	i #1 substring$
	{
		duplicate$ ":" = not
		swap$ "" = not
		and
	}
	{
		#1 i + 'i :=
		duplicate$
		i #1 substring$
	} while$

	duplicate$ i #999 substring$
	swap$
	#1 i #1 - substring$
}

%==============================================================================
% Separa a primeira palavra do texto, empilhando o resto e a palavra, nessa
% ordem
%
% Parm:
%	 o texto
%==============================================================================
FUNCTION {extract.first.word}
{
	#1 'i :=

	duplicate$
	i #1 substring$
	{
		duplicate$ " " = not
		swap$ "" = not
		and
	}
	{
		#1 i + 'i :=
		duplicate$
		i #1 substring$
	} while$

	duplicate$ i #999 substring$
	swap$
	#1 i #1 - substring$
}

%==============================================================================
% Enfatiza um string
%
% Parm:
%	 o string
%==============================================================================
FUNCTION {emphasize}
{
	"\textbf{" swap$ * "}" *
}

%==============================================================================
% Adiciona um texto (se nao-empty$) ja' formatado ao item, separando-o por
% ponto.
% Ex.: NOME. Titulo. data. ano.
%
% Parm:
%	 um string representando o item (pode ser vazio, para iniciar o item)
%	 o texto a ser adicionado
%==============================================================================
FUNCTION {add.to.item}
{
	duplicate$ empty$
		'pop$
		{
			swap$
			duplicate$ empty$
				'skip$
				{" " *}
			if$
			swap$ add.period$ *
		}
	if$
}

%==============================================================================
% Adiciona um item (se nao-empty$) a uma lista do tipo "a, b, c, ..."
%
% Parm:
%	 um string representando a lista (pode ser vazio, para criar uma lista)
%	 o item a ser adicionado
%==============================================================================
FUNCTION {add.to.list}
{
	duplicate$ empty$
		'pop$
		{
			swap$
			duplicate$ empty$
				'skip$
				{", " *}
			if$
			swap$ *
		}
	if$
}

%==============================================================================
% Adiciona um nome a uma lista de nomes (separados por ponto-e-virgula)
%
% Parm:
%	 um string representando a lista (pode ser vazio, para criar uma lista)
%	 o nome a ser adicionado
%==============================================================================
FUNCTION {add.to.namelist}
{
	swap$
	duplicate$ empty$
		'skip$
		{"; " *}
	if$
	swap$ *
}

%==============================================================================
% Formata uma lista de nomes.
%
% Parm:
%	 os nomes a serem formatados
%==============================================================================
FUNCTION {format.names}
{
	duplicate$ num.names$ 'n :=
	#1 'i :=
	"" 'names :=

	{i n #1 + <}
	{
		duplicate$ duplicate$ i "{ll}{~jj}" format.name$
		duplicate$ "others" =
		n #3 >
		i #2 =
		and
		or
		{
			pop$ pop$ names " et~al." *
			n 'i :=
		}{
			"u" change.case$ swap$
			i "{, f.}{~vv}" format.name$ *
			names swap$ add.to.namelist
		} if$ 'names :=
		#1 i + 'i :=
	} while$
	pop$
	names
}

%==============================================================================
% Formata um titulo (pode ser `title' ou `booktitle')
%
% Parm:
%	 um valor (0 ou 1) que indica se a formatacao deve ser enfatizada ou nao
%	 um string com o titulo a ser formatado
%==============================================================================
FUNCTION {format.title}
{
	"t" change.case$
	swap$
		{extract.subtitle emphasize}
		{extract.subtitle}
	if$
	swap$ "l" change.case$ *
}

%==============================================================================
% Retorna a indicacao de paginas ajustada para ter dois tracos (`--') se a
% entrada so' tiver um.
%==============================================================================
FUNCTION {get.adjusted.pages}
{
	#1 'i :=
	"" 'new.pages :=
	pages #1 #1 substring$
	{duplicate$ "" = not}
	{
		duplicate$ "-" =
		pages i #1 - #1 substring$ "-" = not
		and
		pages i #1 + #1 substring$ "-" = not
		and
			{"-" *}
			'skip$
		if$
		new.pages swap$ * 'new.pages :=
		#1 i + 'i :=
		pages i #1 substring$
	} while$
	pop$
	new.pages
}

%==============================================================================
% Coloca a primeira palavra do titulo em letras maiusculas e o resto em
% minusculas. Esta funcao e' usada para gerar referencias cuja entrada e' pelo
% titulo.
%==============================================================================
FUNCTION {format.entrytitle}
{
	#0 title format.title
	extract.first.word "u" change.case$
	swap$ * 
}

%==============================================================================
% Converte a edicao literal ("First", "Second", etc., que é o padrão indicado
% no manual do BibTeX) para o algarismo correspondente. Recebe na pilha o
% string e devolve o numero.
%==============================================================================
FUNCTION {convert.to.number}
{
	duplicate$ "First" =
	{pop$ "1"}
	{duplicate$ "Second" =
	{pop$ "2"}
	{duplicate$ "Third" =
	{pop$ "3"}
	{duplicate$ "Fourth" =
	{pop$ "4"}
	{duplicate$ "Fifth" =
	{pop$ "5"}
	{duplicate$ "Sixth" =
	{pop$ "6"}
	{duplicate$ "Seventh" =
	{pop$ "7"}
	{duplicate$ "Eighth" =
	{pop$ "8"}
	{duplicate$ "Ninth" =
	{pop$ "9"}
	{duplicate$ "Tenth" =
	{pop$ "10"}
	{skip$
	} if$
	} if$
	} if$
	} if$
	} if$
	} if$
	} if$
	} if$
	} if$
	} if$
}

%=========================================================================
% Formatacao dos campos. Se o campo nao existir a funcao devolve um string
% vazio.
%==============================================================================
FUNCTION {format.author}
{
	author empty$
		{""}
		{author format.names}
	if$
}

FUNCTION {format.editor}
{
	editor empty$
		{""}
		{
			editor duplicate$ num.names$ #1 >
				{format.names "~(Eds.)" *}
				{format.names "~(Ed.)" *}
			if$
		}
	if$
}

FUNCTION {format.edition}
{
	edition empty$
		{""}
		{edition convert.to.number ".ed." *}
	if$
}

FUNCTION {format.volume}
{
	volume empty$
		{""}
		{"v." volume *}
	if$
}

FUNCTION {format.number}
{
	number empty$
		{""}
		{"n." number *}
	if$
}

FUNCTION {format.month}
{
	month empty$
		{""}
		{month}
	if$
}

FUNCTION {format.address}
{
	address empty$
		{""}
		{address}
	if$
}

FUNCTION {format.publisher}
{
	type$ "mastersthesis" =
	type$ "phdthesis" =
	or
	{
		school empty$
			{""}
			{school}
		if$
	}{
	type$ "techreport" =
	{
		institution empty$
			{""}
			{institution}
		if$
	}{
		publisher empty$
			{""}
			{publisher}
		if$
	} if$
	} if$
}

FUNCTION {format.year}
{
	year empty$
		{""}
		{year}
	if$
}

FUNCTION {format.chapter}
{
	chapter empty$
		{""}
		{
			type empty$
				{str:chapter}
				{type "~" *}
			if$
			chapter *
		}
	if$
}

FUNCTION {format.pages}
{
	pages empty$
		{""}
		{"p." get.adjusted.pages *}
	if$
}

FUNCTION {format.totalpages}
{
	pages empty$
		{""}
		{pages "p." *}
	if$
}

FUNCTION {format.note}
{
	note empty$
		{""}
		{note}
	if$
}

FUNCTION {format.month.year}
{
	month empty$
		{year}
		{month "~" * year *}
	if$
}

FUNCTION {format.type}
{
	type empty$
		'skip$
		{pop$ type}
	if$
}

FUNCTION {format.howpublished}
{
	howpublished empty$
		{""}
		{howpublished}
	if$
}

FUNCTION {format.series}
{
	series empty$
		{""}
		{series}
	if$
}

%==============================================================================
% As seguintes funcoes adicionam um item a uma lista passada na pilha.
%==============================================================================
FUNCTION {add.address} {format.address add.to.list}
FUNCTION {add.publisher} {format.publisher add.to.list}
FUNCTION {add.year} {format.year add.to.list}
FUNCTION {add.chapter} {format.chapter add.to.list}
FUNCTION {add.edition} {format.edition add.to.list}
FUNCTION {add.volume} {format.volume add.to.list}
FUNCTION {add.number} {format.number add.to.list}
FUNCTION {add.month} {format.month add.to.list}
FUNCTION {add.totalpages} {format.totalpages add.to.list}
FUNCTION {add.pages} {format.pages add.to.list}
FUNCTION {add.note} {format.note add.to.list}
FUNCTION {add.month.year} {format.month.year add.to.list}
FUNCTION {add.howpublished} {format.howpublished add.to.list}

%==============================================================================
% Converte virgula para dois-pontos; usado na formatacao do publisher de
% proceedings e inproceedings, que normalmente levam tambem o local de publi-
% cao; no padrao ABNT essa separacao deve ser por dois-pontos.
% Recebe o string original e devolve o novo.
%==============================================================================
FUNCTION {comma.to.colon}
{
	's :=
	"" 't :=
	#1 'i :=
	{s i #1 substring$ "" = not}
	{
		s i #1 substring$ duplicate$ "," =
			{pop$ t ":" * 't :=}
			{t swap$ * 't :=}
		if$
		i #1 + 'i :=
	} while$
	t
}

%
% localiza a posicao do primeiro caractere maiusculo
% recebe o string, devolve a posicao
%
FUNCTION {find.first.uppercase.char}
{
	's :=
	#1 'j :=
	#1 'i :=
	{
		s i #1 substring$ 't :=
		t "" = not
		t is.uppercase.char not
		and
	}
	{
		i #1 + 'i :=
		t "\" = not
		t "{" = not
		and
		t "}" = not
		and
			{i 'j :=}
			'skip$
		if$
	} while$
	t "" =
		{#1}
		{j}
	if$
}

%
% extrai o primeiro numero que aparece no string passado na pilha,
% retornando-o; se nao ha' numero, retorna ""
%
FUNCTION {extract.first.number}
{
	's :=
	"" 't :=
	#1 'i :=
	{
		s i #1 substring$
		duplicate$ is.numeric.char not
		swap$ "" = not
		and
	}
	{
		i #1 + 'i :=
	} while$
	{s i #1 substring$ is.numeric.char}
	{
		t s i #1 substring$ * 't :=
		i #1 + 'i :=
	} while$
	t
}

%
% divide um string em dois
% recebe o string e a posicao
% devolve a primeira e a segunda parte
FUNCTION {split.string}
{
	swap$ 's :=
	duplicate$
	s swap$ #1 swap$ #1 - substring$
	swap$ s swap$ global.max$ substring$
}

%
% recebe o titulo original e devolve, na ordem, o tipo de proceedings e o
% restante do titulo
%
FUNCTION {extract.proceedings.type}
{
	's :=
	type empty$ not
		{type s}
		{"dummy"
	pop$ s #1 #11 substring$ duplicate$ "Proceedings" =
		{s #12 global.max$ substring$}{
	pop$ s #1 #5 substring$ duplicate$ "Proc." =
		{pop$ "Proceedings" s #6 global.max$ substring$}{
	pop$ s #1 #22 substring$ duplicate$ "Conference Proceedings" =
		{pop$ "Proceedings" s #23 global.max$ substring$}{
	pop$ s #1 #6 substring$ duplicate$ "Annals" =
		{s #7 global.max$ substring$}{
	pop$ s #1 #5 substring$ duplicate$ "Actas" =
		{s #6 global.max$ substring$}{
	pop$ s #1 #5 substring$ duplicate$ "Anais" =
		{s #6 global.max$ substring$}{
	pop$ s #1 #6 substring$ duplicate$ "Anales" =
		{s #7 global.max$ substring$}{
	pop$ s #1 #8 substring$ duplicate$ "Memorias" =
		{s #9 global.max$ substring$}{
	pop$ s #1 #22 substring$ duplicate$ "Trabajos Seleccionados" =
		{s #23 global.max$ substring$}{
	pop$ s #1 #11 substring$ duplicate$ "Tagungsband" =
		{s #12 global.max$ substring$}
		{pop$ "Anais" s} if$
	} if$
	} if$
	} if$
	} if$
	} if$
	} if$
	} if$
	} if$
	} if$
	} if$
}

%==============================================================================
% Converte um booktitle do tipo "Proc. of XXX" no padrao ABNT
% Recebe na pilha o titulo original
% Devolve na pilha, na ordem:
% - o tipo de publicacao
% - o titulo ja' formatado (em maiusculas e com a edicao)
%==============================================================================
FUNCTION {process.proceedings.booktitle}
{
	extract.proceedings.type
	duplicate$ find.first.uppercase.char split.string "u" change.case$
	swap$ extract.first.number
	duplicate$ empty$
		'pop$
		{", " swap$ * * "." *}
	if$
}

%==============================================================================
% Estas funcoes formatam partes comuns a varios tipos de referencia.
%==============================================================================
FUNCTION {format.publication}
{
	""
	address empty$
		{
			"[S.l."
			format.publisher empty$
				{":~s.n.]" *}
				{"]: " format.publisher * *}
			if$ add.year add.to.item
		}
		{
			format.address
			format.publisher empty$
				{": [s.n.]" *}
				{": " format.publisher * *}
			if$ add.year add.to.item
		}
	if$
}

FUNCTION {format.proceedings.publication}
{
	publisher empty$
		{"[S.l.:~s.n.]"}
		{publisher comma.to.colon}
	if$ add.year
}

FUNCTION {format.misc.publication}
{
	address empty$
	publisher empty$
	and
		{""}
		{format.publication}
	if$
}

FUNCTION {format.series.info}
{
	"(" format.series *
	add.volume ")" *
}

FUNCTION {format.complementary.data.with.totalpages}
{
	"" format.totalpages add.to.item
	""
	series empty$
		{add.volume add.number add.to.item}
		{add.number add.to.item format.series.info add.to.item}
	if$
}

FUNCTION {format.complementary.data.with.page.range}
{
	""
	series empty$
		{add.volume add.number add.pages}
		{"" add.number add.pages add.to.item format.series.info add.to.item}
	if$
}

%
% esta funcao recebe o titulo a ser formatado
%
FUNCTION {format.proceedings.booktitle}
{
	process.proceedings.booktitle "" swap$ add.year add.address add.to.item
	swap$ "{\ldots}" * emphasize " " swap$ * *
}

%==============================================================================
% Formatacao das entradas
%==============================================================================
FUNCTION {article}
{
	""
	format.author add.to.item
	#0 title format.title add.to.item
	journal emphasize
		add.address add.volume add.number add.pages add.month.year add.to.item
	format.note add.to.item

	write.item
}

FUNCTION {book}
{
	""
	editor empty$
		{format.author}
		{format.editor}
	if$ add.to.item
	#1 title format.title add.to.item
	format.edition add.to.item
	format.publication add.to.item
	format.complementary.data.with.totalpages add.to.item
	format.note add.to.item

	write.item
}

FUNCTION {inbook}
{
	""
	editor empty$
		{format.author}
		{format.editor}
	if$ add.to.item
	#1 title format.title add.to.item
	format.edition add.to.item
	format.publication add.to.item
	format.complementary.data.with.page.range add.to.item
	format.note add.to.item

	write.item
}

FUNCTION {incollection}
{
	""
	format.author add.to.item
	#0 title format.title add.to.item
	"In:"
		editor empty$
			'skip$
			{format.editor add.to.item}
		if$ 
		#1 booktitle format.title add.to.item add.to.item
	format.edition add.to.item
	format.publication add.to.item
	format.complementary.data.with.page.range add.to.item
	format.note add.to.item

	write.item
}

FUNCTION {inproceedings}
{
	""
	format.author add.to.item
	#0 title format.title add.to.item
	" In: " booktitle format.proceedings.booktitle * *
	format.proceedings.publication add.to.item
	format.complementary.data.with.page.range add.to.item
	format.note add.to.item

	write.item
}

FUNCTION {manual}
{
	""
	author empty$
		{format.entrytitle add.to.item}
		{
			format.author add.to.item
			#1 title format.title add.to.item
		}
	if$

	format.edition add.to.item
	format.publication add.to.item
	format.complementary.data.with.totalpages add.to.item
	format.note add.to.item

	write.item
}

FUNCTION {mastersthesis}
{
	""
	format.author add.to.item
	#1 title format.title add.to.item
	format.year add.to.item
	format.totalpages add.to.item
	str:mastersthesis format.type " --- " * format.publisher *
		add.address add.to.item
	format.note add.to.item

	write.item
}

FUNCTION {misc}
{
	""
	author empty$
		{
			title empty$
				'skip$
				{format.entrytitle add.to.item}
			if$
		}
		{
			format.author add.to.item
			title empty$
				'skip$
				{#1 title format.title add.to.item}
			if$
		}
	if$
	format.misc.publication add.to.item
	format.complementary.data.with.totalpages add.to.item
	format.note add.howpublished add.to.item

	write.item
}

FUNCTION {phdthesis}
{
	""
	format.author add.to.item
	#1 title format.title add.to.item
	format.year add.to.item
	format.totalpages add.to.item
	str:phdthesis format.type " --- " * format.publisher *
		add.address add.to.item
	format.note add.to.item

	write.item
}

FUNCTION {proceedings}
{
	""
	title format.proceedings.booktitle *
	format.proceedings.publication add.to.item
	format.complementary.data.with.page.range add.to.item
	format.note add.to.item

	write.item
}

FUNCTION {techreport}
{
	""
	format.author add.to.item
	#1 title format.title add.to.item
	format.publication add.to.item
	type empty$
		{""}
		{type}
	if$ add.note add.to.item
	number empty$
		'skip$
		{"(" number ")" * * add.to.item}
	if$

	write.item
}

FUNCTION {booklet} {misc}
FUNCTION {unpublished} {misc}
FUNCTION {default.type} {misc}

%==============================================================================
% Le as referencias feitas
%==============================================================================
READ

%==============================================================================
% Inicializa algumas variaveis globais
%==============================================================================
FUNCTION {init}
{
	"" 'last.label :=
	"" 'longest.label :=
	"a" 'current.letter :=
}

%==============================================================================
% Retorna na pilha o string a ser usado para a ordenacao das entradas.
%==============================================================================
FUNCTION {get.sortstring}
{
	author empty$ not
		{format.author}
		{
			editor empty$ not
				{format.editor}
				{
					title empty$ not
						{title}
						{
							key empty$ not
								{key}
								{"???"}
							if$
						}
					if$
				}
			if$
		}
	if$

	type$ "inbook" =
	booktitle empty$ not
	and
		{
			booktitle title = not
				{pop$ title}
				'skip$
			if$
		}
		'skip$
	if$

	type$ "proceedings" =
		{pop$ title}
		'skip$
	if$
}

%==============================================================================
% Define as ``keys'' para ordenacao
%==============================================================================
FUNCTION {set.sortkeys}
{
	get.sortstring purify$ "l" change.case$
	year empty$
		'skip$
		{year *}
	if$
	#1 entry.max$ substring$
	#1 global.max$ substring$
	'sort.key$ :=
}

%==============================================================================
% Retorna na pilha o string a ser usado para a construcao do label. Quase sempre
% sera' o sobrenome do primeiro autor, mas para alguns tipos de referencia pode
% ser o editor, a entidade ou o titulo
%==============================================================================
FUNCTION {get.labelstring}
{
	author empty$ not
		{author #1 "{ll}" format.name$}
		{
			editor empty$ not
				{editor #1 "{ll}" format.name$}
				{
					title empty$ not
						{title}
						{
							key empty$ not
								{key}
								{"???"}
							if$
						}
					if$
				}
			if$
		}
	if$

	type$ "proceedings" =
		{pop$ title}
		'skip$
	if$

	type$ "booklet" =
	type$ "manual" =
	or
	type$ "misc" =
	or
	type$ "proceedings" =
	or
	key empty$ not
	and
		{pop$ key}
		'skip$
	if$

	type$ "inbook" =
	booktitle empty$ not
	and
		{
			booktitle title = not
				{pop$ title}
				'skip$
			if$
		}
		'skip$
	if$
}

%
% remove os espacos e hifens de um string recebido na pilha
% apostrofos sao mantidos pois fazem parte dos caracteres acentuados...
% retorna na pilha o resultado
%
FUNCTION {purify2}
{
	't :=
	#1 'j :=
	""
	{t j #1 substring$ "" = not}
	{
		t j #1 substring$ " " = not
		t j #1 substring$ "~" = not
		and
		t j #1 substring$ "-" = not
		and
			{t j #1 substring$ *}
			'skip$
		if$
		j #1 + 'j :=
	} while$
}

%==============================================================================
% Retorna na pilha o string a ser utilizado como label. Esta funcao recebe o
% string de onde o label deve ser extraido, e retorna os tres primeiros
% caracteres validos (ou seja, apostrofos ou hifens nao sao considerados).
% Esta funcao e' necessaria porque purify$ remove os acentos, e text.prefix$
% considera os caracteres invalidos. ``get.valid.label'' faz entao uma
% combinacao das duas.
%
% Parm:
%	 o string de onde o label deve ser extraido 
%==============================================================================
FUNCTION {get.valid.label}
{
	's :=
	#3 'i :=
	s i text.prefix$
	{
		purify$ purify2 text.length$ #3 <
		s i #1 substring$ "" = not
		and
	}
		{
			#1 i + 'i :=
			s i text.prefix$
		}
	while$
	s i text.prefix$ purify2
}
FUNCTION {get.right.year}
{	
	#-1 #2 substring$
}
%==============================================================================
% Constroi os labels, pegando as tres primeiras letras do nome do autor ou
% titulo (conforme retornado pela funcao ``get.labelstring''), mais os dois
% ultimos digitos do ano, se ele existir
%==============================================================================
FUNCTION {make.labels}
{
	get.labelstring get.valid.label "u" change.case$
	"~" *
	year empty$
		{"??"}
		{
			year purify$
			duplicate$ #1 #1 substring$ "2" =
				'skip$
				{#-1 #2 substring$}
			if$
		}
	if$ *
	'label :=
	label 'sort.key$ :=
}


%==============================================================================
% Formata os labels, adicionando letras minusculas aos labels de mesmo autor e
% ano
%==============================================================================
FUNCTION {format.labels}
{
	last.label
	label 'last.label :=
	label =
		{
			label current.letter * 'label :=
			current.letter chr.to.int$
			#1 + int.to.chr$
			'current.letter :=
		}
		{"a" 'current.letter :=}
	if$
	longest.label purify$ width$ label purify$ width$ <
		{label 'longest.label :=}
		'skip$
	if$
}

%==============================================================================
% Inicia a definicao das referencias
%==============================================================================
FUNCTION {begin.bib}
{
	preamble$ empty$
		'skip$
		{preamble$ write$ newline$ newline$}
	if$
	"\begin{thebibliography}{" longest.label "}" * * write$ newline$
	newline$
}

%==============================================================================
% Termina a definicao das referencias
%==============================================================================
FUNCTION {end.bib}
{
	"\end{thebibliography}" write$ newline$
}

%==============================================================================
% main()
%==============================================================================
EXECUTE {init}
ITERATE {make.labels}
SORT
ITERATE {format.labels}
ITERATE {set.sortkeys}
SORT
EXECUTE {begin.bib}
ITERATE {call.type$}
EXECUTE {end.bib}
