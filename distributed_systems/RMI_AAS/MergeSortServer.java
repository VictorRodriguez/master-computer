
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MergeSortServer {
    
    private static final Random RAND = new Random(42);   // random number generator
    public static List<Worker> workers = new ArrayList<Worker>();
    public static int WORKERS = 8;
    public static int THREADS = 8;
    public static long transferTime;

    // Initialize list of available workers in the Local Networks
    // Using the 10.0.0.x subnet
    private static void initializeWorkers(int workersCount){
	for(int i=11; i<=workersCount+10; i++){
	    workers.add(new Worker(i, "10.0.0."+i, 5000));
	}
    }

    private static void freeWorkers(){
	for(Worker worker : workers)
	    worker.busy = false;
    }

    private static Worker getFreeWorker(){
	for(Worker worker : workers)
	    if(!worker.busy){
		worker.busy = true;
		return worker;
	    }
	return null;
    }

    // Creates an array of the given length, fills it with random
    // non-negative integers, and returns it.
    public static int[] createRandomArray(int length) {
	int[] a = new int[length];
	for (int i = 0; i < a.length; i++) {
	    a[i] = RAND.nextInt(1000000);
	}
	return a;
    }

    public static void parallelMergeSort(int[] a) {
	parallelMergeSort(a, WORKERS);
    }

    public static void measureTransfer(int[] a, int workerCount, Boolean order) {
	if (workerCount <= 1) {
	    synchronized(workers){
		try {
		    Worker worker = getFreeWorker();
		    Registry registry = LocateRegistry.getRegistry(worker.ip);
		    MergeSortRemote stub = (MergeSortRemote) registry.lookup("MergeSortRemote");
		    int[] response = stub.transfer(a, THREADS/WORKERS);

		} catch(Exception e) {
		    System.err.println("Worker exception: " + e.toString());
		    e.printStackTrace();
		}
	    }
	} else if (a.length >= 2) {
	    // split array in half
	    int[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
	    int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);

	    // sort the halves
	    Thread lThread = new Thread(new ServerSorter(left,  workerCount / 2, order));
	    Thread rThread = new Thread(new ServerSorter(right, workerCount / 2, order));
	    lThread.start();
	    rThread.start();

	    try {
		lThread.join();
		rThread.join();
	    } catch (InterruptedException ie) {}
	}
    }
    
    public static void parallelMergeSort(int[] a, int workerCount) {
	if (workerCount <= 1) {
	    synchronized(workers){
		try {
		    Worker worker = getFreeWorker();
		    Registry registry = LocateRegistry.getRegistry(worker.ip);
		    MergeSortRemote stub = (MergeSortRemote) registry.lookup("MergeSortRemote");

		    long startTime = System.currentTimeMillis();
		    int[] response = stub.parallelMergeSort(a, THREADS/WORKERS);
		    long endTime = System.currentTimeMillis();

		    int count = 0;
		    for (int i : response)
			a[count++] = i;
		} catch(Exception e) {
		    System.err.println("Worker exception: " + e.toString());
		    e.printStackTrace();
		    }		    
	    }
	} else if (a.length >= 2) {
	    // split array in half
	    int[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
	    int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);

	    // sort the halves
	    Thread lThread = new Thread(new ServerSorter(left,  workerCount / 2));
	    Thread rThread = new Thread(new ServerSorter(right, workerCount / 2));
	    lThread.start();
	    rThread.start();
	    
	    try {
		lThread.join();
		rThread.join();
	    } catch (InterruptedException ie) {}

	    // merge them back together
	    MergeSort.merge(left, right, a);
	}
    }

    public static void main(String[] args) throws Throwable {
	int LENGTH = 1000000;   // initial length of array to sort
	int RUNS   = 1;   // how many times to grow by 1000000?

	if(args.length == 0) {
	    System.out.println("Proper Usage is: java MergeSortServer <CLIENTS> <ARRAY_LENGHT>");
	    System.exit(0);
	} else {
	    THREADS = Integer.parseInt(args[0]);
	    LENGTH = Integer.parseInt(args[1]);
	}

	initializeWorkers(WORKERS);

	for (int i = 1; i <= RUNS; i++) {
	    int[] a = createRandomArray(LENGTH);

	    // run the algorithm and time how long it takes
	    long startTime1 = System.currentTimeMillis();
	    parallelMergeSort(a, WORKERS);
	    long endTime1 = System.currentTimeMillis();

	    freeWorkers();

	    // Measuring Transfer time between Server and Workers
	    long startTransfer = System.currentTimeMillis();
	    measureTransfer(a, WORKERS, false);
	    long endTransfer = System.currentTimeMillis();

	    freeWorkers();
	    
	    if (!MergeSort.isSorted(a)) {
	    	throw new RuntimeException("not sorted afterward: " + Arrays.toString(a));
	    }
	    System.out.printf("%10d %10d %6d %6d \n", THREADS, LENGTH, endTime1 - startTime1,
			      (endTransfer - startTransfer)/2);
	    LENGTH += 1000000;   
	}
    }
}

class ServerSorter implements Runnable {

    private int[] a;
    private int threadCount;
    private Boolean order = true;

    public ServerSorter(int[] a, int threadCount) {
	this.a = a;
	this.threadCount = threadCount;
    }

    public ServerSorter(int[] a, int threadCount, Boolean order) {
	this.a = a;
	this.threadCount = threadCount;
	this.order = order;
    }
    
    public void run() {
	if (order)
	    MergeSortServer.parallelMergeSort(a, threadCount);
	else
	    MergeSortServer.measureTransfer(a, threadCount, order);
    }
}
