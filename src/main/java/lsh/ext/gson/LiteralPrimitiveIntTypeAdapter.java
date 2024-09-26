package lsh.ext.gson;

import java.io.IOException;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class LiteralPrimitiveIntTypeAdapter<T>
		extends TypeAdapter<T> {

	private final ToIntFunction<? super T> toInt;
	private final IntFunction<? extends T> fromInt;

	public static <T> TypeAdapter<T> getInstance(
			final ToIntFunction<? super T> toInt,
			final IntFunction<? extends T> fromInt
	) {
		return new LiteralPrimitiveIntTypeAdapter<T>(toInt, fromInt)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		out.value(toInt.applyAsInt(value));
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		return fromInt.apply(in.nextInt());
	}

}
