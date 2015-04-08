package nyc.angus.wordgrid.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import nyc.angus.wordgrid.dictionary.DictionaryLoader;
import nyc.angus.wordgrid.dictionary.trie.TrieDictionary;
import nyc.angus.wordgrid.solver.WordGridSolver;
import nyc.angus.wordgrid.solver.solution.GridSolution;
import nyc.angus.wordgrid.ui.Printers;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests of {@link WordGridSolver}.
 */
public class SolverTests {
	private static Set<String> wordSet;
	private static TrieDictionary trieDictionary;

	private WordGridSolver solver;

	@BeforeClass
	public static void initialSetUp() throws IOException {
		wordSet = DictionaryLoader.loadDictionary("/nyc/angus/wordgrid/resource/dictionary.txt");
		trieDictionary = TrieDictionary.createTrie(wordSet);
	}

	@Before
	public void setUp() throws IOException {

		solver = new WordGridSolver(trieDictionary);
	}

	@Test(expected = NullPointerException.class)
	public void nullGrid() {
		solver.findWords(null, setUpWordLengths(4));
	}

	@Test
	public void nullWordLengths() {
		final char[][] grid = { { 'c', 'a', }, { 'r', 't' } };
		final List<GridSolution> solutions = solver.findWords(grid, null);

		assertEquals(0, solutions.size());
	}

	@Test
	public void noWordLengths() {
		final char[][] grid = { { 'c', 'a', }, { 'r', 't' } };
		final List<GridSolution> solutions = solver.findWords(grid, new LinkedList<>());
		assertEquals(0, solutions.size());
	}

	@Test
	public void oneByOneGrid() {
		final char[][] grid = { { 'a' } };
		final List<GridSolution> solutions = solver.findWords(grid, setUpWordLengths(1));
		assertEquals(0, solutions.size());
	}

	@Test
	public void mixedCase() {
		final char[][] grid = { { 'c', 'a', }, { 'R', 't' } };
		final List<GridSolution> solutions = solver.findWords(grid, setUpWordLengths(4));
		assertEquals(1, solutions.size());
		assertEquals("cart", solutions.get(0).getWords().get(0).getString());
	}

	@Test
	public void manySmallWords() {
		final char[][] grid = { { 't', 'n', 'l', 'e' }, { 'm', 'b', 'o', 'w' }, { 'a', 'o', 'r', 't' }, { 'j', 'o', 'i', 't' } };

		final Queue<Integer> wordLengths = setUpWordLengths(3, 4, 6, 3);

		final List<GridSolution> solutions = solver.findWords(grid, wordLengths);

		Printers.printSolutions(solutions);

		assertTrue(solutions.size() > 0);
	}

	@Test
	public void fourByFour2() {
		final char[][] grid = { { 'n', 'o', 'n', 'c' }, { 'p', 'n', 't', 'm' }, { 'e', 'c', 'a', 'a' }, { 'r', 'e', 'n', 'j' } };

		final Queue<Integer> wordLengths = setUpWordLengths(3, 6, 7);

		final List<GridSolution> solutions = solver.findWords(grid, wordLengths);

		Printers.printSolutions(solutions);

		assertEquals(2, solutions.size());
		final GridSolution solutionSet = solutions.get(0);

		assertTrue(solutionSet.containsWord("jam"));
		assertTrue(solutionSet.containsWord("cannon"));
		assertTrue(solutionSet.containsWord("percent"));
	}

	/**
	 * A four-by-four grid where each individual word was found by the solver, but not the whole thing. This was due to
	 * a bug found and explained in <a
	 * href="https://github.com/angusmacdonald/wordbrain-solver/commit/1abe860e4913fac4599490c38049199cbc5d35d7">this
	 * commit>.
	 */
	@Test
	public void twoSolutionBugCheck() {
		final char[][] grid = { { 'b', 'o', 'f', 't' }, { 'o', 's', 'f', 'a' }, { 's', 'r', 'i', 'e' }, { 'e', 'h', 't', 'm' } };
		// meat, sheriff, boots

		final Queue<Integer> wordLengths = setUpWordLengths(4, 7, 5);

		final List<GridSolution> solutions = solver.findWords(grid, wordLengths);

		Printers.printSolutions(solutions);

		assertTrue(solutions.size() > 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidGridEmpty() {
		final char[][] grid = new char[0][0];
		solver.findWords(grid, new LinkedList<>());
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidGridOneEmpty() {
		final char[][] grid = new char[1][0];
		solver.findWords(grid, new LinkedList<>());
	}

	private Queue<Integer> setUpWordLengths(final Integer... lengths) {
		final Queue<Integer> q = new LinkedList<>();

		for (final Integer val : lengths) {
			q.add(val);
		}

		return q;
	}
}
