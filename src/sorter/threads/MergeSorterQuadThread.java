package sorter.threads;

import sorter.MergeSorter;

/**
 * Thread class which is need for extra parallelization in a QUAD-core
 * Mergesort. In a quad-core sort, at end we have 4 sorted parts of a list which
 * should be merged again. This class is needed to merge the 2 sorted parts in 2
 * threads in parallel.
 * 
 * @author gerlacdt
 * 
 */
public class MergeSorterQuadThread implements Runnable {

	MergeSorter<?> sorter;
	Thread thread1;
	Thread thread2;
	int low;
	int middle;
	int high;

	public MergeSorterQuadThread(MergeSorter<?> sorter, Thread thread1,
			Thread thread2, int low, int middle, int high) {
		this.sorter = sorter;
		this.thread1 = thread1;
		this.thread2 = thread2;
		this.low = low;
		this.middle = middle;
		this.high = high;
	}

	public void run() {
		try {
			thread1.join();
			thread2.join();
			sorter.merge(low, middle, high);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
