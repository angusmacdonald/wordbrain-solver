/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import nyc.angus.wordgrid.dictionary.set.SetDictionary;

import org.junit.Test;

/**
 * Tests of the {@link SetDictionary} implementation.
 */
public class SetDictionaryTests {

	@Test
	public void emptyDictionary() {
		final SetDictionary t = new SetDictionary(new HashSet<>());

		assertFalse(t.isWord(""));
		assertFalse(t.isWord("a"));
		assertFalse(t.isWord("d"));
	}

	@Test
	public void zeroCharWord() {
		final SetDictionary t = new SetDictionary(new HashSet<>());

		assertFalse(t.isWord(""));
	}

	@Test(expected = NullPointerException.class)
	public void nullDictionary() {
		new SetDictionary(null);
	}

	@Test
	public void smallSet() {
		final Set<String> dictionary = new HashSet<>();
		dictionary.add("home");
		dictionary.add("homeward");
		dictionary.add("happy");
		dictionary.add("sad");

		final SetDictionary t = new SetDictionary(dictionary);

		assertTrue(t.isPrefix("h"));
		assertTrue(t.isPrefix("so")); // Everything is true.

		assertTrue(t.isWord("home"));
		assertTrue(t.isWord("homeward"));
		assertTrue(t.isWord("happy"));
		assertTrue(t.isWord("sad"));

		assertFalse(t.isWord("ho"));
		assertFalse(t.isWord("homew"));
	}
}
