/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import nyc.angus.wordgrid.dictionary.set.SetDictionary;
import nyc.angus.wordgrid.solver.WordGridSolver;
import nyc.angus.wordgrid.util.DictionaryLoader;
import nyc.angus.wordgrid.util.Printers;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests of {@link WordGridSolver}.
 */
public class SolverTests {
	private static WordGridSolver solver;

	final char[][] grid = { { 'r', 'n', 's', 'o' }, { 'e', 'r', 'r', 't' }, { 'b', 'm', 'i', 'v' }, { 't', 'w', 'i', 'a' } };
	// 6, 2, 3, 5
	final char[][] grid2 = { { 'd', 'i', 'o', 't' }, { 'b', 't', 'h', 'o' }, { 'a', 'b', 's', 's' }, { 'r', 'r', 'e', 't' } };
	// 6, 5, 5
	final char[][] grid3 = { { 't', 'j', 'u' }, { 'a', 's', 's' }, { 'i', 'r', 'g' } };
	// 6, 3
	final char[][] grid4 = { { 'e', 'a' }, { 'i', 'd' } };
	// 4
	final char[][] grid5 = { { 's', 'l', 'k', 'c' }, { 'k', 'l', 'e', 'h' }, { 't', 'u', 'r', 'e' }, { 'i', 'f', 'o', 'p' } };

	// 6, 5, 5

	@BeforeClass
	public static void initialSetUp() throws IOException {
		final Set<String> wordSet = DictionaryLoader.loadDictionary("dictionary.txt");

		final SetDictionary setDictionary = new SetDictionary(wordSet);

		solver = new WordGridSolver(setDictionary);
	}

	@Test
	public void manySmallWords() {
		final char[][] grid = { { 't', 'n', 'l', 'e' }, { 'm', 'b', 'o', 'w' }, { 'a', 'o', 'r', 't' }, { 'j', 'o', 'i', 't' } };

		final Queue<Integer> wordLengths = setUpWordLengths(3, 4, 6, 3);

		final List<LinkedList<String>> solutions = solver.findWords(grid, wordLengths);

		// TODO tin, trowel, boot, jam
		Printers.printSolutions(solutions);
		fail("Not completed.");
	}

	@Test
	public void fourByFour2() {
		final char[][] grid = { { 'n', 'o', 'n', 'c' }, { 'p', 'n', 't', 'm' }, { 'e', 'c', 'a', 'a' }, { 'r', 'e', 'n', 'j' } };

		final Queue<Integer> wordLengths = setUpWordLengths(3, 6, 7);

		final List<LinkedList<String>> solutions = solver.findWords(grid, wordLengths);

		Printers.printSolutions(solutions);

		assertEquals(2, solutions.size());
		final List<String> solutionSet = solutions.get(0);

		assertTrue(solutionSet.contains("jam"));
		assertTrue(solutionSet.contains("cannon"));
		assertTrue(solutionSet.contains("percent"));
	}

	private Queue<Integer> setUpWordLengths(final Integer... lengths) {
		final Queue<Integer> q = new LinkedList<>();

		for (final Integer val : lengths) {
			q.add(val);
		}

		return q;
	}
}
