/*
 * Copyright 2015, Angus Macdonald 
 */

package nyc.angus.wordgrid.benchmark;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Stopwatch;

/**
 * Utility for timing the execution time of a {@link Callable} instance.
 */
public class Timing {
	private final static Logger LOGGER = Logger.getLogger(Timing.class.getName());

	/**
	 * Calculate how long it takes to execute a {@link Callable} instance. Logs the return value in-case this is used to
	 * indicate correct running.
	 * 
	 * @param iterations
	 *        TODO
	 * 
	 * @return The time to execute the {@link Callable} in milliseconds.
	 * @throws Exception
	 *         If the callable failed to run or threw an exception.
	 */
	public static long time(final Callable<Boolean> action, final int iterations) throws Exception {

		long time = 0; // count all times before dividing for average.

		for (int i = 0; i < iterations; i++) {

			LOGGER.log(Level.INFO, "Starting iteration number " + i + ".");

			final Stopwatch stopwatch = Stopwatch.createStarted();

			final Boolean successful = action.call();

			LOGGER.log(Level.FINE, "Action returned: " + successful);

			stopwatch.stop();

			time += stopwatch.elapsed(TimeUnit.MILLISECONDS);
		}

		final long avg = time / iterations;

		return avg;
	}
}
