package lsh.ext.gson.ext.java.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.SequencedCollection;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;

@UtilityClass
public final class UnmodifiableBuilder {

	public static <E> IBuilder1<E, Collection<? extends E>> forUnmodifiableCollection(final Collection<E> collection) {
		return IBuilder1.of(collection::add, () -> Collections.unmodifiableCollection(collection));
	}

	public static <E> IBuilder1<E, List<? extends E>> forUnmodifiableList(final List<E> list) {
		return IBuilder1.of(list::add, () -> Collections.unmodifiableList(list));
	}

	public static <K, V> IBuilder2<K, V, Map<? extends K, ? extends V>> forUnmodifiableMap(final Map<K, V> map) {
		return IBuilder2.of(map::put, () -> Collections.unmodifiableMap(map));
	}

	public static <K, V> IBuilder2<K, V, NavigableMap<? extends K, ? extends V>> forUnmodifiableNavigableMap(final NavigableMap<K, V> navigableMap) {
		return IBuilder2.of(navigableMap::put, () -> Collections.unmodifiableNavigableMap(navigableMap));
	}

	public static <E> IBuilder1<E, NavigableSet<? extends E>> forUnmodifiableNavigableSet(final NavigableSet<E> navigableSet) {
		return IBuilder1.of(navigableSet::add, () -> Collections.unmodifiableNavigableSet(navigableSet));
	}

	public static <E> IBuilder1<E, SequencedCollection<? extends E>> forUnmodifiableCollection(final SequencedCollection<E> sequencedCollection) {
		return IBuilder1.of(sequencedCollection::add, () -> Collections.unmodifiableSequencedCollection(sequencedCollection));
	}

	public static <K, V> IBuilder2<K, V, SequencedMap<? extends K, ? extends V>> forUnmodifiableNavigableMap(final SequencedMap<K, V> sequencedMap) {
		return IBuilder2.of(sequencedMap::put, () -> Collections.unmodifiableSequencedMap(sequencedMap));
	}

	public static <E> IBuilder1<E, SequencedSet<? extends E>> forUnmodifiableSequencedSet(final SequencedSet<E> sequencedSet) {
		return IBuilder1.of(sequencedSet::add, () -> Collections.unmodifiableSequencedSet(sequencedSet));
	}

	public static <E> IBuilder1<E, Set<? extends E>> forUnmodifiableSet(final Set<E> set) {
		return IBuilder1.of(set::add, () -> Collections.unmodifiableSet(set));
	}

	public static <K, V> IBuilder2<K, V, SortedMap<? extends K, ? extends V>> forUnmodifiableSortedMap(final SortedMap<K, V> sortedMap) {
		return IBuilder2.of(sortedMap::put, () -> Collections.unmodifiableSortedMap(sortedMap));
	}

	public static <E> IBuilder1<E, SortedSet<? extends E>> forUnmodifiableSortedSet(final SortedSet<E> sortedSet) {
		return IBuilder1.of(sortedSet::add, () -> Collections.unmodifiableSortedSet(sortedSet));
	}

}
