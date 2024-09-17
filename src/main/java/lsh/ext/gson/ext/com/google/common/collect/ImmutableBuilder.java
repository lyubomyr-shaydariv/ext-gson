package lsh.ext.gson.ext.com.google.common.collect;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.Table;
import com.google.common.graph.Graph;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.ImmutableNetwork;
import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.Network;
import com.google.common.graph.ValueGraph;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IBuilder3;

@UtilityClass
public final class ImmutableBuilder {

	public static <K, V> IBuilder2<K, V, BiMap<K, V>> forBiMap() {
		return forBiMap(ImmutableBiMap.builder());
	}

	public static <K, V> IBuilder2<K, V, BiMap<K, V>> forBiMap(
			final ImmutableBiMap.Builder<K, V> builder
	) {
		return new IBuilder2<>() {
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

	public static <E> IBuilder1<E, Collection<E>> forCollection(
			final ImmutableCollection.Builder<E> builder
	) {
		return new IBuilder1<>() {
			@Override
			public void accept(final E e) {
				builder.add(e);
			}

			@Override
			public Collection<E> build() {
				return builder.build();
			}
		};
	}

	public static <N> IBuilder0<Graph<N>> forGraph(
			final ImmutableGraph.Builder<N> builder
	) {
		return builder::build;
	}

	public static <N> IBuilder1<N, Graph<N>> forGraphAddNode(
			final ImmutableGraph.Builder<N> builder
	) {
		return new IBuilder1<>() {
			@Override
			public void accept(final N n) {
				builder.addNode(n);
			}

			@Override
			public Graph<N> build() {
				return builder.build();
			}
		};
	}

	public static <E> IBuilder1<E, List<E>> forList() {
		return forList(ImmutableList.builder());
	}

	public static <E> IBuilder1<E, List<E>> forList(
			final ImmutableList.Builder<E> builder
	) {
		return new IBuilder1<>() {
			@Override
			public void accept(final E e) {
				builder.add(e);
			}

			@Override
			public List<E> build() {
				return builder.build();
			}
		};
	}

	public static <K, V> IBuilder2<K, V, ListMultimap<K, V>> forListMultimap() {
		return forListMultimap(ImmutableListMultimap.builder());
	}

	public static <K, V> IBuilder2<K, V, ListMultimap<K, V>> forListMultimap(
			final ImmutableListMultimap.Builder<K, V> builder
	) {
		return new IBuilder2<>() {
			@Override
			public void accept(final K k, final V v) {
				builder.put(k, v);
			}

			@Override
			public ListMultimap<K, V> build() {
				return builder.build();
			}
		};
	}

	public static <K, V> IBuilder2<K, V, Map<K, V>> forMap() {
		return forMap(ImmutableMap.builder());
	}

	public static <K, V> IBuilder2<K, V, Map<K, V>> forMap(
			final ImmutableMap.Builder<K, V> builder
	) {
		return new IBuilder2<>() {
			@Override
			public void accept(final K k, final V v) {
				builder.put(k, v);
			}

			@Override
			public Map<K, V> build() {
				return builder.build();
			}
		};
	}

	public static <K, V> IBuilder2<K, V, Multimap<K, V>> forMultimap() {
		return forMultimap(ImmutableMultimap.builder());
	}

	public static <K, V> IBuilder2<K, V, Multimap<K, V>> forMultimap(
			final ImmutableMultimap.Builder<K, V> builder
	) {
		return new IBuilder2<>() {
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
		return forMultiset(ImmutableMultiset.builder());
	}

	public static <E> IBuilder1<E, Multiset<E>> forMultiset(
			final ImmutableMultiset.Builder<E> builder
	) {
		return new IBuilder1<>() {
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

	public static <N, E> IBuilder1<N, Network<N, E>> forNetworkAddNode(
			final ImmutableNetwork.Builder<N, E> builder
	) {
		return new IBuilder1<>() {
			@Override
			public void accept(final N n) {
				builder.addNode(n);
			}

			@Override
			public Network<N, E> build() {
				return builder.build();
			}
		};
	}

	public static <N, E> IBuilder3<N, N, E, Network<N, E>> forNetworkAddEdge(
			final ImmutableNetwork.Builder<N, E> builder
	) {
		return new IBuilder3<>() {
			@Override
			public void accept(final N n1, final N n2, final E e) {
				builder.addEdge(n1, n2, e);
			}

			@Override
			public Network<N, E> build() {
				return builder.build();
			}
		};
	}

	public static <C extends Comparable<?>, V> IBuilder2<C, V, RangeMap<C, V>> forRangeMap(
			final BiFunction<? super C, ? super V, Range<C>> toRange
	) {
		return forRangeMap(ImmutableRangeMap.builder(), toRange);
	}

	public static <C extends Comparable<?>, V> IBuilder2<C, V, RangeMap<C, V>> forRangeMap(
			final ImmutableRangeMap.Builder<C, V> builder,
			final BiFunction<? super C, ? super V, Range<C>> toRange
	) {
		return new IBuilder2<>() {
			@Override
			public void accept(final C k, final V v) {
				builder.put(toRange.apply(k, v), v);
			}

			@Override
			public RangeMap<C, V> build() {
				return builder.build();
			}
		};
	}

	public static <C extends Comparable<?>> IBuilder1<C, RangeSet<C>> forRangeSet(
			final Function<? super C, Range<C>> toRange
	) {
		return forRangeSet(ImmutableRangeSet.builder(), toRange);
	}

	public static <C extends Comparable<?>> IBuilder1<C, RangeSet<C>> forRangeSet(
			final ImmutableRangeSet.Builder<C> builder,
			final Function<? super C, Range<C>> toRange
	) {
		return new IBuilder1<>() {
			@Override
			public void accept(final C c) {
				builder.add(toRange.apply(c));
			}

			@Override
			public RangeSet<C> build() {
				return builder.build();
			}
		};
	}

	public static <E> IBuilder1<E, Set<E>> forSet() {
		return forSet(ImmutableSet.builder());
	}

	public static <E> IBuilder1<E, Set<E>> forSet(
			final ImmutableSet.Builder<E> builder
	) {
		return new IBuilder1<>() {
			@Override
			public void accept(final E e) {
				builder.add(e);
			}

			@Override
			public Set<E> build() {
				return builder.build();
			}
		};
	}

	public static <K, V> IBuilder2<K, V, SetMultimap<K, V>> forSetMultimap() {
		return forSetMultimap(ImmutableSetMultimap.builder());
	}

	public static <K, V> IBuilder2<K, V, SetMultimap<K, V>> forSetMultimap(
			final ImmutableSetMultimap.Builder<K, V> builder
	) {
		return new IBuilder2<>() {
			@Override
			public void accept(final K k, final V v) {
				builder.put(k, v);
			}

			@Override
			public SetMultimap<K, V> build() {
				return builder.build();
			}
		};
	}

	public static <K, V> IBuilder2<K, V, SortedMap<K, V>> forSortedMap() {
		return forSortedMap(ImmutableSortedMap.builder());
	}

	public static <K, V> IBuilder2<K, V, SortedMap<K, V>> forSortedMap(
			final ImmutableSortedMap.Builder<K, V> builder
	) {
		return new IBuilder2<>() {
			@Override
			public void accept(final K k, final V v) {
				builder.put(k, v);
			}

			@Override
			public SortedMap<K, V> build() {
				return builder.build();
			}
		};
	}

	public static <E extends Comparable<?>> IBuilder1<E, SortedMultiset<E>> forSortedMultiset() {
		return forSortedMultiset(ImmutableSortedMultiset.naturalOrder());
	}

	public static <E extends Comparable<?>> IBuilder1<E, SortedMultiset<E>> forSortedMultiset(
			final ImmutableSortedMultiset.Builder<E> builder
	) {
		return new IBuilder1<>() {
			@Override
			public void accept(final E e) {
				builder.add(e);
			}

			@Override
			public SortedMultiset<E> build() {
				return builder.build();
			}
		};
	}

	public static <E extends Comparable<?>> IBuilder1<E, SortedSet<E>> forSortedSet() {
		return forSortedSet(ImmutableSortedSet.naturalOrder());
	}

	public static <E extends Comparable<?>> IBuilder1<E, SortedSet<E>> forSortedSet(
			final ImmutableSortedSet.Builder<E> builder
	) {
		return new IBuilder1<>() {
			@Override
			public void accept(final E e) {
				builder.add(e);
			}

			@Override
			public SortedSet<E> build() {
				return builder.build();
			}
		};
	}

	public static <V> IBuilder3<String, String, V, Table<String, String, V>> forTable() {
		return forTable(ImmutableTable.builder());
	}

	public static <V> IBuilder3<String, String, V, Table<String, String, V>> forTable(
			final ImmutableTable.Builder<String, String, V> builder
	) {
		return new IBuilder3<>() {
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

	public static <N, V> IBuilder3<N, N, V, ValueGraph<N, V>> forValueGraphPutEdgeValue(
			final ImmutableValueGraph.Builder<N, V> builder
	) {
		return new IBuilder3<>() {
			@Override
			public void accept(final N n1, final N n2, final V v) {
				builder.putEdgeValue(n1, n2, v);
			}

			@Override
			public ValueGraph<N, V> build() {
				return builder.build();
			}
		};
	}

	public static <N, V> IBuilder1<N, ValueGraph<N, V>> forValueGraphAddNode(
			final ImmutableValueGraph.Builder<N, V> builder
	) {
		return new IBuilder1<>() {
			@Override
			public void accept(final N n) {
				builder.addNode(n);
			}

			@Override
			public ValueGraph<N, V> build() {
				return builder.build();
			}
		};
	}

}
