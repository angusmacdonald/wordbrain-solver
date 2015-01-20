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

	/**
	 * Path to the frequencies resource, listing 5000 words and a ranking of their frequency of use in an english
	 * corpus.
	 */
	private static final String NYC_ANGUS_WORDGRID_RESOURCE_FREQUENCIES_CSV = "/nyc/angus/wordgrid/resource/frequencies.csv";

	/**
	 * Path to the dictionary resource, listing english words.
	 */
	private static final String DICTIONARY_RESOURCE_PATH = "/nyc/angus/wordgrid/resource/dictionary.txt";

	public static void main(final String[] args) throws IOException {
		final TrieDictionary dictionary = TrieDictionary.createTrie(DictionaryLoader.loadDictionary(DICTIONARY_RESOURCE_PATH));

		final Map<String, Integer> frequencies = WordFrequencyLoader.loadWordFrequencies(NYC_ANGUS_WORDGRID_RESOURCE_FREQUENCIES_CSV, 5);
		final WordFrequencySorting sorter = new WordFrequencySorting(frequencies);

		final SelectGridSizeFrame initialFrame = new SelectGridSizeFrame(dictionary, sorter);
		initialFrame.setVisible(true);
	}
}
