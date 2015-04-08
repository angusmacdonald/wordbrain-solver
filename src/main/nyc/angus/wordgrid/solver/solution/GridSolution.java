package nyc.angus.wordgrid.solver.solution;

import java.util.LinkedList;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hashCode(words);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof GridSolution) {
			final GridSolution that = (GridSolution) object;
			return Objects.equal(this.words, that.words);
		}
		return false;
	}

	@Override
	public String toString() {
		return Joiner.on(", ").join(words);
	}

}
