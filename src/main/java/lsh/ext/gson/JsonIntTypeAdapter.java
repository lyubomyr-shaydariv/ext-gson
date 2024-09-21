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
public final class JsonIntTypeAdapter<T>
		extends TypeAdapter<T> {

	private final Function<? super T, Integer> toInteger;
	private final Function<? super Integer, ? extends T> fromInteger;

	public static <T> TypeAdapter<T> getInstance(
			final Function<? super T, Integer> toInteger,
			final Function<? super Integer, ? extends T> fromInteger
	) {
		return new JsonIntTypeAdapter<T>(toInteger, fromInteger)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		@Nullable
		final Integer i = toInteger.apply(value);
		if ( i == null ) {
			out.nullValue();
			return;
		}
		out.value(i);
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		return fromInteger.apply(in.nextInt());
	}

}
