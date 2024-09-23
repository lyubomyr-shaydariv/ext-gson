package lsh.ext.gson.ext.com.google.common.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IBuilder3;

@UtilityClass
public final class ImmutableBuilder {

	public static <K, V> IBuilder2<K, V, BiMap<K, V>> forBiMap() {
		return new IBuilder2<>() {
			private final ImmutableBiMap.Builder<K, V> builder = ImmutableBiMap.builder();

			@Override
			public void accept(final K k, final V v) {
				builder.put(k, v);
			}

			@Override
			public BiMap<K, V> build() {
				return builder.build();
			}
		};
	}

	public static <K, V> IBuilder2<K, V, Multimap<K, V>> forMultimap() {
		return new IBuilder2<>() {
			private final ImmutableMultimap.Builder<K, V> builder = ImmutableMultimap.builder();

			@Override
			public void accept(final K k, final V v) {
				builder.put(k, v);
			}

			@Override
			public Multimap<K, V> build() {
				return builder.build();
			}
		};
	}

	public static <E> IBuilder1<E, Multiset<E>> forMultiset() {
		return new IBuilder1<>() {
			private final ImmutableMultiset.Builder<E> builder = ImmutableMultiset.builder();

			@Override
			public void accept(final E e) {
				builder.add(e);
			}

			@Override
			public Multiset<E> build() {
				return builder.build();
			}
		};
	}

	public static <V> IBuilder3<String, String, V, Table<String, String, V>> forTable() {
		return new IBuilder3<>() {
			private final ImmutableTable.Builder<String, String, V> builder = ImmutableTable.builder();

			@Override
			public void accept(final String rowKey, final String columnKey, final V v) {
				builder.put(rowKey, columnKey, v);
			}

			@Override
			public Table<String, String, V> build() {
				return builder.build();
			}
		};
	}

}
