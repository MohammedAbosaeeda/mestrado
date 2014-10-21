//""CALCULATES THE FACTORIAL OF NUMBERS BETWEEN 0 AND 10""""""""""""""""""""/
#define	LUA_APP		"	Y = function (g)										\
						local a = function (f) return f(f) end				\
						return a(function (f)								\
							return g(function (x)							\
								local c=f(f)								\
								return c(x)									\
								end)										\
							end)											\
						end													\
																			\
					F = function (f)										\
						return function (n)									\
							if n == 0 then return 1							\
							else return n*f(n-1) end						\
							end												\
						end													\
																			\
					factorial = Y(F)										\
																			\
					local aux												\
																			\
					function test(x)										\
						aux = factorial(x)									\
					end														\
																			\
					for n=0,10 do											\
						test(n)												\
					end														"

