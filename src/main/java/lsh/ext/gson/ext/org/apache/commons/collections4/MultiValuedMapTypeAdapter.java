package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.IBuilder2;
import org.apache.commons.collections4.MultiValuedMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiValuedMapTypeAdapter<K, V>
		extends TypeAdapter<MultiValuedMap<K, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends IBuilder2<? super K, ? super V, ? extends MultiValuedMap<K, V>>> builderFactory;
	private final Function<? super K, String> encodeKey;
	private final Function<? super String, ? extends K> decodeKey;

	public static <V> TypeAdapter<MultiValuedMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super String, ? super V, ? extends MultiValuedMap<String, V>>> builderFactory
	) {
		return getInstance(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <K, V> TypeAdapter<MultiValuedMap<K, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super K, ? super V, ? extends MultiValuedMap<K, V>>> builderFactory,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return new MultiValuedMapTypeAdapter<>(valueTypeAdapter, builderFactory, encodeKey, decodeKey)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final MultiValuedMap<K, V> multiValuedMap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<K, V> e : multiValuedMap.entries() ) {
			final String key = encodeKey.apply(e.getKey());
			final V value = e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public MultiValuedMap<K, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super K, ? super V, ? extends MultiValuedMap<K, V>> builder = builderFactory.get();
		while ( in.hasNext() ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			builder.accept(decodeKey.apply(key), value);
		}
		in.endObject();
		return builder.build();
	}

}
