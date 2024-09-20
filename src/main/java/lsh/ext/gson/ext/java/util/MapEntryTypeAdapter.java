package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.IFunction1;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MapEntryTypeAdapter<K, V>
		extends TypeAdapter<Map.Entry<K, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final IFunction1<? super K, String> encodeKey;
	private final IFunction1<? super String, ? extends K> decodeKey;

	public static <K, V> TypeAdapter<Map.Entry<K, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFunction1<? super K, String> encodeKey,
			final IFunction1<? super String, ? extends K> decodeKey
	) {
		return new MapEntryTypeAdapter<K, V>(valueTypeAdapter, encodeKey, decodeKey)
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
			entry = new AbstractMap.SimpleImmutableEntry<>(null, null); // TODO strategy!
		} else {
			final K k = decodeKey.apply(in.nextName());
			final V v = valueTypeAdapter.read(in);
			if ( in.hasNext() ) {
				final String excessiveKey = in.nextName();
				throw new IllegalStateException(String.format("Expected a single property object with key `%s` but also encountered `%s`", k, excessiveKey));
			}
			entry = new AbstractMap.SimpleImmutableEntry<>(k, v); // TODO strategy!
		}
		in.endObject();
		return entry;
	}

}
