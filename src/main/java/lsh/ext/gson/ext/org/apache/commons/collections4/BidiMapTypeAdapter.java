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
import org.apache.commons.collections4.BidiMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BidiMapTypeAdapter<K, V>
		extends TypeAdapter<BidiMap<K, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends IBuilder2<? super K, ? super V, ? extends BidiMap<K, V>>> builderFactory;
	private final Function<? super K, String> encodeKey;
	private final Function<? super String, ? extends K> decodeKey;

	public static <V> TypeAdapter<BidiMap<String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super String, ? super V, ? extends BidiMap<String, V>>> builderFactory
	) {
		return getInstance(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <K, V> TypeAdapter<BidiMap<K, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super K, ? super V, ? extends BidiMap<K, V>>> builderFactory,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return new BidiMapTypeAdapter<>(valueTypeAdapter, builderFactory, encodeKey, decodeKey)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final BidiMap<K, V> bidiMap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<K, V> e : bidiMap.entrySet() ) {
			final String key = encodeKey.apply(e.getKey());
			final V value = e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public BidiMap<K, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super K, ? super V, ? extends BidiMap<K, V>> builder = builderFactory.get();
		while ( in.hasNext() ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			builder.accept(decodeKey.apply(key), value);
		}
		in.endObject();
		return builder.build();
	}

}
