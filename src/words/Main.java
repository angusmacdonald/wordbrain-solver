package words;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Main {
	public static void main(String[] args) throws IOException {
		
		System.out.println("Reading dictionary...");
		List<String> dictionaryAsList = Files.readAllLines(Paths.get("", "dictionary.txt"));
		Set<String> dictionary = new HashSet<>(dictionaryAsList);
		System.out.println("Finished reading dictionary...");
		
		
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
		

		int length = 8;
		
		System.out.println("Finding words...");
		
		List<String> wordsFound = calculate(grid, dictionary, length);

		System.out.println("Finished finding words...");
		
		System.out.println("");
		System.out.println("Words found:");
		
		for (String string : wordsFound) {
			System.out.println(string);
		}
	}

	private static List<String> calculate(char[][] grid,
			Set<String> dictionary, int length) {
		List<String> wordsFound = new LinkedList<>();
		
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				String currentWord = "";
				Set<Position> positionsSeen = new HashSet<>();
				wordsFound.addAll(findWord(currentWord, x, y, positionsSeen,
						dictionary, length, grid));
			}
		}

		return wordsFound;
	}

	private static List<String> findWord(String currentWord, int x, int y,
			Set<Position> positionsSeen, Set<String> dictionary, int length,
			char[][] grid) {

		if (x < 0 || y < 0 || y >= grid.length || x >= grid[0].length
				|| positionsSeen.contains(new Position(x, y))) {
			return Collections.emptyList();
		}

		LinkedList<String> wordsFound = new LinkedList<>();

		String newWord = currentWord + grid[y][x];

		if (newWord.length() == length && dictionary.contains(newWord)) {
			wordsFound.add(newWord);
		}

		Set<Position> newPositionsSeen = new HashSet<>(positionsSeen);
		newPositionsSeen.add(new Position(x, y));
		wordsFound.addAll(findWord(newWord, x - 1, y, newPositionsSeen,
				dictionary, length, grid));
		wordsFound.addAll(findWord(newWord, x, y - 1, newPositionsSeen,
				dictionary, length, grid));
		wordsFound.addAll(findWord(newWord, x - 1, y - 1, newPositionsSeen,
				dictionary, length, grid));
		wordsFound.addAll(findWord(newWord, x + 1, y, newPositionsSeen,
				dictionary, length, grid));
		wordsFound.addAll(findWord(newWord, x, y + 1, newPositionsSeen,
				dictionary, length, grid));
		wordsFound.addAll(findWord(newWord, x + 1, y + 1, newPositionsSeen,
				dictionary, length, grid));
		wordsFound.addAll(findWord(newWord, x + 1, y - 1, newPositionsSeen,
				dictionary, length, grid));
		wordsFound.addAll(findWord(newWord, x - 1, y + 1, newPositionsSeen,
				dictionary, length, grid));

		return wordsFound;
	}
}
