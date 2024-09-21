package lsh.ext.gson;

import java.io.IOException;
import java.util.function.LongFunction;
import java.util.function.ToLongFunction;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class JsonPrimitiveLongTypeAdapter<T>
		extends TypeAdapter<T> {

	private final ToLongFunction<? super T> toLong;
	private final LongFunction<? extends T> fromLong;

	public static <T> TypeAdapter<T> getInstance(
			final ToLongFunction<? super T> toLong,
			final LongFunction<? extends T> fromLong
	) {
		return new JsonPrimitiveLongTypeAdapter<T>(toLong, fromLong)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		out.value(toLong.applyAsLong(value));
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		return fromLong.apply(in.nextLong());
	}

}
