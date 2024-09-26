package lsh.ext.gson;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.BaseStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Stream2TypeAdapter<S, K, V, E>
		extends TypeAdapter<S> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final boolean useBeginEnd;
	private final Function<? super String, ? extends K> toKey;
	private final BiFunction<? super K, ? super V, ? extends E> toEntry;
	private final BiFunction<? super JsonReader, ? super Iterator<E>, ? extends S> toReadIterator;
	private final Function<? super S, ? extends Iterator<E>> toWriteIterator;
	private final Function<? super E, String> fromKey;
	private final Function<? super E, ? extends V> fromValue;

	public static <S, K, V, E> TypeAdapter<S> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final boolean useBeginEnd,
			final Function<? super String, ? extends K> toKey,
			final BiFunction<? super K, ? super V, ? extends E> toEntry,
			final BiFunction<? super JsonReader, ? super Iterator<E>, ? extends S> toReadIterator,
			final Function<? super S, ? extends Iterator<E>> toWriteIterator,
			final Function<? super E, String> fromKey,
			final Function<? super E, ? extends V> fromValue
	) {
		return new Stream2TypeAdapter<S, K, V, E>(valueTypeAdapter, useBeginEnd, toKey, toEntry, toReadIterator, toWriteIterator, fromKey, fromValue)
				.nullSafe();
	}

	public static <S, K, V, E> TypeAdapter<S> forObject(
			final TypeAdapter<V> valueTypeAdapter,
			final boolean useBeginEnd,
			final Function<? super String, ? extends K> toKey,
			final BiFunction<? super K, ? super V, ? extends E> toEntry,
			final BiFunction<? super JsonReader, ? super Iterator<E>, ? extends S> toReadIterator,
			final Function<? super S, ? extends Iterator<E>> toWriteIterator,
			final Function<? super E, String> fromKey,
			final Function<? super E, ? extends V> fromValue
	) {
		return getInstance(valueTypeAdapter, useBeginEnd, toKey, toEntry, toReadIterator, toWriteIterator, fromKey, fromValue);
	}

	public static <S, V> TypeAdapter<S> forObject(
			final TypeAdapter<V> valueTypeAdapter,
			final boolean useBeginEnd,
			final BiFunction<? super String, ? super V, ? extends Map.Entry<String, V>> toEntry,
			final BiFunction<? super JsonReader, ? super Iterator<Map.Entry<String, V>>, ? extends S> toReadIterator,
			final Function<? super S, ? extends Iterator<Map.Entry<String, V>>> toWriteIterator,
			final Function<? super Map.Entry<String, V>, ? extends V> fromValue
	) {
		return getInstance(valueTypeAdapter, useBeginEnd, Function.identity(), toEntry, toReadIterator, toWriteIterator, Map.Entry::getKey, fromValue);
	}

	public static <V> TypeAdapter<Iterator<Map.Entry<String, V>>> forObjectAsIterator(
			final TypeAdapter<V> valueTypeAdapter,
			final boolean useBeginEnd
	) {
		return forObject(valueTypeAdapter, useBeginEnd, Function.identity(), AbstractMap.SimpleImmutableEntry::new, (jsonReader, iterator) -> iterator, Function.identity(), Map.Entry::getKey, Map.Entry::getValue);
	}

	public static <K, V, E> TypeAdapter<Iterator<E>> forObjectAsIterator(
			final TypeAdapter<V> valueTypeAdapter,
			final Function<? super String, ? extends K> toKey,
			final BiFunction<? super K, ? super V, ? extends E> toEntry,
			final Function<? super E, String> fromKey,
			final Function<? super E, ? extends V> fromValue,
			final boolean useBeginEnd
	) {
		return Stream2TypeAdapter.<Iterator<E>, K, V, E>forObject(valueTypeAdapter, useBeginEnd, toKey, toEntry, (jsonReader, iterator) -> iterator, Function.identity(), fromKey, fromValue);
	}

	public static <V> TypeAdapter<Stream<Map.Entry<String, V>>> forObjectAsStream(
			final TypeAdapter<V> valueTypeAdapter,
			final boolean useBeginEnd,
			final boolean autoClose
	) {
		return forObjectAsStream(valueTypeAdapter, useBeginEnd, Function.identity(), AbstractMap.SimpleImmutableEntry::new, Map.Entry::getKey, Map.Entry::getValue, autoClose);
	}

	public static <K, V, E> TypeAdapter<Stream<E>> forObjectAsStream(
			final TypeAdapter<V> valueTypeAdapter,
			final boolean useBeginEnd,
			final Function<? super String, ? extends K> toKey,
			final BiFunction<? super K, ? super V, ? extends E> toEntry,
			final Function<? super E, String> fromKey,
			final Function<? super E, ? extends V> fromValue,
			final boolean autoClose
	) {
		return Stream2TypeAdapter.<Stream<E>, K, V, E>forObject(valueTypeAdapter, useBeginEnd, toKey, toEntry, (in, iterator) -> {
			final Stream<E> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.IMMUTABLE), false);
			if ( !autoClose ) {
				return stream;
			}
			return stream
					.onClose(() -> {
						try {
							in.close();
						} catch ( final IOException ex ) {
							throw new RuntimeException(ex);
						}
					});
		}, BaseStream::iterator, fromKey, fromValue);
	}

	public static <V> TypeAdapter<Enumeration<Map.Entry<String, V>>> forObjectAsEnumeration(
			final TypeAdapter<V> valueTypeAdapter,
			final boolean useBeginEnd
	) {
		return forObjectAsEnumeration(valueTypeAdapter, Function.identity(), AbstractMap.SimpleImmutableEntry::new, Map.Entry::getKey, Map.Entry::getValue, useBeginEnd);
	}

	public static <K, V, E> TypeAdapter<Enumeration<E>> forObjectAsEnumeration(
			final TypeAdapter<V> valueTypeAdapter,
			final Function<? super String, ? extends K> toKey,
			final BiFunction<? super K, ? super V, ? extends E> toEntry,
			final Function<? super E, String> fromKey,
			final Function<? super E, ? extends V> fromValue,
			final boolean useBeginEnd
	) {
		return Stream2TypeAdapter.<Enumeration<E>, K, V, E>forObject(valueTypeAdapter, useBeginEnd, toKey, toEntry, (jsonReader, iterator) -> new Enumeration<>() {
			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			@Override
			public E nextElement() {
				return iterator.next();
			}
		}, enumeration -> new Iterator<>() {
			@Override
			public boolean hasNext() {
				return enumeration.hasMoreElements();
			}

			@Override
			public E next() {
				return enumeration.nextElement();
			}
		}, fromKey, fromValue);
	}

	@Override
	public void write(final JsonWriter out, final S stream)
			throws IOException {
		if ( useBeginEnd ) {
			out.beginArray();
		}
		final Iterator<E> iterator = toWriteIterator.apply(stream);
		while ( iterator.hasNext() ) {
			final E entry = iterator.next();
			final String name = fromKey.apply(entry);
			final V value = fromValue.apply(entry);
			out.name(name);
			valueTypeAdapter.write(out, value);
		}
		if ( useBeginEnd ) {
			out.endArray();
		}
	}

	@Override
	public S read(final JsonReader in)
			throws IOException {
		if ( useBeginEnd ) {
			in.beginArray();
		}
		return toReadIterator.apply(in, new Iterator<>() {
			@Override
			public boolean hasNext() {
				try {
					if ( !in.hasNext() ) {
						if ( useBeginEnd ) {
							in.endArray();
						}
						return false;
					}
					return true;
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}

			@Override
			public E next() {
				try {
					final K key = toKey.apply(in.nextName());
					final V value = valueTypeAdapter.read(in);
					return toEntry.apply(key, value);
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}
		});
	}

}
