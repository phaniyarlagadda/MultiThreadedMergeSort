package com.rationalcoding.sort.tests;

import java.util.Arrays;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rationalcoding.sort.MultiThreadedMergeSort;
import com.rationalcoding.sort.SequentialMergeSort;
import com.rationalcoding.sort.Sort;

/**
 * 
 * @author yarlagadda
 * 
 *         Automated test to validate the sequentialmergesorta nd
 *         multithreadedmerge sort against different inputs. To run this tests
 *         you need TestNG
 * 
 */
public class MergeSortTests {

	private int[] randomArray;
	private int largeArrayLength = 40000000;

	@DataProvider(name = "Sort-Type")
	public Object[][] sortTypeProvider() {
		return new Object[][] { { new SequentialMergeSort() }, { new MultiThreadedMergeSort() } };
	}

	@BeforeTest
	public void oneTimeSetUp() {
		// create a random array at start and use it for all sort types
		randomArray = new int[largeArrayLength];
		Random rnd = new Random();
		for (int i = 0; i < largeArrayLength; i++) {
			randomArray[i] = rnd.nextInt(largeArrayLength);
		}
	}

	@Test(dataProvider = "Sort-Type")
	public void testNullInput(Sort sortHandler) {
		try {
			sortHandler.sort(null);
			Assert.fail("Exception expected");
		} catch (IllegalArgumentException ex) {
			// expected
		}

	}

	@Test(dataProvider = "Sort-Type")
	public void testZeroLengthArray(Sort sortHandler) {

		sortHandler.sort(new int[0]);

	}

	@Test(dataProvider = "Sort-Type")
	public void testSingleElementArray(Sort sortHandler) {
		int[] input = { 2 };
		int[] expected = getExpectedSortedArray(input);
		sortHandler.sort(input);
		Assert.assertEquals(input, expected);
	}

	@Test(dataProvider = "Sort-Type")
	public void testSortedArray(Sort sortHandler) {
		int[] input = { 2, 3, 4, 5 };
		int[] expected = getExpectedSortedArray(input);
		sortHandler.sort(input);
		Assert.assertEquals(input, expected);
	}

	@Test(dataProvider = "Sort-Type")
	public void testEvenLength(Sort sortHandler) {
		int[] input = { 4, 2, 3, 9, 7, 8 };
		int[] expected = getExpectedSortedArray(input);
		sortHandler.sort(input);
		Assert.assertEquals(input, expected);
	}

	@Test(dataProvider = "Sort-Type")
	public void testOddLength(Sort sortHandler) {
		int[] input = { 4, 2, 3, 9, 7, 8, 11, 10, 1 };
		int[] expected = getExpectedSortedArray(input);
		sortHandler.sort(input);

		Assert.assertEquals(input, expected);
	}

	@Test(dataProvider = "Sort-Type")
	public void testArrayWithDuplicates(Sort sortHandler) {
		int[] input = { 4, 2, 3, 9, 7, 3, 8, 11, 2, 9, 10, 9 };
		sortHandler.sort(input);
		int[] expected = getExpectedSortedArray(input);
		Assert.assertEquals(input, expected);
	}

	@Test(dataProvider = "Sort-Type")
	public void testLargeRandomArray(Sort sortHandler) {

		int[] input = new int[randomArray.length];
		Arrays.copyOf(randomArray, randomArray.length);

		long startTime = System.currentTimeMillis();
		sortHandler.sort(input);
		long endTime = System.currentTimeMillis();
		int[] expected = getExpectedSortedArray(input);
		Assert.assertEquals(input, expected);
		System.out.println("Time to sort large random array of size " + input.length + " is "
				+ (endTime - startTime) + " milliseconds for type "
				+ sortHandler.getClass().getCanonicalName());
	}

	@Test(dataProvider = "Sort-Type")
	public void testLargeSortedArray(Sort sortHandler) {
		int[] input = new int[largeArrayLength];
		for (int i = 0; i < largeArrayLength; i++) {
			input[i] = i;
		}

		long startTime = System.currentTimeMillis();
		sortHandler.sort(input);
		long endTime = System.currentTimeMillis();
		int[] expected = getExpectedSortedArray(input);
		Assert.assertEquals(input, expected);
		System.out.println("Time to sort large sorted array of size " + input.length + " is "
				+ (endTime - startTime) + " milliseconds for type "
				+ sortHandler.getClass().getCanonicalName());
	}

	private int[] getExpectedSortedArray(final int[] input) {
		int[] expected = Arrays.copyOf(input, input.length);
		Arrays.sort(expected);
		return expected;
	}
}
