package lsh.ext.gson;

import java.io.IOException;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class JsonStringTypeAdapter<T>
		extends TypeAdapter<T> {

	private final Function<? super T, String> toString;
	private final Function<? super String, ? extends T> fromString;

	public static <T> TypeAdapter<T> getInstance(
			final Function<? super T, String> toString,
			final Function<? super String, ? extends T> fromString
	) {
		return new JsonStringTypeAdapter<T>(toString, fromString)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		@Nullable
		final String s = toString.apply(value);
		if ( s == null ) {
			out.nullValue();
			return;
		}
		out.value(s);
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		return fromString.apply(in.nextString());
	}

}
