package words;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

public class GridEntryPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JTextField[][] characters;

	/**
	 * The size of the x by x grid, where this value is x.
	 */
	private final int gridSize;

	public GridEntryPanel(final int x) {
		gridSize = x;
		this.setLayout(new GridBagLayout());

		characters = new JTextField[x][x];

		for (int i = 0; i < x * x; i++) {
			final JTextField button = new JTextField("");

			final SizeDocumentFilter sf = new SizeDocumentFilter(1);
			final Document doc = button.getDocument();
			if (doc instanceof AbstractDocument) {
				((AbstractDocument) doc).setDocumentFilter(sf);
			}

			final GridBagConstraints c = new GridBagConstraints();
			c.gridx = i % x;
			c.gridy = i / 4;

			c.gridwidth = 1;
			c.gridheight = 1;

			c.weightx = 2.0;
			c.weighty = 2.0;
			c.insets = new Insets(1, 1, 1, 1);
			c.fill = GridBagConstraints.HORIZONTAL;

			button.setMinimumSize(button.getPreferredSize());
			this.add(button, c);

			characters[i / 4][i % x] = button;
		}

	}

	/**
	 * Create the character grid from the entries in the UI form.
	 */
	public char[][] createGridFromUiForm() {
		final char[][] grid = new char[gridSize][gridSize];

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				final String text = characters[y][x].getText();
				final char charAt = text.length() == 0 ? ' ' : text.charAt(0);
				grid[y][x] = charAt;
			}
		}
		return grid;
	}
}
