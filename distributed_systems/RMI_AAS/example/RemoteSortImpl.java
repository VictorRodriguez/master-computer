import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


// BEGIN main
/** A statement of what the client & server must agree upon. */
interface RemoteSort extends java.rmi.Remote {

	/** The method used to get the Sort on the remote */
	public int[] getRemoteSort(int[] array) throws java.rmi.RemoteException;

	/** The name used in the RMI registry service. */
	public final static String LOOKUPNAME = "RemoteSort";
}


// BEGIN main
public class RemoteSortImpl extends UnicastRemoteObject implements RemoteSort {

	/** Construct the object that implements the remote server.
	 * Called from main, after it has the SecurityManager in place.
	 */
	public RemoteSortImpl() throws RemoteException {
		super();	// sets up networking
	}

	/** The remote method that "does all the work". This won't get
	 * called until the client starts up.
	 */
	public int[] getRemoteSort(int[] array) throws RemoteException {
	        System.out.printf("[Server]: Server receive  \n");
                QuickSort sorter = new QuickSort();
                sorter.quickSort(array,0,array.length -1);
		return array;
	}
}
// END main
