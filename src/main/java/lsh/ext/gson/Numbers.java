package lsh.ext.gson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
	 * @param rawValue Raw value to parse
	 *
	 * @return A {@link Long} value if the given {@code rawValue} can be parsed as a long value, or a {@link Double} value if the given {@code rawValue} can be
	 * parsed as a double value, otherwise {@code null}.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nullable
	@Deprecated
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
			return Long.parseLong(s);
		} catch ( final NumberFormatException ignored ) {
			return null;
		}
	}

	@Nullable
	private static Double tryParseDouble(final String s) {
		try {
			return Double.parseDouble(s);
		} catch ( final NumberFormatException ignored ) {
			return null;
		}
	}

}
