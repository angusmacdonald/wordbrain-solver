package nyc.angus.wordgrid.ui.solver;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

import nyc.angus.wordgrid.ui.filter.SizeDocumentFilter;

/**
 * Panel providing grid of text fields to allow entry of a word grid puzzle.
 */
public class GridEntryPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * The grid for entering characters.
	 */
	private final JTextField[][] characters;

	/**
	 * The size of the x by x grid, where this value is x.
	 */
	private final int gridSize;

	public GridEntryPanel(final int gridSize) {
		this.gridSize = gridSize;
		this.setLayout(new GridBagLayout());

		/*
		 * Set up character grid.
		 */
		characters = new JTextField[gridSize][gridSize];

		for (int i = 0; i < gridSize * gridSize; i++) {
			final JTextField gridField = new JTextField("");
			gridField.setHorizontalAlignment(JTextField.CENTER);
			setFieldToAutoTabAfterNChars(gridField, 1);

			final GridBagConstraints c = new GridBagConstraints();
			c.gridx = calculateYCoord(gridSize, i);
			c.gridy = calculateXCoord(i, gridSize);

			c.gridwidth = 1;
			c.gridheight = 1;

			c.weightx = 2.0;
			c.weighty = 2.0;

			c.insets = new Insets(1, 1, 1, 1);

			c.fill = GridBagConstraints.HORIZONTAL;

			gridField.setMinimumSize(gridField.getPreferredSize());

			this.add(gridField, c);

			characters[calculateXCoord(i, gridSize)][calculateYCoord(gridSize, i)] = gridField;
		}
	}

	/**
	 * Calculate the fields x-coordinate in the grid.
	 */
	private int calculateXCoord(final int i, final int gridSize) {
		return i / gridSize;
	}

	/**
	 * Calculate the fields y-coordinate in the grid.
	 */
	private int calculateYCoord(final int gridSize, final int i) {
		return i % gridSize;
	}

	/**
	 * Set a filter on the text field ot ensure it auto-tabs after n characters are entered.
	 */
	private void setFieldToAutoTabAfterNChars(final JTextField gridField, final int charsBeforeTab) {
		final SizeDocumentFilter sf = new SizeDocumentFilter(charsBeforeTab);
		final Document doc = gridField.getDocument();
		if (doc instanceof AbstractDocument) {
			((AbstractDocument) doc).setDocumentFilter(sf);
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
