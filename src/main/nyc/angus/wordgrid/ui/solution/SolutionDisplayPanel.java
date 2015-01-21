package nyc.angus.wordgrid.ui.solution;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextField;

import nyc.angus.wordgrid.solver.solution.GridWord;
import nyc.angus.wordgrid.solver.solution.Position;

public class SolutionDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JTextField[][] characters;

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
		final Set<Position> positions = word.getPositions();

		for (int y = 0; y < characters.length; y++) {
			for (int x = 0; x < characters[0].length; x++) {

				final JTextField field = characters[y][x];

				field.setText(Character.toString(word.getGrid()[y][x]));

				if (positions.contains(new Position(x, y))) {
					field.setBackground(Color.BLUE);
					field.setForeground(Color.WHITE);
				} else {
					field.setBackground(Color.WHITE);
					field.setForeground(Color.BLACK);
				}

			}
		}
	}
}
