/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;

import nyc.angus.wordgrid.frequency.WordFrequencyLoader;

import org.junit.Test;

/**
 * Tests of the {@link WordFrequencyLoader} class.
 */
public class WordFrequencyLoaderTests {

	private static final String FREQUENCIES_PATH = "/nyc/angus/wordgrid/resource/frequencies.csv";

	@Test(expected = NullPointerException.class)
	public void checkNull() throws IOException {
		WordFrequencyLoader.loadWordFrequencies(null, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkNegativeLineNum() throws IOException {
		WordFrequencyLoader.loadWordFrequencies("path", -1);
	}

	@Test(expected = IOException.class)
	public void noFile() throws IOException {
		WordFrequencyLoader.loadWordFrequencies("path", 1);
	}

	@Test
	public void loadFrequencies() throws IOException {
		final Map<String, Integer> frequencies = WordFrequencyLoader.loadWordFrequencies(FREQUENCIES_PATH, 5);
		assertNotNull(frequencies);
	}

	@Test
	public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		final Constructor<WordFrequencyLoader> constructor = WordFrequencyLoader.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}
}
