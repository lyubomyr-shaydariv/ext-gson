package lsh.ext.gson.adapters.guava;

import java.io.IOException;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Represents a type adapter for {@link Multimap} from Google Guava. Multimaps are deserialized to {@link ArrayListMultimap}.
 *
 * @author Lyubomyr Shaydariv
 * @see MultimapTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class MultimapTypeAdapter<V>
		extends TypeAdapter<Multimap<String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;

	private MultimapTypeAdapter(final TypeAdapter<V> valueTypeAdapter) {
		this.valueTypeAdapter = valueTypeAdapter;
	}

	/**
	 * Returns a {@link Multimap} type adapter.
	 *
	 * @param valueTypeAdapter Multimap value type adapter
	 * @param <V>              Multimap value type parameter
	 *
	 * @return Type adapter instance
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<Multimap<String, V>> get(final TypeAdapter<V> valueTypeAdapter) {
		return new MultimapTypeAdapter<>(valueTypeAdapter);
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final Multimap<String, V> multimap)
			throws IOException {
		out.beginObject();
		for ( final Entry<String, V> e : multimap.entries() ) {
			final String key = e.getKey();
			final V value = e.getValue();
			out.name(key);
			valueTypeAdapter.write(out, value);
		}
		out.endObject();
	}

	@Override
	public Multimap<String, V> read(final JsonReader in)
			throws IOException {
		final Multimap<String, V> multimap = ArrayListMultimap.create();
		in.beginObject();
		while ( in.peek() != JsonToken.END_OBJECT ) {
			final String key = in.nextName();
			final V value = valueTypeAdapter.read(in);
			multimap.put(key, value);
		}
		in.endObject();
		return multimap;
	}

}
