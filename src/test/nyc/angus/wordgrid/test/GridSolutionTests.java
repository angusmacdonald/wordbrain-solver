/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;

import nyc.angus.wordgrid.solver.solution.GridSolution;
import nyc.angus.wordgrid.solver.solution.GridWord;
import nyc.angus.wordgrid.solver.solution.Position;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Tests of the {@link GridSolution} class.
 */
public class GridSolutionTests {
	private GridWord grid1;
	private String word1;
	private String word2;
	private GridWord grid2;

	@Before
	public void setUp() {
		final char[][] charGrids = new char[][] { { 't', 'a' }, { 'l' }, { 'k' } };
		final ArrayList<Position> positions = Lists.newArrayList(new Position(0, 1));
		final ArrayList<Position> positions2 = Lists.newArrayList(new Position(0, 1), new Position(1, 1));
		word1 = "talk";
		word2 = "tal";

		grid1 = new GridWord(word1, positions, charGrids);
		grid2 = new GridWord(word2, positions2, charGrids);
	}

	@Test
	public void createWithConstructor() {
		final GridSolution sol = new GridSolution(grid1);
		assertTrue(sol.containsWord(word1));
	}

	@Test
	public void containsFalse() {
		final GridSolution sol = new GridSolution(grid1);

		assertFalse(sol.containsWord(word2));
	}

	@Test
	public void addSecondWord() {
		final GridSolution sol = new GridSolution(grid1);

		sol.addFirst(grid2);

		assertTrue(sol.containsWord(word2));
	}

	@Test
	public void getWords() {
		final GridSolution sol = new GridSolution(grid1);
		final LinkedList<GridWord> wordList = new LinkedList<>();
		wordList.add(grid1);
		assertEquals(Lists.newLinkedList(wordList), sol.getWords());
	}

	@Test
	public void equalityTrue() {
		final GridSolution sol1 = new GridSolution(grid1);
		final GridSolution sol2 = new GridSolution(grid1);

		assertTrue(sol1.equals(sol2));
	}

	@Test
	public void equalityFalse() {
		final GridSolution sol1 = new GridSolution(grid1);
		final GridSolution sol2 = new GridSolution(grid2);

		assertFalse(sol1.equals(sol2));
	}

	@Test
	public void equalityFalseType() {
		final GridSolution sol1 = new GridSolution(grid1);
		final Object obj = new Object();

		assertFalse(sol1.equals(obj));
	}

	@Test
	public void testHashCode() {
		final GridSolution sol1 = new GridSolution(grid1);
		final GridSolution sol2 = new GridSolution(grid1);

		assertEquals(sol1.hashCode(), sol2.hashCode());
	}

	@Test
	public void testToString() {
		final GridSolution sol1 = new GridSolution(grid1);

		assertEquals("talk", sol1.toString());
	}
}
