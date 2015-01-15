package words;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
	public List<List<String>> findWords(char[][] grid,
			Queue<Integer> lengthOfWord) {
		List<List<String>> wordsFound = new LinkedList<>();

		if (lengthOfWord == null || lengthOfWord.size() == 0) {
			return wordsFound;
		}

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
	 * @param lengthOfWord
	 *            The length of the word we are looking for.
	 * @param grid
	 *            The word grid.
	 * @return List of valid words found.
	 */
	private List<List<String>> findWord(String currentWord, int x, int y,
			Set<Position> positionsSeen, Queue<Integer> lengthOfWord,
			char[][] grid) {

		/*
		 * Terminating conditions:
		 */
		if (x < 0 || y < 0 || y >= grid.length || x >= grid[0].length
				|| positionsSeen.contains(new Position(x, y))
				|| grid[y][x] == ' ') {
			return Collections.emptyList();
		}

		if (lengthOfWord.isEmpty()
				|| currentWord.length() > lengthOfWord.peek()) {
			return Collections.emptyList();
		}

		/*
		 * Begin check with larger word:
		 */
		List<List<String>> wordsFound = new LinkedList<>();

		String newWord = currentWord + grid[y][x];

		if (newWord.length() == lengthOfWord.peek()
				&& dictionary.contains(newWord)) {
			// We've found a potential first word. Move on to second word.
			Queue<Integer> newLengthOfWord = new LinkedList<>(lengthOfWord); 
			newLengthOfWord.remove();

			List<String> resultSet = new LinkedList<>();
			resultSet.add(newWord);

			if (newLengthOfWord.isEmpty()) {
				wordsFound.add(resultSet);
			} else {
				positionsSeen.add(new Position(x, y));
				char[][] updatedGrid = Grids.createNewGrid(grid, positionsSeen);
				List<List<String>> nextWords = findWords(updatedGrid,
						newLengthOfWord);

				if (nextWords.size() > 0 && nextWords.get(0).size() > 0) {
					resultSet.addAll(nextWords.get(0));

					wordsFound.add(resultSet);
				}
			}
		} else if (newWord.length() < lengthOfWord.peek()) {

			Set<Position> newPosSeen = new HashSet<>(positionsSeen);
			newPosSeen.add(new Position(x, y));
			wordsFound.addAll(findWord(newWord, x - 1, y, newPosSeen,
					lengthOfWord, grid));
			wordsFound.addAll(findWord(newWord, x, y - 1, newPosSeen,
					lengthOfWord, grid));
			wordsFound.addAll(findWord(newWord, x - 1, y - 1, newPosSeen,
					lengthOfWord, grid));
			wordsFound.addAll(findWord(newWord, x + 1, y, newPosSeen,
					lengthOfWord, grid));
			wordsFound.addAll(findWord(newWord, x, y + 1, newPosSeen,
					lengthOfWord, grid));
			wordsFound.addAll(findWord(newWord, x + 1, y + 1, newPosSeen,
					lengthOfWord, grid));
			wordsFound.addAll(findWord(newWord, x + 1, y - 1, newPosSeen,
					lengthOfWord, grid));
			wordsFound.addAll(findWord(newWord, x - 1, y + 1, newPosSeen,
					lengthOfWord, grid));

		}

		return wordsFound;
	}

}
