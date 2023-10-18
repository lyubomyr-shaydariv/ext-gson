package lsh.ext.gson.ext.java.lang;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;

/**
 * Represents a type adapter factory that can handle {@link Record} initially introduced in Java 16.
 *
 * @param <T>
 * 		Record type to handle
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RecordTypeAdapterFactory<T extends Record>
		extends AbstractTypeAdapterFactory<T> {

	@Getter
	private static final TypeAdapterFactory instance = new RecordTypeAdapterFactory<>();

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return Record.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	protected TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		@SuppressWarnings("unchecked")
		final Class<T> recordClass = (Class<T>) typeToken.getRawType();
		return gson.getAdapter(recordClass);
	}

}
