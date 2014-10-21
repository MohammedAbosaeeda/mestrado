package nanovm.epos.mediator;

public class NIC {

    public static final int SENT_OK = 11;
   
    public static final int BROADCAST = 2;
    
    public NIC() {
        /* We do nothing here! It will be handle by the binding. */
    }


    public native int send(int dst, char data);

    /*
    public int send(Address dst, Protocol prot, char[] data, int size) {
        // We do nothing here! It will be handle by the binding.
        // NOTE: I think "size" (in the implementation) should be multiplied by the size of the type of "data"
        // NOTE: Until I understand prot it will not be used.
        // it will be fixed as 1 to make the TemperatureSensing application works.
        // NOTE: "dst" will be fixed to BROADCAST, just to make the TemperatureSensing application works.
        return 2;
    }
    */


    /* This is going to be a Native class as well. 
       Maybe the KESO or the Java compiler ask it to be a public class.
       Until there, it will be a internal class as it is.
    */
    class Address {
    }
    
   

}
