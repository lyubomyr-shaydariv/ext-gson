package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.ITypeAdapterFactory;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.BoundedCollection;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;
import org.apache.commons.collections4.keyvalue.UnmodifiableMapEntry;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.collections4.multiset.HashMultiSet;

@UtilityClass
public final class ApacheCommonsCollections4TypeAdapterFactory {

	public static ITypeAdapterFactory<Bag<String>> defaultForBag = forBag(ApacheCommonsCollections4TypeAdapterFactory::defaultBuilderForBag);

	public static ITypeAdapterFactory<Bag<String>> forBag(
			final IBuilder2.ILookup<? super String, ? super Integer, ? extends Bag<String>> lookup
	) {
		return forBag(lookup, Function.identity(), Function.identity());
	}

	public static <E> ITypeAdapterFactory<Bag<E>> forBag(
			final IBuilder2.ILookup<? super E, ? super Integer, ? extends Bag<E>> lookup,
			final Function<? super E, String> toKey,
			final Function<? super String, ? extends E> fromKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(
				Bag.class,
				provider -> {
					final TypeAdapter<Integer> integerTypeAdapter = provider.getTypeAdapterForClass(int.class);
					return ApacheCommonsCollections4TypeAdapter.forBagNCopies(
							integerTypeAdapter,
							lookup.lookup(provider.getTypeToken()),
							toKey,
							fromKey
					);
				}
		);
	}

	// TODO handle all known implementations
	public static <E> Supplier<IBuilder2<E, Integer, Bag<E>>> defaultBuilderForBag(final TypeToken<? super Bag<E>> typeToken) {
		@SuppressWarnings("unchecked")
		final Class<? super Bag<?>> rawType = (Class<? super Bag<?>>) typeToken.getRawType();
		if ( HashBag.class.isAssignableFrom(rawType) ) {
			return () -> Builder.forBagNCopies(HashBag::new);
		}
		return () -> Builder.forBagNCopies(HashBag::new);
	}

	public static final ITypeAdapterFactory<BidiMap<String, Object>> defaultForBidiMap = forBidiMap(ApacheCommonsCollections4TypeAdapterFactory::defaultBuilderForBidiMap);

	public static <V> ITypeAdapterFactory<BidiMap<String, V>> forBidiMap(
			final IBuilder2.ILookup<? super String, ? super V, ? extends BidiMap<String, V>> lookup
	) {
		return forBidiMap(lookup, Function.identity(), Function.identity());
	}

	public static <K, V> ITypeAdapterFactory<BidiMap<K, V>> forBidiMap(
			final IBuilder2.ILookup<? super K, ? super V, ? extends BidiMap<K, V>> lookup,
			final Function<? super K, String> toKey,
			final Function<? super String, ? extends K> fromKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(BidiMap.class, provider -> ApacheCommonsCollections4TypeAdapter.forBidiMap(provider.getTypeAdapter(1), lookup.lookup(provider.getTypeToken()), toKey, fromKey));
	}

	// TODO handle all known implementations
	public static <V> Supplier<IBuilder2<String, V, BidiMap<String, V>>> defaultBuilderForBidiMap(final TypeToken<? super BidiMap<String, V>> typeToken) {
		@SuppressWarnings("unchecked")
		final Class<? extends BidiMap<?, ?>> rawType = (Class<? extends BidiMap<?, ?>>) typeToken.getRawType();
		if ( DualHashBidiMap.class.isAssignableFrom(rawType) ) {
			return () -> IBuilder2.fromMap(new DualHashBidiMap<>());
		}
		if ( DualLinkedHashBidiMap.class.isAssignableFrom(rawType) ) {
			return () -> IBuilder2.fromMap(new DualLinkedHashBidiMap<>());
		}
		return () -> IBuilder2.fromMap(new DualHashBidiMap<>());
	}

	public static ITypeAdapterFactory<BoundedCollection<Object>> defaultForBoundedCollection = forBoundedCollection(ApacheCommonsCollections4TypeAdapterFactory::defaultBuilderForBoundedCollection);

	public static <E> ITypeAdapterFactory<BoundedCollection<E>> forBoundedCollection(
			final IBuilder1.ILookup<? super E, ? extends BoundedCollection<E>> lookup
	) {
		return ITypeAdapterFactory.forClassHierarchy(BoundedCollection.class, provider -> ApacheCommonsCollections4TypeAdapter.forBoundedCollection(provider.getTypeAdapter(0), lookup.lookup(provider.getTypeToken())));
	}

	// TODO handle all known implementations
	public static <E> Supplier<IBuilder1<E, BoundedCollection<E>>> defaultBuilderForBoundedCollection(final TypeToken<? super BoundedCollection<E>> typeToken) {
		throw new UnsupportedOperationException(String.valueOf(typeToken));
	}

	public static final ITypeAdapterFactory<IterableMap<String, Object>> defaultForIterableMap = forIterableMap(ApacheCommonsCollections4TypeAdapterFactory::defaultBuilderForIterableMap);

	public static <V> ITypeAdapterFactory<IterableMap<String, V>> forIterableMap(
			final IBuilder2.ILookup<? super String, ? super V, ? extends IterableMap<String, V>> lookup
	) {
		return forIterableMap(lookup, Function.identity(), Function.identity());
	}

	public static <K, V> ITypeAdapterFactory<IterableMap<K, V>> forIterableMap(
			final IBuilder2.ILookup<? super K, ? super V, ? extends IterableMap<K, V>> lookup,
			final Function<? super K, String> toKey,
			final Function<? super String, ? extends K> fromKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(IterableMap.class, provider -> ApacheCommonsCollections4TypeAdapter.forIterableMap(provider.getTypeAdapter(1), lookup.lookup(provider.getTypeToken()), toKey, fromKey));
	}

	public static <V> Supplier<IBuilder2<String, V, IterableMap<String, V>>> defaultBuilderForIterableMap(final TypeToken<? super IterableMap<String, V>> typeToken) {
		throw new UnsupportedOperationException(String.valueOf(typeToken));
	}

	public static final ITypeAdapterFactory<KeyValue<String, Object>> defaultForKeyValue = forKeyValue(UnmodifiableMapEntry::new);

	public static <V> ITypeAdapterFactory<KeyValue<String, V>> forKeyValue(
			final BiFunction<? super String, ? super V, ? extends KeyValue<String, V>> createEntry
	) {
		return forKeyValue(createEntry, Function.identity(), Function.identity());
	}

	public static <K, V> ITypeAdapterFactory<KeyValue<K, V>> forKeyValue(
			final BiFunction<? super K, ? super V, ? extends KeyValue<K, V>> createEntry,
			final Function<? super K, String> toKey,
			final Function<? super String, ? extends K> fromKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(
				KeyValue.class,
				provider -> ApacheCommonsCollections4TypeAdapter.forKeyValue(provider.getTypeAdapter(1), createEntry, toKey, fromKey)
		);
	}

	public static <V> Supplier<IBuilder2<String, V, KeyValue<String, V>>> defaultBuilderForKeyValue(final TypeToken<? super KeyValue<String, V>> typeToken) {
		throw new UnsupportedOperationException(String.valueOf(typeToken));
	}

	public static ITypeAdapterFactory<MultiSet<Object>> defaultForMultiSet = forMultiSet(ApacheCommonsCollections4TypeAdapterFactory::defaultBuilderForMultiSet);

	public static <E> ITypeAdapterFactory<MultiSet<E>> forMultiSet(
			final IBuilder1.ILookup<? super E, ? extends MultiSet<E>> lookup
	) {
		return ITypeAdapterFactory.forClassHierarchy(MultiSet.class, provider -> ApacheCommonsCollections4TypeAdapter.forMultiSet(provider.getTypeAdapter(0), lookup.lookup(provider.getTypeToken())));
	}

	// TODO handle all known implementations
	public static <E> Supplier<IBuilder1<E, MultiSet<E>>> defaultBuilderForMultiSet(final TypeToken<? super MultiSet<E>> typeToken) {
		@SuppressWarnings("unchecked")
		final Class<? extends MultiSet<?>> rawType = (Class<? extends MultiSet<?>>) typeToken.getRawType();
		if ( HashMultiSet.class.isAssignableFrom(rawType) ) {
			return () -> Builder.forMultiSet(HashMultiSet::new);
		}
		return () -> Builder.forMultiSet(HashMultiSet::new);
	}

	public static final ITypeAdapterFactory<MultiValuedMap<String, Object>> defaultForMultiValueMap = forMultiValueMap(ApacheCommonsCollections4TypeAdapterFactory::defaultBuilderForMultiValuedMap);

	public static <V> ITypeAdapterFactory<MultiValuedMap<String, V>> forMultiValueMap(
			final IBuilder2.ILookup<? super String, ? super V, ? extends MultiValuedMap<String, V>> builderLookup
	) {
		return forMultiValueMap(builderLookup, Function.identity(), Function.identity());
	}

	public static <K, V> ITypeAdapterFactory<MultiValuedMap<K, V>> forMultiValueMap(
			final IBuilder2.ILookup<? super K, ? super V, ? extends MultiValuedMap<K, V>> builderLookup,
			final Function<? super K, String> encodeKey,
			final Function<? super String, ? extends K> decodeKey
	) {
		return ITypeAdapterFactory.forClassHierarchy(MultiValuedMap.class, provider -> ApacheCommonsCollections4TypeAdapter.forMultiValuedMap(provider.getTypeAdapter(1), builderLookup.lookup(provider.getTypeToken()), encodeKey, decodeKey));
	}

	// TODO handle all known implementations
	public static <V> Supplier<IBuilder2<String, V, MultiValuedMap<String, V>>> defaultBuilderForMultiValuedMap(final TypeToken<? super MultiValuedMap<String, V>> typeToken) {
		@SuppressWarnings("unchecked")
		final Class<? extends MultiValuedMap<?, ?>> rawType = (Class<? extends MultiValuedMap<?, ?>>) typeToken.getRawType();
		if ( ArrayListValuedHashMap.class.isAssignableFrom(rawType) ) {
			return () -> Builder.forMultiValuedMap(ArrayListValuedHashMap::new);
		}
		if ( HashSetValuedHashMap.class.isAssignableFrom(rawType) ) {
			return () -> Builder.forMultiValuedMap(HashSetValuedHashMap::new);
		}
		return () -> Builder.forMultiValuedMap(ArrayListValuedHashMap::new);
	}

}
