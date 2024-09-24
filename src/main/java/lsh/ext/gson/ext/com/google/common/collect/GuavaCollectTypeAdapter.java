package lsh.ext.gson.ext.com.google.common.collect;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.Container1TypeAdapter;
import lsh.ext.gson.Container2TypeAdapter;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IBuilder3;

@UtilityClass
public final class GuavaCollectTypeAdapter {

	public static <V> TypeAdapter<BiMap<String, V>> forBiMap(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super String, ? super V, ? extends BiMap<String, V>>> builderFactory
	) {
		return forBiMap(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <K, V> TypeAdapter<BiMap<K, V>> forBiMap(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super K, ? super V, ? extends BiMap<K, V>>> builderFactory,
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


	public static <V> TypeAdapter<Multimap<String, V>> forMultimap(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super String, ? super V, ? extends Multimap<String, V>>> builderFactory
	) {
		return forMultimap(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <K, V> TypeAdapter<Multimap<K, V>> forMultimap(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super K, ? super V, ? extends Multimap<K, V>>> builderFactory,
			final Function<? super K, String> toName,
			final Function<? super String, ? extends K> fromName
	) {
		return Container2TypeAdapter.getInstance(
				valueTypeAdapter,
				map -> map.entries().iterator(),
				Map.Entry::getKey,
				Map.Entry::getValue,
				toName,
				fromName,
				builderFactory
		);
	}

	public static <E> TypeAdapter<Multiset<E>> forMultiset(
			final TypeAdapter<E> valueTypeAdapter,
			final Supplier<? extends IBuilder1<? super E, ? extends Multiset<E>>> builderFactory
	) {
		return Container1TypeAdapter.getInstance(
				valueTypeAdapter,
				es -> forMultiset(es.entrySet()
						.iterator()
				),
				builderFactory
		);
	}

	static <E> Iterator<E> forMultiset(final Iterator<? extends Multiset.Entry<E>> iterator) {
		return new Iterator<>() {
			private E element;
			private int count;

			@Override
			public boolean hasNext() {
				if ( count != 0 ) {
					return true;
				}
				final boolean hasNext = iterator.hasNext();
				if ( hasNext ) {
					move();
				}
				return hasNext;
			}

			@Override
			public E next() {
				if ( count == 0 ) {
					move();
					return element;
				}
				count--;
				return element;
			}

			private void move() {
				final Multiset.Entry<E> entry = iterator.next();
				element = entry.getElement();
				count = entry.getCount();
			}
		};
	}

	static <V> TypeAdapter<Table<String, String, V>> forTable(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder3<? super String, ? super String, ? super V, ? extends Table<String, String, V>>> builderFactory
	) {
		return TableTypeAdapter.getInstance(valueTypeAdapter, builderFactory);
	}

	static <R, C, V> TypeAdapter<Table<R, C, V>> forTable(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder3<? super R, ? super C, ? super V, ? extends Table<R, C, V>>> builderFactory,
			final Function<? super R, String> toRowName,
			final Function<? super String, ? extends R> fromRowName,
			final Function<? super C, String> toColumnName,
			final Function<? super String, ? extends C> fromColumnName
	) {
		return TableTypeAdapter.getInstance(valueTypeAdapter, builderFactory, toRowName, fromRowName, toColumnName, fromColumnName);
	}

}
