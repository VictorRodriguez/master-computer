/*
CSE 373, Winter 2013
This program measures the runtime of the merge sort algorithm,
in both a standard sequential version and a parallel version
that uses multiple threads.

*/

import java.util.*;   // for Random

public class MergeSort {
    
    // Arranges the elements of the given array into sorted order
    // using the "merge sort" algorithm, which splits the array in half,
    // recursively sorts the halves, then merges the sorted halves.
    // It is O(N log N) for all inputs.
    public static void mergeSort(int[] a) {
	if (a.length >= 2) {
	    // split array in half
	    int[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
	    int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);
	    
	    // sort the halves
	    mergeSort(left);
	    mergeSort(right);
	    
	    // merge them back together
	    merge(left, right, a);
	}
    }
    
    // Combines the contents of sorted left/right arrays into output array a.
    // Assumes that left.length + right.length == a.length.
    public static void merge(int[] left, int[] right, int[] a) {
	int i1 = 0;
	int i2 = 0;
	for (int i = 0; i < a.length; i++) {
	    if (i2 >= right.length || (i1 < left.length && left[i1] < right[i2])) {
		a[i] = left[i1];
		i1++;
	    } else {
		a[i] = right[i2];
		i2++;
	    }
	}
    }
	
    // Swaps the values at the two given indexes in the given array.
    public static final void swap(int[] a, int i, int j) {
	if (i != j) {
	    int temp = a[i];
	    a[i] = a[j];
	    a[j] = temp;
	}
    }
    
    // Randomly rearranges the elements of the given array.
    public static void shuffle(int[] a) {
	for (int i = 0; i < a.length; i++) {
	    // move element i to a random index in [i .. length-1]
	    int randomIndex = (int) (Math.random() * a.length - i);
	    swap(a, i, i + randomIndex);
	}
    }
    
    // Returns true if the given array is in sorted ascending order.
    public static boolean isSorted(int[] a) {
	for (int i = 0; i < a.length - 1; i++) {
	    if (a[i] > a[i + 1]) {
		return false;
	    }
	}
	return true;
    }
}
