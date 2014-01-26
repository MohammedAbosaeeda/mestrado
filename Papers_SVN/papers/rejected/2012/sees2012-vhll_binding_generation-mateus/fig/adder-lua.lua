Adder = {}
function Adder:new(o)
    o = o or {}
    setmetatable(o, self)
    self.__index = self
    return o
end

function Adder:sum(a, b)
    return native_sum(a, b)
end


function main()
    a = Adder:new{}
    s = a:sum(2,3)
    print("sum: ", s)    
end


main()

