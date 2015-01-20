package nyc.angus.wordgrid.solver;

import java.util.LinkedList;

/**
 * A solution for a given grid. This is gradually built up, so not all {@link GridSolution} objects are valid solutions.
 */
public class GridSolution {
	private final LinkedList<String> words = new LinkedList<>();

	/**
	 * Add a word to the solution set.
	 */
	public void add(final String validWord) {
		words.add(validWord);
	}

	/**
	 * Are there any words in the grid solution?
	 */
	public boolean isEmpty() {
		return words.isEmpty();
	}

	/**
	 * Add a word to the start of the solution set.
	 */
	public void addFirst(final String word) {
		words.addFirst(word);
	}

	/**
	 * Get the words, ordered, from the solution.
	 */
	public LinkedList<String> getWords() {
		return words;
	}

	/**
	 * Check if a given word is in the solution.
	 */
	public boolean contains(final String string) {
		return words.contains(string);
	}

}
