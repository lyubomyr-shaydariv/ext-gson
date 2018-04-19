package lsh.ext.gson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * An interface to parse a number from a string.
 *
 * @param <N> Number type
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public interface INumberParser<N extends Number> {

	/**
	 * @param value A string to parse
	 *
	 * @return A from-string parsed number, or a {@code null} if parser failed.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nullable
	N parseNumber(@Nonnull String value);

}
