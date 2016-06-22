public class QuickSort{

        public int[] quickSort(int array[], int start, int end)
        {
                int i = start;                          // index of left-to-right scan
                int k = end;                            // index of right-to-left scan

                if (end - start >= 1)                   // check that there are at least two elements to sort
                {
                        int pivot = array[start];       // set the pivot as the first element in the partition

                        while (k > i)                   // while the scan indices from left and right have not met,
                        {
                                while (array[i] <= pivot && i <= end && k > i)  // from the left, look for the first
                                        i++;                                    // element greater than the pivot
                                while (array[k] > pivot && k >= start && k >= i) // from the right, look for the first
                                    k--;                                        // element not greater than the pivot
                                if (k > i)                                       // if the left seekindex is still smaller than
                                        swap(array, i, k);                      // the right index, swap the corresponding elements
                        }
                        swap(array, start, k);          // after the indices have crossed, swap the last element in
                                                        // the left partition with the pivot 
                        quickSort(array, start, k - 1); // quicksort the left partition
                        quickSort(array, k + 1, end);   // quicksort the right partition
                }
                else    // if there is only one element in the partition, do not do any sorting
                {
                        return array;
                }
                return array;
        }

        public void swap(int array[], int index1, int index2) 
        {
	        int temp = array[index1];           // store the first value in a temp
	        array[index1] = array[index2];      // copy the value of the second into the first
	        array[index2] = temp;               // copy the value of the temp into the second
        }
}
