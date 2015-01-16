/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.dictionary.trie;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;

/**
 * A node in the {@link TrieDictionary} data structure.
 */
public class TrieNode {

	/**
	 * The character this node represents in the trie.
	 */
	private final char character;

	/**
	 * Does a traversal ending at this node produce a full word?
	 * <p>
	 * If not, this node will have children, because it is part of a word, but not the end of it.
	 */
	private boolean isWord;

	/**
	 * The characters that can be added to the current traversal and still make up a prefix of a word or a complete
	 * word.
	 */
	private final Map<Character, TrieNode> children = new HashMap<>();

	public TrieNode(final char character, final boolean isWord) {
		this.character = character;
		this.isWord = isWord;
	}

	public void addChild(final TrieNode child) {
		children.put(child.getChar(), child);
	}

	public TrieNode getChild(final char c) {
		return children.get(c);
	}

	public char getChar() {
		return character;
	}

	public boolean isWord() {
		return isWord;
	}

	public void setWord(final boolean isWord) {
		this.isWord = isWord;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(character, isWord);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof TrieNode) {
			final TrieNode that = (TrieNode) object;
			return Objects.equal(this.character, that.character) && Objects.equal(this.isWord, that.isWord);
		}
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("character", character).add("isWord", isWord).add("children", children).toString();
	}
}
