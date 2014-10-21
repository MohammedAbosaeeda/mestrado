//""CALCULATES ALL FIBONACCI NUMBERS SMALLER THAN 1000""""""""""""""""""""""/
#define	LUA_APP		"	function generatefib (n)								\
						return coroutine.wrap(function ()					\
							local a,b = 1, 1								\
							while a <= n do									\
								coroutine.yield(a)							\
								a, b = b, a+b								\
							end												\
						end)												\
					end														\
																			\
					local j = 0												\
					for i in generatefib(1000) do j = j + i end				"

