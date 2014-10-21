namespace str {
    
    char* strCpy(char* destination,const char *origin);
    char* strCpy(char* dest,int beginOrigin,int endOrigin,const char* origin,int beginDest,int endDest);
    int getIndex(char x,const char* str);
    bool strEquals (const char* str1,const char* str2,int begin,int endInclusive);
    bool strEquals (const char* str1,const char* str2);
    int strLen(const char* str);
        
    class Tokenizer {
    private:
        const char* _str;
        int _length;
        int _currentIndex;
        char* _nextToken;
        char _separator;
    public:
        Tokenizer(const char* str, char separator);
        bool hasNext();
        char* nextToken();
        
    };
    
    //The following part should be in stringUtil.cc
    int strcpy(char* destination,const char *origin){
        int i=0;
        do{
            destination[i]=origin[i];
        }while(origin[i++]!='\0');
        return i;
    }
    int strcpy(char* dest,int beginOrigin,int endOrigin,const char* origin,int beginDest,int endDest){
        int i;
        for(i=0;i<=(endOrigin-beginOrigin);i++) 
            dest[i+beginDest]=origin[i+beginOrigin];
        dest[i]='\0';
        return i;
    }
    int strlen(const char* str){
        int i;
        for(i=0;str[i]!='\0';i++);
        return i;
    }
    int getIndex(char x,const char* str){
        for(int i=0;str[i]!=x;i++)
            if(str[i]==x)
                return i;
        return -1;
    }
    bool strEquals (const char* str1,const char* str2,int begin,int endInclusive){
        for(int i=begin;i<=endInclusive;i++)
            if(str1[i]!=str2[i]) return false;
        return true;
    }
    bool strEquals (const char* str1,const char* str2){
        int i;
        for(i=0;(str1[i]==str2[i])&&(str1[i]!='\0');i++);
        return str1[i]==str2[i];        
    }
    
    //---------------------------------------------------------------    
    //Class Tokenizer: Methods implementation
    Tokenizer::Tokenizer(const char* str, char separator){
        _str=str;
        _separator=separator;
        _currentIndex=0;
        _nextToken=0;
        _length=strlen(str);
    }
    bool Tokenizer::hasNext(){
        if(_currentIndex < _length){
            int i=_currentIndex;
            //Remove separators before token
            while((_str[i]==_separator)&&(i<_length))
                i++;
            if(i<_length)
                return true;
        }
        return false;       
    }
    char* Tokenizer::nextToken(){
        if(hasNext())
        {
            //Remove all separators before token
            while((_str[_currentIndex]==_separator)&&(_currentIndex<_length))
                _currentIndex++;
            
            int tokenBegin=_currentIndex;
            //Advances _currentIndex to the end of this token
            while((_str[_currentIndex]!=_separator)&&(_currentIndex<_length))
                _currentIndex++;
            _nextToken=new char[_currentIndex-tokenBegin+1];
            for(int i=tokenBegin;i<_currentIndex;i++)
                _nextToken[i-tokenBegin]=_str[i];
            _nextToken[_currentIndex]='\0';
            return _nextToken;
        }
        return 0;   
    }
//---------------------------------------------------------------

    
};
