package nyc.angus.wordgrid.solver.solution;

import java.util.List;

import com.google.common.base.Objects;

/**
 * A word in a potential solution to a puzzle. Encapsulates both the word, the positions the word exists on, and a copy
 * of the grid at that point in time. on.
 */
public class GridWord {

	/**
	 * Word in the grid.
	 */
	private final String word;

	/**
	 * Where the word is found in the grid.
	 */
	private final List<Position> positions;

	/**
	 * The grid, as it looks when the word
	 */
	private final char[][] grid;

	public GridWord(final String word, final List<Position> positions, final char[][] grid) {
		this.positions = positions;
		this.word = word;
		this.grid = grid;
	}

	public String getString() {
		return word;
	}

	public char[][] getGrid() {
		return grid;
	}

	public List<Position> getPositions() {
		return positions;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(word, positions);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof GridWord) {
			final GridWord that = (GridWord) object;
			return Objects.equal(this.word, that.word) && Objects.equal(this.positions, that.positions);
		}
		return false;
	}

	@Override
	public String toString() {
		return word;
	}
}
