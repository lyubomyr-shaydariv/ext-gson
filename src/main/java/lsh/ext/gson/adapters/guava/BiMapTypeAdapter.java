package lsh.ext.gson.adapters.guava;

import java.io.IOException;
import java.util.Map;

import com.google.common.base.Converter;
import com.google.common.base.Supplier;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Represents a type adapter for {@link BiMap} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see BiMapTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class BiMapTypeAdapter<K, V>
		extends TypeAdapter<BiMap<K, V>> {

	private static final Supplier<? extends BiMap<?, ?>> defaultNewBiMapFactory = HashBiMap::create;
	private static final Converter<?, String> defaultKeyConverter = Converter.identity();

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends BiMap<K, V>> newBiMapFactory;
	private final Converter<K, String> keyConverter;
	private final Converter<String, K> reverseKeyConverter;

	private BiMapTypeAdapter(final TypeAdapter<V> valueTypeAdapter, final Supplier<? extends BiMap<K, V>> newBiMapFactory,
			final Converter<K, String> keyConverter) {
		this.valueTypeAdapter = valueTypeAdapter;
		this.newBiMapFactory = newBiMapFactory;
		this.keyConverter = keyConverter;
		reverseKeyConverter = keyConverter.reverse();
	}

	/**
	 * @param valueTypeAdapter Bidirectional map value type adapter
	 * @param <V>              Bidirectional map value type parameter
	 *
	 * @return A {@link BiMapTypeAdapter} instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<BiMap<String, V>> get(final TypeAdapter<V> valueTypeAdapter) {
		@SuppressWarnings("unchecked")
		final Supplier<? extends BiMap<String, V>> newBiMapFactory = (Supplier<? extends BiMap<String, V>>) defaultNewBiMapFactory;
		@SuppressWarnings("unchecked")
		final Converter<String, String> keyConverter = (Converter<String, String>) defaultKeyConverter;
		return get(valueTypeAdapter, newBiMapFactory, keyConverter);
	}

	/**
	 * @param valueTypeAdapter Bidirectional map value type adapter
	 * @param newBiMapFactory  A {@link BiMap} factory to create instance used while deserialization
	 * @param <V>              Bidirectional map value type parameter
	 *
	 * @return A {@link BiMapTypeAdapter} instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<BiMap<String, V>> get(final TypeAdapter<V> valueTypeAdapter, final Supplier<? extends BiMap<String, V>> newBiMapFactory) {
		@SuppressWarnings("unchecked")
		final Converter<String, String> keyConverter = (Converter<String, String>) defaultKeyConverter;
		return get(valueTypeAdapter, newBiMapFactory, keyConverter);
	}

	/**
	 * @param valueTypeAdapter Bidirectional map value type adapter
	 * @param keyConverter     A converter to convert key to JSON object property names
	 * @param <V>              Bidirectional map value type parameter
	 *
	 * @return A {@link BiMapTypeAdapter} instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <K, V> TypeAdapter<BiMap<K, V>> get(final TypeAdapter<V> valueTypeAdapter, final Converter<K, String> keyConverter) {
		@SuppressWarnings("unchecked")
		final Supplier<? extends BiMap<K, V>> newBiMapFactory = (Supplier<? extends BiMap<K, V>>) defaultNewBiMapFactory;
		return get(valueTypeAdapter, newBiMapFactory, keyConverter);
	}

	/**
	 * @param valueTypeAdapter Bidirectional map value type adapter
	 * @param newBiMapFactory  A {@link BiMap} factory to create instance used while deserialization
	 * @param keyConverter     A converter to convert key to JSON object property names
	 * @param <V>              Bidirectional map value type parameter
	 *
	 * @return A {@link BiMapTypeAdapter} instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <K, V> TypeAdapter<BiMap<K, V>> get(final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends BiMap<K, V>> newBiMapFactory, final Converter<K, String> keyConverter) {
		return new BiMapTypeAdapter<>(valueTypeAdapter, newBiMapFactory, keyConverter)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final BiMap<K, V> biMap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<K, V> e : biMap.entrySet() ) {
			final String key = keyConverter.convert(e.getKey());
			final V value = e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public BiMap<K, V> read(final JsonReader in)
			throws IOException {
		final BiMap<K, V> biMap = newBiMapFactory.get();
		in.beginObject();
		while ( in.hasNext() ) {
			final K key = reverseKeyConverter.convert(in.nextName());
			final V value = valueTypeAdapter.read(in);
			biMap.put(key, value);
		}
		in.endObject();
		return biMap;
	}

}
