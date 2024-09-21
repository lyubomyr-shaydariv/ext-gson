package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEncodingTypeAdapter<T, U>
		extends TypeAdapter<T> {

	protected abstract U encode(T value)
			throws IOException;

	protected abstract void writeEncoded(JsonWriter out, U encodedValue)
			throws IOException;

	protected abstract U readEncoded(JsonReader in)
			throws IOException;

	protected abstract T decode(U encodedValue)
			throws IOException;

	@Override
	public final void write(final JsonWriter out, final T value)
			throws IOException {
		writeEncoded(out, encode(value));
	}

	@Override
	public final T read(final JsonReader in)
			throws IOException {
		final U encodedValue = readEncoded(in);
		return decode(encodedValue);
	}

}
