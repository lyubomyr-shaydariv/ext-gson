package lsh.ext.gson;

import java.io.IOException;
import java.util.function.DoubleFunction;
import java.util.function.ToDoubleFunction;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class LiteralPrimitiveDoubleTypeAdapter<T>
		extends TypeAdapter<T> {

	private final ToDoubleFunction<? super T> toDouble;
	private final DoubleFunction<? extends T> fromDouble;

	public static <T> TypeAdapter<T> getInstance(
			final ToDoubleFunction<? super T> toDouble,
			final DoubleFunction<? extends T> fromDouble
	) {
		return new LiteralPrimitiveDoubleTypeAdapter<T>(toDouble, fromDouble)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		out.value(toDouble.applyAsDouble(value));
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		return fromDouble.apply(in.nextDouble());
	}

}
