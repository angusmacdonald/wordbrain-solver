/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import nyc.angus.wordgrid.dictionary.DictionaryLoader;
import nyc.angus.wordgrid.dictionary.set.SetDictionary;
import nyc.angus.wordgrid.dictionary.trie.TrieDictionary;
import nyc.angus.wordgrid.solver.WordGridSolver;
import nyc.angus.wordgrid.solver.solution.GridSolution;
import nyc.angus.wordgrid.ui.Printers;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Stopwatch;

/**
 * Functions that solve the same puzzle with the set solver and the trie solver to measure the speed at which each one
 * executes.
 * <p>
 * JUnit is used for simplicity, but these are not unit tests.
 */
public class SolverBenchmarking {
	private final static Logger LOGGER = Logger.getLogger(SolverBenchmarking.class.getName());

	private static WordGridSolver setSolver;
	private static WordGridSolver trieSolver;
	private final Random random = new Random(0l);

	@BeforeClass
	public static void initialSetUp() throws IOException {
		final Set<String> wordSet = DictionaryLoader.loadDictionary("dictionary.txt");

		final SetDictionary setDictionary = new SetDictionary(wordSet);
		final TrieDictionary trieDictionary = TrieDictionary.createTrie(wordSet);

		setSolver = new WordGridSolver(setDictionary);
		trieSolver = new WordGridSolver(trieDictionary);
	}

	@Test
	public void eightSizedGrid() {
		final int gridSize = 8;

		final char[][] grid = SyntheticGridBuilder.createXbyXGrid(gridSize, random);

		final Queue<Integer> wordLengths = createRandomWordLengths(gridSize);

		LOGGER.log(Level.INFO, "Executing set finder:");

		final Stopwatch setTimer = Stopwatch.createStarted();
		final List<GridSolution> setSolutions = setSolver.findWords(grid, wordLengths);
		setTimer.stop();

		LOGGER.log(Level.INFO, setTimer.elapsed(TimeUnit.MILLISECONDS) + "ms to execute set finder.");

		LOGGER.log(Level.INFO, "Executing trie finder:");
		final Stopwatch trieTimer = Stopwatch.createStarted();
		final List<GridSolution> trieSolutions = trieSolver.findWords(grid, wordLengths);
		trieTimer.stop();

		LOGGER.log(Level.INFO, trieTimer.elapsed(TimeUnit.MILLISECONDS) + "ms to execute trie finder.");

		Printers.printSolutions(setSolutions);
		Printers.printSolutions(trieSolutions);
	}

	/**
	 * @param gridSize
	 * @return
	 */
	private Queue<Integer> createRandomWordLengths(final int gridSize) {
		int letters = gridSize * gridSize;

		LOGGER.log(Level.INFO, "Creating word lengths...");

		final Queue<Integer> wordLengths = new LinkedList<>();

		while (letters > 0) {
			final int nextInt = random.nextInt(letters) + 1;

			if (nextInt > 1 && letters - nextInt != 1) {
				// Don't allow one letter words.
				wordLengths.add(nextInt);
				letters -= nextInt;
			}
		}

		LOGGER.log(Level.INFO, "Finished creating word lengths.");

		return wordLengths;
	}

}
