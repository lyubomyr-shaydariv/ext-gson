package lsh.ext.gson.adapters;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * Represents a type adapter factory to "unpack" string-packed JSON documents. Can be used with {@link com.google.gson.annotations.JsonAdapter} only.
 * </p>
 *
 * <p>
 * An example JSON document
 * </p>
 *
 * <pre>
 * {
 *     "data": "[1,2,3]"
 * }
 * </pre>
 *
 * <p>
 * can have the following mapping:
 * </p>
 *
 * <pre>
 * final class Wrapper {
 *
 *    {@literal @}JsonAdapter(UnpackedJsonTypeAdapterFactory.class)
 *     final int[] values;
 *
 * }
 * </pre>
 *
 * @author Lyubomyr Shaydariv
 * @see UnpackedJsonTypeAdapter
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnpackedJsonTypeAdapterFactory
		implements TypeAdapterFactory {

	@Override
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		final TypeAdapter<T> delegateTypeAdapter = gson.getAdapter(typeToken);
		return new UnpackedJsonTypeAdapter<>(delegateTypeAdapter)
				.nullSafe();
	}

	private static final class UnpackedJsonTypeAdapter<T>
			extends TypeAdapter<T> {

		private final TypeAdapter<T> typeAdapter;

		private UnpackedJsonTypeAdapter(final TypeAdapter<T> typeAdapter) {
			this.typeAdapter = typeAdapter;
		}

		@Override
		public void write(final JsonWriter out, final T value)
				throws IOException {
			out.value(typeAdapter.toJson(value));
		}

		@Override
		public T read(final JsonReader in)
				throws IOException {
			return typeAdapter.fromJson(in.nextString());
		}

	}

}
