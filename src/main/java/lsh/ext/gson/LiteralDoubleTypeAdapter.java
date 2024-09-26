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
public final class LiteralDoubleTypeAdapter<T>
		extends TypeAdapter<T> {

	private final Function<? super T, Double> toDouble;
	private final Function<? super Double, ? extends T> fromDouble;

	public static <T> TypeAdapter<T> getInstance(
			final Function<? super T, Double> toDouble,
			final Function<? super Double, ? extends T> fromDouble
	) {
		return new LiteralDoubleTypeAdapter<T>(toDouble, fromDouble)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		@Nullable
		final Double d = toDouble.apply(value);
		if ( d == null ) {
			out.nullValue();
			return;
		}
		out.value(d);
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		return fromDouble.apply(in.nextDouble());
	}

}
