//""GETS CURRENT DATE, FORMATS A STRING AND PRINTS IT"""""""""""""""""""""""/
#define	LUA_APP	"	local _greeting = \"Hello\"								\
					local _white = \" \"									\
					local _addressee = \"World\"							\
					local _excitement = \"!\"								\
					local _endl = \"\\n\"									\
					local _message = \"The time right now is\"				\
					local _date = os.time()									\
																			\
					message = string.format(\"%s%s%s%s%s%s%s%s%s\",			\
						_greeting,_white,_addressee,						\
							_excitement,_endl,_message,						\
								_white,_date,_endl)							\
					print(message)											"

