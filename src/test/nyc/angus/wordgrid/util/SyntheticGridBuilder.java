/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.util;

import java.util.Random;

/**
 * Utility for building random grids.
 */
public class SyntheticGridBuilder {
	// Letter frequency (British National Corpus - 90 words): etaoinsrhldcumfpgwybvkxjqz
	// Taken from: http://www.bckelk.ukfsn.org/words/etaoin.html

	private static final char[] letters = { 'e', 't', 'a', 'o', 'i', 'n', 's', 'r', 'h', 'l', 'd', 'c', 'u', 'm', 'f', 'p', 'g', 'w', 'y',
			'b', 'v', 'k', 'x', 'j', 'q', 'z' };

	/**
	 * Create a grid of random characters, with character selection biased towards the most commonly used letters in the
	 * english language.
	 * <p>
	 * For example, 'e' is the most likely character to be picked, followed by 't' and 'a'. 'z' is rarely picked.
	 * <p>
	 * This creates grids that are more likely to contain viable solutions.
	 */
	public static char[][] createXbyXGrid(final int gridSize, final Random random) {

		final char[][] grid = new char[gridSize][gridSize];

		for (int y = 0; y < grid.length; y++) {

			for (int x = 0; x < grid[0].length; x++) {

				grid[y][x] = pickNextChar(random);

			}
		}

		return grid;
	}

	public static String generateRandomPotentialWord(final int wordLength, final Random random) {

		final StringBuilder word = new StringBuilder();

		for (int i = 0; i < wordLength; i++) {
			word.append(pickNextChar(random));
		}

		return word.toString();
	}

	private static char pickNextChar(final Random random) {
		/*
		 * The following code makes it more likely that letters occurring at the start of the letters array will be
		 * chosen, as two random values are chosen. The first random value is used as the upper limit for generating the
		 * second value.
		 */

		final int initialValue = random.nextInt(25);

		final int rndval = (initialValue == 0) ? 0 : random.nextInt(initialValue);
		// 0 isn't a valid input to nextInt

		final char nextChar = letters[rndval];
		return nextChar;
	}
}
