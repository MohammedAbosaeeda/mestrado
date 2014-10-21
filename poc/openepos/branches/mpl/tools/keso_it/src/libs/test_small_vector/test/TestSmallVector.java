package test;

import keso.core.Task;

public class TestSmallVector extends Task {
    
    private void printArray(int anArray[]) {
        DebugOut.print("{");
        for (int i = 0; i < anArray.length - 1; ++ i) {
            DebugOut.print(anArray[i] + ",");
        }
        DebugOut.println(anArray[anArray.length - 1] + "}");
    }
    
    public void launch() {
        int alpha[] = {1, 3 ,5};
        int beta[] = {0, 2 ,4};
        
        int gamma[] = new int[alpha.length];
        SmallVector.sum(gamma, alpha, beta);        
        
        printArray(gamma);
    }    
}
