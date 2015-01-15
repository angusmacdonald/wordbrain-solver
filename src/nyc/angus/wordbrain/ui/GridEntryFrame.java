package nyc.angus.wordbrain.ui;

import java.awt.Button;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;

import nyc.angus.wordbrain.finder.WordBrainSolver;
import nyc.angus.wordbrain.util.DictionaryLoader;
import nyc.angus.wordbrain.util.Printers;

import com.google.common.base.Joiner;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Frame allowing entry of the characters in the word grid.
 */
public class GridEntryFrame extends JFrame implements ActionListener {
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
	private final WordBrainSolver wordFinder;

	public GridEntryFrame(final int x, final Set<String> dictionary) {

		// Set up frame:
		final Container pane = getContentPane();
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { ColumnSpec.decode("170px"), FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(97dlu;default):grow"), }, new RowSpec[] { RowSpec.decode("120px:grow"),
						RowSpec.decode("16px"), RowSpec.decode("29px"), }));
		this.setTitle("WordBrain Solver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Initialize solver:
		wordFinder = new WordBrainSolver(dictionary);

		// Initialize grid entry:
		this.gridFrame = new GridEntryPanel(x);
		pane.add(gridFrame, "1, 1, fill, center");

		listModel = new DefaultListModel<>();
		lstSolutions = new JList<>(listModel);

		getContentPane().add(lstSolutions, "3, 1, 1, 3, fill, fill");

		// Initialize length of word entry:
		wordLengthEntry = new JTextField("");
		pane.add(wordLengthEntry, "1, 2, fill, center");

		submitButton = new Button("Submit");
		submitButton.addActionListener(this);
		pane.add(submitButton, "1, 3, fill, center");

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

	public static void main(final String[] args) throws IOException {
		final GridEntryFrame gst = new GridEntryFrame(4, DictionaryLoader.loadDictionary("dictionary.txt"));
		gst.setVisible(true);
	}
}
