package test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import sorter.Main;
import sorter.MergeSorter;
import sorter.threads.Cores;
import sorter.threads.MergeSorterDriver;
import words.WordReader;

public class TimeMeasureTest {
	
	private final String inputFile = "test_dir/big_file.txt";
	
	private static void print(List<? extends Comparable> values) {
		for (Comparable value : values) {
			System.out.println(value);
		}
	}
	

	@Test
	public void SingleCoreMobyDickWithThreads() throws InterruptedException, IOException {
		WordReader wordReader = new WordReader(inputFile);
		List<String> wordList = wordReader.readInWords();

		MergeSorter<String> sorter = new MergeSorter<String>(wordList);
		long startTime = System.currentTimeMillis();
		MergeSorterDriver driver = new MergeSorterDriver(wordList, Cores.SINGLE);
		driver.runSortinParallel();
		long endTime = System.currentTimeMillis();

		System.out.println("Sorting-Duration (singlecore): " + inputFile + " in millsec: "
				+ (endTime - startTime));
		
		Main.printInFile(wordList, inputFile + "_single.out");
	}

	@Test
	public void DualCoreMobyDickWithThreads() throws InterruptedException, IOException {
		WordReader wordReader = new WordReader(inputFile);
		List<String> wordList = wordReader.readInWords();

		MergeSorter<String> sorter = new MergeSorter<String>(wordList);
		long startTime = System.currentTimeMillis();
		MergeSorterDriver driver = new MergeSorterDriver(wordList, Cores.DUAL);
		driver.runSortinParallel();
		long endTime = System.currentTimeMillis();

		System.out.println("Sorting-Duration (dualcore): " + inputFile + " in millsec: "
				+ (endTime - startTime));
		
		Main.printInFile(wordList, inputFile + "_dual.out");
	}
	
	@Test
	public void QuadCoreMobyDickWithThreads() throws InterruptedException, IOException {
		WordReader wordReader = new WordReader(inputFile);
		List<String> wordList = wordReader.readInWords();

		MergeSorter<String> sorter = new MergeSorter<String>(wordList);
		long startTime = System.currentTimeMillis();
		MergeSorterDriver driver = new MergeSorterDriver(wordList, Cores.QUAD);
		driver.runSortinParallel();
		long endTime = System.currentTimeMillis();

		System.out.println("Sorting-Duration (quadcore): " + inputFile + " in millsec: "
				+ (endTime - startTime));
		
		Main.printInFile(wordList, inputFile + "_quad.out");
	}
	
	@Test public void CompareOutFiles() throws IOException {
		File singleFile = new File(inputFile + "_single.out");
		File dualFile = new File(inputFile + "_dual.out");
		File quadFile = new File(inputFile + "_quad.out");
		
		assertTrue(FileUtils.contentEquals(singleFile, dualFile));
		assertTrue(FileUtils.contentEquals(singleFile, quadFile));
	}
}
