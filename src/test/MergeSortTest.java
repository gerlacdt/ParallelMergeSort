package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import sorter.MergeSorter;
import sorter.threads.Cores;
import sorter.threads.MergeSorterDriver;
import sorter.threads.MergeSorterThread;
import words.WordReader;

/**
 * Junit-Test class for verification of the correct implementation of the
 * Mergesort-algorithm.
 * 
 * @author gerlacdt
 * 
 */
public class MergeSortTest {

	private static void print(List<? extends Comparable> values) {
		for (Comparable value : values) {
			System.out.println(value);
		}
	}

	@Test
	public void mergesortFourIntegers() {
		List<Integer> arrayList = new ArrayList<Integer>();
		arrayList.add(2);
		arrayList.add(1);
		arrayList.add(7);
		arrayList.add(-1);

		MergeSorter<Integer> sorter = new MergeSorter<Integer>(arrayList);
		System.out.println("MergesortIntegers:");
		sorter.sort();

		assertTrue(sorter.getListToSort().get(0) == -1);
		assertTrue(sorter.getListToSort().get(1) == 1);
		assertTrue(sorter.getListToSort().get(2) == 2);
		assertTrue(sorter.getListToSort().get(3) == 7);
	}

	@Test
	public void mergesortFourStrings() {
		List<String> arrayList = new ArrayList<String>();
		arrayList.add("Daniel");
		arrayList.add("zzzz");
		arrayList.add("Zahl");
		arrayList.add("brala");

		MergeSorter<String> sorter = new MergeSorter<String>(arrayList);
		sorter.sort();

		assertTrue(sorter.getListToSort().get(0) == "Daniel");
		assertTrue(sorter.getListToSort().get(1) == "Zahl");
		assertTrue(sorter.getListToSort().get(2) == "brala");
		assertTrue(sorter.getListToSort().get(3) == "zzzz");
	}

	@Test
	public void threadMergeSort() throws InterruptedException {
		List<String> wordList = new ArrayList<String>();
		wordList.add("aaa");
		wordList.add("zzzz");
		wordList.add("bbb");
		wordList.add("ggg");

		MergeSorter<String> sorter = new MergeSorter<String>(wordList);
		Thread thread1 = new Thread(new MergeSorterThread(sorter));
		thread1.start();
		thread1.join();

		assertTrue(wordList.get(0) == "aaa");
		assertTrue(wordList.get(1) == "bbb");
		assertTrue(wordList.get(2) == "ggg");
		assertTrue(wordList.get(3) == "zzzz");
	}

	@Test
	public void threadMergeSortDriverTestSingle() throws InterruptedException {
		List<String> wordList = new ArrayList<String>();
		wordList.add("aaa");
		wordList.add("zzzz");
		wordList.add("bbb");
		wordList.add("ggg");

		MergeSorterDriver driver = new MergeSorterDriver(wordList, Cores.SINGLE);
		driver.runSortinParallel();

		assertTrue(wordList.get(0) == "aaa");
		assertTrue(wordList.get(1) == "bbb");
		assertTrue(wordList.get(2) == "ggg");
		assertTrue(wordList.get(3) == "zzzz");
	}

	@Test
	public void threadMergeSortDriverTestDual() throws InterruptedException {
		List<String> wordList = new ArrayList<String>();
		wordList.add("aaa");
		wordList.add("zzzz");
		wordList.add("bbb");
		wordList.add("ggg");
		wordList.add("yyy");

		MergeSorterDriver driver = new MergeSorterDriver(wordList, Cores.DUAL);
		driver.runSortinParallel();

		assertTrue(wordList.get(0) == "aaa");
		assertTrue(wordList.get(1) == "bbb");
		assertTrue(wordList.get(2) == "ggg");
		assertTrue(wordList.get(3) == "yyy");
		assertTrue(wordList.get(4) == "zzzz");
	}
}
