package nyc.angus.wordgrid.solver;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import nyc.angus.wordgrid.solver.solution.Position;

import com.google.common.base.Preconditions;

/**
 * Utility for working with 2D grids of characters.
 */
public class Grids {

	/**
	 * Create a new grid, removing the specified grid elements, and dropping any characters above these elements down,
	 * so that the only empty (' ') characters are at the top of the grid. The latter operation is termed 'gravity',
	 * since the remaining letters fall down if there is nothing but space below them.
	 * 
	 * @param grid
	 *        The grid to be changed (a copy is made before alteration, so this isn't changed).
	 * @param positionsToRemove
	 *        The grid positions to remove.
	 * @return A new grid, with the specified grid positions removed, and gravity applied to the remaining elements.
	 */
	public static char[][] removeElementsAndApplyGravity(@Nonnull final char[][] grid, @Nonnull final Collection<Position> positionsToRemove) {

		final char[][] newGrid = cloneGrid(grid);

		clearSpecifiedPositions(newGrid, positionsToRemove);

		applyGravity(newGrid);

		return newGrid;
	}

	/**
	 * Clear the specified positions in the grid, replacing the previous character with the ' ' character.
	 */
	private static void clearSpecifiedPositions(@Nonnull final char[][] grid, @Nonnull final Collection<Position> positionsToRemove) {
		for (final Position position : positionsToRemove) {
			grid[position.y][position.x] = ' ';
		}
	}

	/**
	 * If there is an empty space (a ' ' char) beneath any item in the grid, drop the character down.
	 * <p>
	 * This operation leaves space only at the top of the grid.
	 * <p>
	 * The grid passed in to this operation is modified.
	 */
	private static void applyGravity(@Nonnull final char[][] newGrid) {
		for (int y = 0; y < newGrid.length; y++) {
			for (int x = 0; x < newGrid[0].length; x++) {

				if (newGrid[y][x] == ' ') {
					char prev = ' ';
					for (int i = 0; i <= y; i++) {
						final char temp = newGrid[i][x];
						newGrid[i][x] = prev;
						prev = temp;
					}
				}

			}
		}
	}

	/**
	 * Clone the provided grid.
	 */
	private static char[][] cloneGrid(@Nonnull final char[][] original) {
		return Arrays.stream(original).map((final char[] row) -> row.clone()).toArray((final int length) -> new char[length][]);
	}

	/**
	 * Pretty print the contents of a grid to standard out.
	 * 
	 * @param grid
	 *        The grid to be printed.
	 */
	public static void printGrid(@Nonnull final char[][] grid) {
		for (final char[] element : grid) {
			for (int x = 0; x < grid[0].length; x++) {
				System.out.print(element[x] + " ");
			}

			System.out.println("");
		}
	}

	/**
	 * Convert all characters in the grid to their lower-case equivalent, returning the result in a new 2D array.
	 */
	public static char[][] toLowerCase(@Nonnull final char[][] caseSensitiveGrid) {
		Preconditions.checkNotNull(caseSensitiveGrid);
		Preconditions.checkArgument(caseSensitiveGrid.length > 0);

		final char[][] lowerCaseGrid = new char[caseSensitiveGrid.length][caseSensitiveGrid[0].length];

		for (int y = 0; y < lowerCaseGrid.length; y++) {
			for (int x = 0; x < lowerCaseGrid[0].length; x++) {
				lowerCaseGrid[y][x] = Character.toLowerCase(caseSensitiveGrid[y][x]);
			}
		}

		return lowerCaseGrid;
	}
}
