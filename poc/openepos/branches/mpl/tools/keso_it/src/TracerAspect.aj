aspect TracerAspect {

    private static final int indentLevelInc = 4;
    private int indentationLevel;

    public TracerAspect() {
        this.indentationLevel = 0;
    }
    
    private String spaces() {
        String s = "";

        for (int i = 0; i < indentationLevel; ++ i) {
            s += " "; 
        }

        return s;
    }

    pointcut method_or_constructor_call():
         call(* *.computeDominators(..));

    before() : method_or_constructor_call () {
        System.out.println(spaces() + thisJoinPoint + "{");
        indentationLevel += indentLevelInc;
    }

    after() : method_or_constructor_call () {
        indentationLevel -= indentLevelInc;
        System.out.println(spaces() + "}");        
    }

}

