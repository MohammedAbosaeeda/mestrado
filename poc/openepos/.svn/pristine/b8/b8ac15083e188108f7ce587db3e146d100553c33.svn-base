//""IMPLEMENTS THE SIEVE OF ERATOSTHENES TO FIND PRIME NUMBERS UP TO N""""""/
#define	LUA_APP	"	function gen (n)										\
						return coroutine.wrap(function ()					\
							for i=2,n do coroutine.yield(i) end				\
						end)												\
					end														\
																			\
					function filter (p, g)									\
						return coroutine.wrap(function ()					\
							while 1 do										\
								local n = g()								\
								if n == nil then return end					\
								if math.mod(n, p) ~= 0 then					\
									coroutine.yield(n)						\
								end											\
							end												\
						end)												\
					end														\
																			\
					N = 1000												\
					x = gen(N)												\
					while 1 do												\
						local n = x()										\
						if n == nil then break end							\
						x = filter(n, x)									\
					end														"

