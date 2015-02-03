/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.benchmark;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;

import nyc.angus.wordgrid.dictionary.Dictionary;
import nyc.angus.wordgrid.dictionary.DictionaryLoader;
import nyc.angus.wordgrid.dictionary.set.SetDictionary;
import nyc.angus.wordgrid.dictionary.trie.TrieDictionary;
import nyc.angus.wordgrid.util.SyntheticGridBuilder;

/**
 * Functions that solve the same puzzle with the set solver and the trie solver to measure the speed at which each one
 * executes.
 * <p>
 * JUnit is used for simplicity, but these are not unit tests.
 */
public class DictionaryBenchmarking {

	private static Callable<Boolean> loadDictionary = new Callable<Boolean>() {
		@Override
		public Boolean call() throws Exception {
			DictionaryLoader.loadDictionary("/nyc/angus/wordgrid/resource/dictionary.txt");
			return true;
		}
	};

	private static Callable<Boolean> loadAndCreateTrie = new Callable<Boolean>() {
		@Override
		public Boolean call() throws Exception {
			final Set<String> dict = DictionaryLoader.loadDictionary("/nyc/angus/wordgrid/resource/dictionary.txt");
			TrieDictionary.createTrie(dict);
			return true;
		}
	};

	public static Callable<Boolean> testDictionaryPerformance(final int numWords, final Dictionary dict, final Random r) {

		final Callable<Boolean> dictionaryPerformance = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {

				for (int i = 0; i < numWords; i++) {

					final int wordLength = r.nextInt(8);
					final String word = SyntheticGridBuilder.generateRandomPotentialWord(wordLength, r);

					dict.isWord(word);
				}

				return true;
			}
		};

		return dictionaryPerformance;
	}

	public static void main(final String[] args) throws Exception {
		final long createSetDictionary = Timing.time(loadDictionary, 10);
		final long createTrieDictionary = Timing.time(loadAndCreateTrie, 10);

		final Set<String> dict = DictionaryLoader.loadDictionary("/nyc/angus/wordgrid/resource/dictionary.txt");
		final TrieDictionary trieDictionary = TrieDictionary.createTrie(dict);
		final SetDictionary setDictionary = new SetDictionary(dict);

		final long trieTime = Timing.time(testDictionaryPerformance(1000000, trieDictionary, new Random(10L)), 10);
		final long setTime = Timing.time(testDictionaryPerformance(1000000, setDictionary, new Random(10L)), 10);

		System.out.println("Load dictionary time: " + createSetDictionary + " ms");
		System.out.println("Load dictionary + create trie time: " + createTrieDictionary + " ms");

		System.out.println("Trie word analysis time: " + trieTime + " ms");
		System.out.println("Set word analysis time: " + setTime + " ms");
	}

}
