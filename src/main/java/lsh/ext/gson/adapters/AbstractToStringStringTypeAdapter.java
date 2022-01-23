package lsh.ext.gson.adapters;



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

	@Override
	protected final String toString(final T value) {
		return value.toString();
	}

}
