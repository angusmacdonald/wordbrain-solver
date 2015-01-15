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

import javax.swing.JFrame;
import javax.swing.JTextArea;

import nyc.angus.wordbrain.finder.WordBrainSolver;
import nyc.angus.wordbrain.util.DictionaryLoader;
import nyc.angus.wordbrain.util.Printers;

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
	private final JTextArea wordLengthEntry;
	private final Button submitButton;

	/**
	 * Solver.
	 */
	private final WordBrainSolver wordFinder;

	public GridEntryFrame(final int x, final Set<String> dictionary) {

		// Set up frame:
		final Container pane = getContentPane();
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { ColumnSpec.decode("170px"), }, new RowSpec[] { RowSpec.decode("120px"),
						RowSpec.decode("16px"), RowSpec.decode("29px"), }));
		this.setTitle("WordBrain Solver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Initialize solver:
		wordFinder = new WordBrainSolver(dictionary);

		// Initialize grid entry:
		this.gridFrame = new GridEntryPanel(x);
		pane.add(gridFrame, "1, 1, fill, center");

		// Initialize length of word entry:
		wordLengthEntry = new JTextArea("");
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
			findWords(gridFrame.createGridFromUiForm(), wordLengthEntry.getText());
		}
	}

	/**
	 * Find the words in the given grid, provided they are of the specified lengths.
	 */
	private void findWords(final char[][] grid, final String wordLengthString) {
		final Queue<Integer> wordLengths = getWordLengths(wordLengthString);

		final List<List<String>> wordsFound = wordFinder.findWords(grid, wordLengths);

		Printers.printSolutions(wordsFound);
	}

	/**
	 * Get the specified word lengths to be found in the grid.
	 */
	private Queue<Integer> getWordLengths(final String lengthsAsString) {
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
