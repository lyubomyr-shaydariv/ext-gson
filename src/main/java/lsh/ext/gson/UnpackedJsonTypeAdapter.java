package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnpackedJsonTypeAdapter<T>
		extends TypeAdapter<T> {

	private final TypeAdapter<T> typeAdapter;

	public static <T> TypeAdapter<T> getInstance(final TypeAdapter<T> typeAdapter) {
		return new UnpackedJsonTypeAdapter<>(typeAdapter)
				.nullSafe();
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

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory
			implements TypeAdapterFactory {

		@Override
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			final TypeAdapter<T> typeAdapter = gson.getAdapter(typeToken);
			return getInstance(typeAdapter);
		}

	}

}
