package nyc.angus.wordgrid.solver.solution;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Position in a 2D grid.
 */
public class Position {
	public int x;
	public int y;

	public Position(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(x, y);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Position) {
			final Position that = (Position) object;
			return Objects.equal(this.x, that.x) && Objects.equal(this.y, that.y);
		}
		return false;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("x", x).add("y", y).toString();
	}
}
