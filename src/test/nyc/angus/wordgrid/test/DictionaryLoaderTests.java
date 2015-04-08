/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import nyc.angus.wordgrid.dictionary.DictionaryLoader;

import org.junit.Test;

/**
 * Tests of the {@link DictionaryLoader}.
 */
public class DictionaryLoaderTests {

	@Test(expected = IOException.class)
	public void loadFailure() throws IOException {
		DictionaryLoader.loadDictionary("notHere");
	}

	@Test
	public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		final Constructor<DictionaryLoader> constructor = DictionaryLoader.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}
}
