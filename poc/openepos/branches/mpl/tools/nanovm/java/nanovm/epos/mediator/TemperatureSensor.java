package nanovm.epos.mediator;

public class TemperatureSensor {
    
    public TemperatureSensor() {
        /* We do nothing here! It will be handle by the binding. */
    }
    

    public native char sample();
    
}
