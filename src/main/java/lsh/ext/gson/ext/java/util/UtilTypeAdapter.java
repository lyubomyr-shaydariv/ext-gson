package lsh.ext.gson.ext.java.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.Container2TypeAdapter;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.SingleEntryTypeAdapter;
import lsh.ext.gson.Stream1TypeAdapter;

@UtilityClass
public final class UtilTypeAdapter {

	public static <E> TypeAdapter<Iterator<E>> forIterator(final TypeAdapter<E> elementTypeAdapter, final boolean useBeginEnd) {
		return Stream1TypeAdapter.forArray(elementTypeAdapter, useBeginEnd, (jsonReader, iterator) -> iterator, Function.identity());
	}

	public static <E> TypeAdapter<Enumeration<E>> forEnumeration(final TypeAdapter<E> elementTypeAdapter, final boolean useBeginEnd) {
		return Stream1TypeAdapter.forArray(elementTypeAdapter, useBeginEnd, (jsonReader, iterator) -> new Enumeration<>() {
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
		});
	}

	public static <V> TypeAdapter<Map<String, V>> forMap(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super String, ? super V, ? extends Map<String, V>>> builderFactory
	) {
		return forMap(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <K, V> TypeAdapter<Map<K, V>> forMap(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super K, ? super V, ? extends Map<K, V>>> builderFactory,
			final Function<? super K, String> toName,
			final Function<? super String, ? extends K> fromName
	) {
		return Container2TypeAdapter.getInstance(
				valueTypeAdapter,
				map -> map.entrySet().iterator(),
				Map.Entry::getKey,
				Map.Entry::getValue,
				toName,
				fromName,
				builderFactory
		);
	}

	public static <K, V> TypeAdapter<Map.Entry<K, V>> forMapEntry(
			final TypeAdapter<V> valueTypeAdapter,
			final BiFunction<? super K, ? super V, ? extends Map.Entry<K, V>> createEntry,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return SingleEntryTypeAdapter.getInstance(
				valueTypeAdapter,
				Map.Entry::getKey,
				Map.Entry::getValue,
				createEntry,
				encodeKey,
				decodeKey
		);
	}

}
