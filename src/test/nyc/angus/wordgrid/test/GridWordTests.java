/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import nyc.angus.wordgrid.solver.solution.GridWord;
import nyc.angus.wordgrid.solver.solution.Position;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Tests of the {@link GridWord} class.
 */
public class GridWordTests {

	private char[][] charGrids;
	private List<Position> positions;

	private GridWord grid;
	private String word;

	@Before
	public void setUp() {
		charGrids = new char[][] { { 't', 'a' }, { 'l' }, { 'k' } };
		positions = Lists.newArrayList(new Position(0, 1));
		word = "a";
		grid = new GridWord(word, positions, charGrids);
	}

	@Test
	public void getGrid() {
		assertTrue(Arrays.deepEquals(charGrids, grid.getGrid()));
	}

	@Test
	public void getPositions() {
		assertEquals(positions, grid.getPositions());
	}

	@Test
	public void testHashCode() {
		final GridWord grid2 = new GridWord(new String(word), new LinkedList<>(positions), charGrids);
		assertEquals(grid.hashCode(), grid2.hashCode());
	}

	@Test
	public void testEquality() {
		final GridWord grid2 = new GridWord(new String(word), new LinkedList<>(positions), charGrids);
		assertTrue(grid.equals(grid2));
	}

	@Test
	public void testEqualityDifferentObject() {
		final GridWord grid2 = new GridWord(new String(word + "a"), new LinkedList<>(positions), charGrids);
		assertFalse(grid.equals(grid2));
	}

	@Test
	public void testEqualityDifferentObject2() {
		final LinkedList<Position> positions2 = new LinkedList<>(positions);
		positions2.addFirst(new Position(0, 0));
		final GridWord grid2 = new GridWord(new String(word), positions2, charGrids);
		assertFalse(grid.equals(grid2));
	}

	@Test
	public void testEqualityDifferentType() {
		final Object grid2 = new Object();
		assertFalse(grid.equals(grid2));
	}

	@Test
	public void testToString() {
		assertEquals(word, grid.toString());
	}

	@Test
	public void testGetString() {
		assertEquals(word, grid.getString());
	}
}
