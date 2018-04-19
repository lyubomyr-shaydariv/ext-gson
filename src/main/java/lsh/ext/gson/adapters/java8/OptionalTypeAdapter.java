package lsh.ext.gson.adapters.java8;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
	 * Returns an {@link Optional} type adapter.
	 *
	 * @param valueTypeAdapter {@link Optional} backed value type adapter
	 * @param <T>              Optional type parameterization
	 *
	 * @return Type adapter instance
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <T> TypeAdapter<Optional<T>> get(final TypeAdapter<T> valueTypeAdapter) {
		return new OptionalTypeAdapter<>(valueTypeAdapter);
	}

	@Nullable
	@Override
	protected T fromOptional(@Nonnull final Optional<T> optional) {
		return optional.orElse(null);
	}

	@Nonnull
	@Override
	protected Optional<T> toOptional(@Nullable final T value) {
		return Optional.ofNullable(value);
	}

}
