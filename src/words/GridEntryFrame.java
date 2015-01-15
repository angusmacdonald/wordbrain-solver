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
		pane.setLayout(new GridBagLayout());
		
		GridBagConstraints entryConstraint = new GridBagConstraints();
		entryConstraint.gridy = 0;
		entryConstraint.gridx = 0;
		
		pane.add(gridFrame, entryConstraint);
		
		
		wordLengthEntry = new JTextArea("");
	
		GridBagConstraints lengthConstraint = new GridBagConstraints();
		lengthConstraint.fill = GridBagConstraints.HORIZONTAL;
		lengthConstraint.gridy = 1;
		lengthConstraint.gridx = 0;
		pane.add(wordLengthEntry, lengthConstraint);
		
		
		
		Button submitButton = new Button("Submit");
		submitButton.addActionListener(this);
		
		GridBagConstraints buttonConstraint = new GridBagConstraints();
		buttonConstraint.fill = GridBagConstraints.HORIZONTAL;
		buttonConstraint.gridy = 2;
		buttonConstraint.gridx = 0;
		pane.add(submitButton, buttonConstraint);
		
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
