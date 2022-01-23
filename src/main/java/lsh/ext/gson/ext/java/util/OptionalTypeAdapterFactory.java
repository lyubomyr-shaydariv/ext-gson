package lsh.ext.gson.ext.java.util;

import java.util.Optional;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractOptionalTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Optional} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see OptionalTypeAdapter
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalTypeAdapterFactory<T>
		extends AbstractOptionalTypeAdapterFactory<Optional<T>, T> {

	private static final TypeAdapterFactory instance = new OptionalTypeAdapterFactory<>();

	/**
	 * @return An instance of {@link OptionalTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Optional.class;
	}

	@Override
	protected TypeAdapter<Optional<T>> from(final TypeAdapter<T> valueTypeAdapter) {
		return OptionalTypeAdapter.getInstance(valueTypeAdapter);
	}

}
