%%
%% Fax
%%
\NeedsTeXFormat{LaTeX2e}[1997/06/01]
\ProvidesClass{fax}[2000/7/20 v0.1 Fax class]

\newcommand*{\@fontsize}{12pt}
\DeclareOption{10pt}{\renewcommand{\@fontsize}{10pt}}
\DeclareOption{11pt}{\renewcommand{\@fontsize}{11pt}}
\DeclareOption{12pt}{\renewcommand{\@fontsize}{12pt}}

\newcommand*{\@papersize}{a4paper}
\DeclareOption{a4paper}{\renewcommand{\@papersize}{a4paper}}
\DeclareOption{a5paper}{\renewcommand{\@papersize}{a5paper}}
\DeclareOption{b5paper}{\renewcommand{\@papersize}{b5paper}}
\DeclareOption{letterpaper}{\renewcommand{\@papersize}{letterpaper}}
\DeclareOption{legalpaper}{\renewcommand{\@papersize}{legalpaper}}
\DeclareOption{executivepaper}{\renewcommand{\@papersize}{executivepaper}}

\newcommand*{\@language}{english}
\DeclareOption{english}{
  \renewcommand{\@language}{english}
  \def\SenderText{Sender\/:}
  \def\SenderFaxText{Sender's Fax\/:}
  \def\SenderPhoneText{Sender's Phone\/:}
  \def\SenderEMailText{Sender's E-Mail\/:}
  \def\SenderWWWText{Sender's Web Page\/:}
  \def\RecipientText{Recipient\/:}
  \def\RecipientFaxText{Recipient's Fax\/:}
  \def\RecipientPhoneText{Recipient's Phone\/:}
  \def\RecipientEMailText{Recipient's E-Mail\/:}
  \def\RecipientWWWText{Recipient's Web Page\/:}
  \def\RecipientCompanyText{Company\/:}
  \def\SubjectText{Subject\/:}
  \def\DateText{Date\/:}
  \def\PagesText{Pages\/:}
  \def\OpeningText{Dear Sir/Madam\/,}
  \def\ClosingText{Sincerely yours\/,}
}
\DeclareOption{portuguese}{
  \renewcommand{\@language}{portuguese}
  \def\SenderText{Remetente\/:}
  \def\SenderFaxText{Fax do Remetente\/:}
  \def\SenderPhoneText{Telefone do Remetente\/:}
  \def\SenderEMailText{E-Mail do Rementente\/:}
  \def\SenderWWWText{P\'agina WWW do Rementente\/:}
  \def\RecipientText{Destinat\'ario\/:}
  \def\RecipientFaxText{Fax do Destinat\'ario\/:}
  \def\RecipientPhoneText{Telefone do Destinat\'ario\/:}
  \def\RecipientEMailText{E-Mail do Destinat\'ario\/:}
  \def\RecipientWWWText{P\'agina WWW do Destinat\'ario\/:}
  \def\RecipientCompanyText{Companhia do Destinat\'ario\/:}
  \def\SubjectText{Assunto\/:}
  \def\DateText{Data\/:}
  \def\PagesText{N\'umero de P\'aginas\/:}
  \def\OpeningText{Prezado Sr./Sra.\/,}
  \def\ClosingText{Atenciosamente\/,}
}
\DeclareOption{german}{
  \renewcommand{\@language}{german}
  \def\SenderText{Absender\/:}
  \def\SenderFaxText{Absenders Telefax\/:}
  \def\SenderPhoneText{Absenders Telefon\/:}
  \def\SenderEMailText{Absenders E-Mail\/:}
  \def\SenderWWWText{Absenders WWW Seite\/:}
  \def\RecipientText{Empf\"anger\/:}
  \def\RecipientFaxText{Empf\"angers Telefax\/:}
  \def\RecipientPhoneText{Empf\"angers Telefon\/:}
  \def\RecipientEMailText{Empf\"angers E-Mail\/:}
  \def\RecipientWWWText{Empf\"angers WWW\/:}
  \def\RecipientCompanyText{Empf\"anger Firma\/:}
  \def\SubjectText{Betreff\/:}
  \def\DateText{Datum\/:}
  \def\PagesText{Seiten\/:}
  \def\OpeningText{Liebe Frau/Lieber Herr\/,}
  \def\ClosingText{Mit freundlichen Gr\"ussen\/,}
}

\ExecuteOptions{\@fontsize,\@papersize,\@language} \ProcessOptions

\LoadClass[\@fontsize,\@papersize,oneside,final]{letter}[1997/04/16]

\RequirePackage{graphics}

\newcommand*{\company}[1]{\def\Company{#1}}
\def\Company{}
\newcommand*{\department}[1]{\def\Department{#1}}
\def\Department{}
\newcommand*{\logo}[1]{\def\Logo{#1}}
\def\Logo{}

\newcommand*{\from}[1]{\def\Sender{#1}}
\def\Sender{}
\newcommand*{\fromfax}[1]{\def\SenderFax{#1}}
\def\SenderFax{}
\newcommand*{\fromphone}[1]{\def\SenderPhone{#1}}
\def\SenderPhone{}
\newcommand*{\fromemail}[1]{\def\SenderEMail{#1}}
\def\SenderEMail{}
\newcommand*{\fromwww}[1]{\def\SenderWWW{#1}}
\def\SenderWWW{}

\renewcommand*{\to}[1]{\def\Recipient{#1}}
\def\Recipient{}
\newcommand*{\tofax}[1]{\def\RecipientFax{#1}}
\def\RecipientFax{}
\newcommand*{\tophone}[1]{\def\RecipientPhone{#1}}
\def\RecipientPhone{}
\newcommand*{\toemail}[1]{\def\RecipientEMail{#1}}
\def\RecipientEMail{}
\newcommand*{\towww}[1]{\def\RecipientWWW{#1}}
\def\RecipientWWW{}
\newcommand*{\tocompany}[1]{\def\RecipientCompany{#1}}
\def\RecipientCompany{}

\newcommand*{\subject}[1]{\def\Subject{#1}}
\def\Subject{}
\renewcommand*{\date}[1]{\def\Date{#1}}
\def\Date{\today}
\newcommand*{\pages}[1]{\def\Pages{#1}}
\def\Pages{}

\renewcommand{\opening}[1]{\def\Opening{#1}}
\def\Opening{\OpeningText}
\renewcommand{\closing}[1]{\def\Closing{#1}}
\def\Closing{\ClosingText}
\renewcommand{\signature}[1]{\def\Signature{#1}}
\def\Signature{\Sender}

\newcommand*{\Header}{
  \begin{tabular}{c}
    \textsf{\textbf{\Company}}\\
    \textsf{\textbf{\Department}}
  \end{tabular}
}

\newcommand*{\HeaderLogo}{
  \begin{tabular}{cc}
    &\textsf{\textbf{\Company}}\\
    &\textsf{\textbf{\Department}}\\
    \raisebox{0ex}[0pt]{\resizebox*{!}{1.5cm}{\includegraphics{\Logo}}} & \\
  \end{tabular}
}

\newcommand*{\Description}{
  \begin{tabular}{p{0.45\textwidth}cp{0.45\textwidth}}
  \textsf{\footnotesize\RecipientText}&&
    \textsf{\footnotesize\SenderText}\\
  ~~~\textsf{\textbf{\Recipient}}&&~~~\textsf{\textbf{\Sender}}\\
  \textsf{\footnotesize\RecipientFaxText}&&
    \textsf{\footnotesize\SenderFaxText}\\
  ~~~\textsf{\textbf{\RecipientFax}}&&~~~\textsf{\textbf{\SenderFax}}\\
  \ifx \RecipientPhone\empty \else
    \textsf{\footnotesize\RecipientPhoneText}&&
      \textsf{\footnotesize\SenderPhoneText}\\
    ~~~\textsf{\textbf{\RecipientPhone}}&&~~~\textsf{\textbf{\SenderPhone}}\\
  \fi
  \ifx \RecipientEMail\empty \else
    \textsf{\footnotesize\RecipientEMailText}&&
      \textsf{\footnotesize\SenderEMailText}\\
    ~~~\textsf{\textbf{\RecipientEMail}}&&~~~\textsf{\textbf{\SenderEMail}}\\
  \fi
  \ifx \RecipientWWW\empty \else
    \textsf{\footnotesize\RecipientWWWText}&&
      \textsf{\footnotesize\SenderWWWText}\\
    ~~~\textsf{\textbf{\RecipientWWW}}&&~~~\textsf{\textbf{\SenderWWW}}\\
  \fi
  \textsf{\footnotesize\RecipientCompanyText}&&
    \textsf{\footnotesize\DateText}\\
  ~~~\textsf{\textbf{\RecipientCompany}}&&~~~\textsf{\textbf{\Date}}\\
  \textsf{\footnotesize\SubjectText}&&
    \textsf{\footnotesize\PagesText}\\
  ~~~\textsf{\textbf{\Subject}}&&~~~\textsf{\textbf{\Pages}}\\
  \end{tabular}
}

% RecipientFax\empty\or Subject\Empty\or \Pages\empty
\newenvironment{fax}{
  \ifx \Sender\empty
    \ClassError{fax}{"from" is a mandatory field!}
  \fi
  \ifx \Recipient\empty
    \ClassError{fax}{"to" is a mandatory field!}
  \fi
  \ifx \RecipientFax\empty
    \ClassError{fax}{"tofax" is a mandatory field!}
  \fi
  \ifx \Pages\empty
    \ClassError{fax}{"pages" is a mandatory field!}
  \fi

  \clearpage
  \setlength\unitlength{1mm}
  \setlength\parindent{0mm}
  \setlength\parskip{1ex}

  \ifx \Company\empty \else
    \ifx \Logo\empty 
      \makebox[\textwidth][c]{\Header}
    \else
      \makebox[\textwidth][c]{\HeaderLogo}
    \fi
  \fi

  \vskip 1ex
  \rule{\textwidth}{1pt}
  \vskip 1ex
  \makebox[\textwidth][c]{\Description}
  \vskip 1ex
  \rule{\textwidth}{1pt}
  \vskip 1ex

  \Opening\par
}{
  \Closing\par
  \vskip 5em
  \noindent\makebox[\textwidth][c]{\Signature}
  \vfill
  \noindent\rule{\textwidth}{1pt}
  \clearpage
}

\endinput
