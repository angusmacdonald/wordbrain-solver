package words;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Reading dictionary...");
		List<String> dictionaryAsList = Files.readAllLines(Paths.get("", "dictionary.txt"));
		Set<String> dictionary = new HashSet<>(dictionaryAsList);
		System.out.println("Finished reading dictionary...");
		
		WordFinder finder = new WordFinder(dictionary);
		
		List<String> wordsFound = finder.findWords(createGrid(), 8);
		
		printWords(wordsFound);
	}

	private static void printWords(List<String> wordsFound) {
		System.out.println("Words found:");
		for (String string : wordsFound) {
			System.out.println(string);
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
