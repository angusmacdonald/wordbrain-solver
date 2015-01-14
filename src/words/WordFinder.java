package words;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class WordFinder {

	private Set<String> dictionary;

	public WordFinder(Set<String> dictionary) {
		this.dictionary = dictionary;
	}

	/**
	 * Find all valid WordBrain words in the given grid that are the specified
	 * length.
	 * 
	 * @param grid
	 *            Word grid.
	 * @param lengthOfWord
	 *            Length of the word we are looking for.
	 * @return All valid words in the length.
	 */
	public List<String> findWords(char[][] grid, int lengthOfWord) {
		List<String> wordsFound = new LinkedList<>();

		/*
		 * Start looking for words from each position in the grid.
		 */
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {

				// Start with no words seen, and empty string.
				wordsFound.addAll(findWord("", x, y, new HashSet<>(),
						lengthOfWord, grid));

			}
		}

		return wordsFound;
	}

	/**
	 * Recursively called to build up words. If a word is of the desired length,
	 * check if it is in the dictionary.
	 * <p>
	 * Starting with an empty string, it recursively calls out to (up to) 8
	 * characters next to the given character, stopping where it has already
	 * used a character as part of the word, or at the edge of the grid.
	 * 
	 * @param currentWord
	 *            The word being built up as part of this call.
	 * @param x
	 *            The current position in the grid, x axis.
	 * @param y
	 *            The current position in the grid, y axis.
	 * @param positionsSeen
	 *            Set of positions already seen.
	 * @param length
	 *            The length of the word we are looking for.
	 * @param grid
	 *            The word grid.
	 * @return List of valid words found.
	 */
	private List<String> findWord(String currentWord, int x, int y,
			Set<Position> positionsSeen, int length, char[][] grid) {

		if (x < 0 || y < 0 || y >= grid.length || x >= grid[0].length
				|| positionsSeen.contains(new Position(x, y)) || grid[y][x] == ' ') {
			return Collections.emptyList();
		}

		LinkedList<String> wordsFound = new LinkedList<>();

		String newWord = currentWord + grid[y][x];

		if (newWord.length() == length && dictionary.contains(newWord)) {
			wordsFound.add(newWord);
		}

		Set<Position> newPosSeen = new HashSet<>(positionsSeen);
		newPosSeen.add(new Position(x, y));
		wordsFound
				.addAll(findWord(newWord, x - 1, y, newPosSeen, length, grid));
		wordsFound
				.addAll(findWord(newWord, x, y - 1, newPosSeen, length, grid));
		wordsFound.addAll(findWord(newWord, x - 1, y - 1, newPosSeen, length,
				grid));
		wordsFound
				.addAll(findWord(newWord, x + 1, y, newPosSeen, length, grid));
		wordsFound
				.addAll(findWord(newWord, x, y + 1, newPosSeen, length, grid));
		wordsFound.addAll(findWord(newWord, x + 1, y + 1, newPosSeen, length,
				grid));
		wordsFound.addAll(findWord(newWord, x + 1, y - 1, newPosSeen, length,
				grid));
		wordsFound.addAll(findWord(newWord, x - 1, y + 1, newPosSeen, length,
				grid));

		return wordsFound;
	}

}
