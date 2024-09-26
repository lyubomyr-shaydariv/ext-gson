package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BoundedCollection;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;

@UtilityClass
public final class Builder {

	public static <E, B extends Bag<E>> IBuilder1<E, B> forBag(final Supplier<? extends B> create) {
		return IBuilder1.of(create, Bag::add);
	}

	public static <E, B extends Bag<E>> IBuilder2<E, Integer, B> forBagNCopies(final Supplier<? extends B> create) {
		return IBuilder2.of(create, Bag::add);
	}

	public static <E, M extends MultiSet<E>> IBuilder1<E, M> forMultiSet(final Supplier<? extends M> create) {
		return IBuilder1.of(create, MultiSet::add);
	}

	public static <E, C extends BoundedCollection<E>> IBuilder1<E, C> forBoundedCollection(final Supplier<? extends C> create) {
		return IBuilder1.of(create, Collection::add);
	}

	public static <K, V, M extends IterableMap<K, V>> IBuilder2<K, V, M> forIterableMap(final Supplier<? extends M> create) {
		return IBuilder2.of(create, Map::put);
	}

	public static <K, V, M extends MultiValuedMap<K, V>> IBuilder2<K, V, M> forMultiValuedMap(final Supplier<? extends M> create) {
		return IBuilder2.of(create, MultiValuedMap::put);
	}

}
