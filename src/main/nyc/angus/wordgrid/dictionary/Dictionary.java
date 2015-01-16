/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.dictionary;

/**
 * Operations used to determine if a given string is a word, or forms a prefix to word(s).
 */
public interface Dictionary {
	/**
	 * Is the provided string a prefix to a word in the dictionary?
	 * <p>
	 * Implementations that don't support this operation will always return true, to prevent early termination of
	 * operations.
	 * <p>
	 * An empty string is considered a prefix.
	 */
	boolean isPrefix(String potentialPrefix);

	/**
	 * Is the string provided a complete string in the dictionary?
	 * <p>
	 * An empty string is not a word.
	 */
	boolean isWord(String potentialWord);
}
