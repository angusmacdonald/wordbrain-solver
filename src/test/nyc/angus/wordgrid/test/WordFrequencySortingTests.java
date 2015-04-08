/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nyc.angus.wordgrid.frequency.WordFrequencySorting;
import nyc.angus.wordgrid.solver.solution.GridSolution;
import nyc.angus.wordgrid.solver.solution.GridWord;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the {@link WordFrequencySorting} class.
 */
public class WordFrequencySortingTests {

	private Map<String, Integer> frequencies;

	@Before
	public void setUp() {
		frequencies = new HashMap<>();
		frequencies.put("angus", 1);
		frequencies.put("macdonald", 2);
		frequencies.put("city", 3);
		frequencies.put("new", 4);
		frequencies.put("york", 5);
		frequencies.put("blond", 6);
	}

	@Test
	public void testSort() {

		final List<GridSolution> solutions = new LinkedList<>();

		final GridSolution word1 = createGridSolution("notknown");
		final GridSolution word2 = createGridSolution("angus");
		final GridSolution word3 = createGridSolution("blond");
		solutions.add(word1);
		solutions.add(word2);
		solutions.add(word3);

		final WordFrequencySorting sorting = new WordFrequencySorting(frequencies);
		sorting.sortSolutionsByFrequency(solutions);

		assertEquals(solutions.get(0), word2);
		assertEquals(solutions.get(1), word3);
		assertEquals(solutions.get(2), word1);
	}

	@Test
	public void removeDuplicates() {
		final List<GridSolution> solutions = new LinkedList<>();

		final GridSolution word1 = createGridSolution("angus");
		final GridSolution word2 = createGridSolution("angus");
		final GridSolution word3 = createGridSolution("blond");
		solutions.add(word1);
		solutions.add(word2);
		solutions.add(word3);

		final List<GridSolution> results = WordFrequencySorting.removeDuplicates(solutions);

		assertTrue(results.contains(word1));
		assertTrue(results.contains(word3));

		assertEquals(2, results.size());
	}

	private GridSolution createGridSolution(final String word) {
		return new GridSolution(new GridWord(word, null, null));
	}
}
