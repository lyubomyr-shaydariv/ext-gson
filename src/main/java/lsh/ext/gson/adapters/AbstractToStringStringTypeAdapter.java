package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;

/**
 * <p>
 * A string-oriented type adapter that emits strings with {@link Object#toString()} when generating strings.
 * </p>
 *
 * @param <T> Any type
 *
 * @author Lyubomyr Shaydariv
 */
public abstract class AbstractToStringStringTypeAdapter<T>
		extends AbstractStringTypeAdapter<T> {

	@Nonnull
	@Override
	protected final String toString(@Nonnull final T value) {
		return value.toString();
	}

}
