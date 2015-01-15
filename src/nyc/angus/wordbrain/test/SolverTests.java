/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordbrain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nyc.angus.wordbrain.finder.WordBrainSolver;
import nyc.angus.wordbrain.util.DictionaryLoader;
import nyc.angus.wordbrain.util.Printers;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests of {@link WordBrainSolver}.
 */
public class SolverTests {
	private static WordBrainSolver solver;

	@BeforeClass
	public static void initialSetUp() throws IOException {
		solver = new WordBrainSolver(DictionaryLoader.loadDictionary("dictionary.txt"));
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
