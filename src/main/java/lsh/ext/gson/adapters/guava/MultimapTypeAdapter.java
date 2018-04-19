package lsh.ext.gson.adapters.guava;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
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

	private final Gson gson;
	private final Type valueType;

	private MultimapTypeAdapter(final Gson gson, final Type valueType) {
		this.gson = gson;
		this.valueType = valueType;
	}

	/**
	 * Returns a {@link Multimap} type adapter.
	 *
	 * @param gson      Gson instance
	 * @param valueType Multimap value type
	 * @param <V>       Multimap value type parameter
	 *
	 * @return Type adapter instance
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<Multimap<String, V>> get(final Gson gson, final Type valueType) {
		return new MultimapTypeAdapter<>(gson, valueType);
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
			gson.toJson(value, valueType, out);
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
			final V value = gson.fromJson(in, valueType);
			multimap.put(key, value);
		}
		in.endObject();
		return multimap;
	}

}
