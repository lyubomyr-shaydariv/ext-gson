package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.util.Map;

import com.google.common.base.Converter;
import com.google.common.base.Supplier;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Represents a type adapter for {@link Multimap} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see MultimapTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class MultimapTypeAdapter<K, V>
		extends TypeAdapter<Multimap<K, V>> {

	private static final Supplier<? extends Multimap<?, ?>> defaultNewMultimapFactory = ArrayListMultimap::create;
	private static final Converter<?, String> defaultKeyConverter = Converter.identity();

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends Multimap<K, V>> newMultimapFactory;
	private final Converter<K, String> keyConverter;
	private final Converter<String, K> reverseKeyConverter;

	private MultimapTypeAdapter(final TypeAdapter<V> valueTypeAdapter, final Supplier<? extends Multimap<K, V>> newMultimapFactory,
			final Converter<K, String> keyConverter) {
		this.valueTypeAdapter = valueTypeAdapter;
		this.newMultimapFactory = newMultimapFactory;
		this.keyConverter = keyConverter;
		reverseKeyConverter = keyConverter.reverse();
	}

	/**
	 * @param valueTypeAdapter Multimap value type adapter
	 * @param <V>              Multimap value type
	 *
	 * @return A {@link MultimapTypeAdapter} instance whose multimap factory is {@link ArrayListMultimap#create()}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<Multimap<String, V>> create(final TypeAdapter<V> valueTypeAdapter) {
		@SuppressWarnings("unchecked")
		final Supplier<? extends Multimap<String, V>> newMultimapFactory = (Supplier<? extends Multimap<String, V>>) defaultNewMultimapFactory;
		@SuppressWarnings("unchecked")
		final Converter<String, String> keyConverter = (Converter<String, String>) defaultKeyConverter;
		return create(valueTypeAdapter, newMultimapFactory, keyConverter);
	}

	/**
	 * @param valueTypeAdapter   Multimap value type adapter
	 * @param newMultimapFactory A {@link Multimap} factory to create instance used while deserialization
	 * @param <V>                Multimap value type
	 *
	 * @return A {@link MultimapTypeAdapter} instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<Multimap<String, V>> create(final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends Multimap<String, V>> newMultimapFactory) {
		@SuppressWarnings("unchecked")
		final Converter<String, String> keyConverter = (Converter<String, String>) defaultKeyConverter;
		return create(valueTypeAdapter, newMultimapFactory, keyConverter);
	}

	/**
	 * @param valueTypeAdapter Multimap value type adapter
	 * @param keyConverter     A converter to convert key to JSON object property names
	 * @param <K>              Multimap key type
	 * @param <V>              Multimap value type
	 *
	 * @return A {@link MultimapTypeAdapter} instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <K, V> TypeAdapter<Multimap<K, V>> create(final TypeAdapter<V> valueTypeAdapter, final Converter<K, String> keyConverter) {
		@SuppressWarnings("unchecked")
		final Supplier<? extends Multimap<K, V>> newMultimapFactory = (Supplier<? extends Multimap<K, V>>) defaultNewMultimapFactory;
		return create(valueTypeAdapter, newMultimapFactory, keyConverter);
	}

	/**
	 * @param valueTypeAdapter   Multimap value type adapter
	 * @param newMultimapFactory A {@link Multimap} factory to create instance used while deserialization
	 * @param keyConverter       A converter to convert key to JSON object property names
	 * @param <K>                Multimap key type
	 * @param <V>                Multimap value type
	 *
	 * @return A {@link MultimapTypeAdapter} instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <K, V> TypeAdapter<Multimap<K, V>> create(final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends Multimap<K, V>> newMultimapFactory, final Converter<K, String> keyConverter) {
		return new MultimapTypeAdapter<>(valueTypeAdapter, newMultimapFactory, keyConverter)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final Multimap<K, V> multimap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<K, V> e : multimap.entries() ) {
			final String key = keyConverter.convert(e.getKey());
			final V value = e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public Multimap<K, V> read(final JsonReader in)
			throws IOException {
		final Multimap<K, V> multimap = newMultimapFactory.get();
		in.beginObject();
		while ( in.hasNext() ) {
			final K key = reverseKeyConverter.convert(in.nextName());
			final V value = valueTypeAdapter.read(in);
			multimap.put(key, value);
		}
		in.endObject();
		return multimap;
	}

}
