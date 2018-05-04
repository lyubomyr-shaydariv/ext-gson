package lsh.ext.gson.adapters.guava;

import java.io.IOException;
import java.util.Map;

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
public final class BiMapTypeAdapter<V>
		extends TypeAdapter<BiMap<String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends BiMap<String, V>> newBiMapFactory;

	private BiMapTypeAdapter(final TypeAdapter<V> valueTypeAdapter, final Supplier<? extends BiMap<String, V>> newBiMapFactory) {
		this.valueTypeAdapter = valueTypeAdapter;
		this.newBiMapFactory = newBiMapFactory;
	}

	/**
	 * @param valueTypeAdapter Bidirectional map value type adapter
	 * @param <V>              Bidirectional map value type parameter
	 *
	 * @return A {@link BiMapTypeAdapter} instance whose multimap factory is {@link HashBiMap#create()}.
	 *
	 * @see #get(TypeAdapter, Supplier)
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<BiMap<String, V>> get(final TypeAdapter<V> valueTypeAdapter) {
		return get(valueTypeAdapter, (Supplier<? extends BiMap<String, V>>) HashBiMap::create);
	}

	/**
	 * @param valueTypeAdapter Bidirectional map value type adapter
	 * @param newBiMapFactory  A {@link BiMap} factory to create instance used while deserialization
	 * @param <V>              Bidirectional map value type parameter
	 *
	 * @return A {@link BiMapTypeAdapter} instance.
	 *
	 * @see #get(TypeAdapter)
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<BiMap<String, V>> get(final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends BiMap<String, V>> newBiMapFactory) {
		return new BiMapTypeAdapter<>(valueTypeAdapter, newBiMapFactory)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final BiMap<String, V> biMap)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<String, V> e : biMap.entrySet() ) {
			final String key = e.getKey();
			final V value = e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public BiMap<String, V> read(final JsonReader in)
			throws IOException {
		final BiMap<String, V> biMap = newBiMapFactory.get();
		in.beginObject();
		while ( in.hasNext() ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			biMap.put(key, value);
		}
		in.endObject();
		return biMap;
	}

}
