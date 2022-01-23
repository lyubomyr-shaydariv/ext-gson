package lsh.ext.gson.ext.java.lang;

import javax.annotation.Nonnull;

import com.google.common.annotations.Beta;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * Represents a type adapter factory that can handle {@link Record} initially introduced in Java 16.
 *
 * @param <T> Record type to handle
 *
 * @author Lyubomyr Shaydariv
 * @see RecordTypeAdapter
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Beta
public final class RecordTypeAdapterFactory<T extends Record>
		extends AbstractTypeAdapterFactory<T> {

	private static final TypeAdapterFactory instance = new RecordTypeAdapterFactory<>();

	/**
	 * @return The type adapter factory instance.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return Record.class.isAssignableFrom(typeToken.getRawType());
	}

	@Nonnull
	@Override
	protected TypeAdapter<T> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		try {
			@SuppressWarnings("unchecked")
			final Class<T> recordClass = (Class<T>) typeToken.getRawType();
			return RecordTypeAdapter.getInstance(recordClass, gson);
		} catch ( final NoSuchMethodException ex ) {
			throw new RuntimeException(ex);
		}
	}

}
