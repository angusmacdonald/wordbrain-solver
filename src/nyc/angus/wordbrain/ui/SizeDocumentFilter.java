package nyc.angus.wordbrain.ui;

import java.awt.Component;
import java.awt.KeyboardFocusManager;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;

/**
 * A DocumentFilter that allows you to control the maximum number of characters that can be added to the Document. When
 * the Document is full you can optionally tab to the next component to speed data entry.
 * <p>
 * This class can also be used as a generic size filter for JTextFields. In this case when a size of 0 is speicifed for
 * the size of the Document the getColumns() method of JTextField will be used to determine the size restriction.
 * <p>
 * Taken from https://tips4java.wordpress.com/2009/10/25/size-document-filter/.
 */
public class SizeDocumentFilter extends DocumentFilter {
	private int size;
	private boolean autoTab = true;

	/**
	 * Generic constructor for use with JTextFields only. The size of the Document will be determined by the value of
	 * the getColumns() method.
	 */
	public SizeDocumentFilter() {
		this(0);
	}

	/**
	 * Constructor to set the size for this filter
	 *
	 * @param size
	 *        maximum number of characters to be added to the Document
	 */
	public SizeDocumentFilter(final int size) {
		this(size, null);
	}

	/**
	 * Constructor to set the size for this filter as well as provide additional filtering
	 *
	 * @param size
	 *        maximum number of characters to be added to the Document
	 * @param filter
	 *        another DocumentFilter to invoke
	 */
	public SizeDocumentFilter(final int size, final DocumentFilter filter) {
		setSize(size);
	}

	/**
	 * Get the auto tab property
	 *
	 * @return the auto tab property
	 */
	public boolean getAutoTab() {
		return autoTab;
	}

	/**
	 * Set the auto tab property
	 *
	 * @param autoTab
	 *        the default is true
	 */
	public void setAutoTab(final boolean autoTab) {
		this.autoTab = autoTab;
	}

	/**
	 * Get the maximum size for any Document using this filter
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Set maximum size for a Document using this filter. Dynamically changing the size will not affect existing
	 * Documents. Characters will not be removed from any Document. The filter will only be invoked on new additions to
	 * the Document.
	 *
	 * @param size
	 *        the maximum number of character allowed in the Document
	 */
	public void setSize(final int size) {
		this.size = size;
	}

	/**
	 * Make sure the insertion of text will not cause the Document to exceed its size limit. Also, potentially tab to
	 * next component when full.
	 */
	@Override
	public void insertString(final FilterBypass fb, final int offs, final String str, final AttributeSet a) throws BadLocationException {
		final int possibleSize = fb.getDocument().getLength() + str.length();
		final int allowedSize = getAllowedSize(fb);

		if (possibleSize <= allowedSize) {
			super.insertString(fb, offs, str, a);
			handleAutoTab(possibleSize, allowedSize, fb);
		}
	}

	/**
	 * Make sure the replacement of text will not cause the Document to exceed its size limit. Also, potentially tab to
	 * next component when full.
	 */
	@Override
	public void replace(final FilterBypass fb, final int offs, final int length, final String str, final AttributeSet a)
			throws BadLocationException {
		final int possibleSize = fb.getDocument().getLength() + str.length() - length;
		final int allowedSize = getAllowedSize(fb);

		if (possibleSize <= allowedSize) {
			super.replace(fb, offs, length, str, a);
			handleAutoTab(possibleSize, allowedSize, fb);
		}
	}

	/**
	 * When a size isn't specified then we assume the desired size can be obtained from the associated text field.
	 * Otherwise, use the class size property.
	 */
	private int getAllowedSize(final FilterBypass fb) {
		return (size == 0) ? getColumns(fb) : size;
	}

	/*
	 * Use the value returnd by invoking the getColumns() method of JTextField
	 */
	private int getColumns(final FilterBypass fb) {
		// Find the text field that currently has focus
		// and make sure it is using the Document that will be updated

		final Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

		if (c != null && c instanceof JTextField) {
			final JTextField textField = (JTextField) c;
			final Document doc = textField.getDocument();

			if (doc.equals(fb.getDocument())) {
				return textField.getColumns();
			}
		}

		return 0;
	}

	/*
	 * When the Document is full tab to the next component.
	 */
	protected void handleAutoTab(final int possibleSize, final int allowedSize, final FilterBypass fb) {
		if (autoTab == false || possibleSize != allowedSize) {
			return;
		}

		// Find the text field that currently has focus
		// and make sure it is using the Document that has been updated

		final Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

		if (c != null && c instanceof JTextComponent) {
			final JTextComponent component = (JTextComponent) c;
			final Document doc = component.getDocument();

			if (doc.equals(fb.getDocument())) {
				c.transferFocus();
			}
		}
	}
}