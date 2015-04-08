/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import java.io.IOException;

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
}
