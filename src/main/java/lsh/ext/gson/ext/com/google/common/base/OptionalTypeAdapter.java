package lsh.ext.gson.ext.com.google.common.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractOptionalTypeAdapter;

/**
 * Represents a type adapter for {@link Optional} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see OptionalTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class OptionalTypeAdapter<T>
		extends AbstractOptionalTypeAdapter<Optional<T>, T> {

	private OptionalTypeAdapter(final TypeAdapter<T> valueTypeAdapter) {
		super(valueTypeAdapter);
	}

	/**
	 * @param valueTypeAdapter {@link Optional} backed value type adapter
	 * @param <T>              Optional value type
	 *
	 * @return An {@link OptionalTypeAdapter} instance
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <T> TypeAdapter<Optional<T>> create(final TypeAdapter<T> valueTypeAdapter) {
		return new OptionalTypeAdapter<>(valueTypeAdapter);
	}

	@Nullable
	@Override
	protected T fromOptional(@Nonnull final Optional<T> optional) {
		return optional.orNull();
	}

	@Override
	@Nonnull
	protected Optional<T> toOptional(@Nullable final T value) {
		return Optional.fromNullable(value);
	}

}
