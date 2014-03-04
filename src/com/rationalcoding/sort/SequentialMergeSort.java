package com.rationalcoding.sort;

import java.util.Arrays;

/**
 * 
 * @author yarlagadda
 * 
 *         This is implementation of simple merge sort.
 * 
 */

public class SequentialMergeSort implements Sort {

	@Override
	public void sort(int[] arr) {

		// handle null inputs
		if (arr == null) {
			throw new IllegalArgumentException("Input array cannot be null");
		}

		if (arr.length == 0 || arr.length == 1) {
			// already sorted return
			return;
		}

		int start = 0;
		int end = arr.length - 1;
		mergeSort(arr, start, end);
	}

	// helper method for merge sort
	protected void mergeSort(int[] arr, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2;
			mergeSort(arr, start, mid);
			mergeSort(arr, mid + 1, end);
			merge(arr, start, mid, end);
		}
	}

	protected void merge(int[] arr, int start, int mid, int end) {

		// copy the left half into left array
		int[] leftArray = Arrays.copyOfRange(arr, start, mid + 1);
		// copy right half into right array
		int[] rightArray = Arrays.copyOfRange(arr, mid + 1, end + 1);
		int leftArrayPtr = 0, rightArrayPtr = 0, inputArrayPtr = start;
		// merge until we reach end of either one of the arrays
		for (; leftArrayPtr < leftArray.length && rightArrayPtr < rightArray.length; inputArrayPtr++) {

			if (leftArray[leftArrayPtr] <= rightArray[rightArrayPtr]) {
				arr[inputArrayPtr] = leftArray[leftArrayPtr];
				leftArrayPtr++;
			} else {
				arr[inputArrayPtr] = rightArray[rightArrayPtr];
				rightArrayPtr++;
			}
		}

		// finish up if there are any remaining elements
		for (; leftArrayPtr < leftArray.length; leftArrayPtr++, inputArrayPtr++) {
			arr[inputArrayPtr] = leftArray[leftArrayPtr];
		}
		for (; rightArrayPtr < rightArray.length; rightArrayPtr++, inputArrayPtr++) {
			arr[inputArrayPtr] = rightArray[rightArrayPtr];
		}
	}

}
