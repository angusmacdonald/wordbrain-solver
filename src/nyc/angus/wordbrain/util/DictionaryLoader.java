package nyc.angus.wordbrain.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility for loading a dictionary of words from disk into memory.
 */
public class DictionaryLoader {
	private final static Logger LOGGER = Logger.getLogger(DictionaryLoader.class.getName());

	/**
	 * Load the dictionary from the given location into memory.
	 * <p>
	 * The dictionary should be a text file, where each word is on its own line in the file.
	 * 
	 * @param fileLocation
	 *        Path to the dictionary on disk.
	 * @return The dictionary of words in a set.
	 * @throws IOException
	 *         If the dictionary could not be loaded, including if the file could not be found.
	 */
	public static Set<String> loadDictionary(final String fileLocation) throws IOException {
		LOGGER.log(Level.INFO, "Reading dictionary...");

		final Set<String> dictionary = new HashSet<>(Files.readAllLines(Paths.get(fileLocation)));

		LOGGER.log(Level.INFO, "Finished reading dictionary...");

		return dictionary;
	}
}
