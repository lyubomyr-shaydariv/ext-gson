package lsh.ext.gson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

/**
 * Provides miscellaneous number utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class Numbers {

	private Numbers() {
	}

	/**
	 * Tries to parse the given raw value as a number of {@link Long} or {@link Double}.
	 *
	 * @param rawValue Raw value to parse
	 *
	 * @return A {@link Long} value if the given {@code rawValue} can be parsed as a long value, or a {@link Double} value if the given {@code rawValue} can be
	 * parsed as a double value, otherwise {@code null}.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nullable
	public static Number tryParseLongOrDouble(@Nonnull final String rawValue) {
		final Long longValue = tryParseLong(rawValue);
		if ( longValue == null ) {
			return tryParseDouble(rawValue);
		}
		return longValue;
	}

	@Nullable
	private static Long tryParseLong(final String s) {
		try {
			return parseLong(s);
		} catch ( final NumberFormatException ignored ) {
			return null;
		}
	}

	@Nullable
	private static Double tryParseDouble(final String s) {
		try {
			return parseDouble(s);
		} catch ( final NumberFormatException ignored ) {
			return null;
		}
	}

}
