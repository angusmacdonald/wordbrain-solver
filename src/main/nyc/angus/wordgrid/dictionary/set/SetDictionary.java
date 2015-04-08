/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.dictionary.set;

import java.util.Set;

import nyc.angus.wordgrid.dictionary.Dictionary;

/**
 * A dictionary backed by a set implementation. Consequently, it does not support prefix searching using
 * {@link #isPrefix(String)}.
 */
public class SetDictionary implements Dictionary {

	private final Set<String> dictionary;

	public SetDictionary(final Set<String> dictionary) {
		if (dictionary == null) {
			throw new NullPointerException();
		}

		this.dictionary = dictionary;
	}

	/**
	 * Always returns true, because this implementation has no way of determining if a prefix exists, and thus no way of
	 * terminating early. {@inheritDoc}
	 */
	@Override
	public boolean isPrefix(final String potentialPrefix) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isWord(final String potentialWord) {
		return dictionary.contains(potentialWord);
	}

}
