package sorter.threads;

import sorter.MergeSorter;

/**
 * Thread class which start the actually Mergesort.
 * @author gerlacdt
 *
 */
public class MergeSorterThread implements Runnable{
	
	MergeSorter<?> sorter;
	
	public MergeSorterThread(MergeSorter<?> sorter) {
		this.sorter = sorter;
	}

	public void run() {
		sorter.sort();
	}
}
