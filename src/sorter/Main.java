package sorter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import sorter.threads.Cores;
import sorter.threads.MergeSorterDriver;
import words.WordReader;

/**
 * Main class for starting the performance measurement for SINGLE, DUAL and
 * QUAD-core mergesort. Needs one command-line arguments. Thats the filename
 * which should be sorted.
 * 
 * @author gerlacdt
 * 
 */

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.out
					.println("Please start programm with exactly one argument!!");
			System.out.println("USAGE java -jar MergeSort.jar <filename>");
		} else {
			String filename = args[0]; // get filename from command-line
										// arguments
			measureTimes(filename);
		}
	}

	public static void measureTimes(String filename) throws IOException {
		measureTimeSingle(filename);
		measureTimeDual(filename);
		measureTimeQuad(filename);
	}

	/**
	 * Execute Single-Core Mergesort for the given filename and prints the
	 * duration on console.
	 * 
	 * @param filename
	 * @throws IOException 
	 */
	public static void measureTimeSingle(String filename) throws IOException {
		WordReader wordReader = new WordReader(filename);
		List<String> wordList = wordReader.readInWords();

		MergeSorter<String> sorter = new MergeSorter<String>(wordList);
		long startTime = System.currentTimeMillis();
		MergeSorterDriver driver = new MergeSorterDriver(wordList, Cores.SINGLE);
		try {
			driver.runSortinParallel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();

		System.out.println("Sorting-Duration (singlecore): " + filename
				+ " in millsec: " + (endTime - startTime));
		
		printInFile(wordList, filename + "_single.out");
	}

	/**
	 * Execute Dual-Core Mergesort for the given filename and prints the
	 * duration on console.
	 * 
	 * @param filename
	 * @throws IOException 
	 */
	public static void measureTimeDual(String filename) throws IOException {
		WordReader wordReader = new WordReader(filename);
		List<String> wordList = wordReader.readInWords();

		MergeSorter<String> sorter = new MergeSorter<String>(wordList);
		long startTime = System.currentTimeMillis();
		MergeSorterDriver driver = new MergeSorterDriver(wordList, Cores.DUAL);
		try {
			driver.runSortinParallel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();

		System.out.println("Sorting-Duration (dualcore): " + filename
				+ " in millsec: " + (endTime - startTime));
		
		printInFile(wordList, filename + "_dual.out");
	}
	
	/**
	 * Execute Quad-Core Mergesort for the given filename and prints the
	 * duration on console.
	 * 
	 * @param filename
	 * @throws IOException 
	 */
	public static void measureTimeQuad(String filename) throws IOException {
		WordReader wordReader = new WordReader(filename);
		List<String> wordList = wordReader.readInWords();

		MergeSorter<String> sorter = new MergeSorter<String>(wordList);
		long startTime = System.currentTimeMillis();
		MergeSorterDriver driver = new MergeSorterDriver(wordList, Cores.QUAD);
		try {
			driver.runSortinParallel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();

		System.out.println("Sorting-Duration (quadcore): " + filename
				+ " in millsec: " + (endTime - startTime));
		
		printInFile(wordList, filename + "_quad.out");
	}
	
	public static void printInFile(List<? extends Comparable> values, String filename) throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter(filename));
		for (Comparable value : values) {
			writer.println(value);
		}
	}
}