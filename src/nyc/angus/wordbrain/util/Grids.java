package nyc.angus.wordbrain.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Grids {

	public static void main(String[] args) {
		char[][] grid = createGrid();
		printGrid(grid);

		Set<Position> positionsSeen = new HashSet<>();

		positionsSeen.add(new Position(0, 2));
		positionsSeen.add(new Position(0, 1));

		char[][] newGrid = createNewGrid(grid, positionsSeen);

		System.out.println("");
		printGrid(newGrid);
	}

	public static char[][] createNewGrid(char[][] grid, Set<Position> positionsSeen) {

		char[][] newGrid = cloneArray(grid);

		for (Position position : positionsSeen) {
			newGrid[position.y][position.x] = ' ';
		}

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {

				if (newGrid[y][x] == ' ') {
					char prev = ' ';
					for (int i = 0; i <= y; i++) {
						char temp = newGrid[i][x];
						newGrid[i][x] = prev;
						prev = temp;
					}
				}

			}
		}
		return newGrid;
	}

	private static char[][] cloneArray(char[][] original){
		   return Arrays.stream(original)
		             .map((char[] row) -> row.clone())
		             .toArray((int length) -> new char[length][]);
	}

	private static void printGrid(char[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				System.out.print(grid[y][x] + " ");
			}
			System.out.println("");
		}
	}

	private static char[][] createGrid() {
		char[][] grid = new char[4][4];
		// y, x
		grid[0][0] = 'p';
		grid[0][1] = 'e';
		grid[0][2] = 'o';
		grid[0][3] = 'r';
		grid[1][0] = 'o';
		grid[1][1] = 'g';
		grid[1][2] = 'b';
		grid[1][3] = 't';
		grid[2][0] = 't';
		grid[2][1] = 'l';
		grid[2][2] = 'e';
		grid[2][3] = 'o';
		grid[3][0] = 'u';
		grid[3][1] = 'e';
		grid[3][2] = 'v';
		grid[3][3] = 'n';
		return grid;
	}
}
