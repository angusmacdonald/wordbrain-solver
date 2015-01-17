package nyc.angus.wordgrid.ui;

import java.awt.Button;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import nyc.angus.wordgrid.dictionary.Dictionary;
import nyc.angus.wordgrid.solver.WordGridSolver;

import com.google.common.base.Joiner;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Frame allowing entry of the characters in the word grid.
 */
public class WordGridSolverFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	/*
	 * UI Components
	 */
	private final GridEntryPanel gridFrame;
	private final JTextField wordLengthEntry;
	private final Button submitButton;
	private final JList<String> lstSolutions;
	private final DefaultListModel<String> listModel;

	/**
	 * Solver.
	 */
	private final WordGridSolver wordFinder;
	private final JLabel lblPuzzle;
	private final JLabel lblWordLengths;
	private final JLabel lblPotentialSolutions;

	public WordGridSolverFrame(final int x, final Dictionary dictionary) {

		// Set up frame:
		final Container pane = getContentPane();
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("170px"),
						FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(97dlu;default):grow"),
						FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("120px:grow"), FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("16px"), RowSpec.decode("29px"), FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));
		this.setTitle("WordBrain Solver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Initialize solver:
		wordFinder = new WordGridSolver(dictionary);

		lblPuzzle = new JLabel("Puzzle:");
		getContentPane().add(lblPuzzle, "3, 1");

		lblPotentialSolutions = new JLabel("Potential Solutions:");
		getContentPane().add(lblPotentialSolutions, "5, 1");

		// Initialize grid entry:
		this.gridFrame = new GridEntryPanel(x);
		pane.add(gridFrame, "3, 3, fill, center");

		listModel = new DefaultListModel<>();
		lstSolutions = new JList<>(listModel);

		final JScrollPane listScrollPane = new JScrollPane();
		listScrollPane.setViewportView(lstSolutions);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(listScrollPane, "5, 3, 1, 5, fill, fill");

		lblWordLengths = new JLabel("Word Lengths:");
		getContentPane().add(lblWordLengths, "3, 4");

		// Initialize length of word entry:
		wordLengthEntry = new JTextField("");
		pane.add(wordLengthEntry, "3, 6, fill, center");

		submitButton = new Button("Submit");
		submitButton.addActionListener(this);
		pane.add(submitButton, "3, 7, fill, center");

		// Set size of frame:
		this.pack();
	}

	/**
	 * Called when the submit button is selected.
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource().equals(submitButton)) {
			listModel.clear();
			listModel.addElement("Solving...");

			try {
				final List<LinkedList<String>> solutions = findWords(gridFrame.createGridFromUiForm(), wordLengthEntry.getText());

				showSolutions(solutions);
			} catch (final NumberFormatException e1) {
				listModel.clear();
				listModel.addElement("Input format incorrect...");
			}
		}
	}

	/**
	 * Find the words in the given grid, provided they are of the specified lengths.
	 * 
	 * @throws NumberFormatException
	 *         If the word lengths provided are not numeric.
	 */
	private List<LinkedList<String>> findWords(final char[][] grid, final String wordLengthString) throws NumberFormatException {
		final Queue<Integer> wordLengths = getWordLengths(wordLengthString);

		final List<LinkedList<String>> wordsFound = wordFinder.findWords(grid, wordLengths);

		return wordsFound;
	}

	/**
	 * Display the solutions in the interface.
	 */
	private void showSolutions(final List<LinkedList<String>> solutions) {
		listModel.clear();

		if (!solutions.isEmpty()) {
			for (final List<String> result : solutions) {
				listModel.addElement(Joiner.on(", ").join(result));
			}
		} else {
			listModel.addElement("<none found>");
		}

		Printers.printSolutions(solutions);
	}

	/**
	 * Get the specified word lengths to be found in the grid.
	 * 
	 * @throws NumberFormatException
	 *         If the word lengths provided are not numeric.
	 */
	private Queue<Integer> getWordLengths(final String lengthsAsString) throws NumberFormatException {
		final String[] splitLen = lengthsAsString.split(",");

		final Queue<Integer> lens = new LinkedList<>();

		for (final String len : splitLen) {
			lens.add(Integer.parseInt(len.trim()));
		}

		return lens;
	}
}
