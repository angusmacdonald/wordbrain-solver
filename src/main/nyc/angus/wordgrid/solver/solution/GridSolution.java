package nyc.angus.wordgrid.solver.solution;

import java.util.LinkedList;

/**
 * A solution for a given grid. This is gradually built up, so not all {@link GridSolution} objects are valid solutions.
 */
public class GridSolution {
	private final LinkedList<GridWord> words = new LinkedList<>();

	/**
	 * Start creating a new solution by adding the first word in that solution.
	 */
	public GridSolution(final GridWord word) {
		add(word);
	}

	/**
	 * Add a word to the solution set.
	 */
	public void add(final GridWord validWord) {
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
	public void addFirst(final GridWord word) {
		words.addFirst(word);
	}

	/**
	 * Get the words, ordered, from the solution.
	 */
	public LinkedList<GridWord> getWords() {
		return words;
	}

	/**
	 * Check if a given word is in the solution.
	 */
	public boolean containsWord(final String string) {
		for (final GridWord word : words) {
			if (word.getString().equals(string)) {
				return true;
			}
		}

		return false;
	}

}
