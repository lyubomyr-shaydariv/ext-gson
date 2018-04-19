package lsh.ext.gson.adapters.java8;

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

	private static final TypeAdapterFactory instance = new OptionalTypeAdapterFactory<>();

	private OptionalTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link OptionalTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return Optional.class.isAssignableFrom(typeToken.getRawType());
	}

	@Nonnull
	@Override
	protected TypeAdapter<Optional<T>> from(@Nonnull final TypeAdapter<T> valueTypeAdapter) {
		return OptionalTypeAdapter.get(valueTypeAdapter);
	}

}
