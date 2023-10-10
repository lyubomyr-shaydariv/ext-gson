package lsh.ext.gson.ext.com.google.common.base;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
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
	 * @return An {@link OptionalTypeAdapter} instance
	 */
	public static <T> TypeAdapter<Optional<T>> getInstance(final TypeAdapter<T> valueTypeAdapter) {
		return new OptionalTypeAdapter<>(valueTypeAdapter);
	}

	@Nullable
	@Override
	protected T fromOptional(@SuppressWarnings("Guava") final Optional<T> optional) {
		return optional.orNull();
	}

	@Override
	@SuppressWarnings("Guava")
	protected Optional<T> toOptional(@Nullable final T value) {
		return Optional.fromNullable(value);
	}

}
