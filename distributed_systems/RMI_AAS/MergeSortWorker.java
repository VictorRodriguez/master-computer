
import java.util.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.net.InetAddress;
    
public class MergeSortWorker implements MergeSortRemote {

    public MergeSortWorker() {
    }

    public int[] transfer(int[] a, int threadCount) {
	return a;
    }
	
    public int[] parallelMergeSort(int[] a, int threadCount) {
	if (threadCount <= 1) {
	    long startTime = System.currentTimeMillis();
	    MergeSort.mergeSort(a);
	    long endTime = System.currentTimeMillis();
	    //System.out.println("Time: "+(endTime - startTime));
	} else if (a.length >= 2) {
	    // split array in half
	    int[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
	    int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);

	    // sort the halves
	    Thread lThread = new Thread(new WorkerSorter(left,  threadCount / 2));
	    Thread rThread = new Thread(new WorkerSorter(right, threadCount / 2));
	    lThread.start();
	    rThread.start();

	    try {
		lThread.join();
		rThread.join();
	    } catch (InterruptedException ie) {}

	    // merge them back together
	    MergeSort.merge(left, right, a);
	}
	return a;
    }
    public static void main(String args[]) {
	
	try {
	    MergeSortWorker obj = new MergeSortWorker();
	    MergeSortRemote stub = (MergeSortRemote) UnicastRemoteObject.exportObject(obj, 0);

	    // Bind the remote object's stub in the registry
	    Registry registry = LocateRegistry.getRegistry();
	    registry.bind("MergeSortRemote", stub);

	    System.err.println("Worker ready");
	} catch (Exception e) {
	    System.err.println("Worker exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}

class WorkerSorter implements Runnable {

    private int[] a;
    private int threadCount;

    public WorkerSorter(int[] a, int threadCount) {
	this.a = a;
	this.threadCount = threadCount;
    }
    
    public void run() {
	try {
	    MergeSortRemote mergeSort = new MergeSortWorker();
	    mergeSort.parallelMergeSort(a, threadCount);
	} catch (Exception e) {
	    System.err.println("Worker exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}
