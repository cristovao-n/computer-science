package sorting.divideAndConquer.hybridMergesort;

import java.util.Arrays;

import sorting.AbstractSorting;
import util.Util;

/**
 * A classe HybridMergeSort representa a implementação de uma variação do
 * MergeSort que pode fazer uso do InsertionSort (um algoritmo híbrido) da
 * seguinte forma: o MergeSort é aplicado a entradas maiores a um determinado
 * limite. Caso a entrada tenha tamanho menor ou igual ao limite o algoritmo usa
 * o InsertionSort.
 * 
 * A implementação híbrida deve considerar os seguintes detalhes:
 * - Ter contadores das quantidades de MergeSorts e InsertionSorts aplicados, de
 * forma
 * que essa informação possa ser capturada pelo teste.
 * - A cada chamado do método de sort(T[] array) esses contadores são resetados.
 * E a cada chamada
 * interna de um merge ou insertion, os contadores MERGESORT_APPLICATIONS e
 * INSERTIONSORT_APPLICATIONS são incrementados.
 * - O InsertionSort utilizado no algoritmo híbrido deve ser in-place.
 */
public class HybridMergeSort<T extends Comparable<T>> extends
		AbstractSorting<T> {

	/**
	 * For inputs with size less or equal to this value, the insertionsort
	 * algorithm will be used instead of the mergesort.
	 */
	public static final int SIZE_LIMIT = 4;

	protected static int MERGESORT_APPLICATIONS = 0;
	protected static int INSERTIONSORT_APPLICATIONS = 0;

	public void sort(T[] array, int leftIndex, int rightIndex) {
		boolean shouldUseInsertionSort = array.length <= HybridMergeSort.SIZE_LIMIT;
		if (shouldUseInsertionSort) {
			this.insertionSort(array, leftIndex, rightIndex);
		} else {
			this.mergeSort(array, leftIndex, rightIndex);
		}

	}

	private void mergeSort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex >= rightIndex || array.length <= 1) {
			return;
		}

		HybridMergeSort.MERGESORT_APPLICATIONS += 1;
		int middleIndex = (leftIndex + rightIndex) / 2;
		this.mergeSort(array, leftIndex, middleIndex);
		this.mergeSort(array, middleIndex + 1, rightIndex);
		this.merge(array, leftIndex, middleIndex, rightIndex);

	}

	private void merge(T[] array, int leftIndex, int middleIndex, int rightIndex) {
		T[] baseArray = Arrays.copyOf(array, array.length);

		int i = leftIndex;
		int j = middleIndex + 1;
		int k = leftIndex;

		while (i <= middleIndex && j <= rightIndex) {
			if (baseArray[i].compareTo(baseArray[j]) <= 0) {
				array[k] = baseArray[i];
				i++;
			} else {
				array[k] = baseArray[j];
				j++;
			}
			k++;
		}

		while (i <= middleIndex) {
			array[k] = baseArray[i];
			i++;
			k++;
		}

		while (j <= rightIndex) {
			array[k] = baseArray[j];
			j++;
			k++;
		}

	}

	private void insertionSort(T[] array, int leftIndex, int rightIndex) {
		for (int i = leftIndex + 1; i <= rightIndex; i++) {
			HybridMergeSort.INSERTIONSORT_APPLICATIONS += 1;
			int j = i;
			while (j > leftIndex) {
				boolean isCurrentElementSmallerThanPreviousElement = array[j].compareTo(array[j - 1]) < 0;
				if (!isCurrentElementSmallerThanPreviousElement) {
					break;
				}
				Util.swap(array, j, j - 1);
				j--;
			}
		}
	}

}
