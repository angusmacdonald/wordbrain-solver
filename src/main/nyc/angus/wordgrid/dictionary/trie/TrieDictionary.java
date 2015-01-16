/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.dictionary.trie;

import java.util.Set;

import javax.annotation.Nonnull;

import nyc.angus.wordgrid.dictionary.Dictionary;

import com.google.common.base.Preconditions;

/**
 * A dictionary of words organized in a trie data structure, to allow for quick prefix search.
 */
public class TrieDictionary implements Dictionary {
	private final TrieNode root = new TrieNode(' ', false);

	private TrieDictionary() {
		// Private constructor, use createTrie instead.
	}

	/**
	 * Create a trie by initializing it with the provided dictionary.
	 */
	public static TrieDictionary createTrie(@Nonnull final Set<String> dictionary) {
		Preconditions.checkNotNull(dictionary);

		final TrieDictionary trie = new TrieDictionary();

		for (final String word : dictionary) {
			trie.addWord(word);
		}

		return trie;
	}

	/**
	 * Add the given word to the trie by iterating through each character in the word and ensuring it exists at the
	 * corresponding branch of the trie.
	 */
	private void addWord(final String word) {

		TrieNode nextNode = root;

		for (int i = 0; i < word.length(); i++) {
			final char c = word.charAt(i);

			TrieNode newNext = nextNode.getChild(c);

			if (newNext == null) {
				newNext = new TrieNode(c, (i == word.length() - 1));
				nextNode.addChild(newNext);
			} else if (i == word.length() - 1) {
				newNext.setWord(true);
			}

			nextNode = newNext;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPrefix(final String potentialPrefix) {
		TrieNode nextNode = root;

		for (int i = 0; i < potentialPrefix.length(); i++) {
			final char c = potentialPrefix.charAt(i);

			final TrieNode child = nextNode.getChild(c);

			if (child == null) {
				return false;
			}

			nextNode = child;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isWord(final String potentialWord) {
		TrieNode nextNode = root;

		for (int i = 0; i < potentialWord.length(); i++) {
			final char c = potentialWord.charAt(i);

			final TrieNode child = nextNode.getChild(c);

			if (child == null) {
				return false;
			}

			nextNode = child;
		}

		return nextNode.isWord();
	}
}
