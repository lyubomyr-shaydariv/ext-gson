package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.BoundedCollection;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;

@UtilityClass
public final class Builder {

	public static <E, B extends Bag<E>> IBuilder1<E, B> forBag(final Supplier<? extends B> create) {
		return IBuilder1.of(create, Bag::add);
	}

	public static <E, BI extends Bag<E>, BO extends Bag<E>> IBuilder2<E, Integer, BO> forBagNCopies(final Supplier<? extends BI> create, final Function<? super BI, ? extends BO> build) {
		return IBuilder2.of(create, Bag::add, build);
	}

	public static <K, V, MI extends BidiMap<K, V>, MO extends BidiMap<K, V>> IBuilder2<K, V, MO> forBidiMap(final Supplier<? extends MI> create, final Function<? super MI, ? extends MO> build) {
		return IBuilder2.of(create, BidiMap::put, build);
	}

	public static <E, CI extends BoundedCollection<E>, CO extends BoundedCollection<E>> IBuilder1<E, CO> forBoundedCollection(final Supplier<? extends CI> create, final Function<? super CI, ? extends CO> build) {
		return IBuilder1.of(create, Collection::add, build);
	}

	public static <K, V, MI extends IterableMap<K, V>, MO extends IterableMap<K, V>> IBuilder2<K, V, MO> forIterableMap(final Supplier<? extends MI> create, final Function<? super MI, ? extends MO> build) {
		return IBuilder2.of(create, Map::put, build);
	}

	public static <K, V, EI extends KeyValue<K, V>, EO extends KeyValue<K, V>> IBuilder2<K, V, EO> forKeyValue(final BiFunction<? super K, ? super V, ? extends EI> create, final Function<? super EI, ? extends EO> build) {
		return new IBuilder2<>() {
			@Nullable
			private EI keyValue;

			@Override
			public void accept(final K k, final V v) {
				keyValue = create.apply(k, v);
			}

			@Override
			@Nullable
			public EO build() {
				if ( keyValue == null ) {
					return null;
				}
				return build.apply(keyValue);
			}
		};
	}

	public static <E, MI extends MultiSet<E>, MO extends MultiSet<E>> IBuilder1<E, MO> forMultiSet(final Supplier<? extends MI> create, final Function<? super MI, ? extends MO> build) {
		return IBuilder1.of(create, MultiSet::add, build);
	}

	public static <K, V, MI extends MultiValuedMap<K, V>, MO extends MultiValuedMap<K, V>> IBuilder2<K, V, MO> forMultiValuedMap(final Supplier<? extends MI> create, final Function<? super MI, ? extends MO> build) {
		return IBuilder2.of(create, MultiValuedMap::put, build);
	}

}
