import java.rmi.Naming;
import java.util.Date;
import java.util.concurrent.TimeUnit;

// BEGIN main
/* A very simple client for the RemoteSort service. */
public class Client {

	/** The local proxy for the service. */
	protected static RemoteSort netConn = null;

	public static void main(String[] args) {


        int[] int_array = {1};
        String[] string_array = {""};
        
        if (args.length > 0) {
            String stripppedString = args[0].trim();
            stripppedString = stripppedString.replaceAll("^\"+", "").replaceAll("\"+$", "");
            string_array = stripppedString.split(",");
            int_array = new int[string_array.length];
            

            for (int i = 0; i < int_array.length; i++) {
                try {
                int_array[i] = Integer.parseInt(string_array[i]);
                } catch (NumberFormatException nfe) {};
           }
        }

	try {
	        netConn = (RemoteSort)Naming.lookup(RemoteSort.LOOKUPNAME);
                //Call the quick sort method
		int[] return_array = netConn.getRemoteSort(int_array);
                System.out.println("\n");
                for (int i=0; i<return_array.length; i++){
                    System.out.print(return_array[i] + "-");
                }
	} catch (Exception e) {
		System.err.println("RemoteSort exception: " + e.getMessage());
		e.printStackTrace();
	        }
	}
}
