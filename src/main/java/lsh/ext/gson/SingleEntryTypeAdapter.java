package lsh.ext.gson;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public final class SingleEntryTypeAdapter<E, K, V>
		extends TypeAdapter<E> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Function<? super E, ? extends K> fromEntryToKey; // TODO is it reasonable to encode entry key right from the entry without an intermediate function?
	private final Function<? super E, ? extends V> fromEntryToValue;
	private final BiFunction<? super K, ? super V, ? extends E> createEntry;
	private final Function<? super K, String> encodeKey;
	private final Function<? super String, ? extends K> decodeKey;

	public static <E, K, V> TypeAdapter<E> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Function<? super E, ? extends K> fromEntryToKey,
			final Function<? super E, ? extends V> fromEntryToValue,
			final BiFunction<? super K, ? super V, ? extends E> createEntry,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return new SingleEntryTypeAdapter<E, K, V>(valueTypeAdapter, fromEntryToKey, fromEntryToValue, createEntry, encodeKey, decodeKey)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final E entry)
			throws IOException {
		out.beginObject();
		final K key = fromEntryToKey.apply(entry);
		out.name(encodeKey.apply(key));
		final V value = fromEntryToValue.apply(entry);
		valueTypeAdapter.write(out, value);
		out.endObject();
	}

	@Override
	public E read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final E entry;
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
