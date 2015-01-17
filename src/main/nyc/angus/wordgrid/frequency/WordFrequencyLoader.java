package nyc.angus.wordgrid.frequency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility for loading word frequencies into memory.
 */
public class WordFrequencyLoader {
	private final static Logger LOGGER = Logger.getLogger(WordFrequencyLoader.class.getName());

	/**
	 * @param filePath
	 *        Location of the word frequency file.
	 * @param lineWordsStart
	 *        The line in the file that words start (i.e. typically after a header, which should be ignored).
	 * @return Mapping from word to word frequency
	 * @throws IOException
	 *         If the file could not be loaded correctly.
	 */
	public static Map<String, Integer> loadWordFrequencies(final String filePath, final int lineWordsStart) throws IOException {

		final Map<String, Integer> words = new HashMap<>();

		int lineNum = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			for (String line; (line = br.readLine()) != null;) {
				lineNum++;
				if (lineNum >= lineWordsStart) {
					final String[] entries = line.split(",");
					words.put(entries[1].trim(), Integer.parseInt(entries[0].trim()));
				}
			}

		}

		LOGGER.log(Level.INFO, "Finished loading " + words.size() + " words.");

		return words;
	}
}
