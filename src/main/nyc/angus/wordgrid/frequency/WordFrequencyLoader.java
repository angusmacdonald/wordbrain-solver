package nyc.angus.wordgrid.frequency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordFrequencyLoader {
	private final static Logger LOGGER = Logger.getLogger(WordFrequencyLoader.class.getName());

	public static Map<String, Integer> loadWordFrequencies(final String filePath) throws IOException {

		final Map<String, Integer> words = new HashMap<>();

		int lineNum = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			for (String line; (line = br.readLine()) != null;) {
				lineNum++;
				if (lineNum > 4) {
					final String[] entries = line.split(",");
					words.put(entries[1].trim(), Integer.parseInt(entries[0].trim()));
				}
			}

		}

		LOGGER.log(Level.INFO, "Finished loading " + words.size() + " words.");

		return words;
	}
}
