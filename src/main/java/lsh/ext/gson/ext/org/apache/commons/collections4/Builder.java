package lsh.ext.gson.ext.org.apache.commons.collections4;

import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder2;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.MultiValuedMap;

@UtilityClass
public final class Builder {

	public static <E, B extends Bag<E>> IBuilder2<E, Integer, B> forBag(final B bag) {
		return new IBuilder2<>() {
			@Override
			public void accept(final E e, final Integer n) {
				bag.add(e, n);
			}

			@Override
			public B build() {
				return bag;
			}
		};
	}

	public static <K, V, M extends MultiValuedMap<K, V>> IBuilder2<K, V, M> forMultiValuedMap(final M multiValuedMap) {
		return new IBuilder2<>() {
			@Override
			public void accept(final K k, final V v) {
				multiValuedMap.put(k, v);
			}

			@Override
			public M build() {
				return multiValuedMap;
			}
		};
	}

}
