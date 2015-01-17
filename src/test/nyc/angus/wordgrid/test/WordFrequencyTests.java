package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import nyc.angus.wordgrid.frequency.WordFrequencyLoader;

import org.junit.Test;

/**
 * Tests of {@link WordFrequencyLoader}.
 */
public class WordFrequencyTests {
	@Test
	public void testLoad() throws IOException {
		final Map<String, Integer> freq = WordFrequencyLoader.loadWordFrequencies("frequencies.csv");

		assertTrue(freq.size() >= 4353);
		assertEquals(Integer.valueOf(1), freq.get("the"));
	}

}
