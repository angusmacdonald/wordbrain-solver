package words;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

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
	public List<List<String>> findWords(char[][] grid, Queue<Integer> lengthOfWord) {
		List<List<String>> wordsFound = new LinkedList<>();
		
		if (lengthOfWord == null || lengthOfWord.size() == 0){
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
			Set<Position> positionsSeen, Queue<Integer> lengthOfWord, char[][] grid) {

		if (x < 0 || y < 0 || y >= grid.length || x >= grid[0].length
				|| positionsSeen.contains(new Position(x, y)) || grid[y][x] == ' ') {
			return Collections.emptyList();
		}

		List<List<String>> wordsFound = new LinkedList<>();

		String newWord = currentWord + grid[y][x];

		if (newWord.length() == lengthOfWord.peek() && dictionary.contains(newWord)) {
			List<String> resultSet = new LinkedList<>();
			resultSet.add(newWord);
			lengthOfWord.remove();
			List<List<String>> words = findWords(createNewGrid(grid, positionsSeen), lengthOfWord);
			
			for (List<String> list : words) {
				list.add(newWord);
			}
			
			wordsFound.addAll(words);
		}

		Set<Position> newPosSeen = new HashSet<>(positionsSeen);
		newPosSeen.add(new Position(x, y));
		wordsFound
				.addAll(findWord(newWord, x - 1, y, newPosSeen, lengthOfWord, grid));
		wordsFound
				.addAll(findWord(newWord, x, y - 1, newPosSeen, lengthOfWord, grid));
		wordsFound.addAll(findWord(newWord, x - 1, y - 1, newPosSeen, lengthOfWord,
				grid));
		wordsFound
				.addAll(findWord(newWord, x + 1, y, newPosSeen, lengthOfWord, grid));
		wordsFound
				.addAll(findWord(newWord, x, y + 1, newPosSeen, lengthOfWord, grid));
		wordsFound.addAll(findWord(newWord, x + 1, y + 1, newPosSeen, lengthOfWord,
				grid));
		wordsFound.addAll(findWord(newWord, x + 1, y - 1, newPosSeen, lengthOfWord,
				grid));
		wordsFound.addAll(findWord(newWord, x - 1, y + 1, newPosSeen, lengthOfWord,
				grid));

		return wordsFound;
	}
	
	private char[][] createNewGrid(char[][] grid, Set<Position> positionsSeen) {
		
		char[][] newGrid = cloneArray(grid);
		
		
		for (Position position : positionsSeen) {
			newGrid[position.y][position.x] = ' ';
		}
		
		
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {

				if (grid[y][x] == ' '){
					char prev = ' ';
					for (int i = 0; y <= i; i++){
						char temp = newGrid[i][x];
						newGrid[i][x] = prev;
						prev = temp;
					}
				}

			}
		}
		return newGrid;
	}

	private char[][] cloneArray(char[][] original){
		   return Arrays.stream(original)
		             .map((char[] row) -> row.clone())
		             .toArray((int length) -> new char[length][]);
	}

}
