/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import nyc.angus.wordgrid.solver.Grids;

import org.junit.Test;

/**
 * Tests of the {@link Grids} class.
 */
public class GridsTests {

	@Test
	public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		final Constructor<Grids> constructor = Grids.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}
}
