package lsh.ext.gson.ext.java.lang;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Represents a type adapter that can handle {@link Record} initially introduced in Java 16.
 *
 * Due to the current design issues of {@link com.google.gson.internal.bind.ReflectiveTypeAdapterFactory} that cannot be configured directly in flexible way,
 * this type adapter factory creates a shadowing POJO class that mimics the record class to Gson and does mapping between two. The POJO class is generated at
 * runtime.
 *
 * @param <T>
 * 		Record type to handle
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RecordTypeAdapter<T extends Record>
		extends TypeAdapter<T> {

	private final RecordShadowHandler<T> recordShadowHandler;
	private final TypeAdapter<Object> shadowClassTypeAdapter;

	/**
	 * @param <T>
	 * 		Record class type parameter to handle
	 * @param recordClass
	 * 		Record class
	 * @param gson
	 * 		Gson instance to get the shadowing class type adapter from
	 *
	 * @return A new {@link RecordTypeAdapter} instance.
	 */
	public static <T extends Record> TypeAdapter<T> getInstance(final Class<T> recordClass, final Gson gson)
			throws NoSuchMethodException {
		final RecordShadowHandler<T> recordShadowHandler = RecordShadowHandler.from(recordClass);
		final TypeAdapter<Object> shadowClassTypeAdapter = recordShadowHandler.getShadowClassTypeAdapter(gson);
		return new RecordTypeAdapter<>(recordShadowHandler, shadowClassTypeAdapter);
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		try {
			final Object shadow = recordShadowHandler.toShadow(value);
			shadowClassTypeAdapter.write(out, shadow);
		} catch ( final InvocationTargetException | InstantiationException | IllegalAccessException ex ) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		try {
			final Object shadow = shadowClassTypeAdapter.read(in);
			return recordShadowHandler.toRecord(shadow);
		} catch ( final InvocationTargetException | InstantiationException | IllegalAccessException ex ) {
			throw new RuntimeException(ex);
		}
	}

}
