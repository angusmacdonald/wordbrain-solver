package nyc.angus.wordbrain.finder;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.annotation.Nonnull;

import nyc.angus.wordbrain.util.Grids;
import nyc.angus.wordbrain.util.Position;

import com.google.common.base.Preconditions;

/**
 * Provides potential solutions for WordBrain problems, provided a grid of characters, and the lengths of the words to
 * be found in the grid.
 */
public class WordBrainSolver {

	/**
	 * The dictionary of words that may exist in the grid.
	 */
	private final Set<String> dictionary;

	public WordBrainSolver(final Set<String> dictionary) {
		Preconditions.checkNotNull(dictionary);
		this.dictionary = dictionary;
	}

	/**
	 * Find all valid WordBrain words in the given grid that are the specified length.
	 * 
	 * @param grid
	 *        Word grid.
	 * @param wordLengths
	 *        The lengths of the words we are looking for. This is ordered, as some words may only become accessible
	 *        once one word is found and removed from the grid.
	 * @return The list of word combinations that complete the grid. The sub-list contains the words that, used
	 *         together, complete the grid.
	 */
	public List<LinkedList<String>> findWords(final char[][] grid, final Queue<Integer> wordLengths) {
		final List<LinkedList<String>> wordsFound = new LinkedList<>();

		Preconditions.checkNotNull(grid);
		Preconditions.checkArgument(grid.length > 0 || grid[0].length > 0);

		if (wordLengths == null || wordLengths.size() == 0) {
			return wordsFound;
		}

		/*
		 * Start looking for words from each position in the grid.
		 */
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {

				// Start with no words seen, and empty string.
				wordsFound.addAll(findWord(grid, x, y, "", new HashSet<>(), wordLengths));

			}
		}

		return wordsFound;
	}

	/**
	 * Recursively called to build up words. If a word is of the desired length, check if it is in the dictionary.
	 * <p>
	 * Starting with an empty string, it recursively calls out to the (up to) 8 characters next to the given character,
	 * stopping where it has already used a character as part of the word, or at the edge of the grid.
	 * <p>
	 * When it finds a valid word, it moves on in another recursive call to {@link #findWords(char[][], Queue)} to find
	 * another valid word, matching the next word length in <tt>lengthOfWord</tt>.
	 * 
	 * @param grid
	 *        The word grid.
	 * @param xPos
	 *        The current position in the grid, x axis.
	 * @param yPos
	 *        The current position in the grid, y axis.
	 * @param currentWord
	 *        The word being built up as part of this call.
	 * @param positionsUsedInWord
	 *        Set of positions already seen.
	 * @param wordLengthsRequired
	 *        The length of the word we are looking for.
	 * 
	 * @return The list of word combinations that complete the grid. The sub-list contains the words that, used
	 *         together, complete the grid.
	 */
	private List<LinkedList<String>> findWord(final char[][] grid, final int xPos, final int yPos, @Nonnull final String currentWord,
			final Set<Position> positionsUsedInWord, final Queue<Integer> wordLengthsRequired) {

		// Check terminating conditions (co-ordinates off grid, grid position already used, or grid position empty):
		if (xPos < 0 || yPos < 0 || yPos >= grid.length || xPos >= grid[0].length || positionsUsedInWord.contains(new Position(xPos, yPos))
				|| grid[yPos][xPos] == ' ') {
			return Collections.emptyList();
		}

		final List<LinkedList<String>> solutions = new LinkedList<>();

		final String newWord = currentWord + grid[yPos][xPos];

		final Integer wordLengthRequired = wordLengthsRequired.peek();

		if (newWord.length() == wordLengthRequired && dictionary.contains(newWord)) {
			markSolutionAndStartNextWord(grid, xPos, yPos, positionsUsedInWord, wordLengthsRequired, solutions, newWord);
		} else if (newWord.length() < wordLengthRequired) {
			findNextCharacterInWord(grid, xPos, yPos, positionsUsedInWord, wordLengthsRequired, solutions, newWord);
		}

		return solutions;
	}

	/**
	 * The current word is not large enough, as we haven't reached the desired word size yet. Fan out the search by
	 * recursively calling {@link #findWord(char[][], int, int, String, Set, Queue)}, adding every combination of
	 * remaining characters adjacent to the current character.
	 */
	private void findNextCharacterInWord(final char[][] grid, final int xPos, final int yPos, final Set<Position> positionsUsedInWord,
			final Queue<Integer> wordLengthsRequired, final List<LinkedList<String>> solutions, final String word) {

		final Set<Position> newPosSeen = new HashSet<>(positionsUsedInWord);
		newPosSeen.add(new Position(xPos, yPos));

		solutions.addAll(findWord(grid, xPos - 1, yPos, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos, yPos - 1, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos - 1, yPos - 1, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos + 1, yPos, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos, yPos + 1, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos + 1, yPos + 1, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos + 1, yPos - 1, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos - 1, yPos + 1, word, newPosSeen, wordLengthsRequired));
	}

	/**
	 * We have found a valid word.
	 * <p>
	 * If there are no more words to find, add this word to a result list and return it.
	 * <p>
	 * If there are more valid words to find, remove the characters in the discovered word from the grid and recursively
	 * call {@link #findWords(char[][], Queue)} to find the next word.
	 * <p>
	 * If these recursive calls terminate without finding the next required work, this discovered word can be ignored,
	 * as it is not part of a complete solution.
	 */
	private void markSolutionAndStartNextWord(final char[][] grid, final int xPos, final int yPos, final Set<Position> positionsUsedInWord,
			final Queue<Integer> wordLengthsRequired, final List<LinkedList<String>> solutions, final String validWord) {
		/*
		 * We've found a potential first word. Move on to second word.
		 */
		final Queue<Integer> newWordLengthsRequired = cloneQueue(wordLengthsRequired);
		newWordLengthsRequired.remove();

		final LinkedList<String> resultSet = new LinkedList<>();
		resultSet.add(validWord);

		if (newWordLengthsRequired.isEmpty()) {
			// No more words to find after this.
			solutions.add(resultSet);
		} else {
			// Mark current position as seen:
			positionsUsedInWord.add(new Position(xPos, yPos));

			// Remove the word found in this search:
			final char[][] updatedGrid = Grids.removeElementsAndApplyGravity(grid, positionsUsedInWord);

			// Start searching again in new grid for the next word:
			final List<LinkedList<String>> nextWords = findWords(updatedGrid, newWordLengthsRequired);

			// If more words were found, add the whole result to the solution set:
			if (!nextWords.isEmpty() && !nextWords.get(0).isEmpty()) {
				for (final LinkedList<String> list : nextWords) {
					list.addFirst(validWord);
				}

				solutions.addAll(nextWords);
			}
		}
	}

	/**
	 * Clone the provided queue.
	 */
	private Queue<Integer> cloneQueue(final Queue<Integer> wordLengthsRequired) {
		return new LinkedList<>(wordLengthsRequired);
	}
}
