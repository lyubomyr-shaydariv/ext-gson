package lsh.ext.gson.adapters;

import java.io.IOException;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class AbstractStringTypeAdapter<T>
		extends TypeAdapter<T> {

	@Nonnull
	protected abstract T fromString(@Nonnull String string);

	@Nonnull
	protected abstract String toString(@Nonnull T value);

	@Override
	public final T read(final JsonReader in)
			throws IOException {
		final String string = in.nextString();
		return fromString(string);
	}

	@Override
	@SuppressWarnings("resource")
	public final void write(final JsonWriter out, final T value)
			throws IOException {
		final String string = toString(value);
		out.value(string);
	}

}
