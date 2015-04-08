package nyc.angus.wordgrid.solver;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.annotation.Nonnull;

import nyc.angus.wordgrid.dictionary.Dictionary;
import nyc.angus.wordgrid.solver.solution.GridSolution;
import nyc.angus.wordgrid.solver.solution.GridWord;
import nyc.angus.wordgrid.solver.solution.Position;

import com.google.common.base.Preconditions;

/**
 * Provides potential solutions for WordBrain problems, provided a grid of characters, and the lengths of the words to
 * be found in the grid.
 */
public class WordGridSolver {

	/**
	 * The dictionary of words that may exist in the grid.
	 */
	private final Dictionary dictionary;

	public WordGridSolver(@Nonnull final Dictionary dictionary) {
		Preconditions.checkNotNull(dictionary);
		this.dictionary = dictionary;
	}

	/**
	 * Find all valid WordBrain words in the given grid that are the specified length.
	 * 
	 * @param caseSensitiveGrid
	 *        Word grid.
	 * @param wordLengths
	 *        The lengths of the words we are looking for. This is ordered, as some words may only become accessible
	 *        once one word is found and removed from the grid.
	 * @return The list of word combinations that complete the grid. The sub-list contains the words that, used
	 *         together, complete the grid.
	 */
	public List<GridSolution> findWords(final char[][] caseSensitiveGrid, final Queue<Integer> wordLengths) {
		Preconditions.checkNotNull(caseSensitiveGrid);
		Preconditions.checkArgument(caseSensitiveGrid.length > 0 && caseSensitiveGrid[0].length > 0);

		if (wordLengths == null || wordLengths.size() == 0) {
			return Collections.emptyList();
		}

		final char[][] lowerCaseGrid = Grids.toLowerCase(caseSensitiveGrid);

		final List<GridSolution> wordsFound = new LinkedList<>();

		/*
		 * Start looking for words from each position in the grid.
		 */
		for (int y = 0; y < lowerCaseGrid.length; y++) {
			for (int x = 0; x < lowerCaseGrid[0].length; x++) {

				// Start with no words seen, and empty string.
				wordsFound.addAll(findWord(lowerCaseGrid, x, y, "", new LinkedList<>(), wordLengths));

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
	private List<GridSolution> findWord(final char[][] grid, final int xPos, final int yPos, @Nonnull final String currentWord,
			final List<Position> positionsUsedInWord, final Queue<Integer> wordLengthsRequired) {

		// Check terminating conditions (co-ordinates off grid, grid position already used, or grid position empty):
		if (notAValidPosition(grid, xPos, yPos, positionsUsedInWord)) {
			return Collections.emptyList();
		}

		final List<GridSolution> solutions = new LinkedList<>();

		final String newWord = currentWord + grid[yPos][xPos];
		final Integer wordLengthRequired = wordLengthsRequired.peek();

		if (newWord.length() == wordLengthRequired && dictionary.isWord(newWord)) {
			solutions.addAll(markSolutionAndStartNextWord(grid, xPos, yPos, positionsUsedInWord, wordLengthsRequired, newWord));
		} else if (newWord.length() < wordLengthRequired && dictionary.isPrefix(newWord)) {
			solutions.addAll(findNextCharacterInWord(grid, xPos, yPos, positionsUsedInWord, wordLengthsRequired, newWord));
		}

		return solutions;
	}

	/**
	 * A grid position is not valid if one of the co-ordinates is off the grid (e.g. a negative co-ordinate), the grid
	 * position has already been used for the current word, or the grid position does not contain a character (in which
	 * case the ' ' char is found).
	 */
	private boolean notAValidPosition(final char[][] grid, final int xPos, final int yPos, final List<Position> positionsUsedInWord) {
		return xPos < 0 || yPos < 0 || yPos >= grid.length || xPos >= grid[0].length
				|| positionsUsedInWord.contains(new Position(xPos, yPos)) || grid[yPos][xPos] == ' ';
	}

	/**
	 * The current word is not large enough, as we haven't reached the desired word size yet. Fan out the search by
	 * recursively calling {@link #findWord(char[][], int, int, String, Set, Queue)}, adding every combination of
	 * remaining characters adjacent to the current character.
	 */
	private List<GridSolution> findNextCharacterInWord(final char[][] grid, final int xPos, final int yPos,
			final List<Position> positionsUsedInWord, final Queue<Integer> wordLengthsRequired, final String word) {

		final List<Position> newPosSeen = clonePositionsSet(positionsUsedInWord);
		newPosSeen.add(new Position(xPos, yPos));

		final List<GridSolution> solutions = new LinkedList<>();

		solutions.addAll(findWord(grid, xPos - 1, yPos, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos, yPos - 1, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos - 1, yPos - 1, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos + 1, yPos, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos, yPos + 1, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos + 1, yPos + 1, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos + 1, yPos - 1, word, newPosSeen, wordLengthsRequired));
		solutions.addAll(findWord(grid, xPos - 1, yPos + 1, word, newPosSeen, wordLengthsRequired));

		return solutions;
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
	private List<GridSolution> markSolutionAndStartNextWord(final char[][] grid, final int xPos, final int yPos,
			final List<Position> positionsUsedInWord, final Queue<Integer> wordLengthsRequired, final String validWord) {
		final List<GridSolution> solutions = new LinkedList<>();

		/*
		 * We've found a potential first word. Move on to second word.
		 */
		final Queue<Integer> newWordLengthsRequired = cloneQueue(wordLengthsRequired);
		newWordLengthsRequired.remove();

		final List<Position> finalPositionsInWord = clonePositionsSet(positionsUsedInWord);
		finalPositionsInWord.add(new Position(xPos, yPos));

		final GridWord word = new GridWord(validWord, finalPositionsInWord, grid);

		final GridSolution solution = new GridSolution(word);

		if (newWordLengthsRequired.isEmpty()) {
			// No more words to find after this.
			solutions.add(solution);
		} else {
			final char[][] updatedGrid = removeWordFromGrid(grid, finalPositionsInWord);

			solutions.addAll(startSearchForNextWord(updatedGrid, word, newWordLengthsRequired));
		}

		return solutions;
	}

	/**
	 * Start searching for the next word in the grid by removing characters used as part of the first word and
	 * recursively calling {@link #findWords(char[][], Queue)} to begin the search anew.
	 */
	private List<GridSolution> startSearchForNextWord(final char[][] grid, final GridWord word, final Queue<Integer> newWordLengthsRequired) {

		final List<GridSolution> solutions = new LinkedList<>();

		// Start searching again in new grid for the next word:
		final List<GridSolution> nextWords = findWords(grid, newWordLengthsRequired);

		// If more words were found, add the whole result to the solution set:
		if (!nextWords.isEmpty()) {
			for (final GridSolution solution : nextWords) {
				solution.addFirst(word);
			}

			solutions.addAll(nextWords);
		}

		return solutions;
	}

	/**
	 * Create a copy of the grid and update it to remove the characters from the recently discovered grid.
	 * <p>
	 * A copy is made of the positions used to make this word, because sibling calls (in the recursive call structure)
	 * to {@link #startSearchForNextWord(char[][], String, Queue)} can also find valid words and also try to update this
	 * structure, which leads to an incorrect position being added.
	 */
	private char[][] removeWordFromGrid(final char[][] grid, final Collection<Position> positionsUsedInWord) {
		return Grids.removeElementsAndApplyGravity(grid, positionsUsedInWord);
	}

	private Queue<Integer> cloneQueue(final Queue<Integer> wordLengthsRequired) {
		return new LinkedList<>(wordLengthsRequired);
	}

	private List<Position> clonePositionsSet(final List<Position> positionsUsedInWord) {
		return new LinkedList<>(positionsUsedInWord);
	}
}
