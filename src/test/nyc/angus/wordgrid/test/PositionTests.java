/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nyc.angus.wordgrid.solver.solution.Position;

import org.junit.Test;

/**
 * Tests of the {@link Position} class.
 */
public class PositionTests {

	@Test
	public void equality() {
		final Position a = new Position(1, 1);
		final Position b = new Position(1, 1);

		assertTrue(a.equals(b));
	}

	@Test
	public void equalityWrongObject() {
		final Position a = new Position(1, 1);
		final Object o = new Object();

		assertFalse(a.equals(o));
	}

	@Test
	public void equalityFalseY() {
		final Position a = new Position(1, 1);
		final Position b = new Position(1, 2);

		assertFalse(a.equals(b));
	}

	@Test
	public void equalityFalseX() {
		final Position a = new Position(1, 1);
		final Position b = new Position(2, 1);

		assertFalse(a.equals(b));
	}

	@Test
	public void testHashCode() {
		final Position a = new Position(2, 4);
		final Position b = new Position(2, 4);

		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void testToString() {
		final Position a = new Position(2, 4);

		assertEquals("Position{x=2, y=4}", a.toString());
	}
}
