/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import nyc.angus.wordgrid.dictionary.trie.TrieDictionary;

import org.junit.Test;

/**
 * Tests of the {@link TrieDictionary} implementation.
 */
public class TrieTests {

	@Test
	public void emptyDictionary() {
		final TrieDictionary t = TrieDictionary.createTrie(new HashSet<>());

		assertTrue(t.isPrefix(""));

		assertFalse(t.isPrefix("a"));
		assertFalse(t.isPrefix("b"));
		assertFalse(t.isPrefix("c"));
		assertFalse(t.isPrefix("d"));

		assertFalse(t.isWord("a"));
		assertFalse(t.isWord("d"));
	}

	@Test
	public void zeroCharWord() {
		final TrieDictionary t = TrieDictionary.createTrie(new HashSet<>());

		assertFalse(t.isWord(""));
	}

	@Test(expected = NullPointerException.class)
	public void nullDictionary() {
		TrieDictionary.createTrie(null);
	}

	@Test
	public void smallTrie() {
		final Set<String> dictionary = new HashSet<>();
		dictionary.add("home");
		dictionary.add("homeward");
		dictionary.add("happy");
		dictionary.add("sad");

		final TrieDictionary t = TrieDictionary.createTrie(dictionary);

		assertTrue(t.isPrefix("h"));
		assertTrue(t.isPrefix("ha"));
		assertTrue(t.isPrefix("happy"));
		assertTrue(t.isPrefix("home"));
		assertTrue(t.isPrefix("s"));

		assertFalse(t.isPrefix("so"));
		assertFalse(t.isPrefix("homey"));
		assertFalse(t.isPrefix("hr"));
		assertFalse(t.isPrefix(" "));

		assertTrue(t.isWord("home"));
		assertTrue(t.isWord("homeward"));
		assertTrue(t.isWord("happy"));
		assertTrue(t.isWord("sad"));

		assertFalse(t.isWord("ho"));
		assertFalse(t.isWord("homew"));
	}
}
