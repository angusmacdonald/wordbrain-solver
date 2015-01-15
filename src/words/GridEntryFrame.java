package words;

import java.awt.Button;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class GridEntryFrame extends JFrame  implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private Set<String> dictionary;
	private int x;
	
	private GridEntryPanel gridFrame;

	private JTextArea wordLengthEntry;

	public GridEntryFrame(int x, Set<String> dictionary) {
		this.x = x;
		this.dictionary = dictionary;
		
		this.gridFrame = new GridEntryPanel(x);
		
		Container pane = getContentPane();
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("170px"),},
			new RowSpec[] {
				RowSpec.decode("120px"),
				RowSpec.decode("16px"),
				RowSpec.decode("29px"),}));
		
		pane.add(gridFrame, "1, 1, fill, center");
		
		
		wordLengthEntry = new JTextArea("");
		pane.add(wordLengthEntry, "1, 2, fill, center");
		
		
		
		Button submitButton = new Button("Submit");
		submitButton.addActionListener(this);
		pane.add(submitButton, "1, 3, fill, center");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		
		this.setSize(300, 300);
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Reading dictionary...");
		List<String> dictionaryAsList = Files.readAllLines(Paths.get("", "dictionary.txt"));
		Set<String> dictionary = new HashSet<>(dictionaryAsList);
		System.out.println("Finished reading dictionary...");
		
		
		GridEntryFrame gst = new GridEntryFrame(4, dictionary);
		
		
		gst.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		WordFinder finder = new WordFinder(dictionary);
		
		int len = Integer.parseInt(wordLengthEntry.getText());
		List<String> wordsFound = finder.findWords(createGrid(), len);
		
		printWords(wordsFound);
	}

	private char[][] createGrid() {
		char[][] grid = new char[x][x];
		
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				grid[y][x] = gridFrame.getCharacters()[y][x].getText().charAt(0);
			}
		}
		return grid;
	}
	
	private static void printWords(List<String> wordsFound) {
		System.out.println("Words found:");
		for (String string : wordsFound) {
			System.out.println(string);
		}
	}
	
}
