package nyc.angus.wordgrid.ui.solution;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nyc.angus.wordgrid.solver.solution.GridSolution;
import nyc.angus.wordgrid.solver.solution.GridWord;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Frame displaying a potential solution that allows words to be selected to highlight where they are in the grid.
 */
public class SolutionFrame extends JFrame implements MouseListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;

	/*
	 * UI Components
	 */
	private final SolutionDisplayPanel gridFrame;
	private final JList<GridWord> lstWords;
	private final DefaultListModel<GridWord> lstModelOfWords;
	private final JLabel lblPuzzle;
	private final JLabel lblWordsInSolution;

	public SolutionFrame(final GridSolution solution) {

		final Container pane = getContentPane();
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("170px"),
						FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(97dlu;default):grow"),
						FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
						new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("120px:grow"),
								RowSpec.decode("16px"), FormFactory.DEFAULT_ROWSPEC, }));
		this.setTitle("WordBrain Solution");

		lblPuzzle = new JLabel("Puzzle:");
		getContentPane().add(lblPuzzle, "3, 1");

		lblWordsInSolution = new JLabel("Words in solution (select to view):");
		getContentPane().add(lblWordsInSolution, "5, 1");

		// Initialize grid entry:
		this.gridFrame = new SolutionDisplayPanel(solution.getWords().get(0));
		pane.add(gridFrame, "3, 3, fill, center");

		lstModelOfWords = new DefaultListModel<>();
		lstWords = new JList<>(lstModelOfWords);
		lstWords.addMouseListener(this);
		lstWords.addListSelectionListener(this);
		for (final GridWord word : solution.getWords()) {
			lstModelOfWords.addElement(word);
		}

		final JScrollPane listScrollPane = new JScrollPane();
		listScrollPane.setViewportView(lstWords);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(listScrollPane, "5, 3, 1, 2, fill, fill");

		// Set size of frame:
		this.pack();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(final MouseEvent evt) {
		if (evt.getSource().equals(lstWords)) {
			changeGridSelection();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(final ListSelectionEvent evt) {
		if (evt.getSource().equals(lstWords)) {
			changeGridSelection();
		}
	}

	/**
	 * Update the grid if the user selects a different word in the grid.
	 */
	private void changeGridSelection() {
		gridFrame.updateWord(lstWords.getSelectedValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressed(final MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseReleased(final MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseExited(final MouseEvent e) {
	}
}
