package nyc.angus.wordgrid.ui;

import java.util.List;

import nyc.angus.wordgrid.solver.solution.GridSolution;

import com.google.common.base.Joiner;

/**
 * Utility for printing out results from the solver.
 */
public class Printers {

	public static String solutionToString(final List<GridSolution> wordsFound) {

		final StringBuffer ret = new StringBuffer("Solutions found: ");

		if (!wordsFound.isEmpty()) {
			for (final GridSolution result : wordsFound) {
				ret.append(Joiner.on(", ").join(result.getWords()));
			}
		} else {
			ret.append("<none>");
		}

		return ret.toString();
	}
}
