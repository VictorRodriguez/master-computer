
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MergeSortRemote extends Remote {
    int[] parallelMergeSort(int[] a, int threadCount) throws RemoteException;
    int[] transfer(int[] a, int threadCount) throws RemoteException;
}
