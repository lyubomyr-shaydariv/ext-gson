package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class AbstractStringTypeAdapter<T>
		extends TypeAdapter<T> {

	protected abstract T fromString(String text);

	protected abstract String toString(T value);

	@Override
	public final T read(final JsonReader in)
			throws IOException {
		return fromString(in.nextString());
	}

	@Override
	public final void write(final JsonWriter out, final T value)
			throws IOException {
		out.value(toString(value));
	}

}
