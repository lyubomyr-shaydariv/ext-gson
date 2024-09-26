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
public final class LiteralLongTypeAdapter<T>
		extends TypeAdapter<T> {

	private final Function<? super T, Long> toLong;
	private final Function<? super Long, ? extends T> fromLong;

	public static <T> TypeAdapter<T> getInstance(
			final Function<? super T, Long> toLong,
			final Function<? super Long, ? extends T> fromLong
	) {
		return new LiteralLongTypeAdapter<T>(toLong, fromLong)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		@Nullable
		final Long l = toLong.apply(value);
		if ( l == null ) {
			out.nullValue();
			return;
		}
		out.value(l);
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		return fromLong.apply(in.nextLong());
	}

}
