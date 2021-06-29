package lsh.ext.gson.ext.java.util;

import java.util.Optional;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractOptionalTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Optional} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see OptionalTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class OptionalTypeAdapterFactory<T>
		extends AbstractOptionalTypeAdapterFactory<Optional<T>, T> {

	private static final TypeAdapterFactory defaultInstance = new OptionalTypeAdapterFactory<>();

	private OptionalTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link OptionalTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Optional.class;
	}

	@Nonnull
	@Override
	protected TypeAdapter<Optional<T>> from(@Nonnull final TypeAdapter<T> valueTypeAdapter) {
		return OptionalTypeAdapter.get(valueTypeAdapter);
	}

}
