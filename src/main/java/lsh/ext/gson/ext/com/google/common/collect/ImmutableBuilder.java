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

	public static <K, V> IBuilder2<K, V, BiMap<K, V>> forBiMap(final ImmutableBiMap.Builder<K, V> builder) {
		return IBuilder2.of(builder::put, builder::build);
	}

	public static <E> IBuilder1<E, Collection<E>> forCollection(final ImmutableCollection.Builder<E> builder) {
		return IBuilder1.of(builder::add, builder::build);
	}

	public static <N> IBuilder0<Graph<N>> forGraph(final ImmutableGraph.Builder<N> builder) {
		return builder::build;
	}

	public static <N> IBuilder1<N, Graph<N>> forGraphAddNode(final ImmutableGraph.Builder<N> builder) {
		return IBuilder1.of(builder::addNode, builder::build);
	}

	public static <E> IBuilder1<E, List<E>> forList() {
		return forList(ImmutableList.builder());
	}

	public static <E> IBuilder1<E, List<E>> forList(final ImmutableList.Builder<E> builder) {
		return IBuilder1.of(builder::add, builder::build);
	}

	public static <K, V> IBuilder2<K, V, ListMultimap<K, V>> forListMultimap() {
		return forListMultimap(ImmutableListMultimap.builder());
	}

	public static <K, V> IBuilder2<K, V, ListMultimap<K, V>> forListMultimap(final ImmutableListMultimap.Builder<K, V> builder) {
		return IBuilder2.of(builder::put, builder::build);
	}

	public static <K, V> IBuilder2<K, V, Map<K, V>> forMap() {
		return forMap(ImmutableMap.builder());
	}

	public static <K, V> IBuilder2<K, V, Map<K, V>> forMap(final ImmutableMap.Builder<K, V> builder) {
		return IBuilder2.of(builder::put, builder::build);
	}

	public static <K, V> IBuilder2<K, V, Multimap<K, V>> forMultimap() {
		return forMultimap(ImmutableMultimap.builder());
	}

	public static <K, V> IBuilder2<K, V, Multimap<K, V>> forMultimap(final ImmutableMultimap.Builder<K, V> builder) {
		return IBuilder2.of(builder::put, builder::build);
	}

	public static <E> IBuilder1<E, Multiset<E>> forMultiset() {
		return forMultiset(ImmutableMultiset.builder());
	}

	public static <E> IBuilder1<E, Multiset<E>> forMultiset(
			final ImmutableMultiset.Builder<E> builder
	) {
		return IBuilder1.of(builder::add, builder::build);
	}

	public static <N, E> IBuilder1<N, Network<N, E>> forNetworkAddNode(final ImmutableNetwork.Builder<N, E> builder) {
		return IBuilder1.of(builder::addNode, builder::build);
	}

	public static <N, E> IBuilder3<N, N, E, Network<N, E>> forNetworkAddEdge(final ImmutableNetwork.Builder<N, E> builder) {
		return IBuilder3.of(builder::addEdge, builder::build);
	}

	public static <K extends Comparable<?>, V> IBuilder2<K, V, RangeMap<K, V>> forRangeMap(final BiFunction<? super K, ? super V, Range<K>> toRange) {
		return forRangeMap(ImmutableRangeMap.builder(), toRange);
	}

	public static <K extends Comparable<?>, V> IBuilder2<K, V, RangeMap<K, V>> forRangeMap(final ImmutableRangeMap.Builder<K, V> builder, final BiFunction<? super K, ? super V, Range<K>> toRange) {
		return IBuilder2.of((k, v) -> builder.put(toRange.apply(k, v), v), builder::build);
	}

	public static <K extends Comparable<?>> IBuilder1<K, RangeSet<K>> forRangeSet(final Function<? super K, Range<K>> toRange) {
		return forRangeSet(ImmutableRangeSet.builder(), toRange);
	}

	public static <K extends Comparable<?>> IBuilder1<K, RangeSet<K>> forRangeSet(final ImmutableRangeSet.Builder<K> builder, final Function<? super K, Range<K>> toRange) {
		return IBuilder1.of(k -> builder.add(toRange.apply(k)), builder::build);
	}

	public static <E> IBuilder1<E, Set<E>> forSet() {
		return forSet(ImmutableSet.builder());
	}

	public static <E> IBuilder1<E, Set<E>> forSet(final ImmutableSet.Builder<E> builder) {
		return IBuilder1.of(builder::add, builder::build);
	}

	public static <K, V> IBuilder2<K, V, SetMultimap<K, V>> forSetMultimap() {
		return forSetMultimap(ImmutableSetMultimap.builder());
	}

	public static <K, V> IBuilder2<K, V, SetMultimap<K, V>> forSetMultimap(final ImmutableSetMultimap.Builder<K, V> builder) {
		return IBuilder2.of(builder::put, builder::build);
	}

	public static <K extends Comparable<?>, V> IBuilder2<K, V, SortedMap<K, V>> forSortedMap() {
		return forSortedMap(ImmutableSortedMap.naturalOrder());
	}

	public static <K, V> IBuilder2<K, V, SortedMap<K, V>> forSortedMap(final ImmutableSortedMap.Builder<K, V> builder) {
		return IBuilder2.of(builder::put, builder::build);
	}

	public static <E extends Comparable<?>> IBuilder1<E, SortedMultiset<E>> forSortedMultiset() {
		return forSortedMultiset(ImmutableSortedMultiset.naturalOrder());
	}

	public static <E extends Comparable<?>> IBuilder1<E, SortedMultiset<E>> forSortedMultiset(final ImmutableSortedMultiset.Builder<E> builder) {
		return IBuilder1.of(builder::add, builder::build);
	}

	public static <E extends Comparable<?>> IBuilder1<E, SortedSet<E>> forSortedSet() {
		return forSortedSet(ImmutableSortedSet.naturalOrder());
	}

	public static <E extends Comparable<?>> IBuilder1<E, SortedSet<E>> forSortedSet(final ImmutableSortedSet.Builder<E> builder) {
		return IBuilder1.of(builder::add, builder::build);
	}

	public static <R, C, V> IBuilder3<R, C, V, Table<R, C, V>> forTable() {
		return forTable(ImmutableTable.builder());
	}

	public static <R, C, V> IBuilder3<R, C, V, Table<R, C, V>> forTable(final ImmutableTable.Builder<R, C, V> builder) {
		return IBuilder3.of(builder::put, builder::build);
	}

	public static <N, V> IBuilder3<N, N, V, ValueGraph<N, V>> forValueGraphPutEdgeValue(final ImmutableValueGraph.Builder<N, V> builder) {
		return IBuilder3.of(builder::putEdgeValue, builder::build);
	}

	public static <N, V> IBuilder1<N, ValueGraph<N, V>> forValueGraphAddNode(final ImmutableValueGraph.Builder<N, V> builder) {
		return IBuilder1.of(builder::addNode, builder::build);
	}

}
