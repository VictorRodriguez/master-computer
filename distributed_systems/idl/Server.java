import IDL_module.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.*;

class Impl extends IDL_interfacePOA {

    private ORB orb;
    private int[][] finalResult = new int[0][0];
    private ClientData client1 = new ClientData();
    private ClientData client2 = new ClientData(); 
    
    public void setORB(ORB orb_val) {
	orb = orb_val; 
    }
    
    public void shutdown() {
	orb.shutdown(false);
    }

    public int[][] sendMatrix(int data[]){
	
	int result[][] = new int[0][0];
	ClientData newClient = new ClientData(data);

	if (client1.getId() == 0){
	    client1 = newClient;
	} else if (client2.getId() == 0 && client1.getId() != newClient.getId()){
	    client2 = newClient;
	    finalResult = MatrixOps.ikjMultiply(client1.getMatrix(), client2.getMatrix());
	    result = finalResult;
	} else {
	    result = finalResult;
	    finalResult = new int[0][0];
	    client1 = new ClientData();
	    client2 = new ClientData();
	}
	
	return result; 
    }
}


public class Server {
    
    public static void main(String args[]) {
	try{
	    ORB orb = ORB.init(args, null);

	    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
	    rootpoa.the_POAManager().activate();

	    Impl IDL_impl = new Impl();
	    IDL_impl.setORB(orb); 

	    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(IDL_impl);
      
	    IDL_interface href = IDL_interfaceHelper.narrow(ref);
          
	    org.omg.CORBA.Object objRef =
		orb.resolve_initial_references("NameService");
	    
	    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

	    String name = "Hello";
	    NameComponent path[] = ncRef.to_name( name );
	    ncRef.rebind(path, href);

	    System.out.println("HelloServer ready and waiting ...");

	    orb.run();
	} 
        
	catch (Exception e) {
	    System.err.println("ERROR: " + e);
	    e.printStackTrace(System.out);
	}
          
	System.out.println("HelloServer Exiting ...");
        
    }
}
