/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordbrain.util;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Joiner;

/**
 * Utility for printing out results from the solver.
 */
public class Printers {

	public static void printSolutions(final List<LinkedList<String>> wordsFound) {
		System.out.print("Solutions found: ");

		if (!wordsFound.isEmpty()) {
			for (final List<String> result : wordsFound) {
				System.out.println(Joiner.on(", ").join(result));
			}
		} else {
			System.out.println("<none>");
		}
	}
}
