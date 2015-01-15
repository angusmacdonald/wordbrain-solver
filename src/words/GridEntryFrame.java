package words;

import java.awt.Button;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class GridEntryFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private final Set<String> dictionary;
	private final int x;

	private final GridEntryPanel gridFrame;

	private final JTextArea wordLengthEntry;

	public GridEntryFrame(final int x, final Set<String> dictionary) {
		this.x = x;
		this.dictionary = dictionary;

		this.gridFrame = new GridEntryPanel(x);

		final Container pane = getContentPane();
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { ColumnSpec.decode("170px"), }, new RowSpec[] { RowSpec.decode("120px"),
						RowSpec.decode("16px"), RowSpec.decode("29px"), }));

		pane.add(gridFrame, "1, 1, fill, center");

		wordLengthEntry = new JTextArea("");
		pane.add(wordLengthEntry, "1, 2, fill, center");

		final Button submitButton = new Button("Submit");
		submitButton.addActionListener(this);
		pane.add(submitButton, "1, 3, fill, center");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();

		this.setSize(300, 300);
	}

	public static void main(final String[] args) throws IOException {
		System.out.println("Reading dictionary...");
		final List<String> dictionaryAsList = Files.readAllLines(Paths.get("", "dictionary.txt"));
		final Set<String> dictionary = new HashSet<>(dictionaryAsList);
		System.out.println("Finished reading dictionary...");

		final GridEntryFrame gst = new GridEntryFrame(4, dictionary);

		gst.setVisible(true);

	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final WordFinder finder = new WordFinder(dictionary);

		final String lengths = wordLengthEntry.getText();

		final String[] splitLen = lengths.split(",");

		final Queue<Integer> lens = new LinkedList<>();

		for (final String len : splitLen) {
			lens.add(Integer.parseInt(len.trim()));
		}

		final List<List<String>> wordsFound = finder.findWords(createGrid(), lens);

		printWords(wordsFound);
	}

	private char[][] createGrid() {
		final char[][] grid = new char[x][x];

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				final String text = gridFrame.getCharacters()[y][x].getText();
				final char charAt = text.length() == 0 ? ' ' : text.charAt(0);
				grid[y][x] = charAt;
			}
		}
		return grid;
	}

	private static void printWords(final List<List<String>> wordsFound) {
		System.out.println("Words found:");
		for (final List<String> result : wordsFound) {
			for (final String word : result) {
				System.out.print(word + ", ");
			}
			System.out.println("");
		}
	}

}
