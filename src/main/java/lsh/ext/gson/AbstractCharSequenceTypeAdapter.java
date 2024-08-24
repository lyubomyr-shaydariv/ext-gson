package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

// TODO reasonable?
public abstract class AbstractCharSequenceTypeAdapter<T>
		extends TypeAdapter<T> {

	protected abstract T fromCharSequence(CharSequence cs);

	protected abstract CharSequence toCharSequence(T value);

	@Override
	public final T read(final JsonReader in)
			throws IOException {
		return fromCharSequence(in.nextString());
	}

	@Override
	public final void write(final JsonWriter out, final T value)
			throws IOException {
		out.value(toCharSequence(value).toString());
	}

}
