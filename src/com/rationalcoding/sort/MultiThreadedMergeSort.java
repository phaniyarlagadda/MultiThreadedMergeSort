package com.rationalcoding.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * @author Phani
 * 
 *         This is implementation of a multi threaded merge sort. First input
 *         array is divided into small chunks. Each chunk is sorted individually
 *         using Arrays.sort method which implements insertion sort Then all the
 *         chunks are merged in bottom up manner by doubling the width after
 *         each step
 * 
 */
public class MultiThreadedMergeSort extends SequentialMergeSort {

	private final int DEFAULT_POOL_SIZE = 100;
	private final int DEFAULT_CHUNK_SIZE = 1000;
	private ExecutorService pool = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
	private int[] arr;

	@SuppressWarnings("rawtypes")
	@Override
	public void sort(int[] arr) {
		this.arr = arr;

		// handle null inputs
		if (arr == null) {
			throw new IllegalArgumentException("Input array cannot be null");
		}

		if (arr.length == 0 || arr.length == 1) {
			// already sorted return
			return;
		}

		// width is chunks we are diving the array into
		int width = DEFAULT_CHUNK_SIZE;
		if (width > 1) {
			// apply insertion sort on chunks and then merge the chunks
			ArrayList<Future> subTasks = new ArrayList<Future>();
			for (int i = 0; i < arr.length; i = i + width) {
				int start = i;
				int end = i + width;
				if (end > arr.length) {
					end = arr.length;
				}
				// add the runnables to pool
				subTasks.add(pool.submit(new InsertionSortRunnable(start, end)));
			}

			// wait for the tasks to finish
			// join all the threads to main thread
			for (Future f : subTasks) {
				try {
					f.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}

		// apply merging on already sorted chunks
		for (; width < arr.length; width = width * 2) {
			ArrayList<Future> tasks = new ArrayList<Future>();
			for (int i = 0; i < arr.length; i = i + 2 * width) {
				int rStart = i;
				int rEnd = i + width - 1;
				int lEnd = i + 2 * width - 1;
				if (lEnd >= arr.length) {
					lEnd = arr.length - 1;
				}
				tasks.add(pool.submit(new MergeSortRunnable(rStart, rEnd, lEnd)));
			}
			// wait for all threads to finish
			for (Future f : tasks) {
				try {
					f.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}

		}

	}

	private class InsertionSortRunnable implements Runnable {
		private int start;
		private int end;

		public InsertionSortRunnable(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			Arrays.sort(arr, start, end);
		}

	}

	private class MergeSortRunnable implements Runnable {

		private int start;
		private int end;
		private int mid;

		public MergeSortRunnable(int start, int mid, int end) {
			this.start = start;
			this.end = end;
			this.mid = mid;
		}

		@Override
		public void run() {
			if (start < end && mid <= end) {
				merge(arr, start, mid, end);
			}

		}

	}

}
