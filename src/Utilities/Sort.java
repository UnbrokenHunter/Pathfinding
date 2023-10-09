package Utilities;

import java.util.Random;

public class Sort {

	/**
	 * This is a sorting algorithm with O(log(n)) time complexity. This will sort the entire array.
	 * 
	 * @param array The array you want sorted
	 */
	public static void quicksort(int[] array) {
		quicksort(array, 0, array.length - 1);
	}
	
	
	/**
	 * This is a sorting algorithm with O(log(n)) time complexity
	 * 
	 * @param array The array you want sorted
	 * @param lowIndex Where the left pointer starts
	 * @param highIndex  Where the right pointer starts
	 */
	public static void quicksort(int[] array, int lowIndex, int highIndex) {
		
		if(lowIndex >= highIndex) return;
		
		// Choosing a pivot
		int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
		int pivot = array[pivotIndex];
		swap(array, pivotIndex, highIndex);
		
		// Partitioning
		int leftPointer = partition(array, lowIndex, highIndex, pivot);
		
		// Recursively call until it is sorted
		quicksort(array, lowIndex, leftPointer -1);
		quicksort(array, leftPointer + 1, highIndex);
		
	}


	private static int partition(int[] array, int lowIndex, int highIndex, int pivot) {
		int leftPointer = lowIndex;
		int rightPointer = highIndex;
		
		// Stops when the pointers overlap
		while (leftPointer < rightPointer) {
			
			// Left poin6ter walks up the array
			while (array[leftPointer] <= pivot && leftPointer < rightPointer) {
				leftPointer++;
			}
			
			// Right pointer walks down the array
			while (array[rightPointer] >= pivot && leftPointer < rightPointer) {
				rightPointer--;
			}
			
			swap(array, leftPointer, rightPointer);
			
		}
		
		swap(array, leftPointer, highIndex);
		return leftPointer;
	}
	
	
	/*
	 * Swaps the values of the indices
	 */
	private static void swap(int[] array, int index1, int index2) {
		
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
		
	}
	
}
