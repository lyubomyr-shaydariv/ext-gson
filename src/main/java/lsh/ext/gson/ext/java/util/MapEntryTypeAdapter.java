package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.IFunction1;
import lsh.ext.gson.IFunction2;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MapEntryTypeAdapter<K, V>
		extends TypeAdapter<Map.Entry<K, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final IFunction1<? super K, String> encodeKey;
	private final IFunction1<? super String, ? extends K> decodeKey;
	private final IFunction2<? super K, ? super V, ? extends Map.Entry<K, V>> createEntry;

	public static <K, V> TypeAdapter<Map.Entry<K, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFunction1<? super K, String> encodeKey,
			final IFunction1<? super String, ? extends K> decodeKey,
			final IFunction2<? super K, ? super V, ? extends Map.Entry<K, V>> createEntry
	) {
		return new MapEntryTypeAdapter<K, V>(valueTypeAdapter, encodeKey, decodeKey, createEntry)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Map.Entry<K, V> entry)
			throws IOException {
		out.beginObject();
		out.name(encodeKey.apply(entry.getKey()));
		valueTypeAdapter.write(out, entry.getValue());
		out.endObject();
	}

	@Override
	public Map.Entry<K, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final Map.Entry<K, V> entry;
		if ( !in.hasNext() ) {
			entry = createEntry.apply(null, null);
		} else {
			final K k = decodeKey.apply(in.nextName());
			final V v = valueTypeAdapter.read(in);
			if ( in.hasNext() ) {
				final String excessiveKey = in.nextName();
				throw new IllegalStateException(String.format("Expected a single property object with key `%s` but also encountered `%s`", k, excessiveKey));
			}
			entry = createEntry.apply(k, v);
		}
		in.endObject();
		return entry;
	}

}
