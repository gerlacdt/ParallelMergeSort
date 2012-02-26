package sorter;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements the whole logic of the mergesort-algorithm. Nothing more, nothing
 * less. Does not consider parallel support yet!
 * 
 * @author gerlacdt
 * 
 * @param <T>
 */
public class MergeSorter<T extends Comparable<T>> {

	/**
	 * Holds a reference to the list which should be sorted.
	 */
	private List<T> listToSort;
	/**
	 * The lowest index which should be consider during sorting. Usually = 0.
	 */
	private int lowIndex;
	/**
	 * The highest index which should be considers during sorting. Usually =
	 * list.size() - 1.
	 */
	private int highIndex;

	public MergeSorter(List<T> arrayList) {
		this.listToSort = arrayList;
		// sort the whole list
		this.lowIndex = 0;
		this.highIndex = arrayList.size() - 1;
	}

	public MergeSorter(List<T> arrayList, int low, int high) {
		this.listToSort = arrayList;
		this.lowIndex = low;
		this.highIndex = high;
	}

	public List<T> getListToSort() {
		return listToSort;
	}

	public void setListToSort(List<T> listToSort) {
		this.listToSort = listToSort;
	}

	/**
	 * Public Wrapper method to start the Mergesort.
	 * 
	 * @return
	 */
	public List<T> sort() {
		mergesort(lowIndex, highIndex);
		return listToSort;
	}

	/**
	 * Implements the mergsort-algorithm.
	 * 
	 * @param low
	 *            the lower index of the list range which should be sorted
	 * @param high
	 *            the higher index of the list range which should be sorted
	 */
	public void mergesort(int low, int high) {
		if (low < high) {
			int middle = (low + high) / 2;
			mergesort(low, middle);
			mergesort(middle + 1, high);
			merge(low, middle, high);
		}
	}

	public void merge(int low, int middle, int high) {
		int i; // counter
		LinkedList<T> buffer1 = new LinkedList<T>();
		LinkedList<T> buffer2 = new LinkedList<T>();

		// copy current values in temporary containers, needed for merging
		for (i = low; i <= middle; i++) {
			buffer1.add(listToSort.get(i));
		}
		for (i = middle + 1; i <= high; i++) {
			buffer2.add(listToSort.get(i));
		}

		// merge the values in right order (sorting)
		i = low;
		while (!(buffer1.isEmpty() || buffer2.isEmpty())) {
			if (buffer1.getFirst().compareTo(buffer2.getFirst()) < 0) {
				listToSort.set(i, buffer1.remove());
			} else {
				listToSort.set(i, buffer2.remove());
			}
			++i;
		}

		// add the values which are left
		while (!buffer1.isEmpty()) {
			listToSort.set(i, buffer1.remove());
			++i;
		}
		while (!buffer2.isEmpty()) {
			listToSort.set(i, buffer2.remove());
			++i;
		}
	}
}
