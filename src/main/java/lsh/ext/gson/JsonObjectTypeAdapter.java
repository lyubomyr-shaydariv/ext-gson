package lsh.ext.gson;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class JsonObjectTypeAdapter<A, K, V>
		extends TypeAdapter<A> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Function<? super K, String> encodeKey;
	private final Function<? super String, ? extends K> decodeKey;
	private final Function<? super A, ? extends Iterable<? extends Map.Entry<K, V>>> toIterable;
	private final Supplier<? extends IBuilder2<? super K, ? super V, ? extends A>> createBuilder;

	public static <A, K, V> TypeAdapter<A> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey,
			final Function<? super A, ? extends Iterable<? extends Map.Entry<K, V>>> toIterable,
			final Supplier<? extends IBuilder2<? super K, ? super V, ? extends A>> createBuilder
	) {
		return new JsonObjectTypeAdapter<>(valueTypeAdapter, encodeKey, decodeKey, toIterable, createBuilder)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final A object)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<K, V> e : toIterable.apply(object) ) {
			out.name(encodeKey.apply(e.getKey()));
			valueTypeAdapter.write(out, e.getValue());
		}
		out.endObject();
	}

	@Override
	public A read(final JsonReader in)
			throws IOException {
		final IBuilder2<? super K, ? super V, ? extends A> builder = createBuilder.get();
		in.beginObject();
		while ( in.hasNext() ) {
			final String name = in.nextName();
			final V v = valueTypeAdapter.read(in);
			builder.accept(decodeKey.apply(name), v);
		}
		in.endObject();
		return builder.build();
	}

}
