/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import nyc.angus.wordgrid.dictionary.Dictionary;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Initial frame shown on startup to select the size of the grid to display.
 */
public class SelectGridSizeFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JTextField txtGridSizeField;
	private final JButton btnSetUpGrid;
	private final Dictionary dictionary;

	public SelectGridSizeFrame(final Dictionary dictionary) {
		this.setTitle("WordBrain Solver");

		this.dictionary = dictionary;

		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		final JLabel lblSelectGridSize = new JLabel("Select grid size:");
		getContentPane().add(lblSelectGridSize, "2, 2");

		txtGridSizeField = new JTextField();
		getContentPane().add(txtGridSizeField, "2, 4, fill, default");
		txtGridSizeField.setColumns(10);

		btnSetUpGrid = new JButton("Set Up Grid");
		btnSetUpGrid.addActionListener(this);
		getContentPane().add(btnSetUpGrid, "2, 6");

		this.pack();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource().equals(btnSetUpGrid)) {
			final Integer gridSize = Integer.parseInt(txtGridSizeField.getText());
			final WordGridSolverFrame gst = new WordGridSolverFrame(gridSize, dictionary);
			gst.setVisible(true);
		}
	}

}
