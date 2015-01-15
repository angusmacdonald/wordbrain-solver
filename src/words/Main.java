package words;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.common.base.Joiner;

public class Main {
	public static void main(final String[] args) throws IOException {
		final Set<String> dictionary = DictionaryLoader.loadDictionary("dictionary.txt");

		final WordFinder finder = new WordFinder(dictionary);

		final Queue<Integer> q = new LinkedList<>();
		q.add(3);
		q.add(6);
		q.add(7);
		final List<List<String>> wordsFound = finder.findWords(createGrid(), q);

		printWords(wordsFound);

	}

	private static void printWords(final List<List<String>> wordsFound) {
		System.out.println("Words found:");
		for (final List<String> result : wordsFound) {
			System.out.println(Joiner.on(", ").join(result));
		}
	}

	private static char[][] createGrid() {
		final char[][] grid = new char[4][4];
		// y, x
		grid[0][0] = 'n';
		grid[0][1] = 'o';
		grid[0][2] = 'n';
		grid[0][3] = 'c';
		grid[1][0] = 'p';
		grid[1][1] = 'n';
		grid[1][2] = 't';
		grid[1][3] = 'm';
		grid[2][0] = 'e';
		grid[2][1] = 'c';
		grid[2][2] = 'a';
		grid[2][3] = 'a';
		grid[3][0] = 'r';
		grid[3][1] = 'e';
		grid[3][2] = 'n';
		grid[3][3] = 'j';

		return grid;
	}

}
