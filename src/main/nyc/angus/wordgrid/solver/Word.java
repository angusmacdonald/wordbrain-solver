package nyc.angus.wordgrid.solver;

import java.util.Set;

import com.google.common.base.Objects;

/**
 * A word in a potential solution to a puzzle. Encapsulates both the word, the positions the word exists on, and a copy
 * of the grid at that point in time. on.
 */
public class Word {

	private final String word;
	private final Set<Position> positions;
	private final char[][] grid;

	public Word(final String word, final Set<Position> positions, final char[][] grid) {
		this.positions = positions;
		this.word = word;
		this.grid = grid;
	}

	public String getString() {
		return word;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(word, positions);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Position) {
			final Word that = (Word) object;
			return Objects.equal(this.word, that.word) && Objects.equal(this.positions, that.positions);
		}
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("word", word).add("positions", positions).toString();
	}
}
