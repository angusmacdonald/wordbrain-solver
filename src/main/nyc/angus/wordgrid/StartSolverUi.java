/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid;

import java.io.IOException;

import nyc.angus.wordgrid.dictionary.trie.TrieDictionary;
import nyc.angus.wordgrid.ui.SelectGridSizeFrame;
import nyc.angus.wordgrid.util.DictionaryLoader;

/**
 * Start the solver user interface, which allows entry of word grid problems, and shows the results produced by the
 * solver.
 */
public class StartSolverUi {

	public static void main(final String[] args) throws IOException {
		final TrieDictionary dictionary = TrieDictionary.createTrie(DictionaryLoader.loadDictionary("dictionary.txt"));

		final SelectGridSizeFrame initialFrame = new SelectGridSizeFrame(dictionary);
		initialFrame.setVisible(true);
	}
}
