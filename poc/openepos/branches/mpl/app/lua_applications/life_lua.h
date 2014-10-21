//""CONWAY'S GAME OF LIFE WITH 100 GENERATIONS""""""""""""""""""""""""""""""/
#define	LUA_APP	"	ALIVE=\"¥\"	DEAD=\"þ\"									\
					ALIVE=\"O\"	DEAD=\"-\"									\
																			\
					function ARRAY2D(w,h)									\
						local t = {w=w,h=h}									\
						for y=1,h do										\
							t[y] = {}										\
							for x=1,w do									\
								t[y][x]=0									\
							end												\
						end													\
						return t											\
					end														\
																			\
					_CELLS = {}												\
																			\
					function _CELLS:spawn(shape,left,top)					\
						for y=0,shape.h-1 do								\
							for x=0,shape.w-1 do							\
								self[top+y][left+x] = shape[y*shape.w+x+1]	\
							end												\
						end													\
					end														\
																			\
					function _CELLS:evolve(next)							\
						local ym1,y,yp1,yi=self.h-1,self.h,1,self.h			\
						while yi > 0 do										\
							local xm1,x,xp1,xi=self.w-1,self.w,1,self.w		\
							while xi > 0 do									\
								local sum = self[ym1][xm1] + 				\
									self[ym1][x] + self[ym1][xp1] +			\
									self[y][xm1] + self[y][xp1] +			\
									self[yp1][xm1] + 						\
									self[yp1][x] + self[yp1][xp1]			\
								next[y][x] = ((sum==2) and self[y][x])		\
									or ((sum==3) and 1) or 0				\
								xm1,x,xp1,xi = x,xp1,xp1+1,xi-1				\
							end												\
							ym1,y,yp1,yi = y,yp1,yp1+1,yi-1					\
						end													\
					end														\
																			\
					function CELLS(w,h)										\
						local c = ARRAY2D(w,h)								\
						c.spawn = _CELLS.spawn								\
						c.evolve = _CELLS.evolve							\
						return c											\
					end														\
																			\
					HEART = { 1,0,1,1,0,1,1,1,1; w=3,h=3 }					\
					GLIDER = { 0,0,1,1,0,1,0,1,1; w=3,h=3 }					\
					EXPLODE = { 0,1,0,1,1,1,1,0,1,0,1,0; w=3,h=4 }			\
					FISH = { 0,1,1,1,1,1,0,0,0,1,0,0,0,0,1,1,0,0,1,0; 		\
						w=5,h=4 }											\
					BUTTERFLY = { 1,0,0,0,1,0,1,1,1,0,1,0,0,0,1,1,0,1,		\
						0,1,1,0,0,0,1; w=5,h=5 }							\
																			\
					function LIFE(w,h)										\
						local thisgen = CELLS(w,h)							\
						local nextgen = CELLS(w,h)							\
						thisgen:spawn(GLIDER,5,4)							\
						thisgen:spawn(EXPLODE,25,10)						\
						thisgen:spawn(FISH,4,12)							\
						local gen=1											\
						while 1 do											\
							thisgen:evolve(nextgen)							\
							thisgen,nextgen = nextgen,thisgen				\
							gen=gen+1										\
							if gen>100 then break end						\
						end													\
					end														\
																			\
					LIFE(40,20)												"

