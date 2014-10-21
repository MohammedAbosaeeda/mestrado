// EPOS-- File Stream Interface

#ifndef __fstream_h
#define __fstream_h

#include <system/config.h>
#include <utility/debug.h>

__BEGIN_SYS

#define		EOF			(-1)
#define		SEEK_SET	0
#define		SEEK_CUR	1
#define		SEEK_END	2
#define		_IOFBF		0
#define		_IOLBF		1
#define		_IONBF		2
#define		L_tmpnam	20
#define		stdout		0

struct FILE {
    int             level;      /* fill/empty level of buffer */
    unsigned        flags;      /* File status flags          */
    char            fd;         /* File descriptor            */
    unsigned char   hold;       /* Ungetc char if no buffer   */
    int             bsize;      /* Buffer size                */
    unsigned char   *buffer;    /* Data transfer buffer       */
    unsigned char   *curp;      /* Current active pointer     */
    unsigned        istemp;     /* Temporary file indicator   */
    short           token;      /* Used for validity checking */
};



class FStream
{
public:
	static FILE * stdin;
	static FILE * stderr;
	
	static int feof(FILE *fp){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return 1;
	}
	
	static unsigned int fread (void * ptr, unsigned int size, unsigned int nmemb, FILE * stream){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return 0;
	}
	
	static FILE *fopen(const char *path, const char *mode){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return 0;
	}
	
	static FILE *freopen(const char *path, const char *mode, FILE *fp){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return 0;
	}
	
	static int fclose(FILE *fp){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return 0;
	}
	
	static int ferror (FILE *stream){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return 1;
	}
	
	static int getc (FILE *stream){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return EOF;
	}
	
	static int ungetc (int c, FILE *stream){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return EOF;
	}
	
	static int fputs (const char * s, FILE * stream){
		if (stream == stdout){
			ostream << s;
			return 1;
		}else{
			db<FStream>(ERR) << "FStream nao implementado.\n";
			return EOF;
		}
	}
	
	static char* fgets(char* s, int n, FILE* stream){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return 0;
	}
	
	static FILE* tmpfile(){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return 0;
	}
	
	static int fscanf(FILE* stream, const char* format, ...){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return 0;
	}
	
	static void clearerr(FILE* stream){
		db<FStream>(ERR) << "FStream nao implementado.\n";

	}
	
	static unsigned int fwrite(const char* s, unsigned int size, unsigned int nobj, FILE* stream){
		if (stream == stdout){
			unsigned int i, j, k;
			k = 0;
			for (i = 0; i < nobj; i++)
				for (j = 0; j < size; j++){
					ostream << s[k];
					k++;
				}
			return nobj;
		}else{
			db<FStream>(ERR) << "FStream nao implementado.\n";
			return 0;
		}
	}
	
	static int fseek(FILE* stream, long offset, int origin){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return -1;
	}
	
	static long ftell(FILE* stream){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return -1;
	}
	
	static int setvbuf(FILE* stream, char* buf, int mode, unsigned int size){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return 1;
	}
	
	static int fflush(FILE* stream){
		if (stream != stdout)
			db<FStream>(ERR) << "FStream nao implementado.\n";
		return EOF;
	}
	
	static int rename (const char *oldname, const char *newname){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return -1;
	}
	
	static int remove (const char *filename){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return -1;
	}
	
	static char *tmpnam(char *s){
		db<FStream>(ERR) << "FStream nao implementado.\n";
		return 0;
	}
	
private:
	static OStream ostream;
}; 

#define		stdin		FStream::stdin
#define		stderr		FStream::stderr


__END_SYS

#endif

