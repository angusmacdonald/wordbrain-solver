package words;

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

import com.google.common.base.Joiner;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class GridEntryFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private final GridEntryPanel gridFrame;

	private final JTextArea wordLengthEntry;

	private final WordFinder wordFinder;

	public GridEntryFrame(final int x, final Set<String> dictionary) {

		// Set up frame:
		final Container pane = getContentPane();
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { ColumnSpec.decode("170px"), }, new RowSpec[] { RowSpec.decode("120px"),
						RowSpec.decode("16px"), RowSpec.decode("29px"), }));
		this.setTitle("WordBrain Solver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Initialize solver:
		wordFinder = new WordFinder(dictionary);

		// Initialize grid entry:
		this.gridFrame = new GridEntryPanel(x);
		pane.add(gridFrame, "1, 1, fill, center");

		// Initialize length of word entry:
		wordLengthEntry = new JTextArea("");
		pane.add(wordLengthEntry, "1, 2, fill, center");

		// Initialize submit button:
		final Button submitButton = new Button("Submit");
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

		final String lengths = wordLengthEntry.getText();

		final String[] splitLen = lengths.split(",");

		final Queue<Integer> lens = new LinkedList<>();

		for (final String len : splitLen) {
			lens.add(Integer.parseInt(len.trim()));
		}

		final List<List<String>> wordsFound = wordFinder.findWords(gridFrame.createGridFromUiForm(), lens);

		printWords(wordsFound);
	}

	private static void printWords(final List<List<String>> wordsFound) {
		System.out.println("Words found:");
		for (final List<String> result : wordsFound) {
			Joiner.on(", ").join(result);
		}
	}

	public static void main(final String[] args) throws IOException {
		final GridEntryFrame gst = new GridEntryFrame(4, DictionaryLoader.loadDictionary("dictionary.txt"));
		gst.setVisible(true);
	}
}
