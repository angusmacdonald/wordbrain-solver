package nyc.angus.wordgrid;

import java.io.IOException;
import java.util.Map;

import nyc.angus.wordgrid.dictionary.DictionaryLoader;
import nyc.angus.wordgrid.dictionary.trie.TrieDictionary;
import nyc.angus.wordgrid.frequency.WordFrequencyLoader;
import nyc.angus.wordgrid.frequency.WordFrequencySorting;
import nyc.angus.wordgrid.ui.SelectGridSizeFrame;

/**
 * Start the solver user interface, which allows entry of word grid problems, and shows the results produced by the
 * solver.
 */
public class StartSolverUi {

	public static void main(final String[] args) throws IOException {
		final TrieDictionary dictionary = TrieDictionary.createTrie(DictionaryLoader
				.loadDictionary("/nyc/angus/wordgrid/resource/dictionary.txt"));

		final Map<String, Integer> frequencies = WordFrequencyLoader.loadWordFrequencies("/nyc/angus/wordgrid/resource/frequencies.csv", 5);
		final WordFrequencySorting sorter = new WordFrequencySorting(frequencies);

		final SelectGridSizeFrame initialFrame = new SelectGridSizeFrame(dictionary, sorter);
		initialFrame.setVisible(true);
	}
}
