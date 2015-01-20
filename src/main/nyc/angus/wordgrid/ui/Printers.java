/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.ui;

import java.util.List;

import nyc.angus.wordgrid.solver.solution.GridSolution;

import com.google.common.base.Joiner;

/**
 * Utility for printing out results from the solver.
 */
public class Printers {

	public static void printSolutions(final List<GridSolution> wordsFound) {
		System.out.print("Solutions found: ");

		if (!wordsFound.isEmpty()) {
			for (final GridSolution result : wordsFound) {
				System.out.println(Joiner.on(", ").join(result.getWords()));
			}
		} else {
			System.out.println("<none>");
		}
	}
}
