package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.Container1TypeAdapter;
import lsh.ext.gson.Container2TypeAdapter;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.BoundedCollection;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;

@UtilityClass
public final class ApacheCommonsCollections4TypeAdapter {

	public static <E> TypeAdapter<Bag<E>> forBag(
			final TypeAdapter<E> typeAdapter,
			final Supplier<? extends IBuilder1<? super E, ? extends Bag<E>>> builderFactory
	) {
		return Container1TypeAdapter.getInstance(typeAdapter, Bag::iterator, builderFactory);
	}

	public static TypeAdapter<Bag<String>> forBagNCopies(
			final TypeAdapter<Integer> integerTypeAdapter,
			final Supplier<? extends IBuilder2<? super String, ? super Integer, ? extends Bag<String>>> builderFactory
	) {
		return forBagNCopies(integerTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <E> TypeAdapter<Bag<E>> forBagNCopies(
			final TypeAdapter<Integer> integerTypeAdapter,
			final Supplier<? extends IBuilder2<? super E, ? super Integer, ? extends Bag<E>>> builderFactory,
			final Function<? super E, String> toName,
			final Function<? super String, ? extends E> fromName
	) {
		return Container2TypeAdapter.<E, Integer, Bag<E>, Map.Entry<String, Integer>>getInstance(
				integerTypeAdapter,
				es -> IteratorUtils.transformedIterator(
						es.uniqueSet().iterator(),
						e -> new AbstractMap.SimpleImmutableEntry<>(toName.apply(e), es.getCount(e))
				),
				e -> fromName.apply(e.getKey()),
				Map.Entry::getValue,
				toName,
				fromName,
				builderFactory
		);
	}

	public static <V> TypeAdapter<BidiMap<String, V>> forBidiMap(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super String, ? super V, ? extends BidiMap<String, V>>> builderFactory
	) {
		return forBidiMap(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	public static <K, V> TypeAdapter<BidiMap<K, V>> forBidiMap(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super K, ? super V, ? extends BidiMap<K, V>>> builderFactory,
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

	public static <E> TypeAdapter<BoundedCollection<E>> forBoundedCollection(
			final TypeAdapter<E> elementTypeAdapter,
			final Supplier<? extends IBuilder1<? super E, ? extends BoundedCollection<E>>> builderFactory
	) {
		return Container1TypeAdapter.getInstance(elementTypeAdapter, BoundedCollection::iterator, builderFactory);
	}

	public static <K, V> TypeAdapter<IterableMap<K, V>> forIterableMap(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super K, ? super V, ? extends IterableMap<K, V>>> builderFactory,
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

	public static <E> TypeAdapter<MultiSet<E>> forMultiSet(
			final TypeAdapter<E> elementTypeAdapter,
			final Supplier<? extends IBuilder1<? super E, ? extends MultiSet<E>>> builderFactory
	) {
		return Container1TypeAdapter.getInstance(elementTypeAdapter, MultiSet::iterator, builderFactory);
	}

	public static <V> TypeAdapter<MultiValuedMap<String, V>> forMultiValuedMap(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super String, ? super V, ? extends MultiValuedMap<String, V>>> builderFactory
	) {
		return forMultiValuedMap(valueTypeAdapter, builderFactory, Function.identity(), Function.identity());
	}

	// TODO use MapIterator<K, V>
	public static <K, V> TypeAdapter<MultiValuedMap<K, V>> forMultiValuedMap(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder2<? super K, ? super V, ? extends MultiValuedMap<K, V>>> builderFactory,
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

}
