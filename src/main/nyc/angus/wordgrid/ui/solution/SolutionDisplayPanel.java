package nyc.angus.wordgrid.ui.solution;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JTextField;

import nyc.angus.wordgrid.solver.solution.GridWord;
import nyc.angus.wordgrid.solver.solution.Position;

/**
 * A word in a solution, displayed within a grid. The grid is updated with a call to {@link #updateWord(GridWord)}.
 */
public class SolutionDisplayPanel extends JPanel {
	private final static Logger LOGGER = Logger.getLogger(SolutionDisplayPanel.class.getName());

	private static final long serialVersionUID = 1L;

	private final JTextField[][] characters;

	/**
	 * @param word
	 *        The word to be displayed in the grid.
	 */
	public SolutionDisplayPanel(final GridWord word) {
		final int gridSize = word.getGrid().length;

		this.setLayout(new GridBagLayout());

		/*
		 * Set up character grid.
		 */
		characters = new JTextField[gridSize][gridSize];

		for (int y = 0; y < characters.length; y++) {
			for (int x = 0; x < characters[0].length; x++) {
				final JTextField label = new JTextField("x");
				label.setEditable(false);
				label.setHorizontalAlignment(JTextField.CENTER);

				final GridBagConstraints c = new GridBagConstraints();
				c.gridx = x;
				c.gridy = y;

				c.gridwidth = 1;
				c.gridheight = 1;

				c.weightx = 2.0;
				c.weighty = 2.0;

				c.insets = new Insets(1, 1, 1, 1);

				c.fill = GridBagConstraints.HORIZONTAL;

				label.setMinimumSize(label.getPreferredSize());

				this.add(label, c);

				characters[y][x] = label;

			}
		}

		updateWord(word);
	}

	/**
	 * Update the grid to display this word highlighted, and show the state of the grid when this word must be selected.
	 */
	public void updateWord(final GridWord word) {
		final List<Position> positions = word.getPositions();

		for (int y = 0; y < characters.length; y++) {
			for (int x = 0; x < characters[0].length; x++) {
				LOGGER.log(Level.FINER, "Resetting field at (" + x + "," + y + ").");
				final JTextField field = characters[y][x];
				field.setText("");
				field.setBackground(Color.WHITE);
				field.setForeground(Color.BLACK);

			}
		}

		final List<Color> colors = ColorBands.getColorBands(Color.BLUE, positions.size());

		final Iterator<Position> positionIterator = positions.iterator();
		final Iterator<Color> colorIterator = colors.iterator();

		while (positionIterator.hasNext()) {
			final Position pos = positionIterator.next();
			final Color color = colorIterator.next();

			LOGGER.log(Level.FINER, "Setting field at (" + pos.x + "," + pos.y + ") to " + color + ".");

			final JTextField field = characters[pos.y][pos.x];

			field.setText(Character.toString(word.getGrid()[pos.y][pos.x]));
			field.setBackground(color);
			field.setForeground(Color.WHITE);

		}
	}

}
