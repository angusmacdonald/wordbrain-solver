package nyc.angus.wordgrid.ui.solution;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility for getting n different shades of a particular color.
 * <p>
 * From http://stackoverflow.com/questions/18605410/algorithm-to-show-n-different-shades-of-the-same-color-in-order-of-
 * decreasing-da.
 */
public class ColorBands {
	/**
	 * Generate n shades of a given color.
	 */
	public static List<Color> getColorBands(final Color color, final int bands) {
		final List<Color> colorBands = new ArrayList<>(bands);

		for (int index = 0; index < bands; index++) {
			colorBands.add(darken(color, (double) index / (double) bands));
		}

		return colorBands;
	}

	private static Color darken(final Color color, final double fraction) {

		final int red = (int) Math.round(Math.max(0, color.getRed() - 255 * fraction));
		final int green = (int) Math.round(Math.max(0, color.getGreen() - 255 * fraction));
		final int blue = (int) Math.round(Math.max(0, color.getBlue() - 255 * fraction));

		final int alpha = color.getAlpha();

		return new Color(red, green, blue, alpha);

	}
}
