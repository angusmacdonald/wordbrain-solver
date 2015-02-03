/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.benchmark;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import nyc.angus.wordgrid.dictionary.DictionaryLoader;
import nyc.angus.wordgrid.dictionary.set.SetDictionary;
import nyc.angus.wordgrid.dictionary.trie.TrieDictionary;
import nyc.angus.wordgrid.solver.WordGridSolver;
import nyc.angus.wordgrid.util.SyntheticGridBuilder;

/**
 * Benchmarking of the solver implementation, comparing the set and trie dictionary implementations.
 */
public class SolverBenchmarking {
	private final static Logger LOGGER = Logger.getLogger(SolverBenchmarking.class.getName());

	public static void main(final String[] args) throws Exception {

		final Set<String> dict = DictionaryLoader.loadDictionary("/nyc/angus/wordgrid/resource/dictionary.txt");
		final TrieDictionary trieDictionary = TrieDictionary.createTrie(dict);
		final SetDictionary setDictionary = new SetDictionary(dict);

		final int gridSize = 5;
		final int iterations = 30;
		final long trieTime = Timing.time(testSolverPerformance(gridSize, new WordGridSolver(trieDictionary), new Random(10L)), iterations);
		final long setTime = Timing.time(testSolverPerformance(gridSize, new WordGridSolver(setDictionary), new Random(10L)), iterations);

		System.out.println("trie," + trieTime + "," + gridSize + "," + iterations);
		System.out.println("set," + setTime + "," + gridSize + "," + iterations);

	}

	public static Callable<Boolean> testSolverPerformance(final int gridSize, final WordGridSolver solver, final Random r) {

		final Callable<Boolean> dictionaryPerformance = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {

				final char[][] grid = SyntheticGridBuilder.createXbyXGrid(gridSize, r);

				final Queue<Integer> wordLengths = createRandomWordLengths(gridSize, r);

				solver.findWords(grid, wordLengths);

				return true;
			}
		};

		return dictionaryPerformance;
	}

	private static Queue<Integer> createRandomWordLengths(final int gridSize, final Random r) {
		int letters = gridSize * gridSize;

		LOGGER.log(Level.FINE, "Creating word lengths...");

		final Queue<Integer> wordLengths = new LinkedList<>();

		while (letters > 0) {
			final int nextInt = r.nextInt(letters) + 1;

			if (nextInt > 1 && letters - nextInt != 1) {
				// Don't allow one letter words.
				wordLengths.add(nextInt);
				letters -= nextInt;
			}
		}

		LOGGER.log(Level.FINE, "Finished creating word lengths.");

		return wordLengths;
	}

}
