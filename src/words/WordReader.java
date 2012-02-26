package words;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Need to fill a list with String-words into a list. Split the contents of a
 * textfile into correct words (no punctuation etc.). Look at the Regular
 * Expression.
 * 
 * @author gerlacdt
 * 
 */
public class WordReader {

	/**
	 * The wordList which holds all words from one text-file
	 */
	private List<String> wordList = new ArrayList<String>();
	/**
	 * the file from which to read
	 */
	private String fileName;

	public WordReader(String fileName) {
		this.fileName = fileName;
	}

	public List<String> getWordList() {
		return wordList;
	}

	public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}

	/**
	 * Returns a list with all words in one file. Only real words are added to
	 * the list. I.e. no punctuation is considered.
	 * 
	 * @return the list with all words
	 */
	public List<String> readInWords() {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(
					fileName));
			String line;
			// run through every line of the file
			while ((line = fileReader.readLine()) != null) {
				// add words to result-list
				splitLineAndAddWords(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Failure to read File!!!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wordList;
	}

	/**
	 * Helper method to split a line in words. Also add the found words to the
	 * result list.
	 * 
	 * @param line
	 *            line to split into constituent words
	 */
	private void splitLineAndAddWords(String line) {
		StringTokenizer tokenizer = new StringTokenizer(line);  // split line with delimiter: whitespace
		String word;
		String lower;
		// word contains only word-chars ([a-zA-Z0..9]
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher;
		while (tokenizer.hasMoreTokens()) {
			word = tokenizer.nextToken();
			lower = word.toLowerCase();
			matcher = pattern.matcher(lower);
			while (matcher.find()) {
				wordList.add(matcher.group());
			}
		}
	}
}
