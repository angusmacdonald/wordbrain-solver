package nyc.angus.wordgrid.frequency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

/**
 * Utility for loading word frequencies into memory.
 */
public class WordFrequencyLoader {
	private final static Logger LOGGER = Logger.getLogger(WordFrequencyLoader.class.getName());

	private WordFrequencyLoader() {
		// Static methods. Should not be instantiated.
	}

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
		Preconditions.checkNotNull(filePath);
		Preconditions.checkArgument(lineWordsStart >= 0);

		final Map<String, Integer> words = new HashMap<>();

		int lineNum = 0;

		final InputStream inputStream = WordFrequencyLoader.class.getResourceAsStream(filePath);

		if (inputStream != null) {

			try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
				for (String line; (line = bufferedReader.readLine()) != null;) {
					lineNum++;
					if (lineNum >= lineWordsStart) {
						final String[] entries = line.split(",");
						words.put(entries[1].trim(), Integer.parseInt(entries[0].trim()));
					}
				}

			}
		} else {
			throw new IOException("Could not create input stream.");
		}

		LOGGER.log(Level.FINE, "Finished loading " + words.size() + " words.");

		return words;
	}
}
