/*
** $Id: llex.h,v 1.58.1.1 2007/12/27 13:02:25 roberto Exp $
** Lexical Analyzer
** See Copyright Notice in lua.h
*/

#ifndef llex_h
#define llex_h

#include <lua/lobject.h>
#include <lua/lzio.h>


#define FIRST_RESERVED	257
#define UCHAR_MAX		255

/* maximum length of a reserved word */
#define TOKEN_LEN	(sizeof("function")/sizeof(char))


/*
* WARNING: if you change the order of this enumeration,
* grep "ORDER RESERVED"
*/
enum RESERVED {
  /* terminal symbols denoted by reserved words */
  TK_AND = FIRST_RESERVED,				/*257*/
  TK_BREAK,								/*258*/
  TK_DO,								/*259*/
  TK_ELSE,								/*260*/
  TK_ELSEIF,							/*261*/
  TK_END,								/*262*/
  TK_FALSE,								/*263*/
  TK_FOR,								/*264*/
  TK_FUNCTION,							/*265*/
  TK_IF,								/*266*/
  TK_IN,								/*267*/
  TK_LOCAL,								/*268*/
  TK_NIL,								/*269*/
  TK_NOT,								/*270*/
  TK_OR,								/*271*/
  TK_REPEAT,							/*272*/
  TK_RETURN,							/*273*/
  TK_THEN,								/*274*/
  TK_TRUE,								/*275*/
  TK_UNTIL,								/*276*/
  TK_WHILE,								/*277*/
  /* other terminal symbols */
  TK_CONCAT,							/*278*/
  TK_DOTS,								/*279*/
  TK_EQ,								/*280*/
  TK_GE,								/*281*/
  TK_LE,								/*282*/
  TK_NE,								/*283*/
  TK_NUMBER,							/*284*/
  TK_NAME,								/*285*/
  TK_STRING,							/*286*/
  TK_EOS								/*287*/
};

/* number of reserved words */
#define NUM_RESERVED	(cast(int, TK_WHILE-FIRST_RESERVED+1))


/* array with token `names' */
LUAI_DATA const char *const luaX_tokens [];


typedef union {
  lua_Number r;
  TString *ts;
} SemInfo;  /* semantics information */


typedef struct Token {
  int token;
  SemInfo seminfo;
} Token;


typedef struct LexState {
  int current;  /* current character (charint) */
  int linenumber;  /* input line counter */
  int lastline;  /* line of last token `consumed' */
  Token t;  /* current token */
  Token lookahead;  /* look ahead token */
  struct FuncState *fs;  /* `FuncState' is private to the parser */
  struct lua_State *L;
  ZIO *z;  /* input stream */
  Mbuffer *buff;  /* buffer for tokens */
  TString *source;  /* current source name */
  char decpoint;  /* locale decimal point */
} LexState;


LUAI_FUNC void luaX_init (lua_State *L);
LUAI_FUNC void luaX_setinput (lua_State *L, LexState *ls, ZIO *z,
                              TString *source);
LUAI_FUNC TString *luaX_newstring (LexState *ls, const char *str, size_t l);
LUAI_FUNC void luaX_next (LexState *ls);
LUAI_FUNC void luaX_lookahead (LexState *ls);
LUAI_FUNC void luaX_lexerror (LexState *ls, const char *msg, int token);
LUAI_FUNC void luaX_syntaxerror (LexState *ls, const char *s);
LUAI_FUNC const char *luaX_token2str (LexState *ls, int token);


#endif
