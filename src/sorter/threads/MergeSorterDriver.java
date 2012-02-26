package sorter.threads;

import java.util.List;

import sorter.MergeSorter;

/**
 * Driver for creating and starting the different threads for Single, Dual and
 * Quad-core sorting.
 * 
 * @author gerlacdt
 * 
 * @param <T>
 */
public class MergeSorterDriver<T extends Comparable<T>> {

	List<T> list;
	Cores cores;

	public MergeSorterDriver(List<T> list, Cores cores) {
		this.list = list;
		this.cores = cores;
	}

	public List<T> runSortinParallel() throws InterruptedException {

		switch (cores) {
		case SINGLE:
			singleCoreSort();
			break;
		case DUAL:
			dualCoreSort();
			break;
		case QUAD:
			quadCoreSort();
			break;
		default:
			singleCoreSort();
			break;
		}
		return list;
	}

	private void singleCoreSort() throws InterruptedException {
		MergeSorter<T> sorter = new MergeSorter<T>(list);
		Thread thread1 = new Thread(new MergeSorterThread(sorter));
		thread1.start();
		thread1.join();
	}

	private void dualCoreSort() throws InterruptedException {
		int low = 0;
		int high = list.size() - 1;
		int middle = (high - low) / 2;
		MergeSorter<T> sorter1 = new MergeSorter<T>(list, low, middle);
		MergeSorter<T> sorter2 = new MergeSorter<T>(list, middle + 1, high);
		Thread thread1 = new Thread(new MergeSorterThread(sorter1));
		Thread thread2 = new Thread(new MergeSorterThread(sorter2));
		thread1.start();
		thread2.start();

		// wait until all threads are done
		thread1.join();
		thread2.join();
		// merge one more time the 2 arrays from the threads
		sorter1.merge(low, middle, high);
	}

	private void quadCoreSort() throws InterruptedException {
		int low = 0;
		int high = list.size() - 1;
		int numberOfComponentsInThread = high / 4;

		int middle1 = (high - low) / 4;
		int middle2 = middle1 + numberOfComponentsInThread;
		int middle3 = middle2 + numberOfComponentsInThread;

		MergeSorter<T> sorter1 = new MergeSorter<T>(list, low, middle1 - 1);
		MergeSorter<T> sorter2 = new MergeSorter<T>(list, middle1, middle2 - 1);
		MergeSorter<T> sorter3 = new MergeSorter<T>(list, middle2, middle3 - 1);
		MergeSorter<T> sorter4 = new MergeSorter<T>(list, middle3, high);

		Thread thread1 = new Thread(new MergeSorterThread(sorter1));
		Thread thread2 = new Thread(new MergeSorterThread(sorter2));
		Thread thread3 = new Thread(new MergeSorterThread(sorter3));
		Thread thread4 = new Thread(new MergeSorterThread(sorter4));

		// start/run all 4 thread in parallel (efficiently supported only on
		// quad-core hardware :))
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		// merge the sorted list of thread1 and thread2
		Thread quadThread1 = new Thread(new MergeSorterQuadThread(sorter1,
				thread1, thread2, low, middle1 - 1, middle2 - 1));

		// merge the sorted list of thread3 and thread4
		Thread quadThread2 = new Thread(new MergeSorterQuadThread(sorter3,
				thread3, thread4, middle2, middle3 - 1, high));
		
		quadThread1.start();
		quadThread2.start();

		// wait till the 2 halfs of are sorted
		quadThread1.join();
		quadThread2.join();

		// merge the whole array
		sorter4.merge(low, middle2 - 1, high);
	}
}
