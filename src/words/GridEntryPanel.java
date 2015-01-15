package words;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class GridEntryPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField[][] characters;

	public GridEntryPanel(int x) {
		this.setLayout(new GridBagLayout());

		characters = new JTextField[x][x];

		for (int i = 0; i < x * x; i++) {
			JTextField button = new JTextField("");
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = i % x;
			c.gridy = i / 4;

			c.gridwidth = 1;
			c.gridheight = 1;

			c.weightx = 1.0;
			c.weighty = 1.0;
			c.insets = new Insets(5, 5, 5, 5);
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
