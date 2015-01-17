package nyc.angus.wordgrid.frequency;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility for sorting a list of words (solutions in the word grid) by their frequency in an english corpus.
 */
public class WordFrequencySorting {

	private final Map<String, Integer> frequencies;

	/**
	 * @param frequencies
	 *        Mapping from word to the ranking of the frequency of its usage. The most commonly used word is ranked 1.
	 */
	public WordFrequencySorting(final Map<String, Integer> frequencies) {
		this.frequencies = frequencies;
	}

	public static List<LinkedList<String>> removeDuplicates(final List<LinkedList<String>> solutions) {
		final List<LinkedList<String>> noDupSolutions = new LinkedList<>();

		final Set<Integer> seenHashCodes = new HashSet<>();

		for (final LinkedList<String> solution : solutions) {
			int hashCode = 0;

			for (final String word : solution) {
				hashCode += word.hashCode();
			}

			if (!seenHashCodes.contains(hashCode)) {
				noDupSolutions.add(solution);
				seenHashCodes.add(hashCode);
			}
		}

		return noDupSolutions;
	}

	/**
	 * Sort the list of solutions by the frequency with which the words in the solutions appear in the frequencies
	 * provided on initialization of the object.
	 */
	public void sortSolutionsByFrequency(final List<LinkedList<String>> solutions) {

		final Comparator<LinkedList<String>> c = new Comparator<LinkedList<String>>() {

			@Override
			public int compare(final LinkedList<String> o1, final LinkedList<String> o2) {
				final int o1Rank = rank(o1);
				final int o2Rank = rank(o2);

				return o1Rank - o2Rank;
			}

		};

		Collections.<LinkedList<String>> sort(solutions, c);
	}

	/**
	 * Rank solutions by the frequency of words in an english corpus.
	 * <p>
	 * An individual word is ranked lower the more frequently it is used ('the' is the most common word and is ranked
	 * 1), and words not in the ~5000 word corpus are ranked 5000. The solution shown first is the one with the lowest
	 * ranked set of words.
	 * 
	 * @param words
	 *        The list of words to be ranked.
	 * @return The lower the number, the higher the ranking.
	 */
	private int rank(final LinkedList<String> words) {
		int ranking = 0;

		for (final String word : words) {
			final Integer frequency = frequencies.get(word);

			ranking += frequency == null ? 5000 : frequency;
		}

		return ranking;
	}
}
