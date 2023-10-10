package lsh.ext.gson.ext.java.util;

import java.util.Optional;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.AbstractOptionalTypeAdapter;

/**
 * Represents a type adapter for {@link Optional} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see OptionalTypeAdapterFactory
 */
public final class OptionalTypeAdapter<T>
		extends AbstractOptionalTypeAdapter<Optional<T>, T> {

	private OptionalTypeAdapter(final TypeAdapter<T> valueTypeAdapter) {
		super(valueTypeAdapter);
	}

	/**
	 * @param valueTypeAdapter
	 *        {@link Optional} backed value type adapter
	 * @param <T>
	 * 		Optional value type
	 *
	 * @return An {@link OptionalTypeAdapter} instance.
	 */
	public static <T> TypeAdapter<Optional<T>> getInstance(final TypeAdapter<T> valueTypeAdapter) {
		return new OptionalTypeAdapter<>(valueTypeAdapter);
	}

	@Nullable
	@Override
	protected T fromOptional(final Optional<T> optional) {
		return optional.orElse(null);
	}

	@Override
	protected Optional<T> toOptional(@Nullable final T value) {
		return Optional.ofNullable(value);
	}

}
