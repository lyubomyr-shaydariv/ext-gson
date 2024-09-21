package lsh.ext.gson.domain.unix;

import java.io.IOException;
import java.util.function.LongFunction;
import java.util.function.ToLongFunction;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractRawClassHierarchyTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnixTimeTypeAdapter<T>
		extends TypeAdapter<T> {

	private final ToLongFunction<? super T> encode;
	private final LongFunction<? extends T> decode;

	public static <T> TypeAdapter<T> getInstance(final ToLongFunction<? super T> encode, final LongFunction<? extends T> decode) {
		return new UnixTimeTypeAdapter<T>(encode, decode)
				.nullSafe();
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		return decode.apply(in.nextLong() * 1000);
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		out.value(encode.applyAsLong(value) / 1000);
	}

	public static final class Factory<T>
			extends AbstractRawClassHierarchyTypeAdapterFactory<T> {

		private final TypeAdapter<T> typeAdapter;

		private Factory(final Class<T> klass, final TypeAdapter<T> typeAdapter) {
			super(klass);
			this.typeAdapter = typeAdapter;
		}

		public static <T> ITypeAdapterFactory<T> getInstance(final Class<T> klass, final ToLongFunction<T> encode, final LongFunction<? extends T> decode) {
			return new Factory<>(klass, UnixTimeTypeAdapter.getInstance(encode, decode));
		}

		@Override
		protected TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<? super T> typeToken) {
			return typeAdapter;
		}

	}

}
