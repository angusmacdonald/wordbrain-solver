package words;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class GridEntryPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JTextField[][] characters;

	public GridEntryPanel(final int x) {
		this.setLayout(new GridBagLayout());

		characters = new JTextField[x][x];

		for (int i = 0; i < x * x; i++) {
			final JTextField button = new JTextField("");
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

	public JTextField[][] getCharacters() {
		return characters;
	}
}
