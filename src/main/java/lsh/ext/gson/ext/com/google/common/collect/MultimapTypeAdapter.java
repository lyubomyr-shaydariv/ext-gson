package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.Multimap;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.IBuilder2;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("Guava")
public final class MultimapTypeAdapter<K, V>
		extends TypeAdapter<Multimap<K, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends IBuilder2<? super K, ? super V, ? extends Multimap<K, V>>> builderFactory;
	private final Function<? super K, String> encodeKey;
	private final Function<? super String, ? extends K> decodeKey;

	public static <V> TypeAdapter<Multimap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super String, ? super V, ? extends Multimap<String, V>>> builderFactory
	) {
		return getInstance(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <K, V> TypeAdapter<Multimap<K, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super K, ? super V, ? extends Multimap<K, V>>> builderFactory,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return new MultimapTypeAdapter<>(valueTypeAdapter, builderFactory, encodeKey, decodeKey)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Multimap<K, V> multimap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<K, V> e : multimap.entries() ) {
			final K key = e.getKey();
			final V value = e.getValue();
			out.name(encodeKey.apply(key));
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public Multimap<K, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super K, ? super V, ? extends Multimap<K, V>> builder = builderFactory.get();
		while ( in.hasNext() ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			builder.accept(decodeKey.apply(key), value);
		}
		in.endObject();
		return builder.build();
	}

}
