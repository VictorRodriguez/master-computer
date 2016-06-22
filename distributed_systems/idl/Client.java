import IDL_module.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.*;

public class Client
{
    static IDL_interface helloImpl;

    public static void main(String args[])
    {
        int n = 1;
	int id = 0;

        Random random = new Random();
        if (args.length > 0) {
            try {
		id = Integer.parseInt(args[0]);
		n = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
		System.err.println("Argument" + args[0] + " must be an integer.");
		System.exit(1);
            }
        }

	int [] data = new int[n*n + 2];

	// Setting Client ID
	data[0] = id;
	// Setting Matrix N value
	data[1] = n;
	
	for( int i = 2 ; i < data.length ; i++ ) {
	    data[i] = random.nextInt(101);
	}
     
	//for( int[] a : array ) {
	//    System.out.println( Arrays.toString( a ));
	//}

	try{
	    // create and initialize the ORB
	    ORB orb = ORB.init(args, null);

	    // get the root naming context
	    org.omg.CORBA.Object objRef = 
		orb.resolve_initial_references("NameService");
	    
	    // Use NamingContextExt instead of NamingContext. This is 
	    // part of the Interoperable naming Service.  
	    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
 
	    // resolve the Object Reference in Naming
	    String name = "Hello";
	    helloImpl = IDL_interfaceHelper.narrow(ncRef.resolve_str(name));

	    //System.out.println("Obtained a handle on server object: " + helloImpl);
            int result[][] = new int[0][0];
	    while (result.length == 0)
		result = helloImpl.sendMatrix(data);

	    System.out.println("\nResult from Server: \n");
	    for( int[] row : result)
		System.out.println(Arrays.toString(row));
	    
            //helloImpl.shutdown();

        } catch (Exception e) {
	    System.out.println("ERROR : " + e) ;
	    e.printStackTrace(System.out);
	}
    }
}
 
